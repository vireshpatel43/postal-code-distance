
package edu.vanier.postalcodedistanceapp.controllers;

import edu.vanier.postalcodedistanceapp.models.PostalCode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller for the computeDistance loader
 * @author vires
 */

public class ComputeDistanceController{

    @FXML 
    Button btnComputeDistance;
    @FXML
    TextField enteredPC1;
    @FXML
    Label labelValid;
    @FXML
    TextField enteredPC2;
    
    private String fromPC;
    private String toPC;
    
    PostalCodeController PCC = new PostalCodeController();
    
    /**
     * Initializes the UI controls part of the computeDistance stage
     */
    @FXML
    public void initialize() {
        
        //TODO: Exception Handling display error message
        btnComputeDistance.setOnAction((event) -> {
            try {
                HashMap<String, PostalCode> postalCodes = PCC.parse();
                fromPC = enteredPC1.getText().toUpperCase();
                toPC = enteredPC2.getText().toUpperCase();
                
                if(postalCodes.containsKey(fromPC) && postalCodes.containsKey(toPC)) {
                    showDistance();
                    labelValid.setText("");
                }
                else {
                    labelValid.setText("Invalid entries. Please try again.");
                }
                
                
            } catch (Exception ex) {
                Logger.getLogger(ComputeDistanceController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    /**
     * Uses the distanceTo() method with entered postal codes
     * creates and Alert which includes the distance between postal codes
     * @throws Exception 
     */
    private void showDistance() throws Exception {
        HashMap<String, PostalCode> postalCodes = PCC.parse();
        PostalCode PC1 = postalCodes.get(fromPC);
        PostalCode PC2 = postalCodes.get(toPC);
        double distance = PCC.distanceTo(PC1, PC2);
        DecimalFormat df = new DecimalFormat("###.##");
        
        Alert alertDlg = new Alert(Alert.AlertType.INFORMATION);
        alertDlg.setTitle("Distance");
        alertDlg.setHeaderText("The distance between the two postal codes entered is:");
        alertDlg.setContentText(df.format(distance) + "km");
        alertDlg.showAndWait();
    }
    
    
    
    
        
}
