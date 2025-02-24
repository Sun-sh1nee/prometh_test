package companion;

public class Companion {
	private int attackPerSec;
	private int levelCompanion;
	private int costCompanion;
	private String companionURL;
	private double scalFacCompanion;
	private double scalFacPrice;
	
	public Companion(int attack , int cost, double scalFacCompanion ,double scalFacPrice,String image) {
		this.setAttackPerSec(attack);
		this.setCostCompanion(cost);
		this.setScalFacCompanion(scalFacCompanion);
		this.setScalFacPrice(scalFacPrice);
		this.setLevelCompanion(1);
//		this.companionURL = ClassLoader.getSystemResource(image).toString();
		
	}

	public void upgrade() {
		this.setAttackPerSec((int) (attackPerSec * scalFacCompanion));
		this.setCostCompanion((int) (costCompanion * scalFacPrice));
		this.setLevelCompanion(levelCompanion + 1);
	}
	public int getAttackPerSec() {
		return attackPerSec;
	}

	public void setAttackPerSec(int attackPerSec) {
		this.attackPerSec = Math.max(attackPerSec, 1);
	}

	public int getLevelCompanion() {
		return levelCompanion;
	}

	public void setLevelCompanion(int levelCompanion) {
		this.levelCompanion = Math.max(levelCompanion, 1);
	}

	public int getCostCompanion() {
		return costCompanion;
	}

	public void setCostCompanion(int costCompanion) {
		this.costCompanion = Math.max(costCompanion, 1);
	}

	public double getScalFacCompanion() {
		return scalFacCompanion;
	}

	public void setScalFacCompanion(double scalFacCompanion) {
		this.scalFacCompanion = Math.max(scalFacCompanion, 1);
	}

	public double getScalFacPrice() {
		return scalFacPrice;
	}

	public void setScalFacPrice(double scalFacPrice) {
		this.scalFacPrice = scalFacPrice;
	}

	public String getImageURL() {
		return companionURL;
	}


}
