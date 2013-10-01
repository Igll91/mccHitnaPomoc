package mcc.education.vuk;

import mcc.education.vuk.R.array;
import mcc.education.vuk.adapters.AllTipsAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

/**
 * Lista svih savjeta
 * 
 * Kreira AllTipsAdapter {@link AllTipsAdapter} koji popuni listView sa svim
 * savjetima unutar resursa. {@link array#hints}
 * 
 * @author silvio
 *
 */
public class AllTipsActivity extends Activity {

	private ListView allTipsListView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_tips);
		
		allTipsListView = (ListView)findViewById(R.id.listView_alltips_listOftips);
		
		AllTipsAdapter aa = new AllTipsAdapter(this);
		
		allTipsListView.setAdapter(aa);
	}

}
