package com.mCare.exame;

import com.mCare.R;
import com.mCare.R.layout;
import com.mCare.R.menu;
import com.mCare.db.DbHelperExame;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class EditarExame extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_exame);

		//Pega da tela anterior a informacao de qual medicamento eh este
		final int id =  getIntent().getExtras().getInt("id", -1);
		
		//Pega do banco as informacoes do medicamento
		DbHelperExame db = new DbHelperExame(this);
		Exame e = db.buscaExame(id);
		
		//Pega os campos da activity em xml
		EditText exame = (EditText) findViewById(R.id.editTextCampoNomeExameEditar);
		Spinner tipoResultadoExame = (Spinner) findViewById(R.id.spinnerTipoMedicamento);
		
		//Coloca as informacoes nos campos
		exame.setText(e.getNome());
		
		//O tipo de resultado do exame eh uma caixa de selecao (Spinner)
		ArrayAdapter myAdap = (ArrayAdapter) tipoResultadoExame.getAdapter(); //cast to an ArrayAdapter
		int spinnerPosition = myAdap.getPosition(e.getTipoResultadoExame()); //posicao do Tipo na caixa de selecao
		tipoResultadoExame.setSelection(spinnerPosition); //seleciona o Tipo armazenado no banco
	}
}
