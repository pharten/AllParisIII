package unifac;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.lang.Math;

import parisWork.Chemical;

//This program uses the UNIFAC method and groups proposed by
//Gani's group without any 2nd order corrections.
public class UNIFACforPARIS {
//	Static Characters
	static int numMainGroups = 106;
	static int maxNumComp = 10;//Maximum number of components
	static int maxNumSubGroups = 219;
	static double tref = 298.15000000;
	
	private static UNIFACforPARIS ufp=null;
	private UnifacData ud;
	
	private UNIFACforPARIS() {//TMM
		ud=new UnifacData();//load unifac data
	}
	
	public static UNIFACforPARIS getInstance(){
		if (ufp==null) {
			ufp=new UNIFACforPARIS();
		}
		return ufp;
	}
	
	public void runUNIFACInfDil (Vector<String> prevSolvents, double temp) {
//		this.readInputInfo();//TMM
		Vector<String>solutes=this.setupSoluteList();
		
//		Define parameters
		String outputfile = "UnifacInfDilCalcs.txt";
		
//		Setup Infinite Dilute Calculations for each solute
		try { 
			FileWriter fw = new FileWriter(outputfile);
//			for (int i = 0; i < 1; i++) {
			for (int  i = 0; i < solutes.size(); i++) {
//				for (int j = 3; j < 4; j++) {
				for (int j = 0; j < ud.allCAS.size(); j++) {
					
					Vector<String>compounds=new Vector<String>(); 
					compounds.add(solutes.get(i));
					
					Vector <Double>molefracs=new Vector<Double>();
					
					molefracs.add(0.00001);
					
					boolean b = this.setupInfDilCalcs(prevSolvents, compounds,molefracs,j);
					
					if (!b) continue;
					int numSubGroups [][] = this.prepareSubGroupMatrix(compounds);
					double gamma [] = this.unifacCalcs(compounds,molefracs,numSubGroups, temp);

//					Write results
					this.writeResults(fw, gamma,compounds);

//					Clear Vectors
					compounds.clear();
					molefracs.clear();
				}
			}	
			fw.close();		
		} catch (Exception e){
			e.printStackTrace();
		}		
	}
	
	/**
	 * Calculates 10 infinite dilution activity coefficients for all 
	 * chemicals in list 
	 * @author tmarti02
	 * @param tempK - temperature in K
	 */
	public void runUNIFACInfDilutionCalculations (double tempK) {
//		this.readInputInfo();//TMM
		Vector<String> solutes=this.setupSoluteList();
		
		Vector<String>soluteNames=new Vector<String>();
		soluteNames.add("ethanol");//64-17-5
		soluteNames.add("acetone");//67-64-1
		soluteNames.add("diethyl_ether");//60-29-7
		soluteNames.add("water");//7732-18-5
		soluteNames.add("benzene");//71-43-2
		soluteNames.add("cis_2_heptene");//6443-92-1
		soluteNames.add("n_propyl_chloride");//540-54-5
		soluteNames.add("n_heptadecane");//629-78-7
		soluteNames.add("n_propylamine");//107-10-8
		soluteNames.add("dimethyl disulfide");//624-92-0
		
		
//		Define parameters
		String outputfile = "UnifacInfDilCalcs.txt";
		
		DecimalFormat df=new DecimalFormat("0.0000");
		
//		Setup Infinite Dilute Calculations for each solute
		try { 
			FileWriter fw = new FileWriter(outputfile);

			double molFracSolute=0.00001;
			
			fw.write("CAS\t");
			
			for (int i =0;i<soluteNames.size();i++) {
				fw.write("g_"+soluteNames.get(i));
				if (i<soluteNames.size()-1) fw.write("\t");
			}
			fw.write("\r\n");
			
			for (int i = 0; i < ud.allCAS.size(); i++) {
				String CASsolventi=ud.allCAS.get(i);
				fw.write(CASsolventi+"\t");
				
				System.out.println(CASsolventi);
				
				for (int  j = 0; j < solutes.size(); j++) {
					
					String CASsolutej=solutes.get(j);
					
					if (CASsolventi.equals(CASsolutej)) {
						fw.write(df.format(1));
						if (j<solutes.size()-1) fw.write("\t");
						continue;
					}

					Vector<String>compounds=new Vector<String>();
					Vector<Double>molefracs=new Vector<Double>();
					molefracs.clear();
					
					compounds.add(CASsolutej);
					molefracs.add(molFracSolute);
					
					compounds.add(CASsolventi);
					molefracs.add(1-molFracSolute);
					
//					System.out.println(CASsolventi+"\t"+CASsolutej);
					
					int numSubGroups [][] = this.prepareSubGroupMatrix(compounds);
					double gamma [] = this.unifacCalcs(compounds,molefracs,numSubGroups, tempK);

//					Write results
					fw.write(df.format(gamma[0]));
					if (j<solutes.size()-1) fw.write("\t");

				}//end j loop over solutes
				fw.write("\r\n");
				fw.flush();
			}//end i loop over solvents	
			fw.flush();
			fw.close();		
		} catch (Exception e){
			e.printStackTrace();
		}		
	}
	
