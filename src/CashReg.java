//import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
    private Label welcome;
    private Button startBtn, backBtn;

    private ObservableList<Item> itemsObsList;
    private ListView<Item> itemsView;
    private Text subtotalView;

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
        set home scene
        */
        home = new Scene(homeLayout, 300, 250);
        mainStage.setScene(home);
        mainStage.show();


        /*
        main screen elements
        */
        //inventory elements
        Button grapesBtn = new Button("Grapes");
        grapesBtn.setOnAction(e -> {
            register.sell("grapes", qty);
            itemsObsList.setAll(register.itemList);
            subtotalView.setText(String.format("%4.2f", register.subtotal));
        });

        Button bananasBtn = new Button("Nanas");
        bananasBtn.setOnAction(e -> {
            register.sell("bananas", qty);
            itemsObsList.setAll(register.itemList);
            subtotalView.setText(String.format("%4.2f", register.subtotal));
        });

        Button breadBtn = new Button("Bread");
        breadBtn.setOnAction(e -> {
            register.sell("bread", qty);
            itemsObsList.setAll(register.itemList);
            subtotalView.setText(String.format("%4.2f", register.subtotal));
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
        itemsView.setEditable(true);
        itemsView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {
            @Override
            public ListCell<Item> call(ListView<Item> list) {
                return new ItemFormatCell();
            }
        });

        subtotalView = new Text();
        subtotalView.setText(String.format("%4.2f", register.subtotal));
        subtotalView.setTextAlignment(TextAlignment.RIGHT);
        subtotalView.getStyleClass().add("subtotal-text");

        //command bar elements
        backBtn = new Button("<");
        backBtn.setOnAction(e -> mainStage.setScene(home));

        ChoiceBox<Integer> sellQty = new ChoiceBox<>();
        sellQty.getItems().addAll(1, 2, 3, 4, 5);
        sellQty.setValue(1);
        sellQty.getSelectionModel().selectedItemProperty().addListener(
            (v, oldVal, newVal) -> qty = newVal);

        Button voidItemBtn = new Button("Void");
        voidItemBtn.setOnAction(e -> {
            for (Item selected : itemsView.getSelectionModel().getSelectedItems()) {
                register.voidItem(selected, qty);
            }
            itemsObsList.setAll(register.itemList);
            subtotalView.setText(String.format("%4.2f", register.subtotal));
        });

        /*
        main screen layouts
        */
        //inventory layout shows all items which may be sold
        TilePane inventoryLayout = new TilePane(2.5, 2.5);
        inventoryLayout.setPrefColumns(3);
        inventoryLayout.getChildren().addAll(grapesBtn, bananasBtn, breadBtn);

        //transaction layout shows items for purchase
        VBox transactionLayout = new VBox(15);
        transactionLayout.setAlignment(Pos.CENTER_LEFT);
        transactionLayout.getChildren().addAll(itemsView, subtotalView);

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
        checkout = new Scene(mainLayout, 450, 650);
        checkout.getStylesheets().add("RegisterStyle.css");
    }

    //confirm request to exit
    private void confirmClose() {
        boolean answer = ConfirmPrompt.display("Confirm close", "Are you sure you would like to exit?");
        if (answer)
            mainStage.close();
    }

    public class ItemFormatCell extends ListCell<Item> {
        public ItemFormatCell() {}
        @Override
        protected void updateItem(Item item, boolean empty) {
            super.updateItem(item, empty);

            if (item != null) {
                setText(item.name + " : " + item.quantity);
                setTextFill(item.sale == 0 ? Color.BLACK : Color.GREEN);
            }
        }
    }
}
