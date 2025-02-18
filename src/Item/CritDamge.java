package Item;

public class CritDamge extends BaseItem{
	
	private double critDamage;
	private double scalFacCost;
	private double scalFacStatus;
	
	public CritDamge(String itemURL) {
		super("CritDamge", 750, itemURL);
		
		setCritDamage(1.5);
		setScalFacCost(0.001);
		setScalFacStatus(0.35);
	}

	@Override
	public void updateStat() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void upgrade() {
		setCritDamage(getCritDamage() + getScalFacStatus());
		setCostItem((int)(getCostItem() * (1 + getScalFacCost())));
	}

	public double getCritDamage() {
		return critDamage;
	}

	public void setCritDamage(double critDamage) {
		this.critDamage = critDamage;
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
