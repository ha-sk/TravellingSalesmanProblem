// This is a Java program that solves the Traveling Salesman Problem (TSP) using brute force approach
// The program uses an ArrayList to store all the cities and an integer 2D array to store the distance between each pair of cities.
// The program finds all possible permutations of the cities and calculates the total distance for each permutation to find the optimal solution.
import java.util.ArrayList;

class TSPBruteForce {

    static String[] cities = { "Nador", "Oujda", "Fes", "Meknes", "Rabat", "Casablanca", "Agadir", "Marrakech","Tangier",
            "Taza", "El Jadida", "Essaouira"};

    // Define the number of cities based on the length of the cities array
    static int n = cities.length;

    static int[][] dist = {
            { 0, 138, 307, 369, 602, 342, 687, 530, 360, 62, 369, 747},
            { 138, 0, 328, 382, 542, 386, 828, 671, 291, 200, 613, 909 },
            { 307, 328, 0, 282, 197, 253, 666, 512, 369, 180, 560, 879},
            { 369, 382, 282, 0, 212, 327, 560, 405, 530, 375, 557, 930},
            { 602, 542, 197, 212, 0, 87, 518, 327, 277, 420, 162, 329},
            { 342, 386, 253, 327, 87, 0, 420, 235, 245, 290, 231, 554},
            { 687, 828, 666, 560, 518, 420, 0, 235, 820, 589, 580, 634 },
            { 530, 671, 512, 405, 327, 235, 235, 0, 620, 537, 360, 525,},
            { 360, 291, 369, 530, 277, 245, 820, 620, 0, 425, 715, 1088 },
            { 62, 200, 180, 375, 420, 290, 589, 537, 425, 0, 420, 792 },
            { 369, 613, 560, 557, 162, 231, 580, 360, 715, 420, 0, 523 },
            { 747, 909, 879, 930, 329, 554, 634, 525, 1088, 792, 523, 0 },

    };

    static ArrayList<Integer> optimalPath = new ArrayList<Integer>();// Define an ArrayList to store the optimal path
    static int minPath = Integer.MAX_VALUE;

    static void travllingSalesmanProblem(int graph[][], int s) {

        // Initialize an ArrayList to store the vertices
        ArrayList<Integer> vertex = new ArrayList<Integer>();

        // Add all vertices except the starting vertex to the ArrayList
        for (int i = 0; i < n; i++)
            if (i != s)
                vertex.add(i);

        // Generate all permutations of the vertices
        do {
            int current_pathweight = 0;
            int k = s;
            ArrayList<Integer> currentPath = new ArrayList<Integer>();
            currentPath.add(s);// Add the starting vertex to the current path

            // Compute the current path distance
            for (int i = 0; i < vertex.size(); i++) {
                int vertexIndex = vertex.get(i);
                current_pathweight += graph[k][vertexIndex];
                k = vertexIndex;
                currentPath.add(k);
            }
            current_pathweight += graph[k][s]; // Add the distance from the last vertex to the starting vertex to the current path distance

            //current path is shorter than the minimum path, update the minimum path and the optimal path
            if (current_pathweight < minPath) {
                minPath = current_pathweight;
                optimalPath = currentPath;
            }

        } while (findNextPermutation(vertex));
    }
    // Define a method to swap two elements in an ArrayList
    public static ArrayList<Integer> swap(ArrayList<Integer> data, int left, int right) {
        int temp = data.get(left);
        data.set(left, data.get(right));
        data.set(right, temp);
        return data;
    }

    // Define a method to reverse a sub-list of elements in an ArrayList
    public static ArrayList<Integer> reverse(ArrayList<Integer> data, int left, int right) {
        while (left < right) {
            int temp = data.get(left);
            data.set(left++, data.get(right));
            data.set(right--, temp);
        }
        return data;
    }

    // Define a method to generate the next permutation of the vertices
    public static boolean findNextPermutation(ArrayList<Integer> data) {
        if (data.size() <= 1)
            return false;

        int last = data.size() - 2;
        while (last >= 0) {
            if (data.get(last) < data.get(last + 1)) {
                break;
            }
            last--;
        }
        if (last < 0)
            return false;

        int nextGreater = data.size() - 1;
        for (int i = data.size() - 1; i > last; i--) {
            if (data.get(i) > data.get(last)) {
                nextGreater = i;
                break;
            }
        }
        data = swap(data, nextGreater, last);
        data = reverse(data, last + 1, data.size() - 1);
        return true;
    }

    public static void main(String args[]) {
        long startTime = System.currentTimeMillis();

        travllingSalesmanProblem(dist, 0);
        System.out.println("The optimal path is:  ");
        for (int i = 0; i < optimalPath.size(); i++) {
            int cityIndex = optimalPath.get(i);
            System.out.print(cities[cityIndex] + " ");
        }
        System.out.println(cities[0]); // return to starting point

        System.out.println("The cost of most efficient tour = " + minPath);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Execution time: " + duration + " ms");
    }
}