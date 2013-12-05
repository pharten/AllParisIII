package unifac;

import java.util.Vector;

public class UNIFACGroupCodesforPARIS {
	
	public int subGroupIDs (String groupname) {
		
		int groupID = this.getSubGroupIDs(groupname);
		
		return groupID;
	}
	
	public Vector<Integer> mainGroupID (Vector<Integer> subGroups) {
		Vector<Integer> groupIDs = new Vector<Integer> ();
		
		for (int i = 0; i < subGroups.size(); i++) {
			groupIDs.add(this.getMainGroupID(subGroups.get(i)));
		}
		return groupIDs;
	}

	private Integer getMainGroupID (Integer value) {
		Integer id = 0;
		
		if (value >= 1 && value <= 4) {
			id = 1;
		} else if (value >= 5 && value <= 9) {
			id = 2;
		} else if (value >= 15 && value <=17) {
			id = 3;
		} else if (value >= 18 && value <= 21) {
			id = 4;
		} else if (value == 34) {
			id = 5;
		} else if (value == 35) {
			id = 6;
		} else if (value == 36) {
			id = 7;
		} else if (value == 37) {
			id = 8;
		} else if (value >= 42 && value <= 45) {
			id = 9;
		} else if (value == 48) {
			id = 10;
		} else if (value >= 51 && value <= 54) {
			id = 11;
		} else if (value == 55) {
			id = 12;
		} else if (value >= 59 && value <= 63) {
			id = 13;
		} else if (value >= 66 && value <= 69) {
			id = 14;
		} else if (value >= 71 && value <= 73) {
			id = 15;
		} else if (value >= 74 && value <= 75) {
			id = 16;
		} else if (value >= 80 && value <= 82) {
			id = 17;
		} else if (value >= 76 && value <= 78) {
			id = 18;
		} else if (value >= 85 && value <= 88) {
			id = 19;
		} else if (value >= 94 && value <= 95) {
			id = 20;
		} else if (value >= 99 && value <= 101) {
			id = 21;
		} else if (value >= 102 && value <= 104) {
			id = 22;
		} else if (value >= 105 && value <= 106) {
			id = 23;
		} else if (value == 107) {
			id = 24;
		} else if (value == 109) {
			id = 25;
		} else if (value >= 132 && value <= 135) {
			id = 26;
		} else if (value == 136) {
			id = 27;
		} else if (value == 146) {
			id = 28;
		} else if (value >= 138 && value <= 141) {
			id = 29;
		} else if (value == 50) {
			id = 30;
		} else if (value == 38) {
			id = 31;
		} else if (value == 128) {
			id = 32;
		} else if (value == 130) {
			id = 33;
		} else if (value >= 13 && value <= 14) {
			id = 34;
		} else if (value == 154) {
			id = 35;
		} else if (value == 90) {
			id = 36;
		} else if (value == 108) {
			id = 37;
		} else if (value == 118) {
			id = 38;
		} else if (value >= 163 && value <= 167) {
			id = 39;
		} else if (value >= 111 && value <= 117) {
			id = 40;
		} else if (value == 58) {
			id = 41;
		} else if (value >= 203 && value <= 206) {
			id = 42;
		} else if (value >= 207 && value <= 209) {
			id = 43;
		} else if (value == 201) {
			id = 44;
		} else if (value >= 120 && value <= 127) {
			id = 45;
		} else if (value >= 168 && value <= 172) {
			id = 46;
		} else if (value >= 39 && value <= 41) {
			id = 47;
		} else if (value >= 142 && value <= 145) {
			id = 48;
		} else if (value == 202) {
			id = 49;
		} else if (value >= 147 && value <= 149) {
			id = 50;
		} else if (value >= 10 && value <= 12) {
			id = 51;
		} else if (value >= 22 && value <= 24) {
			id = 52;
		} else if (value >= 25 && value <= 26) {
			id = 53;
		} else if (value >= 27 && value <= 29) {
			id = 54;
		} else if (value >= 30 && value <= 33) {
			id = 55;
		} else if (value == 46) {
			id = 56;
		} else if (value == 49) {
			id = 57;
		} else if (value == 56) {
			id = 58;
		} else if (value == 57) {
			id = 59;
		} else if (value == 65) {
			id = 60;
		} else if (value == 70) {
			id = 61;
		} else if (value == 79) {
			id = 62;
		} else if (value == 89) {
			id = 63;
		} else if (value == 91) {
			id = 64;
		} else if (value == 96) {
			id = 65;
		} else if (value == 110) {
			id = 66;
		} else if (value == 119) {
			id = 67;
		} else if (value == 129) {
			id = 68;
		} else if (value == 131) {
			id = 69;
		} else if (value == 137) {
			id = 70;
		} else if (value >= 151 && value <= 152) {
			id = 71;
		} else if (value == 153) {
			id = 72;
		} else if (value == 155) {
			id = 73;
		} else if (value == 156) {
			id = 74;
		} else if (value >= 157 && value <= 158) {
			id = 75;
		} else if (value == 159) {
			id = 76;
		} else if (value == 160) {
			id = 77;
		} else if (value == 161) {
			id = 78;
		} else if (value >= 173 && value <= 174) {
			id = 79;
		} else if (value >= 175 && value <= 176) {
			id = 80;
		} else if (value >= 177 && value <= 180) {
			id = 81;
		} else if (value >= 182 && value <= 187) {
			id = 82;
		} else if (value >= 188 && value <= 189) {
			id = 83;
		} else if (value == 196) {
			id = 84;
		} else if (value == 197) {
			id = 85;
		} else if (value >= 198 && value <= 199) {
			id = 86;
		} else if (value == 200) {
			id = 87;
		} else if (value >= 92 && value <= 93) {
			id = 88;
		} else if (value >= 83 && value <= 84) {
			id = 89;
		} else if (value == 64) {
			id = 90;
		} else if (value == 47) {
			id = 91;
		} else if (value == 150) {
			id = 92;
		} else if (value == 210) {
			id = 93;
		} else if (value == 211) {
			id = 94;
		} else if (value == 212) {
			id = 95;
		} else if (value == 213) {
			id = 96;
		} else if (value == 214) {
			id = 97;
		} else if (value == 215) {
			id = 98;
		} else if (value == 216) {
			id = 99;
		} else if (value == 217) {
			id = 100;
		} else if (value >= 218 && value <= 219) {
			id = 101;
		} else if (value == 97) {
			id = 102;
		} else if (value == 98) {
			id = 103;
		} else if (value == 162) {
			id = 104;
		} else if (value >= 190 && value <= 195) {
			id = 105;
		} else if (value == 181) {
			id = 106;
		}
		
		return id;
	}
	
