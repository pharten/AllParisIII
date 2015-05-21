package parisWork;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Vector;

import parisInit.State;
import parisInit.Units;

public class Chemical extends Object implements Serializable, Cloneable {

	private static final long serialVersionUID = 3406345795798529960L;
	private String Name = "";
	private String CAS = "";
	private String formula = "";//molecular formula
	private String structure = "";
	
	private String Smiles = "";
	private Vector<String> Synonyms = new Vector<String>();
	private int ChemicalFamilyID = 0;

	//**************************************************************************
	//Physical properties in common units:
	private double molecularWeight = 0;//molecular weight kg/kmol
	
	//@TODO- make naming of physical properties consistent- either BP or boilingPoint
	
	private double meltingPoint;//melting point in K
	private double boilingPoint;//normal boiling point in K
	private double solubility = 0;//water solubity in kg/m3
	private double flashPoint = 0;//flash point in K
	private double density;// liquid density in kg/m3
	private double thermalConductivity;// thermal conductivity in J/(m-s-K)
	private double viscosity;// liquid viscosity in  kg/m-s
	private double surfaceTension;// surface tension in kg/s2
	private double vaporPressure;//vapor pressure in kPa
	private double heatCapacity;// heat capacity in kJ/kmol-K
	private double heatCapacitySolid;// heat capacity in kJ/kmol-K
	

	private double enthalpyOfFusion;// change in enthalpy  in kJ/kmol resulting from heating one mole of a substance to change its state from a solid to a liquid. The temperature at which this occurs is the melting point.
	
	
	
	private String meltingPointSource = "";//Source of melting point
	private String boilingPointSource = "";//Source of normal boiling point
	private String solubilitySource = "";//Source of water solubility
	private String flashPointSource = "";//Source of flash point
	private String densitySource = "";// Source of liquid density
	private String thermalConductivitySource = "";// Source of thermal conductivity
	private String viscositySource = "";// Source of liquid viscosity in cP (centipoise)
	private String surfaceTensionSource = "";// Source of surface tension
	private String vaporPressureSource = "";//Source of vapor pressure
	private String heatCapacitySource = "";//Source of heat capacity

	private double antoineConstantA;//antoine A constant for vapor pressure
	private double antoineConstantB;//antoine B constant for vapor pressure
	private double antoineConstantC;//antoine C constant for vapor pressure
	private double antoineTmin;//minimum temperature for which antoineEquation should be used
	private double antoineTmax;//maximum temperature for which antoineEquation should be used
	private String antoineSource = "";//depending on the source the input units and output units for antoine equation may change
	
	private double Tc;//critical temperature in K
	private double Pc;//critical pressure in bar

	private double omega;//eccentric factor, dimensionless
	
	//Infinite dilution activity coefficients
	private double infDilActCoef_ethanol;//64-17-5
	private double infDilActCoef_diethyl_ether;//60-29-7
	private double infDilActCoef_acetone;//67-64-1
	private double infDilActCoef_water;//7732-18-5
	private double infDilActCoef_benzene;//71-43-2
	private double infDilActCoef_cis_2_heptene;//6443-92-1
	private double infDilActCoef_n_propyl_chloride;//540-54-5
	private double infDilActCoef_n_heptadecane;//629-78-7
	private double infDilActCoef_n_propylamine;//107-10-8
	private double infDilActCoef_dimethyl_disulfide;//624-92-0

	//**************************************************************************
	//Environmental properties
//	private double oralRatLD50;//used to calculate HtoxIngestion
//	private double fatheadMinnowLC50;//used to calculate Aquatic Tox
//
//	private String oralRatLD50Source;//used to calculate HtoxIngestion
//	private String fatheadMinnowLC50Source;//used to calculate Aquatic Tox
	
	//WAR properties
	private double HtoxIngestion = 0;
	private double HtoxInhalation = 0;
//	private double HtoxDermal = 0;
	private double TerrestrialTox = 0;
	private double AquaticTox = 0;
	private double GWP = 0;
	private double ODP = 0;
	private double PCOP = 0;
	private double AP = 0;

	//**************************************************************************
	//Properties used in reference screen
	
	private double LFlamLimit = 0;
	private double UFlamLimit = 0;
	private double AutoIgnitTemp = 0;
	
	private double airIndex = 0;
	private double environmentalIndex = 0;
	
	private double replacementScore = 0.0;
	
	public Chemical() {
		super();
	}
	
