package com.curioustake.sftm.utils;

import java.util.Arrays;
import java.util.Collections;

public class DataValidator {

    public static boolean validateSortAscending(final Integer[] original, final Integer[] sorted, final boolean printResult){
        final Integer[] systemSorted = original.clone();

        Arrays.parallelSort(systemSorted);

        if(printResult) {
            System.out.println("CONTROL  \t: " + Arrays.toString(systemSorted));
            System.out.println("TREATMENT\t: " + Arrays.toString(sorted));
        }

        return Arrays.equals(systemSorted, sorted);
    }

    public static boolean validateSortDescending(final Integer[] original, final Integer[] sorted, final boolean printResult){
        final Integer[] systemSorted = original.clone();

        Arrays.parallelSort(systemSorted, Collections.reverseOrder());

        if(printResult) {
            System.out.println("CONTROL  \t: " + Arrays.toString(systemSorted));
            System.out.println("TREATMENT\t: " + Arrays.toString(sorted));
        }

        return Arrays.equals(systemSorted, sorted);
    }

    public static boolean validateSortAscending(final String[] original, final String[] sorted, final boolean printResult){
        final String[] systemSorted = original.clone();

        Arrays.parallelSort(systemSorted);

        if(printResult) {
            System.out.println("CONTROL  \t: " + Arrays.toString(systemSorted));
            System.out.println("TREATMENT\t: " + Arrays.toString(sorted));
        }

        return Arrays.equals(systemSorted, sorted);
    }

    public static boolean validateSortDescending(final String[] original, final String[] sorted, final boolean printResult){
        final String[] systemSorted = original.clone();

        Arrays.parallelSort(systemSorted, Collections.reverseOrder());

        if(printResult) {
            System.out.println("CONTROL  \t: " + Arrays.toString(systemSorted));
            System.out.println("TREATMENT\t: " + Arrays.toString(sorted));
        }

        return Arrays.equals(systemSorted, sorted);
    }

    public static boolean validateOrderStatistic(final Integer[] original, final int orderStatistic, final int treatment, final boolean printResult){
        final Integer[] systemSorted = original.clone();

        Arrays.parallelSort(systemSorted);

        if(printResult) {
            System.out.println("CONTROL  ["+ orderStatistic + "]\t: " + systemSorted[orderStatistic]);
            System.out.println("TREATMENT["+ orderStatistic + "]\t: " + treatment);
        }

        return systemSorted[orderStatistic].equals(treatment);
    }
}
