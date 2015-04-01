package parisWorkTests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import parisWork.Chemical;
import parisWork.Chemicals;
import parisWork.Mixture;

public class MixtureTest {

	Chemicals allChemicals = null;
	
	@Before
	public void setUp() throws Exception {
		allChemicals = Chemicals.readFromFile("data/Chemicals.xml");
	}

	@After
	public void tearDown() throws Exception {
		allChemicals.clear();
	}

	@Test
	public void testMixture() {
		Assert.assertNotNull(new Mixture());
	}

	@Test
	public void testMixtureDouble() {
		double tempK = 273.15 + 25.0;
		Mixture mixture = new Mixture(tempK);
		Assert.assertNotNull(mixture);
		Assert.assertEquals(mixture.getTempK(), 298.15, 0.0);
	}

	@Test
	public void testMixtureChemicalArrayStringDouble() throws Exception 
	{
		Chemical[] chemicals = {allChemicals.get(0), allChemicals.get(1), allChemicals.get(2)};
		Mixture mixture = new Mixture(chemicals, "5:4:1", 298.15);
		Assert.assertNotNull(mixture);
		Assert.assertEquals(mixture.getWghtFractions().get(0), 0.5, 0);
		Assert.assertEquals(mixture.getWghtFractions().get(1), 0.4, 0);
		Assert.assertEquals(mixture.getWghtFractions().get(2), 0.1, 0);

	}

	@Test
	public void testMixtureChemicalChemicalStringDouble() {
		Mixture mixture = new Mixture(allChemicals.get(0), allChemicals.get(1), "6:4", 298.15);
		Assert.assertNotNull(mixture);
		Assert.assertEquals(mixture.getWghtFractions().get(0), 0.6, 0);
		Assert.assertEquals(mixture.getWghtFractions().get(1), 0.4, 0);
	}

	@Test
	public void testMixtureChemicalChemicalChemicalStringDouble() {
		Mixture mixture = new Mixture(allChemicals.get(0), allChemicals.get(1), allChemicals.get(2), "5:4:1", 298.15);
		Assert.assertNotNull(mixture);
		Assert.assertEquals(mixture.getWghtFractions().get(0), 0.5, 0);
		Assert.assertEquals(mixture.getWghtFractions().get(1), 0.4, 0);
		Assert.assertEquals(mixture.getWghtFractions().get(2), 0.1, 0);
	}

	@Test
	public void testEqualsMixture() {
		Mixture mixture1 = new Mixture(allChemicals.get(0), allChemicals.get(1), "6:4", 298.15);
		Mixture mixture2 = new Mixture(allChemicals.get(0), allChemicals.get(1), "6:4", 298.15);
		Assert.assertTrue(mixture1.equals(mixture2));
		mixture2.getWghtFractions().set(0, 0.4);
		mixture2.getWghtFractions().set(1, 0.6);
		Assert.assertFalse(mixture1.equals(mixture2));
	}

	@Test
	public void testClone() throws CloneNotSupportedException {
		Mixture mixture = new Mixture(allChemicals.get(0), allChemicals.get(1), allChemicals.get(2), "5:4:1", 298.15);
		Mixture clone = mixture.clone();
		Assert.assertTrue(mixture.equals(clone));
	}

//	@Test
//	public void testCalculateMixtureScore() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCalculateMixtureProperties() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCalculatePhysicalPropertiesOfMixture() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCalculateActivityCoefficientsOfMixture() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCalculateEnvironmentalIndexesOfMixture() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testIsOnePhase() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCalculateMixtureST() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCalculateMixtureSTwithUNIFACAiVectorOfDoubleDoubleUNIFACforPARIS() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCalculateMixtureSTwithUNIFACAiVectorOfDoubleDouble() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCalculateMassFractions() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSolveForMP() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetMixtureName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetChemicals() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetChemicalsVectorOfChemical() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetChemicalsChemicalArray() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetChemicals2() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetWghtFractions() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetWghtFractions() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetUnifac() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetUnifac() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetMassRatios() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetMixtureScore() {
//		fail("Not yet implemented");
//	}

}
