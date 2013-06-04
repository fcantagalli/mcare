package com.mCare.novocontato;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mCare.R;

public class NovoContato extends Activity implements View.OnClickListener {
	
	
	EditText nome;
	EditText tel1;
	Spinner tipo1;
	EditText tel2;
	Spinner tipo2;
	EditText tel3;
	Spinner tipo3;
	EditText endereco;
	Spinner tipoEndereco;
	EditText numero;
	EditText complemento;
	EditText cep;
	EditText bairro;
	EditText email;
	EditText dataNascimento;
	Spinner escolaridade;
	EditText nomeParente;
	EditText telParente;
	EditText celParente;
	//private ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_novo_contato);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.windows_title_contatos);
		
		// botoes para salvar ou cancelar o novo contato
		ImageView salvar = (ImageView) findViewById(R.id.salvarNovoContato);
		salvar.setOnClickListener(this);
		ImageView cancelar = (ImageView) findViewById(R.id.cancelarNovoContato);
		cancelar.setOnClickListener(this);
		
		//nome do novo contato
		nome = (EditText) findViewById(R.id.editTextNomePessoa);
		
		//telefones e tipos
		tel1 = (EditText) findViewById(R.id.editTextNumero1);
		tipo1 = (Spinner) findViewById(R.id.spinner1);
		tel2 = (EditText) findViewById(R.id.editTextNumero2);
		tipo2 = (Spinner) findViewById(R.id.spinner2);
		tel3 = (EditText) findViewById(R.id.editTextNumero3);
		tipo3 = (Spinner) findViewById(R.id.spinner3);
		
		//Endereco
		endereco = (EditText) findViewById(R.id.editText2);
		tipoEndereco = (Spinner) findViewById(R.id.spinnerEndereco);
		numero = (EditText) findViewById(R.id.editText3);
		complemento = (EditText) findViewById(R.id.editText4);
		cep = (EditText) findViewById(R.id.editText5);
		bairro = (EditText) findViewById(R.id.editText6);
		
		// email
		email = (EditText) findViewById(R.id.editText7);
		
		// data de nascimento
		dataNascimento = (EditText) findViewById(R.id.editText8);
		
		//Escolaridade
		escolaridade = (Spinner) findViewById(R.id.spinnerEsc);
		
		//Parente
		nomeParente = (EditText) findViewById(R.id.editText9);
		telParente = (EditText) findViewById(R.id.editText10);
		celParente = (EditText) findViewById(R.id.editText11);
		
		ImageView cancelarBotao = (ImageView) findViewById(R.id.imageViewCancelar);
		cancelarBotao.setOnClickListener(this);
		
		ArrayAdapter<CharSequence> possiveisTels = ArrayAdapter.createFromResource(this, R.array.tiposEndereco, android.R.layout.simple_list_item_1);
		tipo1.setAdapter(possiveisTels);
		tipo2.setAdapter(possiveisTels);
		tipo3.setAdapter(possiveisTels);
		
		ArrayAdapter<CharSequence> escolaridades = ArrayAdapter.createFromResource(this, R.array.escolaridade, android.R.layout.simple_list_item_1);
		escolaridade.setAdapter(escolaridades);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.novo_contato, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){	
			case R.id.salvarNovoContato: {
				salvaContato();
				break;
			}
			case R.id.cancelarNovoContato:{
				this.finish();
				break;
			}
			case R.id.imageViewCancelar:{
				EditText texto = (EditText) findViewById(R.id.imageViewCancelar);
				texto.setText("");
			}
		}
	}
	
	private void salvaContato(){	
		// primeiro trata campos que nao podem ser null
		
	}

}
