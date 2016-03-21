import java.util.ArrayList;

public class MovieLens100kTranslator {

	public static User lineToUser(String line) {
		// Parse the String line to get out each piece of data
		// Create a User object, passing in the data
		// return that Object
		String[] l = line.split("\\|");
		long id = Long.parseLong(l[0]);
		int age = Integer.parseInt(l[1]);
		char g = l[2].charAt(0);
		String o = l[3];
		String z = l[4];
		User u = new User(id, age, g, o, z);
		return u;
	}

	public static Movie lineToMovie(String line) {
		String[] lin = line.split("\\|");
		String[] gen = { "unknown", "Action", "Adventure", "Animation", "Children's", "Comedy", "Crime", "Documentary",
				"Drama", "Fantasy", "Film-Noir", "Horror", "Musical", "Mystery", "Romance", "Sci-Fi", "Thriller", "War",
				"Western" };
		ArrayList<String> genres = new ArrayList<String>();
		for (int i = 0; i < gen.length; i++) {
			
			if (lin[i+5].equals("1")) {
				genres.add(gen[i]);
			}
		}
		return new Movie(Long.parseLong(lin[0]), lin[1], lin[2], genres);
	}

	public static Rating lineToRating(String line) {
		String[] lin = line.split("\\s");
		Rating r = new Rating(Long.parseLong(lin[0]),Long.parseLong(lin[1]),(double)Integer.parseInt(lin[2]));
		return r;
	}
}
