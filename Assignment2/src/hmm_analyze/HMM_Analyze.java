package hmm_analyze;

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

public class HMM_Analyze {
	public static Double total_known = 0.0;
	public static Double total_unknown = 0.0;
	public static Double true_known = 0.0;
	public static Double true_unknown = 0.0;
	public static String tagset;
	public static Integer total = 0;
	public static Integer diagonalTotal = 0;
	public static ArrayList<String> known_unknownList = new ArrayList<String>();
	public static ArrayList<Double> precision = new ArrayList<Double>();
	public static ArrayList<Double> precisionKnown = new ArrayList<Double>();
	public static ArrayList<Double> precisionUnknown = new ArrayList<Double>();
	public static ArrayList<Double> recall = new ArrayList<Double>();
	public static ArrayList<Double> recallKnown = new ArrayList<Double>();
	public static ArrayList<Double> recallUnknown = new ArrayList<Double>();
	public static ArrayList<Integer> classificationOverall = new ArrayList<Integer>();
	public static ArrayList<Integer> classificationOverallKnown = new ArrayList<Integer>();
	public static ArrayList<Integer> classificationOverallUnknown = new ArrayList<Integer>();
	public static ArrayList<Integer> truthOverall = new ArrayList<Integer>();
	public static ArrayList<Integer> truthOverallKnown = new ArrayList<Integer>();
	public static ArrayList<Integer> truthOverallUnknown = new ArrayList<Integer>();
	public static ArrayList<Line> output = new ArrayList<Line>();
	public static ArrayList<Line> actual = new ArrayList<Line>();
	public static ArrayList<Tag> tagList = new ArrayList<Tag>();
	public static ArrayList<Tag> tagListKnown = new ArrayList<Tag>();
	public static ArrayList<Tag> tagListUnknown = new ArrayList<Tag>();
	public static Map<String,Integer> tagAndID = new HashMap<String,Integer>();
	
	public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
		// TODO Auto-generated method stub
		Integer option = -1;
		String tags = "tags";
		String outputPath =args[0];
		String known_unknown = "known_unknown";
		
		String goldPath = args[1];
		readTags(tags);
		if(tagset.equals("cpostag")){
			option = 3;
		}else{
			option = 4;
		}
		PrintStream confMatrix = new PrintStream("confMatrix_"+ tagset +".csv");
		PrintStream confKnownMatrix = new PrintStream("confKnownMatrix_"+ tagset +".csv");
		PrintStream confUnknownMatrix = new PrintStream("confUnknownMatrix_"+ tagset +".csv");
		
		
		for(int i = 0; i < tagList.size(); i++){
			ArrayList<Double> perc = new ArrayList<Double>();
			ArrayList<Integer> count = new ArrayList<Integer>();
			ArrayList<Double> perc1 = new ArrayList<Double>();
			ArrayList<Integer> count1 = new ArrayList<Integer>();
			ArrayList<Double> perc2 = new ArrayList<Double>();
			ArrayList<Integer> count2 = new ArrayList<Integer>();
			for(int j = 0; j < tagList.size(); j++){
				perc.add(0.0);
				count.add(0);
				perc1.add(0.0);
				count1.add(0);
				perc2.add(0.0);
				count2.add(0);
			}
			tagList.get(i).setCount(count);
			tagList.get(i).setPercentage(perc);
			tagListKnown.get(i).setCount(count1);
			tagListKnown.get(i).setPercentage(perc1);
			tagListUnknown.get(i).setCount(count2);
			tagListUnknown.get(i).setPercentage(perc2);
		}
		
		readKnownUnknown(known_unknown);
		
		readOutputData(outputPath);
		
		readGoldData(goldPath,option);
		
		calculateResults();
		
		calculateClassificationOverall();
		
		calculateTruthOverall();
		
		calculateRecall();
		
		calculatePrecision();
		
