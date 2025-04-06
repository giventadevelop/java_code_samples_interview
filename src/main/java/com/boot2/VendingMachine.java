package com.boot2;

import java.util.HashMap;
import java.util.Map;

public class VendingMachine {

    public static void main(String[] args) {

        processOrder(args);


    }

    private static void processOrder(String[] args) {
        String product =  args[0];
        String cash =  args[1];
        double cashVal =  Double.parseDouble(cash);

        Map<String,Double> shopProducts = new HashMap<>();
        shopProducts.put("Candy",1.50);
        shopProducts.put("Soda",2.75);
        shopProducts.put("Chips",1.80);

        double prodPrice = shopProducts.get(product);

        double balance = cashVal - prodPrice;

        System.out.println("product "+ product  + " cash "+cashVal + " balance "+ balance  );
    }
}
