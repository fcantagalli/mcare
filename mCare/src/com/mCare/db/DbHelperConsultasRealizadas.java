package com.mCare.db;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.mCare.consulta.Consulta;

public class DbHelperConsultasRealizadas {

	public Db dbhelper;
	
	public DbHelperConsultasRealizadas(Context context){
		dbhelper = Db.getInstance(context);
	}
	
	public ArrayList<String> pegaColunas(){
		Cursor c = dbhelper.exercutaSELECTSQL("pragma table_info(consulta)", null);

		ArrayList<String> nomes = null;
		
		if(c.moveToFirst()){
			Log.i("table","exite alguma coisa no cursor");
			Log.i("table","numero de colunas : "+c.getColumnCount());
			Log.i("table","numero de linhas : "+c.getCount());
			
			nomes = new ArrayList<String>(c.getCount());

			while(!c.isAfterLast()){	
				nomes.add(c.getString(1));
				c.moveToNext();
			}
			
		}
		Log.i("phil", " " + nomes);
		return nomes;
	}
	
	public ArrayList<Consulta> listaConsultasDoPaciente(int id){
	
		String query = "SELECT id_consulta, data_hora FROM consulta " +
				"WHERE fk_paciente = "+id;

		Log.i("fe",query);
		//Cursor cursor = dbhelper.serach(false, dbhelper.TABLE_NAME_CONSULTAS_MARCADAS, null, null, null, null, null, null,null);
		Cursor cursor = dbhelper.exercutaSELECTSQL(query, null);
		// Se encontrou
		if(cursor.moveToFirst()){
			ArrayList<Consulta> listaConsultas = new ArrayList<Consulta>();
			Log.i("fe","existe alguma coisa na tabela consulta");
			while(!cursor.isAfterLast()){
				
				long idc = cursor.getLong(0);
				GregorianCalendar gc = dbhelper.textToGregorianCalendar(cursor.getString(1));
				
				Consulta c = new Consulta(idc,gc);
				
				listaConsultas.add(c);
				
				cursor.moveToNext();
			}
			return listaConsultas;
		}
		else{
			return null;
		}
	}
	
	@SuppressWarnings("unused")
	public HashMap<String,String[]> buscaConsultaRealizada(long id){
		
		Log.i("consulta", "id_consulta: " + id);
		Cursor c = dbhelper.serach(false, dbhelper.TABLE_NAME_CONSULTA, null, "id_consulta = "+id, null, null, null, null, null);
		HashMap<String,String[]> result = null;
		//ArrayList<Object> nomes = new ArrayList<Object>();
		String[] nomes = null;
		String[] conteudo = null;
		if(c.moveToFirst()){
			result = new HashMap<String, String[]>();
			while(!c.isAfterLast()){
				nomes = c.getColumnNames();
				result.put("nomes", nomes);
				
				for(int i=0; i<nomes.length; i++){
					Log.i("phil", "nomes: " + nomes[i]);
				}
				Log.i("phil", "numero de linhas: " + c.getCount());
				
				if(nomes!=null){
					conteudo = new String[nomes.length];
					for(int i=0; i< nomes.length; i++){
						switch(c.getType(i)){
						case Cursor.FIELD_TYPE_INTEGER: {
							conteudo[i] = "" + c.getInt(i);
							break;
						}
						case Cursor.FIELD_TYPE_FLOAT: {
							conteudo[i] = "" + c.getDouble(i);
							break;
						}
						case Cursor.FIELD_TYPE_STRING: {
							conteudo[i] = c.getString(i);
							break;
						}
						}
					}
					
					result.put("conteudos", conteudo);
					c.moveToNext();
				}else{
					Log.i("phil", "nomes na classe BdHelperConsultas eh null");
				}
			}
			
		}
		return result;
	}
	
	public void insereConsulta(String sql){
		
		dbhelper.executaSQL(new String[]{sql});
		
	}
	
	
	
}
