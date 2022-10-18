package edu.vanier.postalcodedistanceapp.tests;

import edu.vanier.postalcodedistanceapp.controllers.PostalCodeController;
import edu.vanier.postalcodedistanceapp.models.PostalCode;
import java.util.HashMap;
import java.util.Map;

public class Driver {
    
    private PostalCodeController PCC = new PostalCodeController();

    /**
     * Prints out all of the postal codes located in the CSV file
     * 
     * 
     * @throws Exception 
     */
    public void testParse() throws Exception{
        HashMap<String, PostalCode> test = PCC.parse();
        for (Map.Entry<String, PostalCode> set: test.entrySet()) {
            System.out.println(set.getValue());
        }
    }
    /**
     * Calculates the distance between two postal codes
     * In this case the distance is known as 3002.151km
     * 
     * method outputs 3001.209km
     * 
     * @throws Exception 
     * 
     */
    public void testDistanceTo() throws Exception {
        HashMap<String, PostalCode> test = PCC.parse();
        PostalCode fromPC = test.get("H9G");
        PostalCode toPC = test.get("T2G");
        double distance = PCC.distanceTo(fromPC, toPC);
        System.out.println(distance);
    }
    
    /**
     * Finds the nearby postal codes within a 2km radius
     * In this case the result is 3 different postal codes
     * 
     * 
     * @throws Exception 
     */
    public void testNearbyLocations() throws Exception {
        HashMap<String, PostalCode> test = PCC.parse();
        PostalCode fromPC = test.get("H9G");
        double radius = 2;
        HashMap<String, PostalCode> locations = PCC.nearbyLocations(radius, fromPC);
        for (Map.Entry<String, PostalCode> set: locations.entrySet()) {
            System.out.println(set.getValue());
        }
    }
    
    /**
     * Executes upon running the file
     * 
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        Driver test = new Driver();
        test.testParse();
        test.testDistanceTo();
        test.testNearbyLocations();
    }
    
}
