/**
 * A machinery to answer queries to reverted index
 */
package cc.language.lucene;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
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
	private Version luceneVersion = Version.LUCENE_4_10_2;
	protected Analyzer analyzer;
	private String queryRecord = "sentence";
	//maximal allowed length between tokens in the result
	private int maxLenBetweenTokens = 2;
	
	
	public Querying(){
		//analyzer = new WhitespaceAnalyzer(this.luceneVersion);
		analyzer = new SimpleAnalyzer(this.luceneVersion);
		parser = new QueryParser(this.luceneVersion, this.queryRecord, this.analyzer);
	}
	
	
	public ArrayList<QueryHit> findSimilar(String phrase, String language) throws IOException, ParseException{
		return this.findSimilar(phrase, language, 0.10, 10);
	}

	
	public ArrayList<QueryHit> findSimilar(String phrase, String language, double threshold, int numberOfResults) throws IOException, ParseException{		 
		Query query = this.createQuery(phrase);
		System.out.println(query);
		TopScoreDocCollector collector = TopScoreDocCollector.create(numberOfResults,true);
		iSearcher.search(query, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		ArrayList<QueryHit> result = new ArrayList<QueryHit>();
		for(ScoreDoc hit : hits){
			Document doc = this.iSearcher.doc(hit.doc);
			QueryHit queryHit = new QueryHit(doc);
			result.add(queryHit);
		}
		return result;
	}

	
	/**
	 * Creating a query which takes order into account
	 * @param phrase
	 * @return
	 * @throws ParseException
	 * @throws IOException 
	 */
	private Query createQuery(String phrase) throws ParseException, IOException {
		//TokenStream tokenStream = analyzer.tokenStream(this.queryRecord, phrase);
		phrase = phrase.toLowerCase();
		String[] tokens = phrase.split(" ");
		if(tokens.length <= 1)
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

	
	/**
	 * 
	 * @param indexPath - a path to the created reverted index 
	 * @throws IOException
	 */
	public void loadIndex(String indexPath) throws IOException{
		index = FSDirectory.open(new File( indexPath ));
		indexReader = DirectoryReader.open(index);
		iSearcher   = new IndexSearcher(indexReader);
	}

	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException {
		Querying querying = new Querying();
		querying.loadIndex("./tmp_en_copy");
		ArrayList<QueryHit> findSimilar = querying.findSimilar("it is great", "en");
		System.out.println(findSimilar.size());
		for(QueryHit qh : findSimilar)
			System.out.println(qh);
		
	}

}
