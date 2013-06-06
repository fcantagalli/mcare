package com.mCare.consulta;

import java.util.GregorianCalendar;

import com.mCare.paciente.Paciente;

public class Consulta {
	
	private GregorianCalendar hora;
	private Paciente p;
	private String descricao; // pode ser null
	private String tipo_con;
	private long id;
	
	
	public Consulta(Paciente p, GregorianCalendar hora, String tipo_con, String descricao){
		this.hora = hora;
		this.tipo_con = tipo_con;
		this.descricao = descricao;
		this.p = p;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTipo(){
		return this.tipo_con;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Paciente getPaciente() {
		return p;
	}

	public String getDescricao() {
		return descricao;
	}

	public GregorianCalendar getHora() {
		return hora;
	}


	public void setHora(GregorianCalendar hora) {
		this.hora = hora;
	}
	
}
