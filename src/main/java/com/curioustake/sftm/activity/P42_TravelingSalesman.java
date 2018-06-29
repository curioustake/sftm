package com.curioustake.sftm.activity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class P42_TravelingSalesman  implements Activity {

    public void invoke(String[] args) {
        System.out.println( "Execute => " + Arrays.toString(args) );

        final int size = Integer.parseInt(args[1]);
        final int maxDistance = Integer.parseInt(args[2]);
        final boolean printResult = Boolean.parseBoolean(args[3]);

        if(size <= 1 || maxDistance <= 1) {
            System.out.println("INPUT SIZE TO SMALL ");
            return;
        }

        int[][] cityDistances = new int[size][size];

        Random random = new Random();
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                if(i!=j) {
                    int distance = cityDistances[j][i] == 0 ? random.nextInt(maxDistance) : cityDistances[j][i];
                    cityDistances[i][j] = (distance == 0) ? 1 : distance;
                }
            }
        }

        Set<Integer> unvisitedCities = new HashSet<>();
        for(int i=1; i<size; i++)
            unvisitedCities.add(i);

//        Path bruteForce = shortestPathBruteForce(cityDistances, 0, 0, unvisitedCities);
//        System.out.println("Path Brute Force " + bruteForce);

        Path dpMemoized = shortestPathDPMemoized(cityDistances, 0, 0, unvisitedCities, new HashMap<>());
        System.out.println("Path DP Memoized " + dpMemoized);

        if(printResult) {
            Arrays.stream(cityDistances).forEach(c -> System.out.println(Arrays.toString(c)));
            generateGraph(size, cityDistances, 0, dpMemoized.pathStr_);
        }

//        if((bruteForce.pathStr_.compareTo(dpMemoized.pathStr_) != 0 && (bruteForce.cost_ != dpMemoized.cost_)))
//            throw new RuntimeException("Something got screwed in the DP implementation");
    }

    private Path shortestPathBruteForce(final int[][] distances, int source, int destination, final Set<Integer> unvisitedCities) {

        final Path path = new Path();

        if(unvisitedCities.size() == 0) {
            path.pathStr_ = source + " -- " + destination;
            path.cost_ = distances[source][destination];
            return path;
        }

        Set<Integer> clone = new HashSet<>(unvisitedCities);

        unvisitedCities.stream().forEachOrdered( currentCity -> {
            clone.remove(currentCity);

            Path currentPath = shortestPathBruteForce(distances, currentCity, destination, clone);

            if((path.pathStr_ == null) || (path.cost_ > (currentPath.cost_ + distances[source][currentCity]))){
                path.cost_ = (currentPath.cost_ + distances[source][currentCity]);
                path.pathStr_ = source + " -- " + currentPath.pathStr_;
            }

            clone.add(currentCity);
        });

        return path;
    }

    private Path shortestPathDPMemoized(final int[][] distances, int source, int destination,
                                        final Set<Integer> unvisitedCities, final Map<String, Path> pathCache) {
        final Path path = new Path();

        if(unvisitedCities.size() == 0) {
            path.pathStr_ = source + " -- " + destination;
            path.cost_ = distances[source][destination];
            return path;
        }

        Set<Integer> clone = new HashSet<>(unvisitedCities);

        unvisitedCities.stream().forEachOrdered( currentCity -> {
            clone.remove(currentCity);

            Path currentPath = null;

            if(clone.size() == 0) {
                currentPath = shortestPathDPMemoized(distances, currentCity, destination, clone, pathCache);
            } else {
                StringBuilder sb = new StringBuilder("|" + currentCity + "|");
                clone.stream().forEachOrdered(city -> sb.append(city + "|"));

                String cacheKey = sb.toString();

                if(!pathCache.containsKey(cacheKey))
                    pathCache.put(cacheKey, shortestPathDPMemoized(distances, currentCity, destination, clone, pathCache));

                currentPath = pathCache.get(cacheKey);
            }

            if((path.pathStr_ == null) || (path.cost_ > (currentPath.cost_ + distances[source][currentCity]))){
                path.cost_ = (currentPath.cost_ + distances[source][currentCity]);
                path.pathStr_ = source + " -- " + currentPath.pathStr_;
            }

            clone.add(currentCity);
        });

        return path;
    }

    class Path {
        @Override
        public String toString() {
            return "Path{" +
                    " Order = '" + pathStr_ + '\'' +
                    ", Cost = " + cost_ +
                    '}';
        }

        String pathStr_;
        long cost_;
    }

    private void generateGraph(int size, int[][] cityDistances, int startingCity, String shortestPath) {

        StringBuilder sb = new StringBuilder("graph G {\n rankdir=LR; \n");

        sb.append(startingCity + "[fontcolor=white, color=blue, style=filled];\n");
        sb.append(shortestPath + "[color=blue, style=bold];\n");

        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++)
                if(cityDistances[i][j] != 0 && j>i)
                    sb.append(i + " -- " + j + "[label=\"" + cityDistances[i][j] + "\"];\n");
        sb.append("}");

        System.out.println(sb.toString());
    }
}
