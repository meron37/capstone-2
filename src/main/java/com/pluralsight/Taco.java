package com.pluralsight;

import java.math.BigDecimal;
import java.util.List;

/**
 * Taco represents a fully customizable taco.
 * Includes topping price calculation.
 */
public class Taco implements OrderItem {

    protected String size;
    protected String shell;
    protected boolean deepFried;

    protected String meat;
    protected boolean extraMeat;

    protected String cheese;
    protected boolean extraCheese;

    protected List<Topping> toppings;     // <-- REAL toppings now
    protected String sauces;

    // Base price constants
    private static final BigDecimal SINGLE_BASE  = new BigDecimal("3.00");
    private static final BigDecimal PLATE_BASE   = new BigDecimal("7.50");
    private static final BigDecimal BURRITO_BASE = new BigDecimal("6.00");

    // Extra charges
    private static final BigDecimal EXTRA_MEAT_PRICE   = new BigDecimal("1.00");
    private static final BigDecimal EXTRA_CHEESE_PRICE = new BigDecimal("0.50");
    private static final BigDecimal DEEP_FRY_PRICE     = new BigDecimal("0.75");

    // -------------------------
    // Constructor
    // -------------------------
    public Taco(String size,
                String shell,
                boolean deepFried,
                String meat,
                boolean extraMeat,
                String cheese,
                boolean extraCheese,
                List<Topping> toppings,
                String sauces) {

        this.size = size;
        this.shell = shell;
        this.deepFried = deepFried;
        this.meat = meat;
        this.extraMeat = extraMeat;
        this.cheese = cheese;
        this.extraCheese = extraCheese;
        this.toppings = toppings;
        this.sauces = sauces;
    }

    @Override
    public String getName() {
        return size + " Taco (" + shell + ")";
    }

    @Override
    public String getSize() {
        return size;
    }

    @Override
    public BigDecimal getPrice() {
        return calculatePrice();
    }

    // -------------------------
    // ⭐ PRICE CALCULATION
    // -------------------------
    public BigDecimal calculatePrice() {
        BigDecimal total = BigDecimal.ZERO;

        // Base cost
        switch (size) {
            case "Single Taco" -> total = total.add(SINGLE_BASE);
            case "3-Taco Plate" -> total = total.add(PLATE_BASE);
            case "Burrito" -> total = total.add(BURRITO_BASE);
            default -> total = total.add(SINGLE_BASE);
        }

        // Extra meat
        if (extraMeat) total = total.add(EXTRA_MEAT_PRICE);

        // Extra cheese
        if (extraCheese) total = total.add(EXTRA_CHEESE_PRICE);

        // Deep fried cost
        if (deepFried) total = total.add(DEEP_FRY_PRICE);

        // ⭐ Add premium topping prices
        for (Topping topping : toppings) {
            total = total.add(topping.getPrice());
        }

        return total;
    }

    // Optional
    public String getDetails() {
        return "Toppings: " + toppings + "\nSauces: " + sauces;
    }
}
