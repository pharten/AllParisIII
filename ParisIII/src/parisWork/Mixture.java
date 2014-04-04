package parisWork;

import java.io.Serializable;
import java.util.Vector;

import parisInit.State;
import parisInit.Units;

import unifac.UNIFACforPARIS;

public class Mixture extends Chemical implements Serializable, Cloneable {

	boolean debug=true;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7473228088140944561L;
	private Chemical[] chemicals = null;
	private Vector<Double> wghtFractions = new Vector<Double>();//weight fractions in liquid phase
	private static UNIFACforPARIS unifac = null;//Have to pass instance of this class so dont reload unifac data every time you create a new instance
	private double tempK = 25.0 + 273.15;
	private String massRatios = null;
	private double mixtureScore = 0.0;
	
	public Mixture() {
		super();
	}
	
	public Mixture(double tempK) {
		super();
		this.tempK = tempK;
	}
	
	// constructor used in Screen4:bestMixture calculations
	public Mixture(Chemical[] solvents, String massRatios, double tempK) throws Exception {
		super();
		
		this.tempK = tempK;
		
		chemicals = solvents;
		this.massRatios = massRatios;
		
		String[] mass = this.massRatios.split(":");
		if (mass.length==2) {
			double mass1 = Double.parseDouble(mass[0]);
			double mass2 = Double.parseDouble(mass[1]);
			double total = mass1+mass2;
			wghtFractions.add(mass1/total);
			wghtFractions.add(mass2/total);
		} else if (mass.length==3) {
			double mass1 = Double.parseDouble(mass[0]);
			double mass2 = Double.parseDouble(mass[1]);
			double mass3 = Double.parseDouble(mass[2]);
			double total = mass1+mass2+mass3;
			wghtFractions.add(mass1/total);
			wghtFractions.add(mass2/total);
			wghtFractions.add(mass3/total);
		} else {
			throw new Exception("Wrong number of solvents in the mixture");
		}
		
		calculateEnvironmentalIndexesOfMixture();
	}
	
	// constructor used in Screen4:bestMixture calculations
	public Mixture(Chemical solvent1, Chemical solvent2, String massRatios, double tempK) {
		super();
		
		this.tempK = tempK;
		
		chemicals = new Chemical[2];
		chemicals[0] = solvent1;
		chemicals[1] = solvent2;
		
		String[] mass = massRatios.split(":", 2);
		double mass1 = Double.parseDouble(mass[0]);
		double mass2 = Double.parseDouble(mass[1]);
		double total = mass1+mass2;
		
		wghtFractions.add(mass1/total);
		wghtFractions.add(mass2/total);
		
		this.massRatios = massRatios;
		
		calculateEnvironmentalIndexesOfMixture();
	}
	
	// constructor used in Screen4:bestMixture calculations
	public Mixture(Chemical solvent1, Chemical solvent2, Chemical solvent3, String massRatios, double tempK) {
		super();
		
		this.tempK = tempK;
		
		chemicals = new Chemical[3];
		chemicals[0] = solvent1;
		chemicals[1] = solvent2;
		chemicals[2] = solvent3;
		
		String[] mass = massRatios.split(":", 3);
		double mass1 = Double.parseDouble(mass[0]);
		double mass2 = Double.parseDouble(mass[1]);
		double mass3 = Double.parseDouble(mass[2]);
		double total = mass1+mass2+mass3;
		
		wghtFractions.add(mass1/total);
		wghtFractions.add(mass2/total);
		wghtFractions.add(mass3/total);
		
		this.massRatios = massRatios;
		
		calculateEnvironmentalIndexesOfMixture();
	}

	public Mixture clone() { // deep copy
		
		Mixture clone = new Mixture(this.tempK);
		
		if (this.chemicals!=null) {
			clone.chemicals = new Chemical[this.chemicals.length];
			for (int i=0; i<this.chemicals.length; i++) {
				clone.chemicals[i] = this.chemicals[i].clone();
				clone.wghtFractions.add(this.wghtFractions.get(i).doubleValue());
			}
		}
		clone.setUnifac(getUnifac());
		clone.setAntoineConstantA(getAntoineConstantA());
		clone.setAntoineConstantB(getAntoineConstantB());
		clone.setAntoineConstantC(getAntoineConstantC());
		clone.setAP(getAP());
		clone.setAutoIgnitTemp(getAutoIgnitTemp());
		clone.setDensity(getDensity());
		clone.setFlashPoint(getFlashPoint());
		clone.setGWP(getGWP());
		clone.setHeatCapacity(getHeatCapacity());
		clone.setHtoxDermal(getHtoxDermal());
		clone.setHtoxIngestion(getHtoxIngestion());
		clone.setHtoxInhalation(getHtoxInhalation());
		clone.setInfDilActCoef_acetone(getInfDilActCoef_acetone());
		clone.setInfDilActCoef_benzene(getInfDilActCoef_benzene());
		clone.setInfDilActCoef_cis_2_heptene(getInfDilActCoef_cis_2_heptene());
		clone.setInfDilActCoef_diethyl_ether(getInfDilActCoef_diethyl_ether());
		clone.setInfDilActCoef_dimethyl_disulfide(getInfDilActCoef_dimethyl_disulfide());
		clone.setInfDilActCoef_ethanol(getInfDilActCoef_ethanol());
		clone.setInfDilActCoef_n_heptadecane(getInfDilActCoef_n_heptadecane());
		clone.setInfDilActCoef_n_propyl_chloride(getInfDilActCoef_n_propyl_chloride());
		clone.setInfDilActCoef_n_propylamine(getInfDilActCoef_n_propylamine());
		clone.setInfDilActCoef_water(getInfDilActCoef_water());
		clone.setLFlamLimit(getLFlamLimit());
		clone.setMolecularWeight(getMolecularWeight());
		clone.setODP(getODP());
		clone.setPCOP(getPCOP());
		clone.setSolubility(getSolubility());
		clone.setBoilingPoint(getBoilingPoint());
		clone.setThermalConductivity(getThermalConductivity());
		clone.setMeltingPoint(getMeltingPoint());
		clone.setViscosity(getViscosity());
		clone.setVaporPressure(getVaporPressure());
		clone.setMixtureScore(getMixtureScore());
		clone.setMassRatios(getMassRatios());
		clone.setAirIndex(getAirIndex());
		clone.setEnvironmentalIndex(getEnvironmentalIndex());
		
		return clone;
	}
	
	public double calculateMixtureScore(State activeState) throws Exception {

		this.mixtureScore = Double.MAX_VALUE;
		
		double[] tolerance = activeState.getPTolerances();
		double[] desired = activeState.getPDesiredVals();

//		Properties.calculate(this, UNIFACforPARIS.getInstance(), activeState.tempConvertToSI());
		this.calculateMixtureProperties(activeState.tempConvertToSI());
		if (getBoilingPoint()==-9999) return this.mixtureScore;
		
		double score = numberOfDeviationsWithOffset(desired[0], this.getMolecularWeight(), tolerance[0]);
		score += numberOfDeviationsWithOffset(desired[1], this.getDensity(), tolerance[1]);
		score += numberOfDeviationsWithOffset(desired[2], this.getBoilingPoint(), tolerance[2]);
		score += numberOfDeviationsWithOffset(desired[3], this.getVaporPressure(), tolerance[3]);
		score += numberOfDeviationsWithOffset(desired[4], this.getSurfaceTension(), tolerance[4]);
		score += numberOfDeviationsWithOffset(desired[5], this.getViscosity(), tolerance[5]);
		score += numberOfDeviationsWithOffset(desired[6], this.getThermalConductivity(), tolerance[6]);
		score += flashPointScore(desired[7], this.getFlashPoint());
		
		tolerance = activeState.getATolerances();
		desired = activeState.getADesiredVals();
		
		score += numberOfDeviationsWithOffset(desired[0], this.getInfDilActCoef_ethanol(), tolerance[0]);
		score += numberOfDeviationsWithOffset(desired[1], this.getInfDilActCoef_diethyl_ether(), tolerance[1]);
		score += numberOfDeviationsWithOffset(desired[2], this.getInfDilActCoef_acetone(), tolerance[2]);
		score += numberOfDeviationsWithOffset(desired[3], this.getInfDilActCoef_water(), tolerance[3]);
		score += numberOfDeviationsWithOffset(desired[4], this.getInfDilActCoef_benzene(), tolerance[4]);
		score += numberOfDeviationsWithOffset(desired[5], this.getInfDilActCoef_cis_2_heptene(), tolerance[5]);
		score += numberOfDeviationsWithOffset(desired[6], this.getInfDilActCoef_n_propyl_chloride(), tolerance[6]);
		score += numberOfDeviationsWithOffset(desired[7], this.getInfDilActCoef_n_heptadecane(), tolerance[7]);
		score += numberOfDeviationsWithOffset(desired[8], this.getInfDilActCoef_n_propylamine(), tolerance[8]);
		score += numberOfDeviationsWithOffset(desired[9], this.getInfDilActCoef_dimethyl_disulfide(), tolerance[9]);

		this.mixtureScore = score;
		
		return score;
	}
	
