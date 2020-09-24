import java.util.*;

/**
 * Implementation of vending machine coin change API
 */
public class VendingCoinChange implements VendingCoinChangeAPI {

	// Hashmap representation of the coinbank where key is the coin demoniation and value is the coin count
	private Map<Integer, Integer> coinBank;

	// Constructor
	public VendingCoinChange() {
		coinBank = new HashMap<>();
	}

	/**
	 * Initialize the vending machine coin bank with an initial float 
	 * Note: This will replace the previous coinbank contents
	 * @param initialFloat A hashmap with key coin demonination and value quantity
	 */
	@Override
	public void initialize(Map<Integer, Integer> initialFloat) {
		for (Map.Entry<Integer, Integer> coin : initialFloat.entrySet()) {
			if (coin.getKey() <= 0 || coin.getValue() < 0) {
				System.out.println("Invalid coin entered");
				return;
			}
		}

		coinBank = initialFloat;
	}

	/**
	 * Deposit an individual coin into vending machine coinbank
	 * @param coin An integer representing coin demonination
	 */
	@Override
	public void depositCoin(int coin) {
		if (coin <= 0) {
			System.out.println("Invalid coin entered");
			return;
		}

		System.out.println("Depositing " + coin + " x 1");

		if (coinBank.containsKey(coin)) {
			coinBank.put(coin, coinBank.get(coin) + 1);
		}
		else {
			coinBank.put(coin, 1);
		}
	}

	/**
	 * Calculate coins needed to sum to desired amount and remove from coinbank
	 * @param changeAmount Total amount of the desired change
	 */
	@Override
	public void getChange(int changeAmount) {
		if (changeAmount <= 0) {
			return;
		}

		// Convert hashmap to two arrays while ensuring consistent order
		int[] coins = new int[coinBank.size()];
		int[] count = new int[coinBank.size()];
		int index = 0;
		for (Map.Entry<Integer, Integer> coin : coinBank.entrySet()) {
		    coins[index] = coin.getKey();
		    count[index] = coin.getValue();
		    index++;
		}

		// Calculate dp table for determining change
		try {
			int[][] dp = determineChange(changeAmount, coins, count);
			int[] returnedChange = dp[changeAmount];

			// Remove coin change from coinbank
			System.out.println("Change returned");
			for (int i = 0; i < coinBank.size(); i++) {
				if (returnedChange[i] > 0) {
					int denomination = coins[i];
					coinBank.put(denomination, coinBank.get(denomination) - returnedChange[i]);
					System.out.println(denomination + " x " + returnedChange[i]);
				}
			}
		}
		catch (InsufficientCoinsException e) {
			System.out.println(e);
		}
	}

	/**
	 * Returns the coinbank hashmap
	 * @return A hashmap representation of the coinbank 
	 */
	public Map<Integer, Integer> getCoinBank() {
		return coinBank;
	}

	/** Dynamic programming approach for determining coins to sum up to change amount
	 * @param amount The total amount of the desired change
	 * @param coins Int array of coin demoninations
	 * @param count Int array of corresponding coin counts 
	 * @throws InsufficientCoinsException If no possible coins to sum to amount
	 * @return 2D int array representation of the dp table [i][j] that returns coin count given i = desired amount and j = coin demonination
	 */
	private int[][] determineChange(int amount, int[] coins, int[] count) throws InsufficientCoinsException {
        int[][] coinsMatrix = new int[amount + 1][]; // dp table to store subproblem solutions
        int[] minCoins = new int[amount + 1]; // Array [k] that returns minimun coin count needed to sum to amount k

        // Allocate dp table and set mincoins to max integer value (- 1)
        coinsMatrix[0] = new int[coins.length]; 
        for (int i = 1; i <= amount; i++) {
            coinsMatrix[i] = new int[coins.length];
            minCoins[i] = Integer.MAX_VALUE - 1;
        }

        // Iterative dynamic programming 
        for (int i = 0; i < coins.length; i++) { 
        	for (int j = 0; j < count[i]; j++) { 
                for (int k = amount; k >= 0; k--) { 

                	// Subproblem
                    int currentAmount = k + coins[i]; 
                    if (currentAmount <= amount && minCoins[currentAmount] > minCoins[k] + 1) { 
                        minCoins[currentAmount] = minCoins[k] + 1; 

                        coinsMatrix[currentAmount] = Arrays.copyOf(coinsMatrix[k], coinsMatrix[k].length); 
                        coinsMatrix[currentAmount][i] += 1;
                    }
                }
            }
        }

        // Throws exception if no possible coins to sum amount, else return dp table
        if (minCoins[amount] == Integer.MAX_VALUE - 1) {
        	throw new InsufficientCoinsException();
        }
        else {
         	return coinsMatrix;
        }
	}
}