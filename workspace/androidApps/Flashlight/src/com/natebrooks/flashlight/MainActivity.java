package com.natebrooks.flashlight;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnSeekBarChangeListener {

	// flag to detect flash is on or off
	private boolean deviceHasCamera = true;
	private boolean isLightOn = false;

	private boolean soundOn;

	private Parameters p;
	private Camera camera;

	private ImageButton buttonTorch;
	private SeekBar brightnessSlider;

	private RelativeLayout mainLayout;

	private boolean redSelected = false;
	private boolean yellowSelected = false;
	private boolean blueSelected = false;

	// set up SoundPool for sound effects
	private int lightOnSound;
	private int lightOffSound;

	float streamVolume;
	private AudioManager mAudioManager;
	// the maximum number of simultaneous streams for this SoundPool objectr
	int maxStreamNumber = 5;
	private SoundPool soundPool;

	private ImageButton redButton;
	private ImageButton yellowButton;
	private ImageButton blueButton;

	private int brightnessSliderPosition = 128;

	private TextView brightnessPercentText;

	@Override
	protected void onStop() {
		super.onStop();

		if (camera != null) {
			camera.release();
		}
	}

	// ////////////////////////////////////////////////////////////
	// onCreate////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set up so we can save settings
		SharedPreferences settings = getSharedPreferences(
				"flashlight_preferences", 0);
		SharedPreferences.Editor settingsEditor = settings.edit();

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().addFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.activity_splash);
		
		setContentView(R.layout.activity_main);

		// ////////////////////////////////////////////////////////////
		// setup SoundPool/////////////////////////////////////////////
		// ////////////////////////////////////////////////////////////

		soundPool = new SoundPool(maxStreamNumber, AudioManager.STREAM_MUSIC, 0);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		streamVolume = 1.0f;

		// ////////////////////////////////////////////////////////////
		// setup buttons and slider////////////////////////////////////
		// ////////////////////////////////////////////////////////////
		buttonTorch = (ImageButton) findViewById(R.id.buttonFlashlight);
		brightnessSlider = (SeekBar) findViewById(R.id.brightnessSlider);

		brightnessSlider.setOnSeekBarChangeListener(this);

		redButton = (ImageButton) findViewById(R.id.buttonRed);
		yellowButton = (ImageButton) findViewById(R.id.buttonYellow);
		blueButton = (ImageButton) findViewById(R.id.buttonBlue);

		brightnessPercentText = (TextView) findViewById(R.id.brightnessText);

		lightOnSound = soundPool.load(this, R.raw.lightonsound, 1);
		lightOffSound = soundPool.load(this, R.raw.lightoffsound, 1);

		// ////////////////////////////////////////////////////////////
		// set up the main layout to be able to control colors/////////
		// ////////////////////////////////////////////////////////////

		mainLayout = (RelativeLayout) findViewById(R.id.flashLiteMainLayout);
		mainLayout.setBackgroundColor(Color.argb(128, 255, 255, 255));

		Context context = this;
		PackageManager packageManager = context.getPackageManager();

		// ////////////////////////////////////////////////////////////
		// Check if device has camera//////////////////////////////////
		// ////////////////////////////////////////////////////////////
		if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			deviceHasCamera = false;
		}
		PackageManager pm = context.getPackageManager();
		// if device support camera?
		if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Log.e("err", "Device has no camera!");
			return;
		}

		checkForCamera();

		WindowManager.LayoutParams layout = getWindow().getAttributes();
		layout.screenBrightness = 1F;
		getWindow().setAttributes(layout);

		// ////////////////////////////////////////////////////////////
		// Check if first time user////////////////////////////////////
		// ////////////////////////////////////////////////////////////
		if (settings.getBoolean("firstTime", true)) {
			// build default settings for first time
			settingsEditor.putBoolean("firstTime", false);
			settingsEditor.putInt("brightness", brightnessSliderPosition);
			settingsEditor.putBoolean("soundOn", true);
			settingsEditor.putBoolean("redOn", false);
			settingsEditor.putBoolean("yellowOn", false);
			settingsEditor.putBoolean("blueOn", false);
			settingsEditor.commit();
		} else {
			brightnessSliderPosition = settings.getInt("brightness", 128);
			soundOn = settings.getBoolean("soundOn", true);
			brightnessSlider.setProgress(brightnessSliderPosition);
			redSelected = settings.getBoolean("redOn", false);
			yellowSelected = settings.getBoolean("yellowOn", false);
			blueSelected = settings.getBoolean("blueOn", false);
			changeColors(brightnessSliderPosition);
		}

		if (redSelected) {
			redButton.setImageResource(R.drawable.red_on);
		}
		if (blueSelected) {
			blueButton.setImageResource(R.drawable.blue_on);
		}
		if (yellowSelected) {
			yellowButton.setImageResource(R.drawable.yellow_on);
		}

		buttonTorch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				flipLight();
			}
		});

		redButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				redSelected = !redSelected;
				if (redSelected) {
					if (soundOn) {
						soundPool.play(lightOnSound, 1.0f, 1.0f, 1, 0, 1.0f);
					}
					redButton.setImageResource(R.drawable.red_on);
				} else {
					if (soundOn) {
						soundPool.play(lightOffSound, 1.0f, 1.0f, 1, 0, 1.0f);
					}
					redButton.setImageResource(R.drawable.red_off);
				}
				SharedPreferences settings = getSharedPreferences(
						"flashlight_preferences", 0);
				SharedPreferences.Editor settingsEditor = settings.edit();
				settingsEditor.putBoolean("redOn", redSelected);
				settingsEditor.commit();
				changeColors(brightnessSliderPosition);
			}
		});

		yellowButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				yellowSelected = !yellowSelected;
				if (yellowSelected) {
					if (soundOn) {
						soundPool.play(lightOnSound, 1.0f, 1.0f, 1, 0, 1.0f);
					}
					yellowButton.setImageResource(R.drawable.yellow_on);
				} else {
					if (soundOn) {
						soundPool.play(lightOffSound, 1.0f, 1.0f, 1, 0, 1.0f);
					}
					yellowButton.setImageResource(R.drawable.yellow_off);

				}
				SharedPreferences settings = getSharedPreferences(
						"flashlight_preferences", 0);
				SharedPreferences.Editor settingsEditor = settings.edit();
				settingsEditor.putBoolean("yellowOn", yellowSelected);
				settingsEditor.commit();
				changeColors(brightnessSliderPosition);

			}
		});

		blueButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				blueSelected = !blueSelected;
				if (blueSelected) {
					// play sound, flip to on image
					if (soundOn) {
						soundPool.play(lightOnSound, 1.0f, 1.0f, 1, 0, 1.0f);
					}
					blueButton.setImageResource(R.drawable.blue_on);
				} else {
					// play sound, flip to off image
					if (soundOn) {
						soundPool.play(lightOffSound, 1.0f, 1.0f, 1, 0, 1.0f);
					}
					blueButton.setImageResource(R.drawable.blue_off);
				}
				SharedPreferences settings = getSharedPreferences(
						"flashlight_preferences", 0);
				SharedPreferences.Editor settingsEditor = settings.edit();
				settingsEditor.putBoolean("blueOn", blueSelected);
				settingsEditor.commit();
				changeColors(brightnessSliderPosition);
			}
		});

	}

	// seekbar tracking
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {

		// position used to set colors when buttons are pressed
		// otherwise screen doesn't change colors til slider is moved
		brightnessSliderPosition = progress;
		changeColors(brightnessSliderPosition);

		brightnessPercentText.setText(Integer.toString((int) Math
				.round((brightnessSliderPosition * 100) / 255)) + "%");

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		SharedPreferences settings = getSharedPreferences(
				"flashlight_preferences", 0);
		SharedPreferences.Editor settingsEditor = settings.edit();
		settingsEditor.putInt("brightness", brightnessSliderPosition);
		settingsEditor.commit();
	}

	// end seekbar tracking

	private void changeColors(int progress) {
		// white none selected
		if (!redSelected && !yellowSelected && !blueSelected) {
			mainLayout.setBackgroundColor(Color.argb(progress, 255, 255, 255));
		}
		// white all selected
		else if (redSelected && yellowSelected && blueSelected) {
			mainLayout.setBackgroundColor(Color.argb(progress, 255, 255, 255));
		}
		// red
		else if (redSelected && !yellowSelected && !blueSelected) {
			mainLayout.setBackgroundColor(Color.argb(progress, 255, 0, 0));
		}

		// orange
		else if (redSelected && yellowSelected && !blueSelected) {
			mainLayout.setBackgroundColor(Color.argb(progress, 255, 102, 0));
		}

		// yellow
		else if (!redSelected && yellowSelected && !blueSelected) {
			mainLayout.setBackgroundColor(Color.argb(progress, 255, 255, 0));
		}

		// green
		else if (!redSelected && yellowSelected && blueSelected) {
			mainLayout.setBackgroundColor(Color.argb(progress, 0, 255, 0));
		}

		// blue
		else if (!redSelected && !yellowSelected && blueSelected) {
			mainLayout.setBackgroundColor(Color.argb(progress, 0, 0, 255));
		}

		// purple
		else if (redSelected && !yellowSelected && blueSelected) {
			mainLayout.setBackgroundColor(Color.argb(progress, 255, 0, 255));
		}
	}

	private void flipLight() {
		if (deviceHasCamera) {
			if (isLightOn) {
				if (soundOn) {
					soundPool.play(lightOffSound, streamVolume, streamVolume,
							1, 0, 1f);
				}
				// lightOffSound.start();
				p.setFlashMode(Parameters.FLASH_MODE_OFF);
				camera.setParameters(p);
				camera.stopPreview();
				isLightOn = false;

				buttonTorch.setBackgroundResource(R.drawable.flashlight_off);
			} else {
				if (soundOn) {
					soundPool.play(lightOnSound, 1.0f, 1.0f, 1, 0, 1.0f);
				}
				p.setFlashMode(Parameters.FLASH_MODE_TORCH);

				camera.setParameters(p);
				camera.startPreview();
				isLightOn = true;

				buttonTorch.setBackgroundResource(R.drawable.flashlight_on);
			}
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu_button, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// check id of menu item that was selected
		switch (item.getItemId()) {

		case R.id.fullScreen:
			// toggle fullscreen
			WindowManager.LayoutParams attrs = getWindow().getAttributes();
			attrs.flags ^= WindowManager.LayoutParams.FLAG_FULLSCREEN;
			getWindow().setAttributes(attrs);

			return true;

		case R.id.soundOnOff:
			soundOn = !soundOn;
			SharedPreferences settings = getSharedPreferences(
					"flashlight_preferences", 0);
			SharedPreferences.Editor settingsEditor = settings.edit();
			settingsEditor.putBoolean("soundOn", soundOn);
			settingsEditor.commit();
			return true;

		case R.id.exit:
			moveTaskToBack(true);
			return true;
		}

		return false;
	}

	private void checkForCamera() {
		if (deviceHasCamera) {
			camera = Camera.open();

			p = camera.getParameters();

			// turn light on when app opens
			p.setFlashMode(Parameters.FLASH_MODE_TORCH);

			camera.setParameters(p);
			camera.startPreview();
			isLightOn = true;
		}
		if (!deviceHasCamera) {
			mainLayout.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		if (isLightOn) {
			flipLight();
		}
	}

	@Override
	protected void onPause() {
		camera.release();
		finish();
		super.onPause();
	}
}