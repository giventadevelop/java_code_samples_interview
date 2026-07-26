package com.boot2.design.vehiclepricing.solution;

public class SolutionDemo {

    public static void main(String[] args) {
        Vehicle car = new Car("Toyota", "Camry", 2021, 42_000, 28_000, false);
        VehicleDealer dealer = new VehicleDealer(new CarPricingStrategy());

        System.out.println(dealer.getVehicleDetails(car));
        System.out.println("Price: " + dealer.getVehiclePrice(car));
    }
}
