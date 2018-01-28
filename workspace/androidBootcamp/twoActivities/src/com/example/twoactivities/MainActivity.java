package com.example.twoactivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button = (Button) findViewById(R.id.openButton);
		button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				Intent i = new Intent("com.example.twoactivities.SecondActivity");
				startActivity(i);
			}
		});
	}
}
