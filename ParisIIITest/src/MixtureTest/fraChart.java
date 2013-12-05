package MixtureTest;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

import javax.imageio.*; // needed to write image to file

import java.io.*; // needed for file readers/writers
import java.awt.event.*; // needed for mousemotionadapter
import java.text.*; // needed for DecimalFormat
import java.awt.font.*;


/**
 * Used to draw property charts for mixtures
 * @author TMARTI02
 *
 */
public class fraChart extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6751103519589645460L;

	public JLabelChart jlChart = new JLabelChart();
	
//	JLabel jlPosition = new JLabel();
	
	JLabel jlResults=new JLabel();
	
	JButton jbOK = new JButton();
	
	
	double [] X={0,1,2,3,4,5}; // experimental value
	double [] Y1={0,1.317,1.734,3.450,4.026,4.701}; //predicted/calculated value
	double [] err=null; // error on predicted value
	double [] Y2=null;//second predicted value
	
	String nameY1="";
	String nameY2="";

	double [] Xexp;// experimental value
	double [] Yexp;// experimental value
	
	String Xtitle="Mol fraction component 1"; // title of x-axis
	String Ytitle="Mixture property"; // title of y-axis
	String ChartTitle="Mixture property vs composition"; // chart title

	
	public boolean doDrawStatsMAE=false;
	public boolean doDrawStatsR2=false;
	public boolean doDrawLegend=true;
	
	Font fontGridLines=new Font( "Arial", Font.PLAIN, 10 );
	Font fontTitle=new Font( "Arial", Font.BOLD, 11);
	Font fontLegend = new Font("Arial", Font.PLAIN, 11);

	
	//double [] Y2={0,1.253,2.260,3.266,4.272,5.278};
	
	
	public fraChart(double []x,double[]y,String title,String xtitle,String ytitle) {
		try {
			
			this.X=x;
			this.Y1=y;
			this.ChartTitle=title;
			this.Ytitle=ytitle;
			this.Xtitle=xtitle;
			jbInit();
			myInit();
		} catch ( Exception ex ) {
			System.out.println("fraMapSources/Init, error = "+ex);
		}
	}
	
	public fraChart(double []x,double[]y,double []y2,String title,String xtitle,String ytitle) {
		try {
			
			this.X=x;
			this.Y1=y;
			this.Y2=y2;
			this.ChartTitle=title;
			this.Ytitle=ytitle;
			this.Xtitle=xtitle;
			jbInit();
			myInit();
		} catch ( Exception ex ) {
			System.out.println("fraMapSources/Init, error = "+ex);
		}
	}
	public fraChart(double []x,double[]y,double []y2,String nameY1,String nameY2,double []xexp,double []yexp,String title,String xtitle,String ytitle) {
		try {
			
			this.X=x;
			this.Y1=y;
			this.Y2=y2;
			this.Xexp=xexp;
			this.Yexp=yexp;
			
			this.nameY1=nameY1;
			this.nameY2=nameY2;
			
			this.ChartTitle=title;
			this.Ytitle=ytitle;
			this.Xtitle=xtitle;
			jbInit();
			myInit();
		} catch ( Exception ex ) {
			System.out.println("fraMapSources/Init, error = "+ex);
		}
	}
	
	public fraChart(double []x,double[]y,String nameY1,double []xexp,double []yexp,String title,String xtitle,String ytitle) {
		try {
			
			this.X=x;
			this.Y1=y;
			this.Xexp=xexp;
			this.Yexp=yexp;
			
			this.nameY1=nameY1;
			
			this.ChartTitle=title;
			this.Ytitle=ytitle;
			this.Xtitle=xtitle;
			jbInit();
			myInit();
		} catch ( Exception ex ) {
			System.out.println("fraMapSources/Init, error = "+ex);
		}
	}
	
	public void WriteImageToFile(String filename,String OutputFolder) {

			
		try {

			int w=jlChart.getWidth();
			
			BufferedImage ImgSrc = new BufferedImage(w, w,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = (Graphics2D) ImgSrc.getGraphics();

			this.jlChart.DrawChart(g2);

			File myFile = new File(OutputFolder+"/"+filename);

			ImageIO.write(ImgSrc, "png", myFile);
		} catch (Exception e) {
			System.out.println("Exception creating pic file");
			e.printStackTrace();
		}
	}
	
	void jbInit() throws Exception {
		
		
		int size=400;
		jlChart.setBorder( BorderFactory.createLineBorder( Color.black ) );
		jlChart.setBounds( new Rectangle( 25, 25, size, size ) );
		jlChart.setVisible(true);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		this.setModal(true);
		this.setSize( new Dimension( 460, 500 ) );
		this.setTitle("Fit results");
		this.getContentPane().setLayout( null );
//		jlPosition.setBounds(new Rectangle(240, 363, 251, 52));
		jbOK.setBounds(new Rectangle(150, 430, 103, 31));
		jbOK.setText("Close");
		jbOK.addActionListener(new fraChart_jbOK_actionAdapter(this));
		
		jlResults.setSize(200,70);
		jlResults.setLocation(500, 200);
		jlResults.setForeground(Color.blue);
		
		
		this.getContentPane().add( jlChart, null );
		this.getContentPane().add( jlResults, null );
//		this.getContentPane().add(jlPosition, null);     
		this.getContentPane().add(jbOK, null);  
//		this.setVisible(true);
	}
	
	void myInit() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		// center w.r.t screen:
		this.setLocation( ( screenSize.width - frameSize.width ) / 2,
				( screenSize.height - frameSize.height ) / 2 );
		
//		ToxPredictor.Utilities.Utilities.SetFonts(this.getContentPane());
		
		
		
	}
	
	class JLabelChart extends JLabel {
		// this class maps the sources relative to each other
		
		double maxNum=10;
		
		double Xmin,Ymin,Xmax,Ymax,xincr,yincr;
		int margin;
		int wdraw;
		
		int w; // width of this label
		DecimalFormat myF=new DecimalFormat("0");		
		DecimalFormat myF1=new DecimalFormat("0.0");
		
		int cw=6; // width of symbols;
		
		public void paintComponent( Graphics g ) {
			
			Graphics2D g2 = ( Graphics2D ) g;
			this.DrawChart(g2);
								  
//			this.WriteImageToFile();
			
			// draw fitted line:
			//this.DrawFittedLine(g2);
			
												
		} // end paintComponent
		
		void DrawChart(Graphics2D g2) {
			w=this.getWidth(); // width of drawing area;
			
			margin=(int)((double)w*0.15); // provides space for axis numbers and titles
			
			wdraw=w-2*margin;

			FontRenderContext frc = g2.getFontRenderContext();

			
			g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON );
			
			g2.setColor(Color.white);			
			g2.fillRect(0,0,w,w); // make background white			

			SetAxisBounds();
			
			
			// draw horizontal grid lines:
			DrawHorizontalGridlines(g2,frc);
			
			//draw vertical gridlines:
			DrawVerticalGridlines(g2,frc);
			
			// draw in titles:
			DrawTitles(g2,frc);	
			
			// draw Y=X line:
			
//			this.DrawYEqualsXLine(g2);

//			DrawStatsR2_RMSE(g2, frc);
			if (doDrawStatsMAE) DrawStatsMAE(g2, frc);
			
			if (doDrawLegend) DrawLegend(g2, frc);
			
			if (doDrawStatsR2) DrawStatsR2_RMSE(g2, frc);
			
			// draw symbols for exp vs pred series:
			DrawSymbols (g2);
			
			if (err instanceof double []) {
				DrawErrorBars(g2);
			}
			
			

		}
		
		
		
		
