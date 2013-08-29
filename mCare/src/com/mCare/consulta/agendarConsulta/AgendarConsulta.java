package com.mCare.consulta.agendarConsulta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mCare.R;
import com.mCare.consulta.Consulta;
import com.mCare.db.DbHelperConsultas;
import com.mCare.db.DbHelperPaciente;
import com.mCare.main.MainActivity;
import com.mCare.notificacao.NotificacaoConsulta;
import com.mCare.novocontato.NovoContato;
import com.mCare.paciente.Paciente;

public class AgendarConsulta extends Activity {
	
	Spinner tipoConsulta;
	AutoCompleteTextView autoComplete;
	boolean foiAgendada = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agendar_consulta);
		
		
		//instancia botao de finalizar e seta oque sera feito no click
		Button finalizar = (Button) findViewById(R.id.buttonAgendarConsulta);
		finalizar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				agendarConsulta();
			}
		});
		
		
		DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerAgendarConsulta);
		datePicker.setCalendarViewShown(false);
		
		// altera actionBar
		getActionBar().setTitle("Agendar Consulta");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		// instancia e popula autoComplete
		autoComplete = (AutoCompleteTextView) findViewById(R.id.editTextCampoNomePaciente);
		String[] nomesPacientes = getPacientes();
		if(nomesPacientes!=null){
			ArrayAdapter<String> adapter_nomes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomesPacientes);
			autoComplete.setAdapter(adapter_nomes);
		}
		
		// spinner dos tipos das consultas
		tipoConsulta = (Spinner) findViewById(R.id.spinnerTipoConsulta);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipos_consulta, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tipoConsulta.setAdapter(adapter);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home: {
			Toast.makeText(this, "A consulta não foi agendada", Toast.LENGTH_LONG).show();
			onBackPressed();
		}
		break;
		default: return super.onOptionsItemSelected(item);
		}
		return true;
	}
	
	@Override
	public void onBackPressed() {
		if(!foiAgendada){
			Toast.makeText(this, "A consulta não foi agendada", Toast.LENGTH_LONG).show();
		}
		super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.agendar_consulta_2, menu);
		return true;
	}
	
	
	/**
	 * Metodo para recuperar todas as informacoes de todos os objetos de infomacoes, verificado se ha campos vazios.
	 * verifica os campos obrigatorios e salva no banco de dados.
	 * 
	 */
	public void agendarConsulta(){
		DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerAgendarConsulta);
		TimePicker timePicker = (TimePicker) findViewById(R.id.timePickerAgendarConsulta);
		String dataHorario = "Data: " + datePicker.getYear() + " " + datePicker.getMonth()+1 + " " + datePicker.getDayOfMonth();
		String horario = "Horario: " + timePicker.getCurrentHour() + " " + timePicker.getCurrentMinute();
		Log.wtf("agendar", dataHorario + " ----- " + horario);
		GregorianCalendar calendar = new GregorianCalendar(TimeZone.getDefault(),Locale.getDefault());
		calendar.set(datePicker.getYear(),datePicker.getMonth()+1 , datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
		Log.i("noti","  "+datePicker.getYear()+"   "+datePicker.getMonth()+1+"  "+datePicker.getDayOfMonth()+"  "+ timePicker.getCurrentHour()+"  "+ timePicker.getCurrentMinute());
		//GregorianCalendar calendar = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth()+1, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
		
		//final AutoCompleteTextView autoComplete = (AutoCompleteTextView) findViewById(R.id.editTextCampoNomePaciente);
		
		if(autoComplete.getText().toString().length()==0){
			Toast.makeText(getApplicationContext(), "Digite o nome do paciente", Toast.LENGTH_LONG).show();
			return;
		}
		
		DbHelperPaciente dbPaciente = new DbHelperPaciente(getApplicationContext());
		Paciente paciente = dbPaciente.buscaPaciente(autoComplete.getText().toString());
		dbPaciente = null;
		if(paciente == null){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Não foi possível encontrar o paciente selecionado");
			builder.setIcon(R.drawable.dunno);
			builder.setPositiveButton("Cadastrar novo paciente", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					@SuppressWarnings("unused")
					String nomePaciente = autoComplete.getText().toString();
					Intent intent = new Intent(getApplicationContext(), NovoContato.class);
					intent.putExtra("nome", autoComplete.getText().toString());
					startActivity(intent);
				}
			});
			builder.setNegativeButton("Selecionar outro paciente", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					autoComplete.setText("");
					autoComplete.requestFocus();
					return;
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
			return;
		}
		
		EditText descricao = (EditText) findViewById(R.id.editTextDescricao);
		
		if(descricao.getText().toString().length() == 0){
			Toast.makeText(getApplicationContext(), "Digite uma descrição para a consulta", Toast.LENGTH_LONG).show();
			return;
		}
		Consulta consulta = new Consulta(paciente, calendar, (String)tipoConsulta.getSelectedItem(), descricao.getText().toString());
		
		DbHelperConsultas dbConsulta = new DbHelperConsultas(getApplicationContext());
		dbConsulta.insereConsulta(consulta);
		// parte relativa a notificação
		// 1800000 equivale a 30min de antecedencia
		calendar.set(Calendar.MONTH, datePicker.getMonth());
		Log.i("fe","qual a string de tempo : "+dbConsulta.dbhelper.formataData(calendar));
		NotificacaoConsulta.create(this,(calendar.getTimeInMillis()-1800000) , "Paciente: " + consulta.getPaciente().getNome(), "Consulta agendada", "Você tem uma consulta às "+consulta.getHora().get(Calendar.HOUR_OF_DAY)+" : "+consulta.getHora().get(Calendar.MINUTE)+" \n no endereço: " +consulta.getPaciente().getLogradouro() + ", "+consulta.getPaciente().getNumero(), R.drawable.ic_launcher);
		
		Toast.makeText(getApplicationContext(), "Consulta agendada com sucesso!", Toast.LENGTH_LONG).show();
		
		foiAgendada = true;
	
		setResult(Activity.RESULT_OK);

		onBackPressed();
	}
	/**
	 * Metodo que retorna uma lista com todos os pacientes cadastrados no banco de dados.
	 * 
	 * @return Lista com todos os pacientes cadastrados no banco de dados.
	 */
	public String[] getPacientes(){
		DbHelperPaciente db = new DbHelperPaciente(getApplicationContext());
		ArrayList<Paciente> pacientes = db.listaPacientes();
		if(pacientes==null){
			return null;
		}
		String[] nomePacientes = new String[pacientes.size()];
		for(int i=0; i<pacientes.size(); i++){
			nomePacientes[i] = pacientes.get(i).getNome();
		}
		return nomePacientes;
	}

}
