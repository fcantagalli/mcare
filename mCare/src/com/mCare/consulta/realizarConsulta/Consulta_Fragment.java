package com.mCare.consulta.realizarConsulta;

import java.util.ArrayList;

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

public class Consulta_Fragment extends Fragment {

	ArrayList<String> nomes;

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
		Log.i("Phil", "vaaaaaaaaaaaaaaaaaaaaaaaaaaaaaamooooooooooooooooooooooooooooos");
		nomes = new ArrayList<String>();
		//testes
		nomes.add("Idade@0");
		nomes.add("Peso@1");
		nomes.add("Descrição@2");
		nomes.add("Data@3");
		mostraCampos(layout);
		scroll.addView(layout);
		
		return scroll;
	}

	private void mostraCampos(LinearLayout layout) {
		// pega o nome das colunas do banco

		for (int i = 0; i < nomes.size(); i++) {
			// pega o nome da coluna e faz o split pra saber o tipo
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
				inteiro.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				inteiro.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
				inteiro.setHint(nome.toLowerCase());
				layout.addView(inteiro);
				break;
			}
			// campo tipo decimal
			case 1: {
				EditText decimal = new EditText(getActivity());
				decimal.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				decimal.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
				decimal.setHint(nome.toLowerCase());
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
				layout.addView(text);
				break;
			}
			// campo tipo data
			case 3: {
				DatePicker datePicker = new DatePicker(getActivity());
				datePicker.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				datePicker.setCalendarViewShown(false);
				layout.addView(datePicker);
				break;
			}
			}
			Log.i("Phil", "Campo adicionado: " + nome + " Tipo: " + tipo);
		}
	}

	public void salvaInformacoes() {

	}

}
