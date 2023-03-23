import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {

    public short recieveAndCheckId(short clientId) {
        Scanner scanner = new Scanner(System.in);
        while (clientId < 1 || clientId > 5) {
            System.out.print("Wrong ID. ID must be from 1 to 5. Please try again: ");
            clientId = scanner.nextShort();
        }
        //System.out.println("Your ID is: " + clientId);
        return clientId;
    }

    public void recieveAndCheckAmount(int orderedQuantity[]){                                                           //method to receive and check if ordered amount is a valid number
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < orderedQuantity.length; i++){                                                                //for loop that goes over each product
            System.out.print("Enter quantity for " + (char)('A' + (i)) + " product: ");
            orderedQuantity[i] = scanner.nextInt();                                                                     //entering a value
            if(orderedQuantity[i] < 0){                                                                                 //checks if number is negative
                while(orderedQuantity[i] < 0){                                                                          //if negative and while negative
                    System.out.print("You've entered negative amount of quantity. Quantity must be positive. Please try again: ");
                    orderedQuantity[i] = scanner.nextInt();                                                             //enter again and again
                }
            }
        }
    }

    public void calculateUnitsPrice(int orderedQuantity[], BigDecimal markup[], BigDecimal unitCostEUR[], BigDecimal productPromotion[], short clientID) {  //method to calculate units price

        BigDecimal totalAfterDiscount = new BigDecimal(0);
        BigDecimal totalBeforeDiscount = new BigDecimal(0) ;
        BigDecimal basicClientDiscount[] = {new BigDecimal(0.05), new BigDecimal(0.04), new BigDecimal(0.03), new BigDecimal(0.02), new BigDecimal(1)};

        for (int i = 0; i < orderedQuantity.length; i++) {                                                              //for loop, to go over every product
            BigDecimal cost = BigDecimal.ZERO;

            System.out.println();
            System.out.print("Quantity ordered for " + (char)('A' + (i)) + ": " + orderedQuantity[i] + " | ");                //prints ordered quantity

            System.out.print("Base unit price: " + unitCostEUR[i].setScale(2, BigDecimal.ROUND_HALF_UP) + " | ");     //prints base unit price
            //below its says wether it has product promotion or not
            if(productPromotion[i].compareTo(BigDecimal.ZERO) == 0){                                                    //checks if value of index i of product promotion is 0. if so, no promotion
                System.out.print("No product promotion for " + (char) ('A' + i) + " | ");
            }else if(i == 3) {                                                                                          //if index is 3, the product promotion is a message
                System.out.print("Product promotion is: Buy two, get one free | ");
            }else{                                                                                                      //else, print the product promotion in percentage
                System.out.print("Product promotion is: " + (productPromotion[i].multiply(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_HALF_UP) + "% off."
                + " | ");
            }

            //these if elses calculates the cost with markup for each product
                if (i == 0) {
                    cost = unitCostEUR[i].add(unitCostEUR[i].multiply(markup[i]));
                } else if (i == 1) {
                    cost = unitCostEUR[i].add(unitCostEUR[i].multiply(markup[i]));
                } else if (i == 2) {
                    cost = unitCostEUR[i].add(markup[i]);
                } else {
                    cost = unitCostEUR[i].add(markup[i]);
                }

                //now that we have the cost with markup calculated, below we calculate the cost for the quantity with markup now
                cost = cost.multiply(BigDecimal.valueOf(orderedQuantity[i]));
                //System.out.println("Total price(in EURO) for this product: " + cost.setScale(2, BigDecimal.ROUND_HALF_UP));

                //below, the product promotion is applied if there is one
                if (productPromotion[i].compareTo(BigDecimal.ZERO) > 0) {               //if the product promotion is bigger than 0 execute the code below
                    if(i == 3){                                                         //if the index = 3, we are at the "Buy 2, get 3rd free" prmotion
                        System.out.println("Units " + (char) ('A' + i) + " quantity without promotion: " + orderedQuantity[i]);            //quantity before promotion
                        System.out.println("Units " + (char) ('A' + i) + " quantity with promotion: " + (orderedQuantity[i] +  orderedQuantity[i]/2));
                        orderedQuantity[i] = orderedQuantity[i] +  orderedQuantity[i]/2;     //quantity being updated

                    }else{  // if the other IDEXES
                        cost = cost.subtract(cost.multiply(productPromotion[i]));
                        System.out.println("Total price(in EURO) for this product(with discount) after markup: " + cost.setScale(2, BigDecimal.ROUND_HALF_UP));
                    }
                } else {    //if product promotion is 0/none
                    System.out.println("Total price(in EURO) for this product after markup: " + cost.setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                totalBeforeDiscount = totalBeforeDiscount.add(cost);

                //calculating additional volume discount below
                switch (clientID) {
                    case 1:
                        if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) < 0) {                                 //checks if total is below 10000
                            totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(basicClientDiscount[0]));
                        } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) > 0) {                              //checks if total is above 30000

                            totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.02)));

                        } else if( (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) > 0) && (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) < 0)){
                            totalAfterDiscount = totalBeforeDiscount;                                                   //checks if total is above 10000 and below 30000
                        }
                        break;
                    case 2:
                        if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) < 0) {                                  //checks if total is below 10000
                            totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(basicClientDiscount[1]));
                        } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) > 0) {                                      //checks if total is above 30000

                            totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.02)));

                        } else if( (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) > 0) && (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) < 0)){
                            totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.01)));      //checks if total is above 10000 and below 30000
                        }
                        break;
                    case 3:
                        if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) < 0) {                                      //checks if total is below 10000
                            totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(basicClientDiscount[2]));
                        } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) > 0) {                                   //checks if total is above 30000

                            totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.03)));

                        } else if( (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) > 0) && (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) < 0)){
                            totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.01)));      //checks if total is above 10000 and below 30000
                        }
                        break;
                    case 4:
                        if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) < 0) {                                          //checks if total is below 10000
                            totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(basicClientDiscount[3]));
                        } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) > 0) {                                  //checks if total is above 30000

                            totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.05)));

                        } else if( (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) > 0) && (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) < 0)){
                            totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.03)));      //checks if total is above 10000 and below 30000
                        }
                        break;
                    case 5:                                                                                                          //checks if total is below 10000
                        if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) < 0) {
                            if (basicClientDiscount[4].compareTo(BigDecimal.valueOf(1)) == 0){
                                totalAfterDiscount = totalBeforeDiscount;
                            }else{
                                totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(basicClientDiscount[4]));
                            }
                        } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) > 0) {                                      //checks if total is above 30000

                            totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.07)));

                        } else if( (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) > 0) && (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) < 0)){
                            totalAfterDiscount = totalBeforeDiscount.subtract(totalBeforeDiscount.multiply(new BigDecimal(0.05)));       //checks if total is above 10000 and below 30000
                        }
                        break;
                    default:
                        System.out.println("Invalid client ID");
                }
        }

        System.out.println();
        System.out.println("Total price for all products before client discount: " + totalBeforeDiscount.setScale(2, BigDecimal.ROUND_HALF_UP));
        //below is the client discount table
        switch (clientID) {
            case 1:
                if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) < 0) {                                 //checks if total is below 10000
                    System.out.println("Basic client discount of 5%");
                } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) > 0) {                              //checks if total is above 30000

                    System.out.println("Additional volume discount for total above 30000: 2%");

                } else if( (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) > 0) && (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) < 0)){
                    System.out.println("Additional volume discount for total above 10000 and below 30000: 0%");
                }
                break;
            case 2:
                if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) < 0) {                                  //checks if total is below 10000
                    System.out.println("Basic client discount of 4%");
                } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) > 0) {                                      //checks if total is above 30000

                    System.out.println("Additional volume discount for total above 30000: 2%");

                } else if( (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) > 0) && (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) < 0)){
                    System.out.println("Additional volume discount for total above 10000 and below 30000: 1%");
                }
                break;
            case 3:
                if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) < 0) {                                      //checks if total is below 10000
                    System.out.println("Basic client discount of 3%");
                } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) > 0) {                                   //checks if total is above 30000

                    System.out.println("Additional volume discount for total above 30000: 3%");

                } else if( (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) > 0) && (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) < 0)){
                    System.out.println("Additional volume discount for total above 10000 and below 30000: 1%");
                }
                break;
            case 4:
                if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) < 0) {                                          //checks if total is below 10000
                    System.out.println("Basic client discount of 2%");
                } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) > 0) {                                  //checks if total is above 30000

                    System.out.println("Additional volume discount for total above 30000: 5%");

                } else if( (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) > 0) && (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) < 0)){
                    System.out.println("Additional volume discount for total above 10000 and below 30000: 3%");
                }
                break;
            case 5:                                                                                                          //checks if total is below 10000
                if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) < 0) {
                    System.out.println("Basic client discount of 0%");
                } else if (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) > 0) {                                      //checks if total is above 30000

                    System.out.println("Additional volume discount for total above 30000: 7%");

                } else if( (totalBeforeDiscount.compareTo(BigDecimal.valueOf(10000)) > 0) && (totalBeforeDiscount.compareTo(BigDecimal.valueOf(30000)) < 0)){
                    System.out.println("Additional volume discount for total above 10000 and below 30000: 5%");
                }
                break;
            default:
                System.out.println("Invalid client ID");
        }

        System.out.println("Total price for all products after every discount: " + totalAfterDiscount.setScale(2, BigDecimal.ROUND_HALF_UP));   //TOTAL AFTER EVERY DISCOUNT

    }


    public static void main(String[] args) {
        Main main = new Main();

        Scanner scanner = new Scanner(System.in);
        int orderedQuantity[] = new int[4];         //an array for the ordered quality for 4 products - A,B,C,D

        //arrays to store values with same functionality
        BigDecimal unitCostEUR[] = { new BigDecimal(0.52), new BigDecimal(0.38), new BigDecimal(0.41), new BigDecimal(0.60) };      //unit cost in euro
        BigDecimal markup[] = { new BigDecimal(0.8), new BigDecimal(1.2), new BigDecimal(0.9), new BigDecimal(1) };
        BigDecimal productPromotion[] = { new BigDecimal(0.5), new BigDecimal(0.5), new BigDecimal(0), BigDecimal.valueOf(orderedQuantity[3]/2) };


        System.out.print("Please input your ID: ");
        short clientId = scanner.nextShort();                           //final ID, so it cannot be changed, and short for 1-5

        clientId = main.recieveAndCheckId(clientId);                                     //1. receive and check ID. correct ID if necessary
        final short finalClientId = clientId;                                               //makes the ID unchangable

        System.out.println("Please enter your desired amount for each product");

        main.recieveAndCheckAmount(orderedQuantity);                          //2. receive and check amount

        main.calculateUnitsPrice(orderedQuantity, markup, unitCostEUR, productPromotion, finalClientId);     //3. calculate units price


    }
}