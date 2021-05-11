package data;

/*
 * Class purpose : To handle JSON data
 */
public class NameRank {
	private String userName;
	private int rank;
	private int score;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "NameRank [userName=" + userName + ", rank=" + rank + ", score=" + score + "]";
	}
}
