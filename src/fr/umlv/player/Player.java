package fr.umlv.player;

import java.util.HashSet;

/**
 * This Class represents elements that the user can moove. @see fr.umlv.items.Element
 **/
public class Player{
	private HashSet<String> players;
	
	/**
	 * Player's object constructor.
	 * */
	public Player() {
		this.players = new HashSet<String>();
	}
	
	/**
	 * Getter of players.
	 * @return 
	 * 		   HashSet of strings containing type name object which the player can control.
	 * */
	public HashSet<String> getPlayers(){
		return players;
	}
	
	/**
	 * Add an element's type name to the player.
	 * @param elemTypeName
	 * 		  Type name of element to add.
	 * */
	public void add(String elemTypeName) {
		if(players.contains(elemTypeName) == false)
			players.add(elemTypeName);
	}
	
	/**
	 * remove an element's type name.
	 * @param elemTypeName
	 * 		  Type name of elements to remove.
	 * */
	public void remove(String elemTypeName) {
		players.remove(elemTypeName);
	}
}
