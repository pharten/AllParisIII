package unifac;

import java.util.*;

public class UnifacData {
	
static char delimiter = '|';


//Store UNIFAC Group Information
//Key is Group Name
Hashtable <String, Integer> htGroupID = new Hashtable<String, Integer> ();

////Store UNIFAC Chemical Information
////First Key is CAS number; Second Key is Group Name
Hashtable<String, Hashtable<String, Integer>> htChemInfo = new Hashtable<String, Hashtable<String, Integer>> ();


	double rgroup[] = new double[UNIFACforPARIS.maxNumSubGroups+1];
	double qgroup[] = new double[UNIFACforPARIS.maxNumSubGroups+1];
	double ipa [][] = new double [UNIFACforPARIS.numMainGroups+1][UNIFACforPARIS.numMainGroups+1];//Interaction paramter aij
	double ipb [][] = new double [UNIFACforPARIS.numMainGroups+1][UNIFACforPARIS.numMainGroups+1];//Interaction paramter bij

////Store UNIFAC Interaction Parameter Information
////uij = aij + bij*(temp - tref)
////psiij = exp(-uij/temp)


	Vector<String> allCAS = new Vector<String> ();

	public UnifacData () {
		this.readInputInfo();
	}

	public static void main (String [] args) {
		long t1=System.currentTimeMillis();
		UnifacData uf=new UnifacData();
		long t2=System.currentTimeMillis();
		System.out.println((t2-t1)/1000.0);
		
		
	}
	private void readInputInfo() {
		ArrayList<String> alGroupInfo = new ArrayList<String>();
		ArrayList<String> alInteractionInfo = new ArrayList<String>();
		ArrayList<String> alChemicalInfo = new ArrayList<String>();

		// *****************************************************
		// Read input information
		// *****************************************************

		// Read in UNIFAC group information
		UNIFACGroupsforPARIS ugp = new UNIFACGroupsforPARIS();
		alGroupInfo = ugp.GetGroupInfo();

		// Parse UNIFAC group information into Vectors
		this.parseGroupInfo(alGroupInfo);

		// Read in UNIFAC Interaction
		UNIFACInteractionParams uip = new UNIFACInteractionParams();
		alInteractionInfo = uip.GetInteractionInfo();

		// Parse UNIFAC interaction information into matrix
		this.parseInteractionInfo(alInteractionInfo);

		// Read in Chemical List
		UNIFACChemicals uc = new UNIFACChemicals();
		alChemicalInfo = uc.GetChemicalInfo();

		// Parse UNIFAC chemical information into Hashtable
		this.parseChemicalInfo(alChemicalInfo);
	}

	private void parseChemicalInfo (ArrayList<String> al) {
		for (int i = 0; i < al.size(); i++) {
			String line = al.get(i);
			int firstBreak = line.indexOf(delimiter);
			String cas = line.substring(0, firstBreak);
			String groups = line.substring(firstBreak+1);
			htChemInfo.put(cas, this.parseGroupCounts(groups));
			allCAS.add(cas);
			
		}
	}
	
	private Hashtable<String, Integer> parseGroupCounts (String line) {
		Hashtable<String, Integer> ht = new Hashtable<String, Integer> ();
		
			while (line != null) {
				int firstBreak = line.indexOf(delimiter);
				String group = line.substring(0, firstBreak);
				String templine = line.substring(firstBreak+1);
				if (templine.contains("|")) {
					int secondBreak = line.indexOf(delimiter, firstBreak+1);
					String scount = line.substring(firstBreak+1, secondBreak);
					Integer count = new Integer(scount);
					line = line.substring(secondBreak+1);
					ht.put(group, count);
				} else {
					String scount = line.substring(firstBreak+1);
					Integer count = new Integer(scount);
					line = null;
					ht.put(group, count);
				}
			}
			
		return ht;
		}
	private void parseInteractionInfo (ArrayList<String> al) {
		for (int i = 0; i < al.size(); i++) {
			String line = al.get(i);
			int firstBreak = line.indexOf(delimiter);
			int secondBreak = line.indexOf(delimiter, firstBreak+1);
			int thirdBreak = line.indexOf(delimiter, secondBreak+1);
			String maingroupi = line.substring(0, firstBreak);
			Integer mgi = new Integer(maingroupi);
			int groupi = mgi;
			String maingroupj = line.substring(firstBreak+1, secondBreak);
			Integer mgj = new Integer(maingroupj);
			int groupj = mgj;
			String astring = line.substring(secondBreak+1, thirdBreak);
			Double as = new Double (astring);
			double avalue = as;
			String bstring = line.substring(thirdBreak+1);
			Double bs = new Double (bstring);
			double bvalue = bs;
			
			ipa[groupi][groupj] = avalue;
			ipb[groupi][groupj] = bvalue;
		}
	}
	private void parseGroupInfo (ArrayList<String> al) {
		for (int i = 0; i < al.size(); i++) {
			String line = al.get(i);
			int firstBreak = line.indexOf(delimiter);
			String id = line.substring(0, firstBreak);
			int secondBreak = line.indexOf(delimiter, firstBreak+1);
			String groupname = line.substring(firstBreak+1, secondBreak);
			int thirdBreak = line.indexOf(delimiter, secondBreak+1);
			String q = line.substring(secondBreak+1, thirdBreak);
			String r = line.substring(thirdBreak+1);
			Integer idint = new Integer(id);
			int idd = idint;
			Double qdouble = new Double(q);
			Double rdouble = new Double(r);
			qgroup[idd] = (double) qdouble;
			rgroup[idd] = (double) rdouble;
			htGroupID.put(groupname, idint);
		}
	}
}
