import java.util.Scanner;

public class CashReg {

    public static void main(String[] args) {
        String str;
        int qty, cmd;
        boolean loop = true;

        Scanner scan = new Scanner(System.in);
        Register register = new Register();

        while (loop) {
            System.out.println("Main Menu");
            System.out.println("------------------------");
            System.out.println("\t1 Ring in Items");
            System.out.println("\t2 Checkout");
            System.out.println("\t3 Void Last Item");
            System.out.println("\t4 Void Items");
            System.out.println("\t5 Print Current Items");
            System.out.println("\t6 Exit");

            cmd = scan.nextInt();

            switch (cmd) {
                case 1:
                    register.scanItems();
                    break;
                case 2:
                    register.checkout();
                    break;
                case 3:
                    register.voidLastTrans();
                    break;
                case 4:
                    System.out.println("Enter item to void: ");
                    str = scan.next();
                    System.out.println("Enter quantity to void: ");
                    qty = scan.nextInt();
                    register.voidItems(str, qty);
                    break;
                case 5:
                    register.printReceipt();
                    break;
                case 6:
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid input, dipshit.");
                    break;
            }
        }
        System.out.println("Have a shit day!");
    }
}
