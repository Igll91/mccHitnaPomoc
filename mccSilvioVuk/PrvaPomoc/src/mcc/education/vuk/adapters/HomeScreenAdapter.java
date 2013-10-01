package mcc.education.vuk.adapters;

import java.util.ArrayList;
import java.util.List;

import mcc.education.vuk.R;
import mcc.education.vuk.R.array;
import mcc.education.vuk.helpers.HomeScreenPOJO;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeScreenAdapter extends BaseAdapter {

	protected List<HomeScreenPOJO> listOfHomeScreenPOJOs;
	protected Context context;
	
	public HomeScreenAdapter(Context con)
	{
		this.context = con;
		listOfHomeScreenPOJOs = fillListWithData();
	}
	
	@Override
	public int getCount() {
		return listOfHomeScreenPOJOs.size();
	}

	@Override
	public Object getItem(int arg0) throws IndexOutOfBoundsException 
	{
		return listOfHomeScreenPOJOs.get(arg0);
	}

	@Override
	public long getItemId(int arg0)
	{
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) 
	{
		LayoutInflater inflater = LayoutInflater.from(context);
		View newCoolLookView = inflater.inflate(R.layout.home_screen_list_element, null);
		HomeScreenPOJO tempHomeScreenPojo = listOfHomeScreenPOJOs.get(arg0);
		
		((TextView)newCoolLookView.findViewById(R.id.textViewHomeScreenElement)).setText(tempHomeScreenPojo.getText());
		((ImageView)newCoolLookView.findViewById(R.id.imageViewHomeScreenElement)).setImageResource(context.getResources().getIdentifier(tempHomeScreenPojo.getPictureResource(), "drawable", context.getPackageName()));
		
		
		return newCoolLookView;
	}
	
	/**
	 * puni listu sa HomeScreenPojo objektima
	 * 
	 * dohvaća popis elemenata iz xml resoursa {@link array#homeScreenMenuArray} koji u sebi sadrži
	 * za svaki element nazivElementa te ime resursa koji će se koristit kao slika.
	 * primjer elementa {@link array#homeScreenMenuItem2 }
	 * 
	 * @return listu HomeScreenPOJO objekata dohvaćenu iz arrays.xml resursa
	 */
	protected List<HomeScreenPOJO> fillListWithData()
	{
		TypedArray ta = context.getResources().obtainTypedArray(R.array.homeScreenMenuArray);
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
