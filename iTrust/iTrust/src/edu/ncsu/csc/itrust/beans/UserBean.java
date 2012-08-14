package edu.ncsu.csc.itrust.beans;

public class UserBean {
	private long mid;
	private String password;
	public UserBean(){
		
	}
	public long getMID() {
		return mid;
	}
	public void setMID(long mid) {
		this.mid = mid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
