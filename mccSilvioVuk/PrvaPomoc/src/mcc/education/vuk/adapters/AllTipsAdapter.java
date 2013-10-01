package mcc.education.vuk.adapters;

import java.util.ArrayList;
import java.util.List;

import mcc.education.vuk.R;
import mcc.education.vuk.R.array;
import mcc.education.vuk.R.layout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Pripremi Adapter za postavljanje
 * 
 * Kreira listu Stringova iz resursa {@link array#hints} kojoj za svaki element postavi 
 * dizajn {@link layout#all_tips_element}
 * 
 * @author silvio
 *
 */
public class AllTipsAdapter extends BaseAdapter {

	private Context con;
	private List<String> listOfTips;
	
	/**
	 * Prima context u kojem će se postaviti Adapter te puni Listu sa podacima iz resursa.
	 * 
	 * @param context context u kojem će biti postavljen Adapter
	 */
	public AllTipsAdapter(Context context)
	{
		this.con = context;
		listOfTips = getAllTips();
	}
	
	@Override
	public int getCount() {
		return listOfTips.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listOfTips.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0; 
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		LayoutInflater inflater = LayoutInflater.from(con);
		View newCoolLookView = inflater.inflate(R.layout.all_tips_element, null);
		
		((TextView)newCoolLookView.findViewById(R.id.textView_alltips_numberText)).setText("" + (arg0+1));
		((TextView)newCoolLookView.findViewById(R.id.textView_alltips_tipText)).setText(listOfTips.get(arg0));
		
		return newCoolLookView;
	}
	
	/**
	 * Dohvaća podatke iz resursa u List-u
	 *  
	 * @return vraća List-u String-ova dohaćenih iz resursa  
	 */
	private List<String> getAllTips()
	{
		String[] allTips = con.getResources().getStringArray(R.array.hints);	
		List<String> tempList = new ArrayList<String>();
		
		for(String x: allTips)
			tempList.add(x);
		
		return tempList;
	}
}
