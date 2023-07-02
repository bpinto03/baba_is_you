package fr.umlv.rules;

import fr.umlv.board.Board;
import fr.umlv.items.Element;
import fr.umlv.player.Player;

/**
 * Class to construct rules for the game.
 * */
public class Rules {
	private Element left;
	private Element operator;
	private Element right;
	
	/**
	*Rules's constructor
	*
	*@param left
	*	Left element of a rule.
	*@param operator
	*	Operator element of a rule.
	*@param right
	*	Right element of a rule.
	*/
	public Rules(Element left, Element operator, Element right) {
		this.left = left;
		this.operator = operator;
		this.right = right;
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
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
		return result;
	}

	/**
	 * Equals's method of Item.
	 * @param obj
	 * 		  Object to check if it's equal.
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
		Rules other = (Rules) obj;
		if (left == null) {
			if (other.left != null)
				return false;
		}
		if (operator == null) {
			if (other.operator != null)
				return false;
		}
		if (right == null) {
			if (other.right != null)
				return false;
		}
		
		return other.left.getTypeName().equals(left.getTypeName())
		&& other.operator.getTypeName().equals(operator.getTypeName())
		&& other.right.getTypeName().equals(right.getTypeName());
	}
	
	/**
	 * toString's method of Item.
	 * @return
	 * 		   Representation of an item to string.
	 * */	
	@Override
	public String toString() {
		return this.left.getTypeName() + " " + this.operator.getTypeName() + " " + 	this.right.getTypeName();
	}
	
	/**
	 * Check if left + operator + right is a rule.
	 * @param left
	 * 		  Left type element of a rule.
	 * @param operator
	 * 		  Operator type element of a rule.
	 * @param right
	 * 		  Right type element of a rule.
	 * @return
	 * 		  True if left + operator + right is a rule, else false.
	 * */
	public static boolean isRule(String left, String operator, String right) {
		return GroupsOfElements.Nouns.containsType(left)
		&& GroupsOfElements.Operators.containsType(operator)
		&& (GroupsOfElements.Properties.containsType(right) || GroupsOfElements.Nouns.containsType(right));
	}
	
	/**
	 * Check if the rule is valid. (Noun Operator Noun/Property).
	 * @return
	 * 		   true if the rule is valid, else false.
	 * */
	public boolean isValid() {
		return (((operator.getColumn() == left.getColumn() + 1 && right.getColumn() == left.getColumn() + 2)
				&& (operator.getLine() == left.getLine() && left.getLine() == right.getLine()))
				|| ((operator.getLine() == left.getLine() + 1 && right.getLine() == left.getLine() + 2))
				&& (operator.getColumn() == left.getColumn() && right.getColumn() == left.getColumn()));
	}
	
	/**
	 * Apply the rule to the gameBoard or player.
	 * @param typeLeft
	 * 		  Type name of left element in rule.
	 * @param typeRight
	 * 		  Type name of right element in rule.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param player
	 * 		  Player.
	 * */
	public static void applyRule(String typeLeft, String typeRight, Board gameBoard, Player player) {
		if(typeRight.equals("You"))
			player.add(typeLeft);
		else if(GroupsOfElements.Properties.containsType(typeRight))
			if(typeRight.equals("Vanish"))
				gameBoard.removeSetWithCategory(typeLeft);
			else
				gameBoard.getElementsByCategory(typeLeft).stream().forEach(element -> element.addProperty(typeRight));
		else {
			gameBoard.getElementsByCategory(typeLeft).stream().forEach(element -> element.setNewElement(typeRight));
		    gameBoard.add(typeRight, gameBoard.getElementsByCategory(typeLeft));
		    gameBoard.removeSetWithCategory(typeLeft);
		}
	}
	
	/**
	 * Apply the actual rule.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param player
	 * 		  Player.
	 */
	public void applyRule(Board gameBoard, Player player) {
		Rules.applyRule(left.getTypeName(), right.getTypeName(), gameBoard, player);
	}
	
	/**
	 * Cancel a rule.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param player
	 * 		  Player.
	 * */
	public void CancelRule(Board gameBoard, Player player) {
		var typeRight = right.getTypeName();
		var typeLeft = left.getTypeName();
		if(typeRight == "You")
			player.remove(typeLeft);
		else if(GroupsOfElements.Properties.containsType(typeRight))
			gameBoard.getElementsByCategory(typeLeft).stream().forEach(element -> element.removeProperty(typeRight));
	}
}
