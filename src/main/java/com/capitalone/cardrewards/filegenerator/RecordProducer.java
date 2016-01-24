package com.capitalone.cardrewards.filegenerator;

import java.util.concurrent.BlockingQueue;

public class RecordProducer implements Runnable {

	private final BlockingQueue<String> blockingQueue;
	private final String record;
	private final int noOfRecords;

	public RecordProducer(BlockingQueue<String> blockingQueue, String record, int noOfRecords) {
		this.blockingQueue = blockingQueue;
		this.record = record;
		this.noOfRecords = noOfRecords;
	}

	@Override
	public void run() {
		for (int i = 0; i < noOfRecords; i++) {
			try {
				blockingQueue.put(record);
			} catch (InterruptedException e) {
				System.err.format("InterruptedException: %s%n", e);
			}
		}
	}

}
