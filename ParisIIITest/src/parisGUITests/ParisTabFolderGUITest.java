/**
 * 
 */
package parisGUITests;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.wb.swt.SWTResourceManager;

import org.junit.Assert;
import org.junit.Test;

import parisInit.Developers;
import parisInit.ParisInit;
import parisInit.TechSupport;
import parisWork.ParisWork;

/**
 * @author pharten
 *
 */
public class ParisTabFolderGUITest {
	
	@Test
	public void testParisFolderFolderGUIInit() {

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
				Assert.assertNotNull(tabFolder);;
				Assert.assertEquals(5,tabFolder.getItemCount());
				
				for (int i=1; i<tabFolder.getItemCount(); i++) {
					tabFolder.setSelection(i);
					tabFolder.notifyListeners(SWT.Selection, new Event());
					Assert.assertEquals(i,tabFolder.getSelectionIndex());
				}
				
				tabFolder.setSelection(0);
				tabFolder.notifyListeners(SWT.Selection, new Event());
				Assert.assertEquals(0,tabFolder.getSelectionIndex());

				window.getShell().dispose();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
