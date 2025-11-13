package com.pluralsight;

import java.math.BigDecimal;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Main program for the Taco Shop application.
 * Allows users to add drinks, chips & salsa, custom tacos,
 * checkout, and saves a receipt file into the resources/receipts folder.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Order order = new Order();

        while (true) {
            System.out.println("\n=== Taco Shop Menu ===");
            System.out.println("1) Add Drink");
            System.out.println("2) Add Chips & Salsa");
            System.out.println("3) Add Taco");
            System.out.println("4) Checkout");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    addDrink(order);
                    break;

                case "2":
                    addChips(order);
                    break;

                case "3":
                    addTaco(order);      // ← now interactive
                    break;

                case "4":
                    if (order.isEmpty()) {
                        System.out.println("Order is empty. Add at least one item please select chipsAndSalsa or drink.");
                    } else {
                        order.printReceipt(); // print on screen
                        saveReceipt(order);   // save to file
                        order = new Order();  // reset for next order
                    }
                    break;

                case "0":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    // --------------------------------------------------------------------
    // ADD DRINK
    // --------------------------------------------------------------------
    private static void addDrink(Order order) {

        System.out.println("\n-- Add Drink --");
        System.out.println("1) Small  ($2.00)");
        System.out.println("2) Medium ($2.50)");
        System.out.println("3) Large  ($3.00)");
        System.out.print("Choose: ");

        String sizeChoice = sc.nextLine().trim();
        String sizeLabel;
        BigDecimal price;

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
                System.out.println("Invalid size.");
                return;
        }

        System.out.print("Enter flavor (e.g., Horchata, Coke): ");
        String flavor = sc.nextLine().trim();
        if (flavor.isEmpty()) flavor = "Drink";

        String displayName = flavor + " (" + sizeLabel + ")";

        order.addItem(new Drink(displayName, price, sizeLabel));
        System.out.println("Added: " + displayName + " - $" + price);
    }

    // --------------------------------------------------------------------
    // ADD CHIPS & SALSA
    // --------------------------------------------------------------------
    private static void addChips(Order order) {

        System.out.println("\n-- Add Chips & Salsa ($1.50) --");
        System.out.println("1) Salsa Verde");
        System.out.println("2) Salsa Roja");
        System.out.println("3) Chipotle");
        System.out.println("4) Habanero");
        System.out.println("5) Mild");
        System.out.println("6) Extra Hot");
        System.out.print("Choose: ");

        String salsaType = switch (sc.nextLine().trim()) {
            case "1" -> "Salsa Verde";
            case "2" -> "Salsa Roja";
            case "3" -> "Chipotle";
            case "4" -> "Habanero";
            case "5" -> "Mild";
            case "6" -> "Extra Hot";
            default -> null;
        };

        if (salsaType == null) {
            System.out.println("Invalid choice.");
            return;
        }

        order.addItem(new ChipsAndSalsa(salsaType));
        System.out.println("Added: Chips & " + salsaType + " - $1.50");
    }

    // ADD TACO (FULLY INTERACTIVE CUSTOMTACO)
    // --------------------------------------------------------------------
    private static void addTaco(Order order) {

        System.out.println("\n-- Add Taco --");

        // Choose shell
        System.out.println("Select your shell:");
        System.out.println("1) Corn");
        System.out.println("2) Flour");
        System.out.println("3) Hard shell");
        System.out.println("4) Bowl");
        System.out.print("Choose: ");
        String shellChoice = sc.nextLine().trim();

        String shell = switch (shellChoice) {
            case "1" -> "Corn";
            case "2" -> "Flour";
            case "3" -> "Hard shell";
            case "4" -> "Bowl";
            default -> null;
        };
        if (shell == null) {
            System.out.println("Invalid shell choice.");
            return;
        }

        // Choose size
        System.out.println("Select taco size:");
        System.out.println("1) Single Taco");
        System.out.println("2) 3-Taco Plate");
        System.out.println("3) Burrito");
        System.out.print("Choose: ");
        String sizeChoice = sc.nextLine().trim();

        String size = switch (sizeChoice) {
            case "1" -> "Single Taco";
            case "2" -> "3-Taco Plate";
            case "3" -> "Burrito";
            default -> null;
        };
        if (size == null) {
            System.out.println("Invalid size choice.");
            return;
        }

        //  Deep fried?
        System.out.print("Would you like the taco deep fried? (y/n): ");
        boolean deepFried = readYesNo();

        //  Meat + extra
        System.out.print("Meat (carne asada, al pastor, etc.). Leave blank for none: ");
        String meat = sc.nextLine().trim();
        boolean extraMeat = false;
        if (!meat.isEmpty()) {
            System.out.print("Extra meat? (y/n): ");
            extraMeat = readYesNo();
        }

        // Cheese + extra
        System.out.print("Cheese (Queso Fresco, Cheddar, etc.). Leave blank for none: ");
        String cheese = sc.nextLine().trim();
        boolean extraCheese = false;
        if (!cheese.isEmpty()) {
            System.out.print("Extra cheese? (y/n): ");
            extraCheese = readYesNo();
        }

        // Other toppings
        System.out.println("Other toppings (lettuce, cilantro, onions, etc.).");
        System.out.print("Enter comma-separated list or leave blank: ");
        String otherToppings = sc.nextLine().trim();

        //  Sauces
        System.out.println("Select sauces (for example: Verde, Roja).");
        System.out.print("Enter comma-separated list or leave blank: ");
        String sauces = sc.nextLine().trim();

        // Create the CustomTaco with all user choices
        CustomTaco taco = new CustomTaco(
                size, shell, deepFried,
                meat, extraMeat,
                cheese, extraCheese,
                otherToppings, sauces
        );

        order.addItem(taco);

        System.out.println("Added Taco:");
        System.out.println("- " + taco.getName());
        System.out.println("- Price: $" + taco.getPrice());
    }

    // --------------------------------------------------------------------
    // SAVE RECEIPT TO src/main/resources/receipts FOLDER (WRITER)

    private static void saveReceipt(Order order) {
        try {
            Path dir = Paths.get("src", "main", "resources", "receipts");
            Files.createDirectories(dir);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
            String timestamp = LocalDateTime.now().format(formatter);
            Path file = dir.resolve(timestamp + ".txt");

            try (java.io.BufferedWriter writer = Files.newBufferedWriter(file)) {
                writer.write(order.toReceiptString());
            }

            System.out.println("Receipt saved to: " + file);

        } catch (IOException e) {
            System.out.println("Could not save receipt: " + e.getMessage());
        }
    }

    // --------------------------------------------------------------------

    private static boolean readYesNo() {
        String input = sc.nextLine().trim().toLowerCase();
        // treat anything starting with 'y' as yes
        return input.startsWith("y");
    }
}
