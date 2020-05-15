package codicefiscale;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class XmlUtility {
	
	/**
	 * metodo che inizializza un writer
	 * @param filename il nome del file dove dobbiamo scrivere
	 * @return restituisce l'oggetto xmlstreamwriter
	 */
	public static XMLStreamWriter initializeXmlWriter(String filename) {
		XMLOutputFactory xmloutput = null;
		XMLStreamWriter xmlwriter = null;
		try {
			 xmloutput = XMLOutputFactory.newInstance();
			 xmlwriter = xmloutput.createXMLStreamWriter(new FileOutputStream(filename), "utf-8");
			 xmlwriter.writeStartDocument("utf-8", "1.0");
		} catch (Exception e) {
			 System.out.println("Errore nell'inizializzazione del writer:");
			 System.out.println(e.getMessage());
		}
		return xmlwriter;
	}
	
	/**
	 * metodo che inizializza un reader
	 * @param filename il nome del file dove dobbiamo leggere
	 * @return restituisce l'oggetto xmlstreamreader
	 */
	public static XMLStreamReader initializeXmlReader(String fileName) {
		XMLInputFactory xmlinput = null;
		XMLStreamReader xmlreader = null;
		try {
			xmlinput = XMLInputFactory.newInstance();
			xmlreader = xmlinput.createXMLStreamReader(fileName, new FileInputStream(fileName));
		}catch (Exception e) {
			System.out.println("Errore nell'inizializzazione del reader: ");
			System.out.println(e.getMessage());
		}
		return xmlreader;
	}
	
	/**
	 * estrapola i dati delle persone dal file 
	 * @param xmlreader il reder del file giusto deve essere dato
	 * @return restituisce un arraylist di tipo person
	 */
	public static ArrayList<Person> getPersonListFromXml(XMLStreamReader xmlreader){
		ArrayList<Person> personList = new ArrayList<Person>();
		String name = "";
		String surname = "";
		char sex = 'M';
		Date birthDate = null;
		String birthPlace = "";
		try {
			while (xmlreader.hasNext()) {
				if (xmlreader.getEventType() == XMLStreamConstants.START_ELEMENT) {
					if (xmlreader.getLocalName().equalsIgnoreCase("nome")) {
						name = xmlreader.getElementText();
						xmlreader.nextTag();
						surname = xmlreader.getElementText();
						xmlreader.nextTag();
						sex = xmlreader.getElementText().toCharArray()[0];
						xmlreader.nextTag();
						birthPlace = XmlUtility.getBirthPlaceCode(xmlreader.getElementText().trim());
						xmlreader.nextTag();
						birthDate = XmlUtility.getDateObjectFromString(xmlreader.getElementText()); 
						
						personList.add(new Person(name, surname, sex, birthDate, birthPlace));
					}
				}
			xmlreader.next();
			}
			
		} catch (XMLStreamException e) {
			System.out.println("Errore nella lettura del reader: ");
			System.out.println(e.getMessage());
		}
		
		return personList;
	}
	
	/**
	 * estrapola i codici fiscali
	 * @param xmlreader il reader del file 
	 * @return restituisce un arraylist di tipo string 
	 */
	public static ArrayList<String> getFiscalCodeStringsToArray(XMLStreamReader xmlreader) {
		ArrayList<String> fiscalCodeList = new ArrayList<String>();
		try {
			while (xmlreader.hasNext()) {
				if (xmlreader.getEventType() == XMLStreamConstants.CHARACTERS && xmlreader.getText().trim().length() > 0) 
					fiscalCodeList.add(xmlreader.getText().trim());
			xmlreader.next();
			}
		} catch (XMLStreamException e) {
			System.out.println("Errore nella lettura del reader: ");
			System.out.println(e.getMessage());
		}
		return fiscalCodeList;
	}
	
	/**
	 * metodo che stampa il file xml finale
	 * @param xmlwriter l'oggetto StreamWriter inizializzato già sul file giusto
	 * @param personList la lista delle persone come ArrayList di tipo Person
	 * @param fiscalCodeList la lista dei codici fiscali come ArrayList di tipo String
	 */
	public static void createOutputXmlFile(XMLStreamWriter xmlwriter, ArrayList<Person> personList, ArrayList<String> fiscalCodeList, ArrayList<String> unpairedFiscalCodes, ArrayList<String> unmatchedFiscalCodes) {
		
		try { 
			 xmlwriter.writeStartElement("output"); //apre il tag "output"
			 xmlwriter.writeStartElement("persone"); //apre il tag "persone"
			 xmlwriter.writeAttribute("numero", Integer.toString(personList.size())); //aggiunta attributo numero persone
			 xmlwriter.writeComment("INIZIO LISTA");  //stampa un commento che indica l'inizio della lista

			 for (int i = 0; i < personList.size(); i++) {
				 xmlwriter.writeStartElement("persona"); //apertura tag "persona"
				 xmlwriter.writeAttribute("id", Integer.toString(i)); //aggiunta attributo id

				 xmlwriter.writeStartElement("nome"); //apertura tag "nome"
				 xmlwriter.writeCharacters(personList.get(i).getName()); //scittura del valore
				 xmlwriter.writeEndElement(); //chiusura tag "nome"

				 xmlwriter.writeStartElement("cognome"); //apertura tag "cognome"
				 xmlwriter.writeCharacters(personList.get(i).getSurname()); //scittura del valore
				 xmlwriter.writeEndElement(); //chiusura tag "cognome"

				 xmlwriter.writeStartElement("sesso");//apertura tag "sesso"
				 xmlwriter.writeCharacters(String.valueOf(personList.get(i).getSex())); //scittura del valore
				 xmlwriter.writeEndElement(); //chiusura tag "sesso"

				 xmlwriter.writeStartElement("comune_nascita");//apertura tag "comune_nascita"
				 xmlwriter.writeCharacters(personList.get(i).getBirthPlace()); //scittura del valore
				 xmlwriter.writeEndElement(); //chiusura tag "comune_nscita"

				 xmlwriter.writeStartElement("data_nascita");//apertura tag "data_nascita"
				 xmlwriter.writeCharacters(personList.get(i).getBirthDate().toString()); //scittura del valore
				 xmlwriter.writeEndElement(); //chiusura tag "data_nascita"

				 xmlwriter.writeStartElement("codice_fiscale");//apertura tag "codice_fiscale"
				 if (XmlUtility.checkForAFiscalCode(GenerateCode.generateFiscalCode(personList.get(i))))
					 xmlwriter.writeCharacters(GenerateCode.generateFiscalCode(personList.get(i))); //scrittura codice fiscale
				 else
					 xmlwriter.writeCharacters("ASSENTE"); //scittura del valore
				 xmlwriter.writeEndElement(); //chiusura tag "codice_fiscale"

				 xmlwriter.writeEndElement(); //chiusura tag "persona"

			 }
			 xmlwriter.writeEndElement(); // chiusura di </persone>
			 
			 xmlwriter.writeStartElement("codici"); //inizio tag "codici"
			 
			 xmlwriter.writeStartElement("invalidi"); //inizio tag "invalidi"
			 xmlwriter.writeAttribute("numero", Integer.toString(unpairedFiscalCodes.size()));
			 for (int i = 0; i < unpairedFiscalCodes.size(); i++) {
				 xmlwriter.writeStartElement("codice");
				 xmlwriter.writeCharacters(unpairedFiscalCodes.get(i));
				 xmlwriter.writeEndElement();
			 }
			 xmlwriter.writeEndElement(); //chiusura tag "invalidi"
			 
			 xmlwriter.writeStartElement("spaiati"); //inizio tag "spaiati"
			 xmlwriter.writeAttribute("numero", Integer.toString(unmatchedFiscalCodes.size()));
			 for (int i = 0; i < unmatchedFiscalCodes.size(); i++) {
				 xmlwriter.writeStartElement("codice");
				 xmlwriter.writeCharacters(unmatchedFiscalCodes.get(i));
				 xmlwriter.writeEndElement();
			 }
			 xmlwriter.writeEndElement(); //chiusura tag "spaiati"
			 
			 xmlwriter.writeEndElement(); //chiusura tag "codici"
			 
			 xmlwriter.writeEndElement(); // chiusura di </output>
			 xmlwriter.writeEndDocument(); // scrittura della fine del documento
			 xmlwriter.flush(); // svuota il buffer e procede alla scrittura
			 xmlwriter.close(); // chiusura del documento e delle risorse impiegate
		} catch (Exception e) {
			System.out.println("Errore nella scrittura");
			System.out.println(e.getMessage());
		}
	}
	
	
	
	/**
	 * cerca una stringa all'interno del file comuni.xml
	 * @param birthPlaceString prende in input una stringa
	 * @return restituisce il codice del relativo comune
	 */
	public static String getBirthPlaceCode(String birthPlaceString) {

		XMLStreamReader xmlreaderComuni = XmlUtility.initializeXmlReader("comuni.xml");
		
		try {
			while (xmlreaderComuni.hasNext()) {
				if (xmlreaderComuni.getEventType() == XMLStreamConstants.CHARACTERS && xmlreaderComuni.getText().equalsIgnoreCase(birthPlaceString.trim())) {
					xmlreaderComuni.next();
					xmlreaderComuni.next();
					xmlreaderComuni.next();
					return xmlreaderComuni.getElementText();
				}		
			xmlreaderComuni.next();
			}
			
		} catch (XMLStreamException e) {
			System.out.println("Errore nella lettura del reader(comuni): ");
			System.out.println(e.getMessage());
		}
		return "Z100";
		
	}
	
	/**
	 * controlla se un codice fiscale è presente all'interno del file codiciPersone.xml
	 * @param fiscalCode il codice fiscale da ricercare
	 * @return true se trova una corrispondenza, false altrimenti
	 */
	public static boolean checkForAFiscalCode(String fiscalCode) {

		XMLStreamReader xmlreader = XmlUtility.initializeXmlReader("codiciFiscali.xml");
		
		try {
			while (xmlreader.hasNext()) {
				if (xmlreader.getEventType() == XMLStreamConstants.CHARACTERS)
					if (xmlreader.getText().equalsIgnoreCase(fiscalCode)) 
						return true;
			xmlreader.next();
			}
		} catch (XMLStreamException e) {
			System.out.println("Errore nella lettura del reader(check cf): ");
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	/**
	 * prende in input una stringa e la trasforma in oggetto di tipo data
	 * @param stringDate una stringa del tipo yyyy-mm-dd
	 * @return un oggetto di tipo data
	 */
	public static Date getDateObjectFromString(String stringDate) {
		int year = (stringDate.toCharArray()[0]-48)*1000 + (stringDate.toCharArray()[1]-48)*100 + (stringDate.toCharArray()[2]-48)*10 + stringDate.toCharArray()[3]-48;
		int month = (stringDate.toCharArray()[5]-48)*10 + stringDate.toCharArray()[6]-48;
		int day = (stringDate.toCharArray()[8]-48)*10 + stringDate.toCharArray()[9]-48;
		
		return new Date(year, month, day);
	}
	
}