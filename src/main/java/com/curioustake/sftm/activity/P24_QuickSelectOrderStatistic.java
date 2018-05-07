package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.DataValidator;
import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;
import java.util.Random;

/**
 * Purpose : Select the n order static in a given input
 *
 * Complexity (Time): O(n log(n))
 * */

public class P24_QuickSelectOrderStatistic implements Activity {

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int count = Integer.parseInt(args[1]);
        final int max = Integer.parseInt(args[2]);
        final boolean printResults = Boolean.parseBoolean(args[3]);

        Random random = new Random();
        final int orderStatisticIndex = random.nextInt(count);

        Integer[] original = RandomDataGenerator.getRandomIntegerArray(count, max, printResults);

        if(count <= 1) {
            System.out.println("INPUT SIZE TO SMALL ");
            return;
        }

        Integer[] treatment = original.clone();
        int orderStatistic = getOrderStatistic(treatment, 0, treatment.length-1, orderStatisticIndex);
        System.out.println("\n###################### Quick Select Order Statistic validate ##############################");
        System.out.println("\nQuick Select Order Statistic SUCCESSFUL? [" + DataValidator.validateOrderStatistic(original, orderStatisticIndex, orderStatistic,printResults) + "]\n");
        System.out.println("############################################################################################\n");
    }

    private int getOrderStatistic(Integer[] input, int start, int end, int orderStatisticIndex) {
        if((end - start) == 0)
            return input[start];

        Random random = new Random();
        int randomPivotIndex = start + random.nextInt(end - start);
        swap(input, randomPivotIndex, end);

        int j=start;
        for(int i=start; i< end; i++) {
            if(input[i] < input[end]) {
                swap(input, j, i);
                j++;
            }
        }
        swap(input, j, end);

        if(j == orderStatisticIndex)
            return input[j];
        else if(j> orderStatisticIndex)
            return getOrderStatistic(input, start, j-1, orderStatisticIndex);
        else
            return getOrderStatistic(input, j+1, end, orderStatisticIndex);
    }

    private void swap(Integer[] input, int first, int second) {
        int temp = input[first];
        input[first] = input[second];
        input[second] = temp;
    }

}