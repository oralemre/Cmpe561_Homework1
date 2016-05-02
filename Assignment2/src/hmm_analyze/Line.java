package hmm_analyze;

public class Line {
	private String observation;
	private String tag;
	
	public Line(String observation, String tag){
		this.observation = observation;
		this.tag = tag;
	}
	
	public String getObservation(){
		return this.observation;
	}
	
	public String getTag(){
		return this.tag;
	}
	
	public void setObservation(String observation){
		this.observation = observation;
	}
	
	public void setTag(String tag){
		this.tag = tag;
	}
}
