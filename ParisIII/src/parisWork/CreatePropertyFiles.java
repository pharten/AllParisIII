package parisWork;

import java.io.*;
import java.util.*;

public class CreatePropertyFiles {


	  /** parses a delimited string into a list- accounts for the fact that can have quotation marks in comma delimited lines
	   * 
	   * @param Line - line to be parsed
	   * @param Delimiter - character used to separate line into fields
	   * @return
	   */
	  public static java.util.LinkedList Parse3(String Line, String Delimiter) {


		    java.util.LinkedList myList = new LinkedList();

		    int tabpos = 1;

		    while (tabpos > -1) {

		    	tabpos = Line.indexOf(Delimiter);
		    	
		    	if (Line.length()<1) break;
		    	
		    	if (Line.substring(0,1).equals("\"")) {
		    		Line=Line.substring(1,Line.length()); // kill first " mark
		    		
		    		if (Line.length()==0) break;
		    		
		    		myList.add(Line.substring(0, Line.indexOf("\"")));
		    		
		    		if (Line.indexOf("\"")<Line.length()-1)
		    			Line = Line.substring(Line.indexOf("\"") + 2, Line.length());
		    		else 
		    			break;
		    	} else {
					

					if (tabpos > 0) {
						myList.add(Line.substring(0, tabpos));
						Line = Line.substring(tabpos + 1, Line.length());
					} else if (tabpos == 0) {
						myList.add("");
						Line = Line.substring(tabpos + 1, Line.length());
					} else {
						myList.add(Line.trim());
					}

		    	}
		    			
			}// end while loop

		    
//		    for (int j = 0; j <= myList.size() - 1; j++) {
//				System.out.println(j + "\t" + myList.get(j));					
//			}
		    
		    return myList;

		  }
	
