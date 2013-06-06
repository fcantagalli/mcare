package com.mCare.configuracaoConsulta;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.util.Log;

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
	
	ConsultaModel(ArrayList<String> fields, ArrayList<Integer> types){
		this.fields = fields;
		this.types = types;
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
			sql = sql + nomeCampo + "@" + types.get(i) + " " + tipoSQL;
			if(i<fields.size()-1){
				sql = sql + ", ";
			}
		}
		sql = sql + ");";
		return sql;
	}
	
	public boolean createTable(){
		String sql = generateSQL();
		Log.i("Phil", "sql: " + sql);
		return true;
	}
}