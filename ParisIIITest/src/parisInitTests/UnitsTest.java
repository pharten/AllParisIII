package parisInitTests;

import org.junit.Assert;
import org.junit.Test;

import parisInit.Units;

public class UnitsTest {

	@Test
	public void testMassConvertTo() {
		double expected = 453.6;
		Assert.assertEquals("Bad molecular weight conversion to "+Units.SI, expected, Units.massConvertTo(expected, Units.SI), 0.0);
		Assert.assertEquals("Bad molecular weight conversion to "+Units.CGS, expected, Units.massConvertTo(expected, Units.CGS), 0.0);
		Assert.assertEquals("Bad molecular weight conversion to "+Units.COMMON, expected, Units.massConvertTo(expected, Units.COMMON), 0.0);
		Assert.assertEquals("Bad molecular weight conversion to "+Units.US, 1.0, Units.massConvertTo(expected, Units.US), 0.0);
	}

	@Test
	public void testMassConvertFrom() {
		double expected = 453.6;
		Assert.assertEquals("Bad molecular weight conversion from "+Units.SI, expected, Units.massConvertFrom(expected, Units.SI), 0.0);
		Assert.assertEquals("Bad molecular weight conversion from "+Units.CGS, expected, Units.massConvertFrom(expected, Units.CGS), 0.0);
		Assert.assertEquals("Bad molecular weight conversion from "+Units.COMMON, expected, Units.massConvertFrom(expected, Units.COMMON), 0.0);
		Assert.assertEquals("Bad molecular weight conversion from "+Units.US, expected, Units.massConvertFrom(1.0, Units.US), 0.0);
	}

	@Test
	public void testTempConvertTo() {
		double start = 298.15;
		double expected = 25.0;
		Assert.assertEquals("Bad temperature conversion to "+Units.SI, start, Units.tempConvertTo(start, Units.SI), 0.0);
		Assert.assertEquals("Bad temperature conversion to "+Units.CGS, expected, Units.tempConvertTo(start, Units.CGS), 0.0);
		Assert.assertEquals("Bad temperature conversion to "+Units.COMMON, expected, Units.tempConvertTo(start, Units.COMMON), 0.0);
		Assert.assertEquals("Bad temperature conversion to "+Units.US, 77.0, Units.tempConvertTo(start, Units.US), 0.0);
	}

	@Test
	public void testTempConvertFrom() {
		double start = 25.0;
		double expected = 298.15;
		Assert.assertEquals("Bad temperature conversion from "+Units.SI, expected, Units.tempConvertFrom(expected, Units.SI), 0.0);
		Assert.assertEquals("Bad temperature conversion from "+Units.CGS, expected, Units.tempConvertFrom(start, Units.CGS), 0.0);
		Assert.assertEquals("Bad temperature conversion from "+Units.COMMON, expected, Units.tempConvertFrom(start, Units.COMMON), 0.0);
		Assert.assertEquals("Bad temperature conversion from "+Units.US, expected, Units.tempConvertFrom(77.0, Units.US), 0.0);
	}

//	@Test
//	public void testSolubConvertTo() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSolubConvertFrom() {
//		fail("Not yet implemented");
//	}
//
	@Test
	public void testDensityConvertTo() {
		double start = 1000.0;
		double expected = 1.0;
		Assert.assertEquals("Bad density conversion to "+Units.SI, start, Units.densityConvertTo(start, Units.SI), 0.0);
		Assert.assertEquals("Bad density conversion to "+Units.CGS, expected, Units.densityConvertTo(start, Units.CGS), 0.0);
		Assert.assertEquals("Bad density conversion to "+Units.COMMON, expected, Units.densityConvertTo(start, Units.COMMON), 0.0);
		Assert.assertEquals("Bad density conversion to "+Units.US, 62.4, Units.densityConvertTo(start, Units.US), 0.0);
	}

