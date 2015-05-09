package cc.languee.fileiteration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the transcript of something. 
 * @author jpietsch
 *
 */
public class Transcript {
	private String language;
	private List<Sentence> sentences;
	
	public Transcript() {
		sentences = new ArrayList<Sentence>();
	}
	
	public void addSentence(Sentence s) {
		sentences.add(s);
	}
	/*
	public List<SentenceMapping> getMappedSentences(String languagePair) {
		List<SentenceMapping> mappings = new ArrayList<SentenceMapping>(); 

		SentenceMapping s = new SentenceMapping();
		
		mappings.add(s);
		
		return mappings;
	}
	*/
	public List<Sentence> getSentences(String language) {
		List<Sentence> sentences = new ArrayList<Sentence>();
		List<String> words = new ArrayList<String>();
		words.add("This");
		words.add("is");
		words.add("a");
		words.add("test");
		Sentence s = new Sentence(language, "<source>", "1", "00:00:01", words);
		sentences.add(s);
		return sentences;
	}
}
