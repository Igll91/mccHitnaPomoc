package mcc.education.vuk.helpers;

import java.util.ArrayList;

import mcc.education.vuk.OtherMethodsActivity;

/**
 * Jednostavna klasa za privremenu pohranu podataka	
 * 
 * služi kako bi se podatke pratilo i formiralo za lakšu upotrebu u expandableListi 
 * {@link OtherMethodsActivity#setSODExpandableList}
 * 
 * svaki otac ima listu djece. 
 * 
 * OTAC1   
 *   -> dijete1  
 *   -> dijete2
 *   -> dijete3
 *   -> dijete4
 * 
 * OTAC2
 *   -> dijete12 
 *   -> dijete22
 *   -> dijete32
 *   -> dijete42
 * 
 * @author silvio
 *
 */
public class Parent {
	
	private String mTitle;
    private ArrayList<String> mArrayChildren;
 
    public String getTitle() {
        return mTitle;
    }
 
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
 
    public ArrayList<String> getArrayChildren() {
        return mArrayChildren;
    }
 
    public void setArrayChildren(ArrayList<String> mArrayChildren) {
        this.mArrayChildren = mArrayChildren;
    }
}
