package com.mCare.db;

import android.content.ContentValues;
import android.content.Context;

public class DbHelperTelefone {

private Db dbhelper;
	
	public DbHelperTelefone(Context context){
		dbhelper = Db.getInstance(context);
		
	}
	
	public long insereTelefone(long fk_paciente, String telefone, String tipo_tel){
		
		
		ContentValues cv = new ContentValues();
		
		cv.put("fk_paciente", fk_paciente);
		cv.put("telefone", telefone);
		cv.put("tipo_tel", tipo_tel);
		
		long id = dbhelper.insert(dbhelper.TABLE_NAME_TELEFONE, cv);
				
		return id;
		
	}
	
	public int deletaTelefone(int fk_paciente, long telefone){
		
		int deuCerto = dbhelper.delete(dbhelper.TABLE_NAME_TELEFONE, "fk_paciente=? AND telefone=?", new String[]{""+fk_paciente,""+telefone});
		
		return deuCerto;
	}
	
}
