package models;

public class Ticket {

    private int ticketID;
    private Passenger passenger;
    private Trip trip;
    private String tripKey;

    public Ticket(Passenger passenger, Trip trip) {
        this.passenger = passenger;
        this.trip = trip;
        this.ticketID = (int) (100000 * Math.random());
        this.tripKey = trip.getTripKey();
    }

    public Ticket (Trip trip, int ticketID) {
        this.trip = trip;
        this.ticketID = ticketID;
        this.tripKey = trip.getTripKey();
    }

    public int getTicketID() {
        return ticketID;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Trip getTrip() {
        return trip;
    }

    public double getPrice() {
        return trip.getPrice();
    }

    public String getTripKey() {
        return tripKey;
    }
}