package com.mCare.exame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Spinner;

import com.mCare.R;

public class CadastrarExame extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_exame);
		
		//Pega campos do layout
		EditText nomeExame = (EditText) findViewById(R.id.campoNomeExame);
		Spinner tipo = (Spinner) findViewById(R.id.spinner1);
		
		/********* MANDA CADASTRAR NO BANCO ***********/
		/*LOGO DEPOIS QUE CADASTRAR:
		//Manda informacoes de volta para a tela que chamou o CadastrarExame
		this.getIntent().putExtra("id", id_exame);
		this.getIntent().putExtra("nome", id_paciente);
		this.getIntent().putExtra("tipo", id_paciente);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastrar_exame, menu);
		return true;
	}

}
