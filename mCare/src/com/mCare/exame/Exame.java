package com.mCare.exame;

public class Exame {

	private int id;
	private String nome;
	private int tipo_resultado_exame;
	
	//Campos de resultado_exame
	private int id_consulta;
	private String valor;
	
	//CONSTRUTOR
	public Exame(int id, String nome, int tipo_resultado_exame){
		this.id = id;
		this.nome = nome;
		this.tipo_resultado_exame = tipo_resultado_exame;
	}
	

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nome;
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
	
	public int getTipoResultadoExame() {
		return tipo_resultado_exame;
	}
	public void setTipoResultadoExame(int tipo_resultado_exame) {
		this.tipo_resultado_exame = tipo_resultado_exame;
	}
	
	
	//Campos de resultado_exame
	public int getIdConsulta() {
		return id_consulta;
	}
	public void setIdConsulta(int id_consulta) {
		this.id_consulta = id_consulta;
	}
	
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
}
