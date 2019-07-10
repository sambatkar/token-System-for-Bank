package com.demo.consumer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.PriorityBlockingQueue;

import com.demo.model.Token;

public class TokenServingCounter implements Runnable {

	private PriorityBlockingQueue<Token> priorityBlockingQueue;
	private CountDownLatch countDownLatch;

	public TokenServingCounter(PriorityBlockingQueue<Token> blockingQueue, CountDownLatch countDownLatch) {
		this.priorityBlockingQueue = blockingQueue;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		/**
		 * Serving token one by one in a loop. 
		 */
		while (true) {
			if (countDownLatch.getCount() == 0) {
				break;
			}
			try {
				// Serving the customer with the token
				Token token = priorityBlockingQueue.take();
				System.out.println(Thread.currentThread().getName() + " :- Serving Token :" + token);
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				/*
				 * Decrementing count from the Countdown Latch as the token is served
				 */
				countDownLatch.countDown();
			}
			System.out.println();
		}
	}
}