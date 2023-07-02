package fr.umlv.items.noun;

import fr.umlv.items.Item;

/**
 * Represents Wall object.
 * */
public class Wall extends Item{
	
	/**
	 * Wall's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * @param text
	 * 		  true if item is a text, else false.
	 * */
	public Wall(int column, int line, boolean text) throws IllegalArgumentException {
		super(column, line, "images/NounsObjects/WALL_0.gif", text, "Wall");
	    if(text)
			this.setImage("images/NounsText/Text_WALL_0.gif");
	}
}
