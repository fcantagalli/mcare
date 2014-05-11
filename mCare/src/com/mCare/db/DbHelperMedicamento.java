package com.mCare.db;

import java.util.ArrayList;
import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.mCare.medicamento.Medicamento;
import com.mCare.paciente.Paciente;

public class DbHelperMedicamento {

	public Db dbhelper;
	
	public DbHelperMedicamento(Context context){
		dbhelper = Db.getInstance(context);
	}
	
	public long insereMedicamento(Medicamento m){
		ContentValues cv = new ContentValues();
		
		cv.put("nome", m.getNome());
		cv.put("tipo", m.getTipo());
		cv.put("dosagem", m.getDosagem());
		cv.put("principioativo", m.getPricipioAtivo());
		int favorito;
		if(m.getFavorito()){
			favorito = 1;
		}
		else{
			favorito = 0;
		}
		cv.put("favorito", favorito);
		
		long id = dbhelper.insert(dbhelper.TABLE_NAME_MEDICAMENTO, cv);
		Log.i("med",m.getNome()+" "+m.getTipo()+" "+m.getDosagem()+" "+m.getPricipioAtivo());
		return id;
	}
	
	public boolean updateMedicamento(Medicamento m){
		boolean deucerto;
		
		ContentValues cv = new ContentValues();

		cv.put("nome", m.getNome());
		cv.put("tipo", m.getTipo());
		cv.put("dosagem", m.getDosagem());
		cv.put("principioativo", m.getPricipioAtivo());
		cv.put("favorito", m.getFavorito());
		
		deucerto = dbhelper.update(dbhelper.TABLE_NAME_MEDICAMENTO, cv, "id_medicamento = "+m.getId(), null);
		
		return deucerto;
	}

	public boolean deletaMedicamento(long id){
		
		int i = dbhelper.delete(dbhelper.TABLE_NAME_MEDICAMENTO, "id_medicamento=?", new String[]{""+id});
		
		if(i > 0){
			return true;
		}
		else{
			return false;
		}
	}

