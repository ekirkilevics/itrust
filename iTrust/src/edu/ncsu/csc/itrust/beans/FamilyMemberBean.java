package edu.ncsu.csc.itrust.beans;

public class FamilyMemberBean {
	private long mid = 0;
	private String relation = "";
	private String firstName = "";
	private String lastName = "";

	public FamilyMemberBean() {
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}
}
