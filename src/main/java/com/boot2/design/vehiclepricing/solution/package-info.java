/**
 * Improved car-dealership vehicle pricing design (Strategy pattern).
 *
 * <p><b>What was wrong in {@code com.boot2.design.vehiclepricing.problem}</b>
 * {@code CarDealer} both presented vehicle details and owned all pricing rules,
 * including fragile {@code "CAR"} / {@code "TRUCK"} string branches. Adding a new
 * vehicle type or pricing rule meant editing the dealer (OCP / SRP violations).
 *
 * <p><b>How this package fixes it</b>
 * Pricing is extracted behind {@link VehiclePricingStrategy}. {@link VehicleDealer}
 * depends on that abstraction and delegates {@code getVehiclePrice}; car-specific
 * math lives in {@link CarPricingStrategy}. {@link Vehicle} no longer exposes
 * {@code getType()} for pricing dispatch — type-specific behavior is chosen by
 * injecting the right strategy.
 *
 * <p>See {@code README.md} in this package for a full walkthrough, before/after
 * comparison, and interview talking points.
 *
 * <p>Run the demo: {@link SolutionDemo}
 */
package com.boot2.design.vehiclepricing.solution;
