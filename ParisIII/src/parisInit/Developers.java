package parisInit;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;

public class Developers extends Dialog {
	
	protected int result = SWT.OK;
	protected Shell shell;
	private Button okButton;

    public Developers (Shell parent, int style) {
        super (parent, style);
    }

    public Developers (Shell parent) {
        this (parent, 0); // your default style bits go here (not the Shell's style bits)
    }
	
	/**
	 * Open the dialog.
	 * @return the result
	 */
//	public int open() {
//		start();
//		return stop();
//	}
	
	/**
	 * Open the dialog.
	 * @return the result
	 */
	public void start() {
        shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell.setText(getText());

        // Your code goes here (widget creation, set result, etc).
        createContents();
        
		shell.open();
		shell.layout();
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

	/**
	 * Create contents of the dialog.
	 * @param parent
	 */
	protected void createContents() {
		shell.setImage(getParent().getImage());
		shell.setText("Paris III");
		shell.setSize(455, 311);
		shell.setLayout(new FillLayout(SWT.VERTICAL));
		
		SashForm sashForm_1 = new SashForm(shell, SWT.VERTICAL);
		
		Label lblNewLabel_1 = new Label(sashForm_1, SWT.CENTER);
		lblNewLabel_1.setForeground(SWTResourceManager.getColor(0, 0, 0));
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.BOLD));
		lblNewLabel_1.setText("Developers");
		
		Text text_1 = new Text(sashForm_1, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		text_1.setText("Paul Harten, Douglas Young, Todd Martin, and Heriberto Cabezas, National Risk Management Research Laboratory U.S. Environmental Protection Agency");
		
		Label lblNewLabel_2 = new Label(sashForm_1, SWT.CENTER);
		lblNewLabel_2.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.BOLD));
		lblNewLabel_2.setText("Acknowledgements");
		
		Text text_2 = new Text(sashForm_1, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		text_2.setText("The basic concepts were adopted from the PARIS (1995) developed under a Cooporative Agreement by Renhong Zhao, Yuanxiang Fang, and Rakesh Govind at the University of Cincinnati, in collaboration with Subhas K. Sikdar and Dennis L. Timberlake, Sustainable Technology Division, National Risk Management Research Laboratory, U.S. Environmental Protection Agency.\r\n \r\nPhysical property database of pure compound properties and the property calculations of mixed compounds were developed by Todd Martin from the Sustainable Technology Division, National Risk Management Research Laboratory, U.S. Environmental Protection Agency.\r\n\r\nThe UNIFAC parameter table was obtained from CAPEC,  Department of Chemical Engineering, Technical University of Denmark, Lyngby, Denmark.\r\n\r\nThe database of chemical environmental impact data, compiled by Douglas Young and Todd Martin from the Sustainable Technology Division, National Risk Management Research Laboratory, U.S. Environmental Protection Agency.\r\n");
		
		SashForm sashForm = new SashForm(sashForm_1, SWT.NONE);
		
		Composite composite = new Composite(sashForm, SWT.NONE);
		
		okButton = new Button(sashForm, SWT.NONE);
		okButton.setText("OK");
		okButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
				result = stop();
			}
		});
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		sashForm.setWeights(new int[] {5, 1, 5});
		
		sashForm_1.setWeights(new int[] {1, 4, 1, 4, 1});
	}

	public int getResult() {
		return result;
	}

	public void setOkButton(Button okButton) {
		this.okButton = okButton;
	}

	public Button getOkButton() {
		return okButton;
	}

}