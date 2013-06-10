package com.mCare.medicamento;

import com.mCare.R;
import com.mCare.db.DbHelperMedicamento;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class CadastrarMedicamento extends Activity implements View.OnClickListener {

	EditText nome;
	Spinner tipo;
	EditText dosagem;
	EditText principioAtivo;
	RadioGroup favorito;
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_medicamento);
		
		// botoes para salvar ou cancelar o cadastro do medicamento
		Button salvar = (Button) findViewById(R.id.buttonCadastrarMedicamento);
		salvar.setOnClickListener(this);
		Button cancelar = (Button) findViewById(R.id.buttonCancelarMedicamento);
		cancelar.setOnClickListener(this);
		
		// salva os campos
		nome = (EditText) findViewById(R.id.editTextCampoNomeMedicamento);
		tipo = (Spinner) findViewById(R.id.spinnerTipoMedicamento);
		dosagem = (EditText) findViewById(R.id.editTextCampoDosagem);
		principioAtivo = (EditText) findViewById(R.id.editTextCampoPrincipioAtivo);
		favorito = (RadioGroup) findViewById(R.id.campoFavorito);

		// o tipo de medicamento eh uma caixa de selecao (Spinner)
		ArrayAdapter<CharSequence> possiveisTipos = ArrayAdapter.createFromResource(getApplicationContext(), R.array.array_tipos_medicamento, android.R.layout.simple_list_item_1);
		tipo.setAdapter(possiveisTipos);
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
			Toast.makeText(v.getContext(), "Insira um princ�pio ativo para esse medicamento.", Toast.LENGTH_LONG).show();
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
		
		
		if(this.getParent() == null){
			setResult(Activity.RESULT_OK,intent);
		}
		else{
			this.getParent().setResult(Activity.RESULT_OK, intent);
		}
		Toast.makeText(v.getContext(), "Medicamento cadastrado com sucesso!", Toast.LENGTH_LONG).show();
		onBackPressed();
	}
}
