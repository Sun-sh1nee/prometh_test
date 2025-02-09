package ui;

import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import model.Upgrade;
import model.Player;

import java.util.List;

public class UpgradeMenu extends VBox {
    private boolean isOpen = false;
    private Player player;

    public UpgradeMenu(List<Upgrade> upgrades, Player player) {
        this.player = player;
        setStyle("-fx-background-color: #eee; -fx-padding: 10px;");
        setTranslateY(600); // Start hidden

        for (Upgrade upgrade : upgrades) {
            Button upgradeButton = new Button(upgrade.getName() + " - " + upgrade.getCost() + " Cookies");
            upgradeButton.setOnAction(e -> {
                if (player.getCookies() >= upgrade.getCost()) {
                    player.addCookies(-upgrade.getCost());
                    upgrade.applyUpgrade();
                }
            });
            getChildren().add(upgradeButton);
        }
    }

    public void toggleMenu() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(300), this);
        tt.setToY(isOpen ? 600 : 300);
        tt.play();
        isOpen = !isOpen;
    }
}
