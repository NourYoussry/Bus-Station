package models;

public class Minibus extends Vehicle {

    private int passengerCapacity;

    public Minibus (int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public Minibus () {
        this.passengerCapacity = 15;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }
}
