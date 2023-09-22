package darkMaze.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class MapGenerator {
	
	private int[][] mapWalls;
	private GridPane maze;
	private int mapTiles;
	private int[][] discoveredTiles = new int[12][12];
	private int mapCount;
	private int discoveredTilesCount = 0;
	private List<Integer> highscorelist = new ArrayList<>();
	
	//map-generator constructor
	public MapGenerator(GridPane maze) {
		
		//Checking if the gridpane is null
		if (maze == null) {
			throw new IllegalArgumentException("The Gridpane can not be null!");
		}
		
		this.maze = maze;
		this.mapCount = 0;
		mapWalls = new int[12][12];
		
		highscorelist.add(0);
		highscorelist.add(0);
		highscorelist.add(0);
	}
	
	//Getter for map walls
	public int[][] getWalls() {
		return mapWalls;
	}
		
	//Getter for maze
	public GridPane getMaze() {
		return maze;
	}
		
	//Getter for number of floor tiles on current map
	public int getMapTiles() {
		return mapTiles;
	}
		
	//Getter for map count
	public int getMapCount() {
		return mapCount;
	}
		
	//Getter for number of discovered tiles
	public int getTilesDiscovered() {
		return discoveredTilesCount;
	}
	
	//Getter for highscorelist
	public List<Integer> getHighscorelist() {
		return highscorelist;
	}
	
	//Setting current tile amount for the current map
	public void setTileAmount() {
		mapTiles = 0;
		for (int x = 0; x < 12; x++) {
			for (int y = 0; y < 12; y++) {
				if (mapWalls[x][y] == 0) {
					mapTiles ++;
				}
			}
		}
	}
	
	//Setting attempts allowed per map
	public int attemptsAllowed() {
		if (mapCount == 0) {
			return 70;
		}
		if (mapCount == 1) {
			return 90;
		}
		else {
			return 100;
		}
	 }
	
	//Method to compress code
	public void resetloopForDiscoveredTiles() {
		//re-setting discovered tiles
		for (int x = 0; x < 12; x++) {
			for (int y = 0; y < 12; y++) {
				discoveredTiles[x][y] = mapWalls[x][y];
			}
		}
	}
	
	//Re-setting discovered tiles and tiles discovered counter
	public void resetDiscoveredTiles() {
			
		discoveredTilesCount = 0;
			
		//removing all values in 2D array
		Arrays.stream(discoveredTiles).forEach(x -> Arrays.fill(x, 0));
			
		//Re-setting discovered tiles
		resetloopForDiscoveredTiles();
	}
	
	//Re-setting highscorelist
	public void resetHighscorelist() {
		
		for (int x = 0; x < 3; x++) {
			highscorelist.remove(x);
			highscorelist.add(x,0);
		} 
	}
	
	//Update map count
	public void updateMapCount() {
		mapCount++;
	}
	
	//Updating discovered tiles
	public void updatingLitUpTiles(int playerX, int playerY) {
		
		//Checking if player coordinates are valid, and within map bounds
		if (playerX < 1 || playerY < 1 || playerX > 11 || playerY > 11) {
			throw new IllegalArgumentException("A player coordinate is invalid, and is not within map bounds");
		}
			
		if (discoveredTiles[playerX][playerY] == 0) {
			discoveredTiles[playerX][playerY] = 2;
			discoveredTilesCount++;
		}
		
		if (discoveredTiles[playerX+1][playerY] == 0) {
			discoveredTiles[playerX+1][playerY] = 2;
			discoveredTilesCount++;
		}
		
		if (discoveredTiles[playerX-1][playerY] == 0) {
			discoveredTiles[playerX-1][playerY] = 2;
			discoveredTilesCount++;
		}
		
		if (discoveredTiles[playerX][playerY+1] == 0) {
			discoveredTiles[playerX][playerY+1] = 2;
			discoveredTilesCount++;
		}
		
		if (discoveredTiles[playerX][playerY-1] == 0) {
			discoveredTiles[playerX][playerY-1] = 2;
			discoveredTilesCount++;
		}		
	}
	
	//Updating highscorelist in map generator
	public void updateHighscore(int playerMoves) {
		
		//Checking for illegal player move input
		if (playerMoves < 1) {
			throw new IllegalArgumentException("An error has occured, and the input 'playerMoves' is not a valid integer");
		}
		
		if (mapCount == 0) {
			if (highscorelist.get(0) == 0) {
				highscorelist.remove(0);
				highscorelist.add(0,playerMoves);
				return;
			} else if (playerMoves < highscorelist.get(0)) {
				highscorelist.remove(0);
				highscorelist.add(0,playerMoves);
				return;
			}
		}
		
		if (mapCount == 1) {
			if (highscorelist.get(1) == 0) {
				highscorelist.remove(1);
				highscorelist.add(1,playerMoves);
				return;
			} else if (playerMoves < highscorelist.get(1)) {
				highscorelist.remove(1);
				highscorelist.add(1,playerMoves);
				return;
			}
		}
		
		if (mapCount == 2) {
			if (highscorelist.get(2) == 0) {
				highscorelist.remove(2);
				highscorelist.add(2,playerMoves);
				return;
			} else if (playerMoves < highscorelist.get(2)) {
				highscorelist.remove(2);
				highscorelist.add(2,playerMoves);
				return;
			}
		}
	}
	
	//Checking relation between file highscores and highscorelist in map generator object
	public void checkUpdateHighscorelist(List<String> list) {
		
		//Security to ensure list is not null
		if (list == null) {
			throw new IllegalArgumentException("An error has occured, and no file has been created and referanced for highscore update");
		}
		
		//Checking if the document is empty
		if (list.size() == 0) {
			System.out.println("tom liste");
			return;
		}
		
		//Logic to ensure highscorelist in map generator and highscore values in file are holding on to the same values
		for (int x = 0; x < 3; x++) {
			
			if (Integer.parseInt(list.get(x)) != 0 && highscorelist.get(x) == 0) {
				highscorelist.remove(x);
				highscorelist.add(x, Integer.parseInt(list.get(x)));
			}
			else if(Integer.parseInt(list.get(x)) != 0 && highscorelist.get(x) != 0 && Integer.parseInt(list.get(x)) < highscorelist.get(x)) {
				highscorelist.remove(x);
				highscorelist.add(x, Integer.parseInt(list.get(x)));
			}
		}
	}
	
	//Checking if enemy is visible to player
	public boolean checkEnemyVisible(Enemy enemy) {
		if (discoveredTiles[enemy.getX()][enemy.getY()] == 2) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//Verifying that there is no wall in desired move direction
	public boolean verifyLegalMove(String move, int playerX, int playerY) {
		
		if (playerX < 1 || playerY < 1 || playerX > 11 || playerY > 11) {
			throw new IllegalArgumentException("A player coordinate is out of map bounds");
		}
		
		if (move.equals("right") && mapWalls[playerX+1][playerY] == 0) {
				return true;
		}
		else if (move.equals("left") && mapWalls[playerX-1][playerY] == 0) {
			return true;
			}
		else if (move.equals("up") && mapWalls[playerX][playerY-1] == 0) {
			return true;
		}
		else if (move.equals("down") && mapWalls[playerX][playerY+1] == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//Defining outer walls in 2D array
	public void defineOuterMap() {
		for (int x = 0; x < 12; x++) {
			mapWalls[0][x] = 1;
		}
		for (int x = 0; x < 12; x++) {
			mapWalls[11][x] = 1;
		}
		for (int x = 0; x < 12; x++) {
			mapWalls[x][0] = 1;
		}
		for (int x = 0; x < 12; x++) {
			mapWalls[x][11] = 1;
		}
	}
	
	//Filling inner wall-shadow
	public GridPane fillMap() {
		
		for (int x = 0; x < 12; x++) {
			for (int y = 0; y < 12; y++) {
				StackPane wall = new StackPane();
				wall.setStyle("-fx-background-color: rgba(0, 0, 0)");
				maze.add(wall, x, y);
			}
		}
		return maze;
	}
	
	//Creating floor tile 
	public StackPane createFloorTile() {
		StackPane tile = new StackPane();
		tile.setStyle("-fx-background-color: rgba(192, 192, 192)");
		InnerShadow innerShadow = new InnerShadow();
		tile.setEffect(innerShadow);
		return tile;
	}
	
	//Lighting up floor-tiles within player vision (1 square)
	public void lightUp(int playerX, int playerY) {
		
		if (playerX < 1 || playerY < 1 || playerX > 11 || playerY > 11) {
			throw new IllegalArgumentException("A player coordinate is out of map bounds");
		}
		
			maze.add(createFloorTile(), playerX, playerY);
		
		if(mapWalls[playerX+1][playerY] == 0) {
			maze.add(createFloorTile(), playerX+1, playerY);
		}
		
		if(mapWalls[playerX-1][playerY] == 0) {
			maze.add(createFloorTile(), playerX-1, playerY);
		}
		
		if(mapWalls[playerX][playerY+1] == 0) {
			maze.add(createFloorTile(), playerX, playerY+1);
		}
		
		if(mapWalls[playerX][playerY-1] == 0) {
			maze.add(createFloorTile(), playerX, playerY-1);
		}
	}
	
	//Changing map based on current map
	public void changeMap() {
		
		if (mapCount == 1) {
			map2();
			return;
		}
		
		if (mapCount == 2) {
			map3();
			return;
		}
	}
	
	//Creating map 1 in 2D array
	public void map1() {
		
		//Defining outer walls for map 1
		defineOuterMap();
		
		//Creating inner walls for map 1
		mapWalls[1][2] = 1;
		mapWalls[2][2] = 1;
		mapWalls[4][1] = 1;
		mapWalls[4][3] = 1;
		mapWalls[4][4] = 1;
		mapWalls[3][4] = 1;
		mapWalls[2][4] = 1;
		mapWalls[2][5] = 1;
		mapWalls[2][7] = 1;
		mapWalls[2][7] = 1;
		mapWalls[2][8] = 1;
		mapWalls[2][9] = 1;
		mapWalls[3][9] = 1;
		mapWalls[4][9] = 1;
		mapWalls[6][10] = 1;
		mapWalls[6][9] = 1;
		mapWalls[6][8] = 1;
		mapWalls[6][7] = 1;
		mapWalls[5][7] = 1;
		mapWalls[4][7] = 1;
		mapWalls[4][6] = 1;
		mapWalls[5][6] = 1;
		mapWalls[6][6] = 1;
		mapWalls[5][1] = 1;
		mapWalls[6][1] = 1;
		mapWalls[6][2] = 1;
		mapWalls[6][3] = 1;
		mapWalls[6][4] = 1;
		mapWalls[7][1] = 1;
		mapWalls[8][5] = 1;
		mapWalls[8][6] = 1;
		mapWalls[8][8] = 1;
		mapWalls[8][9] = 1;
		mapWalls[9][9] = 1;
		mapWalls[10][7] = 1;
		mapWalls[10][6] = 1;
		mapWalls[10][4] = 1;
		mapWalls[10][3] = 1;
		mapWalls[9][3] = 1;
		mapWalls[8][3] = 1;
		mapWalls[9][2] = 1;
		mapWalls[10][2] = 1;
		
		//Updating tile amount for the current map 
		setTileAmount();
		
		//Re-setting discovered tiles
		resetloopForDiscoveredTiles();
	}
	
	//Creating map 2 in 2D array
	public GridPane map2() {
		
		//Re-setting 2D array with wall information
		Arrays.stream(mapWalls).forEach(x -> Arrays.fill(x, 0));
		
		//Defining outer walls for map 2
		defineOuterMap();
		
		//Creating inner walls for map 2
		mapWalls[1][2] = 1;
		mapWalls[3][1] = 1;
		mapWalls[3][3] = 1;
		mapWalls[2][4] = 1;
		mapWalls[1][5] = 1;
		mapWalls[5][2] = 1;
		mapWalls[5][3] = 1;
		mapWalls[5][4] = 1;
		mapWalls[4][5] = 1;
		mapWalls[3][6] = 1;
		mapWalls[2][7] = 1;
		mapWalls[2][8] = 1;
		mapWalls[2][9] = 1;
		mapWalls[4][10] = 1;
		mapWalls[4][8] = 1;
		mapWalls[5][8] = 1;
		mapWalls[6][9] = 1;
		mapWalls[5][7] = 1;
		mapWalls[8][9] = 1;
		mapWalls[8][8] = 1;
		mapWalls[7][7] = 1;
		mapWalls[7][6] = 1;
		mapWalls[7][4] = 1;
		mapWalls[6][2] = 1;
		mapWalls[8][1] = 1;
		mapWalls[8][2] = 1;
		mapWalls[8][3] = 1;
		mapWalls[9][5] = 1;
		mapWalls[9][7] = 1;
		mapWalls[9][9] = 1;
		mapWalls[10][2] = 1;
		mapWalls[10][3] = 1;
		
		//Updating tile amount for the current map 
		setTileAmount();
		
		//Re-setting discovered tiles
		resetloopForDiscoveredTiles();
				
		return maze;	
	}
	
	//Creating map 3 in 2D array
	public GridPane map3() {
		
		//Re-setting 2D array with wall information
		Arrays.stream(mapWalls).forEach(x -> Arrays.fill(x, 0));
				
		//Defining outer walls for map 2
		defineOuterMap();
		
		//Creating inner walls for map 3
		mapWalls[2][1] = 1;
		mapWalls[2][3] = 1;
		mapWalls[1][5] = 1;
		mapWalls[2][5] = 1;
		mapWalls[3][3] = 1;
		mapWalls[2][7] = 1;
		mapWalls[2][9] = 1;
		mapWalls[3][10] = 1;
		mapWalls[4][5] = 1;
		mapWalls[4][1] = 1;
		mapWalls[4][2] = 1;
		mapWalls[4][6] = 1;
		mapWalls[4][7] = 1;
		mapWalls[6][3] = 1;
		mapWalls[6][4] = 1;
		mapWalls[6][1] = 1;
		mapWalls[7][4] = 1;
		mapWalls[8][4] = 1;
		mapWalls[9][3] = 1;
		mapWalls[8][2] = 1;
		mapWalls[5][6] = 1;
		mapWalls[7][6] = 1;
		mapWalls[6][8] = 1;
		mapWalls[5][9] = 1;
		mapWalls[7][9] = 1;
		mapWalls[7][10] = 1;
		mapWalls[10][1] = 1;
		mapWalls[10][2] = 1;
		mapWalls[10][5] = 1;
		mapWalls[9][6] = 1;
		mapWalls[10][8] = 1;
		mapWalls[9][9] = 1;
		
		//Updating tile amount for the current map 
		setTileAmount();
				
		//Re-setting discovered tiles
		resetloopForDiscoveredTiles();
		
		return maze;
	}
}
