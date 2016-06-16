package parisWork;

import java.text.DecimalFormat;
import java.util.Vector;

import org.eclipse.swt.widgets.Composite;

import parisEvents.FocusListenerAdapter;
import parisInit.State;
import parisInit.States;
import parisInit.Units;
import unifac.UNIFACforPARIS;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Button;

public class Screen3 extends Screen {

	private Scale scale;
	private Button radioButton_1, radioButton_2;
	private List replacementList, mixturesList;
	final private StackLayout stack = new StackLayout();
	private Composite stackComposite;
	private SashForm sashForm_4, sashForm_5;
	
	private Text[][] textArray = new Text[10][5];
	private FocusListener[] focusListener = new FocusListener[10];
	
	private static DecimalFormat ef = new DecimalFormat("0.00E0");
	private static DecimalFormat pf = new DecimalFormat("#0.0#");
	private Table table;
	private Composite composite_32;
	
	private String[] initTol = new String[10];
	private String[] initProp = new String[10];
	boolean updateReplacements = false;

	public Screen3(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_1 = new SashForm(this, SWT.VERTICAL);
		
		Label lblNewLabel = new Label(sashForm_1, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Tahoma", 18, SWT.NORMAL));
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setText("Infinite Dilution Activity Coefficients\r\n\r\n");
		
		SashForm sashForm_2 = new SashForm(sashForm_1, SWT.NONE);
		
		Composite composite_1 = new Composite(sashForm_2, SWT.NONE);
		
		Composite composite_2 = new Composite(sashForm_2, SWT.NONE);
		composite_2.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite_4 = new Composite(composite_2, SWT.NONE);
		composite_4.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblSubstance = new Label(composite_4, SWT.NONE);
		lblSubstance.setAlignment(SWT.CENTER);
		lblSubstance.setText("Substance\r\nName");
		
		Label lblTolerance = new Label(composite_4, SWT.NONE);
		lblTolerance.setAlignment(SWT.CENTER);
		lblTolerance.setText("\r\nTolerance(%)");
		
		Label lblLower = new Label(composite_4, SWT.NONE);
		lblLower.setAlignment(SWT.CENTER);
		lblLower.setText("\r\nLower");
		
		Label lblDesired = new Label(composite_4, SWT.NONE);
		lblDesired.setAlignment(SWT.CENTER);
		lblDesired.setText("\r\nDesired");
		
		Label lblUpper = new Label(composite_4, SWT.NONE);
		lblUpper.setAlignment(SWT.CENTER);
		lblUpper.setText("\r\nUpper");
		
		Label lblReplacement = new Label(composite_4, SWT.NONE);
		lblReplacement.setAlignment(SWT.CENTER);
		lblReplacement.setText("\r\nReplacement");
		
		Composite composite_16 = new Composite(composite_4, SWT.NONE);
		
		Composite composite_5 = new Composite(composite_2, SWT.NONE);
		composite_5.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblEthanol = new Label(composite_5, SWT.NONE);
		lblEthanol.setText("\r\nethanol");
		lblEthanol.setAlignment(SWT.CENTER);
		
		Text text_0 = new Text(composite_5, SWT.BORDER | SWT.CENTER);
		text_0.setEditable(false);
		textArray[0][0] = text_0;
		
		Text text_1 = new Text(composite_5, SWT.BORDER | SWT.CENTER);
		text_1.setEditable(false);
		textArray[0][1] = text_1;
		
		Text text_2 = new Text(composite_5, SWT.BORDER | SWT.CENTER);
		text_2.setEditable(false);
		textArray[0][2] = text_2;
		
		Text text_3 = new Text(composite_5, SWT.BORDER | SWT.CENTER);
		text_3.setEditable(false);
		textArray[0][3] = text_3;
		
		Text text_4 = new Text(composite_5, SWT.BORDER | SWT.CENTER);
		text_4.setEditable(false);
		textArray[0][4] = text_4;
		
		Composite composite_17 = new Composite(composite_5, SWT.NONE);
		
		Composite composite_6 = new Composite(composite_2, SWT.NONE);
		composite_6.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblDiethyl = new Label(composite_6, SWT.NONE);
		lblDiethyl.setText("diethyl\r\nether\r\n");
		lblDiethyl.setAlignment(SWT.CENTER);
		
		Text text_5 = new Text(composite_6, SWT.BORDER | SWT.CENTER);
		text_5.setEditable(false);
		textArray[1][0] = text_5;
		
		Text text_6 = new Text(composite_6, SWT.BORDER | SWT.CENTER);
		text_6.setEditable(false);
		textArray[1][1] = text_6;
		
		Text text_7 = new Text(composite_6, SWT.BORDER | SWT.CENTER);
		text_7.setEditable(false);
		textArray[1][2] = text_7;
		
		Text text_8 = new Text(composite_6, SWT.BORDER | SWT.CENTER);
		text_8.setEditable(false);
		textArray[1][3] = text_8;
		
		Text text_9 = new Text(composite_6, SWT.BORDER | SWT.CENTER);
		text_9.setEditable(false);
		textArray[1][4] = text_9;
		
		Composite composite_18 = new Composite(composite_6, SWT.NONE);
		
		Composite composite_7 = new Composite(composite_2, SWT.NONE);
		composite_7.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblAcetone = new Label(composite_7, SWT.NONE);
		lblAcetone.setText("\r\nacetone\r\n");
		lblAcetone.setAlignment(SWT.CENTER);
		
		Text text_10 = new Text(composite_7, SWT.BORDER | SWT.CENTER);
		text_10.setEditable(false);
		textArray[2][0] = text_10;
		
		Text text_11 = new Text(composite_7, SWT.BORDER | SWT.CENTER);
		text_11.setEditable(false);
		textArray[2][1] = text_11;
		
		Text text_12 = new Text(composite_7, SWT.BORDER | SWT.CENTER);
		text_12.setEditable(false);
		textArray[2][2] = text_12;
		
		Text text_13 = new Text(composite_7, SWT.BORDER | SWT.CENTER);
		text_13.setEditable(false);
		textArray[2][3] = text_13;
		
		Text text_14 = new Text(composite_7, SWT.BORDER | SWT.CENTER);
		text_14.setEditable(false);
		textArray[2][4] = text_14;
		
		Composite composite_19 = new Composite(composite_7, SWT.NONE);
		composite_19.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite_8 = new Composite(composite_2, SWT.NONE);
		composite_8.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblWater = new Label(composite_8, SWT.NONE);
		lblWater.setText("\r\nwater");
		lblWater.setAlignment(SWT.CENTER);
		
		Text text_15 = new Text(composite_8, SWT.BORDER | SWT.CENTER);
		text_15.setEditable(false);
		textArray[3][0] = text_15;
		
		Text text_16 = new Text(composite_8, SWT.BORDER | SWT.CENTER);
		text_16.setEditable(false);
		textArray[3][1] = text_16;
		
		Text text_17 = new Text(composite_8, SWT.BORDER | SWT.CENTER);
		text_17.setEditable(false);
		textArray[3][2] = text_17;
		
		Text text_18 = new Text(composite_8, SWT.BORDER | SWT.CENTER);
		text_18.setEditable(false);
		textArray[3][3] = text_18;
		
		Text text_19 = new Text(composite_8, SWT.BORDER | SWT.CENTER);
		text_19.setEditable(false);
		textArray[3][4] = text_19;
		
		Composite composite_20 = new Composite(composite_8, SWT.NONE);
		
		Composite composite_9 = new Composite(composite_2, SWT.NONE);
		composite_9.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblBenzene = new Label(composite_9, SWT.NONE);
		lblBenzene.setText("\r\nbenzene\r\n");
		lblBenzene.setAlignment(SWT.CENTER);
		
		Text text_20 = new Text(composite_9, SWT.BORDER | SWT.CENTER);
		text_20.setEditable(false);
		textArray[4][0] = text_20;
		
		Text text_21 = new Text(composite_9, SWT.BORDER | SWT.CENTER);
		text_21.setEditable(false);
		textArray[4][1] = text_21;
		
		Text text_22 = new Text(composite_9, SWT.BORDER | SWT.CENTER);
		text_22.setEditable(false);
		textArray[4][2] = text_22;
		
		Text text_23 = new Text(composite_9, SWT.BORDER | SWT.CENTER);
		text_23.setEditable(false);
		textArray[4][3] = text_23;
		
		Text text_24 = new Text(composite_9, SWT.BORDER | SWT.CENTER);
		text_24.setEditable(false);
		textArray[4][4] = text_24;
		
		Composite composite_21 = new Composite(composite_9, SWT.NONE);
		
		Composite composite_10 = new Composite(composite_2, SWT.NONE);
		composite_10.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblCisHeptene = new Label(composite_10, SWT.NONE);
		lblCisHeptene.setText("cis-2-\r\nheptene\r\n");
		lblCisHeptene.setAlignment(SWT.CENTER);
		
		Text text_25 = new Text(composite_10, SWT.BORDER | SWT.CENTER);
		text_25.setEditable(false);
		textArray[5][0] = text_25;
		
		Text text_26 = new Text(composite_10, SWT.BORDER | SWT.CENTER);
		text_26.setEditable(false);
		textArray[5][1] = text_26;
		
		Text text_27 = new Text(composite_10, SWT.BORDER | SWT.CENTER);
		text_27.setEditable(false);
		textArray[5][2] = text_27;
		
		Text text_28 = new Text(composite_10, SWT.BORDER | SWT.CENTER);
		text_28.setEditable(false);
		textArray[5][3] = text_28;
		
		Text text_29 = new Text(composite_10, SWT.BORDER | SWT.CENTER);
		text_29.setEditable(false);
		textArray[5][4] = text_29;
		
		Composite composite_22 = new Composite(composite_10, SWT.NONE);
		
		Composite composite_11 = new Composite(composite_2, SWT.NONE);
		composite_11.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblNPropylChloride = new Label(composite_11, SWT.NONE);
		lblNPropylChloride.setText("n-propyl \r\nchloride");
		lblNPropylChloride.setAlignment(SWT.CENTER);
		
		Text text_30 = new Text(composite_11, SWT.BORDER | SWT.CENTER);
		text_30.setEditable(false);
		textArray[6][0] = text_30;
		
		Text text_31 = new Text(composite_11, SWT.BORDER | SWT.CENTER);
		text_31.setEditable(false);
		textArray[6][1] = text_31;
		
		Text text_32 = new Text(composite_11, SWT.BORDER | SWT.CENTER);
		text_32.setEditable(false);
		textArray[6][2] = text_32;
		
		Text text_33 = new Text(composite_11, SWT.BORDER | SWT.CENTER);
		text_33.setEditable(false);
		textArray[6][3] = text_33;
		
		Text text_34 = new Text(composite_11, SWT.BORDER | SWT.CENTER);
		text_34.setEditable(false);
		textArray[6][4] = text_34;
		
		Composite composite_23 = new Composite(composite_11, SWT.NONE);
		
		Composite composite_12 = new Composite(composite_2, SWT.NONE);
		composite_12.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblNHeptaDecane = new Label(composite_12, SWT.NONE);
		lblNHeptaDecane.setText("n-hepta-\r\ndecane");
		lblNHeptaDecane.setAlignment(SWT.CENTER);
		
		Text text_35 = new Text(composite_12, SWT.BORDER | SWT.CENTER);
		text_35.setEditable(false);
		textArray[7][0] = text_35;
		
		Text text_36 = new Text(composite_12, SWT.BORDER | SWT.CENTER);
		text_36.setEditable(false);
		textArray[7][1] = text_36;
		
		Text text_37 = new Text(composite_12, SWT.BORDER | SWT.CENTER);
		text_37.setEditable(false);
		textArray[7][2] = text_37;
		
		Text text_38 = new Text(composite_12, SWT.BORDER | SWT.CENTER);
		text_38.setEditable(false);
		textArray[7][3] = text_38;
		
		Text text_39 = new Text(composite_12, SWT.BORDER | SWT.CENTER);
		text_39.setEditable(false);
		textArray[7][4] = text_39;
		
		Composite composite_24 = new Composite(composite_12, SWT.NONE);
		
		Composite composite_13 = new Composite(composite_2, SWT.NONE);
		composite_13.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblNpropylAmine = new Label(composite_13, SWT.NONE);
		lblNpropylAmine.setText("n-propyl-\r\namine");
		lblNpropylAmine.setAlignment(SWT.CENTER);
		
		Text text_40 = new Text(composite_13, SWT.BORDER | SWT.CENTER);
		text_40.setEditable(false);
		textArray[8][0] = text_40;
		
		Text text_41 = new Text(composite_13, SWT.BORDER | SWT.CENTER);
		text_41.setEditable(false);
		textArray[8][1] = text_41;
		
		Text text_42 = new Text(composite_13, SWT.BORDER | SWT.CENTER);
		text_42.setEditable(false);
		textArray[8][2] = text_42;
		
		Text text_43 = new Text(composite_13, SWT.BORDER | SWT.CENTER);
		text_43.setEditable(false);
		textArray[8][3] = text_43;
		
		Text text_44 = new Text(composite_13, SWT.BORDER | SWT.CENTER);
		text_44.setEditable(false);
		textArray[8][4] = text_44;

		Composite composite_25 = new Composite(composite_13, SWT.NONE);

		Composite composite_14 = new Composite(composite_2, SWT.NONE);
		composite_14.setLayout(new FillLayout(SWT.HORIZONTAL));

		Label lblDimethylDisulfide = new Label(composite_14, SWT.NONE);
		lblDimethylDisulfide.setText("dimethyl\r\ndisulfide");
		lblDimethylDisulfide.setAlignment(SWT.CENTER);

		Text text_45 = new Text(composite_14, SWT.BORDER | SWT.CENTER);
		text_45.setEditable(false);
		textArray[9][0] = text_45;

		Text text_46 = new Text(composite_14, SWT.BORDER | SWT.CENTER);
		text_46.setEditable(false);
		textArray[9][1] = text_46;

		Text text_47 = new Text(composite_14, SWT.BORDER | SWT.CENTER);
		text_47.setEditable(false);
		textArray[9][2] = text_47;

		Text text_48 = new Text(composite_14, SWT.BORDER | SWT.CENTER);
		text_48.setEditable(false);
		textArray[9][3] = text_48;

		Text text_49 = new Text(composite_14, SWT.BORDER | SWT.CENTER);
		text_49.setEditable(false);
		textArray[9][4] = text_49;

		Composite composite_26 = new Composite(composite_14, SWT.NONE);

		Composite composite_15 = new Composite(composite_2, SWT.NONE);
		composite_15.setLayout(new FillLayout(SWT.HORIZONTAL));

		Label lblScaleFactor = new Label(composite_15, SWT.NONE);
		lblScaleFactor.setAlignment(SWT.CENTER);
		lblScaleFactor.setText("Tolerance\r\nScale Factor");

		scale = new Scale(composite_15, SWT.NONE);
		scale.setToolTipText("1.0");
		scale.setMaximum(40);
		scale.setMinimum(0);
		scale.setIncrement(10);
		scale.setSelection(10);
		scale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
        		Scale scale = (Scale)e.widget;
        		
         		int sel = scale.getSelection();
        		int max = scale.getMaximum();
        		int min = scale.getMinimum();
        		
         		if (sel==min) scale.setSelection(++sel);

        		double oldScale = Double.parseDouble(scale.getToolTipText());
        		double dscale = 4.0*(sel-min)/(max-min);
        		scale.setToolTipText(Double.toString(dscale));
        		
        		for (int i=0; i<10; i++) {
        			double tol = Double.parseDouble(textArray[i][0].getText());
        			textArray[i][0].setText(pf.format(tol*dscale/oldScale));
            		textArray[i][0].notifyListeners(SWT.FocusOut, new Event());
        		}
        		
			}
		});

		Composite composite_27 = new Composite(composite_15, SWT.NONE);

		Composite composite_28 = new Composite(composite_15, SWT.NONE);

		Composite composite_29 = new Composite(composite_15, SWT.NONE);

		Composite composite_30 = new Composite(composite_15, SWT.NONE);

		Composite composite_31 = new Composite(composite_15, SWT.NONE);

		SashForm sashForm_3 = new SashForm(sashForm_2, SWT.VERTICAL);
		
		Composite composite_32 = new Composite(sashForm_3, SWT.NONE);
		composite_32.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		radioButton_1 = new Button(composite_32, SWT.RADIO);
		radioButton_1.setText("Single");
		radioButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				stack.topControl = sashForm_4;
				stackComposite.layout();
				try {
					loadReplacements(states.getActiveState());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		radioButton_2 = new Button(composite_32, SWT.RADIO);
		radioButton_2.setText("Mixture");
		radioButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				stack.topControl = sashForm_5;
				stackComposite.layout();
				try {
					loadMixtures(states.getActiveState());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Label lblNewLabel_2 = new Label(sashForm_3, SWT.NONE);
		lblNewLabel_2.setAlignment(SWT.CENTER);
		lblNewLabel_2.setText("Solvent Replacement");
		
		stackComposite = new Composite(sashForm_3, SWT.NONE);
		stackComposite.setLayout(stack);
		
		sashForm_4 = new SashForm(stackComposite, SWT.VERTICAL);
		stack.topControl = sashForm_4;
		
		replacementList = new List(sashForm_4, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		replacementList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				double lb, ub;
				List list = (List)arg0.widget;
				int index = list.getSelectionIndex();
				State activeState = states.getActiveState();
				
				if (index>=0) {
					activeState.setSingle(true);
					activeState.setReplacementIndex(index);
					activeState.setReplacementTopIndex(list.getTopIndex());
					
					Chemical replacement = replacements.get(index);
					Color swtRed = SWTResourceManager.getColor(SWT.COLOR_RED);
					Color swtGreen = SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN);
					
					double value = replacement.getInfDilActCoef_ethanol();
					lb = Double.parseDouble(textArray[0][1].getText());
					ub = Double.parseDouble(textArray[0][3].getText());
					textArray[0][4].setText(ef.format(value));
					textArray[0][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = replacement.getInfDilActCoef_diethyl_ether();
					lb = Double.parseDouble(textArray[1][1].getText());
					ub = Double.parseDouble(textArray[1][3].getText());
					textArray[1][4].setText(ef.format(value));
					textArray[1][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = replacement.getInfDilActCoef_acetone();
					lb = Double.parseDouble(textArray[2][1].getText());
					ub = Double.parseDouble(textArray[2][3].getText());
					textArray[2][4].setText(ef.format(value));
					textArray[2][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = replacement.getInfDilActCoef_water();
					lb = Double.parseDouble(textArray[3][1].getText());
					ub = Double.parseDouble(textArray[3][3].getText());
					textArray[3][4].setText(ef.format(value));
					textArray[3][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = replacement.getInfDilActCoef_benzene();
					lb = Double.parseDouble(textArray[4][1].getText());
					ub = Double.parseDouble(textArray[4][3].getText());
					textArray[4][4].setText(ef.format(value));
					textArray[4][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = replacement.getInfDilActCoef_cis_2_heptene();
					lb = Double.parseDouble(textArray[5][1].getText());
					ub = Double.parseDouble(textArray[5][3].getText());
					textArray[5][4].setText(ef.format(value));
					textArray[5][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = replacement.getInfDilActCoef_n_propyl_chloride();
					lb = Double.parseDouble(textArray[6][1].getText());
					ub = Double.parseDouble(textArray[6][3].getText());
					textArray[6][4].setText(ef.format(value));
					textArray[6][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = replacement.getInfDilActCoef_n_heptadecane();
					lb = Double.parseDouble(textArray[7][1].getText());
					ub = Double.parseDouble(textArray[7][3].getText());
					textArray[7][4].setText(ef.format(value));
					textArray[7][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = replacement.getInfDilActCoef_n_propylamine();
					lb = Double.parseDouble(textArray[8][1].getText());
					ub = Double.parseDouble(textArray[8][3].getText());
					textArray[8][4].setText(ef.format(value));
					textArray[8][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = replacement.getInfDilActCoef_dimethyl_disulfide();
					lb = Double.parseDouble(textArray[9][1].getText());
					ub = Double.parseDouble(textArray[9][3].getText());
					textArray[9][4].setText(ef.format(value));
					textArray[9][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
				}
			}
			
		});
		
		sashForm_4.setWeights(new int[] {10});
		
		sashForm_5 = new SashForm(stackComposite, SWT.VERTICAL);
		
		mixturesList = new List(sashForm_5, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		mixturesList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				double lb, ub;
				List list = (List)arg0.widget;
				int index = list.getSelectionIndex();
				State activeState = states.getActiveState();
				
				if (index>=0) {
					activeState.setSingle(false);
					activeState.setReplacementIndex(index);
					activeState.setReplacementTopIndex(list.getTopIndex());
					
					Mixture mixture = bestMixtures.get(index);		
					Color swtRed = SWTResourceManager.getColor(SWT.COLOR_RED);
					Color swtGreen = SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN);
					
					// first update the table
					table.clearAll();
					table.setItemCount(mixture.getChemicals().size());
					for (int i=0; i<mixture.getChemicals().size(); i++) {
						table.getItem(i).setText(0, mixture.getChemicals().get(i).getName());
						table.getItem(i).setText(1, Double.toString(mixture.getWghtFractions().get(i)*100.0));
					}
					
					// then update the replacement values
					double value = mixture.getInfDilActCoef_ethanol();
					lb = Double.parseDouble(textArray[0][1].getText());
					ub = Double.parseDouble(textArray[0][3].getText());
					textArray[0][4].setText(ef.format(value));
					textArray[0][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = mixture.getInfDilActCoef_diethyl_ether();
					lb = Double.parseDouble(textArray[1][1].getText());
					ub = Double.parseDouble(textArray[1][3].getText());
					textArray[1][4].setText(ef.format(value));
					textArray[1][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = mixture.getInfDilActCoef_acetone();
					lb = Double.parseDouble(textArray[2][1].getText());
					ub = Double.parseDouble(textArray[2][3].getText());
					textArray[2][4].setText(ef.format(value));
					textArray[2][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = mixture.getInfDilActCoef_water();
					lb = Double.parseDouble(textArray[3][1].getText());
					ub = Double.parseDouble(textArray[3][3].getText());
					textArray[3][4].setText(ef.format(value));
					textArray[3][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = mixture.getInfDilActCoef_benzene();
					lb = Double.parseDouble(textArray[4][1].getText());
					ub = Double.parseDouble(textArray[4][3].getText());
					textArray[4][4].setText(ef.format(value));
					textArray[4][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = mixture.getInfDilActCoef_cis_2_heptene();
					lb = Double.parseDouble(textArray[5][1].getText());
					ub = Double.parseDouble(textArray[5][3].getText());
					textArray[5][4].setText(ef.format(value));
					textArray[5][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = mixture.getInfDilActCoef_n_propyl_chloride();
					lb = Double.parseDouble(textArray[6][1].getText());
					ub = Double.parseDouble(textArray[6][3].getText());
					textArray[6][4].setText(ef.format(value));
					textArray[6][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = mixture.getInfDilActCoef_n_heptadecane();
					lb = Double.parseDouble(textArray[7][1].getText());
					ub = Double.parseDouble(textArray[7][3].getText());
					textArray[7][4].setText(ef.format(value));
					textArray[7][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = mixture.getInfDilActCoef_n_propylamine();
					lb = Double.parseDouble(textArray[8][1].getText());
					ub = Double.parseDouble(textArray[8][3].getText());
					textArray[8][4].setText(ef.format(value));
					textArray[8][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
					
					value = mixture.getInfDilActCoef_dimethyl_disulfide();
					lb = Double.parseDouble(textArray[9][1].getText());
					ub = Double.parseDouble(textArray[9][3].getText());
					textArray[9][4].setText(ef.format(value));
					textArray[9][4].setForeground((lb <= value && value <= ub) ? swtGreen : swtRed);
				}
			}
			
		});
		
		table = new Table(sashForm_5, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmn_1 = new TableColumn(table, SWT.NONE);
		tblclmn_1.setWidth(100);
		tblclmn_1.setText("Solvent");
		
		TableColumn tblclmn_2 = new TableColumn(table, SWT.NONE);
		tblclmn_2.setWidth(100);
		tblclmn_2.setText("Wt%");
		sashForm_5.setWeights(new int[] {7, 3});
		
		Composite composite = new Composite(sashForm_3, SWT.NONE);
		sashForm_3.setWeights(new int[] {5, 5, 100, 10});
		
		Composite composite_3 = new Composite(sashForm_2, SWT.NONE);
		sashForm_2.setWeights(new int[] {1, 64, 16, 1});
		
		Composite composite_0 = new Composite(sashForm_1, SWT.NONE);
		sashForm_1.setWeights(new int[] {1, 12, 1});

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
		
		removeFocusListeners();
		
		saveTolerances(states.getActiveState());
		saveProperties(states.getActiveState());
		
		if (updateReplacements && replacements!=null) replacements.bubbleSort();

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
		
		loadTolerances(states.getActiveState());
		loadProperties(states.getActiveState());
		
		Mixture mixture = states.getActiveState().getMixture();
		this.scale.setEnabled(mixture!=null && mixture.getChemicals2() !=null && mixture.getChemicals2().length!=0);
		
		radioButton_2.setEnabled(bestMixtures!=null);
		if (states.getActiveState().isSingle()) {
			radioButton_1.setSelection(true);
			radioButton_1.notifyListeners(SWT.Selection, new Event());
			radioButton_2.setSelection(false);
		} else {
			radioButton_2.setSelection(true);
			radioButton_2.notifyListeners(SWT.Selection, new Event());
			radioButton_1.setSelection(false);
		}
		
		for (int i=1; i<initTol.length; i++) {
			initTol[i] = textArray[i][0].getText();
			initProp[i] = textArray[i][2].getText();
		}
		
		addFocusListeners(states.getActiveState());
		
		updateReplacements = false;
		
	}
	
	private void loadTolerances(State activeState) {
		
		blankoutTolerances();
		
		Mixture mixture = activeState.getMixture();
		if (mixture==null || mixture.getChemicals().size()==0) return;
		
		double[] tolerances = activeState.getATolerances();
		
		for (int i=0; i<10; i++) {
			textArray[i][0].setText(pf.format(tolerances[i]));
		}
		scale.setSelection(activeState.getAScale());
		int max = scale.getMaximum();
		int min = scale.getMinimum();
		double dscale = 4.0*(scale.getSelection()-min)/(max-min);
		scale.setToolTipText(Double.toString(dscale));
	}
	
	private void saveTolerances(State activeState) {
		
		Mixture mixture = activeState.getMixture();
		if (mixture==null || mixture.getChemicals().size()==0) return;
		
		double[] tolerances = activeState.getATolerances();
		
		for (int i=0; i<10; i++) {
			if (!textArray[i][0].equals(initTol[i])) {
				updateReplacements = true;
				tolerances[i] = Double.parseDouble(textArray[i][0].getText());
			}
		}
		activeState.setAScale(scale.getSelection());
		
	}
	
	private void blankoutTolerances() {
		for (int i=0; i<10; i++) {
			textArray[i][0].setText("");
		}
	}
	
	private void loadProperties(State activeState) throws Exception {
		
		blankoutActivityCoefficients();
		
		Mixture mixture = activeState.getMixture();
		if (mixture==null || mixture.getChemicals().size()==0) return;
		
		if (activeState.getADesiredVals()==null) activeState.setADesiredVals();
		
		displayActivityCoefficientsWithBounds(activeState);
	}
	
	private void saveProperties(State activeState) {
		double[] desiredVals = activeState.getADesiredVals();
		if (desiredVals == null) return;
		for (int i=0; i<10; i++) {
			if (!textArray[i][2].equals(initProp[i])) {
				updateReplacements = true;
				desiredVals[i] = Double.parseDouble(textArray[i][2].getText());
			}
		}
	}
	
	private void blankoutActivityCoefficients() {
		
		for (int i=0; i<10; i++) {
			textArray[i][1].setText("");
			textArray[i][2].setText("");
			textArray[i][3].setText("");
		}
		
	}
	
	private void displayActivityCoefficientsWithBounds(State activeState) {
		
		double[] tolerances = activeState.getATolerances();
		double[] value = activeState.getADesiredVals();

		for (int i=0; i<10; i++) {
			textArray[i][1].setText(ef.format(getLb(value[i], tolerances[i])));
			textArray[i][2].setText(ef.format(value[i]));
			textArray[i][3].setText(ef.format(getUb(value[i], tolerances[i])));
		}
		
	}
	
	private double getLb(double value, double tolerance) {
		double lb = (1.0-tolerance/100.0) * value;
		if (lb<0) lb = 0.0;
		return lb;
	}
	
	private double getUb(double value, double tolerance) {		
		return (1.0+tolerance/100.0) * value;
	}
	
	private void loadReplacements(State activeState) throws Exception {
		
		for (int i=0; i<10; i++) {
			textArray[i][4].setText("");
		}
		replacementList.removeAll();
		
		Mixture mixture = activeState.getMixture();
		if (mixture==null || mixture.getChemicals2() ==null || mixture.getChemicals2().length==0) return;
		
		if (activeState.getPDesiredVals()==null) activeState.setPDesiredVals();
		
		if (activeState.getADesiredVals()==null) activeState.setADesiredVals();

		if (replacements==null) {
			replacements = new Replacements(activeState, chemicals);
		}
		
		replacementList.setItems(replacements.getNames());
		
		if (!activeState.isSingle()) {
			activeState.setReplacementTopIndex(0);
			activeState.setReplacementIndex(0);
			activeState.setSingle(true);
		}
		replacementList.setTopIndex(activeState.getReplacementTopIndex());
		replacementList.select(activeState.getReplacementIndex());
		
		replacementList.notifyListeners(SWT.Selection, new Event());

	}
	
	private void loadMixtures(State activeState) throws Exception {
		
		for (int i=0; i<10; i++) {
			textArray[i][4].setText("");
		}
		mixturesList.removeAll();
		
//		Mixture mixture = activeState.getMixture();
//		if (mixture==null || mixture.getChemicals().size()==0) return;
//		
//		if (activeState.getPDesiredVals()==null) activeState.setPDesiredVals();
//		
//		if (activeState.getADesiredVals()==null) activeState.setADesiredVals();
//
//		if (replacements==null) {
//			replacements = new Replacements(activeState, chemicals);
//			ParisWork.replacements = replacements;
//		}
		
		mixturesList.setItems(generateMixtureNames(bestMixtures));
		
		if (activeState.isSingle()) {
			activeState.setReplacementTopIndex(0);
			activeState.setReplacementIndex(0);
			activeState.setSingle(false);
		}
		mixturesList.setTopIndex(activeState.getReplacementTopIndex());
		mixturesList.select(activeState.getReplacementIndex());

		mixturesList.notifyListeners(SWT.Selection, new Event());
		
	}
	
	private void addFocusListeners(State activeState) {
		
		Mixture mixture = activeState.getMixture();
		if (mixture==null || mixture.getChemicals().size()==0) return;
		
		FocusListener listener;
		for (int i=0; i<10; i++) {
			listener = new FocusListenerAdapter(textArray, i, ef, null, activeState.getATolerances());  // unit is not used
			for (int j=0; j<4; j++) {
				textArray[i][j].addFocusListener(listener);
				textArray[i][j].setEditable(true);
			}
			focusListener[i] = listener;
		}
	}
	
	private void removeFocusListeners() {
		
		Mixture mixture = states.getActiveState().getMixture();
		if (mixture==null || mixture.getChemicals().size()==0) return;
		
		FocusListener listener;
		for (int i=0; i<10; i++) {
			listener = focusListener[i];
			for (int j=0; j<4; j++) {
				textArray[i][j].removeFocusListener(listener);
				textArray[i][j].setEditable(false);
			}
		}
	}
	
	private String[] generateMixtureNames(Vector<Mixture> bestMixtures) {
		String[] names = new String[bestMixtures.size()];
		for (int i=0; i<names.length; i++) {
			names[i] = bestMixtures.get(i).getMixtureName();
		}
		return names;
	}

	public Text[][] getTextArray() {
		return textArray;
	}
	
}
