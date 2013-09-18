package com.mCare.configuracaoConsulta;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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

		getActionBar().setTitle("Setup Consultation");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		ScrollView scroll = new ScrollView(this);
		scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this.scroll = scroll;

		nomesCampo = new ArrayList<String>();
		tiposCampo = new ArrayList<Integer>();

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		layout.setPadding(16, 16, 16, 16);
		this.layout = layout;

		buttonAddField = new Button(this, null, android.R.attr.buttonStyleSmall);
		buttonAddField.setText("Add new field");
		buttonAddField.setLayoutParams(new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		buttonAddField.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						CriaCampoView.class);
				startActivityForResult(intent, 0);
			}
		});

		Button fim = new Button(this, null, android.R.attr.buttonStyleSmall);
		fim.setText("Save Consultation");
		fim.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		fim.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ConsultaModel model = new ConsultaModel(nomesCampo, tiposCampo, getApplicationContext());
				model.createTable();
				if(model.createTable()){
					Toast.makeText(getApplicationContext(), "Consultation successfully created", Toast.LENGTH_SHORT).show();
					onBackPressed();
				}
			}
		});

		layout.addView(buttonAddField);
		layout.addView(fim);

		scroll.addView(layout);
		setContentView(scroll);

		colocaCampoNaView("Recommendation", 2);
		colocaCampoNaView("Watch",0);
		colocaCampoNaView("Avd",2);
		colocaCampoNaView("Aivd",2);
		colocaCampoNaView("Weight", 1);
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

	public void colocaCampoNaView(String nome, int tipo) {
		layout.removeViewInLayout(buttonAddField);
		TextView label = new TextView(this);
		label.setText(nome + ":");
		label.setEms(10);
		label.setTextAppearance(this, android.R.style.TextAppearance_Medium);
		layout.addView(label);
		nomesCampo.add(nome);
		tiposCampo.add(tipo);

		EditText edit = new EditText(this);
		edit.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		edit.setFocusable(false);
		switch (tipo) {
		// campo tipo inteiro
		case 0:
			edit.setHint("12");
			break;
		// campo tipo decimal
		case 1:
			edit.setHint("165.3");
			break;
		// campo tipo text
		case 2:
			edit.setHint(nome+" value");
			break;
		// campo tipo data
		case 3:
			edit.setHint("12/34/04");
			break;
		// caso que nunca ocorrera
		default:
			edit.setHint("");
			break;
		}
		Log.i("Phil", "Campo adicionado: " + nome + " Tipo: " + tipo);
		layout.addView(edit);
		layout.addView(buttonAddField);
	}

	public void removeCampoDaView(int tag) {
		TextView nomeCampo = (TextView) findViewById(tag);
		View exemploCampo = findViewById(tag + 1);
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
	protected void onActivityResult(int codigo, int resultado, Intent intent) {
		if(intent != null){
			String nomeCampo = (String) intent.getExtras().get("nomeCampo");
			int tipoCampo = (Integer) intent.getExtras().get("tipoCampo");
			colocaCampoNaView(nomeCampo, tipoCampo);
		}else{
			Toast.makeText(this, "Não foi adicionado um campo novo", Toast.LENGTH_LONG).show();
		}
		
	}

}
