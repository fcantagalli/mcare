package com.mCare.consulta.realizarConsulta;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mCare.R;
import com.mCare.db.DbHelperConsultasRealizadas;

public class Visualiza_Consulta_Realizada extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ScrollView scroll = new ScrollView(this);
		scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		
		getActionBar().setTitle("Consultation Details:");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		layout.setPadding(16, 16, 16, 16);
		
		long id_consulta = getIntent().getExtras().getLong("id_consulta");
		
		DbHelperConsultasRealizadas db = new DbHelperConsultasRealizadas(this);
		HashMap<String, String[]> info = db.buscaConsultaRealizada(id_consulta);
		
		if(info == null){
			Toast.makeText(getApplicationContext(), "eh null", Toast.LENGTH_LONG).show();
			return;
		}
		//array com o nome das colunas do banco
		String[] nomesColunas = (String[]) info.get("nomes");
		String[] conteudos = (String[]) info.get("conteudos");
		//for(int i=0; i<nomesColunas.length; i++){
			Log.i("phil", Arrays.asList(nomesColunas).toString());
			Log.i("phil", Arrays.asList(conteudos).toString());
		//}

			criaTextView(layout, "Date:", conteudos[2]);
			criaTextView(layout, "Description:", conteudos[3]);
			criaTextView(layout, "Type:", conteudos[4]);
			
			for(int i=5; i<nomesColunas.length; i++){
				String nome = nomesColunas[i].split("@")[0].replace("_", " ") + ":";
				String conteudo = conteudos[i];
				criaTextView(layout, nome, conteudo);
			}

		scroll.addView(layout);
		setContentView(scroll);
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
		getMenuInflater().inflate(R.menu.visualiza__consulta, menu);
		return true;
	}
	
	public void criaTextView(LinearLayout layout, String nome, String conteudo){
		TextView nome_view = new TextView(this);
		nome_view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		nome_view.setText(nome);
		nome_view.setEms(10);
		nome_view.setTextSize(20);
		nome_view.setPadding(0, 30, 0, 0);
		nome_view.setTextAppearance(this, android.R.attr.textAppearanceMedium);
		layout.addView(nome_view);
		
		TextView conteudo_view = new TextView(this);
		conteudo_view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		conteudo_view.setText(conteudo);
		conteudo_view.setEms(10);
		conteudo_view.setTextSize(17);
		layout.addView(conteudo_view);
	}

}