	/**
	 * Calculates infinite dilution activity coefficients
	 * 
	 * @param soluteCASNumber
	 * @param solventCASNumbers
	 * @param molFractionSolvent
	 * @param temp
	 * @return
	 */
	public double calculateInfDilActCoef (String soluteCASNumber,Vector<String> solventCASNumbers, Vector<Double> molFractionSolvent, double tempK) {
//		this.readInputInfo();//TMM- done in class constructor
		
		Vector<String>compounds=new Vector<String>();
		compounds.add(soluteCASNumber);
		
		Vector<Double>molefracs=new Vector<Double>();
		molefracs.add(0.00001);//mol frac solute
		
		double fraction = 0.99999;//overall mol fraction of solvent
		
		for (int i=0;i<solventCASNumbers.size();i++) {
			compounds.add(solventCASNumbers.get(i));
			double adjustedMolFraction=molFractionSolvent.get(i)*fraction;
			molefracs.add(adjustedMolFraction);
		}
		int numSubGroups [][] = this.prepareSubGroupMatrix(compounds);
		double gamma [] = this.unifacCalcs(compounds,molefracs,numSubGroups, tempK);
		
//		for (int i =0;i<compounds.size();i++) {
//			System.out.println(i+"\t"+compounds.get(i)+"\t"+molefracs.get(i)+"\t"+gamma[i]);
//		}
		
		return gamma[0];
	}
	
