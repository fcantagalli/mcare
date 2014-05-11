package com.mCare.paciente.historico;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mCare.R;
import com.mCare.db.DbHelperConsultasRealizadas;

public class ListaValoresCampo extends Activity {
	
	ArrayList<String> valores;
	String nomeCampo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_valores_campo);

		nomeCampo = (String) getIntent().getExtras().get("nome_campo");
		long idPaciente = getIntent().getExtras().getLong("id_paciente");
		
		ScrollView scroll = (ScrollView) findViewById(R.id.scrollViewListaValoresCampo);
		
		getActionBar().setTitle("Values of: " + nomeCampo.split("@")[0].replace("_", " "));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		layout.setPadding(16, 16, 16, 16);
		
		DbHelperConsultasRealizadas db = new DbHelperConsultasRealizadas(this);
		ArrayList<ArrayList<String>> info = db.retornaValoresCampo(idPaciente, nomeCampo);
		ArrayList<String> datas;
		ArrayList<String> valores;
		if(info!=null){
			datas = info.get(0);
			valores = info.get(1);
			for(int i=0; i<datas.size(); i++){
				criaTextView(layout, datas.get(i), valores.get(i));
			}
			this.valores = valores;
			Log.i("phil", "datas: " + datas);
			Log.i("phil", "valores: " + valores);
			
			Button estatisticas = new Button(this);
			estatisticas.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			estatisticas.setText("Show Statistics");
			estatisticas.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					gerarEstatisticas();				
				}
			});
			
			layout.addView(estatisticas);
			try{
				Double.parseDouble(valores.get(0));
			}catch(NumberFormatException e){
				layout.removeView(estatisticas);
			}
			
			scroll.addView(layout);
		}
		//para economizar memoria
		info = null;
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
		getMenuInflater().inflate(R.menu.lista_valores_campo, menu);
		return true;
	}
	
	public void criaTextView(LinearLayout layout, String nome, String conteudo){
		TextView nome_view = new TextView(this);
		nome_view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		nome_view.setText("Date: " + nome);
		nome_view.setEms(10);
		nome_view.setTextSize(20);
		nome_view.setPadding(0, 30, 0, 0);
		nome_view.setTextAppearance(this, android.R.attr.textAppearanceMedium);
		layout.addView(nome_view);
		
		TextView conteudo_view = new TextView(this);
		conteudo_view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		conteudo_view.setText("Value: " + conteudo);
		conteudo_view.setEms(10);
		conteudo_view.setTextSize(17);
		layout.addView(conteudo_view);
	}
	
	public void gerarEstatisticas(){
		double media = 0;

		for(String s: valores){
			media += Double.parseDouble(s);
		}
		media = media/valores.size();
		
		double desvio = 0;
		for(String s: valores){
			desvio += Math.pow((media - Double.parseDouble(s)), 2);
		}
		desvio = Math.sqrt(desvio);
		
		double maximo = 0;
		double minimo = 100000000;
		for(String s: valores){
			if(maximo<Double.parseDouble(s)){
				maximo = Double.parseDouble(s);
			}
			if(minimo>Double.parseDouble(s)){
				minimo = Double.parseDouble(s);
			}
		}
		
		Log.i("phil", "Mean: " + media + " Standard Deviation: " + desvio); 
		Intent x = new Intent(this, HistoricoGrafico.class);
		x.putExtra("media", media);
		x.putExtra("desvio", desvio);
		x.putExtra("maximo", maximo);
		x.putExtra("minimo", minimo);
		x.putExtra("valores", valores);
		x.putExtra("nomeCampo", nomeCampo);
	
		startActivity(x);
	}
	
	public static String format(double x) {
		return String.format("%.3f", x);
	}

}
