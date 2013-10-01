package mcc.education.vuk.adapters;

import java.util.ArrayList;

import mcc.education.vuk.R;
import mcc.education.vuk.helpers.Parent;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * Pripremi Adapter za uporabu
 * 
 * Za dobivenu Listu elemenata , u mojem slučaju tu listu predstavlja Parent klasa 
 * postavlja izgled i sam sadržaj parent i child elemenata.
 * 
 * potreban "izgled" liste: 
 * 
 *ArrayList<Parent> mParent : 
 *sadržaj 
 *mParent[0] => Parent1 : ArrayList<String> child :=> child[0] dijete1 , child[1] dijete2 ... 
 *mParent[0] => Parent2 : ArrayList<String> child :=> child[0] dijete1 , child[1] dijete2 ... 
 * ...
 * 
 * @author silvio
 *
 */
public class SodExpendableListAdapter extends BaseExpandableListAdapter {

	private LayoutInflater inflater;
	private ArrayList<Parent> mParent;

	/**
	 * Postavlja context gdje će biti umetnuti elementi, te postavlja listu iz koje se dohvačaju podaci 
	 * 
	 * @param context context u kojem će se postaiti Adapter
	 * @param parent lista Parent elemenata koja služu kako bi se znalo koje djete je povezano sa kojim ocem
	 */
	public SodExpendableListAdapter(Context context, ArrayList<Parent> parent)
	{
		mParent = parent;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		return mParent.get(arg0).getArrayChildren().get(arg1);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		return arg1;
	}

	@Override
	public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
		if (view == null) 
			view = inflater.inflate(R.layout.other_methods_deathsigns_childelement, viewGroup,false);
		
		TextView textView = (TextView) view.findViewById(R.id.textView_sod_child);
		//"i" is the position of the parent/group in the list and
		//"i1" is the position of the child
		textView.setText(mParent.get(i).getArrayChildren().get(i1));

		//return the entire view
		return view;
	}

	@Override
	public int getChildrenCount(int arg0) {
		return mParent.get(arg0).getArrayChildren().size();
	}

	@Override
	public Object getGroup(int arg0) {
		return mParent.get(arg0).getTitle();
	}

	@Override
	public int getGroupCount() {
		return mParent.size();
	}

	@Override
	public long getGroupId(int arg0) {
		return arg0;
	}

	@Override
	public View getGroupView(int i, boolean arg1, View view, ViewGroup arg3) {
		if (view == null) 
			view = inflater.inflate(R.layout.other_methods_deathsigns_parentelement, arg3 ,false);
		
		TextView textView = (TextView) view.findViewById(R.id.textView_sod_parent_heading);
		//"i" is the position of the parent/group in the list
		textView.setText(getGroup(i).toString());
		//return the entire view
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		/* used to make the notifyDataSetChanged() method work */
		super.registerDataSetObserver(observer);
	}

}
