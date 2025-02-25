package logic;

import java.util.ArrayList;
import java.util.Random;

import card.*;
import companion.Companion;
import enemy.Monster;
import javafx.animation.KeyFrame;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import player.Player;
import ui.BaseScene;
import ui.HomeScene;
import ui.RandomScene;
import ui.SceneManager;
import ui.StoryScene;
import ui.UpgradeScene;

public class GameLogic {
	private static SimpleLongProperty croissantCount = new SimpleLongProperty();
	private static SimpleLongProperty gemCount = new SimpleLongProperty();
	private static SimpleDoubleProperty monsterHpHome = new SimpleDoubleProperty();
	private static SimpleDoubleProperty monsterHpStory = new SimpleDoubleProperty();
	private static SimpleIntegerProperty attackPerClick = new SimpleIntegerProperty();
	private static SimpleIntegerProperty damagePerSec = new SimpleIntegerProperty();
	private static SimpleStringProperty musicSetting = new SimpleStringProperty();
//	!!!!!!!!!
	private static SimpleStringProperty effectSetting = new SimpleStringProperty();
//	================================================================================
	private static SimpleIntegerProperty storyState = new SimpleIntegerProperty();
	private static SimpleDoubleProperty storyTimerProgress = new SimpleDoubleProperty();
	private static boolean isStoryBattle;
	private static ArrayList<Monster> monsterStory;
	private static Monster monsterHome;
	private static int stage;
	private static Timeline dpsHomeThread;
	private static Timeline dpsStoryThread;
	private static Player player = new Player();
	private static Companion[] companions = new Companion[6];
	private static boolean haveCompanion[] = new boolean[6];

	private static double damageCardBoost;
	private static double gemDropChanceCardBoost;
	private static double critChanceCardBoost;
	private static double critDamageCardBoost;
	private static double companionBoostCardBoost;
	private static double extraDamage;

	private static SimpleBooleanProperty isMusic = new SimpleBooleanProperty(true);
	private static AudioClip backgroundSound;

	private static BaseCard[] equippedCards = new BaseCard[4];
	private static ArrayList<BaseCard> ownedCards = new ArrayList<>();

	public static void init() {
		setStage(1);
		initMonster();
		initCompanion();
		monsterHome = monsterStory.get(0);
		monsterHpHome.set(monsterHome.getMonsterHp());
		monsterHpStory.set(monsterStory.get(1).getMonsterHp());


		croissantCount.set(0);
		gemCount.set(0);
		setAttackPerClick();
		setDamagePerSec();
		
		musicSetting.set("ON"); // not finish

		damageCardBoost = 0;
		gemDropChanceCardBoost = 0;
		critChanceCardBoost = 0;
		critDamageCardBoost = 0;
		companionBoostCardBoost = 0;
		extraDamage = 0;
		isStoryBattle = false;

		setStoryState();

		startDpsHome();
	}

	public static BaseCard[] getEquippedCards() {
		return equippedCards;
	}

	public static ArrayList<BaseCard> getOwnedCards() {
		return ownedCards;
	}

	public static SimpleIntegerProperty getDamagePerSecProperty() {
		return damagePerSec;
	}

	public static void equipCard(BaseCard newCard, int slotIndex) {
	
		BaseCard oldCard = equippedCards[slotIndex];
		if (oldCard instanceof BuffStatCard) {
			((BuffStatCard) oldCard).CancelBuff();
		}

		equippedCards[slotIndex] = newCard;

		if (newCard instanceof BuffStatCard) {
			((BuffStatCard) newCard).applyBuff();
		}

	}

	public static void playBackgroundSound() {
		if (backgroundSound == null) {
			String backgroundURL = ClassLoader.getSystemResource("sounds/backgroundSound.mp3").toString();
			backgroundSound = new AudioClip(backgroundURL);
			backgroundSound.setCycleCount(AudioClip.INDEFINITE);
			backgroundSound.setVolume(0.1);
		}

		if (isMusic.get()) {
			backgroundSound.play();
		} else {
			backgroundSound.stop();
		}
	}

	public static BooleanProperty isMusicProperty() {
		return isMusic;
	}

	public static boolean isMusicOn() {
		return isMusic.get();
	}

	public static void setMusic(boolean value) {
		isMusic.set(value);
		playBackgroundSound();
	}

	public static void toggleMusic() {
		setMusic(!isMusic.get());
	}

	public static void startStoryMode() {
		monsterHpStory.set(monsterStory.get(getStage()).getMonsterHp());
		for(BaseCard card : equippedCards) {
			if(card instanceof ActivateCard)((ActivateCard) card).resetCooldown();
		}
		startDpsStory();
		startTimer();
	}

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

	private static void startTimer() {
		int totalTime = 5;
		isStoryBattle = true;
		new Thread(() -> {
			double timeNow = totalTime;
			while (timeNow > 0) {
				try {
					if (!SceneManager.getSceneName().equals("STORY")) {
						isStoryBattle = false;
						return;
					}
					double progress = timeNow / totalTime;
					Platform.runLater(() -> storyTimerProgress.set(progress));

					timeNow -= 0.1;
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					Thread.currentThread().interrupt();
					e1.printStackTrace();
				}
			}
			isStoryBattle = false;
			Platform.runLater(() -> SceneManager.switchTo("HOME"));
		}).start();
	}

