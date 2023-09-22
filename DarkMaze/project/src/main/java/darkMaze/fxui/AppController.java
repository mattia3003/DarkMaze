package darkMaze.fxui;

import darkMaze.model.Enemy;
import darkMaze.model.MapGenerator;
import darkMaze.model.Player;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;

public class AppController {

	@FXML private GridPane maze;
	@FXML private Button resetButton, startButton, upKey, downKey, leftKey, rightKey, tryAgainButton, updateHighscorelistButton, resetHighscorelistButton;
	@FXML private TextArea introTextbox, highscoreText;
	@FXML private Label mapsCompleted, stepsUsed, squaresDiscovered, youDiedText, youFaintText, introTitle, gameFinishedText1, gameFinishedText2, gameFinishedText3;
	
	private Player player = new Player();
	private Enemy enemy = new Enemy();
	
	private Filereader filereader = new Filereader();
	
	//Creating a "dummy" player to add to the GridPane before using it as an argument in mapCreator to avoid "nullPointerException", then removing it later
	private Player dummyPlayer = new Player();
	
	private MapGenerator mapGenerator;
	
	//Starting the game
	@FXML
	public void startGamePressed() {
		System.out.println("start button pressed");
		
		//Adding 'character' to player model and enemy model
		Image image = new Image((getClass().getResourceAsStream("link.jpg")));
		player.getPlayer().setFill(new ImagePattern(image));
		Image image2 = new Image((getClass().getResourceAsStream("monster.jpg")));
		enemy.getEnemy().setFill(new ImagePattern(image2));
		
		introTitle.setVisible(false);
		introTextbox.setVisible(false);
		maze.add(dummyPlayer.getPlayer(), 0, 0);
		mapGenerator = new MapGenerator(maze);
		startButton.setVisible(false);
		updateHighscorelistPressed();
		onRespawnShowAll();
		mapGenerator.map1();
		mapGenerator.fillMap(); 
		maze = mapGenerator.getMaze(); 
		mapGenerator.lightUp(player.getX(), player.getY());
		maze.getChildren().remove(dummyPlayer.getPlayer());
		maze.add(player.getPlayer(), player.getX(), player.getY());
		maze.add(enemy.getEnemy(), enemy.getX(), enemy.getY());
		enemy.getEnemy().setVisible(false);
		
		stepsUsedUpdate();
		squaresDiscoveredUpdate();
	}
	
	//Re-setting the current map
	@FXML
	public void resetPressed() {
		System.out.println("reset button pressed");
		youDiedText.setVisible(false);
		youFaintText.setVisible(false);
		tryAgainButton.setVisible(false);
		onRespawnShowAll();
		player.resetPosition();
		enemy.resetPosition();
		player.resetPlayerMoves();
		mapGenerator.resetDiscoveredTiles();
		mapGenerator.fillMap(); 
		maze = mapGenerator.getMaze();
		mapGenerator.lightUp(player.getX(), player.getY());
		movePlayerAndEnemy();
		enemy.getEnemy().setVisible(false);
		stepsUsedUpdate();
		squaresDiscoveredUpdate();
	}
	
	//Try again button pressed
	@FXML
	public void tryAgainPressed() {
		resetPressed();
	}
	
	//Exit game button pressed
	@FXML
	public void exitGamePressed() {
		Platform.exit();
	}
	
	//Up key pressed
	@FXML
	public void upKeyPressed() {
		System.out.println("up button pressed");
		playerAction("up");
	}
	
	//Down key pressed
	@FXML
	public void downKeyPressed() {
		System.out.println("down button pressed");
		playerAction("down");
	}
	
	//Left key pressed
	@FXML
	public void leftKeyPressed() {
		System.out.println("left button pressed");
		playerAction("left");
	}
	
	//Right key pressed
	@FXML
	public void rightKeyPressed() {
		System.out.println("right button pressed");
		playerAction("right");
	}
	
	//Updating highscorelist in map generator and in file
	@FXML
	public void updateHighscorelistPressed() {
		
		//Trying to create file, to verify that there is a file tracking highscores
		filereader.createFile("Highscoreliste");
		
		//Updating highscorelist values in code. Reading values from file, checking with code-values and showing it in text area
		mapGenerator.checkUpdateHighscorelist(filereader.readFromFile("Highscoreliste"));
		highscoreText.setText("Highscores:" + "\n" + "Map 1: " + mapGenerator.getHighscorelist().get(0) + "\n" + "Map 2: " + mapGenerator.getHighscorelist().get(1) + "\n" + "Map 3: " + mapGenerator.getHighscorelist().get(2));
	}
	
