//import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;


public class CashReg extends Application {
    private Label welcome;
    private Button startBtn, backBtn;

    private ListView<Item> itemsToBuy;

    private Scene home, checkout;

    private Stage mainStage;

    public Register register;

    private int qty;

    public static void main(String[] args) {
        Register.buildInventory();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        register = new Register();

        mainStage = primaryStage;
        mainStage.setTitle("GFY Market");

        /*
        //close method
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            confirmClose();
        });
        */


        /*
        home screen elements
        */
        welcome = new Label("Click below to begin checkout");

        startBtn = new Button("Checkout");
        startBtn.setOnAction(e -> mainStage.setScene(checkout));

        /*
        home layout
        */
        VBox homeLayout = new VBox(25);
        homeLayout.getChildren().addAll(welcome, startBtn);
        homeLayout.setAlignment(Pos.CENTER);


        /*
        main screen elements
        */
        //inventory elements
        Button grapesBtn = new Button("Grapes");
        grapesBtn.setOnAction(e -> {
            register.sell(Register.inventory.get(0), qty);
            itemsToBuy.getItems().add(Register.inventory.get(0));
        });


        //transaction elements
        /*
        itemsToBuy = new ListView<>();
        itemsToBuy.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        */
        ObservableList<Item> listedItems = FXCollections.observableArrayList(register.itemList);
        itemsToBuy = new ListView<>(listedItems);
        itemsToBuy.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> list) {
                return new ItemFormatCell();
            }
        });
        itemsToBuy.setEditable(true);

        //command bar elements
        backBtn = new Button("<");
        backBtn.setOnAction(e -> mainStage.setScene(home));

        ChoiceBox<Integer> quantitySel = new ChoiceBox<>();
        quantitySel.getItems().addAll(1, 2, 3, 4, 5);
        quantitySel.setValue(1);
        quantitySel.getSelectionModel().selectedItemProperty().addListener((v, oldVal, newVal) -> qty = newVal);


        /*
        main screen layouts
        */
        //inventory layout shows all items which may be sold
        TilePane inventoryLayout = new TilePane(2.5, 2.5);
        inventoryLayout.setPrefColumns(3);
        inventoryLayout.getChildren().addAll(grapesBtn);

        //transaction layout shows items for purchase
        VBox transactionLayout = new VBox(15);
        transactionLayout.setAlignment(Pos.CENTER_LEFT);
        transactionLayout.getChildren().addAll(itemsToBuy);

        //command bar layout
        HBox commandBarLayout = new HBox(20);
        commandBarLayout.getChildren().addAll(backBtn, quantitySel);

        //foundational layout for all other layouts
        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(inventoryLayout);
        mainLayout.setRight(transactionLayout);
        mainLayout.setBottom(commandBarLayout);

        mainLayout.setPadding(new Insets(10));


        //set home scene
        home = new Scene(homeLayout, 300, 250);
        mainStage.setScene(home);
        mainStage.show();


        //set main scene
        checkout = new Scene(mainLayout, 450, 650);
        checkout.getStylesheets().add("RegisterStyle.css");
    }

    //confirm request to exit
    private void confirmClose() {
        boolean answer = ConfirmPrompt.display("Confirm close", "Are you sure you would like to exit?");
        if (answer)
            mainStage.close();
    }

    private void updateTransaction() {
        for (Item item : itemsToBuy.getItems()) {

        }
    }

    public class ItemFormatCell extends ListCell<Item> {
        public ItemFormatCell() {}
        @Override protected void updateItem(Item item, boolean empty) {
            super.updateItem(item, empty);

            if (item != null) {
                setText(item.name + " : " + item.quantity);
                double value = item.sale;
                setTextFill(value == 0 ? Color.BLACK : Color.GREEN);
            }
        }
    }
}
