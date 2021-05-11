package data;

public class Player implements Comparable<Player>{

	private String userName;
	private int score;
	private int coin;
	private String companion;


	public Player() {
	}

	public Player(String userName, int score, int coin, String companion) {
		this.userName = userName;
		this.score = score;
		this.coin = coin;
		this.companion = companion;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public String getCompanion() {
		return companion;
	}

	public void setCompanion(String companion) {
		this.companion = companion;
	}

	@Override
	public String toString() {
		return "Player [ Name=" + userName
				+ ", score=" + score + ", coin=" + coin
				+ ", companion=" + companion + "]";
	}

	@Override
	public int compareTo(Player o) {
		if (this.score < o.getScore()) 
			return 1;
		
		else if (this.score > o.getScore()) 
			return -1;
		
		return 0;
	}

}
