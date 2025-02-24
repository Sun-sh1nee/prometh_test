package card;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class ActivateCard extends BaseCard{
	protected int cooldown;
	
	protected double cooldownTimeLeft = 0;
	
	public ActivateCard(String name, String image, CardTier tier) {
		super(name, image, tier);
		
	}
	
	
	 public double getCooldownTimeLeft() {
	        return cooldownTimeLeft;
	 }
	 public void startCooldown() {
	        if (isOnCooldown) return;

	        isOnCooldown = true;
	        cooldownTimeLeft = cooldown;

	        Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
	            cooldownTimeLeft--;
	            if (cooldownTimeLeft <= 0) {
	                isOnCooldown = false;
	                cooldownTimeLeft = 0;
	            }
	        }));

	        cooldownTimer.setCycleCount(cooldown);
	        cooldownTimer.play();
	    }
	
	public int getCooldown() {
		return cooldown;
	}
}
