package ui;

import card.BaseCard;
import card.BigBangImpactCard;
import card.BuffStatCard;
import card.CardTier;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import logic.GameLogic;

import java.util.Random;

public class RandomScene extends BaseScene {
    private HBox cardDisplay;
    private static final CardTier[] TIERS = { CardTier.COMMON, CardTier.RARE, CardTier.EPIC, CardTier.LEGENDARY};

    public RandomScene() {
        super();

        
        Label chestLabel = new Label("CHEST");
        Rectangle chest = new Rectangle(100, 60, Color.GRAY);

        
        HBox buttonBox = new HBox(20); // add 20 magin
        
        Button buyOne = new Button("X1 💎 20");
        Button buyTen = new Button("X10 💎 180");
        
        buyOne.setOnAction(e -> openChest(1));
        buyTen.setOnAction(e -> openChest(10));
        
        buttonBox.getChildren().addAll(buyOne, buyTen);
        buttonBox.setAlignment(Pos.CENTER);

        
        cardDisplay = new HBox(10);
        cardDisplay.setAlignment(Pos.CENTER);

        
        ScrollPane scrollPane = new ScrollPane(cardDisplay);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(200);
        scrollPane.setStyle(
        		"-fx-background: transparent;" +
        		"-fx-border-color: red;"
        	);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        

       
        VBox randomLayout = new VBox(10, chestLabel, chest, buttonBox, scrollPane);
        randomLayout.setAlignment(Pos.CENTER);

        
        switchBody(randomLayout);
    }

    private void openChest(int times) {
        Random random = new Random();
        cardDisplay.getChildren().clear();

        for (int i = 0; i < times; i++) {
            
        	double tier = random.nextDouble();
        	int indexTier;
        	
        	if(tier >= 0.97) indexTier = 3;
        	else if(tier >= 0.85) indexTier = 2;
        	else if(tier >= 0.60) indexTier = 1;
        	else indexTier = 0;
        	
            CardTier randomTier = TIERS[indexTier];
            
            Boolean isSpecial = random.nextDouble() >= 0.8;
            BaseCard card;
            
            if(!isSpecial) {
            	//System.out.println("normal");
            	card = new BuffStatCard("Buff Overall Card", "cards/buffCard/default.png", randomTier);
            }else {
            	//System.out.println("special");
            	card = new BigBangImpactCard("Big Bang pew pew", "cards/specialCard/attackCard.png", randomTier);
            }
            

            // ✅ Create card UI block
            VBox cardBlock = new VBox(5);
            cardBlock.setAlignment(Pos.CENTER);
            cardBlock.setStyle(
            	    "-fx-padding: 5; " +
            	    "-fx-border-width: 5px; " +
            	    "-fx-background-color: white; " +
            	    "-fx-border-color: " + getTierStyle(card.getTier()) + ";"
            	);
            
            cardBlock.setMinWidth(110);
            cardBlock.setMaxWidth(110); 
            cardBlock.setPrefWidth(110); 



       
            ImageView cardImage = new ImageView();
            cardImage.setPreserveRatio(true); 
            cardImage.setSmooth(true); 
            cardImage.setFitWidth(100);
            cardImage.setFitHeight(120);


            
            cardImage.setImage(new Image(card.getCardURL()));
            GameLogic.getOwnedCards().add(card);

            
            Label cardNameLabel = new Label(card.getName());
            cardNameLabel.setWrapText(true);
            cardNameLabel.setTextAlignment(TextAlignment.CENTER);
            cardNameLabel.setStyle(
	                "-fx-font-size: 12px;" +
	                "-fx-font-weight: bold;" +
	                "-fx-text-fill: black;" +
	                "-fx-border-color: black;" +  
	                "-fx-border-width: 1px;" +
	                "-fx-padding: 3;" +         
	                "-fx-background-color: white;"
	            );

            
            Label tierLabel = new Label(card.getTier().toString());
            tierLabel.setStyle(
            			"-fx-font-size: 9px;" +
            			"-fx-text-fill: " + getTierStyle(card.getTier()) + ";"
            		);

            cardBlock.getChildren().addAll(cardImage, cardNameLabel, tierLabel);

            cardDisplay.getChildren().add(cardBlock);
        }
    }

    
    private String getTierStyle(CardTier tier) {
        switch (tier) {
            case COMMON:
                return "gray";
            case RARE:
                return "blue";
            case EPIC:
                return "purple";
            case LEGENDARY:
                return "orange";
            default:
                return "black;";
        }
    }

}

