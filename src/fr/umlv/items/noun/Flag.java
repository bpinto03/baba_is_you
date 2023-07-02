package fr.umlv.items.noun;

import fr.umlv.items.Item;

/**
 * Represents Flag object.
 * */
public class Flag extends Item{
	
	/**
	 * Flag's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * @param text
	 * 		  true if item is a text, else false.
	 * */
	public Flag(int column, int line, boolean text) throws IllegalArgumentException {
		super(column, line, "images/NounsObjects/FLAG_0.gif", text, "Flag");
	    if(text)
			this.setImage("images/NounsText/Text_FLAG_0.gif");
	}
}
