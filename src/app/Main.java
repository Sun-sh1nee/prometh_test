package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Player;
import ui.GameMenu;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        Player player = new Player("Player1");
        GameMenu gameMenu = new GameMenu(player);
        root.getChildren().add(gameMenu);

        Scene scene = new Scene(root, 400, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cookie Clicker RPG");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