	@Override
	public Chemical clone() throws CloneNotSupportedException { // deep copy (not strings)
		Chemical clone = (Chemical)super.clone();
		
		clone.Name = this.Name;
		clone.CAS = this.CAS;
		clone.formula = this.formula;
		clone.structure = this.structure;
		clone.Smiles = this.Smiles;
		clone.Synonyms = new Vector<String>();
		for (int i=0; i<this.Synonyms.size(); i++) {
			clone.Synonyms.add(this.Synonyms.get(i));
		}
		
		clone.ChemicalFamilyID = this.ChemicalFamilyID;

		clone.molecularWeight = this.molecularWeight;
		clone.meltingPoint = this.meltingPoint;
		clone.boilingPoint = this.boilingPoint;
		clone.solubility = this.solubility;
		clone.flashPoint = this.flashPoint;
		clone.density = this.density;
		clone.thermalConductivity = this.thermalConductivity;
		clone.viscosity = this.viscosity;
		clone.surfaceTension = this.surfaceTension;
		clone.vaporPressure = this.vaporPressure;
		clone.heatCapacity = this.heatCapacity;
		
		clone.LFlamLimit = this.LFlamLimit;
		clone.UFlamLimit = this.LFlamLimit;
		clone.AutoIgnitTemp = this.AutoIgnitTemp;
		clone.antoineConstantA = this.antoineConstantA;
		clone.antoineConstantB = this.antoineConstantB;
		clone.antoineConstantC = this.antoineConstantC;
		clone.antoineTmax = this.antoineTmax;
		clone.antoineTmin = this.antoineTmin;

		clone.meltingPointSource = this.meltingPointSource;
		clone.boilingPointSource = this.boilingPointSource;
		clone.solubilitySource = this.solubilitySource;
		clone.flashPointSource = this.flashPointSource;
		clone.densitySource = this.densitySource;
		clone.thermalConductivitySource = this.thermalConductivitySource;
		clone.viscositySource = this.viscositySource;
		clone.surfaceTensionSource = this.surfaceTensionSource;
		clone.vaporPressureSource = this.vaporPressureSource;
		clone.heatCapacitySource = this.heatCapacitySource;
		clone.antoineSource = this.antoineSource;

		clone.infDilActCoef_ethanol = this.infDilActCoef_ethanol;
		clone.infDilActCoef_diethyl_ether = this.infDilActCoef_diethyl_ether;
		clone.infDilActCoef_acetone = this.infDilActCoef_acetone;
		clone.infDilActCoef_water = this.infDilActCoef_water;
		clone.infDilActCoef_benzene = this.infDilActCoef_benzene;
		clone.infDilActCoef_cis_2_heptene = this.infDilActCoef_cis_2_heptene;
		clone.infDilActCoef_n_propyl_chloride = this.infDilActCoef_n_propyl_chloride;
		clone.infDilActCoef_n_heptadecane = this.infDilActCoef_n_heptadecane;
		clone.infDilActCoef_n_propylamine = this.infDilActCoef_n_propylamine;
		clone.infDilActCoef_dimethyl_disulfide = this.infDilActCoef_dimethyl_disulfide;
		
		clone.HtoxIngestion = this.HtoxIngestion;
		clone.HtoxInhalation = this.HtoxInhalation;
//		clone.HtoxDermal = this.HtoxDermal;
		clone.TerrestrialTox = this.TerrestrialTox;
		clone.AquaticTox = this.AquaticTox;
		clone.GWP = this.GWP;
		clone.ODP = this.ODP;
		clone.PCOP = this.PCOP;
		clone.AP = this.AP;
		
		clone.airIndex = this.airIndex;
		clone.environmentalIndex = this.environmentalIndex;
		clone.replacementScore = this.replacementScore;

		return clone;
	}
	
