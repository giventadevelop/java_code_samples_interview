# HashMap `compute()` Tutorial

Runnable walkthrough of `HashMap.compute(key, remappingFunction)`.

## Core syntax

```java
map.compute(key, (k, currentValue) -> newValue);
```

## Behavior matrix

| Current key/value status | Function returns | Result in map |
|---|---|---|
| Key exists | non-null value | key maps to new value |
| Key exists | `null` | key is removed |
| Key missing | non-null value | key is inserted |
| Key missing | `null` | map unchanged |

## Sections in the demo

1. Update an existing value  
2. Handle a missing key safely (null check)  
3. Remove a key by returning null  
4. Missing key + null → no change  
5. Full behavior matrix printout  
6. Alternative: `computeIfAbsent()`  
7. Alternative: `computeIfPresent()`  
8. Practical item-count example  

## Alternatives

| Method | When the function runs |
|---|---|
| `compute()` | Always (key present or not) |
| `computeIfAbsent()` | Only if key is missing / maps to null |
| `computeIfPresent()` | Only if key exists and value is non-null |

## Helpful guideline: `compute()` vs `merge()`

In a Java `HashMap`, `compute()` recomputes a value for a specific key from scratch
using its current value, while `merge()` combines an explicitly provided new value
with an existing value using a remapping function.

```java
map.compute(key, (k, currentValue) -> recomputedValue);
map.merge(key, newValue, (oldValue, incomingValue) -> combinedValue);
```

| Feature | `compute(K key, BiFunction remappingFunction)` | `merge(K key, V value, BiFunction remappingFunction)` |
|---|---|---|
| Primary use case | Updating a value based only on the current key/value | Combining a new external value with an existing value |
| New value needed? | No. It only takes the key and the function | Yes. You must pass an explicit default/new value |
| If key is absent | Runs the function with `null` as the current value | Directly inserts the new value without running the function |
| If key is present | Runs the function using the key and current value | Runs the function using the old value and the new value |
| If function returns `null` | Removes the key from the map | Removes the key from the map |

**Rule of thumb**
- Use `compute()` when the new value depends only on what is already in the map.
- Use `merge()` when you already have an incoming value to combine with the map.

## Run

```bash
mvn -q compile
java -cp target/classes com.boot2.hashmap.HashMapComputeTutorial
```
