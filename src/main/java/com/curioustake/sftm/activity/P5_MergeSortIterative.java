package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.DataValidator;
import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;

/**
 * Purpose : Sort a given input
 *
 * Details: Not an In-place sorting
 *
 * Complexity (Time): O(n ^ 2)
 * */

public class P5_MergeSortIterative implements Activity {

    enum SORT_ORDER { ASCENDING, DESCENDING}

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int count = Integer.parseInt(args[1]);
        final int max = Integer.parseInt(args[2]);
        final boolean printResults = Boolean.parseBoolean(args[3]);

        Integer[] original = RandomDataGenerator.getRandomIntegerArray(count, max, printResults);

        if(count <= 1) {
            System.out.println("INPUT SIZE TO SMALL ");
            return;
        }

        // ITERATIVE MERGE SORT ASCENDING
        Integer[] inputAsc = original.clone();
        sort(inputAsc, SORT_ORDER.ASCENDING);
        System.out.println("\n###################### ITERATIVE MERGE SORT validateSortAscending #########################");
        System.out.println("\nITERATIVE MERGE SORT ASCENDING SUCCESSFUL? [" + DataValidator.validateSortAscending(original, inputAsc, printResults) + "]\n");
        System.out.println("############################################################################################\n");

        // ITERATIVE MERGE SORT DESCENDING
        Integer[] inputDesc = original.clone();
        sort(inputDesc, SORT_ORDER.DESCENDING);
        System.out.println("\n###################### ITERATIVE MERGE SORT validateSortDescending ########################");
        System.out.println("\nITERATIVE MERGE SORT DESCENDING SUCCESSFUL? [" + DataValidator.validateSortDescending(original, inputDesc, printResults) + "]\n");
        System.out.println("############################################################################################\n");
    }

    private void sort(Integer[] input, SORT_ORDER sortOrder) {
        int i=2;
        for(; i<input.length; i=i*2) {
            for(int j=0; j<input.length; j++) {
                int startingIndex = j*i;

                if(startingIndex >= input.length)
                    break;

                int endingIndex = ((j+1)*i)-1;

                int splitIndex = -1;
                if(endingIndex >= input.length) {
                    splitIndex = (startingIndex + (i/2)) -1;

                    if(splitIndex >= input.length)
                        continue;

                    endingIndex = input.length - 1;
                }

                if((endingIndex - startingIndex) <= 0)
                    break;

                if(splitIndex > 0 )
                    merge(input, startingIndex, endingIndex, splitIndex, sortOrder);
                else
                    merge(input, startingIndex, endingIndex, sortOrder);
            }
        }
        Double splitIndex = Math.floor(i/2);
        merge(input, 0, input.length-1, splitIndex.intValue()-1, sortOrder);
    }

    private Double split(int startingIndex, int endingIndex) {
        return startingIndex + Math.floor((endingIndex - startingIndex)/2);
    }

    private void merge(Integer[] input, int startIndex, int endIndex, SORT_ORDER sortOrder) {
        int splitIndex = split(startIndex, endIndex).intValue();
        merge(input, startIndex, endIndex, splitIndex, sortOrder);
    }

    private void merge(Integer[] input, int startIndex, int endIndex, int splitIndex, SORT_ORDER sortOrder) {
        Integer [] sortedArray1 = Arrays.copyOfRange(input, startIndex, splitIndex+1);
        Integer [] sortedArray2 = Arrays.copyOfRange(input, splitIndex+1, endIndex+1);

        for(int i=startIndex, j=0, k=0; i<=endIndex; i++) {
            if((k>=sortedArray2.length) || ((j<sortedArray1.length) && swap(sortedArray1[j], sortedArray2[k], sortOrder))) {
                input[i] = sortedArray1[j];
                j++;
            } else {
                input[i] = sortedArray2[k];
                k++;
            }
        }
    }

    private boolean swap(final Integer first, final Integer second, SORT_ORDER sortOrder) {
        switch (sortOrder) {
            case ASCENDING:
                return (first < second);
            case DESCENDING:
                return (first > second);
            default:
                throw new RuntimeException("Invalid sort Order");
        }
    }
}

