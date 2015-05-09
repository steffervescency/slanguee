package cc.languee.fileiteration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Sentence {

	private String language;
	private String id;
	private String timestamp; 
	private String source;
	private List<String> words; 

	//private Sentence() {/* empty constructor */};

	public Sentence(String language, String source, String id, String timestamp, List<String> words) {
		this.language = language;
		this.source = source;
		this.id = id;
		this.timestamp = timestamp;
		this.words = words;
	}

	public List<String> getWords() {
		return words;
	}

}
