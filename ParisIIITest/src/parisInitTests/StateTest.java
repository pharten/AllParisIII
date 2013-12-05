package parisInitTests;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import parisInit.State;

public class StateTest {

	@Test
	public void testState() {
		Assert.assertNotNull(new State());
	}

	@Test
	public void testWriteReadFromFile() throws ClassNotFoundException, IOException {
		State state1 = new State();

		state1.setSystemName("trial1");
		state1.setAScale(10);
		state1.setPScale(20);
		state1.setOpenScreen(3);
		state1.setScreen0StackOption(2);
		state1.setScreen0TableHeader("Mol%");
		
		String fileName = "src/data/state.xml";
		state1.setFileName(fileName);
		state1.writeToFile(fileName);
		State state2 = State.readFromFile(fileName);

		Assert.assertEquals("aScale's are not equal", state1.getAScale(), state2.getAScale());
		Assert.assertEquals("pScale's are not equal", state1.getPScale(), state2.getPScale());
		Assert.assertEquals("openScreen's are not equal", state1.getOpenScreen(), state2.getOpenScreen());
		Assert.assertTrue("fileName's are not equal", state1.getFileName().equals(state2.getFileName()));
		Assert.assertEquals("stackOption's are not equal", state1.getScreen0StackOption(), state2.getScreen0StackOption());
		Assert.assertEquals("tableHeader's are not equal", state1.getScreen0TableHeader(), state2.getScreen0TableHeader());
		Assert.assertTrue("systemName's are not equal", state1.getSystemName().equals(state2.getSystemName()));
	}

//
//	@Test
//	public void testClone() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetFileName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetFileName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetOpenScreen() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetOpenScreen() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetSystemName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetSystemName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetSystemUnit() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetSystemUnitUnits() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetSystemUnitString() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetSystemTemp() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetSystemTemp() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetSystemPres() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetSystemPres() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testTempConvertToSI() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testPressureConvertToSI() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetMixture() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetMixture() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetImpactFactors() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetImpactFactors() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetPTolerances() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetPTolerances() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetPScale() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetPScale() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetATolerances() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetATolerances() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetAScale() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetAScale() {
//		fail("Not yet implemented");
//	}

}
