package ui;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import logic.GameLogic;

public class BaseScene extends VBox {
    protected HBox topBar;
    protected StackPane bodyContainer;
    protected HBox navBar;
    protected Label croissantCountLabel;
    protected Label croissantPerSecondLabel;
    protected Label croissantPerClickLabel;
    protected long croissantCount;
    protected int damagePerClick;
    protected int damagePerSec;
    
    
    //
    
    

    public BaseScene() {
        // Top Bar
    	setCroissantCount(0);
    	setDamagePerClick(100);
    	setDamagePerSec(100);
    	
        topBar = new HBox(10);
        topBar.setMinHeight(60);
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-padding: 10px; -fx-background-color: lightgray;");

        // Gem Counter Panel
        HBox gemCounter = new HBox(5);
        gemCounter.setAlignment(Pos.CENTER_LEFT);
        Label gemIcon = new Label("💎");
        Label gemCount = new Label("20");
        gemCount.setFont(new Font(18));
        gemCounter.getChildren().addAll(gemIcon, gemCount);

        // Croissant Info Panel
        VBox croissantInfoPanel = new VBox(2);
        croissantInfoPanel.setAlignment(Pos.CENTER);
        croissantCountLabel = new Label();
        croissantCountLabel.textProperty().bind(GameLogic.croissantCountProperty().asString());
        croissantCountLabel.setFont(new Font(22));
        croissantPerClickLabel = new Label(formatNumber(damagePerClick) + "/click");
        croissantPerSecondLabel = new Label(formatNumber(damagePerSec) + " /sec");
        croissantInfoPanel.getChildren().addAll(croissantCountLabel, croissantPerClickLabel, croissantPerSecondLabel);

        // Settings Button
        Label settingsButton = new Label("⚙️");
        settingsButton.setOnMouseClicked(e -> System.out.println("OPEN SETTING"));

        topBar.getChildren().addAll(gemCounter, croissantInfoPanel, settingsButton);

        // Body Container
        bodyContainer = new StackPane();
        bodyContainer.setMinHeight(400);
        VBox.setVgrow(bodyContainer, Priority.ALWAYS);

        // Navigation Bar
        navBar = new HBox(20);
        navBar.setMinHeight(80);
        navBar.setAlignment(Pos.CENTER);
        navBar.setStyle("-fx-padding: 10px; -fx-background-color: lightgray;");

        Label homeButton = new Label("🏠");
        Label randomButton = new Label("🎲");
        Label upgradeButton = new Label("⬆️");

        homeButton.setOnMouseClicked(e -> SceneManager.switchTo("HOME"));
        randomButton.setOnMouseClicked(e -> SceneManager.switchTo("RANDOM"));
        upgradeButton.setOnMouseClicked(e -> SceneManager.switchTo("UPGRADE"));

        navBar.getChildren().addAll(homeButton, randomButton, upgradeButton);

        // Add to Layout
        this.getChildren().addAll(topBar, bodyContainer, navBar);
   
    }
    
    
    protected String formatNumber(long number) {
        return String.format("%,d", number); // Formats as 90,000,000
    }
    
   
    
    public void switchBody(javafx.scene.Node newContent) {
        bodyContainer.getChildren().clear();
        bodyContainer.getChildren().add(newContent);
    }

	public long getCroissantCount() {
		return croissantCount;
	}

	public void setCroissantCount(long croissantCount) {
		this.croissantCount = croissantCount;
	}

	public int getDamagePerClick() {
		return damagePerClick;
	}

	public void setDamagePerClick(int damagePerClick) {
		this.damagePerClick = damagePerClick;
	}

	public int getDamagePerSec() {
		return damagePerSec;
	}

	public void setDamagePerSec(int damagePerSec) {
		this.damagePerSec = damagePerSec;
	}
    
    
}