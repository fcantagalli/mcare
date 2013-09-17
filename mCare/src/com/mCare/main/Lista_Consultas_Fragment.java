package com.mCare.main;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.mCare.R;
import com.mCare.consulta.Consulta;
import com.mCare.consulta.VisualizaInfoConsultaAgendada;
import com.mCare.consulta.agendarConsulta.AgendarConsulta;
import com.mCare.consulta.realizarConsulta.RealizarConsultaMain;
import com.mCare.db.DbHelperConsultas;

public abstract class Lista_Consultas_Fragment extends Fragment {
	
	List<Consulta> lstConsultas;
	ListView list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_consultas,
				container, false);

		ImageView agendarConsulta = (ImageView) rootView
				.findViewById(R.id.imageViewAgendarConsulta);
		agendarConsulta.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity().getApplicationContext(), AgendarConsulta.class);
				startActivityForResult(intent, 0);
			}
		});

		// Referencias para a tela de agenda do dia em geral
		list = (ListView) rootView.findViewById(R.id.lstConsultas);
		
		lstConsultas = new ArrayList<Consulta>();
		
		DbHelperConsultas dbConsultas = new DbHelperConsultas(getActivity().getApplicationContext());
		lstConsultas = retornaConsultas(dbConsultas);
		if(lstConsultas == null){
			lstConsultas = new LinkedList<Consulta>();
		}
		// Associar o adapter ao listview

		/** cria o adapter e passa a list para ele */
		ConsultaAdapter adapter = new ConsultaAdapter(getActivity().getApplicationContext(), lstConsultas);

		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				final Consulta escolhida = lstConsultas.get(position);
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Paciente: " + escolhida.getPaciente().getNome());
				builder.setItems(R.array.opcoes_consulta_array, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,	int which) {
						selecionaOpcaoMenu(which, escolhida);
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
		
		GregorianCalendar gc = new GregorianCalendar(Locale.getDefault());
		String horario = "Dia: " + gc.get(GregorianCalendar.DAY_OF_MONTH) + " Mes: " + gc.get(GregorianCalendar.MONTH) + " Ano: " + gc.get(GregorianCalendar.YEAR) + " Hora: " + gc.get(GregorianCalendar.HOUR_OF_DAY) + " Minuto: " + gc.get(GregorianCalendar.MINUTE);

		return rootView;
	}
	
	public void selecionaOpcaoMenu(int which, Consulta escolhida) {
		switch (which) {
		//enviar sms em cascata
		case 0: {
			Intent smsIntent = new Intent(Intent.ACTION_VIEW);
			smsIntent.setType("vnd.android-dir/mms-sms");
			String telefones = "";
			DbHelperConsultas dbConsultas = new DbHelperConsultas(getActivity().getApplicationContext());
			lstConsultas = retornaConsultas(dbConsultas);
			if(lstConsultas == null){
				Log.i("Lista_Consultas_Fragment", "listaConsultas eh null");
				lstConsultas = new LinkedList<Consulta>();
			}
			for(int i=0; i<lstConsultas.size(); i++){
				telefones += lstConsultas.get(i).getPaciente().getTelefone();
				if(i!=lstConsultas.size()-1){
					telefones += ",";
				}
			}
			smsIntent.putExtra("address",  "" + telefones);
			Log.i("Lista_Consultas_Fragment", "telefones: " + telefones);
			smsIntent.putExtra("sms_body", "Dear Patient" + ",\nI'm late for our consultation today");
			startActivity(smsIntent);
			break;
		}
		//tracar rota
		case 1: {
			Intent navigationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + escolhida.getPaciente().getLogradouro() + ", " + escolhida.getPaciente().getNumero()));
			startActivity(navigationIntent);
			break;
		}
		//visualizar informacoes consulta agendada
		case 2: {
			Intent infoConsultaIntent = new Intent(this.getActivity().getApplicationContext(), VisualizaInfoConsultaAgendada.class);
			String horarioConsulta = escolhida.getHora().get(GregorianCalendar.HOUR_OF_DAY) + ":" + escolhida.getHora().get(GregorianCalendar.MINUTE);
			String enderecoConsulta = escolhida.getPaciente().getLogradouro() + " nº" + escolhida.getPaciente().getNumero() + " " + escolhida.getPaciente().getBairro();
			String dataConsulta = escolhida.getHora().get(GregorianCalendar.DAY_OF_MONTH) + "/" + escolhida.getHora().get(GregorianCalendar.MONTH) + "/" + escolhida.getHora().get(GregorianCalendar.YEAR);
			String[] informacoes = {escolhida.getPaciente().getNome(), horarioConsulta, dataConsulta, escolhida.getDescricao(), enderecoConsulta, escolhida.getTipo()}; 
			infoConsultaIntent.putExtra("informacoes", informacoes);
			infoConsultaIntent.putExtra("id_consulta", escolhida.getId());
			startActivity(infoConsultaIntent);
			break;
		}
		// realiza consulta
		case 3: {
			Intent realizarConsulta = new Intent(getActivity(), RealizarConsultaMain.class);
			realizarConsulta.putExtra("nome_paciente", escolhida.getPaciente().getNome());
			realizarConsulta.putExtra("id_consulta", escolhida.getId());
			realizarConsulta.putExtra("id_paciente", escolhida.getPaciente().getBd_id());
			startActivity(realizarConsulta);
			break;
		}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i("Lista_Consultas_Fragment", "chamou onActivityResult");
		if(data!=null){
			DbHelperConsultas dbConsultas = new DbHelperConsultas(getActivity());
			lstConsultas = retornaConsultas(dbConsultas);
			
			if (lstConsultas == null) {
				lstConsultas = new LinkedList<Consulta>();
			}
			
			ConsultaAdapter adapter = new ConsultaAdapter(getActivity(), lstConsultas);
			list.setAdapter(adapter);			
		}
	}
	
	abstract List<Consulta> retornaConsultas(DbHelperConsultas db);
	
}
