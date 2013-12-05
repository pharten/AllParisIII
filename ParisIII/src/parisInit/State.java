package parisInit;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

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
	private String systemTemp = "25";//TODO if default units is SI wouldn't you store in SI?
	private String systemPres = "1.0";//TODO if default units is SI wouldn't you store in SI?
	private int screen0StackOption = 2;
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
		
		return state;
	}
	
	public void writeToFile(String filename) throws IOException, ClassNotFoundException {
		
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
		
	}
	
	@Override
	public State clone() { // deep copy
		State state = new State();
		state.fileName = this.fileName;
		state.screen0StackOption = this.screen0StackOption;
		state.screen0TableHeader = this.screen0TableHeader;
		state.systemName = this.systemName;
		state.systemPres = this.systemPres;
		state.systemTemp = this.systemTemp;
		state.systemUnit = this.systemUnit;
		if (this.mixture!=null) state.mixture = this.mixture.clone();
		state.impactFactors = this.impactFactors.clone();
		state.pTolerances = this.pTolerances.clone();
		if (this.pDesiredVals!=null) state.pDesiredVals = this.pDesiredVals.clone();
		state.pScale = this.pScale;
		state.aTolerances = this.aTolerances.clone();
		if (this.aDesiredVals!=null) state.aDesiredVals = this.aDesiredVals.clone();
		state.aScale = this.aScale;
		return state;
	}
	
	public void calculateEnvironmentalIndexes() {
		Mixture mixture = this.getMixture();
		if (mixture!=null) mixture.calculateEnvironmentalIndexesOfMixture();
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
	
}
