package fr.umlv.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

import fr.umlv.items.Element;
import fr.umlv.player.Player;
import fr.umlv.rules.Rules;

/**
 * This class regroup every items and rules of the level in an Map with String of type object and Set of elements and a Set of rules.
 * */
public class Board {
	private Map<String, Set<Element>> dataBoard;
	private Set<Rules> rulesOfGame;
	private int columnLimit;
	private int lineLimit;

	/**
	 * Board's constructor.
	 * */
	public Board(){
		this.dataBoard = new HashMap<String, Set<Element>>();
		this.rulesOfGame = new HashSet<Rules>();
	}


	/**
	 * Getter of columnLimit.
	 * @return
	 * 		  Column limit of board.
	 * */
	public int getColumnLimit() {
		return columnLimit;
	}


	/**
	 * Setter of columnLimit.
	 * @param columnLimit
	 * 		  Column limit to set.
	 * @throws IllegalArgumentException
	 * 		   columnLimit below 0
	 * */
	public void setColumnLimit(int columnLimit) throws IllegalArgumentException{
		if(columnLimit < 0)
			throw new IllegalArgumentException("columnLimit < 0");
		this.columnLimit = columnLimit;
	}


	/**
	 * Getter of lineLimit.
	 * @return
	 * 		  Line limit of board.
	 * */
	public int getLineLimit() {
		return lineLimit;
	}


	/**
	 * Setter of lineLimit.
	 * @param lineLimit
	 * 		  Line limit to set.
	 * @throws IllegalArgumentException
	 * 		   lineLimit below 0
	 * */
	public void setLineLimit(int lineLimit) {
		if(lineLimit < 0)
			throw new IllegalArgumentException("lineLimit < 0");
		this.lineLimit = lineLimit;
	}

	/**
	 * Getter of rules of the board.
	 * @return rulesOfGame
	 * 		   Set containing valid rules of the game.
	 * */
	public Set<Rules> getRulesOfGame(){
		return rulesOfGame;
	}

	/**
	 * Getter of all elements in the data board.
	 * @return elements of data board
	 * 		   Collection containing all sets of elements.
	 * */
	public Collection<Set<Element>> getValues(){
		return dataBoard.values();
	}

	/**
	 * Getter of the first element in a set. (Used to get coords of an object in a game box)
	 * @param box
	 * 		  Set of elements in a box.
	 * @return Element
	 * 		   First element in a Set.
	 * */
	public static Element getElementOfBox(Set<Element> box) {
		List<Element> list = new ArrayList<Element>(box);
		return list.get(0);
	}

	/**
	 * Get all elements with type objectCategory.
	 * @param objectCategory
	 * 		  Type name of the object.
	 * @return
	 * 		  Empty HashSet if the data board does not contain the element, else HashSet of the category.
	 * */
	public Set<Element> getElementsByCategory(String objectCategory){
		var ret = dataBoard.get(objectCategory);
		return  ret == null ? new HashSet<Element>() : ret;
	}

	/**
	 * Get all elements in a specific position (column, line).
	 * @param column
	 * 		  Column of the position.
	 * @param line
	 * 		  Line of the position.
	 * @return
	 * 		  Empty Set if the data board does not contain elements, else Set with elements where position equals to (column, line).
	 * */
	public Set<Element> getElementsByCoords(int column, int line) {
		return dataBoard.values().stream()
		.flatMap(setOfElements -> setOfElements.stream())
		.filter(element -> element.getColumn() == column && element.getLine() == line)
		.collect(Collectors.toSet());
	}



	/**
	 * Add a collection of elements into the data board.
	 * @param objectCategory
	 * 		  Type name of the object.
	 * @param element
	 * 		  Element to add.
	 * */
	public void add(String objectCategory, Element element) {
		if(dataBoard.get(objectCategory) == null) {  //If the category is not yet in data board, create a new HashSet<Element> for the category.
			var newElementsCategory = new HashSet<Element>();
			newElementsCategory.add(element);
			dataBoard.put(objectCategory, newElementsCategory);
		}
		else
			dataBoard.get(objectCategory).add(element);
	}

	/**
	 * Add a Set containing elements into the data board.
	 * @param objectCategory
	 * 		  Type name of the object.
	 * @param elements
	 * 		  Set of elements to add.
	 * */
	public void add(String objectCategory, Set<Element> elements) {
		if(elements != null)
			if(dataBoard.get(objectCategory) == null) {		//If the category is not yet in data board, create a new HashSet<Element> for the category.
				var newElementsCategory = new HashSet<Element>();
				newElementsCategory.addAll(elements);
				dataBoard.put(objectCategory, newElementsCategory);
			}
			else
				dataBoard.get(objectCategory).addAll(elements);
	}

	/**
	 * Clear the board.
	 * */
	public void clear() {
		dataBoard.clear();
		rulesOfGame.clear();
	}

	/**
	 * Recreate a data board without duplications using streams.
	 * */
	public void actualiseDataBoard() {
		dataBoard = dataBoard.keySet().stream()
		.collect(Collectors.toMap(key -> key, key -> dataBoard.get(key).stream().collect(Collectors.toSet())));  //Destroy duplications by collecting a new Map<String, Set<Element>>
	}

	/**
	 * Remove a category of items.
	 * @param objectCategory
	 * 		  Type name of an object.
	 * */
	public void removeSetWithCategory(String objectCategory) {
		dataBoard.remove(objectCategory);
	}

	/**
	 * Remove a rule and cancel it by creating a new set without the rule to remove with streams.
	 * @param ruleToRemove
	 * 		  Rule to remove and cancel.
	 * @param player
	 * 		  Player to add or remove a player when rule is of type "Noun is You".
	 * */
	public void removeRule(Rules ruleToRemove, Player player) {
		var rules = this.rulesOfGame.stream()
		.filter(rule -> rule.equals(ruleToRemove) == false)
		.collect(Collectors.toSet());  //set without the rule to remove
		ruleToRemove.CancelRule(this, player); //Cancel the rule to remove
		this.rulesOfGame = rules;
	}

	/**
	 * Remove an element by creating a new set of elements without the element to remove with streams.
	 * @param elementToRemove
	 * 		  Element to remove.
	 * */
	public void removeElement(Element elementToRemove) {
		var setOfCategoryElements = this.getElementsByCategory(elementToRemove.getTypeName()).stream()
									.filter(element -> element.equals(elementToRemove) == false)
									.collect(Collectors.toSet()); //set containing all element with elementToRemove's category but without elementToRemove
		this.removeSetWithCategory(elementToRemove.getTypeName());	//remove the set with elementToRemove
		this.add(elementToRemove.getTypeName(), setOfCategoryElements);	//add the set without elementToRemove
	}
}
