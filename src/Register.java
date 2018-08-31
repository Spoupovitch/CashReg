import java.util.*;


public class Register {

    private final double tax = 0.07;
    private double subtotal;

    Scanner scan = new Scanner(System.in);
    public ArrayList<Item> itemList;
    public static ArrayList<Item> inventory = new ArrayList<>();

    public Register() {
        subtotal = 0;
        itemList = new ArrayList<>();
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
                for (Item tmp : itemList) {
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
                                tmp.quantity += qty;
                            }
                            //add 1 to quantity if exception thrown
                            catch (InputMismatchException ex) {
                                tmp.quantity += qty = 1;
                            }
                            //add item's value to subtotal
                            sell(tmp, qty);
                            break;
                        }
                    }
                //add new item to list
                if (!found) {
                    itemList.add(Item.getItem(str));
                    int last = itemList.size() - 1;
                    System.out.print("Quantity to sell: ");
                    try {
                        qty = scan.nextInt();
                        while(qty < 0)
                        {
                            System.out.println("Enter a valid positive number you degenerate: ");
                            qty = scan.nextInt();
                        }
                        itemList.get(last).quantity += qty;
                    }
                    //add 1 to quantity if exception thrown
                    catch (InputMismatchException ex) {
                        itemList.get(last).quantity += qty = 1;
                    }
                    //add item's value to subtotal
                    sell(itemList.get(last), qty);
                }
            }
        }
    }

    //add item value to receipt based on input quantity
    public void sell(Item item, int qty) {
        subtotal += item.price * (1 - item.sale) * qty;
        itemList.add(item);
    }

    //subtract item value from receipt based on input quantity
    public void voidItem(Item item, int qty) {
        subtotal -= item.price * (1 - item.sale) * qty;
    }

    //2. ring up all items
    public void checkout() {
        System.out.println("Would you like a receipt? Yes or No");
        String str = scan.next();

        if(str.equals("Yes") || str.equals("yes") || str.equals("Y") || str.equals("y"))
        {
            printReceipt();
            System.out.println("Thanks for shopping with us, have a great day!");
            return;
        }
        System.out.println("Thank you, have a great day!");

    }

    //3. void last transaction
    public void voidLastTrans() {
        if (itemList.isEmpty()) {
            System.out.println("List is currently empty.");
        }
        else {
            int tail = itemList.size() - 1;
            //reduce subtotal
            voidItem(itemList.get(tail), itemList.get(tail).quantity);
            itemList.remove(tail);
            System.out.println("Last sale has been voided.");
        }
    }

    //4. void items from list
    public void voidItems(String str, int qty) {
        for (Item tmp : itemList) {
            //found requested item in list
            if (tmp.name.equals(str)) {
                //remove item from list entirely
                if (qty >= tmp.quantity) {
                    //adjust subtotal
                    voidItem(tmp, tmp.quantity);
                    itemList.remove(tmp);
                    System.out.println(str + " removed from list.");
                }
                //reduce portion of given item's quantity
                else {
                    tmp.quantity -= qty;
                    //subtract appropriate amt from subtotal
                    voidItem(tmp, qty);
                    System.out.printf("%s quantity reduced to %d.\n",
                            tmp.name, tmp.quantity);
                }
            }
        }
    }

    //5. print receipt to screen
    public void printReceipt() {
        System.out.println("Printing receipt...\n");
        for (Item tmp : itemList) {
        }
        System.out.printf("____________________"
                + "\nSubtotal:\t$%.2f\nTax:\t$%.2f\nTotal:\t$%.2f\n\n",
                subtotal, subtotal*tax, subtotal + tax*subtotal);
    }

    //compile inventory
    public static void buildInventory() {
        inventory.add(Item.grapes);
        inventory.add(Item.bananas);
        inventory.add(Item.bread);
        inventory.add(Item.rice);
    }
}
