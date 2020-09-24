package vendingmachine;

import java.util.*;

/**
 * API for tracking coins within the vending machine
 */
public interface VendingCoinChangeAPI {

	/**
	 * Initialize the vending machine coin bank with an initial float 
	 * Note: This will overwrite the previous coin bank contents
	 * @param initialFloat A hashmap with key coin demonination and value coin count
	 */
	public void initialize(Map<Integer, Integer> initialFloat);

	/**
	 * Deposit an individual coin into the vending machine coin bank
	 * @param coin An integer representing coin demonination
	 */
	public void depositCoin(int coin);

	/**
	 * Calculate coins needed to sum to desired amount and remove them from the coin bank
	 * @param changeAmount Total amount of the desired change
	 */
	public void getChange(int changeAmount);
}