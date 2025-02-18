package app;

import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class HomeScene extends BaseScene {
    private double monsterHP = 90000000;
    private double maxHP = 90000000;
    private Label hpLabel;
    private ProgressBar hpBar;

    public HomeScene() {
        super(); // Call BaseScene

        // 🔹 HP Label
        hpLabel = new Label(String.format("%,.0f", monsterHP));

        // 🔹 HP Bar
        hpBar = new ProgressBar(1.0);
        hpBar.setStyle("-fx-accent: red;");
        hpBar.setPrefWidth(200);

        // 🔹 State Label
        Label stateLabel = new Label("STATE XX");

        // 🔹 Monster Area (Clickable)
        StackPane monsterArea = new StackPane(new Rectangle(120, 120, Color.WHITE));
        monsterArea.setOnMouseClicked(e -> attackMonster());

        // 🔹 Layout for Home Page
        VBox homeLayout = new VBox(10, hpLabel, hpBar, stateLabel, monsterArea);
        homeLayout.setAlignment(Pos.CENTER);

        // ✅ Set the body content
        switchBody(homeLayout);
    }

    private void attackMonster() {
        monsterHP -= 5_000_000;
        if (monsterHP < 0) monsterHP = 0;
        hpLabel.setText(String.format("%,.0f", monsterHP));
        hpBar.setProgress(monsterHP / maxHP);
    }
}
