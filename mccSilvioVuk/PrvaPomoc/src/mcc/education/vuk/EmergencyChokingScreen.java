package mcc.education.vuk;

import mcc.education.vuk.helpers.StaticMethodsGroup;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

/**
 * Jednostavan Activity koji preko layout-a dobije sve potrebne informacije za ispis
 * 
 * @author silvio
 *
 */
public class EmergencyChokingScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 	// makne titleBar 
		setContentView(R.layout.activity_emergency_choking_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.activity_main_screen,menu);
		return true;
	}

	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
		return StaticMethodsGroup.menuInit(EmergencyChokingScreen.this, item.getItemId());
    }
}
