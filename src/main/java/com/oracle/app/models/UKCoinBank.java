package com.oracle.app.models;

public class UKCoinBank extends CoinBank {

	public UKCoinBank() {
		size = 8;

		denomNames = new String[] {"1p", "2p", "5p", "10p", "20p", "50p", "\u00A3" + "1", "\u00A3" + "2"};
		denomValues = new int[] {1, 2, 5, 10, 20, 50, 100, 200};
		countCoinBank = new int[size];
		countCoinBuffer = new int[size];
	}
}