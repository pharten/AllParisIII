package parisInit;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

public class States extends Vector<State> implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6440978594650102559L;
	private State activeState = null;

	public States() {
		super();
	}
	
//	public static States readFromFile(String filename) {
//		
//		States states = null;
//		try {
//			if (filename.endsWith(".xml")) {
//				states = States.readByXML(filename);
//			} else {
//				throw new Exception("Filename has unaccepted extension.");
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return states;
//	}
//	
//	private static States readByXML(String filename) throws IOException {
//		FileInputStream fis = new FileInputStream(filename);
//		XMLDecoder decoder = new XMLDecoder(fis);
//		States states = (States)decoder.readObject();
//		decoder.close();
//		return states;
//	}
//	
//	public void writeByXML(String filename) throws IOException {
//		FileOutputStream fos = new FileOutputStream(filename);
//		XMLEncoder encoder = new XMLEncoder(fos);
//		encoder.writeObject(this);
//		encoder.flush();
//		encoder.close();
//		fos.close();
//	}
	
	public State getElementBySystemName(String systemName) {
		
		State state = null;
		for (int i=0; i< this.elementCount; i++) {
			if (this.get(i).getSystemName().equals(systemName)) {
				state = this.get(i);
				setActiveState(state);
				break;
			}
		}
		return state;
		
	}
	
	public void addToList(State state) {
		String name = state.getSystemName().trim();
		if (this.containsName(name)) {
			if (!name.endsWith("_")) name = name.concat("_");
			String newName = "";
			for (int i=0; i<this.elementCount; i++) {
				newName = name.concat(""+i);
				if (!this.containsName(newName)) break;
			}
			state.setSystemName(newName);
		}
		add(1,state);
	}
	
	public boolean containsName(String name) {
		for (int i=0; i<this.elementCount; i++) {
			if (name.matches(this.get(i).getSystemName())) return true;
		}
		return false;
	}
	
	public String[] getSystemNames() {
		String[] systemNames = new String[this.elementCount];
		for (int i=0; i<this.elementCount; i++) {
			systemNames[i] = this.get(i).getSystemName();
		}
		return systemNames;
	}

	public State getActiveState() {
		return activeState;
	}

	public void setActiveState(State activeState) {
		this.activeState = activeState;
	}
	
}
