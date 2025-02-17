package app;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RandomScene extends BaseScene {
    public RandomScene() {
        super();

        // 🔹 Chest UI
        Label chestLabel = new Label("CHEST");
        Rectangle chest = new Rectangle(100, 60, Color.GRAY);

        // 🔹 Buttons for buying chests
        Button buyOne = new Button("X1 💎 20");
        Button buyTen = new Button("X10 💎 180");

        // 🔹 Layout for Random Scene
        VBox chestLayout = new VBox(10, chestLabel, chest, buyOne, buyTen);
        chestLayout.setAlignment(Pos.CENTER);

        // ✅ Set the body content dynamically
        switchBody(chestLayout);
    }
}
