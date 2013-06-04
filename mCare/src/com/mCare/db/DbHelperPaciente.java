package com.mCare.db;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.mCare.consulta.Consulta;
import com.mCare.paciente.Paciente;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class DbHelperPaciente {

	private Db dbhelper;

	public DbHelperPaciente(Context context) {
		dbhelper = Db.getInstance(context);

	}

	public long inserePaciente(Paciente p) {
		long deucerto;

		ContentValues cv = new ContentValues();

		cv.put("nome", p.getNome());
		GregorianCalendar gc = p.getDataNascimento();
		cv.put("data_nascimento", dbhelper.formataData(gc));
		cv.put("sexo", p.getSexo());
		cv.put("escolaridade", p.getEscolaridade());
		cv.put("parente", p.getParente());
		cv.put("parente_tel", p.getParente_tel());
		cv.put("parente cel", p.getParente_cel());

		deucerto = dbhelper.insert(dbhelper.TABLE_NAME_PACIENTES, cv);
		return deucerto;
	}

	public int deletaPaciente(Paciente p) {

		int deuCerto = dbhelper.delete(dbhelper.TABLE_NAME_PACIENTES,
				"id_paciente=?", new String[] { "" + p.getBd_id() });

		return deuCerto;
	}

	/**
	 * Obs: os objetos retornados por esse m�todo possuem somente nome e id para
	 * otimiza��o de memoria na listagem de todos os pacientes. Para exibir mais
	 * detalhes e necessario nova busca para pegar os detalhes.
	 * 
	 * @return lista de pacientes
	 */
	public ArrayList<Paciente> listaPacientes() {

		Cursor cursor = dbhelper.serach(false, dbhelper.TABLE_NAME_PACIENTES,
				new String[] { "id_paciente", "nome" }, null, null, null, null,
				"nome", null);

		Log.i("SQL", "cursor esta fechado? : " + cursor.isFirst());

		if (cursor.moveToFirst()) {
			Log.i("SQL", "cursor possui linhas");
			ArrayList<Paciente> listaPaciente = new ArrayList<Paciente>(
					cursor.getCount());

			while (!cursor.isAfterLast()) {
				Log.i("SQL", "passou no is afterlast");
				int id = cursor.getInt(0);
				String nome = cursor.getString(1);

				Paciente p = new Paciente(id, nome);

				listaPaciente.add(p);
				cursor.moveToNext();
			}
			return listaPaciente;
		} else {
			return null;
		}
	}

	public Paciente buscaPaciente(int id) {

		String query = "SELECT * FROM " + dbhelper.TABLE_NAME_PACIENTES
				+ " WHERE id_paciente = " + id + ";";

		Cursor c = dbhelper.exercutaSELECTSQL(query, null);
		Paciente p = null;

		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				int idpaciente = c.getInt(0);
				String nome = c.getString(1);
				GregorianCalendar gc = dbhelper.textToGregorianCalendar(c
						.getString(2));
				byte sexo = (byte) c.getInt(3);
				String escolaridade = c.getString(4);
				String parente = c.getString(5);
				String parente_tel = c.getString(6);
				String parente_cel = c.getString(7);
				String logradouro = c.getString(8);
				String bairro = c.getString(9);
				int numero = c.getInt(10);
				int tipo_end = c.getInt(11);
				String cep = c.getString(12);
				String cidade = c.getString(13);
				String complemento = c.getString(14);

				p = new Paciente(idpaciente, nome, gc, sexo, logradouro,
						bairro, numero, cidade);

				p.setEscolaridade(escolaridade);
				p.setParente(parente);
				p.setParente_cel(parente_cel);
				p.setParente_tel(parente_tel);
				p.setTipo_endereco(tipo_end);
				p.setCep(cep);
				p.setComplemento(complemento);

			}

		}
		return p;
	}

	public Paciente buscaPaciente(String nome) {

		String query = "SELECT * FROM " + dbhelper.TABLE_NAME_PACIENTES
				+ " WHERE nome = '" + nome + "';";

		Cursor c = dbhelper.exercutaSELECTSQL(query, null);
		Paciente p = null;

		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				int idpaciente = c.getInt(0);
				GregorianCalendar gc = dbhelper.textToGregorianCalendar(c
						.getString(2));
				byte sexo = (byte) c.getInt(3);
				String escolaridade = c.getString(4);
				String parente = c.getString(5);
				String parente_tel = c.getString(6);
				String parente_cel = c.getString(7);
				String logradouro = c.getString(8);
				String bairro = c.getString(9);
				int numero = c.getInt(10);
				int tipo_end = c.getInt(11);
				String cep = c.getString(12);
				String cidade = c.getString(13);
				String complemento = c.getString(14);

				p = new Paciente(idpaciente, nome, gc, sexo, logradouro,
						bairro, numero, cidade);

				p.setEscolaridade(escolaridade);
				p.setParente(parente);
				p.setParente_cel(parente_cel);
				p.setParente_tel(parente_tel);
				p.setTipo_endereco(tipo_end);
				p.setCep(cep);
				p.setComplemento(complemento);
			}

		}
		return p;
	}

}
