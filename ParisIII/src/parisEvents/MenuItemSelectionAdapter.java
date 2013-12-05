package parisEvents;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

public class MenuItemSelectionAdapter extends SelectionAdapter {
	public void widgetSelected(SelectionEvent event) {
		MenuItem widget = (MenuItem)event.widget;
		System.out.println(widget.getText());
	}
}
