
package com.pluralsight;
import java.math.BigDecimal;
public class Main{
    public static void main(String[] args){
        Drink d = new Drink(("coca (Medium"), new BigDecimal("2.50"));
        System.out.println("Name: " + d.getName());
        System.out.println("Price: $ " + d.getPrice());
    }
}
//
//import java.io.IOException;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Scanner;
//
//public class Main {
//
//    private static final Scanner scanner = new Scanner(System.in);
//
//    public static void main(String[] args) {
//
//        while (true) {
//            System.out.println("=== TACO-licious ===");
//            System.out.println("1) New Order");
//            System.out.println("0) Exit");
//            System.out.print("Choose: ");
//
//            String choice = scanner.nextLine();
//
//            switch (choice) {
//                case "1" -> handleNewOrder();
//                case "0" -> {
//                    System.out.println("Goodbye!");
//                    return;
//                }
//                default -> System.out.println("Invalid choice.\n");
//            }
//        }
//    }
//
//    // ================== ORDER SCREEN ==================
//    private static void handleNewOrder() {
//        Order order = new Order();
//
//        while (true) {
//            System.out.println("\n=== Order Menu ===");
//            System.out.println("Current total: $" + order.getTotal());
//            System.out.println("1) Add Taco");
//            System.out.println("2) Add Drink");
//            System.out.println("3) Add Chips & Salsa");
//            System.out.println("4) Checkout");
//            System.out.println("0) Cancel Order");
//            System.out.print("Choose: ");
//
//            String choice = scanner.nextLine();
//
//            switch (choice) {
////                case "1" -> addTaco(order);
////                case "2" -> addDrink(order);
////                case "3" -> addChips(order);
////                case "4" -> {
// /
//
//
//            }}
//
