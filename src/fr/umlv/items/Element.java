package fr.umlv.items;

import java.awt.Image;
import java.util.HashSet;

/**
 * Interface representing an Element.
 * */
public interface Element{
	/**
	 * toString's method of Item.
	 * @return
	 * 		   Representation of an item to string.
	 * */
	public String toString();

	/**
	 * Equals's method of Item.
	 * @param obj
	 *				Object to check if it's equal.
	 * @return
	 * 		   true if Object obj equals this, else false.
	 * */
	public boolean equals(Object obj);

	/**
	 * HashCode's method of Item.
	 * @return
	 * 		   HashCode.
	 * */
	public int hashCode();

	/**
	 * Setter of line.
	 * @param newLine
	 * 		  Line to set.
	 * */
	public void setLine(int newLine);

	/**
	 * Getter of line.
	 * @return
	 * 		   Current line.
	 * */
	public int getLine();

	/**
	 * Setter of column.
	 * @param newColumn
	 * 		  Column to set.
	 * */
	public void setColumn(int newColumn);

	/**
	 * Getter of column.
	 * @return
	 * 		   Current column.
	 * */
	public int getColumn();

	/**
	 * Set an image with his path to an item
	 * @param imagePath
	 * 		  Path of the image to set.
	 * */
	public void setImage(String imagePath);

	/**
	 * Getter of the item's image.
	 * @return
	 * 		  Item's image.
	 * */
	public Image getImage();

	/**
	 * Setter for the type name of the item.
	 * @param  typeName
	 * 		   Type name to set.
	 * */
	public void setTypeName(String typeName);

	/**
	 * Getter for the type name of the item.
	 * @return
	 * 		   Type name of the object.
	 * */
	public String getTypeName();

	/**
	 * Check if item is a text.
	 * @return boolean
	 * 		   true if the item is a text, else false.
	 * */
	public boolean isText();

	/**
	 * Getter of properties.
	 * @return
	 * 		   HashSet containing all properties.
	 * */
	public HashSet<String> getProperties();

	/**
	 * Remove a property of the item.
	 * @param property
	 * 		  String of the property to add.
	 * */
	public void removeProperty(String property);

	/**
	 * Add a property to the item.
	 * @param property
	 * 		  String of the property to add.
	 * */
	public void addProperty(String property);

	/**
	 * Set this into another element.
	 * @param typeNameElementToSet
	 * 		  New type of the element.
	 * */
	public void setNewElement(String typeNameElementToSet);
}
