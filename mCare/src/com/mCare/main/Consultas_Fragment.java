package com.mCare.main;

import java.util.List;

import com.mCare.consulta.Consulta;
import com.mCare.db.DbHelperConsultas;

public class Consultas_Fragment extends Lista_Consultas_Fragment {

	
	@Override
	public List<Consulta> retornaConsultas(DbHelperConsultas db) {
		return db.todasConsultas();
	}

}
