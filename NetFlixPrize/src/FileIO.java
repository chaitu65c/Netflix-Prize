import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {

	public ArrayList<String> readFile(String filename) {

		File readFile = new File(filename);
		if (!readFile.exists()) {
			System.err.println("File" + filename + " does not exist.");
			return null;
		}

		Scanner in = null;

		try {
			
			FileReader reader = new FileReader(filename);
			in = new Scanner(reader);

			ArrayList<String> fileData = new ArrayList<String>();
			
			while (in.hasNextLine()) {
				String input = in.nextLine();
				fileData.add(input);
			}
			
			return fileData;
			
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			if(in != null)
				in.close();
		}
	}
}
