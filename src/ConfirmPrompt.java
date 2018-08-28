import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmPrompt {
    static boolean answer;

    public static boolean display(String title, String promptText) {
        Stage window = new Stage();

        Button yesBtn = new Button("Yes");
        yesBtn.setOnAction(e -> {
            answer = true;
            window.close();
        });
        Button noBtn = new Button("No");
        noBtn.setOnAction(e -> {
            answer = false;
            window.close();
        });

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label prompt = new Label();
        prompt.setText(promptText);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(prompt, yesBtn, noBtn);
        layout.setAlignment(Pos.CENTER);

        Scene confirm = new Scene(layout, 250, 100);
        window.setScene(confirm);
        window.showAndWait();

        return answer;
    }
}
