package com.demo.model;

public class Token {

	private int number;
	private int priority;

	public Token() {

	}

	public Token(int number, int priority) {
		this.number = number;
		this.priority = priority;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "Token [number=" + number + ", priority=" + priority + "]";
	}
}