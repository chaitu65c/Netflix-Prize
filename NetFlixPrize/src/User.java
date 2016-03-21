import java.util.ArrayList;

public class User implements Comparable<User>{
	private long ID;
	private int age;
	private char gender;
	private String zipCode;
	private String occupation;
	private ArrayList<Rating> ratings;   
	private ArrayList<User> allUsers;
	
	public User(){
		allUsers = new ArrayList<User>();
		ratings = new ArrayList<Rating>();
		age = 0;
		occupation = "none";
		gender = 'M';
		zipCode = "";
		ID = 0000000;
	}
	
	public User(long I,int a, char g, String o, String z){
		ratings = new ArrayList<Rating>();
		allUsers = new ArrayList<User>();
		this.ID =I;
		this.age = a;
		this.gender =g;
		this.occupation = o;
		this.zipCode = z;
	}
	
	public String toString(){

		return "ID: " + this.ID + ", AGE: " + this.age + ", GENDER: "+this.gender
				+ ", OCCUPATION: "+this.occupation + ", ZIPCODE: "+this.zipCode + ".";
	}
	
	public void add(Rating t){
		ratings.add(t);
	}
	
	public long getID(){
		return this.ID;	
	}
	public double averageRating(){
		double avg =0, num=0;
		for(int i=0;i<ratings.size();i++){
			avg += ratings.get(i).getS();
			num++;
		}
		return avg/num;
	}
	
	public User getUser(int ID){
		return allUsers.get(ID);
	}

	@Override
	public int compareTo(User o) {
		return (this.ID > o.getID())? 1 : (this.ID== o.getID())? 0: -1;
		}
	
	public static User binarySearch(ArrayList<User> database, long ID){
		return binarySearch(database, ID, 0, database.size()-1);
	}
	
	public static User binarySearch(ArrayList<User> database, long ID, int start, int end){
		int index = (start+end)/2;
		User u= database.get(index);
		
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