		confMatrix.print(tagset);
		confKnownMatrix.print(tagset);
		confUnknownMatrix.print(tagset);
		for(int i = 0 ; i < tagList.size(); i++){
			confMatrix.print("," +  tagList.get(i).getName());
			confKnownMatrix.print("," +  tagList.get(i).getName());
			confUnknownMatrix.print("," +  tagList.get(i).getName());
		}
		confMatrix.print(",Total,Precision");
		confKnownMatrix.print(",Total,Precision");
		confUnknownMatrix.print(",Total,Precision");
		confMatrix.println();
		confKnownMatrix.println();
		confUnknownMatrix.println();
		for(int i = 0; i < tagList.size(); i++){
			confMatrix.print(tagList.get(i).getName());
			confKnownMatrix.print(tagList.get(i).getName());
			confUnknownMatrix.print(tagList.get(i).getName());
			for(int j = 0; j < tagList.get(i).getCount().size(); j++){
				confMatrix.print(","+tagList.get(i).getCount().get(j));
				confKnownMatrix.print(","+tagListKnown.get(i).getCount().get(j));
				confUnknownMatrix.print(","+tagListUnknown.get(i).getCount().get(j));
			}
			confMatrix.print("," + classificationOverall.get(i));
			confKnownMatrix.print("," + classificationOverallKnown.get(i));
			confUnknownMatrix.print("," + classificationOverallUnknown.get(i));
			if(precision.get(i) == -1.0){
				confMatrix.print(",No Data");
			}else{
				confMatrix.print("," + precision.get(i));
			}
			
			if(precisionKnown.get(i) == -1.0){
				confKnownMatrix.print(",No Data");
			}else{
				confKnownMatrix.print("," + precisionKnown.get(i));
			}
			
			if(precisionUnknown.get(i) == -1.0){
				confUnknownMatrix.print(",No Data");
			}else{
				confUnknownMatrix.print("," + precisionUnknown.get(i));
			}
			confMatrix.println();
			confKnownMatrix.println();
			confUnknownMatrix.println();
		}
		confMatrix.print("Total");
		confKnownMatrix.print("Total");
		confUnknownMatrix.print("Total");
		
		for(int i = 0 ; i < tagList.size(); i++){
			confMatrix.print("," + truthOverall.get(i));
			confKnownMatrix.print("," + truthOverallKnown.get(i));
			confUnknownMatrix.print("," + truthOverallUnknown.get(i));
		}
		confMatrix.print("," + total);
		confMatrix.println();
		confMatrix.print("Recall");
		
		confKnownMatrix.print("," + total_known);
		confKnownMatrix.println();
		confKnownMatrix.print("Recall");
		
		confUnknownMatrix.print("," + total_unknown);
		confUnknownMatrix.println();
		confUnknownMatrix.print("Recall");
		for(int i = 0; i < tagList.size(); i++){
			if(recall.get(i) != -1.0){
				confMatrix.print("," + recall.get(i));
			}else{
				confMatrix.print(",No Data");
			}
			
			if(recallKnown.get(i) != -1.0){
				confKnownMatrix.print("," + recallKnown.get(i));
			}else{
				confKnownMatrix.print(",No Data");
			}
			
			if(recallUnknown.get(i) != -1.0){
				confUnknownMatrix.print("," + recallUnknown.get(i));
			}else{
				confUnknownMatrix.print(",No Data");
			}
		}
		confMatrix.println();
		confKnownMatrix.println();
		confUnknownMatrix.println();
		Double accuracy = ((double) diagonalTotal) / ((double) total);
		confMatrix.print("Overall Accuracy," + accuracy);
		confMatrix.println();
		accuracy = true_known / total_known;
		confKnownMatrix.print("Overall Accuracy," + accuracy);
		confKnownMatrix.println();
		accuracy = true_unknown / total_unknown;
		confUnknownMatrix.print("Overall Accuracy," + accuracy);
		
