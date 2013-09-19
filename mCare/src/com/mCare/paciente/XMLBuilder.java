package com.mCare.paciente;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/*
 * CLASSE QUE CRIA UM XML NESSE FORMATO:
 * 
 * <patient>
 *     <name>Philippe</nome>
 *     <gender>Male</gender>
 *     <date-of-birth>22/01/93</date-of-birth>
 *     <medications>
 *         <medication id="1">
 *             <name>Aspirin</name>
 *             <administration>Oral</administration>
 *             <dose unit="ml">10</dose>
 *             <use-orientation>Take it with water, milk or juice</use-orientation>
 *             <missed-dose>
 *                 <delayed unit="hour">1</delayed>
 *                 <message>Skip it and continue with your regular schedule</message>
 *             </missed-dose>
 *             <duration unit="day">3</duration>
 *             <time-table>
 *                 <time>8:30</time>
 *                 <time>20:30</time>
 *                 <time>23:30</time>
 *             </time-table>
 *         </medication>
 *         :
 *         :
 *     </medications>
 *     <recomendations>
 *         <message>Loren Ipsun dolor...</message>
 *     </recomendations>
 * </patient>
 */

public class XMLBuilder {
	
	DocumentBuilderFactory docFactory;
	DocumentBuilder docBuilder;
	Document doc;
	
	// -- elemento raiz do XML --
	Element rootElement;
	
	// -- elemento das medications --
	Element patientMedicationsElement;
	
	public void startDocument(){
		docFactory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = docFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		doc = docBuilder.newDocument();
		
		// -- elemento raiz do xml (patient) --
		rootElement = doc.createElement("patient");
		doc.appendChild(rootElement);
	}
	
	public void setPatientInformation(String patientName, String patientGender, String patientDateOfBirth){
		// -- tag para o nome do paciente --
		Element patientNameElement = doc.createElement("name");
		patientNameElement.appendChild(doc.createTextNode(patientName));
		rootElement.appendChild(patientNameElement);
		
		// -- tag para o gender do paciente --
	    Element patientGenderElement = doc.createElement("gender");
		patientGenderElement.appendChild(doc.createTextNode(patientGender));
		rootElement.appendChild(patientGenderElement);
		
		// -- tag para a date-of-birth do paciente --
		Element patientBirthDateElement = doc.createElement("date-of-birth");
		patientBirthDateElement.appendChild(doc.createTextNode("22/01/1993"));
		rootElement.appendChild(patientBirthDateElement);
	}
	
	public void startMedicationsElement(){
		// -- tag para as medications --
	    patientMedicationsElement = doc.createElement("medications");
	}
	
	public void addMedication(){
		
	}
	

	static void criaXML() {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			
			// -- elemento raiz do xml (patient) --
			Element rootElement = doc.createElement("patient");
			doc.appendChild(rootElement);
			
			// -- tag para o nome do paciente --
			Element patientNameElement = doc.createElement("name");
			patientNameElement.appendChild(doc.createTextNode("Philippe"));
			rootElement.appendChild(patientNameElement);
			
			// -- tag para o gender do paciente --
			Element patientGenderElement = doc.createElement("gender");
			patientGenderElement.appendChild(doc.createTextNode("Male"));
			rootElement.appendChild(patientGenderElement);
			
			// -- tag para a date-of-birth do paciente --
			Element patientBirthDateElement = doc.createElement("date-of-birth");
			patientBirthDateElement.appendChild(doc.createTextNode("22/01/1993"));
			rootElement.appendChild(patientBirthDateElement);
			
			// -- tag para as medications --
			Element patientMedicationsElement = doc.createElement("medications");
			
			// -- tag para cada um dos medicamentos --
			Element medication = doc.createElement("medication");
			medication.setAttribute("id", "1");
			
			// -- tag para o nome do medicamento --
			Element medicationNameElement = doc.createElement("name");
			medicationNameElement.appendChild(doc.createTextNode("Aspirin"));
			medication.appendChild(medicationNameElement);
			
			// -- tag para a administration --
			Element medicationAdministrationElement = doc.createElement("administration");
			medicationAdministrationElement.appendChild(doc.createTextNode("Oral"));
			medication.appendChild(medicationAdministrationElement);
			
			// -- tag para a dose --
			Element medicationDoseElement = doc.createElement("dose");
			medicationDoseElement.setAttribute("unit", "ml");
			medicationDoseElement.appendChild(doc.createTextNode("10"));
			medication.appendChild(medicationDoseElement);
			
			// -- tag para a use-orientation --
			Element medicationUseOrientation = doc.createElement("use-orientation");
			medicationUseOrientation.appendChild(doc.createTextNode("Take it with water, milk or juice"));
		    medication.appendChild(medicationUseOrientation);
		    
		    // -- tag para missed-dose --
		    Element medicationMissedDoseElement = doc.createElement("missed-dose");
		    
		    // -- tag para o dalayed --
		    Element missedDoseDelayedElement = doc.createElement("delayed");
		    missedDoseDelayedElement.setAttribute("unit", "hour");
		    missedDoseDelayedElement.appendChild(doc.createTextNode("1"));
		    medicationMissedDoseElement.appendChild(missedDoseDelayedElement);
		    
		    // -- tag para a message --
		    Element missedDoseMessageElement = doc.createElement("message");
		    missedDoseMessageElement.appendChild(doc.createTextNode("Skip ir and continue with your regular schedule"));
		    medicationMissedDoseElement.appendChild(missedDoseMessageElement);
		    
		    // -- adiciona o missed-dose ao medicationElement --
		    medication.appendChild(medicationMissedDoseElement);
		    
		    // -- tag para a duration --
		    Element medicationDurationElement = doc.createElement("duration");
		    medicationDurationElement.setAttribute("unit", "day");
		    medicationDurationElement.appendChild(doc.createTextNode("3"));
		    medication.appendChild(medicationDurationElement);
		    
		    // -- tag para a time-table --
		    Element timeTableElement = doc.createElement("time-table");
		    
		    // -- tag para cada um dos elementos de time-table --
		    Element timeTableTimeElement = doc.createElement("time");
		    timeTableTimeElement.appendChild(doc.createTextNode("8:30"));
		    timeTableElement.appendChild(timeTableTimeElement);
		    
		    // -- adiciona a time table a medication --
		    medication.appendChild(timeTableElement);
		    
		    patientMedicationsElement.appendChild(medication);
		    rootElement.appendChild(patientMedicationsElement);
		    
		    // -- tag para as recomendations do patient --
		    Element patientRecomendations = doc.createElement("recomendations");
		    
		    // -- tag para a message das recomendations --
		    Element patientRecomendationsMessage = doc.createElement("message");
		    patientRecomendationsMessage.appendChild(doc.createTextNode("Loren Ipsun dolor..."));
		    patientRecomendations.appendChild(patientRecomendationsMessage);
		    
		    rootElement.appendChild(patientRecomendations);
		
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("philippe.xml"));
	 
			transformer.transform(source, result);
			System.out.println("File saved!");
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
}
