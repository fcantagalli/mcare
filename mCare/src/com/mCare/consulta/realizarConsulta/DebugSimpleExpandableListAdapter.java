package com.mCare.consulta.realizarConsulta;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.PSource;

import com.mCare.R;
import com.mCare.medicamento.Medicamento;

public class DebugSimpleExpandableListAdapter extends
		SimpleExpandableListAdapter {

	private LayoutInflater inflater;
	private GroupEntity gp;
	private String[][][][] listdesc;
	private List<Medicamento> estaTomando;
	private Context context;

	public DebugSimpleExpandableListAdapter(String[][][][] listdesc,
			List<Medicamento> estaTomando, GroupEntity gp, Context context,
			List<? extends Map<String, ?>> groupData, int groupLayout,
			String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData,
			int childLayout, String[] childFrom, int[] childTo) {
		super(context, groupData, groupLayout, groupFrom, groupTo, childData,
				childLayout, childFrom, childTo);
		inflater = LayoutInflater.from(context);
		this.gp = gp;
		this.listdesc = listdesc;
		this.context = context;
		this.estaTomando = estaTomando;
	}

	/**
	 * Essa parte e responsaveis pelas linhas de conteudo de cada medicamento
	 */
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
		View v = super.getChildView(groupPosition, childPosition, isLastChild,
				convertView, parent);

		Spinner days = (Spinner) v.findViewById(R.id.spinner_days);
		ArrayAdapter<CharSequence> adapterDay = ArrayAdapter.createFromResource(
				context, R.array.many_days,
				android.R.layout.simple_spinner_dropdown_item);
		days.setAdapter(adapterDay);
		
		Spinner weeks = (Spinner) v.findViewById(R.id.spinner_weeks);
		ArrayAdapter<CharSequence> adapterWeeks = ArrayAdapter.createFromResource(
				context, R.array.many_weeks,
				android.R.layout.simple_spinner_dropdown_item);
		weeks.setAdapter(adapterWeeks);
		
		Spinner months = (Spinner) v.findViewById(R.id.spinner_months);
		ArrayAdapter<CharSequence> adapterMonths = ArrayAdapter.createFromResource(
				context, R.array.many_months,
				android.R.layout.simple_spinner_dropdown_item);
		months.setAdapter(adapterMonths);

		if (gp.listChild.get(groupPosition).getTipo() == "M") {
			gp.days[groupPosition] = days;
			// gp.hours[groupPosition] = hours;
		}
		Log.d(LOG_TAG, "getChildView: groupPosition: " + groupPosition
				+ "; childPosition: " + childPosition + "; v: " + v);
		return v;
	}

	/**
	 * essa parte é resposável por cada campo dos medicamentos. cada checkbox.
	 * As acoes dos checkbox devem ocorrer aqui.
	 * 
	 */
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View v = super.getGroupView(groupPosition, isExpanded, convertView,
				parent);

		CheckBox cb = (CheckBox) v.findViewById(R.id.medicineName);
		ExpandableListView expandableList = (ExpandableListView) parent;
		// final int gposition = groupPosition;

		Medicamento med = gp.listChild.get(groupPosition);

		if (gp.childSelected.get(med.getId()) != null
				&& gp.childSelected.get(med.getId())) {
			cb.setChecked(true);
		}

		cb.setOnCheckedChangeListener(new MeuCheckListener(
				(ExpandableListView) parent, groupPosition));
		if (!isExpanded) {
			if (cb.isChecked()) {
				Log.i(LOG_TAG, "Entrou no IsChecked   " + groupPosition);
				expandableList.expandGroup(groupPosition);
				if (!gp.childSelected.containsKey(med.getId())) {
					gp.childSelected.put(med.getId(), true);
				}
			} else {
				expandableList.collapseGroup(groupPosition);
				if (gp.childSelected.containsKey(med.getId())) {
					gp.childSelected.remove(med.getId());
				}
			}
			if (expandableList instanceof DebugExpandableListView) {
				DebugExpandableListView dev = (DebugExpandableListView) expandableList;
				dev.setRows(calculateRowCount(expandableList));
			}
			expandableList.requestLayout();
			// notifyDataSetChanged();
		}
		Log.d(LOG_TAG,
				"getGroupView: groupPosition: " + groupPosition
						+ "; isExpanded: "
						+ expandableList.isGroupExpanded(groupPosition)
						+ "; v: " + v);
		return v;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// used to make the notifyDataSetChanged() method work
		super.registerDataSetObserver(observer);
	}

	int calculateRowCount(ExpandableListView level2view) {
		int level2GroupCount = gp.listChild.size();
		Log.i("rowcount", "childSelected  " + level2GroupCount);
		int rowCtr = 0;
		for (int i = 0; i < level2GroupCount; ++i) {
			++rowCtr; // for the group row
			if ((level2view != null) && (level2view.isGroupExpanded(i))) {
				rowCtr += /* listdesc[level1][i].length */4; // then add the
															// children too
															// (minus the group
															// descriptor)
				Log.i("rowcount", "esta espandido  " + i);
			}

		}
		Log.i("rowcount", "numero de linhas : " + rowCtr);
		return rowCtr;
	}

	class MeuCheckListener implements CompoundButton.OnCheckedChangeListener {

		ExpandableListView expandableListView;
		int gposition;

		public MeuCheckListener(ExpandableListView ex, int gposition) {
			expandableListView = ex;
			this.gposition = gposition;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub

			Medicamento med = gp.listChild.get(gposition);
			if (!isChecked)
				expandableListView.collapseGroup(gposition);
			if (gp.childSelected.containsKey(med.getId())) {
				gp.childSelected.remove(med.getId());
			} else {
				expandableListView.expandGroup(gposition);
				if (!gp.childSelected.containsKey(med.getId())) {
					gp.childSelected.put(med.getId(), true);
				}
			}
			if (expandableListView instanceof DebugExpandableListView) {
				DebugExpandableListView dev = (DebugExpandableListView) expandableListView;
				dev.setRows(calculateRowCount(expandableListView));
			}

			Log.d(LOG_TAG, "onGroupClick");
			expandableListView.requestLayout();

		}

	}

	class HoursClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private static final String LOG_TAG = "DebugSimpleExpandableListAdapter";
}
