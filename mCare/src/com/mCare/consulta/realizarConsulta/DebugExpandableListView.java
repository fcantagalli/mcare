package com.mCare.consulta.realizarConsulta;

import com.mCare.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

public class DebugExpandableListView extends ExpandableListView {
	private static int ROW_HEIGHT;
    private static final String LOG_TAG = "DebugExpandableListView";
	private int rows;
	
    public DebugExpandableListView( Context context ) {
        super( context );
        ROW_HEIGHT = convertDipToPixels(35, context);
        this.setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
    }

	public void setRows( int rows ) {
		this.rows = rows;
		Log.d( LOG_TAG, "rows set: "+rows );
	}

    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure( widthMeasureSpec, heightMeasureSpec );
    	setMeasuredDimension( getMeasuredWidth(), rows*ROW_HEIGHT );
        Log.d( LOG_TAG, "onMeasure "+this+
                        ": width: "+decodeMeasureSpec( widthMeasureSpec )+
                        "; height: "+decodeMeasureSpec( heightMeasureSpec )+
                        "; measuredHeight: "+getMeasuredHeight()+
                        "; measuredWidth: "+getMeasuredWidth() );
    } 

    protected void onLayout (boolean changed, int left, int top, int right, int bottom) {
        super.onLayout( changed, left,top,right,bottom );
        Log.d( LOG_TAG,"onLayout "+this+": changed: "+changed+"; left: "+left+"; top: "+top+"; right: "+right+"; bottom: "+bottom );
    }
    
    private String decodeMeasureSpec( int measureSpec ) {
        int mode = View.MeasureSpec.getMode( measureSpec );
        String modeString = "<> ";
        switch( mode ) {
            case View.MeasureSpec.UNSPECIFIED:
                modeString = "UNSPECIFIED ";
                break;

            case View.MeasureSpec.EXACTLY:
                modeString = "EXACTLY ";
                break;

            case View.MeasureSpec.AT_MOST:
                modeString = "AT_MOST ";
                break;
        }
        return modeString+Integer.toString( View.MeasureSpec.getSize( measureSpec ) );
    }
    
    public static int convertDipToPixels(float dips, Context context)
    {
        return (int) (dips *  context.getResources().getDisplayMetrics().density + 0.5f);
    }
	
}

