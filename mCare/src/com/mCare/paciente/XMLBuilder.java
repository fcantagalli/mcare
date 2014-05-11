package com.mCare.paciente;

import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mCare.db.DbHelperConsultasRealizadas;
import com.mCare.db.DbHelperMedicamento;
import com.mCare.db.DbHelperPaciente;
import com.mCare.medicamento.Medicamento;

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
	
	Context context;
	int idPaciente;
	
	XMLBuilder(int idPaciente, Context context){
		this.context = context;
		this.idPaciente = idPaciente;
	}

	File criaXML() {
		Paciente paciente = new DbHelperPaciente(context).buscaPaciente(idPaciente);
		File send = null;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			Document doc = docBuilder.newDocument();
			
			// -- elemento raiz do xml (patient) --
			Element rootElement = doc.createElement("patient");
			doc.appendChild(rootElement);
			
			// -- tag para o nome do paciente --
			Element patientNameElement = doc.createElement("name");
			patientNameElement.appendChild(doc.createTextNode(paciente.getNome()));
			rootElement.appendChild(patientNameElement);
			
			// -- tag para o gender do paciente --
			Element patientGenderElement = doc.createElement("gender");
			String gender;
			switch (paciente.getSexo()) {
			case 0: gender = "female";
				break;
			case 1: gender = "male";
				break;
			default: gender = "error";
				break;
			}
			patientGenderElement.appendChild(doc.createTextNode(gender));
			rootElement.appendChild(patientGenderElement);
			
			// -- tag para a date-of-birth do paciente --
			Element patientBirthDateElement = doc.createElement("date-of-birth");
			
			GregorianCalendar calendar = paciente.getDataNascimento();
			String dataNascimento = calendar.get(GregorianCalendar.DAY_OF_MONTH) + "/" + calendar.get(GregorianCalendar.MONTH) + "/" + calendar.get(GregorianCalendar.YEAR);
					
			patientBirthDateElement.appendChild(doc.createTextNode(dataNascimento));
			rootElement.appendChild(patientBirthDateElement);
			
			// -- tag para as medications --
			Element patientMedicationsElement = doc.createElement("medications");
			
			ArrayList<Medicamento> medicamentos = (new DbHelperMedicamento(context)).listaMedicamentos(paciente);
			Medicamento medicamento = null;
			for(Medicamento m : medicamentos){
				Log.i("xml", "adicionado medicamento: " + m.getNome());
				// -- tag para cada um dos medicamentos --
				Element medication = doc.createElement("medication");
				medication.setAttribute("id", m.getId()+"");
				
				// -- tag para o nome do medicamento --
				Element medicationNameElement = doc.createElement("name");
				medicationNameElement.appendChild(doc.createTextNode(m.getNome()));
				medication.appendChild(medicationNameElement);
				
				// -- tag para a administration --
				Element medicationAdministrationElement = doc.createElement("administration");
				medicationAdministrationElement.appendChild(doc.createTextNode(m.getTipo()));
				medication.appendChild(medicationAdministrationElement);
				
				// -- tag para a dose --
				Element medicationDoseElement = doc.createElement("dose");
				medicationDoseElement.setAttribute("unit", "mg");
				medicationDoseElement.appendChild(doc.createTextNode(m.getDosagem()));
				medication.appendChild(medicationDoseElement);
				
				// -- tag para a use-orientation --
				Element medicationUseOrientation = doc.createElement("use-orientation");
				medicationUseOrientation.appendChild(doc.createTextNode(m.getMed_recommendation()));
			    medication.appendChild(medicationUseOrientation);
			    
			    // -- tag para missed-dose --
			    Element medicationMissedDoseElement = doc.createElement("missed-dose");
			    
			    // -- tag para o dalayed --
			    Element missedDoseDelayedElement = doc.createElement("delayed");
			    missedDoseDelayedElement.setAttribute("unit", m.getMiss_dose_type());
			    missedDoseDelayedElement.appendChild(doc.createTextNode(m.getMiss_dose_period()));
			    medicationMissedDoseElement.appendChild(missedDoseDelayedElement);
			    
			    // -- tag para a message --
			    Element missedDoseMessageElement = doc.createElement("message");
			    missedDoseMessageElement.appendChild(doc.createTextNode(m.getMiss_dose_recomm()));
			    medicationMissedDoseElement.appendChild(missedDoseMessageElement);
			    
			    // -- adiciona o missed-dose ao medicationElement --
			    medication.appendChild(medicationMissedDoseElement);
			    
			    // -- tag para a duration --
			    Element medicationDurationElement = doc.createElement("duration");
			    medicationDurationElement.setAttribute("unit", m.getTread_many_time_type());
			    medicationDurationElement.appendChild(doc.createTextNode(m.getTread_many_time()));
			    medication.appendChild(medicationDurationElement);
			    
			    // -- tag para a time-table --
			    Element timeTableElement = doc.createElement("time-table");
			    timeTableElement.setAttribute("frequency", m.getMed_period());
			    
			    String[] horarios = m.getMed_period_time().split(",");
			    
			    // -- tag para cada um dos elementos de time-table --
			    for(int i=0; i<horarios.length; i++){
			    	Element timeTableTimeElement = doc.createElement("time");
				    timeTableTimeElement.appendChild(doc.createTextNode(horarios[i]));
				    timeTableElement.appendChild(timeTableTimeElement);
			    }
			    
			    // -- adiciona a time table a medication --
			    medication.appendChild(timeTableElement);
			    
			    patientMedicationsElement.appendChild(medication);
			    
			    medicamento = m;
			}
			
		    rootElement.appendChild(patientMedicationsElement);
		    
		    // -- tag para as recomendations do patient --
		    Element patientRecomendations = doc.createElement("recomendations");
		    
		    // -- tag para a message das recomendations --
		    DbHelperConsultasRealizadas dbConsultas = new DbHelperConsultasRealizadas(context);
		    HashMap<String,String[]> consulta = dbConsultas.buscaConsultaRealizada(medicamento.getIdConsulta());
		    String recommendation = consulta.get("conteudos")[5];
		    
		    Element patientRecomendationsMessage = doc.createElement("message");
		    patientRecomendationsMessage.appendChild(doc.createTextNode(recommendation));
		    patientRecomendations.appendChild(patientRecomendationsMessage);
		    
		    rootElement.appendChild(patientRecomendations);
		    
		    Log.i("xml", "finalizou o documento!");
		
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			
			
			send = new File(context.getFilesDir(), "test.xml");
			try{
				send.createNewFile();
				
			}catch(Exception e){
				Toast.makeText(context, "Naaao", Toast.LENGTH_LONG).show();
			}
			
			StreamResult result = new StreamResult(send);
			
			transformer.transform(source, result);
			 
			Log.i("xml", "saiu do metodo");
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
		
		return send;
	}
}
