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

public class MixtureTestViscosity {

	
	/**
	 * In this test it checks how well our routine predicts viscosity of mixtures
	 * 
	 * Experimental mixture data taken from:
	 * 
	 * Petrino, P.J.; Gaston-Bonhomme; Chevalier, J.L.E. "Viscosity and Density
	 * of Binary Liquid Mixtures of Hydrocarbons, Esters, Ketones, and Normal
	 * Chloroalkanes," J. Chem. Eng. Data, 1995, 40, 136-140
	 */
	@Test
	public void testCalculateMixtureViscosity() {
		
		
		DecimalFormat df=new DecimalFormat("0.00");
		
		double meanAbsPercentErrorOverall=0;
		int mixtureCount=0;

		try {
			
			String folder="TestResults";
			File F=new File(folder);
			if (!F.exists()) F.mkdir();

			String property="viscosity";

			String mixtureFileName="mixture data "+property+".txt";

			String outputFolder=folder+"/"+property+" plots";
			
			File pFolder=new File(outputFolder);
			if (!pFolder.exists()) pFolder.mkdir();
			
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
					y[j]=Units.viscosityConvertTo(mixture.getViscosity(), Units.COMMON);;
					
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
	
	private void usePureComponentValuesFromPapers(Chemicals chemicals) {
		Chemical chemical;
		

		
		chemical=chemicals.getBySynonym("cyclohexane");
		chemical.setViscosity(Units.viscosityConvertFrom(0.888322,Units.COMMON));

		chemical=chemicals.getBySynonym("methylcyclohexane");
		chemical.setViscosity(Units.viscosityConvertFrom(0.67355936,Units.COMMON));

		chemical=chemicals.getBySynonym("cis-1,2-dimethylcyclohexane");
		chemical.setViscosity(Units.viscosityConvertFrom(1.0133262,Units.COMMON));

		chemical=chemicals.getBySynonym("benzene");
		chemical.setViscosity(Units.viscosityConvertFrom(0.59775496,Units.COMMON));

		chemical=chemicals.getBySynonym("toluene");
		chemical.setViscosity(Units.viscosityConvertFrom(0.5466852,Units.COMMON));

		chemical=chemicals.getBySynonym("o-xylene");
		chemical.setViscosity(Units.viscosityConvertFrom(0.74759584,Units.COMMON));
		
		chemical=chemicals.getBySynonym("p-xylene");
		chemical.setViscosity(Units.viscosityConvertFrom(0.59740875,Units.COMMON));

		chemical=chemicals.getBySynonym("1-chlorohexane");
		chemical.setViscosity(Units.viscosityConvertFrom(0.68598592,Units.COMMON));
			
		chemical=chemicals.getBySynonym("1-chlorohexadecane");
		chemical.setViscosity(Units.viscosityConvertFrom(5.4267135,Units.COMMON));
			
		chemical=chemicals.getBySynonym("ethyl acetate");
		chemical.setViscosity(Units.viscosityConvertFrom(0.42180872,Units.COMMON));
			
		chemical=chemicals.getBySynonym("propyl propionate");
		chemical.setViscosity(Units.viscosityConvertFrom(0.62256805,Units.COMMON));
			
		chemical=chemicals.getBySynonym("acetone");
		chemical.setViscosity(Units.viscosityConvertFrom(0.30061857,Units.COMMON));
		
		chemical=chemicals.getBySynonym("methyl ethyl ketone");
		chemical.setViscosity(Units.viscosityConvertFrom(0.37389296,Units.COMMON));
		

	}

}