	private Chemical[] convertVA(Vector<Chemical> chemicalsV) {
		Chemical[] chemicalsA = new Chemical[chemicalsV.size()];
		for (int i=0; i<chemicalsV.size(); i++) {
			chemicalsA[i] = chemicalsV.get(i);
		}
		return chemicalsA;
	}
	
	private Vector<Chemical> convertAV(Chemical[] chemicalsA) {
		Vector<Chemical> chemicalsV = new Vector<Chemical>();
		if (chemicalsA!=null) {
			for (int i=0; i<chemicalsA.length; i++) {
				chemicalsV.add(chemicalsA[i]);
			}
		}
		return chemicalsV;
	}
	
	private double numberOfDeviationsWithOffset(double desired, double actual, double tol) {
		double upperBound = desired*(1.0+0.01*tol);
		double lowerBound = desired*(1.0-0.01*tol);
		if (lowerBound < 0.0) lowerBound = 0.0;

		double offset = 0.0;
		if (actual < lowerBound || upperBound < actual) offset += 1.0e9;
				
		if (actual < desired) {
			offset += (desired-actual)/(desired-lowerBound);
		} else { // desired <= actual
			offset += (actual-desired)/(upperBound-desired);
		}
		
		return offset;
	}
	
	private double flashPointScore(double lowerBound, double actual) {
		if (lowerBound < 0.0) lowerBound = 0.0;

		double offset = 0.0;
		if (actual < lowerBound) offset += 1.0e9;
				
		return offset;
	}
	
	public void calculateMixtureProperties(double tempK) throws Exception {
//		this.tempK = tempK;
//		this.unifac = UNIFACforPARIS.getInstance();
		
		calculatePhysicalPropertiesOfMixture(tempK);
		calculateActivityCoefficientsOfMixture(tempK);
		
//		if (getBoilingPoint()==-9999) throw new Exception("error: BP");
		if (getFlashPoint()==-9999) throw new Exception("error: FP");
		
	}

	/**
	 * Calculates all the physical properties of the mixture
	 * @param tempK
	 * @param unifac
	 */
	public void calculatePhysicalPropertiesOfMixture(double tempK) {
//		this.tempK = tempK;
		this.unifac = UNIFACforPARIS.getInstance();
		
		if (chemicals.length==1) {
			
			setMolecularWeight(chemicals[0].getMolecularWeight());
			setThermalConductivity(chemicals[0].getThermalConductivity());
			setDensity(chemicals[0].getDensity());
			setViscosity(chemicals[0].getViscosity());
			setBoilingPoint(chemicals[0].getBoilingPoint());
			setFlashPoint(chemicals[0].getFlashPoint());
			setVaporPressure(chemicals[0].getVaporPressure());
			setSurfaceTension(chemicals[0].getSurfaceTension());
			setMeltingPoint(chemicals[0].getMeltingPoint());
			
		} else {
			//If a multicomponent mixture has only 1 nonzero mass fraction we should just report the properties of that component
			boolean haveOnlyOneNonZeroComponent=false;
			int numNonZero=0;
			int nonZeroIndex=-1;

			for (int i=0;i<wghtFractions.size();i++) {
				double xmassi=wghtFractions.get(i);
				if (xmassi>0) {
					numNonZero++;
					nonZeroIndex=i;
				}
			}
			if (numNonZero==1) {
				setMolecularWeight(chemicals[nonZeroIndex].getMolecularWeight());
				setThermalConductivity(chemicals[nonZeroIndex].getThermalConductivity());
				setDensity(chemicals[nonZeroIndex].getDensity());
				setViscosity(chemicals[nonZeroIndex].getViscosity());
				setBoilingPoint(chemicals[nonZeroIndex].getBoilingPoint());
				setFlashPoint(chemicals[nonZeroIndex].getFlashPoint());
				setVaporPressure(chemicals[nonZeroIndex].getVaporPressure());
				setSurfaceTension(chemicals[nonZeroIndex].getSurfaceTension());
				setMeltingPoint(chemicals[nonZeroIndex].getMeltingPoint());

			} else {//Have more than 1 nonzero component
				//Mole fractions in liquid phase:
				//TODO should molFractions be a class variable like wghtFractions to make it consistent?
				Vector<Double> molFractions = calculateMoleFractions();

				//Volume fractions in liquid phase:
				//TODO should volumeFractions be a class variable like wghtFractions to make it consistent?
				Vector<Double> volumeFractions = calculateVolumeFractions(molFractions);

				setMolecularWeight(calculateMixtureMW(molFractions));

				setThermalConductivity(calculateMixtureTC(volumeFractions));
				setDensity(calculateMixtureDensity(wghtFractions));

				setViscosity(calculateMixtureViscosity(wghtFractions, getDensity()));

				//Use volume fractions since much faster and probably about as 
				// accurate (and also can be applied to any number of components):
				setSurfaceTension(calculateMixtureST(volumeFractions));

				//More complicated unifac based method
				//			setSurfaceTension(calculateMixtureSTwithUNIFACAi(molFractions, chemicals, tempK));

				//			setMeltingPoint(calculateMixtureMP_Simple(molFractions));
				//			setMeltingPoint(calculateMixtureMP_Complex(molFractions));

				//****************************************************************
				//following is for convenience until I can add antoine constants:
				boolean haveAntoineConstants=true;

				for (int i=0;i<chemicals.length;i++) {

					if (chemicals[i].getAntoineConstantA()==0 || chemicals[i].getAntoineConstantB()==0|| chemicals[i].getAntoineConstantC()==0) {
						haveAntoineConstants=false;
					}
				}

				if (!haveAntoineConstants) {
					//				System.out.println("Missing antoine constants!");
					return;
				}
				//****************************************************************

				long tBP1=System.currentTimeMillis();
				setBoilingPoint(calculateNormalMixtureBP(molFractions));
				long tBP2=System.currentTimeMillis();

				//			if (debug) {

				long tFP1=System.currentTimeMillis();
				setFlashPoint(calculateMixtureFP_Liaw3(molFractions));

				if (getFlashPoint()==-9999) {
					//Try using Catoire method if Liaw fails: (sometimes there is no solution)
					setFlashPoint(calculateMixtureFP_Catoire(molFractions, getBoilingPoint()));
//					System.out.println("*"+getFlashPoint());
				}
				long tFP2=System.currentTimeMillis();

			//			if (debug) {
			//				System.out.println("time to calculate FP = "+(tFP2-tFP1)/1000.0+" seconds");
			//			}

				//*******************************************************************

				long tVP1=System.currentTimeMillis();
				setVaporPressure(calculateMixtureP(molFractions, tempK));
				long tVP2=System.currentTimeMillis();

				//			if (debug) {
				//				System.out.println("time to calculate VP = "+(tVP2-tVP1)/1000.0+" seconds");
				//			}
			
			} //end numNonZero<>1 
			
			
		}//end chemicals.size <> 1
		
		
		
	}
	
