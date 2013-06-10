package com.mCare.diagnostico;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mCare.R;
import com.mCare.db.DbHelperDiagnostico;

public class CadastrarDiagnostico extends Activity  implements View.OnClickListener {

	EditText nome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_diagnostico);
		
		ImageView salvar = (ImageView) findViewById(R.id.buttonCadastrarDiagnostico);
		salvar.setOnClickListener(this);
		ImageView cancelar = (ImageView) findViewById(R.id.buttonCancelarDiagnostico);
		cancelar.setOnClickListener(this);
		
		// salva os campos
		nome = (EditText) findViewById(R.id.editTextCampoNomeDiagnostico);
	
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
		
		if(this.getParent() == null){
			this.setResult(Activity.RESULT_OK,intent);
		}
		else{
			this.setResult(Activity.RESULT_OK, intent);
		}
		Toast.makeText(v.getContext(), "Diagnostico cadastrado com sucesso!", Toast.LENGTH_LONG).show();
		onBackPressed();
		
	}

}