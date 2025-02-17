package app;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;

public class BaseScene extends VBox {
    private HBox topBar;
    private StackPane bodyContainer; // Holds dynamic content
    private HBox navBar;

    public BaseScene() {
        // 🔹 Top Bar (Fixed at Top)
        topBar = new HBox(10);
        topBar.setMinHeight(100);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-padding: 10px; -fx-background-color: lightgray;");
        topBar.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, null)));

        Label currencyLabel = new Label("💎 20");
        currencyLabel.setFont(new Font(18));

        Label settingsButton = new Label("⚙️");
        settingsButton.setOnMouseClicked(e -> System.out.println("Open Settings"));

        topBar.getChildren().addAll(currencyLabel, settingsButton);

        // 🔹 Body Container (This will be replaced when switching scenes)
        bodyContainer = new StackPane();
        bodyContainer.setMinHeight(370); // Set a height so it doesn't collapse
        bodyContainer.setPadding(new Insets(20)); // Add padding inside the body
        VBox.setVgrow(bodyContainer, Priority.ALWAYS);
        
        // 🔹 Navigation Bar (Fixed at Bottom)
        navBar = new HBox(20);
        navBar.setMinHeight(80);
        navBar.setAlignment(Pos.CENTER);
        navBar.setStyle("-fx-padding: 10px; -fx-background-color: lightgray;");
        navBar.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, null)));

        Label homeButton = new Label("🏠");
        Label randomButton = new Label("🎲");
        Label upgradeButton = new Label("⬆️");

        homeButton.setOnMouseClicked(e -> SceneManager.switchTo("HOME"));
        randomButton.setOnMouseClicked(e -> SceneManager.switchTo("RANDOM"));
        upgradeButton.setOnMouseClicked(e -> SceneManager.switchTo("UPGRADE"));

        navBar.getChildren().addAll(homeButton, randomButton, upgradeButton);

        // 🔹 Add Everything to the VBox Layout
        this.getChildren().addAll(topBar, bodyContainer, navBar);
    }

    /**
     * 🔄 Replaces the current body content with new content
     */
    public void switchBody(javafx.scene.Node newContent) {
        bodyContainer.getChildren().clear();  // Remove old content
        bodyContainer.getChildren().add(newContent);  // Add new content
    }
}
