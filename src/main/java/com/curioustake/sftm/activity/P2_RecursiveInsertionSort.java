package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.DataValidator;
import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;

public class P2_RecursiveInsertionSort implements Activity {

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

        // RECURSIVE INSERTION SORT ASCENDING
        Integer[] inputAsc = original.clone();
        sort(inputAsc, inputAsc.length, SORT_ORDER.ASCENDING);
        System.out.println("\n###################### RECURSIVE INSERTION SORT validateSortAscending ##############################");
        System.out.println("\nRECURSIVE INSERTION SORT ASCENDING SUCCESSFUL? [" + DataValidator.validateSortAscending(original, inputAsc, printResults) + "]\n");
        System.out.println("############################################################################################\n");

        // RECURSIVE INSERTION SORT DESCENDING
        Integer[] inputDesc = original.clone();
        sort(inputDesc, inputDesc.length, SORT_ORDER.DESCENDING);
        System.out.println("\n###################### RECURSIVE INSERTION SORT validateSortDescending #############################");
        System.out.println("\nRECURSIVE INSERTION SORT DESCENDING SUCCESSFUL? [" + DataValidator.validateSortDescending(original, inputDesc, printResults) + "]\n");
        System.out.println("############################################################################################\n");
    }

    private void sort(Integer[] input, final int size, SORT_ORDER sortOrder) {

        if(size == 1)
            return;

        sort(input, size - 1, sortOrder);

        Integer temp = input[size-1];
        int j=size-2;
        for(; j>=0; j--) {
            if(swap(input[j], temp, sortOrder)) {
                input[j+1] = input[j];
            } else {
                break;
            }
        }
        input[j+1] = temp;
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
