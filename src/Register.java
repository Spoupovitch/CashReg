import java.util.ArrayList;


public class Register {

    private final double TAX = .07;
    private double subtotal;

    ArrayList<Item> itemList;
    private ArrayList<Item> inventory;

    Register() {
        subtotal = 0.0;
        itemList = new ArrayList<>();
        buildInventory();
    }

    double getSubtotal() {
        return subtotal;
    }

    double getTotal() {
        return this.subtotal * (1 + this.TAX);
    }

    //add item value to receipt based on input quantity
    void sell(String itemName, int qty) {
        Item item = getItem(itemName);

        if (item == null) {
            return;
        }
        //check whether item exists in item list
        else if (itemList.contains(item)) {
            int index = itemList.indexOf(item);
            //add qty to item's quantity
            itemList.get(index).setQuant(itemList.get(index).getQuant() + qty);
            //modify subtotal
            subtotal += item.getPrice() * (1 - item.getSale()) * qty;
        }
        else {
            //add qty to item's quantity
            item.setQuant(qty);
            itemList.add(item);
            //modify subtotal
            subtotal += item.getPrice() * (1 - item.getSale()) * qty;
        }
    }

    //subtract item value from receipt based on input quantity
    void voidItem(Item item, int qty) {

        if (item == null) {
            return;
        }
        else {
            int index = itemList.indexOf(item);
            //remove item from item list
            if (itemList.get(index).getQuant() <= qty) {
                subtotal -= item.getPrice() * (1 - item.getSale()) * itemList.get(index).getQuant();
                itemList.get(index).setQuant(0);
            }
            //subtract from item quantity
            else {
                itemList.get(index).setQuant(itemList.get(index).getQuant() - qty);
                //modify subtotal
                subtotal -= item.getPrice() * (1 - item.getSale()) * qty;
            }
        }
    }

    //compile inventory
    private void buildInventory() {
        inventory = new ArrayList<>();

        inventory.add(Item.grapes);
        inventory.add(Item.bananas);
        inventory.add(Item.bread);
        inventory.add(Item.rice);
        inventory.add(Item.alaskan_cod);
        inventory.add(Item.eggs);
        inventory.add(Item.lunch_meat);
        inventory.add(Item.ground_beef);
        inventory.add(Item.milk);
        inventory.add(Item.ice_cream);
        inventory.add(Item.cheese);
        inventory.add(Item.peanut_butter);
        inventory.add(Item.orange_juice);
        inventory.add(Item.lotion);
        inventory.add(Item.soup);

        printInventory();
    }

    private Item getItem(String itemName) {
        for (Item item : inventory) {
            if (itemName.equals(item.getName()))
                return item;
        }
        System.out.println("Error: Item not found in inventory.");
        return null;
    }


    //debugging methods
    private void printItemList() {
        Item item;
        System.out.println("Purchased items:");
        for (int i = 0; i < itemList.size(); i++) {
            item = itemList.get(i);
            System.out.println(item.getName() + " : " + item.getQuant());
        }
    }

    private void printInventory() {
        Item item;

        System.out.println("Building inventory...");
        for (int i = 0; i < inventory.size(); i++) {
            item = inventory.get(i);
            System.out.println(item.getName());
        }
        System.out.println("Inventory size: " + inventory.size());
    }
}
