package codicefiscale;

import java.util.ArrayList;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class MainCF {

	public static void main(String[] args) {
	
		// inizializzazione dei reader di codiciFiscali.xml e inputPersone.xml
		XMLStreamReader xmlreaderPerson = XmlUtility.initializeXmlReader("inputPersone.xml");
		XMLStreamReader xmlreaderFiscalCodeList = XmlUtility.initializeXmlReader("codiciFiscali.xml");
		
		// inizializzazione del writer di codiciPersone.xml
		XMLStreamWriter xmlwriterCodiciPersone = XmlUtility.initializeXmlWriter("codiciPersone.xml");
		
		//estrapolazioni dati persone
		ArrayList<Person> personList = XmlUtility.getPersonListFromXml(xmlreaderPerson);
		
		//creo lista codici fiscali delle persone
		ArrayList<String> personFiscalCodes = new ArrayList<String>();
		for (int i = 0; i<personList.size(); i++) 
			personFiscalCodes.add(GenerateCode.generateFiscalCode(personList.get(i)));

		//estrapolazione lista codici fiscali
		ArrayList<String> fiscalCodeList = XmlUtility.getFiscalCodeStringsToArray(xmlreaderFiscalCodeList);

		//creo lista codici fiscali invalidi e spaiati
		ArrayList<String> unmatchedFiscalCodes = new ArrayList<String>();
		ArrayList<String> unpairedFiscalCodes = new ArrayList<String>();
		
		//aggiungo i codici fiscali nei rispettivi array
		for (int i = 0; i < fiscalCodeList.size(); i++) {
			if (!CheckCode.checkFiscalCode(fiscalCodeList.get(i)))
				unpairedFiscalCodes.add(fiscalCodeList.get(i)); //invalidi
			else if (!personFiscalCodes.contains(fiscalCodeList.get(i)))
				unmatchedFiscalCodes.add(fiscalCodeList.get(i)); // spaiati
		}
		
		//creazione file output
		XmlUtility.createOutputXmlFile(xmlwriterCodiciPersone, personList, fiscalCodeList, unpairedFiscalCodes, unmatchedFiscalCodes);


		
		
	}
}
