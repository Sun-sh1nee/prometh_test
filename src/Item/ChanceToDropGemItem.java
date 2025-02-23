package Item;

import logic.GameLogic;

public class ChanceToDropGemItem extends Item {

	private double chanceToDropGem;
	private double scalFacCost;
	private double scalFacStatus;
	
	
	public ChanceToDropGemItem(String itemURL) {
		super("ChanceToDropGemItem", 1000, itemURL);
		
		setChanceToDropGem(0.1);
		setScalFacCost(0.0001);
		setScalFacStatus(0.40);
		
	}

	@Override
	public void updateStat() {
		// TODO Auto-generated method stub
		GameLogic.getPlayer().setChanceToDropGem(chanceToDropGem);
	}

	@Override
	public void upgrade() {
		this.setLevelItem(levelItem.get()+1);
		setChanceToDropGem(getChanceToDropGem() + getScalFacStatus());
		setCostItem((int)(getCostItem().get() * (1 + getScalFacCost())));
		updateStat();
	}

	public double getChanceToDropGem() {
		return chanceToDropGem;
	}

	public void setChanceToDropGem(double chanceToDropGem) {
		this.chanceToDropGem = chanceToDropGem;
	}

	public double getScalFacCost() {
		return scalFacCost;
	}

	public void setScalFacCost(double scalFacCost) {
		this.scalFacCost = scalFacCost;
	}

	public double getScalFacStatus() {
		return scalFacStatus;
	}

	public void setScalFacStatus(double scalFacStatus) {
		this.scalFacStatus = scalFacStatus;
	}
	
	

}
