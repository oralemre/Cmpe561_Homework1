import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.apache.commons.io.FileUtils;

public class DivideRawData {

	public static void main(String[] args) throws IOException {
		
		
		String path = args[0];
		
		ArrayList<File> fileList = new ArrayList<File>();
		ArrayList<File> training = new ArrayList<File>();
		ArrayList<File> test = new ArrayList<File>();
		File f = new File(path);
		
		File tr = new File("training");
		File te = new File("test");
		
		
		if(tr.exists()){
			FileUtils.deleteDirectory(tr);
		}
		
		if(te.exists()){
			FileUtils.deleteDirectory(te);
		}
		
		String [] files = f.list();
		
		new File("training").mkdir();
		new File("test").mkdir();
		
		Random rand = new Random();
		int  n = 0;
		
		try {
			for(int i = 0; i < files.length; i++){
				new File("training/"+ files[i]).mkdir();
				new File("test/"+ files[i]).mkdir();
				String fileName = f.getAbsolutePath() + "/" + files[i];
				File new1 = new File(fileName);
				addfiles(new1, fileList);
				int size = fileList.size();
				
				for(int j = 0; j < (size * 3)/5; j++){
					n = rand.nextInt(fileList.size());
					training.add(fileList.get(n));
					File source = new File(path + "/" + files[i]+"/"+training.get(j).getName());
					File dest = new File("training/"+files[i]+"/"+training.get(j).getName());
					copyFile(source ,dest);
					fileList.remove(n);
				}
				test.addAll(fileList);
				for(int j = 0; j < test.size(); j++){
					File source = new File(path + "/" + files[i]+"/"+test.get(j).getName());
					File dest = new File("test/"+files[i]+"/"+test.get(j).getName());
					copyFile(source ,dest);
				}
				training = new ArrayList<File>();
				test = new ArrayList<File>();
				fileList = new ArrayList<File>();
			}
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static void copyFile( File from, File to ) throws IOException {
	    Files.copy( from.toPath(), to.toPath() );
	}
	
	public static void addfiles (File input,ArrayList<File> files)
	{
	    if(input.isDirectory())
	    {
	        ArrayList <File> path = new ArrayList<File>(Arrays.asList(input.listFiles()));
	        for(int i=0 ; i<path.size();++i)
	        {
	            if(path.get(i).isDirectory())
	            {
	                addfiles(path.get(i),files);
	            }
	            if(path.get(i).isFile())
	            {
	                String name=(path.get(i)).getName();
	                if(name.lastIndexOf('.')>0)
	                {
	                    int lastIndex = name.lastIndexOf('.');
	                    String str = name.substring(lastIndex);
	                    if(str.equals(".txt"))
	                    {
	                        files.add(path.get(i));
	                    }
	                }
	            }
	        }
	    }
	    if(input.isFile())
	    {
	        String name=(input.getName());
	        if(name.lastIndexOf('.')>0)
	        {
	            int lastIndex = name.lastIndexOf('.');
	            String str = name.substring(lastIndex);
	            if(str.equals(".txt"))
	            {
	                files.add(input);
	            }
	        }
	    }

	}
}
