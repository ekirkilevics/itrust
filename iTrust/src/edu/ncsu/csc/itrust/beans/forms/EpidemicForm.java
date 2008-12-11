package edu.ncsu.csc.itrust.beans.forms;

import java.text.SimpleDateFormat;
import java.util.Date;
import edu.ncsu.csc.itrust.enums.State;

public class EpidemicForm {
	private String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
	private String weeksBack = "4";
	private String zip = "27607";
	private String state = State.NC.getName();
	private String detector = "";
	private String icdLower = "250.00";
	private String icdUpper = "251.00";
	private String useEpidemic = "true";

	public String getUseEpidemic() {
		return useEpidemic;
	}

	public void setUseEpidemic(String useEpidemic) {
		this.useEpidemic = useEpidemic;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWeeksBack() {
		return weeksBack;
	}

	public void setWeeksBack(String weeksBack) {
		this.weeksBack = weeksBack;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDetector() {
		return detector;
	}

	public void setDetector(String detector) {
		this.detector = detector;
	}

	public String getIcdLower() {
		return icdLower;
	}

	public void setIcdLower(String icdLower) {
		this.icdLower = icdLower;
	}

	public String getIcdUpper() {
		return icdUpper;
	}

	public void setIcdUpper(String icdUpper) {
		this.icdUpper = icdUpper;
	}
}
