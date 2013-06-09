package com.mCare.db;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.mCare.consulta.Consulta;
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
		
		long id = dbhelper.insert(dbhelper.TABLE_NAME_MEDICAMENTO, cv);
		
		return id;
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
	
	public LinkedList<Medicamento> listaMedicamentos(boolean favorito){
		
		//DEFINE QUERY dependendo se quer favoritos ou nao
		String query = "SELECT id_medicamento, nome " +
						"FROM "+dbhelper.TABLE_NAME_MEDICAMENTO;
		
		if (favorito) {
			query = "WHERE favorito = true";
		}
		else {
			query = "WHERE favorito = false";
		}
		
		//Executa o SQL
		Cursor cursor = dbhelper.exercutaSELECTSQL(query, null);
		
		Log.i("SQL", "cursor esta fechado? : " + cursor.isFirst());
		if(cursor.moveToFirst()){
			Log.i("SQL","cursor possui linhas");
			
			//Armazena resultado
			LinkedList<Medicamento> listaMedicamentos = new LinkedList<Medicamento>();
			while(!cursor.isAfterLast()){
				Log.i("SQL","passou no is afterlast");
				
				int id = Integer.parseInt(cursor.getString(1));
				String nome = cursor.getString(2);
				
				Medicamento m = new Medicamento(id, nome);
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
	
	public void listaMedicamentos(Paciente p){
	
		//Busca ultima consulta do paciente (pra saber se o medicamento foi descontinuado
		int id_ultima_consulta = 0;
		String query_consulta =
				"SELECT max(id_consulta) " +
				"FROM " + dbhelper.TABLE_NAME_CONSULTAS_MARCADAS +
				"WHERE fk_paciente = " + p.getBd_id();
		
		Cursor cursor = dbhelper.exercutaSELECTSQL(query_consulta, null);
		
		Log.i("SQL", "cursor esta fechado? : " + cursor.isFirst());
		if(cursor.moveToFirst()){
			Log.i("SQL","cursor possui linhas");
			
			//Armazena resultado
			while(!cursor.isAfterLast()){
				Log.i("SQL","passou no is afterlast");
				id_ultima_consulta = Integer.parseInt(cursor.getString(1));
				cursor.moveToNext();
			}
		}
		
		//Busca todos os medicamentos que o paciente toma ou ja tomou 
		String query = "SELECT id_medicamento, medicamento.nome, medicamento_paciente.id_consulta, medicamento_paciente.data_consulta" +
						"FROM " + dbhelper.TABLE_NAME_MEDICAMENTO +
						"INNER JOIN medicamento_paciente ON medicamento.id_medicamento = medicamento_paciente.id_medicamento " +
						"WHERE medicamento_paciente.id_paciente = " + p.getBd_id();
		
		//Executa o SQL
		cursor = dbhelper.exercutaSELECTSQL(query, null);
		
		Log.i("SQL", "cursor esta fechado? : " + cursor.isFirst());
		if(cursor.moveToFirst()){
			Log.i("SQL","cursor possui linhas");
			
			//Armazena resultado
			LinkedList<Medicamento> listaMedicamentosAtuais = new LinkedList<Medicamento>();
			LinkedList<Medicamento> listaMedicamentosAnteriores = new LinkedList<Medicamento>();
			while(!cursor.isAfterLast()){
				Log.i("SQL","passou no is afterlast");
				
				int id_medicamento = Integer.parseInt(cursor.getString(1));
				String nome = cursor.getString(2);
				int id_consulta = Integer.parseInt(cursor.getString(3));
				GregorianCalendar hora = dbhelper.textToGregorianCalendar(cursor.getString(4));
				
				Medicamento m = new Medicamento(id_medicamento, nome);
				m.setIdConsulta(id_consulta);
				m.setHora(hora);
				
				//Adiciona aos medicamentos atuais
				listaMedicamentosAtuais.add(m);
				cursor.moveToNext();
			}
			
			//Ordena medicamentos
			/*************** OBSERVACAO DA GABI ************
			 * TERIA QUE ORDENAR PRIMEIRO POR id_medicamento e DEPOIS POR id_consulta!
			 * Nao sei se esta fazendo (nao consegui testar...)
			 * *********************************************
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
			
			//Depois de ordenar, ver qual o ultimo id_consulta de cada medicamento
			Medicamento medicamento_anterior = null;
			//Testa do primeiro ao penultimo
			for (Medicamento m: listaMedicamentosAtuais) {
				if (medicamento_anterior.getId() != m.getId()) { //mudou de medicamento => testa o anterior (a ultima consulta daquele medicamento)
					if (medicamento_anterior.getIdConsulta() < id_ultima_consulta) { //Medicamento foi descontinuado!!!
						listaMedicamentosAnteriores.add(m);
						listaMedicamentosAtuais.remove(m);
					}				
				}
				medicamento_anterior = m;
			}
			//Testa o ultimo
			Medicamento m = listaMedicamentosAtuais.getLast();
			if (m.getIdConsulta() < id_ultima_consulta) { //Medicamento foi descontinuado!!!
				listaMedicamentosAnteriores.add(m);
				listaMedicamentosAtuais.remove(m);
			}
			
			return;
		}
	}
}
