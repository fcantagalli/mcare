package com.mCare.consulta.realizarConsulta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.EditText;
import android.widget.Spinner;

import com.mCare.medicamento.Medicamento;

public class GroupEntity {

	private Integer id;
	private String descricao;
	private String tipo;
	public List<Medicamento> listChild;
	public Map<Integer, Boolean> childSelected = new HashMap<Integer, Boolean>();
	Spinner[] days;
	EditText[] hours;

	public GroupEntity(Integer id, String descricao, String tipo) {
		this.id = id;
		this.descricao = descricao;
		this.tipo = tipo;
	}
	
	public Spinner[] getDays(){
		return days;
	}

	public EditText[] getHours(){
		return hours;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Medicamento> getListChild() {
		return listChild;
	}

	public void setListChild(List<Medicamento> listChild) {
		this.listChild = listChild;
		setContainers();
	}

	private void setContainers(){
		this.days = new Spinner[listChild.size()];
		this.hours = new EditText[listChild.size()];
	}
	
	@Override
	public String toString() {
		return this.getId() + " - " + this.getDescricao();
	}
}
