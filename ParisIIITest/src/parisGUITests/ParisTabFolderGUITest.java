/**
 * 
 */
package parisGUITests;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.junit.Assert;
import org.junit.Test;

import parisInit.Developers;
import parisInit.ParisInit;
import parisInit.TechSupport;
import parisWork.ParisWork;
import parisWork.Screen;
import parisWork.Screen0;
import parisWork.Screen1;
import parisWork.Screen2;
import parisWork.Screen3;
import parisWork.Screen4;

/**
 * @author pharten
 *
 */
public class ParisTabFolderGUITest {
	
	@Test
	public void testParisTabFolderGUIInit() {

		ParisWork window = new ParisWork();
		Shell shell = window.getShell();
		
		Image eiffel = SWTResourceManager.getImage(ParisWork.class.getResource("/data/eiffel-tower.gif").getFile());
		shell.setImage(eiffel);
		
		ParisInit parisInit = new ParisInit(shell);
		
		parisInit.getStartButton().notifyListeners(SWT.Selection, new Event());
		Assert.assertEquals("Result does not come from startButton", parisInit.getResult(), SWT.OK);

		if (parisInit.getResult()==SWT.OK) {
			
			try {
				window.start();
				
				TabFolder tabFolder = window.getTabFolder();
				Assert.assertNotNull("tabFolder should not be null", tabFolder);
				Assert.assertEquals(5, tabFolder.getItemCount());
				
				for (int i=0; i<tabFolder.getItemCount(); i++) {
					tabFolder.setSelection(i);
					tabFolder.notifyListeners(SWT.Selection, new Event());
					Assert.assertEquals(i,tabFolder.getSelectionIndex());
					Screen screen = (Screen)tabFolder.getItem(1).getControl();
					Assert.assertNotNull("screen"+i+" should not be null", screen);
				}

				window.getShell().dispose();
				
				window.stop();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	@Test
	public void testParisTabFolderGUI_NoMixture() {

		ParisWork window = new ParisWork();
		Shell shell = window.getShell();
		
		Image eiffel = SWTResourceManager.getImage(ParisWork.class.getResource("/data/eiffel-tower.gif").getFile());
		shell.setImage(eiffel);
		
		ParisInit parisInit = new ParisInit(shell);
		
		parisInit.getStartButton().notifyListeners(SWT.Selection, new Event());
		Assert.assertEquals("Result does not come from startButton", parisInit.getResult(), SWT.OK);

		if (parisInit.getResult()==SWT.OK) {
			
			try {
				window.start();
				
				TabFolder tabFolder = window.getTabFolder();
				Assert.assertNotNull("tabFolder should not be null", tabFolder);
				
				Assert.assertEquals(0,tabFolder.getSelectionIndex());
				
				Screen0 screen0 = (Screen0)tabFolder.getItem(0).getControl();
				Assert.assertNotNull("screen0 should not be null", screen0);
				
				tabFolder.setSelection(1);
				tabFolder.notifyListeners(SWT.Selection, new Event());
				Assert.assertEquals(1,tabFolder.getSelectionIndex());
				
				Screen1 screen1 = (Screen1)tabFolder.getItem(1).getControl();
				Assert.assertNotNull("screen1 should not be null", screen1);

				tabFolder.setSelection(2);
				tabFolder.notifyListeners(SWT.Selection, new Event());
				Assert.assertEquals(2,tabFolder.getSelectionIndex());
				
				Screen2 screen2 = (Screen2)tabFolder.getItem(2).getControl();
				Assert.assertNotNull("screen2 should not be null", screen2);
				Text[][] textArray = screen2.getTextArray();
				
				Assert.assertNotNull("Screen2 textArray should not be null",textArray);
				Assert.assertEquals("Screen2 textArray length is wrong", 10, textArray.length);
				Assert.assertNotNull("Screen2 textArray[0] should not be null",textArray[0]);
				Assert.assertEquals("Screen2 textArray[0] length is wrong", 5, textArray[0].length);

				Text text1 = textArray[0][0];
				
				Assert.assertEquals("Screen2 textArray[0][0] should be blank", "", text1.getText());
				Assert.assertFalse("Screen2 textArray should not be Editable", text1.getEditable());
				
				tabFolder.setSelection(3);
				tabFolder.notifyListeners(SWT.Selection, new Event());
				Assert.assertEquals(3,tabFolder.getSelectionIndex());
				
				Screen3 screen3 = (Screen3) tabFolder.getItem(3).getControl();
				Assert.assertNotNull("screen3 should not be null", screen3);
				textArray = screen3.getTextArray();
				
				Assert.assertNotNull("Screen3 textArray should not be null",textArray);
				Assert.assertEquals("Screen3 textArray length is wrong", 10, textArray.length);
				Assert.assertNotNull("Screen3 textArray[0] should not be null",textArray[0]);
				Assert.assertEquals("Screen3 textArray[0] length is wrong", 5, textArray[0].length);
				
				text1 = textArray[0][0];
				
				Assert.assertEquals("Screen3 textArray[0][0] should be blank", "", text1.getText());
				Assert.assertFalse("Screen3 textArray should not be Editable", text1.getEditable());
				
				tabFolder.setSelection(4);
				tabFolder.notifyListeners(SWT.Selection, new Event());
				Assert.assertEquals(4,tabFolder.getSelectionIndex());
				
				Screen4 screen4 = (Screen4) tabFolder.getItem(4).getControl();
				Assert.assertNotNull("screen4 should not be null", screen4);
				
				window.getShell().dispose();
				
				window.stop();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
