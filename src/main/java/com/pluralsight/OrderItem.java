package com.pluralsight;

import java.math.BigDecimal;

public interface OrderItem {
    String getName();
    BigDecimal getPrice(); // BigDecimal handle money, prices, and precise decimal numbers
}
