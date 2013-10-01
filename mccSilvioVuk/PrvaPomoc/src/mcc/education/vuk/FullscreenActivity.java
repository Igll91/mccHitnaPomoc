package mcc.education.vuk;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import mcc.education.vuk.helpers.StaticMethodsGroup;
import mcc.education.vuk.helpers.StorageClass;
import mcc.education.vuk.util.SystemUiHider;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * Used as a splash screen with representation of useful tips
 * automatic redirect to the main Activity
 * -can be disabled in settings
 * 
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
	/**
	 *  reference to emergency call button
	 */
	private Button buttonEmergencyCall;
	
	private TextView hintText;
	
	private Timer timer;
	
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		checkSplashStatus();
		
		setContentView(R.layout.activity_fullscreen);

		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible)
					{
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) 
						{
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} 
						else 
						{
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
			});

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		findViewById(R.id.emergency_call_button).setOnTouchListener(
				mDelayHideTouchListener);
		
		this.hintText = (TextView)findViewById(R.id.fullscreen_content);
		
		this.buttonEmergencyCall = (Button)findViewById(R.id.emergency_call_button);
		
		buttonEmergencyCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StaticMethodsGroup.makeEmergencyCall(FullscreenActivity.this); 			}
		});
		
		setHintText();
		stepToTheNextActivity();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) 
	{
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
	
	/**
	 * Timer koji poziva drugu aktivnost
	 * 
	 * Nakon određenog vremena prebaciva korisnika na glavni meni aplikacije {@link MainScreen} 
	 * 
	 */
	private void stepToTheNextActivity()
	{
		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				startActivity(new Intent(FullscreenActivity.this, MainScreen.class));				
			}
		}, 4000  /*amount of time in milliseconds before execution*/);
		
	}
	
	/**
	 * dohvaća random savjet i postavlja ga na poziciju textViewa 
	 */
	private void setHintText()
	{
		String[] hints = getResources().getStringArray(R.array.hints);
	
		Random rand = new Random();
		
		int randomNum = rand.nextInt(hints.length);
		
		this.hintText.setText(hints[randomNum]);

	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		//Toast.makeText(this, "onPause", Toast.LENGTH_LONG).show();
		timer.cancel();
	}
	
	private void checkSplashStatus()
	{
		SharedPreferences settings = getSharedPreferences(StorageClass.PREFS_NAME, MODE_PRIVATE);
		boolean splashIsOn = settings.getBoolean("splash", true); // ako ne postoji vrati true 
		
		if(splashIsOn == false)
			startActivity(new Intent(FullscreenActivity.this, MainScreen.class));	
	}
	
}
