package fr.umlv.control;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.umlv.zen5.*;
import fr.umlv.zen5.Event.Action;
import fr.umlv.display.Graphic;

import fr.umlv.player.Player;
import fr.umlv.board.Board;
import fr.umlv.items.Element;
import fr.umlv.loader.LevelLoader;
import fr.umlv.rules.GroupsOfElements;
import fr.umlv.rules.Rules;
/**
 * The Controller class is the class with the structure of the game.
 */
public class Controller {

	/**
	 * Check if coordinates of an element are in the play area.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param column
	 * 		  Column to check.
	 * @param line
	 * 		  Line to check.
	 * @return boolean
	 * 		   true if coordinates are in the play area, else false.
	 * */
	public static boolean elemInPlayArea(Board gameBoard, int column, int line) {
		return 0 <= column && column < gameBoard.getColumnLimit() && 0 <= line && line < gameBoard.getLineLimit();
	}

	/**
	 * Check if one of the elements in a box of an element contain an element with the property to check.
	 * @param elementsInBox
	 * 		  Box containing elements to check.
	 * @param propertyToCheck
	 * 		  Name of the property to check.
	 * @return boolean
	 * 		   true if an element of the box contain the property, else false.
	 * */

	public static boolean checkProperty(Set<Element> elementsInBox, String propertyToCheck) {
		return elementsInBox.stream().anyMatch(element -> element.getProperties().contains(propertyToCheck));
	}

	/**
	 * Check if an element can be updated.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param elementToCheck
	 * 		  Element to check.
	 * @param moveColumn
	 * 		  Equals to -1 if d is left.
	 * 					 0 if d is up or down.
	 * 					 1 if d is right.
	 * @param moveLine
	 * 	      Equals to -1 if d is up.
	 * 				     0 if d is right or left.
	 * 					 1 if d is down.
	 * @return boolean
	 * 		   true if the move is possible, else false.
	 * */
	public static boolean moveIsPossible(Board gameBoard, Element elementToCheck, int moveColumn, int moveLine) {
		var nextBox = gameBoard.getElementsByCoords(elementToCheck.getColumn() + moveColumn, elementToCheck.getLine() + moveLine); //Box where the player wants to move
		return Controller.checkProperty(nextBox, "Stop") == false && Controller.checkProperty(nextBox, "Push") == false && Controller.elemInPlayArea(gameBoard, elementToCheck.getColumn() + moveColumn, elementToCheck.getLine() + moveLine);
	}

	/**
	 * Push elements in the box where the player wants to go into the next box, it check recursively if the elements are pushable and update them if they are.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param elementsInNextBox
	 * 		  Elements to check if they are pushable, it start by the box where the player wants to go.
	 * @param moveColumn
	 * 		  Equals to -1 if d is left.
	 * 					 0 if d is up or down.
	 * 					 1 if d is right.
	 * @param moveLine
	 * 	      Equals to -1 if d is up.
	 * 				     0 if d is right or left.
	 * 					 1 if d is down.
	 * */
	public static void push(Board gameBoard, Set<Element> elementsInNextBox, int moveColumn, int moveLine) {
		if(elementsInNextBox.isEmpty() == false && Controller.checkProperty(elementsInNextBox, "Push"))
			Controller.push(gameBoard, gameBoard.getElementsByCoords(Board.getElementOfBox(elementsInNextBox).getColumn() + moveColumn, Board.getElementOfBox(elementsInNextBox).getLine() + moveLine), moveColumn, moveLine);

		for(var elements : elementsInNextBox) {
			if(elements.getProperties().contains("Push") && Controller.moveIsPossible(gameBoard, elements, moveColumn, moveLine))
				Controller.updateElement(elements, moveColumn, moveLine);
		}
	}

	/**
	 * Destroy elements with melt property which are on an element with Hot property.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param hotElements
	 * 		  Set containing elements with Hot property.
	 * */
	public static void applyHot(Board gameBoard, Set<Element> hotElements) {
		for(var elements : hotElements) {
			var actualBox = gameBoard.getElementsByCoords(elements.getColumn(), elements.getLine()).stream()
							 .filter(elementToCheck -> elementToCheck.getProperties().contains("Melt"))
							 .collect(Collectors.toSet());
			if(actualBox.isEmpty() == false)
				for(var elementToRemove : actualBox)
					gameBoard.removeElement(elementToRemove);
		}
	}

