
package fr.umlv.display;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import fr.umlv.board.Board;
import fr.umlv.items.Element;
import fr.umlv.player.Player;
import fr.umlv.zen5.ApplicationContext;
/**
 * This Class permits to draw every object of the game using zen5 library
 **/
public class Graphic {
	private int widthSquare; //Width of a screen square.
	private int heightSquare;  //Height of a screen square.
	private int columnOutZone;
	private int lineOutZone;
	private Rectangle2D clear;
	private Rectangle2D fond;

	
	/**
	 * Graphic's object constructor.
	 * @param widthScreen
	 * 		  Width of the screen.
	 * @param heightScreen
	 * 		  Height of the screen.
	 * @param gameBoard
	 * 		  Board of the game.
	 * */
	public Graphic(float widthScreen, float heightScreen, Board gameBoard) {
	    this.widthSquare = (int) widthScreen / 34;
	    this.heightSquare = (int) heightScreen / 20;
	    this.columnOutZone = (34 - gameBoard.getColumnLimit()) / 2;
	    this.lineOutZone = (20 - gameBoard.getLineLimit()) / 2;
	    this.clear = new  Rectangle2D.Float(0, 0, widthSquare * 34, heightSquare * 20);
	    this.fond = new  Rectangle2D.Float(this.columnOutZone * this.widthSquare, this.lineOutZone * this.heightSquare, this.widthSquare * gameBoard.getColumnLimit(), this.heightSquare * gameBoard.getLineLimit());

	}
	
	/**
	 * Draw an element of the game.
	 * @param element
	 * 		  Element of the game to draw.
	 * @param graphics
	 * 		  Screen graphic where we draw.
	 * */
	public void drawElement(Element element, Graphics2D graphics) {
		graphics.drawImage(element.getImage(), (element.getColumn() + columnOutZone) * widthSquare, (element.getLine() + lineOutZone) * heightSquare, widthSquare, heightSquare, null);
	}
	
	/**
	 * Draw a player (To draw all elements that are the player on others elements).
	 * @param gameBoard
	 * 		  Board of the game.
	 * @param player
	 * 		  Player to draw.
	 * @param graphics
	 * 		  Screen graphic where we draw.
	 * */
	public void drawPlayer(Board gameBoard, Player player, Graphics2D graphics) {
		player.getPlayers().stream().forEach(typePlayer -> {
			gameBoard.getElementsByCategory(typePlayer)
					 .stream().forEach(element -> drawElement(element, graphics));;
		});
	}
	
	/**
	 * Draw a game board containing every elements of a level.
	 * @param context
	 * 		  Context where to draw.
	 * @param gameBoard
	 * 		  Game board to draw.
	 * @param player
	 * 		  Player to draw.
	 * */
	public void drawGameBoard(ApplicationContext context, Board gameBoard, Player player) {
		context.renderFrame(graphics ->{
			graphics.setColor(Color.DARK_GRAY);
			graphics.fill(this.clear);
			graphics.setColor(Color.BLACK);
			graphics.fill(this.fond);
			gameBoard.getValues().stream().flatMap(elements -> elements.stream())
			.forEach(element -> this.drawElement(element, graphics));
			this.drawPlayer(gameBoard, player, graphics);
		});
	}
}
