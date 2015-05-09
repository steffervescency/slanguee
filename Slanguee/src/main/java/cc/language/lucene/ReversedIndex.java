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
import org.apache.lucene.analysis.core.SimpleAnalyzer;
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

import cc.languee.fileiteration.Movie;
import cc.languee.fileiteration.MovieIterator;
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
		//analyzer = new WhitespaceAnalyzer(this.luceneVersion);
		analyzer = new SimpleAnalyzer(this.luceneVersion);
		parser   = new QueryParser(this.luceneVersion, this.queryRecord, this.analyzer);
	}
	
	/**
	 * Creates a reversed index based on the sentences from the sentenceIterator
	 * @param indexPath
	 * @param language
	 * @param sentenceIterator
	 * @throws IOException
	 */
	public void createIndex(String indexPath, String language, MovieIterator movieIterator) throws IOException{
		index = FSDirectory.open(new File(indexPath));
		IndexWriterConfig config = new IndexWriterConfig(this.luceneVersion, this.analyzer);
		IndexWriter indexWriter = new IndexWriter(index, config);
		this.addSentences(indexWriter, language, movieIterator);
		indexWriter.close();
	}
	
	
	private void addSentences(IndexWriter indexWriter, String language, MovieIterator movieIterator) throws IOException {
		for(;movieIterator.hasNext();){
			Movie movie = movieIterator.next();
			String src = movie.getId();
			Transcript transcript = movie.getTranscript(language);
			List<Sentence> sentences = transcript.getSentences();
			for(Sentence sentence : sentences){
			    //sentenceIterator.next();
				/*Transcript transcript = new Transcript();
				List<Sentence> sentences = transcript.getSentences();
				Sentence sentence = new Sentence();
				sentence.addWord("This is a test");
				sentence.setId("1");
				sentence.setLanguage("en");
				sentence.setSource("asd/asdasd/asdasd/");*/
				addSentence(indexWriter, sentence, language, src);				
			}

		}
	}
	
	private void addSentence(IndexWriter indexWriter, Sentence sentence, String lang, String src) throws IOException{
		  Document doc = new Document();
		  String sentenceId = sentence.getId();
		  String language = sentence.getLanguage();
		  if(language == null)
			  language = lang;
		  String source = sentence.getSource();
		  if(source == null)
			  source = src;
		  String words = StringUtils.join(sentence.getWords()," ");

		  doc.add(new StringField("sentenceId", sentenceId, Field.Store.YES));
		  doc.add(new StringField("language", language, Field.Store.YES));
		  doc.add(new StringField("source", source, Field.Store.YES));
		  doc.add(new Field("sentence", words,
				  Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS));
		  indexWriter.addDocument(doc);
	}
	

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		//String subtitlesDirectoryPath = "/home/adam/workspace/Slanguee2/data/subtitles/OpenSubtitles2013/xml/de";
		String subtitlesDirectoryPath = "/home/adam/workspace/Slanguee2/data/subtitles/en/OpenSubtitles2013/xml/en";
		//String indexDir = "./tmp_de";
		String indexDir = "./tmp_en";
		File subtitlesDirectory = new File(subtitlesDirectoryPath);
		//String language = "de";
		String language = "en";
		MovieIterator movieIterator = new MovieIterator(subtitlesDirectory, language);
		ReversedIndex index = new ReversedIndex();
		FileUtils.deleteDirectory(new File(indexDir));
		index.createIndex(indexDir, language, movieIterator);
	}

}