	public void calculateActivityCoefficientsOfMixture(double tempK) {
//		this.tempK = tempK;
		this.unifac = UNIFACforPARIS.getInstance();
		
		Vector<Double> molFractions = calculateMoleFractions();
		
		Vector<String>solventCASNumbers=new Vector<String>();
		for (int i=0;i<chemicals.length;i++) {
			solventCASNumbers.add(chemicals[i].getCAS());
		}
		
		setInfDilActCoef_ethanol(unifac.calculateInfDilActCoef("64-17-5", solventCASNumbers, molFractions, tempK));
		setInfDilActCoef_diethyl_ether(unifac.calculateInfDilActCoef("60-29-7", solventCASNumbers, molFractions, tempK));
		setInfDilActCoef_acetone(unifac.calculateInfDilActCoef("67-64-1", solventCASNumbers, molFractions, tempK));
		setInfDilActCoef_water(unifac.calculateInfDilActCoef("7732-18-5", solventCASNumbers, molFractions, tempK));
		setInfDilActCoef_benzene(unifac.calculateInfDilActCoef("71-43-2", solventCASNumbers, molFractions, tempK));
		setInfDilActCoef_cis_2_heptene(unifac.calculateInfDilActCoef("6443-92-1", solventCASNumbers, molFractions, tempK));
		setInfDilActCoef_n_propyl_chloride(unifac.calculateInfDilActCoef("540-54-5", solventCASNumbers, molFractions, tempK));
		setInfDilActCoef_n_heptadecane(unifac.calculateInfDilActCoef("629-78-7", solventCASNumbers, molFractions, tempK));
		setInfDilActCoef_n_propylamine(unifac.calculateInfDilActCoef("107-10-8", solventCASNumbers, molFractions, tempK));
		setInfDilActCoef_dimethyl_disulfide(unifac.calculateInfDilActCoef("624-92-0", solventCASNumbers, molFractions, tempK));
		
	}
	
	public void calculateEnvironmentalIndexesOfMixture() {
		
		double[] gammas = {1};
		if (chemicals.length>1) {
			gammas = this.getUnifac().calculateActivityCoefficients2(this.calculateMoleFractions(), chemicals, tempK);
		}
		
		double airIndex = 0.0;
		double environmentalIndex = 0.0;
		for (int i=0;i<chemicals.length;i++) {
			airIndex += gammas[i] * chemicals[i].getAirIndex() * this.getWghtFractions().get(i);
			environmentalIndex += chemicals[i].getEnvironmentalIndex() * this.getWghtFractions().get(i);
		}
		this.setAirIndex(airIndex);
		this.setEnvironmentalIndex(environmentalIndex);
		
	}
	
	/**
	 * Converts innate mass fractions to mol fractions
	 * @return
	 */
	public Vector<Double> calculateMoleFractions() {
		
		Vector<Double> moleFractions = new Vector<Double>();
		
		//assume 1 gm of mixture
		double moles;
		double totalMoles=0;
		for (int i=0;i<wghtFractions.size();i++) {
			moles = wghtFractions.get(i)/chemicals[i].getMolecularWeight();
			moleFractions.add(moles);
			totalMoles+=moles;
		}
		
		for (int i=0;i<moleFractions.size();i++) {
			moleFractions.set(i,moleFractions.get(i)/totalMoles);				
		}
		
		return moleFractions;
		
	}
	
	
	/**
	 * Converts mole fractions to volume fractions
	 * @param xmol mole fraction of each component in liquid phase
	 * @return volume fraction of each component in the liquid phase
	 */
	public Vector<Double> calculateVolumeFractions(Vector<Double> xmol) {

		Vector<Double> volumeFractions=new Vector<Double>();

		double [] xiVi=new double [xmol.size()];
		double sum=0;
		for (int i=0;i<xmol.size();i++) {
			double Vi=chemicals[i].getMolecularWeight()/chemicals[i].getDensity(); //density is liquid phase density
			xiVi[i]=xmol.get(i)*Vi;
			sum+=xiVi[i];
		}
		
		for (int i=0;i<xmol.size();i++) {
			volumeFractions.add(xiVi[i]/sum);
		}
		
		return volumeFractions;
		
	}
	
	/**
	 * Calculates molecular weight of a mixture
	 * @param molFractions
	 * @return
	 */
	private double calculateMixtureMW(Vector<Double> molFractions) {
		
		double MWmix=0;
		
		for (int i=0;i<molFractions.size();i++) {
			MWmix+=molFractions.get(i)*chemicals[i].getMolecularWeight();
		}
		
		return MWmix;
	}
	
	
	/**
	 * This method calculates the boiling point of a liquid mixture at any given 
	 * pressure (i.e. the bubble point). This method is public for testing
	 * 
	 * @param xmol
	 * @param Pressure_kPa
	 * @return
	 */
	private double calculateMixtureBP(Vector<Double> xmol, double Pressure_kPa) {

		int maxIterations=1000;

		//For initial guess use mol frac weighted average of pure component values:
		double BPmixKinit=0;
		for (int i=0;i<xmol.size();i++) {
			BPmixKinit+=xmol.get(i)*chemicals[i].getBoilingPoint();
		}

		double frac=0.5;
		double small=1e-3;

		while (true) {

			double BPmixK=BPmixKinit; //reset to initial guess

			for (int i = 0; i < maxIterations; i++) {
				double fx = Pressure_kPa - calculateMixtureP(xmol, BPmixK);
				double fx2 = Pressure_kPa - calculateMixtureP(xmol, BPmixK + small);
				double fprime = (fx2 - fx) / (small); // estimate derivative using numerical method

				double dx = fx / fprime;

//				BPmixK-=dx;
				BPmixK = frac*(BPmixK-dx) + (1-frac)*BPmixK; //only take partial step towards new value

//				System.out.println(BPmixK);
				if (BPmixK<0 || BPmixK>1500) break;

				if (Math.abs(dx) < 0.0001) {
					// System.out.println("Converged after "+i+" iterations:"+BPmixK);
					return BPmixK;
				}

			}

			frac /= 2;//make step smaller
//          System.out.println(frac);
			if (frac<1e-8) break;

		}
//		System.out.println(BPmix);
		return -9999;
	}
	
	
	/**
	 * calculates normal boiling point of the mixture
	 * 
	 * @param xmol - mole fractions of each component in the liquid phase
	 * @return
	 */
	private double calculateNormalMixtureBP(Vector<Double> xmol) {
		double Patm=101.325;//normal bp is that at atmospheric pressure
		return calculateMixtureBP(xmol,Patm);
	}	
	/**
	 * Calculates the composition of the vapor phase at the normal boiling point of the mixture
	 * @param molFractions
	 * @param unifac - instance of class for unifac calcs
	 * @param Pressure pressure in mmHg
	 * @return
	 */
	private double [] calculateVaporPhaseCompositionAtNormalBP(Vector<Double> molFractions, double BPmixK) {

		double Pressure=101.325;//normal atmospheric pressure in kPa

		
		double [] gammas=unifac.calculateActivityCoefficients2(molFractions,chemicals, BPmixK);
		
		double [] y=new double [molFractions.size()];
		
		//y1=x1*g1*P1sat/P
		for (int i=0;i<molFractions.size();i++) {
			double Psati=chemicals[i].calculateVaporPressure(BPmixK);
			y[i]=molFractions.get(i)*gammas[i]*Psati/Pressure;
		}
		return y;
	}

