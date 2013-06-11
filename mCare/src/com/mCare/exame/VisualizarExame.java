package com.mCare.exame;

import com.mCare.R;
import com.mCare.R.layout;
import com.mCare.R.menu;
import com.mCare.db.DbHelperExame;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class VisualizarExame extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visualizar_exame);
		
		//Pega os campos da activity em xml
		TextView exame = (TextView) findViewById(R.id.textViewExame);
		TextView tipo = (TextView) findViewById(R.id.textViewTipoResultadoExame);
		
		//Pega da tela anterior a informacao de qual medicamento eh este
		final int id = getIntent().getExtras().getInt("id", -1);
		
		//Pega do banco as informacoes do medicamento
		DbHelperExame db = new DbHelperExame(this);
		Exame e = db.buscaExame(id);
		
		//Coloca as informa��es nos campos
		exame.setText(e.getNome());
		tipo.setText(e.getTipoResultadoExame());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.visualizar_exame, menu);
		return true;
	}

}
