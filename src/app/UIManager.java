package app;

import javafx.scene.layout.StackPane;
import model.Player;
import ui.GameMenu;

public class UIManager {
    private StackPane root;
    private GameMenu gameMenu;
    private Player player;

    public UIManager(StackPane root) {
        this.root = root;
        this.player = new Player("Player1");
        this.gameMenu = new GameMenu(player);
        root.getChildren().add(gameMenu);
    }
}
