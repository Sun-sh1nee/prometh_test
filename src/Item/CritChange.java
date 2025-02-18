package Item;

public class CritChange extends BaseItem {

	private double critChange;
	private double scalFacCost;
	private double scalFacStatus;
	
	public CritChange(String itemURL) {
		super("CritChange", 500, itemURL);
		
		setCritChange(0.3);
		setScalFacCost(0.001);
		setScalFacStatus(0.3);
	}

	@Override
	public void updateStat() {
		// TODO Auto-generated method stub
		
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
