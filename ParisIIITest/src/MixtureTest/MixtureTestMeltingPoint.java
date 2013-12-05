package MixtureTest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Vector;

import org.junit.Test;

import parisInit.Units;
import parisWork.Chemical;
import parisWork.Chemicals;
import parisWork.Mixture;
import unifac.UNIFACforPARIS;

public class MixtureTestMeltingPoint {

	
	/**
	 * In this test it checks how well our routine predicts density of mixtures
	 * 
	 */
	@Test
	public void testCalculateMixtureMeltingPoint() {

		DecimalFormat df=new DecimalFormat("0.00");
		
		
		double meanAbsPercentErrorOverall=0;
		int mixtureCount=0;

		try {
			
			String folder="TestResults";
			File F=new File(folder);
			if (!F.exists()) F.mkdir();

			String property="melting point";

			String mixtureFileName="mixture data "+property+".txt";

			String outputFolder=folder+"/"+property+" plots";
			
			File pFolder=new File(outputFolder);
			if (!pFolder.exists()) pFolder.mkdir();
			
			if (!pFolder.exists()) pFolder.mkdir();
			else {
				File [] files=pFolder.listFiles();
				for (int i=0;i<files.length;i++) {
					files[i].delete();
				}
			}
			
//			Chemicals allChemicals = Chemicals.readFromFile(getClass().getResource("/data/Chemicals.xml").getFile());
//			Chemicals allChemicals = Chemicals.readFromFile(getClass().getResource("/data/Chemicals.txt").getFile());
			Chemicals allChemicals = Chemicals.readFromFile("data/Chemicals.txt");
			
//			this.addMissingChemicals(allChemicals);
//			this.addAntoineConstants(allChemicals);

			//Use pure component values from papers since we want to see
			// how well the mixture values are calculated not the pure comp values
			this.usePureComponentValuesFromPapers(allChemicals);
			
			System.out.println("\n"+property+"\nComp1\tComp2\tMAE");
			
			Vector <MixtureDataSet>dataSets=AllMixtureTests.readDataFileFormat1("src/MixtureTest/"+mixtureFileName);
			
			for (int i=0;i<dataSets.size();i++) {
				MixtureDataSet mds=dataSets.get(i);
				
				Chemical pc1=allChemicals.getBySynonym(mds.nameChemical1);
				Chemical pc2=allChemicals.getBySynonym(mds.nameChemical2);

				if (pc1==null){
					System.out.println(mds.nameChemical1+" is not in the db");
					continue;
				}
			
				if (pc2==null){
					System.out.println(mds.nameChemical2+" is not in the db");
					continue;
				}
			
				Chemicals chemicals=new Chemicals();
				chemicals.add(pc1);
				chemicals.add(pc2);
				
				double meanAbsPercentError=0;
				
				double [] y=new double[mds.xexp.length];

				int n=mds.xexp.length;
				
				String paperSource="jakob et al 1995";
				
				if (mds.nameChemical1.indexOf("noic acid")>-1 || mds.nameChemical2.indexOf("noic acid")>-1) {
					paperSource="bar and newsham 1986";
				}
				
				for (int j=0;j<n;j++) {
//					double [] molFractions={xexp[i],1-xexp[i]};

					Mixture mixture=new Mixture();
					mixture.setChemicals(chemicals);

					if (paperSource.equals("jakob et al 1995")) {
						Vector<Double>molFractions=new Vector<Double>();
						molFractions.add(mds.xexp[j]);
						molFractions.add(1-mds.xexp[j]);
						mixture.setWghtFractions(mixture.calculateMassFractions(molFractions));
					} else if (paperSource.equals("bar and newsham 1986")) {
						Vector<Double>wghtFractions=new Vector<Double>();
						wghtFractions.add(mds.xexp[j]);
						wghtFractions.add(1-mds.xexp[j]);
						mixture.setWghtFractions(wghtFractions);
					}

					mixture.calculateMixtureProperties(25+273.15);
					
					//Store predicted value in array:
					y[j]=mixture.getMeltingPoint();
					
					meanAbsPercentError+=Math.abs((y[j]-mds.yexp[j])/mds.yexp[j]*100);

				}//end loop over experimental points for this mixture

				meanAbsPercentError/=(double)n;
				meanAbsPercentErrorOverall+=meanAbsPercentError;
				
				if (AllMixtureTests.createPlots) {
					String xtitle="Mole fraction component 1";
					String ytitle="Mixture "+property;
					String title=mds.nameChemical1+" + "+mds.nameChemical2;
					fraChart fc = new fraChart(mds.xexp,y,"Pred",mds.xexp,mds.yexp,title,xtitle,ytitle);
					
					fc.WriteImageToFile(mds.nameChemical1+" + "+mds.nameChemical2+".png", outputFolder);
					fc=null;
				}

//				System.out.println(line1+"\t"+df.format(MRSDUnifac)+"\t"+df.format(MRSDVolFrac));
				System.out.println(mds.nameChemical1+"\t"+mds.nameChemical2+"\t"+df.format(meanAbsPercentError));

			}// end loop over data sets
			
			//Now average results over all data sets:
			meanAbsPercentErrorOverall/=(double)dataSets.size();

			System.out.println("overall\t"+df.format(meanAbsPercentErrorOverall));			
			
			if (AllMixtureTests.createPlots) {
				AllMixtureTests.createPlotWebPage(outputFolder,property);
				
				//display webpage:
				File webpage=new File(outputFolder+File.separator+"plots.html");
				URL myURL=webpage.toURI().toURL();	
				String strURL=myURL.toString();
				BrowserLauncher.openURL(strURL);
			}
			
			assertTrue(meanAbsPercentErrorOverall<10);
		
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception:"+e.getMessage());
		}
		
	}
	
