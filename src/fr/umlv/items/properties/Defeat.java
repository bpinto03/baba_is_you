package fr.umlv.items.properties;

import fr.umlv.items.Item;

/**
 * Represents Defeat property.
 * */
public class Defeat extends Item{

	/**
	 * Defeat's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * */
	public Defeat(int column, int line) throws IllegalArgumentException {
		super(column, line, "images/Properties/Text_DEFEAT_0.gif", true, "Defeat");
	}
}
