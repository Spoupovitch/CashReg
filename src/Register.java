import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Register {

    private final double tax = 0.07;
    private double subtotal;

    Scanner scan = new Scanner(System.in);
    private ArrayList<Item> list = new ArrayList<>();
    public static ArrayList<Item> inventory = new ArrayList<>();

    public Register() {
        subtotal = 0;
        buildInventory();
    }
    //1. enter loop to scan in items
    public void scanItems() {
        String str;
        int qty;
        boolean loop = true, found = false;

        System.out.println("Type items to sell one at a time.\n"
                + "\tEnter /v to void last transaction.\n"
                + "\tEnter /t to finish ringing items in.\n");
        while (loop) {
            System.out.println("Scanning items...");
            str = scan.next();
            if (str.equals("/v")) {
                voidLastTrans();
            }
            else if (str.equals("/t")) {
                loop = false;
            }
            else {
                //check for valid input
                if (Item.getItem(str) == null)
                {
                    continue;
                }
                for (Item tmp : list) {
                        found = false;
                        //add to qty of item present in list
                        if (str.equals(tmp.name)) {
                            found = true;
                            System.out.print("Quantity to sell: ");
                            try {
                                qty = scan.nextInt();
                                while(qty < 0)
                                {
                                    System.out.println("Enter a valid positive number you degenerate:");
                                    qty = scan.nextInt();
                                }
                                tmp.quant += qty;
                            }
                            //add 1 to quantity if exception thrown
                            catch (InputMismatchException ex) {
                                tmp.quant += qty = 1;
                            }
                            //add item's value to subtotal
                            sell(tmp, qty);
                            break;
                        }
                    }
                //add new item to list
                if (!found) {
                    list.add(Item.getItem(str));
                    int last = list.size() - 1;
                    System.out.print("Quantity to sell: ");
                    try {
                        qty = scan.nextInt();
                        while(qty < 0)
                        {
                            System.out.println("Enter a valid positive number you degenerate:");
                            qty = scan.nextInt();
                        }
                        list.get(last).quant += qty;
                    }
                    //add 1 to quantity if exception thrown
                    catch (InputMismatchException ex) {
                        list.get(last).quant += qty = 1;
                    }
                    //add item's value to subtotal
                    sell(list.get(last), qty);
                }
            }
        }
    }
    //add item value to receipt based on input quantity
    public void sell(Item i, int qty) {
        subtotal += i.price * qty;
    }
    //subtract item value from receipt based on input quantity
    public void voidItem(Item i, int qty) {
        subtotal -= i.price * qty;
    }
    //2. ring up all items
    public void checkout() {
        System.out.println("Would you like a receipt? Yes or No");
        String str = scan.next();

        if( str.equals("Yes") || str.equals("yes"))
        {
            printReceipt();
            System.out.println("Thanks for shopping here ya fuck face!");
            return;
        }

        System.out.println("Thanks, and get the fuck outta here asshole.");

    }
    //3. void last transaction
    public void voidLastTrans() {
        if (list.isEmpty()) {
            System.out.println("List is currently empty.");
        }
        else {
            int tail = list.size() - 1;
            //reduce subtotal
            voidItem(list.get(tail), list.get(tail).quant);
            list.remove(tail);
            System.out.println("Last sale has been voided.");
        }
    }
    //4. void items from list
    public void voidItems(String str, int qty) {
        for (Item tmp : list) {
            //found requested item in list
            if (tmp.name.equals(str)) {
                //remove item from list entirely
                if (qty >= tmp.quant) {
                    //adjust subtotal
                    voidItem(tmp, tmp.quant);
                    list.remove(tmp);
                    System.out.println(str + " removed from list.");
                }
                //reduce portion of given item's quantity
                else {
                    tmp.quant -= qty;
                    //subtract appropriate amt from subtotal
                    voidItem(tmp, qty);
                    System.out.printf("%s quantity reduced to %d.\n",
                            tmp.name, tmp.quant);
                }
            }
        }
    }
    //5. print receipt to screen
    public void printReceipt() {
        System.out.println("Printing receipt...\n");
        for (Item tmp : list) {
            System.out.printf("%s\t\t%d\n", tmp.name, tmp.quant);
        }
        System.out.printf("____________________"
                + "\nSubtotal:\t$%.2f\nTax:\t$%.2f\nTotal:\t$%.2f\n\n",
                subtotal, subtotal*tax, subtotal + tax*subtotal);
    }
    //compile inventory
    public void buildInventory() {
        inventory.add(Item.grapes);
        inventory.add(Item.bananas);
        inventory.add(Item.bread);
        inventory.add(Item.rice);
    }
}
