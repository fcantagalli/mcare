package com.mCare.novocontato;

import java.util.ArrayList;

public class Contato {
	
	long id;
	String nome;
	ArrayList<Integer> telefones;
	String rua;
	int numero;
	String complemento;
	String bairro;
	int cep;
	
	public Contato(String nome, String rua, int numero, String complemento, String bairro, int cep){
		this.nome = nome;
		//this.telefones = telefone;
		this.rua = rua;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cep = cep;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public ArrayList<Integer> getTelefones() {
		return telefones;
	}
	public void setTelefones(ArrayList<Integer> telefones) {
		this.telefones = telefones;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public int getCep() {
		return cep;
	}
	public void setCep(int cep) {
		this.cep = cep;
	}
	
	
}
