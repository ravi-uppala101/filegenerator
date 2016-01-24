package com.capitalone.cardrewards.filegenerator.main;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.capitalone.cardrewards.filegenerator.RecordConsumer;
import com.capitalone.cardrewards.filegenerator.RecordProducer;
import com.capitalone.cardrewards.filegenerator.constants.ApplicationConstants;

public class GeneratorImpl implements Generator {
	
	private BlockingQueue<String> blockingQueue = null;
	private AtomicInteger progressCounter = null;
	private Properties prop = null;
	private Path filePath = null;
	private Map<Integer,String> inputRecords = null;
	
	public GeneratorImpl(Properties prop) throws IOException {
		blockingQueue = new ArrayBlockingQueue<String>(1000000);
	    progressCounter = new AtomicInteger(0);
	    this.prop = prop;
	    this.filePath = createFilePath();
	    inputRecords = new ConcurrentHashMap<Integer,String>();
	}

	public Path createFilePath() throws IOException {
		String outputFilePath = prop.getProperty(ApplicationConstants.OUTPUTFILEPATH);
		String fileName = prop.getProperty(ApplicationConstants.FILENAME);
		Path path = FileSystems.getDefault().getPath(outputFilePath.trim(), fileName.trim());
		if (Files.deleteIfExists(path)) {
			System.out.println("Previous file is deleted");
		}
		Path filePath = Files.createFile(path);
		return filePath;
	}
	
	public boolean generateSampleFile() throws Exception {
		// get the property value and print it out
		inputRecords.put(1, prop.getProperty(ApplicationConstants.RECORD1));
		inputRecords.put(2, prop.getProperty(ApplicationConstants.RECORD2));
		inputRecords.put(3, prop.getProperty(ApplicationConstants.RECORD3));
		inputRecords.put(4, prop.getProperty(ApplicationConstants.RECORD4));
		inputRecords.put(5, prop.getProperty(ApplicationConstants.RECORD5));

		int noOfThreads = Integer.valueOf(prop.getProperty(ApplicationConstants.NOOFTHREADS, "1"));
		int noOfRecords = Integer.valueOf(prop.getProperty(ApplicationConstants.NOOFRECORDS));

		ExecutorService executor = Executors.newFixedThreadPool(noOfThreads);
		int recordsPerThread = noOfRecords / noOfThreads;

		long startTime = System.currentTimeMillis();
		
		RecordConsumer recordConsumer = new RecordConsumer(blockingQueue, progressCounter, filePath, noOfRecords);
		executor.submit(recordConsumer);
		for(int i = 1; i <= noOfThreads; i++) {
			if(i == noOfThreads) {
				recordsPerThread = (noOfRecords - ((noOfThreads-1) * recordsPerThread));
			}
			RecordProducer task = new RecordProducer(blockingQueue, inputRecords.get(i), recordsPerThread);
			executor.submit(task);
		}
		
		while (progressCounter.get() < noOfRecords) {
			if (progressCounter.get() > 0 && progressCounter.get() % 10000000 == 0) {
				System.out.println(" No of Records written so far..." + progressCounter.get());
			}
		}
		executor.shutdown();
		if(progressCounter.get() == noOfRecords) {
			System.out.println(" No of Records written so far..." + progressCounter.get());
		}
		System.out.println("File writing is completed");
		System.out.println("File is available at the following path = " + filePath.toString());
		while (!executor.isTerminated()) {
	      }
		System.out.println(
				"Total time took for creating file = " + (System.currentTimeMillis() - startTime) / 1000 + " sec's");
		return false;
	}

}
