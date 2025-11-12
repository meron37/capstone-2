package com.pluralsight;

import java.math.BigDecimal;

/*
 * Represents a Chips & Salsa item that can be added to an order.
 * The customer chooses the salsa type
 * The price is always fixed at $1.50.
 */

public class ChipsAndSalsa implements OrderItem {
    private final String name;
    private final BigDecimal price;

    public ChipsAndSalsa(String salsaType){
        this.name = "Chips & " + salsaType;
        this.price = new BigDecimal("1.50"); // fixed price

    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }
}

