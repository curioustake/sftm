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

    public static Integer[] getRandomIntegerArray(final int count, final int min, final int max, final boolean printResults) {
        final Random random = new Random();

        Integer []randomIntegers = random.ints(min, max).limit(count).boxed().toArray(Integer[]::new);

        if(printResults)
            System.out.println("RANDOM INTEGERS : " + Arrays.toString(randomIntegers));

        return randomIntegers;
    }

    public static Integer[] getDistinctRandomIntegerArray(final int count, final int max, final boolean printResults) {
        final Random random = new Random();

        Integer []randomIntegers = random.ints(0, max).distinct().limit(count).boxed().toArray(Integer[]::new);

        if(printResults)
            System.out.println("RANDOM INTEGERS : " + Arrays.toString(randomIntegers));

        return randomIntegers;
    }
}
