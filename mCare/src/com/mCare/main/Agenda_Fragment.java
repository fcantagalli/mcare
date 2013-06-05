package com.mCare.main;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

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
import com.mCare.db.DbHelperConsultas;
import com.mCare.paciente.Paciente;

public class Agenda_Fragment extends Fragment {

	List<Consulta> lstConsultas;

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
		ListView list = (ListView) rootView.findViewById(R.id.lstConsultas);

		lstConsultas = new ArrayList<Consulta>();
		
		DbHelperConsultas dbConsultas = new DbHelperConsultas(getActivity().getApplicationContext());
		lstConsultas = dbConsultas.consultasDoDia();
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

		return rootView;
	}
	
	public void selecionaOpcaoMenu(int which, Consulta escolhida) {
		Log.i("phil", "which: " + which);
		
		//TODO FAZER ESCOLHER O TELEFONE DESEJADO NAS OPCOES DE LIGAR E ENVIAR MSG
		switch (which) {
		//telefonar
		case 0: {
			String uri = "tel:" + escolhida.getPaciente().getTelefone();
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse(uri));
			startActivity(callIntent);
			break;
		}
		//enviar sms
		case 1: {
			Intent smsIntent = new Intent(Intent.ACTION_VIEW);
			smsIntent.setType("vnd.android-dir/mms-sms");
			smsIntent.putExtra("address",  "" + escolhida.getPaciente().getTelefone());
			smsIntent.putExtra("sms_body", "Estou 30 minutos atrasado(a), aguarde-me!!");
			startActivity(smsIntent);
			break;
		}
		//tracar rota
		case 2: {
			Intent navigationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + escolhida.getPaciente().getLogradouro() + ", " + escolhida.getPaciente().getNumero()));
			startActivity(navigationIntent);
		}
		//visualizar informacoes consulta agendada
		case 3: {
			Intent infoConsultaIntent = new Intent(this.getActivity().getApplicationContext(), VisualizaInfoConsultaAgendada.class);
			String horarioConsulta = escolhida.getHora().get(GregorianCalendar.HOUR) + ":" + escolhida.getHora().get(GregorianCalendar.MINUTE);
			String enderecoConsulta = escolhida.getPaciente().getLogradouro() + " nº" + escolhida.getPaciente().getNumero() + " " + escolhida.getPaciente().getBairro();
			String dataConsulta = escolhida.getHora().get(GregorianCalendar.DAY_OF_MONTH) + "/" + escolhida.getHora().get(GregorianCalendar.MONTH) + "/" + escolhida.getHora().get(GregorianCalendar.YEAR);
			String[] informacoes = {escolhida.getPaciente().getNome(), horarioConsulta, dataConsulta, escolhida.getDescricao(), enderecoConsulta, escolhida.getTipo()}; 
			infoConsultaIntent.putExtra("informacoes", informacoes);
			startActivity(infoConsultaIntent);
		}
		case 4: {
			//TODO intent para realizar consulta
		}
		}
	}
	
	/*
	public String selecionaTelefone(Paciente paciente){
		final String[] telefones = new String[1];
		final String telefoneSelecionado = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Selecione o número:");
		builder.setItems(telefones, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,	int which) {
				telefoneSelecionado = telefones[which];
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
		
		return telefoneSelecionado;
	}*/

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		ListView listaConsultas = (ListView) getActivity().findViewById(R.id.lstConsultas);
		
		DbHelperConsultas dbConsultas = new DbHelperConsultas(getActivity().getApplicationContext());
		lstConsultas = dbConsultas.consultasDoDia();
		Log.i("phil", "tamanho da lstConsultas: " + lstConsultas.size());
		if(lstConsultas == null){
			lstConsultas = new LinkedList<Consulta>();
		}
		
		ConsultaAdapter adapter = new ConsultaAdapter(getActivity().getApplicationContext(), lstConsultas);
		listaConsultas.setAdapter(adapter);
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	

}
