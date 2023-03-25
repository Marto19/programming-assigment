import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main() {
    }

    public short recieveAndCheckId(short clientId) {
        for(Scanner scanner = new Scanner(System.in); clientId < 1 || clientId > 5; clientId = scanner.nextShort()) {
            System.out.print("Wrong ID. ID must be from 1 to 5. Please try again: ");
        }

        return clientId;
    }

    public void recieveAndCheckAmount(int[] orderedQuantity) {
        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i < orderedQuantity.length; ++i) {
            System.out.print("Enter quantity for " + (char)(65 + i) + " product: ");
            orderedQuantity[i] = scanner.nextInt();
            if (orderedQuantity[i] < 0) {
                while(orderedQuantity[i] < 0) {
                    System.out.print("You've entered negative amount of quantity. Quantity must be positive. Please try again: ");
                    orderedQuantity[i] = scanner.nextInt();
                }
            }
        }

    }

    public void calculateUnitsPrice(int[] orderedQuantity, BigDecimal[] markup, BigDecimal[] unitCostEUR, BigDecimal[] productPromotion, short clientID) {
        BigDecimal totalAfterDiscount = new BigDecimal(0);
        BigDecimal totalBeforeDiscount = new BigDecimal(0);
        BigDecimal[] basicClientDiscount = new BigDecimal[]{new BigDecimal(0.05), new BigDecimal(0.04), new BigDecimal(0.03), new BigDecimal(0.02), new BigDecimal(1)};

        for(int i = 0; i < orderedQuantity.length; ++i) {
            BigDecimal cost = BigDecimal.ZERO;
            System.out.println();
            System.out.print("Quantity ordered for " + (char)(65 + i) + ": " + orderedQuantity[i] + " | ");
            System.out.print("Base unit price: " + unitCostEUR[i].setScale(2, 4) + " | ");
            if (productPromotion[i].compareTo(BigDecimal.ZERO) == 0) {
                System.out.print("No product promotion for " + (char)(65 + i) + " | ");
            } else if (i == 3) {
                System.out.print("Product promotion is: Buy two, get one free | ");
            } else {
                PrintStream var10000 = System.out;
                BigDecimal var10001 = productPromotion[i].multiply(new BigDecimal(100));
                var10000.print("Product promotion is: " + var10001.setScale(0, 4) + "% off. | ");
            }

            if (i == 0) {
                cost = unitCostEUR[i].add(unitCostEUR[i].multiply(markup[i]));
            } else if (i == 1) {
                cost = unitCostEUR[i].add(unitCostEUR[i].multiply(markup[i]));
            } else if (i == 2) {
                cost = unitCostEUR[i].add(markup[i]);
            } else {
                cost = unitCostEUR[i].add(markup[i]);
            }

            cost = cost.multiply(BigDecimal.valueOf((long)orderedQuantity[i]));
            if (productPromotion[i].compareTo(BigDecimal.ZERO) > 0) {
                if (i == 3) {
                    System.out.println("Units " + (char)(65 + i) + " quantity without promotion: " + orderedQuantity[i]);
                    System.out.println("Units " + (char)(65 + i) + " quantity with promotion: " + (orderedQuantity[i] + orderedQuantity[i] / 2));
                    orderedQuantity[i] += orderedQuantity[i] / 2;
                } else {
                    cost = cost.subtract(cost.multiply(productPromotion[i]));
                    System.out.println("Total price(in EURO) for this product(with discount) after markup: " + cost.setScale(2, 4));
                }
            } else {
                System.out.println("Total price(in EURO) for this product after markup: " + cost.setScale(2, 4));
            }

            totalBeforeDiscount = totalBeforeDiscount.add(cost);
            switch (clientID) {
                case 1:
                    if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) < 0) {
                        totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(basicClientDiscount[0]));
                    } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) > 0) {
                        totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.02)));
                    } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) > 0 && totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) < 0) {
                        totalAfterDiscount = totalBeforeDiscount;
                    }
                    break;
                case 2:
                    if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) < 0) {
                        totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(basicClientDiscount[1]));
                    } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) > 0) {
                        totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.02)));
                    } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) > 0 && totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) < 0) {
                        totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.01)));
                    }
                    break;
                case 3:
                    if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) < 0) {
                        totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(basicClientDiscount[2]));
                    } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) > 0) {
                        totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.03)));
                    } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) > 0 && totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) < 0) {
                        totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.01)));
                    }
                    break;
                case 4:
                    if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) < 0) {
                        totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(basicClientDiscount[3]));
                    } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) > 0) {
                        totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.05)));
                    } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) > 0 && totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) < 0) {
                        totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.03)));
                    }
                    break;
                case 5:
                    if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) < 0) {
                        if (basicClientDiscount[4].compareTo(BigDecimal.valueOf(1L)) == 0) {
                            totalAfterDiscount = totalBeforeDiscount;
                        } else {
                            totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(basicClientDiscount[4]));
                        }
                    } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) > 0) {
                        totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.07)));
                    } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) > 0 && totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) < 0) {
                        totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.05)));
                    }
                    break;
                default:
                    System.out.println("Invalid client ID");
            }
        }

        System.out.println();
        System.out.println("Total price for all products before client discount: " + totalBeforeDiscount.setScale(2, 4));
        switch (clientID) {
            case 1:
                if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) < 0) {
                    System.out.println("Basic client discount of 5%");
                } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) > 0) {
                    System.out.println("Additional volume discount for total above 30000: 2%");
                } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) > 0 && totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) < 0) {
                    System.out.println("Additional volume discount for total above 10000 and below 30000: 0%");
                }
                break;
            case 2:
                if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) < 0) {
                    System.out.println("Basic client discount of 4%");
                } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) > 0) {
                    System.out.println("Additional volume discount for total above 30000: 2%");
                } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) > 0 && totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) < 0) {
                    System.out.println("Additional volume discount for total above 10000 and below 30000: 1%");
                }
                break;
            case 3:
                if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) < 0) {
                    System.out.println("Basic client discount of 3%");
                } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) > 0) {
                    System.out.println("Additional volume discount for total above 30000: 3%");
                } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) > 0 && totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) < 0) {
                    System.out.println("Additional volume discount for total above 10000 and below 30000: 1%");
                }
                break;
            case 4:
                if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) < 0) {
                    System.out.println("Basic client discount of 2%");
                } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) > 0) {
                    System.out.println("Additional volume discount for total above 30000: 5%");
                } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) > 0 && totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) < 0) {
                    System.out.println("Additional volume discount for total above 10000 and below 30000: 3%");
                }
                break;
            case 5:
                if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) < 0) {
                    System.out.println("Basic client discount of 0%");
                } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) > 0) {
                    System.out.println("Additional volume discount for total above 30000: 7%");
                } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000L)) > 0 && totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000L)) < 0) {
                    System.out.println("Additional volume discount for total above 10000 and below 30000: 5%");
                }
                break;
            default:
                System.out.println("Invalid client ID");
        }

        System.out.println("Total price for all products after every discount: " + totalAfterDiscount.setScale(2, 4));
    }

    public static void main(String[] args) {
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);
        int[] orderedQuantity = new int[4];
        BigDecimal[] unitCostEUR = new BigDecimal[]{new BigDecimal(0.52), new BigDecimal(0.38), new BigDecimal(0.41), new BigDecimal(0.6)};
        BigDecimal[] markup = new BigDecimal[]{new BigDecimal(0.8), new BigDecimal(1.2), new BigDecimal(0.9), new BigDecimal(1)};
        BigDecimal[] productPromotion = new BigDecimal[]{new BigDecimal(0), new BigDecimal(0.3), new BigDecimal(0), BigDecimal.valueOf((long)(orderedQuantity[3] / 2))};
        System.out.print("Please input your ID: ");
        short clientId = scanner.nextShort();
        clientId = main.recieveAndCheckId(clientId);
        System.out.println("Please enter your desired amount for each product");
        main.recieveAndCheckAmount(orderedQuantity);
        main.calculateUnitsPrice(orderedQuantity, markup, unitCostEUR, productPromotion, clientId);
    }
}
