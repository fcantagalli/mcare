package com.mCare.medicamento;

import com.mCare.R;
import com.mCare.R.id;
import com.mCare.R.layout;
import com.mCare.R.menu;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class VisualizarMedicamento extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.activity_visualizar_medicamento,container, false);
		
		//Pega os campos da activity em xml
		TextView medicamento = (TextView) rootView.findViewById(R.id.textViewMedicamento);
		TextView tipo = (TextView) rootView.findViewById(R.id.textViewTipo);
		TextView dosagem = (TextView) rootView.findViewById(R.id.textViewDosagem);
		TextView principioAtivo = (TextView) rootView.findViewById(R.id.textViewPrincipioAtivo);
		
		//Pega as informações
		String[] informacoes = {}; /*********** AQUI VEM AS INFORMACOES DO BANCO ***********/
		
		//Coloca as informações nos campos
		medicamento.setText(informacoes[0]);
		tipo.setText(informacoes[1]);
		dosagem.setText(informacoes[2]);
		principioAtivo.setText(informacoes[3]);
		
		return rootView;
	}

}
