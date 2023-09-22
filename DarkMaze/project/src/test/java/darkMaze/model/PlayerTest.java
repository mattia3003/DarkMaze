package darkMaze.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {
	
	@Test
	@DisplayName("Testing a legal move")
	public void testLegalPlayerMove() {
		Player player = new Player();
		player.move("right");
		assertEquals(2, player.getX());
		assertEquals(1, player.getY());
	}
	
	@Test
	@DisplayName("Checking illegal coordinate changes")
	public void testCoordinatesOutOfBounds() {
		assertThrows(IllegalStateException.class, () -> {
			Player player = new Player();
			player.move("left");
		}, "A player should not be able to achieve '0' X-coordinate");
		
		assertThrows(IllegalStateException.class, () -> {
			Player player2 = new Player();
			player2.move("up");
		}, "A player should not be able to achieve '0' Y-coordinate");
		
		assertThrows(IllegalStateException.class, () -> {
			Player player3 = new Player();
			player3.move("down");
			player3.move("down");
			player3.move("down");
			player3.move("down");
			player3.move("down");
			player3.move("down");
			player3.move("down");
			player3.move("down");
			player3.move("down");
			player3.move("down");
			player3.move("down");
		}, "A player should not be able to achieve '12' Y-coordinate");
		
		assertThrows(IllegalStateException.class, () -> {
			Player player4 = new Player();
			player4.move("right");
			player4.move("right");
			player4.move("right");
			player4.move("right");
			player4.move("right");
			player4.move("right");
			player4.move("right");
			player4.move("right");
			player4.move("right");
			player4.move("right");
			player4.move("right");
		}, "A player should not be able to achieve '12' Y-coordinate");
		
	}
	
	@Test
	@DisplayName("Checking non-directional string")
	public void testNondirectionalString() {
		Player player = new Player();
		Player player2 = new Player();
		player2.move("Hello");
		assertEquals(player.getX(), player2.getX());
		assertEquals(player.getY(), player2.getY());
	}
}
