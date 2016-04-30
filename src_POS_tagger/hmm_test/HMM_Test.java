package hmm_test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HMM_Test {
	public static Map<String,ArrayList<String>> wordsAndTags = new HashMap<String,ArrayList<String>>();
	public static ArrayList<String> alltags = new ArrayList<String>(); // All tags for tag set given in hmm_train package.
	public static ArrayList<String> word_tag = new ArrayList<String>();
	public static Map<String,Double> probs_wordtag = new HashMap<String,Double>();
	public static ArrayList<String> tag_tag = new ArrayList<String>();
	public static Map<String,Double> probs_tagtag = new HashMap<String,Double>();
	public static ArrayList<ArrayList<Line>> lines = new ArrayList<ArrayList<Line>>(); // Line is a class for a line of test data -> it has observation and hidden tags mainly.
	public static String tagset; // cpostag or postag
	
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		// TODO Auto-generated method stub
		String testPath = args[0]; 
		String outputPath = args[1];
		PrintStream output = new PrintStream(outputPath);
		PrintStream tags = new PrintStream("tags");
		PrintStream know_unknown = new PrintStream("known_unknown");
		String path1 = "prob_w";
		String path2 = "prob_t";
		getProbs(path1,path2);
		readFile(testPath);
		Collections.sort(word_tag);
		Collections.sort(tag_tag);
		Collections.sort(alltags);
		findTagsForWords();
		
		for(int j = 0 ; j < lines.size(); j++){
			ArrayList<Integer> path_best = viterbi(lines.get(j).size(), lines.get(j));
			for(int i = 0; i < path_best.size(); i++){
				lines.get(j).get(i).setHiddenTag(alltags.get(path_best.get(i)));
			}
			
			for(int i = 0; i < path_best.size(); i++){
				output.println(lines.get(j).get(i).getObservation() + "|" + lines.get(j).get(i).getHiddenTag());
			}
			output.println();
		}
		output.close();
		
		tags.println(tagset);
		for(int i = 0 ; i < alltags.size(); i++){
			tags.println(alltags.get(i));
		}
		tags.close();
		
		for(int i = 0; i < lines.size(); i++){
			for(int j = 0; j < lines.get(i).size(); j++){
				if(wordsAndTags.containsKey(lines.get(i).get(j).getObservation())){
					know_unknown.println("known");
				}else{
					know_unknown.println("unknown");
				}
			}
		}
		know_unknown.close();
	}
	
	public static void findTagsForWords(){
		String currentWord = "";
		String prevWord = "";
		ArrayList<String> tags = new ArrayList<String>();
		for(int i = 0; i < word_tag.size(); i++){
			currentWord = word_tag.get(i).split(" ")[0];
			if(!currentWord.equals(prevWord)){
				if(tags.size() > 0){
					wordsAndTags.put(prevWord, tags);
				}
				tags = new ArrayList<String>();
			}
			tags.add(word_tag.get(i).split(" ")[0]+" "+word_tag.get(i).split(" ")[1]);
			prevWord = currentWord;
		}
	}
	
	public static void readFile(String path) throws UnsupportedEncodingException, FileNotFoundException{
		Integer counter = 1;
		Integer prevID = 0;
		Integer currentID = 0;
		Reader reader = new InputStreamReader(new FileInputStream(path), "UTF-8");
		BufferedReader br = new BufferedReader(reader);
		Scanner read = new Scanner(br);
		ArrayList<Line> lineList = new ArrayList<Line>(); 
		while(read.hasNextLine()){
			ArrayList<String> list = new ArrayList<String>();
			String line = read.nextLine();
			Scanner input = new Scanner(line);
			while(input.hasNext()){
				list.add(input.next());
			}
			input.close();
			if(list.size() >= 4 && !list.get(1).equals("_")){
				currentID = Integer.valueOf(list.get(0));
				if(prevID > currentID){
					counter++;
					lines.add(lineList);
					lineList = new ArrayList<Line>();
				}
				Line l = new Line(Integer.valueOf(list.get(0)), list.get(1), list.get(3), counter);
				lineList.add(l);
				prevID = currentID;
			}else if(list.size() == 3 && !list.get(1).equals("_")){
				currentID = Integer.valueOf(list.get(0));
				if(prevID > currentID){
					counter++;
					lines.add(lineList);
					lineList = new ArrayList<Line>();
				}
				Line l = new Line(Integer.valueOf(list.get(0)), list.get(1), counter);
				lineList.add(l);
				prevID = currentID;
			}
			
		}
		lines.add(lineList);
		read.close();
	}
	
	public static void getProbs(String path1, String path2) throws UnsupportedEncodingException, FileNotFoundException{
		Reader reader = new InputStreamReader(new FileInputStream(path1), "UTF-8");
		BufferedReader br = new BufferedReader(reader);	
		Scanner read = new Scanner(br);
		while(read.hasNextLine()){
			String line = read.nextLine();
			word_tag.add(line.split(" ")[0]+" "+line.split(" ")[1]);
			if(!alltags.contains(line.split(" ")[1])){
				if(line.split(" ")[1].equals("Start") && line.split(" ")[1].equals("Finish")){
					alltags.add(line.split(" ")[1]);
				}
				alltags.add(line.split(" ")[1]);
			}
			probs_wordtag.put(line.split(" ")[0]+" "+line.split(" ")[1] ,Double.valueOf(line.split(" ")[2]));	
		}
		read.close();
		
		reader = new InputStreamReader(new FileInputStream(path2), "UTF-8");
		br = new BufferedReader(reader);	
		read = new Scanner(br);
		tagset = read.nextLine();
		while(read.hasNextLine()){
			String line = read.nextLine();
			tag_tag.add(line.split(" ")[0]+" "+line.split(" ")[1]);
			if(!alltags.contains(line.split(" ")[0])){
				if(line.split(" ")[0].equals("Start") && line.split(" ")[0].equals("Finish")){
					alltags.add(line.split(" ")[0]);
				}
			}
			if(!alltags.contains(line.split(" ")[1])){
				if(line.split(" ")[1].equals("Start") && line.split(" ")[1].equals("Finish")){
					alltags.add(line.split(" ")[1]);
				}
			}
			probs_tagtag.put(line.split(" ")[0]+" "+line.split(" ")[1], Double.valueOf(line.split(" ")[2]));
		}
		read.close();
	}
	
	public static ArrayList<Integer> viterbi(Integer sentenceLen, ArrayList<Line> line){
		// calculate hidden states and write output
		ArrayList<Integer> best_path = new ArrayList<Integer>();
		ArrayList<Integer> backtrace = new ArrayList<Integer>();
		ArrayList<ArrayList<Double>> viterbi = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Integer>> backpointer = new ArrayList<ArrayList<Integer>>();
		createMatrices(viterbi, backpointer, line.size());
		Double max = 0.0;
		Integer maxIndex = 0;
		for(int i = 1 ; i < alltags.size()+1; i++){
			Double value = 0.0;
			if(probs_tagtag.get("Start " + alltags.get(i-1)) != null){
				value =  probs_tagtag.get("Start " + alltags.get(i-1));
			}
			
			if(probs_wordtag.get(line.get(0).getObservation() + " " + alltags.get(i-1)) != null){
				value *= probs_wordtag.get(line.get(0).getObservation() + " " + alltags.get(i-1));
			}else{
				if(wordsAndTags.containsKey(line.get(0).getObservation())){
					value *= 0;
				}else{
					value *= 0.0000001;
				}
			}
			viterbi.get(i).set(0,value);
			backpointer.get(i).set(0,0);
		}
		for(int i = 1 ; i < line.size(); i++){
			for(int j = 1 ; j < alltags.size()+1; j++){
				for(int k = 1; k < alltags.size()+1; k++){
					Double value = 0.0;
					if(probs_tagtag.get(alltags.get(k-1) + " " + alltags.get(j-1)) != null){
						value = probs_tagtag.get(alltags.get(k-1) + " " + alltags.get(j-1)); 
					}
					
					value *= viterbi.get(k).get(i-1);
					if(max < value){
						max = value;
						maxIndex = k;
					}
				}
				if(probs_wordtag.get(line.get(i).getObservation() + " " + alltags.get(j-1)) != null){
					max *= probs_wordtag.get(line.get(i).getObservation() + " " + alltags.get(j-1));
				}else{
					if(wordsAndTags.containsKey(line.get(i).getObservation())){
						max *= 0;
					}else{
						max *= 0.0000001;
					}
				}
				viterbi.get(j).set(i,max);
				backpointer.get(j).set(i, maxIndex);
				max = 0.0;
			}
		}
		
		for(int i = 1; i < alltags.size()+1; i++){
			Double value = 0.0;
			if(probs_tagtag.get(alltags.get(i-1) + " Finish") != null){
				value = probs_tagtag.get(alltags.get(i-1) + " Finish"); 
			}
			value *= viterbi.get(i).get(line.size()-1);
			if(max < value){
				max = value;
				maxIndex = i;
			}	
		}
		viterbi.get(alltags.size()+1).set(line.size()-1,max);
		backpointer.get(alltags.size()+1).set(line.size()-1, maxIndex);
		backtrace.add(maxIndex-1);
		Integer index = maxIndex;
		for(int i = line.size()-1 ; i > 0; i--){
			backtrace.add(backpointer.get(index).get(i)-1);
			index = backpointer.get(index).get(i);
		}
		
		for(int i = 0; i < backtrace.size(); i++){
			best_path.add(backtrace.get(backtrace.size()-1-i));
		}
		return best_path;
	}
	
	public static void createMatrices(ArrayList<ArrayList<Double>> viterbi, ArrayList<ArrayList<Integer>> backpointer, Integer length){
		for(int i = 0; i < alltags.size()+2; i++){
			ArrayList<Double> list = new ArrayList<Double>();
			ArrayList<Integer> list1 = new ArrayList<Integer>();
			for(int j = 0; j < length; j++){
				list.add(0.0);
				list1.add(0);
			}
			viterbi.add(list);
			backpointer.add(list1);
		}
	}

}
