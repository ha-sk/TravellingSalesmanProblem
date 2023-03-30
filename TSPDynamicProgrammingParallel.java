// This is a Java program that finds the most efficient tour that visits all cities and returns to the starting city.
// The program uses dynamic programming to calculate the minimum cost of visiting all cities.

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class TSPDynamicProgrammingParallel {
    // Define the number of cities and a very large value for infinity

    static int MAX = 1000000;

    // Define the cities and their distance matrix
    static String[] cities = { "Nador", "Oujda", "Fes", "Meknes" };

    static int n = cities.length;
    static int[][] dist = {
            { 0, 138, 307, 369 },
            { 138, 0, 328, 382 },
            { 307, 328, 0, 282 },
            { 369, 382, 282, 0 }
    };

    // Memoization array to store previously computed results
    static int[][] memo = new int[n][1 << n];
    static int[][] prev = new int[n][1 << n];

    // Recursive function to compute the shortest tour starting from city i and
    // visiting all cities in mask
    static int fun(int i, int mask) {
        if (mask == ((1 << i) | 1)) // base case: all cities have been visited, return the distance from i to the
                                    // starting city
            return dist[i][0];
        if (memo[i][mask] != 0) // if this result has already been computed, return it
            return memo[i][mask];

        int res = MAX;

        // create a stream of all cities j that have not been visited and are not the
        // starting city
        IntStream stream = IntStream.range(0, n).parallel()
                .filter(j -> (mask & (1 << j)) != 0 && j != i && j != 0);

        // compute the shortest tour starting from j, with i removed from the mask
        int[] tempResults = stream.map(j -> fun(j, mask & (~(1 << i))) + dist[j][i])
                .toArray();

        // find the minimum distance and corresponding previous city
        for (int j = 0; j < tempResults.length; j++) {
            if (tempResults[j] < res) {
                res = tempResults[j];
            }
        }

        memo[i][mask] = res; // store the result in the memoization array
        return res; // return the result
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis(); // Get the current time
        int ans = MAX; // Set ans to the maximum integer value
        int startCity = -1; // Set the start city to -1
        for (int i = 0; i < n; i++) {
            // Calculate the cost of the path starting at city i and visiting all other
            // cities
            int temp = fun(i, (1 << n) - 1) + dist[i][0];
            // If this path is more efficient than the current best, update the variables
            if (temp < ans) {
                ans = temp;
                startCity = i;
            }
        }
        // Print the result
        System.out.println("The cost of most efficient tour = " + ans);

        System.out.println("Execution time   " + (System.currentTimeMillis() - time) + "ms");
    }
}