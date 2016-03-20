import java.util.ArrayList;

public class TestDocument {
	private String expected;
	private String actual;
	private ArrayList<Double> probs;
	private ArrayList<String> testWords;
	
	public TestDocument(String expected){
		this.expected = expected;
		this.actual = "";
		this.probs = new ArrayList<Double>();
		this.testWords = new ArrayList<String>();
	}
	
	public void setExpected(String expected){
		this.expected = expected;
	}
	
	public void setActual(String actual){
		this.actual = actual;
	}
	
	public void setProbs(ArrayList<Double> probs){
		this.probs = probs;
	}
	
	public void setTestWords(ArrayList<String> words){
		this.testWords = words;
	}
	
	public String getExpected(){
		return this.expected;
	}
	
	public String getActual(){
		return this.actual;
	}
	
	public ArrayList<Double> getProbs(){
		return this.probs;
	}
	
	public ArrayList<String> getTestWords(){
		return this.testWords;
	}
	
}
