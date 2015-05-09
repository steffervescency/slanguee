/**
 * Creates a reversed index and allows for quering it
 */
package cc.language.lucene;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import cc.languee.fileiteration.Sentence;
import cc.languee.fileiteration.Transcript;

/**
 * @author adam
 *
 */
public class ReversedIndex {

	protected Analyzer analyzer;
	protected QueryParser parser;
	//TODO: choose a proper name and check what it is
	private String queryRecord = "sentence";
	private Directory index;
	private Version luceneVersion = Version.LUCENE_4_10_2;
	
	@SuppressWarnings("deprecation")
	public ReversedIndex() {
		//analyzer = new EnglishAnalyzer(this.luceneVersion);
		analyzer = new WhitespaceAnalyzer(this.luceneVersion);
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
		IndexWriter indexWriter = new IndexWriter(index, config);
		this.addSentences(indexWriter, language, sentenceIterator);
		indexWriter.close();
	}
	
	
	private void addSentences(IndexWriter indexWriter, String language, Iterator sentenceIterator) throws IOException {
		//for(;sentenceIterator.hasNext();){
		//	sentenceIterator.next();
			Transcript transcript = new Transcript();
			List<Sentence> sentences = transcript.getSentences();
			Sentence sentence = sentences.get(0);
			addSentence(indexWriter, sentence);
		//}
	}
	
	private void addSentence(IndexWriter indexWriter, Sentence sentence) throws IOException{
		  Document doc = new Document();
		  String sentenceId = sentence.getId();
		  String language = sentence.getLanguage();
		  String source = sentence.getSource();
		  String words = StringUtils.join(sentence.getWords()," ");

		  doc.add(new StringField("sentenceId", sentenceId, Field.Store.YES));
		  doc.add(new StringField("language", language, Field.Store.YES));
		  doc.add(new StringField("source", source, Field.Store.YES));
		  doc.add(new Field("sentence", words,
				  Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
		  System.out.println(doc);
		  indexWriter.addDocument(doc);
	}
	

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ReversedIndex index = new ReversedIndex();
		FileUtils.deleteDirectory(new File("./tmp"));
		index.createIndex("./tmp/", "en", null);
	}

}
