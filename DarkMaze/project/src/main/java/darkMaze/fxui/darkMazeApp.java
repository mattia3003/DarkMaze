package darkMaze.fxui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class darkMazeApp extends Application {

	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("Dark Maze");
		Scene scene = new Scene(FXMLLoader.load(darkMazeApp.class.getResource("darkMazeGUI.fxml")));
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		darkMazeApp.launch(args);
	}
}
