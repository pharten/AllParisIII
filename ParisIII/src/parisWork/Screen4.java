/**
 * 
 */
package parisWork;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;

import parisEvents.ButtonSetListener;
import parisInit.State;

import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * @author PHARTEN
 *
 */
public class Screen4 extends Screen {

	private Table table;
	private List[] list = new List[3];
	private List list4, list5;
	private Button radioButton_1, radioButton_2, radioButton_3;
	private boolean radioButtonFlag_1 = false;
	private boolean radioButtonFlag_2 = false;
	private boolean radioButtonFlag_3 = false;
	private Button[][] buttonSet = new Button[3][4];
	private Label lblMassRatios;
	private final int bestSolventsCount = 10;
	private final int bestMixturesCount = 200;
	private ProgressBar progressBar;
	private ButtonSetListener[] listener = new ButtonSetListener[3];
	private Label lblBestMixtures;
	private Button btnMiscibility;
	private static final Logger logger = Logger.getAnonymousLogger();
	private boolean mixtureRunning = false;
	
	private String[] binaryList = {"9:1","8:2","7:3","6:4","5:5"};
	private String[] tertiaryList = {"8:1:1","7:2:1","6:3:1","6:2:2","5:4:1","5:3:2","4:4:2","4:3:3"};

	/**
	 * @param parent
	 * @param style
	 */
	public Screen4(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		logger.setLevel(Level.WARNING);
		
		SashForm sashForm_1 = new SashForm(this, SWT.VERTICAL);
		
		SashForm sashForm = new SashForm(sashForm_1, SWT.NONE);
		
		Composite composite = new Composite(sashForm, SWT.NONE);
		
		Label lblNewLabel = new Label(sashForm, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Tahoma", 18, SWT.NORMAL));
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setText("Solvent Mixtures ");
		
		btnMiscibility = new Button(sashForm, SWT.RADIO);
		btnMiscibility.setFont(SWTResourceManager.getFont("Segoe UI", 8, SWT.NORMAL));
		btnMiscibility.setEnabled(false);
		btnMiscibility.setText("Miscibility Test");
		btnMiscibility.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Button radioButton = (Button)arg0.widget;
				State state = states.getActiveState();
				state.setIncludeMiscibility(!state.includeMiscibility());
				state.setReplacementIndex(0);
				state.setReplacementTopIndex(0);
				radioButton.setSelection(state.includeMiscibility());
				bestMixtures =  null;
				loadBestMixturesList(state);
			}
		});
		
		sashForm.setWeights(new int[] {1, 2, 1});
		
		SashForm sashForm_2 = new SashForm(sashForm_1, SWT.NONE);
		
		Composite composite_2 = new Composite(sashForm_2, SWT.NONE);
		
		SashForm sashForm_3 = new SashForm(sashForm_2, SWT.VERTICAL);
		
		SashForm sashForm_4 = new SashForm(sashForm_3, SWT.NONE);
		
		SashForm sashForm_5 = new SashForm(sashForm_4, SWT.VERTICAL);
		
		radioButton_1 = new Button(sashForm_5, SWT.RADIO);
		radioButton_1.setText("Primary");
		radioButton_1.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Button radioButton = (Button)arg0.widget;
					
				radioButtonFlag_1 = !radioButtonFlag_1;
				radioButton.setSelection(radioButtonFlag_1);
				
				Mixture mixture = states.getActiveState().getMixture();
				if (mixture==null || mixture.getChemicals().size()==0) {
					if (radioButtonFlag_1) {
						emptyMixture(radioButton);
						radioButtonFlag_1 = !radioButtonFlag_1;
						radioButton.setSelection(radioButtonFlag_1);
						return;
					}
				}
				
				radioButtonFlag_2 = true;
				radioButton_2.notifyListeners(SWT.Selection, new Event());
				radioButton_2.setEnabled(radioButtonFlag_1);
				
				buttonSet[0][0].setEnabled(radioButtonFlag_1);
				buttonSet[0][1].setEnabled(radioButtonFlag_1);
				buttonSet[0][2].setEnabled(radioButtonFlag_1);
				buttonSet[0][3].setEnabled(radioButtonFlag_1);
				list[0].setEnabled(radioButtonFlag_1);

				if (radioButtonFlag_1) {
					buttonSet[0][0].setSelection(true);
					buttonSet[0][0].notifyListeners(SWT.Selection, new Event());
				} else {
					buttonSet[0][0].setSelection(false);
					buttonSet[0][1].setSelection(false);
					buttonSet[0][2].setSelection(false);
					buttonSet[0][3].setSelection(false);
					buttonSet[0][0].notifyListeners(SWT.Selection, new Event());
					list[0].removeAll();
				}

			}
		});
		
		Composite composite_4 = new Composite(sashForm_5, SWT.NONE);
		composite_4.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_10 = new SashForm(composite_4, SWT.VERTICAL);
		
		Button button_0 = new Button(sashForm_10, SWT.CHECK);
		button_0.setEnabled(false);
		button_0.setText("Best Solvents");
		buttonSet[0][0] = button_0;
		
		Button button_1 = new Button(sashForm_10, SWT.CHECK);
		button_1.setEnabled(false);
		button_1.setText("All Green Solvents");
		buttonSet[0][1] = button_1;

		Button button_2 = new Button(sashForm_10, SWT.CHECK);
		button_2.setEnabled(false);
		button_2.setText("Initial Solvents");
		buttonSet[0][2] = button_2;
		
		Button button_3 = new Button(sashForm_10, SWT.CHECK);
		button_3.setEnabled(false);
		button_3.setText("All Solvents");
		buttonSet[0][3] = button_3;
		
		sashForm_10.setWeights(new int[] {1, 1, 1, 1});
		
		List list0 = new List(sashForm_5, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		list[0] = list0;
		
		sashForm_5.setWeights(new int[] {1, 6, 20});
		
		SashForm sashForm_6 = new SashForm(sashForm_4, SWT.VERTICAL);
		
		radioButton_2 = new Button(sashForm_6, SWT.RADIO);
		radioButton_2.setEnabled(false);
		radioButton_2.setText("Secondary");
		radioButton_2.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Button radioButton = (Button)arg0.widget;
				
				radioButtonFlag_2 = !radioButtonFlag_2;
				radioButton.setSelection(radioButtonFlag_2);
				
				radioButtonFlag_3 = true;
				radioButton_3.notifyListeners(SWT.Selection, new Event());
				radioButton_3.setEnabled(radioButtonFlag_2);
				
				btnMiscibility.setEnabled(radioButtonFlag_2);
				btnMiscibility.setSelection(states.getActiveState().includeMiscibility());
				
				buttonSet[1][0].setEnabled(radioButtonFlag_2);
				buttonSet[1][1].setEnabled(radioButtonFlag_2);
				buttonSet[1][2].setEnabled(radioButtonFlag_2);
				buttonSet[1][3].setEnabled(radioButtonFlag_2);
				list[1].setEnabled(radioButtonFlag_2);

				lblMassRatios.setEnabled(radioButtonFlag_2);
				list4.setEnabled(radioButtonFlag_2);
						
				if (radioButtonFlag_2) {
					buttonSet[1][0].setSelection(true);
					buttonSet[1][0].notifyListeners(SWT.Selection, new Event());
				} else {
					buttonSet[1][0].setSelection(false);
					buttonSet[1][1].setSelection(false);
					buttonSet[1][2].setSelection(false);
					buttonSet[1][3].setSelection(false);
					buttonSet[1][0].notifyListeners(SWT.Selection, new Event());
					list[1].removeAll();
					list4.removeAll();
				}

			}
		});
		
		Composite composite_5 = new Composite(sashForm_6, SWT.NONE);
		composite_5.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_11 = new SashForm(composite_5, SWT.VERTICAL);
		
		Button button_5 = new Button(sashForm_11, SWT.CHECK);
		button_5.setEnabled(false);
		button_5.setText("Best Solvents");
		buttonSet[1][0] = button_5;
		
		Button button_6 = new Button(sashForm_11, SWT.CHECK);
		button_6.setEnabled(false);
		button_6.setText("All Green Solvents");
		buttonSet[1][1] = button_6;
		
		Button button_7 = new Button(sashForm_11, SWT.CHECK);
		button_7.setEnabled(false);
		button_7.setText("Initial Solvents");
		buttonSet[1][2] = button_7;
		
		Button button_8 = new Button(sashForm_11, SWT.CHECK);
		button_8.setEnabled(false);
		button_8.setText("All Solvents");
		buttonSet[1][3] = button_8;
		
		sashForm_11.setWeights(new int[] {1, 1, 1, 1});
		
		List list1 = new List(sashForm_6, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		list1.setEnabled(false);
		list[1] = list1;
		
		sashForm_6.setWeights(new int[] {1, 6, 20});
		
		SashForm sashForm_7 = new SashForm(sashForm_4, SWT.VERTICAL);
		
		radioButton_3 = new Button(sashForm_7, SWT.RADIO);
		radioButton_3.setEnabled(false);
		radioButton_3.setText("Tertiary");
		radioButton_3.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Button radioButton = (Button)arg0.widget;
				
				radioButtonFlag_3 = !radioButtonFlag_3;
				radioButton.setSelection(radioButtonFlag_3);
				
				buttonSet[2][0].setEnabled(radioButtonFlag_3);
				buttonSet[2][1].setEnabled(radioButtonFlag_3);
				buttonSet[2][2].setEnabled(radioButtonFlag_3);
				buttonSet[2][3].setEnabled(radioButtonFlag_3);
				list[2].setEnabled(radioButtonFlag_3);
						
				if (radioButtonFlag_3) {
					buttonSet[2][0].setSelection(true);
					buttonSet[2][0].notifyListeners(SWT.Selection, new Event());
					list4.setItems(tertiaryList);
					list4.select(0,tertiaryList.length-1);
				} else {
					buttonSet[2][0].setSelection(false);
					buttonSet[2][1].setSelection(false);
					buttonSet[2][2].setSelection(false);
					buttonSet[2][3].setSelection(false);
					buttonSet[2][0].notifyListeners(SWT.Selection, new Event());
					list[2].removeAll();
					list4.setItems(binaryList);
					list4.select(0,binaryList.length-1);
				}

			}
		});
		
		Composite composite_6 = new Composite(sashForm_7, SWT.NONE);
		composite_6.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_12 = new SashForm(composite_6, SWT.VERTICAL);
		
		Button button_9 = new Button(sashForm_12, SWT.CHECK);
		button_9.setText("Best Solvents");
		button_9.setEnabled(false);
		buttonSet[2][0] = button_9;
		
		Button button_10 = new Button(sashForm_12, SWT.CHECK);
		button_10.setText("All Green Solvents");
		button_10.setEnabled(false);
		buttonSet[2][1] = button_10;
		
		Button button_11 = new Button(sashForm_12, SWT.CHECK);
		button_11.setText("Initial Solvents");
		button_11.setEnabled(false);
		buttonSet[2][2] = button_11;
		
		Button button_12 = new Button(sashForm_12, SWT.CHECK);
		button_12.setText("All Solvents");
		button_12.setEnabled(false);
		buttonSet[2][3] = button_12;
		
		sashForm_12.setWeights(new int[] {1, 1, 1, 1});
		
		List list2 = new List(sashForm_7, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.MULTI);
		list2.setEnabled(false);
		list[2] = list2;
		
		sashForm_7.setWeights(new int[] {1, 6, 20});
		
		SashForm sashForm_8 = new SashForm(sashForm_4, SWT.VERTICAL);
		
		lblMassRatios = new Label(sashForm_8, SWT.NONE);
		lblMassRatios.setEnabled(false);
		lblMassRatios.setAlignment(SWT.CENTER);
		lblMassRatios.setText("Mass Ratios");
		
		list4 = new List(sashForm_8, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		list4.setEnabled(false);
		list4.setOrientation(SWT.RIGHT_TO_LEFT);
		sashForm_8.setWeights(new int[] {1, 26});
		
		SashForm sashForm_9 = new SashForm(sashForm_4, SWT.VERTICAL);
		
		lblBestMixtures = new Label(sashForm_9, SWT.NONE);
		lblBestMixtures.setEnabled(false);
		lblBestMixtures.setAlignment(SWT.CENTER);
		lblBestMixtures.setText("Best Mixtures");
		
		list5 = new List(sashForm_9, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		list5.setEnabled(false);
		list5.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int index = list5.getSelectionIndex();
				int topIndex = list5.getTopIndex();
				Mixture mixture = bestMixtures.get(index);
				
				// save position on mixture list
				State activeState = states.getActiveState();
				activeState.setSingle(false);
				activeState.setReplacementIndex(index);
				activeState.setReplacementTopIndex(topIndex);
				
				table.clearAll();
				table.setItemCount(mixture.getChemicals().size());
				for (int i=0; i<mixture.getChemicals().size(); i++) {
					table.getItem(i).setText(0, mixture.getChemicals().get(i).getName());
					table.getItem(i).setText(1, Double.toString(mixture.getWghtFractions().get(i)*100.0));
				}
			}
			
		});
		
		Composite composite_7 = new Composite(sashForm_9, SWT.NONE);
		composite_7.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		table = new Table(composite_7, SWT.BORDER | SWT.FULL_SELECTION);
		table.setEnabled(false);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Rectangle area = table.getParent().getClientArea();
				Point preferredSize = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2*table.getBorderWidth();
				
				if (preferredSize.y > area.height + table.getHeaderHeight()) {
					// Subtract the scrollbar width from the total column width
					// if a vertical scrollbar will be required
					Point vBarSize = table.getVerticalBar().getSize();
					width -= vBarSize.x;
				}
				Point oldSize = table.getSize();
				if (oldSize.x > area.width) {
					// table is getting smaller so make the columns 
					// smaller first and then resize the table to
					// match the client area width
					table.getColumn(0).setWidth(3*width/4);
					table.getColumn(1).setWidth(width - table.getColumn(0).getWidth());
					table.setSize(area.width, area.height);
				} else {
					// table is getting bigger so make the table 
					// bigger first and then make the columns wider
					// to match the client area width
					table.setSize(area.width, area.height);
					table.getColumn(0).setWidth(3*width/4);
					table.getColumn(1).setWidth(width - table.getColumn(0).getWidth());
				}
			}
		});
		
		TableColumn tblclmn_1 = new TableColumn(table, SWT.LEFT);
		tblclmn_1.setWidth(108);
		tblclmn_1.setText("Solvent");
		
		TableColumn tblclmn_2 = new TableColumn(table, SWT.LEFT);
		tblclmn_2.setWidth(36);
		tblclmn_2.setText("Wt%");
		
		sashForm_9.setWeights(new int[] {1, 20, 6});
		sashForm_4.setWeights(new int[] {16, 16, 16, 8, 24});
		
		progressBar = new ProgressBar(sashForm_3, SWT.NONE);
		progressBar.setEnabled(false);
		sashForm_3.setWeights(new int[] {20, 1});
		
		Composite composite_3 = new Composite(sashForm_2, SWT.NONE);
		sashForm_2.setWeights(new int[] {1, 80, 1});
		
		Composite composite_1 = new Composite(sashForm_1, SWT.NONE);
		sashForm_1.setWeights(new int[] {1, 12, 1});
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void begin() throws Exception {
		restore();
		int screenNum = Integer.parseInt(this.getClass().getSimpleName().substring(6));
		states.getActiveState().setOpenScreen(screenNum);
//		System.out.println("at Screen"+screenNum);
	}
	
	@Override
	public boolean finishUp() throws Exception {
		updateShared();
		return true;
	}
	
	@Override
	public void updateShared() throws Exception {
		states.getActiveState().setSingle(bestMixtures==null);
		ParisWork.states = states;
		ParisWork.chemicals = chemicals;
		ParisWork.replacements = replacements;
		ParisWork.bestMixtures = bestMixtures;
	}
	
	@Override
	public void restore() throws Exception {
		states = ParisWork.states;
		chemicals = ParisWork.chemicals;
		replacements = ParisWork.replacements;
		bestMixtures = ParisWork.bestMixtures;
		State activeState = states.getActiveState();
		
		Mixture mixture = activeState.getMixture();
		if (mixture==null || mixture.getChemicals().isEmpty()) {
			replacements = null;
			bestMixtures = null;
			loadSolventLists(states.getActiveState());
			loadBestMixturesList(states.getActiveState());
			return;
		}
		
		if (activeState.getPDesiredVals()==null) activeState.setPDesiredVals();
		if (activeState.getADesiredVals()==null) activeState.setADesiredVals();
		
		if (replacements==null || replacements.size()==0) {
			// regenerate replacements
			replacements = new Replacements(activeState, chemicals);
			bestMixtures = null;
		}
		
		if (replacements.areChanged()) {
			removeButtonSetListeners(states.getActiveState());
			addButtonSetListeners(states.getActiveState());
			loadSolventLists(states.getActiveState());
			bestMixtures = null;
			replacements.setChanged(false);
		}
		
		loadBestMixturesList(states.getActiveState());
	}
	
	public void findMixtures() throws Exception {
		if (!testMixtureSelections()) return;
		
		mixtureRunning = true;
		
		boolean triplet = list[2].isEnabled();
		
		this.getShell().getMenuBar().getItem(0).setEnabled(false);
		this.getShell().getMenuBar().getItem(1).setEnabled(false);
		((TabFolder)this.getParent()).setEnabled(false);
		
		lblBestMixtures.setEnabled(false);
		list5.removeAll();
		list5.setEnabled(false);
		table.clearAll();
		progressBar.setEnabled(true);
		progressBar.setSelection(0);
		
		if (!triplet) {
			bestMixtures = binaryMixtures(states.getActiveState());
		} else {
			bestMixtures = tripletMixtures(states.getActiveState());
		}
		
		this.getShell().getMenuBar().getItem(0).setEnabled(true);
		this.getShell().getMenuBar().getItem(1).setEnabled(true);
		((TabFolder)this.getParent()).setEnabled(true);
		
		loadBestMixturesList(states.getActiveState());

		progressBar.setSelection(0);
		progressBar.setEnabled(false);
		
		mixtureRunning = false;
		
	}
	
	public void stopMixtures() throws Exception {
		mixtureRunning = false;
	}
	
	private String[] generateMixtureNames(Vector<Mixture> bestMixtures) {
		String[] names = new String[bestMixtures.size()];
		for (int i=0; i<names.length; i++) {
			names[i] = bestMixtures.get(i).getMixtureName();
		}
		return names;
	}
	
	private boolean testMixtureSelections() {
		if (!(list[0].isEnabled() && list[0].getSelectionCount()>0)) {
			MessageBox dialog = new MessageBox(list[0].getShell(), SWT.ICON_WARNING | SWT.OK);
			dialog.setText("Warning");
			dialog.setMessage("Primary solvents must be selected");
			int returnCode = dialog.open(); 
			return false;
		} else if (!(list[1].isEnabled() && list[1].getSelectionCount()>0)) {
			MessageBox dialog = new MessageBox(list[1].getShell(), SWT.ICON_WARNING | SWT.OK);
			dialog.setText("Warning");
			dialog.setMessage("Secondary solvents must be selected");
			int returnCode = dialog.open(); 
			return false;
		} else if (list[2].isEnabled()) { // tertiary mixture runs
			if (list[2].getSelectionCount()==0) {
				MessageBox dialog = new MessageBox(list[1].getShell(), SWT.ICON_WARNING | SWT.OK);
				dialog.setText("Warning");
				dialog.setMessage("Tertiary solvents must be selected or turned off");
				int returnCode = dialog.open(); 
				return false;
			}
		} else if (!(list4.isEnabled() && list4.getSelectionCount()>0)){
			MessageBox dialog = new MessageBox(list4.getShell(), SWT.ICON_WARNING | SWT.OK);
			dialog.setText("Warning");
			dialog.setMessage("Mass Ratios must be selected");
			int returnCode = dialog.open(); 
			return false;
		}
		return true;
	}

	private void loadSolventLists(State activeState) {
		
		if (replacements==null || replacements.size()==0) {
			radioButtonFlag_1 = true;
			bestMixtures = null;
		} else {
			radioButtonFlag_1 = false;
		}
		radioButton_1.setSelection(radioButtonFlag_1);
		radioButton_1.notifyListeners(SWT.Selection, new Event());

	}
	
	private void loadBestMixturesList(State activeState) {
		
		if (bestMixtures==null || bestMixtures.size()==0) {		
			lblBestMixtures.setEnabled(false);
			list5.removeAll();
			list5.setEnabled(false);
			table.clearAll();
			table.setEnabled(false);
		} else {
			lblBestMixtures.setEnabled(true);
			list5.setEnabled(true);
			list5.setItems(generateMixtureNames(bestMixtures));
			if (activeState.isSingle()) {
				list5.setSelection(0);
				list5.setTopIndex(0);
			} else {
				list5.setSelection(activeState.getReplacementIndex());
				list5.setTopIndex(activeState.getReplacementTopIndex());
			}
			list5.notifyListeners(SWT.Selection, new Event());
			table.setEnabled(true);
		}

	}
	
	private void addButtonSetListeners(State activeState) {
		
		Mixture mixture = activeState.getMixture();
		if (mixture==null || mixture.getChemicals().size()==0) return;
		
		for (int i=0; i<3; i++) {
			listener[i] = new ButtonSetListener(buttonSet[i], list[i], mixture.getChemicals(), replacements, chemicals, bestSolventsCount);
			for (int j=0; j<4; j++) {
				buttonSet[i][j].addSelectionListener(listener[i]);
			}
		}

	}
	
	private void removeButtonSetListeners(State activeState) {
		
		for (int i=0; i<3; i++) {
			if (listener[i]!=null) {
				for (int j=0; j<4; j++) {
					buttonSet[i][j].removeSelectionListener(listener[i]);
				}
			}
		}
		
	}
	
	private int emptyMixture(Button radioButton) {
		MessageBox dialog = new MessageBox(radioButton.getShell(), SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
		dialog.setText("Warning");
		dialog.setMessage("There is no original solvent mixture to replace.");
		int returnCode = dialog.open(); 
		radioButton.forceFocus();
		return returnCode;
	}
	
	private Vector<Mixture> binaryMixtures(State activeState) throws Exception {
		Vector<Mixture> bestMixtures = new Vector<Mixture>();
		Vector<Chemical> primarySolvents = solventsSelected(activeState, list[0]);
		Vector<Chemical> secondarySolvents = solventsSelected(activeState, list[1]);
		double tempK = activeState.tempConvertToSI();
		
		int[] indices = list4.getSelectionIndices();
	    String[] massRatiosSelected = new String[indices.length];
		for (int i=0; i<massRatiosSelected.length; i++) {
			massRatiosSelected[i] =  list4.getItem(indices[i]);
		}
		
		long unit2 = massRatiosSelected.length;
		long unit1 = secondarySolvents.size()*unit2;
		long unit0 = primarySolvents.size()*unit1;
		long mixtureCount = 0;
		long startTime;
		double time;
		
		Mixture sample = new Mixture(primarySolvents.get(0), secondarySolvents.get(0), massRatiosSelected[0], tempK);
		int maxCapacity = (int) (estimateMaxCapacity(sample)*0.8);

		Vector<Mixture> vMixtures = new Vector<Mixture>(maxCapacity);
		
		startTime = System.currentTimeMillis();
	    logger.info("Starting binaryMixtures Search");
		
	    boolean massRatiosSymmetry[] = testForMassRatiosSymmetry(massRatiosSelected,"5:5");
		boolean solventSymmetryPS[] = testForSolventSymmetry(primarySolvents, secondarySolvents);
		boolean solventSymmetrySP[] = testForSolventSymmetry(secondarySolvents, primarySolvents);
		
		for (int i=0; i<primarySolvents.size(); i++) {
			if (!mixtureRunning) break;
			Chemical solvent1 = primarySolvents.get(i);
			boolean ssPS = solventSymmetryPS[i];
			for (int j=0; j<secondarySolvents.size(); j++) {
				if (!mixtureRunning) break;
				Chemical solvent2 = secondarySolvents.get(j);
				if (solvent2==solvent1) continue;
				Chemical[] solvents = {solvent1, solvent2};
				boolean ssSP = solventSymmetrySP[j];
				boolean ssHC = ssPS && ssSP && (solvent1.hashCode()<solvent2.hashCode());
				for (int k=0; k<massRatiosSelected.length; k++) {
					if (!mixtureRunning) break;
					if (massRatiosSymmetry[k]&&ssHC) continue;
					vMixtures.add(new Mixture(solvents, massRatiosSelected[k], tempK));
//					vMixtures.add(new Mixture(solvent1, solvent2, massRatiosSelected[k]));
					if (vMixtures.size()>maxCapacity) {
						MixtureCalculateConcurrent mixtureConcurrent = new MixtureCalculateConcurrent(activeState);
						mixtureConcurrent.calculate(vMixtures);
						mixtureConcurrent.updateProgress(progressBar, mixtureCount, unit0);
						addToBestMixtures(bestMixtures, vMixtures);
						mixtureCount += vMixtures.size();
						vMixtures.clear();
						if (logger.isLoggable(Level.INFO)) {
							time = (System.currentTimeMillis()-startTime)/3600000.0;
//							logger.info("number of mixtures = "+mixtureCount+", runtime = "+time+" hours");
							System.out.println("number of mixtures = "+mixtureCount+", runtime = "+time+" hours");
						}
					}
				}
			}
		}
		
		MixtureCalculateConcurrent mixtureConcurrent = new MixtureCalculateConcurrent(activeState);
		mixtureConcurrent.calculate(vMixtures);
		mixtureConcurrent.updateProgress(progressBar, mixtureCount, mixtureCount+vMixtures.size());
		addToBestMixtures(bestMixtures, vMixtures);
		mixtureCount += vMixtures.size();
		vMixtures.clear();
		if (logger.isLoggable(Level.INFO)) {
			time = (System.currentTimeMillis()-startTime)/3600000.0;
//			logger.info("number of mixtures = "+mixtureCount+", runtime = "+time+" hours");
			System.out.println("number of mixtures = "+mixtureCount+", runtime = "+time+" hours");
		}

		logger.info("Search took " + (System.currentTimeMillis() - startTime)/1000.0 + " seconds.");
		
		return bestMixtures;
	}
	
	private Vector<Mixture> tripletMixtures(State activeState) throws Exception {
		Vector<Mixture> bestMixtures = new Vector<Mixture>();
		Vector<Chemical> primarySolvents = solventsSelected(activeState, list[0]);
		Vector<Chemical> secondarySolvents = solventsSelected(activeState, list[1]);
		Vector<Chemical> tertiarySolvents = solventsSelected(activeState, list[2]);
		double tempK = activeState.tempConvertToSI();
		
		int[] indices = list4.getSelectionIndices();
	    String[] massRatiosSelected = new String[indices.length];
		for (int i=0; i<massRatiosSelected.length; i++) {
			massRatiosSelected[i] =  list4.getItem(indices[i]);
		}

		long unit3 = massRatiosSelected.length;
		long unit2 = tertiarySolvents.size()*unit3;
		long unit1 = secondarySolvents.size()*unit2;
		long unit0 = primarySolvents.size()*unit1;
		long mixtureCount = 0;
		long startTime;
		double time;
		
		Mixture sample = new Mixture(primarySolvents.get(0), secondarySolvents.get(0), tertiarySolvents.get(0), massRatiosSelected[0], tempK);
		int maxCapacity = (int) (estimateMaxCapacity(sample)*0.8);

		Vector<Mixture> vMixtures = new Vector<Mixture>(maxCapacity);

		startTime = System.currentTimeMillis();
		logger.info("Starting tripletMixtures Search");
		
	    boolean massRatiosSymmetryPS[] = testForMassRatiosSymmetry(massRatiosSelected,"4:4:2");
	    boolean massRatiosSymmetryST[] = testForMassRatiosSymmetry(massRatiosSelected,"8:1:1","6:2:2","4:3:3");
		boolean solventSymmetryPS[] = testForSolventSymmetry(primarySolvents, secondarySolvents);
		boolean solventSymmetrySP[] = testForSolventSymmetry(secondarySolvents, primarySolvents);
		boolean solventSymmetryST[] = testForSolventSymmetry(secondarySolvents, tertiarySolvents);
		boolean solventSymmetryTS[] = testForSolventSymmetry(tertiarySolvents, secondarySolvents);
		
		for (int i=0; i<primarySolvents.size(); i++) {
			if (!mixtureRunning) break;
			Chemical solvent1 = primarySolvents.get(i);
			boolean ssPS = solventSymmetryPS[i];  // primary solvent1 appears in secondary solvents.
			for (int j=0; j<secondarySolvents.size(); j++) {
				if (!mixtureRunning) break;
				Chemical solvent2 = secondarySolvents.get(j);
				if (solvent2==solvent1) continue;
				boolean ssSP = solventSymmetrySP[j];  // secondary solvent2 appears in primary solvents.
				boolean ssHC = ssPS && ssSP && (solvent1.hashCode()<solvent2.hashCode());  // if so, set hash code flag
				boolean ssST = solventSymmetryST[j];  // secondary solvent2 appears in tertiary solvents.
				for (int k=0; k<tertiarySolvents.size(); k++) {
					if (!mixtureRunning) break;
					Chemical solvent3 = tertiarySolvents.get(k);
					if (solvent3==solvent1 || solvent3==solvent2) continue;
					Chemical[] solvents = {solvent1, solvent2, solvent3};
					boolean ssTS = solventSymmetryTS[k];  // tertiary solvent3 appears in secondary solvents.
					boolean ssHC2 = ssST && ssTS && (solvent2.hashCode()>solvent3.hashCode());  // if so, set hash code flag
					for (int l=0; l<massRatiosSelected.length; l++) {
						if (!mixtureRunning) break;
						if (massRatiosSymmetryPS[l]&&ssHC) continue;
						if (massRatiosSymmetryST[l]&&ssHC2) continue;
						vMixtures.add(new Mixture(solvents, massRatiosSelected[l], tempK));
//						vMixtures.add(new Mixture(solvent1, solvent2, solvent3, massRatiosSelected[l]));
						if (vMixtures.size()>=maxCapacity) {
							MixtureCalculateConcurrent mixtureConcurrent = new MixtureCalculateConcurrent(activeState);
							mixtureConcurrent.calculate(vMixtures);
							mixtureConcurrent.updateProgress(progressBar, mixtureCount, unit0);
							addToBestMixtures(bestMixtures, vMixtures);
							mixtureCount += vMixtures.size();
							vMixtures.clear();
							if (logger.isLoggable(Level.INFO)) {
								time = (System.currentTimeMillis()-startTime)/3600000.0;
//								logger.info("number of mixtures = "+mixtureCount+", runtime = "+time+" hours");
								System.out.println("number of mixtures = "+mixtureCount+", runtime = "+time+" hours");
							}
						}
					}
				}
			}
		}
		
		MixtureCalculateConcurrent mixtureConcurrent = new MixtureCalculateConcurrent(activeState);
		mixtureConcurrent.calculate(vMixtures);
		mixtureConcurrent.updateProgress(progressBar, mixtureCount, mixtureCount+vMixtures.size());
		addToBestMixtures(bestMixtures, vMixtures);
		mixtureCount += vMixtures.size();
		vMixtures.clear();
		if (logger.isLoggable(Level.INFO)) {
			time = (System.currentTimeMillis()-startTime)/3600000.0;
//			logger.info("number of mixtures = "+mixtureCount+", runtime = "+time+" hours");
			System.out.println("number of mixtures = "+mixtureCount+", runtime = "+time+" hours");
		}
		
		logger.info("Search took " + (System.currentTimeMillis() - startTime)/1000.0 + " seconds.");
		
		return bestMixtures;		
	}

	private int estimateMaxCapacity(Mixture sample) throws IOException {
		
		// estimate the size of a Mixture instance (in bytes);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(sample);
		oos.close();
		int mixtureSize = baos.size();
		baos.close();
		
		// perform garbage collecting
		System.gc();  
		
		// estimate the maximum capacity of Vector<Mixture>
		int maxCapacity = (int)(Runtime.getRuntime().freeMemory()/mixtureSize);

		return maxCapacity;
	}
	
	private boolean[] testForMassRatiosSymmetry(String[] massRatios, String symmetry) {
		boolean[] flag = new boolean[massRatios.length];
		for (int i=0; i<massRatios.length; i++) {
			flag[i] = massRatios[i].contains(symmetry);
		}
		return flag;
	}
	
	private boolean[] testForMassRatiosSymmetry(String[] massRatios, String symmetry1, String symmetry2, String symmetry3) {
		boolean[] flag = new boolean[massRatios.length];
		for (int i=0; i<massRatios.length; i++) {
			flag[i] = massRatios[i].contains(symmetry1)||massRatios[i].contains(symmetry2)||massRatios[i].contains(symmetry3);
		}
		return flag;
	}
	
	private boolean[] testForSolventSymmetry(Vector<Chemical> solvents1, Vector<Chemical> solvents2) {
		boolean[] flag = new boolean[solvents1.size()];
		for (int i=0; i<solvents1.size(); i++) {
			Chemical solvent1 = solvents1.get(i);
			flag[i] = false;
			for (int j=0; j<solvents2.size(); j++) {
				if (solvent1==solvents2.get(j)) {
					flag[i] = true;
					break;
				}
			}
		}
		return flag;
	}

	private Vector<Chemical> solventsSelected(State activeState, List list) {
		Vector<Chemical> selectedSolvents = null;
		
		if (list.getItemCount()==replacements.size()) {  // list of replacements
			
			selectedSolvents = getSelected(replacements, list.getSelectionIndices());
			
		} else if (list.getItemCount()==chemicals.size()) {  // list of allSolvents
			
			selectedSolvents = getSelected(chemicals, list.getSelectionIndices());
			
		} else { // add some of the chemicals from the mixture to the replacements
			
			Vector<Chemical> listSolvents = new Vector<Chemical>();
			
			Mixture mixture = activeState.getMixture();
			listSolvents.addAll(replacements);
			Vector<Chemical> mixChemicals = mixture.getChemicals();
			for(int i=replacements.size(); i<list.getItemCount(); i++) {
				String name = list.getItem(i);
				for (Chemical chemical: mixChemicals) {
					if (name.equals(chemical.getName())) {  // any solvents in list beyond replacements, take from mixture
						listSolvents.add(chemical);
						break;
					}
				}
			}
			selectedSolvents = getSelected(listSolvents, list.getSelectionIndices());
			
		}
					
		return selectedSolvents;
	}
	
	private Vector<Chemical> getSelected(Vector<Chemical> listSolvents, int[] indices) {
		Vector<Chemical> selectedSolvents = null;
		
		if (indices.length==listSolvents.size()) {  // all chemicals in list are selected
			selectedSolvents = listSolvents;
		} else {
			selectedSolvents = new Vector<Chemical>();
			for (int i=0; i<indices.length; i++) {
				selectedSolvents.add(listSolvents.get(indices[i]));
			}
		}
		
		return selectedSolvents;
	}
	
	private void addToBestMixtures(Vector<Mixture> bestMixtures, Mixture newMixture) {

		int i = bestMixtures.size();
		for (; i>0; i--) {
			if (newMixture.getMixtureScore() >= bestMixtures.get(i-1).getMixtureScore()) break;
		}
		if (i < bestMixturesCount) {
			bestMixtures.add(i, newMixture);
			if (bestMixtures.size()>bestMixturesCount) bestMixtures.remove(bestMixturesCount);
		}

	}
	
	private void addToBestMixtures(Vector<Mixture> bestMixtures, Vector<Mixture> newMixtures) {
		
		for (int j=0; j<newMixtures.size(); j++) {
			Mixture newMixture = newMixtures.get(j);
			newMixtures.set(j, null);
			addToBestMixtures(bestMixtures, newMixture);
		}

	}

	public boolean isMixtureRunning() {
		return mixtureRunning;
	}
	
}
