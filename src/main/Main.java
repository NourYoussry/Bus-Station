package main;

import controllers.addNewTrip;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.DataBase;

import java.sql.ResultSet;

public class Main extends Application {


    private static Stage ps;

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getPrimaryStage() {
        return ps;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        ps = primaryStage;


        //"../views/main.fxml"
        //1330, 820);
        //done check
        //"../views/addNewVehicle.fxml"
        //498, 312);

        //"../views/ShowVehicles.fxml"
        //800, 600);

        //"../views/addNewTrip.fxml"
        //600, 400);

        //"../views/ShowTrips.fxml"
        //800, 600);

        Parent root = FXMLLoader.load(getClass().getResource("../views/main.fxml"));
        Scene scene = new Scene(root, 1330, 820);
        scene.getStylesheets().add("/res/ui.css");

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
