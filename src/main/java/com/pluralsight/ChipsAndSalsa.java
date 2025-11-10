package com.pluralsight;

import java.math.BigDecimal;

public class ChipsAndSalsa implements OrderItem {

    private final String name;
    private final BigDecimal price;

    public ChipsAndSalsa(String salsaType) {
        this.name = "Chips & " + salsaType;
        this.price = new BigDecimal("1.50");
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
