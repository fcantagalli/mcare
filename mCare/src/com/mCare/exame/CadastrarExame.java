package com.mCare.exame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;

import com.mCare.R;

public class CadastrarExame extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_exame);
		
		EditText nomeExame = (EditText) findViewById(R.id.campoNomeExame);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastrar_exame, menu);
		return true;
	}

}
