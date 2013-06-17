package com.mCare.diagnostico;

import com.mCare.R;
import com.mCare.db.DbHelperDiagnostico;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class VisualizarDiagnostico  extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.activity_visualizar_diagnostico,container, false);
		
		//Pega os campos da activity em xml
		TextView diagnostico = (TextView) rootView.findViewById(R.id.textViewDiagnostico);
		
		//Pega da tela anterior a informacao de qual medicamento eh este
		final int id =  getActivity().getIntent().getExtras().getInt("id", -1);
		
		//Pega do banco as informacoes do medicamento
		DbHelperDiagnostico db = new DbHelperDiagnostico(getActivity());
		Diagnostico d = db.buscaDiagnostico(id);
		
		//Coloca as informa��es nos campos
		diagnostico.setText(d.getNome());
		
		return rootView;
	}

}