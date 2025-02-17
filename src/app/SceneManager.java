package app;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.HashMap;

public class SceneManager {
    private static Stage primaryStage;
    private static final HashMap<String, Scene> scenes = new HashMap<>();

    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    public static void switchTo(String name) {
        if (scenes.containsKey(name)) {
            primaryStage.setScene(scenes.get(name));
            primaryStage.show();
        }
    }
}
