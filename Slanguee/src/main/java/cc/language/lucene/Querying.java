/**
 * 
 */
package cc.language.lucene;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.util.CharArraySet;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * @author adam
 *
 */
public class Querying {

	private IndexSearcher iSearcher;
	private Directory index;
	protected QueryParser parser;
	private IndexReader indexReader;
	private MoreLikeThis mlt;
	private Version luceneVersion = Version.LUCENE_4_10_2;
	protected Analyzer analyzer;
	private String queryRecord = "sentence";
	//maximal allowed length between tokens in the result
	private int maxLenBetweenTokens = 2;
	
	
	public Querying(){
		
		analyzer = new WhitespaceAnalyzer(this.luceneVersion );
		parser = new QueryParser(this.luceneVersion, this.queryRecord, this.analyzer);
	}
	
	
	public ScoreDoc[] findSimilar(String phrase, String language) throws IOException, ParseException{
		return this.findSimilar(phrase, language, 0.10, 10);
	}
	
	public ScoreDoc[] findSimilar(String phrase, String language, double threshold, int numberOfResults) throws IOException, ParseException{		 
		Query query = this.createQuery(phrase);
		System.out.println(query);
		TopScoreDocCollector collector = TopScoreDocCollector.create(numberOfResults,true);
		iSearcher.search(query, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		return hits;
	}
	
	private Query createQuery(String phrase) throws ParseException {
		String[] tokens = phrase.split(" ");
		assert(tokens.length > 0);
		String token = tokens[0];
		if(tokens.length == 1)
			return parser.parse(phrase);
		else{
			SpanNearQuery spanNearQuery = new SpanNearQuery(new SpanQuery[] {
					  new SpanTermQuery(new Term(this.queryRecord, tokens[0])),
					  new SpanTermQuery(new Term(this.queryRecord, tokens[1]))},
					  this.maxLenBetweenTokens,
					  true);
			for (int i = 2; i < tokens.length; i++) {
				spanNearQuery = new SpanNearQuery(new SpanQuery[] {
						spanNearQuery,
						  new SpanTermQuery(new Term(this.queryRecord, tokens[i]))},
						  this.maxLenBetweenTokens,
						  true);
			}
			return spanNearQuery;
		}
	}


	public void loadIndex(String indexPath) throws IOException{
		index = FSDirectory.open(new File( indexPath ));
		indexReader = DirectoryReader.open(index);
		iSearcher   = new IndexSearcher(indexReader);
		
		mlt         = new MoreLikeThis(indexReader);
		mlt.setAnalyzer(analyzer);
		mlt.setFieldNames(new String[]{this.queryRecord});
		//Search settings
		//mlt.setBoost(true);
		//mlt.setMinDocFreq(0);
		//mlt.setMinTermFreq(0);
		//mlt.setMinWordLen(1);
		//mlt.setMaxNumTokensParsed(10000);
		//mlt.setMaxQueryTerms(10000);
		//mlt.setStopWords(MoreLikeThis.DEFAULT_STOP_WORDS);
	}
	
	
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException {
		Querying querying = new Querying();
		querying.loadIndex("./tmp");
		ScoreDoc[] findSimilar = querying.findSimilar("is a test", "en");
		System.out.println(findSimilar.length);
		System.out.println(findSimilar[0]);
		
	}

}
