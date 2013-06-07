package com.mCare.consulta.realizarConsulta;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mCare.consulta.Consulta;
import com.mCare.db.DbHelperConsultasRealizadas;
import com.mCare.paciente.Paciente;

public class Consulta_Fragment extends Fragment {

	ArrayList<String> nomes;
	ArrayList<Integer> id_campos;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ScrollView scroll = new ScrollView(getActivity());
		scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		LinearLayout layout = new LinearLayout(getActivity());
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		layout.setPadding(16, 16, 16, 16);
		
		//arraylist para guardar os ids dos campos
		id_campos = new ArrayList<Integer>();
		
		DbHelperConsultasRealizadas db = new DbHelperConsultasRealizadas(getActivity().getApplicationContext());
		nomes = db.pegaColunas();
		
		mostraCampos(layout);
		scroll.addView(layout);
		
		return scroll;
	}

	private void mostraCampos(LinearLayout layout) {
		// pega o nome das colunas do banco

		for (int i = 5; i < nomes.size(); i++) {
			// pega o nome da coluna e faz o split pra saber o tipo
			Log.i("phil", nomes.get(i));
			String[] infoCol = nomes.get(i).split("@");

			// tipo do campo atual
			int tipo = Integer.parseInt(infoCol[1]);
			// nome do campo atual
			String nome = infoCol[0].replaceAll("_", " ");
			Log.i("Phil", "adicionando campo: " + nome + " tipo: " + tipo);
			// titulo do campo
			TextView label = new TextView(getActivity());
			label.setText(nome + ":");
			label.setEms(10);
			label.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
			layout.addView(label);

			switch (tipo) {
			// campo tipo inteiro
			case 0: {
				EditText inteiro = new EditText(getActivity());
				inteiro.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				inteiro.setInputType(InputType.TYPE_CLASS_NUMBER);
				inteiro.setHint(nome.toLowerCase());
				id_campos.add(inteiro.getId());
				layout.addView(inteiro);
				break;
			}
			// campo tipo decimal
			case 1: {
				EditText decimal = new EditText(getActivity());
				decimal.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				decimal.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
				decimal.setHint(nome.toLowerCase());
				id_campos.add(decimal.getId());
				layout.addView(decimal);
				break;
			}
			// campo tipo text
			case 2: {
				EditText text = new EditText(getActivity());
				text.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				text.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
				text.setHint(nome.toLowerCase());
				id_campos.add(text.getId());
				layout.addView(text);
				break;
			}
			// campo tipo data
			case 3: {
				DatePicker datePicker = new DatePicker(getActivity());
				datePicker.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				datePicker.setCalendarViewShown(false);
				id_campos.add(datePicker.getId());
				layout.addView(datePicker);
				break;
			}
			}
		}
	}

	public void salvaInformacoes() {
		long id_consulta = (Long) getActivity().getIntent().getExtras().get("id_consulta");
		String[] nomesColunas = new String[nomes.size()];
		int[] tiposColunas = new int[nomes.size()];
		for(int i=0; i<nomesColunas.length; i++){
			nomesColunas[i] = nomes.get(i).split("@")[0];
			tiposColunas[i] = Integer.parseInt(nomes.get(i).split("@")[1]);
		}
		
		Paciente paciente = new Paciente(12, "Philippe");
		GregorianCalendar gc = new GregorianCalendar();
		Consulta consulta = new Consulta(paciente, gc,"tipo", "descricao");
		DbHelperConsultasRealizadas db = new DbHelperConsultasRealizadas(getActivity());
		
		String sql = "INSERT INTO consulta (";
		for(int i=0; i<nomesColunas.length; i++){
			sql = sql + nomesColunas[i];
			if(i<nomesColunas.length-1){
				sql = sql + ",";
			}
		}
		sql = sql + ") VALUES (";
		
		sql = sql + id_consulta + ", " + consulta.getPaciente().getBd_id() + ", " + db.dbhelper.formataData(consulta.getHora()) + ", " + consulta.getDescricao() + ", " + consulta.getTipo() + ", ";
		
		for(int i=0; i<id_campos.size(); i++){
			switch (id_campos.get(i)) {
			// campo tipo inteiro
			case 0: {
				EditText inteiro = (EditText) getActivity().findViewById(id_campos.get(i));
				if(inteiro.getText().toString()!=null){
					int valor = Integer.parseInt(inteiro.getText().toString());
					sql = sql + valor;
				}else{
					Toast.makeText(getActivity(), "Preencha o campo " + nomes.get(i), Toast.LENGTH_LONG).show();
					return;
				}
				break;
			}
			// campo tipo decimal
			case 1: {
				EditText decimal = (EditText) getActivity().findViewById(id_campos.get(i));
				if(decimal.getText().toString()!=null){
					double valor = Double.parseDouble(decimal.getText().toString());
					sql = sql + valor;
				}else{
					Toast.makeText(getActivity(), "Preencha o campo " + nomes.get(i), Toast.LENGTH_LONG).show();
					return;
				}
				break;
			}
			// campo tipo text
			case 2: {
				EditText text = new EditText(getActivity());
				if(text.getText().toString()!=null){
					String valor = text.getText().toString();
					sql = sql + valor;
				}else{
					Toast.makeText(getActivity(), "Preencha o campo " + nomes.get(i), Toast.LENGTH_LONG).show();
					return;
				}
				break;
			}
			// campo tipo data
			case 3: {
				DatePicker datePicker = new DatePicker(getActivity());
				String valor = datePicker.getYear() + "-" + adiciona0(datePicker.getMonth()) + "-" + adiciona0(datePicker.getDayOfMonth()) + " 00:00";
				sql = sql + valor;
				break;
			}
			}
			if(i<id_campos.size()-1){
				sql = sql + ", ";
			}
		}
		sql = sql + ");";
		
		Log.i("phil", sql);
		
		//id
		//nome coluna
		//tipo
		//conteudo
	}
	
	public String adiciona0(int numero){
		String retorno =  "" + numero;
		if(numero<=9){
			retorno = "0" + numero; 
		}
		return retorno;
	}

}
