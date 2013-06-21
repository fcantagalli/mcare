package com.mCare.db;

import android.content.Context;

public class DbHelperMedicamento_Paciente {
	
	public Db dbhelper;
	
	public DbHelperMedicamento_Paciente(Context context){
		dbhelper = Db.getInstance(context);
	}
}
