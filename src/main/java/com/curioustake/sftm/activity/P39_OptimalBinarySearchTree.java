package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class P39_OptimalBinarySearchTree implements Activity {

    private static final int CHARSET_MAX = 123;
    private static final int CHARSET_MIN = 97;
    private static final int WORD_LENGTH = 10;

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int count = Integer.parseInt(args[1]);
        final boolean printResult = Boolean.parseBoolean(args[2]);

        if(count <= 1 ) {
            System.out.println("INPUT SIZE TO SMALL ");
            return;
        }

        String[] words = RandomDataGenerator.getRandomStringArray(count, WORD_LENGTH, CHARSET_MIN, CHARSET_MAX, printResult);

        Arrays.sort(words);
        Integer[] wordFrequency = RandomDataGenerator.getRandomIntegerArray(count, 1, count, printResult);

        Integer sum = Arrays.stream(wordFrequency).mapToInt(Integer::intValue).sum();

        Double[] wordProbability = new Double[count];

        for(int i=0; i<count; i++) {
            wordProbability[i] = (double) wordFrequency[i]/sum;
        }

        if(printResult) {
            System.out.println(Arrays.toString(words));
            System.out.println(Arrays.toString(wordProbability));
        }

//        Node bruteForce = optimalBSTBruteForce(words, wordProbability, 0, count-1, 1);
//        System.out.println("\nBruteForce Optimal BST");
//        System.out.println("OBST Root Node [" + "value_='" + bruteForce.value_ + '\'' +
//                ", depth_=" + bruteForce.depth_ +
//                ", probability_=" + bruteForce.probability_ +
//                ", searchCost_=" + bruteForce.searchCost_ + "]");
//        System.out.println(bruteForce);

        Node dpMemoized = optimalBSTDPMemoized(words, wordProbability, 0, count-1, 1, new HashMap<>());
        System.out.println("\nDP Memoized Optimal BST");
        System.out.println("OBST Root Node [" + "value_='" + dpMemoized.value_ + '\'' +
                ", depth_=" + dpMemoized.depth_ +
                ", probability_=" + dpMemoized.probability_ +
                ", searchCost_=" + dpMemoized.searchCost_ + "]");
        if(printResult) System.out.println(dpMemoized);

        Node dpBottomUp = optimalBSTDPBottomUp(words, wordProbability);
        System.out.println("\nDP Bottom Up Optimal BST");
        System.out.println("OBST Root Node [" + "value_='" + dpBottomUp.value_ + '\'' +
                ", depth_=" + dpBottomUp.depth_ +
                ", probability_=" + dpBottomUp.probability_ +
                ", searchCost_=" + dpBottomUp.searchCost_ + "]");
        if(printResult) System.out.println(dpBottomUp);

        if((dpMemoized.value_.compareTo(dpBottomUp.value_) != 0) &&
                dpMemoized.searchCost_ != dpBottomUp.searchCost_)
            throw new RuntimeException("Something got screwed in the DP implementation");
    }

    private Node optimalBSTBruteForce(final String[] words, final Double[] wordProbability, int start, int end, int depth) {
        Node node = null;
        if(start == end) {
            node = new Node();
            node.value_ = words[start];
            node.depth_ = depth;
            node.probability_ = wordProbability[start];
            node.searchCost_ = (depth * wordProbability[start]);
            return node;
        }

        for(int i=start; i<=end; i++) {
            Node current = new Node();

            current.value_ = words[i];

            current.depth_ = depth;

            current.probability_ = wordProbability[i];

            if(i != start)
                current.left_ = optimalBSTBruteForce(words, wordProbability, start, i-1, depth+1);

            if(i != end)
                current.right_ = optimalBSTBruteForce(words, wordProbability, i+1, end, depth+1);

            double leftCost = current.left_ != null ? current.left_.searchCost_ : 0;
            double rightCost = current.right_ != null ? current.right_.searchCost_ : 0;

            current.searchCost_ = leftCost + rightCost + (wordProbability[i] * depth);

            if((node == null) || (node.searchCost_ > current.searchCost_))
                node = current;

//            if(start == 0 && end == (words.length-1)) {
//                System.out.println("\nROOT INDEX " + i + current);
//            }
        }

        return node;
    }

    private Node optimalBSTDPMemoized(final String[] words, final Double[] wordProbability, int start, int end, int depth, Map<String, Node> oBSTCache) {
        Node optimalRootNode = null;
        for(int i=start; i<=end; i++) {
            Node current = new Node();

            current.value_ = words[i];

            current.depth_ = depth;

            current.probability_ = wordProbability[i];

            if(i != start) {
                String leftKey = start + "-" + (i-1) + "-" + (depth+1);
                if(!oBSTCache.containsKey(leftKey)) {
                    oBSTCache.put(leftKey, optimalBSTDPMemoized(words, wordProbability, start, i-1, depth+1, oBSTCache));
                }
                current.left_ = oBSTCache.get(leftKey);
            }

            if(i != end) {
                String rightKey = (i+1) + "-" + end + "-" + (depth+1);
                if(!oBSTCache.containsKey(rightKey)) {
                    oBSTCache.put(rightKey, optimalBSTDPMemoized(words, wordProbability, i+1, end, depth+1, oBSTCache));
                }
                current.right_ = oBSTCache.get(rightKey);
            }

            double leftCost = current.left_ != null ? current.left_.searchCost_ : 0;
            double rightCost = current.right_ != null ? current.right_.searchCost_ : 0;

            current.searchCost_ = leftCost + rightCost + (wordProbability[i] * depth);

            if((optimalRootNode == null) || (optimalRootNode.searchCost_ > current.searchCost_))
                optimalRootNode = current;

//            if(start == 0 && end == (words.length-1)) {
//                System.out.println("\nROOT INDEX " + i + current);
//            }
        }

        return optimalRootNode;
    }

    private Node optimalBSTDPBottomUp(final String[] words, final Double[] wordProbability) {
        Map<String, Node> oBSTCache = new HashMap<>();

        for(int i=0; i<words.length; i++) {
            for(int j=0; j<words.length; j++) {
                int start = j;
                int end = j+i;

                if(end > words.length-1)
                    break;

                for(int depth=1; depth<=words.length-i; depth++) {
                    String cacheKey = start + "-" + end + "-" + depth;
                    Node optimalRootNode = getOptimalNode(words, wordProbability, oBSTCache, start, end, depth);
                    oBSTCache.put(cacheKey, optimalRootNode);
                }
            }
        }

        return oBSTCache.get(String.format("%s-%s-%s", 0, words.length-1, 1));
    }

    private Node getOptimalNode(String[] words, Double[] wordProbability, Map<String, Node> oBSTCache, int start, int end, int depth) {
        Node optimalRootNode = null;
        for(int l=start; l<=end; l++) {
            Node current = new Node();

            current.value_ = words[l];

            current.depth_ = depth;

            current.probability_ = wordProbability[l];

            if(l != start) {
                String leftKey = start + "-" + (l-1) + "-" + (depth+1);
                current.left_ = oBSTCache.get(leftKey);
            }

            if(l != end) {
                String rightKey = (l+1) + "-" + end + "-" + (depth+1);
                current.right_ = oBSTCache.get(rightKey);
            }

            double leftCost = current.left_ != null ? current.left_.searchCost_ : 0;
            double rightCost = current.right_ != null ? current.right_.searchCost_ : 0;

            current.searchCost_ = leftCost + rightCost + (wordProbability[l] * depth);

            if((optimalRootNode == null) || (optimalRootNode.searchCost_ > current.searchCost_))
                optimalRootNode = current;
        }

        return optimalRootNode;
    }

    private class Node {
        Node left_;
        Node right_;
        String value_;
        int depth_;
        double probability_;
        double searchCost_;

        @Override
        public String toString() {

            String leftStr = left_ != null ? left_.toString() : "";
            String rightStr = right_ != null ? right_.toString() : "";

            return leftStr + "\n" +
                    "value_='" + value_ + '\'' +
                    ", depth_=" + depth_ +
                    ", probability_=" + probability_ +
                    ", searchCost_=" + searchCost_ +
                    rightStr;
        }
    }
}
