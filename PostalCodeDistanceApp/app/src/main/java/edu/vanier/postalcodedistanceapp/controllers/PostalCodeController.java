package edu.vanier.postalcodedistanceapp.controllers;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import edu.vanier.postalcodedistanceapp.models.PostalCode;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class PostalCodeController {
    
    private HashMap<String, PostalCode> postalCodes = new HashMap<>();
    private String csvFilePath;
    
    @FXML
    Button btnOpenComputeDistance;
    @FXML 
    Button btnOpenFindLocations;
    

    public PostalCodeController(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    public PostalCodeController() {
        
    }
    
    /**
     * Parses through the CSV file and creates PostalCode objects based on each line
     * 
     * @return a HashMap including all of the postal code objects
     * 
     * @throws Exception 
     */
    public HashMap<String, PostalCode> parse() throws Exception {
        csvFilePath = getClass().getResource("/data/zipcodes.csv").getPath();
        CSVReader reader = new CSVReaderBuilder(new FileReader(csvFilePath)).build();
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            int id = Integer.valueOf(nextLine[0]);
            String country = nextLine[1];
            String postalCode = nextLine[2];
            String province = nextLine[nextLine.length - 3];
            double latitude = Double.parseDouble(nextLine[nextLine.length - 2]);
            double longitude = Double.parseDouble(nextLine[nextLine.length - 1]);
            PostalCode mapPostalCode = new PostalCode(id, country, postalCode, province, latitude, longitude);
            postalCodes.put(postalCode, mapPostalCode);
        }
        return postalCodes;
    }
    
    /**
     * Takes two postalCode objects and reads their coordinates
     * using the haversine formula, calculates the distance between them
     * 
     * @param fromPC start coordinate 
     * @param toPC end coordinate
     * @return the distance between the points as a double
     */
    public double distanceTo(PostalCode fromPC, PostalCode toPC) {
        double latitudeFrom = Math.toRadians(fromPC.getLatitude());
        double longitudeFrom = Math.toRadians(fromPC.getLongitude());
        double latitudeTo = Math.toRadians(toPC.getLatitude());
        double longitudeTo = Math.toRadians(toPC.getLongitude());
        double earthRadius = 6371;
        
        double distanceLat = (latitudeTo - latitudeFrom) / 2;
        double distanceLong = (longitudeTo - longitudeFrom) / 2;
        double expressionBracket = Math.sqrt(Math.pow(Math.sin(distanceLat), 2) + (Math.cos(latitudeFrom) * Math.cos(latitudeTo) * Math.pow(Math.sin(distanceLong), 2)));
        double answer = 2 * earthRadius * Math.asin(expressionBracket);
        return answer;
    }
    
    /**
     * Calculates the distance between given postal code and the postal codes 
     * within the HashMap.
     * 
     * @param radius Maximum distance between given postal codes and the postal codes 
     * within the HashMap
     * 
     * @param fromPC Postal Code to be used as reference
     * @return HashMap containing the postal codes within the radius
     */
    public HashMap<String, PostalCode> nearbyLocations (double radius, PostalCode fromPC) {
        HashMap<String, PostalCode> locationList = new HashMap<>();
        for (Map.Entry<String, PostalCode> set: postalCodes.entrySet()) {
            double distanceLocation = distanceTo(set.getValue(), fromPC);
            if (distanceLocation <= radius) {
                locationList.put(set.getKey(), set.getValue());
            }
        }
        return locationList;
    }

    public HashMap<String, PostalCode> getPostalCodes() {
        return postalCodes;
    }
    
    /**
     * Initializes the UI controls part of the main stage
     */
    @FXML
    public void initialize() {
        btnOpenComputeDistance.setOnAction((event) -> {
            try {
                createComputeStage();
            } catch (IOException ex) {
                Logger.getLogger(PostalCodeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnOpenFindLocations.setOnAction((event) -> {
            try {
                createLocationsStage();
            } catch (IOException ex) {
                Logger.getLogger(PostalCodeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });


    }    
    
    /**
     * method used by btnOpenComputeDistance to create a new stage to compute
     * distance
     * @throws IOException 
     */
    private void createComputeStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/postal_code_compute_distance.fxml"));
        ComputeDistanceController controller = new ComputeDistanceController();
        loader.setController(controller);
        Pane root = loader.load();
        Scene scene = new Scene(root, 600, 500);
        Stage stage = new Stage();
        stage.setScene(scene);        
        stage.setTitle("Compute Distance");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    
    /**
     * method used by btnOpenFindLocations to create a new stage to find nearby locations
     * @throws IOException 
     */
    private void createLocationsStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/postal_code_find_locations.fxml"));
        nearbyLocationsController controller = new nearbyLocationsController();
        loader.setController(controller);
        Pane root = loader.load();
        Scene scene = new Scene(root, 600, 500);
        Stage stage = new Stage();
        stage.setScene(scene);        
        stage.setTitle("Nearby Locations");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    
    

    
    
  
     
}
