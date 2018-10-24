package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.DataBase;
import res.animation.PulseTransition;

import java.net.URL;
import java.util.ResourceBundle;

public class addNewTrip implements Initializable {


    public Label TitleLBL;
    public TextField PlateTxtBox;
    public TextField DepDateTxtBox;
    public TextField RetDateTxtBox;
    public ChoiceBox TripTypeCombo;
    public ChoiceBox VehicleTypeCombo;
    public TextField PriceTxtBox;
    public Button AddBTN;
    public Pane BGPane;
    public TextField DestTextBox;
    public Button CloseBTN;
    private String[][] filters;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TripTypeCombo.setItems(FXCollections.observableArrayList("One-Way Internal", "One-Way External", "Round Internal", "Round External"));
        VehicleTypeCombo.setItems(FXCollections.observableArrayList("Bus", "Minibus", "Limousine"));

        initDrag();
    }

    public void AddBTN_Click(MouseEvent mouseEvent) {
        new PulseTransition(AddBTN).play();

        boolean validData = true;

        /* Read user input */
        String licensePlate = PlateTxtBox.getText().trim();
        String departureDate = DepDateTxtBox.getText().trim();
        String returnDate = RetDateTxtBox.getText().trim();

        if (TripTypeCombo.getValue() == null || VehicleTypeCombo.getValue() == null) return;

        String tripType = TripTypeCombo.getValue().toString().trim();
        String vehicleType = VehicleTypeCombo.getValue().toString().trim();
        String tripPrice = PriceTxtBox.getText().trim();
        String destination = DestTextBox.getText().trim();
        String numPassengers = "0";

        if (licensePlate.isEmpty() || departureDate.isEmpty() || returnDate.isEmpty() || tripType.isEmpty() || vehicleType.isEmpty() || tripPrice.isEmpty() || destination.isEmpty()) return;

        /* Validate user input */
        String dateRegEx = "((([0][1-9]|[12][\\d])|[3][01])[-/]([0][13578]|[1][02])[-/][1-9]\\d\\d\\d)|((([0][1-9]|[12][\\d])|[3][0])[-/]([0][13456789]|[1][012])[-/][1-9]\\d\\d\\d)|(([0][1-9]|[12][\\d])[-/][0][2][-/][1-9]\\d([02468][048]|[13579][26]))|(([0][1-9]|[12][0-8])[-/][0][2][-/][1-9]\\d\\d\\d)";
        if (!departureDate.matches(dateRegEx)) validData = false;

        if (!returnDate.equals("")) {
            if (!returnDate.matches(dateRegEx)) validData = false;
        }


        if (!tripPrice.matches("\\d+(\\.)?\\d+")) validData = false;

        String tripKey = licensePlate + "-" + destination + "-" + departureDate;

        /* save trip to database */
        DataBase db = null;

        try {
            db = new DataBase();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (licensePlate.length() > 30) {
            licensePlate = licensePlate.substring(30);
        }

        String[][] values = {
                {"license_plate", licensePlate},
                {"departure_date", departureDate},
                {"return_date", returnDate},
                {"trip_type", tripType},
                {"vehicle_type", vehicleType},
                {"trip_price", tripPrice},
                {"destination", destination},
                {"passengers", numPassengers},
                {"trip_key", tripKey}
        };

        try {
            if (validData) {
                if (AddBTN.getText().equals("Add"))
                    db.insertRecord("trips", values);
                else if (AddBTN.getText().equals("Update")) {

                    filters = new String[][]{
                            {"trip_key", ShowTrips.tripKeyOrg}
                    };

                    db.updateRecords("trips", values, filters);
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Invalid trip data!");
                alert.showAndWait();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Stage currentStage;
    private double x, y;

    private void initDrag() {

        BGPane.setOnMousePressed(event -> {

            currentStage = (Stage) BGPane.getScene().getWindow();

            x = currentStage.getX() - event.getScreenX();
            y = currentStage.getY() - event.getScreenY();

            BGPane.setCursor(Cursor.CLOSED_HAND);

        });

        BGPane.setOnMouseDragged(event -> {

            currentStage.setX(event.getScreenX() + x);
            currentStage.setY(event.getScreenY() + y);

        });


        BGPane.setOnMouseReleased(event -> {

            BGPane.setCursor(Cursor.DEFAULT);

        });
    }

    public void handle(MouseEvent mouseEvent) {
        Stage stage = (Stage) CloseBTN.getScene().getWindow();
        stage.close();
    }
}