//		void SetAxisBounds() {
//			
//			Xmin=1e8;
//			Ymin=1e8;
//			Xmax=-1e8;
//			Ymax=-1e8;
//			
//			for (int i=0; i<X.length;i++) {
//				if (X[i]<Xmin) Xmin=X[i];
//				if (X[i]>Xmax) Xmax=X[i];
//				
//				if (Y1[i]<Ymin) Ymin=Y1[i];
//				//if (Y2[i]<Ymin) Ymin=Y2[i];
//				
//				if (Y1[i]>Ymax) Ymax=Y1[i];
//				//if (Y2[i]>Ymax) Ymax=Y2[i];
//								
//			}
//			
////			Xmin=Math.floor(Xmin)-1;
////			Xmax=Math.ceil(Xmax)+1;
////			Ymin=Math.floor(Ymin)-1;
////			Ymax=Math.ceil(Ymax)+1;
//			
//			Xmin=Math.floor(Xmin);
//			Xmax=Math.ceil(Xmax);
//			Ymin=Math.floor(Ymin);
//			Ymax=Math.ceil(Ymax);
//
//			
//
//			if (Xmax/20>1) {
//				double bob=Math.floor((Xmax-Xmin)/maxNum/10);
//				if (bob==0)bob=1;
//				bob*=10;
//				
//				Xmin=bob*Math.floor(Xmin/bob);
//				Xmax=bob*Math.ceil(Xmin/bob);
//				
//				double bob4=Math.floor((Xmax-Xmin)/maxNum/10);
//				bob4*=10;
//				
//				if (bob4>bob) {
//					Xmin=bob4*Math.floor(Xmin/bob4);
//					Xmax=bob4*Math.ceil(Xmin/bob4);
//				}
//			}
//			
//			
//			if (Ymax/20>1) {
//				double bob=Math.floor((Ymax-Ymin)/maxNum/10);
//				if (bob==0)bob=1;
//				bob*=10;
//				
//				double bob2=Math.floor(Ymin/bob);
//				Ymin=bob*bob2;
//				double bob3=Math.ceil(Ymax/bob);
//				Ymax=bob*bob3;
//			}
//
//
//			
//			// make bounds on both axes the same for tox graph:
//			if (Xmin<Ymin) Ymin=Xmin;
//			else Xmin=Ymin;
//			
//			if (Xmax>Ymax) Ymax=Xmax;
//			else Xmax=Ymax;
//			
////			System.out.println("Xmin="+Xmin);
////			System.out.println("Xmax="+Xmax);
////			System.out.println("Ymin="+Ymin);
////			System.out.println("Ymax="+Ymax);
//			
//			
//		}
		
