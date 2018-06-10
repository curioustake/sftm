package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.RandomDataGenerator;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class P36_Fibonacci implements Activity {

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int count = Integer.parseInt(args[1]);
        final boolean printResult = Boolean.parseBoolean(args[2]);

        if(count <= 1) {
            System.out.println("INPUT SIZE TO SMALL ");
            return;
        }

        Integer[] priceList = RandomDataGenerator.getRandomIntegerArray(count, 1, count, printResult);
        Arrays.parallelSort(priceList);

        Arrays.stream(priceList).forEach(num -> {
            System.out.println("\n");
//            long fibonacciBruteForce = fibonacciBruteForce(num);
//            System.out.println("Fibonacci Brute Force " + num + "|" + fibonacciBruteForce);

            BigInteger fibonacciDPMemoized = fibonacciDPMemoized(num, new HashMap<>());
            System.out.println("Fibonacci DP Memoized " + num + "|" + fibonacciDPMemoized);

            BigInteger fibonacciDPBottomUp = fibonacciDPBottomUp(num);
            System.out.println("Fibonacci DP BottomUp " + num + "|" + fibonacciDPBottomUp);

            if(fibonacciDPBottomUp.compareTo(fibonacciDPMemoized) != 0)
                throw new RuntimeException("Fibonacci DP method got it wrong");
        });
    }

    private long fibonacciBruteForce(final long num) {
        if(num < 1)
            throw new RuntimeException("Fibonacci has to be > 0");

        if(num == 1 || num == 2)
            return 1;

        return fibonacciBruteForce(num - 1) + fibonacciBruteForce(num - 2);
    }

    private BigInteger fibonacciDPMemoized(final long num, Map<Long, BigInteger> fibCache) {
        if(num < 1)
            throw new RuntimeException("Fibonacci has to be > 0");

        if(num == 1 || num == 2)
            return new BigInteger("1");

        if(!fibCache.containsKey(num - 1))
            fibCache.put((num - 1), fibonacciDPMemoized(num - 1, fibCache));

        if(!fibCache.containsKey(num - 2))
            fibCache.put((num - 2), fibonacciDPMemoized(num - 2, fibCache));

        return  fibCache.get(num - 1).add(fibCache.get(num - 2));
    }

    private BigInteger fibonacciDPBottomUp(final int num) {
        if(num < 1)
            throw new RuntimeException("Fibonacci has to be > 0");

        if(num == 1 || num == 2)
            return new BigInteger("1");

        BigInteger[] fibCache = new BigInteger[num];

        for(int i=1; i<=num; i++) {
            BigInteger fibN = (i == 1 || i == 2) ? new BigInteger("1") : fibCache[i-2].add(fibCache[i-3]);

            fibCache[i-1] = fibN;
        }

        return  fibCache[num-1];
    }
}
