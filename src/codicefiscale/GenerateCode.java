package codicefiscale;

public class GenerateCode{	
	
	private static final int FEMALE_DAY_ADD_COSTANT = 40;

	/**
	 * metodo che unisce tutti i vari pezzi del codice fiscale e fa anche il controllo e 
	 * l'evventuale cambio di codice fiscale in caso di omocodia
	 * @param person l'oggetto con tutti i parametri utili
	 * @return restituisce la stringa intera del codice fiscale
	 */
	public static String generateFiscalCode(Person person) {

		String name = generateNameOrSurnameCode(person.getName(), true);
		String surname = generateNameOrSurnameCode(person.getSurname(), false);
		String date = generateDate(person.getBirthDate(), person);
		String fiscalCode = surname + name + date + person.getBirthPlace();
		fiscalCode += generateControlCharacter(fiscalCode);
		
//		while(XmlUtility.checkForAFiscalCode(fiscalCode)) {
//			TaxCodeUtility.changeLastNumberOfAFiscalCode(fiscalCode);
//			fiscalCode = fiscalCode.substring(0, 15); 
//			fiscalCode = fiscalCode + generateControlCharacter(fiscalCode.substring(0, 15));
//		}
		return fiscalCode;
	}
	
	/**
	 * genera il carattere di controllo su una sequenza parziale del codice fiscale
	 * @param fiscalCode la sequenza dei primi "15 caratteri"
	 * @return restituisce il carattere come stringa
	 */
	public static String generateControlCharacter(String fiscalCode) {
		int sum = 0;
		
		for (int i = 0; i<fiscalCode.length(); i++) 
			if((i+1)%2 == 0)
				sum += ControlCHCostants.getEnumFromChar(fiscalCode.toCharArray()[i]).getEqual();
			else
				sum += ControlCHCostants.getEnumFromChar(fiscalCode.toCharArray()[i]).getOdd();
		
		return ControlCHCostants.getEnumFromRestOrOmocodia(sum%26).toString();
	}
	
	/**
	 * metodo che codifica un nome o un cognome per il codice fiscale
	 * @param input il nome o il cognome
	 * @param assertName true se è un nome, false per il cognome
	 * @return una stringa lunga 3 caratteri
	 */
	public static String generateNameOrSurnameCode(String input, boolean assertName) {
		String vocals = "";
		String consonants = "";
		input = input.toUpperCase();
		
		for (int i = 0; i<input.length(); i++) {
			if (TaxCodeUtility.isAVocal(input.toCharArray()[i]))
				vocals += input.toCharArray()[i];
			else
				consonants += input.toCharArray()[i];
		}
		
		if (assertName && consonants.length() >= 4)
			consonants = consonants.toCharArray()[0] + consonants.substring(2);
		
		return (consonants + vocals + "XXX").substring(0, 3);
	}
	
	/**
	 * prende i 3 pezzi della data e genera la stringa per intero
	 * @param date l'oggetto data da dove prelevare i parametri singoli
	 * @param person l'oggetto persona da dove prelevare il sesso
	 * @return restituisce una stringa dela tipo yyMdd
	 */
	public static String generateDate(Date date, Person person) {
		return generateYear(date.getYear()) + Month.getMonthLetterFromIndex(date.getMonth()) + generateDay(date.getDay(), person.getSex());
	}
	
	/**
	 * prende solo le ultime 2 cifre dell'anno
	 * @param year il valore del anno per intero
	 * @return una stringa lunga 2 caratteri
	 */
	public static String generateYear(int year) {
		if (String.valueOf(year%1000%100).length() > 1)
			return String.valueOf(year%1000%100);
		else
			return "0" + String.valueOf(year%1000%100);
	}
	
	/**
	 * restituisce il giorno di nascita come Stringa codificato da 01 a 31 e 41 a 71
	 * @param intDay il giorno di nascita 
	 * @param sex il sesso per sapere se sommare la costante
	 * @return resituisce una stringa lunga 2 caratteri, viene messo lo 0 evventualmente
	 */
	public static String generateDay(int intDay, char sex) {
		String stringDay = "0";
		
		if (sex == 'F')
			intDay += FEMALE_DAY_ADD_COSTANT;
		stringDay += String.valueOf(intDay);
		
		if (stringDay.length() >2)
			stringDay = stringDay.substring(1);
		
		return stringDay;
	}
	
	
	
}




