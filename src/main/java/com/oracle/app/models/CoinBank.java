package com.oracle.app.models;

public class CoinBank {
	protected String[] denomNames; /// Stores name of each denomination
	protected int[] denomValues; // Stores value of each denomination
	protected int[] countCoinBank; // Stores count of each coin in coin bank
	protected int[] countCoinBuffer; // Stores count of each coin in buffer (last deposited)
	protected int size;

	public int getSize() {
		return size;
	}

	public String[] getDenomNames() {
		return denomNames;
	}

	public int[] getDenomVal() {
		return denomValues;
	}

	public int[] getBank() {
		return countCoinBank;
	}

	public int[] getBuffer() {
		return countCoinBuffer;
	}

	public void add(int index, int amount) {
		countCoinBank[index] += amount;
	}

	public void setBuffer(int[] buffer) {
		countCoinBuffer = buffer;
	}
}