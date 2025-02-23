package ui;

import card.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import logic.GameLogic;

public class CardEquipmentScene extends BaseScene {

    private HBox slotsContainer;

    public CardEquipmentScene() {
        super();

        Label title = new Label("Equipment");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        slotsContainer = new HBox(20);
        slotsContainer.setAlignment(Pos.CENTER);
        slotsContainer.setPadding(new Insets(20));

        // Create 4 slot views
        for (int i = 0; i < 4; i++) {
            StackPane slotPane = createSlotPane(i);
            slotsContainer.getChildren().add(slotPane);
        }

        VBox layout = new VBox(20, title, slotsContainer);
        layout.setAlignment(Pos.CENTER);

        switchBody(layout);
    }

    private StackPane createSlotPane(int slotIndex) {
        StackPane slotPane = new StackPane();
        slotPane.setPrefSize(100, 140); // size for the card slot
        slotPane.setStyle("-fx-border-color: black; -fx-border-width: 2; "
                        + "-fx-alignment: center; -fx-background-color: #eeeeee;");

        updateSlotPane(slotPane, slotIndex);

        // When user clicks this slot, open the CardInventoryScene
        slotPane.setOnMouseClicked(e -> {
            // Pass the slotIndex to the inventory scene
            CardInventoryScene.setTargetSlotIndex(slotIndex);
            SceneManager.switchTo("CARD_INVENTORY");
        });

        return slotPane;
    }

    private void updateSlotPane(StackPane slotPane, int slotIndex) {
        BaseCard card = GameLogic.getEquippedCards()[slotIndex];
        slotPane.getChildren().clear();

        if (card == null) {
            // Show a plus sign if no card
            Label plusLabel = new Label("+");
            plusLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: gray;");
            slotPane.getChildren().add(plusLabel);
        } else {
            // Show card name or tier, or an image if you have it
            Label cardLabel = new Label(card.getName() + "\n[" + card.getTier() + "]");
            cardLabel.setStyle("-fx-text-alignment: center;");
            slotPane.getChildren().add(cardLabel);
        }
    }

    // You can call this when returning from inventory to refresh slot visuals
    public void refreshSlots() {
        for (int i = 0; i < slotsContainer.getChildren().size(); i++) {
            StackPane slotPane = (StackPane) slotsContainer.getChildren().get(i);
            updateSlotPane(slotPane, i);
        }
    }
}