	/**
	 * Calculate surface tension using volume fractions
	 * @param volFractions
	 * @return
	 */
	public double calculateMixtureST(Vector<Double> volFractions) {
		
		double STmixVol=0;
		
		for (int i=0;i<chemicals.length;i++) {
			STmixVol+=volFractions.get(i)*chemicals[i].getSurfaceTension();
		}
		return STmixVol;
		
	}
	/**
	 * I added this convenience method so that I could just calculate the 
	 * mixure surface tension without having to calculate all the mixture
	 * properties
	 * 
	 * @param xmol
	 * @param tempK
	 * @param unifac
	 * @author TMARTI02
	 * @return
	 */
	public double calculateMixtureSTwithUNIFACAi(Vector<Double> xmol, double tempK,UNIFACforPARIS unifac) {
		this.unifac=unifac;
		return  calculateMixtureSTwithUNIFACAi(xmol,tempK);
	}
	
	/**
	 * Calculates surface tension of the mixture in SI units
	 * 
	 * TODO- we need to add a method that can use this method for more 
	 * than 2 components
	 * 
	 * @param xmol
	 * @param tempK
	 * @return
	 */
	public double calculateMixtureSTwithUNIFACAi(Vector<Double> xmol, double tempK) {
		double R=8.314e7;
		
		//Need surface tension in common units for it to work:
		double ST1=Units.surfaceTensionConvertTo(chemicals[0].getSurfaceTension(),Units.COMMON);
		double Q1=this.unifac.getQ(chemicals[0].getCAS());
		double A1=2.5e9*Q1;
	
		double ST2=Units.surfaceTensionConvertTo(chemicals[1].getSurfaceTension(),Units.COMMON);
		double Q2=this.unifac.getQ(chemicals[1].getCAS());
		double A2=2.5e9*Q2;


		if (Math.abs(xmol.get(0))<0.0001) {
			return Units.surfaceTensionConvertFrom(ST2, Units.COMMON);
		}
		if (Math.abs(xmol.get(0))>0.9999) {
			return Units.surfaceTensionConvertFrom(ST1, Units.COMMON);
		}

		double x1=xmol.get(0);

		int counter=0;
		double frac=0.1;
		
		double xnplus1=0.5;
		
		//try to solve for x1s, if can't, reduce the fraction:
		while (true) {
			
			counter++;
			if (counter==1000) break;
			xnplus1 = solveForX1s(tempK, R, frac, ST1, A1, ST2, A2, x1);
			
			if (xnplus1>-9999) break;
			else {
				frac/=2.0;//make frac smaller to get convergence
			}
		}
		
		if (counter==1000) return -9999;
		
//		System.out.println(x1+"\t"+xnplus1);
		double STmix=this.calcSurfaceTension(x1, xnplus1, ST1, ST2, A1, A2, tempK,R);
//		System.out.println("STmix="+STmix);
		
		double STmixSI=Units.surfaceTensionConvertFrom(STmix, Units.COMMON);
		
		return STmixSI;
	}

	/**
	 * Calculates vapor pressure of a mixture at a given temperature
	 * Note: For now this is for binary mixture
	 * @param xmol - mole fractions of each component in the liquid phase
	 * @param chemicals - vector of paris chemicals
	 * @param T - temperature in degrees Celsius
	 * @return
	 */
//	public double calculateMixtureSTwithPaquetteAi(Vector<Double> xmol,Chemicals chemicals,double TempK) {
//		double R=8.314e7;
//
//		Chemical chemical1=chemicals.get(0);
//		double ST1=chemical1.getSurfaceTension();
//		double Vb1=chemical1.getMw()/chemical1.getDensity();
//		double Vc1=chemical1.getVc();
//		double A1=1.021e8*Math.pow(Vc1,6.0/15.0)*Math.pow(Vb1,4.0/15.0);
//		
//		Chemical chemical2=chemicals.get(1);
//		double ST2=chemical2.getSurfaceTension();
//		double Vb2=chemical2.getMw()/chemical2.getDensity();
//		double Vc2=chemical2.getVc();
//		double A2=1.021e8*Math.pow(Vc2,6.0/15.0)*Math.pow(Vb2,4.0/15.0);
//
////		System.out.println(A1+"\t"+A2);
//		
//		if (Math.abs(xmol.get(0))<0.0001) return ST2;
//		if (Math.abs(xmol.get(0))>0.9999) return ST1;
//
//		Vector<String>comps=new Vector<String>();
//		for (int i=0;i<chemicals.size();i++) {
//			comps.add(chemicals.get(i).getCAS());
//		}
//
//		double x1=xmol.get(0);
//
//		int counter=0;
//		double frac=0.1;
//		
//		double xnplus1=0.5;
//		
//		//try to solve for x1s, if can't, reduce the fraction:
//		while (true) {
//			
//			counter++;
//			if (counter==1000) break;
//			xnplus1 = solveForX1s(TempK, R, frac, ST1, A1, ST2, A2, comps, x1);
//			
//			if (xnplus1>-9999) break;
//			else {
//				frac/=2.0;//make frac smaller to get convergence
//			}
//		}
//		
//		if (counter==1000) return -9999;
//		
////		System.out.println(x1+"\t"+xnplus1);
//		double STmix=this.calcSurfaceTension(x1, xnplus1, comps, ST1, ST2, A1, A2, TempK,R);
////		System.out.println("STmix="+STmix);
//		
//		return STmix;
//	}

	/**
	 * @param T
	 * @param R
	 * @param frac
	 * @param ST1
	 * @param A1
	 * @param ST2
	 * @param A2
	 * @param comps
	 * @param x1
	 * @return
	 */
	private double solveForX1s(double tempK, double R, double frac, double ST1,
			double A1, double ST2, double A2, double x1) {
		//for initial guess for x1s:
		double xn=0.5;

		//for second guess, perturb it slightly:
		double xnplus1=xn+0.0001;
		
		double fx=this.calculatefx(x1,xn,ST1,ST2,A1,A2,tempK,R);
		
		double fxnew=this.calculatefx(x1,xnplus1,ST1,ST2,A1,A2,tempK,R);

		double fprime=(fxnew-fx)/(xnplus1-xn);

		int counter=0;
		
		while (true) {
			counter++;
			xn=xnplus1;
			fx=fxnew;
			
			if (counter==1000) return -9999;
			
			//calculate new x1s: (modified newton raphson which only takes a partial step using frac
			//http://en.wikipedia.org/wiki/Newton_Raphson
			double newvalue=xnplus1-fx/fprime;//newton raphson estimate
			
			xnplus1=frac*(newvalue)+(1-frac)*xnplus1;

//			System.out.println("fxnew="+fxnew);
//			System.out.println(counter+"\tx1snew="+xnplus1);

			fxnew=this.calculatefx(x1,xnplus1,ST1,ST2,A1,A2,tempK,R);
			
			fprime=(fxnew-fx)/(xnplus1-xn);

			if (Math.abs(fxnew)<0.0001) {
				break;
			} 
		
		}
		return xnplus1;
	}
	
	/***
	 * 
	 * @param x1
	 * @param x1s
	 * @param comps
	 * @param ST1
	 * @param ST2
	 * @param A1
	 * @param A2
	 * @param T
	 * @param R
	 * @return
	 */
	private double calcSurfaceTension(double x1,double x1s,double ST1,double ST2,double A1,double A2,double TempK,double R) {
		
		Vector<Double>x=new Vector<Double>();
		x.add(x1);
		x.add(1-x1);
		
		double []g=this.unifac.calculateActivityCoefficients2(x,chemicals,TempK);

		Vector<Double>xs=new Vector<Double>();
		xs.add(x1s);
		xs.add(1-x1s);
		
		double []gs=this.unifac.calculateActivityCoefficients2(xs,chemicals, TempK);

		
		double term1=ST1+R*TempK/A1*Math.log(x1s*gs[0]/(x1*g[0]));
		
//		System.out.println(x1+"\t"+x1s+"\t"+gs[0]+"\t"+term1);

		
		return term1;
		
	}
	
