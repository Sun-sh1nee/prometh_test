package ui;

import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.GameLogic;

import java.util.HashMap;

public class SceneManager {
    private static Stage primaryStage;
    private static final HashMap<String, Scene> scenes = new HashMap<>();
    private static String sceneName = "HOME";
    public static void setStage(Stage stage) {
        primaryStage = stage;
    }

    public static void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }
    public static String getSceneName() {
    	return sceneName;
    }
    public static void switchTo(String name) {
        if (scenes.containsKey(name)) {
        	sceneName = name;
            primaryStage.setScene(scenes.get(name));
            primaryStage.show();
 
            // Ensure DPS keeps running when switching scenes
            GameLogic.startDpsHome();

            if (name.equals("STORY")) {
            	StoryScene storyScene = (StoryScene) scenes.get("STORY").getRoot();
            	storyScene.updateStoryUI();
                storyScene.updateEquippedCardsBar();
            }
            if (name.equals("HOME")) {
                HomeScene homeScene = (HomeScene) scenes.get("HOME").getRoot();
                homeScene.updateEquippedCardsBar();
            }
        }
    }
 // If you want direct access to the root node:
    public static BaseScene getSceneNode(String name) {
        if (scenes.containsKey(name)) {
            return (BaseScene) scenes.get(name).getRoot();
        }
        return null;
    }
    
    public static void updateHomeScene() {
    	((HomeScene) scenes.get("HOME").getRoot()).updateHpMonsterHome();
    }
}