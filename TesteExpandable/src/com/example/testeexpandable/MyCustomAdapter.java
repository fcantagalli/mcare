package com.example.testeexpandable;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MyCustomAdapter extends BaseExpandableListAdapter {
	
	private LayoutInflater inflater;
	private ArrayList<Parent> mParent;
	private Context context;
	
	public MyCustomAdapter(Context context, ArrayList<Parent> parent){
		mParent = parent;
		inflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return mParent.get(groupPosition).getMarrayChildren().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder = new ViewHolder();
		holder.childPosition = childPosition;
	    holder.groupPosition = groupPosition;
	 
	        if (convertView == null) {
	            convertView = inflater.inflate(R.layout.list_item_child, parent,false);
	            CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkBox1);
	            
	            checkbox.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						CheckBox c = (CheckBox) v;
						if(c.isChecked() ){
							mParent.get(holder.groupPosition).group_check_state.set(holder.childPosition, true);
						}else{
							mParent.get(holder.groupPosition).group_check_state.set(holder.childPosition, false);
						}
						
						String filename = "test.txt";
						File file = new File(context.getFilesDir(), filename);
						String string = "Sending Text by other modules";
						FileOutputStream outputStream;

						try {
						  outputStream = context.openFileOutput(filename, Context.MODE_WORLD_READABLE);
						  outputStream.write(string.getBytes());
						  outputStream.close();
						} catch (Exception e) {
						  e.printStackTrace();
						}
						File send = context.getFileStreamPath("test.txt");
						Intent sendIntent = new Intent();
						sendIntent.setAction(Intent.ACTION_SEND);
						sendIntent.putExtra(Intent.EXTRA_TEXT, "Aqui estão as recomendacoes do remedio. nao esqueça");
						sendIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"bihletti@gmail.com"});
						sendIntent.putExtra(Intent.EXTRA_SUBJECT, "XML medicine data");
						sendIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(send));
						sendIntent.setType("text/plain");
						context.startActivity(Intent.createChooser(sendIntent, context.getResources().getText(R.string.send_to)));
					}
				});
	            
	            Log.i("bih","tamanho mParent "+ mParent.size());
	            Log.i("bih","tamanho grupo "+ mParent.get(groupPosition).group_check_state);
	            
	            if(mParent.get(groupPosition).group_check_state.get(childPosition) == true){
	            	checkbox.setChecked(true);
	            }else{
	            	checkbox.setChecked(false);
	            }
				checkbox.setText(mParent.get(groupPosition).getMarrayChildren().get(childPosition));
	        }
	       
	        CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkBox1);
	        checkbox.setText(mParent.get(groupPosition).getMarrayChildren().get(childPosition));
			
	        convertView.setTag(holder);
	 
	        //return the entire view
		return convertView;
	}

	@Override
	public int getChildrenCount(int i) {
		// TODO Auto-generated method stub
		return mParent.get(i).getMarrayChildren().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return mParent.get(groupPosition).getTitle();
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return mParent.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		Log.i("bih","quando getGroupView e chamado   "+groupPosition);
		ViewHolder holder = new ViewHolder();
		holder.groupPosition = groupPosition;
		
		if(convertView == null){
			convertView = inflater.inflate(R.layout.list_item_parent, parent,false);
		}
		
		TextView textview = (TextView) convertView.findViewById(R.id.textView1);
		textview.setText(getGroup(groupPosition).toString());
		
		convertView.setTag(holder);
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	 @Override
	    public void registerDataSetObserver(DataSetObserver observer) {
	         //used to make the notifyDataSetChanged() method work 
	        //super.registerDataSetObserver(observer);
	    }
	 
	// Intentionally put on comment, if you need on click deactivate it
	/*  @Override
	    public void onClick(View view) {
	        ViewHolder holder = (ViewHolder)view.getTag();
	        if (view.getId() == holder.button.getId()){
	 
	           // DO YOUR ACTION
	        }
	    }*/
	
	protected class ViewHolder{
		protected int childPosition;
		protected int groupPosition;
		Button button;
	}
	
}
