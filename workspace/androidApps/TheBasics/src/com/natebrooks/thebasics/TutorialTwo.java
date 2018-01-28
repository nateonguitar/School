package com.natebrooks.thebasics;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TutorialTwo extends ListActivity{

	String classNames[] = {"main", "menu", "Sweet", "TutorialOne"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classNames));
	}
	
	protected void onListItemClick(ListView lv, View v, int position, long id){
		super.onListItemClick(lv, v, position, id);
			// set openClass to whatever was clicked
		String openClass = classNames[position];
		
		try{
			Class selected = Class.forName("com.natebrooks.thebasics." + openClass);
			Intent selectedIntent = new Intent(this, selected);
			startActivity(selectedIntent);
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		finish();
	}
}