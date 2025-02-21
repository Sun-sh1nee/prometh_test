package Item;

import logic.GameLogic;

public class CritRateItem extends Item {

	private double critChance;
	private double scalFacCost;
	private double scalFacStatus;
	
	public CritRateItem(String itemURL) {
		super("CritRateItem", 500, itemURL);
		
		setCritChange(0.3);
		setScalFacCost(0.001);
		setScalFacStatus(0.3);
	}

	@Override
	public void updateStat() {
		// TODO Auto-generated method stub
		GameLogic.getPlayer().setCritRate(critChance);
	}

	@Override
	public void upgrade() {
		setCritChange(getCritChance() + getScalFacStatus());
		setCostItem((int)(getCostItem() * (1 + getScalFacCost())));		
	}

	public double getCritChance() {
		return critChance;
	}

	public void setCritChange(double critChance) {
		this.critChance= critChance;
	}

	public double getScalFacStatus() {
		return scalFacStatus;
	}

	public void setScalFacStatus(double scalFacStatus) {
		this.scalFacStatus = scalFacStatus;
	}

	public double getScalFacCost() {
		return scalFacCost;
	}

	public void setScalFacCost(double scalFacCost) {
		this.scalFacCost = scalFacCost;
	}
	
	

}
