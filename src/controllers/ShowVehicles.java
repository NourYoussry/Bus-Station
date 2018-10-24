package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;
import models.DataBase;
import models.Trip;
import models.Vehicle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.ResourceBundle;

class xShowVehicles  implements Initializable {

    @FXML
    public TableView<Vehicle> dataTable;
    public Pane BGPane;
    public Button CloseBTN;
    @FXML
    private TableColumn<Vehicle, String> licensePlate;
    @FXML
    private TableColumn<Vehicle, String> commissionDate;
    @FXML
    private TableColumn<Vehicle, String> vehicleType;
    @FXML
    private TableColumn<Vehicle, String> passengerCapacity;
    @FXML
    private ObservableList<Vehicle> vehicleData;

    private ContextMenu cm = new ContextMenu();
    private MenuItem editItem = new MenuItem("Edit Selected Vehicle");
    private MenuItem deleteItem = new MenuItem("Delete Selected Vehicle");
    private DataBase dataBase ;

    private addNewVehicle controller;

    public static String licensePlateOrg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        populateTable();

        try {
            dataBase = new DataBase();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        cm.getItems().addAll(editItem, deleteItem);
        editItem.setOnAction(event -> editItemClick());
        deleteItem.setOnAction(event -> deleteItemClick());

        // V.IMP Line
        dataTable.getSelectionModel().selectFirst();

        initDrag();
    }

    private void showEditVehicle() throws IOException {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/addNewVehicle.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 498, 312);
        scene.getStylesheets().add("/res/ui.css");

        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();

        controller = loader.getController();
    }

    private void editItemClick() {

        try {
            showEditVehicle();

            controller.TitleLBL.setText("Edit Vehicle");
            controller.AddBTN.setText("Update");

            Vehicle theSelectedVehicle = dataTable.getSelectionModel().getSelectedItem();

            licensePlateOrg = theSelectedVehicle.getLisencePlate();

            controller.PlateTxtBox.setDisable(true);
            controller.DateTxtBox.setText(theSelectedVehicle.getCommissionDate());
            controller.PlateTxtBox.setText(theSelectedVehicle.getLisencePlate());
            controller.TypeCombo.setValue(theSelectedVehicle.getVehicleType());

        } catch (IOException ex) {
            System.out.println(ex.toString());
        }

    }

    private void deleteItemClick() {
        Vehicle vehicle = dataTable.getSelectionModel().getSelectedItem();

        String[][] filters = {
                {"license_plate", vehicle.getLisencePlate()}
        };

        try {
            new DataBase().deleteRecords("vehicles", filters);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        refreshTable();
    }

    private void refreshTable() {
        vehicleData.clear();
        populateTable();
    }

    private void populateTable() {
        try {
            DataBase db = new DataBase();

            vehicleData = FXCollections.observableArrayList();
            ResultSet rs = db.getRecords("vehicles");

            while (rs.next()) {
                Vehicle vehicle = new Vehicle(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                vehicleData.add(vehicle);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        licensePlate.setCellValueFactory(new PropertyValueFactory<>("lisencePlate"));
        commissionDate.setCellValueFactory(new PropertyValueFactory<>("commissionDate"));
        vehicleType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        passengerCapacity.setCellValueFactory(new PropertyValueFactory<>("passengerCapacity"));

        dataTable.setItems(vehicleData);
    }

    public void DataTable_Click(MouseEvent mouseEvent) {

        if(mouseEvent.getButton() == MouseButton.SECONDARY)
            cm.show(dataTable, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        else
            cm.hide();
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
