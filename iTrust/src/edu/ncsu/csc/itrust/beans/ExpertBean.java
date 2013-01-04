/**
 * 
 */
package edu.ncsu.csc.itrust.beans;

/**
 * @author Tiago Sommer Damasceno (tsdamasc)
 *
 */
public class ExpertBean {

	String hospitalName;
	String hospitalAddress;
	String hospitalCity;
	String hospitalState;
	String hospitalZip;
	double hospitalLat;
	double hospitalLng;
	int frequency;
	String LaboratoryProcedureCode;
	
	public ExpertBean() {
		
	}
	
	public ExpertBean(String hospitalName, String frequencyString) {
		this.hospitalName = hospitalName;
		this.frequency = Integer.parseInt(frequencyString);
	}
	
	public ExpertBean(String hospitalName, String hospitalAddress, String hospitalCity, String hospitalState, String hospitalZip, double hospitalLat, double hospitalLng, String frequencyString, String LaboratoryProcedureCode) {
		this.hospitalName = hospitalName;
		this.hospitalAddress = hospitalAddress;
		this.hospitalCity = hospitalCity;
		this.hospitalState = hospitalState;
		this.hospitalZip = hospitalZip;
		this.hospitalLat = hospitalLat;
		this.hospitalLng = hospitalLng;
		this.frequency = Integer.parseInt(frequencyString);
		this.LaboratoryProcedureCode = LaboratoryProcedureCode;
	}
	
	public String getName() {
		return hospitalName;
	}
	
	public String getAddress() {
		return hospitalAddress;
	}
	
	public String getCity() {
		return hospitalCity;
	}
	
	public String getState() {
		return hospitalState;
	}
	
	public String getZip() {
		return hospitalZip;
	}
	
	public double getLat() {
		return hospitalLat;
	}
	
	public double getLng() {
		return hospitalLng;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public String getLaboratoryProcedureCode() {
		return LaboratoryProcedureCode;
	}
}
