package model;

public class CardBuff extends Card {
    private int buffAmount;

    public CardBuff(String name, String rarity, int buffAmount) {
        super(name, rarity);
        this.buffAmount = buffAmount;
    }

    @Override
    public void applyEffect(Player player) {
        player.increaseClickPower(buffAmount);
    }
}
