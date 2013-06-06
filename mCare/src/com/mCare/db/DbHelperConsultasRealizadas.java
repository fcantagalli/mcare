package com.mCare.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class DbHelperConsultasRealizadas {

	public Db dbhelper;
	
	public DbHelperConsultasRealizadas(Context context){
		dbhelper.getInstance(context);
	}
	
	public ArrayList<String> pegaColunas(){
		Cursor c = dbhelper.exercutaSELECTSQL("pragma table_info(telefone)", null);

		ArrayList<String> nomes = null;
		
		if(c.moveToFirst()){
			Log.i("table","exite alguma coisa no cursor");
			Log.i("table","numero de colunas : "+c.getColumnCount());
			Log.i("table","numero de linhas : "+c.getCount());
			
			nomes = new ArrayList<String>(c.getCount());

			while(!c.isAfterLast()){	
				nomes.add(c.getString(1));
			}
			
		}
		return nomes;
	}
	
	public long insereConsulta(int id){
		
		ContentValues cv = new ContentValues();
		
		long idNovo = dbhelper.insert(dbhelper.TABLE_NAME_CONSULTA, cv);
		return idNovo;
	}
	
}
