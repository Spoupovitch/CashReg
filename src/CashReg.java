//import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class CashReg extends Application {
    Label welcome;
    Button startBtn;

    Button backBtn;

    Scene home;
    Scene checkout;

    Stage mainStage;

    Register register = new Register();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setTitle("GFY Market");

        //close method
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            confirmClose();
        });

        //home screen elements
        welcome = new Label("Click below to begin checkout");

        startBtn = new Button("Checkout");
        startBtn.setOnAction(e -> mainStage.setScene(checkout));


        //checkout screen elements
        backBtn = new Button("<-");
        backBtn.setOnAction(e -> mainStage.setScene(home));

        //home layout
        VBox homeLayout = new VBox();
        homeLayout.getChildren().addAll(welcome, startBtn);

        //checkout layout
        VBox checkoutLayout = new VBox();
        checkoutLayout.getChildren().addAll(backBtn);

        //set home scene
        home = new Scene(homeLayout, 300, 250);
        mainStage.setScene(home);
        mainStage.show();

        //set checkout scene
        checkout = new Scene(checkoutLayout, 450, 650);
    }

    //confirm request to exit
    private void confirmClose() {
        Boolean answer = ConfirmPrompt.display("Confirm close", "Are you sure you would like to exit?");
        if (answer)
            mainStage.close();
    }
}
