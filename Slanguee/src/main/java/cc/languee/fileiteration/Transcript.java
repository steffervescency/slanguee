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
	private Map<String, Sentence> sentenceMap;

	public Transcript() {
		sentences = new ArrayList<Sentence>();
		sentenceMap = new HashMap<String, Sentence>();
	}

	public void addSentence(Sentence sentence) {
		sentences.add(sentence);
		sentenceMap.put(sentence.getId(), sentence);
	}

	public List<Sentence> getSentences() {
		return sentences;
	}

	public Sentence getSentenceById(String id) {
		return sentenceMap.get(id);
	}
	
	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}
}
