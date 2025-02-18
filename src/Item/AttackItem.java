package Item;

public class AttackItem extends Item {
	private int attack;
	private double scalFacStatus;
	private double scalFacCost;
	
	public AttackItem(String itemURL) {
		super("AttackItem", 100, itemURL);
		
		setAttack(100);
		setScalFacCost(0.08);
		setScalFacStatus(0.15);
	}

	@Override
	public void updateStat() {
		// GameLogic update player status 
		// ex GameLogic.getInstance().getPlayer().setAttck(attack);
	}

	@Override
	public void upgrade() {
		setAttack((int)(getAttack() * getScalFacStatus()));
		setCostItem((int)(getCostItem() * (1 + getScalFacCost())));
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack<1?1:attack;
	}

	public double getScalFacStatus() {
		return scalFacStatus;
	}

	public void setScalFacStatus(double scalFacStatus) {
		this.scalFacStatus = scalFacStatus<0?0.01:scalFacStatus;
	}

	public double getScalFacCost() {
		return scalFacCost;
	}

	public void setScalFacCost(double scalFacCost) {
		this.scalFacCost = scalFacCost<0?0.01:scalFacCost;
	}
	
	
	
}
