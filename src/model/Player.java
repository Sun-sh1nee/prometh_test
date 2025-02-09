package model;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int cookies;
    private int clickPower;
    private int autoCookies;
    private List<Card> cardCollection;

    public Player(String name) {
        this.name = name;
        this.cookies = 0;
        this.clickPower = 1;
        this.autoCookies = 0;
        this.cardCollection = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getCookies() {
        return cookies;
    }

    public void addCookies(int amount) {
        cookies += amount;
    }

    public int getClickPower() {
        return clickPower;
    }

    public void increaseClickPower(int amount) {
        clickPower += amount;
    }

    public int getAutoCookies() {
        return autoCookies;
    }

    public void addAutoCookies(int amount) {
        autoCookies += amount;
    }

    public List<Card> getCardCollection() {
        return cardCollection;
    }

    // 🔥 เมธอด click() เพื่อให้ผู้เล่นกดคุกกี้
    public void click() {
        addCookies(clickPower);
    }
}
