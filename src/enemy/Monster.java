package enemy;

public class Monster {
	private int monsterHp;
	private int stageMonster;
	private String monsterURL;
	private double scalFacMonster;
	
	
	public Monster(int health, int stage,double scal,String imageMonster) {
		this.setMonsterHp(health);
		this.setScalFacMonster(scal);
		this.setStageMonster(stage);
		this.monsterURL =  ClassLoader.getSystemResource(imageMonster).toString();
		
	}
	
	
	public void upgrade() {
		this.monsterHp *= this.scalFacMonster;
	}
	
	public int getNextStage() {
		return (int) (this.monsterHp * this.scalFacMonster);
	}


	public int getMonsterHp() {
		
		return monsterHp;
	}


	public void setMonsterHp(int monsterHp) {
		
		this.monsterHp = Math.max(monsterHp, 0);
	}


	public int getStageMonster() {
		return stageMonster;
	}


	public void setStageMonster(int stageMonster) {
		this.stageMonster = Math.max(stageMonster, 0);
	}


	public String getMonsterURL() {
		return monsterURL;
	}


	public double getScalFacMonster() {
		return scalFacMonster;
	}


	public void setScalFacMonster(double scalFacMonster) {
		this.scalFacMonster = Math.max(scalFacMonster, 1.0);
	};
}
