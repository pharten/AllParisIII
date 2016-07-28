package parisInit;


/*
Property			SI			Common		CGS			US
--------------		---------	-------		-----		-------
mass				kg/kmol		g/mol		g/mol		lb/mol
pressure			kPa			mmHg		Pa			atm
temperature			K			C			C			F	
density				kg/m3		g/cm3		g/cm3		lb/ft3
viscosity			kg/(m-s)	cP			g/(cm-s)	lb/(ft-s)
thermal cond.		J/(m-s-K)	mW/(m-K)	cal/(cm-s-K)Btu/(ft-s-R)		
surface tension		kg/s2		dyn/cm		g/s2		lb/s2
heat capacity		J/(kmol-K)	cal/(mol-K)	cal/(mol-K)	Btu/(mol-R)	
*/

public enum Units {
	SI,
	COMMON,
	CGS,
	US;

	public static double massConvertTo(double mass, Units units) {
		switch (units) {
		case US:
			return mass / 453.6;
		default:
			return mass;
		}
	}
	
	public static double massConvertFrom(double mass, Units units) {
		switch (units) {
		case US:
			return mass * 453.6;
		default:
			return mass;
		}
	}
	
	/**
	 * Converts the temperature from K to Common (C) or US (F)
	 * @param temp
	 * @param units
	 * @return
	 */
	public static double tempConvertTo(double temp, Units units) {
		switch (units) {
		case COMMON: case CGS:
			return temp - 273.15;
		case US:
			return (temp-273.15)*(9.0/5.0) + 32.0;
		default:
			return temp;
		}
	}
	
	/**
	 * Converts the temperature to K from COMMON (C) or US (F)
	 * @param temp
	 * @param units
	 * @return
	 */
	public static double tempConvertFrom(double temp, Units units) {
		switch (units) {
		case COMMON: case CGS:
			return temp + 273.15;
		case US:
			return (temp-32.0)*(5.0/9.0) + 273.15;
		default:
			return temp;
		}
	}
	
	public static double solubConvertTo(double solub, Units units) {
		switch (units) {
		case COMMON: case CGS:
			return solub;
		case US:
			return solub;
		default:
			return solub;
		}
	}
	
	public static double solubConvertFrom(double solub, Units units) {
		switch (units) {
		case COMMON: case CGS:
			return solub;
		case US:
			return solub;
		default:
			return solub;
		}
	}
	
	public static double densityConvertTo(double density, Units units) {
		switch (units) {
		case COMMON: case CGS:
			return density / 1000.0;
		case US:
			return density * 0.0624;
		default:
			return density;
		}
	}
	
	public static double densityConvertFrom(double density, Units units) {
		switch (units) {
		case COMMON: case CGS:
			return density * 1000.0;
		case US:
			return density / 0.0624 ;
		default:
			return density;
		}
	}
	
	public static double pressureConvertTo(double pressure, Units units) {
		switch (units) {
		case COMMON://mmHg
			return pressure * 7.502;
		case CGS://Pa
			return pressure * 1000.0;
		case US:
			return pressure / 101.325;
		default:
			return pressure;
		}
	}
	
	public static double pressureConvertFrom(double pressure, Units units) {
		switch (units) {
		case COMMON:
			return pressure / 7.502;
		case CGS:
			return pressure / 1000.0;
		case US:
			return pressure * 101.325;
		default:
			return pressure;
		}
	}
	
	public static double hcapConvertTo(double hcap, Units units) {
		switch (units) {
		case COMMON: case CGS:
			return hcap / 4184.1;
		case US:
			return hcap / 1899000.0 ;
		default:
			return hcap;
		}
	}
	
	public static double hcapConvertFrom(double hcap, Units units) {
		switch (units) {
		case COMMON: case CGS:
			return hcap * 4184.1;
		case US:
			return hcap * 1899000 ;
		default:
			return hcap;
		}
	}
	
	public static double viscosityConvertTo(double visc, Units units) {
		switch (units) {
		case COMMON:
			return visc * 1000.0;
		case CGS:
			return visc * 10.0;
		case US:
			return visc * 0.6723;
		default:
			return visc;
		}
	}
	
	public static double viscosityConvertFrom(double visc, Units units) {
		switch (units) {
		case COMMON://cP
			return visc / 1000.0;
		case CGS:
			return visc / 10.0;
		case US:
			return visc / 0.6723;
		default:
			return visc;
		}
	}
	
	public static double thermalConductivityConvertTo(double therm, Units units) {
		switch (units) {
		case COMMON:
			return therm * 1000.0;
		case CGS:
			return therm / 418.41;
		case US:
			return therm / 6228.7;
		default:
			return therm;
		}
	}
	
	public static double thermalConductivityConvertFrom(double therm, Units units) {
		switch (units) {
		case COMMON:
			return therm / 1000.0;
		case CGS:
			return therm * 418.41;
		case US:
			return therm * 6228.7;
		default:
			return therm;
		}
	}
	
	public static double surfaceTensionConvertTo(double surf, Units units) {
		switch (units) {
		case COMMON: case CGS:
			return surf * 1000.0;
		case US:
			return surf * 2.205;
		default:
			return surf;
		}
	}
	
	public static double surfaceTensionConvertFrom(double surf, Units units) {
		switch (units) {
		case COMMON: case CGS:
			return surf / 1000.0;
		case US:
			return surf / 2.205;
		default:
			return surf;
		}
	}
	
}