	/**
	 * Destroy all elements in an element with Sink property.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param sinkElements
	 * 		  Set containing elements with Sink property.
	 * */
	public static void applySink(Board gameBoard, Set<Element> sinkElements) { //stream ?

		for(var elements : sinkElements) {
			var actualBox = gameBoard.getElementsByCoords(elements.getColumn(), elements.getLine()).stream()
					 				  .collect(Collectors.toSet());
			if(actualBox.isEmpty() == false && actualBox.size() > 1)
				for(var elementToRemove : actualBox)
					gameBoard.removeElement(elementToRemove);
		}
	}

	/**
	 * Destroy all elements controlled by player in an element with Defeat property.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param player
	 * 		  Player.
	 * */
	public static void applyDefeat(Board gameBoard, Player player) {
		var elementsPlayer = player.getPlayers().stream()
		.flatMap(playerType -> gameBoard.getElementsByCategory(playerType).stream())
		.filter(element -> Controller.checkProperty(gameBoard.getElementsByCoords(element.getColumn(), element.getLine()), "Defeat"))
		.collect(Collectors.toSet());

		//Remove player in elements with defeat property.
		for(var element : elementsPlayer)
			gameBoard.removeElement(element);

		//Check if there is player
		for(var playerType : player.getPlayers())
			if(gameBoard.getElementsByCategory(playerType).isEmpty())
				player.remove(playerType);
	}

	/**
	 * Check if a rule has the good structure (Noun, operator, Noun/Property) and add it to rules Set of gameBoard.
	 * @see fr.umlv.board.Board
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param moveColumn
	 * 		  Equals to -1 if d is left.
	 * 					 0 if d is up or down.
	 * 					 1 if d is right.
	 * @param moveLine
	 * 	      Equals to -1 if d is up.
	 * 				     0 if d is right or left.
	 * 					 1 if d is down.
	 * */
	public static void checkRules(Board gameBoard, int moveColumn, int moveLine) {
		Element left;
		Element operator = null;
		Element right = null;
		for(var text : gameBoard.getElementsByCategory("Texts")) { //For all texts in gameBoard
			if(GroupsOfElements.Nouns.containsType(text.getTypeName())) { //if text is a noun
				left = text;
				var buffer = gameBoard.getElementsByCoords(text.getColumn() + moveColumn, text.getLine() + moveLine).stream()
				.filter(element -> element.isText() && GroupsOfElements.Operators.containsType(element.getTypeName()))
				.collect(Collectors.toList()); //buffer of texts for a rule, it's containing operators in the next box
				if(buffer.isEmpty() == false) {  //If there is a Left element and an operator check the next box
					operator = buffer.get(0);	//Take the first operator in the list
					buffer = gameBoard.getElementsByCoords(text.getColumn() + moveColumn * 2, text.getLine() + moveLine * 2).stream()
					.filter(element -> element.isText() && (GroupsOfElements.Properties.containsType(element.getTypeName()) || GroupsOfElements.Nouns.containsType(element.getTypeName())))
					.collect(Collectors.toList());	//buffer of texts for a rule, it's containing right element of the rule in the box next to operator (if there is an operator).
					if(buffer.isEmpty() == false) {		//If there is Left element , operator, Right element for the rule.
						right = buffer.get(0);
						gameBoard.getRulesOfGame().add(new Rules(left, operator, right));
					}
				}
			}
		}
	}

	/**
	 * Add valid rules into Set of rules.
	 * @param gameBoard
	 * 		  Board of the game.
	 * */
	public static void addValidRules(Board gameBoard) {
		Controller.checkRules(gameBoard, 1, 0); //Horizontal rules
		Controller.checkRules(gameBoard, 0, 1); //Vertical rules
	}

	/**
	 * Check if the level is loose.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param player
	 * 		 Player containing type name of elements he can control.
	 * @return boolean
	 * 		   true if the position where the player is contain an element with win, else false.
	 * */
	public static boolean isDefeat(Board gameBoard, Player player) {
		return player.getPlayers().isEmpty();
	}

	/**
	 * Check if the level is win.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param player
	 * 		  Player containing type name of elements he can control.
	 * @return
	 * 		   true if the position where the player is contain an element with win, else false.
	 * */
	public static boolean isWin(Board gameBoard, Player player) {
		return player.getPlayers().stream()
		.anyMatch(typeNamePlayer -> gameBoard.getElementsByCategory(typeNamePlayer).stream()
									.anyMatch(elements -> Controller.checkProperty(gameBoard.getElementsByCoords(elements.getColumn(), elements.getLine()), "Win")));
	}

