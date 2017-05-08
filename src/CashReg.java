/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Scanner;

public class CashReg {

    public static ArrayList<Item> list = new ArrayList<>();

    public static void main(String[] args) {
        String str;
        int qty;
        boolean loop = true;

        Scanner scan = new Scanner(System.in);
        Register register = new Register();

        Register.buildInventory();

        while (loop) {
            System.out.println("Main Menu");
            System.out.println("------------------------");
            System.out.println("\t1 Ring in Items");
            System.out.println("\t2 Checkout");
            System.out.println("\t3 Void Last Item");
            System.out.println("\t4 Void Items");
            System.out.println("\t5 Print Receipt");
            System.out.println("\t6 Exit");

            if (scan.nextInt() == 1) {
                register.scanItems();
            } else if (scan.nextInt() == 2) {
                register.checkout();
            } else if (scan.nextInt() == 3) {
                Register.voidLastTrans();
            } else if (scan.nextInt() == 4) {
                System.out.println("Enter item to void: ");
                str = scan.next();
                System.out.println("Enter quantity to void: ");
                qty = scan.nextInt();
                register.voidItems(str, qty);
            } else if (scan.nextInt() == 5) {
                Register.printReceipt();
            } else if (scan.nextInt() == 6) {
                loop = false;
            } else {
                System.out.println("Invalid input, dipshit.");
            }
        }
        System.out.println("Have a shit day!");
    }
}
