package com.mCare.medicamento;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.mCare.R;
import com.mCare.db.Db;
import com.mCare.db.DbHelperMedicamento;
import com.mCare.novocontato.NovoContato;

public class ListaMedicamentos extends Fragment implements OnItemClickListener {

	ArrayList<Medicamento> elements;
	ListView listViewMedicamentos;

	MyIndexerAdapter<Medicamento> adapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View rootView = inflater.inflate(R.layout.activity_lista_medicamentos,container, false);
		
		
		//AO CLICAR EM UM MEDICAMENTO -> vai pra visualizar medicamento
		DbHelperMedicamento db = new DbHelperMedicamento(getActivity()
				.getApplicationContext());

		elements = db.listaMedicamentos(); //Pega os medicamentos do banco
		
		if(elements== null){
			elements = new ArrayList<Medicamento>(); //Se nao tem nenhum, cria lista vazia
		}

		//coloca a lista do banco no layout
		listViewMedicamentos = (ListView) rootView.findViewById(R.id.lstMedicamentos);
		listViewMedicamentos.setOnItemClickListener(this);
		listViewMedicamentos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			//Se clicar em algum
            public boolean onItemLongClick(AdapterView<?> arg0, View v,int index, long arg3) {

            	Medicamento m = elements.get(index);
            	
            	Intent intent = new Intent(getActivity(),CadastrarMedicamento.class);
            	intent.putExtra("id", m.getBd_id());
            	intent.putExtra("editar", true);
				startActivity(intent);
				
                Toast.makeText(getActivity(),"Voce selecionou o medicamento :" +elements.get(index).toString(), Toast.LENGTH_LONG).show();
                return true;
            }
		}); 
		
		listViewMedicamentos.setFastScrollEnabled(true);
		adapter = new MyIndexerAdapter<Medicamento>(
				getActivity(), android.R.layout.simple_list_item_1, elements);
		listViewMedicamentos.setAdapter(adapter);
		 
		
		
		
		//NOVO MEDICAMENTO -> vai pra cadastrar medicamento
		ImageView novoMedicamento = (ImageView) findViewById(R.id.imageViewCadastrarMedicamento);
		novoMedicamento.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),CadastrarMedicamento.class);
				startActivityForResult(intent, 0);
			}
		});
		
		
		return rootView;
	}

}
