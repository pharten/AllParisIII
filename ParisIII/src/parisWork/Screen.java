package parisWork;

import java.util.Vector;

import parisInit.States;

import org.eclipse.swt.widgets.Composite;

public class Screen extends Composite {
	States states = ParisWork.states;
	Chemicals allChemicals = ParisWork.allChemicals;
	Chemicals chemicals = ParisWork.chemicals;
	Replacements replacements = ParisWork.replacements;
	Vector<Mixture> bestMixtures = ParisWork.bestMixtures;

	public Screen(Composite parent, int style) {
		super(parent, style);
	}
	
	public void begin() throws Exception {
		restore();
		int screenNum = Integer.parseInt(this.getClass().getSimpleName().substring(6));
		states.getActiveState().setOpenScreen(screenNum);
//		System.out.println("at Screen"+screenNum);  // no longer used
	}
	
	public boolean finishUp() throws Exception {
		updateShared();
		return true;
	}
	
	public void updateShared() throws Exception {
		ParisWork.states = states;
		ParisWork.allChemicals = allChemicals;
		ParisWork.chemicals = chemicals;
		ParisWork.replacements = replacements;
		ParisWork.bestMixtures = bestMixtures;
	}

	public void restore() throws Exception {
		states = ParisWork.states;
		allChemicals = ParisWork.allChemicals;
		chemicals = ParisWork.chemicals;
		replacements = ParisWork.replacements;
		bestMixtures = ParisWork.bestMixtures;
	}

}
