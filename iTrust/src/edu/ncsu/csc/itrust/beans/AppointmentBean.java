package edu.ncsu.csc.itrust.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Ted Lowery (tblowery@ncsu.edu)
 *
 */
public class AppointmentBean {
	private long ID = 0L;
	private long requestID = 0L;
	private long patientMID = 0L;
	private long LHCPMID = 0L;
	private Date appointmentDate;
	private int minutes = 0;
	
	public void setID(long ID) {
		this.ID = ID;
	}
	public long getID() {
		return ID;
	}
	public void setRequestID(long requestID) {
		this.requestID = requestID;
	}
	public long getRequestID() {
		return requestID;
	}
	public void setPatientMID(long patientMID) {
		this.patientMID = patientMID;
	}
	public long getPatientMID() {
		return patientMID;
	}
	public void setLHCPMID(long LHCPMID) {
		this.LHCPMID = LHCPMID;
	}
	public long getLHCPMID() {
		return LHCPMID;
	}
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = (appointmentDate == null ? null : (Date)appointmentDate.clone());
	}
	public Date getAppointmentDate() {
		return (appointmentDate == null ? null : (Date)appointmentDate.clone());
	}
	
	public String getAppointmentDateString() {
		if (appointmentDate == null) return "";
		return new SimpleDateFormat(AppointmentRequestBean.dateFormat).format(appointmentDate);
	}
	
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
}
