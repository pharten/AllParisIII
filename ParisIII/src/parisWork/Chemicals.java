package parisWork;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Vector;

import parisInit.Units;

public class Chemicals extends Vector<Chemical> implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8690181550723531644L;

	public Chemicals() {
		super();
	}
	
	public Chemicals filterForLiquidPhase(double temperature) {
		Chemicals chemicals = new Chemicals();
		Chemical chemical;
		double tm, tb;

		for (int i=0; i<this.elementCount; i++) {
			chemical = this.get(i);
			tm = chemical.getMeltingPoint();
			tb = chemical.getBoilingPoint();
			if (tm <=temperature && temperature <= tb) {
				chemicals.add(chemical);
			}
		}
		return chemicals;
	}
	
	public Chemicals filterForVaporPressure() {
		Chemicals chemicals = new Chemicals();

		for (int i=0; i<this.elementCount; i++) {
			Chemical chemical = this.get(i);

			if (chemical.canCalculateVaporPressure()) {
				chemicals.add(chemical);
			}
		}
		
//		System.out.println(chemicals.size());
		return chemicals;
	}
	
	public void normalizeEnvironmentalCategories() {
		Chemical chemical;
		double[] average = new double[8];
		double[] count = new double[8];
		double[] value = new double[8];
		
		// zero out averages and counts
		for (int i=0; i<8; i++) {
			average[i] = 0.0;
			count[i] = 0.0;
		}

		// find the summation for each category
		for (int i=0; i<this.elementCount; i++) {
			chemical = this.get(i);
			value[0] = chemical.getAP();
			value[1] = chemical.getAquaticTox();
			value[2] = chemical.getGWP();
//			value[3] = chemical.getHtoxDermal();
			value[3] = chemical.getTerrestrialTox();
			value[4] = chemical.getHtoxIngestion();
			value[5] = chemical.getHtoxInhalation();
			value[6] = chemical.getODP();
			value[7] = chemical.getPCOP();
			String casnum = chemical.getCAS();
			for (int j=0; j<8; j++) {
				if (value[j] != 0.0) {
					if (j==5 && (casnum.contentEquals("107-30-2") || casnum.contentEquals("542-88-1"))) {
						// don't include these 1.0/TWA for non-zero averaging (TWA artificially small)
					} else {
						average[j] += value[j];
						count[j] += 1.0;
					}
				}
			}
		}
		
		// calculate average non-zero values
		for (int j=0; j<8; j++) {
			if (count[j] != 0.0) {
				average[j] /= count[j];
			}
		}
		
		// normalize environmental values by average value
		for (int i=0; i<this.elementCount; i++) {
			chemical = this.get(i);
			value[0] = chemical.getAP();
			value[1] = chemical.getAquaticTox();
			value[2] = chemical.getGWP();
//			value[3] = chemical.getHtoxDermal();
			value[3] = chemical.getTerrestrialTox();
			value[4] = chemical.getHtoxIngestion();
			value[5] = chemical.getHtoxInhalation();
			value[6] = chemical.getODP();
			value[7] = chemical.getPCOP();
			for (int j=0; j<8; j++) {
				if (average[j] != 0.0) {
					value[j] /= average[j];
				}
			}
			chemical.setAP(value[0]);
			chemical.setAquaticTox(value[1]);
			chemical.setGWP(value[2]);
//			chemical.setHtoxDermal(value[3]);
			chemical.setTerrestrialTox(value[3]);
			chemical.setHtoxIngestion(value[4]);
			chemical.setHtoxInhalation(value[5]);
			chemical.setODP(value[6]);
			chemical.setPCOP(value[7]);
		}
		
	}
	
	public void sortByName() {
		Chemical chemical;
		String name;
		int index = 0;
		for (int i=0; i<this.elementCount; i++) {
			chemical = this.get(i);
			if (chemical.getName().isEmpty()) {
				chemical.setName(chemical.getCAS());
			}
		}
		for (int i=0; i<this.elementCount; i++) {
			index = i;
			name = this.get(index).getName();
			for (int j=i+1; j<this.elementCount; j++) {
				if (name.compareToIgnoreCase(this.get(j).getName())>0) {
					index=j;
					name = this.get(index).getName();
				}
			}
			if (index!=i) {
				chemical = this.get(i);
				this.set(i, this.get(index));
				this.set(index, chemical);
			}
		}
	}
	
	public void sortByCAS() {
		Chemical chemical;
		String cas;
		int index = 0;

		for (int i=0; i<this.elementCount; i++) {
			index = i;
			cas = this.get(index).getCAS();
			for (int j=i+1; j<this.elementCount; j++) {
				if (cas.compareToIgnoreCase(this.get(j).getCAS())>0) {
					index=j;
					cas = this.get(index).getCAS();
				}
			}
			if (index!=i) {
				chemical = this.get(i);
				this.set(i, this.get(index));
				this.set(index, chemical);
			}
		}
	}
	
	public String[] getAllNames() {
		String[] names = new String[this.elementCount];
		for (int i=0; i<this.elementCount; i++) {
			names[i] = this.get(i).getName().trim();
		}
		return names;
	}
	
	public String[] getAllCAS() {
		String[] casNums = new String[this.elementCount];
		for (int i=0; i<this.elementCount; i++) {
			casNums[i] = this.get(i).getCAS().trim();
		}
		return casNums;
	}
	
	public Chemical getByName(String name) {
		Chemical chemical = null;
		for (int i=0; i<this.elementCount; i++) {
			if (name.compareToIgnoreCase(this.get(i).getName())==0) {
				chemical = this.get(i);
//				System.out.println(name+" appeared for chemical "+chemical.getName());
				break;
			}
		}
		return chemical;
	}
	
	public Chemical getBySynonym(String name) {
		Chemical chemical = null;
		for (int i=0; i<this.elementCount; i++) {
			Vector<String> synonyms = this.get(i).getSynonyms();
			for (int j=0; j<synonyms.size(); j++) {
				if (name.compareToIgnoreCase(synonyms.get(j))==0) {
					chemical = this.get(i);
//					System.out.println(name+" appeared in list of synonyms for chemical "+chemical.getName());
					break;
				}
			}
			if (chemical!=null) break;
		}
		return chemical;
	}
	
	public Chemical getByCAS(String CAS) {
		Chemical chemical = null;
		for (int i=0; i<this.elementCount; i++) {
			if (CAS.compareTo(this.get(i).getCAS())==0) {
				chemical = this.get(i);
				break;
			}
		}
		return chemical;
	}

	public static Chemicals readFromFile(String filename) {
		
		Chemicals chemicals = null;
		try {
			if (filename.endsWith(".txt")) {
				chemicals = readByTxt2(filename);
			} else if (filename.endsWith(".xml")) {
				chemicals = readByXML(filename);
			} else {
				throw new Exception("Filename has unaccepted extension.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return chemicals;
	}
	

	private static Chemicals readByTxt(String fileName) throws IOException, Exception {
		String line, lineSplit[];
		int lineno = 0;
		Chemical chemical;
		Chemicals chemicals = new Chemicals();

		File file = new File(fileName);
//		System.out.println(file.getAbsolutePath());
		FileReader fileReader = new FileReader(file);
		BufferedReader buf = new BufferedReader(fileReader);
		
		line = buf.readLine();	// read the column header line
		lineno++;
		System.out.println("lineno = "+lineno);
		
		while ((line = buf.readLine()) != null) {
			lineno++;

			System.out.println("lineno = "+lineno);
			lineSplit = line.split("\t");
			
			chemical = new Chemical();
			chemical.setCAS(lineSplit[0].trim());
			
			if (!lineSplit[1].trim().isEmpty()) chemical.setMolecularWeight(Double.parseDouble(lineSplit[1]));	
			if (!lineSplit[2].trim().isEmpty()) chemical.setMeltingPoint(Double.parseDouble(lineSplit[2]));
			if (!lineSplit[3].trim().isEmpty()) chemical.setBoilingPoint(Double.parseDouble(lineSplit[3]));
			if (!lineSplit[4].trim().isEmpty()) chemical.setViscosity(Double.parseDouble(lineSplit[4]));
			if (!lineSplit[5].trim().isEmpty()) chemical.setSolubility(Double.parseDouble(lineSplit[5]));
			if (!lineSplit[6].trim().isEmpty()) chemical.setVaporPressure(Double.parseDouble(lineSplit[6]));
			if (!lineSplit[7].trim().isEmpty()) chemical.setThermalConductivity(Double.parseDouble(lineSplit[7]));
			if (!lineSplit[8].trim().isEmpty()) chemical.setSurfaceTension(Double.parseDouble(lineSplit[8]));
			if (!lineSplit[9].trim().isEmpty()) chemical.setFlashPoint(Double.parseDouble(lineSplit[9]));
			if (!lineSplit[10].trim().isEmpty()) chemical.setDensity(Double.parseDouble(lineSplit[10]));
//			if (!lineSplit[11].trim().isEmpty()) chemical.setOralRatLD50(Double.parseDouble(lineSplit[11]));
//			if (!lineSplit[12].trim().isEmpty()) chemical.setFatheadMinnowLC50(Double.parseDouble(lineSplit[12]));
			
			chemical.setMeltingPointSource(lineSplit[13].trim());
			chemical.setBoilingPointSource(lineSplit[14].trim());
			chemical.setSolubilitySource(lineSplit[15].trim());
			chemical.setViscositySource(lineSplit[16].trim());
			chemical.setVaporPressureSource(lineSplit[17].trim());
			chemical.setThermalConductivitySource(lineSplit[18].trim());
			chemical.setSurfaceTensionSource(lineSplit[19].trim());
			chemical.setFlashPointSource(lineSplit[20].trim());
			chemical.setDensitySource(lineSplit[21].trim());
//			chemical.setOralRatLD50Source(lineSplit[22].trim());
//			chemical.setFatheadMinnowLC50Source(lineSplit[23].trim());
			
			chemicals.add(chemical);
				
		}
		buf.close();
		
		return chemicals;
	}
	
//	static String getFieldValue(String fieldName,String [] fieldNames,String [] fieldValues) {
//		return fieldValues[getColumnNumber(fieldNames,fieldName)];
//	}
	
	static int getColumnNumber(String [] fieldnames,String name) {
		
		for (int i=0;i<fieldnames.length;i++) {
			if (fieldnames[i].equals(name)) return i;
		}
		return -1;
	}
	

	public static String [] Parse(String Line, String Delimiter) {
		// parses a delimited string into a list

		java.util.LinkedList<String> myList = new LinkedList<String>();

		int tabpos = 1;

		while (tabpos > -1) {
			tabpos = Line.indexOf(Delimiter);

			if (tabpos > 0) {
				myList.add(Line.substring(0, tabpos));
				Line = Line.substring(tabpos + Delimiter.length(), Line.length());
			} else if (tabpos == 0) {
				myList.add("");
				Line = Line.substring(tabpos + Delimiter.length(), Line.length());
			} else {
				myList.add(Line.trim());
			}
		}

		//Convert LinkedList to String array:
		String  []values=new String[myList.size()];

		for (int i=0;i<myList.size();i++) {
			values[i]=myList.get(i);
		}
		return values;

	}
	
//	/** parses a delimited string into a list- accounts for the fact that can have quotation marks in comma delimited lines
//	 * 
//	 * @param Line - line to be parsed
//	 * @param Delimiter - character used to separate line into fields
//	 * @return
//	 **/
//
//	public static String[] Parse3(String Line, String Delimiter) {
//
//		Vector<String> myList =  new Vector<String>();
//		
//		int tabpos = 1;
//
//		while (tabpos > -1) {
//
//			tabpos = Line.indexOf(Delimiter);
//
//			if (Line.length()<1) break;
//
//			if (Line.substring(0,1).equals("\"")) {
//				Line=Line.substring(1,Line.length()); // kill first " mark
//
//				if (Line.length()==0) break;
//
//				myList.add(Line.substring(0, Line.indexOf("\"")));
//
//				if (Line.indexOf("\"")<Line.length()-1)
//					Line = Line.substring(Line.indexOf("\"") + 2, Line.length());
//				else 
//					break;
//			} else {
//
//				if (tabpos > 0) {
//					myList.add(Line.substring(0, tabpos));
//					Line = Line.substring(tabpos + 1, Line.length());
//				} else if (tabpos == 0) {
//					myList.add("");
//					Line = Line.substring(tabpos + 1, Line.length());
//				} else {
//					myList.add(Line.trim());
//				}
//
//			}
//
//		} // end while loop
//
//		return myList.toArray(values);
//
//	}

	//used to simplify readByTxt2
	private static String [] fieldNames;//String of column headers in the text database
	private static String [] values;//String array of values from a line in the text database
	
	private static Hashtable <String,Integer>colNums=new Hashtable<String,Integer>();
	
	/**
	 * This method reads in chemical database in tab delimited text format
	 * This method uses the column header names to assign the chemical class values
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 * @throws Exception
	 * @author TMARTI02
	 * 
	 */
	public static Chemicals readByTxt2(String filePath) throws IOException, Exception {

//		filePath = ClassLoader.getSystemResource(filePath).getPath();

		String nameCAS="CAS";
//		String nameName="Systematic Name";
		String nameName="Chemical Name";
		String nameFormula="Formula";
		String nameSynonyms="Synonyms";
		String nameMolecularWeight="Molecular Weight in kg/kmol";

		//physical properties:
		String nameMeltingPoint="Melting Point in K";
		String nameBoilingPoint="Boiling Point in K";
		String nameViscosity="Viscosity in kg/m-s";
		//	String nameWaterSolubility="Water Solubility in kg/m3";
		String nameVaporPressure="Vapor Pressure in kPa";
		String nameThermalConductivity="Thermal Conductivity in J/(m-s-K)";
		String nameSurfaceTension="Surface Tension in kg/s2";
		String nameFlashPoint="Flash Point in K";
		String nameDensity="Density in kg/m3";


		String nameMeltingPointSource="Melting Point Source";
		String nameBoilingPointSource="Boiling Point Source";
		String nameViscositySource="Viscosity Source";
		//	String nameWaterSolubilitySource="Water Solubility Source";
		String nameVaporPressureSource="Vapor Pressure Source";
		String nameThermalConductivitySource="Thermal Conductivity Source";
		String nameSurfaceTensionSource="Surface Tension Source";
		String nameFlashPointSource="Flash Point Source";
		String nameDensitySource="Density Source";

		//WAR categories:
		String nameOralRatLD50="Oral Rat LD50 in mg/kg";
		String nameFatheadMinnowLC50="Fathead Minnow LC50 in mg/L";
		String nameOSHA_TWA="OSHA_TWA";
		//String nameHtoxDermal="Dermal Rabbit LD50 in mg/kg";
		String nameGWP="GWP";
		String namePCOP="PCO";
		String nameODP="OD";
		String nameAP="AP";

		//	String nameOralRatLD50Source="Oral Rat LD50 Source";
		//	String nameFatheadMinnowLC50Source="Fathead Minnow LC50 Source";

		//Unifac activity coefficients:
		String nameInfDilActCoef_ethanol="g_ethanol";
		String nameInfDilActCoef_acetone="g_acetone";
		String nameInfDilActCoef_diethyl_ether="g_diethyl_ether";
		String nameInfDilActCoef_water="g_water";
		String nameInfDilActCoef_benzene="g_benzene";	
		String nameInfDilActCoef_cis_2_heptene="g_cis_2_heptene";	
		String nameInfDilActCoef_n_propyl_chloride="g_n_propyl_chloride";
		String nameInfDilActCoef_n_heptadecane="g_n_heptadecane";
		String nameInfDilActCoef_n_propylamine="g_n_propylamine";
		String nameInfDilActCoef_dimethyl_disulfide="g_dimethyl_disulfide";

		String nameAntoineConstantA="antoineConstantA";
		String nameAntoineConstantB="antoineConstantB";
		String nameAntoineConstantC="antoineConstantC";
		String nameAntoineTmin="antoineTmin";
		String nameAntoineTmax="antoineTmax";
		String nameAntoineSource="antoineSource";

		String nameTc="Est Tc in K";
		String namePc="Est Pc in bar";
		String nameOmega="Est omega";

		

		//store header names in an array so can determine column numbers in a loop later:
		String[] names = { nameCAS, nameName, nameFormula, nameSynonyms,
				nameMolecularWeight, nameMeltingPoint,nameBoilingPoint, nameViscosity,
				nameVaporPressure, nameThermalConductivity, nameSurfaceTension,
				nameFlashPoint, nameDensity, nameMeltingPointSource,nameBoilingPointSource,
				nameViscositySource, nameVaporPressureSource,
				nameThermalConductivitySource, nameSurfaceTensionSource,
				nameFlashPointSource, nameDensitySource, nameOralRatLD50,
				nameFatheadMinnowLC50, nameOSHA_TWA, nameGWP, namePCOP, nameODP,
				nameAP, nameInfDilActCoef_ethanol, nameInfDilActCoef_acetone,
				nameInfDilActCoef_diethyl_ether, nameInfDilActCoef_water,
				nameInfDilActCoef_benzene, nameInfDilActCoef_cis_2_heptene,
				nameInfDilActCoef_n_propyl_chloride,
				nameInfDilActCoef_n_heptadecane,
				nameInfDilActCoef_n_propylamine,
				nameInfDilActCoef_dimethyl_disulfide, nameAntoineConstantA,
				nameAntoineConstantB, nameAntoineConstantC,nameAntoineTmin,nameAntoineTmax,nameAntoineSource,nameTc,namePc,nameOmega };		

		String line, lineSplit[];

		int lineno = 0;

		Chemicals chemicals = new Chemicals();

		InputStreamReader reader = new InputStreamReader(ClassLoader.getSystemResourceAsStream(filePath));
		BufferedReader buf = new BufferedReader(reader);

		String header = buf.readLine();	// read the column header line
		fieldNames=header.split("\t");

		//Store column numbers for each field in colNums hashtable:
		for (int i=0;i<names.length;i++) {
			colNums.put(names[i],getColumnNumber(fieldNames,names[i]));
		}

		//following allows you to reorder column names without breaking code:

		lineno++;

		while ((line = buf.readLine()) != null) {
			lineno++;
			
			//		values = line.split("\t");//wont work if have series of blank fields at the end!
			values=Parse(line,"\t");

			Chemical chemical = new Chemical();

			//****************************************************************
			//general chemical info:
			chemical.setCAS(getStringValue(nameCAS));
			chemical.setName(getStringValue(nameName));


			String strSynonyms=getStringValue(nameSynonyms);

			String [] synonyms=strSynonyms.split(";");
			Vector <String>Synonyms=chemical.getSynonyms();
			for (int i=0;i<synonyms.length;i++) {
				Synonyms.add(synonyms[i]);
			}

			chemical.setMolecularWeight(getDoubleValue(nameMolecularWeight));
			chemical.setFormula(getStringValue(nameFormula));

			//****************************************************************
			//Physical properties
			chemical.setDensity(getDoubleValue(nameDensity));
			chemical.setSurfaceTension(getDoubleValue(nameSurfaceTension));
			chemical.setBoilingPoint(getDoubleValue(nameBoilingPoint));
			chemical.setViscosity(getDoubleValue(nameViscosity));
			chemical.setVaporPressure(getDoubleValue(nameVaporPressure));
			chemical.setThermalConductivity(getDoubleValue(nameThermalConductivity));
			chemical.setFlashPoint(getDoubleValue(nameFlashPoint));
			chemical.setMeltingPoint(getDoubleValue(nameMeltingPoint));
			//		chemical.setSolubility(getDoubleValue(nameWaterSolubility));

			chemical.setDensitySource(getStringValue(nameDensitySource));
			chemical.setSurfaceTensionSource(getStringValue(nameSurfaceTensionSource));
			chemical.setBoilingPointSource(getStringValue(nameBoilingPointSource));
			chemical.setViscositySource(getStringValue(nameViscositySource));
			chemical.setVaporPressureSource(getStringValue(nameVaporPressureSource));
			chemical.setThermalConductivitySource(getStringValue(nameThermalConductivitySource));
			chemical.setFlashPointSource(getStringValue(nameFlashPointSource));
			chemical.setMeltingPointSource(getStringValue(nameMeltingPointSource));
			//		chemical.setSolubilitySource(getStringValue(nameWaterSolubilitySource));

			//Infinite dilution activity coefficients:
			chemical.setInfDilActCoef_ethanol(getDoubleValue(nameInfDilActCoef_ethanol));
			chemical.setInfDilActCoef_acetone(getDoubleValue(nameInfDilActCoef_acetone));
			chemical.setInfDilActCoef_diethyl_ether(getDoubleValue(nameInfDilActCoef_diethyl_ether));
			chemical.setInfDilActCoef_water(getDoubleValue(nameInfDilActCoef_water));
			chemical.setInfDilActCoef_benzene(getDoubleValue(nameInfDilActCoef_benzene));
			chemical.setInfDilActCoef_cis_2_heptene(getDoubleValue(nameInfDilActCoef_cis_2_heptene));
			chemical.setInfDilActCoef_n_propyl_chloride(getDoubleValue(nameInfDilActCoef_n_propyl_chloride));
			chemical.setInfDilActCoef_n_heptadecane(getDoubleValue(nameInfDilActCoef_n_heptadecane));
			chemical.setInfDilActCoef_n_propylamine(getDoubleValue(nameInfDilActCoef_n_propylamine));
			chemical.setInfDilActCoef_dimethyl_disulfide(getDoubleValue(nameInfDilActCoef_dimethyl_disulfide));

			//TODO- add antoine constants (needed to calculate vapor pressure:
			if (!isEmpty(nameAntoineConstantA)) chemical.setAntoineConstantA(getDoubleValue(nameAntoineConstantA));
			if (!isEmpty(nameAntoineConstantB))chemical.setAntoineConstantB(getDoubleValue(nameAntoineConstantB));
			if (!isEmpty(nameAntoineConstantC))chemical.setAntoineConstantC(getDoubleValue(nameAntoineConstantC));
			if (!isEmpty(nameAntoineTmin))chemical.setAntoineTmin(getDoubleValue(nameAntoineTmin));
			if (!isEmpty(nameAntoineTmax))chemical.setAntoineTmax(getDoubleValue(nameAntoineTmax));
			chemical.setAntoineSource(getStringValue(nameAntoineSource));

			
			if (!isEmpty(nameTc)) chemical.setTc(getDoubleValue(nameTc));
			if (!isEmpty(namePc))chemical.setPc(getDoubleValue(namePc));
			if (!isEmpty(nameOmega))chemical.setOmega(getDoubleValue(nameOmega));

			
			//**************************************************************************
			//WAR categories:
			double value = 0.0;
			if (!isEmpty(nameOralRatLD50)) value = getDoubleValue(nameOralRatLD50);
			if (value!=0.0) chemical.setHtoxIngestion(1.0/value);
			if (value!=0.0) chemical.setTerrestrialTox(1.0/value);
			value = 0.0;
			if (!isEmpty(nameFatheadMinnowLC50)) value = getDoubleValue(nameFatheadMinnowLC50);
			if (value!=0.0) chemical.setAquaticTox(1.0/value);
			value = 0.0;
			if (!isEmpty(nameOSHA_TWA)) value = getDoubleValue(nameOSHA_TWA);
			if (value!=0.0) chemical.setHtoxInhalation(1.0/value);
			//		if (!isEmpty(nameHtoxDermal)) chemical.setHtoxDermal(getDoubleValue(nameHtoxDermal));
			if (!isEmpty(nameGWP)) chemical.setGWP(getDoubleValue(nameGWP));
			if (!isEmpty(namePCOP)) chemical.setPCOP(getDoubleValue(namePCOP));
			if (!isEmpty(nameODP)) chemical.setODP(getDoubleValue(nameODP));
			if (!isEmpty(nameAP)) chemical.setAP(getDoubleValue(nameAP));

			//Concise code: Set values using reflection (need properties to be public for this to work)
			//		for (int i=0;i<fieldNames.length;i++) {
			//			Field myField = chemical.getClass().getField(fieldNames[i]);
			//			String value=lineSplit[i];
			//			
			//			if (myField.getType().equals("class java.lang.String")) {
			//				myField.set(chemical,value);				
			//			} else if (myField.getType().equals("class java.lang.Double")) {
			//				myField.set(chemical,Double.parseDouble(value));
			//			} else {
			//				System.out.println(myField.getType());	
			//			}
			//		}
			
//			System.out.println(chemical.getCAS()+"\t"+chemical.getTm());

			
			chemicals.add(chemical);
		}
		buf.close();

		return chemicals;
	}
	


	private static String getStringValue(String fieldName) {
		return values[colNums.get(fieldName)];
	}

	private static double getDoubleValue(String fieldName) {
		return Double.parseDouble(values[colNums.get(fieldName)]);
	}

	private static boolean isEmpty(String fieldName) {
		return values[getColumnNumber(fieldNames,fieldName)].isEmpty();
	}
	
	public void readByTxt3(String filePath) throws IOException, Exception {
		String line, lineSplit[];
		
		Chemicals chemicals = new Chemicals();

		File file = new File(filePath);
		//	System.out.println(file.getAbsolutePath());
		FileReader fileReader = new FileReader(file);
		BufferedReader buf = new BufferedReader(fileReader);

		String header = buf.readLine();	// read the column header line
		fieldNames=header.split("\t");
		
		int lineno = 0;
		
		int[] columnNumber = new int[38];
		columnNumber[0] = getColumnNumber(fieldNames,"CAS");
		columnNumber[1] = getColumnNumber(fieldNames,"Systematic Name");
		columnNumber[2] = getColumnNumber(fieldNames,"Formula");
		columnNumber[3] = getColumnNumber(fieldNames,"Synonyms");
		columnNumber[4] = getColumnNumber(fieldNames,"Molecular Weight in kg/kmol");
		columnNumber[5] = getColumnNumber(fieldNames,"Melting Point in K");
		columnNumber[6] = getColumnNumber(fieldNames,"Boiling Point in K");
		columnNumber[7] = getColumnNumber(fieldNames,"Viscosity in kg/m-s");
		columnNumber[8] = getColumnNumber(fieldNames,"Vapor Pressure in kPa");
		columnNumber[9] = getColumnNumber(fieldNames,"Thermal Conductivity in J/(m-s-K)");
		columnNumber[10] = getColumnNumber(fieldNames,"Surface Tension in kg/s2");
		columnNumber[11] = getColumnNumber(fieldNames,"Flash Point in K");
		columnNumber[12] = getColumnNumber(fieldNames,"Density in kg/m3");
		
		columnNumber[13] = getColumnNumber(fieldNames,"Melting Point Source");
		columnNumber[14] = getColumnNumber(fieldNames,"Boiling Point Source");
		columnNumber[15] = getColumnNumber(fieldNames,"Viscosity Source");
		columnNumber[16] = getColumnNumber(fieldNames,"Vapor Pressure Source");		
		columnNumber[17] = getColumnNumber(fieldNames,"Thermal Conductivity Source");
		columnNumber[18] = getColumnNumber(fieldNames,"Surface Tension Source");		
		columnNumber[19] = getColumnNumber(fieldNames,"Flash Point Source");
		columnNumber[20] = getColumnNumber(fieldNames,"Density Source");
		
		columnNumber[21] = getColumnNumber(fieldNames,"Oral Rat LD50 in mg/kg");
		columnNumber[22] = getColumnNumber(fieldNames,"Fathead Minnow LC50 in mg/L");
		columnNumber[23] = getColumnNumber(fieldNames,"OSHA_TWA");
		columnNumber[24] = getColumnNumber(fieldNames,"GWP");
		columnNumber[25] = getColumnNumber(fieldNames,"PCO");
		columnNumber[26] = getColumnNumber(fieldNames,"OD");
		columnNumber[27] = getColumnNumber(fieldNames,"AP");
		
		columnNumber[28] = getColumnNumber(fieldNames,"g_ethanol");
		columnNumber[29] = getColumnNumber(fieldNames,"g_acetone");
		columnNumber[30] = getColumnNumber(fieldNames,"g_diethyl_ether");
		columnNumber[31] = getColumnNumber(fieldNames,"g_water");
		columnNumber[32] = getColumnNumber(fieldNames,"g_benzene");
		columnNumber[33] = getColumnNumber(fieldNames,"g_cis_2_heptene");
		columnNumber[34] = getColumnNumber(fieldNames,"g_n_propyl_chloride");		
		columnNumber[35] = getColumnNumber(fieldNames,"g_n_heptadecane");
		columnNumber[36] = getColumnNumber(fieldNames,"g_n_propylamine");
		columnNumber[37] = getColumnNumber(fieldNames,"g_dimethyl_sulfide");
		
//		columnNumber[38] = getColumnNumber(fieldNames,"antoineConstantA");
//		columnNumber[39] = getColumnNumber(fieldNames,"antoineConstantB");
//		columnNumber[40] = getColumnNumber(fieldNames,"antoineConstantC");
		
		for (int i=0; i<columnNumber.length; i++) {
			if (columnNumber[i]<0 || columnNumber[i]>columnNumber.length) throw new Exception("Column Header "+i+" not found");
		}
		
		//following allows you to reorder column names without breaking code:
		
		lineno++;
		while ((line = buf.readLine()) != null) {
			lineno++;

			//		values = line.split("\t");//wont work if have series of blank fields at the end!
			values=Parse(line,"\t");

			Chemical chemical = new Chemical();

			//****************************************************************
			//general chemical info:
			chemical.setCAS(values[columnNumber[0]]);
			chemical.setName(values[columnNumber[1]]);

			String strSynonyms=values[columnNumber[3]];

			String [] synonyms=strSynonyms.split(";");
			Vector <String>Synonyms=chemical.getSynonyms();
			for (int i=0;i<synonyms.length;i++) {
				Synonyms.add(synonyms[i]);
			}

			chemical.setMolecularWeight(Double.parseDouble(values[columnNumber[4]]));
			chemical.setFormula(values[columnNumber[2]]);

			//****************************************************************
			//Physical properties
			chemical.setDensity(Double.parseDouble(values[columnNumber[12]]));
			chemical.setSurfaceTension(Double.parseDouble(values[columnNumber[10]]));
			chemical.setBoilingPoint(Double.parseDouble(values[columnNumber[6]]));
			chemical.setMeltingPoint(Double.parseDouble(values[columnNumber[5]]));
			chemical.setViscosity(Double.parseDouble(values[columnNumber[7]]));
			chemical.setVaporPressure(Double.parseDouble(values[columnNumber[8]]));
			chemical.setThermalConductivity(Double.parseDouble(values[columnNumber[9]]));
			chemical.setFlashPoint(Double.parseDouble(values[columnNumber[11]]));
			//		chemical.setSolubility(getDoubleValue(nameWaterSolubility));

			chemical.setDensitySource(values[columnNumber[20]]);
			chemical.setSurfaceTensionSource(values[columnNumber[18]]);
			chemical.setBoilingPointSource(values[columnNumber[14]]);
			chemical.setMeltingPointSource(values[columnNumber[13]]);
			chemical.setViscositySource(values[columnNumber[15]]);
			chemical.setVaporPressureSource(values[columnNumber[16]]);
			chemical.setThermalConductivitySource(values[columnNumber[17]]);
			chemical.setFlashPointSource(values[columnNumber[19]]);
			//		chemical.setSolubilitySource(getStringValue(nameWaterSolubilitySource));

			//Infinite dilution activity coefficients:
			chemical.setInfDilActCoef_ethanol(Double.parseDouble(values[columnNumber[28]]));
			chemical.setInfDilActCoef_acetone(Double.parseDouble(values[columnNumber[29]]));
			chemical.setInfDilActCoef_diethyl_ether(Double.parseDouble(values[columnNumber[30]]));
			chemical.setInfDilActCoef_water(Double.parseDouble(values[columnNumber[31]]));
			chemical.setInfDilActCoef_benzene(Double.parseDouble(values[columnNumber[32]]));
			chemical.setInfDilActCoef_cis_2_heptene(Double.parseDouble(values[columnNumber[33]]));
			chemical.setInfDilActCoef_n_propyl_chloride(Double.parseDouble(values[columnNumber[34]]));
			chemical.setInfDilActCoef_n_heptadecane(Double.parseDouble(values[columnNumber[35]]));
			chemical.setInfDilActCoef_n_propylamine(Double.parseDouble(values[columnNumber[36]]));
			chemical.setInfDilActCoef_dimethyl_disulfide(Double.parseDouble(values[columnNumber[37]]));

			//TODO- add antoine constants (needed to calculate vapor pressure:
			//		chemical.setAntoineConstantA(getDoubleValue(nameAntoineConstantA));
			//		chemical.setAntoineConstantB(getDoubleValue(nameAntoineConstantB));
			//		chemical.setAntoineConstantC(getDoubleValue(nameAntoineConstantC));

			//**************************************************************************
			//WAR categories:
			chemical.setHtoxIngestion(Double.parseDouble(values[columnNumber[21]]));
			chemical.setAquaticTox(Double.parseDouble(values[columnNumber[22]]));

			if (!values[columnNumber[23]].isEmpty()) chemical.setHtoxInhalation(Double.parseDouble(values[columnNumber[23]]));
			//		if (!isEmpty(nameHtoxDermal)) chemical.setHtoxDermal(getDoubleValue(nameHtoxDermal));
			if (!values[columnNumber[24]].isEmpty()) chemical.setGWP(Double.parseDouble(values[columnNumber[24]]));
			if (!values[columnNumber[25]].isEmpty()) chemical.setPCOP(Double.parseDouble(values[columnNumber[25]]));
			if (!values[columnNumber[26]].isEmpty()) chemical.setODP(Double.parseDouble(values[columnNumber[26]]));
			if (!values[columnNumber[27]].isEmpty()) chemical.setAP(Double.parseDouble(values[columnNumber[27]]));

			//Concise code: Set values using reflection (need properties to be public for this to work)
			//		for (int i=0;i<fieldNames.length;i++) {
			//			Field myField = chemical.getClass().getField(fieldNames[i]);
			//			String value=lineSplit[i];
			//			
			//			if (myField.getType().equals("class java.lang.String")) {
			//				myField.set(chemical,value);				
			//			} else if (myField.getType().equals("class java.lang.Double")) {
			//				myField.set(chemical,Double.parseDouble(value));
			//			} else {
			//				System.out.println(myField.getType());	
			//			}
			//		}
			chemicals.add(chemical);
		}
		buf.close();
		
	}
	
	private static Chemicals readByXML(String filename) throws IOException {
		XMLDecoder decoder = new XMLDecoder(ClassLoader.getSystemResourceAsStream(filename));
		Chemicals chemicals = (Chemicals)decoder.readObject();
		decoder.close();
		return chemicals;
	}
	
	public void writeByXML(String filename) throws IOException {
		FileOutputStream fos = new FileOutputStream(ClassLoader.getSystemResource(filename).getPath());
		XMLEncoder encoder = new XMLEncoder(fos);
		encoder.writeObject(this);
		encoder.flush();
		encoder.close();
		fos.close();
	}

	public void addSynListDataFromFile(String fileName) throws Exception {
		String line;
		
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader buf = new BufferedReader(fileReader);
		
		int id;
		int start;
		int end;
		String chemName, casNum, formula, structure, synonym;
		int numsyn;
		int k=0;
		while ((line = buf.readLine()) != null) {
			if (line.charAt(line.length()-1)==',') {
				
				start=0;
				end = line.indexOf(',', start);
				id = Integer.parseInt(line.substring(start, end).trim());
				
				start = line.indexOf('"', end+1);
				end = line.indexOf('"', start+1);
				chemName = line.substring(start+1,  end).trim();  // do not include '"', and trim
				start = line.indexOf('"', end+1);
				end = line.indexOf('"', start+1);
				casNum = line.substring(start+1,  end).trim();  // do not include '"', and trim
				
				line = buf.readLine(); //read next line
				start = line.indexOf('"');
				end = line.lastIndexOf('"'); 
				formula = line.substring(start+1,  end).trim();  // do not include '"', and trim
				
				line = buf.readLine(); //read next line
				start = line.indexOf('"');
				end = line.lastIndexOf('"'); 
				structure = line.substring(start+1,  end).trim();  // do not include '"', and trim
				
				line = buf.readLine(); //read next line
				numsyn = Integer.parseInt(line.trim());
				
				Vector<String> synonyms2 = new Vector<String>();
				synonyms2.add(chemName);
				for (int i=0; i<numsyn+1; i++) {
					line = buf.readLine(); //read next line
					start = line.indexOf('"');
					end = line.lastIndexOf('"'); 
					synonym = line.substring(start+1,  end).trim();  // do not include '"', and trim
					if (synonym.length()>0) {
						synonyms2.add(synonym);
					}
				}
				
				Chemical chemical = this.getByCAS(casNum);
				if (chemical==null) {
//					System.out.println("CAS# "+casNum+" not found");
				} else {
					if ((chemical.getFormula()==null||chemical.getFormula()=="")&&formula!="") {
						k++;
						System.out.println(k+") "+chemical.getFormula()+": "+formula);
						chemical.setFormula(formula);
					}
					if ((chemical.getStructure()==null||chemical.getStructure()=="")&&structure!="") {
						k++;
						System.out.println(k+") "+chemical.getStructure()+": "+structure);
						chemical.setStructure(structure);
					}
					if ((chemical.getName()==null||chemical.getName()=="")&&chemName!="") {
						k++;
						System.out.println(k+") "+chemical.getName()+": "+chemName);
						chemical.setName(chemName);
					}

					Vector<String> synonyms=chemical.getSynonyms();
					int numSyn = synonyms.size();

					synonyms.add(chemName);
					synonyms.addAll(synonyms2);

					chemical.eliminateSynonymRepeats();
					
					if (numSyn!=chemical.getSynonyms().size()) {
						System.out.println(casNum+": Originally, numSyn = "+numSyn+", now numSyn = "+chemical.getSynonyms().size());
					}

					
				}
				
			}
		}
		buf.close();
		
	}
	
	public void addWarScoreDataFromFile(String fileName) throws Exception {
		String line, lineSplit[];
		
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader buf = new BufferedReader(fileReader);
		
		int lineno = 0;
		int id;
		int dipprid;
		int start;
		int end;
		String chemName;
			
		while ((line = buf.readLine()) != null) {
			lineno++;
//			System.out.println("lineno = "+lineno);

			if (line.charAt(line.length()-1)==':') {
				
				start=0;
				end = line.indexOf(',', start);
				id = Integer.parseInt(line.substring(start, end).trim());
				
				start = line.indexOf('"', end+1);
				end = line.indexOf('"', start+1);
				chemName = line.substring(start+1,  end).trim();  // do not include '"', and trim
				
				Chemical chemical = this.getByName(chemName);
				line = buf.readLine(); //read next line
				lineno++;
//				System.out.println("lineno = "+lineno);
				
				if (chemical==null) continue;  // chemical not found

				lineSplit = line.split(",",10);
				
				if (!lineSplit[0].trim().isEmpty()) dipprid = Integer.parseInt(lineSplit[0].trim());
				if (!lineSplit[1].trim().isEmpty()) chemical.setHtoxIngestion(Double.parseDouble(lineSplit[1]));
				if (!lineSplit[2].trim().isEmpty()) chemical.setHtoxInhalation(Double.parseDouble(lineSplit[2]));
//				if (!lineSplit[3].trim().isEmpty()) chemical.setHtoxDermal(Double.parseDouble(lineSplit[3]));
				if (!lineSplit[3].trim().isEmpty()) chemical.setTerrestrialTox(Double.parseDouble(lineSplit[3]));
				if (!lineSplit[4].trim().isEmpty()) chemical.setAquaticTox(Double.parseDouble(lineSplit[4]));
				if (!lineSplit[5].trim().isEmpty()) chemical.setGWP(Double.parseDouble(lineSplit[5]));
				if (!lineSplit[6].trim().isEmpty()) chemical.setODP(Double.parseDouble(lineSplit[6]));
				if (!lineSplit[7].trim().isEmpty()) chemical.setPCOP(Double.parseDouble(lineSplit[7]));
				if (!lineSplit[8].trim().isEmpty()) chemical.setAP(Double.parseDouble(lineSplit[8]));
				
			}
		}
		buf.close();
		
	}
	
	public void addPropertyDataFromFile(String fileName) throws Exception {
		String line, lineSplit[];
		
		File file = new File(fileName);
		FileReader fileReader = new FileReader(file);
		BufferedReader buf = new BufferedReader(fileReader);
		
		int lineno = 0;
		int start;
		int end;
		String chemName;
			
		while ((line = buf.readLine()) != null) {
			lineno++;
			System.out.println("lineno = "+lineno);

			if (line.charAt(line.length()-1)==':') {
				
				start = line.indexOf('"', 0);
				end = line.indexOf('"', start+1);
				chemName = line.substring(start+1,  end).trim();  // do not include '"', and trim
				
				Chemical chemical = this.getByName(chemName);
				if (chemical==null) chemical = this.getBySynonym(chemName);

				line = buf.readLine(); //read next line; not used
				lineno++;
				System.out.println("lineno = "+lineno);
				
				line = buf.readLine(); //read next line
				lineno++;
				System.out.println("lineno = "+lineno);
				
				if (chemical!=null) {
					lineSplit = line.split(",",6);
					if (!lineSplit[0].trim().isEmpty()) chemical.setMolecularWeight(Double.parseDouble(lineSplit[0]));
					if (!lineSplit[1].trim().isEmpty()) chemical.setMeltingPoint(Double.parseDouble(lineSplit[1]));
					if (!lineSplit[2].trim().isEmpty()) chemical.setBoilingPoint(Double.parseDouble(lineSplit[2]));
					if (!lineSplit[3].trim().isEmpty()) chemical.setSolubility(Double.parseDouble(lineSplit[3]));
					if (!lineSplit[4].trim().isEmpty()) chemical.setFlashPoint(Double.parseDouble(lineSplit[4]));
				}
				
				line = buf.readLine(); //read next line
				lineno++;
				System.out.println("lineno = "+lineno);
				
				if (chemical!=null) {
					lineSplit = line.split(",",6);
					if (!lineSplit[0].trim().isEmpty()) chemical.setUFlamLimit(Double.parseDouble(lineSplit[0]));
					if (!lineSplit[1].trim().isEmpty()) chemical.setLFlamLimit(Double.parseDouble(lineSplit[1]));
					if (!lineSplit[2].trim().isEmpty()) chemical.setAutoIgnitTemp(Double.parseDouble(lineSplit[2]));
				}

				line = buf.readLine(); //read next line; not used
				lineno++;
				System.out.println("lineno = "+lineno);
			}
		}
		buf.close();
		
	}

	public void convertFromCommonToSI() {
		Chemical chemical;

		for (int i=0; i<this.elementCount; i++) {
			chemical = this.get(i);
			chemical.setDensity(Units.densityConvertFrom(chemical.getDensity(), Units.COMMON));
			chemical.setFlashPoint(Units.tempConvertFrom(chemical.getFlashPoint(), Units.COMMON));
//			chemical.setHeatCapacity(Units.hcapConvertFrom(chemical.getHeatCapacity(), Units.COMMON));
			chemical.setMolecularWeight(Units.massConvertFrom(chemical.getMolecularWeight(), Units.COMMON));
//			chemical.setSolubility(Units.solubConvertFrom(chemical.getSolubility(), Units.COMMON));
			chemical.setSurfaceTension(Units.surfaceTensionConvertFrom(chemical.getSurfaceTension(), Units.COMMON));
			chemical.setBoilingPoint(Units.tempConvertFrom(chemical.getBoilingPoint(), Units.COMMON));
			chemical.setThermalConductivity(Units.thermalConductivityConvertFrom(chemical.getThermalConductivity(), Units.COMMON));
			chemical.setMeltingPoint(Units.tempConvertFrom(chemical.getMeltingPoint(), Units.COMMON));
			chemical.setViscosity(Units.viscosityConvertFrom(chemical.getViscosity(), Units.COMMON));
			chemical.setVaporPressure(Units.pressureConvertFrom(chemical.getVaporPressure(), Units.COMMON));	
		}
	}

//	public void addDataFromOldChemicals(Chemicals oldChemicals) {
//		Chemical oldChemical;
////		Chemical newChemical;
//		
////		int n=0;
////		File file = new File("./src/data/CAS#NotFoundInOldData.txt");
////		FileWriter fileWriter = new FileWriter(file);
//
//		for (Chemical chemical: this) {
//			
//			oldChemical = oldChemicals.getByCAS(chemical.getCAS());
//			if (oldChemical==null) {
////				n++;
//				System.out.println("chemical "+chemical.getCAS()+" not found in oldChemicals");
////				fileWriter.write(n+") "+chemical.getCAS()+"\n");
//			} else {
//				chemical.setName(oldChemical.getName());
//				chemical.setFormula(oldChemical.getFormula());
//				chemical.setStructure(oldChemical.getStructure());
//				chemical.setSmiles(oldChemical.getSmiles());
//				chemical.setSynonyms(oldChemical.getSynonyms());
//				chemical.setChemicalFamilyID(oldChemical.getChemicalFamilyID());
//
//				chemical.setUFlamLimit(oldChemical.getUFlamLimit());
//				chemical.setLFlamLimit(oldChemical.getLFlamLimit());
//				chemical.setAutoIgnitTemp(oldChemical.getAutoIgnitTemp());
//			}
//			
//		}
//		
////		fileWriter.close();
////		
////		n=0;
////		file = new File("./src/data/CAS#NotFoundInNewData.txt");
////		fileWriter = new FileWriter(file);
////
////		for (Chemical chemical: oldChemicals) {
////			
////			newChemical = this.getByCAS(chemical.getCAS());
////			if (newChemical==null) {
////				n++;
////				System.out.println("chemical "+chemical.getCAS()+" not found in newChemicals");
////				fileWriter.write(n+") "+chemical.getCAS()+"\n");
////			}
////			
////		}
////		
////		fileWriter.close();
//		
//	}
	
	private void testVP(Chemicals allChemicals) {
		double tempK=298.15;
		
		for (int i=0;i<allChemicals.size();i++) {
			Chemical chemicali=allChemicals.get(i);
			
			double VPA=-9999;
			double VPLK=-9999;
			
			if(chemicali.haveAntoineConstants()) {
				VPA=chemicali.calculateAntoineVaporPressure(tempK);
			}
			if (chemicali.haveCriticalParameters()) {
				VPLK=chemicali.calculateLeeKeslerVaporPressure(tempK);
			}
			
			if (VPA!=-9999 && VPLK!=-9999) {
				System.out.println(chemicali.getCAS()+"\t"+VPA+"\t"+VPLK);	
			}
		}
		
	}
	
	
	private void convertTxtDBtoXML() {
		Chemicals allChemicals=null;
		
		try {
			allChemicals=readByTxt2("data/Chemicals.txt");
			allChemicals.writeByXML("data/Chemicals3.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void testReadFromFiles() {
		//Test read in from xml file
		long t1=System.currentTimeMillis();
		//read from xml:
		Chemicals allChemicals = Chemicals.readFromFile("data/Chemicals3.xml");
		long t2=System.currentTimeMillis();
		
		double timeForXMLRead=(t2-t1)/1000.0;
		
		System.out.println("time to read XML file:"+timeForXMLRead+" seconds");
		
		//*****************************************************************
		//Test read in from text file
		allChemicals = Chemicals.readFromFile("data/Chemicals.txt");
		long t3=System.currentTimeMillis();
		double timeForTxtRead=(t3-t2)/1000.0;

		System.out.println("time to read txt file:"+timeForTxtRead+" seconds");
		
	
	}
	
//	public static void main(String[] args) {
//		
//		//Following reads in file in text format and then output to xml:
//		Chemicals chemicals=new Chemicals();
//		chemicals.convertTxtDBtoXML();
//
//		//*****************************************************************
//		chemicals.testReadFromFiles();
//
//	}

}
