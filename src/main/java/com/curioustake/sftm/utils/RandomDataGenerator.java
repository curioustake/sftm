package com.curioustake.sftm.utils;

import java.util.Arrays;
import java.util.Random;

public class RandomDataGenerator {

    public static Integer[] getRandomIntegerArray(final int count, final int max, final boolean printResults) {
        return getRandomIntegerArray(count, 0, max, printResults);
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

    public static Integer[][] getRandomIntegerMatrix(final int size, final int max, final boolean printResults) {
        return getRandomIntegerMatrix(size, size, max, printResults);
    }

    public static Integer[][] getRandomIntegerMatrix(final int row, final int column, final int max, final boolean printResults) {
        final Random random = new Random();

        Integer [][]randomMatrix = new Integer[row][];

        for(int i=0; i<row; i++)
            randomMatrix[i] = random.ints(-max, max).limit(column).boxed().toArray(Integer[]::new);

        System.out.println("\n####################### RANDOM MATRIX ###############################");
        if(printResults) {
            for(int i=0; i<row; i++)
                System.out.println(Arrays.toString(randomMatrix[i]));
        }
        System.out.println("#####################################################################\n");

        return randomMatrix;
    }

    public static String getRandomString(final int length, final int charsetMin, final int charsetMax, final boolean printResults) {
        final Random random = new Random();

        Integer []randomIntegers = random.ints(charsetMin, charsetMax).limit(length).boxed().toArray(Integer[]::new);

        String word = "";

        for(int j=0; j<randomIntegers.length; j++)
            word = word + Character.toString((char)randomIntegers[j].intValue());

        if(printResults)
            System.out.println("RANDOM WORD : " + word);

        return word;
    }

    public static String[] getRandomStringArray(final int count, final int length, final int charsetMin, final int charsetMax, final boolean printResults) {
        String[] words = new String[count];

        final Random random = new Random();
        for(int i=0; i<count; i++) {
            int wordLength = random.nextInt(length+1);

            if(wordLength == 0)
                wordLength = 2;

            words[i] = RandomDataGenerator.getRandomString(wordLength, charsetMin, charsetMax, false);
        }

        if(printResults)
            System.out.println("RANDOM WORDS : " + Arrays.toString(words));

        return words;
    }

    public static String[] getRandomStringArray(final int count, final int max, final boolean printResults) {
        final Random random = new Random();

        String []randomWords = new String[count];

        for(int i=0; i<randomWords.length; i++) {
            int wordLength = random.nextInt(max+1);

            if(wordLength == 0)
                wordLength = 1;

            Integer []randomIntegers = random.ints(32, 128).limit(wordLength).boxed().toArray(Integer[]::new);

            String word = "";

            for(int j=0; j<randomIntegers.length; j++)
                word = word + Character.toString((char)randomIntegers[j].intValue());

            randomWords[i] = word;
        }

        if(printResults)
            System.out.println("RANDOM WORDS : " + Arrays.toString(randomWords));

        return randomWords;
    }
}
