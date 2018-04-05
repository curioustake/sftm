package com.curioustake.sftm.utils;

import java.util.Arrays;
import java.util.Random;

public class RandomDataGenerator {

    public static Integer[] getRandomIntegerArray(final int count, final int max, final boolean printResults) {
        Integer[] randomIntegers = new Integer[count];

        final Random random = new Random();

        for(int i=0; i<count; i++)
            randomIntegers[i] = random.nextInt(max);

        if(printResults)
            System.out.println("RANDOM INTEGERS : " + Arrays.toString(randomIntegers));

        return randomIntegers;
    }
}
