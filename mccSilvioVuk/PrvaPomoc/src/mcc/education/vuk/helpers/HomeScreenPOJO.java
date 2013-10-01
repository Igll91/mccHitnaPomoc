package mcc.education.vuk.helpers;

import mcc.education.vuk.adapters.HomeScreenAdapter;


/**
 * Jednostavna Klasa sa 2 atributa
 * 
 * Služi kako bi se lakše pripremili i grupirali podaci koji se prosljeđuju u listView
 * {@link HomeScreenAdapter} 
 * @author silvio
 *
 */
public class HomeScreenPOJO {

	private String text;
	private String pictureResource; 
	
	public HomeScreenPOJO(){}
	
	public HomeScreenPOJO(String text, String pictureResource) {
		this.text = text;
		this.pictureResource = pictureResource;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getPictureResource() {
		return pictureResource;
	}
	
	public void setPictureResource(String pictureResource) {
		this.pictureResource = pictureResource;
	}
	
	
}
