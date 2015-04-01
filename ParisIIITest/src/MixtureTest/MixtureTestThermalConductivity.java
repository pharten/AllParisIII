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

public class MixtureTestThermalConductivity {

	/**
	 * Adds chemicals needed for test that arent in the chemical db
	 * 
	 * TODO: add these chemicals so that this is unnecessary
	 *
	 * @param allChemicals
	 */
	private void addMissingChemicals(Chemicals allChemicals) {
	
//		Chemical chemical=new Chemical();
//		String name="water";
//		Vector <String> synonyms=new Vector<String>();
//		synonyms.add(name);
//		chemical.setName(name);
//		chemical.setCAS("75-65-0");
//		chemical.setSynonyms(synonyms);
//		chemical.setMolecularWeight(18.04);
//		chemical.setDensity(Units.densityConvertFrom(1,Units.COMMON));
//		chemical.setSurfaceTension(Units.surfaceTensionConvertFrom(71.15,Units.COMMON));
//		chemical.setThermalConductivity(Units.thermalConductivityConvertFrom(628,Units.COMMON));
//		allChemicals.add(chemical);	

		Chemical chemical=new Chemical();
		String name="formamide";
		Vector<String>synonyms=new Vector<String>();
		synonyms.add(name);
		chemical.setName(name);
		chemical.setCAS("75-12-7");
		chemical.setSynonyms(synonyms);
		chemical.setMolecularWeight(45.04);
		chemical.setDensity(Units.densityConvertFrom(1.13,Units.COMMON));
		chemical.setThermalConductivity(Units.thermalConductivityConvertFrom(352,Units.COMMON));
		allChemicals.add(chemical);	
		
		chemical=new Chemical();
		name="dimethyl formamide";
		synonyms=new Vector<String>();
		synonyms.add(name);
		chemical.setName(name);
		chemical.setCAS("68-12-2");
		chemical.setSynonyms(synonyms);
		chemical.setMolecularWeight(73.0943);
		chemical.setDensity(Units.densityConvertFrom(0.88,Units.COMMON));
		chemical.setThermalConductivity(Units.thermalConductivityConvertFrom(182.12,Units.COMMON));
		allChemicals.add(chemical);	
	}
	
	
	/**
	 * This method assigns the pure component properties from the research 
	 * articles since we are concerned on how well the mixture equations
	 * predict the mixture values and not how good our experimental values are
	 * in the database
	 * 
	 * @param chemicals
	 */
	void usePureComponentValuesFromPapers(Chemicals chemicals) {
		Chemical chemical;
		
//		chemical=chemicals.getBySynonym("methanol");
//		chemical.setThermalConductivity(0.209);//0 C
		
		chemical=chemicals.getBySynonym("ethanol");
		chemical.setThermalConductivity(0.162);//40 C

		chemical=chemicals.getBySynonym("1-propanol");
		chemical.setThermalConductivity(0.155);//40 C

		chemical=chemicals.getBySynonym("2-propanol");
		chemical.setThermalConductivity(0.137);//40 C
		
		chemical=chemicals.getBySynonym("glycerol");
		chemical.setThermalConductivity(0.285);//40 C
		
		chemical=chemicals.getBySynonym("acetone");
		chemical.setThermalConductivity(0.154);//40 C
		
		chemical=chemicals.getBySynonym("ethylene glycol");
		chemical.setThermalConductivity(0.252);//40 C

		chemical=chemicals.getBySynonym("propylene glycol");
		chemical.setThermalConductivity(0.199);//40 C
		
		chemical=chemicals.getBySynonym("formamide");
		chemical.setThermalConductivity(0.352);//40 C

		chemical=chemicals.getBySynonym("diethylene glycol");
		chemical.setThermalConductivity(0.205);//40 C

		chemical=chemicals.getBySynonym("triethylene glycol");
		chemical.setThermalConductivity(0.194);//40 C

		chemical=chemicals.getBySynonym("pyridine");
		chemical.setThermalConductivity(0.163);//40 C

		chemical=chemicals.getBySynonym("dimethyl formamide");
		chemical.setThermalConductivity(0.181);//40 C
		
		chemical=chemicals.getBySynonym("n-butanol");
		chemical.setThermalConductivity(0.147);//40 C

		
	}
	
	
	/**
	 * In this test it checks how well our routine predicts thermal conductivity
	 * of mixtures using data in Li, 1976 
	 * 
	 */
	@Test
	public void testCalculateMixtureTC() {
		
		
		DecimalFormat df=new DecimalFormat("0.00");
		double largestError = 10.0;
		
		
		double meanAbsPercentErrorOverall=0;
		int mixtureCount=0;

		try {
			
			String folder="TestResults";
			File F=new File(folder);
			if (!F.exists()) F.mkdir();

			String property="thermal conductivity";

			String mixtureFileName="mixture data "+property+".txt";

			String outputFolder=folder+"/"+property+" plots";
			
			File pFolder=new File(outputFolder);
			if (!pFolder.exists()) pFolder.mkdir();
			
//			Chemicals allChemicals = Chemicals.readFromFile(getClass().getResource("/data/Chemicals.xml").getFile());
//			Chemicals allChemicals = Chemicals.readFromFile(getClass().getResource("/data/Chemicals.txt").getFile());
			Chemicals allChemicals = Chemicals.readFromFile("data/Chemicals.txt");
			
			this.addMissingChemicals(allChemicals);

			//Use pure component values from papers since we want to see
			// how well the mixture values are calculated not the pure comp values
			this.usePureComponentValuesFromPapers(allChemicals);
			
//			System.out.println("\n"+property+"\nComp1\tComp2\tMAE");
			
			Vector <MixtureDataSet>dataSets=AllMixtureTests.readDataFileFormat2("src/MixtureTest/"+mixtureFileName);
			
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

					mixture.calculateMixtureProperties(25+273.15);
					double TCmix=mixture.getThermalConductivity();
					
					//Faster to just calc TC instead of all properties:
//					Vector<Double> volFractions=m.calculateVolumeFractions(molFractions);
//					double TCmix=m.calculateMixtureTC(volFractions, chemicals);
					
					//Store predicted value in array:
					y[j]=TCmix;
					
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
//				System.out.println(mds.nameChemical1+"\t"+mds.nameChemical2+"\t"+df.format(meanAbsPercentError));
				assertTrue(meanAbsPercentError<largestError);

			}// end loop over data sets
			
			//Now average results over all data sets:
			meanAbsPercentErrorOverall/=(double)dataSets.size();

//			System.out.println("overall\t"+df.format(meanAbsPercentErrorOverall));			
			
			if (AllMixtureTests.createPlots) {
				AllMixtureTests.createPlotWebPage(outputFolder,property);
				
				//display webpage:
				File webpage=new File(outputFolder+File.separator+"plots.html");
				URL myURL=webpage.toURI().toURL();	
				String strURL=myURL.toString();
				BrowserLauncher.openURL(strURL);
			}
			
			assertTrue(meanAbsPercentErrorOverall<largestError/Math.sqrt(dataSets.size()));
		
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception:"+e.getMessage());
		}
		
	}

}
