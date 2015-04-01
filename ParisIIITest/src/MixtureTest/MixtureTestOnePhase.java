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

public class MixtureTestOnePhase {


	/**
	 * This test checks how well the routine predicts phase separation of mixtures
	 * 
	 */
	@Test
	public void testCalculateOnePhase() {
		
		try {
			
			double tempK=25.0+273.15;

			Chemicals allChemicals = Chemicals.readFromFile("data/Chemicals.xml");

			Chemical pc1=allChemicals.getBySynonym("ethanol");
			Chemical pc2=allChemicals.getBySynonym("water");
			Chemical pc3=allChemicals.getBySynonym("benzene");
			Chemical pc4=allChemicals.getBySynonym("butanol");
			Chemical pc5=allChemicals.getBySynonym("pentanol");
			
			Chemicals chemicals= new Chemicals();
			chemicals.add(pc1);
			chemicals.add(pc2);
			
			Mixture mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			Vector<Double>xmol=new Vector<Double>();
			
			xmol.add(0.9);
			xmol.add(0.1);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
			chemicals.clear();
			chemicals.add(pc1);
			chemicals.add(pc3);
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.9);
			xmol.add(0.1);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
			// water and benzene make two phases
			chemicals.clear();
			chemicals.add(pc2);
			chemicals.add(pc3);
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.5);
			xmol.add(0.5);
			assertFalse(mixture.isOnePhase(xmol, tempK));
			
			// this mixture is one phase even though two components are not.
			chemicals.clear();
			chemicals.add(pc1);
			chemicals.add(pc2);
			chemicals.add(pc3);
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.9);
			xmol.add(0.05);
			xmol.add(0.05);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
			// the algorithm should have same results with ethanol, benzene, water order
			chemicals.clear();
			chemicals.add(pc1);
			chemicals.add(pc3);
			chemicals.add(pc2);
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.9);
			xmol.add(0.05);
			xmol.add(0.05);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
			// add in more benzene and water to bring out the phase separation
			chemicals.clear();
			chemicals.add(pc1);
			chemicals.add(pc2);
			chemicals.add(pc3);
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.4);
			xmol.add(0.3);
			xmol.add(0.3);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
			// the algorithm should have same results with ethanol, benzene, water order
			chemicals.clear();
			chemicals.add(pc1);
			chemicals.add(pc3);
			chemicals.add(pc2);
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.4);
			xmol.add(0.3);
			xmol.add(0.3);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
			// the algorithm should have same results with benzene, ethanol, water order
			chemicals.clear();
			chemicals.add(pc3);
			chemicals.add(pc1);
			chemicals.add(pc2);
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.3);
			xmol.add(0.4);
			xmol.add(0.3);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
			// the algorithm should have same results with benzene, water, ethanol order
			chemicals.clear();
			chemicals.add(pc3);
			chemicals.add(pc2);
			chemicals.add(pc1);
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.3);
			xmol.add(0.3);
			xmol.add(0.4);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
			// the algorithm should have same results with water, ethanol, benzene order
			chemicals.clear();
			chemicals.add(pc2);
			chemicals.add(pc1);
			chemicals.add(pc3);
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.3);
			xmol.add(0.4);
			xmol.add(0.3);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
			// the algorithm should have same results with water, benzene, ethanol order
			chemicals.clear();
			chemicals.add(pc2);
			chemicals.add(pc3);
			chemicals.add(pc1);
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.3);
			xmol.add(0.3);
			xmol.add(0.4);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
			// water and pentanol mixtures
			
			chemicals.clear();
			chemicals.add(pc2);
			chemicals.add(pc5);
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.99);
			xmol.add(0.02);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.9);
			xmol.add(0.1);
			assertFalse(mixture.isOnePhase(xmol, tempK));
			
			// pentanol, butanol, and water mixtures
			
			chemicals.clear();
			chemicals.add(pc5);
			chemicals.add(pc4);
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.5);
			xmol.add(0.5);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
			chemicals.clear();
			chemicals.add(pc5);
			chemicals.add(pc4);
			chemicals.add(pc2);
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.47);
			xmol.add(0.47);
			xmol.add(0.06);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.46);
			xmol.add(0.46);
			xmol.add(0.08);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.34);
			xmol.add(0.34);
			xmol.add(0.32);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.33);
			xmol.add(0.33);
			xmol.add(0.34);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.25);
			xmol.add(0.25);
			xmol.add(0.5);
			assertTrue(mixture.isOnePhase(xmol, tempK));
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception:"+e.getMessage());
		}
		
	}
	


}
