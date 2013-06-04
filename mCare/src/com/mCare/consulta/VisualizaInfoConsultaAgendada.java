package com.mCare.consulta;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.mCare.R;

public class VisualizaInfoConsultaAgendada extends Activity {
	
	Consulta consultaInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visualiza_info_consulta_agendada);
		
		TextView paciente = (TextView) findViewById(R.id.textViewPaciente);
		TextView horario = (TextView) findViewById(R.id.textViewHorario);
		TextView data = (TextView) findViewById(R.id.textViewData);
		TextView observacoes = (TextView) findViewById(R.id.textViewObservacoes);
		TextView endereco = (TextView) findViewById(R.id.textViewEndereco);
		String[] informacoes = (String[]) getIntent().getExtras().get("informacoes");
		paciente.setText(informacoes[0]);
		horario.setText(informacoes[1]);
		data.setText(informacoes[2]);
		observacoes.setText(informacoes[3]);
		endereco.setText(informacoes[4]);
		getActionBar().setTitle("Consulta:");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater()
				.inflate(R.menu.visualiza_info_consulta_agendada, menu);
		return true;
	}

}
