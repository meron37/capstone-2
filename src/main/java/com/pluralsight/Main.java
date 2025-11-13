package com.pluralsight;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Entry point for the Taco Shop
 * adding Drinks and Chips & Salsa
 * to an Order and checking out with a printed receipt.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        //  new order that starts empty
        Order order = new Order();

        // Main program loop - runs until user chooses Exit (0)
        while (true) {

            // Show menu options to the user
            System.out.println("\n=== Drinks & Chips Menu ===");
            System.out.println("1) Add Drink");
            System.out.println("2) Add Chips & Salsa");
            System.out.println("3) Checkout");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            // Read user choice
            String choice = sc.nextLine().trim();

            // Handle menu selection
            switch (choice) {

             // Add a drink to the order
                case "1":
                    addDrink(order);
                    break;

                // Add chips & salsa to the order
                case "2":
                    addChips(order);
                    break;

                // Checkout and print receipt
                case "3":
                    if (order.isEmpty()) {
                        System.out.println("Order is empty. Add at least one item like chips or drink.");
                    } else {
                        order.printReceipt();     // Print receipt to screen
                        order = new Order();       // Reset the order for a new one
                    }
                    break;

                // Option 0 -> Exit the whole program
                case "0":
                    System.out.println("Goodbye!");
                    return; // Ends the program

                //  input is considered invalid
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    /**
     * Adds a drink to the order.
     * Asks the user for size (which sets the price)
     * and a flavor to build the drink's display name.
     */
    private static void addDrink(Order order) {

        // Drink size menu
        System.out.println("\n-- Add Drink --");
        System.out.println("Select size (with price):");
        System.out.println("1) Small  ($2.00)");
        System.out.println("2) Medium ($2.50)");
        System.out.println("3) Large  ($3.00)");
        System.out.print("Choose: ");

        String sizeChoice = sc.nextLine().trim(); // remove whitespace from string from beginning and end of a string

        String sizeLabel = "";          // Will store Small/Medium/Large
        BigDecimal price = BigDecimal.ZERO; // Will store the price


        switch (sizeChoice) {
            case "1":
                sizeLabel = "Small";
                price = new BigDecimal("2.00");
                break;

            case "2":
                sizeLabel = "Medium";
                price = new BigDecimal("2.50");
                break;

            case "3":
                sizeLabel = "Large";
                price = new BigDecimal("3.00");
                break;

            default:
                System.out.println("Invalid size. Drink not added.");
                return;
        }

        // Ask the user for drink flavor
        System.out.print("Enter flavor (e.g., Horchata, Coke): ");
        String flavor = sc.nextLine().trim();

        // If user leaves it blank, default to "Drink"
        if (flavor.isEmpty()) {
            flavor = "Drink";
        }

        // Create final drink name, e.g., "Horchata (Medium)"
        String displayName = flavor + " (" + sizeLabel + ")";

        // Add the created drink to the order
        order.addItem(new Drink(displayName, price));

        // Confirm addition to the user
        System.out.println("Added: " + displayName + " - $" + price);
        System.out.println("Current total: $" + order.getTotal());
    }

    /**
     * Adds a Chips & Salsa item to the order.
     * Lets the user choose the salsa type from a list.
     */
    private static void addChips(Order order) {

        // Chips & salsa menu
        System.out.println("\n-- Add Chips & Salsa ($1.50) --");
        System.out.println("Choose your salsa type:");
        System.out.println("1) Salsa Verde");
        System.out.println("2) Salsa Roja");
        System.out.println("3) Chipotle");
        System.out.println("4) Habanero");
        System.out.println("5) Mild");
        System.out.println("6) Extra Hot");
        System.out.print("Choose: ");

        String choice = sc.nextLine().trim();
        String salsaType = null;

        // Convert choice into actual salsa name
        switch (choice) {
            case "1":
                salsaType = "Salsa Verde";
                break;

            case "2":
                salsaType = "Salsa Roja";
                break;

            case "3":
                salsaType = "Chipotle";
                break;

            case "4":
                salsaType = "Habanero";
                break;

            case "5":
                salsaType = "Mild";
                break;

            case "6":
                salsaType = "Extra Hot";
                break;

            default:
                System.out.println("Invalid choice. Chips not added.");
                return;
        }

        // Add chips item to the order
        order.addItem(new ChipsAndSalsa(salsaType));

        // Confirm to user
        System.out.println("Added: Chips & " + salsaType + " - $1.50");
        System.out.println("Current total: $" + order.getTotal());
    }
}
