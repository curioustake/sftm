package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class P35_RodCuttingDP implements Activity {

    final Map<Integer, Map<Integer, Integer>> memoizedCuts = new HashMap<>();

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int maxRodSize = Integer.parseInt(args[1]);
        final int maxPrice = Integer.parseInt(args[2]);
        final boolean printResult = Boolean.parseBoolean(args[3]);

        if(maxRodSize <= 1) {
            System.out.println("INPUT SIZE TO SMALL ");
            return;
        }

        Integer[] priceList = RandomDataGenerator.getRandomIntegerArray(maxRodSize, 1, maxPrice, printResult);
        Arrays.parallelSort(priceList);

        final Random random = new Random();
        int randomRodLength = 0;

        while(randomRodLength == 0)
            randomRodLength = random.nextInt(maxRodSize);

        if(printResult) {
            System.out.println("PriceList  \t: " + Arrays.toString(priceList));
            System.out.println("Optimize cuts for rod with length : " + randomRodLength);
        }

        // Brute Force
//        Map<Integer, Integer> optimizedSolution = optimizeCutsBruteForce(priceList, randomRodLength);
//        System.out.println("\nMaximized Solution");
//        optimizedSolution.entrySet().stream().forEach(e -> System.out.println(String.format("length %s | pieces %s", e.getKey(), e.getValue())));
//        System.out.println("\nMaximized Price: " + getCutPrice(priceList, optimizedSolution));

        // Memoized DP
        Map<Integer, Integer> optimizedSolutionMemoized = optimizeCutsMemoized(priceList, randomRodLength);
        System.out.println("\nMaximized Solution Memoized");
        optimizedSolutionMemoized.entrySet().stream().forEach(e -> System.out.println(String.format("length %s | pieces %s", e.getKey(), e.getValue())));
        System.out.println("\nMaximized Price: " + getCutPrice(priceList, optimizedSolutionMemoized));

//        if(getCutPrice(priceList, optimizedSolution) != getCutPrice(priceList, optimizedSolutionMemoized)) {
//            throw new RuntimeException("Different Optimized prices");
//        }
    }

    private Map<Integer, Integer> optimizeCutsBruteForce(Integer[] priceList, int rodLength) {
        Map<Integer, Integer> maxCut = new HashMap<>();
        maxCut.put(rodLength, 1);
        int maxPrice = priceList[rodLength-1];

        for(int i=1; i<rodLength; i++) {
            int prefixCutPrice = priceList[i-1];

            Map<Integer, Integer> optimizedCut = optimizeCutsBruteForce(priceList, rodLength - i);

            int optimizedCutPrice = getCutPrice(priceList, optimizedCut);

            if(maxPrice < (prefixCutPrice + optimizedCutPrice)) {
                optimizedCut.put(i, optimizedCut.containsKey(i) ? optimizedCut.get(i)+1 : 1);
                maxCut = optimizedCut;
                maxPrice = prefixCutPrice + optimizedCutPrice;
            }
        }
        return maxCut;
    }

    private Map<Integer, Integer> optimizeCutsMemoized(Integer[] priceList, int rodLength) {
        Map<Integer, Integer> maxCut = new HashMap<>();
        maxCut.put(rodLength, 1);
        int maxPrice = priceList[rodLength-1];

        for(int i=1; i<rodLength; i++) {
            int prefixCutPrice = priceList[i-1];

            if(!memoizedCuts.containsKey(rodLength - i)) {
                memoizedCuts.put(rodLength - i, optimizeCutsMemoized(priceList, rodLength - i));
            }

            Map<Integer, Integer> optimizedCut = new HashMap<>();
            memoizedCuts.get(rodLength - i).entrySet().stream().forEach(e -> optimizedCut.put(e.getKey(), e.getValue()));

            int optimizedCutPrice = getCutPrice(priceList, optimizedCut);

            if(maxPrice < (prefixCutPrice + optimizedCutPrice)) {
                optimizedCut.put(i, optimizedCut.containsKey(i) ? optimizedCut.get(i)+1 : 1);
                maxCut = optimizedCut;
                maxPrice = prefixCutPrice + optimizedCutPrice;
            }
        }
        return maxCut;
    }

    private int getCutPrice(Integer[] priceList, Map<Integer, Integer> optimizedCut) {
        int optimizedCutPrice = 0;
        for(Map.Entry<Integer, Integer> e : optimizedCut.entrySet()) {
            optimizedCutPrice = optimizedCutPrice + (e.getValue() * priceList[e.getKey()-1]);
        }
        return optimizedCutPrice;
    }
}
