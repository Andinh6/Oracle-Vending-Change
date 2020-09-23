package com.oracle.app.services;

import java.util.*;
import com.oracle.app.models.*;

public class VendingHarness extends VendingCoinChange {

	private Scanner scan = new Scanner(System.in);

	public void menu() {
		boolean running = true;

		System.out.println("Initialise coinbank with coins? Yes/No");
		String initialise = scan.next();
		if (initialise.matches("Yes")) {
			depositCoins(takeCoinInput());
		}

		while (running) {
			System.out.println("\nChoose your option:");
			System.out.println("[0] View Stored Coins");
			System.out.println("[1] Deposit Coins");
			System.out.println("[2] Withdraw Coins");
			System.out.println("[3] Get Change");
			System.out.println("[4] Exit");

			String option = scan.next();
			while (!option.matches("\\d+") || Integer.valueOf(option) > 4) {
				System.out.println("Invalid input, please try again.");
				option = scan.next();
			}

			switch(Integer.valueOf(option)) {
				case 0:
					getStoredCoins();
					break;
				case 1:
					depositCoins(takeCoinInput());
					break;
				case 2:
					withdrawCoins(takeCoinInput());
					break;
				case 3:
					System.out.println("How much change?");

					String change = scan.next();
					while (!change.matches("\\d+")) {
						System.out.println("Invalid input, please try again.");
						change = scan.next();
					}

					getChange(Integer.valueOf(change));
					break;
				case 4:
					System.exit(0);
					break;
			}
		}
	}

	private int[] takeCoinInput() {
		int[] coins = new int[coinBank.getSize()];

		for (int i = 0; i < coins.length; i++) {
			System.out.println("How many " + coinBank.getDenomNames()[i] + " coins?:");

			String count = scan.next();
			while (!count.matches("\\d+")) {
				System.out.println("Invalid input, please try again.");
				count = scan.next();
			}

			coins[i] = Integer.valueOf(count);
		}

		return coins;
	}
}