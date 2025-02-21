package logic;

import java.util.ArrayList;

import companion.Companion;
import enemy.Monster;
import javafx.animation.KeyFrame;

import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.util.Duration;
import player.Player;
import ui.SceneManager;

public class GameLogic {
	private static SimpleLongProperty croissantCount = new SimpleLongProperty();
	private static SimpleLongProperty gemCount = new SimpleLongProperty();
	private static SimpleDoubleProperty monsterHpHome = new SimpleDoubleProperty();
	private static SimpleDoubleProperty monsterHpStory = new SimpleDoubleProperty();
	
	private static long maxHP;
//    private static boolean isStory;
	private static int stage;
	private static ArrayList<Monster> monsterStory;
	private static Monster monsterHome;
	private static int damagePerSec;
	private static Timeline dpsHomeThread;
	private static Timeline dpsStoryThread;
	private static Player player = new Player();
	private static Companion[] companions = new Companion[6];
	private static boolean haveCompanion[] = new boolean[6];

	public static void init() {

		stage = 1;
		croissantCount.set(0);
		gemCount.set(0);
		initMonster();
		initCompanion();
		monsterHome = monsterStory.get(0);
		monsterHpHome.set(monsterHome.getMonsterHp());
		maxHP = monsterHome.getMonsterHp();
		monsterHpStory.set(monsterStory.get(0).getMonsterHp());
		updateDamagePerSec();

		startDpsHome();
	}

	public static void startStoryMode() {
		startDpsStory();
		startTimer();
	}

	private static void startTimer() {
		
	}

	private static void TimerCountDown() {

	}

	private static void initMonster() {
		monsterStory = new ArrayList<Monster>();
		ArrayList<String> images = new ArrayList<String>();
		for (int i = 1; i <= 30; ++i) {
			int hpBase = i * 1000;
			int coinBase = i * 100;
			double coinScal = Math.random() + 1.2;
			double hpScal = Math.random() + 1.0;
			monsterStory.add(new Monster(hpBase, coinBase, i, hpScal, coinScal, null));

		}
	}

	public static Monster getMonster() {

		if (SceneManager.getSceneName().equals("HOME")) {
			return monsterHome;
		} else if (SceneManager.getSceneName().equals("STORY")) {
			return monsterStory.get(stage - 1);
		} else {
			return monsterStory.get(0);
		}
	}

	private static void initCompanion() {
		for (int i = 0; i < 6; ++i) {
			int attackBase = (10 + i) * ((int) Math.pow(5, i + 1));
			int costBase = (1 + i) * ((int) Math.pow(10, i + 2));
			double costScal = Math.random() + 1.1;
			double attackScal = Math.random() + 1.0;
			companions[i] = new Companion(attackBase, costBase, attackScal, costScal, null);
			haveCompanion[i] = false;
		}
//    	companions[0] = new Companion(50, 100, 1.3, 1.2, null);
//    	companions[1] = new Companion(400, 1000, 1.3, 1.2, null);
//    	companions[2] = new Companion(50, 100, 1.3, 1.2, null);
//    	companions[3] = new Companion(50, 100, 1.3, 1.2, null);
//    	companions[4] = new Companion(50, 100, 1.3, 1.2, null);
//    	companions[5] = new Companion(50, 100, 1.3, 1.2, null);
	}

	public static void updateDamagePerSec() {
		int damageOutPut = 0;
		for (int i = 0; i < 6; ++i) {
			if (haveCompanion[i]) {
				damageOutPut += companions[i].getAttackPerSec();
			}

		}
		damagePerSec = damageOutPut;
	}

	public static void upgradeCompion(int index) {
		if ((index > 5 || index < 0) && !haveCompanion[index]) {
			System.out.println("upgrade Companion index out of bound");
			return;
		}
		companions[index].upgrade();

	}

	public static void buyCompanion(int index) {
		if ((index > 5 || index < 0) && haveCompanion[index]) {
			System.out.println("buy Companion index out of bound");
			return;
		}
		haveCompanion[index] = true;
	}
	
	
	public static SimpleLongProperty croissantCountProperty() {
		return croissantCount;
	}
	
	public static SimpleLongProperty gemCountProperty() {
		return gemCount;
	}
	public static void addCroissants(long amount) {
		croissantCount.set(croissantCount.get() + amount);
	}

	public static SimpleDoubleProperty monsterHpHomeProperty() {
		return monsterHpHome;
	}

	public static long getMaxHP() {
		return maxHP;
	}

	public static void monsterIsDead() {
		if (SceneManager.getSceneName().equals("STORY")) {

		} else {
			
		}
	}

	public static void reduceMonsterHpHome(double amount) {
		monsterHpHome.set(monsterHpHome.get() - amount);
		if (monsterHpHome.get() <= 0) {
			monsterHpHome.set(maxHP);
			addCroissants(monsterHome.getCoinDrop());
			gemCount.set(gemCount.get() + 2);
		}
	}

	public static void reduceMonsterHpStory(double amount) {
		monsterHpStory.set(monsterHpStory.get() - amount);
		if (monsterHpStory.get() <= 0) {
			stage += 1;
			monsterHpStory.set(monsterStory.get(stage-1).getMonsterHp());
			gemCount.set(gemCount.get() + 2);
		}
	}

	public static Player getPlayer() {
		return player;
	}

	public static void clickHandle() {
		reduceMonsterHpHome(player.getAttackPerClick());
	}

	public static void startDpsHome() {
		if (dpsHomeThread != null) {
			dpsHomeThread.stop();
		}
		if (dpsStoryThread != null) {
			dpsStoryThread.stop();
		}
		dpsHomeThread = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			reduceMonsterHpHome(damagePerSec);
		}));
		dpsHomeThread.setCycleCount(Timeline.INDEFINITE);
		dpsHomeThread.play();
	}

	public static void startDpsStory() {
		if (dpsStoryThread != null) {
			dpsStoryThread.stop();
			
		}
		if (dpsHomeThread != null) {
			dpsHomeThread.stop();
		}
		dpsStoryThread = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			reduceMonsterHpStory(damagePerSec);
		}));
		dpsStoryThread.setCycleCount(Timeline.INDEFINITE);
		dpsStoryThread.play();
	}

	public static int getDamagePerSec() {
		return damagePerSec;
	}
}
