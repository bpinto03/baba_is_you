package fr.umlv.items.noun;

import fr.umlv.items.Item;

/**
 * Represents Karl object.
 * */
public class Karl extends Item{
	
	/**
	 * Karl's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * @param text
	 * 		  true if item is a text, else false.
	 * */
	public Karl(int column, int line, boolean text) throws IllegalArgumentException {
			super(column, line, "images/NounsObjects/KARL_0.gif", text, "Karl");
			
		    if(text)
				this.setImage("images/NounsText/Text_KARL_0.gif");
	}
	
}