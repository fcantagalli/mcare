package com.mCare.configuracaoConsulta;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import android.view.MenuInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mCare.R;

public class SelecionaCamposView extends Activity {

	LinearLayout layout;
	ScrollView scroll;
	Button buttonAddField;
	ArrayList<String> nomesCampo;
	ArrayList<Integer> tiposCampo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ScrollView scroll = new ScrollView(this);
		scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this.scroll = scroll;
		
		nomesCampo = new ArrayList<String>();
		tiposCampo = new ArrayList<Integer>();
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		layout.setPadding(16, 16, 16, 16);
		this.layout = layout;
		
		buttonAddField = new Button(this, null, android.R.attr.buttonStyleSmall);
		buttonAddField.setText("Adicionar novo campo");
		buttonAddField.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		buttonAddField.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CriaCampoView.class);
				startActivityForResult(intent, 0);
			}
		});
		
		Button fim = new Button(this, null, android.R.attr.buttonStyleSmall);
		fim.setText("Finalizar");
		fim.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		fim.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ConsultaModel model = new ConsultaModel(nomesCampo, tiposCampo);
				model.createTable();
			}
		});
		
		layout.addView(buttonAddField);
		layout.addView(fim);
		
		scroll.addView(layout);
		setContentView(scroll);
		
		colocaCampoNaView("Nome", 1);
		colocaCampoNaView("Idade", 0);
		colocaCampoNaView("Data de nascimento", 2);
	}
	
	public void colocaCampoNaView(String nome, int tipo) {
		layout.removeViewInLayout(buttonAddField);
		TextView label = new TextView(this);
		label.setText(nome + ":");
		label.setEms(10);
		label.setTextAppearance(this, android.R.style.TextAppearance_Medium);
		layout.addView(label);
		
		if(tipo <= 2){
			EditText edit = new EditText(this);
			edit.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			edit.setFocusable(false);
			switch(tipo){
				case 0: edit.setHint("12");
				tiposCampo.add(0);
				break;
				case 1: edit.setHint("Texto Exemplo");
				tiposCampo.add(1);
				break;
				case 2: edit.setHint("12/12/12");
				tiposCampo.add(1);
				break;
				default: edit.setHint("null");
				tiposCampo.add(1);
				break;
			}
			Log.i("Phil", "Campo adicionado: " + nome + " Tipo: " + tipo);
			nomesCampo.add(nome);
			layout.addView(edit);
		}else{
			//TODO fazer os views para audio, foto, e video
		}

		layout.addView(buttonAddField);
	}
	
	public void removeCampoDaView(int tag){
		TextView nomeCampo = (TextView) findViewById(tag);
		View exemploCampo = findViewById(tag+1);
		layout.removeView(nomeCampo);
		layout.removeView(exemploCampo);
		int index = nomesCampo.indexOf(nomeCampo.getText().toString());
		nomesCampo.remove(index);
		tiposCampo.remove(index);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.seleciona_campos_view, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int codigo, int resultado, Intent intent){
		String nomeCampo = (String) intent.getExtras().get("nomeCampo");
		int tipoCampo = (Integer) intent.getExtras().get("tipoCampo");
		nomesCampo.add(nomeCampo);
		tiposCampo.add(tipoCampo);
		colocaCampoNaView(nomeCampo, tipoCampo);
	}

}
