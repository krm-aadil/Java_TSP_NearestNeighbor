import java.io.*;
import java.util.*;

//  store the result of the TSP solver
public class TSPRouteResult implements Serializable {
    //  instance variables
    private List<Integer> tour;
    private Map<Integer, String> deliveryPoints;
    private Map<Integer, String> customerNames;
    private Map<Integer, String> deliveryAddresses;

//  constructor
    public TSPRouteResult(List<Integer> tour, Map<Integer, String> deliveryPoints,
                          Map<Integer, String> customerNames, Map<Integer, String> deliveryAddresses) {
        //  initialize the instance variables
        this.tour = tour;
        this.deliveryPoints = deliveryPoints;
        this.customerNames = customerNames;
        this.deliveryAddresses = deliveryAddresses;
    }


    public void serializeToFile(String filename) {
        //  serialize the TSP route result to a file
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

//          write the object to the file
            objectOut.writeObject(this);
            System.out.println("TSP route result has been serialized and exported to " + filename);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
