package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;

/**
 * Purpose : Given and array of integers find a continuous sub structure that has the maximum sum
 *
 * Details: this is the brute force approach
 *
 * Complexity (Time): O(n ^ 2)
 * */

public class P11_MaximumSubarraryBruteForce  implements Activity {
    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int count = Integer.parseInt(args[1]);
        final int max = Integer.parseInt(args[2]);
        final boolean printResults = Boolean.parseBoolean(args[3]);

        Integer[] original = RandomDataGenerator.getRandomIntegerArray(count, -max, max, printResults);

        if(count <= 1) {
            System.out.println("INPUT SIZE TO SMALL ");
            return;
        }

        Integer[] maxSubArray = getMaxSubArray(original);

        System.out.println("MAX SUB ARRAY : " + Arrays.toString(maxSubArray));
    }

    public static Integer[] getMaxSubArray(final Integer[] input) {
        int maxSumStart = 0;
        int maxSumEnd = 0;
        long maxSum = input[0];
        for(int i=0; i<input.length; i++) {
            long runningSum = 0;
            for(int j=i; j<input.length; j++) {
                runningSum = runningSum + input[j];

                if(maxSum < runningSum) {
                    maxSum = runningSum;
                    maxSumStart = i;
                    maxSumEnd = j;
                }
            }
        }

        //System.out.println("MAX SUB ARRAY SUM : [" + maxSum + "]");

        return Arrays.copyOfRange(input, maxSumStart, maxSumEnd+1);
    }

}
