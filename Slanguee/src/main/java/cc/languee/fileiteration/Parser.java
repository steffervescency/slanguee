package cc.languee.fileiteration;

import java.util.ArrayList;
import java.util.List;


public class Parser {

	public List<Movie> parse(String filepath) {
		List<Movie> movies = new ArrayList<Movie>();
		
		Movie m = new Movie();
		Transcript transcript;
		
		transcript = new Transcript();
		m.addTranscript("de", transcript);
		transcript = new Transcript();
		m.addTranscript("en", transcript);
		
		movies.add(m);
		
		return movies;
	}
	
}
