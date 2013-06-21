package com.mCare.medicamento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.mCare.R;
import com.mCare.consulta.realizarConsulta.ExpandableAdapter;
import com.mCare.consulta.realizarConsulta.GroupEntity;
import com.mCare.db.DbHelperDiagnostico;
import com.mCare.db.DbHelperMedicamento;
import com.mCare.diagnostico.Diagnostico;
import com.mCare.paciente.Paciente;

public class ListaMedicamentosPorPaciente extends Fragment {

	Paciente p;
	
	ArrayList<Medicamento> elementsAtuais;
	ArrayList<Medicamento> elementsAnteriores;
	//ListView listViewMedicamentosAtuais;
	//ListView listViewMedicamentosAnteriores;
	//TextView tituloTelaMedicamentosPaciente;
	ExpandableListView exList;
	ExpandableAdapter adapter;
	//MyIndexerAdapter<Medicamento> adapterAtuais;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		
		View rootView = inflater.inflate(R.layout.activity_lista_medicamentos_por_paciente,container, false);
		
		final int id =  getActivity().getIntent().getExtras().getInt("id_paciente", -1);
		String nome_paciente = getActivity().getIntent().getExtras().getString("nome_paciente");
		
		Paciente p = new Paciente(id,nome_paciente);
		
		exList = (ExpandableListView) rootView.findViewById(R.id.expandableListView1);
		
		List<GroupEntity> listgrupo = new ArrayList<GroupEntity>();
		
		GroupEntity grupo = new GroupEntity(1,"Medicamentos","D");
		
		DbHelperMedicamento db = new DbHelperMedicamento(getActivity().getApplicationContext());
		ArrayList<Medicamento> estaTomando = db.listaMedicamentos(p);
		List<Medicamento> child = db.listaMedicamentos();  // provavelmente aqui vem a consulta no banco
		
		if(child== null){
			child = new ArrayList<Medicamento>(); //Se nao tem nenhum, cria lista vazia
		}
		
		GroupEntity grupo2 = new GroupEntity(1,"Diagnosticos","A");
		
		DbHelperDiagnostico dbd = new DbHelperDiagnostico(getActivity().getApplicationContext());
		ArrayList<Diagnostico> diagnosticos = dbd.listaDiagnosticos();
		
		//gambiarra
		if(diagnosticos == null){
			diagnosticos = new ArrayList<Diagnostico>();
		}
		ArrayList<Medicamento> md =new ArrayList<Medicamento>(diagnosticos.size());
		for (Diagnostico d: diagnosticos){
			md.add(new Medicamento(d.getId(),d.getNome()));
		}
		
		grupo2.setListChild(md);
		//PARTE DE TESTE
		//child.add(new Medicamento(1,"doril"));
		//child.add(new Medicamento(2,"buscopan"));
		
		grupo.setListChild(child);
		listgrupo.add(grupo);
		listgrupo.add(grupo2);
		adapter = new ExpandableAdapter(getActivity(),listgrupo,estaTomando);
 		exList.setAdapter(adapter);
 		
		// TODA A PARTE DE BAIXO E O CODIGO ANTIGO.
		/*
		//pega o paciente
		final int id =  getActivity().getIntent().getExtras().getInt("id_paciente", -1);
		String nome_paciente = getActivity().getIntent().getExtras().getString("nome_paciente");
		//DbHelperPaciente dbP = new DbHelperPaciente(getActivity());
		//p = dbP.buscaPaciente(id);
		Paciente p = new Paciente(id,nome_paciente);
		//MOSTRA TITULO DE ACORDO COM O PACIENTE
		tituloTelaMedicamentosPaciente.setText("Medicamentos do paciente "+ p.getNome());
		
		DbHelperMedicamento db = new DbHelperMedicamento(getActivity().getApplicationContext());

		elementsAtuais = db.listaMedicamentos(p); //Pega os medicamentos do banco e coloca em pacientes.medicamentos_atuais e pacientes_medicamentos_anteriores
		
		//-------MEDICAMENTOS ATUAIS-------
		//elementsAtuais = p.getMedicamentosAtuais();
		if(elementsAtuais== null){
			elementsAtuais = new ArrayList<Medicamento>(); //Se nao tem nenhum, cria lista vazia
		}

		//coloca a lista do banco no layout
	//	listViewMedicamentosAtuais = (ListView) rootView.findViewById(R.id.lstMedicamentosAtuais);
		listViewMedicamentosAtuais.setOnItemClickListener(new OnItemClickListener() {
			/*** Quando clica de forma rapida, visualiza o medicamento 
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
		adapterAtuais = new MyIndexerAdapter<Medicamento>(getActivity(), android.R.layout.simple_list_item_1, elementsAtuais);
		listViewMedicamentosAtuais.setAdapter(adapterAtuais);
		
		//-------MEDICAMENTOS ANTERIORES-------
		//elementsAnteriores = p.getMedicamentosAnteriores();
		//if(elementsAnteriores== null){
		//	elementsAnteriores = new LinkedList<Medicamento>(); //Se nao tem nenhum, cria lista vazia
		//}

		//coloca a lista do banco no layout
		/*listViewMedicamentosAnteriores = (ListView) rootView.findViewById(R.id.lstMedicamentosAnteriores);
		listViewMedicamentosAnteriores.setOnItemClickListener(new OnItemClickListener() {
			/*** Quando clica de forma rapida, visualiza o medicamento
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
		*/
		
		return rootView;
	}
	
	public boolean salvaDados(){
		
		GroupEntity c = (GroupEntity) adapter.getGroup(0);
		
		
		
		for(Medicamento m : c.listChild){
			Boolean tem = c.childSelected.get(m.getId());
			
			if(tem != null && tem ){
				
			}
		}
		
		return true;
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
