package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;
import java.util.Random;

public class P14_MatrixMultiplicationBruteForce implements Activity {
    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int size = Integer.parseInt(args[1]);
        final int max = Integer.parseInt(args[2]);
        final boolean printResults = Boolean.parseBoolean(args[3]);

        final Random random = new Random();
        int randomDimension = 0;

        while(randomDimension<=0)
            randomDimension = random.nextInt(max);

        Integer[][] input1 = RandomDataGenerator.getRandomIntegerMatrix(size, randomDimension, max, printResults);
        Integer[][] input2 = RandomDataGenerator.getRandomIntegerMatrix(randomDimension, size, max, printResults);

        Integer[][] product = getMatrixProduct(input1, input2);

        for(int i=0; i<size; i++)
            System.out.println(Arrays.toString(product[i]));
    }

    public static Integer[][] getMatrixProduct(Integer[][] input1, Integer[][] input2) {
        Integer[][] product = new Integer[input1.length][input2[0].length];

        for(int i=0; i<input1.length; i++) {
            for(int j=0; j<input2[0].length; j++) {
                product[i][j] = 0;
                for(int k=0; k<input2.length; k++) {
                    product[i][j]+=(input1[i][k] * input2[k][j]);
                }
            }
        }
        return product;
    }
}
