package ui;



import card.BaseCard;
import card.BuffStatCard;
import card.CardTier;
import card.HeavyHiterCard;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import logic.GameLogic;

public class HomeScene extends BaseScene {
    private Label hpLabel;
    private ProgressBar hpBar;

    public HomeScene() {
        super();

        // 🔹 Display HP (Binds to GameManager HP property)
        hpLabel = new Label();
        hpBar = new ProgressBar(1.0);
        hpBar.setStyle("-fx-accent: red;");
        hpBar.setPrefWidth(200);

        // 🔹 Bind HP Label to GameManager (Auto-Updates)
        hpLabel.textProperty().bind(GameLogic.monsterHpHomeProperty().asString("%.0f"));
        hpBar.progressProperty().bind(GameLogic.monsterHpHomeProperty().divide(GameLogic.getMaxHP())); 

        // 🔹 Monster Clickable Area
        StackPane monsterArea = new StackPane(new Rectangle(120, 120, Color.WHITE));
        monsterArea.setOnMouseClicked(e -> attackMonster());
//        StackPane testCard = new StackPane();
//        testCard.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
//        HeavyHiterCard cd = new HeavyHiterCard("test","",CardTier.LEGENDARY);
//        testCard.setPrefSize(60, 50);
//        testCard.setOnMouseClicked(e ->{
//        	
//        	cd.activate();
//        });
        VBox homeLayout = new VBox(10, hpLabel, hpBar, monsterArea);
//        homeLayout.getChildren().add(testCard);
        homeLayout.setAlignment(Pos.CENTER);
        switchBody(homeLayout);

        GameLogic.startDpsHome(); // Ensure DPS starts
    }

    private void attackMonster() {
//    	BaseCard bc = new BuffStatCard("test","",CardTier.LEGENDARY);
//    	HeavyHiterCard cd = new HeavyHiterCard("test","",CardTier.LEGENDARY);
//    	System.out.println(cd.toString());
    	GameLogic.clickHandle();
    }
}
