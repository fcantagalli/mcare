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
		String sql = "CREATE TABLE mCare.consulta(";
		String type = "";
		for(int i=0; i<fields.size(); i++){
			if(types.get(i) == 0){
				type = "REAL";
			}else{
				type = "TEXT";
			}
			sql = sql + fields.get(i) + " " + type;
			if(i<fields.size()-1){
				sql = sql + ";";
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