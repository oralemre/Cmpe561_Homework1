import java.util.HashMap;
import java.util.Map;

public class Classes {
	private Map<String,Integer> featureFrequency;
	private String name; // name of the class (i.e abbasGuclu)
	private Integer index; // (index of class in Classes ArrayList on NaiveBayes Class)
	private Integer numberOfWords; // (total frequencies of words in a class)
	private Integer numberOfFunctionalWords; // (total frequencies of functional words in a class)
	private Double classProb;
	private Double tp; // (true positive)
	private Double tn; // (true negative)
	private Double fp; // (false positive)
	private Double fn; // (false negative)
	
	public Classes(String name, Integer index){
		this.classProb = 1.0;
		this.name = name;
		this.featureFrequency = new HashMap<String,Integer>();
		this.numberOfWords = 0;
		this.numberOfFunctionalWords = 0;
		this.index = index;
		this.tp = 0.0;
		this.tn = 0.0;
		this.fp = 0.0;
		this.fn = 0.0;
	}
	
	public void setIndex(Integer index){
		this.index = index;
	}
	
	public void setTp(Double tp){
		this.tp = tp;
	}
	
	public void setTn(Double tn){
		this.tn = tn;
	}
	
	public void setFp(Double fp){
		this.fp = fp;
	}
	
	public void setFn(Double fn){
		this.fn = fn;
	}
	
	public void setNumberOfFunctionalWords(Integer numberOfFunctionalWords){
		this.numberOfFunctionalWords = numberOfFunctionalWords;
	}
	
	public void setFeatureFrequency(Map<String,Integer> map){
		this.featureFrequency = map;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setNumberOfWords(Integer numberOfWords){
		this.numberOfWords = numberOfWords;
	}
	
	public void setClassProb(Double prob){
		this.classProb = prob;
	}
	
	public Integer getIndex(){
		return this.index;
	}
	
	public Double getTp(){
		return this.tp;
	}
	
	public Double getTn(){
		return this.tn;
	}
	
	public Double getFp(){
		return this.fp;
	}
	
	public Double getFn(){
		return this.fn;
	}
	
	public Integer getNumberOfFunctionalWords(){
		return this.numberOfFunctionalWords;
	}
	
	public Map<String,Integer> getFeatureFrequency(){
		return this.featureFrequency;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Integer getNumberOfWords(){
		return this.numberOfWords;
	}
	
	public Double getClassProb(){
		return this.classProb;
	}
}
