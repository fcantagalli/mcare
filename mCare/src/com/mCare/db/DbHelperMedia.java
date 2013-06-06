package com.mCare.db;

import java.util.GregorianCalendar;

import android.content.ContentValues;
import android.content.Context;

public class DbHelperMedia {

	public Db dbhelper;
	
	public final int FOTO = 0;
	public final int VIDEO = 1;
	public final int AUDIO = 2;
	
	
	public DbHelperMedia(Context context){
		dbhelper.getInstance(context);
	}
	
	public long insereMedia(int tipoDado,String caminho, String descricao, GregorianCalendar gc){
		
		ContentValues cv = new ContentValues();
		
		cv.put("caminho", caminho);
		cv.put("descricao", descricao);
		cv.put("data", dbhelper.formataData(gc));
		
		long id = dbhelper.insert(tabela(tipoDado), cv);
		
		return id;
	}
	
	public boolean deleteMedia(int tipoDado, long id){
		
		int rows = dbhelper.delete(tabela(tipoDado),"id=?" , new String[]{""+id});
		
		if(rows == 0){
			return false;
		}
		else{
			return true;
		}
	}
	
	
	private String tabela(int tipoDado){
		switch(tipoDado){
		
			case FOTO: return dbhelper.TABLE_NAME_FOTO;
		
			case VIDEO: return dbhelper.TABLE_NAME_VIDEO;
			
			case AUDIO: return dbhelper.TABLE_NAME_AUDIO;
			
			default: return "valor inválido";
		}
		
	}
	
}
