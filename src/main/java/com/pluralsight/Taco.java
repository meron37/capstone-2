package com.pluralsight;

import java.math.BigDecimal;
import java.util.List;

/**
 * Taco represents a fully customizable taco.
 * Prices follow the Taco Prices + Toppings table.
 */
public class Taco implements OrderItem {

    protected String size; // protected access fields, methods, and constructors/ signature
    protected String shell;
    protected boolean deepFried;

    protected String meat;
    protected boolean extraMeat;

    protected String cheese;
    protected boolean extraCheese;

    protected List<Topping> toppings;
    protected String sauces;

    // Base shell prices
    private static final BigDecimal SINGLE_BASE  = new BigDecimal("3.50");
    private static final BigDecimal PLATE_BASE   = new BigDecimal("9.00");
    private static final BigDecimal BURRITO_BASE = new BigDecimal("8.50");

    // Custom method
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
    // PRICE CALCULATION
    // -------------------------
    public BigDecimal calculatePrice() {
        BigDecimal total = getShellBasePrice();

        // base meat price if a meat was chosen
        if (meat != null && !meat.isBlank()) {
            total = total.add(getBaseMeatPrice());
        }

        // base cheese price if cheese chosen
        if (cheese != null && !cheese.isBlank()) {
            total = total.add(getBaseCheesePrice());
        }

        // extra meat price
        if (extraMeat) {
            total = total.add(getExtraMeatPrice());
        }

        // extra cheese price
        if (extraCheese) {
            total = total.add(getExtraCheesePrice());
        }

        // deep fried cost
        if (deepFried) {
            total = total.add(getDeepFryPrice());
        }

        // premium toppings (if any) – regular toppings have price 0.00
        for (Topping topping : toppings) {
            total = total.add(topping.getPrice());
        }

        return total;
    }

    // ---------- helper methods that map size → price ----------

    private BigDecimal getShellBasePrice() {
        return switch (size) {
            case "Single Taco" -> SINGLE_BASE;
            case "3-Taco Plate" -> PLATE_BASE;
            case "Burrito" -> BURRITO_BASE;
            default -> SINGLE_BASE;
        };
    }

    // first serving of meat
    private BigDecimal getBaseMeatPrice() {
        return switch (size) {
            case "Single Taco" -> new BigDecimal("1.00");
            case "3-Taco Plate" -> new BigDecimal("2.00");
            case "Burrito" -> new BigDecimal("3.00");
            default -> BigDecimal.ZERO;
        };
    }

    // extra meat
    private BigDecimal getExtraMeatPrice() {
        return switch (size) {
            case "Single Taco" -> new BigDecimal("0.50");
            case "3-Taco Plate" -> new BigDecimal("1.00");
            case "Burrito" -> new BigDecimal("1.50");
            default -> BigDecimal.ZERO;
        };
    }

    // first serving of cheese
    private BigDecimal getBaseCheesePrice() {
        return switch (size) {
            case "Single Taco" -> new BigDecimal("0.75");
            case "3-Taco Plate" -> new BigDecimal("1.50");
            case "Burrito" -> new BigDecimal("2.25");
            default -> BigDecimal.ZERO;
        };
    }

    // extra cheese
    private BigDecimal getExtraCheesePrice() {
        return switch (size) {
            case "Single Taco" -> new BigDecimal("0.30");
            case "3-Taco Plate" -> new BigDecimal("0.60");
            case "Burrito" -> new BigDecimal("0.90");
            default -> BigDecimal.ZERO;
        };
    }

    // deep-fried price
    private BigDecimal getDeepFryPrice() {
        return switch (size) {
            case "Single Taco" -> new BigDecimal("0.75");
            case "3-Taco Plate" -> new BigDecimal("1.50");
            case "Burrito" -> new BigDecimal("2.25");
            default -> BigDecimal.ZERO;
        };
    }

    public String getDetails() {
        return "Toppings: " + toppings + "\nSauces: " + sauces;
    }
}
