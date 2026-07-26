package com.boot2.design.vehiclepricing.problem;

import java.time.Year;

public class CarDealer {

    public String getVehicleDetails(Vehicle vehicle) {
        return vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel()
                + ", mileage=" + vehicle.getMileage();
    }

    public double getVehiclePrice(Vehicle vehicle) {
        double price = vehicle.getOriginalPrice();
        int vehicleAge = Year.now().getValue() - vehicle.getYear();

        if ("CAR".equals(vehicle.getType())) {
            price -= vehicleAge * 1_000;
            price -= vehicle.getMileage() * 0.08;
        } else if ("TRUCK".equals(vehicle.getType())) {
            price -= vehicleAge * 1_500;
            price -= vehicle.getMileage() * 0.12;
        }

        if (vehicle.hasAccidentHistory()) {
            price -= 2_000;
        }

        return Math.max(price, 0);
    }
}
