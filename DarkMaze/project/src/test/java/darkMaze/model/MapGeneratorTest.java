package darkMaze.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class MapGeneratorTest {
	
	/*
	Important!!
	As a result of not being able to directly check if a gridPane contains an element in a
	Certain square (and due to having identical elements), some of the tests focus on checking 
	if the correct amount of elements are contained in the gridPane and in associated 2D arrays.*/

	@Test
	@DisplayName("Testing constructor 'gridpane' argument")
	public void testConstructor() {
		assertThrows(IllegalArgumentException.class, () -> {
			GridPane maze = null;
			MapGenerator mapGenerator = new MapGenerator(maze);
		}, "A map generator should not be allowed to be initialized with a 'null' gridpane");
	}
	
	@Test
	@DisplayName("Testing setter-method for map tiles amount")
	public void testSetTilesAmount() {
		GridPane maze = new GridPane();
		Circle testobject = new Circle();
		maze.add(testobject, 0, 0);
		MapGenerator mapGenerator = new MapGenerator(maze);
		mapGenerator.map1();
		mapGenerator.setTileAmount();
		assertEquals(59, mapGenerator.getMapTiles());
		
		mapGenerator.map2();
		mapGenerator.setTileAmount();
		assertEquals(68, mapGenerator.getMapTiles());
		
		mapGenerator.map3();
		mapGenerator.setTileAmount();
		assertEquals(68, mapGenerator.getMapTiles());
	}
	
	@Test
	@DisplayName("Testing method for defining outer walls of the 2D array portraying the map walls")
	public void testDefineOuterWalls() {
		GridPane maze = new GridPane();
		Circle testobject = new Circle();
		maze.add(testobject, 0, 0);
		int[][] testArray = new int[12][12];
		testArray[0][0] = 1;
		testArray[11][11]= 1;
		testArray[5][8] = 0;
		testArray[2][0] = 1;
		MapGenerator mapGenerator = new MapGenerator(maze);
		mapGenerator.defineOuterMap();
		
		assertEquals(testArray[0][0], mapGenerator.getWalls()[0][0]);
		assertEquals(testArray[11][11], mapGenerator.getWalls()[11][11]);
		assertEquals(testArray[5][8], mapGenerator.getWalls()[5][8]);
		assertEquals(testArray[2][0], mapGenerator.getWalls()[2][0]);
	}
	
	@Test
	@DisplayName("Testing reset method for highscorelist")
	public void testResetHighscorelist() {
		GridPane maze = new GridPane();
		Circle testobject = new Circle();
		maze.add(testobject, 0, 0);
		MapGenerator mapGenerator = new MapGenerator(maze);
		List<String> testList = new ArrayList<>();
		testList.add(0,"68");
		testList.add(1,"79");
		testList.add(2,"0");
		List<Integer> testList2 = new ArrayList<>();
		testList2.add(0,0);
		testList2.add(1,0);
		testList2.add(2,0);
		mapGenerator.checkUpdateHighscorelist(testList);
		
		mapGenerator.resetHighscorelist();
		assertEquals(testList2, mapGenerator.getHighscorelist());
	}
	
	@Test
	@DisplayName("Testing player coordiante input for updatingLitUpTiles method")
	public void testUpdatingLitUpTiles() {
		assertThrows(IllegalArgumentException.class, () -> {
			GridPane maze = new GridPane();
			Circle testobject = new Circle();
			maze.add(testobject, 0, 0);
			MapGenerator mapGenerator = new MapGenerator(maze);
			mapGenerator.updatingLitUpTiles(0, 2);
		}, "A player coordinate should not be entered below 1 or above 11");
		
		assertThrows(IllegalArgumentException.class, () -> {
			GridPane maze2 = new GridPane();
			Circle testobject2 = new Circle();
			maze2.add(testobject2, 0, 0);
			MapGenerator mapGenerator2 = new MapGenerator(maze2);
			mapGenerator2.updatingLitUpTiles(8, 14);
		}, "A player coordinate should not be entered below 1 or above 11");
	}
	
	@Test
	@DisplayName("Testing discovered tiles count")
	public void testDiscoveredTilesCount() {
		GridPane maze = new GridPane();
		Circle testobject = new Circle();
		maze.add(testobject, 0, 0);
		MapGenerator mapGenerator = new MapGenerator(maze);
		mapGenerator.map1();
		mapGenerator.updatingLitUpTiles(2, 1);
		assertEquals(3, mapGenerator.getTilesDiscovered());
		mapGenerator.updatingLitUpTiles(1, 1);
		assertEquals(3, mapGenerator.getTilesDiscovered());
	}
	
	@Test
	@DisplayName("Testing illegal 'playerMoves' argument for updateHighscore method")
	public void testUpdateHighscore() {
		assertThrows(IllegalArgumentException.class, () -> {
			GridPane maze = new GridPane();
			Circle testobject = new Circle();
			maze.add(testobject, 0, 0);
			MapGenerator mapGenerator = new MapGenerator(maze);
			mapGenerator.updateHighscore(0);
		}, "A player coordinate should not be entered below 1 or above 11");
	}
	
	@Test
	@DisplayName("Testing legal 'playerMoves' arguments for updateHighscore method")
	public void testLegalUpdateHighscore() {
			GridPane maze = new GridPane();
			Circle testobject = new Circle();
			maze.add(testobject, 0, 0);
			MapGenerator mapGenerator = new MapGenerator(maze);
			mapGenerator.updateHighscore(58);
			assertEquals(58, mapGenerator.getHighscorelist().get(0));
			mapGenerator.updateMapCount();
			mapGenerator.updateHighscore(68);
			assertEquals(68, mapGenerator.getHighscorelist().get(1));
			mapGenerator.updateMapCount();
			mapGenerator.updateHighscore(88);
			assertEquals(88, mapGenerator.getHighscorelist().get(2));
	}
	
	@Test
	@DisplayName("Testing illegal 'null' list argument for checkUpdateHighscore method")
	public void testCheckUpdateHighscorelist() {
		assertThrows(IllegalArgumentException.class, () -> {
			GridPane maze = new GridPane();
			Circle testobject = new Circle();
			maze.add(testobject, 0, 0);
			List<String> testList = null;
			MapGenerator mapGenerator = new MapGenerator(maze);
			mapGenerator.checkUpdateHighscorelist(testList);
		}, "A player coordinate should not be entered below 1 or above 11");
	}
	
	@Test
	@DisplayName("Testing expected outputs from checkUpdateHighscorelist method")
	public void testCheckUpdateHighscorelistOutputs() {
		
		//Testing to see if the method sets the highscorelist to correct values
		GridPane maze = new GridPane();
		Circle testobject = new Circle();
		maze.add(testobject, 0, 0);
		MapGenerator mapGenerator = new MapGenerator(maze);
		List<String> testList = new ArrayList<>();
		testList.add(0,"68");
		testList.add(1,"79");
		testList.add(2,"0");
		List<Integer> testList2 = new ArrayList<>();
		testList2.add(0,68);
		testList2.add(1,79);
		testList2.add(2,0);
		mapGenerator.checkUpdateHighscorelist(testList);
		assertEquals(testList2, mapGenerator.getHighscorelist());
		
		//Testing to see if no change is made to the highscore list
		GridPane maze2 = new GridPane();
		Circle testobject2 = new Circle();
		maze.add(testobject2, 0, 0);
		MapGenerator mapGenerator2 = new MapGenerator(maze2);
		List<String> testList3 = new ArrayList<>();
		List<Integer> testList4 = new ArrayList<>();
		testList4.add(0,0);
		testList4.add(1,0);
		testList4.add(2,0);
		mapGenerator.checkUpdateHighscorelist(testList3);
		assertEquals(testList4, mapGenerator2.getHighscorelist());
	}
	
	@Test
	@DisplayName("Testing correct boolean value for enemy visibility")
	public void testEnemyVisible() {
		GridPane maze = new GridPane();
		Circle testobject = new Circle();
		maze.add(testobject, 0, 0);
		Enemy enemy = new Enemy();
		MapGenerator mapGenerator = new MapGenerator(maze);
		mapGenerator.map1();
		assertFalse(mapGenerator.checkEnemyVisible(enemy));
		mapGenerator.updatingLitUpTiles(7,5);
		assertTrue(mapGenerator.checkEnemyVisible(enemy));
	}
	
	@Test
	@DisplayName("Test method for verifying legal player moves")
	public void testVerifyLegalPlayerMove() {
		assertThrows(IllegalArgumentException.class, () -> {
			GridPane maze = new GridPane();
			Circle testobject = new Circle();
			maze.add(testobject, 0, 0);
			MapGenerator mapGenerator = new MapGenerator(maze);
			mapGenerator.map1();
			mapGenerator.verifyLegalMove("right", 0, 3);
		}, "A player coordinate should not be entered below 1 or above 11");
		
		GridPane maze = new GridPane();
		Circle testobject = new Circle();
		maze.add(testobject, 0, 0);
		MapGenerator mapGenerator = new MapGenerator(maze);
		mapGenerator.map1();
		assertFalse(mapGenerator.verifyLegalMove("wrongString", 2, 1));
		
		GridPane maze2 = new GridPane();
		Circle testobject2 = new Circle();
		maze2.add(testobject2, 0, 0);
		MapGenerator mapGenerator2 = new MapGenerator(maze2);
		mapGenerator2.map1();
		assertTrue(mapGenerator2.verifyLegalMove("right", 1, 1));
		
		GridPane maze3 = new GridPane();
		Circle testobject3 = new Circle();
		maze3.add(testobject3, 0, 0);
		MapGenerator mapGenerator3 = new MapGenerator(maze3);
		mapGenerator3.map1();
		assertFalse(mapGenerator3.verifyLegalMove("up", 1, 1));
	}
	
	@Test
	@DisplayName("Test to see if the gridpane is filled with stackpanes portraing 'walls' or 'shadow'")
	public void testFillMap() {
		GridPane maze = new GridPane();
		Circle testobject = new Circle();
		maze.add(testobject, 0, 0);
		MapGenerator mapGenerator = new MapGenerator(maze);
		mapGenerator.fillMap();
		/*We are looking for 145 because 12*12+1 = 145 and the fillMap() method adds 1 stackpane per square 
		and we have to count with the testObject circle */
		assertEquals(145, mapGenerator.getMaze().getChildren().size());
	}
	
	@Test
	@DisplayName("Testing creation of floortile")
	public void testCreateFloorTile() {
		GridPane maze = new GridPane();
		Circle testobject = new Circle();
		maze.add(testobject, 0, 0);
		MapGenerator mapGenerator = new MapGenerator(maze);
		maze.add(mapGenerator.createFloorTile(), 1, 1);
		maze.add(mapGenerator.createFloorTile(), 2, 1);
		assertEquals(3, mapGenerator.getMaze().getChildren().size());
	}
	
	@Test
	@DisplayName("Testing method for lighting up map with floor tiles")
	public void testLightUp() {
		assertThrows(IllegalArgumentException.class, () -> {
			GridPane maze = new GridPane();
			Circle testobject = new Circle();
			maze.add(testobject, 0, 0);
			MapGenerator mapGenerator = new MapGenerator(maze);
			mapGenerator.map1();
			mapGenerator.lightUp(0, 7);
		}, "A player coordinate should not be entered below 1 or above 11");
		
		GridPane maze = new GridPane();
		Circle testobject = new Circle();
		maze.add(testobject, 0, 0);
		MapGenerator mapGenerator = new MapGenerator(maze);
		mapGenerator.map1();
		mapGenerator.lightUp(1, 1);
		mapGenerator.lightUp(2, 1);
		/* We are looking for 6 nodes in the gridPane, because the lightUp() method
		adds stackPane floors in squares the 2D array mapWalls[][] = 0.
		This means that it can place floor tiles on squares that already contains floor tiles.
		As a result, and with knowledge of the map1() layout, coordinates (1,1) gives 2, and
		(2,1) gives 3. We add the testObject and that equals 6.*/
		assertEquals(6, mapGenerator.getMaze().getChildren().size());
	}
	
	@Test
	@DisplayName("Testing methods for defining current map and verifying correct values")
	public void testMapMethods() {
		GridPane maze = new GridPane();
		Circle testobject = new Circle();
		maze.add(testobject, 0, 0);
		int[][] testArray = new int[12][12];
		testArray[0][0] = 1;
		testArray[2][2] = 1;
		testArray[3][1] = 1;
		testArray[2][3] = 1;
		MapGenerator mapGenerator = new MapGenerator(maze);
		mapGenerator.map1();
		
		assertEquals(59, mapGenerator.getMapTiles());
		
		assertTrue(testArray[0][0] == mapGenerator.getWalls()[0][0]);
		assertTrue(testArray[2][2] == mapGenerator.getWalls()[2][2]);
		
		mapGenerator.map2();
		
		assertEquals(68, mapGenerator.getMapTiles());
		
		assertTrue(testArray[0][0] == mapGenerator.getWalls()[0][0]);
		assertFalse(testArray[2][2] == mapGenerator.getWalls()[2][2]);
		assertTrue(testArray[3][1] == mapGenerator.getWalls()[3][1]);
		
		mapGenerator.map3();
		
		assertEquals(68, mapGenerator.getMapTiles());
		
		assertTrue(testArray[0][0] == mapGenerator.getWalls()[0][0]);
		assertFalse(testArray[3][1] == mapGenerator.getWalls()[3][1]);
		assertTrue(testArray[2][3] == mapGenerator.getWalls()[2][3]);
	}
	
}
