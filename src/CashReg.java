import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.text.Text;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;


public class CashReg extends Application {

    private ObservableList<Item> itemsObsList;
    private ListView<Item> itemsView;
    private Text subtotalText,  totalText, savingsText;

    private Scene home, checkout;

    private Stage mainStage;

    private Register register;

    private int qty = 1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        register = new Register();

        mainStage = primaryStage;
        mainStage.setTitle("GFY Market");
        mainStage.resizableProperty().setValue(false);

        /*
        //close method
        mainStage.setOnCloseRequest(e -> {
            e.consume();
            confirmClose();
        });
        */


        /*
        home screen elements
        */
        Label welcome = new Label("Click below to begin checkout");

        Button startBtn = new Button("Checkout");
        startBtn.setOnAction(e -> mainStage.setScene(checkout));

        /*
        home layout
        */
        VBox homeLayout = new VBox(25);
        homeLayout.getChildren().addAll(welcome, startBtn);
        homeLayout.setAlignment(Pos.CENTER);

        /*
        set home scene
        */
        home = new Scene(homeLayout, 300, 250);
        //home.getStylesheets().add("RegisterStyle.css");
        mainStage.setScene(home);
        mainStage.show();


        /*
        main screen elements
        */
        //inventory elements
        Button grapesBtn = new Button("Grapes");
        grapesBtn.setPrefWidth(90);
        grapesBtn.setOnAction(e -> {
            register.sell("grapes", qty);
            miscUpdate();
        });

        Button bananasBtn = new Button("Nanas");
        bananasBtn.setPrefWidth(90);
        bananasBtn.setOnAction(e -> {
            register.sell("bananas", qty);
            miscUpdate();
        });

        Button applesBtn = new Button("Apples");
        applesBtn.setPrefWidth(90);
        applesBtn.setOnAction(e -> {
            register.sell("apples", qty);
            miscUpdate();
        });

        Button breadBtn = new Button("Bread");
        breadBtn.setPrefWidth(90);
        breadBtn.setOnAction(e -> {
            register.sell("bread", qty);
            miscUpdate();
        });

        Button riceBtn = new Button("Rice");
        riceBtn.setPrefWidth(90);
        riceBtn.setOnAction(e -> {
            register.sell("rice", qty);
            miscUpdate();
        });

        Button alaskanCodBtn = new Button("Alaskan Cod");
        alaskanCodBtn.setPrefWidth(90);
        alaskanCodBtn.setOnAction(e -> {
            register.sell("alaskan cod", qty);
            miscUpdate();
        });

        Button eggsBtn = new Button("Eggs");
        eggsBtn.setPrefWidth(90);
        eggsBtn.setOnAction(e -> {
            register.sell("eggs", qty);
            miscUpdate();
        });

        Button lunchMeatBtn = new Button("Lunch Meat");
        lunchMeatBtn.setPrefWidth(90);
        lunchMeatBtn.setOnAction(e -> {
            register.sell("lunch meat", qty);
            miscUpdate();
        });

        Button groundBeefBtn = new Button("Ground Beef");
        groundBeefBtn.setPrefWidth(90);
        groundBeefBtn.setOnAction(e -> {
            register.sell("ground beef", qty);
            miscUpdate();
        });

        Button milkBtn = new Button("Milk");
        milkBtn.setPrefWidth(90);
        milkBtn.setOnAction(e -> {
            register.sell("milk", qty);
            miscUpdate();
        });

        Button iceCreamBtn = new Button("Ice Cream");
        iceCreamBtn.setPrefWidth(90);
        iceCreamBtn.setOnAction(e -> {
            register.sell("ice cream", qty);
            miscUpdate();
        });

        Button cheeseBtn = new Button("Cheese");
        cheeseBtn.setPrefWidth(90);
        cheeseBtn.setOnAction(e -> {
            register.sell("cheese", qty);
            miscUpdate();
        });

        Button peanutButterBtn = new Button("Peanut Butter");
        peanutButterBtn.setPrefWidth(90);
        peanutButterBtn.setOnAction(e -> {
            register.sell("peanut butter", qty);
            miscUpdate();
        });

        Button orangeJuiceBtn = new Button("Orange Juice");
        orangeJuiceBtn.setPrefWidth(90);
        orangeJuiceBtn.setOnAction(e -> {
            register.sell("orange juice", qty);
            miscUpdate();
        });

        Button lotionBtn = new Button("Lotion");
        lotionBtn.setPrefWidth(90);
        lotionBtn.setOnAction(e -> {
            register.sell("lotion", qty);
            miscUpdate();
        });

        Button soupBtn = new Button("Soup");
        soupBtn.setPrefWidth(90);
        soupBtn.setOnAction(e -> {
            register.sell("soup", qty);
            miscUpdate();
        });


