package fr.umlv.items.properties;

import fr.umlv.items.Item;

/**
 * Represents Vanish property.
 * */
public class Vanish extends Item{

	/**
	 * Vanish's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * */
	public Vanish(int column, int line) throws IllegalArgumentException {
		super(column, line, "images/Properties/Text_VANISH_0.gif", true, "Vanish");
	}
}
