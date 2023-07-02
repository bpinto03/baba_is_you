package fr.umlv.items.noun;

import fr.umlv.items.Item;

/**
 * Represents Rock object.
 * */
public class Rock extends Item {
	
	/**
	 * Rock's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * @param text
	 * 		  true if item is a text, else false.
	 * */
	public Rock(int column, int line, boolean text) throws IllegalArgumentException {
		super(column, line, "images/NounsObjects/ROCK_0.gif", text, "Rock");
	    if(text)
			this.setImage("images/NounsText/Text_ROCK_0.gif");
	}
}
