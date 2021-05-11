package data;

public class User {
	private long id;
	private String userName;
	private String email;
	private String password;
	private String userVersion;
	
	// Constructors
	public User() {}
	public User(long id, String userName, String email, String password, String userVersion) {
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.userVersion = userVersion;
	}
	public User(String userName, String email, String password, String userVersion) {
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.userVersion = userVersion;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserVersion() {
		return userVersion;
	}
	public void setUserVersion(String userVersion) {
		this.userVersion = userVersion;
	}
	
	@Override
	public String toString() {
		return "User [ id=" + id
				+ ", userName=" + userName + ", email=" + email
				+ ", password=" + password + ", userVersion=" + userVersion +"]";
	}
}
