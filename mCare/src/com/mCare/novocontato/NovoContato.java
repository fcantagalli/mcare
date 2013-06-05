package com.mCare.novocontato;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mCare.R;
import com.mCare.db.DbHelperPaciente;
import com.mCare.db.DbHelperTelefone;
import com.mCare.paciente.Paciente;

public class NovoContato extends Activity implements View.OnClickListener {
	
	
	EditText nome;
	EditText tel1;
	Spinner tipo1;
	EditText tel2;
	Spinner tipo2;
	EditText tel3;
	Spinner tipo3;
	EditText logradouro;
	Spinner tipoEndereco;
	EditText numero;
	EditText complemento;
	EditText cep;
	EditText bairro;
	EditText cidade;
	EditText email;
	DatePicker dataNascimento;
	Spinner escolaridade;
	EditText nomeParente;
	EditText telParente;
	EditText celParente;
	//private ListView list;
	
	boolean editar = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_contato);
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.windows_title_contatos);
		actionBar.setDisplayShowHomeEnabled(true);
		
		// botoes para salvar ou cancelar o novo contato
		ImageView salvar = (ImageView) findViewById(R.id.salvarNovoContato);
		salvar.setOnClickListener(this);
		ImageView cancelar = (ImageView) findViewById(R.id.cancelarNovoContato);
		cancelar.setOnClickListener(this);
		
		//nome do novo contato
		nome = (EditText) findViewById(R.id.editTextNomePessoa);
		
		if(getIntent().getExtras() != null){
			nome.setText((String)getIntent().getExtras().get("nome"));
		}
		
		//telefones e tipos
		tel1 = (EditText) findViewById(R.id.editTextNumero1);
		tipo1 = (Spinner) findViewById(R.id.spinner1);
		tel2 = (EditText) findViewById(R.id.editTextNumero2);
		tipo2 = (Spinner) findViewById(R.id.spinner2);
		tel3 = (EditText) findViewById(R.id.editTextNumero3);
		tipo3 = (Spinner) findViewById(R.id.spinner3);
		
		//Endereco
		logradouro = (EditText) findViewById(R.id.editText2);
		tipoEndereco = (Spinner) findViewById(R.id.spinnerEndereco);
		ArrayAdapter<CharSequence> possiveisEnderecos = ArrayAdapter.createFromResource(this, R.array.tiposEndereco, android.R.layout.simple_list_item_1);
		tipoEndereco.setAdapter(possiveisEnderecos);
		numero = (EditText) findViewById(R.id.editText3);
		complemento = (EditText) findViewById(R.id.editText4);
		cep = (EditText) findViewById(R.id.editText5);
		bairro = (EditText) findViewById(R.id.editText6);
		cidade = (EditText) findViewById(R.id.editText12);
		
		// email
		email = (EditText) findViewById(R.id.editText7);
		
		// data de nascimento
		dataNascimento = (DatePicker) findViewById(R.id.datePicker1);
		GregorianCalendar gc = new GregorianCalendar();
		dataNascimento.setMaxDate(gc.getTimeInMillis());
		
		//Escolaridade
		escolaridade = (Spinner) findViewById(R.id.spinnerEsc);
		
		//Parente
		nomeParente = (EditText) findViewById(R.id.editText9);
		telParente = (EditText) findViewById(R.id.editText10);
		celParente = (EditText) findViewById(R.id.editText11);
		
		// essa parte e referente para o cara editar o contato ja criado
		
		ImageView cancelarBotao = (ImageView) findViewById(R.id.imageViewCancelar);
		cancelarBotao.setOnClickListener(this);
		
		ArrayAdapter<CharSequence> possiveisTels = ArrayAdapter.createFromResource(this, R.array.tiposTel, android.R.layout.simple_list_item_1);
		tipo1.setAdapter(possiveisTels);
		tipo2.setAdapter(possiveisTels);
		tipo3.setAdapter(possiveisTels);
		
		ArrayAdapter<CharSequence> escolaridades = ArrayAdapter.createFromResource(this, R.array.escolaridade, android.R.layout.simple_list_item_1);
		escolaridade.setAdapter(escolaridades);
		
		Bundle b = getIntent().getExtras();
		
		if(b != null){
			DbHelperPaciente db = new DbHelperPaciente(this);
			
			Paciente p = db.buscaPaciente(b.getInt("id"));
			
			if(p != null){
				editar = true;
				nome.setText(p.getNome());
				tipo1.setSelection(tipoTel(p.getTipo_tel()));
				tel1.setText(p.getTelefone());
				
				tipoEndereco.setSelection(tipoRua(p.getTipo_endereco()));
				logradouro.setText(p.getLogradouro());
				numero.setText(p.getNumero());
				complemento.setText(p.getComplemento());
				cep.setText(p.getCep());
				bairro.setText(p.getBairro());
				cidade.setText(p.getCidade());
				gc = p.getDataNascimento();
				dataNascimento.init(gc.get(gc.YEAR), gc.get(gc.MONTH), gc.get(gc.DAY_OF_MONTH), dListener);
			}
		}
		
	}

	private DatePicker.OnDateChangedListener dListener = new DatePicker.OnDateChangedListener() {

        public void onDateChanged(DatePicker view, int year, int monthOfYear,
                int dayOfMonth) {

                Calendar cPickerDate = Calendar.getInstance();
                cPickerDate.set(year, monthOfYear, dayOfMonth);
                int dayOfYear = cPickerDate.get(Calendar.DAY_OF_YEAR);
                Log.v("day of year", String.valueOf(dayOfYear));


                /*
                //automatically checks radio buttons if user manually adjust picker
                if(dayOfYear==cNow.get(Calendar.DAY_OF_YEAR)+1){
                    rTomorrow.setChecked(true);
                }
                else if(dayOfYear==cNow.get(Calendar.DAY_OF_YEAR)+2){
                    rIn2Days.setChecked(true);
                }
                else if(dayOfYear==cNow.get(Calendar.DAY_OF_YEAR)+7){
                    rNextWeek.setChecked(true);
                } else {
                    dtpRadGroup.clearCheck();
                }
				*/
        }
    };
	
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
				onBackPressed();
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
		if(nome.getText().toString().length() == 0){
			Toast.makeText(getApplicationContext(), "Insira um nome para esse contato.", Toast.LENGTH_LONG).show();
			return;
		}
		if(tel1.getText().toString().length() == 0 && tel2.getText().toString().length() == 0 && tel3.getText().toString().length() == 0){
			Toast.makeText(getApplicationContext(), "Insira um número de telefone pelo menos.", Toast.LENGTH_LONG).show();
			return;
		}
		if(logradouro.getText().toString().length() == 0){
			Toast.makeText(getApplicationContext(), "Insira um endereco para o novo contato.", Toast.LENGTH_LONG).show();
			return;
		}
		if(numero.getText().toString().length() == 0){
			Toast.makeText(getApplicationContext(), "numero do endereco inválido.", Toast.LENGTH_LONG).show();
			return;
		}
		if(cidade.getText().toString().length() == 0 ){
			Toast.makeText(getApplicationContext(), "Campo de cidade inválido.", Toast.LENGTH_LONG).show();
			return;
		}
		// inserindo no banco agora com todos os valores
		
		DbHelperPaciente db = new DbHelperPaciente(getApplicationContext());
		
		GregorianCalendar gc = new GregorianCalendar(dataNascimento.getYear(), dataNascimento.getMonth(), dataNascimento.getDayOfMonth());
		
		String bairro;
		bairro = this.bairro.getText().toString();
		
		if(bairro.length() == 0){
			bairro = null;
		}
		
		Paciente p = new Paciente(-1,nome.getText().toString(),gc,(byte) 1,logradouro.getText().toString(),bairro,Integer.parseInt(numero.getText().toString()),cidade.getText().toString());
		
		p.setEscolaridade((String) escolaridade.getSelectedItem());
		p.setTipo_endereco((String) tipoEndereco.getSelectedItem());
		p.setComplemento(complemento.getText().toString());
		p.setCep(cep.getText().toString());
		p.setParente(nomeParente.getText().toString());
		p.setParente_tel(telParente.getText().toString());
		p.setParente_cel(celParente.getText().toString());
		
		long id = db.inserePaciente(p);
		
		
		if(id != -1){
			DbHelperTelefone dbt = new DbHelperTelefone(getApplicationContext());
			
			if(tel1.getText().toString().length() != 0){
				dbt.insereTelefone(id, tel1.getText().toString() , (String) tipo1.getSelectedItem());
			}
			if(tel2.getText().toString().length() != 0){
				dbt.insereTelefone(id, tel2.getText().toString() , (String) tipo2.getSelectedItem());
			}
			if(tel3.getText().toString().length() != 0){
				dbt.insereTelefone(id, tel3.getText().toString() , (String) tipo3.getSelectedItem());
			}
			Log.wtf("insere", "Telefone inserido");
			Log.wtf("insere", ""+id);
		}
		else{
			Log.wtf("insere", "Telefone nao inserido");
			Log.wtf("insere", ""+id);
		}
		
		Intent intent = new Intent();
		intent.putExtra("nome", nome.getText().toString());
		intent.putExtra("id", id);
		if(this.getParent() == null){
			setResult(Activity.RESULT_OK,intent);
		}
		else{
			this.getParent().setResult(Activity.RESULT_OK, intent);
		}
		Toast.makeText(getApplicationContext(), "Paciente cadastrado com sucesso!", Toast.LENGTH_LONG).show();
		super.onBackPressed();
	}
   
	private int tipoTel(String s){
		if(s.equals("Celular")){
			return 0;
		}
		if(s.equals("Comercial")){
			return 1;
		}
		if(s.equals("Residencial")){
			return 2;
		}
		if(s.equals("Principal")){
			return 3;
		}
		if(s.equals("Outros")){
			return 4;
		}
		else{
			return 0;
		}
	}

	private int tipoRua(String s){
		if(s.equals("Rua")){
			return 0;
		}
		if(s.equals("Avenida")){
			return 1;
		}
		if(s.equals("Praça")){
			return 2;
		}
		else{
			return 0;
		}
	}
	
}

