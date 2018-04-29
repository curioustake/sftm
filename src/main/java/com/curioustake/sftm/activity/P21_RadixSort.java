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

public class P21_RadixSort implements Activity {

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

        // RADIX SORT ASCENDING
        Integer[] inputAsc = original.clone();
        Integer[] outAsc = sort(inputAsc, SORT_ORDER.ASCENDING);
        System.out.println("\n###################### RADIX SORT validateSortAscending ##############################");
        System.out.println("\nRADIX SORT ASCENDING SUCCESSFUL? [" + DataValidator.validateSortAscending(original, outAsc, printResults) + "]\n");
        System.out.println("############################################################################################\n");

        // RADIX SORT DESCENDING
        Integer[] inputDesc = original.clone();
        Integer[] outDesc = sort(inputDesc, SORT_ORDER.DESCENDING);
        System.out.println("\n###################### RADIX SORT validateSortDescending #############################");
        System.out.println("\nRADIX SORT DESCENDING SUCCESSFUL? [" + DataValidator.validateSortDescending(original, outDesc, printResults) + "]\n");
        System.out.println("############################################################################################\n");
    }

    private Integer[] sort(Integer[] input, SORT_ORDER sortOrder) {
        boolean isMostSignificantDigit = false;
        int comparisonDigit = 1;

        while (!isMostSignificantDigit) {
            Integer[] count = getCount();

            isMostSignificantDigit = true;
            for(int i=0; i<input.length; i++) {
                int remainder = input[i]%(comparisonDigit*10);
                int digit = remainder / (comparisonDigit);

                if(remainder < input[i])
                    isMostSignificantDigit = false;

                count[digit]++;
            }

            for(int i=1; i<count.length; i++) {
                count[i] = count[i-1] + count[i];
            }

            input = order(input, count, comparisonDigit, sortOrder);

            comparisonDigit*=10;
        }

        return input;
    }

    private Integer[] getCount() {
        Integer[] count =  new Integer[10];
        for(int i=0; i<count.length;i++) count[i] = 0;
        return count;
    }

    private Integer [] order(final Integer[] input, final Integer []count, int comparisonDigit, SORT_ORDER sortOrder) {
        Integer []output = new Integer[input.length];
        switch (sortOrder) {
            case ASCENDING:
                for(int i=input.length-1; i>=0; i--) {
                    int remainder = input[i]%(comparisonDigit*10);
                    int digit = remainder / (comparisonDigit);

                    int index = count[digit] - 1;
                    output[index] = input[i];
                    count[digit]--;
                }
                return output;
            case DESCENDING:
                int lastIndex = input.length-1;
                for(int i=0; i<input.length; i++) {
                    int remainder = input[i]%(comparisonDigit*10);
                    int digit = remainder / (comparisonDigit);

                    int index = count[digit] - 1;
                    output[lastIndex - index] = input[i];
                    count[digit]--;
                }
                return output;
            default:
                throw new RuntimeException("Invalid sort Order");
        }
    }
}

