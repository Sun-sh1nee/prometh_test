package card;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import logic.GameLogic;

public class FriendshipIsMagicCard extends BaseCard{
	private double companionBoost;
	private boolean isOnCooldown = false;
	public FriendshipIsMagicCard(String name , String image , CardTier tier) {
		super(name, image, tier);
		randomizeAttributes();
	}
	
	private void randomizeAttributes() {
        Random random = new Random();
        
        switch (tier) {
        case COMMON: {  
        	companionBoost = random.nextDouble() * 20 + 30;       
            break;
        }case RARE: {     
        	companionBoost = random.nextDouble() * 25 + 50;     
            break;
        }case EPIC: {     
        	companionBoost = random.nextDouble() * 25 + 100;     
            break;
        }case LEGENDARY: {   
        	companionBoost = random.nextDouble() * 100 + 200;      
            break;
        }
        }
    }
	
	public void activate() {
		if (isOnCooldown)return;
	    
	    isOnCooldown = true;
	    
	    GameLogic.ApplyCompanionBoostCardBoost(companionBoost);
	    
	    Timeline buffTimeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
	      
	        GameLogic.CancelCompanionBoostCardBoost(companionBoost);
	        
	        Timeline cooldownTimeline = new Timeline(new KeyFrame(Duration.seconds(6), e -> {
	            
	            isOnCooldown = false;
	            
	        }));
	        cooldownTimeline.setCycleCount(1);
	        cooldownTimeline.play();
	    }));
	    buffTimeline.setCycleCount(1);
	    buffTimeline.play();
	}
	
	@Override
	public String toString() {
		
		return String.format("Card: %s [%s Tier]\n- Increase: %.2f%% companion damage \n-cooldown: 6sec",
				name , tier , companionBoost);
		
	}

}
