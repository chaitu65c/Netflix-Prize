import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;


public class NetflixTester {
	
	public static final String baseFile = "data\\u1.base";
	public static final String testFile = "data\\u1.test";

	public static final String usersFile = "data\\u.user";
	public static final String moviesFile = "data\\u.item";
	

	public static final String lineSeparator = System.getProperty("line.separator");
	
	public static String readFile(String filename) {
		
		FileReader reader;
		String toReturn = "";
		Scanner in = null;
		
		try {
			reader = new FileReader(filename);
			in = new Scanner(reader);
			
			StringBuffer fileData = new StringBuffer();
			
			while(in.hasNextLine()) {
				String input = in.nextLine();
				fileData.append(input + lineSeparator);
			}
			
			toReturn = fileData.toString();
		} catch (FileNotFoundException e) {
			System.out.println("Test file not found!");
			System.exit(0);
		} finally {
			if (in != null)
				in.close();
		}
		
		
		return toReturn;
	}
	
	public static void main(String[] args) {
		
		System.out.println("\n***Initializing Predictor***");
		long start = System.currentTimeMillis();
		
		NetFlixPredictor tester = new NetFlixPredictor(baseFile, usersFile, moviesFile);
		
		System.out.println("\n***Testing getRating Method***");
		
		{
			int numberTested = 0, userToTest, movieToTest;
			String baseData = readFile(baseFile);
			String[] baseDataLines = baseData.split(lineSeparator);
			ArrayList<Integer> users = new ArrayList<Integer>();
			ArrayList<ArrayList<Integer>> movies = new ArrayList<ArrayList<Integer>>();
			
			for (String line : baseDataLines) {
				String[] lineVals = line.split("\t");
				userToTest = Integer.parseInt(lineVals[0]);
				movieToTest = Integer.parseInt(lineVals[1]);
				boolean found = false;
				for (int i = 0; i < users.size(); i++)
					if (users.get(i) == userToTest) {
						movies.get(i).add(movieToTest);
						found = true;
						break;
					}
				if (!found) {
					users.add(userToTest);
					ArrayList<Integer> newList = new ArrayList<Integer>();
					newList.add(movieToTest);
					movies.add(newList);
				}
			}
			numberTested = 0;
			while(numberTested < 20) {
				int testIndex = 0;
				do {
					testIndex = (int)(Math.random()*943);
				} while (users.indexOf(testIndex) >= 0);
				double val = tester.getRating(testIndex, 1);
				if (val >= 0) {
					System.out.println("Rating returned from getRating for a non-existing user! ");
					System.out.println("(This probably means you're reading in the database incorrectly)");
					System.exit(0);
				}
				numberTested++;
			}
			numberTested = 0;
			while(numberTested < 20) {
				int testIndex = (int)(Math.random()*users.size()), testIndex2;
				do {
					testIndex2 = (int)(Math.random()*200);
				} while (movies.get(testIndex).indexOf(testIndex2) >= 0);
				
				double val = tester.getRating(users.get(testIndex), testIndex2);
				if (val >= 0) {
					System.out.println("Rating returned from getRating for a movie that was not rated by user!");
					System.out.println("(This probably means you're reading in the database incorrectly)");
					System.exit(0);
				}
				numberTested++;
			}
			numberTested = 0;
			while(numberTested < 20) {
				int testIndex = (int)(Math.random()*users.size());
				int testIndex2 = (int)(Math.random()*movies.get(testIndex).size());
				double val = tester.getRating(users.get(testIndex), movies.get(testIndex).get(testIndex2));
				if (val < 0) {
					System.out.println("No rating returned from getRating for a movie that was rated by user!");
					System.out.println("(This probably means you're reading in the database incorrectly)");
					System.exit(0);
				}
				numberTested++;
			}
		}
		
		
		System.out.println("\n***Generating Recommendations***");
		
		String testData = readFile(testFile);
		String[] testDataLines = testData.split(lineSeparator);
		
		double guessStars, actualStars, totalDifference = 0, totalSquareDifference = 0;
		int numberTested = 0, numberPresent = 0, userToTest, movieToTest;
		
		for (String line : testDataLines) {
			String[] lineVals = line.split("\t");
			userToTest = Integer.parseInt(lineVals[0]);
			movieToTest = Integer.parseInt(lineVals[1]);
			actualStars = Integer.parseInt(lineVals[2]);
			guessStars = tester.getRating(userToTest, movieToTest);
			if (guessStars >= 0) {
				numberPresent++;
				continue;
			}
			guessStars = tester.guessRating(userToTest, movieToTest);
			totalDifference += Math.abs(guessStars-actualStars);
			totalSquareDifference += (guessStars-actualStars) * (guessStars-actualStars);
			numberTested++;
			System.out.println("Tested " + (numberTested+numberPresent) + "/" + testDataLines.length);
		}
		long end = System.currentTimeMillis();
		
		System.out.println("\n\n******Results******");
		System.out.println("Time is: " + (end-start)/1000 + " seconds");
		System.out.println("Test combinations already present in dataset: " + numberPresent);
		System.out.println("Test combinations not present (rating guessed): " + numberTested);
		System.out.println("Total difference between guessed and actual ratings: " + totalDifference);
		System.out.println("Average difference between guessed and actual ratings: " + totalDifference/numberTested);
		System.out.println("Root square mean difference between guessed and actual ratings: " + Math.sqrt(totalSquareDifference/numberTested));
		
		
	}

}
