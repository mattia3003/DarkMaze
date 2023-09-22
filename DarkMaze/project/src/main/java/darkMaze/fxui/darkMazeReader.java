package darkMaze.fxui;

import java.util.List;

public interface darkMazeReader {
	
	public void writeToFile(String filename, List<Integer> highscorelist);
	
	public List<String> readFromFile(String filename);
	
	public void createFile(String filename);
	
}