	/**
	 * Calculate function for surface tension calculations-
	 * function is over the form f(x)=0
	 * 
	 * @param x1
	 * @param x1s
	 * @param ST1
	 * @param ST2
	 * @param A1
	 * @param A2
	 * @param TempK
	 * @param R
	 * @return
	 */
	private double calculatefx(double x1,double x1s,double ST1,double ST2,double A1,double A2,double TempK,double R) {

		Vector<Double>x=new Vector<Double>();
		x.add(x1);
		x.add(1-x1);
		
		double []g=this.unifac.calculateActivityCoefficients2(x,chemicals, TempK);

//		System.out.println(g[0]+"\t"+g[1]);
		
		Vector<Double>xs=new Vector<Double>();
		xs.add(x1s);
		xs.add(1-x1s);
		
		double []gs=this.unifac.calculateActivityCoefficients2(xs,chemicals, TempK);

		double term1=ST1+R*TempK/A1*Math.log(x1s*gs[0]/(x1*g[0]));
		double term2=ST2+R*TempK/A2*Math.log((1-x1s)*gs[1]/((1-x1)*g[1]));
		
		double fx=term1-term2;
		return fx;
	}
	/**
	 * Calculates vapor pressure of a mixture at a given temperature
	 * @param xmol - mole fractions of each component in the liquid phase
	 * @param tempK - temperature in degrees K
	 * @return
	 */
	private double calculateMixtureP(Vector<Double> xmol,double tempK) {

		//need to iterate until vapor pressure of the mixture is equal to atmospheric pressure
		int counter=0;

		Vector<String>comps=new Vector<String>();
		for (int i=0;i<xmol.size();i++) {
			comps.add(chemicals[i].getCAS());
		}
		
		double [] gammas=unifac.calculateActivityCoefficients2(xmol,chemicals, tempK);

		double Pcalc=0;
		for (int i=0;i<xmol.size();i++) {
			double Psati=chemicals[i].calculateVaporPressure(tempK);
			Pcalc+=xmol.get(i)*gammas[i]*Psati;
		}

		return Pcalc;
		
	}
	
	/**
	 * Calculates heat of vaporization @ 25C using clausius clapeyron equation
	 * See Catoire et al (2005)
	 * @param xmol - mole fraction of each component in the liquid phase
	 * @param tempK temperature in K
	 * @return
	 */
	private double calculateDeltaHVap(Vector<Double> xmol, double tempK) {
		double T1=tempK;
		double T2=tempK+0.001;
		
		double oneoverT1=1/(T1);
		double oneoverT2=1/(T2);
		
		double Pmix1=calculateMixtureP(xmol, T1);
		double Pmix2=calculateMixtureP(xmol, T2);
		
		double deltaHvap=-8.314/1000*Math.log(Pmix2/Pmix1)/(oneoverT2-oneoverT1);
		return deltaHvap;
	}
	

	
	/**
	 * Converts mol fractions to mass fractions
	 * @param molFractions
	 * @return
	 */
	public Vector<Double> calculateMassFractions(Vector<Double> molFractions) {
		
		Vector<Double> massFractions=new Vector<Double>();
		
		double MWmix=this.calculateMixtureMW(molFractions);
		
		for (int i=0;i<molFractions.size();i++) {
			massFractions.add(molFractions.get(i)*chemicals[i].getMolecularWeight()/MWmix);				
		}
		return massFractions;
		
	}
	
	/**
	 * Converts mol fractions to mass fractions
	 * @param molFractions
	 * @param chemicals
	 * @param MWmix
	 * @return
	 */
	private double [] calculateMoleFractions(double [] massFractions,Chemicals chemicals) {
		
		double [] moleFractions=new double [massFractions.length];
		
		//assume 1 gm of mixture
		
		double totalmoles=0;
		for (int i=0;i<massFractions.length;i++) {
			totalmoles+=massFractions[i]/chemicals.get(i).getMolecularWeight();				
		}
		
		for (int i=0;i<massFractions.length;i++) {
			moleFractions[i]=(massFractions[i]/chemicals.get(i).getMolecularWeight())/totalmoles;				
		}
		return moleFractions;
		
	}
	
	/**
	 * Get number of carbon atoms from molecular formula string
	 * 
	 * @param MF molecular formula - i.e. C6H6
	 * @return
	 */
	private double getCarbonCount(String MF) {
		char ThisChar;
		/*
		 *  Buffer for
		 */
		String RecentElementSymbol = new String();
		String RecentElementCountString = new String("0");
		/*
		 *  String to be converted to an integer
		 */
		int RecentElementCount;

		if (MF.length() == 0) {
			return 0;
		}

		for (int f = 0; f < MF.length(); f++) {
			ThisChar = MF.charAt(f);
			if (f < MF.length()) {
				if (ThisChar >= 'A' && ThisChar <= 'Z') {
					/*
					 *  New Element begins
					 */
					RecentElementSymbol = java.lang.String.valueOf(ThisChar);
					RecentElementCountString = "0";
				}
				if (ThisChar >= 'a' && ThisChar <= 'z') {
					/*
					 *  Two-letter Element continued
					 */
					RecentElementSymbol += ThisChar;
				}
				if (ThisChar >= '0' && ThisChar <= '9') {
					/*
					 *  Two-letter Element continued
					 */
					RecentElementCountString += ThisChar;
				}
			}
			if (f == MF.length() - 1 || (MF.charAt(f + 1) >= 'A' && MF.charAt(f + 1) <= 'Z')) {
				/*
				 *  Here an element symbol as well as its number should have been read completely
				 */
				Integer RecentElementCountInteger = new Integer(RecentElementCountString);
				RecentElementCount = RecentElementCountInteger.intValue();
				if (RecentElementCount == 0) {
					RecentElementCount = 1;
				}
				
				if (RecentElementSymbol.equals("C")) {
					return RecentElementCount;	
				}

			}
		}
		return 0;
	}

	/**
	 * 
	 * Calculate the flash point of the mixture
	 * 
	 * See Catoire et al (2005)
	 * 
	 * 
	 * @param molFractions - mol fractions in liquid phase
	 * @param BPmix_K - boiling point of the mixture
	 * @return flash point of mixture
	 */
	private double calculateMixtureFP_Catoire(Vector<Double>molFractions, double BPmix_K) {
		
		double tempK=298.15;
		double deltaHVap=calculateDeltaHVap(molFractions, tempK);//deltaHvap at 298K
		double [] ymol=calculateVaporPhaseCompositionAtNormalBP(molFractions, getBoilingPoint());

		
		double nmix=0;
		for (int i=0;i<ymol.length;i++) {
			
			//get carbon count from molecular formula
			//alternatively we get from a mol file
			double ni=getCarbonCount(chemicals[i].getFormula());
			nmix+=ymol[i]*ni;
			
		}
//		System.out.println(nmix);
		
		double term1=Math.pow(BPmix_K,0.79686);
		double term2=Math.pow(deltaHVap,0.16845);
		double term3=Math.pow(nmix,-0.05948);
		double FPmix=1.477*term1*term2*term3;
//		System.out.println(FPmix);
		return FPmix;
	}
	
