package codicefiscale;

public enum ControlCHCostants {
	N0(1, 0, "L"),
	N1(0, 1, "M"),
	N2(5, 2, "N"),
	N3(7, 3, "P"),
	N4(9, 4, "Q"),
	N5(13, 5, "R"),
	N6(15, 6, "S"),
	M7(17, 7, "T"),
	N8(19, 8, "U"),
	N9(21, 9, "V"),
	A(1, 0, "0"),
	B(0, 1, "1"),
	C(5, 2, "2"),
	D(7, 3, "3"),
	E(9, 4, "4"),
	F(13, 5, "5"),
	G(15, 6, "6"),
	H(17, 7, "7"),
	I(19, 8, "8"),
	J(21, 9, "9"),
	K(2, 10, "10"),
	L(4, 11, "11"),
	M(18, 12, "12"),
	N(20, 13, "13"),
	O(11, 14, "14"),
	P(3, 15, "15"),
	Q(6, 16, "16"),
	R(8, 17, "17"),
	S(12, 18, "18"),
	T(14, 19, "19"),
	U(16, 20, "20"),
	V(10, 21, "21"),
	W(22, 22, "22"),
	X(25, 23, "23"),
	Y(24, 24, "24"),
	Z(23, 25, "25");
	
	private int equal;
	private int odd;
	private String restOrOmocodia;
	
	private ControlCHCostants(int odd, int equal, String restOrOmocodia) {
		this.equal = equal;
		this.odd = odd;
		this.restOrOmocodia = restOrOmocodia;
	}
	
	/**
	 * migliorato il metodo di base del toString, per togliere la "N" davanti alle cifre
	 */
	public String toString() {
		if (super.toString().length() == 2)
			return super.toString().substring(1);
		else
			return super.toString();
	}
			
	/**
	 * dato un char in input, restituisce l'oggetto enum
	 * @param ch il char da ricercare nel nome
	 * @return l'ogetto enum
	 */
	public static ControlCHCostants getEnumFromChar(char ch) {
		for (int i = 0; i<ControlCHCostants.values().length; i++)
			if (String.valueOf(ch).equalsIgnoreCase(ControlCHCostants.values()[i].toString()))
				return ControlCHCostants.values()[i];
		return null;
	}
	
	/**
	 * dato il valore del resto da ricercare, resituisce il corrispondente oggetto enum
	 * @param num il resto
	 * @return l'enum corrispondente
	 */
	public static ControlCHCostants getEnumFromRestOrOmocodia(int num) {
		for (int i = 0; i<ControlCHCostants.values().length; i++)
			if (String.valueOf(num).equalsIgnoreCase(ControlCHCostants.values()[i].getRestOrOmocodia()))
				return ControlCHCostants.values()[i];
		return null;
	}


	//GETTERS AND SETTERS
	public int getEqual() {
		return equal;
	}

	public void setEqual(int equal) {
		this.equal = equal;
	}

	public int getOdd() {
		return odd;
	}

	public void setOdd(int odd) {
		this.odd = odd;
	}

	public String getRestOrOmocodia() {
		return restOrOmocodia;
	}

	public void setRestOrOmocodia(String restOrOmocodia) {
		this.restOrOmocodia = restOrOmocodia;
	}
	
	
}
