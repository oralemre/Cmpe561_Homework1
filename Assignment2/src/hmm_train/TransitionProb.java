package hmm_train;

public class TransitionProb {
	private String prevTag;
	private String tag;
	private Double count;
	private Double prevTagCount;
	private Double prob;
	
	public TransitionProb(String prevTag , String tag){
		this.prevTag = prevTag;
		this.tag = tag;
		this.count = 1.0;
		this.prevTagCount = 0.0;
		this.prob = 0.0;
	}
	
	public String getPrevTag(){
		return this.prevTag;
	}
	
	public String getTag(){
		return this.tag;
	}
	
	public Double getCount(){
		return this.count;
	}
	
	public Double getPrevTagCount(){
		return this.prevTagCount;
	}
	
	public Double getProb(){
		return this.prob;
	}
	
	public void setPrevTag(String prevTag){
		this.prevTag = prevTag;
	}
	
	public void setTag(String tag){
		this.tag = tag;
	}
	
	public void setCount(Double count){
		this.count = count;
	}
	
	public void setTagCount(Double tagCount){
		this.prevTagCount = tagCount;
	}
	
	public void setProb(Double prob){
		this.prob = prob;
	}
	
	public void incrementCount(){
		this.count += 1;
	}
	
	public Double calculateProb(){
		return this.prob = this.count / this.prevTagCount;
	}
}
