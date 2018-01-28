package com.natebrooks.thebasics;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class TutorialOne extends Activity implements OnCheckedChangeListener{
	
	TextView textOut;
	EditText textIn;
	RadioGroup gravityGroup, styleGroup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
			//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
		setContentView(R.layout.tutorial1);
		textOut = (TextView) findViewById(R.id.tvChange);
		textIn = (EditText) findViewById(R.id.editText1);
		gravityGroup = (RadioGroup) findViewById(R.id.rgGravity);
		styleGroup = (RadioGroup) findViewById(R.id.rgStyle);
		
		Button generateButton = (Button) findViewById(R.id.bGenerate);
		
		generateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
					// set the text at the bottom to 
					// whatever text you type in
				textOut.setText(textIn.getText());
				
			}
		});
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		
	}
	
}
