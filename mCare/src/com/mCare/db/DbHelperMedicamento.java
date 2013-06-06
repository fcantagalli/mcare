package com.mCare.db;

import java.util.LinkedList;

import android.content.ContentValues;
import android.content.Context;

import com.mCare.medicamento.Medicamento;

public class DbHelperMedicamento {

	public Db dbhelper;
	
	public DbHelperMedicamento(Context context){
		dbhelper = dbhelper.getInstance(context);
	}
	
	public long insereMedicamento(Medicamento m){
		ContentValues cv = new ContentValues();
		
		cv.put("nome", m.getNome());
		cv.put("tipo", m.getTipo());
		cv.put("dosagem", m.getDosagem());
		cv.put("principioativo", m.getPricipioAtivo());
		
		long id = dbhelper.insert(dbhelper.TABLE_NAME_MEDICAMENTO, cv);
		
		return id;
	}
	
	public boolean deletaMedicamento(long id){
		
		int i = dbhelper.delete(dbhelper.TABLE_NAME_MEDICAMENTO, "id_medicamento=?", new String[]{""+id});
		
		if(i > 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	/*public LinkedList<Medicamento> listaMedicamento(){
		
		
		
	}*/
}
