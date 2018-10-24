package models;

public class Bus extends Vehicle {

    private int passengerCapacity;

    public Bus(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
}

    public Bus() {
        this.passengerCapacity = 30;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }
}