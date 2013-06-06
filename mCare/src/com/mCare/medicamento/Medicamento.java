package com.mCare.medicamento;

public class Medicamento {

	private String nome;
	private int id;
	private String tipo;
	private String dosagem;
	private String pricipioAtivo;
	
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
	public int getId() {
		return id;
	}
	
}
