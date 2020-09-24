import vendingmachine.*;

import org.junit.jupiter.api.*;

import java.util.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for vending coin change functionality
 */
public class VendingTest {

	private VendingCoinChange vm;

	@BeforeEach
	public void setUp() {
		vm = new VendingCoinChange();

		// For hiding System.out.print calls in mvn output
		System.setOut(new PrintStream(new OutputStream() {
			public void write(int b) {
			}
		}));
	}

	// Unit test featuring an valid initiatiation of the coinbank
	@Test
	public void initialiseValidFloat() {
		Map<Integer, Integer> validFloat = new HashMap<>();
		validFloat.put(1, 100);
		validFloat.put(2, 50);
		validFloat.put(5, 200);
		validFloat.put(10, 10);
		validFloat.put(20, 5);
		validFloat.put(50, 2);
		validFloat.put(100, 1);
		validFloat.put(200, 1);

		vm.initialize(validFloat);

		assertEquals(vm.getCoinBank(), validFloat);
	}

	// Unit test featuring an invalid initiatiation of the coinbank
	@Test
	public void initialiseInvalidFloat() {
		Map<Integer, Integer> invalidFloat = new HashMap<>();
		invalidFloat.put(1, 100);
		invalidFloat.put(2, 50);
		invalidFloat.put(5, 200);
		invalidFloat.put(10, -10);
		invalidFloat.put(-20, 5);
		invalidFloat.put(50, 2);
		invalidFloat.put(-100, 1);
		invalidFloat.put(200, 1);

		vm.initialize(invalidFloat);

		assertNotEquals(vm.getCoinBank(), invalidFloat);
	}

	// Unit test featuring a invalid coin deposits
	@Test
	public void invalidDeposit() {

		vm.depositCoin(-1);
		vm.depositCoin(0);
		Map<Integer, Integer> emptyFloat = new HashMap<>();

		assertEquals(vm.getCoinBank(), emptyFloat);
	}

	// Unit test featuring a valid coin deposits
	@Test
	public void validDeposit() {

		vm.depositCoin(20);
		vm.depositCoin(10);
		vm.depositCoin(10);
		vm.depositCoin(10);
		vm.depositCoin(500);

		Map<Integer, Integer> depositFloat = new HashMap<>();
		depositFloat.put(20, 1);
		depositFloat.put(10, 3);
		depositFloat.put(500, 1);

		assertEquals(vm.getCoinBank(), depositFloat);
	}

	// Unit test featuring a valid and optimal get change request
	@Test
	public void optimalCoinsChange() {
		Map<Integer, Integer> optimalFloat = new HashMap<>();
		optimalFloat.put(1, 1000);
		optimalFloat.put(20, 3);
		optimalFloat.put(50, 1); // Greedy would take 50 x 1 and 1 * 13 for 63

		vm.initialize(optimalFloat);
		vm.getChange(63);

		Map<Integer, Integer> expectedFloat = new HashMap<>();
		expectedFloat.put(1, 997);
		expectedFloat.put(20, 0);
		expectedFloat.put(50, 1); // Optimal would be 20 x 3 and 1 x 3	

		assertEquals(vm.getCoinBank(), expectedFloat);
	}

	// Unit test featuring an invalid get change request
	@Test
	public void invalidCoinsChange() {
		Map<Integer, Integer> insufficientFloat = new HashMap<>();
		insufficientFloat.put(1, 2);
		insufficientFloat.put(20, 3);
		insufficientFloat.put(50, 1); 

		vm.initialize(insufficientFloat);
		vm.getChange(177);

		assertEquals(vm.getCoinBank(), insufficientFloat);
	}

	// Unit test featuring an invalid overwrite of the coinbank
	@Test
	public void invalidBankOverwrite() {
		Map<Integer, Integer> initialFloat = new HashMap<>();
		initialFloat.put(123, 1);

		vm.initialize(initialFloat);

		Map<Integer, Integer> newFloat = new HashMap<>();
		newFloat.put(1, 1);
		newFloat.put(2, -1);
		newFloat.put(3, 1); 

		vm.initialize(newFloat);

		assertEquals(vm.getCoinBank(), initialFloat);
	}

	// Unit test featuring a valid overwrite of the coinbank
	@Test
	public void validBankOverwrite() {
		Map<Integer, Integer> initialFloat = new HashMap<>();
		initialFloat.put(123, 1);

		vm.initialize(initialFloat);

		Map<Integer, Integer> newFloat = new HashMap<>();
		newFloat.put(1, 1);
		newFloat.put(2, 1);
		newFloat.put(3, 1); 

		vm.initialize(newFloat);

	    assertEquals(vm.getCoinBank(), newFloat);
	}

	// Unit test featuring various calls to initialize, deposit and get change
	@Test
	public void multipleCalls() {
		Map<Integer, Integer> initialFloat = new HashMap<>();
		initialFloat.put(123, 1);

		vm.initialize(initialFloat);

		vm.getChange(123);
		for (int i = 0; i < 10; i++) {
			vm.depositCoin(3);
		}
		for (int i = 0; i < 5; i++) {
			vm.depositCoin(20);
		}
		vm.depositCoin(100);
		vm.getChange(123);

		Map<Integer, Integer> finalFloat = new HashMap<>();
		finalFloat.put(3, 9);
		finalFloat.put(20, 4);
		finalFloat.put(100, 0);
		finalFloat.put(123, 0);

		assertEquals(vm.getCoinBank(), finalFloat);
	}
}
