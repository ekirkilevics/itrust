package edu.ncsu.csc.itrust.beans;

import java.io.Serializable;

/**
 * A bean for storing data about a hospital employee.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters� 
 * to create these easily)
 */
public class UserPrefsBean implements Serializable {
	private static final long serialVersionUID = 6575544592646001050L;
	
	private long MID;
	private String themeColor;
	private String secondaryColor;
	
	public UserPrefsBean() {
		MID = 0;
		themeColor = null;
		secondaryColor = null;
	}

	public long getMID() {
		return this.MID;
	}

	public void setMID(long MID) {
		this.MID = MID;
	}
	
	public String getThemeColor() {
		return this.themeColor;
	}

	public void setThemeColor(String themeColor) {
		this.themeColor = themeColor;
	}
	
	public String getSecondaryColor() {
		return this.secondaryColor;
	}

	public void setSecondaryColor(String secondaryColor) {
		this.secondaryColor = secondaryColor;
	}
	

}
