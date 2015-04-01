package MixtureTest;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Vector;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import parisInit.Units;
import parisWork.Chemical;
import parisWork.Chemicals;
import parisWork.Mixture;
import unifac.UNIFACforPARIS;

@RunWith(Suite.class)

@SuiteClasses({ MixtureTestSurfaceTension.class, MixtureTestFlashPoint.class,
		MixtureTestThermalConductivity.class, MixtureTestDensity.class,
		MixtureTestViscosity.class,MixtureTestVaporPressure.class,
		MixtureTestBoilingPoint.class, MixtureTestOnePhase.class, TieLineTest.class})
		
		//MixtureTestMeltingPoint.class
		
//@SuiteClasses({ MixtureTestDensity.class })

public class AllMixtureTests {

	public static boolean createPlots=false;
		
	

	/**
	 * Reads mixture data format 1 (e.g. surface tension file)
	 * @param filepath
	 */
	
	public static Vector<MixtureDataSet> readDataFileFormat1(String filepath) {

		Vector<MixtureDataSet>dataSets=new Vector<MixtureDataSet>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));

			while (true) {

				String line1 = br.readLine();

				if (line1 == null)
					break;

				String[] Names = line1.split("\t");

				MixtureDataSet mds=new MixtureDataSet();
				
				mds.nameChemical1=Names[0].trim();
				mds.nameChemical2=Names[1].trim();
				
				Vector<Double> xexpvec = new Vector<Double>();
				Vector<Double> yexpvec = new Vector<Double>();

				String dataHeader = br.readLine();
				
				while (true) {
					String Line = br.readLine();

					if (Line == null)
						break;

					if (Line.trim().equals(""))
						break;

					String[] Values = Line.split("\t");

					String strXexpi = Values[0];
					String strYexpi = Values[1];

					xexpvec.add(Double.parseDouble(strXexpi));
					yexpvec.add(Double.parseDouble(strYexpi));

				}
				
				//Convert vector to array and store in mds:
				mds.xexp = new double [xexpvec.size()];
				mds.yexp = new double [xexpvec.size()];
				
				for (int j=0;j<xexpvec.size();j++) {
					mds.xexp[j]=xexpvec.get(j);
					mds.yexp[j]=yexpvec.get(j);
				}

				dataSets.add(mds);

			}// end loop over all mixtures
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataSets;
	}
	
	
	/**
	 * Reads mixture data format 3 (e.g. boiling point or vapor pressure file)
	 * 
	 * Example:
	 * methanol 2-butanone 
	 * P 760 
	 * x1 boiling point in C
	 * 0.0465 75
	 * 0.1 73 
	 * 0.1525 71.4 
	 * 0.2005 69.9
	 * ...	...
	 * 
	 * @param filepath
	 */
	
	public static Vector<MixtureDataSet> readDataFileFormat3(String filepath) {

		Vector<MixtureDataSet>dataSets=new Vector<MixtureDataSet>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));

			while (true) {

				String line1 = br.readLine();

				if (line1 == null)
					break;

				String[] Names = line1.split("\t");

				MixtureDataSet mds=new MixtureDataSet();
				
				mds.nameChemical1=Names[0];
				mds.nameChemical2=Names[1];
				
				Vector<Double> xexpvec = new Vector<Double>();
				Vector<Double> yexpvec = new Vector<Double>();

				
				String TP = br.readLine();
				
//				System.out.println(TP);
				
				String variable=TP.substring(0, TP.indexOf("\t"));
				String value=TP.substring(TP.indexOf("\t")+1,TP.length());
				
				if (variable.equals("P")) {
					mds.pressure_mmHg=Double.parseDouble(value);
				} else if (variable.equals("T")) {
					mds.temperatureC=Double.parseDouble(value);
				}
				
				
				String dataHeader = br.readLine();
				
				while (true) {
					String Line = br.readLine();

					if (Line == null)
						break;

					if (Line.trim().equals(""))
						break;

					String[] Values = Line.split("\t");

					String strXexpi = Values[0];
					String strYexpi = Values[1];

					xexpvec.add(Double.parseDouble(strXexpi));
					yexpvec.add(Double.parseDouble(strYexpi));

				}
				
				//Convert vector to array and store in mds:
				mds.xexp = new double [xexpvec.size()];
				mds.yexp = new double [xexpvec.size()];
				
				for (int j=0;j<xexpvec.size();j++) {
					mds.xexp[j]=xexpvec.get(j);
					mds.yexp[j]=yexpvec.get(j);
				}

				dataSets.add(mds);

			}// end loop over all mixtures
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataSets;
	}
	
	 static private java.util.LinkedList Parse3(String Line, String Delimiter) {


		    java.util.LinkedList myList = new LinkedList();

		    int tabpos = 1;

		    while (tabpos > -1) {

		    	tabpos = Line.indexOf(Delimiter);
		    	
		    	if (Line.length()<1) break;
		    	
		    	if (Line.substring(0,1).equals("\"")) {
		    		Line=Line.substring(1,Line.length()); // kill first " mark
		    		
		    		if (Line.length()==0) break;
		    		
		    		myList.add(Line.substring(0, Line.indexOf("\"")));
		    		
		    		if (Line.indexOf("\"")<Line.length()-1)
		    			Line = Line.substring(Line.indexOf("\"") + 2, Line.length());
		    		else 
		    			break;
		    	} else {
					

					if (tabpos > 0) {
						myList.add(Line.substring(0, tabpos));
						Line = Line.substring(tabpos + 1, Line.length());
					} else if (tabpos == 0) {
						myList.add("");
						Line = Line.substring(tabpos + 1, Line.length());
					} else {
						myList.add(Line.trim());
					}

		    	}
		    			
			}// end while loop

		    
//		    for (int j = 0; j <= myList.size() - 1; j++) {
//				System.out.println(j + "\t" + myList.get(j));					
//			}
		    
		    return myList;

		  }
	
	/**
	 * Reads mixture data format 4 (e.g. LLE file)
	 * 
	 * Example:
	 * 1,2-ethanediol	benzene
	 * T	X1A	X1B
	 * 29		0.00251
	 * 47.1		0.00479
	 * 56.8		0.00671
	 * 67.4		0.00943
	 * 20	0.96148	
	 * 25	0.95417	
	 * 40	0.94847	

	 * @param filepath
	 */
	
	public static Vector<MixtureDataSet> readDataFileFormat4(String filepath) {

		Vector<MixtureDataSet>dataSets=new Vector<MixtureDataSet>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));

			while (true) {

				String line1 = br.readLine();

				if (line1 == null)
					break;

				
				
				String[] Names = line1.split("\t");

				MixtureDataSet mds=new MixtureDataSet();
				
				mds.nameChemical1=Names[0];
				mds.nameChemical2=Names[1];
				
				
				Vector<Double> xexpvec = new Vector<Double>();
				Vector<Double> xexpvec2 = new Vector<Double>();
				Vector<Double> yexpvec = new Vector<Double>();

				
				String dataHeader = br.readLine();
//				System.out.println("data header="+dataHeader);

				
				while (true) {
					String Line = br.readLine();

					if (Line == null)
						break;

					if (Line.trim().equals(""))
						break;

					LinkedList<String> l=Parse3(Line,"\t");

					String strY=l.get(0);//Temp
					String strX1 = l.get(1);//X1A
					String strX2 = l.get(2);//X1B

//					System.out.println("Line="+Line);

					if (strX1.equals("N/A")) {
						strX1="-9999";
					}
					
					if (strX2.equals("N/A")) {
						strX2="-9999";
					}

//					System.out.println(strTemp+"\t"+strXexpi+"\t"+strYexpi);

					xexpvec.add(Double.parseDouble(strX1));
					xexpvec2.add(Double.parseDouble(strX2));
					yexpvec.add(Double.parseDouble(strY));
				}
				
				//Convert vector to array and store in mds:
				mds.xexp = new double [xexpvec.size()];
				mds.xexp2 = new double [xexpvec.size()];
				mds.yexp = new double [xexpvec.size()];

				
				for (int j=0;j<xexpvec.size();j++) {
					mds.xexp[j]=xexpvec.get(j);
					mds.xexp2[j]=xexpvec2.get(j);
					mds.yexp[j]=yexpvec.get(j);
				}

				dataSets.add(mds);

			}// end loop over all mixtures
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataSets;
	}
	/**
	 * Reads mixture data format 2 (e.g. thermal conductivity file)
	 * @param filepath
	 */
	public static Vector<MixtureDataSet> readDataFileFormat2(String filepath) {

		Vector<MixtureDataSet>dataSets=new Vector<MixtureDataSet>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filepath));

			String Line1=br.readLine(); //"		Mole fraction component 1"
			
			String Line2=br.readLine();
			
			String [] vals=Line2.split("\t");
			
			double [] xexp=new double [vals.length-2];
			
			for (int i=2;i<vals.length;i++) {
				xexp[i-2]=Double.parseDouble(vals[i]);
			}
			
			
			while (true) {

				String line1 = br.readLine();

				if (line1 == null)
					break;

				String[] vals2 = line1.split("\t");

				MixtureDataSet mds=new MixtureDataSet();
				
				mds.nameChemical1=vals2[0];
				mds.nameChemical2=vals2[1];
				
				Vector<Double> yexpvec = new Vector<Double>();

				for (int i=2;i<vals2.length;i++) {
					String strYexpi = vals2[i];
					yexpvec.add(Double.parseDouble(strYexpi));
				}
				
				//Convert vector to array and store in mds:
				mds.xexp = xexp.clone();
				mds.yexp = new double [xexp.length];
				
				for (int j=0;j<xexp.length;j++) {
					mds.yexp[j]=yexpvec.get(j);
				}

				dataSets.add(mds);
				
//				System.out.println(mds.nameChemical1+"\t"+mds.nameChemical2+"\t"+mds.xexp[1]+"\t"+mds.yexp[1]);

			}// end loop over all mixtures
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataSets;
	}
	public static void createPlotWebPage(String folder,String propertyName) {
		
		File Folder=new File(folder);

		File [] files=Folder.listFiles();

		int countPerRow=3;//plots per row

		int currentCount=0;

		try {

			FileWriter fw=new FileWriter(folder+File.separator+"plots.html");

			fw.write("<html><head><title>"+propertyName+"</title></head>" +
					"\r\n<table border=1 cellpadding=10 cellspacing=0>\r\n");

			if (currentCount==1) {
				fw.write("<tr>\r\n");
			}

			for (int i=0;i<files.length;i++) {
				File filei=files[i];

				if (!filei.getName().endsWith(".png")) continue;

				currentCount++;

				if (currentCount>countPerRow) {
					fw.write("</tr>\r\n");
					fw.write("<tr>\r\n");
					currentCount=1;
				}

				int plotSize=300;
				fw.write("<td><img src=\""+filei.getName()+"\" height="+plotSize+" width="+plotSize+"></td>\r\n");
				

			}//end i loop over files
			
			if (currentCount<countPerRow) {
				fw.write("</tr>\r\n");
			}
			
			fw.write("</table></html>");
			fw.flush();
			fw.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}



