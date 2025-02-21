package ui;

import java.util.Random;
import javafx.animation.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.GameLogic;

public class StoryScene extends BaseScene {
    private Label hpLabel;
    private ProgressBar hpBar;
    private StackPane monsterArea;
    private ProgressBar timerProgress;
    private Timeline countdown;
    
    private final int marginLayout = 10;
    private final int totalTime = 5; // Total 30 seconds
    private SimpleDoubleProperty timeLeft = new SimpleDoubleProperty(totalTime); // Time countdown

    public StoryScene() {
        super();

        Button startButton = new Button("Start");
        startButton.setOnMouseClicked(e -> letStart());

        VBox layout = new VBox(10, startButton);
        layout.setAlignment(Pos.CENTER);

        switchBody(layout);
    }
    
    private void letStart() {
        // 🔹 Display HP (Binds to GameManager HP property)
        hpLabel = new Label();
        hpBar = new ProgressBar(1.0);
        hpBar.setStyle("-fx-accent: red;");
        hpBar.setPrefWidth(200);

        // 🔹 Bind HP Label to GameManager (Auto-Updates)
        hpLabel.textProperty().bind(GameLogic.monsterHPProperty().asString("%.0f"));
        hpBar.progressProperty().bind(GameLogic.monsterHPProperty().divide(1000)); // Normalize
        //hpBar.setBorder(new Border(new BorderStroke(Color.RED,BorderStrokeStyle.SOLID, null, null)));
        // 🔹 Monster Clickable Area
        monsterArea = new StackPane(new Rectangle(120, 120, Color.WHITE));
        monsterArea.setOnMouseClicked(e -> attackMonster());

        // 🔹 Timer Display (ProgressBar)
        timerProgress = new ProgressBar(1.0);
        timerProgress.setStyle("-fx-accent: yellow;");
        timerProgress.setPrefWidth(200);
        timerProgress.setPrefHeight(12);
        
        // Bind timer progress to timeLeft (auto-updates)
        timerProgress.progressProperty().bind(timeLeft.divide(totalTime));
        

        // Start the timer countdown
        startTimer();
        
        
    	
        VBox storyLayout = new VBox(marginLayout, hpLabel, hpBar, timerProgress, monsterArea);
        storyLayout.setAlignment(Pos.CENTER);
        VBox.setMargin(hpBar, new Insets(0, 0, marginLayout * (-1), 0));  // Reduce space below hpBar
        VBox.setMargin(timerProgress, new Insets(0, 0, 0, 0)); // No extra space above timerProgress
        switchBody(storyLayout);

        GameLogic.startDPS(); // Ensure DPS starts
    }

    private void startTimer() {
        // If timer exists, stop it first
        if (countdown != null) {
            countdown.stop();
        }

        // Reset timeLeft to 30 seconds
        timeLeft.set(totalTime);

        // Timeline to reduce timeLeft every second
        
        double speedByFrame = 0.1;
        countdown = new Timeline(new KeyFrame(Duration.seconds(speedByFrame), e -> {
            if (timeLeft.get() > 0) {
                timeLeft.set(timeLeft.get() - speedByFrame);
                System.out.println("Time Left: " + timeLeft.get());  // Debugging
            }

            if (timeLeft.get() <= 0.1) {  // Allow small rounding errors
                countdown.stop();
                timeLeft.set(0);  // Make sure it stops at 0
                System.out.println("Timer ended!");
                endBossFight();
            }
        }));

        countdown.setCycleCount((int) (totalTime / speedByFrame));
        countdown.play();
    }

    private void attackMonster() {
        GameLogic.reduceMonsterHP(100);

        // Random damage animation
        Random rand = new Random();
        int damage = 100; // Use actual damage value here
        double randomX = rand.nextDouble() * 150 - 75; 
        double randomY = rand.nextDouble() * 150 - 100; 
        double randomSize = rand.nextDouble() * 10 + 20; 
        double randomRotation = rand.nextDouble() * 30 * (rand.nextBoolean() ? -1 : 1);

        Text damageText = new Text("-" + damage);
        damageText.setFill(Color.RED);
        damageText.setStyle("-fx-font-weight: bold;");
        damageText.setTranslateX(randomX);
        damageText.setTranslateY(randomY);
        damageText.setRotate(randomRotation);
        damageText.setScaleX(randomSize / 20);
        damageText.setScaleY(randomSize / 20);

        bodyContainer.getChildren().add(damageText);
        TranslateTransition moveUp = new TranslateTransition(Duration.millis(600), damageText);
        moveUp.setByY(-30);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(600), damageText);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> bodyContainer.getChildren().remove(damageText));

        moveUp.play();
        fadeOut.play();
    }

    private void endBossFight() {
        System.out.println("⏳ Time's up! Boss fight ended.");
        // Add logic when timer ends (e.g., disable monster clicking, show Game Over)
    }
}
