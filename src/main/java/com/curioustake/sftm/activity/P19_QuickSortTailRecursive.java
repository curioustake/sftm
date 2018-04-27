package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.DataValidator;
import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;
import java.util.Random;

/**
 * Purpose : Sort a given input
 *
 * Details: In place, dive and conquer sorting
 *
 * Complexity (Time): O(n * log(n))
 * */

public class P19_QuickSortTailRecursive implements Activity {

    enum SORT_ORDER { ASCENDING, DESCENDING}

    Random random = new Random();

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

        // QUICK SORT TAIL RECURSIVE ASCENDING
        Integer[] inputAsc = original.clone();
        sort(inputAsc, 0, inputAsc.length-1, SORT_ORDER.ASCENDING);
        System.out.println("\n###################### QUICK SORT TAIL RECURSIVE validateSortAscending ##############################");
        System.out.println("\nQUICK SORT TAIL RECURSIVE ASCENDING SUCCESSFUL? [" + DataValidator.validateSortAscending(original, inputAsc, printResults) + "]\n");
        System.out.println("############################################################################################\n");

        // QUICK SORT TAIL RECURSIVE DESCENDING
        Integer[] inputDesc = original.clone();
        sort(inputDesc, 0, inputAsc.length-1, SORT_ORDER.DESCENDING);
        System.out.println("\n###################### QUICK SORT TAIL RECURSIVE validateSortDescending #############################");
        System.out.println("\nQUICK SORT TAIL RECURSIVE DESCENDING SUCCESSFUL? [" + DataValidator.validateSortDescending(original, inputDesc, printResults) + "]\n");
        System.out.println("############################################################################################\n");
    }

    private void sort(Integer[] input, int startIndex, int endIndex, SORT_ORDER sortOrder) {

        while (true) {
            if((endIndex - startIndex) <= 0) {
                return;
            }

            int randomPivotIndex = startIndex + random.nextInt(endIndex-startIndex);
            swap(input, randomPivotIndex, endIndex);

            int pivotIndex = startIndex;

            for(int i=pivotIndex; i<endIndex; i++) {
                if(order(input[i], input[endIndex], sortOrder)) {
                    swap(input, pivotIndex, i);
                    pivotIndex++;
                }
            }

            swap(input, pivotIndex, endIndex);

            sort(input, startIndex, pivotIndex-1, sortOrder);
            startIndex = pivotIndex + 1;
        }
    }

    private void swap(Integer[] input, final Integer first, final Integer second) {
        Integer temp =  input[first];
        input[first] = input[second];
        input[second] = temp;
    }

    private boolean order(final Integer first, final Integer second, SORT_ORDER sortOrder) {
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