package parisWork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;

import parisInit.ParisInit;
import parisInit.State;
import parisInit.States;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.events.MenuEvent;

import edu.stanford.ejalbert.BrowserLauncher;

public class ParisWork extends java.lang.Object implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int bestMixturesPrintMax = 100;

	private static Display display = Display.getDefault();
	private static Clipboard clipboard = new Clipboard(display);
	private Shell shell = new Shell(display);
//	private static Help helpSystem = new Help(new String[] { "-eclipsehome", "C:\\PaulHartenSpace\\Juno" });
	private TabFolder tabFolder;
	private MenuItem saveItem, closeItem;
	private MenuItem undoItem, cutItem, copyItem, pasteItem, selectAllItem;
	protected static States states = null;
	protected static Chemicals allChemicals = null;
	protected static Chemicals chemicals = null;
	protected static Replacements replacements = null;
	protected static Vector<Mixture> bestMixtures = null;
	private static FutureTask concurrentTask = null;
//	protected static Logger logger = Logger.getLogger(ParisWork.class);
	protected static String strUserFolder=System.getProperty("user.home")+File.separator+".ParisIII"+File.separator;
	int previousIndex = 0;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		//		Display display = Display.getDefault();
		//		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
		//			public void run() {
		//				try {
		//					ParisWork window = new ParisWork();
		//					ParisInit parisInit = new ParisInit(window.shell);
		//					if (parisInit.open()==IDialogConstants.OK_ID) {
		//						window.open();
		//					}
		//				} catch (Exception e) {
		//					e.printStackTrace();
		//				}
		//			}
		//		});
		try {
			ParisWork window = new ParisWork();

			Image eiffel = new Image(display, window.getClass().getClassLoader().getResourceAsStream("data/eiffel-tower.gif"));
			
			window.shell.setImage(eiffel);
			
			ParisInit parisInit = new ParisInit(window.shell);
			
			concurrentTask = new FutureTask<Chemicals>(new ChemicalsConcurrent("data/Chemicals.xml"));
			concurrentTask.run();

			if (parisInit.open()==SWT.OK) {
				window.open();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Open the window.
	 */
	public void open() throws Exception {
		start();
		stop();
	}
	
	public void start() throws Exception {
		
		states = States.readFromFile("data/defaultStates.xml");
		State state = states.getElementBySystemName("default");
		if (state==null) throw new Exception("default state not found");
//		states.writeByXML(getClass().getResource("/data/defaultStates.xml").getFile());

		if (concurrentTask!=null) {
			allChemicals = (Chemicals) concurrentTask.get();
		} else {
//			Chemicals.addWaterSolvent("data/Chemicals.txt");  // test this before using it
			allChemicals = Chemicals.readFromFile("data/Chemicals.xml");
		}
		allChemicals.correctVaporPressureConstants();
		//		allChemicals.convertFromCommonToSI();
		//		allChemicals.addSynListDataFromFile("./src/data/synlist.dat");
		//		allChemicals.addWarScoreDataFromFile("./src/data/war_score.dat");
		//		allChemicals.addPropertyDataFromFile("./src/data/prop_25c.dat");
//				allChemicals.writeByXML("data/Chemicals2.xml");

		createContents();
		shell.open();
		shell.layout();

//		helpSystem.start();
		
		begin();

	}
	
	public void stop() throws Exception {

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
//		display.dispose();

	}

	/**
	 * Create contents of the window.
	 * @throws Exception 
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() throws Exception {

		//		shell = new Shell();
		shell.setSize(750, 600);
		shell.setText("PARIS III");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmSubFile = new MenuItem(menu, SWT.CASCADE);
		mntmSubFile.setText("File");

		Menu fileMenu = new Menu(mntmSubFile);
		mntmSubFile.setMenu(fileMenu);

		MenuItem openItem = new MenuItem(fileMenu, SWT.PUSH);
		openItem.setText("Open\tALT+O");
		openItem.setAccelerator(SWT.ALT + 'O');
		openItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
//					if (!finishUp()) return;
					FileDialog fd = new FileDialog(shell, SWT.OPEN);
					fd.setText("Open");
					String filterPath = fd.getFilterPath();
					if (filterPath==null || filterPath.length()==0) {
						fd.setFilterPath("C:/");
					} else {
						fd.setFilterPath(filterPath);
					}
					String[] filterExt = { "*.xml", "*.txt", "*.doc", "*.rtf", "*.*" };
					fd.setFilterExtensions(filterExt);
					String selected = fd.open();
					if (selected != null) {
						int openScreen = states.getActiveState().getOpenScreen();
						State state = State.readFromFile(selected);					
						state.setOpenScreen(openScreen);
						states.addToList(state);
						states.setActiveState(state);
						saveItem.setEnabled(true);
						closeItem.setEnabled(true);
						restore();
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		saveItem = new MenuItem(fileMenu, SWT.PUSH);
		saveItem.setText("Save\tALT+S");
		saveItem.setAccelerator(SWT.ALT + 'S');
		saveItem.setEnabled(states.size()!=1);
		saveItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
					if (!finishUp()) return;
					State state = states.getActiveState();
					state.setSingle(true);
					String selected = state.getFileName();
					if (selected!=null && new File(selected).exists()) {
						state.writeToFile();
					} else {
						FileDialog fd = new FileDialog(shell, SWT.SAVE);
						fd.setText("Save");
						fd.setFilterPath(state.getFileName());
						String[] filterExt = { "*.xml", "*.txt", "*.doc", "*.rtf", "*.*" };
						fd.setFilterExtensions(filterExt);
						selected = fd.open();
						if (selected != null) {
							state.setFileName(selected);
							state.writeToFile();
						}
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		MenuItem saveAsItem = new MenuItem(fileMenu, SWT.NONE);
		saveAsItem.setText("Save As...\tALT+A");
		saveAsItem.setAccelerator(SWT.ALT + 'A');
		saveAsItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
					if (!finishUp()) return;
					State state = states.getActiveState();
					state.setSingle(true);
					FileDialog fd = new FileDialog(shell, SWT.SAVE);
					fd.setText("Save As...");
					fd.setFilterPath(state.getFileName());
					fd.setFileName(state.getFileName());
					String[] filterExt = { "*.xml", "*.txt", "*.doc", "*.rtf", "*.*" };
					fd.setFilterExtensions(filterExt);
					String selected = fd.open();
					if (selected != null) {
						state.setFileName(selected);
						state.writeToFile();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		closeItem = new MenuItem(fileMenu, SWT.NONE);
		closeItem.setText("Close\tALT+W");
		closeItem.setAccelerator(SWT.ALT + 'W');
		closeItem.setEnabled(states.size()!=1);
		closeItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
					if (!finishUp()) return;
					State state = states.getActiveState();
					String selected = state.getFileName();
					if (selected!=null && new File(selected).exists()) {
						MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
						messageBox.setMessage("Do you want to save your state "+state.getSystemName()+" in its file?");
						int response = messageBox.open();
						if (response != SWT.CANCEL) {
							messageBox.setText("Closing Application");
							if (response == SWT.YES) {
								state.writeToFile();
							}
							int openScreen = state.getOpenScreen();
							states.remove(state);
							states.setActiveState(states.firstElement());
							states.getActiveState().setOpenScreen(openScreen);
							if (states.size()==1) {
								saveItem.setEnabled(false);
								closeItem.setEnabled(false);
							}
							restore();
						}
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		new MenuItem(fileMenu, SWT.SEPARATOR);

		MenuItem printItem = new MenuItem(fileMenu, SWT.NONE);
		printItem.setText("Print\tALT+P");
		printItem.setAccelerator(SWT.ALT + 'P');
		printItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
					if (!finishUp()) return;
					State state = states.getActiveState();
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
					messageBox.setMessage("Do you want to print "+state.getSystemName()+" substitutes?");
					int response = messageBox.open();
					if (response != SWT.CANCEL) {
						messageBox.setText("Printing Substitutes");
						if (response == SWT.YES) {
							Text t = new Text(shell, SWT.BORDER | SWT.MULTI);
							t.setText("A PrintDialog Example");
							PrintDialog printDialog = new PrintDialog(shell, SWT.NONE);
							printDialog.setText("Print");

							int[] line = {0};
							int[] page = {1};
							
							PrinterData printerData = printDialog.open();
							if (printerData != null) {
								switch (printerData.scope) {
									case PrinterData.ALL_PAGES:
										printerData.startPage = 1;
										printerData.endPage = Integer.MAX_VALUE;
										break;
									case PrinterData.PAGE_RANGE:
									case PrinterData.SELECTION:
										break;
									default:
										throw new Exception("Incorrect Printer Scope");
								}
								Printer p = new Printer(printerData);
								p.startJob("PrintJob"); // name of PDF file
								
								if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) p.startPage();
								
								state.print(p, line, page);
								
								Rectangle trim = p.computeTrim(0, 0, 0, 0);
								Point dpi = p.getDPI();
								int leftMargin = dpi.x + trim.x;
								int topMargin = dpi.y / 2 + trim.y;
								GC gc = new GC(p);
								FontMetrics metrics = gc.getFontMetrics();
								int lineHeight = metrics.getHeight();
								int linesPerPage = (p.getBounds().height-2*topMargin)/lineHeight;
	
								if (line[0]>=linesPerPage-2) { // page increases
									if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) p.endPage();
									line[0]=0;
									page[0]++;
									if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) p.startPage();
								} else {
									line[0]++; // include blank line
								}
								
								line[0]++;
								if (printerData.startPage<=page[0] && page[0]<=printerData.endPage) {
									String output = "Greener Replacements:";
									gc.drawString(output, leftMargin, topMargin+(line[0]-1)*lineHeight);
								}
								gc.dispose();
								
								if (bestMixtures!=null) {
									for (int i=0; i<bestMixturesPrintMax; i++) {
										bestMixtures.get(i).print(p, i+1, line, page);
									}
								}
								
								if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) p.endPage();
								
								p.endJob();
								p.dispose();
							}

						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		new MenuItem(fileMenu, SWT.SEPARATOR);

		MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
		exitItem.setText("Exit\tALT+X");
		exitItem.setAccelerator(SWT.ALT + 'X');
		exitItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					MessageBox messageBox = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
					messageBox.setMessage("Do you want to save all your states before exiting?");
					int response = messageBox.open();
					if (response != SWT.CANCEL) {
						messageBox.setText("Exiting Application");
						if (response == SWT.YES) {
							String filename = getClass().getResource("/data/defaultStates.xml").getFile();
							states.writeByXML(filename);
						}
						clipboard.dispose();
						display.dispose();
//						helpSystem.shutdown();
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		MenuItem mntmSubEdit = new MenuItem(menu, SWT.CASCADE);
		mntmSubEdit.setText("Edit");

		Menu editMenu = new Menu(mntmSubEdit);
		mntmSubEdit.setMenu(editMenu);

		editMenu.addMenuListener (new MenuListener () {
			@Override
			public void menuHidden(MenuEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void menuShown(MenuEvent arg0) {
				
				Control control = display.getFocusControl();
				if (!Text.class.isInstance(control)) {
	         		undoItem.setEnabled(false);
					cutItem.setEnabled(false);
					copyItem.setEnabled(false);
					pasteItem.setEnabled(false);
					selectAllItem.setEnabled(false);
					return;
				} else {
					Text text = (Text)control;
					if (text.isEnabled()) {
		         		undoItem.setEnabled(text.getData()!=null);
						cutItem.setEnabled(text.getSelectionCount()!=0);
						copyItem.setEnabled(text.getSelectionCount()!=0);
						selectAllItem.setEnabled(text.getCharCount()!=0);
					}
	         		Transfer transfer = TextTransfer.getInstance();
	         		Object clips = clipboard.getContents(transfer);
	         		pasteItem.setEnabled(clips!=null);
				}	

			}
		});
		
		undoItem = new MenuItem(editMenu, SWT.PUSH);
		undoItem.setText("Undo\tCTRL+Z");
		undoItem.setAccelerator(SWT.CTRL + 'Z');
		undoItem.setEnabled(false);
		undoItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Text text = (Text)display.getFocusControl();
				if (text.isEnabled()) {
					String temp = text.getText();
					int tempCaret = text.getCaretPosition();
					int tempCount = text.getSelectionCount();
					text.setText((String)text.getData());
					text.setSelection((Integer)text.getData("start"), (Integer)text.getData("end"));
					text.showSelection();
					text.setData(temp);
					if (tempCaret == text.getCaretPosition()) {
						text.setData("start",tempCaret);
						text.setData("end",tempCaret+tempCount);
					} else {
						text.setData("start",tempCaret-tempCount);
						text.setData("end",tempCaret);
					}
				}
			}
		});
		
		new MenuItem(editMenu, SWT.SEPARATOR);
		
		cutItem = new MenuItem(editMenu, SWT.PUSH);
		cutItem.setText("Cut\tCTRL+X");
		cutItem.setAccelerator(SWT.CTRL + 'X');
		cutItem.setEnabled(false);
		cutItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Text text = (Text)display.getFocusControl();
				if (text.isEnabled()) {
					text.setData(text.getText());
					int tempCount = text.getSelectionCount();
//					Object[] clips = {text.getText()};
//	         		Transfer[] transfer = {TextTransfer.getInstance()};
//	         		clipboard.setContents(clips, transfer);
					text.cut();
					text.setData("start", text.getCaretPosition());
					text.setData("end", text.getCaretPosition()+tempCount);
				}
			}
		});

		copyItem = new MenuItem(editMenu, SWT.PUSH);
		copyItem.setText("Copy\tCTRL+C");
		copyItem.setAccelerator(SWT.CTRL + 'C');
		copyItem.setEnabled(false);
		copyItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Text text = (Text)display.getFocusControl();
				if (text.isEnabled()) {
					text.copy();
				}
			}
		});

		pasteItem = new MenuItem(editMenu, SWT.PUSH);
		pasteItem.setText("Paste\tCTRL+V");
		pasteItem.setAccelerator(SWT.CTRL + 'V');
		pasteItem.setEnabled(false);
		pasteItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
//         		Transfer transfer = TextTransfer.getInstance();
//         		Object clips = clipboard.getContents(transfer);
				Text text = (Text)display.getFocusControl();
				if (text.isEnabled()) {
					text.setData(text.getText());
					int tempCaret = text.getCaretPosition();
					int tempCount = text.getSelectionCount();
					text.paste();
				    if (tempCaret == text.getCaretPosition()) {
				    	text.setData("start",tempCaret);
				    	text.setData("end",tempCaret+tempCount);
				    } else {
				    	text.setData("start",tempCaret-tempCount);
				    	text.setData("end",tempCaret);
				    }
				}
			}
		});
		
		new MenuItem(editMenu, SWT.SEPARATOR);
		
		selectAllItem = new MenuItem(editMenu, SWT.PUSH);
		selectAllItem.setText("Select All\tCTRL+A");
		selectAllItem.setAccelerator(SWT.CTRL + 'A');
		selectAllItem.setEnabled(false);
		selectAllItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Text text = (Text)display.getFocusControl();
				if (text.isEnabled()) {
					text.selectAll();
				}
			}
		});
		
		MenuItem mntmSubAction = new MenuItem(menu, SWT.CASCADE);
		mntmSubAction.setText("Action");
		
		Menu actionMenu = new Menu(mntmSubAction);
		mntmSubAction.setMenu(actionMenu);
		
		actionMenu.addMenuListener (new MenuListener () {
			@Override
			public void menuHidden(MenuEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void menuShown(MenuEvent arg0) {
				Menu menu = (Menu)arg0.widget;
				int tabIndex = tabFolder.getSelectionIndex();
				menu.getItem(0).setEnabled(tabIndex==2 || tabIndex==3 || tabIndex==4);
				if (tabIndex==4) {
					boolean mixtureIsRunning = ((Screen4)(tabFolder.getTabList()[4])).isMixtureRunning();
					menu.getItem(0).setEnabled(!mixtureIsRunning);
					menu.getItem(1).setEnabled(!mixtureIsRunning);
					menu.getItem(2).setEnabled(mixtureIsRunning);
				} else {
					menu.getItem(0).setEnabled(true);
					menu.getItem(1).setEnabled(false);
					menu.getItem(2).setEnabled(false);
				}
			}
		});
		
		
		MenuItem mntmUpdate = new MenuItem(actionMenu, SWT.PUSH);
		mntmUpdate.setText("Update\tCTRL+U");
		mntmUpdate.setAccelerator(SWT.CTRL + 'U');
		mntmUpdate.setEnabled(true);
		mntmUpdate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					updateShared();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		MenuItem findMixtures = new MenuItem(actionMenu, SWT.PUSH);
		findMixtures.setText("Find Mixtures\tCTRL+F");
		findMixtures.setAccelerator(SWT.CTRL + 'F');
		findMixtures.setEnabled(false);
		findMixtures.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					((Screen4)(tabFolder.getTabList()[4])).findMixtures();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		MenuItem stopMixtures = new MenuItem(actionMenu, SWT.PUSH);
		stopMixtures.setText("Stop Mixtures\tCTRL+S");
		stopMixtures.setAccelerator(SWT.CTRL + 'S');
		stopMixtures.setEnabled(false);
		stopMixtures.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					((Screen4)(tabFolder.getTabList()[4])).stopMixtures();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
						
		MenuItem mntmSubHelp = new MenuItem(menu, SWT.CASCADE);
		mntmSubHelp.setText("Help");
		
		Menu helpMenu = new Menu(mntmSubHelp);
		mntmSubHelp.setMenu(helpMenu);

//		MenuItem mntmContents = new MenuItem(helpMenu, SWT.NONE);
//		mntmContents.setText("Contents");
//		mntmContents.addSelectionListener(menuItemSelectionAdapter);
//		mntmContents.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				try {
////					helpSystem.displayHelp("/toc.xml");
//				} catch (Exception e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//			}
//		});
//
//		MenuItem mntmSearch = new MenuItem(helpMenu, SWT.NONE);
//		mntmSearch.setText("Search");
//		mntmSearch.addSelectionListener(menuItemSelectionAdapter);
		
		MenuItem mntmUserGuide = new MenuItem(helpMenu, SWT.NONE);
		mntmUserGuide.setText("User's Guide");
		mntmUserGuide.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				try {

					URL myURL = ClassLoader.getSystemResource("./UsersGuide.pdf");

					if (myURL==null) {
						File file1 = new File(strUserFolder+"UsersGuide.pdf");
						File file2 = new File(ClassLoader.getSystemResource("data/UsersGuide.pdf").getPath());
						if (!file1.getParentFile().exists()) file1.getParentFile().mkdir();
						if (!file1.exists() || file1.lastModified() < file2.lastModified()) {
							Files.copy(ClassLoader.getSystemResourceAsStream("data/UsersGuide.pdf"),file1.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
						}
						myURL = file1.toURI().toURL();
					}

					BrowserLauncher launcher = new BrowserLauncher(null);
					launcher.openURLinBrowser(myURL.toExternalForm());

				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});

		new MenuItem(helpMenu, SWT.SEPARATOR);

		MenuItem mntmAbout = new MenuItem(helpMenu, SWT.NONE);
		mntmAbout.setText("About");
		mntmAbout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ParisInit parisInit = new ParisInit(e.display.getActiveShell());
				parisInit.getStartButton().setVisible(false);
				parisInit.open();
			}
		});

		tabFolder = new TabFolder(shell, SWT.NONE);

		TabItem tbtmScreen0 = new TabItem(tabFolder, SWT.NONE);
		tbtmScreen0.setText("Current Mixture");
		Screen0 screen0 = new Screen0(tabFolder, SWT.BORDER);
		tbtmScreen0.setControl(screen0);
		screen0.setLayout(new FillLayout(SWT.HORIZONTAL));

		TabItem tbtmScreen1 = new TabItem(tabFolder, SWT.NONE);
		tbtmScreen1.setText("Impact Factors");
		Screen1 screen1 = new Screen1(tabFolder, SWT.BORDER);
		tbtmScreen1.setControl(screen1);
		screen1.setLayout(new FillLayout(SWT.HORIZONTAL));

		TabItem tbtmScreen2 = new TabItem(tabFolder, SWT.NONE);
		tbtmScreen2.setText("Physical Properties");
		Screen2 screen2 = new Screen2(tabFolder, SWT.BORDER);
		tbtmScreen2.setControl(screen2);
		screen2.setLayout(new FillLayout(SWT.HORIZONTAL));

		TabItem tbtmScreen3 = new TabItem(tabFolder, SWT.NONE);
		tbtmScreen3.setText("Activity Coefficients");
		Screen3 screen3 = new Screen3(tabFolder, SWT.BORDER);
		tbtmScreen3.setControl(screen3);
		screen3.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TabItem tbtmScreen4 = new TabItem(tabFolder, SWT.NONE);
		tbtmScreen4.setText("Solvent Mixtures");
		Screen4 screen4 = new Screen4(tabFolder, SWT.BORDER);
		tbtmScreen4.setControl(screen4);
		screen4.setLayout(new FillLayout(SWT.HORIZONTAL));

		tabFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected (SelectionEvent e) {
				widgetSelected(e);
			}
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					// save index of new selection
					int newIndex = tabFolder.getSelectionIndex();
	 
					// try and finishUp work from previous screen
					if (!finishUp()) return;
					
					// change previousIndex to newIndex
					previousIndex = newIndex;
					
					// run work for new screen
					begin();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});

	}

	public void updateShared() throws Exception {
		saveItem.setEnabled(true);
		closeItem.setEnabled(true);

		int index = states.getActiveState().getOpenScreen();
		
		if (index>4) throw new Exception("Too many screens");
		
		((Screen)(tabFolder.getTabList()[index])).updateShared();

	}

	public void restore() throws Exception {

		int index = states.getActiveState().getOpenScreen();
		
		if (index>4) throw new Exception("Too many screens");
		
		replacements = null;
		bestMixtures = null;
		((Screen)(tabFolder.getTabList()[index])).restore();

	}

	private boolean finishUp() throws Exception {
		boolean finishUpResults = true;	
		
		if (previousIndex>4) throw new Exception("Too many screens");
		
		tabFolder.setSelection(previousIndex);
		
		finishUpResults = ((Screen)(tabFolder.getTabList()[previousIndex])).finishUp();
		
		return finishUpResults;
	}

	private void begin() throws Exception {
		
		int newIndex = previousIndex;
		
		if (newIndex>4)	throw new Exception("Too many screens");
		
		tabFolder.setSelection(newIndex);
		
		((Screen)(tabFolder.getTabList()[newIndex])).begin();
		
	}

	public Shell getShell() {
		return shell;
	}

	public TabFolder getTabFolder() {
		return tabFolder;
	}
	
}
