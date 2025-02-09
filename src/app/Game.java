package app;

import model.Player;
import model.Upgrade;
import model.Automation;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Player player;
    private List<Upgrade> upgrades;
    private List<Automation> automations;

    public Game() {
        player = new Player("Player1");
        upgrades = new ArrayList<>();
        automations = new ArrayList<>();
        initUpgrades();
        initAutomations();
    }

    private void initUpgrades() {
        upgrades.add(new Upgrade("Click Power +1", 50, () -> player.increaseClickPower(1)));
        upgrades.add(new Upgrade("Click Power +5", 200, () -> player.increaseClickPower(5)));
    }

    private void initAutomations() {
        automations.add(new Automation("Auto Clicker", 500, 1, player));
        automations.add(new Automation("Super Clicker", 1500, 5, player));
    }

    public Player getPlayer() {
        return player;
    }

    public List<Upgrade> getUpgrades() {
        return upgrades;
    }

    public List<Automation> getAutomations() {
        return automations;
    }
}
