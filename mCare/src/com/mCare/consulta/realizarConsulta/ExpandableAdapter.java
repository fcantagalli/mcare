package com.mCare.consulta.realizarConsulta;

import java.util.List;
import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidplot.LineRegion;
import com.mCare.R;
import com.mCare.medicamento.Medicamento;

public class ExpandableAdapter extends BaseExpandableListAdapter {
	private Context ctx;
	private List<GroupEntity> lista;
	private List<Medicamento> listaMed;

	public ExpandableAdapter(Context ctx, List<GroupEntity> lista,
			List<Medicamento> listaMed) {
		// super();
		this.ctx = ctx;
		this.lista = lista;
		this.listaMed = listaMed;
		if (listaMed != null) {
			for (Medicamento m : listaMed) {
				lista.get(0).childSelected.put(m.getId(), true);
			}
		}
		
	}

	// interface
	public Object getChild(int groupPosition, int childPosition) {
		return lista.get(groupPosition).getListChild().get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final ViewChildHolder holder;
		final Medicamento child = lista.get(groupPosition).getListChild().get(childPosition);
		
		Log.i("fe","quais ele passa :::  "+child);

		/***
		 * VERIFICA natureza verdadeira do objeto que estava na lista. Se for
		 * medicamento ou diagnostico, eh um checkbox Se for exame, eh um
		 * edittext
		 ***/

		if (convertView == null) {
			convertView = LayoutInflater.from(ctx).inflate(R.layout.list_item_child, null);// carregando layout
			holder = new ViewChildHolder();
			//checkbox
			holder.check = (CheckBox) convertView.findViewById(R.id.medicineName);
			holder.check.setText(child.getNome());
			
			//spinner
			holder.days = (Spinner) convertView.findViewById(R.id.spinner1);
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(convertView.getContext(), R.array.many_days, android.R.layout.simple_spinner_dropdown_item);
			holder.days.setAdapter(adapter);
			
			//editText
			/*holder.hours = (Spinner) convertView.findViewById(R.id.spinner4);
			ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(convertView.getContext(), R.array.many_time, android.R.layout.simple_spinner_dropdown_item);
			holder.hours.setAdapter(adapter1);
			
			holder.inicio = (Spinner) convertView.findViewById(R.id.spinner2);
			ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(convertView.getContext(), R.array.inicial_time, android.R.layout.simple_spinner_dropdown_item);
			holder.inicio.setAdapter(adapter2);
			
			holder.inicio = (Spinner) convertView.findViewById(R.id.spinner3);
			ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(convertView.getContext(), R.array.am, android.R.layout.simple_spinner_dropdown_item);
			holder.inicio.setAdapter(adapter3);
			*/
			if (lista.get(groupPosition).childSelected.get(child.getId()) != null
					&& lista.get(groupPosition).childSelected.get(child.getId())) {
				holder.check.setChecked(true);
				
			}
			
			holder.id = child.getId();
			holder.check.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					CheckBox chk = (CheckBox) v;
					Log.d("Check", "Selecionado " + holder.check.getText()
							+ " Holder:" + holder);

					if (chk.isChecked()) {
						if (!lista.get(groupPosition).childSelected
								.containsKey(holder.id)){
							lista.get(groupPosition).childSelected.put(
									holder.id, true);
							//medsDetail.setVisibility(View.VISIBLE);
						}
							
					} else {
						if (lista.get(groupPosition).childSelected
								.containsKey(holder.id)){
							lista.get(groupPosition).childSelected
							.remove(holder.id);
							//medsDetail.setVisibility(View.INVISIBLE);
						}
					}
					holder.check.setChecked(chk.isChecked());
				}

			});

			// Para todos
			convertView.setTag(holder);

		} 
		
		//Log.i("med",((EditText) convertView.findViewById(R.id.editText2)).getText()+"");
		
		return convertView;
	}

	static class ViewChildHolder {
		int id;
		CheckBox check; // Para checkboxes
		Spinner days; // para days que tera que tomar
		Spinner hours; // para horarios que tera que tomar
		Spinner am;
		Spinner inicio;
	}

	public int getChildrenCount(int groupPosition) {
		return lista.get(groupPosition).getListChild().size();
	}

	public Object getGroup(int groupPosition) {
		return lista.get(groupPosition);
	}

	public int getGroupCount() {
		return lista.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ViewGroupHolder holder;
		GroupEntity grupo = lista.get(groupPosition);
		if (convertView == null) {
			convertView = LayoutInflater.from(ctx).inflate(
					R.layout.list_item_group, null);// carregando layout
			
			convertView.setBackgroundResource(R.color.teste);
			holder = new ViewGroupHolder();

			holder.txtDescricao = (TextView) convertView
					.findViewById(R.id.txt_item_group);
			convertView.setTag(holder);
			holder.txtDescricao.setText(grupo.getDescricao());
			
		} else {
			//holder = (ViewGroupHolder) convertView.getTag();
		}

		return convertView;
	}

	static class ViewGroupHolder {
		TextView txtDescricao;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
