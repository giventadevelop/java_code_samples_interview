# Vehicle Pricing Design Interview Question

## Problem

The original design has a `Vehicle` interface and a `CarDealer` class. `CarDealer` accepts a `Vehicle` parameter and calculates the price by reading vehicle attributes such as year, mileage, original price, and accident history.

Passing an interface as a method parameter is not the problem. That is usually good design because the dealer depends on an abstraction instead of a concrete implementation.

The main problem is that `CarDealer` owns too much pricing logic. If the application later adds trucks, motorcycles, electric cars, luxury cars, or different pricing rules, the `CarDealer` class must keep changing.

## Problems With The Current Design

- `CarDealer` violates the Single Responsibility Principle because it handles dealer behavior and pricing rules.
- `CarDealer` violates the Open/Closed Principle because every new vehicle type or pricing rule requires modifying the dealer.
- The `Vehicle` interface can become a fat interface if every vehicle type is forced to expose attributes that only some pricing algorithms need.
- String checks such as `"CAR"` and `"TRUCK"` are fragile and can grow into long conditional logic.
- Unit testing pricing logic becomes harder because it is mixed with dealer behavior.

## Better Design

The improved design keeps `Vehicle` as the abstraction for vehicle data, but moves price calculation into a separate strategy:

- `Vehicle` describes common vehicle attributes.
- `VehiclePricingStrategy` defines the pricing algorithm contract.
- `CarPricingStrategy` contains car-specific pricing rules.
- `VehicleDealer` delegates price calculation to the pricing strategy.

This follows the Strategy design pattern.

## Why This Is Better

- `VehicleDealer` only handles dealer-related behavior.
- Pricing logic can be tested independently.
- New pricing rules can be added by creating new strategy classes.
- Existing dealer code does not need to change for every new vehicle pricing rule.
- The design is easier to explain in interviews because it clearly applies SRP, OCP, dependency inversion, and strategy pattern.

## Packages

- Problem code: `com.boot2.design.vehiclepricing.problem`
- Solution code: `com.boot2.design.vehiclepricing.solution`
- Package walkthrough (how the solution was achieved, design improvements):  
  `src/main/java/com/boot2/design/vehiclepricing/solution/README.md`  
  (also `package-info.java` in that package for IDE/Javadoc)

## Interview Summary

The issue is not that `Vehicle` is passed as an interface. That part is correct. The issue is that `CarDealer` becomes responsible for calculating prices for different vehicle types. This creates tight coupling and makes the class harder to extend. A cleaner design is to move pricing logic into a separate `VehiclePricingStrategy` and inject that strategy into the dealer.
