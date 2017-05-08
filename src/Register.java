/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Register {

    private static final double tax = 0.07;
    public static double subtotal;
    private static double lastTrans;

    Scanner scan = new Scanner(System.in);
    public static ArrayList<Item> inventory = new ArrayList<>();

    public Register() {
        subtotal = 0;
        lastTrans = 0;
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
                for (Item i : CashRegister.list) {
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
                    }
                }
                //add new Item to list
                if (!found) {
                    CashRegister.list.add(Item.getItem(str));
                    System.out.print("Quantity to sell: ");
                    try {
                        qty = scan.nextInt();
                        CashRegister.list.get(CashRegister.list.size()-1).quant
                                += qty;
                    } catch (InputMismatchException ex) {
                        CashRegister.list.get(CashRegister.list.size()-1).quant
                                += 1;
                    }
                }
            }
        }
    }
    //add single item to list and add price to total
    public void sell(Item i) {
        subtotal += i.price;
    }
    //2. ring up all items
    public void checkout() {
        System.out.printf("\nSubtotal:\t$%02d\nTax:\t$%02d\nTotal:\t$%02d\n",
                subtotal, subtotal*tax, subtotal + subtotal*tax);
    }
    //3. void last transaction
    public static void voidLastTrans() {
        if (!CashRegister.list.isEmpty()) {
            CashRegister.list.remove(CashRegister.list.size() - 1);
            subtotal -= lastTrans;
            System.out.println("Previous transaction has been voided.");
        } else {
            System.out.println("List is currently empty.");
        }
    }
    //4. void items from list
    public void voidItems(String str, int qty){
        for (Item tmp : CashRegister.list) {
            //found requested item in list
            if (tmp.name.equals(str)) {
                //remove item from list entirely
                if (qty >= tmp.quant) {
                    CashRegister.list.remove(tmp);
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
    //5. print receipt to screen
    public static void printReceipt() {
        int i = 0;
        System.out.println("Printing receipt...");
        for (Item tmp : CashRegister.list) {
            //start new line on receipt
            if (i % 4 == 0) {
                System.out.println();
            }
            System.out.printf("%d : %s - %d\t", ++i, tmp.name, tmp.quant);
        }
        System.out.printf("\nSubtotal:\t$%02d\nTax:\t$%02d\nTotal:\t$%02d",
                subtotal, subtotal*tax, subtotal + tax*subtotal);
    }
    //compile inventory
    public static ArrayList buildInventory() {
        inventory.add(Item.grapes);
        inventory.add(Item.bananas);
        inventory.add(Item.bread);
        inventory.add(Item.rice);

        return inventory;
    }
}
