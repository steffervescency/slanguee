package cc.languee.fileiteration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Movie {
	private Map<String, Transcript> transcripts;

	public Movie(){
		this.transcripts = new HashMap<String, Transcript>();
	}

	public void addTranscript(String language, Transcript transcript){
		this.transcripts.put(language, transcript);	
	}

	public Transcript getTranscript(String language){
		return transcripts.get(language);
	}

}
