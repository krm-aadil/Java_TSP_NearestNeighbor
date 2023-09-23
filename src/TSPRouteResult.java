import java.io.*;
import java.util.*;

public class TSPRouteResult implements Serializable {
    private List<Integer> tour;
    private Map<Integer, String> deliveryPoints;
    private Map<Integer, String> customerNames;
    private Map<Integer, String> deliveryAddresses;

    public TSPRouteResult(List<Integer> tour, Map<Integer, String> deliveryPoints,
                          Map<Integer, String> customerNames, Map<Integer, String> deliveryAddresses) {
        this.tour = tour;
        this.deliveryPoints = deliveryPoints;
        this.customerNames = customerNames;
        this.deliveryAddresses = deliveryAddresses;
    }

    // Getter methods for the fields

    public void serializeToFile(String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(this);
            System.out.println("TSP route result has been serialized to " + filename);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
