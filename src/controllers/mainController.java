package controllers;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.stage.Stage;
import models.SceneHelper;
import res.animation.PulseTransition;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class mainController implements Initializable {

    public Button CustomerBTN;
    public Button EmployeeBTN;
    public Button MiniButton;
    public Button CloseBTN;
    public Pane BGPane;

    public Label TitleLBL;


    private double x, y;

    public void handle(MouseEvent mouseEvent) throws IOException {

        SceneHelper sceneHelper = new SceneHelper();

        if (mouseEvent.getSource() == CustomerBTN) {
            new PulseTransition(CustomerBTN).play();

            Stage stage = (Stage) EmployeeBTN.getScene().getWindow();
            stage.hide();

            sceneHelper.initStage("../views/addNewTicket.fxml", "/res/ui.css", 932, 600);
            sceneHelper.getStage().show();
        }
        if (mouseEvent.getSource() == EmployeeBTN) {
            new PulseTransition(EmployeeBTN).play();

            Stage stage = (Stage) EmployeeBTN.getScene().getWindow();
            stage.hide();

            sceneHelper.initStage("../views/Login.fxml", "/res/ui.css", 1330, 820);
            sceneHelper.getStage().show();

        }
        if (mouseEvent.getSource() == CloseBTN) System.exit(0);
        if (mouseEvent.getSource() == MiniButton) Main.getPrimaryStage().setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initDrag();
    }


    private void initDrag() {

        BGPane.setOnMousePressed(event -> {

            x = Main.getPrimaryStage().getX() - event.getScreenX();
            y = Main.getPrimaryStage().getY() - event.getScreenY();

            BGPane.setCursor(Cursor.CLOSED_HAND);

        });

        BGPane.setOnMouseDragged(event -> {

            Main.getPrimaryStage().setX(event.getScreenX() + x);
            Main.getPrimaryStage().setY(event.getScreenY() + y);

        });


        BGPane.setOnMouseReleased(event -> {

            BGPane.setCursor(Cursor.DEFAULT);

        });
    }
}
