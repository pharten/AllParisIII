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

public class MixtureTestLLE {


	@Test
	public void testCalculateLLE() {
		double meanAbsPercentErrorOverall=0;
		int mixtureCount=0;

		DecimalFormat df=new DecimalFormat("0.00");
		DecimalFormat dfSci=new DecimalFormat("0.0000E00");
		
		try {
			
			String folder="TestResults";
			String property="LLE";

			File folderOverall=new File(folder);
			if (!folderOverall.exists()) folderOverall.mkdir();
			
			String mixtureFileName="mixture data "+property+".txt";
//			String mixtureFileName="mixture data "+property+"-test.txt";
//			String mixtureFileName="mixture data "+property+"-test2.txt";
			

			String outputFolder=folder+"/"+property+" plots";
			
			File pFolder=new File(outputFolder);
			if (!pFolder.exists()) pFolder.mkdir();
			
//			Chemicals allChemicals = Chemicals.readFromFile(getClass().getResource("/data/Chemicals.xml").getFile());
			Chemicals allChemicals = Chemicals.readFromFile(getClass().getResource("/data/Chemicals.txt").getFile());

//			this.addMissingChemicals(allChemicals);
			

			//Use pure component values from papers since we want to see
			// how well the mixture values are calculated not the pure comp values
//			this.usePureComponentValuesFromPapers(allChemicals);
			
			System.out.println("\n"+property+"\nComp1\tComp2\tMAE");
			
			Vector <MixtureDataSet>dataSets=AllMixtureTests.readDataFileFormat4("src/MixtureTest/"+mixtureFileName);
			
			for (int i=0;i<dataSets.size();i++) {
				MixtureDataSet mds=dataSets.get(i);
				
				Chemical pc1=null;
				pc1=allChemicals.getBySynonym(mds.nameChemical1);
				
				
				Chemical pc2=null;
				pc2=allChemicals.getBySynonym(mds.nameChemical2);

//				if (!mds.nameChemical1.equals("1,2-ethanediol")) continue;
				
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
				
				double meanAbsError=0;
				
				double [] y=new double[mds.xexp.length];

				int n=mds.xexp.length;
				
				Mixture mixture=new Mixture();
				mixture.setUnifac(UNIFACforPARIS.getInstance());

				int count=0;
				
				System.out.println(mds.nameChemical1+"\t"+mds.nameChemical2);
				System.out.println("X1Aexp\tX1Acalc\tX1Bexp\tX1Bcalc\ttempC");

				for (int j=0;j<n;j++) {
					mixture.setChemicals(chemicals);
					double tempK=mds.yexp[j]+273.15;
					
//					System.out.println(tempK);
					
					long t1=System.currentTimeMillis();
//					double []vals=mixture.calculateLLE_3(tempK);
					double []vals=mixture.calculateLLE(tempK);
					long t2=System.currentTimeMillis();
					
//					System.out.println(t2-t1);
					
					if (vals==null) {
						
					} else {
						double X1Acalc=vals[0];
						double X1Bcalc=vals[1];
						
						double X1Aexp=mds.xexp[j];
						double X1Bexp=mds.xexp2[j];
						
						if (X1Aexp!=-9999) {
							meanAbsError+=Math.abs((X1Acalc-X1Aexp));
							count++;
						}
						
						if (X1Bexp!=-9999) {
							meanAbsError+=Math.abs((X1Bcalc-X1Bexp));
							count++;
						}

						System.out.println(dfSci.format(X1Aexp)+"\t"+dfSci.format(X1Acalc)+"\t"+dfSci.format(X1Bexp)+"\t"+dfSci.format(X1Bcalc)+"\t"+mds.yexp[j]);
					}
				}//end loop over data points in mixture
				System.out.println("");
				
				if (count>0)
					meanAbsError/=(double)count;
				else
					meanAbsError=-9999;
				
//				for (int j=0;j<n;j++) {
////					double [] molFractions={xexp[i],1-xexp[i]};
//					Vector<Double>molFractions=new Vector<Double>();
//					molFractions.add(mds.xexp[j]);
//					molFractions.add(1-mds.xexp[j]);
//					
//					Mixture mixture=new Mixture();
//					mixture.setChemicals(chemicals);
//					mixture.setWghtFractions(mixture.calculateMassFractions(molFractions));
//
//					//Faster to just calc TC instead of all properties:
//					mixture.calculateMixtureProperties(25+273.15);
//					
//					//Store predicted value in array:
////					y[j]=Units.tempConvertTo(mixture.getBoilingPoint(),Units.COMMON);
//					y[j]=mixture.getBoilingPoint();
//					
//					meanAbsPercentError+=Math.abs((y[j]-mds.yexp[j])/mds.yexp[j]*100);
//
//				}//end loop over experimental points for this mixture
//
//				meanAbsPercentError/=(double)n;
//				meanAbsPercentErrorOverall+=meanAbsPercentError;
				
//				if (AllMixtureTests.createPlots) {
//					String xtitle="Mole fraction component 1";
//					String ytitle="Mixture "+property;
//					String title=mds.nameChemical1+" + "+mds.nameChemical2;
//					fraChart fc = new fraChart(mds.xexp,y,"Pred",mds.xexp,mds.yexp,title,xtitle,ytitle);
//					
//					fc.WriteImageToFile(mds.nameChemical1+" + "+mds.nameChemical2+".png", outputFolder);
//					fc=null;
//				}

//				System.out.println(line1+"\t"+df.format(MRSDUnifac)+"\t"+df.format(MRSDVolFrac));
//				System.out.println(mds.nameChemical1+"\t"+mds.nameChemical2+"\t"+df.format(meanAbsError));

			}// end loop over data sets
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	/**
	 * In this test it checks how well our routine predicts boiling point of mixtures
	 * 
	 */
//	@Test
	public void xxtestCalculateLLE() {
		
		
		DecimalFormat df=new DecimalFormat("0.00");
		
		
//		double meanAbsPercentErrorOverall=0;
//		int mixtureCount=0;

		try {
			
//			String folder="TestResults";
//			String property="boiling point";
//
//			File folderOverall=new File(folder);
//			if (!folderOverall.exists()) folderOverall.mkdir();
//			
//			String mixtureFileName="mixture data "+property+".txt";
//
//			String outputFolder=folder+"/"+property+" plots";
//			
//			File pFolder=new File(outputFolder);
//			if (!pFolder.exists()) pFolder.mkdir();
			
//			Chemicals allChemicals = Chemicals.readFromFile(getClass().getResource("/data/Chemicals.xml").getFile());
			Chemicals allChemicals = Chemicals.readFromFile(getClass().getResource("/data/Chemicals.txt").getFile());


//			Chemical pc1=allChemicals.getBySynonym("ethanol");
//			Chemical pc1=allChemicals.getBySynonym("methanol");
//			Chemical pc1=allChemicals.getBySynonym("benzene");
//			Chemical pc1=allChemicals.getByCAS("629-62-9");
//			Chemical pc1=allChemicals.getByCAS("142-62-1");//hexanoic acid
//			Chemical pc1=allChemicals.getByCAS("97-85-8");
			Chemical pc1=allChemicals.getBySynonym("2-butanone");
			
			Chemical pc2=allChemicals.getBySynonym("water");

			
			Chemicals chemicals=new Chemicals();
			chemicals.add(pc1);
			chemicals.add(pc2);
			
			Mixture mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			
			mixture.setChemicals(chemicals);
			
			double tempK=25+273.15;
//			double tempK=150+273.15;
			
//			System.out.println(mixture.calculateLLE(tempK));
			System.out.println(mixture.calculateLLE_simple(298.15));

			assertTrue(true);
			
//			assertTrue(meanAbsPercentErrorOverall<10);
		
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception:"+e.getMessage());
		}
		
	}
	
//	private void addMissingChemicals(Chemicals allChemicals) {
//		
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
//		chemical2.setBoilingPoint(373.15);
//		chemical2.setAntoineConstantA(8.07131);
//		chemical2.setAntoineConstantB(1730.63);
//		chemical2.setAntoineConstantC(233.426);
//		chemical2.setAntoineTmin(1+273.15);
//		chemical2.setAntoineTmax(100+273.15);
//		chemical2.setAntoineSource("DECHEMA");
//		
//		allChemicals.add(chemical2);	
//
//		
//	}

}
