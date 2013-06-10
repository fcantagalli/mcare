package com.mCare.medicamento;

import com.mCare.R;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class EditarMedicamento extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.activity_editar_medicamento,container, false);
		
		//Pega os campos da activity em xml
		AutoCompleteTextView medicamento = (AutoCompleteTextView) rootView.findViewById(R.id.editTextCampoNomeMedicamento);
		Spinner tipo = (Spinner) rootView.findViewById(R.id.spinnerTipoMedicamento);
		AutoCompleteTextView dosagem = (AutoCompleteTextView) rootView.findViewById(R.id.editTextCampoDosagem);
		AutoCompleteTextView principioAtivo = (AutoCompleteTextView) rootView.findViewById(R.id.editTextCampoPrincipioAtivo);
		RadioGroup favorito = (RadioGroup) rootView.findViewById(R.id.campoFavorito);
		
		//Pega as informações
		String[] informacoes = {}; /*********** AQUI VEM AS INFORMACOES DO BANCO ***********/
		
		//Coloca as informações nos campos
		medicamento.setText(informacoes[0]);
		dosagem.setText(informacoes[2]);
		principioAtivo.setText(informacoes[3]);
		
		//O tipo de medicamento eh uma caixa de selecao (Spinner)
		ArrayAdapter myAdap = (ArrayAdapter) tipo.getAdapter(); //cast to an ArrayAdapter
		int spinnerPosition = myAdap.getPosition(informacoes[1]); //posicao do Tipo na caixa de selecao
		tipo.setSelection(spinnerPosition); //seleciona o Tipo armazenado no banco
		
		//O campo 'favorito' eh um radiogroup (sim ou nao)
		if (informacoes[4] == "true") {
			favorito.check(0);
		}
		else {
			favorito.check(1);
		}
		
		return rootView;
	}

}
