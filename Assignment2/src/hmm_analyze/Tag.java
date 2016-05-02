package hmm_analyze;

import java.util.ArrayList;

public class Tag {
	private String name;
	private Integer ID;
	private ArrayList<Double> percentage;
	private ArrayList<Integer> count;
	
	
	public Tag(String name, Integer ID){
		this.name = name;
		this.ID = ID;
		this.percentage = new ArrayList<Double>();
		this.count = new ArrayList<Integer>();
	}
	
	public String getName(){
		return this.name;
	}
	
	public Integer getID(){
		return this.ID;
	}
	
	public ArrayList<Double> getPercentage(){
		return this.percentage;
	}
	
	public ArrayList<Integer> getCount(){
		return this.count;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setID(Integer ID){
		this.ID = ID;
	}
	
	public void setPercentage(ArrayList<Double> percentage){
		this.percentage = percentage;
	}
	
	public void setCount(ArrayList<Integer> count){
		this.count = count;
	}
}
