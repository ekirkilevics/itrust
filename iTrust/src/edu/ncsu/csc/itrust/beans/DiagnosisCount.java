package edu.ncsu.csc.itrust.beans;

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
