package mcc.education.vuk.adapters;

import java.util.ArrayList;
import java.util.List;

import mcc.education.vuk.R;
import mcc.education.vuk.R.array;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 *Puni listu podataka iz resursa 
 * @author silvio
 *
 */
public class FirstAidKitAdapter extends BaseAdapter {

	private List<String> numbers;
	private List<String> text;
	private Context con;
	
	public FirstAidKitAdapter(Context con)
	{
		this.con = con;
		fillData();
	}
	
	@Override
	public int getCount() {
		return numbers.size();
	}

	@Override
	public Object getItem(int arg0) {
		return text.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	/**
	 * dohvaća layout za pojedini element koji se napuni podacima iz liste te se postavi na novo kreirani view koji se vraća kako bi došlo do promjene.
	 */
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		LayoutInflater inflater = LayoutInflater.from(con);
		View newCoolLookView = inflater.inflate(R.layout.first_aid_kit_element, null);
		
		((TextView)newCoolLookView.findViewById(R.id.textView_firstAidKitelement_number)).setText(numbers.get(arg0));
		((TextView)newCoolLookView.findViewById(R.id.textView_firstAidKitelement_text)).setText(text.get(arg0));
		
		return newCoolLookView;
	}
	
	/**
	 * Uzima polje Stringova iz {@link array#firstaid_pack_array} resursa te puni liste sa njima.
	 * 
	 * puni 2 polja zbog dizajnerskih razloga {@link #getView(int, View, ViewGroup)}
	 * primjer jednog stringa 2kom. texttext racijepa na dva dijela 2kom i texttext te ih tako mogu koristiti u 2 resursa.
	 */
	private void fillData() 
	{
		String[] tempStrings = con.getResources().getStringArray(R.array.firstaid_pack_array);
		
		numbers = new ArrayList<String>();
		text = new ArrayList<String>();
		
		for(String currString: tempStrings)
		{
			int pos = currString.indexOf("-");
			numbers.add(currString.substring(0,pos));
			text.add(currString.substring(pos+1));
		}
	}
}
