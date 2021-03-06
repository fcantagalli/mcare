package com.mCare.paciente;

import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.mCare.medicamento.Medicamento;

public class Paciente implements Comparable<Paciente> {

	private int bd_id; // id do paciente no banco de dados. Sera usado como chave estrangeira na consulta; 
	private String nome;
	private GregorianCalendar dataNascimento;
	private byte Sexo; // 0 - feminino 1 - masculino
	
	//telefone
	private String tipo_tel;
	private String telefone;
	private long idTel1;
	private String escolaridade;
	private String parente;
	private String parente_tel;
	private String parente_cel;
	private String tel2;
	private String tipo_tel2;
	private long idTel2;
	private String tel3;
	private String tipo_tel3;
	private long idTel3;

	//endereco
	private String logradouro;
	private String bairro;
	private int numero;
	private String tipo_endereco;
	private String cep;
	private String cidade;
	private String complemento;
	
	//medicamentos
	LinkedList<Medicamento> medicamentos_atuais;
	LinkedList<Medicamento> medicamentos_anteriores;
	
	
	public Paciente(int id, String nome){
		this.bd_id = id;
		this.nome = nome;
	}
	
	
	
	public String getTipo_tel() {
		return tipo_tel;
	}



	public long getIdTel1() {
		return idTel1;
	}



	public void setIdTel1(long idTel1) {
		this.idTel1 = idTel1;
	}



	public long getIdTel2() {
		return idTel2;
	}



	public void setIdTel2(long idTel2) {
		this.idTel2 = idTel2;
	}



	public long getIdTel3() {
		return idTel3;
	}



	public void setIdTel3(long idTel3) {
		this.idTel3 = idTel3;
	}



	public void setTipo_tel(String tipo_tel) {
		this.tipo_tel = tipo_tel;
	}



	public String getTipo_tel2() {
		return tipo_tel2;
	}



	public void setTipo_tel2(String tipo_tel2) {
		this.tipo_tel2 = tipo_tel2;
	}



	public String getTipo_tel3() {
		return tipo_tel3;
	}



	public void setTipo_tel3(String tipo_tel3) {
		this.tipo_tel3 = tipo_tel3;
	}



	public Paciente(int id, String nome, GregorianCalendar dataNascimento, byte Sexo, String logradouro, String bairro, int numero, String cidade){
		this.bd_id = id;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.Sexo = Sexo;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.numero = numero;
		this.cidade = cidade;
	}
	
	public String getParente_cel() {
		return parente_cel;
	}
	
	
	
	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getTel3() {
		return tel3;
	}

	public void setTel3(String tel3) {
		this.tel3 = tel3;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.nome;
	}

	public int getBd_id() {
		return bd_id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public GregorianCalendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(GregorianCalendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public byte getSexo() {
		return Sexo;
	}

	public void setSexo(byte sexo) {
		Sexo = sexo;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getEscolaridade() {
		return escolaridade;
	}

	public void setEscolaridade(String escolaridade) {
		this.escolaridade = escolaridade;
	}

	public String getParente() {
		return parente;
	}

	public void setParente(String parente) {
		this.parente = parente;
	}

	public String getParente_tel() {
		return parente_tel;
	}

	@Override
	public int compareTo(Paciente arg0) {
		    return this.nome.compareTo(arg0.nome);
	}
	
	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getTipo_endereco() {
		return tipo_endereco;
	}

	public void setTipo_endereco(String tipo_endereco) {
		this.tipo_endereco = tipo_endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setParente_tel(String parente_tel) {
		this.parente_tel = parente_tel;
	}

	public void setParente_cel(String parente_cel) {
		this.parente_cel = parente_cel;
	}
	
	
	
	//MEDICAMENTOS
	public LinkedList<Medicamento> getMedicamentosAtuais() {
		return medicamentos_atuais;
	}
	public void setMedicamentosAtuais(LinkedList<Medicamento> medicamentos_atuais) {
		this.medicamentos_atuais = medicamentos_atuais;
	}
	
	public LinkedList<Medicamento> getMedicamentosAnteriores() {
		return medicamentos_anteriores;
	}
	public void setMedicamentosAnteriores(LinkedList<Medicamento> medicamentos_anteriores) {
		this.medicamentos_anteriores = medicamentos_anteriores;
	}
	
}
