package controllers;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.DataBase;
import models.Passenger;
import models.Ticket;
import models.Trip;
import res.animation.PulseTransition;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ShowTickets implements Initializable {

    public TableView<Ticket> DataTable;
    public TableColumn <Trip,String> tripKey;
    public TableColumn <Ticket,String> ticketId;
    public TextField TicketIdTxtBox;
    public Button GOBTN;
    public Pane BGPane;
    public Button CloseBTN;
    private Ticket ticket;
    private String[][]tripKeyFilter;
    private DataBase dataBase;
    private ObservableList<Ticket> filteredList;
    private String number;
    private Trip trip;
    private ObservableList<Trip> tripList;

    ContextMenu cm = new ContextMenu();
    private MenuItem deleteItem = new MenuItem("Delete Selected Ticket");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cm.getItems().addAll(deleteItem);
        deleteItem.setOnAction(event->deleteItemClicked());
        try {
            populateTable(new DataBase().getRecords("tickets"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        TicketIdTxtBox.textProperty().addListener(this::TextChanged);
        DataTable.getSelectionModel().selectFirst();

        initDrag();
    }

    public void deleteItemClicked(){
        ResultSet rs ;
        Ticket ticket = DataTable.getSelectionModel().getSelectedItem();
        try {
            dataBase = new DataBase();
            dataBase.deleteRecords("tickets",new String[][]{{"ticket_id",String.valueOf(ticket.getTicketID())}});
            rs = dataBase.getRecords("tickets");
            populateTable(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void populateTable(ResultSet rs) {
        try {
            DataBase db = new DataBase();

            filteredList = FXCollections.observableArrayList();

            while (rs.next()) {
                ResultSet tripSet = db.filterTableBy("trips", new String[][] {{"trip_key", rs.getString(2)}});

                while(tripSet.next()){
                    trip = new Trip(tripSet.getString(2), tripSet.getString(3), tripSet.getString(4), tripSet.getString(7), Double.parseDouble(tripSet.getString(6)), tripSet.getString(1), tripSet.getString(5), tripSet.getInt(8), tripSet.getString(9));
                }

                Ticket ticket = new Ticket(trip, Integer.parseInt(rs.getString(1)));
                filteredList.add(ticket);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        tripKey.setCellValueFactory(new PropertyValueFactory<>("tripKey"));
        ticketId.setCellValueFactory(new PropertyValueFactory<>("ticketID"));

        DataTable.setItems(filteredList);
    }

    private void TextChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {

        if (!newValue.matches("\\d*"))
            TicketIdTxtBox.setText(newValue.replaceAll("[^\\d]", ""));
    }

    public void GOBTN_Clicked(MouseEvent mouseEvent) {

        new PulseTransition(GOBTN).play();

        number = TicketIdTxtBox.getText();
        try {
            populateTable(new DataBase().filterTableBy("tickets", new String[][] {{"ticket_id", number}}));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
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
