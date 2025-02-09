package app;

import javafx.application.Platform;
import model.Player;

public class GameController {
    private Game game;

    public GameController(Game game) {
        this.game = game;
        startAutoCookieGenerator();
    }

    private void startAutoCookieGenerator() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    Platform.runLater(() -> game.getPlayer().addCookies(game.getPlayer().getAutoCookies()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void handleClick() {
        game.getPlayer().click();
    }
}
