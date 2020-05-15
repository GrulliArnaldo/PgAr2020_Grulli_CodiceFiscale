package codicefiscale;

public class TaxCodeUtility {

	/**
	 * Metodo che controlla se il carattere immesso é una vocale
	 * @param Carattere da controllare
	 * @return restituisce true se é una vocal, false in caso contrario
	 */
	public static boolean isAVocal(char toBeChecked) {
		boolean vocal = false;
		if(toBeChecked == 'A' || toBeChecked == 'E' || toBeChecked == 'I'|| toBeChecked == 'O'|| toBeChecked == 'U')
			vocal = true;
		return vocal;
	}
	
	/**
	 * controlla se un char è un numero
	 * @param number il char da ricercare
	 * @return restituisce true se è un numero, false altrimenti
	 */
	public static boolean isANumber(char number) {
		if (number >= '0' && number <= '9')
			return true;
		else
			return false;
	}
	
	/**
	 * controlla se un char è una lettera
	 * @param letter il char da controllare
	 * @return true se è una lettera, false altrimenti
	 */
	public static boolean isALetter(char letter) {
		if (letter >= 'A' && letter <= 'Z')
			return true;
		else
			return false;
	}
	
	/**
	 * cambia l'ultima cifra presente in un codice fiscale
	 * @param fiscalCode il codice fiscale originario
	 * @return se trova la cifra restituisce il codice fiscale modificato con il carattere adatto dalla tabella dell'omocodia, altrimenti la stringa originale
	 */
	public static String changeLastNumberOfAFiscalCode(String fiscalCode) {
		char[] array = new char[fiscalCode.length()];
		System.arraycopy(fiscalCode.toCharArray(), 0, array, 0, fiscalCode.length());
		for (int i = array.length-1; i>5; i--) {
			if (TaxCodeUtility.isANumber(array[i])) {
				array[i] = ControlCHCostants.getEnumFromChar(array[i]).getRestOrOmocodia().toCharArray()[0];
				return String.valueOf(array);
			}
		}
		return fiscalCode;
	}
	

	
}