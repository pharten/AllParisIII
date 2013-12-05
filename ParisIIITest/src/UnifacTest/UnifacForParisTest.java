/**
 * 
 */
package UnifacTest;

import static org.junit.Assert.*;

import java.io.*;
import java.util.Vector;

import org.junit.Test;

import parisWork.Chemical;
import parisWork.Chemicals;

import unifac.UNIFACforPARIS;




/**
 * @author TMARTI02
 *
 */
public class UnifacForParisTest {

	
	/**
	 * Test method for {@link unifac.UNIFACforPARIS#calculateInfDilActCoef(java.lang.String, java.util.Vector, java.util.Vector, double)}.
	 * 
	 * Tests if the program accurately calculates infinite dilution 
	 * activity coefficients in a solvent or solvent mixture
	 * 
	 * The solvent mixtures in this test are water/NMP mixtures
	 * 
	 * The temperature ranges from 30-60C and the water mass fraction 
	 * ranges from 0-6%.
	 * 
	 */
	@Test
	public void testCalculateInfDilActCoef() {

		UNIFACforPARIS u = UNIFACforPARIS.getInstance();

		Vector<String> solventCASNumbers=new Vector<String>();
		solventCASNumbers.add("7732-18-5");//water- MW=18
		solventCASNumbers.add("872-50-4");//NMP MW=99.13

		double MW_Water=18.01;
		double MW_NMP=99.13;
		

		double[] tempC={30,40,50,60};
		double meanAbsPercentError=0;
		int count=0;

		try {
			Chemicals allChemicals = Chemicals.readFromFile("data/Chemicals.xml");
		
			InputStream in = getClass().getResourceAsStream("krummen 2000.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String header=br.readLine();
			while (true) {
				String Line=br.readLine();
				
				if (Line==null) break;
				
				String [] data=Line.split("\t");
				
				String soluteName=data[0];
				Chemical solute=allChemicals.getBySynonym(soluteName);
				
				if (solute==null) fail("solute not found by "+soluteName);
				
				String soluteCASNumber=solute.getCAS();

				//TODO- rewrite using mixture class so can use it's code for calculating mole fractions
				
				double massFractionWater=Double.parseDouble(data[1])/100.0;
				double massFractionNMP=1-massFractionWater;
				double totalmoles=massFractionWater/MW_Water+massFractionNMP/MW_NMP;
				double moleFractionWater=massFractionWater/MW_Water/totalmoles;
				double moleFractionNMP=massFractionNMP/MW_NMP/totalmoles;
				
				Vector<Double> molFractions=new Vector<Double>();
				molFractions.add(moleFractionWater);
				molFractions.add(moleFractionNMP);
				
				double[] gammaExp=new double [4];
				
				for (int i=2;i<data.length;i++) {
					gammaExp[i-2]=Double.parseDouble(data[i]);
				}
				
				for (int i=0;i<tempC.length;i++) {
					count++;
					double gExp=gammaExp[i];
					double gCalc=u.calculateInfDilActCoef(soluteCASNumber, solventCASNumbers, molFractions, tempC[i]+273.15);
					
					double percentError=Math.abs((gExp-gCalc)/gExp*100);
					
					meanAbsPercentError+=percentError;
//					System.out.println(gExp+"\t"+gCalc+"\t"+percentError);
				}

			}
			
			meanAbsPercentError/=(double)count;
			
			
			System.out.println(meanAbsPercentError);
			
			br.close();
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(meanAbsPercentError<30);		
	}

	/**
	 * Test method for {@link unifac.UNIFACforPARIS#calculateActivityCoefficients(java.util.Vector, java.util.Vector, double)}.
	 */
//	@Test
//	public void testCalculateActivityCoefficients() {
//		fail("Not yet implemented");
//	}

}
