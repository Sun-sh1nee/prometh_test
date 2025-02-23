package ui;

import java.util.Random;

import card.*;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.GameLogic;

public class HomeScene extends BaseScene {
    private Label hpLabel;
    private ProgressBar hpBar;
    private StackPane monsterArea;
    private HBox equippedCardsBar;
    
    public HomeScene() {
        super();

        // 🔹 Display HP (Binds to GameManager HP property)
        hpLabel = new Label();
        hpBar = new ProgressBar(1.0);
        hpBar.setStyle("-fx-accent: red;");
        hpBar.setPrefWidth(200);
        

        // 🔹 Bind HP Label to GameManager (Auto-Updates)
        hpLabel.textProperty().bind(GameLogic.monsterHpHomeProperty().asString("%.0f"));
        hpBar.progressProperty().bind(Bindings.createDoubleBinding(
        	    () -> GameLogic.monsterHpHomeProperty().get() / GameLogic.getMonster().getMonsterHp(),
        	    GameLogic.monsterHpHomeProperty()
        ));

        // 🔹 Monster Clickable Area
        monsterArea = new StackPane(new Rectangle(120, 120, Color.WHITE));
        monsterArea.setOnMouseClicked(e -> attackMonster());
        
        equippedCardsBar = new HBox(10);
        equippedCardsBar.setPadding(new Insets(10));
        equippedCardsBar.setSpacing(20);
        equippedCardsBar.setAlignment(Pos.CENTER);
        updateEquippedCardsBar();
       
        VBox homeLayout = new VBox(10, hpLabel, hpBar, monsterArea,equippedCardsBar);
        homeLayout.setAlignment(Pos.CENTER);
        switchBody(homeLayout);

        GameLogic.startDpsHome(); // Ensure DPS starts
    }
    
    private void attackMonster() {
    	int damage = GameLogic.getPlayer().getAttackPerClick(); // Use actual damage value here
        GameLogic.reduceMonsterHpHome(damage);

        // Random naja jub jub
        Random rand = new Random();
        double randomX = rand.nextDouble() * 150 - 75; // Random X offset
        double randomY = rand.nextDouble() * 150 - 100; // Random Y offset
        double randomSize = rand.nextDouble() * 10 + 20; // Random size between 20-30
        double randomRotation = rand.nextDouble() * 30 * (rand.nextDouble()>0.5?-1:1); // Random rotation
        
        Text damageText = new Text("-" + damage); 
        damageText.setFill(Color.RED);
        damageText.setStyle("-fx-font-weight: bold;");
        damageText.setTranslateX(randomX);
        damageText.setTranslateY(randomY);
        damageText.setRotate(randomRotation);
        damageText.setScaleX(randomSize / 20);
        damageText.setScaleY(randomSize / 20);

        // u can change to monster area if u want na kub
        //        dai rai kub
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
    
    public void updateHpMonsterHome() {
    	
        hpLabel.textProperty().bind(GameLogic.monsterHpHomeProperty().asString("%.0f"));
        hpBar.progressProperty().bind(Bindings.createDoubleBinding(
        	    () -> GameLogic.monsterHpHomeProperty().get() / GameLogic.getMonster().getMonsterHp(),
        	    GameLogic.monsterHpHomeProperty()
        ));
       
    }
    
    public void updateEquippedCardsBar() {
        equippedCardsBar.getChildren().clear();

        BaseCard[] equipped = GameLogic.getEquippedCards();
        for (int i = 0; i < equipped.length; i++) {
            BaseCard card = equipped[i];
            Label cardSlotLabel;
            if (card == null) {
                cardSlotLabel = new Label("Empty");
            } else {
                cardSlotLabel = new Label(card.getName() + "\n[" + card.getTier() + "]");
               
                cardSlotLabel.setOnMouseClicked(e -> {
                    if (card instanceof Activatable) {
                        
                        ((Activatable) card).activate();
                    }
                    
                });
            }
            cardSlotLabel.setStyle("-fx-border-color: black; -fx-padding: 5;");
            equippedCardsBar.getChildren().add(cardSlotLabel);
        }
    }
    
    
}