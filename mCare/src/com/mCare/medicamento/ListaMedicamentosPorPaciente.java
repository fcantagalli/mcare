/*package com.mCare.medicamento;

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
import com.mCare.medicamento.ListaMedicamentos.MyIndexerAdapter;
import com.mCare.paciente.Paciente;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
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

public class ListaMedicamentosPorPaciente extends Fragment implements OnItemClickListener {

	Paciente p; /*********************TEM QUE PEGAR O PACIENTE!!! Como? =( 
	
	LinkedList<Medicamento> elements;
	ListView listViewMedicamentosAtuais;
	ListView listViewMedicamentosAnteriores;
	TextView tituloTelaMedicamentosPaciente;

	MyIndexerAdapter<Medicamento> adapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View rootView = inflater.inflate(R.layout.activity_lista_medicamentos_por_paciente,container, false);
		
		//MOSTRA TITULO DE ACORDO COM O PACIENTE
		tituloTelaMedicamentosPaciente = (TextView) rootView.findViewById(R.id.tituloTelaMedicamentosPaciente);
		tituloTelaMedicamentosPaciente.setText("Medicamentos do paciente "+p.getNome());
		
		
		//AO CLICAR EM UM MEDICAMENTO -> vai pra visualizar medicamento
		DbHelperMedicamento db = new DbHelperMedicamento(getActivity()
				.getApplicationContext());

		db.listaMedicamentos(p); //Pega os medicamentos do banco e coloca em pacientes.medicamentos_atuais e pacientes_medicamentos_anteriores
		
		//-------MEDICAMENTOS ATUAIS-------
		elements = p.getMedicamentosAtuais();
		if(elements== null){
			elements = new LinkedList<Medicamento>(); //Se nao tem nenhum, cria lista vazia
		}

		//coloca a lista do banco no layout
		listViewMedicamentosAtuais = (ListView) rootView.findViewById(R.id.lstMedicamentosAtuais);
		listViewMedicamentosAtuais.setOnItemClickListener(this);
		listViewMedicamentosAtuais.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			//Se clicar em algum
            public boolean onItemLongClick(AdapterView<?> arg0, View v,int index, long arg3) {

            	Medicamento m = elements.get(index);
            	
            	Intent intent = new Intent(getActivity(),CadastrarMedicamento.class);
            	intent.putExtra("id", m.getId());
            	intent.putExtra("editar", true);
				startActivity(intent);
				
                Toast.makeText(getActivity(),"Voce selecionou o medicamento :" +elements.get(index).toString(), Toast.LENGTH_LONG).show();
                return true;
            }
		}); 
		
		listViewMedicamentosAtuais.setFastScrollEnabled(true);
		adapter = new MyIndexerAdapter<Medicamento>(
				getActivity(), android.R.layout.simple_list_item_1, elements);
		listViewMedicamentosAtuais.setAdapter(adapter);
		
		
		//-------MEDICAMENTOS ANTERIORES-------
		/************** OBSERVACAO DA GABI *****************
		 * NÃO SEI DIREITO PRA QUE SERVE O ELEMENTS!
		 * Acho que esta errado, pq usa o mesmo elements pros atuais e anteriores, mas tem dois "onItemLongClick" etc... :/
		 * *************************************************
		 */
		elements = p.getMedicamentosAnteriores();
		if(elements== null){
			elements = new LinkedList<Medicamento>(); //Se nao tem nenhum, cria lista vazia
		}

		//coloca a lista do banco no layout
		listViewMedicamentosAnteriores = (ListView) rootView.findViewById(R.id.lstMedicamentosAnteriores);
		listViewMedicamentosAnteriores.setOnItemClickListener(this);
		listViewMedicamentosAnteriores.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			//Se clicar em algum
            public boolean onItemLongClick(AdapterView<?> arg0, View v,int index, long arg3) {

            	Medicamento m = elements.get(index);
            	
            	Intent intent = new Intent(getActivity(),CadastrarMedicamento.class);
            	intent.putExtra("id", m.getId());
            	intent.putExtra("editar", true);
				startActivity(intent);
				
                Toast.makeText(getActivity(),"Voce selecionou o medicamento :" +elements.get(index).toString(), Toast.LENGTH_LONG).show();
                return true;
            }
		}); 
		
		listViewMedicamentosAnteriores.setFastScrollEnabled(true);
		adapter = new MyIndexerAdapter<Medicamento>(
				getActivity(), android.R.layout.simple_list_item_1, elements);
		listViewMedicamentosAnteriores.setAdapter(adapter);

		
		return rootView;
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(data == null){
			return;
		}
		Medicamento medicamento = new Medicamento( (int) data.getExtras().getLong("id"), (String)data.getExtras().getString("nome"));
		Log.i("inf","informacoes do medicamento cadastrado: "+ "nome : "+medicamento.getNome()+"    Id : "+medicamento.getId());
		elements.add(medicamento);
		
		//Ordena medicamentos por nome
		Collections.sort(elements, new Comparator<Medicamento>() {
	         @Override
	         public int compare(Medicamento o1, Medicamento o2) {
	             return Collator.getInstance().compare(o1.getNome(), o2.getNome());
	         }
	     });
		
		listViewMedicamentosAnteriores.setFastScrollEnabled(true);
		adapter = new MyIndexerAdapter<Medicamento>(getActivity(), android.R.layout.simple_list_item_1, elements);
		listViewMedicamentosAnteriores.setAdapter(adapter);

	}

	//ic_btn_speak_now
	//ic_menu_camera
	//ic_menu_gallery
	//ic_menu_slideshow

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Medicamento m = elements.get(arg2);
		
		Intent myIntent = new Intent(getActivity(), VisualizarMedicamento.class);
		myIntent.putExtra("ID", m.getId());
		this.startActivity(myIntent);
		
		Toast.makeText(getActivity(),"Voce clicou em:" +elements.get(arg2).toString(), Toast.LENGTH_LONG).show();
	}

	class MyIndexerAdapter<T> extends ArrayAdapter<T> implements SectionIndexer {

		ArrayList<Medicamento> myElements;
		HashMap<String, Integer> alphaIndexer;

		String[] sections;

		public MyIndexerAdapter(Context context, int textViewResourceId,
				List<T> objects) {
			super(context, textViewResourceId, objects);
			// myElements = (ArrayList<Medicamento>) objects;
			// here is the tricky stuff
			alphaIndexer = new HashMap<String, Integer>();
			// in this hashmap we will store here the positions for
			// the sections
				
			int size = elements.size();
			for (int i = size - 1; i >= 0; i--) {
				String element = elements.get(i).getNome();
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


}*/
