package controllers;

import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import res.animation.PulseTransition;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ResourceBundle;

public class SMS  implements Initializable {


    public Label TitleLBL;
    public Pane BGPane;

    public TextField PhoneTxtBox;
    public TextArea ContentTxtBox;
    public Button SendBTN;

    public Button CloseBTN;
    public Button MiniButton;


    private Stage currentStage;
    private double x, y;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        PhoneTxtBox.textProperty().addListener(this::TextChanged);

        initDrag();
    }

    public void SendBTN_Clicked(MouseEvent mouseEvent) throws IOException {

        new PulseTransition(SendBTN).play();
        Alert alert;

        if (PhoneTxtBox.getText().trim().isEmpty() || ContentTxtBox.getText().trim().isEmpty())
        {
            alert = new Alert(Alert.AlertType.CONFIRMATION, "Fill All Fields", ButtonType.OK);
        }
        else
        {
            SendBTN.setText("Wait");
            SendBTN.setDisable(true);

            String smsResult = SendMessage("desktop-ecjg199", 9710, "admin"
                    , "0000", "+2" + PhoneTxtBox.getText(), ContentTxtBox.getText());


            if (!smsResult.contains("OK"))
                alert = new Alert(Alert.AlertType.ERROR, smsResult, ButtonType.OK);
            else
            {
                PhoneTxtBox.setText("");
                ContentTxtBox.setText("");

                alert = new Alert(Alert.AlertType.CONFIRMATION, smsResult, ButtonType.OK);
            }

            SendBTN.setText("Send");
            SendBTN.setDisable(false);
        }

        alert.showAndWait();
    }

    private void TextChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {

        if (!newValue.matches("\\d*"))
            PhoneTxtBox.setText(newValue.replaceAll("[^\\d]", ""));
    }

    public void handle(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == CloseBTN) {
            Stage stage = (Stage) CloseBTN.getScene().getWindow();
            stage.close();
        }
        if (mouseEvent.getSource() == MiniButton) {
            Stage stage = (Stage) CloseBTN.getScene().getWindow();
            stage.setIconified(true);
        }
    }

    private String SendMessage(String host, int port, String userName, String password, String number, String message) throws IOException {


        // Create the base URI to send a message with the Diafaan SMS Server HTTP API
        String uri = "http://" + host + ":" + Integer.toString(port) + "/http/send-message/";
        // Add the HTTP query to the base URI
        uri += "?username=" + URLEncoder.encode(userName, "UTF-8");
        uri += "&password=" + URLEncoder.encode(password, "UTF-8");
        uri += "&to=" + URLEncoder.encode(number, "UTF-8");
        uri += "&message=" + URLEncoder.encode(message, "UTF-8");


        URL url = new URL(uri);
        HttpURLConnection uc = (HttpURLConnection)url.openConnection();
        uc.disconnect();

        return uc.getResponseMessage();
    }

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


}
