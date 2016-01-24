# filegenerator

This project is used for generating a sample based on given input parameters in properties file


fields = ""12345","CO","PD","CR","CO","PD","CR","CO","PD","CR","CO","PD","CR","CO","PD","CR","CO","PD","CR","CO","PD","CR","CO","PD","CR"   //this line is used for the text that goes into the file

outputFilePath = /Users/ravinderuppala/eclipse/workspace/filegenerator  // path to the file where it needs to be generated

noOfThreads = 5 // no of threads used for writing...it is not used currently...because file generation went faster than I expected

noOfRecords = 60000000 // no of records needed in the file


#setting up the project
1. install java8, maven, git.
1. git clone https://github.com/ravinderuppala/filegenerator.git
2. cd filegenerator
3. mvn package  //this line generates a uber jar containing all the dependencies in it.
4. java -jar target/filegenerator-0.0.1-SNAPSHOT.jar


#Console output

fields = "12345","CO","PD","CR","CO","PD","CR","CO","PD","CR","CO","PD","CR","CO","PD","CR","CO","PD","CR","CO","PD","CR","CO","PD","CR"

outputFilePath = /Users/ravinderuppala/eclipse/workspace/filegenerator

noOfRecords = 60000000

Previous file is deleted

Started writing to file 

 No of Records written so far...0
 
 No of Records written so far...10000000
 
 No of Records written so far...20000000
 
 No of Records written so far...30000000
 
 No of Records written so far...40000000
 
 No of Records written so far...50000000
 
 No of Records written so far...60000000
 
File writing is completed

Total time took for creating file = 44 sec's
