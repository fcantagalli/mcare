package com.mCare.db;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import com.mCare.media.Foto;
import com.mCare.diagnostico.Diagnostico;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DbHelperMedia {

	public Db dbhelper;
	
	public final int FOTO = 0;
	public final int VIDEO = 1;
	public final int AUDIO = 2;
	
	
	public DbHelperMedia(Context context){
		dbhelper = Db.getInstance(context);
	}
	
	public long insereMedia(int tipoDado,int fk_paciente,String caminho, String descricao, GregorianCalendar gc){
		
		ContentValues cv = new ContentValues();
		
		cv.put("caminho", caminho);
		cv.put("descricao", descricao);
		cv.put("data", dbhelper.formataData(gc));
		cv.put("fk_paciente", fk_paciente);
		
		long id = dbhelper.insert(tabela(tipoDado), cv);
		
		return id;
	}
	// busca as fotos do paciente informado.
	public ArrayList<Foto> buscaFotosPaciente(int id_paciente){
		
		Cursor c = dbhelper.serach(false, tabela(FOTO), null, "fk_paciente="+id_paciente, null, null, null,"data", null);
		ArrayList<Foto> fotos = null;
		if (c.moveToFirst()) {
			fotos = new ArrayList<Foto>(c.getCount());
			while (!c.isAfterLast()) {
				
				
				
				c.moveToNext();
			}
		}
		return fotos;
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
