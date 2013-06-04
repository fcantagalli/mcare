package com.mCare.consulta;

import com.mCare.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EditarConsulta extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_consulta);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editar_consulta, menu);
		return true;
	}

}
