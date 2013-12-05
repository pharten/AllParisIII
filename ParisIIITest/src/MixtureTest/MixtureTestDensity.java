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

public class MixtureTestDensity {

	
	/**
	 * In this test it checks how well our routine predicts density of mixtures
	 * 
	 */
	@Test
	public void testCalculateMixtureDensity() {

		DecimalFormat df=new DecimalFormat("0.00");
		
		double meanAbsPercentErrorOverall=0;
		int mixtureCount=0;

		try {
			
			String folder="TestResults";
			File F=new File(folder);
			if (!F.exists()) F.mkdir();

			String property="density";

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
				
				
				for (int j=0;j<n;j++) {
//					double [] molFractions={xexp[i],1-xexp[i]};
					Vector<Double>molFractions=new Vector<Double>();
					molFractions.add(mds.xexp[j]);
					molFractions.add(1-mds.xexp[j]);
					
					Mixture mixture=new Mixture();
					mixture.setChemicals(chemicals);
					mixture.setWghtFractions(mixture.calculateMassFractions(molFractions));

					//Faster to just calc TC instead of all properties:
					mixture.calculateMixtureProperties(25+273.15);
					
					//Store predicted value in array:
					y[j]=mixture.getDensity();
					
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

		chemical=chemicals.getBySynonym("cyclohexane");
		chemical.setDensity(773.8);

		chemical=chemicals.getBySynonym("methylcyclohexane");
		chemical.setDensity(764.8);
		
		chemical=chemicals.getBySynonym("cis-1,2-dimethylcyclohexane");
		chemical.setDensity(792.9);

		
		chemical=chemicals.getBySynonym("benzene");
		chemical.setDensity(873.4);

		chemical=chemicals.getBySynonym("toluene");
		chemical.setDensity(861.6);

		chemical=chemicals.getBySynonym("o-xylene");
		chemical.setDensity(875.2);

		chemical=chemicals.getBySynonym("p-xylene");
		chemical.setDensity(856.5);

		chemical=chemicals.getBySynonym("1-chlorohexane");
		chemical.setDensity(873.2);

		chemical=chemicals.getBySynonym("1-chlorohexadecane");
		chemical.setDensity(860.7);
		
		chemical=chemicals.getBySynonym("ethyl acetate");
		chemical.setDensity(894.8);

		chemical=chemicals.getBySynonym("propyl propionate");
		chemical.setDensity(875.5);

		chemical=chemicals.getBySynonym("acetone");
		chemical.setDensity(784.7);

		chemical=chemicals.getBySynonym("methyl ethyl ketone");
		chemical.setDensity(799.6);
		
		

		
		

	}

}
