package parisInitTests;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import parisInit.State;
import parisInit.Units;
import parisWork.Mixture;

public class StateTest {
	
//	private State a, b, c;

//	@Before
//	public void initialize() {
//	    a = new State();
//	    b = new State();
//	}

	@Test
	public void testState() {
		Assert.assertNotNull(new State());
	}
	
	@Test
	public void testEquals() throws Exception {
		
		State state1 = new State();
		State state2 = new State();
		
		// test to make sure all the individual members of default state are equal
		
		// Assert primitives are equal
		Assert.assertEquals("stackOption(s) are not equal", state1.getScreen0StackOption(), state2.getScreen0StackOption());
		Assert.assertEquals("pScale(s) are not equal", state1.getPScale(), state2.getPScale());
		Assert.assertEquals("aScale(s) are not equal", state1.getAScale(), state2.getAScale());
		Assert.assertEquals("single(s) are not equal", state1.isSingle(), state2.isSingle());
		Assert.assertEquals("replacementIndex(s) are not equal", state1.getReplacementIndex(), state2.getReplacementIndex());
		Assert.assertEquals("replacementTopIndex(s) are not equal", state1.getReplacementTopIndex(), state2.getReplacementTopIndex());

		// Assert Strings are equal
		Assert.assertEquals("fileName(s) are not equal", state1.getFileName(), state2.getFileName());
		Assert.assertEquals("openScreen(s) are not equal", state1.getOpenScreen(), state2.getOpenScreen());
		Assert.assertEquals("systemName(s) are not equal", state1.getSystemName(), state2.getSystemName());
		Assert.assertEquals("systemUnit(s) are not equal", state1.getSystemUnit(), state2.getSystemUnit());
		Assert.assertEquals("systemTemp(s) are not equal", state1.getSystemTemp(), state2.getSystemTemp());
		Assert.assertEquals("systemPres(s) are not equal", state1.getSystemPres(), state2.getSystemPres());
		Assert.assertEquals("tableHeader(s) are not equal", state1.getScreen0TableHeader(), state2.getScreen0TableHeader());

		// Assert primitive Arrays are equal
		Assert.assertArrayEquals("impactFactors are not equal", state1.getImpactFactors(), state2.getImpactFactors());;
		Assert.assertArrayEquals("pTolerances are not equal", state1.getPTolerances(), state2.getPTolerances(), 0);
		Assert.assertArrayEquals("pDesiredVals are not equal", state1.getPDesiredVals(), state2.getPDesiredVals(), 0);
		Assert.assertArrayEquals("aTolerances are not equal", state1.getATolerances(), state2.getATolerances(), 0);
		Assert.assertArrayEquals("aDesiredVals are not equal", state1.getADesiredVals(), state2.getADesiredVals(), 0);
		
		// Assert User Defined Class Objects are equal
		if (state1.getMixture()==null) {
			Assert.assertTrue("mixture(s) are not equal", state1.getMixture()==state2.getMixture());
		} else {
			Assert.assertTrue("mixture(s) are not equal", state1.getMixture().equals(state2.getMixture()));
		}

	}
	
	@Test
	public void testClone() {
		State state1 = new State();
		state1.setSystemName("trial");
		state1.setSystemTemp("25");
		state1.setSystemPres("1.0");
		State state2 = state1.clone();
		
		// test to make sure all the individual members of default state are equal
		
		// Assert primitives are equal
		Assert.assertEquals("stackOption(s) are not equal", state1.getScreen0StackOption(), state2.getScreen0StackOption());
		Assert.assertEquals("pScale(s) are not equal", state1.getPScale(), state2.getPScale());
		Assert.assertEquals("aScale(s) are not equal", state1.getAScale(), state2.getAScale());
		Assert.assertEquals("single(s) are not equal", state1.isSingle(), state2.isSingle());
		Assert.assertEquals("replacementIndex(s) are not equal", state1.getReplacementIndex(), state2.getReplacementIndex());
		Assert.assertEquals("replacementTopIndex(s) are not equal", state1.getReplacementTopIndex(), state2.getReplacementTopIndex());

		// Assert Strings are equal
		Assert.assertEquals("fileName(s) are not equal", state1.getFileName(), state2.getFileName());
		Assert.assertEquals("openScreen(s) are not equal", state1.getOpenScreen(), state2.getOpenScreen());
		Assert.assertEquals("systemName(s) are not equal", state1.getSystemName(), state2.getSystemName());
		Assert.assertEquals("systemUnit(s) are not equal", state1.getSystemUnit(), state2.getSystemUnit());
		Assert.assertEquals("systemTemp(s) are not equal", state1.getSystemTemp(), state2.getSystemTemp());
		Assert.assertEquals("systemPres(s) are not equal", state1.getSystemPres(), state2.getSystemPres());
		Assert.assertEquals("tableHeader(s) are not equal", state1.getScreen0TableHeader(), state2.getScreen0TableHeader());

		// Assert primitive Arrays are equal
		Assert.assertArrayEquals("impactFactors are not equal", state1.getImpactFactors(), state2.getImpactFactors());;
		Assert.assertArrayEquals("pTolerances are not equal", state1.getPTolerances(), state2.getPTolerances(), 0);
		Assert.assertArrayEquals("pDesiredVals are not equal", state1.getPDesiredVals(), state2.getPDesiredVals(), 0);
		Assert.assertArrayEquals("aTolerances are not equal", state1.getATolerances(), state2.getATolerances(), 0);
		Assert.assertArrayEquals("aDesiredVals are not equal", state1.getADesiredVals(), state2.getADesiredVals(), 0);
		
		// Assert User Defined Class Objects are equal
		if (state1.getMixture()==null) {
			Assert.assertTrue("mixture(s) are not equal", state1.getMixture()==state2.getMixture());
		} else {
			Assert.assertTrue("mixture(s) are not equal", state1.getMixture().equals(state2.getMixture()));
		}

	}

	@Test
	public void testWriteReadFromFile() throws ClassNotFoundException, IOException {
		State state1 = new State();
		state1.setMixture(new Mixture());

		state1.setSystemName("trial1");
		state1.setAScale(10);
		state1.setPScale(20);
		state1.setOpenScreen(3);
		state1.setScreen0StackOption(1);
		state1.setScreen0TableHeader("Mol%");
		
		String fileName = "bin/data/state.xml";
		state1.setFileName(fileName);
		state1.writeToFile();
		State state2 = State.readFromFile(fileName);

		Assert.assertEquals("aScale's are not equal", state1.getAScale(), state2.getAScale());
		Assert.assertEquals("pScale's are not equal", state1.getPScale(), state2.getPScale());
		Assert.assertEquals("openScreen's are not equal", state1.getOpenScreen(), state2.getOpenScreen());
		Assert.assertTrue("fileName's are not equal", state1.getFileName().equals(state2.getFileName()));
		Assert.assertEquals("stackOption's are not equal", state1.getScreen0StackOption(), state2.getScreen0StackOption());
		Assert.assertEquals("tableHeader's are not equal", state1.getScreen0TableHeader(), state2.getScreen0TableHeader());
		Assert.assertTrue("systemName's are not equal", state1.getSystemName().equals(state2.getSystemName()));
		
//		Assert.assertTrue("States are not equal", state1.equals(state2));
	}


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
