public class Item {

    private String name;
    private double price;
    private double sale;
    private int quantity;

    //Item constructor, item not on sale
    private Item(String n, double p) {
        this.name = n;
        this.price = p;
        this.sale = 0;
        this.quantity = 0;
    }

    //Item constructor, third param is sale percentage
    private Item(String n, double p, double s) {
        this.name = n;
        this.price = p;
        this.sale = s;
        this.quantity = 0;
    }

    String getName() {
        return this.name;
    }

    double getPrice() {
        return this.price;
    }

    double getSale() {
        return this.sale;
    }

    int getQuant() {
        return this.quantity;
    }

    void setQuant(int qty) {
        this.quantity = qty;
    }


    //produce
    static Item grapes = new Item("grapes", 1.60);
    static Item bananas = new Item("bananas", .99);
    //wheat, grain, starch
    static Item bread = new Item("bread", 1.65, .15);
    static Item rice = new Item("rice", .69);
    //meat, fish, poultry
    static Item alaskan_cod = new Item("alaskan cod", 9.34, .2);
    static Item eggs = new Item("eggs", 3.33);
    static Item lunch_meat = new Item("lunch meat", 3.80);
    static Item ground_beef = new Item("ground beef", 5.18);
    //dairy
    static Item milk = new Item("milk", 4.75, .2);
    static Item ice_cream = new Item("ice cream", 5.44, .15);
    static Item cheese = new Item("cheese", 2.80);
    //miscellaneous
    static Item peanut_butter = new Item("peanut butter", 3.25);
    static Item orange_juice = new Item("orange juice", 3.40, .09);
    static Item lotion = new Item("lotion", 2.89);
    static Item soup = new Item("soup", .79);
}
