
package edu.vanier.postalcodedistanceapp.controllers;

import edu.vanier.postalcodedistanceapp.models.PostalCode;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Controller for the nearbyLocations loader
 * @author vires
 */
public class nearbyLocationsController {
    @FXML
    Button btnFindLocations;
    @FXML
    TextField enteredPostalCode;
    @FXML
    TextField enteredRadius;
    @FXML
    Label nearbyValid;
    
    
    private String postalCode;
    private int radius;
    
    PostalCodeController PCC = new PostalCodeController();
    private TableView locationsTable = new TableView();
    
    /**
     * Initializes the UI controls part of the nearbyLocations stage
     */
    @FXML
    public void initialize() {
        btnFindLocations.setOnAction((event) -> {
            try {
                createTable();
            } catch (Exception ex) {
                Logger.getLogger(nearbyLocationsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    /**
     * Uses nearbyLocations() with entered radius and postalCode to create
     * a HashMap with the locations within the radius. Creates a new Stage with a 
     * table and enters the HashMap data into the cells. Clears the table once the 
     * user exits the stage.
     * @throws Exception 
     */
    private void createTable() throws Exception {
        locationsTable.getItems().clear();
        locationsTable.getColumns().clear();
        HashMap<String, PostalCode> postalCodes = PCC.parse();
        postalCode = enteredPostalCode.getText().toUpperCase();
        if (!postalCodes.containsKey(postalCode)) {
            nearbyValid.setText("Invalid entry. Try again.");
        }
        try {
        radius = Integer.parseInt(enteredRadius.getText());
        }catch (Exception e) {
            nearbyValid.setText("Invalid entry. Try again.");
        }
        if (radius <=1 || radius >=500) {
            nearbyValid.setText("Invalid entry. Try again.");
        }
        
        if (postalCodes.containsKey(postalCode) && radius >= 1 && radius <= 500) {
            nearbyValid.setText("");
            HashMap<String, PostalCode> locationList = PCC.nearbyLocations(radius, postalCodes.get(postalCode));           
            System.out.println(locationList);
            Stage stage = new Stage();
            Scene scene = new Scene(new Group());
            stage.setTitle("Location List");
            stage.setWidth(450);
            stage.setHeight(500);

            final Label label = new Label("Locations within radius:");
            label.setFont(new Font("Arial", 20));
            
            for (Map.Entry<String, PostalCode> set : locationList.entrySet()) {
                locationsTable.getItems().add(set.getValue());
            }

            locationsTable.setEditable(true);
            TableColumn<PostalCode, String> country = new TableColumn("Country");
            country.setCellValueFactory(new PropertyValueFactory<>("country"));
            
            TableColumn postalCode = new TableColumn("Postal Code");
            postalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            
            TableColumn province = new TableColumn("Province");
            province.setCellValueFactory(new PropertyValueFactory<>("province"));
            
            TableColumn latitude = new TableColumn("Latitude");
            latitude.setCellValueFactory(new PropertyValueFactory<>("latitude"));
            
            TableColumn longitude = new TableColumn("Longitude");
            longitude.setCellValueFactory(new PropertyValueFactory<>("longitude"));

            locationsTable.getColumns().addAll(country, postalCode, province, latitude, longitude);
            
            final VBox vbox = new VBox();
            vbox.setSpacing(5);
            vbox.setPadding(new Insets(10, 0, 0, 10));
            vbox.getChildren().addAll(label, locationsTable);

            ((Group) scene.getRoot()).getChildren().addAll(vbox);

            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        }
        
        
    }
    
}
