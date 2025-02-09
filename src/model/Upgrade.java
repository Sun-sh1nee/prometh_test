package model;

public class Upgrade {
    private String name;
    private int cost;
    private Runnable upgradeEffect;

    public Upgrade(String name, int cost, Runnable upgradeEffect) {
        this.name = name;
        this.cost = cost;
        this.upgradeEffect = upgradeEffect;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public void applyUpgrade() {
        upgradeEffect.run();
    }
}
