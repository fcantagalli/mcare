package com.mCare.main;

import java.util.List;

import android.util.Log;

import com.mCare.consulta.Consulta;
import com.mCare.db.DbHelperConsultas;

public class Agenda_Fragment extends Lista_Consultas_Fragment {

	@Override
	public List<Consulta> retornaConsultas(DbHelperConsultas db) {
		List<Consulta> consultas = db.consultasDoDia();
		Log.i("Agenda_Fragment", "consultas: " + consultas);
		return db.consultasDoDia();
	}
	
	
}
