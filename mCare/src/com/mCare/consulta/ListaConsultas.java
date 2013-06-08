package com.mCare.consulta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

import com.mCare.R;
import com.mCare.consulta.realizarConsulta.Visualiza_Consulta_Realizada;
import com.mCare.db.DbHelperConsultasRealizadas;
import com.mCare.paciente.Paciente;

public class ListaConsultas extends Activity implements OnItemClickListener{

	ArrayList<Consulta> elements;
	ListView listViewConsulta;

	MyIndexerAdapter<Consulta> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_consultas_paciente);
	
		DbHelperConsultasRealizadas db = new DbHelperConsultasRealizadas(getApplicationContext());
		Log.i("fe","id do paciente : "+getIntent().getExtras().getInt("id"));
		elements = db.listaConsultasDoPaciente(getIntent().getExtras().getInt("id"));
		

		if(elements == null){
			Log.i("fe","Nenhuma consula encontrada");
			elements = new ArrayList<Consulta>();
		}

		//elements = new ArrayList<Paciente>();
		
		// OBS: esse trecho abaixo foi usado para teste, ele gera 300 Pacientes
		// com nomes aleatorios
		// e adiciona na lista elements
		
		  /*String s = "QWERTZUIOPASDFGHJKLYXCVBNM"; Random r = new Random(); for
		  (int i = 0; i < 300; i++) {
		  
		  elements.add(new Consulta(1,s.substring(r.nextInt(s.length()))));
		  
		  }
		 

		 Collections.sort(elements); // Must be sorted! // ja retorna ordenado
		*/
		// listview
		
		listViewConsulta = (ListView) findViewById(R.id.listView1);
		listViewConsulta.setOnItemClickListener(this);
		listViewConsulta.setFastScrollEnabled(true);
		adapter = new MyIndexerAdapter<Consulta>(this, android.R.layout.simple_list_item_1, elements);
		listViewConsulta.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_consultas, menu);
		return true;
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		Consulta c = elements.get(arg2);
		
		Intent myIntent = new Intent(getApplicationContext(), Visualiza_Consulta_Realizada.class);
		myIntent.putExtra("id_consulta", c.getId());
		this.startActivity(myIntent);
	}

	class MyIndexerAdapter<T> extends ArrayAdapter<T> implements SectionIndexer {

		ArrayList<Paciente> myElements;
		HashMap<String, Integer> alphaIndexer;

		String[] sections;

		public MyIndexerAdapter(Context context, int textViewResourceId,
				List<T> objects) {
			super(context, textViewResourceId, objects);
			// myElements = (ArrayList<Paciente>) objects;
			// here is the tricky stuff
			alphaIndexer = new HashMap<String, Integer>();
			// in this hashmap we will store here the positions for
			// the sections
				
			int size = elements.size();
			for (int i = size - 1; i >= 0; i--) {
				GregorianCalendar gc = elements.get(i).getHora();
				String element = gc.get(gc.DAY_OF_WEEK)+"/"+gc.get(gc.MONTH)+"/"+gc.get(gc.YEAR)+"  às "+gc.get(gc.HOUR_OF_DAY)+" : "+gc.get(gc.MINUTE);
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

			return sections; // to string will be called each object, to display
			// the letter
		}

	}
	
}