	public boolean equals(Chemical other) {
		
		if (this==other) return true;
		if (other==null) return false;
		if (!(other instanceof Chemical)) return false;

		// compare primitives
		if (this.ChemicalFamilyID!=other.ChemicalFamilyID) return false;
		if (this.molecularWeight!=other.molecularWeight) return false;
		if (this.meltingPoint!=other.meltingPoint) return false;
		if (this.boilingPoint!=other.boilingPoint) return false;
		if (this.solubility!=other.solubility) return false;
		if (this.flashPoint!=other.flashPoint) return false;
		if (this.density!=other.density) return false;
		if (this.thermalConductivity!=other.thermalConductivity) return false;
		if (this.viscosity!=other.viscosity) return false;
		if (this.surfaceTension!=other.surfaceTension) return false;
		if (this.vaporPressure!=other.vaporPressure) return false;
		if (this.LFlamLimit!=other.LFlamLimit) return false;
		if (this.UFlamLimit!=other.UFlamLimit) return false;
		if (this.AutoIgnitTemp!=other.AutoIgnitTemp) return false;
		
		if (this.infDilActCoef_ethanol!=other.infDilActCoef_ethanol) return false;
		if (this.infDilActCoef_diethyl_ether!=other.infDilActCoef_diethyl_ether) return false;
		if (this.infDilActCoef_acetone!=other.infDilActCoef_acetone) return false;
		if (this.infDilActCoef_water!=other.infDilActCoef_water) return false;
		if (this.infDilActCoef_benzene!=other.infDilActCoef_benzene) return false;
		if (this.infDilActCoef_cis_2_heptene!=other.infDilActCoef_cis_2_heptene) return false;
		if (this.infDilActCoef_n_propyl_chloride!=other.infDilActCoef_n_propyl_chloride) return false;
		if (this.infDilActCoef_n_heptadecane!=other.infDilActCoef_n_heptadecane) return false;
		if (this.infDilActCoef_n_propylamine!=other.infDilActCoef_n_propylamine) return false;
		if (this.infDilActCoef_dimethyl_disulfide!=other.infDilActCoef_dimethyl_disulfide) return false;
		
		if (this.HtoxIngestion!=other.HtoxIngestion) return false;
		if (this.HtoxInhalation!=other.HtoxInhalation) return false;
//		if (this.HtoxDermal!=other.HtoxDermal) return false;
		if (this.TerrestrialTox!=other.TerrestrialTox) return false;
		if (this.AquaticTox!=other.AquaticTox) return false;
		if (this.GWP!=other.GWP) return false;
		if (this.ODP!=other.ODP) return false;
		if (this.PCOP!=other.PCOP) return false;
		if (this.AP!=other.AP) return false;
		
		if (this.airIndex!=other.airIndex) return false;
		if (this.environmentalIndex!=other.environmentalIndex) return false;
		if (this.replacementScore!=other.replacementScore) return false;

		if (this.antoineConstantA!=other.antoineConstantA) return false;
		if (this.antoineConstantB!=other.antoineConstantB) return false;
		if (this.antoineConstantC!=other.antoineConstantC) return false;
		if (this.antoineTmax!=other.antoineTmax) return false;
		if (this.antoineTmin!=other.antoineTmin) return false;
		
		// compare strings
		if (this.antoineSource!=null ? other.antoineSource==null : !this.antoineSource.equals(other.antoineSource)) return false;
		if (this.Name!=null ? other.Name==null : !this.Name.equals(other.Name)) return false;
		if (this.CAS!=null ? other.CAS==null : !this.CAS.equals(other.CAS)) return false;
		if (this.formula!=null ? other.formula==null : !this.formula.equals(other.formula)) return false;
		if (this.structure!=null ? other.structure==null : !this.structure.equals(other.structure)) return false;
		if (this.Smiles!=null ? other.Smiles==null : !this.Smiles.equals(other.Smiles)) return false;
		
		if (this.Synonyms==null && other.Synonyms!=null) return false;
		if (this.Synonyms!=null && other.Synonyms==null) return false;
		if (this.Synonyms!=null && other.Synonyms!=null) {
			if (this.Synonyms.size()!=other.Synonyms.size()) return false;
			else {
				for (int i=0; i<this.Synonyms.size(); i++) {
					String syn1 = this.Synonyms.get(i);
					String syn2 = other.Synonyms.get(i);
					if (!syn1.equals(syn2)) return false;
				}
			}
		}
		
		if (this.meltingPointSource==null ? other.meltingPointSource!=null : !this.meltingPointSource.equals(other.meltingPointSource)) return false;
		if (this.boilingPointSource==null ? other.boilingPointSource!=null : !this.boilingPointSource.equals(other.boilingPointSource)) return false;
		if (this.solubilitySource==null ? other.solubilitySource!=null : !this.solubilitySource.equals(other.solubilitySource)) return false;
		if (this.flashPointSource==null ? other.flashPointSource!=null : !this.flashPointSource.equals(other.flashPointSource)) return false;
		if (this.densitySource==null ? other.densitySource!=null : !this.densitySource.equals(other.densitySource)) return false;
		if (this.thermalConductivitySource==null ? other.thermalConductivitySource!=null : !this.thermalConductivitySource.equals(other.thermalConductivitySource)) return false;
		if (this.viscositySource==null ? other.viscositySource!=null : !this.viscositySource.equals(other.viscositySource)) return false;
		if (this.surfaceTensionSource==null ? other.surfaceTensionSource!=null : !this.surfaceTensionSource.equals(other.surfaceTensionSource)) return false;
		if (this.vaporPressureSource==null ? other.vaporPressureSource!=null : !this.vaporPressureSource.equals(other.vaporPressureSource)) return false;
		if (this.heatCapacitySource==null ? other.heatCapacitySource!=null : !this.heatCapacitySource.equals(other.heatCapacitySource)) return false;

		return true;
	}

	
	//*********************************************************************
	//Getter/setters:
	
//	public String getOralRatLD50Source() {
//		return oralRatLD50Source;
//	}
//
//	public void setOralRatLD50Source(String oralRatLD50Source) {
//		this.oralRatLD50Source = oralRatLD50Source;
//	}
//
//	public String getFatheadMinnowLC50Source() {
//		return fatheadMinnowLC50Source;
//	}
//
//	public void setFatheadMinnowLC50Source(String fatheadMinnowLC50Source) {
//		this.fatheadMinnowLC50Source = fatheadMinnowLC50Source;
//	}
	
