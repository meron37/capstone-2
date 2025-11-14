package com.pluralsight;

import java.math.BigDecimal;

public class ChipsAndSalsa implements OrderItem{

    /*
     * Represents a Chips & Salsa item that can be added to an order.
     * The customer chooses the salsa type
     * The price is always fixed at $1.50.
     */


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
    @Override
    public String getSize() {
        return "";   // chips don’t have size
    }
    }


