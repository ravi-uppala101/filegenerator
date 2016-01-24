package com.capitalone.cardrewards.filegenerator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class RecordConsumer implements Runnable {

	private final BlockingQueue<String> blockingQueue;
	private final AtomicInteger progressCounter;
	private final Path filePath;
	private final int noOfRecords;

	public RecordConsumer(BlockingQueue<String> blockingQueue, AtomicInteger progressCounter,
			Path filePath, int noOfRecords) {
		this.blockingQueue = blockingQueue;
		this.progressCounter = progressCounter;
		this.filePath = filePath;
		this.noOfRecords = noOfRecords;
	}

	@Override
	public void run() {
		try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
			System.out.println("Started writing to file ");
			String recordString = null;
			while (true) {
				recordString = blockingQueue.take();
				writer.write(recordString, 0, recordString.length());
				writer.newLine();
				progressCounter.incrementAndGet();
				if(progressCounter.get() == noOfRecords) {
					break;
				}
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		} catch (InterruptedException e) {
			System.err.format("InterruptedException: %s%n", e);
		}

	}

}
