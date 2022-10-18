package edu.vanier.postalcodedistanceapp.ui;

import edu.vanier.postalcodedistanceapp.controllers.PostalCodeController;
import edu.vanier.postalcodedistanceapp.tests.Driver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This is a JavaFX project template to be used for creating GUI applications.
 * JavaFX 18 is already linked to this project in the build.gradle file.
 * @link: https://openjfx.io/javadoc/18/
 * @author Sleiman Rabah.
 * @see: Build Scripts/build.gradle
 */
public class MainApp extends Application {

    //Just Testing GitHub
    @Override
    public void start(Stage stage) throws Exception {
      
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/postal_code_main_window.fxml"));

        PostalCodeController controller = new PostalCodeController();
        
        loader.setController(controller);
        
        Pane root = loader.load();

        //--> Step 1) Create the parent node of the scene graph.
        
        //-----------
        //TODO:| Create your Scene graph here.
        //-----------       
        //FIXME: ask me in class what FIXME is supposed to mean.
        //-----
        //--> Step 2) Create the scene with the specified width and height
        //          and attach the scene graph to the scene.        
        Scene scene = new Scene(root, 700, 600);
        //--> Step 3) Load the scene into stage (window)
        stage.setScene(scene);        

        stage.setTitle("Postal Code Distance App");
        // Resize the stage so the size matches the scene
        stage.sizeToScene();
        //--> Step 4) Show the window.
        stage.show();
    }
    

    public static void main(String[] args) throws Exception {
        launch(args);
    }
    
    
}