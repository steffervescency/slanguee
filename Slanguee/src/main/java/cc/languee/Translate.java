/**
 * 
 */
package cc.languee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cc.language.lucene.PairQueryHit;
import cc.language.lucene.QueryHit;
import cc.language.lucene.Querying;

/**
 * @author adam
 *
 */
public class Translate {
    private String englishIndexPath = "./tmp_en";
    private String germanIndexPath = "./tmp_de";
	
    private Querying englishIndex;
    private Querying germanIndex;
    
    private DBMappingAccess dbMappingAccess;
    
	public Translate() throws IOException{
		//load indexes
		this.englishIndex = new Querying();
		this.englishIndex.loadIndex(this.englishIndexPath);

		this.germanIndex = new Querying();
		this.germanIndex.loadIndex(this.germanIndexPath);
		
		this.dbMappingAccess = new DBMappingAccess();
	}
	
	
	public List<PairQueryHit> giveTranslation(String query, String sourceLanguage, String targetLanguage) throws Exception{
		Querying sourceIndex = this.chooseIndex(sourceLanguage);
		Querying targetIndex = this.chooseIndex(targetLanguage);
		ArrayList<QueryHit> sourceLangResults = sourceIndex.findSimilar(query, sourceLanguage);
		ArrayList<PairQueryHit> targetLangResults = new ArrayList<PairQueryHit>();
		for(QueryHit qh : sourceLangResults){
			List<QueryHit> targetHit = this.mapToDifferentLanguage(qh, sourceLanguage, targetLanguage, targetIndex);
			ArrayList<QueryHit> sourceResult = new ArrayList<QueryHit>();
			sourceResult.add(qh);
			PairQueryHit pqh = new PairQueryHit(sourceResult, targetHit);
			targetLangResults.add(pqh);
		}
		return targetLangResults;
	}
	
	
	private Querying chooseIndex(String language) throws Exception{
		if(language.compareTo("en") == 0)
			return englishIndex;
		else if(language.compareTo("de") == 0)
			return germanIndex;
		else
			throw new Exception("No language available: " + language );
	}
	
	private List<QueryHit> mapToDifferentLanguage(QueryHit queryHit, String sourceLanguage, String targetLanguage, Querying targetIndex) throws IOException{
		String originFileName = queryHit.getSource();
		int originLineNumber = Integer.parseInt(queryHit.getId());
		String fileNameMapping = this.dbMappingAccess.getFileNameMapping(originFileName, sourceLanguage, targetLanguage);
		if(fileNameMapping == null)
			return null;
		ArrayList<Integer> lineNumberMapping = this.dbMappingAccess.getLineNumberMapping(originFileName, originLineNumber, sourceLanguage, targetLanguage);
		if(lineNumberMapping.size() == 0)
			return null;
		List<QueryHit> result = new ArrayList<QueryHit>();
		for(Integer lineNumber : lineNumberMapping){
			QueryHit targetSentence = targetIndex.findSentenceBySourceAndLine(targetLanguage, fileNameMapping, Integer.toString(lineNumber));
			result.add(targetSentence);
		}
		return result;
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Translate translator = new Translate();
		List<PairQueryHit> giveTranslation = translator.giveTranslation("Ich verstehe nur", "de", "en");
		for(PairQueryHit qh : giveTranslation){
			System.out.println(qh);
			System.out.println("##############");
		}
	}

}
