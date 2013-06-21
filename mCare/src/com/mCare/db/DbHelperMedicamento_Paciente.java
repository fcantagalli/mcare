package com.mCare.db;

import java.util.GregorianCalendar;

import android.content.ContentValues;
import android.content.Context;

public class DbHelperMedicamento_Paciente {
	
	public Db dbhelper;
	
	public DbHelperMedicamento_Paciente(Context context){
		dbhelper = Db.getInstance(context);
	}
	
	public long insereMedicamento_Paciente(long id_medicamento, long id_consulta, int fk_paciente){
		ContentValues cv = new ContentValues();
		
		cv.put("id_medicamento", id_medicamento);
		cv.put("id_consulta", id_consulta);
		cv.put("fk_paciente", fk_paciente);
		cv.put("data_consulta", dbhelper.formataData(new GregorianCalendar()));
		
		long id = dbhelper.insert(dbhelper.TABLE_NAME_MEDICAMENTO_PACIENTE, cv);
		return id;
	}
	
}
