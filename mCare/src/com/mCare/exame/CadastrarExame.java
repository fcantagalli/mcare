package com.mCare.exame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mCare.R;
import com.mCare.db.DbHelperExame;

public class CadastrarExame extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_exame);
		
		
		Button b = (Button) findViewById(R.id.button_salva_exame);
		b.setOnClickListener(this);
		//Pega campos do layout
		
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.button_salva_exame:{
				salvarExame();
				
			}
		}
	}

	private void salvarExame(){
		EditText nomeExame = (EditText) findViewById(R.id.editTextCampoNomeExameEditar);
		Spinner tipo = (Spinner) findViewById(R.id.spinnerTipoResultadoExame);
		Exame e = new Exame(-1,nomeExame.getText().toString(), tipo.getSelectedItemPosition());
		DbHelperExame db = new DbHelperExame(this);
		
		long id = db.insereExame(e);
		Log.i("exame","id do novo exame : " + id);
		Intent intent = new Intent();
		intent.putExtra("id", (int) id);
		intent.putExtra("nome", e.getNome());
		intent.putExtra("tipo", e.getTipoResultadoExame());
		
		if(this.getParent() == null){
			setResult(Activity.RESULT_OK,intent);
		}
		else{
			this.getParent().setResult(Activity.RESULT_OK, intent);
		}
		
		super.onBackPressed();
	}
}
