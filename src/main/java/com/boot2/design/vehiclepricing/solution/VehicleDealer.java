package com.boot2.design.vehiclepricing.solution;

public class VehicleDealer {

    private final VehiclePricingStrategy pricingStrategy;

    public VehicleDealer(VehiclePricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public String getVehicleDetails(Vehicle vehicle) {
        return vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel()
                + ", mileage=" + vehicle.getMileage();
    }

    public double getVehiclePrice(Vehicle vehicle) {
        return pricingStrategy.calculatePrice(vehicle);
    }
}
