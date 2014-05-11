package com.mCare.paciente.historico;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mCare.R;
import com.mCare.db.DbHelperConsultasRealizadas;

public class SelecionaCamposCompara extends Activity implements OnItemClickListener {

	ArrayList<String> nomes;
	long id_paciente;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seleciona_campos_compara);
		
		ListView listCampos = (ListView) findViewById(R.id.listViewListaCamposConsultaCompara);
	
		String idPaciente = getIntent().getExtras().getString("id_paciente");
		id_paciente = Long.parseLong(idPaciente);
		Log.i("compara", "inicio, id_paciente: " + id_paciente);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		View homeIcon = findViewById(android.R.id.home);
	    ((View) homeIcon.getParent()).setVisibility(View.VISIBLE);
	    ((View) homeIcon).setVisibility(View.VISIBLE);
		
	    ActionBar actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.action_bar_realizar_consulta);
		
		//titulo da activity
		TextView titulo = (TextView) actionBar.getCustomView().findViewById(R.id.textViewTitulo);
		Log.i("RealizarConsultaMain", "referencia de titulo: " + titulo);
		titulo.setText("Select the fields:");
		
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		//botao de finalizar da actionbar
		Button finalizar = (Button) actionBar.getCustomView().findViewById(R.id.buttonFinalizar);
		finalizar.setText("Plot");
		finalizar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				criaGrafico();
			}
		});
		
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
		listCampos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, nomesDisplay);
		listCampos.setAdapter(arrayAdapter);
		listCampos.setOnItemClickListener(this);
		
	}
	
	public void criaGrafico(){
		ListView listCampos = (ListView) findViewById(R.id.listViewListaCamposConsultaCompara);
		ArrayList<String> selecionados = new ArrayList<String>();
		SparseBooleanArray checked = listCampos.getCheckedItemPositions();
		for(int i=0; i<checked.size(); i++){
			if(checked.get(i)){
				selecionados.add(nomes.get(i+5));
			}
		}
		Intent grafico = new Intent(this, ComparaHistoricoGrafico.class);
		grafico.putExtra("selecionados", selecionados);
		grafico.putExtra("id_paciente", getIntent().getExtras().getLong("id_paciente"));
		startActivity(grafico);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:{
			onBackPressed();
		}
		break;
		default: return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.seleciona_campos_compara, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}

}
