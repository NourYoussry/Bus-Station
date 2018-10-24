package models;

public class Limousine extends Vehicle {

    private int passengerCapacity;

    public Limousine (int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public Limousine () {
        this.passengerCapacity = 5;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }
}
