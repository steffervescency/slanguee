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

	public void addSentence(Sentence sentence) {
		sentences.add(sentence);
	}

	public List<Sentence> getSentences() {
		return sentences;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}
}
