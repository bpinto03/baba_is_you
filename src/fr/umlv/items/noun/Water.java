package fr.umlv.items.noun;

import fr.umlv.items.Item;

/**
 * Represents Water object.
 * */
public class Water extends Item{
	
	/**
	 * Water's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * @param text
	 * 		  true if item is a text, else false.
	 * */
	public Water(int column, int line, boolean text) throws IllegalArgumentException {
		super(column, line, "images/NounsObjects/WATER_0.gif", text, "Water");
	    if(text)
			this.setImage("images/NounsText/Text_WATER_0.gif");
	}
}
