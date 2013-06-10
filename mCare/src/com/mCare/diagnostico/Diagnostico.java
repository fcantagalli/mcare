package com.mCare.diagnostico;

import java.util.GregorianCalendar;

public class Diagnostico {

	private int id;
	private String nome;
	
	//Campos de Diagnostico_Consulta
	private long id_consulta;
	private GregorianCalendar hora;
	
	
	//CONSTRUTOR
	public Diagnostico(int id, String nome){
		this.id = id;
		this.nome = nome;
	}
	
	//ENCAPSULAMENTOS
	public int getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	//Campos de Diagnostico_Consulta
	public long getIdConsulta() {
		return id_consulta;
	}
	public void setIdConsulta(long id_consulta) {
		this.id_consulta = id_consulta;
	}
	
	public GregorianCalendar getHora() {
		return hora;
	}
	public void setHora(GregorianCalendar hora) {
		this.hora = hora;
	}
	
	
}
