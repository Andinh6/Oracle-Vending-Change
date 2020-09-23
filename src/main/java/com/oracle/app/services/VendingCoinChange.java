package com.oracle.app.services;

import java.util.*;
import com.oracle.app.models.*;

public class VendingCoinChange {

	protected CoinBank coinBank;

	public VendingCoinChange() {
		coinBank = new UKCoinBank();
	}

	public VendingCoinChange(int[] initCoins) {
		coinBank = new UKCoinBank();

		depositCoins(initCoins);
	}

	public int[] getStoredCoins() {
		int[] coins = coinBank.getBank();

		System.out.print("\n[BANK]");
		printCoins(coins);

		return coins;
	}

	public int[] depositCoins(int[] coins) {

		for (int i = 0; i < coins.length; i++) {
			coinBank.add(i, coins[i]);
		}

		System.out.print("\n[INPUT]");
		printCoins(coins);

		return coins;
	}

	public int[] withdrawCoins(int[] coins) {

		for (int i = 0; i < coins.length; i++) {
			if (coinBank.getBank()[i] < coins[i]) {
				System.out.println("Cannot retrieve more coins than is in the coinbank!");

				return new int[coinBank.getSize()];
			}
		}

		for (int i = 0; i < coins.length; i++) {
			coinBank.add(i, -coins[i]);
		}

		System.out.print("\n[OUTPUT]");
		printCoins(coins);

		return coins;
	}

	public void getChange(int target) {
		int[][] dp = determineChange(target, coinBank.getDenomVal(), coinBank.getBank());
		int[] coinsChange = new int[coinBank.getSize()];

		try {
			for (int i = 0; i < coinsChange.length; i++) {
				coinsChange[i] = dp[target][i];
			}
			System.out.println("Exact change found");
		}
		catch (Exception e) {
			for (int i = 0; i < coinsChange.length; i++) {
				coinsChange[i] = coinBank.getBuffer()[i];
			}
			System.out.println("No change possible");
		}

		withdrawCoins(coinsChange);
	}

	public void printCoins(int[] coins) {
		System.out.print("[ ");
		for (int i = 0; i < coins.length; i++) {
			System.out.print(coins[i] + " ");
		}
		System.out.println("]");
	}

	// Dynamic programming approach for calculating minimun amount of coins to sum some amount
	private static int[][] determineChange(int amount, int[] coins, int[] numCoins) {
        int[][] coinsMatrix = new int[amount + 1][]; // Dynamic programming table where i = 0 to desired amount and j = coin denominations
        int[] minCoins = new int[amount + 1]; // Minimun amount of coins needed to sum to amount k

        coinsMatrix[0] = new int[coins.length]; // Create 2D dynamic programming table and set min coins to max int (- 1)
        for (int i = 1; i <= amount; i++) {
            coinsMatrix[i] = new int[coins.length];
            minCoins[i] = Integer.MAX_VALUE - 1;
        }

        for (int i = 0; i < coins.length; i++) { // Iterate through each coin denomination 
        	for (int j = 0; j < numCoins[i]; j++) { // Iterate up to number of coins available of each coin denomination
                for (int k = amount; k >= 0; k--) { // Iterate down from desired amount to 0

                    int currentAmount = k + coins[i]; // Add coin of particular denomination to current amount
                    if (currentAmount <= amount && minCoins[currentAmount] > minCoins[k] + 1) { 
                        minCoins[currentAmount] = minCoins[k] + 1; 

                        coinsMatrix[currentAmount] = Arrays.copyOf(coinsMatrix[k], coinsMatrix[k].length); 
                        coinsMatrix[currentAmount][i] += 1;
                    }
                }
            }
        }

        return minCoins[amount] == Integer.MAX_VALUE - 1 ? null : coinsMatrix;
	}
}