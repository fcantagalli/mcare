package com.mCare.exame;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.mCare.R;

public class EditarExame extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_editar_exame);

		//Pega os campos da activity em xml
		//;;EditText exame = (EditText) findViewById(R.id.editTextCampoNomeExameEditar);
		
		//Pega da tela anterior a informacao de qual medicamento eh este
	//	final int id =  getIntent().getExtras().getInt("id", -1);
		
		//Pega do banco as informacoes do medicamento
		//DbHelperExame db = new DbHelperExame(this);
		//Exame e = db.buscaExame(id);
		
		//Coloca as informacoes nos campos
		//exame.setText(e.getNome());
	}
}
