package fr.umlv.items.properties;

import fr.umlv.items.Item;

/**
 * Represents Melt property.
 * */
public class Melt extends Item{
	
	/**
	 * Melt's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * */
	public Melt(int column, int line) throws IllegalArgumentException {
		super(column, line, "images/Properties/Text_MELT_0.gif", true, "Melt");
	}
}