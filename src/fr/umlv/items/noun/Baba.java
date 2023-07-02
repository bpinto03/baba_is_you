package fr.umlv.items.noun;

import fr.umlv.items.Item;

/**
 * Represents Baba object.
 * */
public class Baba extends Item{
	
	/**
	 * Baba's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * @param text
	 * 		  true if item is a text, else false. 
	 * */
	public Baba(int column, int line, boolean text) throws IllegalArgumentException {
			super(column, line, "images/NounsObjects/BABA_0.gif", text, "Baba");
			
		    if(text)
				this.setImage("images/NounsText/Text_BABA_0.gif");
	}
	
}
