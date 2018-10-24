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
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Passenger;
import models.Trip;
import res.animation.PulseTransition;
import models.DataBase;
import models.Ticket;
import sun.security.krb5.internal.crypto.Des;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class addNewTicket implements Initializable {

    public TableView<Trip> DataTable;
    public Button PayBTN;
    public ChoiceBox<String> DestCombox;
    public TextField DateTextBox;
    public Pane BGPane;
    public Button CloseBTN;

    @FXML
    private TableColumn<Trip, String> vehicleCol;
    @FXML
    private TableColumn<Trip, String> destCol;
    @FXML
    private TableColumn<Trip, String> deptCol;
    @FXML
    private TableColumn<Trip, String> seatsCol;
    @FXML
    private TableColumn<Trip, String> keyCol;
    @FXML
    private TableColumn<Trip, String> priceCol;
    @FXML
    private TableColumn<Trip, String> retCol;

    public Button FindBTN;
    private String regEx;
    private Trip trip;
    private ObservableList<Trip> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DestCombox.setItems(FXCollections.observableArrayList("Cairo", "Alexandria", "Luxor", "Tripoli", "Baghdad", "Gaza", "Khartoumm"));

        try {
            populateTable(new DataBase().getRecords("trips"));
        } catch (Exception ex) {
            ex.getMessage();
        }
        DataTable.getSelectionModel().selectFirst();

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

    private void populateTable(ResultSet rs) {
        try {

            data = FXCollections.observableArrayList();

            while (rs.next()) {
                Trip trip = new Trip(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(7), Double.parseDouble(rs.getString(6)), rs.getString(1), rs.getString(5), rs.getInt(8), rs.getString(9));
                data.add(trip);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());;
        }

        vehicleCol.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        deptCol.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        destCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
        seatsCol.setCellValueFactory(new PropertyValueFactory<>("seatsLeft"));
        retCol.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        keyCol.setCellValueFactory(new PropertyValueFactory<>("tripKey"));

        DataTable.setItems(data);
    }

    public void DataTable_Click(MouseEvent mouseEvent) {
        trip = DataTable.getSelectionModel().getSelectedItem(); // get selected trip

        if (trip.getSeatsLeft() == 0) trip = null;

        if (trip == null) {
            PayBTN.setStyle("-fx-background-color: #000e1b; -fx-border-color: #000e1b; -fx-text-fill: #FFF");
            PayBTN.setText("Selected trip is full!");
        }
    }

    public void Pay_Clicked(MouseEvent mouseEvent) {

        if (trip == null) return;

        new PulseTransition(PayBTN).play();

        Ticket ticket = new Ticket(new Passenger("Amr Mustafa"), trip);

        String[][] insertValues = {
                {"trip_key", trip.getTripKey()},
                {"ticket_id", String.valueOf(ticket.getTicketID())}
        };

        String[][] updateValues = {
                {"passengers", String.valueOf(trip.getNumberOfPassengers()+ 1)}
        };

        String[][] updateFilters = {
                {"trip_key", trip.getTripKey()}
        };

        try {
            DataBase database = new DataBase();

            database.insertRecord("tickets", insertValues);
            database.updateRecords("trips", updateValues, updateFilters);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void FindBTN_Clicked(MouseEvent mouseEvent) {
        new PulseTransition(FindBTN).play();

        regEx = "((([0][1-9]|[12][\\d])|[3][01])[-/]([0][13578]|[1][02])[-/][1-9]\\d\\d\\d)|((([0][1-9]|[12][\\d])|[3][0])[-/]([0][13456789]|[1][012])[-/][1-9]\\d\\d\\d)|(([0][1-9]|[12][\\d])[-/][0][2][-/][1-9]\\d([02468][048]|[13579][26]))|(([0][1-9]|[12][0-8])[-/][0][2][-/][1-9]\\d\\d\\d)";

        if(!DateTextBox.getText().matches(regEx) || DestCombox.getValue().equals("")) {
            DateTextBox.setPromptText("Enter A Valid Date");
            DestCombox.setValue("Choose");
            return;
        }

        String[][] filters = {
                {"destination", DestCombox.getValue()},
                {"departure_date", DateTextBox.getText()}
        };

        try {
            ResultSet rs = new DataBase().filterTableBy("trips", filters);
            if (rs.next()) {
                rs.beforeFirst();
                populateTable(rs);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Notification");
                alert.setHeaderText("No results found.");
                alert.showAndWait();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void handle(MouseEvent mouseEvent) {
        Stage stage = (Stage) CloseBTN.getScene().getWindow();
        stage.close();
    }
}


