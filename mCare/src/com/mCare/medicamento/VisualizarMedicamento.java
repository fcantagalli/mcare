package com.mCare.medicamento;

import com.mCare.R;
import com.mCare.R.id;
import com.mCare.R.layout;
import com.mCare.R.menu;
import com.mCare.db.DbHelperMedicamento;

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
		
		//Pega da tela anterior a informacao de qual medicamento eh este
		final int id = getActivity().getIntent().getExtras().getInt("id", -1);
		
		//Pega do banco as informacoes do medicamento
		DbHelperMedicamento db = new DbHelperMedicamento(getActivity());
		Medicamento m = db.buscaMedicamento(id);
		
		//Coloca as informa��es nos campos
		medicamento.setText(m.getNome());
		tipo.setText(m.getTipo());
		dosagem.setText(m.getDosagem());
		principioAtivo.setText(m.getPricipioAtivo());
		
		return rootView;
	}

}
