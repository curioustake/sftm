package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.DataValidator;
import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;
import java.util.Collections;

/**
 * Purpose : Sort a given input
 *
 * Details: Stable sort, non comparison based sort
 *
 * Complexity (Time): O(n)
 * */

public class P22_RadixSortStrings implements Activity {

    private static final int CHARACTER_SET_SIZE = 127 - 32 + 1;
    private static final int CHARACTER_SET_START = 32;

    private enum SORT_ORDER { ASCENDING, DESCENDING}

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int count = Integer.parseInt(args[1]);
        final int max = Integer.parseInt(args[2]);
        final boolean printResults = Boolean.parseBoolean(args[3]);

        String[] original = RandomDataGenerator.getRandomStringArray(count, max, printResults);

        if(count <= 1) {
            System.out.println("INPUT SIZE TO SMALL ");
            return;
        }

        // RADIX STRING SORT ASCENDING
        String[] inputAsc = original.clone();
        String[] outputAsc = sort(inputAsc, SORT_ORDER.ASCENDING);
        System.out.println("\n###################### RADIX STRING SORT validateSortAscending ##############################");
        System.out.println("\nRADIX STRING SORT ASCENDING SUCCESSFUL? [" + DataValidator.validateSortAscending(original, outputAsc, printResults) + "]\n");
        System.out.println("############################################################################################\n");

        // RADIX STRING SORT DESCENDING
        String[] inputDesc = original.clone();
        String[] outputDesc = sort(inputDesc, SORT_ORDER.DESCENDING);
        System.out.println("\n###################### RADIX STRING SORT validateSortDescending #############################");
        System.out.println("\nRADIX STRING SORT DESCENDING SUCCESSFUL? [" + DataValidator.validateSortDescending(original, outputDesc, printResults) + "]\n");
        System.out.println("############################################################################################\n");
    }

    private String[] sort(String[] input, SORT_ORDER sortOrder) {
        final int maxWordLength = getMaxWordLength(input);

        for(int j=maxWordLength; j>0; j--) {
            Integer[] count = getCount();

            for(int i=0; i<input.length; i++) {
                if(input[i].length() >= j) {
                    int charVal = input[i].charAt(j-1);
                    count[charVal- CHARACTER_SET_START]++;
                } else {
                    count[CHARACTER_SET_SIZE-1]++;
                }
            }

            for(int i=1; i<count.length; i++) {
                count[i] = count[i-1] + count[i];
            }

            input = order(input, count, j, sortOrder);
        }

        return input;
    }

    private int getMaxWordLength(String[] input) {
        int maxWordLength = 0;
        for(int i=0; i<input.length; i++)
            if(maxWordLength < input[i].length())
                maxWordLength = input[i].length();
        return maxWordLength;
    }

    private Integer[] getCount() {
        Integer[] count =  new Integer[CHARACTER_SET_SIZE];
        for(int i=0; i<count.length;i++) count[i] = 0;
        return count;
    }

    private String [] order(final String[] input, final Integer []count, int comparisonChar, SORT_ORDER sortOrder) {
        String []output = new String[input.length];

        switch (sortOrder) {
            case ASCENDING:
                for(int i=input.length-1; i>=0; i--) {
                    int countIndex = CHARACTER_SET_SIZE-1;
                    if(input[i].length() >= comparisonChar) {
                        int charVal = input[i].charAt(comparisonChar-1);
                         countIndex = charVal - CHARACTER_SET_START;
                    }

                    int index = count[countIndex] - 1;
                    output[index] = input[i];
                    count[countIndex]--;
                }
                return output;
            case DESCENDING:
                int lastIndex = input.length-1;
                for(int i=0; i<input.length; i++) {
                    int countIndex = CHARACTER_SET_SIZE-1;
                    if(input[i].length() >= comparisonChar) {
                        int charVal = input[i].charAt(comparisonChar-1);
                        countIndex = charVal - CHARACTER_SET_START;
                    }

                    int index = count[countIndex] - 1;
                    output[lastIndex - index] = input[i];
                    count[countIndex]--;
                }
                return output;
            default:
                throw new RuntimeException("Invalid sort Order");
        }
    }
}
