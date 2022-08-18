package application;

public class Member {
	String userName = null;
	String userId = null;
	String userPassword = null;
	String userHak = null;
	String userBan = null;
	String userBun = null;
	public Member() {
		this("", "", "", "", "", "");
	}
	public Member(String userName, String userId, String userPassword, String userHak, String userBan, String userBun) {
		super();
		this.userName = userName;
		this.userId = userId;
		this.userPassword = userPassword;
		this.userHak = userHak;
		this.userBan = userBan;
		this.userBun = userBun;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserHak() {
		return userHak;
	}
	public void setUserHak(String userHak) {
		this.userHak = userHak;
	}
	public String getUserBan() {
		return userBan;
	}
	public void setUserBan(String userBan) {
		this.userBan = userBan;
	}
	public String getUserBun() {
		return userBun;
	}
	public void setUserBun(String userBun) {
		this.userBun = userBun;
	}
}
