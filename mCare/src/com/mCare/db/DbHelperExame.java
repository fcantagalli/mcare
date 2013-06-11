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
import com.mCare.diagnostico.Diagnostico;
import com.mCare.exame.Exame;
import com.mCare.medicamento.Medicamento;


public class DbHelperExame {

	public Db dbhelper;
	
	public DbHelperExame(Context context){
		dbhelper = Db.getInstance(context);
	}
	
	public long insereExame(Exame e){
		ContentValues cv = new ContentValues();
		
		cv.put("nome", e.getNome());
		cv.put("tipocampovalor", e.getTipoResultadoExame());
		
		long id = dbhelper.insert(dbhelper.TABLE_NAME_EXAME, cv);
		
		return id;
	}
	
	public boolean deletaExame(long id){
		
		int i = dbhelper.delete(dbhelper.TABLE_NAME_EXAME, "id_exame=?", new String[]{""+id});
		
		if(i > 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public Exame buscaExame(int id) {

		String query = "SELECT id_exame, nome, tipocampovalor "
				+ " FROM " + dbhelper.TABLE_NAME_EXAME
				+ " WHERE id_exame = '" + id + "';";

		Cursor c = dbhelper.exercutaSELECTSQL(query, null);
		Exame e = null;

		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {

				//Pega do select
				int idExame = c.getInt(0);
				String nomeExame = c.getString(1);
				int tipoResultadoExame = c.getInt(2);
				
				//Coloca na classe
				e = new Exame(idExame,nomeExame,tipoResultadoExame);
				
				c.moveToNext();
			}
		}
		return e;
	}

	public LinkedList<Exame> listaExames(){
		
		String query = "SELECT id_exame, nome, tipocampovalor " +
						" FROM "+dbhelper.TABLE_NAME_EXAME;
		
		//Executa o SQL
		Cursor cursor = dbhelper.exercutaSELECTSQL(query, null);
		
		Log.i("SQL", "cursor esta fechado? : " + cursor.isFirst());
		if(cursor.moveToFirst()){
			Log.i("SQL","cursor possui linhas");
			
			//Armazena resultado
			LinkedList<Exame> listaExames = new LinkedList<Exame>();
			while(!cursor.isAfterLast()){
				Log.i("SQL","passou no is afterlast");
				
				int id_exame = Integer.parseInt(cursor.getString(0));
				String nome = cursor.getString(1);
				int tipo_resultado_exame = Integer.parseInt(cursor.getString(2));
				int id_consulta = Integer.parseInt(cursor.getString(3));
				String valor = cursor.getString(4);
				
				
				Exame e = new Exame(id_exame, nome, tipo_resultado_exame);
				e.setIdConsulta(id_consulta);
				e.setValor(valor);
				
				//Adiciona aos diagnosticos
				listaExames.add(e);
				cursor.moveToNext();
			}
			
			//Ordena exames pelo nome
			Collections.sort(listaExames, new Comparator<Exame>() {
		         @Override
		         public int compare(Exame o1, Exame o2) {
		             return Collator.getInstance().compare(o1.getNome(), o2.getNome());
		         }
		     });
			
			return listaExames;
		}
		return null;
	}
	
	public LinkedList<Exame> listaExames(Consulta c){
	
		//Busca todos os exames com resultado cadastrado naquela consulta
		String query = " SELECT exame.id_exame, exame.nome, exame.tipocampovalor " +
						" 	resultado_exame.id_consulta, resultado_exame.valor" +
						" FROM exame" +
						" INNER JOIN resultado_exame ON resultado_exame.id_exame = exame.id_exame " +
						" WHERE resultado_exame.id_consulta = " + c.getId();
		
		//Executa o SQL
		Cursor cursor = dbhelper.exercutaSELECTSQL(query, null);
		
		Log.i("SQL", "cursor esta fechado? : " + cursor.isFirst());
		if(cursor.moveToFirst()){
			Log.i("SQL","cursor possui linhas");
			
			//Armazena resultado
			LinkedList<Exame> listaExames = new LinkedList<Exame>();
			while(!cursor.isAfterLast()){
				Log.i("SQL","passou no is afterlast");
				
				int id_exame = Integer.parseInt(cursor.getString(0));
				String nome = cursor.getString(1);
				int tipo_resultado_exame = Integer.parseInt(cursor.getString(2));
				int id_consulta = Integer.parseInt(cursor.getString(3));
				String valor = cursor.getString(4);
				
				
				Exame e = new Exame(id_exame, nome, tipo_resultado_exame);
				e.setIdConsulta(id_consulta);
				e.setValor(valor);
				
				//Adiciona aos diagnosticos
				listaExames.add(e);
				cursor.moveToNext();
			}
			
			//Ordena exames pelo nome
			Collections.sort(listaExames, new Comparator<Exame>() {
		         @Override
		         public int compare(Exame o1, Exame o2) {
		             return Collator.getInstance().compare(o1.getNome(), o2.getNome());
		         }
		     });
			
			return listaExames;
		}
		return null;
	}
}

