package com.pluralsight;

import java.math.BigDecimal;

/**
 * Abstract base class for all taco types.
 * Stores shared taco attributes such as size, shell, meat, cheese,
 * toppings, sauces, and whether the taco is deep fried.
 * Each concrete taco must implement its own price calculation.
 */
public abstract class Taco implements OrderItem {

    protected String size;        // Single Taco, 3-Taco Plate, Burrito
    protected String shell;       // Corn, Flour, Hard Shell, Bowl
    protected boolean deepFried;
    protected String meat;// protected access modifier allows members (fields, methods, and constructors) to be accessible within the same package and by subclasses, even if those subclasses are in a different package.
    protected boolean extraMeat;
    protected String cheese;
    protected boolean extraCheese;
    protected String otherToppings; // onions, cilantro, etc.
    protected String sauces;        // hot, mild, verde, etc.

}
