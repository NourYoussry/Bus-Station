package controllers;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import res.animation.PulseTransition;
import models.Vehicle;
//import models.busClass;
import models.Bus;
import models.Limousine;
//import models.MiniBus;
import models.DataBase;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javax.print.attribute.standard.DateTimeAtCompleted;

public class addNewVehicle implements Initializable {


    public Button AddBTN;
    public TextField PlateTxtBox;
    public TextField DateTxtBox;
    public ChoiceBox TypeCombo;
    public Pane BGPane;
    public Label TitleLBL;
    //work on an object from vehicle class
    public Vehicle vehicle;
    public DataBase dataBase;
    public Button CloseBTN;

    /*	protected String lisencePlate;
	protected int passengerCapacity;
	protected int currentCapacity;
	private Date lastMaintained;
	protected int vehicleId;
	protected int driverId;*/

    public void AddBTN_Click(MouseEvent mouseEvent) {
        new PulseTransition(AddBTN).play();

        boolean validData = true;

        try {
            dataBase = new DataBase();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (PlateTxtBox.getText().trim().isEmpty() || DateTxtBox.getText().trim().isEmpty() || TypeCombo.getValue() == null) return;

        String TheVehicleType = TypeCombo.getValue().toString();

        if (TheVehicleType.equals("Bus"))
            vehicle = new Bus(30);
        else if (TheVehicleType.equals("Minibus"))
            vehicle = new Bus(15);
        else if (TheVehicleType.equals("Limousine"))
            vehicle = new Limousine(5);

        vehicle.setLisencePlate(PlateTxtBox.getText().trim());
        vehicle.setCommissionDate(DateTxtBox.getText().trim());

        String dateRegEx = "((([0][1-9]|[12][\\d])|[3][01])[-/]([0][13578]|[1][02])[-/][1-9]\\d\\d\\d)|((([0][1-9]|[12][\\d])|[3][0])[-/]([0][13456789]|[1][012])[-/][1-9]\\d\\d\\d)|(([0][1-9]|[12][\\d])[-/][0][2][-/][1-9]\\d([02468][048]|[13579][26]))|(([0][1-9]|[12][0-8])[-/][0][2][-/][1-9]\\d\\d\\d)";
        if (DateTxtBox.getText().matches(dateRegEx)) {
            vehicle.setCommissionDate(DateTxtBox.getText());
        } else {
            validData = false;
            DateTxtBox.setPromptText("invalid Date Format!");
        }

        //database part

        String[][] mySQlValues = {
                {"license_plate", vehicle.getLisencePlate()},
                {"passenger_capacity", Integer.toString(vehicle.getPassengerCapacity())},
                {"vehicle_type", TheVehicleType},
                {"comission_date", vehicle.getCommissionDate()}
        };

        if (AddBTN.getText().equals("Add") && validData) {

            try {
                dataBase.insertRecord("vehicles", mySQlValues);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (AddBTN.getText().equals("Update") && validData) {

            String[][] filters = {
                    {"license_plate", ShowVehicles.licensePlateOrg}
            };

            try {
                dataBase.updateRecords("vehicles", mySQlValues, filters);
            } catch (Exception ex) {
                ex.getStackTrace();
            }

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TypeCombo.setItems(FXCollections.observableArrayList("Bus", "Minibus", "Limousine"));

        initDrag();
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
