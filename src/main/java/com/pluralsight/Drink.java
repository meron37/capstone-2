package com.pluralsight;

import java.math.BigDecimal;

/*A drink has a name and a price, and does not support toppings or customization, like tacos.
*/
public class Drink implements OrderItem {
    private final String name; // (final) When it created a drink, name/price won’t change
    private final BigDecimal price;

    public Drink(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
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

