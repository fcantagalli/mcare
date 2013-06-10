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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.mCare.R;
import com.mCare.db.DbHelperMedicamento;

public class ListaMedicamentos extends Activity {

	LinkedList<Medicamento> elementsFavoritos;
	LinkedList<Medicamento> elementsNaoFavoritos;
	ListView listViewMedicamentosFavoritos;
	ListView listViewMedicamentosNaoFavoritos;

	MyIndexerAdapter<Medicamento> adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_medicamentos);
		
		//AO CLICAR EM UM MEDICAMENTO -> vai pra visualizar medicamento
		DbHelperMedicamento db = new DbHelperMedicamento(getApplicationContext());
		//MEDICAMENTOS FAVORITOS
		elementsFavoritos = db.listaMedicamentos(true); //Pega os medicamentos FAVORITOS do banco
		
		if(elementsFavoritos== null){
			elementsFavoritos = new LinkedList<Medicamento>(); //Se nao tem nenhum, cria lista vazia
		}

		//coloca a lista do banco no layout
		listViewMedicamentosFavoritos = (ListView) findViewById(R.id.lstMedicamentosFavoritos);
		listViewMedicamentosFavoritos.setOnItemClickListener(new OnItemClickListener() {

			/*** Quando clica de forma rapida, visualiza o medicamento ***/
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				Medicamento m = elementsFavoritos.get(arg2);
				
				Intent myIntent = new Intent(getApplicationContext(), VisualizarMedicamento.class);
				myIntent.putExtra("ID", m.getId());
				getApplicationContext().startActivity(myIntent);
				
				Toast.makeText(getApplicationContext(),"Voce clicou em:" +elementsFavoritos.get(arg2).toString(), Toast.LENGTH_LONG).show();
			}
		});
		listViewMedicamentosFavoritos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			/*** Quando clica de forma devagar, edita o medicamento ***/
            public boolean onItemLongClick(AdapterView<?> arg0, View v,int index, long arg3) {

            	Medicamento m = elementsFavoritos.get(index);
            	
            	Intent intent = new Intent(getApplicationContext(),EditarMedicamento.class);
            	intent.putExtra("id", m.getId());
				startActivity(intent);
				
                Toast.makeText(getApplicationContext(),"Voce selecionou o medicamento :" +elementsFavoritos.get(index).toString(), Toast.LENGTH_LONG).show();
                return true;
            }
		}); 
		
		listViewMedicamentosFavoritos.setFastScrollEnabled(true);
		adapter = new MyIndexerAdapter<Medicamento>(
				getApplicationContext(), android.R.layout.simple_list_item_1, elementsFavoritos);
		listViewMedicamentosFavoritos.setAdapter(adapter);
		
		
		
		//MEDICAMENTOS NAO-FAVORITOS
		elementsNaoFavoritos = db.listaMedicamentos(false); //Pega os medicamentos NAO-FAVORITOS do banco
		
		if(elementsNaoFavoritos== null){
			elementsNaoFavoritos = new LinkedList<Medicamento>(); //Se nao tem nenhum, cria lista vazia
		}

		//coloca a lista do banco no layout
		listViewMedicamentosNaoFavoritos = (ListView) findViewById(R.id.lstMedicamentosNaoFavoritos);
		listViewMedicamentosFavoritos.setOnItemClickListener(new OnItemClickListener() {
			/*** Quando clica de forma rapida, visualiza o medicamento ***/
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				Medicamento m = elementsNaoFavoritos.get(arg2);
				
				Intent myIntent = new Intent(getApplicationContext(), VisualizarMedicamento.class);
				myIntent.putExtra("ID", m.getId());
				getApplicationContext().startActivity(myIntent);
				
				Toast.makeText(getApplicationContext(),"Voce clicou em:" +elementsNaoFavoritos.get(arg2).toString(), Toast.LENGTH_LONG).show();
			}
		});
		listViewMedicamentosNaoFavoritos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			/*** Quando clica de forma devagar, edita o medicamento ***/
            public boolean onItemLongClick(AdapterView<?> arg0, View v,int index, long arg3) {

            	Medicamento m = elementsNaoFavoritos.get(index);
            	
            	//Cria uma intencao de fazer alguma coisa -> abrir a tela cadastrar medicamento
            	Intent intent = new Intent(getApplicationContext(),EditarMedicamento.class);
            	intent.putExtra("id", m.getId()); //manda o id do medicamento para a tela cadastrar medicamento
				startActivity(intent);
				
                Toast.makeText(getApplicationContext(),"Voce selecionou o medicamento :" +elementsNaoFavoritos.get(index).toString(), Toast.LENGTH_LONG).show();
                return true;
            }
		}); 
		
		listViewMedicamentosNaoFavoritos.setFastScrollEnabled(true);
		adapter = new MyIndexerAdapter<Medicamento>(
				getApplicationContext(), android.R.layout.simple_list_item_1, elementsNaoFavoritos);
		listViewMedicamentosNaoFavoritos.setAdapter(adapter);
		
		 
		
		
		
		//NOVO MEDICAMENTO -> vai pra cadastrar medicamento
		ImageView novoMedicamento = (ImageView) findViewById(R.id.imageViewCadastrarMedicamento);
		novoMedicamento.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), CadastrarMedicamento.class);
				startActivityForResult(intent, 0);
			}
		});
	}
	
	
	
	//Se alguma das telas que eu chamei retornar alguma coisa, eu faco o seguinte
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		//Se a tela nao me mandar dados, nao faco nada
		if(data == null){
			return;
		}
		
		//Se a tela me mandar informacoes do medicamento criado, ADICIONO A LISTA
		Medicamento medicamento = new Medicamento(  (int) data.getExtras().getLong("id"), 
													(String)data.getExtras().getString("nome"));
		Log.i("inf","informacoes do medicamento cadastrado: "+ "nome : "+medicamento.getNome()+"    Id : "+medicamento.getId());
		
		//Se for um dos favoritos
		if (medicamento.getFavorito()) {
			elementsFavoritos.add(medicamento);

			//Ordena medicamentos por nome
			Collections.sort(elementsFavoritos, new Comparator<Medicamento>() {
		         @Override
		         public int compare(Medicamento o1, Medicamento o2) {
		             return Collator.getInstance().compare(o1.getNome(), o2.getNome());
		         }
		     });
			
			//Medicamentos favoritos
			listViewMedicamentosFavoritos.setFastScrollEnabled(true);
			adapter = new MyIndexerAdapter<Medicamento>(getApplicationContext(), android.R.layout.simple_list_item_1, elementsFavoritos);
			listViewMedicamentosFavoritos.setAdapter(adapter);
		}
		//Nao favorito
		else
		{
			elementsNaoFavoritos.add(medicamento);

			//Ordena medicamentos por nome
			Collections.sort(elementsNaoFavoritos, new Comparator<Medicamento>() {
		         @Override
		         public int compare(Medicamento o1, Medicamento o2) {
		             return Collator.getInstance().compare(o1.getNome(), o2.getNome());
		         }
		     });
			
			//Medicamentos nao-favoritos
			listViewMedicamentosNaoFavoritos.setFastScrollEnabled(true);
			adapter = new MyIndexerAdapter<Medicamento>(getApplicationContext(), android.R.layout.simple_list_item_1, elementsNaoFavoritos);
			listViewMedicamentosNaoFavoritos.setAdapter(adapter);
		}
	}

	//ic_btn_speak_now
	//ic_menu_camera
	//ic_menu_gallery
	//ic_menu_slideshow


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
