/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Register {

    private final double tax = 0.07;
    private double subtotal;
    private double lastTransAmt;

    Scanner scan = new Scanner(System.in);
    private ArrayList<Item> list = new ArrayList<>();
    public static ArrayList<Item> inventory = new ArrayList<>();

    public Register() {
        subtotal = 0;
        lastTransAmt = 0;
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
            if (scan.hasNext("/v")) {
                str = scan.next();
                voidLastTrans();
            } else if (scan.hasNext("/t")) {
                loop = false;
            } else {
                str = scan.next();
                //check for valid input
                if (Item.getItem(str) == null)
                    break;
                for (Item i : list) {
                    //add to qty of item present in list
                    if (str.equals(i.name)) {
                        found = true;
                        System.out.print("Quantity to sell: ");
                        try {
                            qty = scan.nextInt();
                            i.quant += qty;
                        } catch (InputMismatchException ex) {
                            i.quant += 1;
                        }
                        //add item's value to subtotal
                        sell(i);
                    }
                }
                //add new Item to list
                if (!found) {
                    list.add(Item.getItem(str));
                    System.out.print("Quantity to sell: ");
                    try {
                        qty = scan.nextInt();
                        list.get(list.size() - 1).quant += qty;
                    } catch (InputMismatchException ex) {
                        list.get(list.size() - 1).quant += 1;
                    }
                    //add item's value to subtotal
                    sell(list.get(list.size() - 1));
                }
            }
        }
    }
    //logic for applying item value to receipt
    public void sell(Item i) {
        subtotal +=  i.price * i.quant;
    }
    //2. ring up all items
    public void checkout() {
        //openRegister();
        printReceipt();
        //newCheck();
    }
    //3. void last transaction
    public void voidLastTrans() {
        if (!list.isEmpty()) {
            int lastIndex = list.size() - 1;
            lastTransAmt = (list.get(lastIndex).price)*(list.get(lastIndex).quant);
            list.remove(lastIndex);
            subtotal -= lastTransAmt;
            System.out.println("Previous transaction has been voided.");
        } else {
            System.out.println("List is currently empty.");
        }
    }
    //4. void items from list
    public void voidItems(String str, int qty){
        for (Item tmp : list) {
            //found requested item in list
            if (tmp.name.equals(str)) {
                //remove item from list entirely
                if (qty >= tmp.quant) {
                    list.remove(tmp);
                    System.out.printf("%s removed from list.\n", tmp.name);
                }
                //reduce portion of given item's quantity
                else {
                    tmp.quant -= qty;
                    System.out.printf("%s quantity reduced to %d",
                            tmp.name, tmp.quant);
                }
            }
        }
    }

    public void printCurrentItems()
    {
        for(Item item : list)
        {
            System.out.println(item.name + ": " + item.quant);
        }
    }
    //5. print receipt to screen
    public void printReceipt() {
        /*int i = 0;
        System.out.println("Printing receipt...");
        for (Item tmp : list) {
            //start new line on receipt
            if (i % 4 == 0) {
                System.out.println();
            }
            System.out.printf("%d : %s - %d\t", ++i, tmp.name, tmp.quant);
        }
        System.out.printf("\nSubtotal:\t$%02d\nTax:\t$%02d\nTotal:\t$%02d",
                subtotal, subtotal*tax, subtotal + tax*subtotal);*/
        System.out.println(subtotal);
    }
    //compile inventory
    public void buildInventory() {
        inventory.add(Item.grapes);
        inventory.add(Item.bananas);
        inventory.add(Item.bread);
        inventory.add(Item.rice);
    }
}
