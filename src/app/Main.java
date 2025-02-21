package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.GameLogic;
import ui.HomeScene;
import ui.RandomScene;
import ui.SceneManager;
import ui.StoryScene;
import ui.UpgradeScene;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        SceneManager.setStage(primaryStage);

        
        primaryStage.setWidth(500);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        
        
        // Create Scenes
        SceneManager.addScene("HOME", new Scene(new HomeScene(), 500, 600));
        SceneManager.addScene("STORY", new Scene(new StoryScene(), 500, 600));
        SceneManager.addScene("UPGRADE", new Scene(new UpgradeScene(), 500, 600));

        // Start on Home Scene
        SceneManager.switchTo("HOME");
        GameLogic.newGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
//hello