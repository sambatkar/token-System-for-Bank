package com.demo.producer;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

import com.demo.model.Token;

public class TokenVendingMachine implements Runnable {

	private PriorityBlockingQueue<Token> priorityBlockingQueue;
	private Random randomPriorityGenerator = new Random();

	public TokenVendingMachine(PriorityBlockingQueue<Token> blockingQueue) {
		this.priorityBlockingQueue = blockingQueue;
	}

	@Override
	public void run() {
		for (int i = 0; i <= 50; i++) {
			Token token = new Token(i, randomPriorityGenerator.nextInt(4));
			System.out.println("New token issued :" + token);
			priorityBlockingQueue.put(token);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println();
		}
	}
}