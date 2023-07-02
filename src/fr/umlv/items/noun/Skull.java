package fr.umlv.items.noun;

import fr.umlv.items.Item;

/**
 * Represents Skull object.
 * */
public class Skull extends Item{
	
	/**
	 * Skull's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * @param text
	 * 		  true if item is a text, else false.
	 * */
	public Skull(int column, int line, boolean text) throws IllegalArgumentException {
		super(column, line, "images/NounsObjects/SKULL_0.gif", text, "Skull");
	    if(text)
			this.setImage("images/NounsText/Text_SKULL_0.gif");
	}
}
