package com.mCare.db;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class DbHelperConsultasRealizadas {

	public Db dbhelper;
	
	public DbHelperConsultasRealizadas(Context context){
		dbhelper = Db.getInstance(context);
	}
	
	public ArrayList<String> pegaColunas(){
		Cursor c = dbhelper.exercutaSELECTSQL("pragma table_info(consulta)", null);

		ArrayList<String> nomes = null;
		
		if(c.moveToFirst()){
			Log.i("table","exite alguma coisa no cursor");
			Log.i("table","numero de colunas : "+c.getColumnCount());
			Log.i("table","numero de linhas : "+c.getCount());
			
			nomes = new ArrayList<String>(c.getCount());

			while(!c.isAfterLast()){	
				nomes.add(c.getString(1));
				c.moveToNext();
			}
			
		}
		Log.i("phil", " " + nomes);
		return nomes;
	}
	
	public HashMap<String,Object> buscaConsultaRealizada(long id){
		
		Cursor c = dbhelper.serach(false, dbhelper.TABLE_NAME_CONSULTA, null, "id_consulta="+id, null, null, null, null, null);
		HashMap<String,Object> result = null;
		if(c.moveToFirst()){
			result = new HashMap<String,Object>();
			while(!c.isAfterLast()){
				result.put("nomes", c.getColumnNames());
				result.put("dados",c.getExtras());
			}
			
		}
		return result;
	}
	
	public void insereConsulta(String sql){
		
		dbhelper.executaSQL(new String[]{sql});
		
	}
	
	
	
}
