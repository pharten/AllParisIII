package parisWork;

import java.text.DecimalFormat;
import java.util.Vector;

import org.eclipse.swt.widgets.Composite;

import parisInit.State;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.custom.SashForm;

public class Screen1 extends Screen {

	private Text text_9;
	private Text text_10;
	private Text text_11;
	private Text text_12;
	private Text text_13;
	private Text text_14;
	private Text text_15;
	private Text text_16;
	private Text text_17;
	private Text text_18;
	private Text text_20;
	private Text text_21;
	private Text text_22;
	private Text text_23;
	private Text text_24;
	private Text text_25;
	private Text text_26;
	private Text text_27;
	private Text text_28;
	private Text text_29;
	private Text text_31;
	private Text text_32;
	private Text text_33;
	private Text text_34;
	private Text text_35;
	private Text text_36;
	private Text text_38;
	private Text text_39;
	private Text text_40;
	private Text text_42;
	private Text text_43;
	private Text text_44;
	private Text text_45;
	private Text text_46;
	private Text text_47;
	private Text text_48;
	private Text text_49;
	private Text text_50;
	private Text text_51;
	private Text text_53;
	private Text text_52;
	private Text text_54;
	private Text text_55;
	private Text text_56;
	private Text text_57;
	private Text text_58;
	private Text text_59;
	private Text text_60;
	private Text text_61;
	private Composite composite_26;
	private Composite composite_9;
	private Composite composite_8;
	private Label lblImpactFactors;
	private Label label_22;
	private Text text_8;
	private Text text_19;
	private Text text_30;
	private Text text_41;
	private Label label_23;
	private Composite composite_0;
	private Text text_0;
	private Button button;
	private Composite composite_1;
	private Text text_1;
	private Composite composite_2;
	private Text text_2;
	private Composite composite_3;
	private Text text_3;
	private Composite composite_4;
	private Text text_4;
	private Composite composite_5;
	private Text text_5;
	private Composite composite_6;
	private Text text_6;
	private Composite composite_7;
	private Text text_7;
	private Composite composite;
	private Text text_37;
	private SashForm sashForm_2;
	private Composite composite_20;
	private Composite composite_22;
	private Color swtWhite = this.getDisplay().getSystemColor(SWT.COLOR_WHITE);
	private Color swtGray = SWTResourceManager.getColor(236, 233, 216);
	
