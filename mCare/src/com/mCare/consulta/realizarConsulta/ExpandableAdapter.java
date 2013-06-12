package com.mCare.consulta.realizarConsulta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mCare.R;
import com.mCare.medicamento.Medicamento;

public class ExpandableAdapter extends BaseExpandableListAdapter {
	private Context ctx;
	private List<GroupEntity> lista;
	private Map<Integer, Boolean> childSelected = new HashMap<Integer, Boolean>();

	public ExpandableAdapter(Context ctx, List<GroupEntity> lista) {
		super();
		this.ctx = ctx;
		this.lista = lista;
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
		final Medicamento child = lista.get(groupPosition).getListChild()
				.get(childPosition);

		if (convertView == null) {
			convertView = LayoutInflater.from(ctx).inflate(
					R.layout.list_item_child, null);// carregando layout
			holder = new ViewChildHolder();

			holder.check = (CheckBox) convertView.findViewById(R.id.checkBox1);
			holder.check.setText(child.getNome());
			holder.id = child.getId();
			holder.check.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					CheckBox chk = (CheckBox) v;
					Log.d("Check", "Selecionado " + holder.check.getText()
							+ " Holder:" + holder);

					if (chk.isChecked()) {
						if (!childSelected.containsKey(holder.id))
							childSelected.put(holder.id, true);
					} else {
						if (childSelected.containsKey(holder.id))
							childSelected.remove(holder.id);
					}
					holder.check.setChecked(chk.isChecked());
				}

			});
			convertView.setTag(holder);

		} else {
			holder = (ViewChildHolder) convertView.getTag();
		}
		holder.check.setText(child.getNome());
		holder.check.setChecked(childSelected.containsKey(holder.id));

		return convertView;
	}

	static class ViewChildHolder {
		int id;
		CheckBox check;
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
			convertView = LayoutInflater.from(ctx).inflate(R.layout.list_item_group, null);// carregando layout
			holder = new ViewGroupHolder();

			holder.txtDescricao = (TextView) convertView.findViewById(R.id.txt_item_group);
			convertView.setTag(holder);
		} else {
			holder = (ViewGroupHolder) convertView.getTag();
		}

		holder.txtDescricao.setText(grupo.getDescricao());

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
