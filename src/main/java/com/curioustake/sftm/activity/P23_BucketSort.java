package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.DataValidator;
import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Purpose : Sort a given input
 *
 * Details: Non-Comparison sort "MOSTLY"
 *
 * Complexity (Time): O(n log (n))
 * */

public class P23_BucketSort implements Activity {

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

        // BUCKET SORT ASCENDING
        Integer[] inputAsc = original.clone();
        Integer[] outputAsc = sort(inputAsc, SORT_ORDER.ASCENDING);
        System.out.println("\n###################### BUCKET SORT validateSortAscending ##############################");
        System.out.println("\nBUCKET SORT ASCENDING SUCCESSFUL? [" + DataValidator.validateSortAscending(original, outputAsc, printResults) + "]\n");
        System.out.println("############################################################################################\n");

        // BUCKET SORT DESCENDING
        Integer[] inputDesc = original.clone();
        Integer[] outputDesc = sort(inputDesc, SORT_ORDER.DESCENDING);
        System.out.println("\n###################### BUCKET SORT validateSortDescending #############################");
        System.out.println("\nBUCKET SORT DESCENDING SUCCESSFUL? [" + DataValidator.validateSortDescending(original, outputDesc, printResults) + "]\n");
        System.out.println("############################################################################################\n");
    }

    private Integer[] sort(Integer[] input, SORT_ORDER sortOrder) {

        if(input.length <= 1)
            return input;

        final Integer max = Arrays.stream(input).max(Integer::compareTo).get();

        final int maxFactor = getMaxFactor(max);

        final List<List<Integer>> buckets = new ArrayList<>();

        final int maxBucketSize = max/maxFactor;

        for(int i=0; i<=maxBucketSize; i++)
            buckets.add(new ArrayList<>());

        for(int i=0; i<input.length; i++) {
            int bucketId = input[i]/maxFactor;
            buckets.get(bucketId).add(input[i]);
        }

        for(int i=0; i<buckets.size(); i++) {
            if(input.length == buckets.get(i).size()) {
                if(sortOrder.equals(SORT_ORDER.ASCENDING))
                    Arrays.sort(input);
                else
                    Arrays.sort(input, Comparator.reverseOrder());
                return input;
            }

            buckets.set(i, Arrays.asList(sort(buckets.get(i).stream().toArray(Integer[]::new), sortOrder)));
        }
        return order(buckets, sortOrder);
    }

    private int getMaxFactor(Integer max) {
        int maxFactor = 10;
        while((max/maxFactor) > 10)
            maxFactor*=10;
        return maxFactor;
    }

    private Integer[] order(final List<List<Integer>> sortedBuckets , SORT_ORDER sortOrder) {
        List<Integer> output = new ArrayList<>();
        switch (sortOrder) {
            case ASCENDING:
                sortedBuckets.stream().forEach(b -> output.addAll(b));
                break;
            case DESCENDING:
                int index = 0;
                for(int i=sortedBuckets.size()-1; i>=0; i--) {
                    output.addAll(index, sortedBuckets.get(i));
                    index = index + sortedBuckets.get(i).size();
                }
                break;
            default:
                throw new RuntimeException("Invalid sort Order");
        }
        return output.toArray(new Integer[output.size()]);
    }
}

