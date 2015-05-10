/**
 * 
 */
package cc.language.lucene;

import java.util.List;

/**
 * @author adam
 *
 */
public class PairQueryHit {
	
	private List<QueryHit> sourceSentences;
	private List<QueryHit> targetSentences;
	
	public PairQueryHit(List<QueryHit> sourceSentences, List<QueryHit> targetSentences){
		this.sourceSentences = sourceSentences;
		this.targetSentences = targetSentences;
	}

	public List<QueryHit> getSourceSentences() {
		return sourceSentences;
	}


	public void setSourceSentences(List<QueryHit> sourceSentences) {
		this.sourceSentences = sourceSentences;
	}


	public List<QueryHit> getTargetSentences() {
		return targetSentences;
	}


	public void setTargetSentences(List<QueryHit> targetSentences) {
		this.targetSentences = targetSentences;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(QueryHit qh : sourceSentences){
			if(qh == null){
				sb.append("null");
			}
			else{
				sb.append(qh.toString());
				sb.append("\n");				
			}
		}
		sb.append("---\n");
		for(QueryHit qh : targetSentences){
			if(qh == null){
				sb.append("null");
			}
			else{
				sb.append(qh.toString());
				sb.append("\n");				
			}
		}
		return sb.toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
