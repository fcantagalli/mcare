package com.mCare.diagnostico;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SectionIndexer;
import com.mCare.R;
import com.mCare.db.DbHelperDiagnostico;

public class ListaDiagnosticos extends Fragment {

	LinkedList<Diagnostico> elements;
	ListView listViewDiagnosticos;
	MyIndexerAdapter<Diagnostico> adapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View rootView = inflater.inflate(R.layout.activity_lista_diagnosticos,container, false);
		
		//AO CLICAR EM UM DIAGNOSTICO -> NAO FAZ NADA (o diagnostico eh soh o nome...) -> nao usa o visualizar_diagnostico
		DbHelperDiagnostico db = new DbHelperDiagnostico(getActivity()
				.getApplicationContext());
		
		elements = db.listaDiagnosticos(); //Pega os diagnosticos do banco
		
		if(elements== null){
			elements = new LinkedList<Diagnostico>(); //Se nao tem nenhum, cria lista vazia
		}

		//coloca a lista do banco no layout
		listViewDiagnosticos = (ListView) rootView.findViewById(R.id.lstDiagnosticos);
		
		listViewDiagnosticos.setFastScrollEnabled(true);
		adapter = new MyIndexerAdapter<Diagnostico>(
				getActivity(), android.R.layout.simple_list_item_1, elements);
		listViewDiagnosticos.setAdapter(adapter);
		
		
		
		//NOVO DIAGNOSTICO -> vai pra cadastrar diagnostico
		ImageView novoDiagnostico = (ImageView) rootView.findViewById(R.id.imageViewCadastrarDiagnostico);
		novoDiagnostico.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), CadastrarDiagnostico.class);
				startActivityForResult(intent, 0);
			}
		});
		
		
		return rootView;
	}
	
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(data == null){
			return;
		}
		Diagnostico diagnostico = new Diagnostico( (int) data.getExtras().getLong("id"), (String)data.getExtras().getString("nome"));
		Log.i("inf","informacoes do diagnostico cadastrado: "+ "nome : "+diagnostico.getNome()+"    Id : "+diagnostico.getId());
		elements.add(diagnostico);

		//Ordena diagnosticos por nome
		Collections.sort(elements, new Comparator<Diagnostico>() {
	         @Override
	         public int compare(Diagnostico o1, Diagnostico o2) {
	             return Collator.getInstance().compare(o1.getNome(), o2.getNome());
	         }
	     });
		
		listViewDiagnosticos.setFastScrollEnabled(true);
		adapter = new MyIndexerAdapter<Diagnostico>(getActivity(), android.R.layout.simple_list_item_1, elements);
		listViewDiagnosticos.setAdapter(adapter);

	}



	class MyIndexerAdapter<T> extends ArrayAdapter<T> implements SectionIndexer {

		ArrayList<Diagnostico> myElements;
		HashMap<String, Integer> alphaIndexer;

		String[] sections;

		public MyIndexerAdapter(Context context, int textViewResourceId,
				List<T> objects) {
			super(context, textViewResourceId, objects);
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

}
