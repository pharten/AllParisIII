package parisWork;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import parisInit.State;

public class Screen0 extends Screen {
	private Combo combo_1, combo_2, combo_3, combo_4;
	private Text text_1;
	private Text text_2;
	private Combo combo;
	private StackLayout stack = new StackLayout();
	private Composite stackComposite, composite_6, composite_7, composite_8;
	private List list_0, list_3;
	private Button btn_3;
	private Table table;
	private TableEditor tableEditor;
	private Shell shell;
	private Reference reference;
	private State initialState;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws Exception 
	 */
	public Screen0(Composite parent, int style) throws Exception {
		super(parent, style);
		
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm = new SashForm(this, SWT.VERTICAL);
		
		Group group_1 = new Group(sashForm, SWT.NONE);
		group_1.setText("System");
		group_1.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite_1 = new Composite(group_1, SWT.NONE);
		RowLayout rl_composite_1 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_1.center = true;
		composite_1.setLayout(rl_composite_1);
		
		Label label_1a = new Label(composite_1, SWT.NONE);
		label_1a.setAlignment(SWT.RIGHT);
		label_1a.setLayoutData(new RowData(62, -1));
		label_1a.setText("Name");
		
		combo_1 = new Combo(composite_1, SWT.NONE);
		combo_1.setLayoutData(new RowData(305, SWT.DEFAULT));
		combo_1.setVisibleItemCount(10);
		combo_1.setItems(new String[] {"default"});
		combo_1.select(0);
		
		combo_1.addSelectionListener(new SelectionListener() {
	        @Override
	    	public void widgetSelected(SelectionEvent event) {
	        	try {
	        		int index = combo_1.getSelectionIndex();
	        		if (states.get(index)!=states.getActiveState()) {
	        			State state = states.get(index);
	        			replacements = null;
	        			bestMixtures = null;
	        			changeState(state);
	        		}
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
	        
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				if (arg0.doit){
					combo_2.setFocus();
				}
			}
		});
		
		combo_1.addFocusListener(new FocusListener() {
	        @Override
	    	public void focusLost(FocusEvent event) {
	        	String text = combo_1.getText().trim();
	        	State activeState = states.getActiveState();
	        	if (!text.equals(activeState.getSystemName())) {
	        		if (states.containsName(text)) {
	        			State state = states.getElementBySystemName(text);
	        			combo_1.select(states.indexOf(state));
	        			combo_1.notifyListeners(SWT.Selection, new Event());
	        		} else {	// this is a new state
	        			State state = activeState.clone();
	        			state.setSystemName(text);
	        			state.setOpenScreen(0);
	        			states.add(1,state);
	        			states.setActiveState(state);
	        			combo_1.setItems(states.getSystemNames());
	        			combo_1.setText(state.getSystemName());
	        		}
	        	}
		    }
			@Override
			public void focusGained(FocusEvent arg0) {
//				States states = (States)combo_1.getShell().getData();
//        		combo_1.setItems(states.getSystemNames());
			}
		});
		
		Composite composite_2 = new Composite(group_1, SWT.NONE);
		RowLayout rl_composite_2 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_2.center = true;
		composite_2.setLayout(rl_composite_2);
		
		Label label_2a = new Label(composite_2, SWT.NONE);
		label_2a.setAlignment(SWT.RIGHT);
		label_2a.setLayoutData(new RowData(62, -1));
		label_2a.setText("Units");
		
		combo_2 = new Combo(composite_2, SWT.READ_ONLY);
		combo_2.setLayoutData(new RowData(45, SWT.DEFAULT));
		combo_2.setVisibleItemCount(4);
		combo_2.setItems(new String[] {"SI", "COMMON", "CGS", "US"});
		combo_2.select(0);
		
		combo_2.addSelectionListener(new SelectionListener() {
	        @Override
	    	public void widgetSelected(SelectionEvent event) {
	        	states.getActiveState().setSystemUnit(combo_2.getText());
//	        	System.out.println("SystemUnit widgetSelected");
		    }
	        
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		Label label_2b = new Label(composite_2, SWT.NONE);
		label_2b.setText("Note: Units are converted to SI for calclulations.");
		
		Composite composite_3 = new Composite(group_1, SWT.NONE);
		RowLayout rl_composite_3 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_3.center = true;
		composite_3.setLayout(rl_composite_3);
		
		Label label_3a = new Label(composite_3, SWT.NONE);
		label_3a.setLayoutData(new RowData(62, -1));
		label_3a.setAlignment(SWT.RIGHT);
		label_3a.setText("Temperature");
		
		combo_3 = new Combo(composite_3, SWT.READ_ONLY);
		combo_3.setLayoutData(new RowData(23, SWT.DEFAULT));
		combo_3.setVisibleItemCount(6);
		combo_3.setItems(new String[] {"10", "15", "20", "25", "30", "35"});
		combo_3.select(3);
		
		combo_3.addSelectionListener(new SelectionListener() {
	        @Override
	    	public void widgetSelected(SelectionEvent event) {
	        	State state = states.getActiveState();
	        	state.setSystemTemp(combo_3.getText());
	        	
    			chemicals = allChemicals.filterForLiquidPhase(state.tempConvertToSI());
    			chemicals = chemicals.filterForVaporPressure();
    			chemicals.normalizeEnvironmentalCategories();
    			ParisWork.chemicals = chemicals;
    			
    			combo.select(state.getScreen0StackOption());
    			combo.notifyListeners(SWT.Selection, new Event());
    			state.setMixture(null);
    			reBuildTable(state);
//	        	System.out.println("SystemTemp widgetSelected");
		    }
	        
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		Label label_3b = new Label(composite_3, SWT.NONE);
		label_3b.setText("degrees C");
		
		Composite composite_4 = new Composite(group_1, SWT.NONE);
		RowLayout rl_composite_4 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_4.center = true;
		composite_4.setLayout(rl_composite_4);
		
		Label label_4a = new Label(composite_4, SWT.NONE);
		label_4a.setAlignment(SWT.RIGHT);
		label_4a.setLayoutData(new RowData(62, SWT.DEFAULT));
		label_4a.setText("Pressure");
		
		combo_4 = new Combo(composite_4, SWT.READ_ONLY);
		combo_4.setLayoutData(new RowData(23, SWT.DEFAULT));
		combo_4.setVisibleItemCount(1);
		combo_4.setItems(new String[] {"1.0"});
		combo_4.select(0);
		
		combo_4.addSelectionListener(new SelectionListener() {
	        @Override
	    	public void widgetSelected(SelectionEvent event) {
	        	State state = states.getActiveState();
	        	state.setSystemPres(combo_4.getText());
    			state.setMixture(null);
//	        	System.out.println("SystemPress widgetSelected");
		    }
	        
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		Label label_4b = new Label(composite_4, SWT.NONE);
		label_4b.setText("Atm");
		
		Group group_2 = new Group(sashForm, SWT.NONE);
		group_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_1 = new SashForm(group_2, SWT.NONE);
		
		Composite composite_16 = new Composite(sashForm_1, SWT.NONE);
		
		Composite composite_15 = new Composite(sashForm_1, SWT.NONE);
		composite_15.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblNewLabel = new Label(composite_15, SWT.NONE);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setText("\r\nChemical Display Options");
		
		combo = new Combo(composite_15, SWT.READ_ONLY);
//		combo.setItems(new String[] {"Show Chemicals by Family", "Search for Chemicals by CAS Number", "Search for Chemicals by Name"});
		combo.setItems(new String[] {"Search for Chemicals by CAS Number", "Search for Chemicals by Name"});
		combo.select(1);
		
		Composite composite_17 = new Composite(composite_15, SWT.NONE);
		
		combo.addSelectionListener(new SelectionListener() {
	        @Override
	    	public void widgetSelected(SelectionEvent event) {
	        	try {
	        		Combo combo = (Combo)event.widget;
	        		int index = combo.getSelectionIndex()+1;
					switch (index) {
					case 0:
						stack.topControl = composite_6;
						String[] empty = {};
						list_3.setItems(empty);
						stackComposite.layout();
						list_0.setFocus();
						list_0.select(0);
						break;
					case 1:
						stack.topControl = composite_7;
						chemicals.sortByCAS();
						list_3.setItems(chemicals.getAllCAS());
						stackComposite.layout();
						text_1.setFocus();
						break;
					case 2:
						stack.topControl = composite_8;
						chemicals.sortByName();
						list_3.setItems(chemicals.getAllNames());
						stackComposite.layout();
						text_2.setFocus();
						break;
					default:
						new Exception("Abnormal StackLayout Index Request");	
					}
					states.getActiveState().setScreen0StackOption(index);
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

		});
		
		Composite composite = new Composite(sashForm_1, SWT.NONE);
		
		stackComposite = new Composite(sashForm_1, SWT.NONE);
		stackComposite.setLayout(stack);
		
		composite_6 = new Composite(stackComposite, SWT.NONE);
		composite_6.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblNewLabel_0 = new Label(composite_6, SWT.NONE);
		lblNewLabel_0.setAlignment(SWT.CENTER);
		lblNewLabel_0.setText("\r\nFamily Name");
		
		ChemicalFamilies families = ChemicalFamilies.readFromFile("data/ChemicalFamilies.xml");
		families.sortByName();
		list_0 = new List(composite_6, SWT.BORDER | SWT.V_SCROLL);
		list_0.setItems(families.getAllNames());
		
		Composite composite_9 = new Composite(composite_6, SWT.NONE);
		
		composite_7 = new Composite(stackComposite, SWT.NONE);
		composite_7.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite_22 = new Composite(composite_7, SWT.NONE);
		composite_22.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblNewLabel_1 = new Label(composite_22, SWT.NONE);
		lblNewLabel_1.setAlignment(SWT.CENTER);
		lblNewLabel_1.setText("\r\nInput CAS Number (format: *-xx-x) ");
		
		Composite composite_29 = new Composite(composite_7, SWT.NONE);
		composite_29.setLayout(new FillLayout(SWT.VERTICAL));
		
		text_1 = new Text(composite_29, SWT.BORDER);
		
		Composite composite_30 = new Composite(composite_29, SWT.NONE);
		
		text_1.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent arg0) {
				String text = text_1.getText();
				String[]list = list_3.getItems();
				int index=0;
				for(; index<list.length; index++) {
					if (text.compareToIgnoreCase(list[index])<=0) break;
				}
				list_3.setTopIndex(index);
			}

		});
		
		Composite composite_21 = new Composite(composite_7, SWT.NONE);
		
		Composite composite_23 = new Composite(composite_7, SWT.NONE);
		
		composite_8 = new Composite(stackComposite, SWT.NONE);
		composite_8.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite_24 = new Composite(composite_8, SWT.NONE);
		composite_24.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblNewLabel_2 = new Label(composite_24, SWT.NONE);
		lblNewLabel_2.setAlignment(SWT.CENTER);
		lblNewLabel_2.setText("\r\nChemical Name Search");
		
		Composite composite_25 = new Composite(composite_8, SWT.NONE);
		composite_25.setLayout(new FillLayout(SWT.VERTICAL));
		
		text_2 = new Text(composite_25, SWT.BORDER);
		
		Composite composite_31 = new Composite(composite_25, SWT.NONE);
		
		text_2.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent arg0) {
				String text = text_2.getText();
				String[]list = list_3.getItems();
				int index=0;
				while(index<list.length) {
					if (text.compareToIgnoreCase(list[index])<=0) break;
					index++;
				}
				list_3.setTopIndex(index);
			}

		});
		
		stack.topControl = composite_8;
		
		Composite composite_26 = new Composite(composite_8, SWT.NONE);
		
		Composite composite_27 = new Composite(composite_8, SWT.NONE);
		
		Composite composite_18 = new Composite(sashForm_1, SWT.NONE);
		sashForm_1.setWeights(new int[] {1, 20, 1, 20, 1});
		
		Group group_3 = new Group(sashForm, SWT.NONE);
		group_3.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_2 = new SashForm(group_3, SWT.NONE);
		
		Composite composite_5 = new Composite(sashForm_2, SWT.NONE);
		
		Composite composite_10 = new Composite(sashForm_2, SWT.NONE);
		composite_10.setLayout(new FillLayout(SWT.VERTICAL));
		
		SashForm sashForm_3 = new SashForm(composite_10, SWT.VERTICAL);
		
		Composite composite_28 = new Composite(sashForm_3, SWT.NONE);
		composite_28.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblNewLabel_3 = new Label(composite_28, SWT.NONE);
		lblNewLabel_3.setAlignment(SWT.CENTER);
		lblNewLabel_3.setText("Chemicals");
		
		list_3 = new List(sashForm_3, SWT.BORDER | SWT.V_SCROLL);
		sashForm_3.setWeights(new int[] {1, 8});
		
		Composite composite_11 = new Composite(sashForm_2, SWT.NONE);
		composite_11.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite_12 = new Composite(composite_11, SWT.NONE);
		RowLayout rl_composite_12 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_12.justify = true;
		rl_composite_12.fill = true;
		rl_composite_12.center = true;
		composite_12.setLayout(rl_composite_12);
		
		Button btn_1 = new Button(composite_11, SWT.PUSH);
		btn_1.setText("->");
		
		btn_1.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int index = list_3.getSelectionIndex();
				if (index>=0) {
					Chemical chemical = chemicals.get(index);
					
					int num = table.getItemCount();
					int i=0;
					for (; i<num; i++) {
						if (chemical.getName().equals(table.getItem(i).getText(0))) break;
					}
					if (i<num) return;
					
					// Clean up any previous editor control
					Control oldEditor = tableEditor.getEditor();
					if (oldEditor != null) oldEditor.dispose();
					
					TableItem tableItem = new TableItem(table, SWT.NONE);
					tableItem.setText(0,chemical.getName());
					Mixture mixture = states.getActiveState().getMixture();
					Vector<Chemical> chemicals = mixture.getChemicals();
					chemicals.add(chemical);
					mixture.setChemicals(chemicals);
					mixture.getWghtFractions().add(0.0);
					
					if (table.getItemCount()==1) {
						tableItem.setText(1,"100.0");
						mixture.getWghtFractions().set(0,1.0);
					} else {
						for (i=0; i<table.getItemCount(); i++) {
							table.getItem(i).setText(1,"");
							mixture.getWghtFractions().set(i,0.0);
						}
					}
//					saveTable(states.getActiveState());
				}
			}

			
		});
		
		Button btn_2 = new Button(composite_11, SWT.PUSH);
		btn_2.setText("<-");
		
		btn_2.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				// Clean up any previous editor control
				Control oldEditor = tableEditor.getEditor();
				if (oldEditor != null) oldEditor.dispose();
				
				Mixture mixture = states.getActiveState().getMixture();
				int index = table.getSelectionIndex();
				if (index>=0) {
					table.remove(index);
					Vector<Chemical> chemicals = mixture.getChemicals();
					chemicals.remove(index);
					mixture.setChemicals(chemicals);
					mixture.getWghtFractions().remove(index);
				}
				if (table.getItemCount()==1) {
					table.getItem(0).setText(1,"100.0");
					mixture.getWghtFractions().set(0, 1.0);
				} else {
					for (int i=0; i<table.getItemCount(); i++) {
						table.getItem(i).setText(1,"");
						mixture.getWghtFractions().set(i, 0.0);
					}
				}
			}
			
		});
		
		Composite composite_13 = new Composite(composite_11, SWT.NONE);
		RowLayout rl_composite_13 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_13.justify = true;
		rl_composite_13.fill = true;
		rl_composite_13.center = true;
		composite_13.setLayout(rl_composite_13);
		
		btn_3 = new Button(composite_11, SWT.NONE);
		btn_3.setText("ref");
		
		Composite composite_20 = new Composite(composite_11, SWT.NONE);
		btn_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index=list_3.getSelectionIndex();
				if (index<0) return;
				
				reference = new Reference(e.display.getActiveShell());
				reference.open(chemicals.get(index), states.getActiveState().getSystemUnit());
			}
		});
		
		Composite composite_14 = new Composite(sashForm_2, SWT.NONE);
		composite_14.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		table = new Table(composite_14, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		tableEditor = new TableEditor(table);
		
		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Clean up any previous editor control
				Control oldEditor = tableEditor.getEditor();
				if (oldEditor != null) oldEditor.dispose();
				
				// Identify selected row
				TableItem item = (TableItem)e.item;
				if (item == null) return;
				
				// the control that will be the editor must be a child of the Table
				Text newEditor = new Text(table, SWT.CENTER);
				tableEditor.setEditor(newEditor); // here
				newEditor.setText(item.getText(1));
				newEditor.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent me) {
						Text text = (Text)tableEditor.getEditor();
						try {
							double value = Double.parseDouble(text.getText());
							tableEditor.getItem().setText(1,Double.toString(value));
						} catch (NumberFormatException e) {
							tableEditor.getItem().setText(1,"");
						}
					}
				});
				newEditor.selectAll();
				newEditor.setFocus();
				tableEditor.setEditor(newEditor, item, 1);
			}
		});
		
		table.addControlListener(new ControlAdapter() {
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

		
		TableColumn tblclmn_1 = new TableColumn(table, SWT.CENTER);
		tblclmn_1.setWidth(190);
		tblclmn_1.setText("Mixture");
		
		TableColumn tblclmn_2 = new TableColumn(table, SWT.CENTER);
		tblclmn_2.setWidth(50);
		tblclmn_2.setText("Wt%");
		
		Composite composite_19 = new Composite(sashForm_2, SWT.NONE);
		tblclmn_2.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TableColumn tblclm = (TableColumn)arg0.widget;
				if (tblclm.getText().equals("Wt%")) tblclm.setText("Mol%");
				else if (tblclm.getText().equals("Mol%")) tblclm.setText("Wt%");
//				saveTable(states.getActiveState());
			}
			
		});
		sashForm_2.setWeights(new int[] {1, 20, 2, 20, 1});
		
		sashForm.setWeights(new int[] {1, 1, 1});
		
		// the editor must have the same size as the cell and must not be any smaller than 50 pixels
		tableEditor.horizontalAlignment = SWT.RIGHT;
		tableEditor.grabHorizontal = true;
		tableEditor.minimumWidth = 50;