	private static DecimalFormat ef = new DecimalFormat("0.00E0");
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws Exception 
	 */
	public Screen1(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_1 = new SashForm(this, SWT.VERTICAL);
		
		Composite composite_29 = new Composite(sashForm_1, SWT.BORDER);
		composite_29.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblNewLabel = new Label(composite_29, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Tahoma", 16, SWT.NORMAL));
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setText("Potential Environmental Impact\r\nScores For Current Formulation\r\n\r\n");
		
		sashForm_2 = new SashForm(sashForm_1, SWT.NONE);
		
		composite_20 = new Composite(sashForm_2, SWT.NONE);
		
		composite_8 = new Composite(sashForm_2, SWT.NONE);
		composite_8.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite_24 = new Composite(composite_8, SWT.NONE);
		
		lblImpactFactors = new Label(composite_8, SWT.NONE);
		lblImpactFactors.setText("\r\nImpact Factors");
		lblImpactFactors.setFont(SWTResourceManager.getFont("Tahoma", 7, SWT.NORMAL));
		lblImpactFactors.setAlignment(SWT.CENTER);
		
		label_22 = new Label(composite_8, SWT.NONE);
		label_22.setText("\r\nChemicals");
		label_22.setFont(SWTResourceManager.getFont("Tahoma", 7, SWT.NORMAL));
		label_22.setAlignment(SWT.CENTER);
		
		text_8 = new Text(composite_8, SWT.BORDER);
		
		text_19 = new Text(composite_8, SWT.BORDER);
		
		text_30 = new Text(composite_8, SWT.BORDER);
		
		text_41 = new Text(composite_8, SWT.BORDER);
		
		Composite composite_25 = new Composite(composite_8, SWT.NONE);
		
		label_23 = new Label(composite_8, SWT.NONE);
		label_23.setText("\r\nTotals");
		label_23.setFont(SWTResourceManager.getFont("Tahoma", 7, SWT.NORMAL));
		label_23.setAlignment(SWT.CENTER);
		
		Composite composite_23 = new Composite(sashForm_2, SWT.NONE);
		composite_23.setSize(533, 100);
		composite_23.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		composite_9 = new Composite(composite_23, SWT.NONE);
		FillLayout fl_composite_9 = new FillLayout(SWT.VERTICAL);
		fl_composite_9.marginWidth = 2;
		composite_9.setLayout(fl_composite_9);
		
		composite = new Composite(composite_9, SWT.NONE);
		
		Composite composite_3b = new Composite(composite_9, SWT.NONE);
		composite_3b.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		button = new Button(composite_3b, SWT.NONE);
		button.setText("Default");
		button.setFont(SWTResourceManager.getFont("Tahoma", 7, SWT.NORMAL));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				text_0.setText("5");
				text_1.setText("5");
				text_2.setText("5");
				text_3.setText("5");
				text_4.setText("5");
				text_5.setText("5");
				text_6.setText("5");
				text_7.setText("5");
				loadDynamicValues(states.getActiveState());
			}
		});
		
		
		Label lblNewLabel_4 = new Label(composite_9, SWT.NONE);
		lblNewLabel_4.setFont(SWTResourceManager.getFont("Tahoma", 7, SWT.NORMAL));
		lblNewLabel_4.setAlignment(SWT.CENTER);
		lblNewLabel_4.setText("\r\nWt%");
		
		text_9 = new Text(composite_9, SWT.BORDER);
		
		text_20 = new Text(composite_9, SWT.BORDER);
		
		text_31 = new Text(composite_9, SWT.BORDER);
		
		text_42 = new Text(composite_9, SWT.BORDER);
		
		Composite composite_19 = new Composite(composite_9, SWT.NONE);
		
		text_61 = new Text(composite_9, SWT.BORDER);
		text_61.setEditable(false);
		
		Composite composite_10 = new Composite(composite_23, SWT.NONE);
		composite_10.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblNewLabel_1 = new Label(composite_10, SWT.CENTER);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Tahoma", 7, SWT.NORMAL));
		lblNewLabel_1.setAlignment(SWT.CENTER);
		lblNewLabel_1.setText("\r\nIngestion");
		
		composite_0 = new Composite(composite_10, SWT.NONE);
		composite_0.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text_0 = new Text(composite_0, SWT.BORDER | SWT.CENTER | SWT.MULTI);
		text_0.setText("5");
		
		Slider slider_0 = new Slider(composite_0, SWT.VERTICAL);
		slider_0.setMaximum(10);
		slider_0.setSelection(5);
		slider_0.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int value = Integer.parseInt(text_0.getText());
				if (e.detail==SWT.ARROW_UP && value < 10) {
					text_0.setText(Integer.toString(value+1));
				} else if (e.detail==SWT.ARROW_DOWN && value > 0) {
					text_0.setText(Integer.toString(value-1));
				}
				loadDynamicValues(states.getActiveState());
			}
		});
		
		Label label_4 = new Label(composite_10, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_10 = new Text(composite_10, SWT.BORDER);
		
		text_21 = new Text(composite_10, SWT.BORDER);
		
		text_32 = new Text(composite_10, SWT.BORDER);
		
		text_43 = new Text(composite_10, SWT.BORDER);
		
		Label label = new Label(composite_10, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_52 = new Text(composite_10, SWT.BORDER);
		
		Composite composite_11 = new Composite(composite_23, SWT.NONE);
		composite_11.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblInhalation = new Label(composite_11, SWT.CENTER);
		lblInhalation.setFont(SWTResourceManager.getFont("Tahoma", 7, SWT.NORMAL));
		lblInhalation.setText("\r\nInhalation");
		lblInhalation.setAlignment(SWT.CENTER);
		
		composite_1 = new Composite(composite_11, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text_1 = new Text(composite_1, SWT.BORDER | SWT.CENTER | SWT.MULTI);
		text_1.setText("5");
		
		Slider slider_1 = new Slider(composite_1, SWT.VERTICAL);
		slider_1.setMaximum(10);
		slider_1.setSelection(5);
		slider_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int value = Integer.parseInt(text_1.getText());
				if (e.detail==SWT.ARROW_UP && value < 10) {
					text_1.setText(Integer.toString(value+1));
				} else if (e.detail==SWT.ARROW_DOWN && value > 0) {
					text_1.setText(Integer.toString(value-1));
				}
				loadDynamicValues(states.getActiveState());
			}
		});
		
		Label label_5 = new Label(composite_11, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_11 = new Text(composite_11, SWT.BORDER);
		
		text_22 = new Text(composite_11, SWT.BORDER);
		
		text_33 = new Text(composite_11, SWT.BORDER);
		
		text_44 = new Text(composite_11, SWT.BORDER);
		
		Label label_1 = new Label(composite_11, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_53 = new Text(composite_11, SWT.BORDER);
		
		Composite composite_12 = new Composite(composite_23, SWT.NONE);
		composite_12.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblDermal = new Label(composite_12, SWT.NONE);
		lblDermal.setFont(SWTResourceManager.getFont("Tahoma", 7, SWT.NORMAL));
		lblDermal.setText("\r\nDermal");
		lblDermal.setAlignment(SWT.CENTER);
		
		composite_2 = new Composite(composite_12, SWT.NONE);
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text_2 = new Text(composite_2, SWT.BORDER | SWT.CENTER | SWT.MULTI);
		text_2.setText("5");
		
		Slider slider_2 = new Slider(composite_2, SWT.VERTICAL);
		slider_2.setMaximum(10);
		slider_2.setSelection(5);
		slider_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int value = Integer.parseInt(text_2.getText());
				if (e.detail==SWT.ARROW_UP && value < 10) {
					text_2.setText(Integer.toString(value+1));
				} else if (e.detail==SWT.ARROW_DOWN && value > 0) {
					text_2.setText(Integer.toString(value-1));
				}
				loadDynamicValues(states.getActiveState());
			}
		});
		
		Label label_6 = new Label(composite_12, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_12 = new Text(composite_12, SWT.BORDER);
		
		text_23 = new Text(composite_12, SWT.BORDER);
		
		text_34 = new Text(composite_12, SWT.BORDER);
		
		text_45 = new Text(composite_12, SWT.BORDER);
		
		Label label_2 = new Label(composite_12, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_54 = new Text(composite_12, SWT.BORDER);
		
		Composite composite_13 = new Composite(composite_23, SWT.NONE);
		composite_13.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblAquaticTox = new Label(composite_13, SWT.NONE);
		lblAquaticTox.setFont(SWTResourceManager.getFont("Tahoma", 7, SWT.NORMAL));
		lblAquaticTox.setText("\r\nAquatic Toxicity");
		lblAquaticTox.setAlignment(SWT.CENTER);
		
		composite_3 = new Composite(composite_13, SWT.NONE);
		composite_3.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text_3 = new Text(composite_3, SWT.BORDER | SWT.CENTER | SWT.MULTI);
		text_3.setText("5");
		
		Slider slider_3 = new Slider(composite_3, SWT.VERTICAL);
		slider_3.setMaximum(10);
		slider_3.setSelection(5);
		slider_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int value = Integer.parseInt(text_3.getText());
				if (e.detail==SWT.ARROW_UP && value < 10) {
					text_3.setText(Integer.toString(value+1));
				} else if (e.detail==SWT.ARROW_DOWN && value > 0) {
					text_3.setText(Integer.toString(value-1));
				}
				loadDynamicValues(states.getActiveState());
			}
		});
		
		Label label_7 = new Label(composite_13, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_13 = new Text(composite_13, SWT.BORDER);
		
		text_24 = new Text(composite_13, SWT.BORDER);
		
		text_35 = new Text(composite_13, SWT.BORDER);
		
		text_46 = new Text(composite_13, SWT.BORDER);
		
		Label label_3 = new Label(composite_13, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_55 = new Text(composite_13, SWT.BORDER);
		
		Composite composite_14 = new Composite(composite_23, SWT.NONE);
		composite_14.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblGwp = new Label(composite_14, SWT.NONE);
		lblGwp.setFont(SWTResourceManager.getFont("Tahoma", 7, SWT.NORMAL));
		lblGwp.setText("\r\nGWP");
		lblGwp.setAlignment(SWT.CENTER);
		
		composite_4 = new Composite(composite_14, SWT.NONE);
		composite_4.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text_4 = new Text(composite_4, SWT.BORDER | SWT.CENTER | SWT.MULTI);
		text_4.setText("5");
		
		Slider slider_4 = new Slider(composite_4, SWT.VERTICAL);
		slider_4.setMaximum(10);
		slider_4.setSelection(5);
		slider_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int value = Integer.parseInt(text_4.getText());
				if (e.detail==SWT.ARROW_UP && value < 10) {
					text_4.setText(Integer.toString(value+1));
				} else if (e.detail==SWT.ARROW_DOWN && value > 0) {
					text_4.setText(Integer.toString(value-1));
				}
				loadDynamicValues(states.getActiveState());
			}
		});
		
		Label label_8 = new Label(composite_14, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_14 = new Text(composite_14, SWT.BORDER);
		
		text_25 = new Text(composite_14, SWT.BORDER);
		
		text_36 = new Text(composite_14, SWT.BORDER);
		
		text_47 = new Text(composite_14, SWT.BORDER);
		
		Label label_12 = new Label(composite_14, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_56 = new Text(composite_14, SWT.BORDER);
		
		Composite composite_15 = new Composite(composite_23, SWT.NONE);
		composite_15.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblOdp = new Label(composite_15, SWT.NONE);
		lblOdp.setFont(SWTResourceManager.getFont("Tahoma", 7, SWT.NORMAL));
		lblOdp.setText("\r\nODP");
		lblOdp.setAlignment(SWT.CENTER);
		
		composite_5 = new Composite(composite_15, SWT.NONE);
		composite_5.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text_5 = new Text(composite_5, SWT.BORDER | SWT.CENTER | SWT.MULTI);
		text_5.setText("5");
		
		Slider slider_5 = new Slider(composite_5, SWT.VERTICAL);
		slider_5.setMaximum(10);
		slider_5.setSelection(5);
		slider_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int value = Integer.parseInt(text_5.getText());
				if (e.detail==SWT.ARROW_UP && value < 10) {
					text_5.setText(Integer.toString(value+1));
				} else if (e.detail==SWT.ARROW_DOWN && value > 0) {
					text_5.setText(Integer.toString(value-1));
				}
				loadDynamicValues(states.getActiveState());
			}
		});
		
		Label label_9 = new Label(composite_15, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_15 = new Text(composite_15, SWT.BORDER);
		
		text_26 = new Text(composite_15, SWT.BORDER);
		
		text_37 = new Text(composite_15, SWT.BORDER);
		
		text_48 = new Text(composite_15, SWT.BORDER);
		
		Label label_13 = new Label(composite_15, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_57 = new Text(composite_15, SWT.BORDER);
		
		Composite composite_16 = new Composite(composite_23, SWT.NONE);
		composite_16.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblPcop = new Label(composite_16, SWT.NONE);
		lblPcop.setFont(SWTResourceManager.getFont("Tahoma", 7, SWT.NORMAL));
		lblPcop.setText("\r\nPCOP");
		lblPcop.setAlignment(SWT.CENTER);
		
		composite_6 = new Composite(composite_16, SWT.NONE);
		composite_6.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text_6 = new Text(composite_6, SWT.BORDER | SWT.CENTER | SWT.MULTI);
		text_6.setText("5");
		
		Slider slider_6 = new Slider(composite_6, SWT.VERTICAL);
		slider_6.setMaximum(10);
		slider_6.setSelection(5);
		slider_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int value = Integer.parseInt(text_6.getText());
				if (e.detail==SWT.ARROW_UP && value < 10) {
					text_6.setText(Integer.toString(value+1));
				} else if (e.detail==SWT.ARROW_DOWN && value > 0) {
					text_6.setText(Integer.toString(value-1));
				}
				loadDynamicValues(states.getActiveState());
			}
		});
		
		Label label_10 = new Label(composite_16, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_16 = new Text(composite_16, SWT.BORDER);
		
		text_27 = new Text(composite_16, SWT.BORDER);
		
		text_38 = new Text(composite_16, SWT.BORDER);
		
		text_49 = new Text(composite_16, SWT.BORDER);
		
		Label label_14 = new Label(composite_16, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_58 = new Text(composite_16, SWT.BORDER);
		
		Composite composite_17 = new Composite(composite_23, SWT.NONE);
		composite_17.setLayout(new FillLayout(SWT.VERTICAL));
		
		Label lblAcidRain = new Label(composite_17, SWT.NONE);
		lblAcidRain.setFont(SWTResourceManager.getFont("Tahoma", 7, SWT.NORMAL));
		lblAcidRain.setText("\r\nAcid Rain");
		lblAcidRain.setAlignment(SWT.CENTER);
		
		composite_7 = new Composite(composite_17, SWT.NONE);
		composite_7.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text_7 = new Text(composite_7, SWT.BORDER | SWT.CENTER | SWT.MULTI);
		text_7.setText("5");
		
		Slider slider_7 = new Slider(composite_7, SWT.VERTICAL);
		slider_7.setMaximum(10);
		slider_7.setSelection(5);
		slider_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int value = Integer.parseInt(text_7.getText());
				if (e.detail==SWT.ARROW_UP && value < 10) {
					text_7.setText(Integer.toString(value+1));
				} else if (e.detail==SWT.ARROW_DOWN && value > 0) {
					text_7.setText(Integer.toString(value-1));
				}
				loadDynamicValues(states.getActiveState());
			}
		});
		
		Label label_11 = new Label(composite_17, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_17 = new Text(composite_17, SWT.BORDER);
		
		text_28 = new Text(composite_17, SWT.BORDER);
		
		text_39 = new Text(composite_17, SWT.BORDER);
		
		text_50 = new Text(composite_17, SWT.BORDER);
		
		Label label_15 = new Label(composite_17, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_59 = new Text(composite_17, SWT.BORDER);
		
		Composite composite_18 = new Composite(composite_23, SWT.NONE);
		FillLayout fl_composite_18 = new FillLayout(SWT.VERTICAL);
		fl_composite_18.marginWidth = 2;
		composite_18.setLayout(fl_composite_18);
		
		composite_26 = new Composite(composite_18, SWT.NONE);
		composite_26.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite_27 = new Composite(composite_18, SWT.NONE);
		
		Label label_18 = new Label(composite_18, SWT.None);
		label_18.setText("\r\nTotals");
		label_18.setFont(SWTResourceManager.getFont("Tahoma", 7, SWT.NORMAL));
		label_18.setAlignment(SWT.CENTER);
		
		text_18 = new Text(composite_18, SWT.BORDER);
		
		text_29 = new Text(composite_18, SWT.BORDER);
		
		text_40 = new Text(composite_18, SWT.BORDER);
		
		text_51 = new Text(composite_18, SWT.BORDER);
		
		Label label_17 = new Label(composite_18, SWT.SEPARATOR | SWT.HORIZONTAL);
		
		text_60 = new Text(composite_18, SWT.BORDER);
		
		composite_22 = new Composite(sashForm_2, SWT.NONE);
		sashForm_2.setWeights(new int[] {1, 30, 80, 1});
		
		Composite composite_21 = new Composite(sashForm_1, SWT.NONE);
		composite_21.setLayout(new FillLayout(SWT.HORIZONTAL));
		sashForm_1.setWeights(new int[] {1, 6, 1});

		text_0.setEditable(false);
		text_1.setEditable(false);
		text_2.setEditable(false);
		text_3.setEditable(false);
		text_4.setEditable(false);
		text_5.setEditable(false);
		text_6.setEditable(false);
		text_7.setEditable(false);
		text_8.setEditable(false);
		text_9.setEditable(false);
		text_10.setEditable(false);
		text_11.setEditable(false);
		text_12.setEditable(false);
		text_13.setEditable(false);
		text_14.setEditable(false);
		text_15.setEditable(false);
		text_16.setEditable(false);
		text_17.setEditable(false);
		text_18.setEditable(false);
		text_19.setEditable(false);
		text_20.setEditable(false);
		text_21.setEditable(false);
		text_22.setEditable(false);
		text_23.setEditable(false);
		text_24.setEditable(false);
		text_25.setEditable(false);
		text_26.setEditable(false);
		text_27.setEditable(false);
		text_28.setEditable(false);
		text_29.setEditable(false);
		text_30.setEditable(false);
		text_31.setEditable(false);
		text_32.setEditable(false);
		text_33.setEditable(false);
		text_34.setEditable(false);
		text_35.setEditable(false);
		text_36.setEditable(false);
		text_37.setEditable(false);
		text_38.setEditable(false);
		text_39.setEditable(false);
		text_40.setEditable(false);
		text_41.setEditable(false);
		text_42.setEditable(false);
		text_43.setEditable(false);
		text_44.setEditable(false);
		text_45.setEditable(false);
		text_46.setEditable(false);
		text_47.setEditable(false);
		text_48.setEditable(false);
		text_49.setEditable(false);
		text_50.setEditable(false);
		text_51.setEditable(false);
		text_52.setEditable(false);
		text_53.setEditable(false);
		text_54.setEditable(false);
		text_55.setEditable(false);
		text_56.setEditable(false);
		text_57.setEditable(false);
		text_58.setEditable(false);
		text_59.setEditable(false);
		text_60.setEditable(false);
		
		text_0.setBackground(swtWhite);
		text_1.setBackground(swtWhite);
		text_2.setBackground(swtWhite);
		text_3.setBackground(swtWhite);
		text_4.setBackground(swtWhite);
		text_5.setBackground(swtWhite);
		text_6.setBackground(swtWhite);
		text_7.setBackground(swtWhite);

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
	public void restore() throws Exception {
		states = ParisWork.states;
		chemicals = ParisWork.chemicals;
		replacements = ParisWork.replacements;
		loadImpactFactors(states.getActiveState());
		loadFixedValues(states.getActiveState());
		loadDynamicValues(states.getActiveState());
	}
	
	@Override
	public void updateShared() throws Exception {
		int[] newImpactFactors = this.getImpactFactors();
		if (anyChanges(newImpactFactors)) {
			states.getActiveState().setImpactFactors(newImpactFactors);
			states.getActiveState().calculateEnvironmentalIndexes();
			replacements = null;
		}
		ParisWork.states = states;
		ParisWork.chemicals = chemicals;
		ParisWork.replacements = replacements;
	}
	
	private void loadImpactFactors(State activeState) {
		int[] impactFactors = activeState.getImpactFactors();
		text_0.setText(Integer.toString(impactFactors[0]));
		text_1.setText(Integer.toString(impactFactors[1]));
		text_2.setText(Integer.toString(impactFactors[2]));
		text_3.setText(Integer.toString(impactFactors[3]));
		text_4.setText(Integer.toString(impactFactors[4]));
		text_5.setText(Integer.toString(impactFactors[5]));
		text_6.setText(Integer.toString(impactFactors[6]));
		text_7.setText(Integer.toString(impactFactors[7]));
	}

	private boolean anyChanges(int[] newImpactFactors) {
		
		int[] previousImpactFactors = states.getActiveState().getImpactFactors();
		if (previousImpactFactors==null) return true;
		
		for (int i=0; i<newImpactFactors.length; i++) {
			if (newImpactFactors[i]!=previousImpactFactors[i]) return true;
		}
		return false;
		
	}
	
	private void loadFixedValues(State activeState) {
		
		double wght;
		Chemical solvent = null;
		
		blankoutFixedValues();
		
		if (activeState.getMixture()==null) return;
	
		Vector<Chemical> solvents = activeState.getMixture().getChemicals();
		Vector<Double> wghts = activeState.getMixture().getWghtFractions();
		if (solvents.size()>0) {
			solvent = solvents.get(0);
			wght = wghts.get(0);
			
			text_8.setText(solvent.getName());
			text_9.setText(Double.toString(wght*100.0));
			text_10.setText(ef.format(solvent.getHtoxIngestion()));
			text_11.setText(ef.format(solvent.getHtoxInhalation()));
			text_12.setText(ef.format(solvent.getHtoxDermal()));
			text_13.setText(ef.format(solvent.getAquaticTox()));
			text_14.setText(ef.format(solvent.getGWP()));
			text_15.setText(ef.format(solvent.getODP()));
			text_16.setText(ef.format(solvent.getPCOP()));
			text_17.setText(ef.format(solvent.getAP()));
		}
		if (solvents.size()>1) {
			solvent = solvents.get(1);
			wght = wghts.get(1);

			text_19.setText(solvent.getName());
			text_20.setText(Double.toString(wght*100.0));
			text_21.setText(ef.format(solvent.getHtoxIngestion()));
			text_22.setText(ef.format(solvent.getHtoxInhalation()));
			text_23.setText(ef.format(solvent.getHtoxDermal()));
			text_24.setText(ef.format(solvent.getAquaticTox()));
			text_25.setText(ef.format(solvent.getGWP()));
			text_26.setText(ef.format(solvent.getODP()));
			text_27.setText(ef.format(solvent.getPCOP()));
			text_28.setText(ef.format(solvent.getAP()));
		}
		if (solvents.size()>2) {
			solvent = solvents.get(2);
			wght = wghts.get(2);
			
			text_30.setText(solvent.getName());
			text_31.setText(Double.toString(wght*100.0));
			text_32.setText(ef.format(solvent.getHtoxIngestion()));
			text_33.setText(ef.format(solvent.getHtoxInhalation()));
			text_34.setText(ef.format(solvent.getHtoxDermal()));
			text_35.setText(ef.format(solvent.getAquaticTox()));
			text_36.setText(ef.format(solvent.getGWP()));
			text_37.setText(ef.format(solvent.getODP()));
			text_38.setText(ef.format(solvent.getPCOP()));
			text_39.setText(ef.format(solvent.getAP()));
		}
		if (solvents.size()>3) {
			solvent = solvents.get(3);
			wght = wghts.get(3);
			
			text_41.setText(solvent.getName());
			text_42.setText(Double.toString(wght*100.0));
			text_43.setText(ef.format(solvent.getHtoxIngestion()));
			text_44.setText(ef.format(solvent.getHtoxInhalation()));
			text_45.setText(ef.format(solvent.getHtoxDermal()));
			text_46.setText(ef.format(solvent.getAquaticTox()));
			text_47.setText(ef.format(solvent.getGWP()));
			text_48.setText(ef.format(solvent.getODP()));
			text_49.setText(ef.format(solvent.getPCOP()));
			text_50.setText(ef.format(solvent.getAP()));
		}
		
	}
	
	private void loadDynamicValues(State activeState) {
		
		double[] normIF = new double[8];
		double value, wght, sumh;
		double[] sumv = new double[8];
		Chemical solvent = null;
		
		blankoutDynamicValues();
		
		if (activeState.getMixture()==null) return;
		
		normIF[0] = Double.parseDouble(text_0.getText());
		normIF[1] = Double.parseDouble(text_1.getText());
		normIF[2] = Double.parseDouble(text_2.getText());
		normIF[3] = Double.parseDouble(text_3.getText());
		normIF[4] = Double.parseDouble(text_4.getText());
		normIF[5] = Double.parseDouble(text_5.getText());
		normIF[6] = Double.parseDouble(text_6.getText());
		normIF[7] = Double.parseDouble(text_7.getText());
		
//		double norm = 0.0;
//		for (int i=0; i<8; i++) {
//			norm += normIF[i];
//		}
//		if (norm > 0) {
//			for (int i=0; i<8; i++) {
//				normIF[i] /= norm;
//			}
//		}
		
		Vector<Chemical> solvents = activeState.getMixture().getChemicals();
		Vector<Double> wghts = activeState.getMixture().getWghtFractions();
		
		if (solvents.size()==0) return;
		
		if (solvents.size()>0) {
			solvent = solvents.get(0);
			wght = wghts.get(0);

			value = wght*normIF[0]*solvent.getHtoxIngestion();
			sumh = value;
			sumv[0] = value;
			
			value = wght*normIF[1]*solvent.getHtoxInhalation();
			sumh += value;
			sumv[1] = value;
			
			value = wght*normIF[2]*solvent.getHtoxDermal();
			sumh += value;
			sumv[2] = value;
			
			value = wght*normIF[3]*solvent.getAquaticTox();
			sumh += value;
			sumv[3] = value;
			
			value = wght*normIF[4]*solvent.getGWP();
			sumh += value;
			sumv[4] = value;

			value = wght*normIF[5]*solvent.getODP();
			sumh += value;
			sumv[5] = value;

			value = wght*normIF[6]*solvent.getPCOP();
			sumh += value;
			sumv[6] = value;

			value = wght*normIF[7]*solvent.getAP();
			sumh += value;
			sumv[7] = value;

			text_18.setText(ef.format(sumh));
			text_18.setBackground(swtGray);
		}
		if (solvents.size()>1) {
			solvent = solvents.get(1);
			wght = wghts.get(1);
			
			value = wght*normIF[0]*solvent.getHtoxIngestion();
			sumh = value;
			sumv[0] += value;
			
			value = wght*normIF[1]*solvent.getHtoxInhalation();
			sumh += value;
			sumv[1] += value;
			
			value = wght*normIF[2]*solvent.getHtoxDermal();
			sumh += value;
			sumv[2] += value;
			
			value = wght*normIF[3]*solvent.getAquaticTox();
			sumh += value;
			sumv[3] += value;
			
			value = wght*normIF[4]*solvent.getGWP();
			sumh += value;
			sumv[4] += value;

			value = wght*normIF[5]*solvent.getODP();
			sumh += value;
			sumv[5] += value;

			value = wght*normIF[6]*solvent.getPCOP();
			sumh += value;
			sumv[6] += value;

			value = wght*normIF[7]*solvent.getAP();
			sumh += value;
			sumv[7] += value;

			text_29.setText(ef.format(sumh));
			text_29.setBackground(swtGray);
		}
		if (solvents.size()>2) {
			solvent = solvents.get(2);
			wght = wghts.get(2);
			
			value = wght*normIF[0]*solvent.getHtoxIngestion();
			sumh = value;
			sumv[0] += value;
			
			value = wght*normIF[1]*solvent.getHtoxInhalation();
			sumh += value;
			sumv[1] += value;
			
			value = wght*normIF[2]*solvent.getHtoxDermal();
			sumh += value;
			sumv[2] += value;
			
			value = wght*normIF[3]*solvent.getAquaticTox();
			sumh += value;
			sumv[3] += value;
			
			value = wght*normIF[4]*solvent.getGWP();
			sumh += value;
			sumv[4] += value;

			value = wght*normIF[5]*solvent.getODP();
			sumh += value;
			sumv[5] += value;

			value = wght*normIF[6]*solvent.getPCOP();
			sumh += value;
			sumv[6] += value;

			value = wght*normIF[7]*solvent.getAP();
			sumh += value;
			sumv[7] += value;
			
			text_40.setText(ef.format(sumh));
			text_40.setBackground(swtGray);
		}
		if (solvents.size()>3) {
			solvent = solvents.get(3);
			wght = wghts.get(3);
			
			value = wght*normIF[0]*solvent.getHtoxIngestion();
			sumh = value;
			sumv[0] += value;
			
			value = wght*normIF[1]*solvent.getHtoxInhalation();
			sumh += value;
			sumv[1] += value;
			
			value = wght*normIF[2]*solvent.getHtoxDermal();
			sumh += value;
			sumv[2] += value;
			
			value = wght*normIF[3]*solvent.getAquaticTox();
			sumh += value;
			sumv[3] += value;
			
			value = wght*normIF[4]*solvent.getGWP();
			sumh += value;
			sumv[4] += value;

			value = wght*normIF[5]*solvent.getODP();
			sumh += value;
			sumv[5] += value;

			value = wght*normIF[6]*solvent.getPCOP();
			sumh += value;
			sumv[6] += value;

			value = wght*normIF[7]*solvent.getAP();
			sumh += value;
			sumv[7] += value;
			
			text_51.setText(ef.format(sumh));
			text_51.setBackground(swtGray);
		}
		
		text_52.setText(ef.format(sumv[0]));
		text_53.setText(ef.format(sumv[1]));
		text_54.setText(ef.format(sumv[2]));
		text_55.setText(ef.format(sumv[3]));
		text_56.setText(ef.format(sumv[4]));
		text_57.setText(ef.format(sumv[5]));
		text_58.setText(ef.format(sumv[6]));
		text_59.setText(ef.format(sumv[7]));
		text_52.setBackground(swtGray);
		text_53.setBackground(swtGray);
		text_54.setBackground(swtGray);
		text_55.setBackground(swtGray);
		text_56.setBackground(swtGray);
		text_57.setBackground(swtGray);
		text_58.setBackground(swtGray);
		text_59.setBackground(swtGray);
		
		sumh = 0;
		for (int i=0; i<sumv.length; i++) {
			sumh += sumv[i];
		}
		text_60.setText(ef.format(sumh));
		text_60.setBackground(swtGray);
		
		text_61.setText("100.0");
		
	}
	
	private void blankoutFixedValues() {
		text_8.setText("");
		text_9.setText("");
		text_10.setText("");
		text_11.setText("");
		text_12.setText("");
		text_13.setText("");
		text_14.setText("");
		text_15.setText("");
		text_16.setText("");
		text_17.setText("");			
		text_19.setText("");
		text_20.setText("");
		text_21.setText("");
		text_22.setText("");
		text_23.setText("");
		text_24.setText("");
		text_25.setText("");
		text_26.setText("");
		text_27.setText("");
		text_28.setText("");			
		text_30.setText("");
		text_31.setText("");
		text_32.setText("");
		text_33.setText("");
		text_34.setText("");
		text_35.setText("");
		text_36.setText("");
		text_37.setText("");
		text_38.setText("");
		text_39.setText("");			
		text_41.setText("");
		text_42.setText("");
		text_43.setText("");
		text_44.setText("");
		text_45.setText("");
		text_46.setText("");
		text_47.setText("");
		text_48.setText("");
		text_49.setText("");
		text_50.setText("");			
	}

	private void blankoutDynamicValues() {			
		text_18.setText("");		
		text_29.setText("");			
		text_40.setText("");		
		text_51.setText("");
		text_52.setText("");
		text_53.setText("");
		text_54.setText("");
		text_55.setText("");
		text_56.setText("");
		text_57.setText("");
		text_58.setText("");
		text_59.setText("");
		text_60.setText("");
		text_18.setBackground(swtGray);
		text_29.setBackground(swtGray);
		text_40.setBackground(swtGray);
		text_51.setBackground(swtGray);
		text_52.setBackground(swtGray);
		text_53.setBackground(swtGray);
		text_54.setBackground(swtGray);
		text_55.setBackground(swtGray);
		text_56.setBackground(swtGray);
		text_57.setBackground(swtGray);
		text_58.setBackground(swtGray);
		text_59.setBackground(swtGray);
		text_60.setBackground(swtGray);	
	}
	
	private int[] getImpactFactors() {
		int[] impactFactors = new int[8];
		impactFactors[0] = Integer.parseInt(text_0.getText());
		impactFactors[1] = Integer.parseInt(text_1.getText());
		impactFactors[2] = Integer.parseInt(text_2.getText());
		impactFactors[3] = Integer.parseInt(text_3.getText());
		impactFactors[4] = Integer.parseInt(text_4.getText());
		impactFactors[5] = Integer.parseInt(text_5.getText());
		impactFactors[6] = Integer.parseInt(text_6.getText());
		impactFactors[7] = Integer.parseInt(text_7.getText());
		return impactFactors;
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
