package parisGUITests;

import static org.junit.Assert.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.wb.swt.SWTResourceManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import parisInit.ParisInit;
import parisInit.States;
import parisWork.Chemicals;
import parisWork.ParisWork;
import parisWork.Reference;
import parisWork.Screen;
import parisWork.Screen0;

public class ParisReferenceGUITest {
	
	static ParisWork window = null;
	Screen0 screen0 = null;

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		window.getShell().dispose();
		
	}

	@Before
	public void setUp() throws Exception {
		
		try {
			window = new ParisWork();
		} catch (Exception e) {
			e.printStackTrace();
		}

//		window = new ParisWork();
		Assert.assertNotNull(window);
		
		Shell shell = window.getShell();
		Assert.assertNotNull(shell);
		
		window.start();
			
		TabFolder tabFolder = window.getTabFolder();
		Assert.assertNotNull(tabFolder);;
		Assert.assertEquals(5,tabFolder.getItemCount());
			
		tabFolder.setSelection(0);
		tabFolder.notifyListeners(SWT.Selection, new Event());
		
		int index = tabFolder.getSelectionIndex();
		Assert.assertEquals(0,index);
		
		screen0 = (Screen0)(tabFolder.getTabList()[index]);
		Assert.assertNotNull(screen0);
		
		screen0.begin();
		
	}

	@After
	public void tearDown() throws Exception {
		
		Assert.assertTrue(screen0.finishUp());
		
		window.getShell().dispose();
		
		window.stop();

	}

	@Test
	public void testReference() {
		
		Button btn = screen0.getBtn3();
		Assert.assertNotNull(btn);
		
		btn.notifyListeners(SWT.Selection, new Event());
		
		List list = screen0.getList();
		Assert.assertNotNull(list);
		
		list.setSelection(2);
		btn.notifyListeners(SWT.Selection, new Event());
		
		Reference reference = screen0.getReference();
		Assert.assertNotNull(reference);
		
		Button btnOK = reference.getBtnOK();
		Assert.assertNotNull(btnOK);
		
		btnOK.notifyListeners(SWT.Selection, new Event());

	}

//	@Test
//	public void testOpen() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDialogShell() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testDialogShellInt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCheckSubclass() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCheckParent() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCheckStyle() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testError() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetParent() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetStyle() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetText() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testSetText() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testObject() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetClass() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testHashCode() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testEquals() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testClone() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testToString() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testNotify() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testNotifyAll() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testWaitLong() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testWaitLongInt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testWait() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFinalize() {
//		fail("Not yet implemented");
//	}

}