//		new Label(group, SWT.NONE);

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
		if (!testTable()) return false;
		
		updateShared();
		return true;
	}
	
	@Override
	public void updateShared() throws Exception { 
		if (states!=null) {
			State activeState = states.getActiveState();

			if (table.getItemCount()==0) {
				activeState.setMixture(null);
				activeState.setPDesiredVals(null);
				activeState.setADesiredVals(null);
			} else {
				saveTable(activeState);
				activeState.setPDesiredVals();
				activeState.setADesiredVals();
				activeState.calculateEnvironmentalIndexes();
			}

			if (!activeState.equals(initialState)) {
				// reset values
				replacements = null;
				bestMixtures = null;
				activeState.setSingle(true);
			}

		}
		ParisWork.states = states;
		ParisWork.chemicals = chemicals;
		ParisWork.replacements = replacements;
		ParisWork.bestMixtures = bestMixtures;
	}
	
	@Override
	public void restore() throws Exception {
		shell = this.getShell();
		states = ParisWork.states;
		allChemicals = ParisWork.allChemicals;
		replacements = ParisWork.replacements;
		bestMixtures = ParisWork.bestMixtures;
		
		combo_1.setItems(states.getSystemNames());
		
		changeState(states.getActiveState());
		initialState = states.getActiveState().clone();
	}

	public void changeState(State state) throws Exception {
		states.setActiveState(state);
		
		combo_1.select(combo_1.indexOf(state.getSystemName()));
		combo_2.select(combo_2.indexOf(state.getSystemUnit().toString()));
		combo_3.select(combo_3.indexOf(state.getSystemTemp()));
		combo_4.select(combo_4.indexOf(state.getSystemPres()));

		chemicals = allChemicals.filterForLiquidPhase(state.tempConvertToSI());
		chemicals = chemicals.filterForVaporPressure();
		chemicals.normalizeEnvironmentalCategories();
		
		combo.select(state.getScreen0StackOption());
		combo.notifyListeners(SWT.Selection, new Event());
		reBuildTable(state);
	}
	public StackLayout getStack() {
		return stack;
	}

	public void setStack(StackLayout stack) {
		this.stack = stack;
	}
	
	public boolean testTable() {
		TableItem[] tableItem = table.getItems();
		double sum = 0;
		for (int i=0; i<tableItem.length; i++) {
			if (tableItem[i].getText(1)=="") {
				MessageBox dialog = new MessageBox(table.getShell(), SWT.ICON_ERROR | SWT.CANCEL);
				dialog.setText("Error");
				dialog.setMessage(table.getColumn(1).getText()+" cannot be blank");
				int returnCode = dialog.open();
				return false;
			} else {
				sum += Double.parseDouble(tableItem[i].getText(1));
			}
		}
		if (tableItem.length!=0 && sum != 100.0) {
			MessageBox dialog = new MessageBox(table.getShell(), SWT.ICON_ERROR | SWT.CANCEL);
			dialog.setText("Error");
			dialog.setMessage(table.getColumn(1).getText()+" must add up to 100%");
			int returnCode = dialog.open();
			return false;
		}
		return true;
	}
	
	public void reBuildTable(State activeState) {
		if (tableEditor.getEditor() != null) tableEditor.getEditor().dispose();
		table.removeAll();
		table.getColumn(1).setText(activeState.getScreen0TableHeader());
		Mixture mixture = activeState.getMixture();
		if (mixture==null) {
			activeState.setMixture(new Mixture(activeState.tempConvertToSI()));
		} else {
			Vector<Chemical> chemicals = mixture.getChemicals();
			if (table.getColumn(1).getText().equals("Wt%")) {
				Vector<Double> massFractions = mixture.getWghtFractions();
				for (int i=0; i<chemicals.size(); i++) {
					TableItem tableItem = new TableItem(table, SWT.NONE);
					tableItem.setText(0,chemicals.get(i).getName());
					tableItem.setText(1, String.valueOf(massFractions.get(i)*100.0));
				}
			} else { // translate to Mol%
				Vector<Double> molFractions = mixture.calculateMoleFractions();
				for (int i=0; i<chemicals.size(); i++) {
					TableItem tableItem = new TableItem(table, SWT.NONE);
					tableItem.setText(0, chemicals.get(i).getName());
					tableItem.setText(1, String.valueOf((molFractions.get(i))*100.0));
				}
			}
		}
	}
	
	public void saveTable(State state) {
		TableItem[] tableItem = table.getItems();
		state.setScreen0TableHeader(table.getColumn(1).getText());
		Mixture mixture = state.getMixture();
		
		if (table.getColumn(1).getText().equals("Wt%")) {
			Vector<Double> wghtFractions = mixture.getWghtFractions();
			wghtFractions.clear();
			for (int i=0; i<tableItem.length; i++) {
				wghtFractions.add(Double.parseDouble(tableItem[i].getText(1))/100.0);
			}
		} else { // given in Mol%
			Vector<Double> molFractions = new Vector<Double>(tableItem.length);
			for (int i=0; i<tableItem.length; i++) {
				molFractions.add(Double.parseDouble(tableItem[i].getText(1))/100.0);
			}
			mixture.setWghtFractions(mixture.calculateMassFractions(molFractions));
		}
		
	}

	public List getList() {
		return list_3;
	}

	public Button getBtn() {
		return btn_3;
	}

	public Reference getReference() {
		return reference;
	}	
	
}
