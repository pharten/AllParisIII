package parisInit;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Vector;

import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.printing.Printer;

import parisWork.Chemical;
import parisWork.Mixture;

public class State extends Object implements Serializable, Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -979581117109756754L;
	
	private String fileName = null;
	private int openScreen = 0;
	private String systemName = null;
	private Units systemUnit = Units.SI;
	private String systemTemp = "25.0";//TODO if default units is SI wouldn't you store in SI?
	private String systemPres = "1.0";//TODO if default units is SI wouldn't you store in SI?
	private int screen0StackOption = 1;
	private String screen0TableHeader = "Wt%";
	private Mixture mixture = null;
	private int[] impactFactors = {5, 5, 5, 5, 5, 5, 5, 5};
	private double[] pTolerances = {11.0, 14.0, 10.0, 30.0, 16.0, 30.0, 30.0};
	private double[] pDesiredVals = null;
	private int pScale = 10;
	private double[] aTolerances = {25.0, 30.0, 28.0, 30.0, 30.0, 30.0, 30.0, 30.0, 30.0, 30.0};
	private double[] aDesiredVals = null;
	private int aScale = 10;
	private boolean single = true;
	private int replacementIndex = 0;
	private int replacementTopIndex = 0;
	private boolean includeMiscibility = true;

	public State() {
		super();
	}
	
	public static State readFromFile(String filename) throws IOException, ClassNotFoundException {
		State state;
		
		FileInputStream fis = new FileInputStream(filename);
		if (filename.endsWith(".xml")) {
			XMLDecoder decoder = new XMLDecoder(fis);
			state = (State) decoder.readObject();
			decoder.close();
		} else {
			ObjectInputStream ois = new ObjectInputStream(fis);
			state = (State) ois.readObject();
			ois.close();
		}
		fis.close();
		state.setFileName(filename);
		
		return state;
	}
	
	public void writeToFile() throws IOException, ClassNotFoundException {
		
		String filename = this.getFileName();
		this.setFileName(null);
		FileOutputStream fos = new FileOutputStream(filename);
		if (filename.endsWith(".xml")) {
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(this);
			encoder.flush();
			encoder.close();
		} else {
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.flush();
			oos.close();
		}
		fos.close();
		this.setFileName(filename);
		
	}
	
	public boolean equals(State other) { // deep equals

		if (this==other) return true;
		if (other==null) return false;
		if (!(other instanceof State)) return false;
		
		// compare primitives
		if (this.screen0StackOption!=other.screen0StackOption) return false;
		if (this.pScale!=other.pScale) return false;
		if (this.aScale!=other.aScale) return false;
		if (this.single!=other.single) return false;
		if (this.replacementIndex!=other.replacementIndex) return false;
		if (this.replacementTopIndex!=other.replacementTopIndex) return false;
		
		// compare strings
		if (this.fileName==null && other.fileName!=null) return false;
		if (this.fileName!=null && !this.fileName.equals(other.fileName)) return false;
		if (this.screen0TableHeader==null && other.screen0TableHeader!=null) return false;
		if (this.screen0TableHeader!=null && !this.screen0TableHeader.equals(other.screen0TableHeader)) return false;
		if (this.systemName==null && other.systemName!=null) return false;
		if (this.systemName!=null && !this.systemName.equals(other.systemName)) return false;
		if (this.systemPres==null && other.systemPres!=null) return false;
		if (this.systemPres!=null && !this.systemPres.equals(other.systemPres)) return false;
		if (this.systemTemp==null && other.systemTemp!=null) return false;
		if (this.systemTemp!=null && !this.systemTemp.equals(other.systemTemp)) return false;
		
		// compare arrays of primitives
		if (!Arrays.equals(this.impactFactors, other.impactFactors)) return false; 
		if (!Arrays.equals(this.pTolerances, other.pTolerances)) return false;
		if (!Arrays.equals(this.pDesiredVals, other.pDesiredVals)) return false;
		if (!Arrays.equals(this.aTolerances, other.aTolerances)) return false;
		if (!Arrays.equals(this.aDesiredVals, other.aDesiredVals)) return false;
		
		// compare user defined classes
		if (this.systemUnit==null && other.systemUnit!=null) return false;
		if (this.systemUnit!=null && !this.systemUnit.equals(other.systemUnit)) return false;
		if (this.mixture==null && other.mixture!=null) return false;
		if (this.mixture!=null && !this.mixture.equals(other.mixture)) return false;

		return true;
	}
	
	public boolean equalsForReplacements(State other) { // deep equals for replacements

		if (this==other) return true;
		if (other==null) return false;
		if (!(other instanceof State)) return false;
		
		// compare primitives
//		if (this.screen0StackOption!=other.screen0StackOption) return false;
		if (this.pScale!=other.pScale) return false;
		if (this.aScale!=other.aScale) return false;
		if (this.single!=other.single) return false;
//		if (this.replacementIndex!=other.replacementIndex) return false;
//		if (this.replacementTopIndex!=other.replacementTopIndex) return false;
		
		// compare strings
//		if (this.fileName==null && other.fileName!=null) return false;
//		if (this.fileName!=null && !this.fileName.equals(other.fileName)) return false;
//		if (this.screen0TableHeader==null && other.screen0TableHeader!=null) return false;
//		if (this.screen0TableHeader!=null && !this.screen0TableHeader.equals(other.screen0TableHeader)) return false;
//		if (this.systemName==null && other.systemName!=null) return false;
//		if (this.systemName!=null && !this.systemName.equals(other.systemName)) return false;
		if (this.systemPres==null && other.systemPres!=null) return false;
		if (this.systemPres!=null && !this.systemPres.equals(other.systemPres)) return false;
		if (this.systemTemp==null && other.systemTemp!=null) return false;
		if (this.systemTemp!=null && !this.systemTemp.equals(other.systemTemp)) return false;
		
		// compare arrays of primitives
		if (!Arrays.equals(this.impactFactors, other.impactFactors)) return false; 
		if (!Arrays.equals(this.pTolerances, other.pTolerances)) return false;
		if (!Arrays.equals(this.pDesiredVals, other.pDesiredVals)) return false;
		if (!Arrays.equals(this.aTolerances, other.aTolerances)) return false;
		if (!Arrays.equals(this.aDesiredVals, other.aDesiredVals)) return false;
		
		// compare user defined classes
//		if (this.systemUnit==null && other.systemUnit!=null) return false;
//		if (this.systemUnit!=null && !this.systemUnit.equals(other.systemUnit)) return false;
		if (this.mixture==null && other.mixture!=null) return false;
		if (this.mixture!=null && !this.mixture.deepEquals(other.mixture)) return false;

		return true;
	}
	
	public State clone() { // deep copy
		
		State clone = null;
		try {
			clone = (State) super.clone();

			if (this.fileName!=null) clone.fileName = new String(this.fileName);
			clone.screen0StackOption = this.screen0StackOption;
			clone.screen0TableHeader = new String(this.screen0TableHeader);
			clone.systemName = new String(this.systemName);
			clone.systemPres = new String(this.systemPres);
			clone.systemTemp = new String(this.systemTemp);
			clone.systemUnit = this.systemUnit;
			if (this.mixture!=null)	clone.mixture = this.mixture.clone();
			clone.impactFactors = this.impactFactors.clone();
			clone.pTolerances = this.pTolerances.clone();
			if (this.pDesiredVals!=null) clone.pDesiredVals = this.pDesiredVals.clone();
			clone.pScale = this.pScale;
			clone.aTolerances = this.aTolerances.clone();
			if (this.aDesiredVals!=null) clone.aDesiredVals = this.aDesiredVals.clone();
			clone.aScale = this.aScale;
			clone.single = this.single;
			clone.replacementIndex = this.replacementIndex;
			clone.replacementTopIndex = this.replacementTopIndex;
			clone.includeMiscibility = this.includeMiscibility;

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return clone;
	}
	
	public boolean deepEquals(State other) { // deep equals

		if (this==other) return true;
		if (other==null) return false;
		if (!(other instanceof State)) return false;
		
		// compare primitives
		if (this.screen0StackOption!=other.screen0StackOption) return false;
		if (this.pScale!=other.pScale) return false;
		if (this.aScale!=other.aScale) return false;
		if (this.single!=other.single) return false;
		if (this.replacementIndex!=other.replacementIndex) return false;
		if (this.replacementTopIndex!=other.replacementTopIndex) return false;
		
		// compare strings
		if ((this.fileName==null ? other.fileName!=null : !this.fileName.equals(other.fileName))) return false;
		if (this.screen0TableHeader==null ? other.screen0TableHeader!=null : !this.screen0TableHeader.equals(other.screen0TableHeader)) return false;
		if (this.systemName==null ? other.systemName!=null : !this.systemName.equals(other.systemName)) return false;
		if (this.systemPres==null ? other.systemPres!=null : !this.systemPres.equals(other.systemPres)) return false;
		if (this.systemTemp==null ? other.systemTemp!=null : !this.systemTemp.equals(other.systemTemp)) return false;
		
		// compare arrays of primitives
		if (!Arrays.equals(this.impactFactors, other.impactFactors)) return false; 
		if (!Arrays.equals(this.pTolerances, other.pTolerances)) return false;
		if (!Arrays.equals(this.pDesiredVals, other.pDesiredVals)) return false;
		if (!Arrays.equals(this.aTolerances, other.aTolerances)) return false;
		if (!Arrays.equals(this.aDesiredVals, other.aDesiredVals)) return false;
		
		// compare user defined classes
		if (this.systemUnit==null ? other.systemUnit!=null : !this.systemUnit.equals(other.systemUnit)) return false;
		if (this.mixture==null ? other.mixture!=null : !this.mixture.deepEquals(other.mixture)) return false;

		return true;
	}
	
	public void print(Printer p, int[] line, int[] page) {
		DecimalFormat df = new DecimalFormat("0.0000");
		String output = null;
		
		Rectangle trim = p.computeTrim(0, 0, 0, 0);
		Point dpi = p.getDPI();
		int leftMargin = dpi.x + trim.x;
		int topMargin = dpi.y / 2 + trim.y;
		GC gc = new GC(p);
		FontMetrics metrics = gc.getFontMetrics();
		int lineHeight = metrics.getHeight();
		int linesPerPage = (p.getBounds().height-2*topMargin)/lineHeight;
		
		Mixture initialMixture = this.getMixture();
		Vector<Double> wghtFractions = initialMixture.getWghtFractions();
		Vector<Chemical> chemicals = initialMixture.getChemicals();
		
		if (line[0]>=linesPerPage-1) {  // new page
			if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) p.endPage();
			line[0]=0;
			page[0]++;
			if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) p.startPage();
		}

		line[0]++;
		if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) {
			output = "Solvent replacements for "+this.systemName+" at "+this.getSystemTemp()+" C and "+this.systemPres+" Atm";
			gc.drawString(output, leftMargin, topMargin+(line[0]-1)*lineHeight);
		}
		
		if (line[0]>=linesPerPage-wghtFractions.size()-1) {  // new page
			if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) p.endPage();
			line[0]=0;
			page[0]++;
			if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) p.startPage();
		}

		line[0]++;
		if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) {
			output = "Initial Mixture: Environmental Index = "+df.format(initialMixture.getEnvironmentalIndex())+
					", Air Index = "+df.format(initialMixture.getAirIndex());
			gc.drawString(output, leftMargin, topMargin+(line[0]-1)*lineHeight);
		}

		for (int i=0; i<wghtFractions.size(); i++) {
			line[0]++;
			if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) {
				output = df.format(wghtFractions.get(i))+": "+chemicals.get(i).getCAS()+
						", "+chemicals.get(i).getName();
				gc.drawString(output, leftMargin, topMargin+(line[0]-1)*lineHeight);
			}
		}
		
		if (line[0]>=linesPerPage-4) {  // new page
			if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) p.endPage();
			line[0]=0;
			page[0]++;
			if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) p.startPage();
		}

		line[0]++;
		if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) {
			output = "Environmental Impact weights: Ingestion = "+this.impactFactors[0]+", Inhalation = "+this.impactFactors[1]+", Acid Rain = "+this.impactFactors[7]+",";
			gc.drawString(output, leftMargin, topMargin+(line[0]-1)*lineHeight);
		}
		
		line[0]++;
		if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) {
			output = "Terrrestrial Toxicity = "+this.impactFactors[2]+", Aquatic Toxicity = "+this.impactFactors[3]+", Global Warming Potential = "+this.impactFactors[4]+",";
			gc.drawString(output, leftMargin, topMargin+(line[0]-1)*lineHeight);
		}
		
		line[0]++;
		if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) {
			output = "Ozone Depletion Potential = "+this.impactFactors[5]+", Photochemical Oxidation Potential = "+this.impactFactors[6];
			gc.drawString(output, leftMargin, topMargin+(line[0]-1)*lineHeight);
		}
		
		gc.dispose();
	}
	
	public void calculateEnvironmentalIndexes() {
		Mixture mixture = this.getMixture();
		if (mixture!=null) {
			Chemical[] chemicals = mixture.getChemicals2();
			for (int i=0; i<chemicals.length; i++) {
				chemicals[i].calculateEnvironmentalIndexes(getImpactFactors(), pressureConvertToSI());
			}
			mixture.calculateEnvironmentalIndexesOfMixture();
		}
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getOpenScreen() {
		return openScreen;
	}

	public void setOpenScreen(int openScreen) {
		this.openScreen = openScreen;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	public Units getSystemUnit() {
		return systemUnit;
	}

	public void setSystemUnit(Units units) {
		this.systemUnit = units;
	}
	
	public void setSystemUnit(String unitsText) {
		this.systemUnit = Units.valueOf(unitsText);
	}
	
	public String getSystemTemp() {
		return systemTemp;
	}

	public void setSystemTemp(String systemTemp) {
		this.systemTemp = systemTemp;
	}

	public String getSystemPres() {
		return systemPres;
	}

	public void setSystemPres(String systemPres) {
		this.systemPres = systemPres;
	}

	public int getScreen0StackOption() {
		return screen0StackOption;
	}

	public void setScreen0StackOption(int screen0StackOption) {
		this.screen0StackOption = screen0StackOption;
	}

	public String getScreen0TableHeader() {
		return screen0TableHeader;
	}

	public void setScreen0TableHeader(String tableHeader) {
		this.screen0TableHeader = tableHeader;
	}
	
	public double tempConvertToSI() {
		double temp = Double.parseDouble(getSystemTemp()); // String given in celsius
		return temp + 273.15;  // convert to Kelvin
	}
	
	public double pressureConvertToSI() {
		double pressure = Double.parseDouble(getSystemPres()); // String given in atms
		return pressure * 101.325;  // convert to kPa	
	}

	public Mixture getMixture() {
		return mixture;
	}

	public void setMixture(Mixture mixture) {
		this.mixture = mixture;
	}

	public int[] getImpactFactors() {
		return impactFactors;
	}

	public void setImpactFactors(int[] impactFactors) {
		this.impactFactors = impactFactors;
	}

	public double[] getPTolerances() {
		return pTolerances;
	}

	public void setPTolerances(double[] pTolerances) {
		this.pTolerances = pTolerances;
	}

	public double[] getPDesiredVals() {
		return pDesiredVals;
	}

	public void setPDesiredVals(double[] pDesiredVals) {
		this.pDesiredVals = pDesiredVals;
	}
	
	public void setPDesiredVals() throws Exception {
		
		if (mixture==null || mixture.getChemicals().size()==0) throw new Exception("No mixture found");
		
		double[] desiredVals = new double[8];
		
		mixture.calculatePhysicalPropertiesOfMixture(this.tempConvertToSI());
		desiredVals[0] = mixture.getMolecularWeight();
		desiredVals[1] = mixture.getDensity();
		desiredVals[2] = mixture.getBoilingPoint();
		desiredVals[3] = mixture.getVaporPressure();
		desiredVals[4] = mixture.getSurfaceTension();
		desiredVals[5] = mixture.getViscosity();
		desiredVals[6] = mixture.getThermalConductivity();
		desiredVals[7] = mixture.getFlashPoint();
		this.setPDesiredVals(desiredVals);
	}

	public int getPScale() {
		return pScale;
	}

	public void setPScale(int pScale) {
		this.pScale = pScale;
	}

	public double[] getATolerances() {
		return aTolerances;
	}

	public void setATolerances(double[] aTolerances) {
		this.aTolerances = aTolerances;
	}

	public double[] getADesiredVals() {
		return aDesiredVals;
	}
	
	public void setADesiredVals(double[] aDesiredVals) {
		this.aDesiredVals = aDesiredVals;
	}

	public void setADesiredVals() throws Exception {
		
		if (mixture==null || mixture.getChemicals().size()==0) throw new Exception("No mixture found");
		
		double[] desiredVals = new double[10];
		
		mixture.calculateActivityCoefficientsOfMixture(this.tempConvertToSI());
		desiredVals[0] = mixture.getInfDilActCoef_ethanol();
		desiredVals[1] = mixture.getInfDilActCoef_diethyl_ether();
		desiredVals[2] = mixture.getInfDilActCoef_acetone();
		desiredVals[3] = mixture.getInfDilActCoef_water();
		desiredVals[4] = mixture.getInfDilActCoef_benzene();
		desiredVals[5] = mixture.getInfDilActCoef_cis_2_heptene();
		desiredVals[6] = mixture.getInfDilActCoef_n_propyl_chloride();
		desiredVals[7] = mixture.getInfDilActCoef_n_heptadecane();
		desiredVals[8] = mixture.getInfDilActCoef_n_propylamine();
		desiredVals[9] = mixture.getInfDilActCoef_dimethyl_disulfide();
		this.setADesiredVals(desiredVals);
	}

	public int getAScale() {
		return aScale;
	}

	public void setAScale(int aScale) {
		this.aScale = aScale;
	}

	public boolean isSingle() {
		return single;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}

	public int getReplacementIndex() {
		return replacementIndex;
	}

	public void setReplacementIndex(int replacementIndex) {
		this.replacementIndex = replacementIndex;
	}
	
	public int getReplacementTopIndex() {
		return replacementTopIndex;
	}

	public void setReplacementTopIndex(int topIndex) {
		this.replacementTopIndex = topIndex;	
	}

	/**
	 * @return includeMiscibility
	 */
	public boolean includeMiscibility() {
		return includeMiscibility;
	}

	/**
	 * @param includeMiscibility the includeMiscibility to set
	 */
	public void setIncludeMiscibility(boolean includeMiscibility) {
		this.includeMiscibility = includeMiscibility;
	}
	
}
