package models;

import java.util.Date;

enum busClass {ROYAL, STANDARD, DELUXE, BUSINESS};

public  class Vehicle {

    protected String lisencePlate;
    protected int passengerCapacity;
    protected int currentCapacity;
    protected  String vehicleType;
    //    protected int vehicleId;
//    protected int driverId;
    private String commissionDate;

    public Vehicle(){};
    public Vehicle(String licensePlate, String commissionDate,String vehicleType,int passengerCapacity){
        this.lisencePlate = licensePlate;
        this.commissionDate = commissionDate;
        this.vehicleType = vehicleType;
        this.passengerCapacity = passengerCapacity;
    }

    public String getLisencePlate() {
        return lisencePlate;
    }

    public void setLisencePlate(String lisencePlate) {
        this.lisencePlate = lisencePlate;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    public boolean isFull() {
        return (this.currentCapacity == this.passengerCapacity);
    }

    public void reservePlace() { //should actually return a ticket number or all details of the reservation
        if (isFull()) return;//should do sth else
        //do some ticket related stuff
        ++this.currentCapacity;

    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getCommissionDate() {
        return commissionDate;
    }

    public void setCommissionDate(String commissionDate) {
        this.commissionDate = commissionDate;
    }
}