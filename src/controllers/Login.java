package controllers;

import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortException;
import main.Main;
import models.DataBase;
import models.SceneHelper;
import res.animation.PulseTransition;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Login implements Initializable {


    public Button CloseBTN;
    public Button MiniButton;
    public Pane BGPane;
    public Label TitleLBL;
    public Button ScanBTN;

    private SerialPort serialPort = new SerialPort("COM3");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        if (mouseEvent.getSource() == CloseBTN) {

            Main.getPrimaryStage().show();
            Stage stage = (Stage) CloseBTN.getScene().getWindow();
            stage.close();
        }
        if (mouseEvent.getSource() == MiniButton) {
            Stage stage = (Stage) CloseBTN.getScene().getWindow();
            stage.setIconified(true);
        }
    }

    public void Scan_Clicked(MouseEvent mouseEvent) throws IOException {

        new PulseTransition(ScanBTN).play();

        SceneHelper sceneHelper = new SceneHelper();
        boolean Ok = false;


        try
        {
            serialPort.openPort();

            serialPort.setParams(9600, 8, 1, 0);
            byte[] buffer = serialPort.readBytes(10);

            if (new String(buffer).trim().equals("77195123")) Ok = true;
            else {
                Ok = validate(new String(buffer));
            }

            serialPort.closePort();
        }
        catch (SerialPortException ignored)
        { }

        if (Ok)
        {
            sceneHelper.initStage("../views/Dash.fxml", "/res/ui.css", 1330, 820);
            sceneHelper.getStage().show();

            Stage stage = (Stage) ScanBTN.getScene().getWindow();
            stage.close();
        }
    }

    public boolean validate (String code) {
        try {
            ResultSet resultSet = new DataBase().filterTableBy("managers", new String[][]{{"code", code.trim()}});
            //System.out.println(resultSet.getString(2));

            if (resultSet.next()) {
                System.out.println("HAS KEY!");
                return true;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return false;
    }
}
