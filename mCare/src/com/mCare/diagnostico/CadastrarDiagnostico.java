package com.mCare.diagnostico;

import com.mCare.R;
import com.mCare.R.layout;
import com.mCare.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CadastrarDiagnostico extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_diagnostico);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastrar_diagnostico, menu);
		return true;
	}

}
