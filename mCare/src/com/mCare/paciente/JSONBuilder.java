package com.mCare.paciente;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mCare.db.DbHelperConsultasRealizadas;
import com.mCare.db.DbHelperMedicamento;
import com.mCare.db.DbHelperPaciente;
import com.mCare.medicamento.Medicamento;

import android.content.Context;
import android.util.Log;

public class JSONBuilder {

	Context context;
	int idPaciente;
	
	JSONBuilder(int idPaciente, Context context){
		this.context = context;
		this.idPaciente = idPaciente;
	}
	
	File criaJSON(){
		Paciente paciente = new DbHelperPaciente(context).buscaPaciente(idPaciente);
		File send = null;
		FileWriter writeFile = null;
		
		//Cria um Objeto JSON
		JSONObject jsonObject = new JSONObject();
		
		//atributo para o nome do paciente
		jsonObject.put("name", paciente.getNome());
		
		//atributo genero
		String gender;
		switch (paciente.getSexo()) {
		case 0: gender = "female";
			break;
		case 1: gender = "male";
			break;
		default: gender = "error";
			break;
		}
		jsonObject.put("gender", gender);
		
		//atributo date-of-birth
		GregorianCalendar calendar = paciente.getDataNascimento();
		String dataNascimento = calendar.get(GregorianCalendar.DAY_OF_MONTH) + "/" + calendar.get(GregorianCalendar.MONTH) + "/" + calendar.get(GregorianCalendar.YEAR);
		jsonObject.put("date-of-birth", dataNascimento);
		
		//atributo para lista de medicamentos
		ArrayList<Medicamento> medicamentos = (new DbHelperMedicamento(context)).listaMedicamentos(paciente);
		Medicamento medicamento = null;
		JSONArray jsonMeds = new JSONArray();
		
		for(Medicamento m : medicamentos){
			
			// para cada medicamento, cria um sub-json para todos os atributos do medicamento
			JSONObject med = new JSONObject();
			// id do med
			med.put("id", m.getId()+"");
			// nome do med
			med.put("name", m.getNome()+"");
			// administration
			med.put("administration", m.getTipo());
			// dose
			med.put("dose", m.getDosagem().toString() + " "+ "mg");
			// user orientation
			med.put("user-orientation", m.getMed_recommendation());
			
				// missed dose
				JSONObject missedDose = new JSONObject();
				// delayed
				missedDose.put("unit", m.getMiss_dose_type());
				// period
				missedDose.put("period", m.getMiss_dose_period());
				// message
				missedDose.put("message", m.getMiss_dose_recomm());
				
			med.put("missed dose", missedDose);
			
				// duration
				JSONObject duration = new JSONObject();
				// unidade do tempo
				duration.put("unit", m.getTread_many_time_type());
				// tempo
				duration.put("time", m.getTread_many_time());
			
			med.put("duration", duration);
			
				// tabela de tempo dos medicamentos
				JSONObject timeTable = new JSONObject();
				// frequencia dos medicamentos
				timeTable.put("frequency", m.getMed_period());
				// para os horarios dos remedios
				JSONArray times = new JSONArray();
				String[] horarios = m.getMed_period_time().split(",");
				for(int i = 0; i < horarios.length ; i++){
					times.add(horarios[i]);
				}
				timeTable.put("time", times);
			
			med.put("time-table", timeTable);
			
			jsonMeds.add(med);
			
			medicamento = m;
		}
		
		jsonObject.put("medications", jsonMeds);
		
		// -- tag para a message das recomendations --
	    DbHelperConsultasRealizadas dbConsultas = new DbHelperConsultasRealizadas(context);
	    HashMap<String,String[]> consulta = dbConsultas.buscaConsultaRealizada(medicamento.getIdConsulta());
	    String recommendation = consulta.get("conteudos")[5];
		jsonObject.put("recomendations", recommendation);
		
		try{
			send = new File(context.getFilesDir(), "testJSON.json");
			writeFile = new FileWriter(send);
			//Escreve no arquivo conteudo do Objeto JSON
			ArrayList<String> cripted = criptografia.Criptorgrafia.encriptar(jsonObject.toJSONString());
			writeFile.write(cripted.get(0));
			writeFile.append('\n');
			writeFile.write(cripted.get(1));
			writeFile.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		//Imprimne na Tela o Objeto JSON para vizualização
		Log.i("xml",""+jsonObject);
		Log.i("xml", "saiu do metodo");

		return send;

	}
	
}
