package fr.umlv.items.properties;

import fr.umlv.items.Item;

/**
 * Represents Stop property.
 * */
public class Stop extends Item{

	/**
	 * Stop's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * */
	public Stop(int column, int line) throws IllegalArgumentException {
		super(column, line, "images/Properties/Text_STOP_0.gif", true, "Stop");
	}
}
