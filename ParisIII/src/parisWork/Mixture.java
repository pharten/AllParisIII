package parisWork;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Vector;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.graphics.FontMetrics;

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
	
	public boolean equals(Mixture other) {
		
		if (this==other) return true;
		if (other==null) return false;
		if (!(other instanceof Mixture)) return false;
		
		if (this.tempK!=other.tempK) return false;
		if (this.mixtureScore!=other.mixtureScore) return false;
		if (this.massRatios==null && other.massRatios!=null) return false;
		if (this.massRatios!=null && !this.massRatios.equals(other.massRatios))return false;

		if (this.chemicals!=null && other.chemicals!=null) {
			if (this.chemicals.length!=other.chemicals.length) return false;
			if (this.wghtFractions.size()!=other.wghtFractions.size()) return false;
			for (int i=0; i<this.chemicals.length; i++) {
				if (!this.chemicals[i].equals(other.chemicals[i])) return false;
				if (!this.wghtFractions.get(i).equals(other.wghtFractions.get(i))) return false;
			}
		} else if (this.chemicals!=other.chemicals)  return false;
		
		if (this.unifac!=other.unifac) return false;

		if (this.getSurfaceTension()!=other.getSurfaceTension()) return false;
		if (this.getDensity()!=other.getDensity()) return false;
		if (this.getFlashPoint()!=other.getFlashPoint()) return false;
		if (this.getHeatCapacity()!=other.getHeatCapacity()) return false;
		if (this.getMolecularWeight()!=other.getMolecularWeight()) return false;
		if (this.getBoilingPoint()!=other.getBoilingPoint()) return false;
		if (this.getThermalConductivity()!=other.getThermalConductivity()) return false;
		if (this.getMeltingPoint()!=other.getMeltingPoint()) return false;
		if (this.getViscosity()!=other.getViscosity()) return false;
		if (this.getVaporPressure()!=other.getVaporPressure()) return false;
		
		if (this.getInfDilActCoef_acetone()!=other.getInfDilActCoef_acetone()) return false;
		if (this.getInfDilActCoef_benzene()!=other.getInfDilActCoef_benzene()) return false;
		if (this.getInfDilActCoef_cis_2_heptene()!=other.getInfDilActCoef_cis_2_heptene()) return false;
		if (this.getInfDilActCoef_diethyl_ether()!=other.getInfDilActCoef_diethyl_ether()) return false;
		if (this.getInfDilActCoef_dimethyl_disulfide()!=other.getInfDilActCoef_dimethyl_disulfide()) return false;
		if (this.getInfDilActCoef_ethanol()!=other.getInfDilActCoef_ethanol()) return false;
		if (this.getInfDilActCoef_n_heptadecane()!=other.getInfDilActCoef_n_heptadecane()) return false;
		if (this.getInfDilActCoef_n_propyl_chloride()!=other.getInfDilActCoef_n_propyl_chloride()) return false;
		if (this.getInfDilActCoef_n_propylamine()!=other.getInfDilActCoef_n_propylamine()) return false;
		if (this.getInfDilActCoef_water()!=other.getInfDilActCoef_water()) return false;

		if (this.getMixtureScore()!=other.getMixtureScore()) return false;
		if (this.getAirIndex()!=other.getAirIndex()) return false;
		if (this.getEnvironmentalIndex()!=other.getEnvironmentalIndex()) return false;
		
		return true;
	}

	@Override
	public Mixture clone() throws CloneNotSupportedException { // deep copy
		
		Mixture clone = (Mixture) super.clone(); // most of the properties are cloned here
		
		if (this.chemicals!=null) {
			clone.chemicals = new Chemical[this.chemicals.length];
			clone.wghtFractions = new Vector<Double>();
			for (int i=0; i<this.chemicals.length; i++) {
				clone.chemicals[i] = this.chemicals[i].clone();
				clone.wghtFractions.add(this.wghtFractions.get(i));
			}
		}
		
		clone.setUnifac(getUnifac());
		
		clone.setMixtureScore(getMixtureScore());
		clone.setAirIndex(getAirIndex());
		clone.setEnvironmentalIndex(getEnvironmentalIndex());
		
		if (this.getMassRatios()!=null) clone.setMassRatios(new String(getMassRatios()));
		
		return clone;
	}
	
	public boolean deepEquals(Mixture other) { // deep copy
		
		if (this==other) return true;
		if (other==null) return false;
		if (!(other instanceof Mixture)) return false;
		
		if (this.unifac!=other.unifac) return false;
		
		// compare primitives
		if (this.tempK!=other.tempK) return false;
		if (this.getAntoineConstantA()!=other.getAntoineConstantA()) return false;
		if (this.getAntoineConstantB()!=other.getAntoineConstantB()) return false;
		if (this.getAntoineConstantC()!=other.getAntoineConstantC()) return false;
		if (this.getAP()!=other.getAP()) return false;
		if (this.getAutoIgnitTemp()!=other.getAutoIgnitTemp()) return false;
		if (this.getDensity()!=other.getDensity()) return false;
		if (this.getFlashPoint()!=other.getFlashPoint()) return false;
		if (this.getGWP()!=other.getGWP()) return false;
		if (this.getHeatCapacity()!=other.getHeatCapacity()) return false;
//		if (this.getHtoxDermal()!=other.getHtoxDermal()) return false;
		if (this.getTerrestrialTox()!=other.getTerrestrialTox()) return false;
		if (this.getHtoxIngestion()!=other.getHtoxIngestion()) return false;
		if (this.getHtoxInhalation()!=other.getHtoxInhalation()) return false;
		if (this.getInfDilActCoef_acetone()!=other.getInfDilActCoef_acetone()) return false;
		if (this.getInfDilActCoef_benzene()!=other.getInfDilActCoef_benzene()) return false;
		if (this.getInfDilActCoef_cis_2_heptene()!=other.getInfDilActCoef_cis_2_heptene()) return false;
		if (this.getInfDilActCoef_diethyl_ether()!=other.getInfDilActCoef_diethyl_ether()) return false;
		if (this.getInfDilActCoef_dimethyl_disulfide()!=other.getInfDilActCoef_dimethyl_disulfide()) return false;
		if (this.getInfDilActCoef_ethanol()!=other.getInfDilActCoef_ethanol()) return false;
		if (this.getInfDilActCoef_n_heptadecane()!=other.getInfDilActCoef_n_heptadecane()) return false;
		if (this.getInfDilActCoef_n_propyl_chloride()!=other.getInfDilActCoef_n_propyl_chloride()) return false;
		if (this.getInfDilActCoef_n_propylamine()!=other.getInfDilActCoef_n_propylamine()) return false;
		if (this.getInfDilActCoef_water()!=other.getInfDilActCoef_water()) return false;
		if (this.getLFlamLimit()!=other.getLFlamLimit()) return false;
		if (this.getMolecularWeight()!=other.getMolecularWeight()) return false;
		if (this.getODP()!=other.getODP()) return false;
		if (this.getPCOP()!=other.getPCOP()) return false;
		if (this.getSolubility()!=other.getSolubility()) return false;
		if (this.getBoilingPoint()!=other.getBoilingPoint()) return false;
		if (this.getThermalConductivity()!=other.getThermalConductivity()) return false;
		if (this.getMeltingPoint()!=other.getMeltingPoint()) return false;
		if (this.getViscosity()!=other.getViscosity()) return false;
		if (this.getVaporPressure()!=other.getVaporPressure()) return false;

		if (this.getMixtureScore()!=other.getMixtureScore()) return false;
		if (this.getEnvironmentalIndex()!=other.getEnvironmentalIndex()) return false;
		if (this.getAirIndex()!=other.getAirIndex()) return false;
		
		// compare strings
		if (this.getMassRatios()==null ? other.getMassRatios()!=null : !this.getMassRatios().equals(other.getMassRatios())) return false;

		// compare arrays of primitives
		if (this.wghtFractions==null ? other.wghtFractions!=null : !this.wghtFractions.equals(other.wghtFractions)) return false;
		
		//compare mixture chemicals by CAS# only
		if (this.chemicals==other.chemicals) return true;
		if (this.chemicals==null || other.chemicals==null) return false;
		if (this.chemicals.length != other.chemicals.length) return false;
		else {
			for (int i=0; i<this.chemicals.length; i++) {
				String CAS1 = this.chemicals[i].getCAS();
				String CAS2 = other.chemicals[i].getCAS();
				if (CAS1==null ? CAS2!=null : !CAS1.equals(CAS2)) return false;
			}
		}

		return true;
	}
	
	public void print(Printer p, int replacementNumber, int[] line, int[] page) {
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
		
		if (line[0]>=linesPerPage-this.wghtFractions.size()-1) {
			if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) p.endPage();
			line[0]=0;
			page[0]++;
			if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) p.startPage();
		}
				
		line[0]++;
		if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) {
			int flags =(int)(this.getMixtureScore()*(1.0e-9));
			double mixtureScore = this.getMixtureScore()-flags*(1.0e9);
			output = replacementNumber+": Environmental Index = "+df.format(this.getEnvironmentalIndex())+
					", Air Index = "+df.format(this.getAirIndex())+
					", #flags = "+flags+
					", Score = "+df.format(mixtureScore);
			gc.drawString(output, leftMargin, topMargin+(line[0]-1)*lineHeight);
		}
		
		for (int i=0; i<this.wghtFractions.size(); i++) {
			line[0]++;
			if (p.getPrinterData().startPage<=page[0] && page[0]<=p.getPrinterData().endPage) {
				output = df.format(wghtFractions.get(i))+": "+this.chemicals[i].getCAS()+
						", "+this.chemicals[i].getName();
				gc.drawString(output, leftMargin, topMargin+(line[0]-1)*lineHeight);
			}
		}
		
		gc.dispose();
	}
	
	public double calculateMixtureScore(State activeState) throws Exception {

		this.mixtureScore = Double.MAX_VALUE;
		this.tempK = activeState.tempConvertToSI();
		
		if(activeState.includeMiscibility() && !this.isOnePhase(tempK)) return mixtureScore;
		
		double[] tolerance = activeState.getPTolerances();
		double[] desired = activeState.getPDesiredVals();

//		Properties.calculate(this, UNIFACforPARIS.getInstance(), activeState.tempConvertToSI());
		this.calculateMixtureProperties(tempK);
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
				Double visc=new Double(getViscosity());
				if (visc.isNaN()) {
//					System.out.println("Cant calculate viscosity!");
					//Sometimes if the viscosity of any component is less than 0.2 cP, then the mixture
					//calculation will fail since ln(ln(v+0.8)) is taking the ln of negative number!
					// so use simple empirical calculation based on mole fraction:
					setViscosity(calculateMixtureViscositySimple(molFractions));	
				}
				
				//for testing purposes:
//				setViscosity(calculateMixtureViscositySimple(molFractions));

				
				//Use volume fractions since much faster and probably about as 
				// accurate (and also can be applied to any number of components):
				setSurfaceTension(calculateMixtureST(volumeFractions));

				//More complicated unifac based method
				//			setSurfaceTension(calculateMixtureSTwithUNIFACAi(molFractions, chemicals, tempK));

				//			setMeltingPoint(calculateMixtureMP_Simple(molFractions));
				//			setMeltingPoint(calculateMixtureMP_Complex(molFractions));

				//****************************************************************
				//following is for convenience until I can add antoine constants:

				if (!this.canDetermineVaporPressure(tempK)) {
//					System.out.println("Missing antoine constants and critical constants- can't calculate vapor pressure");
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
	
	
	/**
	 * Calculate viscosity using volume fractions
	 * @param volFractions
	 * @return
	 */
	public double calculateMixtureViscositySimple(Vector<Double> molFractions) {
		
		double VmixVol=0;

		//////////////////////////////////////////////////
		//equation 1: simply weight by mol fractions
//		for (int i=0;i<chemicals.length;i++) {
//			VmixVol+=molFractions.get(i)*chemicals[i].getViscosity();
//		}
		
		/////////////////////////////////////////////////////////
		//equation 2: weight by mol fracs but use ln of viscosities when combining:
//		for (int i=0;i<chemicals.length;i++) {
//			VmixVol+=molFractions.get(i)*Math.log(chemicals[i].getViscosity());
//		}
//		VmixVol=Math.exp(VmixVol);
	/////////////////////////////////////////////////////////////
		//equation 3: use approach similar to wikipedia approach but without the complicated double ln function:
		//equation 3 gives best MAE for chemicals in MixtureTextViscosity
		
		for (int i=0;i<molFractions.size();i++) {
			double v_i=chemicals[i].getViscosity()/chemicals[i].getDensity();
			VmixVol+=molFractions.get(i)*Math.log(v_i);
		}
		VmixVol=Math.exp(VmixVol);
		VmixVol*=getDensity();
		
		return VmixVol;
	}
	
	
	public boolean canDetermineVaporPressure(double tempK) {
		for (int i=0;i<chemicals.length;i++) {
			if (!chemicals[i].canDetermineVaporPressure(tempK)) {
				return false;
			}
		}
		return true;
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
	
	private double solveForXa2(double Xa1, double Xa2init,double tempK,double desiredFx,double smallFrac,double fracMin) {
		// check for change of sign of fx function (see if there's a root:

		//				System.out.println("");
		//				double [] concs={1e-8,1e-7,1e-6,1e-5,1e-4,1e-3,1e-2,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,0.95,0.99,0.999};
		//				for (int i=0;i<concs.length;i++) {
		//					double fx=this.calculateFxLLE_2(tempK, Xa1, concs[i]);
		//					System.out.println(concs[i]+"\t"+fx);
		//				}

		int totalIterations=0;
		//				int maxIterations=1000;

		//For initial guess use mol frac weighted average of pure component values:

		//				double small=1e-8;

		double frac=0.5;

		while (true) {

			double Xa2=Xa2init;//nearly pure component B

			int maxIterations=(int)(100/frac);

			for (int i = 0; i < maxIterations; i++) {

				totalIterations++;
				double fx=this.calculateFxLLE_2(tempK, Xa1, Xa2);
				double fx2= this.calculateFxLLE_2(tempK, Xa1, Xa2*(1-smallFrac));

				double fprime = (fx2 - fx) / (-Xa2*smallFrac); // estimate derivative using numerical method

				double dx = fx / fprime;

				double deltax= (frac*(Xa2-dx) + (1-frac)*Xa2)-Xa2; //only take partial step towards new value

				//						double deltax=0.001*fx;

				while (Xa2+deltax<0) {
					deltax/=2.0;
				}

				while (Xa2+deltax>1) {
					deltax/=2.0;
				}

				//						System.out.println("before XAa:"+i+"\t"+Xa2+"\t"+fx);

				Xa2+=deltax;

				//						System.out.println("after XAa:"+i+"\t"+Xa2+"\t"+fx);
				//						System.out.println(frac+"\t"+i+"\t"+Xa2+"\t"+fx);

				if (Xa2<0 || Xa2>1) break;

				if (Math.abs(Xa1-Xa2)<0.1) {
					//							System.out.println("trivial soln");
					break;//trivial solution!
				}


				//						if (Math.abs(deltax)/Xa2 < bob) {
				if (Math.abs(fx) < desiredFx) {
					//							System.out.println("After "+totalIterations+" iterations, Xa2="+Xa2);
					return Xa2;
				}

			}

			frac /= 10;//make step smaller
			//		          System.out.println(frac);
			if (frac<fracMin) break;

		}

		//				System.out.println(BPmix);
		return -9999;
	}


	private double solveForXa2_simple(double Xa1, double Xa2init,double tempK,double bob1,double bob2) {
		// check for change of sign of fx function (see if there's a root:

		//				System.out.println("");
		//				double [] concs={1e-8,1e-7,1e-6,1e-5,1e-4,1e-3,1e-2,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,0.95,0.99,0.999};
		//				for (int i=0;i<concs.length;i++) {
		//					double fx=this.calculateFxLLE_2(tempK, Xa1, concs[i]);
		//					System.out.println(concs[i]+"\t"+fx);
		//				}

		int totalIterations=0;


		//For initial guess use mol frac weighted average of pure component values:

		//				double frac=0.5;

		int maxIterations=(int)(10/bob2);

		double Xa2=Xa2init;//nearly pure component B

		double fxold=9999;

		for (int i = 0; i < maxIterations; i++) {

			totalIterations++;
			double fx=this.calculateFxLLE_2(tempK, Xa1, Xa2);

			//					if (fxold!=9999) {
			//						if (fx*fxold<0 && bob2>1e-5) {
			//							bob2/=2;//decrease bob2 to avoid bad oscillations
			//						}
			//					}
			//					fxold=fx;

			double deltax=bob2*fx;

			while (Xa2+deltax<0) {
				deltax/=2.0;
			}

			while (Xa2+deltax>1) {
				deltax/=2.0;
			}


			Xa2+=deltax;

			//					System.out.println("XA2:"+i+"\t"+bob2+"\t"+Xa2+"\t"+fx);
			//					System.out.println(frac+"\t"+i+"\t"+Xa2+"\t"+fx);

			if (Xa2<0 || Xa2>1) break;

			//						if (Math.abs(Xa1-Xa2)<0.1) break;//trivial solution!


			//						if (Math.abs(deltax)/Xa2 < bob) {
			if (Math.abs(fx) < bob1) {
				//							System.out.println("After "+totalIterations+" iterations, Xa2="+Xa2);
				return Xa2;
			}

		}


		//				System.out.println(BPmix);
		return -9999;
	}

	//			private double solveForXa2_Simple(double Xa1, double Xa2init,double tempK) {
	//
	//				Vector<Double>xmolI=new Vector<Double>();//compositions of phase I
	//				xmolI.add(Xa1);
	//				xmolI.add(1-Xa1);
	//				
	//				Vector<Double>xmolII=new Vector<Double>();//compositions of phase II
	//				xmolII.add(Xa2init);
	//				xmolII.add(1-Xa2init);
	//				
	//				double [] gammasI=unifac.calculateActivityCoefficients2(xmolI,chemicals, tempK);
	//				double [] gammasII=unifac.calculateActivityCoefficients2(xmolII,chemicals, tempK);
	//
	//				double Xa2=Xa1*gammasI[0]/(gammasII[0]);
	//				
	//				return Xa2;
	//			}

	/*
	 * Determines if mixture is one phase, already given mass fractions
	 */
	public boolean isOnePhase(double tempK) throws Exception {
		
		Vector<Double> xmol = this.calculateMoleFractions();
		
		return isOnePhase(xmol, tempK);

	}
	
	/*
	 * Determines if mixture is one phase
	 */
	public boolean isOnePhase(Vector<Double> xmol, double tempK) throws Exception {
		if (xmol.size()==1) { //one component so one liquid phase
			
			return true;
		
		} else if (xmol.size()==2) { // binary calculations
			
			return isMiscibleBinary(xmol, tempK);

		} else if (xmol.size()==3) { // ternary calculations
			 
			return isMiscibleTernary(xmol, tempK);

		} else if (xmol.size()<=4) { // test all combinations of binary mixtures
			
			Mixture mixtureTemp = new Mixture(tempK);
			Chemical[] chemicals = new Chemical[2];
			mixtureTemp.setChemicals(chemicals);
			Vector<Double> xmolTemp = new Vector<Double>(2);
			xmolTemp.add(xmol.get(0));
			xmolTemp.add(xmol.get(1));
			for (int j=0; j<xmol.size()-1; j++) {
				chemicals[0] = this.getChemicals2()[j];
				for (int i=j+1; i<xmol.size(); i++) {
					double totMolFrac = xmol.get(j)+xmol.get(i);
					xmolTemp.set(0, xmol.get(j)/totMolFrac);
					xmolTemp.set(1, xmol.get(i)/totMolFrac);
					chemicals[1] = this.getChemicals2()[i];
					if (!mixtureTemp.isMiscibleBinary(xmolTemp, tempK)) return false;
				}
			}
			return true;

		} else  {
			
			throw new Exception("Too many componets in solvent mixture");
			
		}


	}
			
	/**
	 * This calculates compositions of two liquid phases in equlibrium for a binary mixture
	 * 
	 * @param xmol
	 * @param Pressure_kPa
	 * @return compositions of component 1 in each phase- if no phase split then returns null
	 */
	public double [] calculateLLE(double tempK) {

		
//		double [] initialGuess=this.findInitialGuess(tempK);
//		
//		if (initialGuess==null) return null;
		
//		System.out.println(initialGuess[0]+"\t"+initialGuess[1]);
		
		long t1=System.currentTimeMillis();
		
		int totalIterations=0;
//		int maxIterations=1000;

		//For initial guess use mol frac weighted average of pure component values:
		
		double desiredFx=1e-4;
		double fracMax=0.5;
		double fracMin=1e-4;
		double smallFrac=1e-4;
		
		double frac=fracMax;
		
		while (true) {

			
//			double Xa1=initialGuess[0];//nearly pure component A
//			double Xa2=initialGuess[1];//nearly pure component B
			
			double Xa1=0.9999;//nearly pure component A
			double Xa2=0.0001;//nearly pure component B


			int maxIterations=(int)(100/frac);
			
//			System.out.println(frac);
			
			for (int i = 0; i < maxIterations; i++) {
				totalIterations++;
				
				Xa2=this.solveForXa2(Xa1,Xa2,tempK,desiredFx,smallFrac,fracMin);//need to iterate to determine change on Xa2
				if (Xa2==-9999) return null;//much quicker if just quit here instead of trying smaller fracs
//				if (Xa2==-9999) break;
				double fx = calculateFxLLE(tempK, Xa1, Xa2);
				
				double Xa1_bob=Xa1*(1-smallFrac);//perturb Xa1 slightly (needed to calculate derivative)
				
				
				double Xa2_bob=this.solveForXa2(Xa1_bob,Xa2,tempK,desiredFx,smallFrac,fracMin);//need to iterate to determine change on Xa2
				
//				System.out.println(Xa2_bob);
				double fx2 = calculateFxLLE(tempK, Xa1_bob, Xa2_bob);;//fx at Xa1-small
				
				double fprime = (fx2 - fx) / (-Xa1*smallFrac); // estimate derivative using numerical method

				double dx = fx / fprime;

				double deltax = (frac*(Xa1-dx) + (1-frac)*Xa1)-Xa1; //only take partial step towards new value

//				double deltax=bob*fx;//simpler way dont use derivative
				
				while (Xa1+deltax>=1) {
					deltax/=2.0;
				}
				
				while (Xa1+deltax<0) {
					deltax/=2.0;
				}
				
//				System.out.println(i+"\t"+Xa1+"\t"+Xa2+"\t"+fx);
				
//				if (deltax<1e-15 && fx>0.1) break;
				
				Xa1+=deltax;	

				
//				System.out.println(deltax);
				
//				System.out.println(Xa1+"\t"+Xa2);
//				System.out.println(i+"\t"+Xa1+"\t"+Xa2+"\t"+fx);
//				System.out.println(frac+"\t"+i+"\t"+Xa1+"\t"+Xa2+"\t"+fx);
				
				if (Math.abs(Xa1-Xa2)<0.1) {
//					System.out.println("here1");
					break;//trival solution
				}

				if (new Double (Xa1).isNaN()) {
//					System.out.println("here2");
					break;
				}
				
				if (Xa1<0 || Xa1>1) {
//					System.out.println("here3");
					break;
				}

//				if (Math.abs(deltax)/Xa1 < 0.0001) {
				if (Math.abs(fx) < desiredFx) {
//					 System.out.println("Converged after "+totalIterations+" iterations:"+Xa2);

					long t2=System.currentTimeMillis();
//					System.out.println((t2-t1)+" millisecs");
					
					double []molfracs=new double [2];
					
					if (Xa2>Xa1) {
//						System.out.println("*Xa2>Xa1");
						molfracs[0]=Xa2;
						molfracs[1]=Xa1;
						return molfracs;
						
					} else {
						molfracs[0]=Xa1;
						molfracs[1]=Xa2;
//						System.out.println(Xa1+"\t"+Xa2);
						return molfracs;
					}
					
					
				}

			}
			frac /= 10.0;//make step smaller
//          System.out.println(frac);
			if (frac<fracMin) break;

		}
		
//		 System.out.println("Did not converge after "+totalIterations+" iterations");

//		System.out.println(BPmix);
		return null;
	}
	
	
	
//	public double [] calculateLLE_3(double tempK) {
//		
//		double bob2=1e-2;
//		
//		
//		//try 3 different parameters until converges:
//		for (int i=1;i<=3;i++) {
//			
////			System.out.println("bob2:"+bob2);
//			double []vals=calculateLLE_2(tempK,bob2);
//			
//			if (vals==null) {
//				bob2/=10;
//			} else {
//				return vals;
//			}
//		}
//		
//		return null;
//		
//	}
	
//	public double [] calculateLLE_2(double tempK) {
//		return calculateLLE_2(tempK,1e-3);
//	}
//	
//	/**
//	 * This method iterates by Xn+1=Xn + K*f(x)
//	 * K determines how agressive the search is
//	 * 
//	 * @param tempK
//	 * @param bob2
//	 * @return
//	 */
//	public double [] calculateLLE_2(double tempK,double K) {
//
//		long t1=System.currentTimeMillis();
//
//		int totalIterations=0;
//
//		//For initial guess use mol frac weighted average of pure component values:
//
//		double bob1=1e-2;//used to decide if fx is near zero
////		double bob2=1e-3;
//
//		int maxIterations=(int)(10/K);
//
//		double Xa1=0.9999;//nearly pure component A
//		double Xa2=0.0001;//nearly pure component B
//
//		double fxold=9999;
//		
//		
//		for (int i = 0; i < maxIterations; i++) {
//			totalIterations++;
//
//			//				Xa2=this.solveForXa2(Xa1,Xa2,tempK);//need to iterate to determine change on Xa2
//			Xa2=this.solveForXa2_simple(Xa1,Xa2,tempK,bob1,K);//need to iterate to determine change on Xa2
//
//			if (Xa2==-9999) {
////				System.out.println("return null!");
//				return null;
//			}
//			double fx = calculateFxLLE(tempK, Xa1, Xa2);
//			
////			if (fxold!=9999) {
////				if (fx*fxold<0 && bob2>1e-5) {
////					bob2/=2;//decrease bob2 to avoid bad oscillations
////				}
////			}
////			fxold=fx;
//
//			double deltax=K*fx;//simpler way dont use derivative
//
//			while (Xa1+deltax>=1) {
//				deltax/=2.0;
//			}
//
//			while (Xa1+deltax<0) {
//				deltax/=2.0;
//			}
//
//			Xa1+=deltax;	
//
////							System.out.println(Xa1+"\t"+Xa2);
////			System.out.println(i+"\t"+bob2+"\t"+Xa1+"\t"+Xa2+"\t"+fx);
//			//				System.out.println(frac+"\t"+i+"\t"+Xa1+"\t"+Xa2+"\t"+fx);
//
//			if (Math.abs(Xa1-Xa2)<0.2) {
//				//					System.out.println("here1");
//				return null;//trival solution
//			}
//
//			if (new Double (Xa1).isNaN()) {
//				//					System.out.println("here2");
//				break;
//			}
//
//			if (Xa1<0 || Xa1>1) {
//				//					System.out.println("here3");
//				break;
//			}
//
//			//				if (Math.abs(deltax)/Xa1 < 0.0001) {
//			if (Math.abs(fx) < bob1) {
//				//					 System.out.println("Converged after "+totalIterations+" iterations:"+Xa2);
//
//				long t2=System.currentTimeMillis();
//				//					System.out.println((t2-t1)+" millisecs");
//
//				double []molfracs=new double [2];
//
//				if (Xa2>Xa1) {
//					System.out.println("*Xa2>Xa1");
//					molfracs[0]=Xa2;
//					molfracs[1]=Xa1;
//					return molfracs;
//
//				} else {
//					molfracs[0]=Xa1;
//					molfracs[1]=Xa2;
//					//						System.out.println(Xa1+"\t"+Xa2);
//					return molfracs;
//				}
//
//
//			}
//
//		}
//		//		 System.out.println("Did not converge after "+totalIterations+" iterations");
//
//		//		System.out.println(BPmix);
//		return null;
//	}
	/**
	 * This calculates compositions of two liquid phases in equilibrium 
	 * for a binary mixture
	 * 
	 * This calculation assumes that phase 1 is pure component 1
	 * 
	 * @param xmol
	 * @param Pressure_kPa
	 * @return composition of component 1 in phase that is mostly component 2
	 */
	public double calculateLLE_simple(double tempK) {

		long t1=System.currentTimeMillis();
		
		double Xa1=1;//phase 1 is pure component A
//		double Xa2=0.00001;//nearly pure component B
		double Xa2=0.0001;//nearly pure component B

		double desiredFx=0.001;
		double frac=0.5;
		
		Xa2=this.solveForXa2(Xa1,Xa2,tempK,desiredFx,1e-4,1e-4);
//		this.bruteForceSolveForXa2(Xa1, tempK);
		
		if (Math.abs(1-Xa2)<0.0001) Xa2=-9999;//we hit trivial solution

		long t2=System.currentTimeMillis();
//		System.out.println(Xa1+"\t"+Xa2);
//		System.out.println((t2-t1)+" millisecs");
		
		
		
		return Xa2;
	}
	
	/**
	 * Method incomplete but can be used to determine if fx changes signs over a certain concentration range
	 * @param Xa1
	 * @param tempK
	 */
	void bruteForceSolveForXa2(double Xa1,double tempK) {
		
		for (double Xa2=0.00001;Xa2<=0.01;Xa2+=0.00001) {
//		for (double Xa2=0.001;Xa2<=0.9;Xa2+=0.001) {
			double fx=this.calculateFxLLE_2(tempK, Xa1, Xa2);
			System.out.println(Xa2+"\t"+fx);
		}
		
		
	}
	

	private double calculateFxLLE(double tempK, double Xa1, double Xa2) {
		
		double Xb1=1-Xa1;
		double Xb2=1-Xa2;

		Vector<Double>xmolI=new Vector<Double>();//compositions of phase I
		xmolI.add(Xa1);
		xmolI.add(Xb1);
		
		Vector<Double>xmolII=new Vector<Double>();//compositions of phase II
		xmolII.add(Xa2);
		xmolII.add(Xb2);
		
		double [] gammasI=unifac.calculateActivityCoefficients2(xmolI,chemicals, tempK);
		double [] gammasII=unifac.calculateActivityCoefficients2(xmolII,chemicals, tempK);
		
		double termA=Xa1*gammasI[0];
		double termB=Xa2*gammasII[0];
		double termC=Xb1*gammasI[1];
		double termD=Xb2*gammasII[1];
		
//		double fx = termA+termC-(termB+termD);
		double fx = Math.abs(termA-termB)+Math.abs(termC-termD);
		return fx;
	}
	
	private double calculateFxLLE_2(double tempK, double Xa1, double Xa2) {
		
		double Xb1=1-Xa1;
		double Xb2=1-Xa2;

		Vector<Double>xmolI=new Vector<Double>();//compositions of phase I
		xmolI.add(Xa1);
		xmolI.add(Xb1);
		
		Vector<Double>xmolII=new Vector<Double>();//compositions of phase II
		xmolII.add(Xa2);
		xmolII.add(Xb2);
		
		double [] gammasI=unifac.calculateActivityCoefficients2(xmolI,chemicals, tempK);
		double [] gammasII=unifac.calculateActivityCoefficients2(xmolII,chemicals, tempK);
		
		double termA=Xa1*gammasI[0];
		double termB=Xa2*gammasII[0];
		
		double fx = termA-termB;
		
		return fx;
	}
	
	private boolean isMiscibleBinary(Vector<Double> xmol, double tempK) {
		
		double x1a, x2a, x1b, x2b;
		double[] gammaI = new double[2];
		double[] gammaII = new double[2];
		double x1bnew, x2bnew, x1anew, x2anew;
		double molDelta, deltaMax = 1.0e-5;
		int iter = 0, maxIter = 30;
		double k1, k2, sum;
		
		// start off with components separated, if they mix together then they are miscible
		x1a = 0.9999;
		x2a = 0.0001;
		x2b = x1a;
		x1b = x2a;

//		System.out.println(xmol.get(0)+", "+xmol.get(1)+", "+x1a+", "+x2a);
		
		do {
			
			Vector<Double>xmolI=new Vector<Double>();//compositions of phase I
			xmolI.add(x1a);
			xmolI.add(x2a); 
			
			Vector<Double>xmolII=new Vector<Double>();//compositions of phase II
			xmolII.add(x1b);
			xmolII.add(x2b);

			gammaI = unifac.calculateActivityCoefficients2(xmolI, chemicals, tempK);
			gammaII = unifac.calculateActivityCoefficients2(xmolII, chemicals, tempK);
			
			k1 = gammaI[0]/gammaII[0];
			k2 = gammaI[1]/gammaII[1];

			x1anew = (1-k2)/(k1-k2);
			x2anew = 1-x1anew;
			
			x1bnew = k1*x1anew;
			x2bnew = k2*x2anew;

			sum = x1bnew + x2bnew;
			x1bnew = x1bnew/sum;
			x2bnew = x2bnew/sum;
			
			iter++;
			molDelta = Math.abs(x1anew-x1a)+Math.abs(x2anew-x2a)+Math.abs(x1bnew-x1b)+Math.abs(x2bnew-x2b);
//			System.out.println(x1anew+", "+x2anew+", "+x1bnew+", "+x2bnew+": "+iter+", "+molDelta);
			
			x1a = x1anew;
			x2a = x2anew;
			x1b = x1bnew;
			x2b = x2bnew;

		} while (molDelta > deltaMax && iter <= maxIter);
		
		double x1 = xmol.get(0);
		if (x1a > x1b) {
			if (x1 < x1a && x1 > x1b) return false;
		} else {
			if (x1 < x1b && x1 > x1a) return false;
		}
		return true;
		
	}
	
	private boolean isMiscibleTernary(Vector<Double> xmolIn, double tempK) {
		
		double xtemp, x1a, x2a, x3a, x1b, x2b, x3b;
		double[] gammaI = new double[2];
		double[] gammaII = new double[2];
		double x1bnew, x2bnew, x3bnew, x1anew, x2anew, x3anew;
		double molDelta, deltaMax = 1.0e-4;
		int iter, maxIter = 60;
		double k1, k2, k3, sum;
		Chemical[] chemHold = new Chemical[3];
		Chemical chemTemp = null;
		double[] xmolHold = new double[3];
		
		chemHold[0] = chemicals[0];
		chemHold[1] = chemicals[1];
		chemHold[2] = chemicals[2];
		xmolHold[0] = xmolIn.get(0);
		xmolHold[1] = xmolIn.get(1);
		xmolHold[2] = xmolIn.get(2);

		// order chemical by largest mol fraction first, etc..
		if (xmolHold[1]>xmolHold[0]) {
			chemTemp = chemHold[0];
			chemHold[0] = chemHold[1];
			chemHold[1] = chemTemp;
			xtemp = xmolHold[0];
			xmolHold[0] = xmolHold[1];
			xmolHold[1] = xtemp;
		}
		if (xmolHold[2]>xmolHold[1]) {
			chemTemp = chemHold[1];
			chemHold[1] = chemHold[2];
			chemHold[2] = chemTemp;
			xtemp = xmolHold[1];
			xmolHold[1] = xmolHold[2];
			xmolHold[2] = xtemp;
		}
		if (xmolHold[1]>xmolHold[0]) {
			chemTemp = chemHold[0];
			chemHold[0] = chemHold[1];
			chemHold[1] = chemTemp;
			xtemp = xmolHold[0];
			xmolHold[0] = xmolHold[1];
			xmolHold[1] = xtemp;
		}
//		System.out.println(chemHold[0].getName()+": "+xmolHold[0]+"; "+
//				chemHold[1].getName()+": "+xmolHold[1]+"; "+
//				chemHold[2].getName()+": "+xmolHold[2]);
		
		// composition of Phase 1
		double f = xmolHold[2]/xmolHold[1];
		x1a = 0.9999;
		x2a = (1.0-x1a)/(1+f);
//		x3a = x2a*f;
		x3a = 1.0-x1a-x2a;
		
//		sum = x1a + x2a + x3a;
//		x1a = x1a/sum;
//		x2a = x2a/sum;
//		x3a = x3a/sum;
		
		// composition of Phase 2
		x1b = .0001;
		x2b = (1.0-x1b)/(1+f);
		x3b = 1.0-x1b-x2b;
		
//		sum = x1b + x2b + x3b;
//		x1b = x1b/sum;
//		x2b = x2b/sum;
//		x3b = x3b/sum;
		
//		System.out.println(x1a+", "+x2a+", "+x3a+", "+x1b+", "+x2b+", "+x3b);
		iter = 0;
		do {
			
			Vector<Double> xmolI=new Vector<Double>();//compositions of phase I
			xmolI.add(x1a);
			xmolI.add(x2a);
			xmolI.add(x3a);
			gammaI = unifac.calculateActivityCoefficients2(xmolI, chemHold, tempK);
			
			Vector<Double> xmolII=new Vector<Double>();//compositions of phase II
			xmolII.add(x1b);
			xmolII.add(x2b);
			xmolII.add(x3b);
			gammaII = unifac.calculateActivityCoefficients2(xmolII, chemHold, tempK);
			
			k1 = gammaI[0]/gammaII[0];
			k2 = gammaI[1]/gammaII[1];
			k3 = gammaI[2]/gammaII[2];
			
			x1anew = (1-x3a*(k3-k2)-k2)/(k1-k2);
			x2anew = (1-x1anew)/(1+f);
//			x3anew = x2anew*f;
			x3anew = 1.0-x1anew-x2anew;
			
//			sum = x1anew + x2anew + x3anew;
//			x1anew = x1anew/sum;
//			x2anew = x2anew/sum;
//			x3anew = x3anew/sum;
			
			x1bnew = k1*x1anew;
			x2bnew = k2*x2anew;
			x3bnew = k3*x3anew;

			sum = x1bnew + x2bnew + x3bnew;
			x1bnew = x1bnew/sum;
			x2bnew = x2bnew/sum;
			x3bnew = x3bnew/sum;
			
			iter++;
			molDelta = Math.abs(x1anew-x1a)+Math.abs(x2anew-x2a)+Math.abs(x3anew-x3a)+Math.abs(x1bnew-x1b)+Math.abs(x2bnew-x2b)+Math.abs(x3bnew-x3b);
//			System.out.println(x1anew+", "+x2anew+", "+x3anew+", "+x1bnew+", "+x2bnew+", "+x3bnew+": "+iter+", "+molDelta);

			x1a = x1anew;
			x2a = x2anew;
			x3a = x3anew;
			
			x1b = x1bnew;
			x2b = x2bnew;
			x3b = x3bnew;

		} while (molDelta > deltaMax && iter <= maxIter);

//		System.out.println(x1a+", "+x2a+", "+x3a+", "+x1b+", "+x2b+", "+x3b);
		
		double x1 = xmolHold[0];
		double x2 = xmolHold[1];
		double x3 = xmolHold[2];		
		if (x1a > x1b) {
			if (x1 < x1a && x1 > x1b) {
				if (x2b > x3b) {
					if (x2 > x2a && x2 < x2b) return false;
				} else {
					if (x3 > x3a && x3 < x3b) return false;
				}
			}
		} else {
			if (x1 < x1b && x1 > x1a) {
				if (x2b > x3b) {
					if (x2 > x2a && x2 < x2b) return false;
				} else {
					if (x3 > x3a && x3 < x3b) return false;
				}
			}
		}
		return true;
		
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
			double Psati=chemicals[i].determineVaporPressure(BPmixK);
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
			double Psati=chemicals[i].determineVaporPressure(tempK);
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
			
			double Psat=chemicals[i].determineVaporPressure(tempK);
			double PsatFP=chemicals[i].determineVaporPressure(FPi);
			
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
	
	public boolean isContained(Chemicals chemicals) {
		
		Chemical[] mChemicals = this.chemicals;
		for (int i=0; i<mChemicals.length; i++) {
			if (!mChemicals[i].isContained(chemicals)) return false;
		}
		return true;
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

	public double getTempK() {
		return tempK;
	}

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
