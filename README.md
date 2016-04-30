# ASSIGNMENT2 (POS Tagger)

## How to clone github repository to local?

Open terminal and write this command:

    git clone https://github.com/oralemre/Cmpe561_Homework1.git
    
If you already clone repository, change directory as where the repo is located in your local and to get new files on repository use following command:

    git pull
    
## What is next after clone repository to local or git pull?
 
Change directory as where you clone repository. If you open that directory, you see train_hmm_tagger.jar, hmm_tagger.jar and evaluate_hmm_tagger.jar files. First run 
 
    java -jar train_hmm_tagger.jar <path of training data> -- <cpostag|postag>
    
    i.e - > java -jar train_hmm_tagger.jar /Users/emre.oral/Documents/CMPE561/metu_sabanci_cmpe_561/train/turkish_metu_sabanci_train.conll cpostag
    
    i.e - > java -jar train_hmm_tagger.jar /Users/emre.oral/Documents/CMPE561/metu_sabanci_cmpe_561/train/turkish_metu_sabanci_train.conll postag
 
command and then run 

    java -jar hmm_tagger.jar <path of test data> <path of output>
    
    i.e - > java -jar train_hmm_tagger.jar /Users/emre.oral/Documents/CMPE561/metu_sabanci_cmpe_561/validation/turkish_metu_sabanci_val.conll output
    
finally run

    java -jar evaluate_hmm_tagger.jar <path of output> <path of gold data>
    
    i.e - > java -jar evaluate_train_hmm_tagger.jar output /Users/emre.oral/Documents/CMPE561/metu_sabanci_cmpe_561/validation/turkish_metu_sabanci_val.conll 

command.

### Important Notes

* In confusion_matrices folder you can find confMatrix_cpostag.csv, confMatrix_postag.csv, confKnownMatrix_cpostag.csv, confKnownMatrix_postag.csv, confUnknownMatrix_cpostag.csv and confUnknownMatrix_postag.csv files.

* confMatrix_cpostag.csv is confusion matrix for overall (both known and unknown words) for cpostag.

* confMatrix_postag.csv is confusion matrix for overall (both known and unknown words) for postag.

* confKnownMatrix_cpostag.csv is confusion matrix for known words for cpostag.

* confKnownMatrix_postag.csv is confusion matrix for known words for postag.

* confUnknownMatrix_cpostag.csv is confusion matrix for unknown words for cpostag.

* confUnknownMatrix_postag.csv is confusion matrix for unknown words for postag.
 
* You can find data set in metu_sabanci_cmpe_561 folder.

* First run train_hmm_tagger for a tag set (postag or cpostag) and then run other two jar files and after that process run train_hmm_tagger for the other tag set and then run other two jar files again.
 
* Please don't delete any file that are created after a jar file run.

* Confusion Matrices in confusion_matrices folder is for validation data in metu_sabanci data set. If you run jar files by using above instructions, these matrices are created on current folder for your test data.

* If you encounter any problems when using git pull command, delete local directory and clone repository to local again.
 
* Check project report on moodle for detailed informations.

* If you have a question or problem, please open an issue and let me know about that.

# ASSIGNMENT1 (Classifier)

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

TestAndTraining jar run in 20-30 seconds and NaiveBayes run in 60-80 seconds. If you encounter any problem when running programs please open an issue and make me informed about that. Also these projects are written in java7 so be sure that you have java7. To run NaiveBayes on another dataset first run TestAndTraining jar by giving path of raw data and then run NaiveBayes.jar.








