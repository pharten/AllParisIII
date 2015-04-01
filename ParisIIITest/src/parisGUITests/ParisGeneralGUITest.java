package parisGUITests;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.junit.Assert;
import org.junit.Test;

import parisWork.ParisWork;
import parisWork.Screen0;
import parisWork.Screen1;
import parisWork.Screen2;
import parisWork.Screen3;

public class ParisGeneralGUITest {

	@Test
	public void testParisGUI_SingleChemicalMixture() {

		ParisWork window = new ParisWork();
		Shell shell = window.getShell();
		
		Image eiffel = SWTResourceManager.getImage(ParisWork.class.getResource("/data/eiffel-tower.gif").getFile());
		shell.setImage(eiffel);
		
		try {
			window.start();

			TabFolder tabFolder = window.getTabFolder();
			Assert.assertNotNull(tabFolder);

			Assert.assertEquals(0,tabFolder.getSelectionIndex());
			
			Screen0 screen0 = (Screen0)tabFolder.getItem(0).getControl();
			Assert.assertNotNull("screen0 should not be null",screen0);
			List nameList = screen0.getList();
			Assert.assertNotNull("screen0.nameList should not be null",nameList);
			Button btn1 = screen0.getBtn1();
			Assert.assertNotNull("screen0.btn_1 should not be null",btn1);
			Table table = screen0.getTable();
			Assert.assertNotNull("screen0.table should not be null",table);
			
			nameList.setSelection(0);
			btn1.notifyListeners(SWT.Selection, new Event());
			
			Assert.assertTrue("List name is not the same as Mixture name", nameList.getItem(0).contentEquals(table.getItem(0).getText(0)));
			Assert.assertTrue("Mixture Wt% is not 100.0", table.getItem(0).getText(1).contentEquals("100.0"));

			tabFolder.setSelection(1);
			tabFolder.notifyListeners(SWT.Selection, new Event());
			Assert.assertEquals(1,tabFolder.getSelectionIndex());
			
			Screen1 screen1 = (Screen1)tabFolder.getItem(1).getControl();
			Assert.assertNotNull("screen1 should not be null",screen0);
			Text[] chemicalName = screen1.getChemicalName();
			Assert.assertNotNull("screen1.chemicalName should not be null",chemicalName);
			Text[] chemicalWght = screen1.getChemicalWght();
			Assert.assertNotNull("screen1.chemicalWght should not be null",chemicalWght);
			
			Assert.assertTrue("Screen0 Chemical name is not the same as Screen1 Chemical name", table.getItem(0).getText(0).contentEquals(chemicalName[0].getText()));
			Assert.assertTrue("Screen0 Wt% is not the same as Screen1 Wt%", table.getItem(0).getText(1).contentEquals(chemicalWght[0].getText()));

			tabFolder.setSelection(2);
			tabFolder.notifyListeners(SWT.Selection, new Event());
			Assert.assertEquals(2,tabFolder.getSelectionIndex());

			Screen2 screen2 = (Screen2)tabFolder.getItem(2).getControl();
			Text[][] textArray = screen2.getTextArray();

			Assert.assertNotNull("Screen2 textArray should not be null",textArray);
			Assert.assertEquals("Screen2 textArray length is wrong", 10, textArray.length);
			Assert.assertNotNull("Screen2 textArray[0] should not be null",textArray[0]);
			Assert.assertEquals("Screen2 textArray[0] length is wrong", 5, textArray[0].length);

			Text text1 = textArray[0][0];

			Assert.assertEquals("Screen2 textArray[0][0] is wrong", "11.0", text1.getText());

			tabFolder.setSelection(3);
			tabFolder.notifyListeners(SWT.Selection, new Event());
			Assert.assertEquals(3,tabFolder.getSelectionIndex());

			Screen3 screen3 = (Screen3) tabFolder.getItem(3).getControl();
			textArray = screen3.getTextArray();

			Assert.assertNotNull("Screen3 textArray should not be null",textArray);
			Assert.assertEquals("Screen3 textArray length is wrong", 10, textArray.length);
			Assert.assertNotNull("Screen3 textArray[0] should not be null",textArray[0]);
			Assert.assertEquals("Screen3 textArray[0] length is wrong", 5, textArray[0].length);

			text1 = textArray[0][0];

			Assert.assertEquals("Screen3 textArray[0][0] is wrong", "25.0", text1.getText());

			window.getShell().dispose();

			window.stop();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