//		void SetAxisBounds() {
//			
//			double min=1e8;
//			double max=-1e8;
//			
//			for (int i=0; i<X.length;i++) {
//				if (X[i]<min) min=X[i];
//				if (X[i]>max) max=X[i];
//				
//				if (Y1[i]<min) min=Y1[i];
//				if (Y1[i]>max) max=Y1[i];
//				//if (Y2[i]>Ymax) Ymax=Y2[i];
//								
//			}
//			
//			min=Math.floor(min);
//			max=Math.ceil(max);
//			
////			System.out.println(min);
////			System.out.println(max);
//			
//			if ((max-min)/10.0>1) {
//				double bob=Math.ceil((max-min)/maxNum/10);
//			
//				if (bob==0)bob=1;
//				bob*=10;
//				
////				double incrTemp=(Ymax-Ymin)/10;
////
////				if (incrTemp>1 && incrTemp<=5) bob=5;
////				if (incrTemp>5 && incrTemp<=10) bob=10;
////
////				if (incrTemp>10 && incrTemp<=50) bob=50;
////				if (incrTemp>50 && incrTemp<=100) bob=100;
//
//				min=Math.floor(min/bob)*bob;//make a multiple of bob
//				max=Math.ceil(max/bob)*bob;//make a multiple of bob
//			}
//			
//			Xmin=min;
//			Ymin=Xmin;
//			
//			Xmax=max;
//			Ymax=Xmax;
//			
//			incr=(Ymax-Ymin)/10;
//			
////			System.out.println("incr ="+incr);
//
//			if (incr>0.1 && incr<=0.5) incr=0.5;
//			if (incr>0.5 && incr<=1) incr=1;
//
//			if (incr>1 && incr<=5) incr=5;
//			if (incr>5 && incr<=10) incr=10;
//
//			if (incr>10 && incr<=50) incr=50;
//			if (incr>50 && incr<=100) incr=100;
//			
//			System.out.println("Xmin ="+Xmin);
//			System.out.println("Xmax ="+Xmax);
//			System.out.println("incr ="+incr);
//			
////			if (max/20.0>1) {
////			double bob=Math.floor((max-min)/maxNum/10);
////			if (bob==0)bob=1;
////			bob*=10;
////			
////			min=bob*Math.floor(min/bob);
////			max=bob*Math.ceil(max/bob);
////			
////			System.out.println(min);
////			System.out.println(max);
////			
////			double bob4=Math.floor((max-min)/maxNum/10);
////			bob4*=10;
////			
////			if (bob4>bob) {
////				min=bob4*Math.floor(min/bob4);
////				max=bob4*Math.ceil(max/bob4);
////			}
////		}
//
//
//		}
		
		void SetAxisBounds() {
			
			double min1=1e8;
			double max1=-1e8;
			
			Xmin=0;
			Xmax=1;
			
			for (int i=0; i<X.length;i++) {
				if (Y1[i]<min1) min1=Y1[i];
				if (Y2!=null) if (Y2[i]<min1) min1=Y2[i];
				if (Yexp!=null && i<Yexp.length) if (Yexp[i]<min1) min1=Yexp[i];
				
				if (Y1[i]>max1) max1=Y1[i];
				if (Y2!=null) if (Y2[i]>max1) max1=Y2[i];
				if (Yexp!=null && i<Yexp.length) if (Yexp[i]>max1) max1=Yexp[i];

			}
			//***********************************************
			//keep constant scale here
//			min1=20;
//			max1=30;
			//***********************************************
			
			yincr=(max1-min1)/10;

			if (yincr<=0.1) yincr=0.1;
			else if (yincr>0.1 && yincr<=0.5) yincr=0.5;
			else if (yincr>0.5 && yincr<=1) yincr=1;
			else if (yincr>1 && yincr<=5) yincr=5;
			else if (yincr>5 && yincr<=10) yincr=10;
			else if (yincr>10 && yincr<=50) yincr=50;
			else if (yincr>50 && yincr<=100) yincr=100;
			else if (yincr>100) yincr=100;
			
			if (yincr<1 && max1>100) yincr=1;//avoid overlapping x axis labels
			
			double min=Math.floor(min1/yincr)*yincr;//make a multiple of incr
			double max=Math.ceil(max1/yincr)*yincr;//make a multiple of incr

			double minDev=Math.abs(min-min1);
			double maxDev=Math.abs(max-max1);

			if (minDev<0.5*yincr) min-=yincr;//pad graph a bit if nearest point is too close
			if (maxDev<0.5*yincr) max+=yincr;//pad graph a bit if nearest point is too close
			
			Ymin=min;
			Ymax=max;
			
			xincr=0.1;
			
		}

		
		void DrawHorizontalGridlines(Graphics2D g2, FontRenderContext frc) {
//			System.out.println("enter draw horizontal");
			// this method draws horizontal gridlines and labels them
			
//			double bob=Math.ceil((Ymax-Ymin)/maxNum/10);
//			if(bob==0) bob=1;
//			bob*=10;
//			
//			double incr=1.0; //12-14-11
//			boolean shrink=false;
//			if (Ymax-Ymin<=1) {
//				shrink=true;// we need go in 0.1 increments otherwise we wont have any divisions
//				incr=0.1;//12-14-11
//			}
				
//			for (double y=Ymin;y<=Ymax; y+=incr) {
//				
//				if (Ymax-Ymin>maxNum && Ymax-Ymin>20) {
//					if ((y)%bob!=0) continue;
//				}
//				
//				double yy = ( Ymax - y) * (wdraw) / ( Ymax-Ymin);
//				int iy=(int)Math.round(yy)+margin;
//				
//				g2.setColor(Color.lightGray);
//				g2.drawLine(margin,iy,wdraw+margin,iy);
//				
//				g2.setColor(Color.black);    
//								
//				// draw in numbers on y-axis:
//				String strNum=myF.format(y);
//				if (shrink) strNum=myF1.format(y);
//
//				double swidth = (double) g2.getFont().getStringBounds(strNum, frc).
//				getWidth();
//				
//				double sheight = (double) g2.getFont().getStringBounds(strNum, frc).
//				getHeight();
//																				
//				g2.drawString(strNum,margin-(int)swidth-3,iy+(int)(sheight/2));
//				
//			}//end loop
			
//			System.out.println("incr ="+incr);
			
			g2.setFont(fontGridLines);
			
			for (double y=Ymin;y<=Ymax+0.0001; y+=yincr) {
//				System.out.println("y="+y+"\t"+incr);
				
				double yy = ( Ymax - y) * (wdraw) / ( Ymax-Ymin);
				int iy=(int)Math.round(yy)+margin;
				
				g2.setColor(Color.lightGray);
				g2.drawLine(margin,iy,wdraw+margin,iy);
				
				g2.setColor(Color.black);    
								
				// draw in numbers on y-axis:
				String strNum=myF.format(y);
				if (yincr<1) strNum=myF1.format(y);

				double swidth = (double) g2.getFont().getStringBounds(strNum, frc).
				getWidth();
				
				double sheight = (double) g2.getFont().getStringBounds(strNum, frc).
				getHeight();
																				
				g2.drawString(strNum,margin-(int)swidth-3,iy+(int)(sheight/2));
				
			}//end loop
			
//			System.out.println("leave draw horizontal");

			g2.setColor(Color.lightGray);
			g2.drawLine(margin,margin,wdraw+margin,margin);//draw min line
			int iy=(int)Math.round(wdraw)+margin;
			g2.drawLine(margin,iy,wdraw+margin,iy);

			
		}
		
		void DrawVerticalGridlines(Graphics2D g2, FontRenderContext frc) {
			// draw vertical grid lines
			g2.setFont(fontGridLines);
			
			
			for (double x=Xmin;x<=Xmax+0.0001; x+=xincr) {
//				System.out.println("x="+x);
				
				double xx = ( x - Xmin ) * (wdraw) / ( Xmax-Xmin);
				int ix=(int)Math.round(xx)+margin;
				
				g2.setColor(Color.lightGray);
				g2.drawLine(ix,margin,ix,wdraw+margin);
				
				g2.setColor(Color.black);        
				
				String strNum=myF.format(x);
				if (xincr<1) strNum=myF1.format(x);
					
				double swidth = g2.getFont().getStringBounds(strNum, frc).getWidth();			
				double sheight = g2.getFont().getStringBounds(strNum, frc).getHeight();
				                																										
				// draw in numbers on x-axis:
				g2.drawString(strNum+"",ix-(int)(swidth/2.0),wdraw+margin+(int)sheight+3);
				
								
			}
			g2.setColor(Color.lightGray);
			g2.drawLine(margin,margin,margin,wdraw+margin);
			g2.drawLine(wdraw+margin,margin,wdraw+margin,wdraw+margin);
//			System.out.println("Xmax="+Xmax);

//			double bob=Math.ceil((Xmax-Xmin)/maxNum/10);
//			if(bob==0) bob=1;
//			bob*=10;
//
//			double incr=1.0; //12-14-11
//			boolean shrink=false;
//			if (Xmax-Xmin<=1) {
//				shrink=true;// we need go in 0.1 increments otherwise we wont have any divisions
//				incr=0.1;//12-14-11
//			}
//			
//			for (double x=Xmin;x<=Xmax; x+=incr) {
//				
//				if (Xmax-Xmin>maxNum && Xmax-Xmin>20) {
//					if ((x)%bob!=0) continue;
//				}
//				
//				double xx = ( x - Xmin ) * (wdraw) / ( Xmax-Xmin);
//				int ix=(int)Math.round(xx)+margin;
//				
//				g2.setColor(Color.lightGray);
//				g2.drawLine(ix,margin,ix,wdraw+margin);
//				
//				g2.setColor(Color.black);        
//				
//				String strNum=myF.format(x);
//				if (shrink) strNum=myF1.format(x);
//					
//				double swidth = g2.getFont().getStringBounds(strNum, frc).getWidth();			
//				double sheight = g2.getFont().getStringBounds(strNum, frc).getHeight();
//				                																										
//				// draw in numbers on x-axis:
//				g2.drawString(strNum+"",ix-(int)(swidth/2.0),wdraw+margin+(int)sheight+3);
//				
//								
//			}
			
			
			
			
		
		}
		
		void DrawTitles(Graphics2D g2,FontRenderContext frc) {

			g2.setFont( fontGridLines);
			
			String strMax=myF.format(Ymax);
			if (yincr<1) strMax=myF1.format(Ymax);

			double widthMaxNumber = (double) g2.getFont().getStringBounds(strMax, frc).getWidth();
			double heightMaxNumber = (double) g2.getFont().getStringBounds(strMax, frc).getHeight();
			
			int textPadding=5;
			
			g2.setColor(Color.black);
			g2.setFont(fontTitle);
			
			double sheight = (double) g2.getFont().getStringBounds("P", frc).
			getHeight();
			
			double swidthXtitle = (double) g2.getFont().getStringBounds(Xtitle, frc).
			getWidth();
			double swidthYtitle = (double) g2.getFont().getStringBounds(Ytitle, frc).
			getWidth();
			double swidthTitle = (double) g2.getFont().getStringBounds(ChartTitle, frc).
			getWidth();

			double xx=(w-swidthXtitle)/2.0;
			int ixx=(int)Math.round(xx);
			
			double yy=wdraw+margin+heightMaxNumber+sheight+textPadding;
//			double yy=wdraw+margin+0.5*margin+sheight/2;
			int iyy=(int)Math.round(yy);
			
			// draw title of x-axis:			
			g2.drawString(Xtitle,ixx,iyy);
//			(int)(w +margin+sheight+20)
			// ***************************************************************
			
			xx=swidthYtitle+(getHeight()-swidthYtitle)/2.0;				
			ixx=(int)Math.round(xx);
		
//			yy=margin-(int)widthMaxNumber-sheight-textPadding;
			yy=margin-(int)widthMaxNumber-10;
			
//			yy=0.5*margin-sheight/2;
			iyy=(int)Math.round(yy);
			
			int angle=-90;
			g2.rotate(angle*Math.PI/180.0);
			g2.setFont(fontTitle);
//			draw title of y-axis:	
			g2.drawString(Ytitle,-ixx,iyy);			
			g2.rotate(-angle*Math.PI/180.0);
			
			// ***************************************************************
			
			// now draw chart title:
			g2.setFont( fontTitle);
			
			
			sheight = (double) g2.getFont().getStringBounds(ChartTitle, frc).
			getHeight();
			
			g2.drawString(ChartTitle,(int)((w-swidthTitle)/2.0),margin/2+(int)(sheight/2));
					
		}
		
		/*void DrawFittedLine(Graphics2D g2) {
			g2.setColor(Color.black);
			
			double x1 = ( X[1] - Xmin ) * wdraw / ( Xmax-Xmin);
			int ix1 = ( int ) Math.round( x1)+margin;
			
			double x2 = ( X[X.length-1] - Xmin ) * wdraw / ( Xmax-Xmin);
			int ix2 = ( int ) Math.round( x2)+margin;
			
			double y1 = ( Ymax - Y2[1] ) * wdraw / ( Ymax-Ymin );
			int iy1 = ( int ) Math.round( y1)+margin;
			
			double y2 = ( Ymax - Y2[X.length-1] ) * wdraw / ( Ymax-Ymin );
			int iy2 = ( int ) Math.round( y2)+margin;
			
			g2.drawLine(ix1,iy1,ix2,iy2);
		}
		*/
		
		void DrawYEqualsXLine(Graphics2D g2) {
			g2.setColor(Color.black);
			
			
			int ix1 = margin; // x=0					
			int ix2 = wdraw+margin;	// x=Xmax					
			int iy1 = wdraw+margin; // y=0
			int iy2 = margin; // y=Ymax
			
			g2.drawLine(ix1,iy1,ix2,iy2);
		}
		
		void DrawSymbols(Graphics2D g2) {
			
			boolean drawY1symbols=false;
			boolean drawY2symbols=false;
			
			if (Xexp!=null) {
				
				for (int i = 0; i < Xexp.length; i++) {   
					double xsymbol = ( Xexp[i] - Xmin ) * (wdraw) / ( Xmax-Xmin);
					int ixsymbol = ( int ) Math.round( xsymbol -cw/2)+margin;

					double ysymbol = ( Ymax - Yexp[i] ) * (wdraw) / ( Ymax-Ymin );
					int iysymbol = ( int ) Math.round( ysymbol -cw/2)+margin;

					g2.setColor(Color.black);
//					g2.fillOval(ixsymbol, iysymbol, cw, cw);
					g2.setColor(Color.black);
					g2.drawOval(ixsymbol, iysymbol, cw, cw);
				}
			}

			
//			 now draw in data:                  
			for (int i = 0; i < X.length; i++) {            	  
				
				if (drawY1symbols) {
					double xsymbol = ( X[i] - Xmin ) * (wdraw) / ( Xmax-Xmin);
					int ixsymbol = ( int ) Math.round( xsymbol -cw/2)+margin;
					
					double ysymbol = ( Ymax - Y1[i] ) * (wdraw) / ( Ymax-Ymin );
					int iysymbol = ( int ) Math.round( ysymbol -cw/2)+margin;

					g2.setColor(Color.red);
					g2.fillOval(ixsymbol, iysymbol, cw, cw);
					
					g2.setColor(Color.black);
					g2.drawOval(ixsymbol, iysymbol, cw, cw);
				}
				
				if (i<X.length-1) {
					g2.setColor(Color.red);
					
					double x = ( X[i] - Xmin ) * (wdraw) / ( Xmax-Xmin);
					int ix = ( int ) Math.round(x)+margin;

					double x2 = ( X[i+1] - Xmin ) * (wdraw) / ( Xmax-Xmin);
					int ix2 = ( int ) Math.round(x2)+margin;
					
					double y = ( Ymax - Y1[i] ) * (wdraw) / ( Ymax-Ymin );
					int iy = ( int ) Math.round(y)+margin;

					double y2 = ( Ymax - Y1[i+1] ) * (wdraw) / ( Ymax-Ymin );
					int iy2 = ( int ) Math.round(y2)+margin;
					
					g2.drawLine(ix, iy, ix2, iy2);

				}
				
				
				
				if (Y2!=null) {
					
					double xsymbol = ( X[i] - Xmin ) * (wdraw) / ( Xmax-Xmin);
					int ixsymbol = ( int ) Math.round( xsymbol -cw/2)+margin;

					double y2symbol = ( Ymax - Y2[i] ) * (wdraw) / ( Ymax-Ymin );
					int iy2symbol = ( int ) Math.round( y2symbol -cw/2)+margin;
					
					if (drawY2symbols) {
						g2.setColor(Color.blue);
						g2.fillRect(ixsymbol, iy2symbol, cw, cw);
						
						g2.setColor(Color.black);
						g2.drawRect(ixsymbol, iy2symbol, cw, cw);
						
					}
					
					if (i<X.length-1) {
						g2.setColor(Color.blue);
						
						double x = ( X[i] - Xmin ) * (wdraw) / ( Xmax-Xmin);
						int ix = ( int ) Math.round(x)+margin;

						double x2 = ( X[i+1] - Xmin ) * (wdraw) / ( Xmax-Xmin);
						int ix2 = ( int ) Math.round( x2)+margin;
						
						double y2 = ( Ymax - Y2[i] ) * (wdraw) / ( Ymax-Ymin );
						int iy2 = ( int ) Math.round(y2)+margin;

						double y2_2 = ( Ymax - Y2[i+1] ) * (wdraw) / ( Ymax-Ymin );
						int iy2_2 = ( int ) Math.round( y2_2)+margin;
						
						g2.drawLine(ix, iy2, ix2, iy2_2);

					}
					
				}

			}
			
		}
		
		void DrawErrorBars(Graphics2D g2) {
//			 now draw in data:                  
			for (int i = 0; i <X.length; i++) {            	  
				double x = ( X[i] - Xmin ) * (wdraw) / ( Xmax-Xmin);
				int ix = ( int ) Math.round( x)+margin;
				
				int ix1 = ix-cw/2;
				int ix2 = ix+cw/2;

				double y1 = ( Ymax - (Y1[i]-err[i]) ) * (wdraw) / ( Ymax-Ymin );
				int iy1 = ( int ) Math.round( y1)+margin;

				double y2 = ( Ymax - (Y1[i]+err[i]) ) * (wdraw) / ( Ymax-Ymin );
				int iy2 = ( int ) Math.round( y2)+margin;

				g2.setColor(Color.black);
				g2.drawLine(ix,iy1,ix,iy2);				
				g2.drawLine(ix1,iy1,ix2,iy1);
				g2.drawLine(ix1,iy2,ix2,iy2);				
												
			}
			
		}

		void DrawStatsR2_RMSE(Graphics2D g2, FontRenderContext frc) {

			double MeanX = 0;
			double MeanY = 0;

			for (int i = 0; i < X.length; i++) {
				MeanX += X[i];
				MeanY += Y1[i];
			}
			// System.out.println("");

			MeanX /= (double) X.length;
			MeanY /= (double) X.length;

			// double Yexpbar=this.ccTraining.meanOrMode(this.classIndex);

			// System.out.println("Yexpbar = "+Yexpbar);

			double termXY = 0;
			double termXX = 0;
			double termYY = 0;
			double SSreg=0;
			double SStot=0;
			double MAE=0;

			double R2 = 0;

			for (int i = 0; i < X.length; i++) {
				termXY += (X[i] - MeanX) * (Y1[i] - MeanY);
				termXX += (X[i] - MeanX) * (X[i] - MeanX);
				termYY += (Y1[i] - MeanY) * (Y1[i] - MeanY);
				SSreg+=Math.pow(X[i]-Y1[i],2.0);
				MAE+=Math.abs(X[i]-Y1[i]);
				SStot+=Math.pow(X[i]-MeanX,2.0);
			}

			R2 = termXY * termXY / (termXX * termYY);
			double R2old = 1 - SSreg / SStot;

			double R = Math.sqrt(R2);
			
			double RMSE = Math.sqrt(SSreg / (double) X.length);
			MAE /= (double)X.length;
			
//			System.out.println(R2+"\t"+R2old);
			 
			DecimalFormat myDF = new DecimalFormat("0.000");

			String results = "<html>R<sup>2</sup> =" + myDF.format(R2)
					+ "<br>R=" + myDF.format(R) + "<br>RMSE = "
					+ myDF.format(RMSE) + "<br>N=" + X.length + "</html>";

			jlResults.setText(results);

//			String s1 = "r2=" + myDF.format(R2);
//			int s1width = (int) g2.getFont().getStringBounds(s1, frc)
//			.getWidth();
//			int s1height = (int) g2.getFont().getStringBounds(s1, frc)
//			.getHeight();

			String s1a = "r";
			String s1b = "2";
			String s1c = " = " + myDF.format(R2);

			if (X.length == 1)
				s1c = " = N/A";

			String s2 = "RMSE = " + myDF.format(RMSE);


			int s1awidth = (int) g2.getFont().getStringBounds(s1a, frc)
					.getWidth();
			
			int s1aheight = (int) g2.getFont().getStringBounds(s1a, frc)
			.getHeight();


			int s1bwidth = (int) g2.getFont().getStringBounds(s1b, frc)
					.getWidth();

			int s1cwidth = (int) g2.getFont().getStringBounds(s1c, frc)
					.getWidth();

			int s2width = (int) g2.getFont().getStringBounds(s2, frc)
					.getWidth();


			int s2height = (int) g2.getFont().getStringBounds(s2, frc)
					.getHeight();

			int xbox = 75;
			int ybox = 75;
			int xpad = 12;
			int ypad = 12;

			int widthbox = s2width + xpad;
			int heightbox = s1aheight + s2height + ypad;

			g2.setColor(Color.white);
			g2.fillRect(xbox, ybox, widthbox, heightbox);

			g2.setColor(Color.black);
			g2.drawRect(xbox, ybox, widthbox, heightbox);

			Font f11 = new Font("Arial", Font.BOLD, 11);
			Font f6 = new Font("Arial", Font.BOLD, 6);

			g2.setColor(Color.black);
			g2.setFont(f11);

			// g2.drawString(s1,90,90);

			g2.drawString(s1a, xbox + xpad, ybox + ypad + 5);
			g2.setFont(f6);
			g2.drawString(s1b, xbox + xpad + s1awidth, ybox + ypad);
			g2.setFont(f11);
			g2.drawString(s1c, xbox + xpad + s1awidth + s1bwidth, ybox + ypad
					+ 5);

			g2.setFont(f11);
			g2.drawString(s2, xbox + xpad, ybox + ypad + 5 + s1aheight);

		}
		
		void DrawStatsMAE(Graphics2D g2, FontRenderContext frc) {

			double MAE=0;

			for (int i = 0; i < X.length; i++) {
				MAE+=Math.abs(X[i]-Y1[i]);
			}

			MAE /= (double)X.length;
			
			 
			DecimalFormat myDF = new DecimalFormat("0.00");

			String results = "<html>MAE=" + myDF.format(MAE)
					+ "</html>";

			jlResults.setText(results);

			String s1 = "MAE = "+myDF.format(MAE);;

			int s1width = (int) g2.getFont().getStringBounds(s1, frc)
					.getWidth();
			
			int s1height = (int) g2.getFont().getStringBounds(s1, frc)
					.getHeight();

			int xbox = (int)(margin+0.05*wdraw);
			int ybox = (int)(margin+0.05*wdraw);
			int xpad = 12;
			int ypad = 12;

			int widthbox = s1width + xpad;
			int heightbox = s1height + ypad;
			
			boolean havePointInBox=HavePointInBox(xbox, ybox, widthbox, heightbox);
			
			if (havePointInBox) {
				//try bottom right corner:
				xbox = (int)(w-margin-0.05*wdraw-widthbox);
				ybox = (int)(w-margin-0.05*wdraw-heightbox);
				
				havePointInBox=HavePointInBox(xbox, ybox, widthbox, heightbox);
				
				if (havePointInBox) {//put it back to upper left
					xbox = (int)(margin+0.05*wdraw);
					ybox = (int)(margin+0.05*wdraw);
				}
			}
			
			

			g2.setColor(Color.white);
			g2.fillRect(xbox, ybox, widthbox, heightbox);

			g2.setColor(Color.black);
			g2.drawRect(xbox, ybox, widthbox, heightbox);


			g2.setColor(Color.black);
			g2.setFont(fontLegend);

			g2.drawString(s1, xbox + xpad, ybox + ypad + 5);

		}
		
		void DrawLegend(Graphics2D g2, FontRenderContext frc) {

			int widthbox = 80;
			int heightbox = 40;
			if (Y2!=null)heightbox+=20;
			
			//put in top left corner:
//			int xbox = (int)(margin+0.05*wdraw);
//			int ybox = (int)(margin+0.05*wdraw);

			//put in top right corner:
			int xbox = (int)(w-margin-0.05*wdraw-widthbox);
			int ybox = (int)(margin+0.05*wdraw);
			
			
			boolean havePointInBox=HavePointInBox(xbox, ybox, widthbox, heightbox);
			
			if (havePointInBox) {
				//try bottom right corner:
				xbox = (int)(w-margin-0.05*wdraw-widthbox);
				ybox = (int)(w-margin-0.05*wdraw-heightbox);
				
				havePointInBox=HavePointInBox(xbox, ybox, widthbox, heightbox);
				
				if (havePointInBox) {//put at upper left
					xbox = (int)(margin+0.05*wdraw);
					ybox = (int)(margin+0.05*wdraw);
				}
			}
			
			g2.setColor(Color.white);
			g2.fillRect(xbox, ybox, widthbox, heightbox);

			g2.setColor(Color.black);
			g2.drawRect(xbox, ybox, widthbox, heightbox);

			Font f11 = new Font("Arial", Font.PLAIN, 11);

			g2.setColor(Color.black);
			g2.setFont(f11);

			//***************************************************
			//exp point symbol:
			int ix=xbox+10;
			int iy=ybox+10;
//			g2.setColor(Color.red);
//			g2.fillOval(ix, iy, cw, cw);
			
			g2.setColor(Color.black);
			g2.drawOval(ix, iy, cw, cw);								

			String s1="Exp.";
			int s1height = (int) g2.getFont().getStringBounds(s1, frc)
			.getHeight();

			g2.setColor(Color.black);
			g2.setFont(f11);
			g2.drawString(s1, ix+15, (int)(iy+s1height/2.0)+2);
			
			//***************************************************
			//label y1 series:
			int iy2=iy+20;

			String s2=nameY1;
			g2.setColor(Color.black);
			g2.drawString(s2, ix+15, (int)(iy2+4));
			
			g2.setColor(Color.red);
			g2.drawLine(ix-5, iy2, ix+10, iy2);
			
			//***************************************************
			//label y2 series:
			
			if (Y2!=null) {
				iy2=iy+40;

				s2=nameY2;
				g2.setColor(Color.black);
				g2.drawString(s2, ix+15, (int)(iy2+4));
			
				g2.setColor(Color.blue);
				g2.drawLine(ix-5, iy2, ix+10, iy2);
			}

		}

		
		boolean HavePointInBox(int xbox,int ybox,int widthbox,int heightbox) {

			
			double x1=xbox;
			double x2=xbox+widthbox;
			double y1=ybox;
			double y2=ybox+heightbox;
			
			for (int i = 0; i < X.length; i++) {            	  
				double x = ( X[i] - Xmin ) * (wdraw) / ( Xmax-Xmin);
				int ix = ( int ) Math.round( x -cw/2)+margin;
				
				double y = ( Ymax - Y1[i] ) * (wdraw) / ( Ymax-Ymin );
				int iy = ( int ) Math.round( y -cw/2)+margin;
				
				if (ix >=x1 && ix <=x2 && iy>=y1 && iy<=y2) {
					return true;
				}
				
			}
			
			return false;
			
			
		}

	} // end MAP class
	
	public static void main( String[] args ) {
		
		
		
	}
	
	void jbOK_actionPerformed(ActionEvent e) {
		// this.setVisible(false);
		System.exit(0);
		
		
	}
	
	
	

	class fraChart_jbOK_actionAdapter implements java.awt.event.ActionListener {
		fraChart adaptee;
		
		fraChart_jbOK_actionAdapter(fraChart adaptee) {
			this.adaptee = adaptee;
		}
		public void actionPerformed(ActionEvent e) {
			adaptee.jbOK_actionPerformed(e);
		}
	}
	
	
	
	
}
