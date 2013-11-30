package com.mCare.medicamento;

import com.mCare.R;
import com.mCare.R.id;
import com.mCare.R.layout;
import com.mCare.R.menu;
import com.mCare.db.DbHelperMedicamento;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class VisualizarMedicamento extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visualizar_medicamento);
		
		//Pega os campos da activity em xml
				TextView medicamento = (TextView) findViewById(R.id.textViewMedicamento);
				TextView tipo = (TextView) findViewById(R.id.textViewTipo);
				TextView dosagem = (TextView) findViewById(R.id.textViewDosagem);
				TextView principioAtivo = (TextView) findViewById(R.id.textViewPrincipioAtivo);
				
				//Pega da tela anterior a informacao de qual medicamento eh este
				int id = getIntent().getExtras().getInt("id", -1);
				
				//Pega do banco as informacoes do medicamento
				DbHelperMedicamento db = new DbHelperMedicamento(this);
				Medicamento m = db.buscaMedicamento(id);
				
				//Coloca as informa��es nos campos
				medicamento.setText(m.getNome());
				tipo.setText(m.getTipo());
				dosagem.setText(m.getDosagem());
				principioAtivo.setText(m.getPricipioAtivo());
		
	}

}