	private static void initMonster() {
		monsterStory = new ArrayList<Monster>();
//		ArrayList<String> images = new ArrayList<String>();
		monsterStory.add(new Monster(200, 50, 1, 1.0, 1.0, null));
		for (int i = 1; i <= 30; ++i) {
			int hpBase = i * 1000;
			int coinBase = i * 100;
			double coinScal = 1;
			double hpScal = 1.3;

			monsterStory.add(new Monster(hpBase, coinBase, i, hpScal, coinScal, null));

		}
	}

	public static SimpleLongProperty getCroissantCount() {
		return croissantCount;
	}

	public static void setCroissantCount(long croissant) {
		croissantCount.set(croissant);
	}

	public static Monster getMonster() {

		if (SceneManager.getSceneName().equals("HOME")) {
			return getMonsterStage(stage - 1);
		} else if (SceneManager.getSceneName().equals("STORY")) {
			return getMonsterStage(stage);
		}
		return monsterStory.get(0);

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

	public static SimpleDoubleProperty monsterHpStoryProperty() {
		return monsterHpStory;
	}

	public static SimpleIntegerProperty attackPerClickProperty() {
		return attackPerClick;
	}

	public static SimpleIntegerProperty storyStateProperty() {
		return storyState;
	}

	public static SimpleDoubleProperty storyTimerProgressProperty() {
		return storyTimerProgress;
	}

	public static Monster getMonsterStage(int index) {
		if ((index >= 30 || index < 0)) {
			System.out.println("monster stage index out of bound");
			return null;
		}
		return monsterStory.get(index);
	}

	public static void monsterStoryIsDead() {
		

			if (stage >= monsterStory.size()) {
				System.out.println("🎉 Story Completed! Returning to Home...");
				SceneManager.switchTo("HOME");
				return;
			}

			// Proceed to the next stage
			monsterHome = monsterStory.get(stage);
			monsterHpHome.set(monsterHpStoryProperty().get());
			stage++;
			setStoryState();
			// monsterHpStory.set(monsterStory.get(stage).getMonsterHp());
			monsterHpStory.set(getMonsterStage(stage).getMonsterHp());
			
			for(BaseCard card : equippedCards) {
				if(card instanceof ActivateCard)((ActivateCard) card).resetCooldown();
			}
			// monsterHpHome.set(monsterStory.get(stage - 1).getMonsterHp());
			gemCount.set(gemCount.get() + 2);
			SceneManager.switchTo("HOME");
//			((HomeScene) SceneManager.getSceneNode("HOME")).updateHpMonsterHome();
		
	}
	
	public static void monsterHomeIsDead() {
			monsterHpHome.set(getMonsterStage(stage - 1).getMonsterHp());
			addCroissants(monsterHome.getCoinDrop());

			Random random = new Random();
			if (random.nextDouble() < player.getChanceToDropGem()) {
				gemCount.set(gemCount.get() + 1);
			}
//			((HomeScene) SceneManager.getSceneNode("HOME")).updateHpMonsterHome();
	}

	public static void reduceMonsterHpHome(double amount) {

		monsterHpHome.set(monsterHpHome.get() - amount);
		if (monsterHpHome.get() <= 0) {
			monsterHomeIsDead();
		}

	}

	public static void reduceMonsterHpStory(double amount) {
		monsterHpHome.set(monsterHpHome.get() - amount<=0?0:monsterHpHome.get() - amount);
		if (monsterHpStory.get() <= 0) {
			monsterStoryIsDead();
		}

		
	}



	public static double clickHandle() {
		int amount = player.getAttackPerClick();
		if (damageCardBoost > 0)
			amount *=  (1 + (damageCardBoost/100.0));
		Random random = new Random();
		double critRate = player.getCritRate() + (critChanceCardBoost/100.0);
		if (random.nextDouble() < critRate) {
			amount *= (player.getCritDamage() + (critDamageCardBoost/100.0));
		}
		if (extraDamage > 0)
			amount *= (1 + (extraDamage / 100.0));
		return amount;
	}

	public static void startDpsHome() {
		if (dpsHomeThread != null) {
			dpsHomeThread.stop();
		}

		dpsHomeThread = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			double dam = damagePerSec.get() * (1 + (companionBoostCardBoost / 100.0));
			reduceMonsterHpHome(dam );
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
			double dam = damagePerSec.get() * (1 + (companionBoostCardBoost / 100.0));
			reduceMonsterHpStory(dam);
		}));
		dpsStoryThread.setCycleCount(Timeline.INDEFINITE);
		dpsStoryThread.play();

	}

	public static Player getPlayer() {
		return player;
	}

	public static int getStage() {
		return stage;
	}

	public static void setStage(int stageNow) {
		stage = stageNow;
	}

	public static void setAttackPerClick() {
		attackPerClick.set(player.getAttackPerClick());
	}

	public static boolean reduceGemCount(int amount) {
		if (gemCountProperty().get() < amount)
			return false;
		gemCount.set(gemCountProperty().get() - amount);
		return true;
	}

	public static void setDamagePerSec() {
		damagePerSec.set(player.getDamagePerSec());

	}

	public static void setStoryState() {
		storyState.set(stage);
	}
}