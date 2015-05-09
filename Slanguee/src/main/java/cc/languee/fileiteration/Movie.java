package cc.languee.fileiteration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Movie {
	private Map<String, Transcript> transcripts;
	private String id;

	public Movie(String id){
		this.transcripts = new HashMap<String, Transcript>();
		this.id = id;
	}

	public void addTranscript(String language, Transcript transcript){
		this.transcripts.put(language, transcript);	
	}

	public Transcript getTranscript(String language){
		return transcripts.get(language);
	}

	public String getId() {
		return id;
	}

}
