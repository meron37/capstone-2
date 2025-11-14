package com.pluralsight;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Main program for the Taco Shop application.
 * This class controls the main menu (Home Screen), starting new orders,
 * adding tacos / drinks / chips, checking out, and saving receipts.
 */
public class Main {

    // Shared Scanner for reading user input from the console
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to ByteMyTaco! 😊");
        System.out.println("Good food, good vibes — create what you crave. Every choice matters.\n");
        AsciiArt.printLog();   // your ASCII art banner

        // =================== MAIN APPLICATION LOOP (HOME SCREEN) ===================
        // This loop keeps showing the Home Screen until the user chooses Exit (0).
        while (true) {
            // ------------- HOME SCREEN -------------
            System.out.println("\n=== Home Screen ===");
            System.out.println("1) New Order");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            String homeChoice = sc.nextLine().trim();

            switch (homeChoice) {
                case "1" -> startNewOrder(); // Start a brand new customer order
                case "0" -> {
                    System.out.println("Goodbye!");
                    return; // Ends the program
                }
                default -> System.out.println("Invalid choice. Please choose 1 or 0.");
            }
        }
    }

    /**
     * Starts a new order and shows the Order Screen.
     * This method handles the full order flow for one customer
     * (adding items, checkout, or cancel).
     */
    private static void startNewOrder() {

        // --- Ask for a name for the order, like at a real restaurant ---
        System.out.print("What name should we put on this order? ");
        String orderName = sc.nextLine().trim();
        if (orderName.isEmpty()) {
            orderName = "Guest";
        }
        System.out.println("Thanks, " + orderName + "! Let's start your order.");

        // create a new order that starts empty
        Order order = new Order();

        while (true) {
            // --------- ORDER SCREEN (show newest items first) ----------
            System.out.println("\n=== Order Screen ===");

            // Get current items in the order
            List<OrderItem> items = order.getItems();
            if (items.isEmpty()) {
                System.out.println("(No items in this order yet)");
            } else {
                System.out.println("Current Order (" + orderName + ") - newest items first:");
                for (int i = items.size() - 1; i >= 0; i--) {
                    OrderItem item = items.get(i);
                    System.out.println(" - " + item.getName() + "  $" + item.getPrice());
                }
            }

            // Show the order options for this order
            System.out.println("\nOrder Screen:");
            System.out.println("1) Add Taco");
            System.out.println("2) Add Drink");
            System.out.println("3) Add Chips & Salsa");
            System.out.println("4) Checkout");
            System.out.println("0) Cancel Order (go back to Home)");
            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    // FULL FLOW: Taco -> Drink -> Chips -> Checkout -> back to Home
                    // This path gives a full combo experience
                    addTaco(order);
                    addDrink(order);
                    addChips(order);
                    boolean finished = checkoutFlow(order);
                    // after checkout (confirm or cancel) go back to Home Screen
                    return;
                }

                case "2" -> addDrink(order);       // optional: drink only
                case "3" -> addChips(order);       // optional: chips only

                case "4" -> {
                    // Go through checkout; if confirmed, this order is done,
                    // and we go back to the Home Screen.
                    boolean finished = checkoutFlow(order);
                    if (finished) {
                        // confirmed: go back to Home
                        return;
                    }
                    // if not finished, stay on Order Screen
                }

                case "0" -> {
                    System.out.println("Order canceled. Returning to Home Screen.");
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // -------------------- CHECKOUT SCREEN --------------------

    /**
     * Handles the checkout flow:
     * - Shows the receipt
     * - Asks user to Confirm or Cancel
     * - Saves the receipt if confirmed
     *
     * order the current order
     * @return true if the order was confirmed, false if canceled or invalid
     */
    private static boolean checkoutFlow(Order order) {
        if (order.isEmpty()) {
            System.out.println("Order is empty. Add at least one item before checkout.");
            return false;
        }

        System.out.println("\n=== Checkout ===");
        // Print the full receipt to the console
        System.out.println(order.toReceiptString());

        System.out.println("1) Confirm");
        System.out.println("0) Cancel (go back to Order Screen)");
        System.out.print("Choose: ");
        String choice = sc.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.println("Order confirmed! Saving receipt...");
                saveReceipt(order);
                return true;   // order is done

            case "0":
                System.out.println("Checkout canceled. Returning to Order Screen.");
                return false;  // still on this order

            default:
                System.out.println("Invalid choice. Returning to Order Screen.");
                return false;
        }
    }

    // -------------------- DRINK --------------------

    /**
     * Adds a drink to the order.
     * Asks the user for size (which sets the price) and flavor.
     */
    private static void addDrink(Order order) {

        System.out.println("\n===== Add Drink ======");
        System.out.println("1) Small  ($2.00)");
        System.out.println("2) Medium ($2.50)");
        System.out.println("3) Large  ($3.00)");
        System.out.print("Choose: ");

        String sizeChoice = sc.nextLine().trim();
        String sizeLabel;
        BigDecimal price;

        // Map the user’s size choice to a label and price
        switch (sizeChoice) {
            case "1" -> {
                sizeLabel = "Small";
                price = new BigDecimal("2.00");
            }
            case "2" -> {
                sizeLabel = "Medium";
                price = new BigDecimal("2.50");
            }
            case "3" -> {
                sizeLabel = "Large";
                price = new BigDecimal("3.00");
            }
            default -> {
                System.out.println("Invalid size.");
                return;
            }
        }

        // Ask the user for drink flavor
        System.out.print("Enter flavor (e.g., Horchata, Coke): ");
        String flavor = sc.nextLine().trim();
        if (flavor.isEmpty()) flavor = "Drink";

        // Create final display name using flavor + size
        String displayName = flavor + " (" + sizeLabel + ")";

        // Add the created drink to the order (size is also passed)
        order.addItem(new Drink(displayName, price, sizeLabel));
        System.out.println("Added: " + displayName + " - $" + price);
    }

    // -------------------- CHIPS --------------------

    /**
     * Adds a Chips & Salsa item to the order.
     * Lets the user choose the salsa type from a list.
     */
    private static void addChips(Order order) {

        System.out.println("\n ===== Add Chips & Salsa ($1.50) =====");
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

        // Add chips item to the order
        order.addItem(new ChipsAndSalsa(salsaType));
        System.out.println("Added: Chips & " + salsaType + " - $1.50");
    }

    // -------------------- TACO --------------------

    /**
     * Full taco-building flow:
     * - Choose shell
     * - Choose size
     * - Optional deep fried
     * - Meat & extra meat
     * - Cheese & extra cheese
     * - Regular toppings
     * - Sauces
     * Then creates a Taco object and adds it to the order.
     */
    private static void addTaco(Order order) {

        System.out.println("\n ======  Add Taco =====");

        // Shell
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

        // Size
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

        // Show the pricing table for toppings and extras
        printToppingsMenu();

        // Deep fried?
        System.out.print("Would you like the taco deep fried? (y/n): ");
        boolean deepFried = readYesNo();
        if (deepFried) {
            BigDecimal fryCharge = switch (size) {
                case "Single Taco" -> new BigDecimal("0.75");
                case "3-Taco Plate" -> new BigDecimal("1.50");
                case "Burrito" -> new BigDecimal("2.25");
                default -> BigDecimal.ZERO;
            };
            System.out.println("Deep fried added (+" + fryCharge + ")");
        }

        // Meat + extra
        System.out.print("Meat toppings are (carne asada | al pastor | carnitas | pollo | chorizo | pescado). Leave blank for none: ");
        String meat = sc.nextLine().trim();
        boolean extraMeat = false;

        if (!meat.isEmpty()) {
            System.out.print("Extra meat? (y/n): ");
            extraMeat = readYesNo();

            if (extraMeat) {
                BigDecimal extraMeatCharge = switch (size) {
                    case "Single Taco" -> new BigDecimal("0.50");
                    case "3-Taco Plate" -> new BigDecimal("1.00");
                    case "Burrito" -> new BigDecimal("1.50");
                    default -> BigDecimal.ZERO;
                };
                System.out.println("Extra meat added (+" + extraMeatCharge + ")");
            }
        }

        // Cheese + extra
        System.out.print("Cheese (Queso Fresco, Oaxaca, Cotija, Cheddar). Leave blank for none: ");
        String cheese = sc.nextLine().trim();
        boolean extraCheese = false;

        if (!cheese.isEmpty()) {
            System.out.print("Extra cheese? (y/n): ");
            extraCheese = readYesNo();

            if (extraCheese) {
                BigDecimal extraCheeseCharge = switch (size) {
                    case "Single Taco" -> new BigDecimal("0.30");
                    case "3-Taco Plate" -> new BigDecimal("0.60");
                    case "Burrito" -> new BigDecimal("0.90");
                    default -> BigDecimal.ZERO;
                };
                System.out.println("Extra cheese added (+" + extraCheeseCharge + ")");
            }
        }

        // Regular toppings
        System.out.println("Regular toppings (included): lettuce, cilantro, onions, tomatoes, jalapeños, radishes, pico de gallo, guacamole, corn.");
        System.out.print("Enter the toppings you want add (comma-separated) or leave blank: ");
        String toppingsInput = sc.nextLine().trim();
        List<Topping> toppings = new ArrayList<>();

        if (!toppingsInput.isEmpty()) {
            String[] parts = toppingsInput.split(",");
            for (String raw : parts) {
                String name = raw.trim();
                if (!name.isEmpty()) {
                    toppings.add(new Topping(name)); // included, price 0.00
                }
            }
        }

        // Sauces
        System.out.println("Select sauces (for example: Verde, Roja).");
        System.out.print("Enter comma-separated list or leave blank: ");
        String sauces = sc.nextLine().trim();

        // Create Taco object with all the selected options
        Taco taco = new Taco(
                size, shell, deepFried,
                meat, extraMeat,
                cheese, extraCheese,
                toppings, sauces
        );

        // Add taco to the order
        order.addItem(taco);

        // Summary of what was added
        System.out.println("\nAdded Taco:");
        System.out.println("- Name: " + taco.getName());
        System.out.println("- Shell: " + shell);
        System.out.println("- Size: " + size);
        System.out.println("- Deep Fried: " + (deepFried ? "Yes": "No"));
        System.out.println("- Meat: " + (meat.isEmpty() ? "None" : meat + (extraMeat ? " (extra)" : "")));
        System.out.println("- Cheese: " + (cheese.isEmpty() ? "None" : cheese + (extraCheese ? " (extra)" : "")));

        System.out.println("- Toppings:");
        printToppings(toppings);

        System.out.println("- Sauces: " + (sauces.isEmpty() ? "None" : sauces));
        System.out.println("- PRICE (before tax): $" + taco.getPrice());
    }

    // --------- pricing table text ----------
    private static void printToppingsMenu() {
        System.out.println("\n==== Taco Prices ====");
        System.out.println("Shell (corn / flour / hard shell / bowl)");
        System.out.println("  Single:  $3.50");
        System.out.println("  3-Taco:  $9.00");
        System.out.println("  Burrito: $8.50");

        System.out.println("\n==== Toppings (per size) ====");
        System.out.println("Meats (carne asada, al pastor, carnitas, pollo, chorizo, pescado)");
        System.out.println("  Single:  $1.00  |  3-Taco: $2.00  |  Burrito: $3.00");
        System.out.println("\nExtra Meat:");
        System.out.println("  Single:  $0.50  |  3-Taco: $1.00  |  Burrito: $1.50");

        System.out.println("\nCheese (Queso Fresco, Oaxaca, Cotija, Cheddar)");
        System.out.println("  Single:  $0.75  |  3-Taco: $1.50  |  Burrito: $2.25");
        System.out.println("\nExtra Cheese:");
        System.out.println("  Single:  $0.30  |  3-Taco: $0.60  |  Burrito: $0.90");

        System.out.println("\nRegular Toppings (included in price):");
        System.out.println("  lettuce, cilantro, onions, tomatoes, jalapeños,");
        System.out.println("  radishes, pico de gallo, guacamole, corn");
        System.out.println("Deep fried option: Single +$0.75 | 3-Taco +$1.50 | Burrito +$2.25");
        System.out.println("=========================================\n");
    }

    private static void printToppings(List<Topping> toppings) {
        if (toppings.isEmpty()) {
            System.out.println("  • None");
            return;
        }
        for (Topping t : toppings) {
            if (t.getPrice().compareTo(BigDecimal.ZERO) > 0) {
                System.out.println("  • " + t.getName() + " (+" + t.getPrice() + ")");
            } else {
                System.out.println("  • " + t.getName() + " (included)");
            }
        }
    }

    // -------------------- SAVE RECEIPT --------------------
    /**
     * Saves the receipt text to a .txt file in src/main/resources/receipts
     * using a timestamp-based file name.
     */
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

    /**
     * Reads a yes/no answer from the user.
     * Returns true if the user starts the input with 'y' or 'Y'.
     */
    private static boolean readYesNo() {
        String input = sc.nextLine().trim().toLowerCase();
        return input.startsWith("y");
    }
}
