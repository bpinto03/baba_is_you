package fr.umlv.items.properties;

import fr.umlv.items.Item;

/**
 * Represents Win property.
 * */
public class Win extends Item{
	
	/**
	 * Win's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * */
	public Win(int column, int line) throws IllegalArgumentException {
		super(column, line, "images/Properties/Text_WIN_0.gif", true, "Win");
	}
}
