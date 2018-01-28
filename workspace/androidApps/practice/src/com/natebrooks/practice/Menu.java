package com.natebrooks.practice;

import com.natebrooks.practice.R.id;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Menu extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_menu);
		
			// set up button references
		Button practice01Btn = (Button) findViewById(R.id.practice01button);
		Button practice02Btn = (Button) findViewById(R.id.practice02button);
		
		
		practice01Btn.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent("com.natebrooks.practice.PRACTICE01"));
				
			}
		});
		
		practice02Btn.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent("com.natebrooks.practice.PRACTICE02"));
				
			}
		});	
		
	}
}
