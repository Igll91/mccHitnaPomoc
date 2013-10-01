package mcc.education.vuk.helpers;

import mcc.education.vuk.R;
import mcc.education.vuk.SettingsActivity;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Skupina statičnih metoda
 * 
 * Služi za statično pozivanje metoda nebi li se smanjilo pisanje 
 * istog koda na više mjesta. 
 * Također služi kako bi se uveo red u sam kod nebi li sam bio čitljiviji,uredniji.
 * 
 * @author silvio
 *
 */
public abstract class StaticMethodsGroup {
	
	/**
	 * Poziva ACTION_DIAL intent
	 * 
	 * Obavlja poziv prema hitnoj pomoći preko android intenta ACTION_DIAL-a.
	 * Korišten ACTION_DIAL ukoliko nebi došlo do slučajnog pritiskanja određenog buttona za 
	 * tu akciju. 
	 * 
	 * @param x activity koji poziva metodu
 	 */
	public static void makeEmergencyCall(Activity x) 
	{
		 SharedPreferences settings = x.getSharedPreferences(StorageClass.PREFS_NAME, 0);
		 
		 String telNum = settings.getString("emergencyNumber", StorageClass.telNum);
		 
		 String uri = "tel:" + telNum.trim() ;
		 Intent intent = new Intent(Intent.ACTION_DIAL);
		 intent.setData(Uri.parse(uri));
		 x.startActivity(intent);
   }
	/**
	 *određuje koja akcija će se izvršiti ovisno o tome koji menu button je pritisnut
	 * 
	 * preko parametara dohvaća activity koji poziva metodu te id od menu item-a koji poziva
	 * metodu. 
	 * Ovisno o item id-u izvrši se određeni dio koda ( 3 su mogučnosti ): 
	 * <p>1. pozivanje activity-a postavki</p>
	 * <p>2. pozivanje dialoga sa podacima o aplikaciji i autoru </p>
	 * <p>2. pozivanje metode za uspostavu poziva prema hitnoj službi {@link #makeEmergencyCall(Activity)}</p>
	 * 
	 * @param act Activity u kojem se poziva funkcija
	 * @param itemID id pritisnutog itema u meniju
	 * @return vraća true ako je uspješno obavljen određeni zadatak , potreban zbog {@link mcc.education.vuk.EmergencyReanimation#onOptionsItemSelected(android.view.MenuItem)} metode
	 */
	public static boolean menuInit(Activity act, int itemID)
	{
		
		switch (itemID)
        {
        	case R.id.menu_item_main_screen_settings:
        		act.startActivity(new Intent(act, SettingsActivity.class));	
        		return true;
        		
        	case R.id.menu_item_main_screen_about:
        		final Dialog dialog = new Dialog(act);
    			dialog.setContentView(R.layout.about);
    			dialog.setTitle(R.string.about);
    			
    			Button dialogButton = (Button) dialog.findViewById(R.id.button_about_ok);
    			dialogButton.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View v) {
    					dialog.dismiss();
    				}
    			});
     
    			dialog.show();
        		return true;
        		
        	case R.id.menu_item_main_screen_call:
        		StaticMethodsGroup.makeEmergencyCall(act);	
        		return true;
        }	
		
		return false;
	}
}
