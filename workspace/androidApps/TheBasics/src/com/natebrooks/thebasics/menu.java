package com.natebrooks.thebasics;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class menu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.main);

		// button sound
		final MediaPlayer buttonSound = MediaPlayer.create(menu.this,
				R.raw.button_click);

		// setting up the button references
		Button tut1 = (Button) findViewById(R.id.tutorial1);
		Button tut2 = (Button) findViewById(R.id.tutorial2);
		Button tut3 = (Button) findViewById(R.id.tutorial3);

		tut1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				buttonSound.start();
				startActivity(new Intent("com.natebrooks.thebasics.TUTORIALONE"));

			}
		});

		tut2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				buttonSound.start();
				startActivity(new Intent("com.natebrooks.thebasics.TUTORIALTWO"));

			}
		});
		
		tut3.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				buttonSound.start();
				startActivity(new Intent("com.natebrooks.thebasics.WALLPAPER"));

			}
		});

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater awesome = getMenuInflater();
		awesome.inflate(R.menu.main_menu, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuSweet:
			startActivity(new Intent("com.natebrooks.thebasics.SWEET"));
			return true;
		case R.id.menuToast:
			Toast andEggs = Toast.makeText(menu.this, "this is a toast", Toast.LENGTH_LONG);
			andEggs.show();
			return true;
		}

		return false;
	}
}
