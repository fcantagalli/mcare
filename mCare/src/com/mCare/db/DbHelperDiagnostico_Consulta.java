package com.mCare.db;

import java.util.GregorianCalendar;

import android.content.ContentValues;
import android.content.Context;

public class DbHelperDiagnostico_Consulta {

	
public Db dbhelper;
	
	public DbHelperDiagnostico_Consulta(Context context) {
		// TODO Auto-generated constructor stub
		dbhelper = Db.getInstance(context);
	}
	
	public long insereDiagnostico_Consulta(long id_diagnostico, long id_consulta){
		ContentValues cv = new ContentValues();
		
		cv.put("id_diagnostico", id_diagnostico);
		cv.put("id_consulta", id_consulta);
		cv.put("data_consulta", dbhelper.formataData(new GregorianCalendar()));
		
		long id = dbhelper.insert(dbhelper.TABLE_NAME_MEDICAMENTO_PACIENTE, cv);
		return id;
	}
	
	
}
