package jsonObj;

public class CompanionData {
	
	private String userName;
	private boolean knight;
	private boolean archer;
	private boolean mage;
	
	public CompanionData() {}
	
	public CompanionData(String username, boolean knight, boolean archer, boolean mage) {
		
		this.userName = username;
		this.knight = knight;
		this.archer = archer;
		this.mage = mage;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public boolean isKnight() {
		return knight;
	}

	public void setKnight(boolean knight) {
		this.knight = knight;
	}

	public boolean isArcher() {
		return archer;
	}

	public void setArcher(boolean archer) {
		this.archer = archer;
	}

	public boolean isMage() {
		return mage;
	}

	public void setMage(boolean mage) {
		this.mage = mage;
	}

	@Override
	public String toString() {
		return "CompanionData [username=" + userName + ", knight=" + knight + ", archer=" + archer + ", mage=" + mage
				+ "]";
	}
	
	
}
