package edu.ncsu.csc.itrust.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Ted Lowery (tblowery@ncsu.edu)
 * 
 */
public class ReportRequestBean {
	private long ID = 0L;
	private long requesterMID = 0L;
	private long patientMID = 0L;
	private long approverMID = 0L;
	private Date requestedDate;
	private Date approvedDate;
	private Date viewedDate;
	private String status = "";
	private String comment = "";

	public final static String dateFormat = "MM/dd/yyyy HH:mm";

	public final static String Requested = "Requested";
	public final static String Approved = "Approved";
	public final static String Rejected = "Rejected";
	public final static String Viewed = "Viewed";

	public ReportRequestBean() {
	}

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

	public void setPatientMID(long patientMID) {
		this.patientMID = patientMID;
	}

	public long getPatientMID() {
		return patientMID;
	}

	public void setApproverMID(long approverMID) {
		this.approverMID = approverMID;
	}

	public long getApproverMID() {
		return approverMID;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = (requestedDate == null ? null : (Date) requestedDate.clone());
	}

	public void setRequestedDateString(String s) {
		try {
			setRequestedDate(new SimpleDateFormat(dateFormat).parse(s));
		} catch (ParseException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public Date getRequestedDate() {
		return (requestedDate == null ? null : (Date) requestedDate.clone());
	}

	public String getRequestedDateString() {
		if (requestedDate == null)
			return "";
		return new SimpleDateFormat(dateFormat).format(requestedDate);
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = (approvedDate == null ? null : (Date) approvedDate.clone());
	}

	public void setApprovedDateString(String s) {
		try {
			setApprovedDate(new SimpleDateFormat(dateFormat).parse(s));
		} catch (ParseException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public Date getApprovedDate() {
		return (approvedDate == null ? null : (Date) approvedDate.clone());
	}

	public String getApprovedDateString() {
		if (approvedDate == null)
			return "";
		return new SimpleDateFormat(dateFormat).format(approvedDate);
	}

	public void setViewedDate(Date viewedDate) {
		this.viewedDate = (viewedDate == null ? null : (Date) viewedDate.clone());
	}

	public void setViewedDateString(String s) {
		try {
			setViewedDate(new SimpleDateFormat(dateFormat).parse(s));
		} catch (ParseException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public Date getViewedDate() {
		return (viewedDate == null ? null : (Date) viewedDate.clone());
	}

	public String getViewedDateString() {
		if (viewedDate == null)
			return "";
		return new SimpleDateFormat(dateFormat).format(viewedDate);
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}
}
