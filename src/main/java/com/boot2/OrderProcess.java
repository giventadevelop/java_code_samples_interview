package com.boot2;

import java.util.Comparator;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class OrderProcess {

  /*  enum Status {
        CREATED, PAID, SHIPPED, DELIVERED, CANCELLED
    }

    String orderId;
    Status status;
    long timestampEpochMs;

    public static Map<String, Status> latestStatus(List<OrderEvent> events) {
        events.stream().collect(Collectors.groupingBy(OrderEvent::getOrderId),
                Collectors.reducing(BinaryOperator.maxBy(Comparator.comparingLong(OrderEvent::timestampEpochMs)))
                        .entrySet.stream().collect(Collectors.toMap((e)->Map.Entry::getKey, e.get(),get)))
    };

return the latest Status per orderId.
   * "Latest" = highest timestamp; if timestamps tie, apply precedence:
            * CREATED < PAID < SHIPPED < DELIVERED < CANCELLED*/

}
