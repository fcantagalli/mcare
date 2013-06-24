package com.mCare.consulta.realizarConsulta;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mCare.R;
import com.mCare.db.DbHelperExame;
import com.mCare.db.DbHelperResultado_Exame;
import com.mCare.exame.Exame;
import com.mCare.main.Utils;

public class Exame_Fragment extends Fragment {

	private ListView lstExame;
	private ExameAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_exame__fragment,container, false);

		lstExame = (ListView) rootView.findViewById(R.id.listView1);

		DbHelperExame dbdb = new DbHelperExame(getActivity().getApplicationContext());
		LinkedList<Exame> exames = dbdb.listaExames();

		if (exames == null) {

			exames = new LinkedList<Exame>();
		}
		
		adapter = new ExameAdapter(getActivity().getApplicationContext(), exames);

		lstExame.setAdapter(adapter);
		
		return rootView;
	}
	
	
	public void salvaExames(long id_consulta){
		DbHelperResultado_Exame db = new DbHelperResultado_Exame(getActivity());
		if(adapter != null){
			for(int i= 0 ; i < adapter.listExames.size() ; i++){
				adapter.listExames.get(i).setValor(adapter.textViews.get(i).getText().toString());
			}
			
			for(Exame e : adapter.listExames){
				if(e.getValor() != null){
					db.insereResultado_Exame(e.getId(), id_consulta , e.getNome(), e.getValor());
				}
			}
		}
	}

	class ExameAdapter extends BaseAdapter {

		List<Exame> listExames;
		List<EditText> textViews;

		/** Classe utilizada para instanciar os objetos do XML **/
		private LayoutInflater inflater;

		public ExameAdapter(Context context, List<Exame> plistExames) {
			this.listExames = plistExames;
			textViews = new ArrayList<EditText>(plistExames.size());
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		/**
		 * final no parametro garante que nao mudemos o objeto item nesse metodo
		 **/
		void addItem(final Exame item) {
			this.listExames.add(item);
			/** Atualiza a lista caso seja adicionado **/
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return listExames.size();
		}

		@Override
		public Object getItem(int arg0) {
			return listExames.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup viewGroup) {

			/** pega o registro da lista **/
			final Exame con = listExames.get(position);

			if (convertView == null) {
				/** utiliza o XML row_consulta para exibir na tela */
				convertView = inflater.inflate(R.layout.row_exame, null);

				/** instancia os objetos do XML **/
				EditText campo = (EditText) convertView.findViewById(R.id.editText1);
				TextView nome = (TextView) convertView.findViewById(R.id.textView1);
				
				nome.setText(con.getNome());
				switch(con.getTipoResultadoExame()){
					case 0: campo.setInputType(InputType.TYPE_CLASS_NUMBER);
							break;
					case 1: campo.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
							break;
					case 2: campo.setInputType(InputType.TYPE_CLASS_TEXT);
							break;
					case 3: campo.setInputType(InputType.TYPE_CLASS_DATETIME);
							break;
					default: campo.setInputType(InputType.TYPE_CLASS_TEXT);
							break;
				}
				
				textViews.add(campo);
			}
			return convertView;
		}
		
	}
}
