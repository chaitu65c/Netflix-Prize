import java.util.ArrayList;

public class Rating implements Comparable<Rating>{
	private long user;
	private long  movie;
	private double stars;
	
	public Rating(long u, long m, double s){
		user =u;
		movie = m;
		stars = s;
		}
	
	public String toString(){
		return "User: " + user+ ", Movie: " + movie+ ", Rating: " + stars; 
	}
	
	public long getU(){
		return user;
	}

	public long getM(){
		return movie;
	}
	
	public double getS(){
		return stars;
	}

	@Override
	public int compareTo(Rating o) {
		return (this.user > o.getU())? 1 : (this.user == o.getU())? 0: -1;
	}
	
	public static Rating binarySearch(ArrayList<Rating> database, long ID){
		return binarySearch(database, ID, 0, database.size()-1);
	}
	
	public static Rating binarySearch(ArrayList<Rating> database, long ID, int start, int end){
		int index = (start+end)/2;
		Rating u= database.get(index);
		
		if(u.getU() == ID){
			return u;
		}
		else if(start == end){
			return null;
		}
		else if(u.getU() > ID){
			return binarySearch(database, ID, start, index-1);
		}
		else{
			return binarySearch(database, ID, index+1, end);
		}
	}
	
}
