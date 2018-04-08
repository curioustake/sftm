package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;

public class P8_TwoNumbersEqualGivenSum implements Activity {

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int count = Integer.parseInt(args[1]);
        final int max = Integer.parseInt(args[2]);
        final boolean printResults = Boolean.parseBoolean(args[3]);

        final int search = Integer.parseInt(args[4]);

        Integer[] original = RandomDataGenerator.getRandomIntegerArray(count, max, printResults);

        Arrays.sort(original);

        searchSum(original, search);
    }

    private void searchSum(Integer[] input, int searchSum) {
        for(int i=input.length-1; i>0; i--) {
            int searchTerm = searchSum - input[i];
            if((searchSum >= 0) && search(input, searchTerm, 0, i-1)) {
                System.out.println("Search Sum " + searchSum + " is made up of [" + input[i] + " + " + searchTerm + "]");
            }
        }
    }

    private boolean search(Integer[] input, int searchTerm, int start, int end) {
        if((end - start) <= 0) {
            return input[start] == searchTerm;
        }

        Double splitIndexDouble = start + Math.floor((end - start)/2);
        int splitIndex = splitIndexDouble.intValue();

        if(input[splitIndex] == searchTerm)
            return true;
        else if(input[splitIndex] > searchTerm)
            return search(input, searchTerm, start, splitIndex-1);
        else
            return search(input, searchTerm, splitIndex+1, end);
    }
}
