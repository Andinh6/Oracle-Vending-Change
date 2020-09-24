import java.util.*;

/**
 * API for tracking coins within the vending machine
 */
public interface VendingCoinChangeAPI {

	/**
	 * Initialize the vending machine coin bank with an initial float 
	 * Note: This will replace the previous coinbank contents
	 * @param initialFloat A hashmap with key coin demonination and value quantity
	 */
	public void initialize(Map<Integer, Integer> initialFloat);

	/**
	 * Deposit an individual coin into vending machine coinbank
	 * @param coin An integer representing coin demonination
	 */
	public void depositCoin(int coin);

	/**
	 * Calculate coins needed to sum to desired amount and remove them from coinbank
	 * @param changeAmount Total amount of the desired change
	 */
	public void getChange(int changeAmount);
}