	@Test
	public void testDensityConvertFrom() {
		double start = 1.0;
		double expected = 1000.0;
		Assert.assertEquals("Bad density conversion from "+Units.SI, expected, Units.densityConvertFrom(expected, Units.SI), 0.0);
		Assert.assertEquals("Bad density conversion from "+Units.CGS, expected, Units.densityConvertFrom(start, Units.CGS), 0.0);
		Assert.assertEquals("Bad density conversion from "+Units.COMMON, expected, Units.densityConvertFrom(start, Units.COMMON), 0.0);
		Assert.assertEquals("Bad density conversion from "+Units.US, expected, Units.densityConvertFrom(62.4, Units.US), 0.0);
	}
	
	@Test
	public void testPressureConvertTo() {
		double start = 1.0;
		double expected = 7.502;
		Assert.assertEquals("Bad pressure conversion to "+Units.SI, start, Units.pressureConvertTo(start, Units.SI), 0.0);
		Assert.assertEquals("Bad pressure conversion to "+Units.CGS, 1000.0, Units.pressureConvertTo(start, Units.CGS), 0.0);
		Assert.assertEquals("Bad pressure conversion to "+Units.COMMON, expected, Units.pressureConvertTo(start, Units.COMMON), 0.0);
		Assert.assertEquals("Bad pressure conversion to "+Units.US, start, Units.pressureConvertTo(101.325, Units.US), 0.0);
	}

	@Test
	public void testPressureConvertFrom() {
		double start = 7.502;
		double expected = 1.0;
		Assert.assertEquals("Bad pressure conversion from "+Units.SI, expected, Units.pressureConvertFrom(expected, Units.SI), 0.0);
		Assert.assertEquals("Bad pressure conversion from "+Units.CGS, expected, Units.pressureConvertFrom(1000.0, Units.CGS), 0.0);
		Assert.assertEquals("Bad pressure conversion from "+Units.COMMON, expected, Units.pressureConvertFrom(start, Units.COMMON), 0.0);
		Assert.assertEquals("Bad pressure conversion from "+Units.US, 101.325, Units.pressureConvertFrom(expected, Units.US), 0.0);
	}
	
	@Test
	public void testHcapConvertTo() {
		double start = 4184.1;
		double expected = 1.0;
		Assert.assertEquals("Bad heat capacity conversion to "+Units.SI, start, Units.hcapConvertTo(start, Units.SI), 0.0);
		Assert.assertEquals("Bad heat capacity conversion to "+Units.CGS, expected, Units.hcapConvertTo(start, Units.CGS), 0.0);
		Assert.assertEquals("Bad heat capacity conversion to "+Units.COMMON, expected, Units.hcapConvertTo(start, Units.COMMON), 0.0);
		Assert.assertEquals("Bad heat capacity conversion to "+Units.US, expected, Units.hcapConvertTo(1899000.0, Units.US), 0.0);
	}

	@Test
	public void testHcapConvertFrom() {
		double start = 1.0;
		double expected = 4184.1;
		Assert.assertEquals("Bad heat capacity conversion from "+Units.SI, expected, Units.hcapConvertFrom(expected, Units.SI), 0.0);
		Assert.assertEquals("Bad heat capacity conversion from "+Units.CGS, expected, Units.hcapConvertFrom(start, Units.CGS), 0.0);
		Assert.assertEquals("Bad heat capacity conversion from "+Units.COMMON, expected, Units.hcapConvertFrom(start, Units.COMMON), 0.0);
		Assert.assertEquals("Bad heat capacity conversion from "+Units.US, 1899000.0, Units.hcapConvertFrom(start, Units.US), 0.0);
	}
	
	@Test
	public void testViscosityConvertTo() {
		double start = 1.0;
		double expected = 1000.0;
		Assert.assertEquals("Bad viscosity conversion to "+Units.SI, expected, Units.viscosityConvertTo(expected, Units.SI), 0.0);
		Assert.assertEquals("Bad viscosity conversion to "+Units.CGS, 10.0, Units.viscosityConvertTo(start, Units.CGS), 0.0);
		Assert.assertEquals("Bad viscosity conversion to "+Units.COMMON, expected, Units.viscosityConvertTo(start, Units.COMMON), 0.0);
		Assert.assertEquals("Bad viscosity conversion to "+Units.US, 0.6723, Units.viscosityConvertTo(start, Units.US), 0.0);
	}

