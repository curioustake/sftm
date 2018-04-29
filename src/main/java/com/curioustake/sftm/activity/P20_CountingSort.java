package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.DataValidator;
import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;

/**
 * Purpose : Sort a given input
 *
 * Details: Stable sort, non comparison based sort
 *
 * Complexity (Time): O(n)
 * */

public class P20_CountingSort implements Activity {

    private enum SORT_ORDER { ASCENDING, DESCENDING}

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

        // COUNTING SORT ASCENDING
        Integer[] inputAsc = original.clone();
        Integer[] outAsc = sort(inputAsc, SORT_ORDER.ASCENDING);
        System.out.println("\n###################### COUNTING SORT validateSortAscending ##############################");
        System.out.println("\nCOUNTING SORT ASCENDING SUCCESSFUL? [" + DataValidator.validateSortAscending(original, outAsc, printResults) + "]\n");
        System.out.println("############################################################################################\n");

        // COUNTING SORT DESCENDING
        Integer[] inputDesc = original.clone();
        Integer[] outDesc = sort(inputDesc, SORT_ORDER.DESCENDING);
        System.out.println("\n###################### COUNTING SORT validateSortDescending #############################");
        System.out.println("\nCOUNTING SORT DESCENDING SUCCESSFUL? [" + DataValidator.validateSortDescending(original, outDesc, printResults) + "]\n");
        System.out.println("############################################################################################\n");
    }

    private Integer[] sort(Integer[] input, SORT_ORDER sortOrder) {
        Integer[] count = getCount(input);

        for(int i=0; i<input.length; i++) {
            count[input[i]] = count[input[i]] == null ? 1 : (count[input[i]]+1);
        }

        count[0] = (count[0] == null) ? 0 : count[0];

        for(int i=1; i<count.length; i++) {
            count[i] = (count[i] == null) ? count[i-1] : count[i-1] + count[i];
        }

        return order(input, count, sortOrder);
    }

    private Integer[] getCount(Integer[] input) {
        int max = -1;
        for(int i=0; i<input.length; i++) {
            if(input[i] > max)
                max = input[i];
        }
        return new Integer[max+1];
    }

    private Integer [] order(final Integer[] input, final Integer []count, SORT_ORDER sortOrder) {
        Integer []output = new Integer[input.length];
        switch (sortOrder) {
            case ASCENDING:
                for(int i=input.length-1; i>=0; i--) {
                    int index = count[input[i]] - 1;
                    output[index] = input[i];
                    count[input[i]]--;
                }
                return output;
            case DESCENDING:
                int lastIndex = input.length-1;
                for(int i=0; i<input.length; i++) {
                    int index = count[input[i]] - 1;
                    output[lastIndex - index] = input[i];
                    count[input[i]]--;
                }
                return output;
            default:
                throw new RuntimeException("Invalid sort Order");
        }
    }
}
