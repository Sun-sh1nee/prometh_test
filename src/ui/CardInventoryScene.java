package ui;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import logic.GameLogic;
import card.BaseCard;

public class CardInventoryScene extends BaseScene {

    private static int targetSlotIndex = 0; // which slot we're equipping to
    private VBox inventoryContainer;
    private Label hoverInfoLabel; // the "yellow box" for stats

    public static void setTargetSlotIndex(int slotIndex) {
        targetSlotIndex = slotIndex;
    }

    public CardInventoryScene() {
        super();

        Label title = new Label("Inventory");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // FlowPane for displaying owned cards
        FlowPane cardsPane = new FlowPane();
        cardsPane.setHgap(10);
        cardsPane.setVgap(10);
        cardsPane.setPadding(new Insets(10));
        cardsPane.setAlignment(Pos.CENTER);

        // If you want the cards to wrap at a certain width, you can do:
        // cardsPane.setPrefWrapLength(500);

        // Wrap the FlowPane in a ScrollPane so it becomes scrollable
        ScrollPane scrollPane = new ScrollPane(cardsPane);
        scrollPane.setFitToWidth(true);  
        // scrollPane.setFitToHeight(true); // if you want it to expand vertically as well
        scrollPane.setPadding(new Insets(10));

        // The “yellow box” for showing toString() on hover
        hoverInfoLabel = new Label("Hover a card to see details");
        hoverInfoLabel.setStyle("-fx-background-color: lightgray; -fx-padding: 5; -fx-wrap-text: true;");
        hoverInfoLabel.setWrapText(true);
        hoverInfoLabel.setMaxWidth(600);
        hoverInfoLabel.setMaxHeight(150); // Increase max height
        hoverInfoLabel.setPrefHeight(150); // Set preferred height
        hoverInfoLabel.setMinHeight(120);


        // Populate with owned cards
        ArrayList<BaseCard> ownedCards = GameLogic.getOwnedCards();
//        BaseCard[] equippedCardsAry = GameLogic.getEquippedCards(); 
//        ArrayList<BaseCard> equippedCards = GameLogic.getOwnedCards();
//        for(BaseCard equip : equippedCardsAry) {
//        	equippedCards.add(equip);
//    	}
        
        for (BaseCard card : ownedCards) {
//        	if(!equippedCards.contains(card)) {
        		StackPane cardView = createCardView(card);
            	cardsPane.getChildren().add(cardView);
//        	}
        	
        }

        // A button to close and go back to equipment scene (if user wants to cancel)
        Label closeButton = new Label("Close");
        closeButton.setStyle("-fx-border-color: black; -fx-padding: 5; -fx-background-color: lightgray;");
        closeButton.setOnMouseClicked(e -> SceneManager.switchTo("CARD_EQUIPMENT"));

        inventoryContainer = new VBox(15, title, scrollPane, hoverInfoLabel, closeButton);
        inventoryContainer.setAlignment(Pos.CENTER);
        inventoryContainer.setPadding(new Insets(20));

        switchBody(inventoryContainer);
    }

    private StackPane createCardView(BaseCard card) {
        StackPane cardPane = new StackPane();
        cardPane.setPrefSize(80, 100);
        cardPane.setStyle("-fx-border-color: black; -fx-background-color: #dddddd; -fx-alignment: center;");

        Label cardLabel = new Label(card.getName() + "\n[" + card.getTier() + "]");
        cardLabel.setStyle("-fx-text-alignment: center; -fx-font-size: 12;");
        cardPane.getChildren().add(cardLabel);

        // Show toString() on hover
        cardPane.setOnMouseEntered(e -> hoverInfoLabel.setText(card.toString()));
        cardPane.setOnMouseExited(e -> hoverInfoLabel.setText("Hover a card to see details"));

        // On click, equip to targetSlotIndex
        cardPane.setOnMouseClicked(e -> {
            GameLogic.equipCard(card, targetSlotIndex);
            SceneManager.switchTo("CARD_EQUIPMENT");

            // Refresh the equipment scene to show the newly equipped card
            CardEquipmentScene equipScene = 
                (CardEquipmentScene) SceneManager.getSceneNode("CARD_EQUIPMENT");
            if (equipScene != null) {
                equipScene.refreshSlots();
            }
        });

        return cardPane;
    }
}
