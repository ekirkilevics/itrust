package edu.ncsu.csc.itrust.beans;

/**
 * A bean for storing data about a hospital.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters� 
 * to create these easily)
 */
public class HospitalBean {
	String hospitalID = "";
	String hospitalName = "";
	String hospitalAddress = "";
	String hospitalCity = "";
	String hospitalState = "";
	String hospitalZip = "";
	double hospitalLat = 0;
	double hospitalLng = 0;

	public HospitalBean() {
	}

	public HospitalBean(String hospitalID) {
		this.hospitalID = hospitalID;
	}

	public HospitalBean(String hospitalID, String hospitalName) {
		this.hospitalID = hospitalID;
		this.hospitalName = hospitalName;
	}
	
	public HospitalBean(String hospitalID, String hospitalName, String hospitalAddress, String hospitalCity, String hospitalState, String hospitalZip, double hospitalLat, double hospitalLng) {
		this.hospitalID = hospitalID;
		this.hospitalName = hospitalName;
		this.hospitalAddress = hospitalAddress;
		this.hospitalCity = hospitalCity;
		this.hospitalState = hospitalState;
		this.hospitalZip = hospitalZip;
		this.hospitalLat = hospitalLat;
		this.hospitalLng = hospitalLng;
	}

	public String getHospitalID() {
		return hospitalID;
	}

	public void setHospitalID(String hospitalID) {
		this.hospitalID = hospitalID;
	}
	
	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getHospitalAddress() {
		return hospitalAddress;
	}

	public void setHospitalAddress(String hospitalAddress) {
		this.hospitalAddress = hospitalAddress;
	}
	
	public String getHospitalCity() {
		return hospitalCity;
	}

	public void setHospitalCity(String hospitalCity) {
		this.hospitalCity = hospitalCity;
	}
	
	public String getHospitalState() {
		return hospitalState;
	}

	public void setHospitalState(String hospitalState) {
		this.hospitalState = hospitalState;
	}
	
	public String getHospitalZip() {
		return hospitalZip;
	}

	public void setHospitalZip(String hospitalZip) {
		this.hospitalZip = hospitalZip;
	}
	
	public double getHospitalLat() {
		return hospitalLat;
	}

	public void setHospitalLat(double hospitalLat) {
		this.hospitalLat = hospitalLat;
	}
	
	public double getHospitalLng() {
		return hospitalLng;
	}

	public void setHospitalLng(double hospitalLng) {
		this.hospitalLng = hospitalLng;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass().equals(this.getClass()) && this.equals((HospitalBean) obj);
	}

	@Override
	public int hashCode() {
		assert false : "hashCode not designed";
		return 42; // any arbitrary constant will do
	}

	private boolean equals(HospitalBean other) {
		return hospitalID.equals(other.hospitalID) && hospitalName.equals(other.hospitalName);
	}
}
