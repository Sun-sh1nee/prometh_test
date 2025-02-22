package logic;

import java.util.ArrayList;
import java.util.Random;

import companion.Companion;
import enemy.Monster;
import javafx.animation.KeyFrame;

import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
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
	private static SimpleBooleanProperty music = new SimpleBooleanProperty();
	private static int damagePerSec ;
	private static long maxHP;
	private static boolean isStoryBattle;
	private static int stage;
	private static ArrayList<Monster> monsterStory;
	private static Monster monsterHome;
	
	private static Timeline dpsHomeThread;
	private static Timeline dpsStoryThread;
	private static Player player = new Player();
	private static Companion[] companions = new Companion[6];
	private static boolean haveCompanion[] = new boolean[6];
//	===================================
	private static double damageCardBoost;
	private static double gemDropChanceCardBoost;
	private static double critChanceCardBoost;
	private static double critDamageCardBoost;
	private static double companionBoostCardBoost;
	private static double extraDamage;

//==========================================
	public static void init() {

		stage = 1;
		croissantCount.set(0);
		gemCount.set(0);
		initMonster();
		initCompanion();
		music.set(true);
		monsterHome = monsterStory.get(0);
		monsterHpHome.set(monsterHome.getMonsterHp());
		maxHP = monsterHome.getMonsterHp();
		monsterHpStory.set(monsterStory.get(0).getMonsterHp());
//		========
		damageCardBoost = 0;
		gemDropChanceCardBoost = 0;
		critChanceCardBoost = 0;
		critDamageCardBoost = 0;
		companionBoostCardBoost = 0;
		extraDamage = 0;
		isStoryBattle = false;
//		========
		updateDamagePerSec();

		startDpsHome();
	}

//	==================================================
	public static boolean isStoryBattle() {
		return isStoryBattle;
	}

	public static void setStoryBattle(boolean isStoryBattle) {
		GameLogic.isStoryBattle = isStoryBattle;
	}
	
	public static double getextraDamage() {
		return extraDamage;
	}

	public static void ApplyextraDamage(double extraDamage) {
		GameLogic.extraDamage += extraDamage;
	}

	public static void CancelextraDamage(double decrease) {
		GameLogic.extraDamage -= decrease;
	}


	public static double getDamageCardBoost() {
		return damageCardBoost;
	}

	public static void ApplyDamageCardBoost(double damageCardBoost) {
		GameLogic.damageCardBoost += damageCardBoost;
	}

	public static void CancelDamageCardBoost(double decrease) {
		GameLogic.damageCardBoost -= decrease;
	}

	public static double getGemDropChanceCardBoost() {
		return gemDropChanceCardBoost;
	}

	public static void ApplyGemDropChanceCardBoost(double gemDropChanceCardBoost) {
		GameLogic.gemDropChanceCardBoost += gemDropChanceCardBoost;
	}

	public static void CancelGemDropChanceCardBoost(double decrease) {
		GameLogic.gemDropChanceCardBoost -= decrease;
	}

	public static double getCritChanceCardBoost() {
		return critChanceCardBoost;
	}

	public static void ApplyCritChanceCardBoost(double critChanceCardBoost) {
		GameLogic.critChanceCardBoost += critChanceCardBoost;
	}

	public static void CancelCritChanceCardBoost(double decrease) {
		GameLogic.critChanceCardBoost -= decrease;
	}

	public static double getCritDamageCardBoost() {
		return critDamageCardBoost;
	}

	public static void ApplyCritDamageCardBoost(double critDamageCardBoost) {
		GameLogic.critDamageCardBoost += critDamageCardBoost;
	}

	public static void CancelCritDamageCardBoost(double decrease) {
		GameLogic.critDamageCardBoost -= decrease;
	}

	public static double getCompanionBoostCardBoost() {
		return companionBoostCardBoost;
	}

	public static void ApplyCompanionBoostCardBoost(double companionBoostCardBoost) {
		GameLogic.companionBoostCardBoost += companionBoostCardBoost;
	}

	public static void CancelCompanionBoostCardBoost(double decrease) {
		GameLogic.companionBoostCardBoost -= decrease;
	}
// =============================================

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

//===================================
	public static Monster getMonster() {

		if (!isStoryBattle) {
			return monsterHome;
		} else {
			return monsterStory.get(stage - 1);
		}

	}

	public static int getStage() {
		return stage;
	}

//================================
	private static void initCompanion() {
		for (int i = 0; i < 6; ++i) {
			int attackBase = (10 + i) * ((int) Math.pow(5, i + 1));
			int costBase = (1 + i) * ((int) Math.pow(10, i + 2));
			double costScal = Math.random() + 1.1;
			double attackScal = Math.random() + 1.0;
			companions[i] = new Companion(attackBase, costBase, attackScal, costScal, null);
			haveCompanion[i] = false;
		}
	}

	public static void updateDamagePerSec() {
		int damageOutPut = 0;
		for (int i = 0; i < 6; ++i) {
			if (haveCompanion[i]) {
				damageOutPut += companions[i].getAttackPerSec();
			}

		}
		if(companionBoostCardBoost > 0)damageOutPut *= (1+(companionBoostCardBoost/100));
		damagePerSec = (damageOutPut);
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

	public static SimpleBooleanProperty musicProperty() {
		return music;
	}

	public static void addCroissants(long amount) {
		croissantCount.set(croissantCount.get() + amount);
	}

	public static SimpleDoubleProperty monsterHpHomeProperty() {
		return monsterHpHome;
	}
	public static SimpleDoubleProperty monsterHpStoryProperty() {
		return monsterHpStory;
	}

	public static long getMaxHP() {
		return maxHP;
	}

	public static void reduceMonsterHpHome(double amount) {
		monsterHpHome.set(monsterHpHome.get() - amount);
		if (monsterHpHome.get() <= 0) {
			monsterHpHome.set(maxHP);
			addCroissants(monsterHome.getCoinDrop());
			Random random = new Random();
			if (random.nextDouble(100) < player.getChanceToDropGem()) {
				gemCount.set(gemCount.get() + 1);
			}
		}
	}

//	====================================================
	public static Monster getMonsterStage(int Stage) {

		return monsterStory.get(Stage - 1);
	}

	public static void reduceMonsterHpStory(double amount) {
		monsterHpStory.set(monsterHpStory.get() - amount);
		if (monsterHpStory.get() <= 0) {

			monsterHpHome.set(monsterStory.get(stage - 1).getMonsterHp());

			maxHP = monsterStory.get(stage - 1).getMonsterHp();
			stage += 1;
			monsterHpStory.set(monsterStory.get(stage - 1).getMonsterHp());
			gemCount.set(gemCount.get() + 2);
		}
	}

// =============================================
	public static Player getPlayer() {
		return player;
	}

	public static void clickHandle() {
		double damage = player.getAttackPerClick();
		if(damageCardBoost > 0)damage *= damageCardBoost;
		Random random = new Random();
		double critRate = player.getCritRate() + critChanceCardBoost;
		if (random.nextDouble(100) < critRate) {
			damage *= (player.getCritDamage() + critDamageCardBoost);
		}
		if(extraDamage > 0)damage *= (1 + (extraDamage/100.0));
		
		if(isStoryBattle) {
			reduceMonsterHpStory(damage);
		}else {
			reduceMonsterHpHome(damage);
		}
		
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
