package parisInit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

public class TechSupport extends Dialog {
	
	protected int result = SWT.OK;
	protected Shell shell;
	private Button okButton;
            
    public TechSupport (Shell parent, int style) {
            super (parent, style);
    }
    
    public TechSupport (Shell parent) {
            this (parent, 0); // your default style bits go here (not the Shell's style bits)
    }
    
//    public int open () {
//    		start();
//    		return stop();
//    }
    
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
		
		Label lblNewLabel = new Label(sashForm_1, SWT.CENTER);
		lblNewLabel.setForeground(SWTResourceManager.getColor(0, 0, 0));
		lblNewLabel.setFont(SWTResourceManager.getFont("Times New Roman", 12, SWT.BOLD));
		lblNewLabel.setText("Tech Support");
		
		Text text_1 = new Text(sashForm_1, SWT.BORDER | SWT.READ_ONLY | SWT.WRAP | SWT.V_SCROLL);
		text_1.setText("\r\nPARIS III is distributed by:\r\n              \r\n"+
				"US EPA, NRMRL\r\n26 W. M.L. King Drive          \r\n"+
				"Cincinnati, Ohio 45268\r\n              \r\nFax: 513-569-7677\r\n              \r\n"+
				"Web Site: http://www.epa.gov/nrmrl/std/parisIII/parisIII.html \r\n              \r\n"+
				"Phone: (Tech Support)\r\n              \r\nE-mail: ParisIII@epa.gov");
		
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
		
		sashForm_1.setWeights(new int[] {1, 9, 1});
	}

	public int getResult() {
		return result;
	}

	public Button getOkButton() {
		return okButton;
	}
	
}

