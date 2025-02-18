package Item;

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
		
	}

	@Override
	public void upgrade() {
		setChanceToDropGem(getChanceToDropGem() + getScalFacStatus());
		setCostItem((int)(getCostItem() * (1 + getScalFacCost())));
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
