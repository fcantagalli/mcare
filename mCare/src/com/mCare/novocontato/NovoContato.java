package com.mCare.novocontato;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
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
	
	private List<Tel> telefones;
	private NovoContatoAdapter adapter;
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
		
		TextView tv1 = (TextView) findViewById(R.id.textView1);
		TextView tv2 = (TextView) findViewById(R.id.textView2);
		//list = (ListView) findViewById(R.id.listViewContatos);
		LinearLayout ll1 = (LinearLayout) findViewById(R.id.linearLayout2);
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		ImageView maisTel = (ImageView) findViewById(R.id.imageView2);
		Spinner spinnerEscolaridade = (Spinner) findViewById(R.id.spinner2);
		RelativeLayout pai = (RelativeLayout) findViewById(R.id.paiDosTel);
		
		ImageView cancelarBotao = (ImageView) findViewById(R.id.imageViewCancelar);
		cancelarBotao.setOnClickListener(this);
		ArrayAdapter<CharSequence> possiveisTels = ArrayAdapter.createFromResource(this, R.array.tiposEndereco, android.R.layout.simple_list_item_1);
		spinner.setAdapter(possiveisTels);
		
		ArrayAdapter<CharSequence> escolaridades = ArrayAdapter.createFromResource(this, R.array.escolaridade, android.R.layout.simple_list_item_1);
		spinnerEscolaridade.setAdapter(escolaridades);
		
		telefones = new ArrayList<Tel>();
		
		//adapter = new NovoContatoAdapter(this, telefones,list,tv2,tv1);
		
		//list.setAdapter(adapter);
		maisTel.setOnClickListener(this);
		/*maisTel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ListView list2 = (ListView) findViewById(R.id.listViewContatos);
				//list2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, list2.getHeight()+97));
				//Log.i("phil", "cheguei!");
				adapter.addItem(new Tel());
			}
		});*/
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
				break;
			}
			case R.id.cancelarNovoContato:{
				break;
			}
			case R.id.imageView2:{
				adapter.addItem(new Tel());
				break;
			}
			case R.id.imageViewCancelar:{
				EditText texto = (EditText) findViewById(R.id.editTextNumero);
				texto.setText("");
			}
		}
	}

}
