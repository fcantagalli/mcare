package com.mCare.medicamento;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.mCare.R;
import com.mCare.R.layout;
import com.mCare.R.menu;
import com.mCare.db.DbHelperMedicamento;
import com.mCare.db.DbHelperPaciente;
import com.mCare.medicamento.ListaMedicamentos.MyIndexerAdapter;
import com.mCare.paciente.Paciente;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ListaMedicamentosPorPaciente extends Fragment {

	Paciente p;
	
	LinkedList<Medicamento> elementsAtuais;
	LinkedList<Medicamento> elementsAnteriores;
	ListView listViewMedicamentosAtuais;
	ListView listViewMedicamentosAnteriores;
	TextView tituloTelaMedicamentosPaciente;

	MyIndexerAdapter<Medicamento> adapterAtuais;
	MyIndexerAdapter<Medicamento> adapterAnteriores;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View rootView = inflater.inflate(R.layout.activity_lista_medicamentos_por_paciente,container, false);
		
		//pega o paciente
		final int id =  getActivity().getIntent().getExtras().getInt("id_paciente", -1);
		DbHelperPaciente dbP = new DbHelperPaciente(getActivity());
		p = dbP.buscaPaciente(id);
		
		//MOSTRA TITULO DE ACORDO COM O PACIENTE
		tituloTelaMedicamentosPaciente = (TextView) rootView.findViewById(R.id.tituloTelaMedicamentosPaciente);
		tituloTelaMedicamentosPaciente.setText("Medicamentos do paciente "+ p.getNome());
		
		
		//AO CLICAR EM UM MEDICAMENTO -> vai pra visualizar medicamento
		DbHelperMedicamento db = new DbHelperMedicamento(getActivity()
				.getApplicationContext());

		db.listaMedicamentos(p); //Pega os medicamentos do banco e coloca em pacientes.medicamentos_atuais e pacientes_medicamentos_anteriores
		
		//-------MEDICAMENTOS ATUAIS-------
		elementsAtuais = p.getMedicamentosAtuais();
		if(elementsAtuais== null){
			elementsAtuais = new LinkedList<Medicamento>(); //Se nao tem nenhum, cria lista vazia
		}

		//coloca a lista do banco no layout
		listViewMedicamentosAtuais = (ListView) rootView.findViewById(R.id.lstMedicamentosAtuais);
		listViewMedicamentosAtuais.setOnItemClickListener(new OnItemClickListener() {
			/*** Quando clica de forma rapida, visualiza o medicamento ***/
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				Medicamento m = elementsAtuais.get(arg2);
				
				Intent myIntent = new Intent(getActivity(), VisualizarMedicamento.class);
				myIntent.putExtra("ID", m.getId());
				getActivity().startActivity(myIntent);
				
				Toast.makeText(getActivity(),"Voce clicou em:" +elementsAtuais.get(arg2).toString(), Toast.LENGTH_LONG).show();
			}
		});
		
		listViewMedicamentosAtuais.setFastScrollEnabled(true);
		adapterAtuais = new MyIndexerAdapter<Medicamento>(
				getActivity(), android.R.layout.simple_list_item_1, elementsAtuais);
		listViewMedicamentosAtuais.setAdapter(adapterAtuais);
		
		
		//-------MEDICAMENTOS ANTERIORES-------
		elementsAnteriores = p.getMedicamentosAnteriores();
		if(elementsAnteriores== null){
			elementsAnteriores = new LinkedList<Medicamento>(); //Se nao tem nenhum, cria lista vazia
		}

		//coloca a lista do banco no layout
		listViewMedicamentosAnteriores = (ListView) rootView.findViewById(R.id.lstMedicamentosAnteriores);
		listViewMedicamentosAnteriores.setOnItemClickListener(new OnItemClickListener() {
			/*** Quando clica de forma rapida, visualiza o medicamento ***/
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				Medicamento m = elementsAnteriores.get(arg2);
				
				Intent myIntent = new Intent(getActivity(), VisualizarMedicamento.class);
				myIntent.putExtra("ID", m.getId());
				getActivity().startActivity(myIntent);
				
				Toast.makeText(getActivity(),"Voce clicou em:" +elementsAnteriores.get(arg2).toString(), Toast.LENGTH_LONG).show();
			}
		});
		
		listViewMedicamentosAnteriores.setFastScrollEnabled(true);
		adapterAnteriores = new MyIndexerAdapter<Medicamento>(
				getActivity(), android.R.layout.simple_list_item_1, elementsAnteriores);
		listViewMedicamentosAnteriores.setAdapter(adapterAnteriores);

		
		return rootView;
	}
	
	


	class MyIndexerAdapter<T> extends ArrayAdapter<T> implements SectionIndexer {

		ArrayList<Medicamento> myElements;
		HashMap<String, Integer> alphaIndexer;

		String[] sections;

		public MyIndexerAdapter(Context context, int textViewResourceId,
				List<T> objects) {
			super(context, textViewResourceId, objects);
			myElements = (ArrayList<Medicamento>) objects;
			// here is the tricky stuff
			alphaIndexer = new HashMap<String, Integer>();
			// in this hashmap we will store here the positions for
			// the sections
				
			int size = myElements.size();
			for (int i = size - 1; i >= 0; i--) {
				String element = myElements.get(i).getNome();
				alphaIndexer.put(element.substring(0, 1), i);
				// We store the first letter of the word, and its index.
				// The Hashmap will replace the value for identical keys are
				// putted in
			}

			// now we have an hashmap containing for each first-letter
			// sections(key), the index(value) in where this sections begins
			// we have now to build the sections(letters to be displayed)
			// array .it must contains the keys, and must (I do so...) be
			// ordered alphabetically

			Set<String> keys = alphaIndexer.keySet(); // set of letters ...sets
			// cannot be sorted...

			Iterator<String> it = keys.iterator();
			ArrayList<String> keyList = new ArrayList<String>(); // list can be
			// sorted

			while (it.hasNext()) {
				String key = it.next();
				keyList.add(key);
			}

			Collections.sort(keyList);

			sections = new String[keyList.size()]; // simple conversion to an
			// array of object
			keyList.toArray(sections);

			// ooOO00K !

		}

		@Override
		public int getPositionForSection(int section) {
			// Log.v("getPositionForSection", ""+section);
			String letter = sections[section];

			return alphaIndexer.get(letter);
		}

		@Override
		public int getSectionForPosition(int position) {
			// you will notice it will be never called (right?)
			Log.v("getSectionForPosition", "called");
			return 0;
		}

		@Override
		public Object[] getSections() {
			return sections; // to string will be called each object, to display the letter
		}

	}


}
