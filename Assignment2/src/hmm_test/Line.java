package hmm_test;

public class Line {
	private Integer ID;
	private String observation;
	private String hiddenTag;
	private Integer sentenceID;
	private String status;
	
	public Line(Integer ID, String observation, Integer sentenceID){
		this.ID = ID;
		this.observation = observation;
		this.hiddenTag = "unknown";
		this.sentenceID = sentenceID;
		this.status = "unknown";
	}
	
	public Line(Integer ID, String observation, String tag, Integer sentenceID){
		this.ID = ID;
		this.observation = observation;
		this.hiddenTag = tag;
		this.sentenceID = sentenceID;
		this.status = "unknown";
	}
	
	public Integer getID(){
		return this.ID;
	}
	
	public String getObservation(){
		return this.observation;
	}
	
	public String getHiddenTag(){
		return this.hiddenTag;
	}
	
	public Integer getSentenceID(){
		return this.sentenceID;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public void setID(Integer ID){
		this.ID = ID;
	}
	
	public void setObservation(String observation){
		this.observation = observation;
	}
	
	public void setHiddenTag(String tag){
		this.hiddenTag = tag;
	}
	
	public void setSentenceID(Integer sentenceID){
		this.sentenceID = sentenceID;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
}
