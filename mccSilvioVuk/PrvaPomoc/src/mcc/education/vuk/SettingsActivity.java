
package mcc.education.vuk;

import mcc.education.vuk.helpers.StorageClass;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Služi za mjenjanje postavka aplikacije
 * 
 * mogućnost uključivanja/isključivanja splash screena, te postavljanje resetiranje 
 * telefonskog broja koji se korisit u metodama za uspostavu poziva.
 * 
 * @author silvio
 *
 */
public class SettingsActivity extends Activity {

	private CheckBox splashCheckBox;
	private TextView splashDeatils;
	private Button telNumButton;
	private LinearLayout telNumLayout;
	private ToggleButton onoffTelButton;
	private EditText telNumText;
	private Button telNumReset;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		splashCheckBox = (CheckBox)findViewById(R.id.settings_splash_screen_checkbox);
		splashDeatils = (TextView)findViewById(R.id.settings_splash_screen_textview);
		telNumButton = (Button)findViewById(R.id.button_settings_telNum); // button kojim potvrđujemo promjenu tel. Broja aplikacije
		telNumLayout = (LinearLayout)findViewById(R.id.linearLayout_settings_telNumChanger);
		onoffTelButton = (ToggleButton)findViewById(R.id.toggleButton_settings);
		telNumText = (EditText)findViewById(R.id.editText_settings_number);
		telNumReset = (Button)findViewById(R.id.button_settings_telNum_reset);
		
		telNumReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
				builder.setMessage(getResources().getString(R.string.confirmQuestion)).setPositiveButton(getResources().getString(R.string.ok), dialogClickListener)
				    .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
			}
		});
		
		/**
		 *  sakriva/otkriva dio u kojem unosimo novi broj i potvrđujemo ga 
		 */
		onoffTelButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1)
					telNumLayout.setVisibility(0); // visible
				else
					telNumLayout.setVisibility(8); // gone 
			}
		});
		
		telNumButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				String txtNumber = telNumText.getText().toString();
				
				if(txtNumber.length() <= 1)
					Toast.makeText(SettingsActivity.this, getResources().getString(R.string.settings_telNum_tooShort_message), Toast.LENGTH_LONG).show();
				else
				{
					SharedPreferences settings = getSharedPreferences(StorageClass.PREFS_NAME, MODE_PRIVATE);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("emergencyNumber", txtNumber);
					editor.commit();
					Toast.makeText(SettingsActivity.this, getResources().getString(R.string.settings_telNum_saved_message), Toast.LENGTH_LONG).show();
				}
			}
		});
		
		checkStatus();
		
		splashCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferences settings = getSharedPreferences(StorageClass.PREFS_NAME, MODE_PRIVATE);
			      SharedPreferences.Editor editor = settings.edit();
			      editor.putBoolean("splash", isChecked);

			      // Commit the edits!
			      editor.commit();
			      checkStatus();
			}
		});
	}

	/**
	 * kod pritiska na checkbox {@link #splashCheckBox} provjerava trenutno stanje te ovisno o tome postavlja text i atribut checked
	 */
	private void checkStatus()
	{
		SharedPreferences settings = getSharedPreferences(StorageClass.PREFS_NAME, MODE_PRIVATE);
		boolean splashIsOn = settings.getBoolean("splash", true); // ako ne postoji vrati true 
		
		splashCheckBox.setChecked(splashIsOn);
		if(splashIsOn)
			splashDeatils.setText(R.string.settings_splash_details_on);
		else
			splashDeatils.setText(R.string.settings_splash_details_off);

	}
	
	/**
	 * Dodatna provjera kod promjene broja , potrebno potvrditi za izmjenu podataka 
	 */
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:  //Yes button clicked
	        
				SharedPreferences settings = getSharedPreferences(StorageClass.PREFS_NAME, MODE_PRIVATE);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("emergencyNumber", StorageClass.telNum);
				editor.commit();
				Toast.makeText(SettingsActivity.this, getResources().getString(R.string.settings_telNum_reset_message), Toast.LENGTH_LONG).show();
			
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	            //No button clicked
	            break;
	        }
	    }
	};
}


