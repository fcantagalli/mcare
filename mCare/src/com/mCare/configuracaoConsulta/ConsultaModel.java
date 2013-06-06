package com.mCare.configuracaoConsulta;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.mCare.db.Db;

@SuppressLint("DefaultLocale")
public class ConsultaModel {
	
	/*
	 * SQlite Datatypes
	 * INTEGER - 1
	 * REAL - 2
	 * TEXT - 3
	 * BLOB - 4
	 */
	
	private ArrayList<String> fields;
	private ArrayList<Integer> types;
	private Context context;
	
	ConsultaModel(ArrayList<String> fields, ArrayList<Integer> types, Context context){
		this.fields = fields;
		this.types = types;
		this.context = context;
	}
	
	@SuppressLint("DefaultLocale")
	public String generateSQL(){
		String sql = "CREATE TABLE consulta (id_consulta INTEGER PRIMARY KEY, fk_paciente INTEGER NOT NULL REFERENCES paciente(id_paciente) ON UPDATE CASCADE ON DELETE CASCADE, data_hora TEXT NOT NULL, descricao TEXT NULL, tipo_con TEXT NULL, ";
		String tipoSQL = "";
		String nomeCampo = "";
		for(int i=0; i<fields.size(); i++){
			switch(types.get(i)){
			case 0: tipoSQL = "INTEGER";
			break;
			case 1: tipoSQL = "REAL";
			break;
			default: tipoSQL = "TEXT";
			break;
			}
			nomeCampo = fields.get(i).replaceAll(" ", "_");
			sql = sql + "'" + nomeCampo + "@" + types.get(i) + "' " + tipoSQL;
			if(i<fields.size()-1){
				sql = sql + ", ";
			}
		}
		sql = sql + ");";
		return sql;
	}
	
	public boolean createTable(){
		String sql = generateSQL();
		Log.i("SQL", sql);
		Db db = Db.getInstance(context);
		db.executaSQL(new String[]{sql});
		return true;
	}
}