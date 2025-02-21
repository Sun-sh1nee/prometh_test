package ui;

import Item.AttackItem;
import Item.CritDamageItem;
import Item.CritRateItem;
import Item.Item;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.GameLogic;
import player.Player;

// Example Component (Replace with your actual component class)
class ComponentPane extends VBox {
  private Item item;
  private Label levelLabel;

  public ComponentPane(Item component) {
    this.item = component;

    // UI elements
    Label nameLabel = new Label(item.toString()); // Replace with actual name
    Rectangle itemBox = new Rectangle(50, 50, Color.LIGHTGRAY);
    levelLabel = new Label("Level: " + item.getLevelItem());
    levelLabel.textProperty().bind(item.levelProperty().asString("Level: %d")); // Bind to level property
    Button upgradeButton = new Button("Upgrade");

    // Upgrade action
    upgradeButton.setOnAction(e -> {
      item.upgrade();
      // levelLabel.setText("Level: " + component.getLevel()); // Update level
      // display
    });

    // Layout
    HBox topLayout = new HBox(10, itemBox, nameLabel);
    topLayout.setAlignment(Pos.CENTER);

    this.getChildren().addAll(topLayout, levelLabel, upgradeButton);
    this.setAlignment(Pos.CENTER);
    this.setPadding(new Insets(10));
    this.setSpacing(5);
    this.setStyle("-fx-border-color: black;"); // For visual separation
  }
}

public class UpgradeScene extends BaseScene {

  public UpgradeScene() {
    super();

    // Create components (replace with your actual components)
    Player player = GameLogic.getPlayer();

    
    Item attackItem = player.getItems().get(0);
    Item critRateItem = player.getItems().get(1);
    Item critDamageItem = player.getItems().get(2);
    Item chanceToDropItem = player.getItems().get(3);

    // Create component panes
    ComponentPane componentPane1 = new ComponentPane(attackItem);
    ComponentPane componentPane2 = new ComponentPane(critRateItem);
    ComponentPane componentPane3 = new ComponentPane(critDamageItem);
    ComponentPane componentPane4 = new ComponentPane(chanceToDropItem);

    // Main layout for the upgrade scene
    VBox upgradeLayout = new VBox(10, componentPane1, componentPane2, componentPane3, componentPane4);
    upgradeLayout.setAlignment(Pos.CENTER);

    // Set the body content dynamically
    switchBody(upgradeLayout);
  }
}
