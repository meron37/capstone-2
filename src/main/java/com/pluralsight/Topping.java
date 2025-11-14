package com.pluralsight;

import java.math.BigDecimal;

/**
 * Represents a topping used in tacos
 * A topping has a name, an optional price, and an "extra".
 */
public class Topping {

    private String name;
    private BigDecimal price;
    private boolean isExtra;

    /**
     * this is when a topping has no extra cost.
     * Example: lettuce, onions, cilantro
     */
    public Topping(String name) {
        this.name = name.trim();
        this.price = BigDecimal.ZERO;
        this.isExtra = false;
    }

    /**
     * This is when the topping has a cost.
     * Example: extra cheese, premium meat, guacamole, etc.
     */
    public Topping(String name, BigDecimal price, boolean isExtra) {
        this.name = name.trim();
        this.price = price;
        this.isExtra = isExtra;
    }

    // -----------------------------
    // Getters
    // -----------------------------
    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isExtra() {
        return isExtra;
    }

    // -----------------------------
    //  method to print
    // -----------------------------
    @Override
    public String toString() {
        if (price.compareTo(BigDecimal.ZERO) > 0) {
            return name + (isExtra ? " (extra +$" + price + ")"
                    : " (+$" + price + ")");
        }
        return name; // free toppings
    }
}
