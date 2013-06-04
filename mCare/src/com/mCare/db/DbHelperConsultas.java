package com.mCare.db;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import com.mCare.consulta.Consulta;
import com.mCare.paciente.Paciente;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.util.Log;

public class DbHelperConsultas {

	public Db dbhelper;
	
	public DbHelperConsultas(Context context){
		dbhelper = Db.getInstance(context);
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
		
		String query = "SELECT consultas_marcadas.fk_paciente, data_hora, descricao, tipo_con, nome, logradouro, numero, bairro, cidade FROM consultas_marcadas" +
				"INNER JOIN paciente ON id_paceinte = consultas_marcadas.fk_paciente;";
		
		//Cursor cursor = dbhelper.serach(false, dbhelper.TABLE_NAME_CONSULTAS_MARCADAS, null, null, null, null, null, null,null);
		Cursor cursor = dbhelper.exercutaSELECTSQL(query, null);
		Log.i("SQL", "cursor esta fechado? : " + cursor.isFirst());
		if(cursor.moveToFirst()){
			Log.i("SQL","cursor possui linhas");
			LinkedList<Consulta> listaConsultas = new LinkedList<Consulta>();
			while(!cursor.isAfterLast()){
				Log.i("SQL","passou no is afterlast");
				
				GregorianCalendar gc = dbhelper.textToGregorianCalendar(cursor.getString(2));
				String descricao = cursor.getString(3);
				//Log.w("SQL",descricao);
				String tipo_con = cursor.getString(4);
				
				int idPaciente = cursor.getInt(1);
				String nome = cursor.getString(5);
				String logradouro = cursor.getString(6);
				int numero = cursor.getInt(7);
				String bairro = cursor.getString(8);
				String cidade = cursor.getString(9);
				
				Paciente p = new Paciente(idPaciente,nome,null,(byte)-1,logradouro,bairro,numero,cidade);
				
				Consulta c = new Consulta(p,gc,tipo_con,descricao);

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
 		String query = "SELECT consultas_marcadas.fk_paciente, data_hora, descricao, tipo_con, nome, logradouro, numero, bairro, cidade FROM consultas_marcadas" +
				"INNER JOIN paciente ON id_paceinte = consultas_marcadas.fk_paciente " +
				"INNER JOIN "+dbhelper.TABLE_NAME_TELEFONE+" ON telefone.fk_paciente = id_paciente " +
				"WHERE date(data_hora) >= date('now') " +
				"GROUP BY fk_paciente;";
 		
		/*String query = "SELECT nome, fk_paciente, data_hora, descricao, tipo_con, logradouro, bairro,cidade, telefone " +
				"FROM "+dbhelper.TABLE_NAME_CONSULTAS_MARCADAS+"INNER JOIN "+dbhelper.TABLE_NAME_TELEFONE+" ON "+dbhelper.TABLE_NAME_CONSULTAS_MARCADAS+".fk_paciente = "+dbhelper.TABLE_NAME_TELEFONE+".fk_paciente AND tipo_tel = 1"+
				" WHERE date(data_hora) >= date('now')" +
				"GROUP BY fk_paciente;";*/
				
		Cursor cursor = dbhelper.exercutaSELECTSQL(query, null);
		// Se encontrou
		if(cursor.moveToFirst()){
			LinkedList<Consulta> listaConsultas = new LinkedList<Consulta>();
			while(!cursor.isAfterLast()){
				
				GregorianCalendar gc = dbhelper.textToGregorianCalendar(cursor.getString(2));
				String descricao = cursor.getString(3);
				//Log.w("SQL",descricao);
				String tipo_con = cursor.getString(4);
				
				int idPaciente = cursor.getInt(1);
				String nome = cursor.getString(5);
				String logradouro = cursor.getString(6);
				int numero = cursor.getInt(7);
				String bairro = cursor.getString(8);
				String cidade = cursor.getString(9);
				
				Paciente p = new Paciente(idPaciente,nome,null,(byte)-1,logradouro,bairro,numero,cidade);
				Consulta c = new Consulta(p,gc,tipo_con,descricao);
				
				listaConsultas.add(c);
				
				cursor.moveToNext();
			}
			return listaConsultas;
		}
		else{
			return null;
		}
	}
	
	
	
}