	public double getHeatCapacitySolid() {
		return heatCapacitySolid;
	}

	public void setHeatCapacitySolid(double heatCapacitySolid) {
		this.heatCapacitySolid = heatCapacitySolid;
	}
	
	public double getEnthalpyOfFusion() {
		return enthalpyOfFusion;
	}

	public void setEnthalpyOfFusion(double enthalpyOfFusion) {
		this.enthalpyOfFusion = enthalpyOfFusion;
	}

	public double getAntoineTmin() {
		return antoineTmin;
	}

	public void setAntoineTmin(double antoineTmin) {
		this.antoineTmin = antoineTmin;
	}

	public double getAntoineTmax() {
		return antoineTmax;
	}

	public void setAntoineTmax(double antoineTmax) {
		this.antoineTmax = antoineTmax;
	}

	public String getAntoineSource() {
		return antoineSource;
	}

	public void setAntoineSource(String antoineSource) {
		this.antoineSource = antoineSource;
	}

	public String getMeltingPointSource() {
		return meltingPointSource;
	}

	public void setMeltingPointSource(String meltingPointSource) {
		this.meltingPointSource = meltingPointSource;
	}

	public String getBoilingPointSource() {
		return boilingPointSource;
	}

	public void setBoilingPointSource(String tbSource) {
		boilingPointSource = tbSource;
	}

	public String getSolubilitySource() {
		return solubilitySource;
	}

	public void setSolubilitySource(String solubilitySource) {
		this.solubilitySource = solubilitySource;
	}

	public String getFlashPointSource() {
		return flashPointSource;
	}

	public void setFlashPointSource(String flashPointSource) {
		this.flashPointSource = flashPointSource;
	}

	public String getDensitySource() {
		return densitySource;
	}

	public void setDensitySource(String densitySource) {
		this.densitySource = densitySource;
	}

	public String getThermalConductivitySource() {
		return thermalConductivitySource;
	}

	public void setThermalConductivitySource(String thermalConductivitySource) {
		this.thermalConductivitySource = thermalConductivitySource;
	}

	public String getViscositySource() {
		return viscositySource;
	}

	public void setViscositySource(String viscositySource) {
		this.viscositySource = viscositySource;
	}

	public String getSurfaceTensionSource() {
		return surfaceTensionSource;
	}

	public void setSurfaceTensionSource(String surfaceTensionSource) {
		this.surfaceTensionSource = surfaceTensionSource;
	}

	public String getVaporPressureSource() {
		return vaporPressureSource;
	}

	public void setVaporPressureSource(String vaporPressureSource) {
		this.vaporPressureSource = vaporPressureSource;
	}
	
	public String getHeatCapacitySource() {
		return heatCapacitySource;
	}

