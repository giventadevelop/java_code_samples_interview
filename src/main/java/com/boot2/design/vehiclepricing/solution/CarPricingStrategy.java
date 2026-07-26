package com.boot2.design.vehiclepricing.solution;

import java.time.Year;

public class CarPricingStrategy implements VehiclePricingStrategy {

    @Override
    public double calculatePrice(Vehicle vehicle) {
        double price = vehicle.getOriginalPrice();
        int vehicleAge = Year.now().getValue() - vehicle.getYear();

        price -= vehicleAge * 1_000;
        price -= vehicle.getMileage() * 0.08;

        if (vehicle.hasAccidentHistory()) {
            price -= 2_000;
        }

        return Math.max(price, 0);
    }
}
