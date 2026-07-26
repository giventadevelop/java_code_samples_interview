package com.boot2.design.vehiclepricing.solution;

public interface Vehicle {

    String getMake();

    String getModel();

    int getYear();

    int getMileage();

    double getOriginalPrice();

    boolean hasAccidentHistory();
}
