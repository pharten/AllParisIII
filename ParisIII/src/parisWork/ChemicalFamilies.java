package parisWork;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;


public class ChemicalFamilies extends Vector<ChemicalFamily> implements Serializable, Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -732059379487391451L;


	public ChemicalFamilies() {
		super();
	}

	public static ChemicalFamilies readFromFile(String filename) {
		
		ChemicalFamilies families = new ChemicalFamilies();
		try {
			if (filename.endsWith(".txt")) {
				families.addAll(readByTxt(filename));
			} else if (filename.endsWith(".xml")) {
				families.addAll(readByXML(filename));
			} else {
				throw new Exception("Filename has unaccepted extension.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return families;
	}
	
	public void sortByName() {
		ChemicalFamily family;
		String name;
		int index = 0;
		for (int i=0; i<this.elementCount; i++) {
			index = i;
			name = this.get(index).getName();
			for (int j=i+1; j<this.elementCount; j++) {
				if (name.compareTo(this.get(j).getName())>0) {
					index=j;
					name = this.get(index).getName();
				}
			}
			if (index!=i) {
				family = this.get(i);
				this.set(i, this.get(index));
				this.set(index, family);
			}
		}
	}
	
	public String[] getAllNames() {
		String[] names = new String[this.elementCount];
		for (int i=0; i<this.elementCount; i++) {
			names[i] = this.get(i).getName().substring(0);
		}
		return names;
	}
	
	private static ChemicalFamilies readByTxt(String fileName) throws IOException, Exception {
		String line, lineSplit[], name, trial;
		char[] linechar = null;
		int num, lineno = 0;
		int cnt, pos, start, end;
		boolean qflag;
		char charpos;
		ChemicalFamilies families = new ChemicalFamilies();

		File file = new File(fileName);
//		System.out.println(file.getAbsolutePath());
		FileReader fileReader = new FileReader(file);
		BufferedReader buf = new BufferedReader(fileReader);
		while ((line = buf.readLine()) != null) {
			lineno++;

			linechar = line.toCharArray();
			lineSplit = new String[linechar.length];
			
			qflag = false;
			cnt = 0;
			pos = 0;
			start = 0;
			end = 0;
			while (pos<linechar.length) {
				charpos = linechar[pos];
				if (qflag && charpos=='"') {
					qflag = false;  // turn quote flag off
					end = pos-1;
				} else if (charpos=='"') {
					qflag = true;	// turn quote flag on
					start = pos+1;
				} else if (!qflag && charpos==',') {
					if (end <= start) end = pos-1;
					lineSplit[cnt++] = new String(linechar, start, end-start+1).trim();
					start = pos+1;	// increment starting position
				}
				pos++;				// increment char position;
			}

			if (end<=start) end = pos-1;
			trial = new String(linechar, start, end-start+1).trim();

			if (cnt==0) {
				lineSplit[cnt++] = trial;
			} else if (trial.length()!=0) {
				lineSplit[cnt++] = trial;
			}

			if (cnt!=2) {
				throw new Exception("Problem on line number "+lineno+": "+cnt);
			} else {
//				System.out.println(lineSplit[0]+", "+lineSplit[1]);
				name = lineSplit[0].trim();
				num = Integer.parseInt(lineSplit[1].trim());
				families.add(new ChemicalFamily(num, name));
			}
		}
		buf.close();
		
		return families;
	}
	
	private static ChemicalFamilies readByXML(String filename) throws IOException {
		XMLDecoder decoder = new XMLDecoder(ClassLoader.getSystemResourceAsStream(filename));
		ChemicalFamilies families = (ChemicalFamilies)decoder.readObject();
		decoder.close();
		return families;
	}
	
	public void writeByXML(String filename) throws IOException {
//		ChemicalFamilies families = (ChemicalFamilies) this.clone();
		FileOutputStream fos = new FileOutputStream(filename);
		XMLEncoder encoder = new XMLEncoder(fos);
		encoder.writeObject(this);
		encoder.flush();
		encoder.close();
		fos.close();
	}

}
