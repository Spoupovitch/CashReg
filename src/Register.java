import java.util.*;


public class Register {

    private final double tax = 0.07;
    public double subtotal;

    Scanner scan = new Scanner(System.in);
    public ArrayList<Item> itemList;
    public ArrayList<Item> inventory;

    public Register() {
        subtotal = 0.0;
        itemList = new ArrayList<>();
        buildInventory();
    }

    //1. enter loop to scan in items
    public void scanItems() {
        String str;
        int qty;
        boolean loop = true, found = false;

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
                if (getItem(str) == null)
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
                            sell(str, qty);
                            break;
                        }
                    }
                //add new item to list
                if (!found) {
                    itemList.add(getItem(str));
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
                    sell(str, qty);
                }
            }
        }
    }

    //add item value to receipt based on input quantity
    public void sell(String itemName, int qty) {
        Item item = getItem(itemName);

        if (item == null) {
            return;
        }
        //check whether item exists in item list
        else if (itemList.contains(item)) {
            int index = itemList.indexOf(item);
            //add qty to item's quantity
            itemList.get(index).quantity += qty;
            //modify subtotal
            subtotal += item.price * (1 - item.sale) * qty;
            printItemList();
        }
        else {
            //add qty to item's quantity
            item.quantity += qty;
            itemList.add(item);
            //modify subtotal
            subtotal += item.price * (1 - item.sale) * qty;
            printItemList();
        }
    }

    //subtract item value from receipt based on input quantity
    public void voidItem(Item item, int qty) {

        if (item == null) {
            return;
        }
        //check whether item exists in item list
        else {
            int index = itemList.indexOf(item);
            //subtract qty from item's quantity
            itemList.get(index).quantity -= qty;
            //modify subtotal
            subtotal -= item.price * (1 - item.sale) * qty;
            printItemList();
        }
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
    public void buildInventory() {
        inventory = new ArrayList<>();

        inventory.add(Item.grapes);
        inventory.add(Item.bananas);
        inventory.add(Item.bread);
        inventory.add(Item.rice);

        printInv(inventory);
    }

    public Item getItem(String itemName) {
        for (Item item : inventory) {
            if (itemName.equals(item.name))
                return item;
        }
        System.out.println("Error: Item not found in inventory.");
        return null;
    }


    public void printItemList() {
        Item item;
        System.out.println("Purchased items:");
        for (int i = 0; i < itemList.size(); i++) {
            item = itemList.get(i);
            System.out.println(item.getName() + " : " + item.getQuant());
        }
    }

    public void printInv(ArrayList<Item> inventory) {
        Item item;

        System.out.println("Building inventory...");
        for (int i = 0; i < inventory.size(); i++) {
            item = inventory.get(i);
            System.out.println(item.getName());
        }
        System.out.println("Inventory size: " + inventory.size());
    }
}
