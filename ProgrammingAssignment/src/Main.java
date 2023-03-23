import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {

    public void recieveAndCheckId(short clientId){
        Scanner scanner = new Scanner(System.in);
        if(clientId < 1 || clientId > 5){
            while(clientId < 1 || clientId > 5){
                System.out.print("Wrong ID. ID must be from 1 to 5. Please try again: ");
                clientId = scanner.nextShort();
            }
        }
        System.out.println("You're ID is: " + clientId);
    }

    public void recieveAndCheckAmount(int orderedQuantity[]){
        Scanner scanner = new Scanner(System.in);
        for(int i = 0; i < orderedQuantity.length; i++){
            System.out.print("Enter quantity for " + (char)('A' + (i)) + " product: ");
            orderedQuantity[i] = scanner.nextInt();
            if(orderedQuantity[i] < 0){
                while(orderedQuantity[i] < 0){
                    System.out.print("You've entered negative amount of quantity. Quantity must be positive. Please try again: ");
                    orderedQuantity[i] = scanner.nextInt();
                }
            }
        }
    }
   /* public void recieveInput(int orderedQuantity[]){
        Scanner scanner = new Scanner(System.in);

        final short clientId = scanner.nextShort();
        System.out.print("Please input your ID: ");
        recieveAndCheckId(clientId);

        System.out.print("Please enter your desired amount for each product");
        recieveAndCheckAmount(orderedQuantity);
    }*/

    public void calculateUnitsPrice(int orderedQuantity[], BigDecimal markup[], BigDecimal unitCostEUR[], BigDecimal productPromotion[], short clientID) {
        //System.out.println("The amount of each products is ");
        //for (int j = 0; j < orderedQuantity.length; j++){
          //  System.out.print("Units for " + (char)('A' + (i)) + ": ");          //to remove and make chart-looking table
         //   System.out.println(orderedQuantity[i]);
        //}
        BigDecimal totalAfterDiscount = new BigDecimal(0);
        BigDecimal totalBeforeDiscount = new BigDecimal(0) ;
        BigDecimal basicClientDiscount[] = {new BigDecimal(0.05), new BigDecimal(0.04), new BigDecimal(0.03), new BigDecimal(0.02), new BigDecimal(1)};

        for (int i = 0; i < orderedQuantity.length; i++) {
            BigDecimal cost = BigDecimal.ZERO;

            System.out.println();
            System.out.println("Quantity ordered for " + (char)('A' + (i)) + ": " + orderedQuantity[i]);

            System.out.println("Base unit price: " + unitCostEUR[i].setScale(2, BigDecimal.ROUND_HALF_UP));

            if(productPromotion[i].compareTo(BigDecimal.ZERO) == 0){
                System.out.println("No product promotion for " + (char) ('A' + i));
            }else if(i == 3) {
                System.out.println("Product promotion is: Buy two, get one free");
            }else{
                System.out.println("Product promotion is: " + (productPromotion[i].multiply(new BigDecimal(100))).setScale(0, BigDecimal.ROUND_HALF_UP) + "% off."
                + ", on average for 100 items: ");
            }

            if (BigDecimal.valueOf(orderedQuantity[i]).compareTo(BigDecimal.ZERO) == 0) {
                //System.out.println("Quantity ordered: " + orderedQuantity[i]);
            } else {
                if (i == 0) {
                    cost = unitCostEUR[i].add(unitCostEUR[i].multiply(markup[i]));

                } else if (i == 1) {
                    cost = unitCostEUR[i].add(unitCostEUR[i].multiply(markup[i]));
                } else if (i == 2) {
                    cost = unitCostEUR[i].add(markup[i]);
                } else {
                    cost = unitCostEUR[i].add(markup[i]);
                }

                cost = cost.multiply(BigDecimal.valueOf(orderedQuantity[i]));
                //System.out.println("Total price(in EURO) for this product: " + cost.setScale(2, BigDecimal.ROUND_HALF_UP));


                if (productPromotion[i].compareTo(BigDecimal.ZERO) > 0) {
                    if(i == 3){
                        //BigDecimal discount = cost.add(productPromotion[i]);
                        System.out.println("Units " + (char) ('A' + i) + " quantity without promotion after markup: " + orderedQuantity[i]);
                        System.out.println("Units " + (char) ('A' + i) + " quantity with promotion after markup: " + (orderedQuantity[i] +  orderedQuantity[i]/2));
                        orderedQuantity[i] = orderedQuantity[i] +  orderedQuantity[i]/2;

                    }else{
                        BigDecimal discount = cost.divide(BigDecimal.valueOf(3), 2, BigDecimal.ROUND_HALF_UP);
                        //System.out.println("Units " + (char) ('A' + i) + " quantity cost in euro (no discount): " + cost.setScale(2, BigDecimal.ROUND_HALF_UP));
                        cost = cost.subtract(discount);
                        System.out.println("Total price(in EURO) for this product(with discount) after markup: " + cost.setScale(2, BigDecimal.ROUND_HALF_UP));
                    }
                } else {
                    System.out.println("Total price(in EURO) for this product after markup: " + cost.setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                totalBeforeDiscount = totalBeforeDiscount.add(cost);

                //calculating additional volume discount below
                switch (clientID){
                    case 1:
                        if(orderedQuantity[i] <= 10000 && orderedQuantity[i] != 0){
                            totalAfterDiscount = totalAfterDiscount.add(cost.subtract(cost.multiply(basicClientDiscount[0])));
                            System.out.println("workin?: " + totalAfterDiscount.setScale(2, BigDecimal.ROUND_HALF_UP));
                            break;
                        }
                    case 2:
                        if(orderedQuantity[i] <= 10000 && orderedQuantity[i] != 0){
                            totalAfterDiscount = totalAfterDiscount.add(cost.subtract(cost.multiply(basicClientDiscount[1])));
                            break;
                        }
                    case 3:
                        if(orderedQuantity[i] <= 10000 && orderedQuantity[i] != 0){
                            totalAfterDiscount = totalAfterDiscount.add(cost.subtract(cost.multiply(basicClientDiscount[2])));
                            break;
                        }
                    case 4:
                        if(orderedQuantity[i] <= 10000 && orderedQuantity[i] != 0){
                            totalAfterDiscount = totalAfterDiscount.add(cost.subtract(cost.multiply(basicClientDiscount[3])));
                            break;
                        }
                    case 5:
                        if(orderedQuantity[i] <= 10000 && orderedQuantity[i] != 0){
                            if(basicClientDiscount[i].compareTo(BigDecimal.ZERO) == 1){
                                totalAfterDiscount = totalBeforeDiscount;
                                System.out.println(totalBeforeDiscount.setScale(2, BigDecimal.ROUND_HALF_UP));
                            }
                            else{
                                System.out.println("workin?: " + totalAfterDiscount.setScale(2, BigDecimal.ROUND_HALF_UP));
                                totalAfterDiscount = totalAfterDiscount.add(cost.subtract(cost.multiply(basicClientDiscount[4])));
                                break;
                            }
                        }
                }
            }
        }

        System.out.println("id: " + clientID);

        System.out.println();
        System.out.println("Total price for all products before discount: " + totalBeforeDiscount.setScale(2, BigDecimal.ROUND_HALF_UP));
        System.out.println("Total price for all products after client discount: " + totalAfterDiscount.setScale(2, BigDecimal.ROUND_HALF_UP));

    }
    public void additionalVolumeDiscount(short clientId, int orderedQuantity[]){

    }

    public void displayInfo(){

    }

    //make discount of quantityUnitCost[1] 30%;
    //table1
    //base unit price == unit cost(eur)?

    public static void main(String[] args) {
        Main main = new Main();
        //String products[] = {"A","B","C","D"};      //producer's products
        //int clients[] = {1,2,3,4,5};                //producer's clients
        Scanner scanner = new Scanner(System.in);
        int orderedQuantity[] = new int[4];

        BigDecimal unitCostEUR[] = { new BigDecimal(0.52), new BigDecimal(0.38), new BigDecimal(0.41), new BigDecimal(0.60) };
        BigDecimal markup[] = { new BigDecimal(0.8), new BigDecimal(1.2), new BigDecimal(0.9), new BigDecimal(1) };
        BigDecimal productPromotion[] = { new BigDecimal(0), new BigDecimal(0.3), new BigDecimal(0), BigDecimal.valueOf(orderedQuantity[3]/2) };

        BigDecimal quantityUnitCost[] = new BigDecimal[4];

        System.out.print("Please input your ID: ");
        final short clientId = scanner.nextShort();

        main.recieveAndCheckId(clientId);

        System.out.print("Please enter your desired amount for each product");
        main.recieveAndCheckAmount(orderedQuantity);
        main.calculateUnitsPrice(orderedQuantity, markup, unitCostEUR, productPromotion, clientId);


    }
}