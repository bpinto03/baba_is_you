package fr.umlv.items.properties;

import fr.umlv.items.Item;

/**
 * Represents Hot property.
 * */
public class Hot extends Item{

	/**
	 * Hot's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * */
	public Hot(int column, int line) throws IllegalArgumentException {
		super(column, line, "images/Properties/Text_HOT_0.gif", true, "Hot");
	}
}
