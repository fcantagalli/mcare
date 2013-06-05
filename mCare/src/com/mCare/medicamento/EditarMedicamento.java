package com.mCare.medicamento;

import com.mCare.R;
import com.mCare.R.id;
import com.mCare.R.layout;
import com.mCare.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

public class EditarMedicamento extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_medicamento);
		
		//Pega os campos da activity em xml
		AutoCompleteTextView medicamento = (AutoCompleteTextView) findViewById(R.id.editTextCampoNomeMedicamento);
		Spinner tipo = (Spinner) findViewById(R.id.spinnerTipoMedicamento);
		AutoCompleteTextView dosagem = (AutoCompleteTextView) findViewById(R.id.editTextCampoDosagem);
		AutoCompleteTextView principioAtivo = (AutoCompleteTextView) findViewById(R.id.editTextCampoPrincipioAtivo);
		
		//Pega as informações
		String[] informacoes = (String[]) getIntent().getExtras().get("informacoes");
		
		//Coloca as informações nos campos
		medicamento.setText(informacoes[0]);
		dosagem.setText(informacoes[2]);
		principioAtivo.setText(informacoes[3]);
		
		//O tipo de medicamento eh uma caixa de selecao (Spinner)
		ArrayAdapter myAdap = (ArrayAdapter) tipo.getAdapter(); //cast to an ArrayAdapter
		int spinnerPosition = myAdap.getPosition(informacoes[1]); //posicao do Tipo na caixa de selecao
		tipo.setSelection(spinnerPosition); //seleciona o Tipo armazenado no banco
		
		//Titulo da actionbar
		getActionBar().setTitle("Medicamento:");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editar_medicamento, menu);
		return true;
	}

}