	private double calculateMPFunction(Vector<Double> xmol,double tempK,int i) {
		
		double R=8.314;//gas constant in J/molK
		
		double [] gammas=unifac.calculateActivityCoefficients2(xmol,chemicals, tempK);

		
		double Tmi=chemicals[i].getMeltingPoint();
		double Hfusi=chemicals[i].getEnthalpyOfFusion();
		
		double deltaCp=chemicals[i].getHeatCapacity()-chemicals[i].getHeatCapacitySolid();
		
		double term1=Math.log(gammas[i]*xmol.get(i));
		
		double term2=Hfusi/(R*Tmi)*(Tmi/tempK-1);
		
		double term3=deltaCp/R*(Math.log(Tmi/tempK)-Tmi/tempK+1);
		
		return term1+term2+term3;
	}
	
	
	/**
	 * Calculates lechatelier function (used for calculating FP of mixture)
	 * Function is of the form f(x)=0;
	 * 
	 * @param xmol
	 * @param tempK
	 * @return
	 */
	private double calculate_LeChatelierFunction(Vector<Double> xmol, double tempK) {
		double f=0;
		
		
		Vector<String>comps=new Vector<String>();
		for (int i=0;i<xmol.size();i++) {
			comps.add(chemicals[i].getCAS());
		}

		double [] gammas=unifac.calculateActivityCoefficients2(xmol,chemicals, tempK);

		for (int i=0;i<xmol.size();i++) {
			
			double FPi=chemicals[i].getFlashPoint();
			
			if (FPi>1000) continue;
			
			double Psat=chemicals[i].calculateVaporPressure(tempK);
			double PsatFP=chemicals[i].calculateVaporPressure(FPi);
			
			f+=xmol.get(i)*gammas[i]*Psat/PsatFP;
			
		}
		f-=1.0;
		
		return f;
	}
	
	/**
	 * /**
	 * Calculates the eutectic point of the mixture using the method of 
	 * Jakob, A; Roh, R.; Rose, C.; Gmehling J.  "Solid-liquid equilibria
	 * in binary mixtures of organic compounds", Fluid Phase Equilibria, Vol 13,
	 * (1995) 117-126. See equation 7, pg 119- this equation only needs heats
	 * of fusion and doesn't need the difference in heat capacities between the
	 * solid and liquid.
	 *  
	 *  TODO: make this work for any number of components
	 *   
	 * @param xmol - mol fractions 
	 * 
	 * @return
	 */
	private double calculateEutecticComposition_Simple() {
		//for initial guess, use mol fraction weighted value:
		
//		let eutectic composition = 0.5:
		double x0=0.5;
		Vector<Double>x=new Vector<Double>();
		x.add(x0);
		x.add(1-x0);
		
		double MPmix=0;
		for (int i=0;i<chemicals.length;i++) {
			MPmix+=x.get(i)*chemicals[i].getMeltingPoint();
		}
		
		int counter=0;

		double MPcalc=-9999;

		double frac=0.25;
		
		
		double R=8.314;//gas constant in J/molK or KJ/kmolK
		
		double deltaHm0=chemicals[0].getEnthalpyOfFusion();
		double deltaHm1=chemicals[1].getEnthalpyOfFusion();
		double MP0=chemicals[0].getMeltingPoint();
		double MP1=chemicals[1].getMeltingPoint();
		
		while (true) {
			counter++;

			double [] gammas=unifac.calculateActivityCoefficients2(x,chemicals, MPmix);

			double x0calc=Math.exp(-deltaHm0/(R*MPmix)*(1-MPmix/MP0))/gammas[0];
		
			x0=frac*(x0calc)+(1-frac)*x0;
			x.set(0, x0);
			x.set(1, 1-x0);

			if (x0>=1) return 1;
			
			double term1=(R/deltaHm1)*Math.log((1-x0)*gammas[1]);
			
//			MPcalc=1/(term1+1/MPi);//equation 7 solved for T
			MPcalc=-1/(term1-1/MP1);//equation 7 solved for T

			double diff=Math.abs(MPcalc-MPmix);

			if (Math.abs(diff)<0.01) {
//				double LHS=Math.log(xmol.get(0)*gammas[0]);
//				double RHS=heatFusion0/(R*MPmix)*(1-MPmix/MP0);
//				System.out.println(LHS+"\t"+RHS);
				break;
			}

			if (counter==10000) {
				return -9999;//didnt converge
			}

			MPmix=frac*(MPcalc)+(1-frac)*MPmix;

//			System.out.println("x0="+x0+"\tMPcalc="+MPcalc);

		}
		
		return x0;
	}
	
	
	/**
	 * /**
	 * Calculates the melting point of the mixture using the method of 
	 * Jakob, A; Roh, R.; Rose, C.; Gmehling J.  "Solid-liquid equilibria
	 * in binary mixtures of organic compounds", Fluid Phase Equilibria, Vol 13,
	 * (1995) 117-126. See equation 7, pg 119- this equation only needs heats
	 * of fusion and doesn't need the difference in heat capacities between the
	 * solid and liquid.
	 *  
	 *  TODO: make this work for any number of components
	 *   
	 * @param xmol - mol fractions 
	 * 
	 * @return
	 */
	private double calculateMixtureMP_Simple(Vector <Double>xmol) {

		//calculate eutectic composition:
		double xE=this.calculateEutecticComposition_Simple();
		
		//for initial guess, use mol fraction weighted value:
		double MPmix=0;
		for (int i=0;i<xmol.size();i++) {
			MPmix+=xmol.get(i)*chemicals[i].getMeltingPoint();
		}

//		MPmix=200;
		
//		System.out.println("MPmix="+MPmix);
		int counter=0;

		double MPcalc=-9999;

		double frac=0.5;
		
		int i=-1;
		
		if (xmol.get(0)<xE) i=1;
		else i=0;
		
		double MPi=chemicals[i].getMeltingPoint();
		double heatFusioni=chemicals[i].getEnthalpyOfFusion();
		double R=8.314;//gas constant in J/molK or KJ/kmolK
		
		while (true) {
			counter++;

			double [] gammas=unifac.calculateActivityCoefficients2(xmol,chemicals, MPmix);

			double term1=(R/heatFusioni)*Math.log(xmol.get(i)*gammas[i]);
			
//			MPcalc=1/(term1+1/MPi);//equation 7 solved for T- the equation is wrong in the paper!
			MPcalc=-1/(term1-1/MPi);//equation 7 solved for T

			double diff=Math.abs(MPcalc-MPmix);

			if (Math.abs(diff)<0.01) {
//				double LHS=Math.log(xmol.get(0)*gammas[0]);
//				double RHS=heatFusion0/(R*MPmix)*(1-MPmix/MP0);
//				System.out.println(LHS+"\t"+RHS);
				break;
			}

			if (counter==10000) {
				return -9999;//didnt converge
			}

			MPmix=frac*(MPcalc)+(1-frac)*MPmix;
			
//			System.out.println(MPcalc+"\t"+MPmix);

		}
		
		
//		System.out.println(xmol.get(0)+"\t"+MPmix);
		return MPmix;
	}

