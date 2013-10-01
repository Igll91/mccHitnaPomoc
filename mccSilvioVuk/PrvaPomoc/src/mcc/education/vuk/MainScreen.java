package mcc.education.vuk;

import mcc.education.vuk.adapters.HomeScreenAdapter;
import mcc.education.vuk.helpers.StaticMethodsGroup;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class MainScreen extends Activity {
	
	private ListView listViewHomeScreen;
	private ImageButton settings;
	private ImageButton callButton;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final boolean customTitleSupported = requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		
		setContentView(R.layout.activity_main_screen);
		
		// custom "titleBar" sa buttonima za poziv i postavke
		if ( customTitleSupported ) 
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);
            
		this.listViewHomeScreen = (ListView)findViewById(R.id.listViewHomeScreen);
		this.settings = (ImageButton)findViewById(R.id.imageButton_titlebar_settings);
		this.callButton = (ImageButton)findViewById(R.id.imageButton_titlebar_call);
		
		// LISTENERS ---------------------------------------------------------------------- neuredno znam :/ 
		/*
		 * poziva Activity odgovoran za postavke aplikacije
		 */
		settings.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callSettingsActivity();
			}
		});
		
		/*
		 * Poziva dialer korisnikovog android uređaja
		 */
		callButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StaticMethodsGroup.makeEmergencyCall(MainScreen.this);
			}
		});
		

		listViewHomeScreen.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				switch(arg2)
				{
					case 0:
						startActivity(new Intent(MainScreen.this, EmergencyScreen.class));	
						return;
					case 1:
						startActivity(new Intent(MainScreen.this, OtherMethodsActivity.class));	
						return;
					case 2:
						startActivity(new Intent(MainScreen.this, FirstAidKitContentActivity.class));	
						return;
					case 3:					
						startActivity(new Intent(MainScreen.this, AllTipsActivity.class));
						return;
					
					default:					
						return;
				}
			}
		});
		// LISTENERS ----------------------------------------------------------------------
		
		HomeScreenAdapter aa = new HomeScreenAdapter(this);
		listViewHomeScreen.setAdapter(aa);
	}
	
	/**
	 * Dodatna provjera
	 * 
	 * Pritiskom na back button poziva se Dialog u kojem je potrebno potvrditi izlazak iz 
	 * aplikacije 
	 * 
	 * Uvedeno nebi li korisnik slučajno u panici izašao iz aplikacije u ključnim trenucima.
	 */
	@Override
	public void onBackPressed() 
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("exit");
		alert.setMessage(R.string.backButton);
		alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				MainScreen.this.finish();
			}
		});
		
		alert.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainScreen.this, "cancel", Toast.LENGTH_LONG).show();
			}
		});
		
		alert.show();
		return;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.activity_main_screen, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
		return StaticMethodsGroup.menuInit(MainScreen.this, item.getItemId());
    }
	
	private void callSettingsActivity()
	{
		startActivity(new Intent(MainScreen.this, SettingsActivity.class));	
	}

}
