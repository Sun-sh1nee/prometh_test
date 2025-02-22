package logic;

import java.util.ArrayList;
import java.util.Random;

import companion.Companion;
import enemy.Monster;
import javafx.animation.KeyFrame;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.Priority;
import javafx.util.Duration;
import player.Player;
import ui.BaseScene;
import ui.SceneManager;
import ui.StoryScene;

public class GameLogic {
	private static SimpleLongProperty croissantCount = new SimpleLongProperty();
	private static SimpleLongProperty gemCount = new SimpleLongProperty();
	private static SimpleDoubleProperty monsterHpHome = new SimpleDoubleProperty();
	private static SimpleDoubleProperty monsterHpStory = new SimpleDoubleProperty();
	private static SimpleIntegerProperty attackPerClick = new SimpleIntegerProperty();
	private static SimpleIntegerProperty attackPerSec = new SimpleIntegerProperty();
	private static SimpleStringProperty musicSetting = new SimpleStringProperty();
	private static SimpleStringProperty effectSetting = new SimpleStringProperty();
	private static SimpleIntegerProperty storyState = new SimpleIntegerProperty();
	private static SimpleDoubleProperty storyTimerProgress = new SimpleDoubleProperty();
	
	private static long maxHP;
//    private static boolean isStory;
	private static ArrayList<Monster> monsterStory;
	private static Monster monsterHome;
	private static int stage;
	private static int damagePerSec;
	private static Timeline dpsHomeThread;
	private static Timeline dpsStoryThread;
	private static Player player = new Player();
	private static Companion[] companions = new Companion[6];
	private static boolean haveCompanion[] = new boolean[6];

	public static void init() {		
		setStage(1);
		setDamagePerSec(0);
		
		
		initMonster();
		initCompanion();
		monsterHome = monsterStory.get(0);
		monsterHpHome.set(monsterHome.getMonsterHp());
		maxHP = monsterHome.getMonsterHp();
		monsterHpStory.set(monsterStory.get(0).getMonsterHp());
		
		croissantCount.set(0);  // can change in gamelogic
		gemCount.set(0);		// can change in gamelogic
		setattackPerClick();
		setattackPerSec();
		musicSetting.set("ON"); // not finish
		setStoryState();

		startDpsHome();
	}
	
	public static void startStoryMode() {
		monsterHpStory.set(monsterStory.get(getStage()-1).getMonsterHp());
		startDpsStory();
		startTimer();
	}

	private static void startTimer() {
		int totalTime = 5;
	    new Thread(()->{
	    	double timeNow = totalTime;
	    	while(timeNow>0) {
	    		try {
	    			if(!SceneManager.getSceneName().equals("STORY")) return;
	    			double progress = timeNow/totalTime;
	    			Platform.runLater(() -> storyTimerProgress.set(progress));
	    			
	    			timeNow-=0.1;
	    			Thread.sleep(100);
	    		} catch (InterruptedException e1) {
	    			Thread.currentThread().interrupt();
	    			e1.printStackTrace();
	    		}
	    	}
	    	Platform.runLater(() -> SceneManager.switchTo("HOME"));
	    }).start();
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
		setDamagePerSec(damageOutPut);
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
		if ((index >= 30 || index < 0) && haveCompanion[index]) {
			System.out.println("monster stage index out of bound");
			return null;
		}
		return monsterStory.get(index-1);
	}
	
	

	public static void monsterIsDead() {
		if (SceneManager.getSceneName().equals("STORY")) {
			
			if (stage >= monsterStory.size()) {
	            System.out.println("🎉 Story Completed! Returning to Home...");
	            SceneManager.switchTo("HOME"); 
	            return;
	        }

	        // Proceed to the next stage
	        stage++;
	        setStoryState();
	        monsterHpStory.set(monsterStory.get(stage - 1).getMonsterHp());
	        maxHP = monsterStory.get(stage - 1).getMonsterHp();
	        gemCount.set(gemCount.get() + 2);
	        SceneManager.switchTo("HOME"); 
		} else {
			monsterHpHome.set(maxHP);
			addCroissants(monsterHome.getCoinDrop());
			
			Random random = new Random();
	        if (random.nextDouble() < player.getChanceToDropGem()) {
	        	gemCount.set(gemCount.get() + 1);
	        }
		}
	}

	public static void reduceMonsterHpHome(double amount) {
		monsterHpHome.set(monsterHpHome.get() - amount);
		if (monsterHpHome.get() <= 0) {
			monsterIsDead();
		}
	}

	public static void reduceMonsterHpStory(double amount) {
	    monsterHpStory.set(monsterHpStory.get() - amount);
	    if (monsterHpStory.get() <= 0) {
	    	monsterIsDead();
	    }
	}
	

	public static void clickHandle() {
		double damage = attackPerClick.get();
		Random random = new Random();
        if (random.nextDouble(100) < player.getCritRate()) {
        	damage *= player.getCritDamage();
        }
		
		reduceMonsterHpHome(damage);
	}
  
	public static void startDpsHome() {
		if (dpsHomeThread != null) {
			dpsHomeThread.stop();
		}

		dpsHomeThread = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> {
			reduceMonsterHpHome(getDamagePerSec()*0.1);
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
		dpsStoryThread = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> {
			reduceMonsterHpStory(getDamagePerSec()*0.1);
		}));
		dpsStoryThread.setCycleCount(Timeline.INDEFINITE);
		dpsStoryThread.play();
		
		
	}

	public static Player getPlayer() {
		return player;
	}
	
	public static long getMaxHP() {
		return maxHP;
	}
	
	public static int getDamagePerSec() {
		return damagePerSec;
	}
	
	public static void setDamagePerSec(int damage) {
		damagePerSec = damage;
	}
	
	public static int getStage() {
		return stage;
	}
	
	public static void setStage(int stageNow) {
		stage = stageNow;
	}
	
	public static void setattackPerClick() {
	    attackPerClick.set(player.getAttackPerClick());

	    // ✅ บังคับให้ UI รีเฟรชโดยตั้งค่าใหม่จริงๆ
	    Platform.runLater(() -> {
	        double currentHp = monsterHpStory.get();
	        monsterHpStory.set(currentHp - 1);  // เปลี่ยนค่าเล็กน้อยเพื่อกระตุ้นการอัปเดต UI
	        monsterHpStory.set(currentHp);  // ตั้งค่ากลับไปเหมือนเดิม
	    });
	}



	
	public static void setattackPerSec() {
		attackPerClick.set(getDamagePerSec());
	}
	
	public static void setStoryState() {
		storyState.set(stage);
	}
}