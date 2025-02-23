package ui;

import Item.AttackItem;
import Item.Item;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.GameLogic;
import player.Player;

class ComponentPane extends VBox {
  private Item item;
  private Label levelLabel;
  private Label costLabel;

  public ComponentPane(Item item) {
    this.item = item;

    // UI elements
    Label nameLabel = new Label(item.toString());
    Rectangle itemBox = new Rectangle(50, 50, Color.LIGHTGRAY);
    levelLabel = new Label("Level: " + item.getLevelItem());
    levelLabel
        .textProperty()
        .bind(item.levelProperty().asString("Level: %d"));

    // Cost Label
    costLabel = new Label();
    costLabel
        .textProperty()
        .bind(Bindings.format("Cost: %d", item.getCostItem()));

    // Style based on croissant count
    ObjectBinding<Color> textColorBinding =
        Bindings.createObjectBinding(
            () ->
                GameLogic.getCroissantCount().get() >= item.getCostItem()
                        .get()
                    ? Color.BLACK
                    : Color.RED,
            GameLogic.getCroissantCount(),
            item.getCostItem()); // Depend on both croissant count and item
                                      // cost

    costLabel.textFillProperty().bind(textColorBinding);

    Button upgradeButton = new Button("Upgrade");

    upgradeButton.setOnAction(
        e -> {
          if (GameLogic.getCroissantCount().get() >= item.getCostItem().get()) {
            GameLogic.setCroissantCount(
                GameLogic.getCroissantCount().get() - item.getCostItem().get());
            item.upgrade();
          }
        });
    // Upgrade action
    upgradeButton.setOnAction(e -> {
      item.upgrade();
      if(item instanceof AttackItem) {
    	  GameLogic.setattackPerClick();
      }
      // levelLabel.setText("Level: " + component.getLevel()); // Update level
      // display
    });

    // Layout
    HBox topLayout = new HBox(10, itemBox, nameLabel);
    topLayout.setAlignment(Pos.CENTER);

    this.getChildren().addAll(topLayout, levelLabel, costLabel, upgradeButton);
    this.setAlignment(Pos.CENTER);
    this.setPadding(new Insets(10));
    this.setSpacing(5);
    this.setStyle("-fx-border-color: black;");
  }
}

public class UpgradeScene extends BaseScene {

  public UpgradeScene() {
    super();

    Player player = GameLogic.getPlayer();

    Item attackItem = player.getItems().get(0);
    Item critRateItem = player.getItems().get(1);
    Item critDamageItem = player.getItems().get(2);
    Item chanceToDropItem = player.getItems().get(3);

    ComponentPane componentPane1 = new ComponentPane(attackItem);
    ComponentPane componentPane2 = new ComponentPane(critRateItem);
    ComponentPane componentPane3 = new ComponentPane(critDamageItem);
    ComponentPane componentPane4 = new ComponentPane(chanceToDropItem);

    VBox upgradeLayout =
        new VBox(
            10, componentPane1, componentPane2, componentPane3, componentPane4);
    upgradeLayout.setAlignment(Pos.CENTER);

    ScrollPane scrollPane = new ScrollPane(upgradeLayout);
    scrollPane.setFitToWidth(true);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

    switchBody(scrollPane);
  }
}
