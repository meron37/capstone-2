package com.pluralsight;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a customer's order. The Order stores a list of
 * OrderItem objects such as drinks, tacos, and chips.
 * It provides methods to add items, calculate totals and tax,
 * and print a formatted receipt.
 */
public class Order {

    // 8.25% tax
    private static final BigDecimal TAX_RATE = new BigDecimal("0.0825");

    // list of items in this order
    private final List<OrderItem> items;

    // when the order was created
    private final LocalDateTime orderDate;

    public Order() {
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Needed by Main.startNewOrder() so it can display
     * "Current Order (newest first)".
     */
    public List<OrderItem> getItems() {
        // return a copy so outside code can’t modify internal list by accident
        return new ArrayList<>(items);
    }

    /** Subtotal before tax */
    public BigDecimal getSubtotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            total = total.add(item.getPrice());
        }
        return total;
    }

    /** Tax amount */
    public BigDecimal getTax() {
        return getSubtotal()
                .multiply(TAX_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /** Total including tax */
    public BigDecimal getTotalWithTax() {
        return getSubtotal().add(getTax());
    }

    /** Kept in case you still call getTotal() somewhere */
    public BigDecimal getTotal() {
        return getTotalWithTax();
    }

    /** Builds the printable receipt text. */
    public String toReceiptString() {
        StringBuilder sb = new StringBuilder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

        sb.append("---- Receipt ----\n");
        sb.append("Date: ")
                .append(orderDate.format(formatter))
                .append("\n\n");

        for (OrderItem item : items) {
            sb.append(item.getName())
                    .append(" .... $")
                    .append(String.format("%.2f", item.getPrice().doubleValue()))
                    .append("\n");
        }

        BigDecimal subtotal = getSubtotal();
        BigDecimal tax = getTax();
        BigDecimal total = getTotalWithTax();

        sb.append("\nSUBTOTAL: $")
                .append(String.format("%.2f", subtotal.doubleValue()))
                .append("\n");

        sb.append("TAX (8.25%): $")
                .append(String.format("%.2f", tax.doubleValue()))
                .append("\n");

        sb.append("TOTAL: $")
                .append(String.format("%.2f", total.doubleValue()))
                .append("\n");

        return sb.toString();
    }

    public void printReceipt() {
        System.out.println(toReceiptString());
    }
}
