package MixtureTest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Vector;

import org.junit.Test;

import parisWork.Chemical;
import parisWork.Chemicals;
import parisWork.Mixture;
import parisInit.Units;

import unifac.UNIFACforPARIS;

public class MixtureTestSurfaceTension {

	/**
	 * Adds chemicals needed for test that arent in the chemical db
	 * 
	 * TODO: add these chemicals so that this is unnecessary
	 *
	 * @param allChemicals
	 */
	private void addMissingChemicals(Chemicals allChemicals) {
		Chemical chemical1=new Chemical();
		String name="2-methyl-2-propanol";
		Vector <String> synonyms=new Vector<String>();
		synonyms.add(name);
		chemical1.setName(name);
		chemical1.setCAS("75-65-0");
		chemical1.setSynonyms(synonyms);
		chemical1.setMolecularWeight(74.122);
		chemical1.setDensity(Units.densityConvertFrom(0.781,Units.COMMON));
		chemical1.setSurfaceTension(Units.surfaceTensionConvertFrom(20.2,Units.COMMON));
		allChemicals.add(chemical1);
		
//		Chemical chemical2=new Chemical();
//		String name2="water";
//		Vector <String> synonyms2=new Vector<String>();
//		synonyms2.add(name2);
//		chemical2.setName(name2);
//		chemical2.setCAS("75-65-0");
//		chemical2.setSynonyms(synonyms2);
//		chemical2.setMolecularWeight(18.04);
//		chemical2.setDensity(Units.densityConvertFrom(1,Units.COMMON));
//		chemical2.setSurfaceTension(Units.surfaceTensionConvertFrom(71.15,Units.COMMON));
//		allChemicals.add(chemical2);	

		
	}
	