	//Re-setting highscorelist in map generator and in file
	@FXML
	public void resetHighscorelistPressed() {
		
		//Re-setting highscore values and updating file with new values
		mapGenerator.resetHighscorelist();
		filereader.writeToFile("Highscoreliste", mapGenerator.getHighscorelist());
	}
	
	//Complete method for player action
	public void playerAction(String move) {
		
		//Checking for legal player move
		if (!mapGenerator.verifyLegalMove(move, player.getX(), player.getY())) {
			return;
		}
		
		//Moving the player
		player.move(move);
		
		//Checking for player death
		if (player.getX() == enemy.getX() && player.getY() == enemy.getY()) {
			System.out.println("you died");
			youDiedText.setVisible(true);
			tryAgainButton.setVisible(true);
			onDeathHideAll();
			return;
		}
		
		//Checking for player death after enemy movement
		enemy.move(mapGenerator.getWalls());
		
		if (player.getX() == enemy.getX() && player.getY() == enemy.getY()) {
			System.out.println("you died");
			youDiedText.setVisible(true);
			tryAgainButton.setVisible(true);
			onDeathHideAll();
			return;
		}
		
		//Updating player and enemy graphical representation
		mapGenerator.lightUp(player.getX(), player.getY());
		movePlayerAndEnemy();
		mapGenerator.updatingLitUpTiles(player.getX(), player.getY());
		enemy.getEnemy().setVisible(mapGenerator.checkEnemyVisible(enemy));
		updateGameState();
	}
	
	//Method for updating display of steps used by player and checking energy left
	public void stepsUsedUpdate() {
		stepsUsed.setText("Steps used: " + player.getPlayerMoves() + " / " + mapGenerator.attemptsAllowed());
		
		if (player.getPlayerMoves() == mapGenerator.attemptsAllowed()) {
			youFaintText.setVisible(true);
			tryAgainButton.setVisible(true);
			onDeathHideAll();
			
		}
	}
	
	//Method for updating display of squares discovered by player and checking tiles left
	public void squaresDiscoveredUpdate() {
		squaresDiscovered.setText("Tiles left to discover: " + (mapGenerator.getMapTiles() - mapGenerator.getTilesDiscovered()));
		mapsCompleted.setText(mapGenerator.getMapCount() + " / " + 3 + " maps completed");
		
		if ((mapGenerator.getMapTiles() - mapGenerator.getTilesDiscovered()) == 0) {
			System.out.println("all tiles found");
			mapGenerator.updateHighscore(player.getPlayerMoves());
			filereader.writeToFile("highscoreliste", mapGenerator.getHighscorelist());
			mapGenerator.updateMapCount();
		if (mapGenerator.getMapCount() == 3) {
			gameFinished();
			return;
		}
			mapGenerator.changeMap();
			resetPressed();
		}
	}
	
	//The following methods are created to reduce code size
	
	public void updateGameState() {
		player.updateAttemptsUsed();
		squaresDiscoveredUpdate();
		stepsUsedUpdate();
	}
	
	public void movePlayerAndEnemy() {
		maze.getChildren().remove(player.getPlayer());
		maze.getChildren().remove(enemy.getEnemy());
		maze.add(player.getPlayer(), player.getX(), player.getY());
		maze.add(enemy.getEnemy(), enemy.getX(), enemy.getY());
	}
	
	public void onDeathHideAll() {
		maze.setVisible(false);
		stepsUsed.setVisible(false);
		squaresDiscovered.setVisible(false);
		upKey.setVisible(false);
		downKey.setVisible(false);
		leftKey.setVisible(false);
		rightKey.setVisible(false);
		resetButton.setVisible(false);
		mapsCompleted.setVisible(false);
		highscoreText.setVisible(false);
		updateHighscorelistButton.setVisible(false);
		resetHighscorelistButton.setVisible(false);
	}
	
	public void onRespawnShowAll() {
		maze.setVisible(true);
		stepsUsed.setVisible(true);
		squaresDiscovered.setVisible(true);
		upKey.setVisible(true);
		downKey.setVisible(true);
		leftKey.setVisible(true);
		rightKey.setVisible(true);
		resetButton.setVisible(true);
		mapsCompleted.setVisible(true);
		highscoreText.setVisible(true);
		updateHighscorelistButton.setVisible(true);
		resetHighscorelistButton.setVisible(true);
	}
	
	public void gameFinished() {
		onDeathHideAll();
		gameFinishedText1.setVisible(true);
		gameFinishedText2.setVisible(true);
		gameFinishedText3.setVisible(true);
	}
}
