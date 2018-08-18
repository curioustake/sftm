package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class P43_LongestMonotonicallyIncreasingSequence implements Activity {

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

        Integer[] lis = longestIncreasingSubsequence(original);

        System.out.println(Arrays.toString(lis));
    }

    private Integer[] longestIncreasingSubsequence(final Integer[] input) {
        Integer[] lisIndex = new Integer[input.length];

        for(int i=0; i<lisIndex.length; i++)
            lisIndex[i] = 1;

        for(int i=0; i<input.length; i++){
            for(int j=i+1; j<input.length; j++) {
                if((input[j] >= input[i]) && (lisIndex[j] < (lisIndex[i] + 1))) {
                    lisIndex[j] = lisIndex[i] + 1;
                }
            }
        }

        List<List<Integer>> lis = new ArrayList<>();

        for(int i=0; i<lisIndex.length; i++) {
            if(lisIndex[i] == 1) {
                List<Integer> l = new ArrayList<>();
                l.add(input[i]);
                lis.add(l);
            } else {
                for(int j=0; j<lis.size(); j++) {

                }
            }
        }

        return lisIndex;
    }
}
