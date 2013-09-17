package com.mCare.consulta.realizarConsulta;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView.OnChildClickListener;
import android.util.Log;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import com.mCare.R;
import com.mCare.medicamento.Medicamento;

public class ColorExpListAdapter extends BaseExpandableListAdapter {
	
    private Context context;
    private String listdesc[][][][];
    private LayoutInflater inflater;
    private List<GroupEntity> listGroup;
    private List<Medicamento> estaTomando;
    private ExpandableListView topExpList;
	static private DebugExpandableListView listViewCache[];
    static final String KEY_COLORNAME = "colorName";
    static final String KEY_SHADENAME = "shadeName";
    private static final String KEY_RGB = "rgb";
    private static final String LOG_TAG = "ColorExpListAdapter";

    public ColorExpListAdapter(List<GroupEntity> listGroup, List<Medicamento> estaTomando,Context context,ExpandableListView topExpList,String listdesc[][][][] ) {
    	this.estaTomando = estaTomando;
    	this.listGroup = listGroup;
        this.context = context;
        this.topExpList = topExpList;
        this.listdesc = listdesc;
        inflater = LayoutInflater.from( context );
		listViewCache = new DebugExpandableListView[listdesc.length];
        
    }

    public Object getChild(int groupPosition, int childPosition) {
        return listdesc[groupPosition][childPosition];
    }

    public long getChildId(int groupPosition, int childPosition) {
        return (long)( groupPosition*1024+childPosition );  // Max 1024 children per group
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Log.d( LOG_TAG, "getChildView: groupPositon: "+groupPosition+"; childPosition: "+childPosition );
		View v = null;
		if( listViewCache[groupPosition] != null ){
			Log.i(LOG_TAG, "estava no cache do listView");
            v = listViewCache[groupPosition];
			//v = convertView;
		}
        else {
            DebugExpandableListView dev = new DebugExpandableListView( context );
            dev.setGroupIndicator(null);
            dev.setItemsCanFocus(true);
			List group = createGroupList( groupPosition );
			Log.d("list","GROUPPP  "+group);
			List child = createChildList( groupPosition );
			dev.setRows(calculateRowCount( groupPosition, null)); // nao sei porque menos 1, mas esta funcionando
           	dev.setAdapter( 
			        new DebugSimpleExpandableListAdapter(
			        	listdesc,
			        	estaTomando,
			        	listGroup.get(groupPosition),
				        context,
				        group,	// groupData describes the first-level entries
				        R.layout.list_item_child,	// Layout for the first-level entries
				        new String[] { KEY_COLORNAME },	// Key in the groupData maps to display
				        new int[] { R.id.medicineName },		// Data under "colorName" key goes into this TextView
				        child,	// childData describes second-level entries
				        R.layout.list_sub_child,	// Layout for second-level entries
				        new String[] { },    // Keys in childData maps to display
				        new int[] { }	// Data under the keys above go into these TextViews
                    )
           	);
            dev.setOnGroupClickListener( new Level2GroupExpandListener( groupPosition ) );
			listViewCache[groupPosition] = dev;
			v = dev;
		}
        return v;
    }

    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    public Object getGroup(int groupPosition) {
        return listdesc[groupPosition][0][0][0];        
    }

    public int getGroupCount() {
        return listdesc.length;
    }

    public long getGroupId(int groupPosition) {
        return (long)( groupPosition*1024 );  // To be consistent with getChildId
    } 

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Log.d( LOG_TAG, "getGroupView: groupPositon: "+groupPosition+"; isExpanded: "+isExpanded );
        View v = null;
        if( convertView != null )
            v = convertView;
        else
            v = inflater.inflate(R.layout.list_item_group, parent, false);
        
        String gt = (String) getGroup( groupPosition );
		TextView colorGroup = (TextView)v.findViewById( R.id.txt_item_group );
		if( gt != null )
			colorGroup.setText( gt );
        return v;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    } 

    public void onGroupCollapsed (int groupPosition) {} 
    public void onGroupExpanded(int groupPosition) {}

