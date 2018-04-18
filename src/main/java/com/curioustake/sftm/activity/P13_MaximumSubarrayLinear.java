package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;

/**
 * Purpose : Given and array of integers find a continuous sub structure that has the maximum sum
 *
 * Details: this is the linear approach
 *
 * Complexity (Time): O(n)
 * */

public class P13_MaximumSubarrayLinear  implements Activity {
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

        Integer[] maxSubArrayControl = P11_MaximumSubarraryBruteForce.getMaxSubArray(original);
        System.out.println("\nMAX SUB ARRAY CONTROL : " + Arrays.toString(maxSubArrayControl));

        SubArray subarrayScope = getMaxSubArray(original);
        Integer[] maxSubArray = Arrays.copyOfRange(original, subarrayScope.start_, subarrayScope.end_+1);
        System.out.println("\nMAX SUB ARRAY TREATMENT : " + Arrays.toString(maxSubArray));

        System.out.println("\nCOMPUTED IDENTICAL MAX SUB-ARRAY? [" + Arrays.equals(maxSubArrayControl, maxSubArray) + "]");

        System.out.println("\nCOMPUTED MAX SUB-ARRAY SUM? [" + (getSum(maxSubArrayControl) + "|" + getSum(maxSubArray)) + "]");

        System.out.println("\nCOMPUTED IDENTICAL MAX SUB-ARRAY SUM? [" + (getSum(maxSubArrayControl) == getSum(maxSubArray)) + "]");

        if((getSum(maxSubArrayControl) != getSum(maxSubArray))) {
            throw new RuntimeException("Something went wrong while computing max subarray");
        }
    }

    private SubArray getMaxSubArray(final Integer[] input) {
        long maxSubarraySum = input[0];
        SubArray maxSubarray = new SubArray(0,0);

        long tailMaxSubarraySum = input[0];
        SubArray tailMaxSubarray = new SubArray(0,0);

        for(int i=1; i<input.length; i++) {
            tailMaxSubarraySum+=input[i];

            if(tailMaxSubarraySum < input[i]) {
                tailMaxSubarray.start_ = i;
                tailMaxSubarraySum = input[i];
            }

            tailMaxSubarray.end_ = i;

            if(maxSubarraySum < tailMaxSubarraySum) {
                maxSubarraySum = tailMaxSubarraySum;
                maxSubarray.start_ = tailMaxSubarray.start_;
                maxSubarray.end_ = tailMaxSubarray.end_;
            }
        }
        return maxSubarray;
    }

    private long getSum(Integer[] input) {
        long sum=0;
        for(int i=0; i<input.length; i++) {
            sum+=input[i];
        }
        return sum;
    }

    class SubArray {
        int start_;
        int end_;

        SubArray(int start, int end) {
            this.start_ = start;
            this.end_ = end;
        }
    }
}