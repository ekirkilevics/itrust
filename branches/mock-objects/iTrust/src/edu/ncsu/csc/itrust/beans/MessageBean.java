package edu.ncsu.csc.itrust.beans;

import java.sql.Timestamp;

/**
 * A bean for storing data about a message from one user to another.
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters (Eclipse Hint: Use Source > Generate Getters and Setters� 
 * to create these easily)
 */
public class MessageBean {
	private long to;
	private long from;
	private long id;
	private long parentMsgId;
	private String body;
	private Timestamp timestamp;
	
	/**
	 * Gets the MIDs of the recipients for this Message
	 * @return
	 */
	public long getTo() {
		return to;
	}
	
	/**
	 * Sets the MIDs of the recipients for this Message
	 * @param to
	 */
	public void setTo(long to) {
		this.to = to;
	}
	
	public long getMessageId() {
		return this.id;
	}
	
	public void setMessageId(long id) {
		this.id = id;
	}

	public long getParentMessageId() {
		return this.parentMsgId;
	}
	
	public void setParentMessageId(long parentMsgId) {
		this.parentMsgId = parentMsgId;
	}
	
	/**
	 * Gets the MID of the sender for this message
	 * @return
	 */
	public long getFrom() {
		return from;
	}

	/**
	 * Sets the MID of the sender for this message
	 * @param from
	 */
	public void setFrom(long from) {
		this.from = from;
	}

	/**
	 * Gets the body of this message
	 * @return
	 */
	public String getBody() {
		return body;
	}
	
	/**
	 * Sets the body of this message
	 * @param body
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * Gets the time for this message
	 * @return
	 */
	public Timestamp getSentDate() {
		return this.timestamp;
	}

	/**
	 * Sets the time for this message
	 * @param timestamp
	 */
	public void setSentDate(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
