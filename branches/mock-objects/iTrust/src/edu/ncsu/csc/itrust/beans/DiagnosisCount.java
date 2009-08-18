package edu.ncsu.csc.itrust.beans;

/**
 * A bean for storing data about Diagnosis Counts.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters… 
 * to create these easily)
 */
public class DiagnosisCount {
	private int numInRegion = 0;
	private int numInState = 0;
	private int numInTotal = 0;

	public int getNumInRegion() {
		return numInRegion;
	}

	public void setNumInRegion(int numInRegion) {
		this.numInRegion = numInRegion;
	}

	public int getNumInState() {
		return numInState;
	}

	public void setNumInState(int numInState) {
		this.numInState = numInState;
	}

	public int getNumInTotal() {
		return numInTotal;
	}

	public void setNumInTotal(int numInTotal) {
		this.numInTotal = numInTotal;
	}
}
