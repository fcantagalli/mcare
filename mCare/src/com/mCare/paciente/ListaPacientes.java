package com.mCare.paciente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.mCare.R;
import com.mCare.R.layout;
import com.mCare.R.menu;
import com.mCare.db.DbHelperPaciente;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

public class ListaPacientes extends Fragment implements OnItemClickListener{

	ArrayList<Paciente> elements;
	ListView listViewPacientes;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.activity_lista_pacientes, container,false);
				
		DbHelperPaciente db = new DbHelperPaciente(getActivity().getApplicationContext());
				
				elements = db.listaPacientes();
				
				//OBS: esse trecho abaixo foi usado para teste, ele gera 300 Pacientes com nomes aleatorios
				//e adiciona na lista elements
				/*String s = "QWERTZUIOPASDFGHJKLYXCVBNM";
		        Random r = new Random();
		        for (int i = 0; i < 300; i++) {

		                elements.add(new Paciente(1,s.substring(r.nextInt(s.length()))));

		        }*/
				
		         //Collections.sort(elements); // Must be sorted! // ja retorna ordenado do bd

		         // listview
		         listViewPacientes = (ListView) getActivity().findViewById(R.id.listTelaPacientes);
		         listViewPacientes.setFastScrollEnabled(true);
		         MyIndexerAdapter<Paciente> adapter = new MyIndexerAdapter<Paciente>(getActivity(), android.R.layout.simple_list_item_1,elements);
		         listViewPacientes.setAdapter(adapter);
		         
		         return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		Paciente p = elements.get(arg2);
		
		Intent myIntent = new Intent(getActivity(), InfPaciente.class);
		myIntent.putExtra("ID",p.getBd_id());
		this.startActivity(myIntent);
		
	}

	class MyIndexerAdapter<T> extends ArrayAdapter<T> implements SectionIndexer {
		 
        ArrayList<Paciente> myElements;
        HashMap<String, Integer> alphaIndexer;

        String[] sections;

        public MyIndexerAdapter(Context context, int textViewResourceId,
                        List<T> objects) {
                super(context, textViewResourceId, objects);
               // myElements =  (ArrayList<Paciente>) objects;
                // here is the tricky stuff
                alphaIndexer = new HashMap<String, Integer>();
                // in this hashmap we will store here the positions for
                // the sections

                int size = elements.size();
                for (int i = size - 1; i >= 0; i--) {
                        String element = elements.get(i).getNome();
                        alphaIndexer.put(element.substring(0, 1), i);
                //We store the first letter of the word, and its index.
                //The Hashmap will replace the value for identical keys are putted in
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

                return sections; // to string will be called each object, to display
                // the letter
        }

}



}