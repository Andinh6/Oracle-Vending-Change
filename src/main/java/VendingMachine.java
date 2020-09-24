import java.util.*;
import java.io.*;

/**
 * Test Harness for vending machine with basic command line interface
 */
public class VendingMachine {

	private Scanner scan = new Scanner(System.in);

	// Vending machine menu loop
	public void menu() {

		VendingCoinChange vm = new VendingCoinChange();

		boolean running = true;
		while (running) {
			System.out.println("\nChoose your option (Enter a number)");
			System.out.println("[0] Initialize");
			System.out.println("[1] View Coins");
			System.out.println("[2] Deposit Coin");
			System.out.println("[3] Get Change");
			System.out.println("[4] Exit");

			String option = scan.next();
			while (!option.matches("\\d+") || Integer.valueOf(option) > 4) {
				System.out.println("Invalid input, please try again.");
				option = scan.next();
			}

			switch(Integer.valueOf(option)) {
				case 0:
					vm.initialize(takeFloatInput());
					break;
				case 1:
					printCoinBank(vm.getCoinBank());
					break;
				case 2:
					vm.depositCoin(takeCoinInput());
					break;
				case 3:
					vm.getChange(takeChangeInput());
					break;
				case 4:
					running = false;
					break;
			}
		}
	}

	// Take input of integers and returns hashmap with key demonination and value count
	private Map<Integer, Integer> takeFloatInput() {
	    Map<Integer, Integer> floatInput = new HashMap<>();

		try {
		    BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Enter coins (with spaces inbetween)");

		    String lines = buff.readLine();    
		    String[] strings = lines.trim().split("\\s+");
		            
		    for (int i = 0; i < strings.length; i++) {
		    	int coin = Integer.parseInt(strings[i]);

    			if (floatInput.containsKey(coin)) {
					floatInput.put(coin, floatInput.get(coin) + 1);
				}
				else {
					floatInput.put(coin, 1);
				}
			}	
		}
		catch (Exception e) {
			System.out.println("Invalid input, please try again.");
		}

	    return floatInput;
	}

	// Take individual coin input
	private int takeCoinInput() {
		System.out.println("Enter a denomination");

		String denomination = scan.next();
		while (!denomination.matches("\\d+")) {
			System.out.println("Invalid input, please try again.");
			denomination = scan.next();
		}

		int coin = Integer.valueOf(denomination);

		return coin;
	}

	// Take desired change input
	private int takeChangeInput() {
		System.out.println("Enter desired change amount");

		String amount = scan.next();
		while (!amount.matches("\\d+")) {
			System.out.println("Invalid input, please try again.");
			amount = scan.next();
		}

		int change = Integer.valueOf(amount);

		return change;
	}

	// Print contents of coinbank hashmap
	private void printCoinBank(Map<Integer, Integer> bank) {
		System.out.println("Coinbank contains");

		for (Map.Entry<Integer, Integer> coin : bank.entrySet()) {
		    System.out.println(coin.getKey() + " x " + coin.getValue());
		}
	}
}