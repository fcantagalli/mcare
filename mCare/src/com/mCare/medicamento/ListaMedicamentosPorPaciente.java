package com.mCare.medicamento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import android.R.drawable;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.Spinner;
import android.widget.TextView;

import com.mCare.R;
import com.mCare.consulta.realizarConsulta.ColorExpListAdapter;
import com.mCare.consulta.realizarConsulta.ExpandableAdapter;
import com.mCare.consulta.realizarConsulta.GroupEntity;
import com.mCare.db.DbHelperDiagnostico;
import com.mCare.db.DbHelperDiagnostico_Consulta;
import com.mCare.db.DbHelperExame;
import com.mCare.db.DbHelperMedicamento;
import com.mCare.db.DbHelperMedicamento_Paciente;
import com.mCare.db.DbHelperResultado_Exame;
import com.mCare.diagnostico.Diagnostico;
import com.mCare.exame.Exame;
import com.mCare.paciente.Paciente;

public class ListaMedicamentosPorPaciente extends Fragment {

	String listdesc[][][][];/* = 
		{
	        { // Medicamentos
	          {  // aspirina
	            { "Medicines", "aspirina" },
	            { "campos","#D3D3D3" },
	            { "campos","#D3D3D3" }
			    
	          },
	          {  // ass
	            { "Medicines", "Ass" },
			    { "campos","#EAEAEA" },
			    { "campos","#D3D3D3" }
	          },
	          {
	        	  {"Medicines","alguma"},
	        	  {"campo","#EAEAEA"},
	        	  { "campos","#D3D3D3" }}
	        },
	        { // diagnosticos
	          {  // lightblue
	            { "diagnosticos", "asma" },
	          },
	          {  // darkblue
	            { "diagnosticos", "tuberculose" },
	          }
	        }
		};
	*/
	Paciente p;
	
	ArrayList<Medicamento> elementsAtuais;
	ArrayList<Medicamento> elementsAnteriores;
	List<GroupEntity> listgrupo = null;
	GroupEntity grupo = null;
	GroupEntity grupo2 = null;
	ArrayList<Medicamento> estaTomando;
	