	public void setHeatCapacitySource(String heastCapacitySource) {
		this.heatCapacitySource = heastCapacitySource;
	}

//	public double getOralRatLD50() {
//		return oralRatLD50;
//	}
//
//	public void setOralRatLD50(double oralRatLD50) {
//		this.oralRatLD50 = oralRatLD50;
//	}
//
//	public double getFatheadMinnowLC50() {
//		return fatheadMinnowLC50;
//	}
//
//	public void setFatheadMinnowLC50(double fatheadMinnowLC50) {
//		this.fatheadMinnowLC50 = fatheadMinnowLC50;
//	}
	
	public double getInfDilActCoef_ethanol() {
		return infDilActCoef_ethanol;
	}

	public void setInfDilActCoef_ethanol(double infDilActCoef_ethanol) {
		this.infDilActCoef_ethanol = infDilActCoef_ethanol;
	}

	public double getInfDilActCoef_diethyl_ether() {
		return infDilActCoef_diethyl_ether;
	}

	public void setInfDilActCoef_diethyl_ether(double infDilActCoef_diethyl_ether) {
		this.infDilActCoef_diethyl_ether = infDilActCoef_diethyl_ether;
	}

	public double getInfDilActCoef_acetone() {
		return infDilActCoef_acetone;
	}

	public void setInfDilActCoef_acetone(double infDilActCoef_acetone) {
		this.infDilActCoef_acetone = infDilActCoef_acetone;
	}

	public double getInfDilActCoef_water() {
		return infDilActCoef_water;
	}

	public void setInfDilActCoef_water(double infDilActCoef_water) {
		this.infDilActCoef_water = infDilActCoef_water;
	}

	public double getInfDilActCoef_benzene() {
		return infDilActCoef_benzene;
	}

	public void setInfDilActCoef_benzene(double infDilActCoef_benzene) {
		this.infDilActCoef_benzene = infDilActCoef_benzene;
	}

	public double getInfDilActCoef_cis_2_heptene() {
		return infDilActCoef_cis_2_heptene;
	}

	public void setInfDilActCoef_cis_2_heptene(double infDilActCoef_cis_2_heptene) {
		this.infDilActCoef_cis_2_heptene = infDilActCoef_cis_2_heptene;
	}

	public double getInfDilActCoef_n_propyl_chloride() {
		return infDilActCoef_n_propyl_chloride;
	}

	public void setInfDilActCoef_n_propyl_chloride(double infDilActCoef_n_propyl_chloride) {
		this.infDilActCoef_n_propyl_chloride = infDilActCoef_n_propyl_chloride;
	}

	public double getInfDilActCoef_n_heptadecane() {
		return infDilActCoef_n_heptadecane;
	}

	public void setInfDilActCoef_n_heptadecane(double infDilActCoef_n_heptadecane) {
		this.infDilActCoef_n_heptadecane = infDilActCoef_n_heptadecane;
	}

	public double getInfDilActCoef_n_propylamine() {
		return infDilActCoef_n_propylamine;
	}

	public void setInfDilActCoef_n_propylamine(double infDilActCoef_n_propylamine) {
		this.infDilActCoef_n_propylamine = infDilActCoef_n_propylamine;
	}

	public double getInfDilActCoef_dimethyl_disulfide() {
		return infDilActCoef_dimethyl_disulfide;
	}

	public void setInfDilActCoef_dimethyl_disulfide(double infDilActCoef_dimethyl_disulfide) {
		this.infDilActCoef_dimethyl_disulfide = infDilActCoef_dimethyl_disulfide;
	}

	public double getVaporPressure() {
		return vaporPressure;
	}

	public void setVaporPressure(double vaporPressure) {
		this.vaporPressure = vaporPressure;
	}

	public double getHeatCapacity() {
		return heatCapacity;
	}

	public void setHeatCapacity(double heatCapacity) {
		this.heatCapacity = heatCapacity;
	}

	public double getSurfaceTension() {
		return surfaceTension;
	}

	public void setSurfaceTension(double surfaceTension) {
		this.surfaceTension = surfaceTension;
	}

	public double getViscosity() {
		return viscosity;
	}

	public void setViscosity(double viscosity) {
		this.viscosity = viscosity;
	}

	public double getThermalConductivity() {
		return thermalConductivity;
	}