	/**
	 * Manage rules by removing rules that are not in game, adding rules in game and apply them.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param p
	 * 		 Player containing type name of elements he can control.
	 * */
	public static void updateRules(Board gameBoard, Player p) {
		//remove rules
		gameBoard.getRulesOfGame().stream().filter(rule -> rule.isValid() == false).forEach(rule -> gameBoard.removeRule(rule, p));
		//add rules
		Controller.addValidRules(gameBoard);
		//apply rules
		gameBoard.getRulesOfGame().stream().forEach(rule -> rule.applyRule(gameBoard, p));
	}

	/**
	 * Update an element by adding moveColumn or moveLine to his coords.(equals to -1, 0 or 1 depending on the direction).
	 * @param element
	 * 		  Element to update.
	 * @param moveColumn
	 * 		  Equals to -1 if d is left.
	 * 					 0 if d is up or down.
	 * 					 1 if d is right.
	 * @param moveLine
	 * 	      Equals to -1 if d is up.
	 * 				     0 if d is right or left.
	 * 					 1 if d is down.
	 *
	 * */
	public static void updateElement(Element element, int moveColumn, int moveLine) {
		element.setColumn(element.getColumn() + moveColumn);
		element.setLine(element.getLine() + moveLine);
	}

	/**
	 * Update all elements that represents the player if they can be updated.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param p
	 * 	  	  Player to update.
	 * @param moveColumn
	 * 		  Equals to -1 if d is left.
	 * 					 0 if d is up or down.
	 * 					 1 if d is right.
	 * @param moveLine
	 * 	      Equals to -1 if d is up.
	 * 				     0 if d is right or left.
	 * 					 1 if d is down.
	 * */
	public static void updatePlayer(Board gameBoard, Player p, int moveColumn, int moveLine) {
		p.getPlayers().stream()
					  .map(groupsOfElement -> gameBoard.getElementsByCategory(groupsOfElement))
					  .forEach(groupsOfElement -> groupsOfElement.forEach(element -> {
						  var elementsAftermove = gameBoard.getElementsByCoords(element.getColumn() + moveColumn, element.getLine() + moveLine); //Get elements where the user wants to go (empty HashSet if there is not elements).

						  if(elementsAftermove.isEmpty() == false) //If there are elements, push them if it is pushable.
							  Controller.push(gameBoard, elementsAftermove, moveColumn, moveLine);

						  if(Controller.moveIsPossible(gameBoard, element, moveColumn, moveLine)) //Check if element that the player wants to move can move.
							  updateElement(element, moveColumn, moveLine);
					  }));
	}

	/**
	 * Actualize the game by updating and applying rules.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param player
	 * 	  	  Player to update.
	 * */
	public static void actualiseGame(Board gameBoard, Player player) {
		//update rules
		Controller.updateRules(gameBoard, player);

		//Apply sink
		var sinkElements = gameBoard.getValues().stream()
						   .filter(elements -> elements.isEmpty() == false && Board.getElementOfBox(elements).getProperties().contains("Sink"))
						   .collect(Collectors.toList());
		for(var elementWithSink : sinkElements)
			Controller.applySink(gameBoard, elementWithSink);

		//Apply Hot
		var hotElements = gameBoard.getValues().stream()
						  .filter(elements -> elements.isEmpty() == false && Board.getElementOfBox(elements).getProperties().contains("Hot"))
						  .collect(Collectors.toList());
		for(var elementWithHot : hotElements)
			Controller.applyHot(gameBoard, elementWithHot);
		Controller.applyDefeat(gameBoard, player);
		gameBoard.actualiseDataBoard();
	}

	/**
	 * Manage KeyboardKey that has been pressed.
	 * @param key
	 * 		  Key pressed.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param p
	 * 		  Player.
	 * @param context
	 * 		  Context where we manage the KeyBoard.
	 * */
	public static void manageKeyboard(KeyboardKey key, Board gameBoard, Player p, ApplicationContext context) {
	    switch(key) {
	    case UP: Controller.updatePlayer(gameBoard, p, 0, -1); break;
	    case LEFT: Controller.updatePlayer(gameBoard, p, -1, 0); break;
	    case DOWN: Controller.updatePlayer(gameBoard, p, 0, 1); break;
	    case RIGHT: Controller.updatePlayer(gameBoard, p, 1, 0); break;
	    case Q: context.exit(0); break;
	    default:break;
	    }
	}

