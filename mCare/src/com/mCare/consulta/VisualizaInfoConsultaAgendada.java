package com.mCare.consulta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mCare.R;
import com.mCare.consulta.realizarConsulta.RealizarConsultaMain;
import com.mCare.consulta.realizarConsulta.Visualiza_Consulta_Realizada;

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
		TextView tipo = (TextView) findViewById(R.id.TextViewTipo);
		
		String[] informacoes = (String[]) getIntent().getExtras().get("informacoes");
		paciente.setText(informacoes[0]);
		horario.setText(informacoes[1]);
		data.setText(informacoes[2]);
		observacoes.setText(informacoes[3]);
		endereco.setText(informacoes[4]);
		tipo.setText(informacoes[5]);
		
		Button realizarConsulta = (Button) findViewById(R.id.buttonRealizarConsulta);
		realizarConsulta.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				realizarConsulta();
			}
		});
		
		getActionBar().setTitle("Informações sobre a consulta:");
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	public void realizarConsulta(){
		Intent realizaConsulta = new Intent(this, RealizarConsultaMain.class);
		long id_consulta = getIntent().getExtras().getLong("id_consulta");
		String nomePaciente = getIntent().getExtras().getStringArray("informacoes")[0];
		realizaConsulta.putExtra("nome_paciente", nomePaciente);
		realizaConsulta.putExtra("id_consulta", id_consulta);
		int id_paciente = getIntent().getExtras().getInt("id_paciente");
		realizaConsulta.putExtra("id_paciente", id_paciente);
		startActivity(realizaConsulta);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home: onBackPressed();
		break;
		default: return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater()
				.inflate(R.menu.visualiza_info_consulta_agendada, menu);
		return true;
	}

}
