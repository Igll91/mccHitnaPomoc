package mcc.education.vuk;

import mcc.education.vuk.adapters.EmergencyScreenAdapter;
import mcc.education.vuk.helpers.StaticMethodsGroup;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class EmergencyScreen extends Activity {

	private ListView emergencyListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_emergency_screen);
		
		emergencyListView = (ListView)findViewById(R.id.listView_emergencyScreen);
		
		EmergencyScreenAdapter esa = new EmergencyScreenAdapter(this);
		
		emergencyListView.setAdapter(esa);
		emergencyListView.setOnItemClickListener(listener);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_screen, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
		return StaticMethodsGroup.menuInit(EmergencyScreen.this, item.getItemId());
    }
	
	private OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			switch(arg2)
			{
				case 0:
					startActivity(new Intent(EmergencyScreen.this, EmergencyChokingScreen.class));
					return;
				case 1:
					startActivity(new Intent(EmergencyScreen.this, EmergencyBleedingScreen.class));	
					return;
				case 2:
					startActivity(new Intent(EmergencyScreen.this, EmergencyReanimation.class));	
					return;
				
				default:					
					return;
			}
			
		}
	}; 

}
