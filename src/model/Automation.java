package model;

public class Automation {
    private String name;
    private int cost;
    private int cookiesPerSecond;
    private Player player;

    public Automation(String name, int cost, int cookiesPerSecond, Player player) {
        this.name = name;
        this.cost = cost;
        this.cookiesPerSecond = cookiesPerSecond;
        this.player = player;
    }

    public void activate() {
        player.addAutoCookies(cookiesPerSecond);
    }
}
