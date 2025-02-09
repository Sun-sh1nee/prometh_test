package model;

public class CardSpecial extends Card {
    private String specialEffect;

    public CardSpecial(String name, String rarity, String specialEffect) {
        super(name, rarity);
        this.specialEffect = specialEffect;
    }

    @Override
    public void applyEffect(Player player) {
        // Special effects for Story Mode
        System.out.println("Applying special effect: " + specialEffect);
    }
}
