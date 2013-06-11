package com.mCare.diagnostico;

import com.mCare.R;
import com.mCare.db.DbHelperDiagnostico;
import com.mCare.db.DbHelperMedicamento;
import com.mCare.medicamento.Medicamento;

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
		
		//Pega da tela anterior a informacao de qual medicamento eh este
		final int id =  getIntent().getExtras().getInt("id", -1);
		
		//Pega do banco as informacoes do medicamento
		DbHelperDiagnostico db = new DbHelperDiagnostico(this);
		Diagnostico d = db.buscaDiagnostico(id);
		
		//Coloca as informacoes nos campos
		diagnostico.setText(d.getNome());
	}
}
