package controllers;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.DataBase;
import models.Trip;
import models.TripType;
import models.VehicleType;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ShowTrips implements Initializable {

    @FXML
    public TableView<Trip> DataTable;
    public Pane BGPane;
    public Button CloseBTN;
    @FXML
    private TableColumn<Trip, String> licensePlate;
    @FXML
    private TableColumn<Trip, String> departureDate;
    @FXML
    private TableColumn<Trip, String> returnDate;
    @FXML
    private TableColumn<Trip, TripType> tripType;
    @FXML
    private TableColumn<Trip, VehicleType> vehicleType;
    @FXML
    private TableColumn<Trip, String> destination;
    @FXML
    private TableColumn<Trip, String> passengers;
    @FXML
    private TableColumn<Trip, String> tripPrice;
    @FXML
    private TableColumn<Trip, String> tripKey;

    private ObservableList<Trip> data;

    private ContextMenu cm = new ContextMenu();
    private MenuItem editItem = new MenuItem("Edit Selected Trip");
    private MenuItem deleteItem = new MenuItem("Delete Selected Trip");

    private addNewTrip controller;

    public static String tripKeyOrg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        populateTable();

        cm.getItems().addAll(editItem, deleteItem);
        editItem.setOnAction(event -> editItemClick());
        deleteItem.setOnAction(event -> deleteItemClick());

        // V.IMP Line
        DataTable.getSelectionModel().selectFirst();

        initDrag();
    }

    public void DataTable_Click(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.SECONDARY)
            cm.show(DataTable, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        else
            cm.hide();
    }

    private void editItemClick() {

        try {
            showEditTrip();

            controller.TitleLBL.setText("Edit Trip");
            controller.AddBTN.setText("Update");

            Trip trip = DataTable.getSelectionModel().getSelectedItem();

            tripKeyOrg = trip.getTripKey();

            controller.PlateTxtBox.setDisable(true);
            controller.TripTypeCombo.setValue(trip.getTripType());
            controller.VehicleTypeCombo.setValue(trip.getVehicleType());
            controller.PlateTxtBox.setText(trip.getLicensePlate());
            controller.DepDateTxtBox.setText(trip.getDepartureDate());
            controller.RetDateTxtBox.setText(trip.getReturnDate());
            controller.PriceTxtBox.setText(String.valueOf(trip.getPrice()));

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private void showEditTrip() throws IOException {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/addNewTrip.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add("/res/ui.css");

        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

        controller = loader.getController();
    }

    private void deleteItemClick() {
        Trip trip = DataTable.getSelectionModel().getSelectedItem();

        String[][] filters = {
                {"trip_key", trip.getTripKey()}
        };

        try {
            new DataBase().deleteRecords("trips", filters);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        refreshTable();
    }

    public void refreshTable() {
        data.clear();
        populateTable();
    }

    private void populateTable() {
        try {
            DataBase db = new DataBase();

            data = FXCollections.observableArrayList();
            ResultSet rs = db.getRecords("trips");

            while (rs.next()) {
                Trip trip = new Trip(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(7), Double.parseDouble(rs.getString(6)), rs.getString(1), rs.getString(5), rs.getInt(8), rs.getString(9));
                data.add(trip);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());;
        }

        // Set cell value factory to TableView
        licensePlate.setCellValueFactory(new PropertyValueFactory<>("licensePlate"));
        departureDate.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        tripType.setCellValueFactory(new PropertyValueFactory<>("tripType"));
        vehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        passengers.setCellValueFactory(new PropertyValueFactory<>("numberOfPassengers"));
        tripPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tripKey.setCellValueFactory(new PropertyValueFactory<>("tripKey"));

        //DataTable.setItems(null);
        DataTable.setItems(data);
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
