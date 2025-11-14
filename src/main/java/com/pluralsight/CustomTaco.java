//package com.pluralsight;
//
//import java.math.BigDecimal;
//
///**
// * CustomTaco represents a fully customizable taco.
// * Price is based on size, shell, extra meat, extra cheese,
// * and whether the taco is deep fried.
// */
//public class CustomTaco extends Taco {
//
//    // Base prices for each size
//    private static final BigDecimal SINGLE_BASE  = new BigDecimal("3.00"); // BigDecimal handle well for he decimal/ $
//    private static final BigDecimal PLATE_BASE   = new BigDecimal("7.50");
//    private static final BigDecimal BURRITO_BASE = new BigDecimal("6.00");
//
//    // Extra ingredient charges
//    private static final BigDecimal EXTRA_MEAT_PRICE   = new BigDecimal("1.00");
//    private static final BigDecimal EXTRA_CHEESE_PRICE = new BigDecimal("0.50");
//    private static final BigDecimal DEEP_FRY_PRICE     = new BigDecimal("0.75");
//
//
//    /*
//    Since Taco(super class) has protected  no need to declare the variables. CustomTaco extend Taco it automatically gets
//    */
//
//    public CustomTaco(String size,
//                      String shell,
//                      boolean deepFried,
//                      String meat,
//                      boolean extraMeat,
//                      String cheese,
//                      boolean extraCheese,
//                      String otherToppings,
//                      String sauces) {
//
//        super(size, shell, deepFried,
//                meat, extraMeat,
//                cheese, extraCheese,
//                otherToppings, sauces);
//    }
//
//    /**
//     * Calculates the total taco price using:
//     * - Base size price
//     * - Extra meat
//     * - Extra cheese
//     * - Deep fry charge
//     *
//     * @return Final price as BigDecimal
//     */
//    @Override
//    public BigDecimal getPrice() {
//        BigDecimal total = BigDecimal.ZERO;
//
//        // Base price depending on size
//        switch (size) {
//            case "Single Taco":
//                total = total.add(SINGLE_BASE);
//                break;
//
//            case "3-Taco Plate":
//                total = total.add(PLATE_BASE);
//                break;
//
//            case "Burrito":
//                total = total.add(BURRITO_BASE);
//                break;
//
//            default:
//                total = total.add(SINGLE_BASE); // fallback
//                break;
//        }
//
//        // Extra meat cost
//        if (extraMeat) {
//            total = total.add(EXTRA_MEAT_PRICE);
//        }
//
//        // Extra cheese cost
//        if (extraCheese) {
//            total = total.add(EXTRA_CHEESE_PRICE);
//        }
//
//        // Deep frying cost
//        if (deepFried) {
//            total = total.add(DEEP_FRY_PRICE);
//        }
//
//        return total;
//    }
//}
