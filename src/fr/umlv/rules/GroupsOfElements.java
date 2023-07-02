package fr.umlv.rules;

import java.util.HashSet;

/**
 * GroupsOfElements to regroup all type names of Element.
 */
public enum GroupsOfElements {
	/**
	 * Groups containing all nouns.
	 * */
	Nouns("Baba", "Flag", "Lava", "Rock", "Skull", "Wall", "Water", "Karl"),
	
	/**
	 * Groups containing all operators.
	 * */
	Operators("Is"),
	
	/**
	 * Groups containing all properties.
	 * */
	Properties("Defeat", "Hot", "Melt", "Push", "Sink", "Stop", "Win", "You", "Vanish");
	
	private HashSet<String> typeElements;
	
	/**
	 * GroupsOfElements's constructor.
	 * @param typeElement
	 * 		  Collector containing type names of a category (noun / operator / property).
	 * */
	private GroupsOfElements(String ... typeElement) {
		this.typeElements = new HashSet<String>();
		for(var type : typeElement)
			if(type != null)
				this.typeElements.add(type);
	}
	
	/**
	 * Getter of all type names.
	 * @return 
	 * 		  Set of type elements.
	 * */
	public HashSet<String> getTypeElements() {
		return this.typeElements;
	}
	
	/**
	 * Check if "type" is in a category.
	 * @param type
	 * 		  Type to Check.
	 * @return
	 * 		  true if type is in GroupsOfElements, else false.
	 * */
	public boolean containsType(String type) {
		return typeElements.contains(type);
	}
}
