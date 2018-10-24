package models;

public class Trip {

    private String tripType;     // one-way internal | one-way external | round internal | round external
    private String vehicleType;  // bus | minibus | limousine

    private String departureDate;
    private String returnDate = null; // null if one-way trip

    private String destination;
    private String licensePlate;
    private String tripKey;

    private double price;
    private int numberOfPassengers;
    private int seatsLeft;

    public Trip(String departureDate, String returnDate, String tripType, String destination, double price, String licensePlate, String vehicleType, int numberOfPassengers, String tripKey) {
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.tripType = tripType;
        this.destination = destination;
        this.price = price;
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.numberOfPassengers = numberOfPassengers;
        this.tripKey = tripKey;
    }

    public int getSeatsLeft() {
        if      (this.getVehicleType().equals("Bus"))       return new Bus().getPassengerCapacity()       - this.numberOfPassengers;
        else if (this.getVehicleType().equals("Minibus"))   return new Minibus().getPassengerCapacity()   - this.numberOfPassengers;
        else if (this.getVehicleType().equals("Limousine")) return new Limousine().getPassengerCapacity() - this.numberOfPassengers;
        return 0;
    }

    public int setSeatsLeft(int seatsLeft) {
        return this.seatsLeft = seatsLeft;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licsencePlate) {
        this.licensePlate = licsencePlate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public String getTripKey() {
        return tripKey;
    }

    @Override
    public String toString() {
        return this.getTripKey() + ", " + this.getSeatsLeft();
    }
}