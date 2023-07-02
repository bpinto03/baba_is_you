package fr.umlv.loader;

import java.io.IOException;
import java.util.Scanner;
import java.io.FileReader;

import fr.umlv.board.Board;
import fr.umlv.items.noun.*;
import fr.umlv.items.operator.Is;
import fr.umlv.items.properties.*;

/**
 * Class to load a level from a file.
 * */
public class LevelLoader {
	
	/**
	 * Set limit of the play area.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param splitBuffer
	 * 		  Contains a line of a file split with " ".
	 * */
	public static void setLimitOfGame(Board gameBoard, String[] splitBuffer) {
		gameBoard.setColumnLimit(Integer.parseInt(splitBuffer[0]));
		gameBoard.setLineLimit(Integer.parseInt(splitBuffer[1]));
	}
	
	/**
	 * Add a text element to the block.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param splitBuffer
	 * 		  Contains a line of a file split with " ".
	 * */
	public static void addText(Board gameBoard, String[] splitBuffer) {
		switch(splitBuffer[0]) {
		case "Baba": gameBoard.add("Texts", new Baba(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2]), true)); break;
		case "Flag": gameBoard.add("Texts", new Flag(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2]), true)); break;
		case "Lava": gameBoard.add("Texts", new Lava(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2]), true)); break;
		case "Rock": gameBoard.add("Texts", new Rock(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2]), true)); break;
		case "Skull": gameBoard.add("Texts", new Skull(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2]), true)); break;
		case "Wall": gameBoard.add("Texts", new Wall(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2]), true)); break;
		case "Water": gameBoard.add("Texts", new Water(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2]), true)); break;
		case "Is": gameBoard.add("Texts", new Is(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2])));break;
		case "Defeat": gameBoard.add("Texts", new Defeat(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2])));break;
		case "Hot": gameBoard.add("Texts", new Hot(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2])));break;
		case "Melt": gameBoard.add("Texts", new Melt(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2])));break;
		case "Push": gameBoard.add("Texts", new Push(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2])));break;
		case "Sink": gameBoard.add("Texts", new Sink(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2])));break;
		case "Stop": gameBoard.add("Texts", new Stop(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2])));break;
		case "Win": gameBoard.add("Texts", new Win(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2])));break;
		case "You": gameBoard.add("Texts", new You(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2])));break;
		case "Vanish": gameBoard.add("Texts", new Vanish(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2])));break;
		case "Karl": gameBoard.add("Texts", new Karl(Integer.parseInt(splitBuffer[1]), Integer.parseInt(splitBuffer[2]), true));break;
		default: break;
		}
	}
	
	/**
	 * Add an Element with type obj into a Board by reading a file.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param reader
	 * 		  Reader of the file.
	 * @param obj
	 * 		  Type of the object to add.
	 * */
	public static void addObject(Board gameBoard, Scanner reader, String obj) {
		var splitBuffer = reader.nextLine().split(" ");
		while(splitBuffer[0].equals("/") == false) { //While there is no / keep reading the object.
			switch(obj) {
			case "Texts": LevelLoader.addText(gameBoard, splitBuffer);break;
			case "Baba": gameBoard.add(obj, new Baba(Integer.parseInt(splitBuffer[0]), Integer.parseInt(splitBuffer[1]), false)); break;
			case "Flag": gameBoard.add(obj, new Flag(Integer.parseInt(splitBuffer[0]), Integer.parseInt(splitBuffer[1]), false)); break;
			case "Lava": gameBoard.add(obj, new Lava(Integer.parseInt(splitBuffer[0]), Integer.parseInt(splitBuffer[1]), false)); break;
			case "Rock": gameBoard.add(obj, new Rock(Integer.parseInt(splitBuffer[0]), Integer.parseInt(splitBuffer[1]), false)); break;
			case "Skull": gameBoard.add(obj, new Skull(Integer.parseInt(splitBuffer[0]), Integer.parseInt(splitBuffer[1]), false)); break;
			case "Wall": gameBoard.add(obj, new Wall(Integer.parseInt(splitBuffer[0]), Integer.parseInt(splitBuffer[1]), false)); break;
			case "Water": gameBoard.add(obj, new Water(Integer.parseInt(splitBuffer[0]), Integer.parseInt(splitBuffer[1]), false)); break;
			case "Karl": gameBoard.add(obj, new Karl(Integer.parseInt(splitBuffer[0]), Integer.parseInt(splitBuffer[1]), false));break;
			default: break;
			}
			splitBuffer = reader.nextLine().split(" ");
		}
	}
	
	/**
	 * Load a level by reading the file in pathfile.
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param pathFile
	 * 		  Path of the file to read.
	 * @throws IOException
	 * 		   File not found.
	 * */
	public static void loadLevel(Board gameBoard, String pathFile) throws IOException {
		var file = new FileReader(pathFile);
		var reader = new Scanner(file);
		String[] splitBuffer;
		if(reader.hasNextLine()) {
			splitBuffer = reader.nextLine().split(" ");
			LevelLoader.setLimitOfGame(gameBoard, splitBuffer);
		}
		while(reader.hasNextLine()) {
			splitBuffer = reader.nextLine().split(" ");
			
			if(splitBuffer[0].equals("#")) {
				LevelLoader.addObject(gameBoard, reader, splitBuffer[1]);
			}
		}
		reader.close();
	}
}
