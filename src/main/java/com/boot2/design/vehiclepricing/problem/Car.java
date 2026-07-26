package com.boot2.design.vehiclepricing.problem;

public class Car implements Vehicle {

    private final String make;
    private final String model;
    private final int year;
    private final int mileage;
    private final double originalPrice;
    private final boolean accidentHistory;

    public Car(String make, String model, int year, int mileage, double originalPrice, boolean accidentHistory) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.originalPrice = originalPrice;
        this.accidentHistory = accidentHistory;
    }

    @Override
    public String getType() {
        return "CAR";
    }

    @Override
    public String getMake() {
        return make;
    }

    @Override
    public String getModel() {
        return model;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getMileage() {
        return mileage;
    }

    @Override
    public double getOriginalPrice() {
        return originalPrice;
    }

    @Override
    public boolean hasAccidentHistory() {
        return accidentHistory;
    }
}
