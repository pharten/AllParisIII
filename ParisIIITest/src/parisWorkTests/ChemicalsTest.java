package parisWorkTests;

import java.io.IOException;
import java.util.Vector;

import org.junit.Assert;
import org.junit.Test;

import parisWork.Chemical;
import parisWork.Chemicals;

public class ChemicalsTest {
	
	private static Chemical chemical1 = new Chemical();
	private static Chemical chemical2 = new Chemical();
	private static Chemical[] expected1 = {chemical1, chemical2};
	private static Chemical[] expected2 = {chemical2};
	private static String[] nameList = {"ChemicalX", "ChemicalY"};
	private static String[] casList = {"23-4-1234", "23-5-1234"};
	private static String[] synList = {"Synonym1", "Synonym2"};

	@Test
	public void testChemicals() {

		Chemicals actual = new Chemicals();
		actual.add(chemical1);
		actual.add(chemical2);

		Assert.assertArrayEquals("Expected and Actual are not the same", expected1, actual.toArray());
	}

	@Test
	public void testFilterForLiquidPhase() {
		chemical1.setBoilingPoint(273.15);
		chemical1.setMeltingPoint(253.15);
		chemical2.setBoilingPoint(303.15);
		chemical2.setMeltingPoint(273.15);
		
		Chemicals actual = new Chemicals();
		actual.add(chemical1);
		actual.add(chemical2);
		
		actual = actual.filterForLiquidPhase(298.15);
		
		Assert.assertArrayEquals("Expected and Actual are not the same", expected2, actual.toArray());
	}

	@Test
	public void testSortByName() {
		chemical1.setName(nameList[0]);
		chemical2.setName(nameList[1]);
		
		Chemicals actual = new Chemicals();
		actual.add(chemical2); // add in reverse order
		actual.add(chemical1); // add in reverse order
		
		actual.sortByName();  // sort order by name
		
		Assert.assertArrayEquals("Expected and Actual are not the same", expected1, actual.toArray());
	}

	@Test
	public void testSortByCAS() {
		chemical1.setCAS(casList[0]);
		chemical2.setCAS(casList[1]);
		
		Chemicals actual = new Chemicals();
		actual.add(chemical2); // add in reverse order
		actual.add(chemical1); // add in reverse order
		
		actual.sortByCAS();  // sort order by CAS number
		
		Assert.assertArrayEquals("Expected and Actual are not the same", expected1, actual.toArray());
	}

	@Test
	public void testGetAllNames() {

		chemical1.setName(nameList[0]);
		chemical2.setName(nameList[1]);
		
		Chemicals chemicals = new Chemicals();
		chemicals.add(chemical1);
		chemicals.add(chemical2);
		
		String[] actual = chemicals.getAllNames();
		
		Assert.assertArrayEquals("Expected and Actual are not the same", nameList, actual);
	}

	@Test
	public void testGetAllCAS() {
		chemical1.setCAS(casList[0]);
		chemical2.setCAS(casList[1]);
		
		Chemicals chemicals = new Chemicals();
		chemicals.add(chemical1);
		chemicals.add(chemical2);
		
		String[] actual = chemicals.getAllCAS();
		
		Assert.assertArrayEquals("Expected and Actual are not the same", casList, actual);

	}

	@Test
	public void testGetByName() {
		chemical1.setName(nameList[0]);
		chemical2.setName(nameList[1]);
		
		Chemicals chemicals = new Chemicals();
		chemicals.add(chemical1);
		chemicals.add(chemical2);
		
		Chemical actual = chemicals.getByName(nameList[1]);
		
		Assert.assertEquals("Expected and Actual are not the same", chemical2, actual);
	}

	@Test
	public void testGetBySynonym() {

		Vector<String> synonyms = new Vector<String>();
		synonyms.add(synList[0]);
		synonyms.add(synList[1]);
		
		chemical2.setSynonyms(synonyms);
		
		Chemicals chemicals = new Chemicals();
		chemicals.add(chemical1);
		chemicals.add(chemical2);
		
		Chemical actual = chemicals.getBySynonym("Synonym2");
		
		Assert.assertEquals("Expected and Actual are not the same", chemical2, actual);
	}

	@Test
	public void testGetByCAS() {
		chemical1.setCAS(casList[0]);
		chemical2.setCAS(casList[1]);
		
		Chemicals chemicals = new Chemicals();
		chemicals.add(chemical1);
		chemicals.add(chemical2);
		
		Chemical actual = chemicals.getByCAS(casList[1]);
		
		Assert.assertEquals("Expected and Actual are not the same", chemical2, actual);
	}

