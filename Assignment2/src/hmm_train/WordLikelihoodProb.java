package hmm_train;
public class WordLikelihoodProb {
	private String word;
	private String tag;
	private Double wordCount;
	private Double tagCount;
	private Double prob;
	
	public WordLikelihoodProb(String word , String tag){
		this.word = word;
		this.tag = tag;
		this.wordCount = 1.0;
		this.tagCount = 0.0;
		this.prob = 0.0;
	}
	
	public String getWord(){
		return this.word;
	}
	
	public String getTag(){
		return this.tag;
	}
	
	public Double getWordCount(){
		return this.wordCount;
	}
	
	public Double getTagCount(){
		return this.tagCount;
	}
	
	public Double getProb(){
		return this.prob;
	}
	
	public void setWord(String word){
		this.word = word;
	}
	
	public void setTag(String tag){
		this.tag = tag;
	}
	
	public void setWordCount(Double wordCount){
		this.wordCount = wordCount;
	}
	
	public void setTagCount(Double tagCount){
		this.tagCount = tagCount;
	}
	
	public void setProb(Double prob){
		this.prob = prob;
	}
	
	public void incrementWordCount(){
		this.wordCount += 1;
	}
	
	public Double calculateProb(){
		return this.prob = this.wordCount / this.tagCount;
	}
}
