package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.DataValidator;
import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;

/**
 * Purpose : Sort a given input
 *
 * Details: Not an In-place sorting
 *
 * Complexity (Time): O(n * log(n))
 * */

public class P4_MergeSort implements Activity {

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

        // MERGE SORT ASCENDING
        Integer[] inputAsc = original.clone();
        sort(inputAsc, 0, inputAsc.length-1, SORT_ORDER.ASCENDING);
        System.out.println("\n###################### MERGE SORT validateSortAscending ##############################");
        System.out.println("\nMERGE SORT ASCENDING SUCCESSFUL? [" + DataValidator.validateSortAscending(original, inputAsc, printResults) + "]\n");
        System.out.println("############################################################################################\n");

        // MERGE SORT DESCENDING
        Integer[] inputDesc = original.clone();
        sort(inputDesc, 0, inputAsc.length-1, SORT_ORDER.DESCENDING);
        System.out.println("\n###################### MERGE SORT validateSortDescending #############################");
        System.out.println("\nMERGE SORT DESCENDING SUCCESSFUL? [" + DataValidator.validateSortDescending(original, inputDesc, printResults) + "]\n");
        System.out.println("############################################################################################\n");
    }

    private void sort(Integer[] input, int startIndex, int endIndex, SORT_ORDER sortOrder) {
        if((endIndex - startIndex) <= 0) {
            return;
        }

        int splitIndex = split(startIndex, endIndex).intValue();

        sort(input, startIndex, splitIndex, sortOrder);
        sort(input, splitIndex+1, endIndex, sortOrder);

        merge(input, startIndex, endIndex, sortOrder);
    }

    private Double split(int startingIndex, int endingIndex) {
        return startingIndex + Math.floor((endingIndex - startingIndex)/2);
    }

    private void merge(Integer[] input, int startIndex, int endIndex, SORT_ORDER sortOrder) {
        int splitIndex = split(startIndex, endIndex).intValue();

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
