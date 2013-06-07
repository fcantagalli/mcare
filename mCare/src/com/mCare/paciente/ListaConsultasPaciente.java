package com.mCare.paciente;

import java.util.ArrayList;

import com.mCare.R;
import com.mCare.R.layout;
import com.mCare.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class ListaConsultasPaciente extends Activity {

	ArrayList<String> elements;
	ListView listViewPacientes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_consultas_paciente);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_consultas_paciente, menu);
		return true;
	}

}
