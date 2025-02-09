package app;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class SoundManager {
    private MediaPlayer backgroundMusic;
    private boolean soundEnabled = true;

    public SoundManager(String filePath) {
        Media sound = new Media(new File(filePath).toURI().toString());
        backgroundMusic = new MediaPlayer(sound);
    }

    public void play() {
        if (soundEnabled) {
            backgroundMusic.play();
        }
    }

    public void toggleSound() {
        soundEnabled = !soundEnabled;
        if (!soundEnabled) {
            backgroundMusic.stop();
        }
    }
}