	  Hashtable<String,Hashtable> loadPropredCranium() {
		
		try {

			String filepath="Calcs/Propred_Cranium.txt";
			
			BufferedReader br=new BufferedReader(new FileReader(filepath));
			
			String header=br.readLine();
			
			LinkedList <String>llHeader=Parse3(header, "\t");
			
			Hashtable<String,Hashtable>data=new Hashtable<String,Hashtable>();
			
			while (true) {
				String Line=br.readLine();
				if (Line==null) break;
//				System.out.println(Line);
				
				LinkedList <String>llLine=Parse3(Line, "\t");
				
				Hashtable <String,String>htLine=new Hashtable<String,String>();
				
				for (int i=0;i<llHeader.size();i++) {
					htLine.put(llHeader.get(i),llLine.get(i));
				}
				data.put(htLine.get("CAS"), htLine);
			}
			
			//1000-70-0	186.4			684.74
//			System.out.println(data.get("1000-70-0").get("PM_CT (K)"));

			
			br.close();
			
			return data;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		
	}
	
	  
	double calcLeeKeslerVP(Hashtable data,double TempK) {
		
		if (data==null) {
			return -9999;
		}

		double Tc=-9999;
		double Pc=-9999;
		double omega=-9999;

		String strTc=(String)(data.get("PM_CT (K)"));
		String strPc=(String)(data.get("PM_CP (bar)"));
		String strOmega=(String)(data.get("PM_omega"));

		
		if (strTc.equals("") || strPc.equals("") || strOmega.equals("")) {
			return -9999;
		}
		
		Tc=Double.parseDouble(strTc);
		Pc=Double.parseDouble(strPc);
		omega=Double.parseDouble(strOmega);
		
		double Tr=TempK/Tc;
		
		double f0=5.92714-6.09648/Tr-1.28862*Math.log(Tr)+0.169347*Math.pow(Tr,6);
		double f1=15.2518-15.6875/Tr-13.4721*Math.log(Tr)+0.43577*Math.pow(Tr, 6);
		
		double VP=(Math.exp(f0+(omega*f1)))*Pc;
		VP*=750.061683;// convert to mmHg
		
//		System.out.println(VP);
		
		return VP;
	}
	
	void go () {
//		String endpoint="WS";
//		String propredPropName="PM_WS (log(mg/L))";
//		String craniumPropName="N/A";
		
//		String endpoint="V";
//		String propredPropName="PM_Viscosity (cp)";
//		String craniumPropName="Cr_Viscosity (Pa.s)";

//		String endpoint="TC";
//		String propredPropName="PM_TC (mW/mK)";
//		String craniumPropName="Cr_TC (W/m K)";
		
//		String endpoint="ST";
//		String propredPropName="PM_ST (dyn/cm)";
//		String craniumPropName="Cr_ST (N/m)";

//		String endpoint="FP";
//		String propredPropName="PC_FP (K)";
//		String craniumPropName="Cr_FP (K)";

		String endpoint="D";
		String propredPropName="PM_Density (g/cc)";
		String craniumPropName="Cr_Density (kg/m3)";

//		String endpoint="LD50";
//		String propredPropName="PM_LD50 (log(mol/kg))";
//		String craniumPropName="N/A";

//		String endpoint="LC50";
//		String propredPropName="N/A";
//		String craniumPropName="N/A";

//		String endpoint="BP";
//		String propredPropName="PM_BP (K)";
//		String craniumPropName="Cr_BP (K)";
		
//		String endpoint="MP";
//		String propredPropName="PM_MP (K)";
//		String craniumPropName="Cr_MP (K)";

//		String endpoint="VP";
//		String propredPropName="PM_VP (298K, bar)";
//		String craniumPropName="Cr_VP (Pa)";

		Hashtable<String,Hashtable>data=this.loadPropredCranium();
		this.goThroughHCFile(endpoint, data,propredPropName,craniumPropName);
		
	}
	
	void goThroughHCFile(String endpoint,Hashtable<String,Hashtable>data,String propredPropName,String craniumPropName) {
		
		try {

			String filepath="Calcs/"+endpoint+".txt";
			
			BufferedReader br=new BufferedReader(new FileReader(filepath));
			
			FileWriter fw=new FileWriter("Calcs/"+endpoint+"_final.txt");
			
			String header=br.readLine();
			
			LinkedList <String>llHeader=Parse3(header, "\t");
//			System.out.println("CAS\t"+endpoint+"_Final\t"+endpoint+"_FinalSource");
			
			String d="\t";
//			fw.write("CAS"+d+endpoint+"_Final"+d+endpoint+"_FinalSource\r\n");

			if (endpoint.equals("VP")) {
				fw.write(endpoint+"_Propred"+d+endpoint+"_Cranium"+d+endpoint+"_LeeKesler"+d+endpoint+"_Final"+d+endpoint+"_FinalSource\r\n");
			} else {
				fw.write(endpoint+"_Propred"+d+endpoint+"_Cranium"+d+endpoint+"_Final"+d+endpoint+"_FinalSource\r\n");	
			}
			
			
			while (true) {
				String Line=br.readLine();
				if (Line==null) break;
//				System.out.println(Line);
				
				LinkedList <String>llLine=Parse3(Line, "\t");
				
				String CAS=llLine.get(0);
				String Exp=llLine.get(llLine.size()-2);
				String HCPred=llLine.get(llLine.size()-1);
//				System.out.println("CAS="+CAS);
				
				Hashtable<String,String>record=data.get(CAS);
				String Propred="N/A";
				String Cranium="N/A";
				
				if (record!=null) {
					if (!propredPropName.equals("N/A")) {
						Propred=(String)(data.get(CAS).get(propredPropName));
						if (Propred.equals("")) Propred="N/A";
						
//						System.out.println(CAS+"\t"+Propred);
					}
					if (!craniumPropName.equals("N/A")) {
						Cranium=(String)(data.get(CAS).get(craniumPropName));
						if (Cranium.equals("")) Cranium="N/A";
					}
					
					
				}
				
				double propFinal=-9999;
				String propFinalSource="N/A";
				
				double dExp=Double.parseDouble(Exp);
				double dHCPred=Double.parseDouble(HCPred);
				double dPropred=-9999;
				double dCranium=-9999;
				double dLeeKesler=-9999;
				
				if (!Propred.equals("N/A")) {
					dPropred=Double.parseDouble(Propred);
				}
				
				if (!Cranium.equals("N/A")) {
					dCranium=Double.parseDouble(Cranium);
				}

				
				
				//Unit conversions:
				
				if (endpoint.equals("VP") && !Propred.equals("N/A")) {
					dPropred*=750.061683;
				}
				
				if (endpoint.equals("VP") && !Cranium.equals("N/A")) {
					dCranium*=0.00750061683;
				}

				if (endpoint.equals("VP")) {
					dLeeKesler=this.calcLeeKeslerVP(data.get(CAS),298.15);
				}
				
				if (endpoint.equals("BP")) {
					double BPLK=this.calculateBP_LK(data.get(CAS));
					System.out.println(CAS+"\t"+BPLK);
				}
				
				
				if (endpoint.equals("WS") && !Propred.equals("N/A")) {
					//convert from log10 mg/L to mg/L:
					dPropred=Math.pow(10, dPropred);
				}
				
				if(endpoint.equals("V") && !Cranium.equals("N/A")) {
					dCranium*=1000;//convert from Pas to cP	
				}
				
				if(endpoint.equals("TC") && !Cranium.equals("N/A")) {
					dCranium*=1000;//convert from W/mK to mW/mk	
				}
				
				if(endpoint.equals("D") && !Cranium.equals("N/A")) {
					dCranium/=1000;//convert from W/mK to mW/mk	
				}
				
				if(endpoint.equals("ST") && !Cranium.equals("N/A")) {
					dCranium*=1000;//convert from N/m to dyne/cm	
				}
				
				
				if(endpoint.equals("BP") && !Cranium.equals("N/A")) {
					dCranium-=273.15;//convert from N/m to dyne/cm	
				}
				if(endpoint.equals("BP") && !Propred.equals("N/A")) {
					dPropred-=273.15;//convert from N/m to dyne/cm	
				}
				
				if(endpoint.equals("MP") && !Cranium.equals("N/A")) {
					dCranium-=273.15;//convert from N/m to dyne/cm	
				}
				if(endpoint.equals("MP") && !Propred.equals("N/A")) {
					dPropred-=273.15;//convert from N/m to dyne/cm	
				}


				if (endpoint.equals("LD50") && !Propred.equals("N/A")) {
					//convert from -Log10(mol/kg) to mg/kg:
					
					String MW=(String)(data.get(CAS).get("PM_MW"));
					double dMW=Double.parseDouble(MW);
					
					dPropred=1000*dMW*Math.pow(10,-dPropred);
						
//					System.out.println(CAS+"\t"+dPropred);
					
				}
				
				
				if(endpoint.equals("FP")) {
					if(!Cranium.equals("N/A")) {
						dCranium-=273.15;//convert from N/m to dyne/cm
					}
					if(!Propred.equals("N/A")) {
						dPropred-=273.15;//convert from N/m to dyne/cm
					}
				}

				if (endpoint.equals("LD50") || endpoint.equals("LC50") ) {
					
					if (dExp>-9999) {
						propFinal=dExp;
						propFinalSource="Exp";
					} else if(dHCPred>-9999) {
						propFinal=dHCPred;
						propFinalSource="Pred-HC";
//						System.out.println("here");
					}//dont use propred or cranium  
					
					
				} else if (endpoint.equals("WS") || endpoint.equals("MP")) {
					if (dExp>-9999) {
						propFinal=dExp;
						propFinalSource="Exp";
					} else if(dHCPred>-9999) {
						propFinal=dHCPred;
						propFinalSource="Pred-HC";
					} else if(!Propred.equals("N/A")) {
						propFinal=dPropred;
						propFinalSource="Pred-Propred";//dont use cranium
					}
				} else if (endpoint.equals("V") || endpoint.equals("TC") || endpoint.equals("BP")) {
					if (dExp>-9999) {
						propFinal=dExp;
						propFinalSource="Exp";
					} else if(dHCPred>-9999) {
						propFinal=dHCPred;
						propFinalSource="Pred-HC";
					} else if(!Propred.equals("N/A")) {
						propFinal=dPropred;
						propFinalSource="Pred-Propred";
					} else if (!Cranium.equals("N/A")) {
						propFinal=dCranium;
						propFinalSource="Pred-Cranium";
					}
					
				} else if (endpoint.equals("ST") || endpoint.equals("FP") || endpoint.equals("D")) {
					if (dExp>-9999) {
						propFinal=dExp;
						propFinalSource="Exp";
					} else if(dHCPred>-9999) {
						propFinal=dHCPred;
						propFinalSource="Pred-HC";
					} else if (!Cranium.equals("N/A")) {
						propFinal=dCranium;
						propFinalSource="Pred-Cranium";
					} else if(!Propred.equals("N/A")) {
						propFinal=dPropred;
						propFinalSource="Pred-Propred";
					}
					
				} else if (endpoint.equals("VP")) {
					if (dExp>-9999) {
						propFinal=dExp;
						propFinalSource="Exp";
					} else if(dHCPred>-9999) {
						propFinal=dHCPred;
						propFinalSource="Pred-HC";
					} else if(dLeeKesler>-9999) {
						propFinal=dLeeKesler;
						propFinalSource="Pred-Lee_Kesler";
					} else if (!Cranium.equals("N/A")) {
						propFinal=dCranium;
						propFinalSource="Pred-Cranium";
					} else if(!Propred.equals("N/A")) {
						propFinal=dPropred;
						propFinalSource="Pred-Propred";
					}
					
				}

				//FP, BP, MP
				
				

				boolean tempEndPoint=false;
				
				if (endpoint.equals("FP") || endpoint.equals("MP") || endpoint.equals("BP")) {
					tempEndPoint=true;
				}
				
				if (propFinal<0 && !tempEndPoint) {
					propFinal=-9999;
					propFinalSource="N/A";
				}
				
//				System.out.println(CAS+"\t"+Exp+"\t"+HCPred+"\t"+Propred);
				
//				System.out.println(CAS+"\t"+propFinal+"\t"+propFinalSource);
//				fw.write(CAS+d+propFinal+d+propFinalSource+"\r\n");
				
				if (endpoint.equals("VP")) {
					fw.write(dPropred+d+dCranium+d+dLeeKesler+d+propFinal+d+propFinalSource+"\r\n");
				} else {
					fw.write(dPropred+d+dCranium+d+propFinal+d+propFinalSource+"\r\n");
				}

				
				fw.flush();
//				if (dExp>-9999 && dPropred>-9999) {
//					System.out.println(CAS+"\t"+dExp+"\t"+dPropred);
//				}
				
			}
			
			fw.close();
			br.close();
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}
	}
	
	
	private double calculateBP_LK(Hashtable data) {

		if (data==null) return -9999;
		
		int maxIterations=1000;

		double Tc=-9999;

		String strTc=(String)(data.get("PM_CT (K)"));
		
//		System.out.println(strTc);
		
		if (strTc.equals("")) {
			return -9999;
		}
			
		Tc=Double.parseDouble(strTc);
		
		if (Tc<0) return -9999; 
		
		//For initial guess use linear function of critical temperature:
		double BPmixKinit=0.7348*Tc - 32.54;
		
		
//		System.out.println(BPmixKinit);
		double frac=0.5;
		double small=1e-3;
		double Pressure_kPa=760;
		
		
		while (true) {

			double BPmixK=BPmixKinit; //reset to initial guess

			for (int i = 0; i < maxIterations; i++) {
				double fx = Pressure_kPa - this.calcLeeKeslerVP(data,BPmixK);
				double fx2 = Pressure_kPa - this.calcLeeKeslerVP(data,BPmixK+small);;
				double fprime = (fx2 - fx) / (small); // estimate derivative using numerical method

				double dx = fx / fprime;

//				BPmixK-=dx;
				BPmixK = frac*(BPmixK-dx) + (1-frac)*BPmixK; //only take partial step towards new value

//				System.out.println(BPmixK);
				if (BPmixK<0 || BPmixK>1500) break;

				if (Math.abs(dx) < 0.0001) {
					// System.out.println("Converged after "+i+" iterations:"+BPmixK);
					return BPmixK-273.15;
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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		CreatePropertyFiles c=new CreatePropertyFiles ();
		c.go();

	}

}
