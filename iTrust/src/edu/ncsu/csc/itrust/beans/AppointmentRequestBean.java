package edu.ncsu.csc.itrust.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Ted Lowery (tblowery@ncsu.edu)
 *
 */
public class AppointmentRequestBean {
	
	public final static String NeedPatientConfirm = "Need Patient Confirm";
	public final static String NeedLHCPConfirm = "Need LHCP Confirm";
	public final static String Scheduled = "Scheduled";
	public final static String Rejected = "Rejected";
	public final static String Rescheduled = "Rescheduled";
	
	public final static String dateFormat = "MM/dd/yyyy HH:mm";

	private long ID = 0L;
	private long requesterMID;
	private long requestedMID;
	private String hospitalID;
	private Date date1;
	private String date1Str;
	private String date2Str;
	private Date date2;
	private int minutes;
	private String reason;
	private int weeksUntilVisit;
	private String status;
	
	public void setID(long iD) {
		ID = iD;
	}
	public long getID() {
		return ID;
	}
	public void setRequesterMID(long requesterMID) {
		this.requesterMID = requesterMID;
	}
	public long getRequesterMID() {
		return requesterMID;
	}
	public void setRequestedMID(long requestedMID) {
		this.requestedMID = requestedMID;
	}
	public long getRequestedMID() {
		return requestedMID;
	}
	public void setHospitalID(String hospitalID) {
		this.hospitalID = hospitalID;
	}
	public String getHospitalID() {
		return hospitalID;
	}
	
	public String getHospitalIDString() {
		if(hospitalID == null) return "";
		else return hospitalID;
	}
	
	public void setDate1(Date date1) {
		this.date1 = (date1 == null ? null : (Date)date1.clone());
	}
	public void setDate1String(String s) {
			date1Str = s;
	}
	public Date getDate1() throws ParseException{
		if (date1Str != null && !date1Str.equals(""))
		date1 = new SimpleDateFormat(dateFormat).parse(date1Str);
		return (date1 == null ? null : (Date)date1.clone());
	}
	public String getDate1String() throws ParseException{
		if (date1Str == null || date1Str.equals("")) return "";
		date1 = new SimpleDateFormat(dateFormat).parse(date1Str);
		return new SimpleDateFormat(dateFormat).format(date1);
	}
	public void setDate2(Date date2) {
		this.date2 = (date2 == null ? null : (Date)date2.clone());
	}
	public void setDate2String(String s) throws ParseException {
			date2Str = s;
	}
	public Date getDate2() throws ParseException{
		if (date2Str != null && !date2Str.equals(""))
			date2 = new SimpleDateFormat(dateFormat).parse(date2Str);
		return (date2 == null ? null : (Date)date2.clone());
	}
	public String getDate2String() throws ParseException{
		if (date2Str == null || date2Str.equals("")) return "";
		date2 = new SimpleDateFormat(dateFormat).parse(date2Str);
		return new SimpleDateFormat(dateFormat).format(date2);
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getReason() {
		return reason;
	}
	
	public String getReasonString() {
		if(reason == null) return "";
		return reason;
	}
	
	public void setWeeksUntilVisit(int weeksUntilVisit) {
		this.weeksUntilVisit = weeksUntilVisit;
	}
	public int getWeeksUntilVisit() {
		return weeksUntilVisit;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}

	public int getMinutes () {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
}
