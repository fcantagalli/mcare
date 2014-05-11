package com.mCare.medicamento;

import java.util.GregorianCalendar;

public class Medicamento {

	private String nome;
	private int id;
	private String tipo;
	private String dosagem;
	private String pricipioAtivo;
	private boolean favorito;
	private String hours;
	private int days;
	
	//Campos de Medicamento_Paciente
	private long id_consulta;
	private long id_paciente;
	private GregorianCalendar hora;
	
	//Campo para verificar se eh um Medicamento, Diagnostico ou Exame (usado nas ListViews do ListaMedicamentosPaciente = Dados Adicionais)
	private String natureza_verdadeira;
	
	private String tread_many_time;
	private String tread_many_time_type;
	private String med_period;
	private String med_period_time;
	private String med_recommendation;
	private String miss_dose_period;
	private String miss_dose_type;
	private String miss_dose_recomm;
	
	public String getTread_many_time() {
		return tread_many_time;
	}

	public void setTread_many_time(String tread_many_time) {
		this.tread_many_time = tread_many_time;
	}

	public String getTread_many_time_type() {
		return tread_many_time_type;
	}

	public void setTread_many_time_type(String tread_many_time_type) {
		this.tread_many_time_type = tread_many_time_type;
	}

	public String getMed_period() {
		return med_period;
	}

	public void setMed_period(String med_period) {
		this.med_period = med_period;
	}

	public String getMed_period_time() {
		return med_period_time;
	}

	public void setMed_period_time(String med_period_time) {
		this.med_period_time = med_period_time;
	}

	public String getMed_recommendation() {
		return med_recommendation;
	}

	public void setMed_recommendation(String med_recommendation) {
		this.med_recommendation = med_recommendation;
	}

	public String getMiss_dose_period() {
		return miss_dose_period;
	}

	public void setMiss_dose_period(String miss_dose_period) {
		this.miss_dose_period = miss_dose_period;
	}

	public String getMiss_dose_type() {
		return miss_dose_type;
	}

	public void setMiss_dose_type(String miss_dose_type) {
		this.miss_dose_type = miss_dose_type;
	}

	public String getMiss_dose_recomm() {
		return miss_dose_recomm;
	}

	public void setMiss_dose_recomm(String miss_dose_recomm) {
		this.miss_dose_recomm = miss_dose_recomm;
	}
	
	//CONSTRUTORES
	public Medicamento(int id, String nome){
		this.id = id;
		this.setNome(nome);
	}
	
	public Medicamento(int id, String nome, String tipo){
		this.id = id;
		this.setNome(nome);
		this.setTipo(tipo);
	}
	
	public Medicamento(int id, String nome, String tipo, String natureza_verdadeira){
		this.id = id;
		this.setNome(nome);
		this.setTipo(tipo);
		this.setNaturezaVerdadeira(natureza_verdadeira);
	}
	
	// TO STRING
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String d = dosagem;
		String p = pricipioAtivo;
		if(d == null){
			d = "";
		}
		if(p == null){
			p = "";
		}
		return nome+"  "+d+"\n"+p;
	}

	//ENCAPSULAMENTO
	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
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
	
	
	//Campo para verificar se eh um Medicamento, Diagnostico ou Exame (usado nas ListViews do ListaMedicamentosPaciente = Dados Adicionais)
	public String getNaturezaVerdadeira() {
		return natureza_verdadeira;
	}
	public void setNaturezaVerdadeira(String natureza_verdadeira) {
		this.natureza_verdadeira = natureza_verdadeira;
	}
	
}
