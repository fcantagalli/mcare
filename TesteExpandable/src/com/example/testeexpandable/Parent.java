package com.example.testeexpandable;

import java.util.ArrayList;

import android.util.Log;

public class Parent {

	private String nTitle;
	private ArrayList<String> marrayChildren;
	ArrayList<Boolean> group_check_state = new ArrayList<Boolean>();
	
	public void setStates(){
		Log.d("bih","tamanho do marray"+marrayChildren.size());
		for(int i = 0; i < marrayChildren.size(); i++){
			group_check_state.add(true);
		}
	}
	public String getTitle(){
		return nTitle;
	}

	public String getnTitle() {
		return nTitle;
	}

	public void setnTitle(String nTitle) {
		this.nTitle = nTitle;
	}

	public ArrayList<String> getMarrayChildren() {
		return marrayChildren;
	}

	public void setMarrayChildren(ArrayList<String> marrayChildren) {
		this.marrayChildren = marrayChildren;
	}
	
	
}