        //transaction elements
        itemsObsList = FXCollections.observableArrayList(register.itemList);
        itemsObsList.addListener(new ListChangeListener<Item>() {
            @Override
            public void onChanged(Change<? extends Item> c) {
            }
        });

        itemsView = new ListView<>(itemsObsList);
        itemsView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        itemsView.setMaxWidth(150);
        itemsView.setEditable(true);
        itemsView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> list) {
                return new ItemFormatCell();
            }
        });

        subtotalText = new Text();
        subtotalText.setText("Subtotal:\t\t" + String.format(
            "%4.2f", register.getSubtotal()));
        subtotalText.setTextAlignment(TextAlignment.RIGHT);
        subtotalText.getStyleClass().add("total-text");

        totalText = new Text();
        totalText.setText("Total:\t\t" + String.format(
            "%4.2f", register.getTotal()));
        totalText.setTextAlignment(TextAlignment.RIGHT);
        totalText.getStyleClass().add("total-text");

        savingsText = new Text();
        savingsText.setText("You saved:\t\t" + String.format(
                "%4.2f", register.getSavings()));
        savingsText.setTextAlignment(TextAlignment.RIGHT);
        savingsText.getStyleClass().add("total-text");


        //command bar elements
        Button backBtn = new Button("<");
        backBtn.setOnAction(e -> mainStage.setScene(home));

        ChoiceBox<Integer> sellQty = new ChoiceBox<>();
        sellQty.getItems().addAll(1, 2, 3, 4, 5);
        sellQty.setValue(1);
        sellQty.getSelectionModel().selectedItemProperty().addListener(
            (v, oldVal, newVal) -> qty = newVal);
        sellQty.setMaxWidth(70);

        Button voidItemBtn = new Button("Void");
        voidItemBtn.setOnAction(e -> {
            for (Item selected : itemsView.getSelectionModel().getSelectedItems()) {
                register.voidItem(selected, qty);
            }
            miscUpdate();
        });


        /*
        main screen layouts
        */
        //inventory layout shows all items which may be sold
        TilePane inventoryLayout = new TilePane(5, 15);
        inventoryLayout.setAlignment(Pos.CENTER);
        inventoryLayout.setPrefColumns(3);
        inventoryLayout.setPrefTileWidth(90);
        inventoryLayout.getChildren().addAll(grapesBtn, bananasBtn, applesBtn, breadBtn,
            riceBtn, alaskanCodBtn, eggsBtn, lunchMeatBtn, groundBeefBtn, milkBtn,
            iceCreamBtn, cheeseBtn, peanutButterBtn, orangeJuiceBtn, lotionBtn, soupBtn);

        //transaction layout shows items for purchase
        VBox transactionLayout = new VBox(15);
        transactionLayout.setAlignment(Pos.CENTER);
        transactionLayout.getChildren().addAll(itemsView, subtotalText, totalText, savingsText);

        //command bar layout
        HBox commandBarLayout = new HBox(20);
        commandBarLayout.getChildren().addAll(backBtn, sellQty, voidItemBtn);

        //foundational layout for all other layouts
        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(inventoryLayout);
        mainLayout.setRight(transactionLayout);
        mainLayout.setBottom(commandBarLayout);
        mainLayout.setPadding(new Insets(10));

        /*
        set main scene
        */
        checkout = new Scene(mainLayout, 460, 400);
        checkout.getStylesheets().add("RegisterStyle.css");
    }

    //confirm request to exit
    private void confirmClose() {
        boolean answer = ConfirmPrompt.display("Confirm close", "Are you sure you would like to exit?");
        if (answer)
            mainStage.close();
    }

    //update register variables upon button press
    private void miscUpdate() {
        itemsObsList.setAll(register.itemList);
        subtotalText.setText("Subtotal:\t\t"
                + String.format("%4.2f", register.getSubtotal()));
        totalText.setText("Total:\t\t"
                + String.format("%4.2f", register.getTotal()));
        savingsText.setText("You saved:\t\t"
                + String.format("%4.2f", register.getSavings()));
    }

    public class ItemFormatCell extends ListCell<Item> {
        public ItemFormatCell() {}
        @Override
        protected void updateItem(Item item, boolean empty) {
            super.updateItem(item, empty);

            if (item != null) {
                setText(item.getQuant() + " : " + item.getName() + "  . . .  " + String.format(
                    "%4.2f", item.getQuant() * item.getPrice() * (1 - item.getSale())));
                setTextFill((item.getSale() == 0) ? Color.BLACK : Color.GREEN);
                if (item.getQuant() == 0) {
                    setTextFill(Color.GRAY);
                }
            }
        }
    }
}
