package com.curioustake.sftm.ctci;

import com.curioustake.sftm.activity.Activity;

import java.util.Arrays;

public class P2_MatrixRightTurn implements Activity {

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        if(args.length <= 1) {
            System.out.println("Incorrect number of argument");
            return;
        }

        final int size = Integer.parseInt(args[1]);

        int [][] randomMatrix = new int[size][size];

        int counter = 0;

        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++)
                randomMatrix[i][j] = counter++;

        turnRight(randomMatrix);

        for(int i=0; i<size; i++)
            System.out.println(Arrays.toString(randomMatrix[i]));
    }

    public void turnRight(int [][] input) {
        int size = input[0].length;
        for(int i=0; i<size/2; i++) {
            for(int j=i; j<size-1-i; j++) {
                int temp = input[i][j];
                input[i][j] = input[size - 1 - j][i];
                input[size - 1 - j][i] = input[size - 1 - i][size - 1 - j];
                input[size - 1 - i][size - 1 - j] = input[j][size - 1 - i];
                input[j][size - 1 - i] = temp;
            }
        }
    }
}
