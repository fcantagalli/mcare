package com.mCare.medicamento;

import com.mCare.R;
import com.mCare.R.id;
import com.mCare.R.layout;
import com.mCare.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

public class EditarMedicamento extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.activity_editar_medicamento,container, false);
		
		//Pega os campos da activity em xml
		AutoCompleteTextView medicamento = (AutoCompleteTextView) getActivity().findViewById(R.id.editTextCampoNomeMedicamento);
		Spinner tipo = (Spinner) getActivity().findViewById(R.id.spinnerTipoMedicamento);
		AutoCompleteTextView dosagem = (AutoCompleteTextView) getActivity().findViewById(R.id.editTextCampoDosagem);
		AutoCompleteTextView principioAtivo = (AutoCompleteTextView) getActivity().findViewById(R.id.editTextCampoPrincipioAtivo);
		
		//Pega as informa��es
		String[] informacoes = (String[]) getActivity().getIntent().getExtras().get("informacoes");
		
		//Coloca as informa��es nos campos
		medicamento.setText(informacoes[0]);
		dosagem.setText(informacoes[2]);
		principioAtivo.setText(informacoes[3]);
		
		//O tipo de medicamento eh uma caixa de selecao (Spinner)
		ArrayAdapter myAdap = (ArrayAdapter) tipo.getAdapter(); //cast to an ArrayAdapter
		int spinnerPosition = myAdap.getPosition(informacoes[1]); //posicao do Tipo na caixa de selecao
		tipo.setSelection(spinnerPosition); //seleciona o Tipo armazenado no banco
		
		//Titulo da actionbar
		getActivity().getActionBar().setTitle("Medicamento:");
		
		
		return rootView;
	}

}
