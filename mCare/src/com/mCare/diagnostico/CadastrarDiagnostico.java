package com.mCare.diagnostico;

import com.mCare.R;
import com.mCare.db.DbHelperDiagnostico;

import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CadastrarDiagnostico extends Fragment  implements View.OnClickListener {

	EditText nome;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_cadastrar_diagnostico,container, false);
		
		// botoes para salvar ou cancelar o cadastro do diagnostico
		ImageView salvar = (ImageView) rootView.findViewById(R.id.buttonCadastrarDiagnostico);
		salvar.setOnClickListener(this);
		ImageView cancelar = (ImageView) rootView.findViewById(R.id.buttonCancelarDiagnostico);
		cancelar.setOnClickListener(this);
		
		// salva os campos
		nome = (EditText) rootView.findViewById(R.id.editTextCampoNomeDiagnostico);
		
		return rootView;
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){	
			case R.id.buttonCadastrarDiagnostico: {
				salvaDiagnostico(v);
				break;
			}
		}
	}
	

	
	private void salvaDiagnostico(View v){	
		Log.d("up","entrou no salvaDiagnostico");
		
		// primeiro trata campos que nao podem ser null
		if(nome.getText().toString().length() == 0){
			Toast.makeText(v.getContext(), "Insira um nome para esse diagnostico.", Toast.LENGTH_LONG).show();
			return;
		}
		
		// inserindo no banco agora com todos os valores
		DbHelperDiagnostico db = new DbHelperDiagnostico(v.getContext());
		
		Diagnostico d = new Diagnostico(-1, nome.getText().toString());
		
		long id = db.insereDiagnostico(d);
		
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
		Toast.makeText(v.getContext(), "Diagnostico cadastrado com sucesso!", Toast.LENGTH_LONG).show();
		onBackPressed();
		*/
	}

}