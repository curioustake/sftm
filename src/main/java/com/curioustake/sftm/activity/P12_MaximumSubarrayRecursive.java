package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;

/**
 * Purpose : Given and array of integers find a continuous sub structure that has the maximum sum
 *
 * Details: this is the recursive (divide and conquer) approach
 *
 * Complexity (Time): O(n log(n))
 * */

public class P12_MaximumSubarrayRecursive  implements Activity {
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

        SubArray subarrayScope = getMaxSubArray(original, 0, original.length-1);
        Integer[] maxSubArray = Arrays.copyOfRange(original, subarrayScope.start_, subarrayScope.end_+1);
        System.out.println("\nMAX SUB ARRAY TREATMENT : " + Arrays.toString(maxSubArray));

        System.out.println("\nCOMPUTED IDENTICAL MAX SUB-ARRAY? [" + Arrays.equals(maxSubArrayControl, maxSubArray) + "]");

        System.out.println("\nCOMPUTED MAX SUB-ARRAY SUM? [" + (getSum(maxSubArrayControl) + "|" + getSum(maxSubArray)) + "]");

        System.out.println("\nCOMPUTED IDENTICAL MAX SUB-ARRAY SUM? [" + (getSum(maxSubArrayControl) == getSum(maxSubArray)) + "]");
    }

    private SubArray getMaxSubArray(final Integer[] input, int start, int end) {

        if(end == start)
            return new SubArray(start, end);

        Double splitIndex = start + Math.floor((end - start)/2);

        SubArray leftMaxSubarray = getMaxSubArray(input, start, splitIndex.intValue());
        SubArray rightMaxSubarray = getMaxSubArray(input,splitIndex.intValue() + 1, end);
        SubArray crossingMaxSubarray = getMaxCrossingSubArray(input, start, end, splitIndex.intValue());

        long leftMaxSubArraySum = getSum(input, leftMaxSubarray);
        long rightMaxSubArraySum = getSum(input, rightMaxSubarray);
        long crossingMaxSubArraySum = getSum(input, crossingMaxSubarray);

        if(leftMaxSubArraySum > rightMaxSubArraySum) {
            if(leftMaxSubArraySum > crossingMaxSubArraySum)
                return leftMaxSubarray;
            else
                return crossingMaxSubarray;
        } else {
            if(rightMaxSubArraySum > crossingMaxSubArraySum)
                return rightMaxSubarray;
            else
                return crossingMaxSubarray;
        }
    }

    private SubArray getMaxCrossingSubArray(final Integer[] input, int start, int end, int splitIndex) {
        long leftMaxSubArraySum = input[splitIndex];
        long leftRunningSubArraySum = input[splitIndex];

        int leftStart = splitIndex;

        for(int i=splitIndex-1; i>=start; i--) {
            leftRunningSubArraySum+=input[i];
            if(leftRunningSubArraySum >= leftMaxSubArraySum) {
                leftStart = i;
                leftMaxSubArraySum+=input[i];
            }
        }

        long rightMaxSubArraySum = input[splitIndex+1];
        long rightRunningSubArraySum = input[splitIndex+1];

        int rightEnd = splitIndex+1;

        for(int i=splitIndex+2; i<=end; i++) {
            rightRunningSubArraySum+=input[i];
            if(rightRunningSubArraySum >= rightMaxSubArraySum) {
                rightEnd = i;
                rightMaxSubArraySum+=input[i];
            }
        }

        return new SubArray(leftStart, rightEnd);
    }

    private long getSum(Integer[] input) {
        long sum=0;
        for(int i=0; i<input.length; i++) {
            sum+=input[i];
        }
        return sum;
    }

    private long getSum(Integer[] input, SubArray subArray) {
        long sum=0;
        for(int i=subArray.start_; i<=subArray.end_; i++) {
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
