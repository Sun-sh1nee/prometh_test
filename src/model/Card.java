package model;

public abstract class Card {
    protected String name;
    protected String rarity; // สูง, กลาง, ต่ำ

    public Card(String name, String rarity) {
        this.name = name;
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public String getRarity() {
        return rarity;
    }

    public abstract void applyEffect(Player player);
}
