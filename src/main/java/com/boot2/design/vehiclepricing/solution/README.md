# Vehicle Pricing Solution — Design Walkthrough

Package: `com.boot2.design.vehiclepricing.solution`  
Contrast with: `com.boot2.design.vehiclepricing.problem`

This document explains **how the improved dealership pricing design was achieved**,
what was wrong with the original, and **which design improvements** landed in this package.

---

## 1. The interview problem (original design)

In the **problem** package, a dealership prices vehicles like this:

| Class | Responsibility (as written) |
|--------|-----------------------------|
| `Vehicle` | Data contract, including `getType()` (`"CAR"`, `"TRUCK"`, …) |
| `Car` | Concrete vehicle |
| `CarDealer` | Shows details **and** calculates price with `if/else` on `vehicle.getType()` |

Core smell in `CarDealer.getVehiclePrice`:

```java
if ("CAR".equals(vehicle.getType())) {
    // car depreciation rules
} else if ("TRUCK".equals(vehicle.getType())) {
    // truck depreciation rules
}
```

Passing `Vehicle` as an interface into the dealer was **not** the mistake — that is good
dependency on an abstraction. The mistake was stuffing **every pricing algorithm** into
the dealer and selecting it with string type checks.

---

## 2. Design problems we needed to fix

| Issue | Why it hurts |
|--------|----------------|
| **SRP violation** | `CarDealer` mixes “dealer presentation” with “pricing algorithms”. |
| **OCP violation** | New type (motorcycle, EV) or new rule set forces edits to `CarDealer`. |
| **Fragile dispatch** | `"CAR"` / `"TRUCK"` strings are easy to typo and grow into long conditionals. |
| **Fat / awkward interface** | `getType()` exists mainly so the dealer can branch — pricing concerns leak into the model. |
| **Harder unit tests** | Pricing rules cannot be tested without constructing dealer behavior. |

---

## 3. Design improvement applied: Strategy pattern

We **separated “what a vehicle is” from “how we price it”**.

```
                    ┌─────────────────────────┐
                    │   VehiclePricingStrategy │  «interface»
                    │   calculatePrice(v)      │
                    └────────────┬────────────┘
                                 │
              ┌──────────────────┴──────────────────┐
              │                                     │
   ┌──────────▼──────────┐              ┌───────────▼────────────┐
   │ CarPricingStrategy  │              │ TruckPricingStrategy   │  (add later)
   │ (car rules only)    │              │ without touching dealer│
   └──────────▲──────────┘              └───────────▲────────────┘
              │                                     │
              └──────────────┬──────────────────────┘
                             │ injected
                  ┌──────────▼──────────┐
                  │   VehicleDealer     │
                  │ details + delegate  │
                  └──────────┬──────────┘
                             │ uses
                  ┌──────────▼──────────┐
                  │      Vehicle        │  «interface» (data only)
                  └──────────┬──────────┘
                             │
                        ┌────▼────┐
                        │   Car   │
                        └─────────┘
```

### Classes in this package

| Class / interface | Role after the improvement |
|-------------------|----------------------------|
| `Vehicle` | Vehicle **data** only (`make`, `model`, `year`, mileage, price, accident). **No** `getType()`. |
| `Car` | Concrete `Vehicle`. |
| `VehiclePricingStrategy` | Abstraction for **any** pricing algorithm. |
| `CarPricingStrategy` | Car-specific depreciation / accident adjustments. |
| `VehicleDealer` | Dealer concerns only; **delegates** price to the injected strategy. |
| `SolutionDemo` | Shows wiring: `new VehicleDealer(new CarPricingStrategy())`. |

---

## 4. How the solution was achieved (step by step)

1. **Keep** the idea of depending on `Vehicle` for reading attributes (still good).
2. **Extract** the price formula that lived inside `CarDealer` into `CarPricingStrategy`.
3. **Introduce** `VehiclePricingStrategy` so the dealer depends on an interface, not a concrete formula.
4. **Inject** the strategy into `VehicleDealer` via constructor (dependency inversion).
5. **Remove** type-string branching from the dealer — choosing car vs truck pricing is now
   “pass a different strategy”, not “edit another `else if`”.
6. **Drop** `getType()` from the solution `Vehicle` interface so the model is not used as a
   poor man’s polymorphic switch.

### Before (problem) — dealer owns pricing

```java
public double getVehiclePrice(Vehicle vehicle) {
    // age / mileage / type / accident all inside CarDealer
}
```

### After (solution) — dealer delegates

```java
public class VehicleDealer {
    private final VehiclePricingStrategy pricingStrategy;

    public VehicleDealer(VehiclePricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public double getVehiclePrice(Vehicle vehicle) {
        return pricingStrategy.calculatePrice(vehicle);
    }
}
```

### Car rules live only in the strategy

```java
public class CarPricingStrategy implements VehiclePricingStrategy {
    public double calculatePrice(Vehicle vehicle) {
        // age * 1000, mileage * 0.08, accident -2000, floor at 0
    }
}
```

---

## 5. What improved (checklist for interviews)

- **Single Responsibility** — dealer presents / orchestrates; strategy prices.
- **Open/Closed** — add `TruckPricingStrategy` (or seasonal promo strategy) without changing `VehicleDealer`.
- **Dependency Inversion** — dealer depends on `VehiclePricingStrategy`, not concrete car math.
- **Strategy pattern** — algorithms are interchangeable objects.
- **Cleaner model** — `Vehicle` is data; type-specific behavior is not encoded as string switches.
- **Testability** — unit-test `CarPricingStrategy` alone; mock the strategy when testing the dealer.

---

## 6. Extending later (without touching the dealer)

```java
// Same dealer API, different pricing policy
VehicleDealer carLot   = new VehicleDealer(new CarPricingStrategy());
VehicleDealer truckLot = new VehicleDealer(new TruckPricingStrategy()); // new class only
```

Optional next steps in a real system: strategy registry by vehicle class, or compose
decorators (e.g. `WarrantyAdjustedPricing` wrapping another strategy).

---

## 7. How to run

From the project root:

```text
mvn -q -DskipTests compile
java -cp target/classes com.boot2.design.vehiclepricing.solution.SolutionDemo
```

Compare behavior with:

```text
java -cp target/classes com.boot2.design.vehiclepricing.problem.ProblemDemo
```

Same sample Camry numbers; different **structure** of who owns the pricing logic.

---

## 8. One-sentence interview answer

> The bug was not “passing a `Vehicle` interface”; it was putting all pricing rules inside
> `CarDealer` with type strings. We fixed it by extracting a `VehiclePricingStrategy`
> (Strategy pattern) so the dealer stays open for extension and closed for modification.

Broader notes also live at: `docs/vehicle-pricing-design.md` (repo `docs/` folder).
