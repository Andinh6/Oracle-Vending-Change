# Vending Machine - Coin Change API

## Background

The fundamental problem in our vending machine coin tracking API is actually variation of the well known change-making problem. The most common variation of this problem entails finding the minimun number of coins (of some demoninations) that add up to a given value, assuming the set of available coins is infinite. We can assume that for our problem, we also want to return the minimal number of change too for the benefit of the customer. After all, good vending machines give back change, but the best vending machines give change with the minimal number of coins.

The crucial differences that make our problem unique is that the set of available coins is not infinite, it is defined by the coins stored in the vending machine, and that we are also trying to find out the set of coins returned as change, not just the number of coins.

### Dynamic Programming Approach

The UK coin system is a canonical coin system, meaning that a greedy method that picks the largest coins denomination up to the remaining desired amount should produce an optimal minimal number of coins. However, this is assuming we have an infinite supply of coin demoninations, which is not the case in our vending machine problem.

The greedy method could be adapted to produce an optimal solution (enumerate all subsets), but this would result in an exponential time complexity. A far more efficient solution would be to use a dynamic programming approach. I was able to adapt the dynamic programming approach for the change-making problem to account for the finite number of coins and the set of coins selected, allowing for a polynomial time complexity.

## Setup
`sudo apt-get install openjdk-8-jdk` 

`sudo apt-get install maven` 
## Usage
`mvn package` 

`java -cp target/vending-change-0.1.jar com.oracle.app.Application` 