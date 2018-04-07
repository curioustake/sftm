package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.DataValidator;
import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;

/**
 * Purpose : Sort a given input
 *
 * Details: In-place sorting
 *
 * Complexity (Time): O(n ^ 2)
 * */

public class P3_SelectionSort implements Activity {

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

        // SELECTION SORT ASCENDING
        Integer[] inputAsc = original.clone();
        sort(inputAsc, SORT_ORDER.ASCENDING);
        System.out.println("\n###################### SELECTION SORT validateSortAscending ##############################");
        System.out.println("\nSELECTION SORT ASCENDING SUCCESSFUL? [" + DataValidator.validateSortAscending(original, inputAsc, printResults) + "]\n");
        System.out.println("############################################################################################\n");

        // SELECTION SORT DESCENDING
        Integer[] inputDesc = original.clone();
        sort(inputDesc, SORT_ORDER.DESCENDING);
        System.out.println("\n###################### SELECTION SORT validateSortDescending #############################");
        System.out.println("\nSELECTION SORT DESCENDING SUCCESSFUL? [" + DataValidator.validateSortDescending(original, inputDesc, printResults) + "]\n");
        System.out.println("############################################################################################\n");
    }

    private void sort(Integer[] input, SORT_ORDER sortOrder) {
        for(int i=0; i<input.length; i++) {
            int smallestIndex = i;
            for(int j=i+1; j<input.length; j++) {
                if(swap(input[smallestIndex], input[j], sortOrder)) {
                    smallestIndex = j;
                }
            }
            Integer temp = input[smallestIndex];
            input[smallestIndex] = input[i];
            input[i] = temp;
        }
    }

    private boolean swap(final Integer first, final Integer second, SORT_ORDER sortOrder) {
        switch (sortOrder) {
            case ASCENDING:
                return (first > second);
            case DESCENDING:
                return (first < second);
            default:
                throw new RuntimeException("Invalid sort Order");
        }
    }
}
