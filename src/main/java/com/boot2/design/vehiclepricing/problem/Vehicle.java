package com.boot2.design.vehiclepricing.problem;

public interface Vehicle {

    String getType();

    String getMake();

    String getModel();

    int getYear();

    int getMileage();

    double getOriginalPrice();

    boolean hasAccidentHistory();
}
