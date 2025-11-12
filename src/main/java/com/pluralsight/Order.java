package com.pluralsight;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Order represents a customer's entire purchase.
 * It stores a list of OrderItem objects (drinks, tacos, chips, etc.)
 * and can calculate the total or print a simple receipt.
 */
public class Order {

    // a list that holds all the items (each item implements OrderItem)
    private final List<OrderItem> items = new ArrayList<>();

    // Adds an item( tacos, drink, Taco)
    public void addItem(OrderItem item) {
        items.add(item);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    // loop through all items and sum up
    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO; // start from 0
        for (OrderItem item : items) {
            total = total.add(item.getPrice()); // add each item's price
        }
        return total;
    }


    public String toReceiptString() {
        StringBuilder sb = new StringBuilder();
        sb.append("---- Receipt ----\n");
        for (OrderItem item : items) {
            sb.append(item.getName())
                    .append(" .... $")
                    .append(item.getPrice())
                    .append("\n");
        }
        sb.append("TOTAL: $").append(getTotal()).append("\n");
        return sb.toString();
    }

    public void printReceipt() {
        System.out.println(toReceiptString());
    }
}
