package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;

/**
 * Purpose : (Binary) Search a term in a given input
 *
 * Details: First sort the input
 *
 * Complexity (Time): O(n log(n)) => O(log(n)) ignoring the sorting cost
 * */

public class P7_IterativeBinarySearch implements Activity {

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int count = Integer.parseInt(args[1]);
        final int max = Integer.parseInt(args[2]);
        final boolean printResults = Boolean.parseBoolean(args[3]);

        final int searchTerm = Integer.parseInt(args[4]);

        Integer[] original = RandomDataGenerator.getRandomIntegerArray(count, max, printResults);

        Arrays.sort(original);

        boolean isPresent = search(original,searchTerm);

        System.out.println(Arrays.toString(original));
        System.out.println("Search Term " + searchTerm + " is present? [" + isPresent + "]");
    }

    private boolean search(Integer[] input, int searchTerm) {

        int start = 0;
        int end = input.length - 1;

        while(true) {
            if ((end - start) <= 0) {
                return input[start] == searchTerm;
            }

            Double splitIndexDouble = start + Math.floor((end - start) / 2);
            int splitIndex = splitIndexDouble.intValue();

            if (input[splitIndex] == searchTerm)
                return true;
            else if (input[splitIndex] > searchTerm)
                end = splitIndex - 1;
            else
                start = splitIndex + 1;
        }
    }
}
