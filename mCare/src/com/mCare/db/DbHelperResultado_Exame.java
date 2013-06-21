package com.mCare.db;

import android.content.ContentValues;
import android.content.Context;

public class DbHelperResultado_Exame {

	
public Db dbhelper;
	
	public DbHelperResultado_Exame(Context context) {
		dbhelper = Db.getInstance(context);
	}
	
	public long insereResultado_Exame(long id_exame, long id_consulta, String nome, String valor){
		ContentValues cv = new ContentValues();
		
		cv.put("id_exame", id_exame);
		cv.put("id_consulta", id_consulta);
		cv.put("nome", nome);
		cv.put("valor", valor);
		
		long id = dbhelper.insert(dbhelper.TABLE_NAME_RESULTADO_EXAME, cv);
		return id;
	}
	
}
