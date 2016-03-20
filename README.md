## How to clone github repository to local?

Open terminal and write this command:

    git clone https://github.com/oralemre/Cmpe561_Homework1.git

## What is next after clone repository to local?
 
Change directory as where you clone repository. If you open that directory, you see test, training folders and  NaiveBayes.jar, TestAndTraining.jar. First run 
 
    java -jar NaiveBayes.jar
 
command and see result for those test and training set. If you want to run program on another test and training set run

    java -jar TestAndTraining.jar <path of raw data> 

(i.e in my local, path of raw data is C:/Users/samsung/Desktop/SPRING2016/CMPE561/69yazar/raw_texts)    
first and then again run 

    java -jar NaiveBayes.jar
    
command.

### Some Notes

TestAndTraining jar run in 20-30 seconds and NaiveBayes run in 60-80 seconds. If you encounter any problem when running programs please open an issue and make me informed about that. Also these projects are written in java7 so be sure that you have java7. 








