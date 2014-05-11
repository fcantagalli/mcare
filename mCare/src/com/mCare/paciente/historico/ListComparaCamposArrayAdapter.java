package com.mCare.paciente.historico;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mCare.R;

public class ListComparaCamposArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;

	public ListComparaCamposArrayAdapter(Context context, String[] values) {
		super(context, R.layout.list_item_child_compara, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_item_child_compara, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.campoNome);
		textView.setText(values[position]);
		
		CheckBox cb = (CheckBox) rowView.findViewById(R.id.campoCheckBox);
		cb.setOnCheckedChangeListener(new MeuCheckListener(position));
		// Change the icon for Windows and iPhone

		return rowView;
	}
	
	class MeuCheckListener implements CompoundButton.OnCheckedChangeListener {

		int gposition;

		public MeuCheckListener(int gposition) {
			this.gposition = gposition;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				
			}else{
				
			}
		}

	}
	
}