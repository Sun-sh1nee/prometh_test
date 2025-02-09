package ui;

import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import model.Player;

public class GameMenu extends VBox {
    private Player player;
    private ImageView cookieButton;
    private Text cookieCounter;

    public GameMenu(Player player) {
        this.player = player;

        cookieCounter = new Text("Cookies: " + player.getCookies());
        
        Image cookieImg = new Image(getClass().getResourceAsStream("/images/cookie.png"));
        cookieButton = new ImageView(cookieImg);
        cookieButton.setFitWidth(150);
        cookieButton.setFitHeight(150);

        // ✅ กำหนดให้คลิกแล้วอัปเดตค่า
        cookieButton.setOnMouseClicked(e -> handleClick());

        getChildren().addAll(cookieCounter, cookieButton);
    }

    private void handleClick() {
        player.click();
        cookieCounter.setText("Cookies: " + player.getCookies());
        animateClick();
    }

    private void animateClick() {
        ScaleTransition st = new ScaleTransition(Duration.millis(100), cookieButton);
        st.setToX(1.2);
        st.setToY(1.2);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }
}
