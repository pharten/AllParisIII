package MixtureTest;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import parisWork.Chemical;
import parisWork.Chemicals;
import parisWork.Mixture;
import unifac.UNIFACforPARIS;

public class TieLineTest {

	/**
	 * This test checks how well the routine predicts phase separation of mixtures
	 * 
	 */
	@Test
	public void testTieLine() {

		try {

			double tempK=25.0+273.15;
			boolean result = true;

			Chemicals allChemicals = Chemicals.readFromFile("data/Chemicals.xml");

			Chemical pc1=allChemicals.getBySynonym("propanol");
			Chemical pc2=allChemicals.getBySynonym("water");
			Chemical pc3=allChemicals.getBySynonym("pentanol");
			Chemical pc4=allChemicals.getBySynonym("hexanol");
			Chemical pc5=allChemicals.getBySynonym("pentanol");

			// 0.0 propanol fraction
			Chemicals chemicals= new Chemicals();
			chemicals.add(pc2);
			chemicals.add(pc3);

			Mixture mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);

			Vector<Double> xmol = new Vector<Double>();
			xmol.add(0.995);
			xmol.add(1.0-xmol.get(0));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+", "+result);

			assertTrue(result);
			
			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);

			xmol.clear();
			xmol.add(0.977);
			xmol.add(1.0-xmol.get(0));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+", "+result);

			assertTrue(result);
			
			xmol.clear();
			xmol.add(0.976);
			xmol.add(1.0-xmol.get(0));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+", "+result);

			assertFalse(result);
			
			chemicals= new Chemicals();
			chemicals.add(pc3);
			chemicals.add(pc2);

			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.024);
			xmol.add(1.0-xmol.get(0));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+", "+result);

			assertFalse(result);
			
			chemicals= new Chemicals();
			chemicals.add(pc2);
			chemicals.add(pc3);

			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.46);
			xmol.add(1.0-xmol.get(0));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+", "+result);

			assertFalse(result);
			
			chemicals= new Chemicals();
			chemicals.add(pc3);
			chemicals.add(pc2);

			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.54);
			xmol.add(1.0-xmol.get(0));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+", "+result);

			assertFalse(result);
			
			chemicals= new Chemicals();
			chemicals.add(pc2);
			chemicals.add(pc3);

			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.45);
			xmol.add(0.55);
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+", "+result);

			assertTrue(result);
			
			chemicals= new Chemicals();
			chemicals.add(pc3);
			chemicals.add(pc2);

			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.55);
			xmol.add(0.45);
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+": "+result);

			assertTrue(result);
			
			// 0.0 propanol fraction.  Same as immediately above, but ternary search
			chemicals= new Chemicals();
			chemicals.add(pc3);
			chemicals.add(pc2);
			chemicals.add(pc1);

			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.55);
			xmol.add(0.45);
			xmol.add(0.0);
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			// 0.0 propanol fraction, ternary search
			chemicals.clear();
			chemicals.add(pc1);
			chemicals.add(pc2);
			chemicals.add(pc3);

			mixture=new Mixture();
			mixture.setUnifac(UNIFACforPARIS.getInstance());
			mixture.setChemicals(chemicals);
			
			xmol.clear();
			xmol.add(0.0);
			xmol.add(0.98);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			xmol.clear();
			xmol.add(0.0);
			xmol.add(0.97);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);

			xmol.clear();
			xmol.add(0.0);
			xmol.add(0.9);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.0);
			xmol.add(0.8);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.0);
			xmol.add(0.7);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.0);
			xmol.add(0.6);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.0);
			xmol.add(0.5);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.0);
			xmol.add(0.4);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			

			xmol.clear();
			xmol.add(0.0);
			xmol.add(0.46);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.0);
			xmol.add(0.45);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			// 0.1 propanol fraction, ternary search
			xmol.clear();
			xmol.add(0.1);
			xmol.add(0.86);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			xmol.clear();
			xmol.add(0.1);
			xmol.add(0.85);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.1);
			xmol.add(0.8);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.1);
			xmol.add(0.7);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.1);
			xmol.add(0.6);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.1);
			xmol.add(0.5);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.1);
			xmol.add(0.48);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.1);
			xmol.add(0.47);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			xmol.clear();
			xmol.add(0.1);
			xmol.add(0.4);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			// 0.2 propanol fraction, ternary search
			xmol.clear();
			xmol.add(0.2);
			xmol.add(0.7);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			xmol.clear();
			xmol.add(0.2);
			xmol.add(0.69);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.2);
			xmol.add(0.6);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.2);
			xmol.add(0.51);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.2);
			xmol.add(0.5);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			// 0.9 water fraction, ternary search
			xmol.clear();
			xmol.add(0.06);
			xmol.add(0.9);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.07);
			xmol.add(0.9);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			// 0.8 water fraction, ternary search
			xmol.clear();
			xmol.add(0.15);
			xmol.add(0.8);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			xmol.clear();
			xmol.add(0.14);
			xmol.add(0.8);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			// 0.7 water fraction, ternary search
			xmol.clear();
			xmol.add(0.20);
			xmol.add(0.7);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			xmol.clear();
			xmol.add(0.19);
			xmol.add(0.7);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			// 0.6 water fraction, ternary search
			xmol.clear();
			xmol.add(0.26);
			xmol.add(0.6);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			xmol.clear();
			xmol.add(0.25);
			xmol.add(0.6);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			// 0.5 water fraction, ternary search
			xmol.clear();
			xmol.add(0.2);
			xmol.add(0.5);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			xmol.clear();
			xmol.add(0.19);
			xmol.add(0.5);
			xmol.add(1.0-xmol.get(0)-xmol.get(1));
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			// 0.5 pentanol fraction, ternary search
			double pentfrac = 0.5;
			xmol.clear();
			xmol.add(0.03);
			xmol.add(1.0-xmol.get(0)-pentfrac);
			xmol.add(pentfrac);
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.04);
			xmol.add(1.0-xmol.get(0)-pentfrac);
			xmol.add(pentfrac);
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			// 0.4 pentanol fraction, ternary search
			pentfrac = 0.4;
			xmol.clear();
			xmol.add(0.12);
			xmol.add(1.0-xmol.get(0)-pentfrac);
			xmol.add(pentfrac);
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.13);
			xmol.add(1.0-xmol.get(0)-pentfrac);
			xmol.add(pentfrac);
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			// 0.3 pentanol fraction, ternary search
			pentfrac = 0.3;
			xmol.clear();
			xmol.add(0.20);
			xmol.add(1.0-xmol.get(0)-pentfrac);
			xmol.add(pentfrac);
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			xmol.clear();
			xmol.add(0.19);
			xmol.add(1.0-xmol.get(0)-pentfrac);
			xmol.add(pentfrac);
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			// 0.2 pentanol fraction, ternary search
			pentfrac = 0.2;
			xmol.clear();
			xmol.add(0.26);
			xmol.add(1.0-xmol.get(0)-pentfrac);
			xmol.add(pentfrac);
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
			xmol.clear();
			xmol.add(0.25);
			xmol.add(1.0-xmol.get(0)-pentfrac);
			xmol.add(pentfrac);
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			// 0.1 pentanol fraction, ternary search
			pentfrac = 0.1;
			xmol.clear();
			xmol.add(0.19);
			xmol.add(1.0-xmol.get(0)-pentfrac);
			xmol.add(pentfrac);
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertFalse(result);
			
			xmol.clear();
			xmol.add(0.20);
			xmol.add(1.0-xmol.get(0)-pentfrac);
			xmol.add(pentfrac);
			
			result = mixture.isOnePhase(xmol, tempK);
//			System.out.println(chemicals.get(0).getName()+": "+xmol.get(0)+
//					", "+chemicals.get(1).getName()+": "+xmol.get(1)+
//					", "+chemicals.get(2).getName()+": "+xmol.get(2)+", "+result);

			assertTrue(result);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception:"+e.getMessage());
		}

	}

}