		confMatrix.close();
		confKnownMatrix.close();
		confUnknownMatrix.close();
	}
	
	public static void calculateRecall(){
		for(int i = 0 ; i < tagList.size(); i++){
			Double result = 0.0;
			Double result1 = 0.0;
			Double result2= 0.0;
			for(int j = 0 ; j < tagList.size() ; j++){
				if(i == j){
					if(truthOverall.get(i) != 0){
						result = (((double)tagList.get(i).getCount().get(j)) / truthOverall.get(i));
					}else{
						result = -1.0;
					}
					
					if(truthOverallKnown.get(i) != 0){
						result1 = (((double)tagListKnown.get(i).getCount().get(j)) / truthOverallKnown.get(i));
					}else{
						result1 = -1.0;
					}
					
					if(truthOverallUnknown.get(i) != 0){
						result2 = (((double)tagListUnknown.get(i).getCount().get(j)) / truthOverallUnknown.get(i));
					}else{
						result2 = -1.0;
					}
					
				}
			}
			recall.add(result);
			recallKnown.add(result1);
			recallUnknown.add(result2);
		}
	}
	
	public static void calculatePrecision(){
		for(int i = 0 ; i < tagList.size(); i++){
			Double result = 0.0;
			Double result1 = 0.0;
			Double result2 = 0.0;
			for(int j = 0 ; j < tagList.size() ; j++){
				if(i == j){
					if(classificationOverall.get(i) != 0){
						result = (((double)tagList.get(i).getCount().get(j)) / classificationOverall.get(i));
					}else{
						result = -1.0;
					}
					
					if(classificationOverallKnown.get(i) != 0){
						result1 = (((double)tagListKnown.get(i).getCount().get(j)) / classificationOverallKnown.get(i));
					}else{
						result1 = -1.0;
					}
					
					if(classificationOverallUnknown.get(i) != 0){
						result2 = (((double)tagListUnknown.get(i).getCount().get(j)) / classificationOverallUnknown.get(i));
					}else{
						result2 = -1.0;
					}
					
				}
			}
			precision.add(result);
			precisionKnown.add(result1);
			precisionUnknown.add(result2);
		}
	}
	
	
	
	public static void calculateClassificationOverall(){
		for(int i = 0 ; i < tagList.size(); i++){
			Integer count = 0;
			Integer count1 = 0;
			Integer count2 = 0;
			for(int j = 0 ; j < tagList.size() ; j++){
				count += tagList.get(i).getCount().get(j);
				count1 += tagListKnown.get(i).getCount().get(j);
				count2 += tagListUnknown.get(i).getCount().get(j);
				if(i == j){
					diagonalTotal += tagList.get(i).getCount().get(j);
				}
			}
			total += count; 
			classificationOverall.add(count);
			classificationOverallKnown.add(count1);
			classificationOverallUnknown.add(count2);
		}
	}
	
	public static void calculateTruthOverall(){
		for(int i = 0 ; i < tagList.size(); i++){
			Integer count = 0;
			Integer count1 = 0;
			Integer count2 = 0;
			for(int j = 0 ; j < tagList.size() ; j++){
				count += tagList.get(j).getCount().get(i);
				count1 += tagListKnown.get(j).getCount().get(i);
				count2 += tagListUnknown.get(j).getCount().get(i);
			}
			truthOverall.add(count);
			truthOverallKnown.add(count1);
			truthOverallUnknown.add(count2);
		}
	}
	
	public static void readKnownUnknown(String path) throws UnsupportedEncodingException, FileNotFoundException{
		Reader reader = new InputStreamReader(new FileInputStream(path), "UTF-8");
		BufferedReader br = new BufferedReader(reader);	
		Scanner read = new Scanner(br);
		while(read.hasNextLine()){
			String current = read.nextLine();
			known_unknownList.add(current);
		}
		read.close();
	}
	
	public static void calculateResults(){
		for(int i = 0 ; i < output.size(); i++){
			Line l1 = output.get(i);
			Line l2 = actual.get(i);
			Integer index1 = tagAndID.get(l1.getTag());
			Integer index2 = tagAndID.get(l2.getTag());
			tagList.get(index1).getCount().set(index2, tagList.get(index1).getCount().get(index2)+1);
			if(known_unknownList.get(i).equals("known")){
				total_known++;
				tagListKnown.get(index1).getCount().set(index2, tagListKnown.get(index1).getCount().get(index2)+1);
			}else{
				total_unknown++;
				tagListUnknown.get(index1).getCount().set(index2, tagListUnknown.get(index1).getCount().get(index2)+1);
			}
			
			if(index1 == index2){
				if(known_unknownList.get(i).equals("known")){
					true_known++;
				}else{
					true_unknown++;
				}
			}
		}
	}
	
	public static void readOutputData(String path) throws UnsupportedEncodingException, FileNotFoundException{
		Reader reader = new InputStreamReader(new FileInputStream(path), "UTF-8");
		BufferedReader br = new BufferedReader(reader);	
		Scanner read = new Scanner(br);
		while(read.hasNextLine()){
			String current = read.nextLine();
			if(current.length() > 0){
				Line l = new Line(current.split("\\|")[0], current.split("\\|")[1]);
				output.add(l);
			}
		}
		read.close();
	}
	
	public static void readGoldData(String path, Integer option) throws UnsupportedEncodingException, FileNotFoundException{
		Reader reader = new InputStreamReader(new FileInputStream(path), "UTF-8");
		BufferedReader br = new BufferedReader(reader);	
		Scanner read = new Scanner(br);
		while(read.hasNextLine()){
			ArrayList<String> list = new ArrayList<String>();
			String current = read.nextLine();
			Scanner input = new Scanner(current);
			while(input.hasNext()){
				list.add(input.next());
			}
			if(list.size() > 0){
				if(!list.get(1).equals("_")){
					Line l = new Line(list.get(1), list.get(option));
					actual.add(l);
				}
			}
			input.close();
		}
		read.close();
	}
	
	public static void readTags(String path) throws UnsupportedEncodingException, FileNotFoundException{
		Reader reader = new InputStreamReader(new FileInputStream(path), "UTF-8");
		BufferedReader br = new BufferedReader(reader);	
		Scanner read = new Scanner(br);
		tagset = read.nextLine();
		Integer counter = 0;
		while(read.hasNextLine()){
			String st = read.nextLine();
			Tag t = new Tag(st, counter);
			tagList.add(t);
			t = new Tag(st, counter);
			tagListKnown.add(t);
			t = new Tag(st, counter);
			tagListUnknown.add(t);
			tagAndID.put(t.getName(), t.getID());
			counter++;
		}
		read.close();
	}
}
