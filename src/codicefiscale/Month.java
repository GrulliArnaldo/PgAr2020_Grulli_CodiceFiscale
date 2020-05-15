package codicefiscale;

public enum Month {
	
	GENNAIO('A', 31), 
	FEBBRAIO('B', 28),
	MARZO('C', 31),
	APRILE('D', 30), 
	MAGGIO('E', 31), 
	GIUGNO('H', 30), 
	LUGLIO('L', 31), 
	AGOSTO('M', 31), 
	SETTEMBRE('P', 30), 
	OTTOBRE('R', 31), 
	NOVEMBRE('S', 30), 
	DICEMBRE('T', 31);
	
	private char relativeLetter;
	private int days;
	
	private Month(char letter, int days) {
		this.relativeLetter = letter;
		this.days = days;
	}
	
	/**
	 * dato in input il numero del mese, restituisce la lettera corrispondente per il codice fiscale
	 * @param index valore che verrà decrementato di 1 per corrispondere al indice dell'array
	 * @return restituisce la lettera come stringa
	 */
	public static String getMonthLetterFromIndex(int index) {
		return String.valueOf(Month.values()[index-1].relativeLetter);
	}
	
	/**
	 * controlla se la lettera corrisponde a una lettera relativa a un mese
	 * @param ch parametro da controllare
	 * @return restituisce i giorni del mese se lo trova, -1 altrimenti
	 */
	public static int getAMonthDayFromRelativeLetter(char ch) {
		for (int i = 0; i< Month.values().length; i++)
			if (ch == Month.values()[i].getRelativeLetter())
				return Month.values()[i].getDays();
		return -1;
	}

	//GETTERS AND SETTERS
	public char getRelativeLetter() {
		return relativeLetter;
	}

	public void setRelativeLetter(char relativeLetter) {
		this.relativeLetter = relativeLetter;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}
	
	
	
}
