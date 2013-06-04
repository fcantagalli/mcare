package com.mCare.db;

import java.util.Arrays;
import java.util.Collections;
import java.util.GregorianCalendar;

import com.mCare.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Classe respons�vel pelo banco de dados do aplicativo
 * 
 * @author Felipe
 * 
 */

public class Db extends SQLiteOpenHelper {
	
	private static final String NOME_DB = "mCareDB";
	
	// formato de data a ser usado: YYYY-MM-DD HH:MM
	
	/** A vers�o da base de dados que esta classe compreende. */
	private static final int VERSAO_BD = 1;
	private static final String LOG_TAG = "DB";
	public final String TABLE_NAME_CONSULTAS_MARCADAS = "consultas_marcadas";
	public final String TABLE_NAME_PACIENTES = "paciente";
	public final String TABLE_NAME_TELEFONE = "telefone"; // tipo tel : 0 - residencial, 1 - celular, 2 - comercial, 3 - outros (se e que precisa)
	
	private static Db dbhelper;
	private final Context context;

	private Db(Context contexto) {
		super(contexto, NOME_DB, null, VERSAO_BD);
		this.context = contexto;
	}
	
	/**
	 * Implementa��o de um singleton
	 * @param context
	 * @return
	 */
	public static Db getInstance(Context context){
	        if(dbhelper == null)
	        {
	            dbhelper = new Db(context);
	        }
	        return dbhelper;
	}
	
	public Cursor serach(boolean UNIQUE, String tablename, String[] coluns, String WHERE,String[] selectionArgs, String GROUPBY, String HAVING, String ORDERBY, String limitOfRows ){
		
		 SQLiteDatabase db = getReadableDatabase();
		 Cursor c= db.query(UNIQUE, tablename, coluns, WHERE, selectionArgs, GROUPBY, HAVING, ORDERBY, limitOfRows);
		// db.close();
		 return c;
	}
	
	public long insert(String tableName, ContentValues cv){
		SQLiteDatabase db = getWritableDatabase();
		long id = -1;
		try{
			id = db.insertOrThrow(tableName, "", cv);
		}catch(SQLException e){
			id = -1;
			Toast.makeText(context, "não foi possível inserir o registro na tabela "+tableName,Toast.LENGTH_SHORT).show();
		}
		//db.close();
		return id;
	}
	
	public int delete(String tableName, String whereClause, String[] whereArgs){
		SQLiteDatabase db = getWritableDatabase();
		int deuCerto  = db.delete(tableName, whereClause, whereArgs);
		db.close();
		return deuCerto;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("PRAGMA foreign_keys=ON;");
		String[] codigoSql = context.getString(R.string.SQL_CREATE_TABLES).split("@");
		executaSQL(db, codigoSql);
		Log.i("SQL","tabela criada");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(LOG_TAG, "Atualizando a base de dados da vers�o " + oldVersion
				+ " para " + newVersion
				+ ", que destruir� todos os dados antigos");
		
		//db.execSQL("DROP TABLE IF EXISTS"+ " pessoa");
		// contexto.getString(R.string.ContextoDados_onUpgrade)
	}
	
	private void executaSQL(SQLiteDatabase db, String[] sql) {
		for (String s : sql)
			if (s.length() > 0){ // para evitar string vazias
				Log.w("SQL","print de sql : "+s);
				try{
					db.execSQL(s);
					Log.i("SQL","Passou no sql de boas :D");
				}catch(SQLException e){
					Log.e("SQL",e.getMessage());
				}
			}
	}
	public Cursor exercutaSELECTSQL(String sql,String[] selectionArgs){
		SQLiteDatabase db = getReadableDatabase();
		Cursor  c = db.rawQuery(sql, selectionArgs);
		//db.close();
		return c;
	}
	
	public void executaSQL(String[] sql){
		SQLiteDatabase db = getWritableDatabase();
		for (String s : sql)
			if (s.trim().length() > 0) // para evitar string vazias
				db.execSQL(s);
		db.close();
	}
	
	public String formataData(GregorianCalendar gc){
		String formatada;
		try{
			
			String mes = ""+gc.get(gc.MONTH);
			String dia = ""+gc.get(gc.DATE);
			if(mes.length() == 1){
				mes = "0"+mes;
			}
			if(dia.length()==1){
				dia = "0"+dia;
			}
			
			String hora = ""+gc.get(gc.HOUR_OF_DAY);
			String minuto = ""+gc.get(gc.MINUTE);
			if(hora.length() == 1){
				hora = "0"+hora;
			}
			if(minuto.length()==1){
				minuto = "0"+minuto;
			}
			formatada = gc.get(gc.YEAR)+"-"+mes+"-"+dia+" "+hora+":"+minuto;
		}catch(IllegalArgumentException e){
			Log.e("GREG",e.getMessage());
			formatada = gc.get(gc.YEAR)+"-"+gc.get(gc.MONTH)+"-"+gc.get(gc.DATE);
		}
		
		return formatada;
	}
	
	public GregorianCalendar textToGregorianCalendar(String data){

		String mes = data.substring(5, 7);
		if(mes.length() == 1){
			mes = "0"+mes;
		}
		String dia = data.substring(8, 10);
		if(dia.length() == 1){
			dia = "0"+mes;
		}
		
		if(data.length() > 11){
			return new GregorianCalendar(Integer.parseInt(data.substring(0,4)), Integer.parseInt(mes) ,Integer.parseInt(dia) ,Integer.parseInt(data.substring(11,13)) ,Integer.parseInt(data.substring(13,15)));
		}
		else{
			return new GregorianCalendar(Integer.parseInt(data.substring(0,4)), Integer.parseInt(mes) ,Integer.parseInt(dia));
		}
	}
}