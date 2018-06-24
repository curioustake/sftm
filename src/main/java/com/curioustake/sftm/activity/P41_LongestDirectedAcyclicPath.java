package com.curioustake.sftm.activity;

import com.curioustake.sftm.utils.RandomDataGenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class P41_LongestDirectedAcyclicPath implements Activity {

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int size = Integer.parseInt(args[1]);
        final int weight = Integer.parseInt(args[2]);
        final boolean printResult = Boolean.parseBoolean(args[3]);

        if(size <= 1 || weight <= 1) {
            System.out.println("INPUT SIZE TO SMALL ");
            return;
        }

        Integer[] edges = RandomDataGenerator.getRandomIntegerArray(size*2, 0, size, printResult);

        Integer[][] graph = new Integer[size][size];

        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                graph[i][j] = 0;
            }
        }

        Random random = new Random();
        for(int i=0; i<edges.length-1; i++) {
            int wt = random.nextInt(weight);
            int x = edges[i];
            int y = edges[i+1];

            if(x != y)
                graph[x][y] = (wt > 0) ? wt : 1;
        }

        int source = edges[random.nextInt(size)];
        int destination = edges[random.nextInt(size)];
        while(source == destination)
            destination = edges[random.nextInt(size)];

        System.out.println("Compute Longest Directed Acyclic Path from [" + source + "] to [" + destination + "]");

        Set<Integer> nodesAvailable = new HashSet<>();
        for(int i=0; i<size; i++) {
            if(i != source)
                nodesAvailable.add(i);
        }

        DirectedAcyclicPath ldapBruteForce = getLongestDirectedAcyclicPathBruteForce(graph, source, destination, nodesAvailable);

        DirectedAcyclicPath ldapDPMemoized = getLongestDirectedAcyclicPathDPMemoized(graph, source, destination, nodesAvailable);

        if(ldapBruteForce != null && ((ldapBruteForce.path_.size() != ldapDPMemoized.path_.size()) ||
                ldapBruteForce.weight_ != ldapDPMemoized.weight_))
            throw new RuntimeException("Something got screwed in the DP implementation");

        if(ldapBruteForce != null && printResult)
            generateGraph(size, graph, source, destination, ldapBruteForce);


    }

    private DirectedAcyclicPath getLongestDirectedAcyclicPathBruteForce(Integer[][] graph, int source, int destination, Set<Integer> nodesAvailable) {
        DirectedAcyclicPath ldapBruteForce = longestDirectedAcyclicPathBruteForce(graph, source, destination, nodesAvailable);

        if(ldapBruteForce == null) {
            System.out.println("No reachable path from " + source + " to " + destination);
            return null;
        }
        ldapBruteForce.add(source, 0);

        System.out.println("Path Brute Force " + ldapBruteForce.path_.toString());
        System.out.println("Cost  Brute Force " + ldapBruteForce.weight_);
        return ldapBruteForce;
    }

    private DirectedAcyclicPath longestDirectedAcyclicPathBruteForce(Integer[][] graph, int source, int destination, final Set<Integer> nodesAvailable) {

        DirectedAcyclicPath ldap = null;

        Set<Integer> unvisitedNodes = new HashSet<>(nodesAvailable);
        unvisitedNodes.remove(source);

        if(source == destination) {
            ldap = new DirectedAcyclicPath();
            return ldap;
        }

        Integer[] adjacentNodes = graph[source];
        for(int i=0; i<adjacentNodes.length; i++) {
            if(adjacentNodes[i] == 0 || !unvisitedNodes.contains(i))
                continue;

            DirectedAcyclicPath currentLdap = longestDirectedAcyclicPathBruteForce(graph, i, destination, unvisitedNodes);

            if((currentLdap != null)) {
                if(ldap == null || ((adjacentNodes[i] + currentLdap.weight_) > ldap.weight_ )) {
                    currentLdap.add(i, adjacentNodes[i]);
                    ldap = currentLdap;
                }
            }
        }

        return ldap;
    }

    private DirectedAcyclicPath getLongestDirectedAcyclicPathDPMemoized(Integer[][] graph, int source, int destination, Set<Integer> nodesAvailable) {
        StringBuilder sb = new StringBuilder();
        nodesAvailable.stream().forEach(n -> sb.append("-" + n));
        String nodesAvailableStr = sb.toString();

        DirectedAcyclicPath ldapDPMemoized = longestDirectedAcyclicPathDPMemoized(graph, source, destination, nodesAvailable, nodesAvailableStr, new HashMap<>());

        if(ldapDPMemoized == null) {
            System.out.println("No reachable path from " + source + " to " + destination);
            return null;
        }
        ldapDPMemoized.add(source, 0);

        System.out.println("Path DP Memoized " + ldapDPMemoized.path_.toString());
        System.out.println("Cost DP Memoized " + ldapDPMemoized.weight_);

        return ldapDPMemoized;
    }

    private DirectedAcyclicPath longestDirectedAcyclicPathDPMemoized(Integer[][] graph, int source, int destination,
                   final Set<Integer> nodesAvailable, final String nodesAvailableStr, final Map<String, DirectedAcyclicPath> ldapCache) {

        DirectedAcyclicPath ldap = null;

        Set<Integer> unvisitedNodes = new HashSet<>(nodesAvailable);
        unvisitedNodes.remove(source);

        StringBuilder sb = new StringBuilder(nodesAvailableStr);
        String unvisitedNodesStr = sb.toString();
        unvisitedNodesStr = unvisitedNodesStr.replaceFirst("-" + source, "");

        if(source == destination) {
            ldap = new DirectedAcyclicPath();
            return ldap;
        }

        Integer[] adjacentNodes = graph[source];
        for(int i=0; i<adjacentNodes.length; i++) {
            if(adjacentNodes[i] == 0 || !unvisitedNodes.contains(i))
                continue;

            String cacheKey = i + "-" + destination + "|" + unvisitedNodesStr;

            if(!ldapCache.containsKey(cacheKey))
                ldapCache.put(cacheKey, longestDirectedAcyclicPathDPMemoized(graph, i, destination, unvisitedNodes, unvisitedNodesStr, ldapCache));

            DirectedAcyclicPath currentLdap = ldapCache.get(cacheKey);

            if((currentLdap != null)) {
                if(ldap == null || ((adjacentNodes[i] + currentLdap.weight_) > ldap.weight_ )) {
                    DirectedAcyclicPath newLdap = new DirectedAcyclicPath(currentLdap);
                    newLdap.add(i, adjacentNodes[i]);
                    ldap = newLdap;
                }
            }
        }

        return ldap;
    }

    private void generateGraph(int size, Integer[][] graph, int source, int destination, DirectedAcyclicPath ldap) {
        String prefix = " -> ";
        StringBuilder ldapSb = new StringBuilder();
        for(int i=0; i<ldap.path_.size(); i++){
            if(i != 0)
                ldapSb.append(prefix);

            ldapSb.append(ldap.path_.get(i));
        }

        StringBuilder sb = new StringBuilder("digraph G {\n rankdir=LR; \n");
        sb.append(source + "[fontcolor=white, color=blue, style=filled];\n");
        sb.append(destination + "[fontcolor=white" +
                ", color=blue, style=filled];\n");
        sb.append(ldapSb.toString() + "[color=blue, style=bold];\n");
        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++)
                if(graph[i][j] != 0)
                    sb.append(i + " -> " + j + "[label=\"" + graph[i][j] + "\"];\n");
        sb.append("}");

        System.out.println(sb.toString());
    }

    class DirectedAcyclicPath {
        LinkedList<Integer> path_;
        long weight_;

        DirectedAcyclicPath() {
            path_ = new LinkedList<>();
            weight_ = 0;
        }

        DirectedAcyclicPath(DirectedAcyclicPath copy) {
            path_ = new LinkedList<>(copy.path_);
            weight_ = copy.weight_;
        }

        void add(Integer node, int weight) {
            path_.addFirst(node);
            weight_ = weight_+ weight;
        }
    }
}
