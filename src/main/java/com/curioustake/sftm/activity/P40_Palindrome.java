package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;
import java.util.HashMap;

public class P40_Palindrome implements Activity {

    public static final int CHARSET_MAX = 91;
    public static final int CHARSET_MIN = 65;

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int length1 = Integer.parseInt(args[1]);
        final boolean printResult = Boolean.parseBoolean(args[2]);

        if(length1 <= 1) {
            System.out.println("INPUT SIZE TO SMALL ");
            return;
        }

        String str = RandomDataGenerator.getRandomString(length1, CHARSET_MIN, CHARSET_MAX, printResult);

//        String bruteForce = palindromeBruteForce(str, 0, str.length() - 1);
//        System.out.println("\nBruteForce Longest Palindrome");
//        System.out.println(bruteForce);

        String dpMemoized = palindromeDPMemoized(str, 0, str.length() - 1, new HashMap<>());
        System.out.println("\nDP Memoized Longest Palindrome");
        System.out.println(dpMemoized);

        String dpBottomUp = palindromeDPBottomUp(str);
        System.out.println("\nDP Bottom Up Longest Palindrome");
        System.out.println(dpBottomUp);

        if((dpMemoized.compareTo(dpBottomUp) != 0) && (dpMemoized.length() != dpBottomUp.length()))
            throw new RuntimeException("Something got screwed in the DP implementation");
    }

    private String palindromeBruteForce(final String str, int start, int end) {

        String palindrome = "";

        int i=start, j=end;
        while((i <= j) && (str.charAt(i) == str.charAt(j))) {
            ++i;
            --j;
        }

        if(i > j) {
            palindrome = str.substring(start, end+1);
        } else {
            String p1 = palindromeBruteForce(str, start, end - 1);
            String p2 = palindromeBruteForce(str, start + 1, end);

            if(p1.length() > palindrome.length()) {
                palindrome = p1;
            }

            if(p2.length() > palindrome.length()) {
                palindrome = p2;
            }
        }

        return palindrome;
    }

    private String palindromeDPMemoized(final String str, int start, int end, HashMap<String, String> palindromeCache) {

        String palindrome = "";

        int i=start, j=end;
        while((i <= j) && (str.charAt(i) == str.charAt(j))) {
            ++i;
            --j;
        }

        if(i > j) {
            palindrome = str.substring(start, end+1);
        } else {
            String cacheKey1 = String.format("%s-%s", start, end-1);
            if(!palindromeCache.containsKey(cacheKey1))
                palindromeCache.put(cacheKey1, palindromeDPMemoized(str, start, end - 1, palindromeCache));

            String p1 = palindromeCache.get(cacheKey1);

            String cacheKey2 = String.format("%s-%s", start+1, end);
            if(!palindromeCache.containsKey(cacheKey2))
                palindromeCache.put(cacheKey2, palindromeDPMemoized(str, start + 1, end, palindromeCache));
            String p2 = palindromeCache.get(cacheKey2);

            if(p1.length() > palindrome.length()) {
                palindrome = p1;
            }

            if(p2.length() > palindrome.length()) {
                palindrome = p2;
            }
        }

        return palindrome;
    }

    private String palindromeDPBottomUp(final String str) {
        HashMap<String, String> palindromeCache = new HashMap<>();

        for(int i=0; i<str.length(); i++) {
            for(int j=0; (j+i)<str.length(); j++) {
                int start = j;
                int end = j+i;

                while((start<=end) && (str.charAt(start) == str.charAt(end))) {
                    start++;
                    end--;
                }

                String palindrome = "";
                if(start>end) {
                    palindrome = str.substring(j, j+i+1);
                } else {
                    start = j;
                    end = j+i;

                    String cacheKey1 = String.format("%s-%s", start, end-1);
                    String p1 = palindromeCache.get(cacheKey1);

                    String cacheKey2 = String.format("%s-%s", start+1, end);
                    String p2 = palindromeCache.get(cacheKey2);

                    palindrome = (p1.length() > p2.length()) ? p1 : p2;
                }
                palindromeCache.put(String.format("%s-%s", j, j+i), palindrome);
            }
        }
        return palindromeCache.get(String.format("%s-%s", 0, str.length()-1));
    }
}
