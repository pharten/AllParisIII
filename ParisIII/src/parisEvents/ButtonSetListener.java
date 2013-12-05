package parisEvents;

import java.util.Vector;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.List;

import parisInit.State;
import parisWork.Chemical;

public class ButtonSetListener implements SelectionListener {
	
	private Button[] buttonSet;
	private List list;
	private Vector<Chemical> mixtureSolvents;
	private Vector<Chemical> greenerSolvents;
	private Vector<Chemical> allSolvents;
	private int selectionCount;
	private State state = null;
	
	public ButtonSetListener(Button[] buttonSet, List list, Vector<Chemical> mixtureSolvents, Vector<Chemical> greenerSolvents, Vector<Chemical> allSolvents, int selectionCount) {
		this.buttonSet = buttonSet;
		this.list = list;
		this.mixtureSolvents = mixtureSolvents;
		this.greenerSolvents = greenerSolvents;
		this.allSolvents = allSolvents;
		this.selectionCount = selectionCount;
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void widgetSelected(SelectionEvent arg0) {
		Button button = (Button)arg0.widget;
		
		if (button==buttonSet[0]) {	// best green solvents button
			if (button.getSelection()) {
				buttonSet[1].setSelection(false);
				buttonSet[2].setSelection(false);
				buttonSet[3].setSelection(false);
				fillList(greenerSolvents, selectionCount);
			} else {
				buttonSet[2].setSelection(false);
				fillList(greenerSolvents, 0);
			}
		} else if (button==buttonSet[1]) {	// all green solvents button
			if (button.getSelection()) {
				buttonSet[0].setSelection(false);
				buttonSet[2].setSelection(false);
				buttonSet[3].setSelection(false);
				fillList(greenerSolvents, greenerSolvents.size());
			} else {
				buttonSet[2].setSelection(false);
				fillList(greenerSolvents, 0);
			}
		} else if (button==buttonSet[2]) {	// initial solvents button
			if (button.getSelection()) {
				addToList(mixtureSolvents);
			} else {
				deselectFromList(mixtureSolvents);
			}
		} else if (button==buttonSet[3]) {	// all solvents button
			if (button.getSelection()) {
				buttonSet[0].setSelection(false);
				buttonSet[1].setSelection(false);
				buttonSet[2].setSelection(false);
				fillList(allSolvents, allSolvents.size());
			} else {
				buttonSet[2].setSelection(false);
				fillList(allSolvents, 0);
			}
		} else {
			try {
				throw new Exception("wrong number of check boxes");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private void fillList(Vector<Chemical> solvents, int selectionCount) {
		if (solvents==null || solvents.size() == 0) return;
		
		String[] names = new String[solvents.size()];
		for (int i=0; i<solvents.size(); i++) {
			names[i] = solvents.get(i).getName();
		}
		list.setItems(names);
		
		if (selectionCount > solvents.size()) selectionCount = solvents.size();
		list.select(0,selectionCount-1);
	}
	
	private void addToList(Vector<Chemical> solvents) {
		if (solvents==null || solvents.size() == 0) return;
		
		int index;
		String name;
		for (int i=0; i<solvents.size(); i++) {
			name = solvents.get(i).getName();
			index = list.indexOf(name);
			if (index<0) {	// if not found, add and select
				list.add(name);
				list.select(list.getItemCount()-1);
			} else {		// if found, just select
				list.select(index);
			}
		}
	}
	
	private void deselectFromList(Vector<Chemical> solvents) {
		if (solvents==null || solvents.size() == 0) return;
		
		int index;
		String name;
		for (int i=0; i<solvents.size(); i++) {
			name = solvents.get(i).getName();
			index = list.indexOf(name);
			if (0<=index && index <list.getItemCount()) {	// if found, deselect
				list.deselect(index);
			}
		}
	}
	
}
