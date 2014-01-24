package parisWork;

import java.text.DecimalFormat;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Text;

import parisInit.Units;

public class Reference extends Dialog {

	protected int result = SWT.OK;
	protected Shell shell;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_7;
	private Text text_8;
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
	private Text text_19;
	private Text text_20;
	private Text txtSynonym;
	protected Label lblKgkmol;
	protected Label lblK_1;
	protected Label lblK_2;
	protected Label lblSqrtjm3;
	protected Label lblK_3;
	protected Label lblK_4;
	protected Label lblKgm;
	protected Label lblKpa;
	protected Label lblJkmolk;
	protected Label lblKgms;
	protected Label lblJmsk;
	protected Label lblKgs;
	protected Button btnOK;
	
	private static DecimalFormat df = new DecimalFormat("0.0##");
	private static DecimalFormat ef = new DecimalFormat("#0.000E0");

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Reference(Shell parentShell) {
//		super(parent);
//		setText("Chemical Reference");
		super(parentShell, SWT.MIN | SWT.RESIZE);
		shell = new Shell(getParent(),SWT.MIN | SWT.RESIZE);

		createContents();
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public void open(Chemical chemical, Units units) {
		
		addInChemicalProperties(chemical, units);
		modifyUnits(units);

		shell.open();
		shell.layout();

		return;
	}
	
	public int stop() {
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	private void addInChemicalProperties(Chemical chemical, Units units) {
		text_3.setText(chemical.getName());
		text_4.setText(chemical.getFormula());
		text_5.setText(chemical.getCAS());
		text_6.setText(chemical.getStructure());
		if (text_6.getText().equals("")) text_6.setText(chemical.getSmiles());
		txtSynonym.setText(chemical.getSynomymsInString());
		text_7.setText(df.format(Units.massConvertTo(chemical.getMolecularWeight(), units)));
		text_8.setText(df.format(Units.tempConvertTo(chemical.getMeltingPoint(), units)));
		text_9.setText(df.format(Units.tempConvertTo(chemical.getBoilingPoint(), units)));
		text_10.setText(df.format(Units.solubConvertTo(chemical.getSolubility(), units)));
		text_11.setText(df.format(Units.tempConvertTo(chemical.getFlashPoint(), units)));
		text_12.setText(df.format(Units.tempConvertTo(chemical.getLFlamLimit(), units)));
		text_13.setText(df.format(Units.tempConvertTo(chemical.getUFlamLimit(), units)));
		text_14.setText(df.format(Units.tempConvertTo(chemical.getAutoIgnitTemp(), units)));
		text_15.setText(ef.format(Units.densityConvertTo(chemical.getDensity(), units)));
		text_16.setText(ef.format(Units.pressureConvertTo(chemical.getVaporPressure(), units)));
		text_17.setText(ef.format(Units.hcapConvertTo(chemical.getHeatCapacity(), units)));
		text_18.setText(ef.format(Units.viscosityConvertTo(chemical.getViscosity(), units)));
		text_19.setText(ef.format(Units.thermalConductivityConvertTo(chemical.getThermalConductivity(), units)));
		text_20.setText(ef.format(Units.surfaceTensionConvertTo(chemical.getSurfaceTension(), units)));
	}
	
	private void modifyUnits(Units units) {
		switch (units) {
		case COMMON:
			lblKgkmol.setText(" g/mol");		// molecular mass
			lblK_1.setText(" C");				// melting temperature
			lblK_2.setText(" C");				// boiling temperature
			lblSqrtjm3.setText(" g/cm3");		// water solubility
			lblK_3.setText(" C");				// Lower Limit
			lblK_4.setText(" C");				// Upper Limit
			lblKgm.setText(" g/cc"); 			// liquid density
			lblKpa.setText(" mmHg");			// vapor pressure
			lblJkmolk.setText(" cal/mol-C");	// liquid heat capacity
			lblKgms.setText(" cP");				// liquid viscosity
			lblJmsk.setText(" mW/(m-K)");		// liquid thermal conductivity
			lblKgs.setText(" dyne/cm");			// surface tension
			break;
		case CGS:
			lblKgkmol.setText(" g/mol");
			lblK_1.setText(" C");
			lblK_2.setText(" C");
			lblSqrtjm3.setText(" sqrt(cal/cm3)");
			lblK_3.setText(" C");
			lblK_4.setText(" C");
			lblKgm.setText(" g/cm3");
			lblKpa.setText(" Pa");
			lblJkmolk.setText(" cal/mol-C");
			lblKgms.setText(" g/cm-s");
			lblJmsk.setText(" cal/(cm-s-C)");
			lblKgs.setText(" g/s2");
			break;
		case US:
			lblKgkmol.setText(" lb/mol");
			lblK_1.setText(" F");
			lblK_2.setText(" F");
			lblSqrtjm3.setText(" sqrt(Btu/ft3)");
			lblK_3.setText(" F");
			lblK_4.setText(" F");
			lblKgm.setText(" lb/ft3");
			lblKpa.setText(" atm");
			lblJkmolk.setText(" Btu/mol-F");
			lblKgms.setText(" lb/ft-s");
			lblJmsk.setText(" Btu/(ft-s-F)");
			lblKgs.setText(" lb/s2");
			break;
		default:  // case SI:
			lblKgkmol.setText(" kg/kmol");
			lblK_1.setText(" K");
			lblK_2.setText(" K");
			lblSqrtjm3.setText(" sqrt(J/m3)");
			lblK_3.setText(" K");
			lblK_4.setText(" K");
			lblKgm.setText(" kg/m3");
			lblKpa.setText(" kPa");
			lblJkmolk.setText(" J/kmol-K");
			lblKgms.setText(" kg/m-s");
			lblJmsk.setText(" J/(m-s-K)");
			lblKgs.setText(" kg/s2");
			break;
		}
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setText("Paris III");
		shell.setSize(577, 414);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		SashForm sashForm_1 = new SashForm(shell, SWT.VERTICAL);
		
		Composite composite = new Composite(sashForm_1, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblNewLabel = new Label(composite, SWT.CENTER);
		lblNewLabel.setText("Properties of Selected Chemical at Specified Temperature");
		
		SashForm sashForm_2 = new SashForm(sashForm_1, SWT.NONE);
		
		Composite composite_23 = new Composite(sashForm_2, SWT.NONE);
		composite_23.setLayout(new FillLayout(SWT.VERTICAL));
		
		SashForm sashForm_4 = new SashForm(composite_23, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(sashForm_4, SWT.NONE);
		lblNewLabel_1.setAlignment(SWT.RIGHT);
		lblNewLabel_1.setText("Chemical Name");
		
		text_3 = new Text(sashForm_4, SWT.BORDER | SWT.WRAP);
		text_3.setEditable(false);
		sashForm_4.setWeights(new int[] {1, 2});
		
		SashForm sashForm_5 = new SashForm(composite_23, SWT.NONE);
		
		Label lblNewLabel_3 = new Label(sashForm_5, SWT.NONE);
		lblNewLabel_3.setAlignment(SWT.RIGHT);
		lblNewLabel_3.setText("Formula");
		
		text_4 = new Text(sashForm_5, SWT.BORDER | SWT.WRAP);
		text_4.setEditable(false);
		sashForm_5.setWeights(new int[] {1, 2});
		
		SashForm sashForm_6 = new SashForm(composite_23, SWT.NONE);
		
		Label lblNewLabel_5 = new Label(sashForm_6, SWT.NONE);
		lblNewLabel_5.setAlignment(SWT.RIGHT);
		lblNewLabel_5.setText("CAS Number");
		
		text_5 = new Text(sashForm_6, SWT.BORDER | SWT.WRAP);
		text_5.setEditable(false);
		sashForm_6.setWeights(new int[] {1, 2});
		
		SashForm sashForm_7 = new SashForm(composite_23, SWT.NONE);
		
		Label lblNewLabel_6 = new Label(sashForm_7, SWT.NONE);
		lblNewLabel_6.setAlignment(SWT.RIGHT);
		lblNewLabel_6.setText("Structure");
		
		text_6 = new Text(sashForm_7, SWT.BORDER | SWT.WRAP);
		text_6.setEditable(false);
		sashForm_7.setWeights(new int[] {1, 2});
		
		Composite composite_1 = new Composite(sashForm_2, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group grpSynonyms = new Group(composite_1, SWT.NONE);
		grpSynonyms.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpSynonyms.setText("Synonyms");
		
		txtSynonym = new Text(grpSynonyms, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		txtSynonym.setEditable(false);
		sashForm_2.setWeights(new int[] {251, 316});
		
		SashForm sashForm = new SashForm(sashForm_1, SWT.NONE);
		
		Composite composite_25 = new Composite(sashForm, SWT.NONE);
		composite_25.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group group1 = new Group(composite_25, SWT.NONE);
		group1.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite_7 = new Composite(group1, SWT.NONE);
		composite_7.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblMolecularWeight = new Label(composite_7, SWT.NONE);
		lblMolecularWeight.setAlignment(SWT.RIGHT);
		lblMolecularWeight.setText("Molecular Weight ");
		
		text_7 = new Text(composite_7, SWT.BORDER | SWT.CENTER);
		text_7.setEditable(false);
		
		lblKgkmol = new Label(composite_7, SWT.NONE);
		lblKgkmol.setText(" kg/kmol");
		
		Composite composite_8 = new Composite(group1, SWT.NONE);
		composite_8.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblMeltingPoint = new Label(composite_8, SWT.NONE);
		lblMeltingPoint.setAlignment(SWT.RIGHT);
		lblMeltingPoint.setText("Melting Point ");
		
		text_8 = new Text(composite_8, SWT.BORDER | SWT.CENTER);
		text_8.setEditable(false);
		
		lblK_1 = new Label(composite_8, SWT.NONE);
		lblK_1.setText(" K");
		
		Composite composite_9 = new Composite(group1, SWT.NONE);
		composite_9.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblBoilingPoint = new Label(composite_9, SWT.NONE);
		lblBoilingPoint.setAlignment(SWT.RIGHT);
		lblBoilingPoint.setText("Boiling Point ");
		
		text_9 = new Text(composite_9, SWT.BORDER | SWT.CENTER);
		text_9.setEditable(false);
		
		lblK_2 = new Label(composite_9, SWT.NONE);
		lblK_2.setText(" K");
		
		Composite composite_10 = new Composite(group1, SWT.NONE);
		composite_10.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblSolubilityParameter = new Label(composite_10, SWT.NONE);
		lblSolubilityParameter.setAlignment(SWT.RIGHT);
		lblSolubilityParameter.setText("Solubility Param ");
		
		text_10 = new Text(composite_10, SWT.BORDER | SWT.CENTER);
		text_10.setEditable(false);
		
		lblSqrtjm3 = new Label(composite_10, SWT.NONE);
		lblSqrtjm3.setText(" sqrt(J/m3)");
		
		Composite composite_11 = new Composite(group1, SWT.NONE);
		composite_11.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblLFlammability = new Label(composite_11, SWT.NONE);
		lblLFlammability.setAlignment(SWT.RIGHT);
		lblLFlammability.setText("Flash Point ");
		
		text_11 = new Text(composite_11, SWT.BORDER | SWT.CENTER);
		text_11.setEditable(false);
		
		lblK_3 = new Label(composite_11, SWT.NONE);
		lblK_3.setText(" K");
		
		Composite composite_12 = new Composite(group1, SWT.NONE);
		composite_12.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblFlashPoint = new Label(composite_12, SWT.NONE);
		lblFlashPoint.setAlignment(SWT.RIGHT);
		lblFlashPoint.setText("L Flammability Lim ");
		
		text_12 = new Text(composite_12, SWT.BORDER | SWT.CENTER);
		text_12.setEditable(false);
		
		Label lblVolumeIn = new Label(composite_12, SWT.NONE);
		lblVolumeIn.setText(" volume % in air");
		
		Composite composite_13 = new Composite(group1, SWT.NONE);
		composite_13.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblUFlammabilityLim = new Label(composite_13, SWT.NONE);
		lblUFlammabilityLim.setAlignment(SWT.RIGHT);
		lblUFlammabilityLim.setText("U Flammability Lim ");
		
		text_13 = new Text(composite_13, SWT.BORDER | SWT.CENTER);
		text_13.setEditable(false);
		
		Label lblVolumeIn_1 = new Label(composite_13, SWT.NONE);
		lblVolumeIn_1.setText(" volume % in air");
		
		Composite composite_14 = new Composite(group1, SWT.NONE);
		composite_14.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblAutoIgnitionTemp = new Label(composite_14, SWT.NONE);
		lblAutoIgnitionTemp.setAlignment(SWT.RIGHT);
		lblAutoIgnitionTemp.setText("AutoIgnition Temp ");
		
		text_14 = new Text(composite_14, SWT.BORDER | SWT.CENTER);
		text_14.setEditable(false);
		
		lblK_4 = new Label(composite_14, SWT.NONE);
		lblK_4.setText(" K");
		
		Composite composite_24 = new Composite(sashForm, SWT.NONE);
		composite_24.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group group2 = new Group(composite_24, SWT.NONE);
		group2.setText("Temperature Dependent");
		group2.setLayout(new FillLayout(SWT.VERTICAL));
		
		Composite composite_3 = new Composite(group2, SWT.NONE);
		composite_3.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite_15 = new Composite(group2, SWT.NONE);
		composite_15.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblLiquidDensity = new Label(composite_15, SWT.NONE);
		lblLiquidDensity.setText("Liquid Density ");
		lblLiquidDensity.setAlignment(SWT.RIGHT);
		
		text_15 = new Text(composite_15, SWT.BORDER | SWT.CENTER);
		text_15.setEditable(false);
		
		lblKgm = new Label(composite_15, SWT.NONE);
		lblKgm.setText(" kg/m3");
		
		Composite composite_16 = new Composite(group2, SWT.NONE);
		composite_16.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblVaporPressure = new Label(composite_16, SWT.NONE);
		lblVaporPressure.setText("Vapor Pressure ");
		lblVaporPressure.setAlignment(SWT.RIGHT);
		
		text_16 = new Text(composite_16, SWT.BORDER | SWT.CENTER);
		text_16.setEditable(false);
		
		lblKpa = new Label(composite_16, SWT.NONE);
		lblKpa.setText(" kPa");
		
		Composite composite_17 = new Composite(group2, SWT.NONE);
		composite_17.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblLiquidHeatCapac = new Label(composite_17, SWT.NONE);
		lblLiquidHeatCapac.setText("Liquid Heat Capac ");
		lblLiquidHeatCapac.setAlignment(SWT.RIGHT);
		
		text_17 = new Text(composite_17, SWT.BORDER | SWT.CENTER);
		text_17.setEditable(false);
		
		lblJkmolk = new Label(composite_17, SWT.NONE);
		lblJkmolk.setText(" J/kmol-K");
		
		Composite composite_18 = new Composite(group2, SWT.NONE);
		composite_18.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblLiquidViscosity = new Label(composite_18, SWT.NONE);
		lblLiquidViscosity.setText("Liquid Viscosity ");
		lblLiquidViscosity.setAlignment(SWT.RIGHT);
		
		text_18 = new Text(composite_18, SWT.BORDER | SWT.CENTER);
		text_18.setEditable(false);
		
		lblKgms = new Label(composite_18, SWT.NONE);
		lblKgms.setText(" kg/m-s");
		
		Composite composite_19 = new Composite(group2, SWT.NONE);
		composite_19.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblLiquidThermCond = new Label(composite_19, SWT.NONE);
		lblLiquidThermCond.setText("Liquid Therm Cond ");
		lblLiquidThermCond.setAlignment(SWT.RIGHT);
		
		text_19 = new Text(composite_19, SWT.BORDER | SWT.CENTER);
		text_19.setEditable(false);
		
		lblJmsk = new Label(composite_19, SWT.NONE);
		lblJmsk.setText(" J/(m-s-K)");
		
		Composite composite_20 = new Composite(group2, SWT.NONE);
		composite_20.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Label lblSurfaceTension = new Label(composite_20, SWT.NONE);
		lblSurfaceTension.setText("Surface Tension ");
		lblSurfaceTension.setAlignment(SWT.RIGHT);
		
		text_20 = new Text(composite_20, SWT.BORDER | SWT.CENTER);
		text_20.setEditable(false);
		
		lblKgs = new Label(composite_20, SWT.NONE);
		lblKgs.setText(" kg/s2");
		
		Composite composite_4 = new Composite(group2, SWT.NONE);
		composite_4.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		sashForm.setWeights(new int[] {1, 1});
		
		SashForm sashForm_3 = new SashForm(sashForm_1, SWT.NONE);
		
		Composite composite_5 = new Composite(sashForm_3, SWT.NONE);
		composite_5.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		btnOK = new Button(sashForm_3, SWT.NONE);
		btnOK.setText("OK");
		btnOK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
				result = stop();
			}
		});
		
		Composite composite_2 = new Composite(sashForm_3, SWT.NONE);
		composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));
		sashForm_3.setWeights(new int[] {5, 1, 5});
		sashForm_1.setWeights(new int[] {1, 5, 7, 1});
		
	}

	public Button getBtnOK() {
		return btnOK;
	}
}
