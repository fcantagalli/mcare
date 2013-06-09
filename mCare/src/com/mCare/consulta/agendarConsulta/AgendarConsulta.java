package com.mCare.consulta.agendarConsulta;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agendar_consulta);
		
		Button finalizar = (Button) findViewById(R.id.buttonAgendarConsulta);
		finalizar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				agendarConsulta();
			}
		});
		
		DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerAgendarConsulta);
		datePicker.setCalendarViewShown(false);
		
		getActionBar().setTitle("Agendar Consulta");
		getActionBar().setSubtitle("insira as informações de uma nova consulta");
		
		autoComplete = (AutoCompleteTextView) findViewById(R.id.editTextCampoNomePaciente);
		String[] nomesPacientes = getPacientes();
		if(nomesPacientes!=null){
			ArrayAdapter<String> adapter_nomes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nomesPacientes);
			autoComplete.setAdapter(adapter_nomes);
		}
		
		tipoConsulta = (Spinner) findViewById(R.id.spinnerTipoConsulta);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipos_consulta, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		tipoConsulta.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.agendar_consulta_2, menu);
		return true;
	}
	
	public void agendarConsulta(){
		DatePicker datePicker = (DatePicker) findViewById(R.id.datePickerAgendarConsulta);
		TimePicker timePicker = (TimePicker) findViewById(R.id.timePickerAgendarConsulta);
		String dataHorario = "Data: " + datePicker.getYear() + " " + datePicker.getMonth()+1 + " " + datePicker.getDayOfMonth();
		String horario = "Horario: " + timePicker.getCurrentHour() + " " + timePicker.getCurrentMinute();
		Log.wtf("agendar", dataHorario + " ----- " + horario);
		GregorianCalendar calendar = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth()+1, datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
		
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
		
		Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
		// 3600000 equivale a 1 hora de antecedencia
		Log.i("fe","qual a string de tempo : "+dbConsulta.dbhelper.formataData(calendar));
		NotificacaoConsulta.create(getApplicationContext(),(calendar.getTimeInMillis()-3600000) , "Paciente: " + consulta.getPaciente().getNome(), "Consulta agendada", "Você tem uma consulta!", R.drawable.ic_launcher, NotificacaoConsulta.notificationId(calendar));
		
		Toast.makeText(getApplicationContext(), "Consulta agendada com sucesso!", Toast.LENGTH_LONG).show();
		
		onBackPressed();
	}
	
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
