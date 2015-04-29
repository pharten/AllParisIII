package parisInit;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.swt.widgets.Text;


public class ParisInit extends Dialog {

	protected int result = 1;
	protected Shell shell;
	private Button startButton, cancelButton, techSupportButton, developersButton;
	private Developers developer;
	private TechSupport techSupport;

	/**
	 * Create the dialog.
	 * @param parent
	 */
	
    public ParisInit (Shell parent, int style) {
        super (parent, style);
        shell = new Shell(parent, SWT.CLOSE | SWT.TITLE | SWT.APPLICATION_MODAL);
        
        // Your code goes here (widget creation, set result, etc).
        createContents();
    }

    public ParisInit (Shell parent) {
        this (parent, 0); // your default style bits go here (not the Shell's style bits)
    }

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public int open() {
		start();
		return stop();
	}
	
	/**
	 * Open the dialog.
	 * @return the result
	 */
	public void start() {
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
	 */
	private void createContents() {
		shell.setImage(getParent().getImage());
		shell.setText("Paris III");
		
		shell.setSize(455, 311);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		shell.setLayout(null);
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(5, 5, 316, 160);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		composite.setLayout(null);
		
		Label label = new Label(composite, SWT.CENTER);
		label.setBounds(0, 5, 316, 50);
		label.setAlignment(SWT.CENTER);
		label.setText("Program for Assisting the Replacement\r\nof Industrial Solvents");
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		
		Label label_1 = new Label(composite, SWT.CENTER);
		label_1.setBounds(0, 56, 316, 72);
		label_1.setAlignment(SWT.CENTER);
		label_1.setText("PARIS III");
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		label_1.setFont(SWTResourceManager.getFont("Arial", 38, SWT.BOLD));
		
		Label label_2 = new Label(composite, SWT.CENTER);
		label_2.setBounds(0, 130, 316, 30);
		label_2.setAlignment(SWT.CENTER);
		label_2.setText("Solvent Design Software from US EPA");
		label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));

		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(326, 5, 114, 160);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		composite_1.setLayout(null);
		
		ImageHyperlink parisImagehyperlink = new ImageHyperlink(composite_1, SWT.NONE);
		parisImagehyperlink.setBounds(10, 0, 92, 160);
		parisImagehyperlink.setImage(shell.getImage());
		parisImagehyperlink.setText("New ImageHyperlink");
		
		Composite composite_3 = new Composite(shell, SWT.NONE);
		composite_3.setBounds(5, 170, 297, 74);
		composite_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		composite_3.setLayout(null);
		
		techSupportButton = new Button(composite_3, SWT.NONE);
		techSupportButton.setText("Tech Support");
		techSupportButton.setBounds(57, 0, 176, 32);
		techSupportButton.setTouchEnabled(true);
		techSupportButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				techSupport = new TechSupport(shell);
				techSupport.start();
			}
		});
		
		developersButton = new Button(composite_3, SWT.NONE);
		developersButton.setText("Developers and Acknowledgements");
		developersButton.setBounds(10, 38, 277, 36);
		developersButton.setTouchEnabled(true);
		developersButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				developer = new Developers(shell);
				developer.start();
			}
		});
		
		Composite composite_2 = new Composite(shell, SWT.NONE);
		composite_2.setBounds(307, 170, 133, 74);
		composite_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		composite_2.setLayout(null);
		
		startButton = new Button(composite_2, SWT.NONE);
		startButton.setText("Start");
		startButton.setBounds(0, 0, 80, 74);
		startButton.setTouchEnabled(true);
		startButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = SWT.OK;
				shell.dispose();
			}
		});
		
		cancelButton = new Button(composite_2, SWT.NONE);
		cancelButton.setText("Exit");
		cancelButton.setBounds(86, 42, 37, 32);
		cancelButton.setTouchEnabled(true);
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = SWT.CANCEL;
				shell.dispose();
			}
		});
		
		Composite composite_4 = new Composite(shell, SWT.NONE);
		composite_4.setBounds(5, 249, 435, 29);
		composite_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		composite_4.setLayout(null);
		
		Label label_3 = new Label(composite_4, SWT.CENTER);
		label_3.setBounds(56, 0, 306, 19);
		label_3.setAlignment(SWT.CENTER);
		label_3.setText("Paris III is a trademark of the US EPA");
		label_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
		
		shell.setTabList(new Control[]{composite, composite_1, composite_3, composite_2, composite_4});

	}

	public TechSupport getTechSupport() {
		return techSupport;
	}

	public Developers getDeveloper() {
		return developer;
	}

	public Button getStartButton() {
		return startButton;
	}
	
	public Button getCancelButton() {
		return cancelButton;
	}

	public Button getTechSupportButton() {
		return techSupportButton;
	}

	public Button getDevelopersButton() {
		return developersButton;
	}

	public Shell getShell() {
		return shell;
	}
	
	public int getResult() {
		return result;
	}
}
