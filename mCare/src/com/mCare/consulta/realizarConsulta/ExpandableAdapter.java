package com.mCare.consulta.realizarConsulta;

import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.mCare.R;
import com.mCare.medicamento.Medicamento;

public class ExpandableAdapter extends BaseExpandableListAdapter {
	private Context ctx;
	private List<GroupEntity> lista;

	public ExpandableAdapter(Context ctx, List<GroupEntity> lista,
			List<Medicamento> listaMed) {
		// super();
		this.ctx = ctx;
		this.lista = lista;
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

			holder.check = (CheckBox) convertView.findViewById(R.id.checkBox1);
			holder.check.setText(child.getNome());
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
								.containsKey(holder.id))
							lista.get(groupPosition).childSelected.put(
									holder.id, true);
					} else {
						if (lista.get(groupPosition).childSelected
								.containsKey(holder.id))
							lista.get(groupPosition).childSelected
									.remove(holder.id);
					}
					holder.check.setChecked(chk.isChecked());
				}

			});

			// Para todos
			convertView.setTag(holder);

		} else {
			//holder = (ViewChildHolder) convertView.getTag();
		}

		return convertView;
	}

	static class ViewChildHolder {
		int id;
		CheckBox check; // Para checkboxes
		EditText edit; // Para edit texts
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
			convertView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			convertView.setOnLongClickListener(new View.OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub
					return false;
				}
			});
			
			convertView.setBackgroundResource(R.color.DarkGray);
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
