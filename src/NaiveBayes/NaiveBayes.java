import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NaiveBayes {
	public static Map<String,Classes> nameAndClass = new HashMap<String,Classes>();
	public static ArrayList<Classes> C = new ArrayList<Classes>(); 
	public static ArrayList<String> Voc = new ArrayList<String>();
	public static ArrayList<String> functionWords = new ArrayList<String>();
	public static ArrayList<TestDocument> testDocs = new ArrayList<TestDocument>();
	public static Map<String,ArrayList<Double>> condiProbs = new HashMap<String,ArrayList<Double>>();
	public static Integer totalNumberOfDocuments = 0;
	public static Double alfa = 0.1;
	public static String testPath = "";
	public static String trainingPath = "";
	public static String functionWordPath = "";
	public static String [] classes = null;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		testPath = System.getProperty("user.dir") + "/test"; // path of test set , TestAndTraining program create test and training path in current path
		trainingPath = System.getProperty("user.dir") + "/training";
		functionWordPath = System.getProperty("user.dir") + "/fWords.txt";
		classes = (new File(trainingPath)).list(); // authors
		Double microTp = 0.0;
		Double microFn = 0.0;
		Double microFp = 0.0;
		Double totalPrecision = 0.0;
		Double totalRecall = 0.0;
		Double macroPrecision = 0.0;
		Double macroRecall = 0.0;
		Double microPrecision = 0.0;
		Double microRecall = 0.0;
		
		createFunctionWords(); // by using fWords.txt  it creates arraylist for functional words.
		createVocAndClasses(); // this method creates Classes and adds them to Classes Array (C), also create Vocabulary;
		createTestDocuments(); // this method creates test documents
		calculateCondProbs(); // calculate conditional probabilities of features respect to classes.
		for(int i = 0; i < testDocs.size(); i++){
			applyMultinomialNB(testDocs.get(i),false); // first apply Multinomial NB for BoW features.
			if(testDocs.get(i).getExpected().equals(testDocs.get(i).getActual())){
				C.get(nameAndClass.get(testDocs.get(i).getExpected()).getIndex()).
				setTp(C.get(nameAndClass.get(testDocs.get(i).getExpected()).getIndex()).getTp()+1);				
			}else{
				C.get(nameAndClass.get(testDocs.get(i).getExpected()).getIndex()).
				setFn(C.get(nameAndClass.get(testDocs.get(i).getExpected()).getIndex()).getFn()+1);
				C.get(nameAndClass.get(testDocs.get(i).getActual()).getIndex()).
				setFp(C.get(nameAndClass.get(testDocs.get(i).getActual()).getIndex()).getFp()+1);
			}	
		}
		
		for(int i = 0; i < C.size(); i++){
			microTp += C.get(i).getTp();
			microFp += C.get(i).getFp();
			microFn += C.get(i).getFn();
			if(!(C.get(i).getTp() == 0 && C.get(i).getFp() == 0)){
				totalPrecision += (C.get(i).getTp() / (C.get(i).getTp() + C.get(i).getFp()));
				totalRecall += (C.get(i).getTp() / (C.get(i).getTp() + C.get(i).getFn()));
			}
		}
		
		macroPrecision = totalPrecision / C.size() ;
		macroRecall = totalRecall / C.size();
		
		microPrecision = microTp / (microTp + microFp);
		microRecall = microTp / (microTp + microFn);
		
		System.out.println("=======================================================");
		System.out.println("Result of BoW");
		System.out.println("=======================================================");
		System.out.println("MicroAveraged Precision: " + microPrecision*100);
		System.out.println("MacroAveraged Precision: " + macroPrecision*100);
		System.out.println("MicroAveraged Recall: " + microRecall*100);
		System.out.println("MacroAveraged Recall: " + macroRecall*100);
		System.out.println("MicroAveraged Fscore: " + (2*microPrecision*microRecall / (microPrecision + microRecall)) * 100);
		System.out.println("MacroAveraged Fscore: " + (2*macroPrecision*macroRecall / (macroPrecision + macroRecall)) * 100);
		
		microTp = 0.0;
		microFp = 0.0;
		microFn = 0.0;
		totalPrecision = 0.0;
		totalRecall = 0.0;
		
		for(int i = 0; i < C.size(); i++){
			C.get(i).setFn(0.0);
			C.get(i).setTn(0.0);
			C.get(i).setFp(0.0);
			C.get(i).setTp(0.0);
		}
		
		
		
		for(int i = 0; i < testDocs.size(); i++){
			testDocs.get(i).setProbs(new ArrayList<Double>());
			applyMultinomialNB(testDocs.get(i),true); // then apply Multinomial NB for BoW + FWs features
			if(testDocs.get(i).getExpected().equals(testDocs.get(i).getActual())){
				C.get(nameAndClass.get(testDocs.get(i).getExpected()).getIndex()).
				setTp(C.get(nameAndClass.get(testDocs.get(i).getExpected()).getIndex()).getTp()+1);	
			}else{
				C.get(nameAndClass.get(testDocs.get(i).getExpected()).getIndex()).
				setFn(C.get(nameAndClass.get(testDocs.get(i).getExpected()).getIndex()).getFn()+1);
				C.get(nameAndClass.get(testDocs.get(i).getActual()).getIndex()).
				setFp(C.get(nameAndClass.get(testDocs.get(i).getActual()).getIndex()).getFp()+1);
			}
		}
		
		for(int i = 0; i < C.size(); i++){
			microTp += C.get(i).getTp();
			microFp += C.get(i).getFp();
			microFn += C.get(i).getFn();
			if(!(C.get(i).getTp() == 0 && C.get(i).getFp() == 0)){
				totalPrecision += (C.get(i).getTp() / (C.get(i).getTp() + C.get(i).getFp()));
				totalRecall += (C.get(i).getTp() / (C.get(i).getTp() + C.get(i).getFn()));
			}
		}
		
		
		
		macroPrecision = totalPrecision / C.size() ;
		macroRecall = totalRecall / C.size();
		microPrecision = microTp / (microTp + microFp);
		microRecall = microTp / (microTp + microFn);
		
		System.out.println();
		System.out.println();
		
		System.out.println("=======================================================");
		System.out.println("Result of BoW + FWs");
		System.out.println("=======================================================");
		System.out.println("MicroAveraged Precision: " + microPrecision*100);
		System.out.println("MacroAveraged Precision: " + macroPrecision*100);
		System.out.println("MicroAveraged Recall: " + microRecall*100);
		System.out.println("MacroAveraged Recall: " + macroRecall*100);
		System.out.println("MicroAveraged Fscore: " + (2*microPrecision*microRecall / (microPrecision + microRecall)) * 100);
		System.out.println("MacroAveraged Fscore: " + (2*macroPrecision*macroRecall / (macroPrecision + macroRecall)) * 100);
		
	}
	
	public static void createFunctionWords() throws UnsupportedEncodingException{
		try {
			Reader reader = new InputStreamReader(new FileInputStream(functionWordPath), "windows-1254");
			BufferedReader br = new BufferedReader(reader);
			Scanner read = new Scanner(br);
			while(read.hasNext()){
				functionWords.add(read.next());
			}
			read.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
	}
	
	
	public static void createTestDocuments() throws UnsupportedEncodingException{
		for(int i = 0; i < classes.length; i++){
			File [] docs = (new File(testPath + "/" + classes[i])).listFiles();
			for(int j = 0; j < docs.length; j++){
				TestDocument td = new TestDocument(classes[i]);
				try{
					Reader reader = new InputStreamReader(new FileInputStream(docs[j].getAbsolutePath()), "windows-1254"); // for windows-1254 encoding (turkish characters)
					BufferedReader br = new BufferedReader(reader);
					Scanner read = new Scanner(br);
					while(read.hasNext()){
						String str = tokenization(read.next()); // apply tokenization to all tokens and extracts words
						if(Voc.contains(str)){
							td.getTestWords().add(str);
						}
						if(functionWords.contains(str)){
							td.getTestWords().add("f_"+str);
						}
						
					}
					testDocs.add(td);
					read.close();
				}catch(FileNotFoundException e) {
					e.printStackTrace();
					
				}
			}
		}
	}
	
	
	public static void createVocAndClasses() throws FileNotFoundException{
		for(int i = 0; i < classes.length ; i++){
			totalNumberOfDocuments += (new File(trainingPath + "/" + classes[i])).list().length;
		}
		
		for(int i = 0; i < classes.length ; i++){
			Classes c = new Classes(classes[i],i);
			File [] docs = (new File(trainingPath + "/" + classes[i])).listFiles();
			c.setClassProb(((double) docs.length) / totalNumberOfDocuments);
			for(int j = 0; j < docs.length ; j++){
				try {
					Reader reader = new InputStreamReader(new FileInputStream(docs[j].getAbsolutePath()), "windows-1254");
					BufferedReader br = new BufferedReader(reader);
					Scanner read = new Scanner(br);
					while(read.hasNext()){
						String str = tokenization(read.next());
						if(c.getFeatureFrequency().containsKey(str)){
							c.getFeatureFrequency().put(str, c.getFeatureFrequency().get(str)+1);
							c.setNumberOfWords(c.getNumberOfWords()+1);
							if(functionWords.contains(str)){
								c.getFeatureFrequency().put(str, c.getFeatureFrequency().get("f_"+str) + 1);
								c.setNumberOfFunctionalWords(c.getNumberOfFunctionalWords()+1);
							}
						}else{
							if(!Voc.contains(str)){
								Voc.add(str);
							}
							if(functionWords.contains(str)){
								// since this functional words also a word in a document, to distinguish words and functional words add f_ to beginning of functional words
								c.getFeatureFrequency().put("f_"+str, 1); 
								c.setNumberOfFunctionalWords(c.getNumberOfFunctionalWords()+1);
							}
							c.getFeatureFrequency().put(str, 1);
							c.setNumberOfWords(c.getNumberOfWords()+1);
						}
					}
					read.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			C.add(c);
			nameAndClass.put(c.getName(), c);
		}
	}
	
	public static void calculateCondProbs(){
		for(int i = 0 ; i < Voc.size(); i++){
			ArrayList<Double> probs = new ArrayList<Double>();
			for(int j = 0; j < C.size(); j++){
				if(C.get(j).getFeatureFrequency().get(Voc.get(i)) != null){
					Double prob = ((double)(C.get(j).getFeatureFrequency().get(Voc.get(i))+alfa))/(C.get(j).getNumberOfWords() + alfa*Voc.size());
					probs.add(prob);
				}else{
					Double prob = ((double)(alfa))/(C.get(j).getNumberOfWords() + alfa*Voc.size());
					probs.add(prob);
				}
			}
			condiProbs.put(Voc.get(i), probs);
		}
		
		for(int i = 0; i < functionWords.size(); i++){
			ArrayList<Double> probs = new ArrayList<Double>();
			for(int j = 0; j < C.size(); j++){
				if(C.get(j).getFeatureFrequency().get("f_" + functionWords.get(i)) != null){
					Double prob = ((double)(C.get(j).getFeatureFrequency().get("f_" + functionWords.get(i))+alfa))/(C.get(j).getNumberOfFunctionalWords() + alfa*functionWords.size());
					probs.add(prob);
				}else{
					Double prob = ((double)(alfa))/(C.get(j).getNumberOfFunctionalWords() + alfa*functionWords.size());
					probs.add(prob);
				}
			}
			condiProbs.put("f_" + functionWords.get(i), probs);
		}
	}
	
	public static void applyMultinomialNB(TestDocument td, boolean select){
		for(int i = 0; i < C.size(); i++){
			Double score = Math.log(C.get(i).getClassProb());
			for(int j = 0; j < td.getTestWords().size(); j++){
				if(!select){ // normal false
					if(!td.getTestWords().get(j).contains("f_")){
						score += Math.log(condiProbs.get(td.getTestWords().get(j)).get(i)); 
					}
				}else{ // extra feature true
					score += Math.log(condiProbs.get(td.getTestWords().get(j)).get(i)); 
				}
				
			}
			td.getProbs().add(score);
		}
		
		int maxIndex = 0;
		Double max = td.getProbs().get(maxIndex);
		
		for(int i = 1; i < td.getProbs().size(); i++){
			if(max < td.getProbs().get(i)){
				maxIndex = i;
				max = td.getProbs().get(i);
			}
		}
		td.setActual(classes[maxIndex]);
	
	}
	
	public static char[] createCharArray(ArrayList<Character> list){
		char[] chars  = new char[list.size()];
		for(int i = 0; i < list.size(); i++){
			chars[i] = list.get(i);
		}
		return chars;
	}
	
	public static String tokenization(String s){
		/* 
		 * Tokenization Algorithm 
		 */
		if(s.matches("[a-zA-Z\\p{L}\\\\.,?!/-:“”’]*")){
			char[] chars = s.toCharArray();
			ArrayList<Character> finalChars = new ArrayList<Character>();
			for(int i = 0; i < chars.length; i++){
				if(String.valueOf(chars[i]).matches("[\\\\.,?!/:“”]{1}")){
				}else{
					finalChars.add(chars[i]);
				}
			}
			chars = createCharArray(finalChars);
			s = String.valueOf(chars);
			if(s.equals("-")){
				s = "";
			}
		}else if(s.matches("[0-9]+[.,]{1}[0-9]*")){
			if(s.matches("[0-9]+[.,]{1}")){
				char[] chars = s.toCharArray();
				for(int i = 0; i < chars.length; i++){
					if(String.valueOf(chars[i]).matches("[.,]{1}")){
						chars[i] = ' ';
						break;
					}
				}
				s = String.valueOf(chars);
			}	
		}else if(s.matches("[0-9]+[.,]{1}[0-9]+.*")){
			char[] chars = s.toCharArray();
			int counter = 0;
			for(int i = 0; i < chars.length; i++ ){
				if(String.valueOf(chars[i]).matches("[.,]{1}")){
					counter++;
				}
				if((String.valueOf(chars[i]).matches("[^0-9.,]{1}")) ||
				(String.valueOf(chars[i]).matches("[.,]{1}") && counter > 1)){
					chars[i] = ' ';
				}
			}
			s = String.valueOf(chars);
			
		}else{
			if(!s.matches("[0-9]*")){
				s = "";
			}
		}
		return s.toLowerCase();
	}
}
