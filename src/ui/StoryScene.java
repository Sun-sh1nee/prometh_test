package ui;

import java.util.Random;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.GameLogic;

public class StoryScene extends BaseScene {
    private Label hpLabel;
    private Label stageNowLabel;
    private ProgressBar hpBar;
    private StackPane monsterArea;
    private ProgressBar timerProgress;
    
    
    private final int marginLayout = 10;

    public StoryScene() {
        super();

        updateStoryUI();
    }
    
    private void letStart() {
    	stageNowLabel = new Label();
    	stageNowLabel.textProperty().bind(GameLogic.storyStateProperty().asString());
    	
    	
        hpLabel = new Label();
        hpBar = new ProgressBar(1.0);
        hpBar.setStyle("-fx-accent: red;");
        hpBar.setPrefWidth(200);

        hpLabel.setText(getAccessibleHelp());
        hpLabel.textProperty().bind(GameLogic.monsterHpStoryProperty().asString("%.0f"));
        hpBar.progressProperty().bind(Bindings.createDoubleBinding(
        	    () -> GameLogic.monsterHpStoryProperty().get() / GameLogic.getMonster().getMonsterHp(),
        	    GameLogic.monsterHpStoryProperty()
        ));

     
        monsterArea = new StackPane(new Rectangle(120, 120, Color.WHITE));
        monsterArea.setOnMouseClicked(e -> attackMonster());

   
        timerProgress = new ProgressBar(1.0);
        timerProgress.setStyle("-fx-accent: yellow;");
        timerProgress.setPrefWidth(200);
        timerProgress.setPrefHeight(12);
        timerProgress.progressProperty().bind(GameLogic.storyTimerProgressProperty());

    	
        VBox storyLayout = new VBox(marginLayout, stageNowLabel, hpLabel, hpBar, timerProgress, monsterArea);
        storyLayout.setAlignment(Pos.CENTER);
        VBox.setMargin(hpBar, new Insets(0, 0, marginLayout * (-1), 0));  // Reduce space below hpBar
        VBox.setMargin(timerProgress, new Insets(0, 0, 0, 0)); // No extra space above timerProgress
        switchBody(storyLayout);
        GameLogic.startStoryMode();
    }


    private void attackMonster() {
    	int damage = GameLogic.getPlayer().getAttackPerClick(); // Use actual damage value here
        GameLogic.reduceMonsterHpStory(damage);

        // Random damage animation
        Random rand = new Random();
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
    
    public void updateStoryUI() {
        System.out.println("🔄 Updating Story UI...");

        Label stageLabel = new Label("Stage: " + GameLogic.getStage());
        Button attackButton = new Button("Attack");
        attackButton.setOnMouseClicked(e -> letStart());

        VBox storyLayout = new VBox(10, stageLabel, attackButton);
        storyLayout.setAlignment(Pos.CENTER);

        switchBody(storyLayout); // ✅ Now we always pass a valid Node
    }
}