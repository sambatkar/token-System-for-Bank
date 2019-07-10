package com.demo.driver;

import java.util.Comparator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;

import com.demo.consumer.TokenServingCounter;
import com.demo.model.Token;
import com.demo.producer.TokenVendingMachine;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class OpenBank {

	final static ThreadFactory THE_Factory = new ThreadFactoryBuilder().setNameFormat("Counter-(%d)").build();
	final static int NUMBER_OF_TOKENS = 50;
	final static int NUMBER_OF_COUNTERS = 3;

	public static void main(String[] args) throws InterruptedException {

		Comparator<Token> compareByPriority = new Comparator<Token>() {

			@Override
			public int compare(Token o1, Token o2) {
				if (o2.getPriority()>o1.getPriority()) {
					return 1;
				}else if(o2.getPriority()<o1.getPriority()) {
					return -1;
				}else {
					return o1.getNumber()-o2.getNumber();
				}
			}
		};

		PriorityBlockingQueue<Token> blockingQueue = new PriorityBlockingQueue<Token>(NUMBER_OF_TOKENS,
				compareByPriority);

		/**
		 * Create and START the vending Machine. The Machine will create NUMBER_OF_TOKENS token/day.
		 * Which will be served in NUMBER_OF_COUNTERS counters
		 */
		TokenVendingMachine tokenVendingMachine = new TokenVendingMachine(blockingQueue);
		new Thread(tokenVendingMachine).start();
		CountDownLatch countDownLatch = new CountDownLatch(NUMBER_OF_TOKENS);

		// Here we have the Token consumer. We have NUMBER_OF_COUNTERS counter
		TokenServingCounter tokenConsumer = new TokenServingCounter(blockingQueue, countDownLatch);
		ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_COUNTERS, THE_Factory);
		for (int i = 1; i <= NUMBER_OF_COUNTERS; i++) {
			executor.submit(tokenConsumer);
		}
		countDownLatch.await();
		System.out.println("Stopped");
		executor.shutdown();
	}
}