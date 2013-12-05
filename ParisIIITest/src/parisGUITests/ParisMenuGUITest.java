package parisGUITests;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.junit.Assert;
import org.junit.Test;

import parisWork.ParisWork;

public class ParisMenuGUITest {
	
//	static ParisWork window = new ParisWork();
	
	@Test
	public void testParisMenuBarGUI() {
		
		try {
			ParisWork window = new ParisWork();
			
			window.start();
			
			Menu menuBar = window.getShell().getMenuBar();
			Assert.assertNotNull(menuBar);
			Assert.assertEquals(4, menuBar.getItemCount());				

			assertMenuBarItem(menuBar.getItem(0), "File");
			assertMenuBarItem(menuBar.getItem(1), "Edit");
			assertMenuBarItem(menuBar.getItem(2), "Action");
			assertMenuBarItem(menuBar.getItem(3), "Help");
			
			window.getShell().dispose();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void assertMenuBarItem(MenuItem menuItem, String itemName) {
		Assert.assertNotNull(menuItem);
		Assert.assertEquals(SWT.CASCADE, menuItem.getStyle());
		Assert.assertTrue(menuItem.isEnabled());
		Assert.assertTrue(menuItem.getText().matches(itemName));
		Assert.assertNotNull(menuItem.getMenu());
	}
	
	@Test
	public void testParisFileMenuGUI() {

		try {
			ParisWork window = new ParisWork();
			
			window.start();		
			
			Menu fileMenu = window.getShell().getMenuBar().getItem(0).getMenu();
			
			Assert.assertEquals(5+2, fileMenu.getItemCount()); // include separators
			
			assertMenuItem(fileMenu.getItem(0), true, "Open", SWT.ALT+'O');		
			assertMenuItem(fileMenu.getItem(1), false, "Save", SWT.ALT+'S');	
			assertMenuItem(fileMenu.getItem(2), true, "Save As", SWT.ALT+'A');	
			assertMenuItem(fileMenu.getItem(3), false, "Close", SWT.ALT+'W');
			Assert.assertEquals(SWT.SEPARATOR, fileMenu.getItem(4).getStyle());
//			assertMenuItem(fileMenu.getItem(5), true, "Print", SWT.ALT+'P');
			Assert.assertEquals(SWT.SEPARATOR, fileMenu.getItem(5).getStyle());
			assertMenuItem(fileMenu.getItem(6), true, "Exit", SWT.ALT+'X');
			
			window.getShell().dispose();
			
//			fileItem.setSelection(false);
//			fileItem.getMenu().notifyListeners(SWT.Selection, new Event());

			//				tabFolder.notifyListeners(SWT.Selection, new Event());
			//				Assert.assertEquals(0,tabFolder.getSelectionIndex());


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void testParisEditMenuGUI() {
		
		try {
			ParisWork window = new ParisWork();
			
			window.start();		
			
			Menu editMenu = window.getShell().getMenuBar().getItem(1).getMenu();
			
			Assert.assertEquals(7, editMenu.getItemCount()); // include separators
			
			assertMenuItem(editMenu.getItem(0), false, "Undo", SWT.CTRL+'Z');
			Assert.assertEquals(SWT.SEPARATOR, editMenu.getItem(1).getStyle());
			assertMenuItem(editMenu.getItem(2), false, "Cut", SWT.CTRL+'X');	
			assertMenuItem(editMenu.getItem(3), false, "Copy", SWT.CTRL+'C');
			assertMenuItem(editMenu.getItem(4), false, "Paste", SWT.CTRL+'V');
			Assert.assertEquals(SWT.SEPARATOR, editMenu.getItem(5).getStyle());
			assertMenuItem(editMenu.getItem(6), false, "Select All", SWT.CTRL+'A');
			
			window.getShell().dispose();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void testParisActionMenuGUI() {
		
		try {
			ParisWork window = new ParisWork();

			window.start();		
			
			Menu actionMenu = window.getShell().getMenuBar().getItem(2).getMenu();
			
//			tabFolder.notifyListeners(SWT.Selection, new Event());
//			Assert.assertEquals(0,tabFolder.getSelectionIndex());
			
			Assert.assertEquals(3, actionMenu.getItemCount()); // include separators
			
			assertMenuItem(actionMenu.getItem(0), true, "Update", SWT.CTRL+'U');
			assertMenuItem(actionMenu.getItem(1), false, "Find Mixtures", SWT.CTRL+'F');	
			assertMenuItem(actionMenu.getItem(2), false, "Stop Mixtures", SWT.CTRL+'S');

			window.getShell().dispose();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void testParisHelpMenuGUI() {

		try {
			ParisWork window = new ParisWork();
			
			window.start();		
			
			Menu helpMenu = window.getShell().getMenuBar().getItem(3).getMenu();
			
			Assert.assertEquals(2+1, helpMenu.getItemCount()); // include separators
			
//			assertMenuItem(helpMenu.getItem(0), true, "Contents", 0);
//			assertMenuItem(helpMenu.getItem(1), true, "Search", 0);	
			assertMenuItem(helpMenu.getItem(0), true, "User's Guide", 0);
			Assert.assertEquals(SWT.SEPARATOR, helpMenu.getItem(1).getStyle());
			assertMenuItem(helpMenu.getItem(2), true, "About", 0);
			
//			helpMenu.getItem(3).notifyListeners(SWT.Selection, new Event());;

			window.getShell().dispose();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void assertMenuItem(MenuItem menuItem, boolean isEnabled, String itemName, int accelerator) {
		Assert.assertNotNull(menuItem);
		Assert.assertEquals(isEnabled, menuItem.isEnabled());
		Assert.assertTrue(menuItem.getText().contains(itemName));
		Assert.assertEquals(accelerator, menuItem.getAccelerator());
	}

}
