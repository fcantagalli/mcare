package com.mCare.consulta.realizarConsulta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mCare.medicamento.Medicamento;

public class GroupEntity {

	private Integer id;
	private String descricao;
	private String tipo;
	public List<Medicamento> listChild;
	public Map<Integer, Boolean> childSelected = new HashMap<Integer, Boolean>();
	Spinner[] treadManyTime;
	Spinner[] treadManyType;
	Spinner[] medFreq;
	TextView[] medFreqTime;
	EditText[] Recommendations;
	Spinner[] missDosePeriod;
	Spinner[] missDoseType;
	EditText[] missDoseRecomm;
	
	public GroupEntity(Integer id, String descricao, String tipo) {
		this.id = id;
		this.descricao = descricao;
		this.tipo = tipo;
	}
	
	public Spinner[] getTreadManyTime() {
		return treadManyTime;
	}



	public void setTreadManyTime(Spinner[] treadManyTime) {
		this.treadManyTime = treadManyTime;
	}



	public Spinner[] getTreadManyType() {
		return treadManyType;
	}



	public void setTreadManyType(Spinner[] treadManyType) {
		this.treadManyType = treadManyType;
	}



	public Spinner[] getMedFreq() {
		return medFreq;
	}



	public void setMedFreq(Spinner[] medFreq) {
		this.medFreq = medFreq;
	}



	public TextView[] getMedFreqTime() {
		return medFreqTime;
	}



	public void setMedFreqTime(TextView[] medFreqTime) {
		this.medFreqTime = medFreqTime;
	}



	public EditText[] getRecommendations() {
		return Recommendations;
	}



	public void setRecommendations(EditText[] recommendations) {
		Recommendations = recommendations;
	}



	public Spinner[] getMissDosePeriod() {
		return missDosePeriod;
	}



	public void setMissDosePeriod(Spinner[] missDosePeriod) {
		this.missDosePeriod = missDosePeriod;
	}



	public Spinner[] getMissDoseType() {
		return missDoseType;
	}



	public void setMissDoseType(Spinner[] missDoseType) {
		this.missDoseType = missDoseType;
	}



	public EditText[] getMissDoseRecomm() {
		return missDoseRecomm;
	}



	public void setMissDoseRecomm(EditText[] missDoseRecomm) {
		this.missDoseRecomm = missDoseRecomm;
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
		treadManyTime = new Spinner[listChild.size()];
		treadManyType =  new Spinner[listChild.size()];;
		medFreq = new Spinner[listChild.size()];;
		medFreqTime = new TextView[listChild.size()];
		Recommendations = new EditText[listChild.size()];
		missDosePeriod = new Spinner[listChild.size()];;
		missDoseType = new Spinner[listChild.size()];;
		missDoseRecomm = new EditText[listChild.size()];
	}
	
	@Override
	public String toString() {
		return this.getId() + " - " + this.getDescricao();
	}
}
