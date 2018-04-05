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

public class P1_InsertionSort implements Activity {

    enum SORT_ORDER { ASCENDING, DESCENDING}

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int count = Integer.parseInt(args[1]);
        final int max = Integer.parseInt(args[2]);
        final boolean printResults = Boolean.parseBoolean(args[3]);

        Integer[] original = RandomDataGenerator.getRandomIntegerArray(count, max, printResults);

        if(count <= 1) {
            System.out.println("INPUT SIZE TO SMALL ");
        }

        // INSERTION SORT ASCENDING
        Integer[] inputAsc = original.clone();
        sort(inputAsc, SORT_ORDER.ASCENDING);
        System.out.println("\n###################### INSERTION SORT validateSortAscending ##############################");
        System.out.println("\nINSERTION SORT ASCENDING SUCCESSFUL? [" + DataValidator.validateSortAscending(original, inputAsc, printResults) + "]\n");
        System.out.println("############################################################################################\n");

        // INSERTION SORT DESCENDING
        Integer[] inputDesc = original.clone();
        sort(inputDesc, SORT_ORDER.DESCENDING);
        System.out.println("\n###################### INSERTION SORT validateSortDescending #############################");
        System.out.println("\nINSERTION SORT DESCENDING SUCCESSFUL? [" + DataValidator.validateSortDescending(original, inputDesc, printResults) + "]\n");
        System.out.println("############################################################################################\n");
    }

    private void sort(Integer[] input, SORT_ORDER sortOrder) {
        for(int i=1; i<input.length; i++) {
            Integer temp = input[i];
            int j=i-1;
            for(; j>=0; j--) {
                if(swap(input[j], temp, sortOrder)) {
                    input[j+1] = input[j];
                } else {
                    break;
                }
            }
            input[j+1] = temp;
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
