package fr.umlv.items;


import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.util.HashSet;
import java.util.Objects;



/**
 * The abstract class Items represents all items which are Items.
 * */
public abstract class Item implements Element {
	private int column;
	private int line;
	private String typeName;
	private Image image;
	private boolean text;
	private HashSet<String> properties;

	/**
	 * This constructor initializes an Item (typically for invocation by subclass constructors).
	 * It performs verifications on the parameters, to ensure that they provide a correct state of the created object.
	 *
	 * @param column
	 * 		  Column of the Item.
	 * @param line
	 * 		  Line of the Item.
	 * @param imagePath
	 * 		  Path of the image that represents the Items.
	 * @param text
	 * 		  Boolean to know if the item is a text or not.
	 * @param typeName
	 * 		  Type name of the item.
	 * @throws IllegalArgumentException if column or line are out of context.
	 * */
	public Item(int column, int line, String imagePath, boolean text, String typeName) throws IllegalArgumentException{
		Objects.requireNonNull(imagePath, "imagePath can not be null");
		if(column < 0)
			throw new IllegalArgumentException("column < 0: out of context");
		if(line < 0)
			throw new IllegalArgumentException("line < 0: out of context");
		this.typeName = Objects.requireNonNull(typeName, "typeName can not be null");
		this.column = column;
		this.line = line;
		try {
			image = ImageIO.read(new FileInputStream(imagePath));
		} catch(IOException e) {
			System.err.println("Image '" + imagePath + "' does not exists.\n" + e.getMessage());
			System.exit(1);
		}
		this.text= text;
		this.properties = new HashSet<String>();
	}

	/**
	 * toString's method of Item.
	 * @return
	 * 		   Representation of an item to string.
	 * */
	@Override
	public String toString() {
		var ret = "(" + column +", " + line +") " + typeName;
		if(text)
			return ret + " texte";
		return ret + " item";
	}

	/**
	 * HashCode's method of Item.
	 * @return
	 * 		   HashCode.
	 * */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + line;
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
		result = prime * result + (text ? 1231 : 1237);
		result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
		return result;
	}

	/**
	 * Equals's method of Item.
	 * @param obj
	 *				Object to check if it's equal.
	 * @return
	 * 		   true if Object obj equals this, else false.
	 * */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (column != other.column)
			return false;
		if (line != other.line)
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		if (text != other.text)
			return false;
		if (typeName == null) {
			if (other.typeName != null)
				return false;
		} else if (!typeName.equals(other.typeName))
			return false;
		return true;
	}

	/**
	 * Check if item is a text.
	 * @return boolean
	 * 		   true if the item is a text, else false.
	 * */
	public boolean isText() {
		return text;
	}

	/**
	 * Set an array of properties to an item
	 * @param properties
	 * 		  HashSet of properties to set.
	 * */
	@SuppressWarnings("unchecked")
	public void setProperties(HashSet<String> properties) {
		this.properties = (HashSet<String>) properties.clone();
	}

	/**
	 * Remove a property of the item.
	 * @param property
	 * 		  String of the property to add.
	 * */
	public void removeProperty(String property) {
		properties.remove(property);
	}

	/**
	 * Add a property to the item.
	 * @param property
	 * 		  String of the property to add.
	 * */
	public void addProperty(String property) {
		properties.add(property);
	}

	/**
	 * Getter of properties.
	 * @return
	 * 		   HashSet containing all properties.
	 * */
	public HashSet<String> getProperties(){
		return properties;
	}

	/**
	 * Set an image with his path to an item
	 * @param imagePath
	 * 		  Path of the image to set.
	 * */
	public void setImage(String imagePath) {
		try {
			image = ImageIO.read(new FileInputStream(imagePath));
		} catch(IOException e) {
			System.err.println("Image '" + imagePath + "' does not exists.\n" + e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Getter of the item's image.
	 * @return
	 * 		  Item's image.
	 * */
	public Image getImage() {
		return image;
	}

	/**
	 * Setter of line.
	 * @param newLine
	 * 		  Line to set.
	 * */
	public void setLine(int newLine) {
		this.line = newLine;
	}

	/**
	 * Getter of line.
	 * @return
	 * 		   Current line.
	 * */
	public int getLine() {
		return line;
	}

	/**
	 * Setter of column.
	 * @param newColumn
	 * 		  Column to set.
	 * */
	public void setColumn(int newColumn) {
		this.column = newColumn;
	}

	/**
	 * Getter of column.
	 * @return
	 * 		   Current column.
	 * */
	public int getColumn() {
		return column;
	}

	/**
	 * Setter for the type name of the item.
	 * @param  typeName
	 * 		   Type name to set.
	 * */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * Getter for the type name of the item.
	 * @return
	 * 		   Type name of the object.
	 * */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * Change the image, typeName from another element and clear properties to set a new type of element.
	 * @param object
	 * 		  ObjectConstructor to copy image and type name.
	 * */
	private void setNewElement(ObjectConstructor object) {
		setImage(object.getImagePath());
		this.typeName = object.getTypeObject();
		properties.clear();
	}

	/**
	 * Set this into another element.
	 * @param typeNameElementToSet
	 * 		  New type of the element.
	 * */
	public void setNewElement(String typeNameElementToSet) {
		switch(typeNameElementToSet) {
		case "Baba": this.setNewElement(ObjectConstructor.Baba); break;
		case "Lava": this.setNewElement(ObjectConstructor.Lava); break;
		case "Flag": this.setNewElement(ObjectConstructor.Flag); break;
		case "Rock": this.setNewElement(ObjectConstructor.Rock); break;
		case "Skull": this.setNewElement(ObjectConstructor.Skull); break;
		case "Wall": this.setNewElement(ObjectConstructor.Wall); break;
		case "Water": this.setNewElement(ObjectConstructor.Water); break;
		case "Karl": this.setNewElement(ObjectConstructor.Karl); break;
		default: break;
		}
	}
}
