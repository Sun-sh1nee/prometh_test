package ui;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import model.Card;
import model.CardBuff;
import model.CardSpecial;
import model.Player;

import java.util.Random;

public class CardDrawMenu extends VBox {
    private Player player;
    private Random random = new Random();

    public CardDrawMenu(Player player) {
        this.player = player;
        setStyle("-fx-background-color: #ccc; -fx-padding: 10px;");

        Button drawButton = new Button("Draw Buff Card (Cost: 100 Cookies)");
        drawButton.setOnAction(e -> drawCard());
        getChildren().add(drawButton);
    }

    private void drawCard() {
        if (player.getCookies() < 100) return;

        player.addCookies(-100);
        Card newCard;

        int rarityChance = random.nextInt(100);
        if (rarityChance < 10) {
            newCard = new CardSpecial("Legendary Buff", "สูง", "Double Attack Chance");
        } else if (rarityChance < 40) {
            newCard = new CardBuff("Medium Buff", "กลาง", 5);
        } else {
            newCard = new CardBuff("Small Buff", "ต่ำ", 2);
        }

        player.getCardCollection().add(newCard);
    }
}
