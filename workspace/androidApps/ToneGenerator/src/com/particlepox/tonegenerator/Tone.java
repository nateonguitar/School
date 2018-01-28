package com.particlepox.tonegenerator;

import android.support.v7.app.ActionBarActivity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Tone extends ActionBarActivity {
	private Button playStopButton;
	private SeekBar hzBar;
	private TextView hzTextView;

	private boolean playing = false;

	private int duration = 1; // seconds
	private int sampleRate = 44100;
	private int numSamples = duration * sampleRate;
	private double sample[] = new double[numSamples];
	private int targetSamples = 100;
	private int numCycles;

	private final byte generatedSnd[] = new byte[2 * numSamples];

	private AudioTrack audioTrack;

	Handler handler = new Handler();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tone);

		playStopButton = (Button) findViewById(R.id.play);
		hzBar = (SeekBar) findViewById(R.id.hzbar);
		hzTextView = (TextView) findViewById(R.id.hztextview);

		/*
		 * playStopButton.setOnTouchListener(new OnTouchListener() {
		 * 
		 * @Override public boolean onTouch(View v, MotionEvent event) {
		 * 
		 * return false; } });
		 */

		playStopButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(playing){
					playing = false;
				}
				else{
					playing = true;
					genTone(2000);
					playSound();
				}
			}
		});

		hzBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {

			}

			@Override
			public void onProgressChanged(SeekBar seekbar, int progress,
					boolean fromUser) {
				
				hzTextView.setText((progress + 20) + " hz");

			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	void genTone(double freqOfTone){
		 
		  //clean out the arrays
		  for (int i = 0; i < targetSamples * 2; ++i) {
		          sample[i] = 0;
		     }
		  for (int i = 0; i < targetSamples * 2 * 2; ++i) {
		         generatedSnd[i] = (byte) 0x0000;
		  }
		    
		  // calculate adjustments to make the sample start and stop evenly
		  numCycles = (int) (0.5 +  freqOfTone * targetSamples/sampleRate);
		  numSamples = (int) (0.5 + numCycles * sampleRate/freqOfTone);
		 
		     // fill out the array
		     for (int i = 0; i < numSamples; ++i) {
		         sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
		     }
		     // convert to 16 bit pcm sound array
		     // assumes the sample buffer is normalized.
		     int idx = 0;
		     for (double dVal : sample) {
		     // scale loudness by frequency
		      double amplitude = (double) (32767 * 5/(Math.log(freqOfTone)));
		      if (amplitude > 32767) amplitude = 32767;
		      // scale signal to amplitude
		         short val = (short) (dVal * amplitude);
		         // in 16 bit wav PCM, first byte is the low order byte
		         generatedSnd[idx++] = (byte) (val & 0x00ff);
		         generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
		     }
		 }

	private void stopSound() {
		audioTrack.flush();
		audioTrack.stop();
		audioTrack.release();
		playing = false;
	}

	void playSound() {
		final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
				sampleRate, AudioFormat.CHANNEL_OUT_MONO,
				AudioFormat.ENCODING_PCM_16BIT, numSamples * 2,
				AudioTrack.MODE_STREAM);
		audioTrack.write(generatedSnd, 0, numSamples * 2);
		audioTrack.play();
		while (playing) {
			audioTrack.write(generatedSnd, 0, numSamples * 2);
		}
		stopSound();

	}
}