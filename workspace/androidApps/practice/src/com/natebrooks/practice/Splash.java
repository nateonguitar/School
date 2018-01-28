package com.natebrooks.practice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		setContentView(R.layout.activity_splash);
		
		Thread splashTimer = new Thread(){
			public void run(){
				try{
					sleep(1000);
					
					Intent menuIntent = new Intent("com.natebrooks.practice.MENU");
					startActivity(menuIntent);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				finally{
					finish();
				}
			}
		};
		
		splashTimer.start();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	
}
