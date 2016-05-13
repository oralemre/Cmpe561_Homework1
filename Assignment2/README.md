# ASSIGNMENT2 (POS Tagger)

## What is next after clone repository to local or git pull?
 
Change directory as where you clone repository. If you open that directory, you see train_hmm_tagger.jar, hmm_tagger.jar and evaluate_hmm_tagger.jar files. First run 
 
    java -jar train_hmm_tagger.jar <path of training data> -- <cpostag|postag>
    
    i.e - > java -jar train_hmm_tagger.jar /Users/emre.oral/Documents/CMPE561/metu_sabanci_cmpe_561/train/turkish_metu_sabanci_train.conll cpostag
    
    i.e - > java -jar train_hmm_tagger.jar /Users/emre.oral/Documents/CMPE561/metu_sabanci_cmpe_561/train/turkish_metu_sabanci_train.conll postag
 
command and then run 

    java -jar hmm_tagger.jar <path of test data> <path of output>
    
    i.e - > java -jar hmm_tagger.jar /Users/emre.oral/Documents/CMPE561/metu_sabanci_cmpe_561/validation/turkish_metu_sabanci_val.conll output
    
finally run

    java -jar evaluate_hmm_tagger.jar <path of output> <path of gold data>
    
    i.e - > java -jar evaluate_hmm_tagger.jar output /Users/emre.oral/Documents/CMPE561/metu_sabanci_cmpe_561/validation/turkish_metu_sabanci_val.conll 

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

* Be sure that you have java8. 











