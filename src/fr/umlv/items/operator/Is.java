package fr.umlv.items.operator;

import fr.umlv.items.Item;

/**
 * Represent Is operator.
 * */
public class Is extends Item{

	/**
	 * Is's constructor.
	 * @param column
	 * 		  Column of item.
	 * @param line
	 * 		  Line of item.
	 * */
	public Is(int column, int line) throws IllegalArgumentException {
		super(column, line, "images/Operators/Text_IS_0.gif", true, "Is");
	}
}
