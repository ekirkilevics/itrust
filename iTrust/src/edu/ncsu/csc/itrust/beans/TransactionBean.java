package edu.ncsu.csc.itrust.beans;

import java.sql.Timestamp;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class TransactionBean {
	private long transactionID;
	private long loggedInMID;
	private long secondaryMID;
	private TransactionType tranactionType;
	private Timestamp timeLogged;
	private String addedInfo;

	public String getAddedInfo() {
		return addedInfo;
	}

	public void setAddedInfo(String addedInfo) {
		this.addedInfo = addedInfo;
	}

	public long getLoggedInMID() {
		return loggedInMID;
	}

	public void setLoggedInMID(long loggedInMID) {
		this.loggedInMID = loggedInMID;
	}

	public long getSecondaryMID() {
		return secondaryMID;
	}

	public void setSecondaryMID(long secondaryMID) {
		this.secondaryMID = secondaryMID;
	}

	public Timestamp getTimeLogged() {
		return timeLogged;
	}

	public void setTimeLogged(Timestamp timeLogged) {
		this.timeLogged = timeLogged;
	}

	public TransactionType getTranactionType() {
		return tranactionType;
	}

	public void setTranactionType(TransactionType tranactionType) {
		this.tranactionType = tranactionType;
	}

	public long getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(long transactionID) {
		this.transactionID = transactionID;
	}
}