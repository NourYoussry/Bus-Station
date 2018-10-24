package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortException;
import main.Main;
import res.animation.PulseTransition;
import models.DataBase;

import java.net.URL;
import java.util.ResourceBundle;

public class addNewManager implements Initializable {

    public Label TitleLBL;
    public Button AddBTN;
    public TextField NameTxtBox;
    public TextField CodeTxtBox;
    public ChoiceBox<String> TypeCombo;
    public Pane BGPane;
    public Button CloseBTN;
    public Button ScanBTN;
    private DataBase database;


    private SerialPort serialPort = new SerialPort("COM3");

    public void AddBTN_Click(MouseEvent mouseEvent) {
        new PulseTransition(AddBTN).play();

        String employerName = NameTxtBox.getText();
        String employerCode = CodeTxtBox.getText();
        String accessibility = TypeCombo.getValue();

        if (employerName.trim().isEmpty() || employerCode.trim().isEmpty() || accessibility.trim().isEmpty()) return ;

        String[][] addValuesDatabase = {
                {"name",employerName},
                {"code",employerCode},
                {"accessibility", accessibility}
        };

        try
        {
            database = new DataBase();
            if (AddBTN.getText().equals("Add"))
                database.insertRecord("managers",addValuesDatabase);
            else if (AddBTN.getText().equals("Update")) {
                String[][] filters = {
                        {"code", ShowManagers.managerCodeOrg}
                };
                database.updateRecords("managers", addValuesDatabase, filters);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TypeCombo.setItems(FXCollections.observableArrayList("A", "B", "C"));

        initDrag();
    }

    public void handle(MouseEvent mouseEvent) {
        Stage stage = (Stage) CloseBTN.getScene().getWindow();
        stage.close();
    }

    public void ScanBTn_Click(MouseEvent mouseEvent) {
        try
        {
            serialPort.openPort();

            serialPort.setParams(9600, 8, 1, 0);
            byte[] buffer = serialPort.readBytes(10);
            CodeTxtBox.setText(new String(buffer));

            serialPort.closePort();
        }
        catch (SerialPortException ignored)
        { }
    }
}