	public Medicamento buscaMedicamento(String nome) {

		String query = "SELECT id_medicamento, nome, tipo, dosagem, principioativo, favorito "
				+ " FROM " + dbhelper.TABLE_NAME_MEDICAMENTO
				+ " WHERE nome = '" + nome + "';";

		Cursor c = dbhelper.exercutaSELECTSQL(query, null);
		Medicamento m = null;

		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {

				//Pega do select
				int idMedicamento = c.getInt(0);
				String nomeMedicamento = c.getString(1);
				String tipo = c.getString(2);
				String dosagem = c.getString(3);
				String pricipioAtivo = c.getString(4);
				int favorito = c.getInt(5);
				
				//Coloca na classe
				m = new Medicamento(idMedicamento,nomeMedicamento);
				m.setTipo(tipo);
				m.setDosagem(dosagem);
				m.setPricipioAtivo(pricipioAtivo);
				
				if (favorito == 0) {
					m.setFavorito(false);
				}
				else {
					m.setFavorito(true);
				}

				c.moveToNext();
			}
		}
		return m;
	}
	
	public Medicamento buscaMedicamento(int id) {

		String query = "SELECT id_medicamento, nome, tipo, dosagem, principioativo, favorito "
				+ " FROM " + dbhelper.TABLE_NAME_MEDICAMENTO
				+ " WHERE id_medicamento = '" + id + "';";

		Cursor c = dbhelper.exercutaSELECTSQL(query, null);
		Medicamento m = null;

		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {

				//Pega do select
				int idMedicamento = c.getInt(0);
				String nomeMedicamento = c.getString(1);
				String tipo = c.getString(2);
				String dosagem = c.getString(3);
				String pricipioAtivo = c.getString(4);
				int favorito = c.getInt(5);
				
				//Coloca na classe
				m = new Medicamento(idMedicamento,nomeMedicamento);
				m.setTipo(tipo);
				m.setDosagem(dosagem);
				m.setPricipioAtivo(pricipioAtivo);
				
				Log.i("med",m.getNome()+" "+m.getTipo()+" "+m.getDosagem()+" "+m.getPricipioAtivo());
				
				if (favorito == 0) {
					m.setFavorito(false);
				}
				else {
					m.setFavorito(true);
				}

				c.moveToNext();
			}
		}
		return m;
	}

	public ArrayList<Medicamento> listaMedicamentos(){
		Cursor c = dbhelper.serach(false, dbhelper.TABLE_NAME_MEDICAMENTO, null, null, null, null,null, null, null);
		ArrayList<Medicamento> result = null;
		if(c.moveToFirst()){
			result = new ArrayList<Medicamento>(c.getCount());
			Log.i("med","numero de linhas: "+c.getCount());
			
			while(!c.isAfterLast()){
				long id = c.getLong(0);
				String nome = c.getString(1);
				String tipo = c.getString(2);
				String dosagem = c.getString(3);
				String principioativo = c.getString(4);
				int favorito = c.getInt(5);
				
				Medicamento m = new Medicamento((int) id,nome,tipo);
				m.setDosagem(dosagem);
				m.setPricipioAtivo(principioativo);
				
				Log.i("med",m.getNome()+" "+m.getTipo()+" "+m.getDosagem()+" "+m.getPricipioAtivo());
				
				if(favorito == 1){
					m.setFavorito(true);
				}
				else{
					m.setFavorito(false);
				}
				result.add(m);
				c.moveToNext();
			}
		}
		
		return result;
		
	}
	
	public ArrayList<Medicamento> listaMedicamentos(boolean favorito){
		
		//DEFINE QUERY dependendo se quer favoritos ou nao
		String query = " SELECT id_medicamento, nome, tipo, dosagem, principioativo, favorito" +
						" FROM "+dbhelper.TABLE_NAME_MEDICAMENTO;
		
		if (favorito) {
			query = query+ " WHERE favorito = 1;";
		}
		else {
			query = query + " WHERE favorito = 0;";
		}
		
		//Executa o SQL
		Cursor cursor = dbhelper.exercutaSELECTSQL(query, null);
		
		Log.i("SQL", "cursor esta fechado? : " + cursor.isFirst());
		if(cursor.moveToFirst()){
			Log.i("SQL","cursor possui linhas");
			
			//Armazena resultado
			ArrayList<Medicamento> listaMedicamentos = new ArrayList<Medicamento>();
			while(!cursor.isAfterLast()){
				Log.i("SQL","passou no is afterlast");
				
				int id = (int) cursor.getLong(0);
				String nome = cursor.getString(1);
				String tipo = cursor.getString(2);
				String dosagem = cursor.getString(3);
				String principioAtivo = cursor.getString(4);
				int fav = cursor.getInt(5);
				
				Medicamento m = new Medicamento(id, nome,tipo);
				m.setDosagem(dosagem);
				m.setPricipioAtivo(principioAtivo);
				if(fav == 1){
					m.setFavorito(true);
				}
				else{
					m.setFavorito(false);
				}
				
				listaMedicamentos.add(m);
				cursor.moveToNext();
			}
			Collections.sort(listaMedicamentos, new Comparator<Medicamento>() {
		         @Override
		         public int compare(Medicamento o1, Medicamento o2) {
		             return Collator.getInstance().compare(o1.getNome(), o2.getNome());
		         }
		     });
			return listaMedicamentos;
		}
		else{
			return null;
		}
	}
	
	public ArrayList<Medicamento> listaMedicamentos(Paciente p){
	
		//Busca ultima consulta do paciente (pra saber se o medicamento foi descontinuado
		int id_ultima_consulta = 0;
		String query_consulta =
				" SELECT max(id_consulta) " +
				" FROM " + "consulta" +
				" WHERE fk_paciente = " + p.getBd_id();
		
		Cursor cursor = dbhelper.exercutaSELECTSQL(query_consulta, null);
		
		Log.i("SQL", "cursor esta fechado? : " + cursor.isFirst());
		if(cursor.moveToFirst()){
			Log.i("SQL","cursor possui linhas");
			
			//Armazena resultado
			while(!cursor.isAfterLast()){
				Log.i("SQL","passou no is afterlast");
				id_ultima_consulta = cursor.getInt(0);
				cursor.moveToNext();
			}
		}
		else{
			return null;
		}
		
		//Busca todos os medicamentos que o paciente toma 
		String query = "SELECT medicamento.id_medicamento, medicamento.nome, medicamento_paciente.id_consulta, medicamento_paciente.data_consulta, medicamento_paciente.tread_many_time, medicamento_paciente.tread_many_time_type," +
				        " medicamento_paciente.med_period, medicamento_paciente.med_period_time, medicamento_paciente.med_recommendation, medicamento_paciente.miss_dose_period, medicamento_paciente.miss_dose_type, medicamento_paciente.miss_dose_recomm, medicamento.tipo, medicamento.dosagem" +
						" FROM " + dbhelper.TABLE_NAME_MEDICAMENTO +
						" INNER JOIN medicamento_paciente ON medicamento.id_medicamento = medicamento_paciente.id_medicamento" +
						" AND medicamento_paciente.id_consulta = "+id_ultima_consulta+
						" WHERE medicamento_paciente.fk_paciente = " + p.getBd_id();
		
		//Executa o SQL
		cursor = dbhelper.exercutaSELECTSQL(query, null);
		
		Log.i("SQL", "cursor esta fechado? : " + cursor.isFirst());
		ArrayList<Medicamento> listaMedicamentosAtuais = null;
		if(cursor.moveToFirst()){
			Log.i("SQL","cursor possui linhas");
			
			//Armazena resultado
			listaMedicamentosAtuais = new ArrayList<Medicamento>(cursor.getCount());
			//LinkedList<Medicamento> listaMedicamentosAnteriores = new LinkedList<Medicamento>();
			while(!cursor.isAfterLast()){
				Log.i("SQL","passou no is afterlast");
				
				int id_medicamento = cursor.getInt(0);
				String nome = cursor.getString(1);
				int id_consulta = cursor.getInt(2);
				GregorianCalendar hora = dbhelper.textToGregorianCalendar(cursor.getString(3));
				//String hours = cursor.getString(4);
				//int days = cursor.getInt(5);
				
				String tread_many_time = cursor.getString(4);
				String tread_many_time_type = cursor.getString(5);
				String med_period = cursor.getString(6);
				String med_period_time = cursor.getString(7);
				String med_recommendation = cursor.getString(8);
				String miss_dose_period = cursor.getString(9);
				String miss_dose_type = cursor.getString(10);
				String miss_dose_recomm = cursor.getString(11);
				
				String tipo = cursor.getString(12);
				String dosagem = cursor.getString(13);
				Log.i("helperMedicamento", "dosagem: " + dosagem);
				
				Medicamento m = new Medicamento(id_medicamento, nome);
				m.setIdConsulta(id_consulta);
				m.setHora(hora);
				m.setTread_many_time(tread_many_time);
				m.setTread_many_time_type(tread_many_time_type);
				m.setMed_period(med_period);
				m.setMed_period_time(med_period_time);
				m.setMed_recommendation(med_recommendation);
				m.setMiss_dose_period(miss_dose_period);
				m.setMiss_dose_type(miss_dose_type);
				m.setMiss_dose_recomm(miss_dose_recomm);
				m.setTipo(tipo);
				m.setDosagem(dosagem);
				//m.setHours(hours);
				//m.setDays(days);
				
				//Adiciona aos medicamentos atuais
				listaMedicamentosAtuais.add(m);
				cursor.moveToNext();
			}
			
			//Ordena medicamentos
			/*************** OBSERVACAO DA GABI ************
			 * TERIA QUE ORDENAR PRIMEIRO POR id_medicamento e DEPOIS POR id_consulta!
			 * Nao sei se esta fazendo (nao consegui testar...)
			 * *********************************************
			 * Felipe : O comparator la embaixo precisa ser de medicamentos e nao integer. eu mudei isso, ve se era isso mesmo que voce queria.
			 * 
			 * 
			 * Gabi: Na verdade eu nao tava usando ele... Da uma olhada se ta funcionando esse aqui de baixo qnd a gente tiver dados? :D
			 */
			Collections.sort(listaMedicamentosAtuais, new Comparator<Medicamento>() {
		         @Override
		         public int compare(Medicamento o1, Medicamento o2) {
		        	int c;
	        	    c = Collator.getInstance().compare(o1.getNome(), o2.getNome());
	        	    if (c == 0) {
	        	       c = Collator.getInstance().compare(o1.getIdConsulta(), o2.getIdConsulta());
	        	    }
	        	    return c;
		         }
		     });

			
		}
		return listaMedicamentosAtuais;
	}
}
