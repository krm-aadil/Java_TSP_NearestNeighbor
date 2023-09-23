import java.util.*;

public class TSPNearestDelivery {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numLocations;

        // Prompt user for the number of delivery points
        System.out.print("Enter the number of delivery points (excluding starting point): ");
        numLocations = scanner.nextInt() + 1; // Add 1 to account for the starting point

        //  arrays and maps to store input data
        String[] deliveryPoints = new String[numLocations];
        HashMap<Integer, String> itemIdsAndNames = new HashMap<>(); // Item IDs and names
        HashMap<Integer, String> customerNames = new HashMap<>(); // Customer names
        HashMap<Integer, String> deliveryAddresses = new HashMap<>(); // Delivery addresses

        //  the distance matrix
        int[][] distanceMatrix = new int[numLocations][numLocations];

// Prompt user for distance between points
        for (int i = 0; i < numLocations; i++) {
            for (int j = 0; j < numLocations; j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0; // Distance to itself is 0
                } else {
                    // Prompt user for distance between points
                    System.out.print("Enter distance from point " + i + " to point " + j + ": ");
                    distanceMatrix[i][j] = scanner.nextInt();
                }
            }
        }

        //  delivery points, item IDs, customer IDs, addresses, and names
        for (int i = 1; i < numLocations; i++) { // Start from i=1 to skip point 0
            // Prompt user for delivery point name
            System.out.print("Enter name for delivery point " + i + ": ");
            deliveryPoints[i] = scanner.next();

            // Prompt user for item ID and store in itemIdsAndNames map
            System.out.print("Enter item ID for delivery point " + i + ": ");
            int itemId = scanner.nextInt();
            itemIdsAndNames.put(itemId, deliveryPoints[i]);

            // Prompt user for customer ID
            System.out.print("Enter customer ID for delivery point " + i + ": ");
            int customerId = scanner.nextInt();

            // Prompt user for customer name and store separately in customerNames map
            System.out.print("Enter customer name for delivery point " + i + ": ");
            String customerName = scanner.next();
            customerNames.put(customerId, customerName); // Store customer name separately

            // Prompt user for delivery address and store in deliveryAddresses map
            System.out.print("Enter address for delivery point " + i + ": ");
            String address = scanner.next();
            deliveryAddresses.put(customerId, address); // Store address in deliveryAddresses
        }

        // Specify the starting location (delivery van's location)
        int startLocation = 0; // Change this index to specify the starting location

        // Find the nearest Delivery TSP route
        List<Integer> tour = nearestDeliveryTSP(distanceMatrix, startLocation);

        // Output the TSP route with details
        System.out.println("\nTSP Route:");
        for (Integer location : tour) {
            if (location == 0) {
                System.out.println("Starting Location");
            } else {
                // Get customer ID from itemIdsAndNames map and fetch customer details
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

        // Create a TSPRouteResult object to hold the route and related data
        TSPRouteResult tspRouteResult = new TSPRouteResult(tour, itemIdsAndNames, customerNames, deliveryAddresses);

        // Specify the filename for serialization
        String filename = "tsp_route_result.ser";

        // Serialize and write the TSP route result to a file
        tspRouteResult.serializeToFile(filename);
    }

    // Method to compute the nearest Delivery TSP route
    public static List<Integer> nearestDeliveryTSP(int[][] distanceMatrix, int startLocation)
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
            // Find the nearest delivery point
            int nearestDelivery = findNearestDelivery(currentLocation, distanceMatrix, visited);
            tour.add(nearestDelivery);
            visited[nearestDelivery] = true;
            currentLocation = nearestDelivery;
        }

        // Return to the starting location to complete the cycle
        tour.add(startLocation);

        return tour;
    }

    // Method to find the nearest delivery point
    public static int findNearestDelivery(int currentLocation, int[][] distanceMatrix, boolean[] visited)
    {
        // Initialize the nearest delivery point and the minimum distance
        int nearestDelivery = -1;
        int minDistance = Integer.MAX_VALUE;
        int numLocations = distanceMatrix.length;

        // Find the nearest delivery point
        for (int i = 0; i < numLocations; i++) {
            if (!visited[i] && distanceMatrix[currentLocation][i] < minDistance) {
                nearestDelivery = i;
                minDistance = distanceMatrix[currentLocation][i];
            }
        }

        return nearestDelivery;
    }

    // Method to find a key in a map by its associated value
    public static <K, V> K getKeyByValue(Map<K, V> map, V value)
    {
        // Iterate through the map to find the key with the specified value
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
