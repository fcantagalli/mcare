package com.mCare.medicamento;

import java.util.GregorianCalendar;

public class Medicamento implements Comparable<Medicamento> {

	private String nome;
	private int id;
	private String tipo;
	private String dosagem;
	private String pricipioAtivo;
	private boolean favorito;
	
	//Campos de Medicamento_Paciente
	private long id_consulta;
	private long id_paciente;
	private GregorianCalendar hora;
	
	
	
	
	public Medicamento(int id, String nome){
		this.id = id;
		this.nome = nome;
	}
	
	public Medicamento(int id, String nome, String tipo){
		this.id = id;
		this.nome = nome;
		this.tipo = tipo;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDosagem() {
		return dosagem;
	}
	public void setDosagem(String dosagem) {
		this.dosagem = dosagem;
	}
	public String getPricipioAtivo() {
		return pricipioAtivo;
	}
	public void setPricipioAtivo(String pricipioAtivo) {
		this.pricipioAtivo = pricipioAtivo;
	}
	public boolean getFavorito() {
		return favorito;
	}
	public void setFavorito(boolean favorito) {
		this.favorito = favorito;
	}
	
	
	public int getId() {
		return id;
	}
	
	
	
	//Campos de Medicamento_Paciente
	public long getIdConsulta() {
		return id_consulta;
	}
	public void setIdConsulta(long id_consulta) {
		this.id_consulta = id_consulta;
	}
	
	public long getIdPaciente() {
		return id_paciente;
	}
	public void setIdPaciente(long id_paciente) {
		this.id_paciente = id_paciente;
	}
	
	public GregorianCalendar getHora() {
		return hora;
	}
	public void setHora(GregorianCalendar hora) {
		this.hora = hora;
	}

	@Override
	public int compareTo(Medicamento another) {
		// TODO Auto-generated method stub
		return this.nome.compareTo(another.nome);
	}
	
	
}
