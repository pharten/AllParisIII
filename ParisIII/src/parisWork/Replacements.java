/**
 * 
 */
package parisWork;

import java.io.Serializable;
import java.util.Vector;

import parisInit.State;
import parisInit.Units;

/**
 * @author PHARTEN
 *
 */
public class Replacements extends Vector<Chemical> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6784893243942873489L;
	
	private State state;
	private Chemicals chemicals;
	private boolean changed = false;
	
	public Replacements(State state, Chemicals chemicals) {
		super();
		this.state = state;
		this.chemicals = chemicals;
		filter();
		quickSort();
	}

	private void filter() {
		double systemPressure = Units.pressureConvertFrom(Double.parseDouble(this.state.getSystemPres()),Units.US);
		Mixture mixture = this.state.getMixture();
		double aIndex = mixture.getAirIndex();
		double eIndex = mixture.getEnvironmentalIndex();
		int[] impactFactors = this.state.getImpactFactors();
		
		this.clear();
		for (Chemical chemical: chemicals) {
			chemical.calculateEnvironmentalIndexes(impactFactors, systemPressure);
			if (chemical.getEnvironmentalIndex() < eIndex && chemical.getAirIndex() < aIndex) {
				chemical.calculateReplacementScore(state);
				this.add(chemical);
			}
		}
		
	}

	private void sort() {
		double minScore, thisScore;
		int minIndex;
		Chemical chem1;
		
		for (int i=0; i<this.size()-1; i++) {
			minIndex = i;
			minScore = this.get(i).getReplacementScore();
			for (int j=i+1; j<this.size(); j++) {
				thisScore = this.get(j).getReplacementScore();
				if (thisScore < minScore) {
					minIndex = j;
					minScore = thisScore;
				}
			}
			if (minIndex!=i) {
				chem1 = get(i);
				set(i, get(minIndex));
				set(minIndex, chem1);
			}
		}
		changed = true;
	}
	
	private Chemicals sort(Chemicals chemicals) {
		double minScore, thisScore;
		int minIndex;
		Chemical chem1;
		
		for (int i=0; i<chemicals.size()-1; i++) {
			minIndex = i;
			minScore = chemicals.get(i).getReplacementScore();
			for (int j=i+1; j<chemicals.size(); j++) {
				thisScore = chemicals.get(j).getReplacementScore();
				if (thisScore < minScore) {
					minIndex = j;
					minScore = thisScore;
				}
			}
			if (minIndex!=i) {
				chem1 = chemicals.get(i);
				chemicals.set(i, chemicals.get(minIndex));
				chemicals.set(minIndex, chem1);
			}
		}
		changed = true;
		
		return chemicals;
	}
	
	public void bubbleSort() {
		Chemical chem1, chem2;
		boolean swflag = false;
		
		for (Chemical chemical: this) {
			chemical.calculateReplacementScore(state);
		}
		
		for (int i=0; i<this.size()-1; i++) {
			swflag = false;
			for (int j=0; j<this.size()-i-1; j+=2) {
				chem1 = this.get(j);
				chem2 = this.get(j+1);
				if (chem1.getReplacementScore() > chem2.getReplacementScore()) {
					this.set(j, chem2);
					this.set(j+1, chem1);
					swflag = true;
				}
			}
			for (int j=1; j<this.size()-i-1; j+=2) {
				chem1 = this.get(j);
				chem2 = this.get(j+1);
				if (chem1.getReplacementScore() > chem2.getReplacementScore()) {
					this.set(j, chem2);
					this.set(j+1, chem1);
					swflag = true;
				}
			}
			if (!swflag) break;
			changed = true;
		}
		
	}

	private void quickSort() {
		int midIndex, minQuick = 9;
		double score, firstScore;
		Vector<Chemicals> quickLists;
		Vector<Chemicals> newQuickLists = new Vector<Chemicals>();
		
		Chemicals belowList = new Chemicals();
		Chemicals equalList;
		Chemicals aboveList;
		
		for (int i=0; i<this.size(); i++) {
			belowList.add(this.get(i));
		}
		newQuickLists.add(belowList); // start from a list not sorted
		
		do {
			quickLists = newQuickLists;
			newQuickLists = new Vector<Chemicals>(quickLists.capacity());
			for (Chemicals chemicals: quickLists) {
				if (chemicals.size()<minQuick) { // don't do anything if this chemical list is already small
					newQuickLists.add(chemicals);
				} else {
					belowList = new Chemicals();
					equalList = new Chemicals();
					aboveList = new Chemicals();
					firstScore = chemicals.get(0).getReplacementScore();
					for (Chemical chemical: chemicals) { // break up which lists the chemicals fall into
						score = chemical.getReplacementScore();
						if (score < firstScore) {
							belowList.add(chemical);
						} else if (score > firstScore) {
							aboveList.add(chemical);
						} else {
							equalList.add(chemical);
						}
					}
					if (equalList.size()==1) {
						belowList.addAll(equalList);
					} else if (belowList.size()==0 && aboveList.size()!=0) { 
						belowList.addAll(equalList);
					} else if (aboveList.size()==0 && belowList.size()!=0) {
						aboveList.addAll(equalList);
					} else {
						midIndex = equalList.size()/2;
						belowList.addAll(equalList.subList(0, midIndex));
						aboveList.addAll(equalList.subList(midIndex, equalList.size()));
					}
					newQuickLists.add(belowList);  // add belowList into newQuickLists
					newQuickLists.add(aboveList);  // add aboveList into newQuickLists
				}
			}
		} while (newQuickLists.size()>quickLists.size());  // there are more breakups
		
		this.clear();
		for (Chemicals chemicals: newQuickLists ) {
			this.addAll(sort(chemicals));  // sort the replacements from this list into this
		}
		changed = true;
		
		return; // return the original replacements all sorted
	}

	public String[] getNames() {
		String[] names = new String[this.size()];
		for (int i=0; i<this.size(); i++) {
			String init = (int)(this.get(i).getReplacementScore()/1000.0) + ": ";
			names[i] = init + this.get(i).getName();
		}
		return names;
	}

	public boolean areChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
}