	/**
	 * Calculates the melting point of the mixture using equation 18 of Huang
	 * et al, 2000
	 * 
	 * 
	 * 
	 *  
	 * @param xmol - mol fractions in liquid phase
	 * @return
	 */
	private double calculateMixtureMP_Complex(Vector <Double>xmol) {
		
		double xE=this.calculateEutecticComposition_Complex();
		
		//For initial guess of FP, use mole fraction weighted average:
		double MPmix=0;
		for (int i=0;i<xmol.size();i++) {
			MPmix+=chemicals[i].getMeltingPoint()*xmol.get(i);
		}
		
		int i=-1;
		if (xmol.get(0)<xE) i=1;
		else i=0;

		MPmix=this.solveForMP(xmol, MPmix, i);
		return MPmix;
		
	}
	
	
	/**
	 * Calculates the eutectic composition of the mixture using equation 18 of Huang
	 * et al, 2000
	 * 
	 * 
	 * 
	 *  
	 * @param xmol - mol fractions in liquid phase
	 * @return
	 */
	private double calculateEutecticComposition_Complex() {
		
		
//		let eutectic composition = 0.5:
		double x0=0.5;
		Vector<Double>x=new Vector<Double>();
		x.add(x0);
		x.add(1-x0);
		
		double MPmix=0;
		for (int i=0;i<chemicals.length;i++) {
			MPmix+=x.get(i)*chemicals[i].getMeltingPoint();
		}

		double R=8.314;//gas constant in J/molK or KJ/kmolK
		
		double deltaHm0=chemicals[0].getEnthalpyOfFusion();
		double MP0=chemicals[0].getMeltingPoint();
		double deltaCp0=chemicals[0].getHeatCapacity()-chemicals[0].getHeatCapacitySolid();

		double deltaHm1=chemicals[1].getEnthalpyOfFusion();
		double MP1=chemicals[1].getMeltingPoint();
		

		double counter=0;
		double frac=0.25;
		
		while (true) {
			counter++;

			double [] gammas=unifac.calculateActivityCoefficients2(x,chemicals, MPmix);

			double term1=-deltaHm0/(R*MP0)*(MP0/MPmix-1);
			double term2=-deltaCp0/R*(Math.log(MP0/MPmix)-MP0/MPmix+1);

			double x0calc=Math.exp(term1+term2)/gammas[0];
		
			x0=frac*(x0calc)+(1-frac)*x0;
			x.set(0, x0);
			x.set(1, 1-x0);
			
			if (x0>=1) return 1;
			
			double MPcalc=this.solveForMP(x, MPmix, 1);
			
			double diff=Math.abs(MPcalc-MPmix);

			if (Math.abs(diff)<0.01) {
//				double LHS=Math.log(xmol.get(0)*gammas[0]);
//				double RHS=heatFusion0/(R*MPmix)*(1-MPmix/MP0);
//				System.out.println(LHS+"\t"+RHS);
				break;
			}

			if (counter==10000) {
				return -9999;//didnt converge
			}

			MPmix=frac*(MPcalc)+(1-frac)*MPmix;

//			System.out.println("x0="+x0+"\tMPcalc="+MPcalc);

		}
		return x0;
	}
	double solveForMP(Vector<Double>x,double MPmix,int i) {
		//for second guess, perturb it slightly:
		double MPmixplus1=MPmix+0.0001;
		
		double fx=calculateMPFunction(x,MPmix,i);
		double fxnew=calculateMPFunction(x,MPmixplus1,i);
		double fprime=(fxnew-fx)/(MPmixplus1-MPmix);

		int counter=0;
		double frac=0.5;
		
		while (true) {
			counter++;
			MPmix=MPmixplus1;
			fx=fxnew;
			
			if (counter==1000) return -9999;
			
			//calculate new x1s: (modified newton raphson which only takes a partial step using frac
			//http://en.wikipedia.org/wiki/Newton_Raphson
			double newvalue=MPmixplus1-fx/fprime;//newton raphson estimate
			
			MPmixplus1=frac*(newvalue)+(1-frac)*MPmixplus1;

			fxnew=calculateMPFunction(x,MPmixplus1,i);
			
			fprime=(fxnew-fx)/(MPmixplus1-MPmix);

			if (Math.abs(fxnew)<0.0001) {
				break;
			} 
		
		}
		
		return MPmix;
		
	}
	
	/**
	 * /**
	 * Calculates the flash point of the mixture using the method of 
	 * Liaw, H-J; Gerbaud, V.; Li, Y-H. "Prediction of miscible mixtures flash-point
	 * from UNIFAC group contribution methods", Fluid Phase Equilibria, 300,(2011), 70-82
	 *  
	 * @param xmol - mol fractions in liquid phase
	 * @return
	 */
	private double calculateMixtureFP_Liaw(Vector <Double>xmol) {
		
		
		//For initial guess of FP, use mole fraction weighted average:
		double FPmix=0;
		double sumMolFrac=0;
		for (int i=0;i<xmol.size();i++) {
			
			if (chemicals[i].getFlashPoint()>1000) continue;//for components like water I set the FP to 9999 C
			
//			System.out.println(chemical.getCAS()+"\t"+chemical.getFlashPoint());
			
			sumMolFrac+=xmol.get(i);
			FPmix+=chemicals[i].getFlashPoint()*xmol.get(i);
		}
		FPmix/=sumMolFrac;//divide by sum of mol fractions in case one component was nonflammable
		
		
		//for second guess, perturb it slightly:
		double FPmixplus1=FPmix+0.0001;
		
		double fx=calculate_LeChatelierFunction(xmol, FPmix);
		
		double fxnew=calculate_LeChatelierFunction(xmol, FPmixplus1);

		double fprime=(fxnew-fx)/(FPmixplus1-FPmix);

		int counter=0;
		
		double frac=0.5;
		
		while (true) {
			counter++;
			FPmix=FPmixplus1;
			fx=fxnew;
			
			if (counter==1000) return -9999;
			
			//calculate new x1s: (modified newton raphson which only takes a partial step using frac
			//http://en.wikipedia.org/wiki/Newton_Raphson
			double newvalue=FPmixplus1-fx/fprime;//newton raphson estimate

//			System.out.println(counter+"\t"+FPmixplus1+"\t"+newvalue);

//			double maxIncr=50;
//			if (Math.abs(newvalue-FPmixplus1)>maxIncr) {
//				//to avoid blowing up:
//				if (newvalue>FPmixplus1) FPmixplus1+=maxIncr;
//				else FPmixplus1-=maxIncr;
//				
//				System.out.println(counter+"\t"+FPmixplus1);
//				
//			} else {
//				FPmixplus1=frac*(newvalue)+(1-frac)*FPmixplus1;	
//			}
			
			FPmixplus1=frac*(newvalue)+(1-frac)*FPmixplus1;			

			fxnew=calculate_LeChatelierFunction(xmol, FPmixplus1);
			
			fprime=(fxnew-fx)/(FPmixplus1-FPmix);

			if (Math.abs(fxnew)<0.0001) {
				break;
			} 
		
		}
		System.out.println("FP converged in "+counter+" iterations");
		return FPmixplus1;
	}

	
	/**
	 * /**
	 * Calculates the flash point of the mixture using the method of 
	 * Liaw, H-J; Gerbaud, V.; Li, Y-H. "Prediction of miscible mixtures flash-point
	 * from UNIFAC group contribution methods", Fluid Phase Equilibria, 300,(2011), 70-82
	 *  
	 * @param xmol - mol fractions in liquid phase
	 * 
	 * This version calculates derivatives using small perturbation of current value instead of using value and next value
	 * 
	 * 
	 * 
	 * Modified newton raphson which only takes a partial step using frac
			//http://en.wikipedia.org/wiki/Newton_Raphson

	 * @return
	 */
	private double calculateMixtureFP_Liaw3(Vector <Double>xmol) {
		int maxIterations=1000;
		double tol=0.0001;
		
		//For initial guess of FP, use mole fraction weighted average:
		double FPmix=0;
		double sumMolFrac=0;
		for (int i=0;i<xmol.size();i++) {

			//for components like water I set the FP to 9999 C so skip them
			if (chemicals[i].getFlashPoint()>1000) continue;
//			System.out.println(chemical.getCAS()+"\t"+chemical.getFlashPoint());
			sumMolFrac+=xmol.get(i);
			FPmix+=chemicals[i].getFlashPoint()*xmol.get(i);
		}
		FPmix/=sumMolFrac;//divide by sum of mol fractions in case one component was nonflammable
		
		double frac=0.5;
		double small=1e-3;
		
		for (int i=0;i<maxIterations;i++) {
			double fx=calculate_LeChatelierFunction(xmol, FPmix);
			double fx2=calculate_LeChatelierFunction(xmol, FPmix+small);
			double fprime=(fx2-fx)/small;
			
			double dx=fx/fprime;
			
//			FPmix-=dx;
			FPmix=frac*(FPmix-dx)+(1-frac)*FPmix;//only take partial step towards new value
			
			if (Math.abs(dx)<0.0001) {
//				System.out.println("Converged after "+i+" iterations");
				return FPmix;
			}
		}
		
//		System.out.println("Fail to converge");
		return -9999;
		
	}
	//**See page 366 of numerical recipes in C- this uses Newton-Raphson+Bisection
	private double calculateMixtureFP_Liaw2(Vector <Double>xmol)
	{
		int MAXIT=100;
		
//		String nrerror;
		int j;
		double df,dx,dxold,f,fh,fl;
		double temp,xh,xl,rts;
		
		double FPmix=0;
		double sumMolFrac=0;
		for (int i=0;i<xmol.size();i++) {
			
			if (chemicals[i].getFlashPoint()>1000) continue;//for components like water I set the FP to 9999 C
			
//			System.out.println(chemical.getCAS()+"\t"+chemical.getFlashPoint());
			
			sumMolFrac+=xmol.get(i);
			FPmix+=chemicals[i].getFlashPoint()*xmol.get(i);
		}
		FPmix/=sumMolFrac;//divide by sum of mol fractions in case one component was nonflammable
	
		double FP1=200;
		double FP2=1000;
		double xacc=0.01;


//		(*funcd)(x1,&fl,&df);
//		(*funcd)(x2,&fh,&df);
		
		fl=calculate_LeChatelierFunction(xmol, FP1);
		fh=calculate_LeChatelierFunction(xmol, FP2);
		
		if ((fl > 0.0 && fh > 0.0) || (fl < 0.0 && fh < 0.0)) {
//			System.out.println("Root must be bracketed in rtsafe");
//			for (double i=FP1;i<=FP2;i++) {
//				double fi=calculate_LeChatelierFunction(xmol, FP2);
//				System.out.println(i+"\t"+fi);
//			}
			
			return -9999;
		}
		
		if (fl == 0.0) return FP1;
		if (fh == 0.0) return FP2;
		
		if (fl < 0.0) {
			xl=FP1;
			xh=FP2;
		} else {
			xh=FP1;
			xl=FP2;
		}
//		rts=0.5*(FP1+FP2);//initial guess
		rts=FPmix;//TMM
		
		dxold=Math.abs(FP2-FP1);
		dx=dxold;

//		(*funcd)(rts,&f,&df);
		f=calculate_LeChatelierFunction(xmol, rts);
		
		double f2=calculate_LeChatelierFunction(xmol, rts+1e-8);
		df=(f2-f)/1e-8;
		
		for (j=1;j<=MAXIT;j++) {
			if ((((rts-xh)*df-f)*((rts-xl)*df-f) > 0.0)
				|| (Math.abs(2.0*f) > Math.abs(dxold*df))) {
				dxold=dx;
				dx=0.5*(xh-xl);
				rts=xl+dx;
				if (xl == rts) return rts;
			} else {
				dxold=dx;
				dx=f/df;
				temp=rts;
				rts -= dx;
				if (temp == rts) return rts;
			}
			if (Math.abs(dx) < xacc) {
//				System.out.println("Converged after "+j+" iterations");
				return rts;
			}
//			(*funcd)(rts,&f,&df);
			if (f < 0.0)
				xl=rts;
			else
				xh=rts;
		}
		
		System.out.println("Maximum number of iterations exceeded in rtsafe");
		return -9999;

	}
	
