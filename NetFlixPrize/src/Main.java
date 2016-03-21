import java.util.ArrayList;

public class Main {

	public static void main(String[] args){
		FileIO reader = new FileIO();
		ArrayList<String> fileData =reader.readFile("data\\u.user");
		ArrayList<String> fileData1 =reader.readFile("data\\u.item");
		ArrayList<String> fileData2 =reader.readFile("data\\u.data");
		
		ArrayList<User> userDatabase = new ArrayList<User>();
		for(String d: fileData){
			//System.out.println(d);
			User newUser = MovieLens100kTranslator.lineToUser(d);
			userDatabase.add(newUser);
		}
		
		ArrayList<Movie> movieDatabase = new ArrayList<Movie>();
		for(String d: fileData1){
			//System.out.println(d);
			Movie newMovie = MovieLens100kTranslator.lineToMovie(d);
			movieDatabase.add(newMovie);
		}
		
		ArrayList<Rating> ratingDatabase = new ArrayList<Rating>();
		for(String d: fileData2){
			//System.out.println(d);
			Rating newRating = MovieLens100kTranslator.lineToRating(d);
			ratingDatabase.add(newRating);
		}
//		for(Movie u: movieDatabase){
//			System.out.println(u.toString());
//			}
		for(User u: userDatabase){
		System.out.println(u.toString());
		}
//		for(Rating e: ratingDatabase){
//		System.out.println(e.toString());
//		}
	}
}
