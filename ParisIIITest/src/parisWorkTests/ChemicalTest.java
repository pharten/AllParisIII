package parisWorkTests;

import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

import parisWork.Chemical;

public class ChemicalTest {

	@Test
	public void testChemical() {
		Assert.assertNotNull(new Chemical());
	}

	@Test
	public void testClone() throws CloneNotSupportedException {
		Chemical chemical = new Chemical();
		chemical.setName("test");
		chemical.setCAS("xx-xxx-xxx");
		chemical.setFormula("NH3");
		chemical.setSmiles("ssshhhttt");
		chemical.setStructure("H3C2H3");
		chemical.getSynonyms().add("synonym1");
		chemical.getSynonyms().add("synonym2");
		chemical.setMeltingPointSource("source1");
		chemical.setBoilingPointSource("source2");
		chemical.setSolubilitySource("source3");
		chemical.setFlashPointSource("source4");
		chemical.setDensitySource("source5");
		chemical.setThermalConductivitySource("source6");
		chemical.setViscositySource("source7");
		chemical.setSurfaceTensionSource("source8");
		chemical.setVaporPressureSource("source9");
		chemical.setHeatCapacitySource("source10");
		
		Chemical clone = (Chemical) chemical.clone();
		Assert.assertEquals(chemical.getName(),clone.getName());
		Assert.assertEquals(chemical.getCAS(),clone.getCAS());
		Assert.assertEquals(chemical.getFormula(),clone.getFormula());
		Assert.assertEquals(chemical.getSmiles(),clone.getSmiles());
		Assert.assertEquals(chemical.getStructure(),clone.getStructure());
		for (int i=0; i<chemical.getSynonyms().size(); i++) {
			Assert.assertEquals(chemical.getSynonyms().get(i),clone.getSynonyms().get(i));
		}
		
		Assert.assertEquals(chemical.getMeltingPointSource(),clone.getMeltingPointSource());
		Assert.assertEquals(chemical.getBoilingPointSource(),clone.getBoilingPointSource());
		Assert.assertEquals(chemical.getSolubilitySource(),clone.getSolubilitySource());
		Assert.assertEquals(chemical.getFlashPointSource(),clone.getFlashPointSource());
		Assert.assertEquals(chemical.getDensitySource(),clone.getDensitySource());
		Assert.assertEquals(chemical.getThermalConductivitySource(),clone.getThermalConductivitySource());
		Assert.assertEquals(chemical.getViscositySource(),clone.getViscositySource());
		Assert.assertEquals(chemical.getSurfaceTensionSource(),clone.getSurfaceTensionSource());
		Assert.assertEquals(chemical.getVaporPressureSource(),clone.getVaporPressureSource());
		Assert.assertEquals(chemical.getHeatCapacitySource(),clone.getHeatCapacitySource());
		
	}

	@Test
	public void testSetGetTmSource() {
		String expected = "source";
		Chemical chemical = new Chemical();
		chemical.setMeltingPointSource(expected);
		Assert.assertEquals(expected, chemical.getMeltingPointSource());
	}

	@Test
	public void testSetGetTbSource() {
		String expected = "source";
		Chemical chemical = new Chemical();
		chemical.setBoilingPointSource(expected);
		Assert.assertEquals(expected, chemical.getBoilingPointSource());
	}

	@Test
	public void testSetGetSolubilitySource() {
		String expected = "source";
		Chemical chemical = new Chemical();
		chemical.setSolubilitySource(expected);
		Assert.assertEquals(expected, chemical.getSolubilitySource());
	}

	@Test
	public void testSetgetFlashPointSource() {
		String expected = "source";
		Chemical chemical = new Chemical();
		chemical.setFlashPointSource(expected);
		Assert.assertEquals(expected, chemical.getFlashPointSource());
	}

	@Test
	public void testSetGetDensitySource() {
		String expected = "source";
		Chemical chemical = new Chemical();
		chemical.setDensitySource(expected);
		Assert.assertEquals(expected, chemical.getDensitySource());
	}

	@Test
	public void testSetGetThermalConductivitySource() {
		String expected = "source";
		Chemical chemical = new Chemical();
		chemical.setThermalConductivitySource(expected);
		Assert.assertEquals(expected, chemical.getThermalConductivitySource());
	}

	@Test
	public void testSetGetViscositySource() {
		String expected = "source";
		Chemical chemical = new Chemical();
		chemical.setViscositySource(expected);
		Assert.assertEquals(expected, chemical.getViscositySource());
	}

	@Test
	public void testSetGetSurfaceTensionSource() {
		String expected = "source";
		Chemical chemical = new Chemical();
		chemical.setSurfaceTensionSource(expected);
		Assert.assertEquals(expected, chemical.getSurfaceTensionSource());
	}

	@Test
	public void testSetGetVPSource() {
		String expected = "source";
		Chemical chemical = new Chemical();
		chemical.setVaporPressureSource(expected);
		Assert.assertEquals(expected, chemical.getVaporPressureSource());
	}

	@Test
	public void testSetGetHeatCapacitySource() {
		String expected = "source";
		Chemical chemical = new Chemical();
		chemical.setHeatCapacitySource(expected);
		Assert.assertEquals(expected, chemical.getHeatCapacitySource());
	}

	@Test
	public void testSetGetName() {
		String expected = "Name";
		Chemical chemical = new Chemical();
		chemical.setName(expected);
		Assert.assertEquals(expected, chemical.getName());
	}

	@Test
	public void testSetGetCAS() {
		String expected = "xx-xxx-xxx";
		Chemical chemical = new Chemical();
		chemical.setCAS(expected);
		Assert.assertEquals(expected, chemical.getCAS());
	}

	@Test
	public void testSetGetFormula() {
		String expected = "NH3";
		Chemical chemical = new Chemical();
		chemical.setFormula(expected);
		Assert.assertEquals(expected, chemical.getFormula());
	}

	@Test
	public void testSetGetStructure() {
		String expected = "H3C2H3";
		Chemical chemical = new Chemical();
		chemical.setStructure(expected);
		Assert.assertEquals(expected, chemical.getStructure());
	}

	@Test
	public void testSetGetSmiles() {
		String expected = "ssshhhttt";
		Chemical chemical = new Chemical();
		chemical.setSmiles(expected);
		Assert.assertEquals(expected, chemical.getSmiles());
	}

	@Test
	public void testSetGetSynonyms() {
		String[] expected = {"synonym1", "synonym2", "synonym3"};
		Vector<String> actual = new Vector<String>();
		actual.add(expected[0]);
		actual.add(expected[1]);
		actual.add(expected[2]);
		Chemical chemical = new Chemical();
		chemical.setSynonyms(actual);
		for (int i=0; i<chemical.getSynonyms().size(); i++) {
			Assert.assertEquals(expected[i], chemical.getSynonyms().get(i));
		}
	}

	@Test
	public void testGetSynomymsInString() {
		Chemical chemical = new Chemical();
		chemical.getSynonyms().add("synonym1");
		chemical.getSynonyms().add("synonym2");
		Assert.assertEquals("synonym1; synonym2", chemical.getSynomymsInString());
	}

	@Test
	public void testEliminateSynonymRepeats() {
		String[] expected = {"synonym1", "synonym2"};
		Chemical chemical = new Chemical();
		chemical.getSynonyms().add(expected[0]);
		chemical.getSynonyms().add(expected[1]);
		chemical.getSynonyms().add(expected[0]);
		chemical.eliminateSynonymRepeats();
		for (int i=0; i<chemical.getSynonyms().size(); i++) {
			Assert.assertEquals(expected[i], chemical.getSynonyms().get(i));
		}
	}

}
