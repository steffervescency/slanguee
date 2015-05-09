/**
 * Creates a reversed index and allows for quering it
 */
package cc.language.lucene;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * @author adam
 *
 */
public class ReversedIndex {

	protected Analyzer analyzer;
	protected QueryParser parser;
	//TODO: choose a proper name and check what it is
	private String queryRecord = "synsetId";
	private Directory index;
	private Version luceneVersion = Version.LUCENE_4_10_2;
	
	@SuppressWarnings("deprecation")
	public ReversedIndex() {
		analyzer = new EnglishAnalyzer(this.luceneVersion);
		//analyzer = new StandardAnalyzer(this.luceneVersion);
		parser   = new QueryParser(this.luceneVersion, this.queryRecord, this.analyzer);
	}
	
	/**
	 * Creates a reversed index based on the senteces from the sentenceIterator
	 * @param indexPath
	 * @param language
	 * @param sentenceIterator
	 * @throws IOException
	 */
	public void createIndex(String indexPath, String language, Iterator sentenceIterator) throws IOException{
		index = FSDirectory.open(new File(indexPath));
		IndexWriterConfig config = new IndexWriterConfig(this.luceneVersion, this.analyzer);
		IndexWriter w = new IndexWriter(index, config);
		this.addSentences(language, sentenceIterator);
		w.close();
	}
	
	
	private void addSentences(String language, Iterator sentenceIterator) {
		for(;sentenceIterator.hasNext();){
			sentenceIterator.next();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
