package Item;

import logic.GameLogic;

public class CritRateItem extends Item {

	private double critChange;
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
		GameLogic.getPlayer().setCritRate(critChange);
	}

	@Override
	public void upgrade() {
		setCritChange(getCritChange() + getScalFacStatus());
		setCostItem((int)(getCostItem() * (1 + getScalFacCost())));		
	}

	public double getCritChange() {
		return critChange;
	}

	public void setCritChange(double critChange) {
		this.critChange= critChange;
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
