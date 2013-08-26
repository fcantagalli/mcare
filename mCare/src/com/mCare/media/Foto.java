package com.mCare.media;

import java.util.GregorianCalendar;

public class Foto {

	private long id;
	private String caminho;
	private String descricao;
	private GregorianCalendar data;
	
	public Foto(long id, String caminho){
		this.id = id;
		this.caminho = caminho;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public GregorianCalendar getData() {
		return data;
	}
	public void setData(GregorianCalendar data) {
		this.data = data;
	}
	public long getId() {
		return id;
	}
	public String getCaminho() {
		return caminho;
	}
	
	
}
