package app;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UpgradeScene extends BaseScene {
    public UpgradeScene() {
        super();

        // 🔹 Upgrade UI
        Label upgradeLabel = new Label("Item");
        Rectangle itemBox = new Rectangle(100, 100, Color.LIGHTGRAY);
        
        Button upgradeButton = new Button("Upgrade x1");
        Label itemList = new Label("Item lists");

        // 🔹 Layout for Upgrade Scene
        VBox upgradeLayout = new VBox(10, upgradeLabel, itemBox, upgradeButton, itemList);
        upgradeLayout.setAlignment(Pos.CENTER);

        // ✅ Set the body content dynamically
        switchBody(upgradeLayout);
    }
}
