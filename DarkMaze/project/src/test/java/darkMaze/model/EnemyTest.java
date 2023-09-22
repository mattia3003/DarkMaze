package darkMaze.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnemyTest {
	
	@Test
	@DisplayName("Checking if 2D array is correct size")
	public void test2DArrayLength() {
		assertThrows(IllegalArgumentException.class, () -> {
			Enemy enemy = new Enemy();
			int[][] array = new int[13][12];
			enemy.move(array);
		}, "Incorrect array size");
		
		assertThrows(IllegalArgumentException.class, () -> {
			Enemy enemy2 = new Enemy();
			int[][] array = new int[12][5];
			enemy2.move(array);
		}, "Incorrect array size");
	}
	
	@Test
	@DisplayName("Testing a legal enemy move")
	public void testLegalEnemyMove() {
		Enemy enemy = new Enemy();
		int[][] array = new int[12][12];
		
		for (int x = 0; x < 12; x++) {
			array[0][x] = 1;
		}
		for (int x = 0; x < 12; x++) {
			array[11][x] = 1;
		}
		for (int x = 0; x < 12; x++) {
			array[x][0] = 1;
		}
		for (int x = 0; x < 12; x++) {
			array[x][11] = 1;
		}
		
		enemy.move(array);

		//Hard to test correct behavior when random numbers are involved, so the JUnit test could be ran several times to verify
		assertTrue(enemy.getX() == (6) || enemy.getX() == (7) || enemy.getX() == (8));
		assertTrue(enemy.getY() == (4) || enemy.getY() == (5) || enemy.getY() == (6));
		
	}
	
	/*Again, hard to test if the enemy coordinates can be set lover than 1 or above 11 when its movement-pattern is random
	Testing this results in a guessing game, where the amount of moves needed are unknown, and the test often fails due to the enemy not reaching a 'out of bounds' value.
	As a result I have chosen not to include such a test */
	
}