	@Test
	public void testWriteReadByXML() throws Exception {
		
	    Chemicals chemicals1 = new Chemicals();
		chemicals1.add(chemical1);
		chemicals1.add(chemical2);
		chemical1.setName(nameList[0]);
		chemical2.setName(nameList[1]);
		chemical1.setCAS(casList[0]);
		chemical2.setCAS(casList[1]);
		
		Vector<String> synonyms = new Vector<String>();
		synonyms.add(synList[0]);
		synonyms.add(synList[1]);
		chemical2.setSynonyms(synonyms);
		
		chemical1.setBoilingPoint(273.15);
		chemical1.setMeltingPoint(253.15);
		chemical2.setBoilingPoint(303.15);
		chemical2.setMeltingPoint(273.15);
		
		chemicals1.writeByXML("data/chemicals2.xml");
		
		Chemicals chemicals2 = Chemicals.readFromFile("data/chemicals2.xml");
		
		Assert.assertEquals("Chemical lists are not same size", chemicals1.size(), chemicals2.size());
		
		for (int i=0; i<chemicals1.size(); i++) {
			Chemical chemical3 = chemicals1.get(i);
			Chemical chemical4 = chemicals2.get(i);
			Assert.assertTrue("Chemicals lists are not equal", chemical3.equals(chemical4));
		}
		
	}
	
	@Test
	public void testReadByTxt2() throws Exception {
		Chemicals chemicals1, chemicals2;
		Chemical chemical3, chemical4;
		
		chemicals1 = Chemicals.readFromFile("data/Chemicals.txt");
		chemicals2 = Chemicals.readFromFile("data/Chemicals.xml");
		
		Assert.assertNotNull("Chemical list from txt file is null", chemicals1);
		Assert.assertNotNull("Chemical list form xml file is null", chemicals2);
		Assert.assertEquals("Chemical lists are not same size", chemicals1.size(), chemicals2.size());
		
		for (int i=0; i<chemicals1.size(); i++) {
			chemical3 = chemicals1.get(i);
			chemical4 = chemicals2.get(i);
			Assert.assertTrue("Chemicals lists are not equal", chemical3.equals(chemical4));
		}

	}
	
	@Test
	public void testStdTempVaporPressureCalculate() throws Exception {
		double tempK = 298.15; // standard temperature in K
		double ratio, maxRatio = 100.0;
		double vp, vpCalc;
		int cnt0=0, cnt1=0, cnt2=0, cnt3=0, cnt4=0, cnt5=0, cnt6=0, cnt7=0, cnt8=0, cnt9=0;
		
		Chemicals chemicals = Chemicals.readFromFile("data/Chemicals.xml");
		chemicals.correctVaporPressureConstants();
//		chemicals.filterForLiquidPhase(tempK);
		
		for (Chemical chemical: chemicals) {
			
			vpCalc = chemical.determineVaporPressure(tempK);
			vp = chemical.getVaporPressure(); // vapor pressure at standard temp
			
			cnt0++;
			if (!chemical.isLiquidPhase(tempK)) {
				cnt1++;
			} else if (chemical.isAntoineReady(tempK)){
				cnt2++;
				ratio = (vp>vpCalc ? vp/vpCalc : vpCalc/vp);
				if (ratio>maxRatio) {
					cnt3++;
//					System.out.println("Antoine vpCalc = "+vpCalc+", vp = "+vp+", "+chemical.getCAS());
				}
//				Assert.assertTrue("Vapor pressure is not calculate the same as on file, "+chemical.getCAS(), ratio<=maxRatio);
			} else if (chemical.haveCriticalParameters()){
				cnt4++;
				ratio = (vp>vpCalc ? vp/vpCalc : vpCalc/vp);
				if (ratio>maxRatio) {
					cnt5++;
//					System.out.println("LeeKesler vpCalc = "+vpCalc+", vp = "+vp+", "+chemical.getCAS());			
				}
//				Assert.assertTrue("Vapor pressure is not calculate the same as on file, "+chemical.getCAS(), ratio<=maxRatio);
			} else if (chemical.haveAntoineConstants()){
				cnt6++;
				ratio = (vp>vpCalc ? vp/vpCalc : vpCalc/vp);
				if (ratio>maxRatio) {
					cnt7++;
//					System.out.println("Antoine out-of-range vpCalc = "+vpCalc+", vp = "+vp+", "+chemical.getCAS()+", "+chemical.getAntoineTmin()+", "+chemical.getAntoineTmax());			
				}
//				Assert.assertTrue("Vapor pressure is not calculate the same as on file, "+chemical.getCAS(), ratio<=maxRatio);
			} else {
				cnt8++;
				ratio = (vp>vpCalc ? vp/vpCalc : vpCalc/vp);
				if (ratio>maxRatio) {
					cnt9++;
//					System.out.println("ClausiusClapeyron vpCalc = "+vpCalc+", vp = "+vp+", "+chemical.getCAS());			
				}
			}
			
		}
//		System.out.println("Std Temp, maxRatio = "+maxRatio+", total = "+cnt0+", non-liquid phase = "+cnt1);
		Assert.assertTrue("Antoine ratio > "+maxRatio+", more than "+cnt3+" of "+cnt2, cnt3<=3);
		Assert.assertTrue("LeeKesler ratio > "+maxRatio+", more than "+cnt5+" of "+cnt4, cnt5<=108);
		Assert.assertTrue("Antoine out-of-range ratio > "+maxRatio+", more than "+cnt7+" of "+cnt6, cnt7<=1);
		Assert.assertTrue("ClausiusClapeyron ratio > "+maxRatio+", more than "+cnt9+" of "+cnt8, cnt9<=0);
		
	}
	
}
