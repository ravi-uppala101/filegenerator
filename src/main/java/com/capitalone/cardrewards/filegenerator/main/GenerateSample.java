package com.capitalone.cardrewards.filegenerator.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.capitalone.cardrewards.filegenerator.constants.ApplicationConstants;

public class GenerateSample {

	
	
	public static void main(String[] args) throws Exception {
		GenerateSample gnerateSample = new GenerateSample();
		Properties prop = gnerateSample.loadPropertyFile();
		gnerateSample.generate(prop);

	}
	
	public void generate(Properties prop) throws Exception {
		Generator generator = new GeneratorImpl();
		generator.generateSampleFile(prop);
	}
	
	public Properties loadPropertyFile() {
		
		Properties prop = new Properties();
    	InputStream input = null;
    	
    	try {
        
    		String filename = "application.properties";
    		input = GenerateSample.class.getClassLoader().getResourceAsStream(filename);
    		if(input==null){
    	        System.out.println("Sorry, unable to find " + filename);
    		    System.exit(1);
    		}

    		//load a properties file from class path, inside static method
    		prop.load(input);
 
                //get the property value and print it out
                System.out.println(prop.getProperty(ApplicationConstants.FIELDS));
    	        System.out.println(prop.getProperty(ApplicationConstants.OUTPUTFILEPATH));
    	        System.out.println(prop.getProperty(ApplicationConstants.NOOFTHREADS));
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        } finally{
        	if(input!=null){
        		try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	}
        }
		
    	return prop;
	}

}
