package logic;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.util.Duration;
import player.Player;

public class GameLogic {
    private static SimpleLongProperty croissantCount = new SimpleLongProperty();
    private static SimpleDoubleProperty monsterHP = new SimpleDoubleProperty();
    private static double maxHP;
    private static int damagePerSec;
    private static Player player;
    private static Timeline dpsThread;

    public static void newGame() {
    	player = new Player();
        croissantCount.set(0);
        monsterHP.set(1000);
        maxHP = 1000;
        damagePerSec = 100;
        startDPS();
    }

    public static SimpleLongProperty croissantCountProperty() {
        return croissantCount;
    }

    public static void addCroissants(long amount) {
        croissantCount.set(croissantCount.get() + amount);
    }

    public static SimpleDoubleProperty monsterHPProperty() {
        return monsterHP;
    }

    public static void reduceMonsterHP(double amount) {
        monsterHP.set(monsterHP.get() - amount);
        if (monsterHP.get() <= 0) {
            monsterHP.set(maxHP);
            addCroissants(50);
        }
    }

    public static void startDPS() {
        if (dpsThread != null) {
            dpsThread.stop();
        }

        dpsThread = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> {
            reduceMonsterHP(damagePerSec*0.1);
        }));
        dpsThread.setCycleCount(Timeline.INDEFINITE);
        dpsThread.play();
    }
}
