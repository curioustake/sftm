package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.DataValidator;
import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;

/**
 * Purpose
 * Let A[1..n] be an array of n distinct numbers. If i < j and A[i] > A[j], then the
 * pair (i, j) is called an inversion of A.
 * Example : Following array has 5 inversions [2, 3, 8, 6, 1].
 *
 * Details: Modifies merge getInversionCount to find inversions in a given data set
 *
 * Complexity (Time): O(n * log(n))
 * */

public class P10_InversionDetector implements Activity {

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int count = Integer.parseInt(args[1]);
        final int max = Integer.parseInt(args[2]);
        final boolean printResults = Boolean.parseBoolean(args[3]);

        Integer[] original = RandomDataGenerator.getDistinctRandomIntegerArray(count, max, printResults);

        if(count <= 1) {
            System.out.println("INPUT SIZE TO SMALL ");
            return;
        }

        Integer[] inputAsc = original.clone();
        int inversionCount = getInversionCount(inputAsc, 0, inputAsc.length-1);
        int inversionCountControl = validateInversionCount(original.clone());
        System.out.println("\n###################### MERGE SORT validateSortAscending ##############################");
        System.out.println("\nSORT ASCENDING SUCCESSFUL? [" + DataValidator.validateSortAscending(original, inputAsc, printResults) + "]\n");
        System.out.println("Inversion Count : [" + inversionCount + "] | brute force [" + inversionCountControl + "]" );
        if(inversionCount != inversionCountControl) throw new RuntimeException("Something went worng in computing inversion count");
        System.out.println("############################################################################################\n");
    }

    private int getInversionCount(Integer[] input, int startIndex, int endIndex) {
        int inversionCount = 0;
        if((endIndex - startIndex) <= 0) {
            return inversionCount;
        }

        int splitIndex = split(startIndex, endIndex).intValue();

        inversionCount += getInversionCount(input, startIndex, splitIndex);
        inversionCount += getInversionCount(input, splitIndex+1, endIndex);

        inversionCount += merge(input, startIndex, endIndex);

        return inversionCount;
    }

    private Double split(int startingIndex, int endingIndex) {
        return startingIndex + Math.floor((endingIndex - startingIndex)/2);
    }

    private int merge(Integer[] input, int startIndex, int endIndex) {
        int splitIndex = split(startIndex, endIndex).intValue();

        Integer[] sortedArray1 = Arrays.copyOfRange(input, startIndex, splitIndex+1);
        Integer[] sortedArray2 = Arrays.copyOfRange(input, splitIndex+1, endIndex+1);

        int inversionCount = 0;

        for(int i=startIndex, j=0, k=0; i<=endIndex; i++) {
            if((k>=sortedArray2.length) || ((j<sortedArray1.length) && (sortedArray1[j] < sortedArray2[k]))) {
                input[i] = sortedArray1[j];
                j++;
            } else {
                input[i] = sortedArray2[k];
                inversionCount = inversionCount + (sortedArray1.length - j);
                k++;
            }
        }
        return inversionCount;
    }

    private int validateInversionCount(Integer[] input) {
        int inversionCount = 0;

        for(int i=0; i<input.length; i++) {
            for(int j=i+1; j<input.length; j++) {
                if(input[i] > input[j]) {
                    inversionCount++;
                }
            }
        }

        return inversionCount;
    }
}