	private ExpandableListView exList;
	private ColorExpListAdapter colorExpListAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.activity_lista_medicamentos_por_paciente,container, false);
		
		final int id =  getActivity().getIntent().getExtras().getInt("id_paciente", -1);
		String nome_paciente = getActivity().getIntent().getExtras().getString("nome_paciente");
		
		Paciente p = new Paciente(id,nome_paciente);
		exList = (ExpandableListView) rootView.findViewById(R.id.expandableListView1); //CONTEUDO DA LISTA		
		
		if(listgrupo == null) listgrupo = new ArrayList<GroupEntity>(2); //CONTEM OS TOPICOS
		
		/**** DEFINE LISTA DE MEDICAMENTOS ****/
		if(grupo == null){
			grupo = new GroupEntity(1,"Medicines","M");
			DbHelperMedicamento db = new DbHelperMedicamento(getActivity().getApplicationContext());
			estaTomando = db.listaMedicamentos(p);
		
		//favoritos primeiro - ainda nao esta pegando favoritos
			List<Medicamento> medicamentos = db.listaMedicamentos();
		// tratamente de erro caso nao haja nenhum medicamento
			if(medicamentos == null){
				medicamentos = new ArrayList<Medicamento>();
			}
		
			Log.i("fe","Child::::"+medicamentos);
			Log.i("fe",""+estaTomando);
			
			/**** ADICIONA CONTEUDO AS LISTAS ****/
			grupo.setListChild(medicamentos);
			/** adiciona o topico medicamento*/
			listgrupo.add(0,grupo);
		}
		
		/**** DEFINE LISTA DE DIAGNOSTICOS ****/
		if(grupo2 == null){
			grupo2 = new GroupEntity(2,"Diagnosis","D");
		
			DbHelperDiagnostico dbd = new DbHelperDiagnostico(getActivity().getApplicationContext());
			ArrayList<Diagnostico> diagnosticos = dbd.listaDiagnosticos();
		
			/****/
			//tratamente de erro caso nao haja nenhum diagnostico
			if(diagnosticos == null){
				diagnosticos = new ArrayList<Diagnostico>();
			}
		
			ArrayList<Medicamento> childrenDiagnosticos =new ArrayList<Medicamento>(diagnosticos.size()); //SAO NA VERDADE DIAGNOSTICOS
			for (Diagnostico d: diagnosticos){
				childrenDiagnosticos.add(new Medicamento(d.getId(),d.getNome(),"Diagnostico"));		//SAO NA VERDADE DIAGNOSTICOS
			}

			Log.i("fe","Children diag ::::"+childrenDiagnosticos);
			/**** ADICIONA CONTEUDO AS LISTAS ****/
			grupo2.setListChild(childrenDiagnosticos);
			/** adiciona o topico diagnostico*/
			listgrupo.add(1,grupo2);
		}

		listdesc = transformaEmMatriz(grupo.listChild, grupo2.listChild);
		colorExpListAdapter = new ColorExpListAdapter(listgrupo,estaTomando,getActivity(),exList,listdesc);
		exList.setAdapter(colorExpListAdapter);
		return rootView;
	}
	
	public boolean salvaDados(){
		
		/**** MEDICAMENTO_PACIENTE ****/
		GroupEntity c = listgrupo.get(0);
		Log.i("dddd","entrou no salvaDados");
		long id_consulta = getActivity().getIntent().getExtras().getLong("id_consulta");
		int id =  getActivity().getIntent().getExtras().getInt("id_paciente", -1);
		String data = getActivity().getIntent().getExtras().getString("data_consulta"); 
		
		DbHelperMedicamento_Paciente dbm = new DbHelperMedicamento_Paciente(getActivity().getApplicationContext());
		
		for(int i = 0; i < c.listChild.size() ; i++){
			Boolean tem = c.childSelected.get(c.listChild.get(i).getId());

			if(tem != null && tem ){
				Log.i("dddd","vai chamar o metodo para inserir");
				dbm.insereMedicamento_Paciente(
				(long) c.listChild.get(i).getId(),
				id_consulta , 
				id,
				(String) c.getTreadManyTime()[i].getSelectedItem(),
				(String) c.getTreadManyType()[i].getSelectedItem(),
				(String) c.getMedFreq()[i].getSelectedItem(),
				(String) c.getMedFreqTime()[i].getText().toString(),
				c.getRecommendations()[i].getText().toString(),
				(String) c.getMissDosePeriod()[i].getSelectedItem(),
				(String) c.getMissDoseType()[i].getSelectedItem(), 
				c.getMissDoseRecomm()[i].getText().toString());
			}
		}
		
		/**** DIAGNOSTICO_CONSULTA ****/
		c = (GroupEntity) listgrupo.get(1);
		
		DbHelperDiagnostico_Consulta dbd = new DbHelperDiagnostico_Consulta(getActivity().getApplicationContext());
		
		for (Medicamento m: c.listChild){
			Boolean tem = c.childSelected.get(m.getId());
			
			if(tem != null && tem ){
				dbd.insereDiagnostico_Consulta(m.getId(), id_consulta);
			}
		}
		
		return true;
	}
	
	int SpinnerToNumberOfDays(Spinner s){
		String data = (String) s.getSelectedItem();
		int days = 0;
		
		if( data == "How Many Days"){
			days = 0; // explicit
		}
		String[] aux = data.split(" ");
		if(aux[1] == "Days"){
			days = Integer.parseInt(aux[0]);
		}
		else if (aux[1] == "Months"){
			days = Integer.parseInt(aux[0])*30;
		}
			
		return days;
	}
	
	String[][][][] transformaEmMatriz(List<Medicamento> meds, List<Medicamento> diag){
		
		String[][][][] matriz = new String[2][][][];
		String[][][] m;
		String[][][] d;
		if(meds.size() > 0){
			m = new String[meds.size()][2][2]; // array para os medicamentos
		}
		else{
			m = new String[1][1][1];
			m[0][0][0] = "Medicines"; 
		}
		if(diag.size() > 0 ){
			d = new String[diag.size()][2][2]; // array para os diagnosticos
		}
		else{
			d = new String[1][1][1];
			d[0][0][0] = "Diagnosis";
		}
		for(int i = 0; i< meds.size(); i++){
			m[i][0][0] = "Medicines";
			m[i][0][1] = meds.get(i).getNome();
			
			m[i][1][0] = "campos";
			m[i][1][1] = "#EAEAEA";

		}

		for(int i = 0; i< diag.size(); i++){
			d[i][0][0] = "Medicines";
			d[i][0][1] = diag.get(i).getNome();
			
			d[i][1][0] = "campos";
			d[i][1][1] = "#EAEAEA";

		}
		matriz[0] =m;
		matriz[1] = d;
		
		return matriz;
	}
}
