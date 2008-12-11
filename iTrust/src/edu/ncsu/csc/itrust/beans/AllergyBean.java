package edu.ncsu.csc.itrust.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AllergyBean {
	private long id;
	private long patientID;
	private String description;
	private Date firstFound;

	public AllergyBean() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getFirstFound() {
		return (Date) firstFound.clone();
	}

	public void setFirstFound(Date firstFound) {
		if (null != firstFound)
			this.firstFound = (Date) firstFound.clone();
		else
			this.firstFound = null;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String toString() {
		return this.description;
	}

	public long getPatientID() {
		return patientID;
	}

	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}

	public String getFirstFoundStr() {
		try {
			return new SimpleDateFormat("MM/dd/yyyy").format(getFirstFound());
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
