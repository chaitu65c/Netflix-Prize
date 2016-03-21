import java.util.ArrayList;

public class Movie implements Comparable<Movie>{
	private long ID;
	private String title;
	
	private String releaseDate;
	private ArrayList<String> genres;
	private ArrayList<Rating> ratings;
	private ArrayList<Movie> allMovies;
	private int decade;
	private int year;
	
	public Movie(long I, String t,  String r, ArrayList<String> gen){
		ratings =  new ArrayList<Rating>();
		allMovies = new ArrayList<Movie>();
		this.ID =I;
		this.title =t;
		this.releaseDate =r;
		this.genres = gen;
		
	}
	
	public Movie(){
		
		ratings =  new ArrayList<Rating>();
		allMovies = new ArrayList<Movie>();
		this.ID =0;
		this.title =null;
		this.releaseDate =null;
		this.genres = null;
		
	}
	
	public String toString(){
		String s= "ID: " + this.ID + ", TITLE: " + this.title + ", RELEASE DATE: "+this.releaseDate + ", "+ "Genres: ";
		for(int i =0; i< this.genres.size(); i++){
			s += this.genres.get(i) + " ";
		}
		return s;
	}
	
	public void add(Rating rating){
		ratings.add(rating);
	}
	
	public String getTitle(){
		return title;
	}
	
	public Movie getMovie(int ID){
		return allMovies.get(ID);
	}
	
	public int getYear(){
		return this.year;
	}
	public int getDecade(){
		return this.decade;
	}
	public ArrayList<Rating> getRating(){
		return ratings;
	}
	public double averageRating(){
		double avg=0;
		for(Rating r: ratings){
			avg += r.getS();
		}
		return avg/ratings.size();
	}
	public long getID(){
		return this.ID;
	}

	@Override
	public int compareTo(Movie arg0) {
		return (this.ID > arg0.getID())? 1 : (this.ID== arg0.getID())? 0: -1;
	}
	public static Movie binarySearch(ArrayList<Movie> database, long ID){
		return binarySearch(database, ID, 0, database.size()-1);
	}
	
	public static Movie binarySearch(ArrayList<Movie> database, long ID, int start, int end){
		int index = (start+end)/2;
		Movie u= database.get(index);
		
		if(u.getID() == ID){
			return u;
		}
		else if(start == end){
			return null;
		}
		else if(u.getID() > ID){
			return binarySearch(database, ID, start, index-1);
		}
		else{
			return binarySearch(database, ID, index+1, end);
		}
	}
	
	
}