	public void setThermalConductivity(double thermalConductivity) {
		this.thermalConductivity = thermalConductivity;
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public double getTc() {
		return Tc;
	}

	public void setTc(double tc) {
		Tc = tc;
	}

	public double getPc() {
		return Pc;
	}

	public void setPc(double pc) {
		Pc = pc;
	}

	public double getOmega() {
		return omega;
	}

	public void setOmega(double omega) {
		this.omega = omega;
	}

	
	public double getAntoineConstantA() {
		return antoineConstantA;
	}

	public void setAntoineConstantA(double antoineConstantA) {
		this.antoineConstantA = antoineConstantA;
	}

	public double getAntoineConstantB() {
		return antoineConstantB;
	}

	public void setAntoineConstantB(double antoineConstantB) {
		this.antoineConstantB = antoineConstantB;
	}

	public double getAntoineConstantC() {
		return antoineConstantC;
	}

	public void setAntoineConstantC(double antoineConstantC) {
		this.antoineConstantC = antoineConstantC;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getCAS() {
		return CAS;
	}

	public void setCAS(String cAS) {
		CAS = cAS;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public double getMeltingPoint() {
		return meltingPoint;
	}

	public void setMeltingPoint(double tm) {
		meltingPoint = tm;
	}

	public double getBoilingPoint() {
		return boilingPoint;
	}

	public void setBoilingPoint(double tb) {
		boilingPoint = tb;
	}

	public double getMolecularWeight() {
		return molecularWeight;
	}

	public void setMolecularWeight(double molecularWeight) {
		this.molecularWeight = molecularWeight;
	}

	public double getSolubility() {
		return solubility;
	}

	public void setSolubility(double solubility) {
		this.solubility = solubility;
	}

	public double getFlashPoint() {
		return flashPoint;
	}

	public void setFlashPoint(double flashPoint) {
		this.flashPoint = flashPoint;
	}

	public double getLFlamLimit() {
		return LFlamLimit;
	}

	public void setLFlamLimit(double lFlamLimit) {
		this.LFlamLimit = lFlamLimit;
	}

	public double getUFlamLimit() {
		return UFlamLimit;
	}

	public void setUFlamLimit(double uFlamLimit) {
		this.UFlamLimit = uFlamLimit;
	}

	public double getAutoIgnitTemp() {
		return AutoIgnitTemp;
	}

	public void setAutoIgnitTemp(double autoIgnitTemp) {
		this.AutoIgnitTemp = autoIgnitTemp;
	}

	public String getSmiles() {
		return Smiles;
	}

	public void setSmiles(String smiles) {
		this.Smiles = smiles;
	}

	public Vector<String> getSynonyms() {
		return Synonyms;
	}

	public void setSynonyms(Vector<String> synonyms) {
		this.Synonyms = synonyms;
	}

	public int getChemicalFamilyID() {
		return ChemicalFamilyID;
	}

	public void setChemicalFamilyID(int chemicalFamilyID) {
		this.ChemicalFamilyID = chemicalFamilyID;
	}
	
	public String getSynomymsInString() {
		String synonymInString = "";
		if (this.Synonyms.size()>0) {
			synonymInString = this.Synonyms.get(0);
		}
		for (int i=1; i<this.Synonyms.size(); i++) {
			synonymInString = synonymInString.concat("; "+this.Synonyms.get(i));
		}
		return synonymInString;
	}

	public double getHtoxIngestion() {
		return HtoxIngestion;
	}

	public void setHtoxIngestion(double htoxIngestion) {
		this.HtoxIngestion = htoxIngestion;
	}

	public double getHtoxInhalation() {
		return HtoxInhalation;
	}

	public void setHtoxInhalation(double htoxInhalation) {
		this.HtoxInhalation = htoxInhalation;
	}

//	public double getHtoxDermal() {
//		return HtoxDermal;
//	}
//
//	public void setHtoxDermal(double htoxDermal) {
//		this.HtoxDermal = htoxDermal;
//	}
	
	public double getTerrestrialTox() {
		return TerrestrialTox;
	}

	public void setTerrestrialTox(double terrestrialTox) {
		this.TerrestrialTox = terrestrialTox;
	}

	public double getAquaticTox() {
		return AquaticTox;
	}

	public void setAquaticTox(double aquaticTox) {
		this.AquaticTox = aquaticTox;
	}

	public double getGWP() {
		return GWP;
	}

	public void setGWP(double gWP) {
		this.GWP = gWP;
	}

	public double getODP() {
		return ODP;
	}

	public void setODP(double oDP) {
		this.ODP = oDP;
	}

	public double getPCOP() {
		return PCOP;
	}

	public void setPCOP(double pCOP) {
		this.PCOP = pCOP;
	}

	public double getAP() {
		return AP;
	}
	
	public void setAP(double acidRain) {
		this.AP = acidRain;
	}

	public double getAirIndex() {
		return airIndex;
	}
	
	public void setAirIndex(double airIndex) {
		this.airIndex = airIndex;
	}
	
	public double getEnvironmentalIndex() {
		return environmentalIndex;
	}
	
	public void setEnvironmentalIndex(double environmentalIndex) {
		this.environmentalIndex = environmentalIndex;
	}
	
	public double getReplacementScore() {
		return replacementScore;
	}
	
	public void setReplacementScore(double replacementScore) {
		this.replacementScore = replacementScore;
	}
	
	/**
	 * Calculates the vapor pressure using antoine equation
	 * 
	 * TODO: add empirical correlations based on critical constants if 
	 * antoine constants are unavailable 
	 * 
	 * TODO: add if/then statement so that antoine equation is not used if
	 * the temperature is outside the bounds of Tmin and Tmax
	 * 
	 * @param tempK
	 * @return vapor pressure in SI units (kPa)
	 */
	public double calculateVaporPressure(double tempK) {
//		double VP_kpa=-9999;
//		System.out.println("antoineSource:"+antoineSource);
		
//		if (antoineSource.equals("NIST"))  {
//			double VP_bar=Math.pow(10,(antoineConstantA-antoineConstantB/(tempK+antoineConstantC)));
//			//Convert to proper units:
//			VP_kpa=100*VP_bar;
//			
//		} else if (antoineSource.equals("Yaws") || antoineSource.equals("DECHEMA")) {
//			double VP_mmHg=Math.pow(10,(antoineConstantA-antoineConstantB/(tempK-273.15+antoineConstantC)));
//			VP_kpa=Units.pressureConvertFrom(VP_mmHg, Units.COMMON);
//		}
		
		//Database has been updated so that all antoine equations will yield vapor pressure in kpa and T is in K:
		
		if (haveAntoineConstants()) {
			return calculateAntoineVaporPressure(tempK);
		} else if (haveCriticalParameters()) {
			return calculateLeeKeslerVaporPressure(tempK);
		} else {
			return -9999;
		}
		
	}
	
	public double calculateLeeKeslerVaporPressure(double tempK) {
		
		double Tr=tempK/Tc;
		
		double f0=5.92714-6.09648/Tr-1.28862*Math.log(Tr)+0.169347*Math.pow(Tr,6);
		double f1=15.2518-15.6875/Tr-13.4721*Math.log(Tr)+0.43577*Math.pow(Tr, 6);
		
		double VP=(Math.exp(f0+(omega*f1)))*Pc;
		VP*=100.0;// convert to kPa
		
		return VP;
		
	}
	public double calculateAntoineVaporPressure(double tempK) {
		double VP_kpa=Math.pow(10,(antoineConstantA-antoineConstantB/(tempK+antoineConstantC)));
		return VP_kpa;
	}
	
	public boolean isLiquidPhase(double temperature) {
		if (getMeltingPoint() < temperature && temperature < getBoilingPoint()) {
			return true;
		} else {
			return false;
		}
	}

	public void eliminateSynonymRepeats() {
		
		// remove repeats from top down
		for (int i=Synonyms.size()-1; i>=0; i--) {
			if (Name.equalsIgnoreCase(Synonyms.get(i))) Synonyms.remove(i);
		}
		
		for (int i=0; i<Synonyms.size()-1; i++) {
			String synonym = Synonyms.get(i);
			// remove repeats from top down
			for (int j=Synonyms.size()-1; j>i; j--) {
				if (synonym.equalsIgnoreCase(Synonyms.get(j))) Synonyms.remove(j);
			}
		}
		
	}
	
	public boolean isAntoineReady(double tempK) {
		if (!haveAntoineConstants() || tempK < antoineTmin || tempK > antoineTmax) {
			return false;
		} else {
			return true;
		}
	}

	public boolean haveCriticalParameters() {
		if (Tc==0 || Pc==0 || omega==0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean haveAntoineConstants() {
		if (antoineConstantA==0 || antoineConstantB==0|| antoineConstantC==0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean canCalculateVaporPressure() {
		if (haveAntoineConstants() || haveCriticalParameters()) return true;
		else return false;
	}
	
	public void calculateEnvironmentalIndexes(int[] impacts, double systemPressure) {
		double psi;
		
		psi = impacts[0]*this.getHtoxIngestion();
		psi += impacts[1]*this.getHtoxInhalation();
//		psi += impacts[2]*this.getHtoxDermal();
		psi += impacts[2]*this.getTerrestrialTox();
		psi += impacts[3]*this.getAquaticTox();
		psi += impacts[4]*this.getGWP();
		psi += impacts[5]*this.getODP();
		psi += impacts[6]*this.getPCOP();
		psi += impacts[7]*this.getAP();
		
		environmentalIndex = psi;
		airIndex = psi*(this.getVaporPressure()/systemPressure);
	}
	
	public double calculateReplacementScore(State activeState) {
		
		final DecimalFormat df = new DecimalFormat("0.0##");
		final DecimalFormat ef = new DecimalFormat("0.00E0");

		double[] tolerance = activeState.getPTolerances();
		double[] desired = activeState.getPDesiredVals();
		
		double score = numberOfDeviationsWithOffset(desired[0], this.molecularWeight, tolerance[0], df);
		score += numberOfDeviationsWithOffset(desired[1], this.density, tolerance[1], ef);
		score += numberOfDeviationsWithOffset(desired[2], this.boilingPoint, tolerance[2], df);
		score += numberOfDeviationsWithOffset(desired[3], this.vaporPressure, tolerance[3], ef);
		score += numberOfDeviationsWithOffset(desired[4], this.surfaceTension, tolerance[4], ef);
		score += numberOfDeviationsWithOffset(desired[5], this.viscosity, tolerance[5], ef);
		score += numberOfDeviationsWithOffset(desired[6], this.thermalConductivity, tolerance[6], ef);
		score += flashPointScore(desired[7], this.flashPoint);
		
		tolerance = activeState.getATolerances();
		desired = activeState.getADesiredVals();
		
		score += numberOfDeviationsWithOffset(desired[0], this.infDilActCoef_ethanol, tolerance[0], ef);
		score += numberOfDeviationsWithOffset(desired[1], this.infDilActCoef_diethyl_ether, tolerance[1], ef);
		score += numberOfDeviationsWithOffset(desired[2], this.infDilActCoef_acetone, tolerance[2], ef);
		score += numberOfDeviationsWithOffset(desired[3], this.infDilActCoef_water, tolerance[3], ef);
		score += numberOfDeviationsWithOffset(desired[4], this.infDilActCoef_benzene, tolerance[4], ef);
		score += numberOfDeviationsWithOffset(desired[5], this.infDilActCoef_cis_2_heptene, tolerance[5], ef);
		score += numberOfDeviationsWithOffset(desired[6], this.infDilActCoef_n_propyl_chloride, tolerance[6], ef);
		score += numberOfDeviationsWithOffset(desired[7], this.infDilActCoef_n_heptadecane, tolerance[7], ef);
		score += numberOfDeviationsWithOffset(desired[8], this.infDilActCoef_n_propylamine, tolerance[8], ef);
		score += numberOfDeviationsWithOffset(desired[9], this.infDilActCoef_dimethyl_disulfide, tolerance[9], ef);

		this.replacementScore = score;
		
		return score;
	}
	
	private double numberOfDeviationsWithOffset(double desired, double actual, double tol, DecimalFormat df) {
		
		actual = Double.parseDouble(df.format(actual));
		double upperBound = Double.parseDouble(df.format(desired*(1.0+0.01*tol)));
		double lowerBound = Double.parseDouble(df.format(desired*(1.0-0.01*tol)));	
		
		if (lowerBound < 0.0) lowerBound = 0.0;

		double offset = 0.0;
		if (actual < lowerBound || upperBound < actual) offset += 1.0e9;
				
		if (actual < desired) {
			return offset + (desired-actual)/(desired-lowerBound);
		} else { // desired <= actual
			return offset + (actual-desired)/(upperBound-desired);
		}
	}
	
	private double flashPointScore(double lowerBound, double actual) {
		if (lowerBound < 0.0) lowerBound = 0.0;

		double offset = 0.0;
		if (actual < lowerBound) offset += 1.0e9;
				
		return offset;
	}

	public boolean isContained(Chemicals chemicals) {
		for (Chemical chemical: chemicals) {
			if (chemical.getCAS().contentEquals(CAS)) return true;
		}
		return false;
	}

}
