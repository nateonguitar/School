package com.natebrooks.thebasics;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class Main extends Activity {
		// splash
	
	MediaPlayer logoMusic;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			
			// pass the savedInstanceState to the oncreate() 
			//in Activity class
		super.onCreate(savedInstanceState);
		
			//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    
	    	//Remove notification bar
	    // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
		setContentView(R.layout.splash);
		
		logoMusic = MediaPlayer.create(Main.this, R.raw.thebasics);
		
		logoMusic.start();
		
		Thread logoTimer = new Thread(){
			public void run(){
				try{
					sleep(3000);
					
					Intent menuIntent = new Intent("com.natebrooks.thebasics.MENU");
					startActivity(menuIntent);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				finally{
					finish();
				}
			}
		};
		
		logoTimer.start();
	} // end onCreate()

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		logoMusic.release();
	}
	
	
}