	private int getSubGroupIDs (String value) {
		int groupID = 0;
		
		if (value.equals("CH3")) {
			groupID = 1;
		} else if (value.equals("CH2")) {
			groupID = 2;
		} else if (value.equals("CH")) {
			groupID = 3;
		} else if (value.equals("C")) {
			groupID = 4;
		} else if (value.equals("CH2=CH")) {
			groupID = 5;
		} else if (value.equals("CH=CH")) {
			groupID = 6;
		} else if (value.equals("CH2=C")) {
			groupID = 7;
		} else if (value.equals("CH=C")) {
			groupID = 8;
		} else if (value.equals("C=C")) {
			groupID = 9;
		} else if (value.equals("CH2=C=CH")) {
			groupID = 10;
		} else if (value.equals("CH2=C=C")) {
			groupID = 11;
		} else if (value.equals("CH=C=CH")) {
			groupID = 12;
		} else if (value.equals("CH#-C")) {
			groupID = 13; 
		} else if (value.equals("C#C")) {
			groupID = 14; 
		} else if (value.equals("ACH")) {
			groupID = 15;
		} else if (value.equals("AC(link)")) {
			groupID = 16;
		} else if (value.equals("AC(cond)")) {
			groupID = 17;
		} else if (value.equals("ACCH3")) {
			groupID = 18;
		} else if (value.equals("ACCH2")) {
			groupID = 19;
		} else if (value.equals("ACCH")) {
			groupID = 20;
		} else if (value.equals("ACC")) {
			groupID = 21;
		} else if (value.equals("ACCH=CH2")) {
			groupID = 22;
		} else if (value.equals("ACCH=CH")) {
			groupID = 23;
		} else if (value.equals("ACC=CH2")) {
			groupID = 24;
		} else if (value.equals("ACC#CH")) {
			groupID = 25;
		} else if (value.equals("ACC#-C")) {
			groupID = 26;
		} else if (value.equals("CH2,cy")) {
			groupID = 27;
		} else if (value.equals("CH.cy")) {
			groupID = 28;
		} else if (value.equals("C,cy")) {
			groupID = 29;
		} else if (value.equals("CH=CH,cy")) {
			groupID = 30;
		} else if (value.equals("CH2=C,cy")) {
			groupID = 31;
		} else if (value.equals("CH=C,cy")) {
			groupID = 32;
		} else if (value.equals("C=C,cy")) {
			groupID = 33;
		} else if (value.equals("OH")) {
			groupID = 34;
		} else if (value.equals("CH3OH")) {
			groupID = 35;
		} else if (value.equals("H2O")) {
			groupID = 36;
		} else if (value.equals("ACOH")) {
			groupID = 37;
		} else if (value.equals("(CH2OH)2")) {//Ethylene glycol
			groupID = 38;
		} else if (value.equals("C2H5O2")) {
			groupID = 39;
		} else if (value.equals("C2H4O2-1")) {
			groupID = 40;
		} else if (value.equals("C2H4O2-2")) {
			groupID = 41;
		} else if (value.equals("CH3CO")) {//Ketones
			groupID = 42;
		} else if (value.equals("CH2CO")) {
			groupID = 43;
		} else if (value.equals("CHCO")) {
			groupID = 44;
		} else if (value.equals("CCO")) {
			groupID = 45;
		} else if (value.equals("ACCO")) {
			groupID = 46;
		} else if (value.equals("C=O,cy")) {
			groupID = 47;
		} else if (value.equals("CHO")) {//Aldehyde group
			groupID = 48;
		} else if (value.equals("ACCHO")) {//
			groupID = 49;
		}  else if (value.equals("FURFURAL")) {
			groupID = 50;
		} else if (value.equals("CH3COO")) {//Esters
			groupID = 51;
		} else if (value.equals("CH2COO")) {
			groupID = 52;
		} else if (value.equals("CHCOO")) {
			groupID = 53;
		} else if (value.equals("CCOO")) {
			groupID = 54;
		} else if (value.equals("HCOO")) {
			groupID = 55;
		} else if (value.equals("ACCOO")) {
			groupID = 56;
		} else if (value.equals("ACOOC")) {
			groupID = 57;
		} else if (value.equals("COO")) {
			groupID = 58;
		} else if (value.equals("CH3O")) {//Ether
			groupID = 59;
		} else if (value.equals("CH2O")) {
			groupID = 60;
		} else if (value.equals("CH-O")) {//Ether group
			groupID = 61;
		} else if (value.equals("CO")) {//Ether group
			groupID = 62;
		} else if (value.equals("CH2O,f")) {//F=tetrahydrofuran
			groupID = 63;
		} else if (value.equals("O,cy")) {//Ether group
			groupID = 64;
		} else if (value.equals("ACO")) {//Ether group
			groupID = 65;
		} else if (value.equals("CH3NH2")) {
			groupID = 66;
		} else if (value.equals("CH2NH2")) {
			groupID = 67;
		} else if (value.equals("CHNH2")) {
			groupID = 68;
		} else if (value.equals("CNH2")) {
			groupID = 69;
		} else if (value.equals("NH2")) {
			groupID = 70;
		} else if (value.equals("CH3NH")) {
			groupID = 71;
		} else if (value.equals("CH2NH")) {
			groupID = 72;
		} else if (value.equals("CHNH")) {
			groupID = 73;
		} else if (value.equals("CH3N")) {
			groupID = 74;
		} else if (value.equals("CH2N")) {
			groupID = 75;
		} else if (value.equals("C5H5N")) {//Pyridine
			groupID = 76;
		} else if (value.equals("C5H4N")) {
			groupID = 77;
		} else if (value.equals("C5H3N")) {
			groupID = 78;
		} else if (value.equals("AN")) {
			groupID = 79;
		} else if (value.equals("ACNH2")) {
			groupID = 80;
		} else if (value.equals("ACNH")) {
			groupID = 81;
		} else if (value.equals("ACN")) {
			groupID = 82;
		} else if (value.equals("NH,cy")) {
			groupID = 83;
		} else if (value.equals("N,cy")) {
			groupID = 84;
		} else if (value.equals("CH3CN")) {
			groupID = 85;
		} else if (value.equals("CH2CN")) {
			groupID = 86;
		} else if (value.equals("CHCN")) {
			groupID = 87;
		} else if (value.equals("CCN")) {
			groupID = 88;
		} else if (value.equals("AC-CN")) {
			groupID = 89;
		} else if (value.equals("CH2=CHCN")) {
			groupID = 90;
		} else if (value.equals("CN")) {
			groupID = 91;
		} else if (value.equals("CH=N,cy")) {
			groupID = 92;
		} else if (value.equals("C=N,cy")) {
			groupID = 93;
		} else if (value.equals("COOH")) {
			groupID = 94;
		} else if (value.equals("HCOOH")) {
			groupID = 95;
		} else if (value.equals("ACCOOH")) {
			groupID = 96;
		} else if (value.equals("OCOCO")) {
			groupID = 97;
		} else if (value.equals("OCOO")) {
			groupID = 98;
		} else if (value.equals("CH2Cl")) {
			groupID = 99;
		} else if (value.equals("CHCl")) {
			groupID = 100;
		} else if (value.equals("CCl")) {
			groupID = 101;
		} else if (value.equals("CH2Cl2")) {
			groupID = 102;
		} else if (value.equals("CHCl2")) {
			groupID = 103;
		} else if (value.equals("CCl2")) {
			groupID = 104;
		} else if (value.equals("CHCl3")) {
			groupID = 105;
		} else if (value.equals("CCl3")) {
			groupID = 106;
		} else if (value.equals("CCl4")) {
			groupID = 107;
		} else if (value.equals("Cl(C=C)")) {//Chlorine attached to a C=C unit
			groupID = 108;
		} else if (value.equals("ACCl")) {
			groupID = 109;
		} else if (value.equals("Cl")) {
			groupID = 110;
		} else if (value.equals("CHF3")) {
			groupID = 111;
		} else if (value.equals("CF3")) {
			groupID = 112;
		} else if (value.equals("CHF2")) {
			groupID = 113;
		} else if (value.equals("CF2")) {
			groupID = 114;
		} else if (value.equals("CH2F")) {
			groupID = 115;
		} else if (value.equals("CHF")) {
			groupID = 116;
		} else if (value.equals("CF")) {
			groupID = 117;
		} else if (value.equals("ACF")) {
			groupID = 118;
		} else if (value.equals("F")) {
			groupID = 119;
		} else if (value.equals("CCl3F")) {
			groupID = 120;
		} else if (value.equals("CCl2F")) {
			groupID = 121;
		} else if (value.equals("HCCl2F")) {
			groupID = 122;
		} else if (value.equals("HCClF")) {
			groupID = 123;
		} else if (value.equals("CClF2")) {
			groupID = 124;
		} else if (value.equals("HCClF2")) {
			groupID = 125;
		} else if (value.equals("CClF3")) {
			groupID = 126;
		} else if (value.equals("CCl2F2")) {
			groupID = 127;
		} else if (value.equals("I")) {
			groupID = 128;
		} else if (value.equals("ACl")) {
			groupID = 129;
		} else if (value.equals("Br")) {
			groupID = 130;
		} else if (value.equals("ACBr")) {
			groupID = 131;
		} else if (value.equals("CH3NO2")) {
			groupID = 132;
		} else if (value.equals("CH2NO2")) {
			groupID = 133;
		} else if (value.equals("CHNO2")) {
			groupID = 134;
		} else if (value.equals("CNO2")) {
			groupID = 135;
		} else if (value.equals("ACNO2")) {
			groupID = 136;
		} else if (value.equals("NO2")) {
			groupID = 137;
		} else if (value.equals("CH3SH")) {
			groupID = 138;
		} else if (value.equals("CH2SH")) {
			groupID = 139;
		} else if (value.equals("CHSH")) {
			groupID = 140;
		} else if (value.equals("CSH")) {
			groupID = 141;
		} else if (value.equals("CH3S")) {
			groupID = 142;
		} else if (value.equals("CH2S")) {
			groupID = 143;
		} else if (value.equals("CHS")) {
			groupID = 144;
		} else if (value.equals("CS")) {
			groupID = 145;
		} else if (value.equals("CS2")) {
			groupID = 146;
		} else if (value.equals("C4H4S")) {//Thiophene
			groupID = 147;
		} else if (value.equals("C4H3S")) {
			groupID = 148;
		} else if (value.equals("C4H2S")) {
			groupID = 149;
		} else if (value.equals("S,cy")) {
			groupID = 150;
		} else if (value.equals("ACSH")) {
			groupID = 151;
		} else if (value.equals("ACS")) {
			groupID = 152;
		} else if (value.equals("SH")) {
			groupID = 153;
		} else if (value.equals("CH3SOCH3")) {//Dimethyl sulfoxide
			groupID = 154;
		} else if (value.equals("SO")) {
			groupID = 155;
		} else if (value.equals("SO2")) {
			groupID = 156;
		} else if (value.equals("O(SO)O")) {//Sulfite
			groupID = 157;
		} else if (value.equals("O(SO2)")) {//Sulfonate
			groupID = 158;
		} else if (value.equals("SO4")) {
			groupID = 159;
		} else if (value.equals("ACSO")) {
			groupID = 160;
		} else if (value.equals("ACSO2")) {
			groupID = 161;
		} else if (value.equals("SO2,cy")) {
			groupID = 162;
		} else if (value.equals("DMF")) {//Dimethylformamide
			groupID = 163;
		} else if (value.equals("CON(Me)2")) {//CON(CH3)2
			groupID = 164;
		} else if (value.equals("CONMeCH2")) {//CON(CH3)CH2
			groupID = 165;
		} else if (value.equals("HCON2CH2")) {
			groupID = 166;
		} else if (value.equals("CON2H2")) {
			groupID = 167;
		} else if (value.equals("CONHCH3")) {
			groupID = 168;
		} else if (value.equals("HCONHCH2")) {
			groupID = 169;
		} else if (value.equals("CONHCH2")) {
			groupID = 170;
		} else if (value.equals("CONH2")) {
			groupID = 171;
		} else if (value.equals("HCONH")) {
			groupID = 172;
		} else if (value.equals("CONHCO")) {
			groupID = 173;
		} else if (value.equals("CONCO")) {
			groupID = 174;
		} else if (value.equals("ACCONH2")) {
			groupID = 175;
		} else if (value.equals("ACCONH")) {
			groupID = 176;
		} else if (value.equals("ACNHCOH")) {//PHENYL FORMAMIDE
			groupID = 177;
		} else if (value.equals("ACN(CO)H")) {
			groupID = 178;
		} else if (value.equals("ACNHCO")) {
			groupID = 179;
		} else if (value.equals("AC-NCO")) {
			groupID = 180;
		} else if (value.equals("NCO")) {//ISOCYANATE
			groupID = 181;
		} else if (value.equals("UREA")) {
			groupID = 182;
		} else if (value.equals("NH2CONH")) {
			groupID = 183;
		} else if (value.equals("NH2CON")) {
			groupID = 184;
		} else if (value.equals("NHCONH")) {
			groupID = 185;
		} else if (value.equals("NHCON")) {
			groupID = 186;
		} else if (value.equals("NCON")) {
			groupID = 187;
		} else if (value.equals("ACNHCON2")) {//NUMBER = # OF H ON N
			groupID = 188;
		} else if (value.equals("ACNHCON1")) {
			groupID = 189;
		} else if (value.equals("CH2OCH2")) {//Ethylene oxide
			groupID = 190;
		} else if (value.equals("CH2OCH")) {//Ethylene oxide
			groupID = 191;
		} else if (value.equals("CH2OC")) {//Ethylene oxide
			groupID = 192;
		} else if (value.equals("CHOCH")) {//Ethylene oxide
			groupID = 193;
		} else if (value.equals("CHOC")) {//Ethylene oxide
			groupID = 194;
		} else if (value.equals("COC")) {//Ethylene oxide
			groupID = 195;
		} else if (value.equals("ONO")) {//Nitrite
			groupID = 196;
		} else if (value.equals("ONO2")) {//Nitrate
			groupID = 197;
		} else if (value.equals("CHNOH")) {//Oxime
			groupID = 198;
		} else if (value.equals("CNOH")) {
			groupID = 199;
		} else if (value.equals("ACCHNOH")) {
			groupID = 200;
		} else if (value.equals("NMP")) {//N-Methylpyrrolidone
			groupID = 201;
		} else if (value.equals("MORFOLIN")) {//Morpholine
			groupID = 202;
		} else if (value.equals("SiH3")) {
			groupID = 203;
		} else if (value.equals("SiH2")) {
			groupID = 204;
		} else if (value.equals("SiH")) {
			groupID = 205;
		} else if (value.equals("Si")) {
			groupID = 206;
		} else if (value.equals("SiH2O")) {
			groupID = 207;
		} else if (value.equals("SiHO")) {
			groupID = 208;
		} else if (value.equals("SiO")) {
			groupID = 209;
		} else if (value.equals("PO4")) {
			groupID = 210;
		} else if (value.equals("PHO3")) {
			groupID = 211;
		} else if (value.equals("PO3")) {
			groupID = 212;
		} else if (value.equals("(P=O)O2")) {
			groupID = 213;
		} else if (value.equals("(PO3)OH")) {
			groupID = 214;
		} else if (value.equals("ACPO4")) {
			groupID = 215;
		} else if (value.equals("ACPO")) {
			groupID = 216;
		} else if (value.equals("ACP")) {
			groupID = 217;
		} else if (value.equals("PH")) {
			groupID = 218;
		} else if (value.equals("P")) {
			groupID = 219;
		}
		
		return groupID;
	}
}
