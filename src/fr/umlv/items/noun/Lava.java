package fr.umlv.items.noun;

import fr.umlv.items.Item;

/**
 * Represents Lava object.
 * */
public class Lava extends Item{
	
	/**
	 * Lava's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * @param text
	 * 		  true if item is a text, else false.
	 * */
	public Lava(int column, int line, boolean text) throws IllegalArgumentException {
		super(column, line, "images/NounsObjects/LAVA_0.gif", text, "Lava");
	    if(text)
			this.setImage("images/NounsText/Text_LAVA_0.gif");
	}
}
