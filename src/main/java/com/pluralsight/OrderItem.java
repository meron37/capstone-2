package com.pluralsight;

import java.math.BigDecimal;
/**
 * Represents a basic item that can be added to an order.
 * Any class that implements OrderItem must provide a name and a price. This allows drinks, tacos,
 * and chips & salsa to be treated the same way inside an Order.
 */


public interface OrderItem {
    String getName();
    BigDecimal getPrice(); // BigDecimal handle money, prices, and precise decimal numbers
    String getSize();
}

