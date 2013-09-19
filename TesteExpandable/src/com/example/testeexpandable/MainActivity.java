package com.example.testeexpandable;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class MainActivity extends Activity {
	private ExpandableListView mExpandableList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mExpandableList = (ExpandableListView) findViewById(R.id.expandableListView1);
		
		ArrayList<Parent> arrayParents = new ArrayList<Parent>();
		ArrayList<String> arrayChildren = new ArrayList<String>();
		
		//here we set the parents and the children
        for (int i = 0; i < 2; i++){
            //for each "i" create a new Parent object to set the title and the children
            Parent parent = new Parent();
            parent.setnTitle("Parent " + i);
            Log.i("bih","creating parent "+i);
            arrayChildren = new ArrayList<String>();
            for (int j = 0; j < 5; j++) {
                arrayChildren.add("Child " + j);
                Log.e("bih","creating child "+j);
            }
            parent.setMarrayChildren(arrayChildren);
            parent.setStates();
            //in this array we add the Parent object. We will use the arrayParents at the setAdapter
            arrayParents.add(parent);
        }
        
        //sets the adapter that provides data to the list.
        mExpandableList.setAdapter(new MyCustomAdapter(MainActivity.this,arrayParents));
        
        mExpandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
				Log.d("bih","entrou no OnGroupExpand");
			}
		});
        
        mExpandableList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
			
			@Override
			public void onGroupCollapse(int groupPosition) {
				// TODO Auto-generated method stub
				Log.d("bih","entrou no OnGroupCollapse");
			}
		});
        
        
        mExpandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				Log.d("bih","entrou no on Group Click");
				if(parent.isGroupExpanded(groupPosition)){
					parent.collapseGroup(groupPosition);
				}
				else{
					parent.expandGroup(groupPosition);
				}
				return true;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
