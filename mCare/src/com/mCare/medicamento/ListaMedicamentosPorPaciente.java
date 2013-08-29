package com.mCare.medicamento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import android.content.Context;
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
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.mCare.R;
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

	Paciente p;
	
	ArrayList<Medicamento> elementsAtuais;
	ArrayList<Medicamento> elementsAnteriores;
	List<GroupEntity> listgrupo = null;
	GroupEntity grupo = null;
	GroupEntity grupo2 = null;
	ArrayList<Medicamento> estaTomando;
	static ExpandableListView exList;
	static ExpandableAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		
		View rootView = inflater.inflate(R.layout.activity_lista_medicamentos_por_paciente,container, false);
		
		final int id =  getActivity().getIntent().getExtras().getInt("id_paciente", -1);
		String nome_paciente = getActivity().getIntent().getExtras().getString("nome_paciente");
		
		Paciente p = new Paciente(id,nome_paciente);
		
		exList = (ExpandableListView) rootView.findViewById(R.id.expandableListView1); //CONTEUDO DA LISTA
		
		if(listgrupo == null) listgrupo = new ArrayList<GroupEntity>(); //CONTEM OS TOPICOS
		
		/**** DEFINE LISTA DE MEDICAMENTOS ****/
		if(grupo == null){
			grupo = new GroupEntity(1,"Medicamentos","D");
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
			listgrupo.add(grupo);
		}
		
		/**** DEFINE LISTA DE DIAGNOSTICOS ****/
		if(grupo2 == null){
			grupo2 = new GroupEntity(2,"Diagnosticos","A");
		
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
			listgrupo.add(grupo2);
		}

		adapter = new ExpandableAdapter(getActivity(),listgrupo,estaTomando);
		
 		exList.setAdapter(adapter);
		exList.expandGroup(0);
		exList.expandGroup(1);
		//exList.setBackgroundResource(R.color.WhiteSmoke);
		return rootView;
	}
	
	public boolean salvaDados(){
		
		/**** MEDICAMENTO_PACIENTE ****/
		GroupEntity c = (GroupEntity) adapter.getGroup(0);
		
		long id_consulta = getActivity().getIntent().getExtras().getLong("id_consulta");
		int id =  getActivity().getIntent().getExtras().getInt("id_paciente", -1);
		
		DbHelperMedicamento_Paciente dbm = new DbHelperMedicamento_Paciente(getActivity().getApplicationContext());
		
		for(Medicamento m : c.listChild){
			Boolean tem = c.childSelected.get(m.getId());
			
			if(tem != null && tem ){
				dbm.insereMedicamento_Paciente(m.getId(),id_consulta , id);
			}
		}
		
		/**** DIAGNOSTICO_CONSULTA ****/
		c = (GroupEntity) adapter.getGroup(1);
		
		DbHelperDiagnostico_Consulta dbd = new DbHelperDiagnostico_Consulta(getActivity().getApplicationContext());
		
		for (Medicamento m: c.listChild){
			Boolean tem = c.childSelected.get(m.getId());
			
			if(tem != null && tem ){
				dbd.insereDiagnostico_Consulta(m.getId(), id_consulta);
			}
		}
		
		return true;
	}


}
