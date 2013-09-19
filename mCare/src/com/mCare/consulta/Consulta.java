package com.mCare.consulta;

import java.util.GregorianCalendar;

import com.mCare.paciente.Paciente;

public class Consulta implements Comparable<Consulta> {
	
	private GregorianCalendar hora;
	private Paciente p;
	private String descricao; // pode ser null
	private String tipo_con;
	private long id;
	
	public Consulta(long id, String descricao, GregorianCalendar hora){
		this.id = id;
		this.hora = hora;
		this.descricao = descricao;
	}
	
	
	@Override
	public String toString() {
		return "Date: " + hora.get(GregorianCalendar.DAY_OF_MONTH) + "/" + hora.get(GregorianCalendar.MONTH) + "/" + hora.get(GregorianCalendar.YEAR) + "\nDescription: " + descricao;
	}


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

	@Override
	public int compareTo(Consulta another) {
		// TODO Auto-generated method stub
		
		return hora.compareTo(another.getHora());
	}
	
}
