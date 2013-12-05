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
		shell.setBackground(SWTResourceManager.getColor(157, 185, 235));
		shell.setLayout(null);
		
		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(5, 5, 316, 142);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		composite.setLayout(null);
		
		Text text = new Text(composite, SWT.CENTER | SWT.MULTI);
		text.setEditable(false);
		text.setText("Program for Assisting the Replacement of\r\nIndustrial Solvents");
		text.setBounds(0, 10, 316, 30);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		Text text_1 = new Text(composite, SWT.CENTER | SWT.MULTI);
		text_1.setEditable(false);
		text_1.setText(" PARIS III");
		text_1.setBounds(0, 41, 316, 55);
		text_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		text_1.setFont(SWTResourceManager.getFont("Arial", 40, SWT.BOLD));
		
		Text text_2 = new Text(composite, SWT.CENTER | SWT.MULTI);
		text_2.setEditable(false);
		text_2.setText("Solvent Design Software\r\nfrom US EPA");
		text_2.setBounds(0, 102, 306, 30);
		text_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setBounds(326, 5, 114, 160);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		composite_1.setLayout(null);
		
		ImageHyperlink parisImagehyperlink = new ImageHyperlink(composite_1, SWT.NONE);
		parisImagehyperlink.setBounds(10, 0, 92, 160);
		parisImagehyperlink.setImage(shell.getImage());
		parisImagehyperlink.setText("New ImageHyperlink");
		
		Composite composite_3 = new Composite(shell, SWT.NONE);
		composite_3.setBounds(5, 152, 297, 92);
		composite_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		composite_3.setLayout(null);
		
		techSupportButton = new Button(composite_3, SWT.NONE);
		techSupportButton.setText("Tech Support");
		techSupportButton.setBounds(30, 0, 176, 36);
		techSupportButton.setTouchEnabled(true);
		techSupportButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				techSupport = new TechSupport(shell);
				techSupport.start();
			}
		});
		
		developersButton = new Button(composite_3, SWT.NONE);
		developersButton.setText("Developer and Acknolodgements");
		developersButton.setBounds(30, 42, 238, 40);
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
		composite_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		composite_2.setLayout(null);
		
		startButton = new Button(composite_2, SWT.NONE);
		startButton.setText("Start");
		startButton.setBounds(0, 10, 68, 54);
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
		cancelButton.setBounds(86, 32, 37, 32);
		cancelButton.setTouchEnabled(true);
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				result = SWT.CANCEL;
				shell.dispose();
			}
		});
		
		Composite composite_4 = new Composite(shell, SWT.NONE);
		composite_4.setBounds(5, 249, 435, 15);
		composite_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		composite_4.setLayout(null);
		
		Label lblNewLabel = new Label(composite_4, SWT.CENTER);
		lblNewLabel.setBounds(94, 0, 231, 13);
		lblNewLabel.setAlignment(SWT.CENTER);
		lblNewLabel.setText("Paris III is a trademark of the US EPA");
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
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
