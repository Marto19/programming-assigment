import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {

    public void recieveAndCheckId(short clientId){
        Scanner scanner = new Scanner(System.in);
        clientId = scanner.nextShort();
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
        System.out.println("The amount of each products is ");
        for (int i = 0; i < orderedQuantity.length; i++){
            System.out.print("Units for " + (char)('A' + (i)) + ": ");          //to remove and make chart-looking table
            System.out.println(orderedQuantity[i]);
        }
    }
    public void recieveInput(int orderedQuantity[]){
        Scanner scanner = new Scanner(System.in);

        final short clientId = scanner.nextShort();
        System.out.print("Please input your ID: ");
        recieveAndCheckId(clientId);

        System.out.print("Please enter your desired amount for each product");
        recieveAndCheckAmount(orderedQuantity);
    }

    public void calculateUnitsPrice(int orderedQuantity[]){
        BigDecimal unitCostEUR[] = {new BigDecimal(0.52), new BigDecimal(0.38), new BigDecimal(0.41), new BigDecimal(0.60)};
        //add arrays for Markup and Product Promotion, so that they can be changed
        BigDecimal Markup[] = {new BigDecimal(0.8), new BigDecimal(1.2), new BigDecimal(0.9), new BigDecimal(1)};

        BigDecimal quantityUnitCost[] = new BigDecimal[4];      //unit cost for every product, so 4. Here we save the data for the quantity unit cost;
        System.out.println();

        quantityUnitCost[0] = (unitCostEUR[0].add(unitCostEUR[0].multiply(Markup[0])));  //0.52 + 0.52 x 80%
        System.out.println("Unit A cost in euro:" + quantityUnitCost[0].setScale(2, BigDecimal.ROUND_HALF_UP));
        quantityUnitCost[0] = quantityUnitCost[0].multiply(BigDecimal.valueOf(orderedQuantity[0]));    //orderedQuantity * (0.52 + 0.52 x 80%)
        quantityUnitCost[0] = quantityUnitCost[0].setScale(2, BigDecimal.ROUND_HALF_UP);       //display with two decimal points
        System.out.println("Unit A quantity cost in euro:" + quantityUnitCost[0]);
        System.out.println();

        quantityUnitCost[1] = (unitCostEUR[1].add(unitCostEUR[1].multiply(Markup[1])));  //0.38 + 0.38 x 120%
        System.out.println("Unit B cost in euro:" + quantityUnitCost[1].setScale(2, BigDecimal.ROUND_HALF_UP));
        quantityUnitCost[1] = quantityUnitCost[1].multiply(BigDecimal.valueOf(orderedQuantity[1]));    //orderedQuantity * (0.38 + 0.38 x 120%)
        quantityUnitCost[1] = quantityUnitCost[1].setScale(2, BigDecimal.ROUND_HALF_UP);       //display with two decimal points
        System.out.println("Unit B quantity cost in euro without discount:" + quantityUnitCost[1]);
        System.out.println("Unit B quantity cost in euro with discount:" + (quantityUnitCost[1].subtract((quantityUnitCost[1].multiply(new BigDecimal(0.3))).setScale(2, RoundingMode.HALF_UP))));
        System.out.println();

        quantityUnitCost[2] = (unitCostEUR[2].add(Markup[2]));
        System.out.println("Unit C cost in euro:" + quantityUnitCost[2].setScale(2, BigDecimal.ROUND_HALF_UP)); //0.41 + 0.9
        quantityUnitCost[2] = quantityUnitCost[2].multiply(BigDecimal.valueOf(orderedQuantity[3]));    //orderedQuantity * (0.41 + 0.9)
        quantityUnitCost[2] = quantityUnitCost[2].setScale(2, BigDecimal.ROUND_HALF_UP);       //display with two decimal points
        System.out.println("Unit C quantity cost in euro without discount:" + quantityUnitCost[2]);
        System.out.println();
        //System.out.println(quantityUnitCost[0].add(quantityUnitCost[1]).add(quantityUnitCost[2]));
    }

    //make discount of quantityUnitCost[1] 30%;
    //table1
    //base unit price == unit cost(eur)?

    public static void main(String[] args) {
        Main main = new Main();
        String products[] = {"A","B","C","D"};      //producer's products
        int clients[] = {1,2,3,4,5};                //producer's clients
        int orderedQuantity[] = new int[4];

        main.recieveInput(orderedQuantity);
        main.calculateUnitsPrice(orderedQuantity);
    }
}