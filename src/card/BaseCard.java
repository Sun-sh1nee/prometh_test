package card;

public abstract class BaseCard {
	protected String name;
	protected String CardURL;
	protected CardTier tier;

	public BaseCard(String name , String image , CardTier tier) {
		this.setName(name);
		this.setTier(tier);
//		String path = ClassLoader.getSystemResource(image).toString();
//		this.setCardURL(path);
	}
	
	public abstract String toString();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardURL() {
		return CardURL;
	}

	public void setCardURL(String cardURL) {
		CardURL = cardURL;
	}

	public CardTier getTier() {
		return tier;
	}

	public void setTier(CardTier tier) {
		this.tier = tier;
	}

}
