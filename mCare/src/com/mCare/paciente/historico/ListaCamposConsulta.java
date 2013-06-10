package com.mCare.paciente.historico;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mCare.R;
import com.mCare.db.DbHelperConsultasRealizadas;

public class ListaCamposConsulta extends Activity implements OnItemClickListener {

	ArrayList<String> nomes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_campos_consulta);
		
		getActionBar().setTitle("Selecione o campo desejado:");
		getActionBar().setDisplayHomeAsUpEnabled(true);

		ListView listCampos = (ListView) findViewById(R.id.listViewListaCamposConsulta);
		
		DbHelperConsultasRealizadas db = new DbHelperConsultasRealizadas(getApplicationContext());
		nomes = db.pegaColunas();
		String[] nomesDisplay = new String[nomes.size()-5];
		//remove campos padroes
		for(int i=0; i<nomesDisplay.length; i++){
			nomesDisplay[i] = nomes.get(i+5).split("@")[0].replace("_", " ");
		}
		
		for(String s: nomes){
			s.split("@")[0].replace("_", " ");
		}

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, nomesDisplay);
		listCampos.setAdapter(arrayAdapter);
		listCampos.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_campos_consulta, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent myIntent = new Intent(this, ListaValoresCampo.class);
		myIntent.putExtra("nome_campo", nomes.get(arg2 + 5));
		long idPaciente = getIntent().getExtras().getLong("id_paciente");
		myIntent.putExtra("id_paciente", idPaciente);
		this.startActivity(myIntent);
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

}
