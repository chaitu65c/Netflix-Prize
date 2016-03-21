import java.util.ArrayList;
import java.util.Collections;


public class NetFlixPredictor {


	// Add fields to represent your database.
	private ArrayList<Movie> Md;
	private ArrayList<User> Ud;
	private ArrayList<Rating> Rd;
	private byte[][] rating;
	

	
	/**
	 * 
	 * Use the file names to read all data into some local structures. If any other database files are used,
	 * make sure they are stored in your Eclipse project and hardcode their location.
	 * 
	 * @param ratingsFilename The filename of the ratings database.
	 * @param usersFilename The filename of the users database.
	 * @param moviesFilename The filename of the movies database.
	 */
	public NetFlixPredictor(String ratingsFilename, String usersFilename, String moviesFilename) {

		FileIO reader = new FileIO();
		ArrayList<String> fileData =reader.readFile(usersFilename);
		ArrayList<String> fileData1 =reader.readFile(moviesFilename);
		ArrayList<String> fileData2 =reader.readFile(ratingsFilename);
		Md = new ArrayList<Movie>();
		Ud = new ArrayList<User>();
		Rd = new ArrayList<Rating>();

		for(String d: fileData){
			User newUser = MovieLens100kTranslator.lineToUser(d);
			Ud.add(newUser);
		}

		for(String d: fileData1){
			Movie newMovie = MovieLens100kTranslator.lineToMovie(d);
			Md.add(newMovie);
		}
		
		for(String d: fileData2){
			Rating newRating = MovieLens100kTranslator.lineToRating(d);
			Rd.add(newRating);
		}
		
		rating = new byte[Ud.size()+1][Md.size()+1];
		
		for(Rating t: Rd){
			rating[(int) t.getU()][(int)t.getM() ] = (byte)t.getS();
		}
		Collections.sort(Ud);
		Collections.sort(Md);
		Collections.sort(Rd);
	}
	

	/**
	 * Returns an array of objects representing movie data.
	 * 
	 * @return An array of objects representing individual movies.
	 */
	public Object[] getMovieData() {
		return Md.toArray();
	}
	

	/**
	 * Returns an array of objects representing user data.
	 * 
	 * @return An array of objects representing individual users.
	 */
	public Object[] getUserData() {
		return Ud.toArray();
	}

	
	/**
	 * If userNumber has rated movieNumber, return the rating. Otherwise, return -1.
	 * 
	 * @param userNumber The ID of the user.
	 * @param movieNumber The ID of the movie.
	 * @return The rating that userNumber gave movieNumber, or -1 if the user does not exist in the database, the movie does not exist, or the movie has not been rated by this user.
	 */
	public double getRating(long userID, long movieID) {
		double ratings = rating[(int)userID][(int)movieID];
		if(ratings ==0){
			return -1;
		}    
		return ratings;
	}
	
	/**
	 * If userNumber has rated movieNumber, return the rating. Otherwise, use other available data to guess what this user would rate the movie.
	 * 
	 * @param userNumber The ID of the user.
	 * @param movieNumber The ID of the movie.
	 * @return The rating that userNumber gave movieNumber, or the best guess if the movie has not been rated by this user.
	 * @pre A user with id userID and a movie with id movieID exist in the database.
	 */
	public double guessRating(long userID, long movieID) {
	    if(getRating(userID, movieID) != -1){
	    	return getRating(userID, movieID);
	    }
	    

	    return Math.round((getUserAvg((int)userID) + getMovieAvg((int)movieID))/2);
	}
	
	public double getUserAvg(long userID){
		double avg,num;
		avg=num=0;
		for(Rating r: Rd){
			if(r.getU() == userID){
				avg+= r.getS();
				num++;
			}
		}
		if(num == 0){
			return 0;
		}
		return Math.round(avg/num);
	}
	
	public double getMovieAvg(long movieID){
		double avg=0;
		int num =0;
		for(Rating r: Rd){
			if(r.getM() == movieID){
				avg += r.getS();
				num++;
			}
		}
		if(num == 0){
			return 0;
		}
		return Math.round(avg/num);
		
	}
}
