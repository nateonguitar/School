package com.natebrooks.practice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Practice01 extends Activity{

	EditText firstNum;
	EditText secondNum;
	Button calc;
	TextView equals;
	String first;
	String second;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_practice01);
		
		firstNum = (EditText) findViewById(R.id.firstNumber);
		secondNum = (EditText) findViewById(R.id.secondNumber);
		calc = (Button) findViewById(R.id.calcButton);
		equals = (TextView) findViewById(R.id.equals);
		
		calc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				first = firstNum.getText().toString();
				second = secondNum.getText().toString();
				
				if(first.matches("")){
					first = "0";
				}
				if(second.matches("")){
					second = "0";
				}
				
				equals.setText(String.valueOf((Integer.parseInt(first) + Integer.parseInt(second))));
				
			}
		});
	}
	
	
}
