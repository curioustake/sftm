package com.curioustake.sftm.ctci;

import com.curioustake.sftm.activity.Activity;

import java.util.Arrays;

public class P1_UniqueCharString implements Activity {

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        if(args.length <= 1) {
            System.out.println("Incorrect number of argument");
            return;
        }

        String inputString = "";

        for(int i=1; i<args.length; i++)
            inputString = inputString + " " + args[i];

        System.out.println("Does string " + inputString + " have unique characters? [" + isUnique(inputString) + "]");
    }

    public boolean isUnique(String input) {
        char[] tempArray = input.toCharArray();
        Arrays.sort(tempArray);
        String sortedString = new String(tempArray);

        System.out.println("Sorted String [" + sortedString + "]");

        for(int i=0; i < sortedString.length()-1; i++) {
            char current = sortedString.charAt(i);
            char next = sortedString.charAt(i+1);

            if((current != 32) &&
                    (current == next) || (current == next + 32) || (current == next - 32))
                return false;
        }

        return true;
    }
}
