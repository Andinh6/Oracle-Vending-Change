# Vending Machine - Coin Change API

## Background

The main problem in our vending machine coin tracking API is similar to the well known change-making problem. The most common variation of this problem entails finding the minimum number of coins (of some demoninations) that add up to a given value, assuming the set of available coins is infinite. We can assume that for our problem, we also want to return the minimal number of change too for the benefit of the customer. After all, good vending machines give back change, but the best vending machines give change with the minimal number of coins.

The crucial differences that make our problem unique is that the set of available coins is not infinite, it is defined by the coins stored in the vending machine, and that we are also trying to find out the set of coins returned as change, not just the number of coins.

### Dynamic Programming Approach

The UK coin system is a canonical coin system, meaning that a greedy method that picks the largest coins denomination up to the remaining desired amount should produce an optimal minimal number of coins. However, this is assuming we have an infinite supply of coins, which is not the case in our vending machine problem.

The greedy method could be adapted to produce an optimal solution (enumerate all subsets), but this would result in an exponential time complexity. A far more efficient solution would be to use a dynamic programming approach. I adapted the dynamic programming approach for the change-making problem to account for the finite number of coins and the set of coins selected, allowing for a polynomial time complexity.

## Setup
`sudo apt-get install openjdk-8-jdk` 

`sudo apt-get install maven` 
## Usage
`mvn package` 

`java -cp target/vending-change-0.1.jar Application` 

## Design

The Vending Machine's Coin Change API features three public methods based on the API component requirements provided on the task specification. 

### Initialize
`public void initialize(Map<Integer, Integer> initialFloat)`

Initialize the vending machine's coinbank with an initial float, a hashmap with coin demoninations and corresponding coin counts. Coins with negative values or counts will void the initialisation.

### DepositCoin
`public void depositCoin(int coin)` 

Deposit an individual coin into the vending machine's coinbank. Coins with negative values are void.

### GetChange
`public void getChange(int changeAmount)` 

Calculate the minimum number of counts to sum up to a total amount, and remove the coins from the vending machine's coinbank. The performance for a typical usecase of the vending machine using dynamic programming is slower than a non-minimal coin count greedy approach, but ensures an optimal solution. However, a minimal coin count greedy approach would be significantly slower.

## Decisions and Justifications

* The coinbank is represented as a hashmap with integer pairs for storing coin demonination and its corresponding count. Hashmaps allowed for efficient lookup and modification of particular coin demoninations. A coin object could've also been used instead of integers but this wasn't absolutely necessary.
* The coinbank implementation allows for the representation of arbitrary coin demoninations, but it could be adapted to enforce aa strict set of coin demoninations e.g. 1p, 2p, 5p, 10p, 20p, 50p, £1 and £2 coins only
* The coinbank hashmap is converted to two arrays during change calculation due to the overhead of iterating through a hashmap repeatedly during the iterative dynamic programming execution. This allowed for slightly better performance and more intuitive code.
* Used an Exception for when there is insufficient coins for a desired change amount, which is caught in an try/catch block. An alternate option would be an if function to check for a null return but I felt the former code looked cleaner. 
* The API features minimal features (initialise, deposit, and get change) to ensure modularity with the rest of the imaginary "vending machine". It avoids handling aspects of a vending machine that would probably be better served in different components beyond this API. 
* The API contains some print functions mainly to provide clarify for the testing harness.
* A set of JUnit tests are included 