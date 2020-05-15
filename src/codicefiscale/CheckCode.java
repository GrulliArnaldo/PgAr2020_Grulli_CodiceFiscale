package codicefiscale;

public class CheckCode {
	
	private static final int MIN_DAYS = 1;
	private static final int CODE_SIZE = 16;
	private static final int FEMALE_DAY_ADD_COSTANT = 40;

	/**
	 * mette insieme tutti i controlli parziali e fa l'ultimo controllo sul carattere di controllo
	 * @param fiscalCode il codice fiscale da controllare
	 * @return true se tutti i controlli passano, false altrimenti
	 */
	public static boolean checkFiscalCode(String fiscalCode) {
		String subFiscalCode = fiscalCode.substring(0, 15);
		String checkCharacter = fiscalCode.substring(15);
		
		if(checkLenght(fiscalCode))
			if(checkNameAndSurname(fiscalCode) && checkYear(fiscalCode) && checkBirthDay(fiscalCode) && checkBirthPlace(fiscalCode) && 
					GenerateCode.generateControlCharacter(subFiscalCode).equalsIgnoreCase(checkCharacter))
				return true;
		
		return false;
	}
	
	/**
	 * controlla se la lunghezza della stringa corrisponde a CODE_SIZE
	 * @param fiscalClde da controllare
	 * @return true se la lunghezza coincide, false altrimenti
	 */
	public static boolean checkLenght(String fiscalCode) {
		if(fiscalCode.length() == CODE_SIZE) 
			return true;
		return false;
	}
	
	/**
	 * controlla se i primi 6 caratteri sono delle lettere
	 * @param fiscalCode il codice fiscale da controllare
	 * @return true se sono tutte lettere, false altrimenti
	 */
	public static boolean checkNameAndSurname(String fiscalCode) {
		String birthPlaceCode = fiscalCode.substring(0, 6).toUpperCase();
		int count = 0;
		
		for(int i = 0; i < birthPlaceCode.length(); i++)
			if(TaxCodeUtility.isALetter(birthPlaceCode.toCharArray()[i]))
				count++;
		
		if(count == birthPlaceCode.length())
			return true;
		return false;
	}
	
	/**
	 * controlla che l'anno sia composto soltanto da cifre
	 * @param fiscalCode codice fiscale da controllare
	 * @return true se sono 2 cifre, false altrimenti
	 */
	public static boolean checkYear(String fiscalCode) {
		String year = fiscalCode.substring(6, 8);
		
		if (TaxCodeUtility.isANumber(year.toCharArray()[0]) && 
			TaxCodeUtility.isANumber(year.toCharArray()[1]))
				return true;
		return false;
	}
	
	/**
	 * controlla se la lettera del mese ha una corrispondenza, e nel caso ci fosse,
	 * controlla se il giorno di nascita sta nel range massimo possibile di giorni
	 * @param fiscalCode da controllare
	 * @return true se tutti i controlli vanno a buon fine, false altrimenti
	 */
	public static boolean checkBirthDay(String fiscalCode) {
		String birthday = fiscalCode.substring(8, 11).toUpperCase();
		int maxMonthDays = Month.getAMonthDayFromRelativeLetter(birthday.toCharArray()[0]);
		int days = 0;
		
		if (!(maxMonthDays == -1)){
			days = (birthday.toCharArray()[1] - 48)*10 + (birthday.toCharArray()[2] - 48);
			if((days >= MIN_DAYS && days <= maxMonthDays) || (days >= MIN_DAYS+FEMALE_DAY_ADD_COSTANT && days <= maxMonthDays + FEMALE_DAY_ADD_COSTANT))
				return true;
		}
		return false;
	}
	
	/**
	 * controlla se i primi il comune è composto da 1 lettera e 3 cifre
	 * @param fiscalCode il codice fiscale da controllare
	 * @return true se corrisponde, false altrimenti
	 */
	public static boolean checkBirthPlace(String fiscalCode) {
		String birthPlaceCode = fiscalCode.substring(11, 15);
		
		if(TaxCodeUtility.isALetter(birthPlaceCode.toCharArray()[0]))
			if (TaxCodeUtility.isANumber(birthPlaceCode.toCharArray()[1]) && 
				TaxCodeUtility.isANumber(birthPlaceCode.toCharArray()[2]) && 
				TaxCodeUtility.isANumber(birthPlaceCode.toCharArray()[3]))
					return true;
		
		return false;
	}
	
}
