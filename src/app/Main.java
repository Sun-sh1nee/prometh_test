package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        SceneManager.setStage(primaryStage);

        
        primaryStage.setWidth(500);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        
        
        // Create Scenes
        SceneManager.addScene("HOME", new Scene(new HomeScene(), 500, 600));
        SceneManager.addScene("RANDOM", new Scene(new RandomScene(), 500, 600));
        SceneManager.addScene("UPGRADE", new Scene(new UpgradeScene(), 500, 600));

        // Start on Home Scene
        SceneManager.switchTo("HOME");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
//hello