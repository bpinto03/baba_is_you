package fr.umlv.items.properties;

import fr.umlv.items.Item;

/**
 * Represents You property.
 * */
public class You extends Item{

	/**
	 * You's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * */
	public You(int column, int line) throws IllegalArgumentException {
		super(column, line, "images/Properties/Text_YOU_0.gif", true, "You");
	}
}
