package hmm_train;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HMM_Train {
	public static ArrayList<String> word_tagGroups = new ArrayList<String>(); // it contains groups like . Punc
	public static ArrayList<String> tag_tagGroups = new ArrayList<String>(); // it contains groups like Noun Verb -> it stores which tags comes after from which tag
	public static ArrayList<WordLikelihoodProb> wprobs = new ArrayList<WordLikelihoodProb>();
	public static ArrayList<TransitionProb> tprobs = new ArrayList<TransitionProb>(); 
	public static Map<String,Double> countTag= new HashMap<String,Double>();
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String path = args[0];
		String tagset = args[1];
		PrintStream prob_t = new PrintStream("prob_t"); // transition probs are written to this file.
		PrintStream prob_w = new PrintStream("prob_w"); // word likelihood probs are written to this file.
		Integer option = -1;
		if(tagset.equalsIgnoreCase("cpostag")){
			option = 3; // if tag set choice is cpostag, makes option 3.
		}else if(tagset.equalsIgnoreCase("postag")){
			option = 4; // if tag set choice is postag, makes option 4.
		}else{
			option = -1;
		}
		trainTagger(option,path); // estimates parameters for HMM (calculates word likelihood probs and tag transition probs)
		
		prob_t.println(tagset);
		for(int i = 0; i < tprobs.size(); i++){
			prob_t.println(tag_tagGroups.get(i) + " " + tprobs.get(i).getProb());
		}
		
		
		for(int i = 0; i < wprobs.size(); i++){
			prob_w.println(word_tagGroups.get(i) + " " + wprobs.get(i).getProb());
		}
		
		prob_t.close();
		prob_w.close();
	}
	
	public static void trainTagger(Integer option, String path) throws UnsupportedEncodingException, FileNotFoundException{
		Reader reader = new InputStreamReader(new FileInputStream(path),"UTF-8");
		BufferedReader br = new BufferedReader(reader);
		Scanner read = new Scanner(br);
		String prev = "Start";
		countTag.put(prev, 1.0);
		String current = "";
		Integer previousID = 0;
		Integer currentID = 0;
		
		while(read.hasNextLine()){
			ArrayList<String> list1 = new ArrayList<String>();
			String str = read.nextLine();
			Scanner input = new Scanner(str);
			while(input.hasNext()){
				list1.add(input.next());
			}
			input.close();
			if(list1.size() != 0){
				currentID = Integer.valueOf(list1.get(0));
				if(previousID > currentID){
					current = "Finish";
					
					if(!tag_tagGroups.contains(prev + " " + current)){
						TransitionProb t = new TransitionProb(prev,current);
						tag_tagGroups.add(prev + " " + current);
						tprobs.add(t);
					}else{
						tprobs.get(tag_tagGroups.indexOf(prev +  " " + current)).incrementCount();
					}
					prev = "Start";
					countTag.put(prev, countTag.get(prev)+1.0);
				}
				current = list1.get(option);
				previousID = currentID;
			}
			
			
			if((list1.size() != 0) && !(list1.get(1).equals("_"))){
				
				if(!countTag.containsKey(current)){
					countTag.put(current, 1.0);
				}else{
					countTag.put(current, countTag.get(current)+1.0);
				}
				
				if(!word_tagGroups.contains(list1.get(1) +  " " + current)){
					WordLikelihoodProb w = new WordLikelihoodProb(list1.get(1), current);
					word_tagGroups.add(list1.get(1) +  " " + current);
					wprobs.add(w);
				}else{
					wprobs.get(word_tagGroups.indexOf(list1.get(1) +  " " + current)).incrementWordCount();
				}
				
				if(!tag_tagGroups.contains(prev + " " + current)){
					TransitionProb t = new TransitionProb(prev,current);
					tag_tagGroups.add(prev + " " + current);
					tprobs.add(t);
				}else{
					tprobs.get(tag_tagGroups.indexOf(prev +  " " + current)).incrementCount();
				}
				prev = current;
			}
		}
		
		for(int i = 0; i < wprobs.size(); i++){
			Double tagCount = countTag.get(word_tagGroups.get(i).split(" ")[1]);
			wprobs.get(i).setTagCount(tagCount);
			wprobs.get(i).calculateProb();
		}
		
		for(int i = 0; i < tprobs.size(); i++){
			Double tagCount = countTag.get(tag_tagGroups.get(i).split(" ")[0]);
			tprobs.get(i).setTagCount(tagCount);
			tprobs.get(i).calculateProb();
		}
		
		read.close();
	}
}
