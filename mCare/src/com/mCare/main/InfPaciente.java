package com.mCare.main;

import android.os.Bundle;
import com.mCare.R;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class InfPaciente extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inf_paciente);
		TextView nome = (TextView) findViewById(R.id.nomePaciente);
		nome.setText("Bianca");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inf_paciente, menu);
		return true;
	}

}
