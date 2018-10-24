package main;

import controllers.addNewTrip;
import controllers.addNewVehicle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SceneHelper {


    private FXMLLoader loader;
    public Object controller;
    private Stage stage;

    public addNewTrip    addNewTripController    = null;
    public addNewVehicle addNewVehicleController = null;


    public SceneHelper(Object controller) {

        loader = new FXMLLoader();
        stage = new Stage();

        if (controller instanceof addNewVehicle) {
            addNewVehicleController = (addNewVehicle) controller;
        } else if (controller instanceof addNewTrip) {
            addNewTripController    = (addNewTrip) controller;
        }
    }


    public void initStage(String fxml, String styleSheet, int width, int height) throws IOException {

        Parent root;
        Scene scene;

        loader = new FXMLLoader(getClass().getResource(fxml));
        root = loader.load();

        scene = new Scene(root, width, height);
        scene.getStylesheets().add(styleSheet);
        //scene.setFill(Color.TRANSPARENT);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
    }


    public FXMLLoader getLoader() {
        return loader;
    }

    public Stage getStage() {
        return stage;
    }

}