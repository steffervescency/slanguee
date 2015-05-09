package cc.languee.fileiteration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Sentence {

	private String language;
	private String id;
//	private String timestamp; 
	private String source;
	private List<String> words; 

	public Sentence(){
		words = new ArrayList<String>();
	}
	
	public void addWord(String word) {
		words.add(word);
	}
	
	public List<String> getWords() {
		return words;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public String toString() {
		String result="";
		for(String word:getWords()){
			result+= word+" ";
		}
		return result;
	}
}
