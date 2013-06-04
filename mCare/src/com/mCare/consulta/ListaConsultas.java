package com.mCare.consulta;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.mCare.R;

public class ListaConsultas extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_consultas);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_consultas, menu);
		return true;
	}

}
