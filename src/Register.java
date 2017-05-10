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
                    break;
                for (Item tmp : list) {
                    //add to qty of item present in list
                    if (str.equals(tmp.name)) {
                        found = true;
                        System.out.print("Quantity to sell: ");
                        try {
                            qty = scan.nextInt();
                            tmp.quant += qty;
                        }
                        //add 1 to quantity if exception thrown
                        catch (InputMismatchException ex) {
                            tmp.quant += 1;
                        }
                        //add item's value to subtotal
                        sell(tmp);
                    }
                }
                //add new item to list
                if (!found) {
                    list.add(Item.getItem(str));
                    int last = list.size() - 1;
                    System.out.print("Quantity to sell: ");
                    try {
                        qty = scan.nextInt();
                        list.get(last).quant += qty;
                    }
                    //add 1 to quantity if exception thrown
                    catch (InputMismatchException ex) {
                        list.get(last).quant += 1;
                    }
                    //add item's value to subtotal
                    sell(list.get(last));
                }
            }
        }
    }
    //logic for applying item value to receipt
    public void sell(Item i) {
        subtotal += i.price * i.quant;
    }
    //subtract item value from receipt
    public void voidItem(Item i) {
        subtotal -= i.price * i.quant;
    }
    //2. ring up all items
    public void checkout() {
        //openRegister();
        printReceipt();
        //newCheck();
    }
    //3. void last transaction
    public void voidLastTrans() {
        if (list.isEmpty()) {
            System.out.println("List is currently empty.");
        }
        else {
            int tail = list.size() - 1;
            //reduce subtotal
            voidItem(list.get(tail));
            list.remove(tail);
            System.out.println("Previous transaction has been voided.");
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
                    voidItem(tmp);
                    list.remove(tmp);
                    System.out.println(str + " removed from list.");
                }
                //reduce portion of given item's quantity
                else {
                    tmp.quant -= qty;
                    //subtract appropriate amt from subtotal
                    subtotal -= tmp.price * qty;
                    System.out.printf("%s quantity reduced to %d\n",
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
