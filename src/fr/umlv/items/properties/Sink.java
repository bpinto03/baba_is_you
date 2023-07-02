package fr.umlv.items.properties;

import fr.umlv.items.Item;

/**
 * Represents Sink property.
 * */
public class Sink extends Item{

	/**
	 * Sink's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * */
	public Sink(int column, int line) throws IllegalArgumentException {
		super(column, line, "images/Properties/Text_SINK_0.gif", true, "Sink");
	}
}
