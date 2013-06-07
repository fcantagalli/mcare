package com.mCare.db;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.mCare.consulta.Consulta;
import com.mCare.paciente.Paciente;

public class DbHelperConsultas {

	public Db dbhelper;
	
	public DbHelperConsultas(Context context){
		dbhelper = Db.getInstance(context);
	}
	
	public void deletaConsulta(long id){
		dbhelper.delete(dbhelper.TABLE_NAME_CONSULTA, "id_consulta = "+id, null);
	}
	
	public long insereConsulta(Consulta consulta){
		long deucerto;
		
		ContentValues cv = new ContentValues();
		
		cv.put("fk_paciente", consulta.getPaciente().getBd_id());
		GregorianCalendar gc = consulta.getHora();
		cv.put("data_hora",dbhelper.formataData(gc));
		cv.put("descricao", consulta.getDescricao());
		cv.put("tipo_con", consulta.getTipo());
		
		deucerto = dbhelper.insert(dbhelper.TABLE_NAME_CONSULTAS_MARCADAS, cv);
		return deucerto;
	}
	
	public List<Consulta> todasConsultas(){
		
		String query = "SELECT consultas_marcadas.fk_paciente, data_hora, descricao, tipo_con, nome, logradouro, numero, bairro, cidade, id_consulta FROM consultas_marcadas" +
				" INNER JOIN paciente ON id_paciente = consultas_marcadas.fk_paciente;";
		
		//Cursor cursor = dbhelper.serach(false, dbhelper.TABLE_NAME_CONSULTAS_MARCADAS, null, null, null, null, null, null,null);
		Cursor cursor = dbhelper.exercutaSELECTSQL(query, null);
		
		Log.i("SQL", "cursor esta fechado? : " + cursor.isFirst());
		if(cursor.moveToFirst()){
			Log.i("SQL","cursor possui linhas");
			LinkedList<Consulta> listaConsultas = new LinkedList<Consulta>();
			while(!cursor.isAfterLast()){

				
				Log.i("SQL","passou no is afterlast");
				
				GregorianCalendar gc = dbhelper.textToGregorianCalendar(cursor.getString(1));
				String descricao = cursor.getString(2);
				//Log.w("SQL",descricao);
				String tipo_con = cursor.getString(3);
				
				int idPaciente = cursor.getInt(0);
				String nome = cursor.getString(4);
				String logradouro = cursor.getString(5);
				int numero = cursor.getInt(6);
				String bairro = cursor.getString(7);
				String cidade = cursor.getString(8);
				long id_consulta = cursor.getLong(9);
				Paciente p = new Paciente(idPaciente,nome,null,(byte)-1,logradouro,bairro,numero,cidade);
				
				Consulta c = new Consulta(p,gc,tipo_con,descricao);
				c.setId(id_consulta);
				listaConsultas.add(c);
				cursor.moveToNext();
			}
			return listaConsultas;
		}
		else{
			return null;
		}
	}
	
	
	
 	public LinkedList<Consulta> consultasDoDia(){
		
		//String diaAtual = dbhelper.formataData(new GregorianCalendar());
		// A CONSULTA ABAIXO NAO PEGA SOMENTE AS DO DIA, E SIM AS DO DIA E AS POSTERIORES CUIDADOOOOOOO
 		String query = "SELECT consulta.fk_paciente, data_hora, descricao, tipo_con, nome, logradouro, numero, bairro, cidade, id_consulta FROM consultas_marcadas as consulta " +
				"INNER JOIN paciente as p ON p.id_paciente = consulta.fk_paciente " +
				"INNER JOIN telefone as t ON t.fk_paciente = p.id_paciente " +
				"WHERE date(consulta.data_hora) >= date('now'); " +
				"GROUP BY id_consulta;";
 		
				
		Cursor cursor = dbhelper.exercutaSELECTSQL(query, null);
		// Se encontrou
		if(cursor.moveToFirst()){
			LinkedList<Consulta> listaConsultas = new LinkedList<Consulta>();
			while(!cursor.isAfterLast()){
				
				GregorianCalendar gc = dbhelper.textToGregorianCalendar(cursor.getString(1));
				String descricao = cursor.getString(2);
				//Log.w("SQL",descricao);
				String tipo_con = cursor.getString(3);
				
				int idPaciente = cursor.getInt(0);
				String nome = cursor.getString(4);
				String logradouro = cursor.getString(5);
				int numero = cursor.getInt(6);
				String bairro = cursor.getString(7);
				String cidade = cursor.getString(8);
				long id_consulta = cursor.getLong(9);
				Paciente p = new Paciente(idPaciente,nome,null,(byte)-1,logradouro,bairro,numero,cidade);
				Consulta c = new Consulta(p,gc,tipo_con,descricao);
				c.setId(id_consulta);
				listaConsultas.add(c);
				
				cursor.moveToNext();
			}
			return listaConsultas;
		}
		else{
			return null;
		}
	}
 	
	public Consulta buscaConsulta(int id){
				
				String query = "SELECT consulta.fk_paciente, data_hora, descricao, tipo_con, nome, id_consulta FROM consultas_marcadas as consulta " +
						"INNER JOIN paciente as p ON p.id_paciente = consulta.fk_paciente " +
						"WHERE id_consulta =  "+id+
						" GROUP BY consulta.fk_paciente;";		 		
						
				Consulta consulta = null;
				Cursor cursor = dbhelper.exercutaSELECTSQL(query, null);
				// Se encontrou
				if(cursor.moveToFirst()){
					while(!cursor.isAfterLast()){
						
						GregorianCalendar gc = dbhelper.textToGregorianCalendar(cursor.getString(1));
						String descricao = cursor.getString(2);
						//Log.w("SQL",descricao);
						String tipo_con = cursor.getString(3);
						
						int idPaciente = cursor.getInt(0);
						String nome = cursor.getString(4);
						long id_consulta = cursor.getLong(5);
						Paciente p = new Paciente(idPaciente,nome);
						consulta = new Consulta(p,gc,tipo_con,descricao);
						consulta.setId(id_consulta);
						
						cursor.moveToNext();
					}
					return consulta;
				}
				else{
					return null;
				}
				
			}
}
