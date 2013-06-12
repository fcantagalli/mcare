package com.mCare.main;

import java.util.List;

import android.content.Intent;

import com.mCare.consulta.Consulta;
import com.mCare.db.DbHelperConsultas;

public class Agenda_Fragment extends Lista_Consultas_Fragment {

	@Override
	public List<Consulta> retornaConsultas(DbHelperConsultas db) {
		return db.consultasDoDia();
	}
	
	
}
