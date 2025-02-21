package ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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

        VBox homeLayout = new VBox(10, hpLabel, hpBar, monsterArea);
        homeLayout.setAlignment(Pos.CENTER);
        switchBody(homeLayout);

        GameLogic.startDpsHome(); // Ensure DPS starts
    }

    private void attackMonster() {
    	GameLogic.clickHandle();
    }
}