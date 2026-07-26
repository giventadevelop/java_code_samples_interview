package com.boot2.design.vehiclepricing.problem;

public class ProblemDemo {

    public static void main(String[] args) {
        Vehicle car = new Car("Toyota", "Camry", 2021, 42_000, 28_000, false);
        CarDealer dealer = new CarDealer();

        System.out.println(dealer.getVehicleDetails(car));
        System.out.println("Price: " + dealer.getVehiclePrice(car));
    }
}