	/**
	 * This method assigns the pure component properties from the research 
	 * articles since we are concerned on how well the mixture equations
	 * predict the mixture values and not how good our experimental values are
	 * in the database
	 * 
	 * @param chemicals
	 */
	private void usePureComponentValuesFromPapers(Chemicals chemicals) {
			 
		Chemical chemical=chemicals.getBySynonym("1-Chlorobutane");
		chemical.setSurfaceTension(Units.surfaceTensionConvertFrom(23.29, Units.COMMON));
		
		chemical=chemicals.getBySynonym("2-Chlorobutane");
		chemical.setSurfaceTension(Units.surfaceTensionConvertFrom(21.83, Units.COMMON));
		
		chemical=chemicals.getBySynonym("1-butanol");
		chemical.setSurfaceTension(Units.surfaceTensionConvertFrom(24.25, Units.COMMON));
		
		chemical=chemicals.getBySynonym("2-butanol");
		chemical.setSurfaceTension(Units.surfaceTensionConvertFrom(23.11, Units.COMMON));

		chemical=chemicals.getBySynonym("2-Methyl-1-propanol");
		chemical.setSurfaceTension(Units.surfaceTensionConvertFrom(22.34, Units.COMMON));

		chemical=chemicals.getBySynonym("2-Methyl-2-propanol");
		chemical.setSurfaceTension(Units.surfaceTensionConvertFrom(20.2, Units.COMMON));

		chemical=chemicals.getBySynonym("1-Chloro-2-methylpropane");
		chemical.setSurfaceTension(Units.surfaceTensionConvertFrom(21.80, Units.COMMON));

		chemical=chemicals.getBySynonym("2-Chloro-2-methylpropane");
		chemical.setSurfaceTension(Units.surfaceTensionConvertFrom(18.9, Units.COMMON));

		
		chemical=chemicals.getBySynonym("Ethylene glycol");
		chemical.setSurfaceTension(Units.surfaceTensionConvertFrom(46.2, Units.COMMON));

		chemical=chemicals.getBySynonym("1,2-Propanediol");
		chemical.setSurfaceTension(Units.surfaceTensionConvertFrom(35.5, Units.COMMON));

		chemical=chemicals.getBySynonym("1,3-Propanediol");
		chemical.setSurfaceTension(Units.surfaceTensionConvertFrom(47.0, Units.COMMON));
		 
		chemical=chemicals.getBySynonym("1,3-Butanediol");
		chemical.setSurfaceTension(Units.surfaceTensionConvertFrom(37.0, Units.COMMON));

		chemical=chemicals.getBySynonym("1,4-Butanediol");
		chemical.setSurfaceTension(Units.surfaceTensionConvertFrom(43.8, Units.COMMON));
		
		
		
	}
	
	
	/**
	 * In this test it checks the prediction of surface tension of mixtures. Right
	 * now the mixture class uses the volume fraction method 
	 * 
	 */
	@Test
	public void testCalculateMixtureST() {
		
		String property="surface tension";
		
		DecimalFormat df=new DecimalFormat("0.00");
		double largestError = 16.0;
		
		Mixture m=new Mixture();
		
		double meanAbsPercentErrorOverall=0;
		

		try {
			
			String folder="TestResults";
			File F=new File(folder);
			if (!F.exists()) F.mkdir();

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
			
//			System.out.println("Comp1\tComp2\tMRSDUnifac\tMRSDVolFrac");
//			System.out.println("\n"+property+"\nComp1\tComp2\tMAE");
			
			Vector <MixtureDataSet>dataSets=AllMixtureTests.readDataFileFormat1("src/MixtureTest/"+mixtureFileName);
			
			for (int i=0;i<dataSets.size();i++) {
				MixtureDataSet mds=dataSets.get(i);
				
				Chemical pc1=allChemicals.getBySynonym(mds.nameChemical1);
				Chemical pc2=allChemicals.getBySynonym(mds.nameChemical2);

//				System.out.println(pc1.getCAS()+"\t"+mds.nameChemical1);
//				System.out.println(pc2.getCAS()+"\t"+mds.nameChemical2);
				
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
				
//				double MRSDUnifac=0;
//				double MRSDVolFrac=0;
				
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

					try {
						mixture.calculateMixtureProperties(25+273.15);
					} catch (Exception e) {
						System.out.println(e.getMessage()+"\t"+pc1.getCAS()+"\t"+pc2.getCAS());
					}
					double STmix=mixture.getSurfaceTension();

					
//					System.out.println(chemicals.get(0).getCAS()+"\t"+chemicals.get(1).getCAS()+"\t"+molFractions.get(0)+"\t"+mixture.getFlashPoint());
					
					STmix=Units.surfaceTensionConvertTo(STmix, Units.COMMON);

					//Store predicted values in arrays:
					y[j]=STmix;

					meanAbsPercentError+=Math.abs((STmix-mds.yexp[j])/mds.yexp[j]*100);

				}//end loop over experimental points for this mixture

//				MRSDUnifac=100*Math.sqrt(MRSDUnifac/(double)xexp.length);
//				MRSDVolFrac=100*Math.sqrt(MRSDVolFrac/(double)xexp.length);
				
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
			
//			assertTrue(meanAbsPercentErrorUnifacOverall<10);
			assertTrue(meanAbsPercentErrorOverall<largestError/Math.sqrt(dataSets.size()));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception:"+e.getMessage());
		}
		
	}
	/**
	 * In this test the predictions for mixtures using the Unifac based method
	 *  and the simpler volume fraction method are compared
	 * 
	 * TODO- add additional mixtures to determine if volume fraction method 
	 * is just as good (it is faster and more easily applied to mixtures 
	 * with more than 2 components). If volume fraction method works better just
	 * make mixture class use that method and omit unifac method from this class
	 * 
	 * 
	 */
	//@Test
	private void compareVolFracToUNIFACMixtureST() {
		
		String property="surface tension";
		
		DecimalFormat df=new DecimalFormat("0.00");
		
		Mixture m=new Mixture();
		
		double meanAbsPercentErrorUnifacOverall=0;
		double meanAbsPercentErrorVolFracOverall=0;
		

		try {
			
			String folder="TestResults";

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
			
//			System.out.println("Comp1\tComp2\tMRSDUnifac\tMRSDVolFrac");
			System.out.println("\n"+property+"\nComp1\tComp2\tMAEUnifac\tMAEVolFrac");
			
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
				
//				double MRSDUnifac=0;
//				double MRSDVolFrac=0;
				
				double meanAbsPercentErrorUnifac=0;
				double meanAbsPercentErrorVolFrac=0;
				
				double [] yunifac=new double[mds.xexp.length];
				double [] yvolfrac=new double[mds.xexp.length];

				int n=mds.xexp.length;
				
				
				for (int j=0;j<n;j++) {
//					double [] molFractions={xexp[i],1-xexp[i]};
					Vector<Double>molFractions=new Vector<Double>();
					molFractions.add(mds.xexp[j]);
					molFractions.add(1-mds.xexp[j]);
					
					Mixture mixture=new Mixture();
					mixture.setChemicals(chemicals);
					mixture.setWghtFractions(mixture.calculateMassFractions(molFractions));

//					mixture.calculateMixtureProperties(25+273.15, unifac);
//					double STmix=mixture.getSurfaceTension();

					//Faster to just calc ST:
					
					long t1=System.currentTimeMillis();
					double STmix=m.calculateMixtureSTwithUNIFACAi(molFractions, 25+273.15);
					long t2=System.currentTimeMillis();
					
					STmix=Units.surfaceTensionConvertTo(STmix, Units.COMMON);

					//Calculate just using volume fraction
					Vector<Double> volFractions=m.calculateVolumeFractions(molFractions);
					double STmixVol=m.calculateMixtureST(volFractions);

					long t3=System.currentTimeMillis();
					
//					System.out.println((t2-t1)+"\t"+(t3-t2));//unifac method takes a lot longer!
					
					STmixVol=Units.surfaceTensionConvertTo(STmixVol, Units.COMMON);

					//Store predicted values in arrays:
					yunifac[j]=STmix;
					yvolfrac[j]=STmixVol;

//					MRSDUnifac+=Math.pow((mds.yexp[i]-STmix)/mds.yexp[i],2.0);
//					MRSDVolFrac+=Math.pow((mds.yexp[i]-STmixVol)/mds.yexp[i],2.0);			

					meanAbsPercentErrorUnifac+=Math.abs((STmix-mds.yexp[j])/mds.yexp[j]*100);
					meanAbsPercentErrorVolFrac+=Math.abs((STmixVol-mds.yexp[j])/mds.yexp[j]*100);

				}//end loop over experimental points for this mixture

//				MRSDUnifac=100*Math.sqrt(MRSDUnifac/(double)xexp.length);
//				MRSDVolFrac=100*Math.sqrt(MRSDVolFrac/(double)xexp.length);
				
				meanAbsPercentErrorUnifac/=(double)n;
				meanAbsPercentErrorVolFrac/=(double)n;
				
				meanAbsPercentErrorUnifacOverall+=meanAbsPercentErrorUnifac;
				meanAbsPercentErrorVolFracOverall+=meanAbsPercentErrorVolFrac;
				
				if (AllMixtureTests.createPlots) {
					String xtitle="Mole fraction component 1";
					String ytitle="Mixture "+property;
					String title=mds.nameChemical1+" + "+mds.nameChemical2;
					fraChart fc = new fraChart(mds.xexp,yunifac,yvolfrac,"Unifac","VolFrac",mds.xexp,mds.yexp,title,xtitle,ytitle);
					fc.WriteImageToFile(mds.nameChemical1+" + "+mds.nameChemical2+".png", outputFolder);
					fc=null;
				}

//				System.out.println(line1+"\t"+df.format(MRSDUnifac)+"\t"+df.format(MRSDVolFrac));
				System.out.println(mds.nameChemical1+"\t"+mds.nameChemical2+"\t"+df.format(meanAbsPercentErrorUnifac)+"\t"+df.format(meanAbsPercentErrorVolFrac));

			}// end loop over data sets
			
			//Now average results over all data sets:
			meanAbsPercentErrorUnifacOverall/=(double)dataSets.size();
			meanAbsPercentErrorVolFracOverall/=(double)dataSets.size();

			System.out.println("overall\t"+df.format(meanAbsPercentErrorUnifacOverall)+"\t"+df.format(meanAbsPercentErrorVolFracOverall));			
			
			if (AllMixtureTests.createPlots) {
				AllMixtureTests.createPlotWebPage(outputFolder,property);
				
				//display webpage:
				File webpage=new File(outputFolder+File.separator+"plots.html");
				URL myURL=webpage.toURI().toURL();	
				String strURL=myURL.toString();
				BrowserLauncher.openURL(strURL);
			}
			
//			assertTrue(meanAbsPercentErrorUnifacOverall<10);
			assertTrue(meanAbsPercentErrorVolFracOverall<10);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception:"+e.getMessage());
		}
		
	}

}
