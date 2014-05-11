package com.mCare.consulta.realizarConsulta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.database.DataSetObserver;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.spec.PSource;

import com.mCare.R;
import com.mCare.medicamento.Medicamento;

public class DebugSimpleExpandableListAdapter extends SimpleExpandableListAdapter {

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
		// Spinner dos days
		Spinner days = (Spinner) v.findViewById(R.id.spinner_days);
		ArrayAdapter<CharSequence> adapterDay = ArrayAdapter
				.createFromResource(context, R.array.many_days,
						android.R.layout.simple_spinner_dropdown_item);
		days.setAdapter(adapterDay);
		
		// Spinner das semanas
		Spinner weeks = (Spinner) v.findViewById(R.id.spinner_weeks);
		ArrayAdapter<CharSequence> adapterWeeks = ArrayAdapter
				.createFromResource(context, R.array.time_type,
						android.R.layout.simple_spinner_dropdown_item);
		weeks.setAdapter(adapterWeeks);
		
		// periodo em horas que sera tomado
		Spinner months = (Spinner) v.findViewById(R.id.spinner_period);
		ArrayAdapter<CharSequence> adapterPeriod = ArrayAdapter
				.createFromResource(context, R.array.time_period,
						android.R.layout.simple_spinner_dropdown_item);
		months.setAdapter(adapterPeriod);
		
		EditText recomme = (EditText) v.findViewById(R.id.text_recommendations);
		
		Spinner delay_period = (Spinner) v.findViewById(R.id.spinner_missing_delay);
		ArrayAdapter<CharSequence> adapterDelayPeriod = ArrayAdapter
				.createFromResource(context, R.array.missing_period,
						android.R.layout.simple_spinner_dropdown_item);
		delay_period.setAdapter(adapterDelayPeriod);
		
		Spinner delay_period_type = (Spinner) v.findViewById(R.id.spinner_missing_type);
		ArrayAdapter<CharSequence> adapterPeriodType = ArrayAdapter
				.createFromResource(context, R.array.missing_period_type,
						android.R.layout.simple_spinner_dropdown_item);
		delay_period_type.setAdapter(adapterPeriodType);
		
		EditText recommeDelay = (EditText) v.findViewById(R.id.text_recommendation_delay);
		
		TextView medHours = (TextView) v.findViewById(R.id.text_hours_medicine);
		
		//Button de mais para adicionar horas
		Button button = (Button) v.findViewById(R.id.button_plus_hours);
		button.setOnClickListener(new HoursClickListener(medHours));
		
		if (gp.getTipo() == "M") {
			Log.i("dddd","entrou para setar as variaveis");
			gp.treadManyTime[groupPosition] = days;
			gp.treadManyType[groupPosition] = weeks;
			gp.medFreq[groupPosition] = months;
			gp.medFreqTime[groupPosition] = medHours;
			gp.Recommendations[groupPosition] = recomme;
			gp.missDosePeriod[groupPosition] = delay_period;
			gp.missDoseType[groupPosition] = delay_period_type;
			gp.missDoseRecomm[groupPosition] = recommeDelay;
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
				rowCtr += /* listdesc[level1][i].length */6; // then add the
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

	private class HoursClickListener implements View.OnClickListener {

		private TextView textHours;
		private CharSequence[] horas = {"6:00","7:00","8:00","9:00","10:00","12:00","13:00","14:00","15:00", "16:00","18:00","20:00","21:00","22:00","23:00","24:00"};
		private boolean[] checked;
		private AlertDialog.Builder builder;

		public HoursClickListener(TextView textHours) {
			this.textHours = textHours;
			checked = new boolean[horas.length];
			builder = new AlertDialog.Builder(context);
		}

		@Override
		public void onClick(View v) {
			// Set a title
			builder.setTitle("Time to take the medicine");
			// Set a message
			
 			builder.setMultiChoiceItems(horas, checked,multiChoice);
			// - See more at:
			// http://www.javabeat.net/2013/05/alertdialog-box-android/#sthash.PW2DpGnC.dpuf

			builder.setPositiveButton("OK",clicker);
			// - See more at:
			// http://www.javabeat.net/2013/05/alertdialog-box-android/#sthash.PW2DpGnC.dpuf
			
			builder.setNeutralButton("Add more", clicker);
		
			builder.setNegativeButton("Cancel",clicker);

			// Create the dialog
			AlertDialog alertdialog = builder.create();

			// show the alertdialog
			alertdialog.show();
			// - See more at:
			// http://www.javabeat.net/2013/05/alertdialog-box-android/#sthash.PW2DpGnC.dpuf
		}
		
		OnMultiChoiceClickListener multiChoice = new OnMultiChoiceClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					checked[which] = true;
				}
				else{
					checked[which] = false;
				}
			}
		};
		
		DialogInterface.OnClickListener clicker = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Log.i("check","em qual esta indo "+ which);
				if(which == -1){
					
					String text = "";
					for(int i = 0; i < checked.length; i++){
						if(checked[i]){
							text+= horas[i]+" ,";
						}
					}
					
					text = text.substring(0, text.length()-1);
					
					textHours.setText(text);
					// Displaying a toast message
					Toast.makeText(context,
							"Your text has been modified",
							Toast.LENGTH_LONG).show();

					return;
				}else
				if(which == -3){
					AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
			        builder1.setTitle("Hello User");
			        builder1.setMessage("What is your name:");
			 
			         // Use an EditText view to get user input.
			         final EditText input = new EditText(context);
			         input.setId(0);
			         builder1.setView(input);
			 
			        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			 
			            @Override
			            public void onClick(DialogInterface dialog, int whichButton) {
			                String value = input.getText().toString();
			                Log.d(LOG_TAG, "Novo horario: " + value);
			                
			                // arrumando o array horas
			                HashMap<CharSequence, Boolean> keep = new HashMap<CharSequence, Boolean>();
			                for(int i = 0; i < horas.length; i++){
			                	keep.put(horas[i], checked[i]);
			                }
			                
			                horas = Arrays.copyOf(horas, horas.length+1);
			                horas[horas.length-1] = value;
			                
			                Arrays.sort(horas);
			                //arrumando o array checked
			                checked = Arrays.copyOf(checked, checked.length+1);
			                checked[checked.length-1] = false;
			                Boolean b;
			                for(int i = 0; i < horas.length ; i++){
			                	b = keep.get(horas[i]);
			                	if(b != null) checked[i] = keep.get(horas[i]);
			                	else checked[i] = false;
			                }
			                builder.setMultiChoiceItems(horas, checked, multiChoice);
							builder.create();
							builder.show();
			                return;
			            }
			        });
			 
			        builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			 
			            @Override
			            public void onClick(DialogInterface dialog, int which) {
			                dialog.cancel();
			            }
			        });
			 
			        builder1.create();
			        builder1.show();
				}else
				if(which == -2){
					dialog.cancel();
				}
			}
		};

	}

	private static final String LOG_TAG = "DebugSimpleExpandableListAdapter";
}
