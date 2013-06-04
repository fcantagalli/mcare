package com.mCare.configuracaoConsulta;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import com.mCare.R;

public class CriaCampoView extends Activity implements OnItemSelectedListener {
	
	int posicaoSelecionada;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cria_campo_view);
		
		//popula spinner com os tipos do campo (enconta na classe strings.xml)
		Spinner tiposCampo = (Spinner) findViewById(R.id.spinnerTipoCampo);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.array_tipos_campo, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tiposCampo.setAdapter(adapter);
		tiposCampo.setOnItemSelectedListener(this);
		
		AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.editTextCampoNome);
		String[] nomes_campos = getResources().getStringArray(R.array.nomes_campo);
		ArrayAdapter<String> adapter_nomes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomes_campos);
		autoComplete.setAdapter(adapter_nomes);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.cria_campo_view, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		posicaoSelecionada = arg2;
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}
	
	public void salvaCampo(View view){
		EditText campo = (EditText) findViewById(R.id.editTextCampoNome);
		String nomeCampo = campo.getText().toString();
		if(nomeCampo.length() == 0){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Por favor digite o nome do campo");
			builder.setIcon(R.drawable.dunno);
			builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
		}else{
			Intent data = new Intent();
			data.putExtra("nomeCampo", campo.getText().toString());
			data.putExtra("tipoCampo", posicaoSelecionada);
			
			if(this.getParent() == null){
				setResult(Activity.RESULT_OK, data);
			}else{
				this.getParent().setResult(Activity.RESULT_OK, data);
			}
			super.onBackPressed();
		}
	}

}