/**
  * Creates a level2 group list out of the listdesc array according to
  * the structure required by SimpleExpandableListAdapter. The resulting
  * List contains Maps. Each Map contains one entry with key "colorName" and
  * value of an entry in the listdesc array.
  * @param level1 Index of the level1 group whose level2 subgroups are listed.
  */
	// array de medicamentos ou diagnosticos
	private List createGroupList( int level1 ) {
        ArrayList result = new ArrayList();
        if(listdesc[level1][0][0].length <= 1){
        	return result;
        }else{
		    for( int i = 0 ; i < listdesc[level1].length ; ++i ) {
		        HashMap m = new HashMap();
		        m.put( KEY_COLORNAME,listdesc[level1][i][0][1] );
		    	result.add( m );
		    }
        }
	    return (List)result;
    }

/**
  * Creates the child list out of the listdesc array according to the
  * structure required by SimpleExpandableListAdapter. The resulting List
  * contains one list for each group. Each such second-level group contains
  * Maps. Each such Map contains two keys: "shadeName" is the name of the
  * shade and "rgb" is the RGB value for the shade.
  * @param level1 Index of the level1 group whose level2 subgroups are included in the child list.
  */
	
	// arrayList de arrayList para armazenar os valores internos da segunda expandable list
	// no meu caso seria uma view ou uma lista de objetos contendo cada referência de cada campo da view
	// instanciado para recuperar o conteúdo.
    private List createChildList( int level1 ) {
	    ArrayList result = new ArrayList();
	    for( int i = 0 ; i < listdesc[level1].length ; ++i ) {
// Second-level lists
	        ArrayList secList = new ArrayList();
	        for( int n = 1 ; n < listdesc[level1][i].length ; ++n ) {
	            HashMap child = new HashMap();
		        child.put( KEY_SHADENAME, listdesc[level1][i][n][0] );
	            child.put( KEY_RGB, listdesc[level1][i][n][1] );
		        secList.add( child );
	        }
	        result.add( secList );
	    }
	    return result;
    }

// Calculates the row count for a level1 expandable list adapter. Each level2 group counts 1 row (group row) plus any child row that
// belongs to the group
   /* int calculateRowCount( int level1, ExpandableListView level2view ) {
        int level2GroupCount = listdesc[level1].length;
        int rowCtr = 0;
        for( int i = 0 ; i < level2GroupCount ; ++i ) {
            ++rowCtr;       // for the group row
			if( ( level2view != null ) && ( level2view.isGroupExpanded( i ) ) ){
				rowCtr += listdesc[level1][i].length;	// then add the children too (minus the group descriptor)
				Log.i("rowcount","esta espandido  "+i);
			}
				
        }
        Log.i("rowcount","numero de linhas : "+rowCtr);
		return rowCtr;
    }*/

    int calculateRowCount( int level1, ExpandableListView level2view ) {
        int level2GroupCount = listdesc[level1].length;
        int rowCtr = 0;
        for( int i = 0 ; i < level2GroupCount ; ++i ) {
            ++rowCtr;       // for the group row
			if( ( level2view != null ) && ( level2view.isGroupExpanded( i ) ) ){
				rowCtr += /*listdesc[level1][i].length*/4;	// then add the children too (minus the group descriptor)
				Log.i("rowcount","esta espandido  "+i);
			}
				
        }
        Log.i("rowcount","numero de linhas : "+rowCtr);
		return rowCtr;
    }

	class Level2GroupExpandListener implements ExpandableListView.OnGroupClickListener {
		
		private int level1GroupPosition;
		
		public Level2GroupExpandListener( int level1GroupPosition ) {
			this.level1GroupPosition = level1GroupPosition;
		}

       	public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
       		Log.i("bih","entrou no onGroupClick");
       		if( parent.isGroupExpanded( groupPosition ) )
            	parent.collapseGroup( groupPosition );
        	else
           		parent.expandGroup( groupPosition );
			if( parent instanceof DebugExpandableListView ) {
				DebugExpandableListView dev = (DebugExpandableListView)parent;
				dev.setRows( calculateRowCount( level1GroupPosition, parent ));
			}
           	Log.d( LOG_TAG, "onGroupClick" );
           	topExpList.requestLayout();
          	return true;
     	}
	}
}
