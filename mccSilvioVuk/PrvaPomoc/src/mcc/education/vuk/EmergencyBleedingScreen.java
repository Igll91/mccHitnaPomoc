package mcc.education.vuk;

import mcc.education.vuk.R.array;
import mcc.education.vuk.R.color;
import mcc.education.vuk.helpers.StaticMethodsGroup;
import mcc.education.vuk.helpers.StorageClass;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Početni ekran o krvavljenju
 * 
 * Sadrži 2 taba
 * 
 * @author silvio
 *
 */
public class EmergencyBleedingScreen extends Activity {

	private FrameLayout container;
	
	private View innerBleedingView;
	private View outerBleedingView;
	
	private Button innerBleedingButton;
	private Button outerBleedingButton;
	
	private ImageView tabInner;
	private ImageView tabOuter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_emergency_bleeding_screen);
		
		container = (FrameLayout)findViewById(R.id.frameLayout_emergency_container);
		
		tabInner = (ImageView)findViewById(R.id.imageview_emergency_bleeding_inner_tab);
		tabOuter = (ImageView)findViewById(R.id.imageview_emergency_bleeding_outer_tab);
		
		outerBleedingView = getLayoutInflater().inflate(R.layout.emergencyouterbleeding, null);
		drawElementsOuterBleeding();
		innerBleedingView = getLayoutInflater().inflate(R.layout.emergencyinnerbleeding, null);
		
		innerBleedingButton = (Button)findViewById(R.id.button_emergency_bleeding_inner);
		outerBleedingButton = (Button)findViewById(R.id.button_emergency_bleeding_outer);
		innerBleedingButton.setOnClickListener(listener);
		outerBleedingButton.setOnClickListener(listener);
		
		checkSelectedTab();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main_screen, menu);
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
		return StaticMethodsGroup.menuInit(EmergencyBleedingScreen.this, item.getItemId());
    }
	
	/**
	 * Mijenja view 
	 * 
	 * Mijenja view koji će trenutno biti "prikazan" odnosno obriše prijašnji view 
	 * i na njegovo mjesto stavi view iz parametra. 
	 * 
	 * @param view novi view koji će biti postavljen umjesto trenutnog
	 */
	private void setView(View view)
	{
		container.removeAllViews();
		container.addView(view);
	}
	
	/**
	 * Dodaje view-ove u layout
	 * 
	 * na određena mjesta dodaje view-ove koji se generiraju iz resursa {@link array#arrayBleeding1}
	 * te {@link array#arrayBleeding2}.
	 */
	private void drawElementsOuterBleeding()
	{	
		LayoutInflater inflater = LayoutInflater.from(outerBleedingView.getContext());
		LinearLayout ll = (LinearLayout)outerBleedingView.findViewById(R.id.linearLayout_emergency_bleeding_outerLayout);
		String[] arrayOfString = getResources().getStringArray(R.array.arrayBleeding1);
		
		int counter = 1;
		for(String currentString: arrayOfString)
		{
			View newCoolLookView = inflater.inflate(R.layout.emergencyonelinerelement, null);
			
			((TextView)newCoolLookView.findViewById(R.id.textView_emergency_oneliner_number)).setText(String.valueOf(counter));
			((TextView)newCoolLookView.findViewById(R.id.textView_emergency_oneliner_text)).setText(currentString);
			
			ll.addView(newCoolLookView,1 + counter++);
		}
		
		arrayOfString = getResources().getStringArray(R.array.arrayBleeding2);
		
		int counter2 = 1;
		for(String currentString: arrayOfString)
		{
			View newCoolLookView = inflater.inflate(R.layout.cold_listviewelement, null);
			
			((TextView)newCoolLookView.findViewById(R.id.textView_others_cold_number)).setText(String.valueOf(counter2));
			((TextView)newCoolLookView.findViewById(R.id.textView_others_cold_text)).setText(currentString);
			
			
			ll.addView(newCoolLookView,4 + counter + counter2++);
		}
	}

	/**
	 * Provjera selektiranog taba
	 * 
	 * Kod pokretanja activity-a provjerava koji tab je spremljen u sharedPreference, ako ne
	 * postoji dodjeljuje mu se vrijednost "inner".
	 * Također se poziva metoda {@link #tabPainter(boolean)} koja označava mijenjanjem boja
	 * koji od tabova je trenutno selektiran  
	 */
	private void checkSelectedTab()
	{
		SharedPreferences settings = getSharedPreferences(StorageClass.PREFS_NAME, MODE_PRIVATE);
		String tabSelected = settings.getString("tabsBleeding", "inner"); // ako ne postoji vrati "inner"
		
		if(tabSelected.equals("inner"))
		{
			tabPainter(true);
			setView(innerBleedingView);
		}
		else if(tabSelected.equals("outer"))
		{
			tabPainter(false);
			setView(outerBleedingView);
		}
			
	}
	
	/**
	 * Postavlja vrijednost sharedPreference
	 * 
	 * Postavlja vrijednost sharedPreference koja se koristi za pračenje aktivnog tab-a
	 * @param txt vrijednost koja će se postaviti u sharedPreference
	 */
	private void setEditor(String txt)
	{
		SharedPreferences settings = getSharedPreferences(StorageClass.PREFS_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("tabsBleeding", txt);
		editor.commit();
	}
	
	/**
	 * Mjenja boju selektiranog taba
	 * 
	 * Postavlja pozadinsku boju selektiranog taba-a na {@link color#redC80000}, a na 
	 * boju neselektiranog tab-a postavlja na bijelu.
	 * 
	 * @param isInner pošto su dvije vrijednosti prima true ako je kliknuto na button koji predstavlja unutarnje krvarenje
	 */
	private void tabPainter(boolean isInner)
	{
		if(isInner)
		{
			tabInner.setBackgroundColor(getResources().getColor(R.color.redC80000));
			tabOuter.setBackgroundColor(getResources().getColor(android.R.color.white));
		}
		else
		{
			tabInner.setBackgroundColor(getResources().getColor(android.R.color.white));
			tabOuter.setBackgroundColor(getResources().getColor(R.color.redC80000));
		}
	}
	
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.button_emergency_bleeding_inner:
				setEditor("inner");
				tabPainter(true);
				setView(innerBleedingView);
				break;
			case R.id.button_emergency_bleeding_outer:
				setEditor("outer");
				tabPainter(false);
				setView(outerBleedingView);
				break;
			}
		}
	};
}
