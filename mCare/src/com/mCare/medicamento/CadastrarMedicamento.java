package com.mCare.medicamento;

import com.mCare.R;
import com.mCare.db.DbHelperMedicamento;

import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class CadastrarMedicamento extends Fragment  implements View.OnClickListener {

	EditText nome;
	Spinner tipo;
	EditText dosagem;
	EditText principioAtivo;
	RadioGroup favorito;
	

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.activity_cadastrar_medicamento,container, false);
		
		// botoes para salvar ou cancelar o cadastro do medicamento
		ImageView salvar = (ImageView) rootView.findViewById(R.id.buttonCadastrarMedicamento);
		salvar.setOnClickListener(this);
		ImageView cancelar = (ImageView) rootView.findViewById(R.id.buttonCancelarMedicamento);
		cancelar.setOnClickListener(this);
		
		// salva os campos
		nome = (EditText) rootView.findViewById(R.id.editTextCampoNomeMedicamento);
		tipo = (Spinner) rootView.findViewById(R.id.spinnerTipoMedicamento);
		dosagem = (EditText) rootView.findViewById(R.id.editTextCampoDosagem);
		principioAtivo = (EditText) rootView.findViewById(R.id.editTextCampoPrincipioAtivo);
		favorito = (RadioGroup) rootView.findViewById(R.id.campoFavorito);

		// o tipo de medicamento eh uma caixa de selecao (Spinner)
		ArrayAdapter<CharSequence> possiveisTipos = ArrayAdapter.createFromResource(rootView.getContext(), R.array.array_tipos_medicamento, android.R.layout.simple_list_item_1);
		tipo.setAdapter(possiveisTipos);
		
		return rootView;
	}
	
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){	
			case R.id.buttonCadastrarMedicamento: {
				salvaMedicamento(v);
				break;
			}
		}
	}
	

	
	private void salvaMedicamento(View v){	
		Log.d("up","entrou no salvaMedicamento");
		
		// primeiro trata campos que nao podem ser null (nome e principio ativo)
		if(nome.getText().toString().length() == 0){
			Toast.makeText(v.getContext(), "Insira um nome para esse medicamento.", Toast.LENGTH_LONG).show();
			return;
		}
		if(principioAtivo.getText().toString().length() == 0){
			Toast.makeText(v.getContext(), "Insira um princípio ativo para esse medicamento.", Toast.LENGTH_LONG).show();
			return;
		}
		
		
		// inserindo no banco agora com todos os valores
		DbHelperMedicamento db = new DbHelperMedicamento(v.getContext());
		
		Medicamento m = new Medicamento(-1, nome.getText().toString(), tipo.getSelectedItem().toString());
		m.setDosagem(dosagem.getText().toString());
		
		if (favorito.getCheckedRadioButtonId() == 0) { //a primeira opcao do campo 'favorito' eh "Sim"
			m.setFavorito(true);
		}
		else {
			m.setFavorito(false);
		}
		
		long id = db.insereMedicamento(m);
		
		
		Intent intent = new Intent();
		intent.putExtra("nome", nome.getText().toString());
		intent.putExtra("id", id);
		
		/*********** OBSERVACAO DA GABI *************
		 * Nao consegui fazer isso dentro do fragment!
		 * Pesquisei na internet, mas falava pra fazer as coisas na view-pai desse fragment...
		 * Precisa mesmo dessa parte do codigo?
		 */
		/*
		if(this.getParentFragment() == null){
			setResult(Activity.RESULT_OK,intent);
		}
		else{
			this.getParentFragment().setResult(Activity.RESULT_OK, intent);
		}
		Toast.makeText(v.getContext(), "Medicamento cadastrado com sucesso!", Toast.LENGTH_LONG).show();
		onBackPressed();
		*/
	}
}
