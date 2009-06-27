package edu.ncsu.csc.itrust.beans;

public class CODBean {
	private String diagnosisName, icdCode;
	private int total;

	public String getDiagnosisName() {
		return diagnosisName;
	}

	public void setDiagnosisName(String diagnosisName) {
		this.diagnosisName = diagnosisName;
	}

	public String getIcdCode() {
		return icdCode;
	}

	public void setIcdCode(String icdCode) {
		this.icdCode = icdCode;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
