package com.mCare.diagnostico;

import com.mCare.R;
import android.os.Bundle;
import android.app.Activity;
import android.widget.EditText;

public class EditarDiagnostico extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_diagnostico);

		//Pega os campos da activity em xml
		EditText diagnostico = (EditText) findViewById(R.id.editTextCampoNomeDiagnosticoEditar);
		
		//Pega as informações
		String[] informacoes = {}; /*********** AQUI VEM AS INFORMACOES DO BANCO ***********/
		
		//Coloca as informações nos campos
		diagnostico.setText(informacoes[0]);
	}
}
