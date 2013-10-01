package mcc.education.vuk.adapters;

import java.util.ArrayList;
import java.util.List;

import mcc.education.vuk.R;
import mcc.education.vuk.R.array;
import mcc.education.vuk.helpers.HomeScreenPOJO;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

/**
 *Pripremi Adapter za postavljanje
 * 
 * Nasljeđuje potrebne atribute i metode iz {@link HomeScreenAdapter} klase te
 * samo izmjeni odnosno override-a  {@link #fillListWithData()} metodu zbog promjene 
 * resursa iz kojeg se uzimaju podaci.
 * 
 * @author silvio
 *
 */
public class EmergencyScreenAdapter extends HomeScreenAdapter {

	public EmergencyScreenAdapter(Context con) {
		super(con);
	}
	
	/**
	 * Puni listu sa podacima iz resursa
	 * 
	 * uzima podatke iz {@link array#emergencyScreenMenuArray} te ih obrađuje i sprema
	 * u listu HomeScreenPOJO objekata koja se koristi kod izrade dizajna .
	 */
	@Override
	protected List<HomeScreenPOJO> fillListWithData()
	{
		TypedArray ta = context.getResources().obtainTypedArray(R.array.emergencyScreenMenuArray);
		List<HomeScreenPOJO> list = new ArrayList<HomeScreenPOJO>();
		
		int numberOfElements = ta.length();
		
		for (int i = 0; i < numberOfElements; ++i) 
		{
			int id = ta.getResourceId(i, 0);
			
			HomeScreenPOJO tempPOJO = new HomeScreenPOJO();
			
			if(id > 0)
			{
				String [] menuItems = context.getResources().getStringArray(id);
				tempPOJO.setText(menuItems[0]);
				tempPOJO.setPictureResource(menuItems[1]);
			}
			else
				Log.e("error","HomeScreenAdapter -> fillListWithData() = check arrays.xml");
			
			list.add(tempPOJO);
		}
		ta.recycle(); 
		return list;
	}
	


}
