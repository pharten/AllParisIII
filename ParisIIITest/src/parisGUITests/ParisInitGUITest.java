/**
 * 
 */
package parisGUITests;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
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
public class ParisInitGUITest {
	
	@Test
	public void testParisInitGUIImage() {
		
		String filename = "eiffel-tower.gif";
		
		Assert.assertNotNull(filename+" file not found", ParisWork.class.getResource("/data/"+filename));

		Image eiffel = SWTResourceManager.getImage(ParisWork.class.getResource("/data/eiffel-tower.gif").getFile());
		Assert.assertNotNull("Eiffel Tower image not found",eiffel);
		
		Shell shell = new Shell();
		shell.setImage(eiffel);
		Image eiffel2 = shell.getImage();
		
		Assert.assertSame(eiffel, eiffel2);

	}
	
	@Test
	public void testParisInitGUITechSupport() {
		
		Shell shell = new Shell();
		TechSupport techSupport = new TechSupport(shell);
		techSupport.start();
		techSupport.getOkButton().notifyListeners(SWT.Selection, new Event());
		Assert.assertEquals("Result does not come from OK Button", techSupport.getResult(), SWT.OK);
//		techSupport.stop();
		
	}
	
	@Test
	public void testParisInitGUIDevelopers() {
		
		Shell shell = new Shell();
		Developers developers = new Developers(shell);
		developers.start();
		developers.getOkButton().notifyListeners(SWT.Selection, new Event());
		Assert.assertEquals("Result does not come from OK Button", developers.getResult(), SWT.OK);
//		developers.stop();
		
	}
	
	@Test
	public void testParisInitGUI() {

		Shell shell = new Shell();
		
		ParisInit parisInit = new ParisInit(shell);
		parisInit.start();
		
		parisInit.getStartButton().notifyListeners(SWT.Selection, new Event());
		Assert.assertEquals("Result does not come from startButton", parisInit.getResult(), SWT.OK);
		parisInit.stop();

		parisInit = new ParisInit(shell);
		parisInit.start();
		
		parisInit.getCancelButton().notifyListeners(SWT.Selection, new Event());
		Assert.assertEquals("Result does not equal exitButton", parisInit.getResult(), SWT.CANCEL);
		parisInit.stop();
		
		parisInit = new ParisInit(shell);
		parisInit.start();
		
		parisInit.getTechSupportButton().notifyListeners(SWT.Selection, new Event());
		parisInit.getTechSupport().getOkButton().notifyListeners(SWT.Selection, new Event());
		Assert.assertEquals("TechSupport result does not equal OkButton", parisInit.getTechSupport().getResult(), SWT.OK);
		
		parisInit.getDevelopersButton().notifyListeners(SWT.Selection, new Event());
		parisInit.getDeveloper().getOkButton().notifyListeners(SWT.Selection, new Event());
		Assert.assertEquals("Developer result does not equal OkButton", parisInit.getDeveloper().getResult(), SWT.OK);

		parisInit.getCancelButton().notifyListeners(SWT.Selection, new Event());
		Assert.assertEquals("Result does not equal exitButton", parisInit.getResult(), SWT.CANCEL);
		parisInit.stop();

	}



}
