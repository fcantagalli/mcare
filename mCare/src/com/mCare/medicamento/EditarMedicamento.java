package com.mCare.medicamento;

import com.mCare.R;
import com.mCare.db.DbHelperMedicamento;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;
import android.widget.Spinner;

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
		RadioGroup favorito = (RadioGroup) findViewById(R.id.campoFavorito);
		
		//Pega da tela anterior a informacao de qual medicamento eh este
		final int id =  getIntent().getExtras().getInt("id", -1);
		
		//Pega do banco as informacoes do medicamento
		DbHelperMedicamento db = new DbHelperMedicamento(this);
		Medicamento m = db.buscaMedicamento(id);
		
		//Coloca as informa��es nos campos
		medicamento.setText(m.getNome());
		dosagem.setText(m.getDosagem());
		principioAtivo.setText(m.getPricipioAtivo());
		
		//O tipo de medicamento eh uma caixa de selecao (Spinner)
		ArrayAdapter myAdap = (ArrayAdapter) tipo.getAdapter(); //cast to an ArrayAdapter
		int spinnerPosition = myAdap.getPosition(m.getTipo()); //posicao do Tipo na caixa de selecao
		tipo.setSelection(spinnerPosition); //seleciona o Tipo armazenado no banco
		
		//O campo 'favorito' eh um radiogroup (sim ou nao)
		if (m.getFavorito()) {
			favorito.check(0);
		}
		else {
			favorito.check(1);
		}
	}

}