	public void runUNIFACStdCalcs (Vector<String> compounds, Vector<Double> molefracs, double temp) {
//		This method expects comps to be listed by CAS number
//		this.readInputInfo();
		
//		Define parameters
		String outputfile = "UnifacStdCalcs.txt";
		
		try { 
			FileWriter fw = new FileWriter(outputfile);
			
			int numSubGroups [][] = this.prepareSubGroupMatrix(compounds);
			double gamma [] = this.unifacCalcs(compounds,molefracs,numSubGroups, temp);
			
//			Write results
			this.writeResults(fw, gamma,compounds);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		
	}
	
	/**
	 * Calculates activity coefficients
	 * TMM
	 * 
	 * @param comps
	 * @param x
	 * @param temp in K
	 * @return
	 */
	public double [] calculateActivityCoefficients (Vector<Double> molefracs,Vector<String> compounds, double tempK) {
//		This method expects comps to be listed by CAS number
		int numSubGroups [][] = this.prepareSubGroupMatrix(compounds);
		double gamma [] = this.unifacCalcs(compounds,molefracs,numSubGroups, tempK);
		return gamma;
	}

	/**
	 * This method calculates activity coefficients but allows you to just 
	 * send vector of mole fractions and chemicals (instead of vector of cas numbers)
	 * 
	 * @param x
	 * @param chemicals
	 * @param tempK
	 * @return
	 */
	public double [] calculateActivityCoefficients2 (Vector<Double> molefracs, Vector<Chemical> chemicals,double tempK) {
		Vector<String>compounds=new Vector<String>();
		for (int i=0;i<chemicals.size();i++) {
			compounds.add(chemicals.get(i).getCAS());
		}
		return calculateActivityCoefficients (molefracs,compounds, tempK);
	}
	
	public double [] calculateActivityCoefficients2 (Vector<Double> molefracs, Chemical[] chemicals,double tempK) {
		Vector<String>compounds=new Vector<String>();
		for (int i=0;i<chemicals.length;i++) {
			compounds.add(chemicals[i].getCAS());
		}
		return calculateActivityCoefficients (molefracs,compounds, tempK);
	}
	
	public int getMissingInteractionParameterCount(Chemical[] chemicals) {

		Vector<String>compounds=new Vector<String>();
		for (int i=0;i<chemicals.length;i++) {
			compounds.add(chemicals[i].getCAS());
		}
		int numSubGroups [][] = this.prepareSubGroupMatrix(compounds);
		Vector<Integer> activeSubGroup = new Vector<Integer> ();
		Vector<Integer> activeMainGroup = new Vector<Integer> ();
		
		int numComps=compounds.size();
		
		UNIFACGroupCodesforPARIS ugc = new UNIFACGroupCodesforPARIS();
		
		
//		Initialize summations and Calculate molecular information ri & qi
		for (int i = 0; i < numComps; i++) {
			for (int j = 0; j < maxNumSubGroups; j++) {
				if (numSubGroups[i][j] == 0) continue;
				activeSubGroup.add(j);
			}
		}
		
//		Remove duplicates and re-order groups
		activeSubGroup = this.findDuplicateGroups(activeSubGroup);
		activeSubGroup = this.orderGroups(activeSubGroup);
		activeMainGroup = ugc.mainGroupID(activeSubGroup);
		
//		Define tau values
//		deltau = aij + bij*(temp - tref)
//		tau = exp(-deltau/temp)
		
		
		int missingCount=0;
		for (int i = 0; i < activeMainGroup.size(); i++) {
			for (int j = 0; j < activeMainGroup.size(); j++) {
				
				if (ud.ipa[activeMainGroup.get(i)][activeMainGroup.get(j)]==0) {
					missingCount++;
				}
			}
		}
		return missingCount;
	}
	
	private void writeResults (FileWriter fw, double gamma [],Vector<String>compounds) throws Exception {
		DecimalFormat df = new DecimalFormat ("##0.00000");
		for (int k = 0; k < compounds.size(); k++) {
			fw.write(compounds.get(k) + "  " + df.format(gamma[k]) + "\r\n");
		}
		fw.flush();
	}
	
	private Vector<String> setupSoluteList () {
		Vector<String> solutes = new Vector<String> ();
		solutes.add("64-17-5");//Ethanol
		solutes.add("67-64-1");//Acetone
		solutes.add("60-29-7");//diethyl_ether
		solutes.add("7732-18-5");//water
		solutes.add("71-43-2");//benzene
		solutes.add("6443-92-1");//cis_2_heptene
		solutes.add("540-54-5");//n_propyl_chloride
		solutes.add("629-78-7");//n_heptadecane
		solutes.add("107-10-8");//n_propylamine
		solutes.add("624-92-0");//dimethyl_sulfide
		return solutes;
	}
	
	
	
	private boolean setupInfDilCalcs(Vector<String> prevSolvents, Vector<String>compounds,Vector<Double>molefracs,int solventNumber) {	
//		Set combinations of compounds
//		Calculate mole fraction for each of the solvents
		double fraction = 0.99999;
		if (prevSolvents != null) {
			fraction = 0.99999/((double)prevSolvents.size()+1.);
		}
		if (prevSolvents != null) {
			if (this.isSolventDuplicated(prevSolvents, ud.allCAS.get(solventNumber))) return false;
			for (int p = 0; p < prevSolvents.size(); p++) {
				compounds.add(prevSolvents.get(p));				
				molefracs.add(fraction);
			}
		}
		
		compounds.add(ud.allCAS.get(solventNumber));
		molefracs.add(fraction);

		return true;
	}
	
	private int [][] initMatrix (int comps, int groups) {
		int mat [][] = new int [comps][groups];
		for (int i = 0; i < comps; i++) {
			for (int j = 0; j < groups; j++) {
				mat[i][j] = 0;
			}
		}
		return mat;
	}
	
	
	public int[][] prepareSubGroupMatrix (Vector<String> compounds) {
		UNIFACGroupCodesforPARIS ugc = new UNIFACGroupCodesforPARIS();
		
//		Initialize sub group matrix [comps][sub groups+1]- TMM
		int numSubGroups [][] = this.initMatrix(compounds.size(), maxNumSubGroups+1);
		
//		Populate numSubGroup matrix
		for (int i = 0; i < compounds.size(); i++) {
			Hashtable<String, Integer> htTemp = new Hashtable<String, Integer> ();
			
			htTemp.putAll(ud.htChemInfo.get(compounds.get(i)));
			Enumeration <String> groupnames;
			groupnames = htTemp.keys();
			while (groupnames.hasMoreElements()) {
				String group = groupnames.nextElement();

				int groupnumber = ugc.subGroupIDs(group);
				int groupcount = htTemp.get(group);
				numSubGroups[i][groupnumber] = groupcount;
				
//				System.out.println(compounds.get(i)+"\t"+group+"\t"+groupnumber+"\t"+groupcount);

			}	
		}
		return numSubGroups;
	}
	
	
	
	private double [] unifacCalcs (Vector<String> compounds,Vector<Double> molefracs,int numSubGroups[][], double tempK) {
//		Define parameters used in the UNIFAC calculations
		
		Vector<Integer> activeSubGroup = new Vector<Integer> ();
		Vector<Integer> activeMainGroup = new Vector<Integer> ();
		
		int numComps=compounds.size();
		double r [] = new double[numComps];
		double q [] = new double[numComps];
		double v [] = new double[numComps];
		double f [] = new double[numComps];
		double vdenom = 0.0000000;
		double fdenom = 0.0000000;
		double gamma [] = new double [numComps];
		double lngammacomb [] = new double [numComps];
		double lngammares [] = new double [numComps];
		double lngammatotal [] = new double [numComps];
		
		UNIFACGroupCodesforPARIS ugc = new UNIFACGroupCodesforPARIS();
		
//		System.out.println(compounds);
//		System.out.println(molefracs);
		
//		Initialize summations and Calculate molecular information ri & qi
		for (int i = 0; i < numComps; i++) {
			r[i] = 0.000000;
			q[i] = 0.000000;
			lngammacomb[i] = 0.000000;
			lngammares[i] = 0.000000;
			for (int j = 0; j < maxNumSubGroups; j++) {
				if (numSubGroups[i][j] == 0) continue;
				r[i] = r[i] + numSubGroups[i][j]*ud.rgroup[j];
				q[i] = q[i] + numSubGroups[i][j]*ud.qgroup[j];
				activeSubGroup.add(j);
			}
		}
		
//		Calculate V and F
//		v[i] = r[i]/SUM(r[j]*x[j])
//		f[i] = q[i]/SUM(q[j]*x[j])
//		Calculate denominators of V and F		
		for (int i = 0; i < compounds.size(); i++) {
			vdenom = vdenom + r[i]*molefracs.get(i);
			fdenom = fdenom + q[i]*molefracs.get(i);
		}
		
		for (int j = 0; j < compounds.size(); j++) {
			v[j] = r[j]/vdenom;
			f[j] = q[j]/fdenom;
		}
		
//		Remove duplicates and re-order groups
		activeSubGroup = this.findDuplicateGroups(activeSubGroup);
		activeSubGroup = this.orderGroups(activeSubGroup);
		activeMainGroup = ugc.mainGroupID(activeSubGroup);
		
//		Calculate combinatorial portion of activity coefficient
		for (int i = 0; i < compounds.size(); i++) {
			lngammacomb[i] = 1.000000 - v[i] + Math.log(v[i])- 5*q[i]*(1-v[i]/f[i]+Math.log(v[i]/f[i]));
		}
		
//		System.out.println(lngammacomb[0]);
//		System.out.println(lngammacomb[1]);
		
//		***Calculate ln gammak for the solution***
//		Calculate total number of groups per compound
		int totNumGroups [] = new int[compounds.size()];
		for (int i = 0; i < compounds.size(); i++) {
			totNumGroups[i] = 0;
			for (int nsg = 0; nsg < activeSubGroup.size(); nsg++) {
				totNumGroups [i] += numSubGroups[i][activeSubGroup.get(nsg)];
			}
		}
		
//		Calculate mole fraction of each group in each molecule
		double xmolecule[][] = new double [compounds.size()][activeSubGroup.size()];
		for (int i = 0; i < compounds.size(); i++) {
			for (int nsg = 0; nsg < activeSubGroup.size(); nsg++) {
				xmolecule[i][nsg] = (double) numSubGroups[i][activeSubGroup.get(nsg)]/totNumGroups[i];
			}
		}
		
//		Calculate total number of each group in solution
		double totNumGroupsSoln [ ]= new double [activeSubGroup.size()];
		double totSubGroups = 0;
		for (int nsg = 0; nsg < activeSubGroup.size(); nsg ++) {
			totNumGroupsSoln [nsg] = 0.0000000;
			for (int i = 0; i < compounds.size(); i++) {
				totNumGroupsSoln [nsg] += numSubGroups[i][activeSubGroup.get(nsg)]*molefracs.get(i);
			}
			totSubGroups += totNumGroupsSoln[nsg];
		}
		
		
//		Calculate mole fraction of each group in solution
		double xsolution[] = new double [activeSubGroup.size()];
		for (int nsg = 0; nsg < activeSubGroup.size(); nsg++) xsolution[nsg] = (double) totNumGroupsSoln[nsg]/totSubGroups;
		
//		Calculate theta for solution
		double thetaSolution [] = new double [activeSubGroup.size()];
		double sum = this.calcThetaSum(xsolution,activeSubGroup);
		thetaSolution = this.calcTheta(xsolution, sum,activeSubGroup);

		
//		Calculate theta for molecule
		double thetaMolecule [][] = new double [compounds.size()][activeSubGroup.size()];
		for (int i = 0; i < compounds.size(); i++) {
			double xtemp [] = this.convertToSingleArray(xmolecule,i,activeSubGroup);
			sum = this.calcThetaSum(xtemp,activeSubGroup);
			double thetatemp [] = this.calcTheta(xtemp, sum,activeSubGroup);
			for (int nsg = 0; nsg <activeSubGroup.size(); nsg++) thetaMolecule[i][nsg] = thetatemp[nsg];
		}
		
//		Define tau values
//		deltau = aij + bij*(temp - tref)
//		tau = exp(-deltau/temp)
		double tau [][] = new double [activeMainGroup.size()][activeMainGroup.size()];
		for (int i = 0; i < activeMainGroup.size(); i++) {
			for (int j = 0; j < activeMainGroup.size(); j++) {
				double deltau = ud.ipa[activeMainGroup.get(i)][activeMainGroup.get(j)] + ud.ipb[activeMainGroup.get(i)][activeMainGroup.get(j)]*(tempK - tref);
				tau[i][j] = Math.exp(-deltau/tempK);
			}
		}

//		Calculate summations for solution
		double thetaTauSumSolution [] = new double [activeSubGroup.size()];
		double thetaTauLogSolution [] = new double [activeSubGroup.size()];
		for (int k = 0; k < activeSubGroup.size(); k++) {
			thetaTauSumSolution [k] = 0.00000;
			thetaTauLogSolution [k] = 0.00000;
			for (int m = 0; m < activeSubGroup.size(); m++) {
				double tempSum = 0.000000;
				for (int n = 0; n < activeSubGroup.size(); n++) {
					tempSum += thetaSolution[n]*tau[n][m];
				}
				thetaTauSumSolution [k] += thetaSolution[m]*tau[k][m]/tempSum;
				thetaTauLogSolution [k] += thetaSolution[m]*tau[m][k];
			}
		}
		

		
//		Calculate summation for each molecule
		double thetaTauSumMolecule [][] = new double [compounds.size()][activeSubGroup.size()];
		double thetaTauLogMolecule [][] = new double [compounds.size()][activeSubGroup.size()];
		for (int i = 0; i < compounds.size(); i++) {
			for (int k = 0; k < activeSubGroup.size(); k++) {
				thetaTauSumMolecule [i][k] = 0.000000;
				thetaTauLogMolecule [i][k] = 0.000000;
				for (int m = 0; m < activeSubGroup.size(); m++) {
					double tempSum = 0.00000;
					for (int n = 0; n < activeSubGroup.size(); n++) {
						tempSum += thetaMolecule[i][n]*tau[n][m];
					}
					thetaTauSumMolecule[i][k] += thetaMolecule[i][m]*tau[k][m]/tempSum;
					thetaTauLogMolecule[i][k] += thetaMolecule[i][m]*tau[m][k];
				}
			}
		}
		
//		Calculate lngammaksolution
		double lngammaksolution [] = new double [activeSubGroup.size()];
		for (int k = 0; k < activeSubGroup.size(); k++) {
			lngammaksolution[k] = ud.qgroup[activeSubGroup.get(k)]*(1. - Math.log(thetaTauLogSolution[k]) - thetaTauSumSolution[k]);
		}
		
//		Calculate lngammakmolecule for each group in each molecule
		double lngammakmolecule[][] =  new double [compounds.size()][activeSubGroup.size()];
		for (int i = 0; i < compounds.size(); i++) {
			for (int k = 0; k < activeSubGroup.size(); k++) {
				lngammakmolecule[i][k] = ud.qgroup[activeSubGroup.get(k)]*(1. - Math.log(thetaTauLogMolecule[i][k]) - thetaTauSumMolecule[i][k]);
			}
		}
		
//		****Calculate lngammares***
		for (int i = 0; i < compounds.size(); i++) {
			lngammares[i] = 0.0000000;
			for (int k = 0; k < activeSubGroup.size(); k++) {
				lngammares[i] += numSubGroups[i][activeSubGroup.get(k)]*(lngammaksolution[k]-lngammakmolecule[i][k]);
			}
		}
		
//		**********************
//		Calculate lngammatotal
//		**********************
		for (int i = 0; i < compounds.size(); i++) {
			lngammatotal[i] = lngammacomb[i]+lngammares[i];
			gamma[i] = Math.exp(lngammatotal[i]);
//			System.out.println("Gamma of " + compounds.get(i) + " " + gamma[i]);
		}
		
		return gamma;
	}
	
	private Vector<Integer> orderGroups (Vector<Integer> al) {
		Vector<Integer> updatedAl = new Vector<Integer> ();
		
		for (int i = 0; i < al.size(); i++) {
			int index = 0;
			for (int j = 1; j < al.size(); j++) {
				if (al.get(j).compareTo(al.get(index)) < 0) {
					index = j;
				}
			}
			updatedAl.add(al.get(index));
			al.remove(index);
			i--;
		}
		return updatedAl;
	}
	
	private Vector<Integer> findDuplicateGroups (Vector<Integer> al) {
		Vector<Integer> updatedAl = new Vector<Integer> ();
		
		updatedAl.add(al.get(0));
		for (int i = 1; i < al.size(); i++) {
			boolean isDuplicate = false;
			for (int j = 0; j < updatedAl.size(); j++) {
				if (al.get(i).equals(updatedAl.get(j))) {
					isDuplicate = true;      
				}
			}
			if (!isDuplicate) {
				updatedAl.add(al.get(i));
			} else {
				al.remove(i);
				i--;
			}
		}
		return updatedAl;
	}
	
	private boolean isSolventDuplicated (Vector<String>prevSolvents, String solvent) {
		boolean b = false;
		
		for (int i = 0; i < prevSolvents.size(); i++) {
			if (prevSolvents.get(i).equals(solvent)) {
				b = true;
				return b;
			}
		}
		
		return b;
	}
	
	
	
	private double [] calcTheta (double x[], double sum,Vector<Integer> activeSubGroup) {
		double value [] = new double [activeSubGroup.size()];
		for (int nsg = 0; nsg < activeSubGroup.size(); nsg++) {
			value [nsg] = x[nsg]*ud.qgroup[activeSubGroup.get(nsg)]/sum;
		}
		return value;
	}
	
	private double calcThetaSum (double x[],Vector<Integer> activeSubGroup) {
		double sum = 0.000000;
		for (int i = 0; i < activeSubGroup.size(); i++) {
			sum += x[i]*ud.qgroup[activeSubGroup.get(i)];
		}
		return sum;
	}
	
	private double [] convertToSingleArray (double x[][], int i,Vector<Integer> activeSubGroup) {
		double value [] = new double [activeSubGroup.size()];
		for (int nsg = 0; nsg < activeSubGroup.size(); nsg++) {
			value [nsg] = x[i][nsg];
		}	
		return value;
	}
	
	/**
	 * Calculates infinite dilution activity coefficients for a solute in a solvent mixture
	 * in attempt to match Krummen et al 2000 (Ind. Eng. Chem. Res. 2000, 39, 2114-2123)
	 * @author tmarti02
	 * 
	 */
	void testInfiniteDilutionActivityCoefficientsInSolventMixtures(UnifacData ud) {
//		String soluteCASNumber="110-54-3";//n-hexane
//		String soluteCASNumber="71-43-2";//benzene
		String soluteCASNumber="110-82-7";//cyclohexane
		
		Vector<String> solventCASNumbers=new Vector<String>();
		solventCASNumbers.add("7732-18-5");//water- MW=18
		solventCASNumbers.add("872-50-4");//NMP MW=99.13
		
		
		double x1=0.06;
		double x2=1-x1;
		double MW1=18.01;
		double MW2=99.13;
		double totalmoles=x1/MW1+x2/MW2;
		
		Vector<Double> molFractions=new Vector<Double>();
		molFractions.add(x1/MW1/totalmoles);
		molFractions.add(x2/MW2/totalmoles);

//		double tempK=30+273.15;
		for (double temp=30;temp<=60;temp+=10) {
			double g=calculateInfDilActCoef(soluteCASNumber, solventCASNumbers, molFractions, temp+273.15);
			System.out.print(g+"\t");
		}
	}
	
	
	public static void main(String[] args) {
		UNIFACforPARIS u=new UNIFACforPARIS();
		u.runUNIFACInfDilutionCalculations(298.15);
		
			
	}
	
	public double getQ (String CAS) {
			
			Vector<String>compounds = new Vector<String>();
			compounds.add(CAS);
			
			int numSubGroups [][] = this.prepareSubGroupMatrix(compounds);
			
	//		Define parameters used in the UNIFAC calculations
			double q = 0;
			
			for (int j = 0; j < maxNumSubGroups; j++) {
				if (numSubGroups[0][j] == 0) continue;
				q+=numSubGroups[0][j]*ud.qgroup[j];
			}
	
			return q;
	}

}