	public void usePureComponentValuesFromPapers(Chemicals chemicals) {
		Chemical chemical;

		chemical=chemicals.getBySynonym("1,4-dioxane");
		chemical.setEnthalpyOfFusion(12999);
		chemical.setMeltingPoint(284.85);
		chemical.setHeatCapacity(149.65);
		chemical.setHeatCapacitySolid(130.88);
		
		chemical=chemicals.getBySynonym("indane");
//		chemical.setEnthalpyOfFusion(8592);
		chemical.setEnthalpyOfFusion(11700);//NIST
		chemical.setMeltingPoint(220.84);
		chemical.setHeatCapacity(190.25);
		chemical.setHeatCapacitySolid(173.61);
		
		chemical=chemicals.getBySynonym("dodecane");
		chemical.setEnthalpyOfFusion(36556);
		chemical.setMeltingPoint(263.58);
		chemical.setHeatCapacity(376);
		chemical.setHeatCapacitySolid(327.24);

//		chemical=chemicals.getByCAS("98-95-3");//nitrobz
//		chemical.setEnthalpyOfFusion(11581);
//		chemical.setMeltingPoint(279.05);
//		chemical.setHeatCapacity(?);
//		chemical.setHeatCapacitySolid(?);

		chemical=chemicals.getBySynonym("1,2-dichloroethane");
		chemical.setEnthalpyOfFusion(8836);
		chemical.setMeltingPoint(237.9);
		chemical.setHeatCapacity(129.4);
		chemical.setHeatCapacitySolid(101.4);
		
		chemical=chemicals.getBySynonym("1,4-xylene");
		chemical.setEnthalpyOfFusion(16793);
		chemical.setMeltingPoint(286.35);
		chemical.setHeatCapacity(182.2);
		chemical.setHeatCapacitySolid(162.7);

		
//		chemical=chemicals.getBySynonym("formic acid");
//		chemical.setEnthalpyOfFusion(12678);
//		chemical.setMeltingPoint(281.4);

		chemical=chemicals.getBySynonym("acetic acid");
		chemical.setEnthalpyOfFusion(11720);
		chemical.setMeltingPoint(289.7);
		chemical.setHeatCapacity(123.1);
		chemical.setHeatCapacitySolid(78.86);

		
		chemical=chemicals.getBySynonym("propanoic acid");
		chemical.setEnthalpyOfFusion(10660);
		chemical.setMeltingPoint(252.3);
		chemical.setHeatCapacity(158.6);
		chemical.setHeatCapacitySolid(104.87);


		chemical=chemicals.getBySynonym("water");
		chemical.setEnthalpyOfFusion(6012);
		chemical.setMeltingPoint(273.15);
		chemical.setHeatCapacity(75.38);
		chemical.setHeatCapacitySolid(37.94);
		

//		chemical=chemicals.getBySynonym("acenaphthene");
//		chemical.setEnthalpyOfFusion(21521);
//		chemical.setMeltingPoint(387.15);
		
//			

	}

}
