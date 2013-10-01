package mcc.education.vuk;

import mcc.education.vuk.adapters.FirstAidKitAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Activity koji poziva jednostavan FirstAidKitAdapter 
 * 
 * poziva adapter koji pripremi podatke te ga samo postavi u listu 
 * za ispis tih podataka.
 * 
 * @author silvio
 *
 */
public class FirstAidKitContentActivity extends Activity {

	private ListView firstAidKitListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_aid_kit_content);
		
		firstAidKitListView = (ListView)findViewById(R.id.listView_firstaidkit_listOfContent);
		
		FirstAidKitAdapter aa = new FirstAidKitAdapter(this);
		
		firstAidKitListView.setAdapter(aa);
	}

}
