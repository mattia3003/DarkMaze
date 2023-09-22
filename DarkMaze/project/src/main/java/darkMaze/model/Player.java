package darkMaze.model;

import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Player{

	private Circle player;
	private int playerX;
	private int playerY;
	private int playerMoves;
	
	//Player constructor
	public Player() {
		
		playerX = 1;
		playerY = 1;
		playerMoves = 0;
		
		player = new Circle();
		InnerShadow shadow = new InnerShadow();
		player.setRadius(16);
		player.setStroke(Color.BLACK);
		player.setStrokeWidth(2);
		player.setEffect(shadow);
	}
	
	//Getter for player model
	public Circle getPlayer() {
		return player;
	}
	
	//Getter for X-coordinate
	public int getX() {
		return playerX;
	}
	
	//Getter for Y-coordinate
	public int getY() {
		return playerY;
	}
	
	//Getter for player moves
	public int getPlayerMoves() {
		return playerMoves;
	}
	
	//Updating amount of player moves
	public void updateAttemptsUsed() {
		playerMoves++;
	}
	
	//Re-setting player moves
	public void resetPlayerMoves() {
		playerMoves = 0;
	}
	
	//Re-setting player position
	public void resetPosition() {
		playerX = 1;
		playerY = 1;
	}
	
	//Method for input from movement buttons
	public void move(String event) {
		
		//The method throws IllegalStateExceptions if the method is trying to change the coordinates out of map bounds
		//The design of the correlations between the classes makes sure this does not happen, but the throws are there if this class is used outside of the "game-logic"
		
		if (event.equals("right")) {
			if (playerX == 11) {
				throw new IllegalStateException("The playerX coordinate can not be greater than 11");
			}
			playerX += 1;
			playerY += 0;
			return;
			
		} else if (event.equals("left")) {
			if (playerX == 1) {
				throw new IllegalStateException("The playerX coordinate can not be less than 1");
			}
			playerX += -1;
			playerY += 0;
			return;
		} 
		else if (event.equals("down")) {
			if (playerY == 11) {
				throw new IllegalStateException("The playerY coordinate can not be greater than 11");
			}
			playerX += 0;
			playerY += 1;
			return;
		}
		else if (event.equals("up")) {
			if (playerY == 1) {
				throw new IllegalStateException("The playerY coordinate can not be less than 1");
			}
			playerX += 0;
			playerY += -1;
			return;
		}
		else {
			System.out.println("The string input does not match a player movement direction");
		}
	}
}
