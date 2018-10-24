package controllers;

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
import models.SceneHelper;
import models.Manager;
import models.DataBase;
import models.Trip;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ShowManagers implements Initializable {
    @FXML
    public TableView<Manager> DataTable;
    @FXML
    public TableColumn<Manager,String> managerName;
    @FXML
    public TableColumn<Manager, String> managerCode;
    @FXML
    public TableColumn<Manager,String> managerAccessibility;
    public Pane BGPane;
    public Button CloseBTN;

    private ObservableList<Manager> managerList;

    private ContextMenu cm = new ContextMenu();
    private MenuItem editItem = new MenuItem("Edit Selected Manager");
    private MenuItem deleteItem = new MenuItem("Delete Selected Manager");

    private addNewManager controller;

    public static String managerCodeOrg;

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

    private void populateTable(){
        managerList = FXCollections.observableArrayList();
        try {
            /* import manager values from database .. store them in a result set .. add N manager objects to observableList

             */
            ResultSet importedFromDatabase = new DataBase().getRecords("managers");
            while(importedFromDatabase.next()){
                Manager manager = new Manager(importedFromDatabase.getString(1),importedFromDatabase.getString(2),importedFromDatabase.getString(3));
                managerList.add(manager);
            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        managerName.setCellValueFactory(new PropertyValueFactory<>("managerName"));
        managerCode.setCellValueFactory(new PropertyValueFactory<>("managerCode"));
        managerAccessibility.setCellValueFactory(new PropertyValueFactory<>("managerAccessibility"));

        DataTable.setItems(managerList);


    }

    private void editItemClick() {

        try {
            showEditManager();

            controller.TitleLBL.setText("Edit Manager");
            controller.AddBTN.setText("Update");

            Manager manager = DataTable.getSelectionModel().getSelectedItem();

            controller.NameTxtBox.setDisable(true);
            controller.NameTxtBox.setText(manager.getManagerName());
            controller.CodeTxtBox.setText(manager.getManagerCode());
            controller.TypeCombo.setValue(manager.getManagerAccessibility());

            managerCodeOrg = manager.getManagerCode();

        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private void showEditManager() throws IOException {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/addNewManager.fxml"));
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
        Manager manager = DataTable.getSelectionModel().getSelectedItem();

        String[][] filters = {
                {"code", manager.getManagerCode()}
        };

        try {
            new DataBase().deleteRecords("managers", filters);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        refreshTable();
    }

    public void refreshTable() {
        managerList.clear();
        populateTable();
    }

    public void DataTable_Click(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.SECONDARY)
            cm.show(DataTable, mouseEvent.getScreenX(), mouseEvent.getScreenY());
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