	/**
	 * Apply option's rules if it's not empty and if it's a valid rule.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param player
	 * 		  Player.
	 * @param executeOption
	 * 		  ArrayList containing texts of the game.
	 * */
	public static void applyExecuteOption(Board gameBoard, Player player, List<String> executeOption) {
		executeOption = executeOption.stream().map(String::toLowerCase).map(option -> Character.toUpperCase(option.charAt(0)) + option.substring(1)).collect(Collectors.toList());
		while(executeOption.size() >= 3) {
			var left = executeOption.get(0);
			var operator = executeOption.get(1);
			var right = executeOption.get(2);
			if(Rules.isRule(left, operator, right)) {
				Rules.applyRule(left, right, gameBoard, player);
				executeOption.remove(left);
				executeOption.remove(operator);
				executeOption.remove(right);
			}
			else
				executeOption.remove(0);
		}
	}

	/**
	 * Manage arguments.
	 * @param args
	 * 		  Arguments.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param player
	 * 		  Player.
	 * @return
	 * 		  return list with rules added by option and levels to load filename.
	 * @throws IOException
	 * 		   File not found.
	 * */
	public static List<List<String>> manageArgs(String[] args, Board gameBoard, Player player) throws IOException {
		String lastArg = "";
		var listWithOptions = new ArrayList<List<String>>();
		List<String> levelsToLoad = new ArrayList<String>();
		var executeOption = new ArrayList<String>();
		for(var arg : args) {
			if(arg.substring(0, 2).equals("--"))
				lastArg = arg;
			if(arg.equals(lastArg) == false) {
				if(lastArg.equals("--levels"))
					levelsToLoad = Files.list(Paths.get(arg)).map(Object::toString).collect(Collectors.toList());
				else if(lastArg.equals("--level")) {
					levelsToLoad.clear();
					levelsToLoad.add("levels/" + arg);
				}
				else if(lastArg.equals("--execute")) {
					executeOption.add(arg);
				}
			}
		}
		if(levelsToLoad.isEmpty())
			levelsToLoad.add("levels/default-level.txt");
		//Apply option's rules.
		listWithOptions.add(executeOption);
		listWithOptions.add(levelsToLoad.stream().sorted().collect(Collectors.toList()));
		return listWithOptions;
	}

	/**
	 * Initialize a game level.
	 * @param context
	 * 		  Context where we need to initialize the level.
	 * @param args
	 * 		  Arguments.
	 * @throws IOException
	 * 		   File not found.
	 * */
	public static void launchGame(ApplicationContext context, String[] args) throws IOException {
		ScreenInfo screenInfo = context.getScreenInfo();
	    float width = screenInfo.getWidth();
	    float height = screenInfo.getHeight();
	    var p = new Player();
	    var board = new Board();


	    Controller.gameLoop(context, board, p, Controller.manageArgs(args, board, p), width, height);
	}

	/**
	 * Launch the game Baba Is You.
	 * @param context
	 * 		  Context the game can be played.
	 * @param board
	 * 		  Game board
	 * @param p
	 * 		  Player.
	 * @param listWithOptions
	 * 		  List with Rule's options in index 0 and levels to load in index 1.
	 * @param width
	 * 		  Width of the screen.
	 * @param height
	 * 		  Height of the screen.
	 * @throws IOException
	 * 		   File not found.
	 * */
	public static void gameLoop(ApplicationContext context, Board board, Player p, List<List<String>> listWithOptions, float width, float height) throws IOException {
		boolean win = false;
		while(listWithOptions.get(1).isEmpty() == false) {
			LevelLoader.loadLevel(board, listWithOptions.get(1).get(0));
			Controller.applyExecuteOption(board, p, listWithOptions.get(0));
			var g = new Graphic(width, height, board);
			for(var e : board.getElementsByCategory("Texts"))
				e.addProperty("Push");
			Controller.actualiseGame(board, p);
	   		g.drawGameBoard(context, board, p);
			while(Controller.isDefeat(board, p) == false && win == false) {
				Event event = context.pollOrWaitEvent(100);
			  	Controller.actualiseGame(board, p);

			  	 if (event == null)
			          continue;
			  	g.drawGameBoard(context, board, p);
			   	if(event.getAction() == Action.KEY_PRESSED) {
			   		Controller.manageKeyboard(event.getKey(), board, p, context);
				}
			   	win = Controller.isWin(board, p);
			}
			if(win)
				listWithOptions.get(1).remove(0);
			win = false;
			board.clear();
			p.getPlayers().clear();
		}
	}
	/**
	 * Main function.
	 * @param args
	 * 		  Arguments.
	 * */
	public static void main(String[] args) {
		Application.run(Color.BLACK, context ->{
			try {
				Controller.launchGame(context, args);
			} catch (IOException e) {
				System.err.println(e.getMessage());
				System.exit(1);
			}
			System.out.println("Sortie de jeu");
			context.exit(0);
	    });
	}
}
