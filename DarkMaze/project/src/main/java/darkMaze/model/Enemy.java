package darkMaze.model;

import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Enemy {
	
	private Circle enemy;
	private int enemyX;
	private int enemyY;
	
	//Enemy constructor
	public Enemy() {
		
		enemyX = 7;
		enemyY = 5;
		
		enemy = new Circle();
		InnerShadow shadow = new InnerShadow();
		enemy.setRadius(16);
		enemy.setStroke(Color.BLACK);
		enemy.setStrokeWidth(2);
		enemy.setEffect(shadow);
	}
	
	//Getter for enemy model
	public Circle getEnemy() {
		return enemy;
	}
	
	//Getter for X-coordinate
	public int getX() {
		return enemyX;
	}
	
	//Getter for Y-coordinate
	public int getY() {
		return enemyY;
	}
	
	//Re-setting enemy position
	public void resetPosition() {
		enemyX = 7;
		enemyY = 5;
	}
	
	//Method for input from movement buttons. Creating a pseudo-random movement pattern. The goal is to make the enemy "unpredictable" and it may not move at all
	public void move(int[][] mapWalls) {
		
	//Throwing IllegalStateExceptions if enemy is trying to move out of bounds, but this is not possible in the 'game-logic' so it is just for safety 
		
		//Checking if input 2D array is the correct size
		if (mapWalls.length != 12 || mapWalls[0].length != 12) {
			throw new IllegalArgumentException("The input 2D array is not the correct size. It has to be 12x12");
		}
		
		//Creating random double
		double moveChance = Math.random();
		
		//Using the double as a random element for movement pattern
		if (mapWalls[enemyX+1][enemyY] == 0 && moveChance > 0.7) {
			if (enemyX == 11) {
				throw new IllegalStateException("The enemy can not move out of map bounds");
			}
			enemyX += 1;
			enemyY += 0;
		}
		else if (mapWalls[enemyX-1][enemyY] == 0 && moveChance > 0.4) {
			if (enemyX == 1) {
				throw new IllegalStateException("The enemy can not move out of map bounds");
			}
			enemyX += -1;
			enemyY += 0;
		}
		else {
		
		//Creating a new double to use after there is no move in X-axis
		double moveChance1 = Math.random();
		
		if (mapWalls[enemyX][enemyY+1] == 0 && moveChance1 > 0.4) {
			if (enemyY == 11) {
				throw new IllegalStateException("The enemy can not move out of map bounds");
			}
			enemyX += 0;
			enemyY += 1;
		}
		
		else if (mapWalls[enemyX][enemyY-1] == 0){
			if (enemyY == 1) {
				throw new IllegalStateException("The enemy can not move out of map bounds");
			}
			enemyX += 0;
			enemyY += -1;
		}
		//At this point there is a chance no move has been made, and this is intentional, to create an extra element to deal with for the player
	}			
}
}
