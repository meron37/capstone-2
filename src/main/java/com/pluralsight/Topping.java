package com.pluralsight;

import java.math.BigDecimal;

/**
 * Represents a taco topping.
 * Regular toppings are included (price = 0).
 * Premium toppings or extras can have a positive price.
 */
public class Topping {

    private final String name;
    private final BigDecimal price;
    private final boolean isExtra;

    // Regular (included) topping
    public Topping(String name) {
        this(name, BigDecimal.ZERO, false);
    }

    // Topping with a price (extra)
    public Topping(String name, BigDecimal price, boolean isExtra) {
        this.name = name;
        this.price = price;
        this.isExtra = isExtra;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isExtra() {
        return isExtra;
    }

    @Override
    public String toString() {
        if (price.compareTo(BigDecimal.ZERO) > 0) {
            return name + " (+" + price + ")";
        }
        return name;
    }
}