	/**
	 * Calculates mixture viscosity 
	 * See http://en.wikipedia.org/wiki/Viscosity
	 * 
	 * @param xmass
	 * @param densityMix
	 * @return
	 */
	private double calculateMixtureViscosity(Vector<Double>xmass, double densityMix) {

		double viscosityMix=0;

		double VBNmix=0;
		
		for (int i=0;i<xmass.size();i++) {
			
			double viscCommon=Units.viscosityConvertTo(chemicals[i].getViscosity(),Units.COMMON);
			double densityCommon=Units.densityConvertTo(chemicals[i].getDensity(),Units.COMMON);
			
			double v_i=viscCommon/densityCommon;
			
			double VBNi=14.534*Math.log(Math.log(v_i+0.8))+10.975;
			VBNmix+=xmass.get(i)*VBNi;
		}
		
		double v_mix=Math.exp(Math.exp((VBNmix-10.975)/14.534))-0.8;
		
		double densityMixCommon=Units.densityConvertTo(densityMix,Units.COMMON);
		
		viscosityMix=v_mix*densityMixCommon;
		
		double viscosityMixSI=Units.viscosityConvertFrom(viscosityMix, Units.COMMON);
		
		return viscosityMixSI;
		

	}
	
	/**
	 * Calculates mixture thermal conductivity 
	 * See http://onlinelibrary.wiley.com/doi/10.1002/aic.690220520/abstract
	 * 
	 * @param xvol - volume fractions of each component
	 * @return
	 */
	private double calculateMixtureTC(Vector<Double>xvol) {

		double TCMix=0;

		for (int i=0;i<xvol.size();i++) {
			
			double TCi=chemicals[i].getThermalConductivity();
			for (int j=0;j<xvol.size();j++) {
				double TCj=chemicals[j].getThermalConductivity();
				
				double TCij=2/(1/TCi+1/TCj);
				TCMix+=xvol.get(i)*xvol.get(j)*TCij;
			}
		}
		
		return TCMix;
	}
	
	
/**
 * Calculates the density of the mixture
 * see http://en.wikibooks.org/wiki/Introduction_to_Chemical_Engineering_Processes/The_most_important_point
 * 
 * @param xmass - mass fractions of each component in the liquid phase
 * @return
 */
	private double calculateMixtureDensity(Vector<Double> xmass) {
		
		double density=0;
		
		double sum=0;
		
		for (int i=0;i<xmass.size();i++) {
			sum+=xmass.get(i)/chemicals[i].getDensity();
		}
		density=1/sum;
		return density;
	}
	
	public String getMixtureName() {
		int deflen = 10;
		String chemName;
		
		String mixName = new String();
		mixName += (int)(this.getMixtureScore()*1.0e-9)+": ";
		
		Chemical[] chemicals = this.getChemicals2();
		for (int i=0; i<chemicals.length; i++) {
			chemName = chemicals[i].getName();
			mixName += (chemName.length()<=deflen ? chemName : chemName.substring(0,deflen))+"; ";
		}
		mixName += this.getMassRatios();

		return mixName;
	}

	public Vector<Chemical> getChemicals() {
		return convertAV(chemicals);
	}

	public void setChemicals(Vector<Chemical> chemicals) {
		this.chemicals = convertVA(chemicals);
	}
	
	public void setChemicals(Chemical[] chemicals) {
		this.chemicals = chemicals;
	}
	
	public Chemical[] getChemicals2() {
		return chemicals;
	}

	public Vector<Double> getWghtFractions() {
		return wghtFractions;
	}

	public void setWghtFractions(Vector<Double> weightFractions) {
		this.wghtFractions = weightFractions;
	}

//	public double getTempK() {
//		return tempK;
//	}
//
//	public void setTempK(double tempK) {
//		this.tempK = tempK;
//	}

	public UNIFACforPARIS getUnifac() {
		if (this.unifac == null) this.unifac = UNIFACforPARIS.getInstance();
		return unifac;
	}

	public void setUnifac(UNIFACforPARIS unifac) {
		this.unifac = unifac;
	}

	public String getMassRatios() {
		return massRatios;
	}
	
	private void setMassRatios(String massRatios) {
		this.massRatios = massRatios;
	}
	
	public double getMixtureScore() {
		return mixtureScore;
	}
	
	private void setMixtureScore(double mixtureScore) {
		this.mixtureScore = mixtureScore;
	}

}
