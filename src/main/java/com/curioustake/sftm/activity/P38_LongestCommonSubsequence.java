package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class P38_LongestCommonSubsequence implements Activity {

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int length1 = Integer.parseInt(args[1]);
        final int length2 = Integer.parseInt(args[2]);
        final boolean printResult = Boolean.parseBoolean(args[3]);

        if(length1 <= 1 || length2 <= 1) {
            System.out.println("INPUT SIZE TO SMALL ");
            return;
        }

        String sequence1 = RandomDataGenerator.getRandomString(length1, 65, 90, printResult);
        String sequence2 = RandomDataGenerator.getRandomString(length2, 65, 90, printResult);

//        String bruteForce = lcsBruteForce(sequence1, sequence1.length(), sequence2, sequence2.length());
//        System.out.println("\nBruteForce LCS");
//        System.out.println(bruteForce);

        String dpMemoized = lcsDPMemoized(sequence1, sequence1.length(), sequence2, sequence2.length(), new HashMap<>());
        System.out.println("\nDP Memoized LCS");
        System.out.println(dpMemoized);

        String dpBottmUp = lcsDPBottomUp(sequence1, sequence2);
        System.out.println("\nDP Bottom Up LCS");
        System.out.println(dpBottmUp);

        if(dpBottmUp.compareTo(dpMemoized) != 0)
            throw new RuntimeException("Something got screwed in the DP implementation");
    }

    private String lcsBruteForce(final String sequence1, final int length1, final String sequence2, final int length2) {

        String lcs = "";

        if(length1 == 0 || length2 == 0)
            return lcs;

        if(sequence1.charAt(length1-1) == sequence2.charAt(length2-1)) {
            lcs = lcsBruteForce(sequence1, length1 - 1, sequence2, length2 -1) + sequence1.charAt(length1-1);
        } else {
            String lcs1 = lcsBruteForce(sequence1, length1 - 1, sequence2, length2);
            String lcs2 = lcsBruteForce(sequence1, length1, sequence2, length2 - 1);

            lcs = lcs1.length() > lcs2.length() ? lcs1 : lcs2;
        }

        return lcs;
    }

    private String lcsDPMemoized(final String sequence1, final int length1, final String sequence2, final int length2, final Map<String, String> lcsCache) {

        String lcs = "";

        if(length1 == 0 || length2 == 0)
            return lcs;

        if(sequence1.charAt(length1-1) == sequence2.charAt(length2-1)) {
            final String lcsCacheKey = (length1-1) + "-" + (length2-1);
            if(!lcsCache.containsKey(lcsCacheKey))
                lcsCache.put(lcsCacheKey, lcsDPMemoized(sequence1, length1 - 1, sequence2, length2 -1, lcsCache));

            lcs =  lcsCache.get(lcsCacheKey)+ sequence1.charAt(length1-1);
        } else {
            final String lcsCacheKey1 = (length1 - 1) + "-" + length2;
            if(!lcsCache.containsKey(lcsCacheKey1))
                lcsCache.put(lcsCacheKey1, lcsDPMemoized(sequence1, length1 - 1, sequence2, length2, lcsCache));
            String lcs1 = lcsCache.get(lcsCacheKey1);

            final String lcsCacheKey2 = length1 + "-" + (length2 - 1);
            if(!lcsCache.containsKey(lcsCacheKey2))
                lcsCache.put(lcsCacheKey2, lcsDPMemoized(sequence1, length1, sequence2, length2 - 1, lcsCache));
            String lcs2 = lcsCache.get(lcsCacheKey2);

            lcs = lcs1.length() > lcs2.length() ? lcs1 : lcs2;
        }

        return lcs;
    }

    private String lcsDPBottomUp(final String sequence1, final String sequence2) {

        final Map<String, String> lcsCache = new HashMap<>();

        for(int i=0; i<sequence1.length(); i++) {
            for(int j=0; j<sequence2.length(); j++) {
                String lcs = "";
                if(i==0 || j==0) {
                    if(sequence1.charAt(i) == sequence2.charAt(j)) {
                        lcs = lcs + sequence1.charAt(i);
                    } else {
                        if(i != 0)
                            lcs = lcsCache.get((i-1) + "-" + j);
                        else if(j != 0)
                            lcs = lcsCache.get(i + "-" + (j-1));
                    }
                } else {
                    if(sequence1.charAt(i) == sequence2.charAt(j)) {
                        String lcsCacheKey = (i - 1) + "-" + (j - 1);
                        lcs = lcsCache.get(lcsCacheKey) + sequence1.charAt(i);
                    } else {
                        String lcsCacheKey1 = (i - 1) + "-" + (j);
                        String lcs1 = lcsCache.get(lcsCacheKey1);

                        String lcsCacheKey2 = (i) + "-" + (j-1);
                        String lcs2 = lcsCache.get(lcsCacheKey2);

                        lcs = lcs1.length() > lcs2.length() ? lcs1 : lcs2;
                    }
                }
                lcsCache.put(i + "-" + j, lcs);
            }
        }

        return lcsCache.get((sequence1.length()-1) + "-" + (sequence2.length() -1 ));
    }
}
