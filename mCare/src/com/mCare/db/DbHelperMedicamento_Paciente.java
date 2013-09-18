package com.mCare.db;

import java.util.GregorianCalendar;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

public class DbHelperMedicamento_Paciente {
	
	public Db dbhelper;
	
	public DbHelperMedicamento_Paciente(Context context){
		dbhelper = Db.getInstance(context);
	}
	
	public long insereMedicamento_Paciente(long id_medicamento, long id_consulta, int fk_paciente, String treadManyTime, 
			String treadManyType,String medPeriod,String medPeriodTime ,String medRecomm, String missDosePeriod, String missDoseType,
			String missDoseRecomm){
		ContentValues cv = new ContentValues();
		
		Log.i("dddd","informacoes do meds:\n"+
		"idmed "+id_medicamento+
		"id_consulta "+id_consulta+
		"fk_consulta  "+fk_paciente+
		"treadMany  "+treadManyTime+
		"treadManyType  "+treadManyType+
		"medPeriod  "+medPeriod+
		"medPeriodTime  "+medPeriodTime+
		"medRecomm  "+medRecomm+
		"missDosePeriod  "+missDosePeriod+
		"missDoseType  "+missDoseType+
		"missDoseRecomm  "+missDoseRecomm);
		
		cv.put("id_medicamento", id_medicamento);
		cv.put("id_consulta", id_consulta);
		cv.put("fk_paciente", fk_paciente);
		cv.put("data_consulta", dbhelper.formataData(new GregorianCalendar()));
		cv.put("tread_many_time", treadManyTime);
		cv.put("tread_many_time_type", treadManyType);
		cv.put("med_period", medPeriod);
		cv.put("med_period_time", medPeriodTime);
		cv.put("med_recommendation", medRecomm);
		cv.put("miss_dose_period", missDosePeriod);
		cv.put("miss_dose_type", missDoseType);
		cv.put("miss_dose_recomm", missDoseRecomm);
		
		long id = dbhelper.insert(dbhelper.TABLE_NAME_MEDICAMENTO_PACIENTE, cv);
		return id;
	}
	
}
