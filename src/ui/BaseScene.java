package ui;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    
    
    protected StackPane rootContainer; // ครอบทั้ง Main UI และ Settings
    protected VBox mainContainer; // ชั้นแรก (พื้นหลัง)
    protected StackPane settingsContainer; // ชั้น Settings
    protected boolean isSettingsOpen = false;

    public BaseScene() {
    	setCroissantCount(0);
    	setDamagePerClick(100);
    	setDamagePerSec(100);
    	
        topBar = new HBox(10);
        topBar.setMinHeight(60);
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-padding: 10px; -fx-background-color: lightgray;");

        HBox gemCounter = new HBox(5);
        gemCounter.setAlignment(Pos.CENTER_LEFT);
        Label gemIcon = new Label("💎");
        Label gemCount = new Label("20");
        gemCount.setFont(new Font(18));
        gemCounter.getChildren().addAll(gemIcon, gemCount);

        VBox croissantInfoPanel = new VBox(2);
        croissantInfoPanel.setAlignment(Pos.CENTER);
        croissantCountLabel = new Label();
        croissantCountLabel.textProperty().bind(GameLogic.croissantCountProperty().asString());
        croissantCountLabel.setFont(new Font(22));
        croissantPerClickLabel = new Label(formatNumber(damagePerClick) + "/click");
        croissantPerSecondLabel = new Label(formatNumber(damagePerSec) + " /sec");
        croissantInfoPanel.getChildren().addAll(croissantCountLabel, croissantPerClickLabel, croissantPerSecondLabel);

        Label settingsButton = new Label("⚙️");
        //settingsButton.setOnMouseClicked(e -> System.out.println("open setting"));
        settingsButton.setOnMouseClicked(e -> {
        	toggleSettingsPage();
        	System.out.println("open setting");
        });
        
        topBar.getChildren().addAll(gemCounter, croissantInfoPanel, settingsButton);

        bodyContainer = new StackPane();
        bodyContainer.setMinHeight(400);
        VBox.setVgrow(bodyContainer, Priority.ALWAYS);

        navBar = new HBox(20);
        navBar.setMinHeight(80);
        navBar.setAlignment(Pos.CENTER);
        navBar.setStyle("-fx-padding: 10px; -fx-background-color: lightgray;");

        Label homeButton = new Label("🏠");
        Label storyButton = new Label("🎲");
        Label upgradeButton = new Label("⬆️");

        homeButton.setOnMouseClicked(e -> SceneManager.switchTo("HOME"));
        storyButton.setOnMouseClicked(e -> SceneManager.switchTo("STORY"));
        upgradeButton.setOnMouseClicked(e -> SceneManager.switchTo("UPGRADE"));

        navBar.getChildren().addAll(homeButton, storyButton, upgradeButton);

        
        mainContainer = new VBox(topBar, bodyContainer, navBar);
        mainContainer.setAlignment(Pos.CENTER);
        
        createSettingsPage();
        
        rootContainer = new StackPane(mainContainer, settingsContainer);
        rootContainer.setAlignment(Pos.CENTER);

        this.getChildren().add(rootContainer);
    }
    
    private void createSettingsPage() {
        System.out.println("✅ Creating Centered Settings Page...");

        // 🟢 พื้นหลังโปร่งใส (ให้เห็น Main UI ข้างหลัง)
        Region background = new Region();
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");
        background.setPrefSize(800, 600);
        background.setOnMouseClicked(e -> toggleSettingsPage()); // คลิกข้างนอกเพื่อปิด

        // 🟢 กล่อง Settings (Popup ตรงกลาง)
        VBox settingsBox = new VBox(15);
        settingsBox.setAlignment(Pos.CENTER);
        settingsBox.setStyle(
        	    "-fx-background-color: white; " +
        	    "-fx-padding: 15px; " +
        	    "-fx-background-radius: 10px;"
        );
        settingsBox.setMaxSize(250, 150); // ลดขนาดลง

        Label title = new Label("Settings");
        title.setFont(new Font(20)); // ลดขนาดตัวอักษร

        Button toggleMusicButton = new Button("Music: ON");
        toggleMusicButton.setOnAction(e -> toggleMusic(toggleMusicButton));

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> toggleSettingsPage());

        settingsBox.getChildren().addAll(title, toggleMusicButton, closeButton);

        // 🟢 StackPane จัดหน้า Settings ให้อยู่ตรงกลาง
        StackPane settingsContent = new StackPane(settingsBox);
        settingsContent.setAlignment(Pos.CENTER);

        // 🟢 StackPane ครอบหน้า Settings + Background
        settingsContainer = new StackPane(background, settingsContent);
        settingsContainer.setAlignment(Pos.CENTER);
        settingsContainer.setVisible(false); // ซ่อนตอนเริ่มเกม
    }

    private void toggleSettingsPage() {
        isSettingsOpen = !isSettingsOpen;
        settingsContainer.setVisible(isSettingsOpen);
        System.out.println(isSettingsOpen ? "⚙️ Opened Settings Page" : "❌ Closed Settings Page");
    }

    private void toggleMusic(Button button) {
        boolean isMusicOn = button.getText().equals("Music: ON");
        button.setText("Music: " + (isMusicOn ? "OFF" : "ON"));
        System.out.println("🎵 Music toggled: " + button.getText());
    }
    
    
    protected String formatNumber(long number) {
        return String.format("%,d", number);
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