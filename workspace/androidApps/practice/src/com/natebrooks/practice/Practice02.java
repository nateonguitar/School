package com.natebrooks.practice;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Practice02 extends Activity{

	EditText price;
	EditText inches;
	Button calc;
	TextView equals;
	
	double pricePerYard;
	double inchesTall;
	double pricePerSquareInch;
	
	boolean priceEmpty;
	boolean inchesEmpty;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
		InputMethodManager.HIDE_IMPLICIT_ONLY);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_practice02);
		
		price = (EditText) findViewById(R.id.firstNumber);
		inches = (EditText) findViewById(R.id.secondNumber);
		calc = (Button) findViewById(R.id.calcButton);
		equals = (TextView) findViewById(R.id.equals);
		
		
		calc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
					//Check for blank fields
				if(price.getText().toString().matches("")){
					pricePerYard = 0;
					priceEmpty = true;
				}
				else{
					priceEmpty = false;
				}
				if(inches.getText().toString().matches("")){
					inchesTall = 0;
					inchesEmpty = true;
				}
				else{
					inchesEmpty = false;
				}
				
					//Set values to user input
				if(inchesEmpty == false){
					inchesTall = Double.parseDouble(inches.getText().toString());
				}
				if(priceEmpty == false){
					pricePerYard = Double.parseDouble(price.getText().toString());
				}
				
					// calculate the price per square inch and display
				pricePerSquareInch =  pricePerYard / (inchesTall * 36);
				
				equals.setText("Price per\nsquare inch\n$" + String.valueOf((double) Math.round(pricePerSquareInch * 10000) / 10000));
				
				InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				mgr.hideSoftInputFromWindow(price.getWindowToken(), 0);
					
			}
		});
	}
	
	public void calculate(){
		int w = 2;
	}
	
}