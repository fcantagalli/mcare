package com.mCare.diagnostico;

import com.mCare.R;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class EditarDiagnostico extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.activity_editar_diagnostico,container, false);
		
		//Pega os campos da activity em xml
		EditText diagnostico = (EditText) rootView.findViewById(R.id.editTextCampoNomeDiagnosticoEditar);
		
		//Pega as informações
		String[] informacoes = {}; /*********** AQUI VEM AS INFORMACOES DO BANCO ***********/
		
		//Coloca as informações nos campos
		diagnostico.setText(informacoes[0]);
		
		return rootView;
	}
}
