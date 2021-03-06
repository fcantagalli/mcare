package com.mCare.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.Toast;

import com.mCare.R;
import com.mCare.db.Db;
import com.mCare.db.DbHelperPaciente;
import com.mCare.novocontato.NovoContato;
import com.mCare.paciente.InfPaciente;
import com.mCare.paciente.Paciente;

public class ListaPacientes_Fragment extends Fragment implements OnItemClickListener {

	ArrayList<Paciente> elements;
	ListView listViewPacientes;

	MyIndexerAdapter<Paciente> adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("ListaPacientes_Fragment", "fui chamado (OnCreateView)");
		View rootView = inflater.inflate(R.layout.activity_lista_pacientes,container, false);

		DbHelperPaciente db = new DbHelperPaciente(getActivity().getApplicationContext());

		elements = db.listaPacientes();
		

		if(elements== null){
			elements = new ArrayList<Paciente>();
		}
		
		listViewPacientes = (ListView) rootView.findViewById(R.id.listTelaPacientes);
		listViewPacientes.setOnItemClickListener(this);
		listViewPacientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,int index, long arg3) {

            	Paciente p = elements.get(index);
            	
            	Intent intent = new Intent(getActivity(),NovoContato.class);
            	intent.putExtra("id", p.getBd_id());
            	intent.putExtra("editar", true);
				startActivity(intent);
                return true;
            }
		}); 
		listViewPacientes.setFastScrollEnabled(true);
		adapter = new MyIndexerAdapter<Paciente>(
				getActivity(), android.R.layout.simple_list_item_1, elements);
		listViewPacientes.setAdapter(adapter);
		 
		ImageView adiciona = (ImageView) rootView.findViewById(R.id.AdicionaPaciente);
		
		adiciona.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),NovoContato.class);
				startActivityForResult(intent, 0);
			}
		});
		
		return rootView;
	}

	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		DbHelperPaciente db = new DbHelperPaciente(getActivity());

		elements = db.listaPacientes();
		
		Log.i("ListaPacientes_Fragment", "elements: " + elements + " listView: " + listViewPacientes);
		
		if(elements== null){
			elements = new ArrayList<Paciente>();
		}
		
		if(listViewPacientes!=null){
			adapter = new MyIndexerAdapter<Paciente>(getActivity(), android.R.layout.simple_list_item_1, elements);
			listViewPacientes.setAdapter(adapter);
		}else{
			Log.i("ListaPacientes_Fragment", "listViewPacientes eh null");
		}
	}


	public void updateList(Intent data) {
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		Paciente p = elements.get(arg2);
		
		Intent myIntent = new Intent(getActivity(), InfPaciente.class);
		myIntent.putExtra("ID", p.getBd_id());
		this.startActivity(myIntent);
	}

	class MyIndexerAdapter<T> extends ArrayAdapter<T> implements SectionIndexer {

		ArrayList<Paciente> myElements;
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

			return sections; // to string will be called each object, to display
			// the letter
		}

	}
	

}