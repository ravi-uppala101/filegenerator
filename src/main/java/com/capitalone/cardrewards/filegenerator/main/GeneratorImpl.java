package com.capitalone.cardrewards.filegenerator.main;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.capitalone.cardrewards.filegenerator.constants.ApplicationConstants;

public class GeneratorImpl implements Generator {

	public boolean generateSampleFile(Properties prop) throws Exception {
        //get the property value and print it out
        String fieldString = prop.getProperty(ApplicationConstants.FIELDS);
        String outputFilePath = prop.getProperty(ApplicationConstants.OUTPUTFILEPATH);
        int noOfThreads = Integer.valueOf(prop.getProperty(ApplicationConstants.NOOFTHREADS, "1"));
        int noOfRecords = Integer.valueOf(prop.getProperty(ApplicationConstants.NOOFRECORDS));
		
        String[] fields = StringUtils.split(fieldString, ApplicationConstants.FIELD_SEPARATOR);
		
        Path path = FileSystems.getDefault().getPath(outputFilePath, "accountInfo.txt");
        if(Files.deleteIfExists(path)) {
        	System.out.println("Previous file is deleted");
        }  
        Path filePath = Files.createFile(path);
        
       long startTime = System.currentTimeMillis();
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
        	System.out.println("Started writing to file ");
        	for(int i = 0; i< noOfRecords; i++) {
        		writer.write(fieldString, 0, fieldString.length());
        		writer.newLine();
        		if( i % 10000000 == 0) {
        			System.out.println(" No of Records written so far..."+i);
        		}
        	}
        	System.out.println(" No of Records written so far..."+noOfRecords);
            System.out.println("File writing is completed");
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        System.out.println("Total time took for creating file = "+(System.currentTimeMillis() - startTime)/1000 +" sec's");
		return false;
	}

}
