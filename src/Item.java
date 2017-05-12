public class Item {

    public String name;
    public double price;
    public double sale = 0;
    public int quant = 0;

    //Item constructor, not on sale
    public Item(String n, double p) {
        this.name = n;
        this.price = p;
    }
    //Item constructor, third param is sale percentage
    public Item(String n, double p, double s) {
        this.name = n;
        this.price = p;
        this.sale = s;
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
    static Item lunch_meat = new Item("lunch meat", 4.18);
    //dairy
    static Item milk = new Item("milk", 4.75, .2);
    static Item ice_cream = new Item("ice cream", 5.44, .15);
    static Item cheese = new Item("cheese", 2.80);
    //miscellaneous
    static Item peanut_butter = new Item("peanut butter", 3.25);
    static Item orange_juice = new Item("orange juice", 3.40, .09);
    static Item lotion = new Item("lotion", 2.89);
    static Item soup = new Item("soup", .79);

    public static Item getItem(String str) {
        for (Item tmp : Register.inventory) {
            if (str.equals(tmp.name))
                return tmp;
        }
        System.out.println("Error: Item not found in inventory.");
        return null;
    }
}
