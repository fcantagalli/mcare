package com.mCare.paciente;

import java.util.GregorianCalendar;

import android.os.Bundle;
import com.mCare.R;
import com.mCare.db.DbHelperPaciente;

import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class InfPaciente extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inf_paciente);
		
		int id =  getIntent().getExtras().getInt("ID", -1);
		
		DbHelperPaciente db = new DbHelperPaciente(this);
		Paciente p = db.buscaPaciente(id);
		
		TextView nome = (TextView) findViewById(R.id.nomePaciente);
		TextView telefone = (TextView) findViewById(R.id.campoTelefone1);
		TextView dataNascimento = (TextView) findViewById(R.id.campoDataNascimento);
		TextView sexo = (TextView) findViewById(R.id.campoSexo);
		TextView escolaridade = (TextView) findViewById(R.id.campoEscolaridade);
		TextView parente = (TextView) findViewById(R.id.campoParente);
		TextView parente_tel = (TextView) findViewById(R.id.campoParente_tel);
		TextView parente_cel = (TextView) findViewById(R.id.campoParente_cel);
		TextView tipo_end = (TextView) findViewById(R.id.campoTipoEnd);
		TextView logradouro = (TextView) findViewById(R.id.campoLograduro);
		TextView numero = (TextView) findViewById(R.id.campoNumero);
		TextView complemento = (TextView) findViewById(R.id.campoComplemento);
		TextView cep = (TextView) findViewById(R.id.campoCEP);
		TextView bairro = (TextView) findViewById(R.id.campoBairro);
		TextView cidade = (TextView) findViewById(R.id.campoCidade);
		
		if(p != null){
			nome.setText(p.getNome());
			telefone.setText(p.getTipo_tel()+" : "+p.getTelefone());
			sexo.setText(sexo(p.getSexo()));
			GregorianCalendar gc = p.getDataNascimento();
			dataNascimento.setText(gc.get(gc.DAY_OF_MONTH)+"/"+gc.get(gc.MONTH)+"/"+gc.get(gc.YEAR));
			
			tipo_end.setText(p.getTipo_endereco()+" ");
			logradouro.setText(p.getLogradouro());
			numero.setText("Numero: "+p.getNumero());
			cidade.setText("Cidade : "+ p.getCidade());
			
			if(p.getComplemento() != null){
				complemento.setText(p.getComplemento());
			}
			else{
				complemento.setVisibility(View.GONE);
			}
			
			if(p.getCep() != null){
				cep.setText("Cep: "+p.getCep());
			}
			else{
				cep.setVisibility(View.GONE);
			}
			
			if(p.getBairro()!=null){
				Log.wtf("inf", "oque tem em bairro: "+p.getBairro());
				bairro.setText("Bairro: "+p.getBairro());
			}else{
				bairro.setVisibility(View.INVISIBLE);
			}
			
			if(p.getEscolaridade() != null){
				escolaridade.setText(p.getEscolaridade());
			}
			if(p.getParente() != null){
				parente.setText(p.getParente());
			}
			if(p.getParente_tel() != null){
				parente_tel.setText(p.getParente_tel());
			}
			if(p.getParente_cel() != null){
				parente_cel.setText(p.getParente_cel());
			}
		}
		
	}

	private String sexo(byte sexo){
		if(sexo == 1){
			return "Masculino";
		}
		else{
			return "Feminino";
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inf_paciente, menu);
		return true;
	}

}
