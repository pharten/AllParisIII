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
//	private Chemicals chemicals;
	private boolean changed = true;
	
	public Replacements(State state, Chemicals chemicals) {
		super();
		this.state = state;
		filter(chemicals);
//		quickSort();
		quickSort_inPlace(0,this.size()-1);
//		testSort();
	}

	private void filter(Chemicals chemicals) {
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
//				chemical.setReplacementScore(1.0);
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
				changed = true;
			}
		}

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
				changed = true;
			}
		}
		
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
		
		return; // return the original replacements all sorted
	}
	
	private void quickSort_inPlace() { // non-recursive
		
		int minQuick = 9;
		int l, u, i, j;
		double score;
		Chemical temp;
		Vector<Integer> list = null;
		Vector<Integer> newList = new Vector<Integer>();
		
		newList.add(0);
		newList.add(this.size());
		
		do {
			
			list = newList;
			newList = new Vector<Integer>();

			for (int k=0; k<list.size()-1; k++) {
				
				newList.add(list.get(k));

				l = list.get(k);
				u = list.get(k+1)-1;

				if (u-l+1<=minQuick) { // don't do anything if this is already small

					// do nothing more

				} else { // more than minQuick elements

					i = l-1; j = u;
					score = this.get(u).getReplacementScore();

//					System.out.println(l+":"+u);

					while (true) {
						while (i<u && this.get(++i).getReplacementScore() <= score);
						while (j>l && this.get(--j).getReplacementScore() >= score);
						if (i>=j) break;
						temp = this.get(i);
						this.set(i, this.get(j));
						this.set(j, temp);
						changed = true;
					}

					if (i==u) {
						newList.add(i);
					} else { // i < u
						temp = this.get(i);
						this.set(i, this.get(u));
						this.set(u, temp);
						changed = true;
						newList.add(i+1);
					}

				}
				
			}
			newList.add(this.size());
			
		} while (newList.size() > list.size()); // there are more breakups
		
		for (int k=0; k<newList.size()-1; k++) {
			sort_inPlace(newList.get(k), newList.get(k+1)-1);
		}
		
		return; // return the original replacements all sorted
	}
	
	private void quickSort_inPlace(int l, int r) { // recursive
		// This method has a problem when the elements are all the same.
		// Then the last element is greater than or equal to all the rest
		// of the elements, and the block splits into block sizes r-l and 1
		// each recursive iteration.  If the starting block size is large
		// enough, a stack overflow error is given.
		
		int minQuick = 9;
		int i, j;
		double score;
		Chemical temp;

		if (r-l+1<=minQuick) {

			sort_inPlace(l, r);

		} else {

			i = l-1; j = r;
			score = this.get(r).getReplacementScore();
			
			while (true) {
				while (i<r && this.get(++i).getReplacementScore() <= score);
				while (j>l && this.get(--j).getReplacementScore() >= score);
				if (i>=j) break;
				temp = this.get(i);
				this.set(i, this.get(j));
				this.set(j, temp);
				changed = true;
			}
			
			if (i!=r) {
				temp = this.get(i);
				this.set(i, this.get(r));
				this.set(r, temp);
				changed = true;
			}
			
			if (i<r) {
				
				quickSort_inPlace(l,i-1);
				quickSort_inPlace(i+1,r);
				
			} else { // i==r, all values <= last value
				
				// j is first element (from right-to-left) whose score is less than last element
				
				if (j-l+1 > minQuick) {
					
					int m = (r+l)/2;
					if (this.get(m).getReplacementScore()!=score) {
						// try midpoint for partitioning instead
						temp = this.get(m);
						this.set(m, this.get(r));
						this.set(r, temp);
						changed = true;
						quickSort_inPlace(l,r);
					} else {
						quickSort_inPlace(l,j);
					}

				} else if (j>l) { // all scores before j (from right-to-left) are the same value, no more sorting needed
					
					quickSort_inPlace(l,j);
					
				} else {
					
					// nothing more needed
					
				}
				
			}



		}

		return; // return the original replacements all sorted
	}
	
	private void sort_inPlace(int lower, int upper) {
		
		Chemical temp;
		
		int size = upper-lower+1;
		if (size<=1) {
			
			// do nothing
			
		} else if (size==2) {
			
			temp = this.get(lower);
			if (temp.getReplacementScore()>this.get(upper).getReplacementScore()) {
				this.set(lower, this.get(upper));
				this.set(upper, temp);
				changed = true;
			}
			
		} else {
			
			double minScore, thisScore;
			int minIndex;
			
			for (int i=lower; i<upper; i++) {
				minIndex = i;
				temp = this.get(minIndex);
				minScore = temp.getReplacementScore();
				for (int j=i+1; j<=upper; j++) {
					thisScore = this.get(j).getReplacementScore();
					if (thisScore < minScore) {
						minIndex = j;
						minScore = thisScore;
					}
				}
				if (minIndex!=i) {
					this.set(i, this.get(minIndex));
					this.set(minIndex, temp);
					changed = true;
				}
				
			}
			
		}
		
	}
	
	private void testSort() {
		for (int i=0; i<this.size()-1; i++) {
			if (this.get(i).getReplacementScore()>this.get(i+1).getReplacementScore()) {
				System.out.println("Error: "+i+", "+this.get(i).getReplacementScore()+" > "+this.get(i+1).getReplacementScore());
				break;
			}
		}
	}

	public String[] getNames() {
		String[] names = new String[this.size()];
		for (int i=0; i<this.size(); i++) {
			String init = (int)(this.get(i).getReplacementScore()*1.0e-9) + ": ";
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
