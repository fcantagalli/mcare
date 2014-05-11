package com.mCare.paciente;

import java.io.File;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.mCare.R;
import com.mCare.consulta.ListaConsultasPaciente;
import com.mCare.db.DbHelperPaciente;
import com.mCare.paciente.historico.ListComparaCamposArrayAdapter;
import com.mCare.paciente.historico.ListaCamposConsulta;
import com.mCare.paciente.historico.SelecionaCamposCompara;

public class InfPaciente extends Activity {
	
	int id_paciente;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inf_paciente);
		
		getActionBar().setTitle("Patient Details");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		final int id =  getIntent().getExtras().getInt("ID", -1);
		id_paciente = id;
		
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
		Button visualizarConsultas = (Button) findViewById(R.id.buttonVisualizarConsultas);
		Button visualizarHistorico = (Button) findViewById(R.id.buttonVisualizarHistorico);
		Button exportaXML = (Button) findViewById(R.id.buttonExportaXML);
		Button comparaValores =  (Button) findViewById(R.id.buttonComparaValores);
		
		comparaValores.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),SelecionaCamposCompara.class);
				Log.i("infPaciente", "id_paciente: " + id_paciente);
				intent.putExtra("id_paciente", "" + id_paciente);
				startActivity(intent);
				
			}
		});
		
		exportaXML.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//exportaXML();
				exportaJSON();
				
			}
		});
		
		exportaXML.setText("Export XML");
		
		if(p != null){
			nome.setText(p.getNome());
			telefone.setText(p.getTipo_tel()+" : "+p.getTelefone());
			sexo.setText(sexo(p.getSexo()));
			GregorianCalendar gc = p.getDataNascimento();
			dataNascimento.setText(gc.get(gc.DAY_OF_MONTH)+"/"+gc.get(gc.MONTH)+"/"+gc.get(gc.YEAR));
			
			tipo_end.setText(p.getTipo_endereco()+" ");
			logradouro.setText(p.getLogradouro());
			numero.setText("Number: "+p.getNumero());
			cidade.setText("City: "+ p.getCidade());
			
			if(p.getComplemento() != null){
				complemento.setText(p.getComplemento());
			}
			else{
				complemento.setVisibility(View.GONE);
			}
			
			if(p.getCep() != null){
				cep.setText("Postal Code: "+p.getCep());
			}
			else{
				cep.setVisibility(View.GONE);
			}
			
			if(p.getBairro()!=null){
				bairro.setText("District: "+p.getBairro());
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
			
			visualizarConsultas.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getApplicationContext(),ListaConsultasPaciente.class);
					intent.putExtra("id", id);
					startActivity(intent);
				}
			});	
		}
		else{
			visualizarConsultas.setVisibility(View.INVISIBLE);
		}
		
		final long id_paciente = p.getBd_id();
		
		visualizarHistorico.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),ListaCamposConsulta.class);
				intent.putExtra("id_paciente", id_paciente);
				startActivity(intent);
			}
		});

	}
	
	public void exportaJSON(){
		JSONBuilder json = new JSONBuilder(id_paciente, this);
		json.criaJSON();
		File send = getApplication().getFileStreamPath("testJSON.json");
		send.setReadable(true, false);
		Log.i("xml", "pode ler? " + send.canRead());
		Log.i("xml", "pode escrever? " + send.canWrite());
		Log.i("xml", "existe? " + send.exists());
		Log.i("xml", "caminho: " + send.getPath());
		Log.i("xml", "toString() " + send.toString());
		Log.i("xml", "url" + Uri.fromFile(send));
		
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "Aqui estão as recomendacoes do remedio. nao esqueça");
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, "XML medicine file");
		sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(send));
		sendIntent.setType("text/xml");
		
		startActivity(Intent.createChooser(sendIntent, "Send File"));
	}
	
	public void exportaXML(){
		XMLBuilder xml = new XMLBuilder(id_paciente, getApplicationContext());
		xml.criaXML();
		
		File send = getApplication().getFileStreamPath("test.xml");
		send.setReadable(true, false);
		Log.i("xml", "pode ler? " + send.canRead());
		Log.i("xml", "pode escrever? " + send.canWrite());
		Log.i("xml", "existe? " + send.exists());
		Log.i("xml", "caminho: " + send.getPath());
		Log.i("xml", "toString() " + send.toString());
		Log.i("xml", "url" + Uri.fromFile(send));
		
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "Aqui estão as recomendacoes do remedio. nao esqueça");
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, "XML medicine file");
		sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(send));
		sendIntent.setType("text/xml");
		//addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(sendIntent, "Send File"));
		
		/*
		String filename = "test.xml";
		File file = new File(context.getFilesDir(), filename);
		String string = "Sending Text by other modules";
		FileOutputStream outputStream;
		try {
		  outputStream = context.openFileOutput(filename, Context.MODE_WORLD_READABLE);
		  outputStream.write(string.getBytes());
		  outputStream.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}
		File send = context.getFileStreamPath("test.xml");
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, "Aqui estão as recomendacoes do remedio. nao esqueça");
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, "XML medicine file");
		sendIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(send));
		sendIntent.setType("image/jpeg");
		context.startActivity(Intent.createChooser(sendIntent, context.getResources().getText(R.string.send_to)));*/
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home: onBackPressed();
		break;
		default: return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private String sexo(byte sexo){
		if(sexo == 1){
			return "Male";
		}
		else{
			return "Female";
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inf_paciente, menu);
		return true;
	}

}
