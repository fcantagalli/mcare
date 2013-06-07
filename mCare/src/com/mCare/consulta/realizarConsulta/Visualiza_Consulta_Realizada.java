package com.mCare.consulta.realizarConsulta;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mCare.R;
import com.mCare.db.DbHelperConsultasRealizadas;

public class Visualiza_Consulta_Realizada extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ScrollView scroll = new ScrollView(this);
		scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		layout.setPadding(16, 16, 16, 16);
		
		long id_consulta = getIntent().getExtras().getLong("id_consulta");
		
		DbHelperConsultasRealizadas db = new DbHelperConsultasRealizadas(this);
		HashMap<String, Object> info = db.buscaConsultaRealizada(id_consulta);
		//array com o nome das colunas do banco
		String[] nomesColunas = (String[]) info.get("nomes");
		//bundle com os conteudos
		Bundle dados = (Bundle) info.get("dados");
		
		if(dados != Bundle.EMPTY){
			criaTextView(layout, "Data:", dados.getString(nomesColunas[2]));
			criaTextView(layout, "Descricao:", dados.getString(nomesColunas[3]));
			criaTextView(layout, "Tipo:", dados.getString(nomesColunas[4]));
			
			for(int i=5; i<nomesColunas.length; i++){
				String nome = nomesColunas[i].split("@")[0];
				String conteudo = dados.getString(nomesColunas[i]);
				criaTextView(layout, nome, conteudo);
			}
		}else{
			Log.i("phil", "bundle vazio!");
		}
		scroll.addView(layout);
		setContentView(scroll);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.visualiza__consulta, menu);
		return true;
	}
	
	public void criaTextView(LinearLayout layout, String nome, String conteudo){
		TextView nome_view = new TextView(this);
		nome_view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		nome_view.setText(nome);
		layout.addView(nome_view);
		
		TextView conteudo_view = new TextView(this);
		conteudo_view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		conteudo_view.setText(conteudo);
		layout.addView(conteudo_view);
	}

}
