import java.util.*;

public class TSPNearestNeighbor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numLocations;

        System.out.print("Enter the number of delivery points (excluding starting point): ");
        numLocations = scanner.nextInt() + 1; // Add 1 to account for the starting point

        String[] deliveryPoints = new String[numLocations];
        HashMap<Integer, String> itemIdsAndNames = new HashMap<>();
        HashMap<Integer, String> customerNames = new HashMap<>(); // Separate map for customer names
        HashMap<Integer, String> deliveryAddresses = new HashMap<>();

        // Initialize the distance matrix
        int[][] distanceMatrix = new int[numLocations][numLocations];
        for (int i = 0; i < numLocations; i++) {
            for (int j = 0; j < numLocations; j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0; // Distance to itself is 0
                } else {
                    System.out.print("Enter distance from point " + i + " to point " + j + ": ");
                    distanceMatrix[i][j] = scanner.nextInt();
                }
            }
        }

        // Initialize delivery points, item IDs, customer IDs, addresses, and names
        for (int i = 1; i < numLocations; i++) { // Start from i=1 to skip point 0
            System.out.print("Enter name for delivery point " + i + ": ");
            deliveryPoints[i] = scanner.next();
            System.out.print("Enter item ID for delivery point " + i + ": ");
            int itemId = scanner.nextInt();
            itemIdsAndNames.put(itemId, deliveryPoints[i]);
            System.out.print("Enter customer ID for delivery point " + i + ": ");
            int customerId = scanner.nextInt();
            System.out.print("Enter customer name for delivery point " + i + ": ");
            String customerName = scanner.next();
            customerNames.put(customerId, customerName); // Store customer name separately
            System.out.print("Enter address for delivery point " + i + ": "); // Prompt for address
            String address = scanner.next();
            deliveryAddresses.put(customerId, address); // Store address in deliveryAddresses
        }

        // Specify the starting location (delivery van's location)
        int startLocation = 0; // Change this index to specify the starting location

        // Find the nearest neighbor TSP route
        List<Integer> tour = nearestNeighborTSP(distanceMatrix, startLocation);

        // Output the TSP route with details
        System.out.println("\nTSP Route:");
        for (Integer location : tour) {
            if (location == 0) {
                System.out.println("Starting Location");
            } else {
                Integer customerId = getKeyByValue(itemIdsAndNames, deliveryPoints[location]);
                String customerName = customerNames.get(customerId);
                String address = deliveryAddresses.get(customerId);
                System.out.println("Location: " + deliveryPoints[location]);
                System.out.println("Customer ID: " + customerId);
                System.out.println("Customer Name: " + customerName);
                System.out.println("Address: " + address); // Display the address
                System.out.println();
            }
        }
    }

    public static List<Integer> nearestNeighborTSP(int[][] distanceMatrix, int startLocation)
    {
        int numLocations = distanceMatrix.length;
        boolean[] visited = new boolean[numLocations];
        List<Integer> tour = new ArrayList<>();

        // Start from the specified starting location
        int currentLocation = startLocation;
        tour.add(currentLocation);
        visited[currentLocation] = true;

        for (int i = 1; i < numLocations; i++)
        {
            int nearestNeighbor = findNearestNeighbor(currentLocation, distanceMatrix, visited);
            tour.add(nearestNeighbor);
            visited[nearestNeighbor] = true;
            currentLocation = nearestNeighbor;
        }

        // Return to the starting location to complete the cycle
        tour.add(startLocation);

        return tour;
    }

    public static int findNearestNeighbor(int currentLocation, int[][] distanceMatrix, boolean[] visited)
    {
        int nearestNeighbor = -1;
        int minDistance = Integer.MAX_VALUE;
        int numLocations = distanceMatrix.length;

        for (int i = 0; i < numLocations; i++) {
            if (!visited[i] && distanceMatrix[currentLocation][i] < minDistance) {
                nearestNeighbor = i;
                minDistance = distanceMatrix[currentLocation][i];
            }
        }

        return nearestNeighbor;
    }

    public static <K, V> K getKeyByValue(Map<K, V> map, V value)
    {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
