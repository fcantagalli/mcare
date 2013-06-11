package com.mCare.exame;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SectionIndexer;

import com.mCare.R;
import com.mCare.db.DbHelperExame;

public class ListaExames extends Activity {

	LinkedList<Exame> elements;
	ListView listViewExames;
	MyIndexerAdapter<Exame> adapter;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_exames);
		
		DbHelperExame db = new DbHelperExame(getApplicationContext());
		
		elements = db.listaExames(); //Pega os exames do banco
		
		if(elements== null){
			elements = new LinkedList<Exame>(); //Se nao tem nenhum, cria lista vazia
		}

		//coloca a lista do banco no layout
		
		listViewExames = (ListView) findViewById(R.id.lstExames);
		listViewExames.setFastScrollEnabled(true);
		adapter = new MyIndexerAdapter<Exame>(this, android.R.layout.simple_list_item_1, elements);
		listViewExames.setAdapter(adapter);

		//NOVO EXAME -> vai pra cadastrar exame
		ImageView novoExame = (ImageView) findViewById(R.id.imageViewCadastrarExame);
		novoExame.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CadastrarExame.class);
				startActivityForResult(intent, 0);
			}
		});
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if(data.getExtras() == Bundle.EMPTY){
			return;
		}
		Exame exame = new Exame( (int) data.getExtras().getLong("id"), (String)data.getExtras().getString("nome"), 
				(int) data.getExtras().getInt("tipo"));
		Log.i("inf","informacoes do exame cadastrado: "+ "nome : "+exame.getNome()+"    Id : "+exame.getId());
		elements.add(exame);

		//Ordena exames por nome
		Collections.sort(elements, new Comparator<Exame>() {
	         @Override
	         public int compare(Exame o1, Exame o2) {
	             return Collator.getInstance().compare(o1.getNome(), o2.getNome());
	         }
	     });
		
		listViewExames.setFastScrollEnabled(true);
		adapter = new MyIndexerAdapter<Exame>(this, android.R.layout.simple_list_item_1, elements);
		listViewExames.setAdapter(adapter);

	}



	class MyIndexerAdapter<T> extends ArrayAdapter<T> implements SectionIndexer {

		ArrayList<String> myElements;
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
