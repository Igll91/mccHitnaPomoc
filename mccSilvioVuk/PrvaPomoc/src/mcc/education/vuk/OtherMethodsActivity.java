package mcc.education.vuk;

import java.util.ArrayList;

import mcc.education.vuk.R.array;
import mcc.education.vuk.adapters.SodExpendableListAdapter;
import mcc.education.vuk.helpers.Parent;
import mcc.education.vuk.helpers.StaticMethodsGroup;
import mcc.education.vuk.helpers.StorageClass;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class OtherMethodsActivity extends Activity implements OnClickListener{

	private ImageButton imgBtnSignsOfDeath;
	private ImageButton imgBtnThunderstruk;
	private ImageButton imgBtnCold;
	private ImageButton imgBtnSnake;
	
	/**
	 * expandable lista u sod view-u 
	 */
	private ExpandableListView mExpandableList;
	
	private View sod, thunder, cold, snake;
	
	/**
	 * layout u kojem brišemo prošli i stavljamo novo selektirani view 
	 */
	private FrameLayout fl;
	
	/**
	 * postavlja reference na resurse 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 	//Remove title bar
		setContentView(R.layout.activity_other_methods);

		fl = (FrameLayout)findViewById(R.id.frameLayout_otherMethods_wannabeTabDisplayer);
		
		sod = getLayoutInflater().inflate(R.layout.other_methods_deathsigns_layout, null);
		cold = getLayoutInflater().inflate(R.layout.other_methods_cold_layout, null);
		thunder = getLayoutInflater().inflate(R.layout.other_methods_thunderstruck_layout, null);
		snake = getLayoutInflater().inflate(R.layout.other_methods_snake_layout, null);
		
		imgBtnCold = (ImageButton)findViewById(R.id.imageButton_OtherMethods_cold);
		imgBtnSignsOfDeath = (ImageButton)findViewById(R.id.imageButton_OtherMethods_signsOfDeath);
		imgBtnThunderstruk = (ImageButton)findViewById(R.id.imageButton_OtherMethods_thunderstruck);
		imgBtnSnake = (ImageButton)findViewById(R.id.imageButton_OtherMethods_snake);
		
		imgBtnCold.setOnClickListener(this);
		imgBtnSignsOfDeath.setOnClickListener(this);
		imgBtnThunderstruk.setOnClickListener(this);
		imgBtnSnake.setOnClickListener(this);
		
		
		mExpandableList = (ExpandableListView)sod.findViewById(R.id.expandableListView_otherMethods_deathssigns);

		checkSelectedTab();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main_screen, menu);
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
		return StaticMethodsGroup.menuInit(OtherMethodsActivity.this, item.getItemId());
    }

	/**
	 * Provjerava za pritisnuti view koji id mu odgovara te taj određeni view puni sa podacima u dodatnoj metodi i postavlja ga trenutno vidljivim
	 */
	@Override
	public void onClick(View v) {
		SharedPreferences settings = getSharedPreferences(StorageClass.PREFS_NAME, MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		switch(v.getId())
		{
			case R.id.imageButton_OtherMethods_cold:
				editor.putString("tabs", "cold");
				editor.commit();
				drawElementsCold(true);
				setVisibility(cold);
				return;
			case R.id.imageButton_OtherMethods_snake:
				editor.putString("tabs", "snake");
				editor.commit();
				drawElementsSnake(true);
				setVisibility(snake);
				return;
			case R.id.imageButton_OtherMethods_thunderstruck:
				editor.putString("tabs", "thunder");
				editor.commit();
				drawElementsThunder(true);
				setVisibility(thunder);
				return;
			case R.id.imageButton_OtherMethods_signsOfDeath:
				editor.putString("tabs", "sod");
				editor.commit();
				setSODExpandableList();
				setVisibility(sod);
				return;
		}
		
	}
	
	/**
	 * makiva prijašnji view u frame layoutu i postavlja novi  
	 * @param view view koji će biti aktivan/vidljiv
	 */
	private void setVisibility(View view)
	{
		fl.removeAllViews();
		fl.addView(view);
		tabColorChanger();
	}
	
	/**
	 * poziva se kod onCreate metode , provjerava koji je tab zabilježen u sharedPreferences-ima te ga postavlja vidljivim
	 * ako ni jedan nije postavljen odnosno aplikacija se prvi puta pokreće postavlja ga na "sod"
	 */
	private void checkSelectedTab()
	{
		SharedPreferences settings = getSharedPreferences(StorageClass.PREFS_NAME, MODE_PRIVATE);
		String tabSelected = settings.getString("tabs", "sod"); // ako ne postoji vrati "sod"
		
		if(tabSelected.equals("sod"))
		{
			setSODExpandableList();
			setVisibility(sod);
		}
		else if(tabSelected.equals("cold"))
		{
			drawElementsCold(false);
			setVisibility(cold);
		}
		else if(tabSelected.equals("thunder"))
		{
			drawElementsThunder(false);
			setVisibility(thunder);
		}
		else if(tabSelected.equals("snake"))
		{
			drawElementsSnake(false);
			setVisibility(snake);
		}
			
	}
	
	/**
	 * priprema podatke te ih šalje u {@link SodExpendableListAdapter} koji zatim lista uzima kao izvor resursa za prikaz.
	 */
	private void setSODExpandableList()
	{
		ArrayList<Parent> arrayParents = new ArrayList<Parent>();
		ArrayList<String> arrayChildren = new ArrayList<String>();
		
		String [] headings = getResources().getStringArray(R.array.hReferenceSignsOfDeath);
		String [] headingsText = getResources().getStringArray(R.array.hTextReferenceSignsOfDeath);
		
		int counter = 0;
		//here we set the parents and the children
		for (String currentHeading : headings)
		{
			Parent parent = new Parent();
			parent.setTitle(currentHeading);

			arrayChildren = new ArrayList<String>();
			arrayChildren.add(headingsText[counter++]);
			
			parent.setArrayChildren(arrayChildren);

			//in this array we add the Parent object. We will use the arrayParents at the setAdapter
			arrayParents.add(parent);
		}

		//sets the adapter that provides data to the list.
		SodExpendableListAdapter adapter = new SodExpendableListAdapter(sod.getContext(), arrayParents);
		mExpandableList.setAdapter(adapter);
	 
	}
	
	/**
	 * Na određena mjesta ubacuje u taj view podatke iz array resursa
	 * 
	 * Uzima podatke iz {@link array#hypothermia_conciousArray } te {@link array#hypothermia_unconciousArray} resursa te iz njih dinamički
	 * kreira nove view-ove koje umetne u cold {@link #cold} view na određene pozicije.
	 * 
	 * @param delete koristi se za provjeru, potrebno je obrisati stare view-ove nebi li došlo do više istih view-a 
	 */
	private void drawElementsCold(boolean delete)
	{
		LayoutInflater inflater = LayoutInflater.from(cold.getContext());
		LinearLayout ll = (LinearLayout)cold.findViewById(R.id.linearLayout_other_methods_innerLayout);
		String[] arrayOfString = getResources().getStringArray(R.array.hypothermia_conciousArray);
		
		if(delete == true && ll.getChildAt(11) != null)
		{
				ll.removeViews(9, 6);
				ll.removeViews(4, 4);
		}
		
		int counter = 1;
		for(String currentString: arrayOfString)
		{
			View newCoolLookView = inflater.inflate(R.layout.cold_listviewelement, null);
			
			((TextView)newCoolLookView.findViewById(R.id.textView_others_cold_number)).setText(String.valueOf(counter));
			((TextView)newCoolLookView.findViewById(R.id.textView_others_cold_text)).setText(currentString);
			
			
			ll.addView(newCoolLookView,3 + counter++);
		}
		
		arrayOfString = getResources().getStringArray(R.array.hypothermia_unconciousArray);
		
		int counter2 = 1;
		for(String currentString: arrayOfString)
		{
			View newCoolLookView = inflater.inflate(R.layout.cold_listviewelement, null);
			
			((TextView)newCoolLookView.findViewById(R.id.textView_others_cold_number)).setText(String.valueOf(counter2));
			((TextView)newCoolLookView.findViewById(R.id.textView_others_cold_text)).setText(currentString);
			
			
			ll.addView(newCoolLookView,3 + counter + counter2++);
		}
	}
	
	/**
	 * Na određena mjesta ubacuje u taj view podatke iz array resursa
	 * 
	 * Uzima podatke iz {@link array#arraySnakeBiteEffects} resursa te iz njih dinamički
	 * kreira nove view-ove koje umetne u snake {@link #snake} view na određene pozicije.
	 * 
	 * @param delete koristi se za provjeru, potrebno je obrisati stare view-ove nebi li došlo do više istih view-a 
	 */
	private void drawElementsSnake(boolean delete)
	{
		LayoutInflater inflater = LayoutInflater.from(snake.getContext());
		LinearLayout ll = (LinearLayout)snake.findViewById(R.id.linearLayout_other_methods_innerLayoutSnake);
		String[] arrayOfString = getResources().getStringArray(R.array.arraySnakeBiteEffects);
	
		if(delete == true && ll.getChildAt(11) != null)
		{
				ll.removeViews(10, 7);
				ll.removeViews(4, 5);
		}
		
		int counter = 1;
		for(String currentString: arrayOfString)
		{
			View newCoolLookView = inflater.inflate(R.layout.cold_listviewelement, null);
			
			((TextView)newCoolLookView.findViewById(R.id.textView_others_cold_number)).setText(String.valueOf(counter));
			((TextView)newCoolLookView.findViewById(R.id.textView_others_cold_text)).setText(currentString);
			
			ll.addView(newCoolLookView,3 + counter++);
		}
		
		arrayOfString = getResources().getStringArray(R.array.arraySnakeBiteTreatment);
		
		int counter2 = 1;
		for(String currentString: arrayOfString)
		{
			View newCoolLookView = inflater.inflate(R.layout.cold_listviewelement, null);
			
			((TextView)newCoolLookView.findViewById(R.id.textView_others_cold_number)).setText(String.valueOf(counter2));
			((TextView)newCoolLookView.findViewById(R.id.textView_others_cold_text)).setText(currentString);
			
			
			ll.addView(newCoolLookView,3 + counter + counter2++);
		}
	}
	
	/**
	 * Na određena mjesta ubacuje u taj view podatke iz array resursa
	 * 
	 * Uzima podatke iz {@link array#arrayThunderHelpAdvices} resursa te iz njih dinamički
	 * kreira nove view-ove koje umetne u thunder {@link #thunder} view na određene pozicije.
	 * 
	 * @param delete koristi se za provjeru, potrebno je obrisati stare view-ove nebi li došlo do više istih view-a 
	 */
	private void drawElementsThunder(boolean delete)
	{
		LayoutInflater inflater = LayoutInflater.from(thunder.getContext());
		LinearLayout ll = (LinearLayout)thunder.findViewById(R.id.linearLayout_other_methods_innerLayoutThunder);
		String[] arrayOfString = getResources().getStringArray(R.array.arrayThunderHelpAdvices);
	
		if(delete == true && ll.getChildAt(6) != null)
		{
				ll.removeViews(4, 3);
		}
		
		int counter = 1;
		for(String currentString: arrayOfString)
		{
			View newCoolLookView = inflater.inflate(R.layout.cold_listviewelement, null);
			
			((TextView)newCoolLookView.findViewById(R.id.textView_others_cold_number)).setText(String.valueOf(counter));
			((TextView)newCoolLookView.findViewById(R.id.textView_others_cold_text)).setText(currentString);
			
			
			ll.addView(newCoolLookView,3 + counter++);
		}
	}
	/**
	 * Provjerava koji je tab aktivan te za taj tab stavi normalnu sliku , a za ostale pocrnjenu
	 */
	private void tabColorChanger()
	{
		SharedPreferences settings = getSharedPreferences(StorageClass.PREFS_NAME, MODE_PRIVATE);
		String tabSelected = settings.getString("tabs", "sod"); // ako ne postoji vrati "sod"
	
		Resources r = getResources();
		Drawable death = r.getDrawable(R.drawable.mandeathhidden);
		Drawable cold = r.getDrawable(R.drawable.manfrosthidden);
		Drawable thunder = r.getDrawable(R.drawable.manthunderhidden);
		Drawable snake = r.getDrawable(R.drawable.mansnakehidden);
		
		if(tabSelected.equals("sod"))
		{
			imgBtnSignsOfDeath.setImageDrawable(r.getDrawable(R.drawable.mandeath));
			
			imgBtnCold.setImageDrawable(cold);
			imgBtnSnake.setImageDrawable(snake);
			imgBtnThunderstruk.setImageDrawable(thunder);
		}
		else if(tabSelected.equals("cold"))
		{
			imgBtnCold.setImageDrawable(r.getDrawable(R.drawable.manfrost));
			
			imgBtnSignsOfDeath.setImageDrawable(death);
			imgBtnSnake.setImageDrawable(snake);
			imgBtnThunderstruk.setImageDrawable(thunder);
		}
		else if(tabSelected.equals("thunder"))
		{
			imgBtnThunderstruk.setImageDrawable(r.getDrawable(R.drawable.manthunder));
			
			imgBtnSignsOfDeath.setImageDrawable(death);
			imgBtnCold.setImageDrawable(cold);
			imgBtnSnake.setImageDrawable(snake);
		}
		else if(tabSelected.equals("snake"))
		{
			imgBtnSnake.setImageDrawable(r.getDrawable(R.drawable.mansnake));
			
			imgBtnSignsOfDeath.setImageDrawable(death);
			imgBtnCold.setImageDrawable(cold);
			imgBtnThunderstruk.setImageDrawable(thunder);
		}
	}
}
