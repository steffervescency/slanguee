/**
 * 
 */
package cc.language.lucene;

import java.util.List;

import org.apache.lucene.document.Document;

/**
 * @author adam
 *
 */
public class QueryHit {

	private Document doc;
	private String language;
	private String id;
//	private String timestamp; 
	private String source;
	private String words; 
	
	public QueryHit(Document doc){
		this.doc = doc;
		this.language = this.doc.getField("language").stringValue();
		this.source = this.doc.getField("source").stringValue();
		this.id = this.doc.getField("sentenceId").stringValue();
		this.words = this.doc.getField("sentence").stringValue();
	}
	
	public Document getDoc() {
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
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

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}
	
	public String toString(){
		return this.words + " " + this.source + " " + this.id + " " + this.language;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