	@Test
	public void testViscosityConvertFrom() {
		double start = 1000.0;
		double expected = 1.0;
		Assert.assertEquals("Bad viscosity conversion from "+Units.SI, start, Units.viscosityConvertFrom(start, Units.SI), 0.0);
		Assert.assertEquals("Bad viscosity conversion from "+Units.CGS, expected, Units.viscosityConvertFrom(10.0, Units.CGS), 0.0);
		Assert.assertEquals("Bad viscosity conversion from "+Units.COMMON, expected, Units.viscosityConvertFrom(start, Units.COMMON), 0.0);
		Assert.assertEquals("Bad viscosity conversion from "+Units.US, expected, Units.viscosityConvertFrom(0.6723, Units.US), 0.0);
	}
	
	@Test
	public void testThermalConductivityConvertTo() {
		double start = 1.0;
		double expected = 1000.0;
		Assert.assertEquals("Bad thermal conductivity conversion to "+Units.SI, expected, Units.thermalConductivityConvertTo(expected, Units.SI), 0.0);
		Assert.assertEquals("Bad thermal conductivity conversion to "+Units.CGS, start, Units.thermalConductivityConvertTo(418.41, Units.CGS), 0.0);
		Assert.assertEquals("Bad thermal conductivity conversion to "+Units.COMMON, expected, Units.thermalConductivityConvertTo(start, Units.COMMON), 0.0);
		Assert.assertEquals("Bad thermal conductivity conversion to "+Units.US, start, Units.thermalConductivityConvertTo(6228.7, Units.US), 0.0);
	}

	@Test
	public void testThermalConductivityConvertFrom() {
		double start = 1000.0;
		double expected = 1.0;
		Assert.assertEquals("Bad thermal conductivity conversion from "+Units.SI, start, Units.thermalConductivityConvertFrom(start, Units.SI), 0.0);
		Assert.assertEquals("Bad thermal conductivity conversion from "+Units.CGS, 418.41, Units.thermalConductivityConvertFrom(1.0, Units.CGS), 0.0);
		Assert.assertEquals("Bad thermal conductivity conversion from "+Units.COMMON, expected, Units.thermalConductivityConvertFrom(start, Units.COMMON), 0.0);
		Assert.assertEquals("Bad thermal conductivity conversion from "+Units.US, 6228.7, Units.thermalConductivityConvertFrom(1.0, Units.US), 0.0);
	}
	
	@Test
	public void testSurfaceTensionConvertTo() {
		double start = 1.0;
		double expected = 1000.0;
		Assert.assertEquals("Bad surface tension conversion to "+Units.SI, expected, Units.surfaceTensionConvertTo(expected, Units.SI), 0.0);
		Assert.assertEquals("Bad surface tension conversion to "+Units.CGS, expected, Units.surfaceTensionConvertTo(start, Units.CGS), 0.0);
		Assert.assertEquals("Bad surface tension conversion to "+Units.COMMON, expected, Units.surfaceTensionConvertTo(start, Units.COMMON), 0.0);
		Assert.assertEquals("Bad surface tension conversion to "+Units.US, 2.205, Units.surfaceTensionConvertTo(start, Units.US), 0.0);
	}

	@Test
	public void testSurfaceTensionConvertFrom() {
		double start = 1000.0;
		double expected = 1.0;
		Assert.assertEquals("Bad surface tension conversion from "+Units.SI, start, Units.surfaceTensionConvertFrom(start, Units.SI), 0.0);
		Assert.assertEquals("Bad surface tension conversion from "+Units.CGS, expected, Units.surfaceTensionConvertFrom(start, Units.CGS), 0.0);
		Assert.assertEquals("Bad surface tension conversion from "+Units.COMMON, expected, Units.surfaceTensionConvertFrom(start, Units.COMMON), 0.0);
		Assert.assertEquals("Bad surface tension conversion from "+Units.US, expected, Units.surfaceTensionConvertFrom(2.205, Units.US), 0.0);
	}
	
}
