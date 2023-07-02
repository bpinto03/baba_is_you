package fr.umlv.items.properties;

import fr.umlv.items.Item;

/**
 * Represents Push property.
 * */
public class Push extends Item{
	
	/**
	 * Push's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * */
	public Push(int column, int line) throws IllegalArgumentException {
		super(column, line, "images/Properties/Text_PUSH_0.gif", true, "Push");
	}
}
