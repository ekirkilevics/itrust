package edu.ncsu.csc.itrust.beans;

public class HCPVisitBean {
	private String HCPName;
	private String HCPSpecialty;
	private String HCPAddr;
	private String OVDate;
	private boolean designated;
	private long hcpMID;
	
	public HCPVisitBean() {
		HCPName = new String();
		HCPSpecialty = new String();
		HCPAddr = new String();
		OVDate = new String();
		
	}
	
	public void setHCPMID(long mid) {
		hcpMID = mid;
	}
	
	public long getHCPMID() {
		return hcpMID;
	}
	
	public String getHCPName() {
		return HCPName;
	}
	
	public void setHCPName(String name) {
		if (null != name) {
			HCPName = name;
		}
	}
	
	public String getHCPSpecialty() {
		return HCPSpecialty;
	}
	
	public void setHCPSpecialty(String specialty) {
		if (null != specialty) {
			HCPSpecialty = specialty;
		}
		else {
			HCPSpecialty = "none";
		}
	}
	
	public String getHCPAddr() {
		return HCPAddr;
	}
	
	public void setHCPAddr(String addr) {
		if (null != addr) {
			HCPAddr = addr;
		}
	}
	
	public String getOVDate() {
		return OVDate;
	}
	
	public void setOVDate(String date) {
		if (null != date) {
			OVDate = date;
		}
	}
	
	public boolean isDesignated() {
		return designated;
	}
	
	public void setDesignated(boolean val) {
		designated = val;
	}
}
