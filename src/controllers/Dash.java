package controllers;

import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;
import models.SceneHelper;
import res.animation.PulseTransition;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class Dash implements Initializable {


    public Button VehicleTab;
    public Button TripsTab;
    public Button TicketsTab;
    public Button ManagersTab;

    public Button CloseBTN;
    public Button MiniButton;
    public Label TitleLBL;
    public Pane BGPane;

    public Button AddVehicleBTN;
    public Label AddVehivleLBL;
    public Button ShowVehivleBTN;
    public Label ShowVehivleLBL;

    public Button AddTripsBTN;
    public Label AddTripsLBL;
    public Button ShowTripsBTN;
    public Label ShowTripsLBL;


    public Button AddTicketBTN;
    public Label AddTicketLBL;
    public Button ShowTicketsBTN;
    public Label ShowTicketsLBL;
    
    
    public Button AddManagersBTN;
    public Label AddManagersLBL;
    public Button ShowManagersBTN;
    public Label ShowManagersLBL;


    public Button LogoutBTN;
    public Button SMSBTN;


    private Stage currentStage;
    private double x, y;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Image smsImage = new Image(getClass().getResourceAsStream("../res/images/SMS.png"));

        Image busImage = new Image(getClass().getResourceAsStream("../res/images/Bus.png"));
        Image AddImage = new Image(getClass().getResourceAsStream("../res/images/Add.png"));

        Image addTripImage = new Image(getClass().getResourceAsStream("../res/images/AddTrip.png"));
        Image ShowTripsImage = new Image(getClass().getResourceAsStream("../res/images/ShowTrips.png"));

        Image addTicketImage = new Image(getClass().getResourceAsStream("../res/images/AddTicket.png"));
        Image ShowTicketImage = new Image(getClass().getResourceAsStream("../res/images/ShowTicket.png"));

        Image addUserImage = new Image(getClass().getResourceAsStream("../res/images/AddManagers.png"));
        Image ShowUserImage = new Image(getClass().getResourceAsStream("../res/images/ShowManagers.png"));


        ShowVehivleBTN.setGraphic(new ImageView(busImage));
        AddVehicleBTN.setGraphic(new ImageView(AddImage));

        ShowTripsBTN.setGraphic(new ImageView(ShowTripsImage));
        AddTripsBTN.setGraphic(new ImageView(addTripImage));

        ShowTicketsBTN.setGraphic(new ImageView(ShowTicketImage));
        AddTicketBTN.setGraphic(new ImageView(addTicketImage));

        ShowManagersBTN.setGraphic(new ImageView(ShowUserImage));
        AddManagersBTN.setGraphic(new ImageView(addUserImage));

        SMSBTN.setGraphic(new ImageView(smsImage));


        vehicleShow(true);
        tripShow(false);
        TicketsShow(false);
        ManagersShow(false);


        initDrag();
    }

    public void Nav_Clicked(MouseEvent mouseEvent) throws IOException {

        SceneHelper sceneHelper = new SceneHelper();

        if (mouseEvent.getSource() == SMSBTN)
        {
            new PulseTransition(SMSBTN).play();


            sceneHelper.initStage("../views/SMS.fxml", "/res/ui.css", 498, 312);
            sceneHelper.getStage().show();
        }
        if (mouseEvent.getSource() == LogoutBTN)
        {
            sceneHelper.initStage("../views/Login.fxml", "/res/ui.css", 1330, 820);
            sceneHelper.getStage().show();

            Stage stage = (Stage) LogoutBTN.getScene().getWindow();
            stage.close();
        }
    }

    public void Tabs_Clicked(MouseEvent mouseEvent) {

        if (mouseEvent.getSource() == VehicleTab){

            vehicleShow(true);
            tripShow(false);
            TicketsShow(false);
            ManagersShow(false);
        }

        if (mouseEvent.getSource() == TripsTab){

            vehicleShow(false);
            tripShow(true);
            TicketsShow(false);
            ManagersShow(false);
        }

        if (mouseEvent.getSource() == TicketsTab){

            vehicleShow(false);
            tripShow(false);
            TicketsShow(true);
            ManagersShow(false);
        }

        if (mouseEvent.getSource() == ManagersTab){

            vehicleShow(false);
            tripShow(false);
            TicketsShow(false);
            ManagersShow(true);
        }

    }



    public void Vehicle_Clicked(MouseEvent mouseEvent) throws IOException {

        SceneHelper sceneHelper = new SceneHelper();

        if (mouseEvent.getSource() == AddVehicleBTN){
            sceneHelper.initStage("../views/addNewVehicle.fxml", "/res/ui.css", 498, 312);
            sceneHelper.getStage().show();
        }

        if(mouseEvent.getSource() == ShowVehivleBTN){
            sceneHelper.initStage("../views/ShowVehicles.fxml", "/res/ui.css", 800, 600);
            sceneHelper.getStage().show();
        }
    }

    public void Trips_Clicked(MouseEvent mouseEvent) throws IOException {

        SceneHelper sceneHelper = new SceneHelper();

        if (mouseEvent.getSource() == AddTripsBTN){
            sceneHelper.initStage("../views/addNewTrip.fxml", "/res/ui.css", 600, 400);
            sceneHelper.getStage().show();
        }

        if (mouseEvent.getSource() == ShowTripsBTN){
            sceneHelper.initStage("../views/ShowTrips.fxml", "/res/ui.css", 830, 600);
            sceneHelper.getStage().show();
        }
    }

    public void Tickets_Clicked(MouseEvent mouseEvent) throws IOException {
        SceneHelper sceneHelper = new SceneHelper();

        if (mouseEvent.getSource() == AddTicketBTN){

            sceneHelper.initStage("../views/addNewTicket.fxml", "/res/ui.css", 932, 600);
            sceneHelper.getStage().show();
        }
        if (mouseEvent.getSource() == ShowTicketsBTN){

            sceneHelper.initStage("../views/ShowTickets.fxml", "/res/ui.css", 813, 600);
            sceneHelper.getStage().show();
        }

    }

    public void Managers_Clicked(MouseEvent mouseEvent) throws IOException {

        SceneHelper sceneHelper = new SceneHelper();

        if (mouseEvent.getSource() == AddManagersBTN){

            sceneHelper.initStage("../views/addNewManager.fxml", "/res/ui.css", 498, 312);
            sceneHelper.getStage().show();
        }

        if (mouseEvent.getSource() == ShowManagersBTN){

            sceneHelper.initStage("../views/ShowManagers.fxml", "/res/ui.css", 832, 600);
            sceneHelper.getStage().show();
        }
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


    public void handle(MouseEvent mouseEvent) {

        if(mouseEvent.getSource() == AddVehicleBTN)
            new PulseTransition(AddVehicleBTN).play();
        if(mouseEvent.getSource() == ShowVehivleBTN)
            new PulseTransition(ShowVehivleBTN).play();

        if(mouseEvent.getSource() == AddTripsBTN)
            new PulseTransition(AddTripsBTN).play();
        if(mouseEvent.getSource() == ShowTripsBTN)
            new PulseTransition(ShowTripsBTN).play();

        if(mouseEvent.getSource() == AddTicketBTN)
            new PulseTransition(AddTicketBTN).play();
        if(mouseEvent.getSource() == ShowTicketsBTN)
            new PulseTransition(ShowTicketsBTN).play();

        if(mouseEvent.getSource() == AddManagersBTN)
            new PulseTransition(AddManagersBTN).play();
        if(mouseEvent.getSource() == ShowManagersBTN)
            new PulseTransition(ShowManagersBTN).play();

        ////////////////////////////////////////////////

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

    private void vehicleShow(boolean state){

        AddVehicleBTN.setVisible(state);
        AddVehivleLBL.setVisible(state);

        ShowVehivleBTN.setVisible(state);
        ShowVehivleLBL.setVisible(state);
    }

    private void tripShow(boolean state){

        AddTripsBTN.setVisible(state);
        AddTripsLBL.setVisible(state);

        ShowTripsBTN.setVisible(state);
        ShowTripsLBL.setVisible(state);
    }

    private void TicketsShow(boolean state){

        AddTicketBTN.setVisible(state);
        AddTicketLBL.setVisible(state);

        ShowTicketsBTN.setVisible(state);
        ShowTicketsLBL.setVisible(state);
    }

    private void ManagersShow(boolean state){

        AddManagersBTN.setVisible(state);
        AddManagersLBL.setVisible(state);

        ShowManagersBTN.setVisible(state);
        ShowManagersLBL.setVisible(state);
    }

}