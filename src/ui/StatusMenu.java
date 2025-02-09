package ui;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Player;

public class StatusMenu extends VBox {
    private Player player;
    private Text statsText;

    public StatusMenu(Player player) {
        this.player = player;
        setStyle("-fx-background-color: #ddd; -fx-padding: 10px;");

        statsText = new Text(getStatsText());
        getChildren().add(statsText);

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> statsText.setText(getStatsText()));
        getChildren().add(refreshButton);
    }

    private String getStatsText() {
        return "Cookies: " + player.getCookies() + "\nClick Power: " + player.getClickPower() + "\nAuto Cookies: " + player.getAutoCookies();
    }
}
