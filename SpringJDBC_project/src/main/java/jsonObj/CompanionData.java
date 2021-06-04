package jsonObj;

public class CompanionData {
	
	private String userName;
	private String companion;
	private int level;
	
	
	public CompanionData(String userName) {
		this.userName = userName;
		this.level = 1;
	}
	
	public CompanionData(String userName, String companion) {
		this.userName = userName;
		this.companion = companion;
		this.level = 1;
	}
	
	public CompanionData(String username, String companion, int level) {
		
		this.userName = username;
		this.companion = companion;
		this.level = level;
	}
	
	public CompanionData() {
		this.level = 1;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		this.userName = username;
	}

	public String getCompanion() {
		return companion;
	}

	public void setCompanion(String companion) {
		this.companion = companion;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "CompanionData [userName=" + userName + ", companion=" + companion + ", level=" + level + "]";
	}
	
}
