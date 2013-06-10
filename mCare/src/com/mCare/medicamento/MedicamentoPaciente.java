/********* OBS. POR ENQUANTO NAO ESTA SENDO USADA.
 * PODE SER QUE NAO SEJA NUNCA! **********/

package com.mCare.medicamento;

import java.util.GregorianCalendar;

public class MedicamentoPaciente {

	private Medicamento medicamento;
	private long id_consulta;
	private long id_paciente;
	private GregorianCalendar hora;
	
	
	public Medicamento getMedicamento() {
		return medicamento;
	}
	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}
	
	public long getIdConsulta() {
		return id_consulta;
	}
	public void getIdConsulta(long id_consulta) {
		this.id_consulta = id_consulta;
	}
	
	public long getIdPaciente() {
		return id_paciente;
	}
	public void setIdPaciente(long id_paciente) {
		this.id_paciente = id_paciente;
	}
	
	public void setHora(GregorianCalendar hora) {
		this.hora = hora;
	}
	public GregorianCalendar getHora() {
		return hora;
	}


	
}
