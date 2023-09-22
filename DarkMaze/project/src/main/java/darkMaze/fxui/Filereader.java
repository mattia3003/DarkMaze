package darkMaze.fxui;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Filereader implements darkMazeReader {
	
	@Override
	public void createFile(String filename) {
		try {
			File file = new File(filename);
			if (file.createNewFile()) {
				System.out.println("Created new file: " + filename);
			}
			else {
				System.out.println("File already exists");
			}
		}
		catch (IOException e) {
			System.out.println("An error has occured trying to create file");
			e.printStackTrace();
		}
	}

	@Override
	public void writeToFile(String filename, List<Integer> highscorelist) {
		try {
			PrintWriter writer = new PrintWriter(filename);
			for (Integer i: highscorelist) {
				writer.println(i);
			}
			
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("An error has occured trying to write to file");
			e.printStackTrace();
		}
	}

	@Override
	public List<String> readFromFile(String filename) {
		
		try {
			Scanner scanner = new Scanner(new File(filename));
			List<String> printedHighscorelist = new ArrayList<>();
			
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				printedHighscorelist.add(line);
			}
			return printedHighscorelist;
		}
		catch (IOException e) {
			System.out.println("An error has occured trying to read from file");
			e.printStackTrace();
			return null;
			}
	}
}
