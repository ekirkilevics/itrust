package edu.ncsu.csc.itrust.beans;

import java.sql.Date;

public class ProcedureBean {
	private long ovProcedureID = 0L;
	private String CPTCode;
	private String description;
	private String attribute;
	private Date date;

	public ProcedureBean() {
	}

	public ProcedureBean(String code) {
		CPTCode = code;
	}

	public ProcedureBean(String code, String description) {
		CPTCode = code;
		this.description = description;
	}

	public ProcedureBean(String code, String description, String attribute) {
		CPTCode = code;
		this.description = description;
		this.attribute = attribute;
	}
	
	/**
	 * Gets the CPT Code for this procedure
	 * 
	 * @return The CPT Code for this procedure
	 */
	public String getCPTCode() {
		return CPTCode;
	}

	public void setCPTCode(String code) {
		CPTCode = code;
	}
	
	/**
	 * Gets the CPT Description for this procedure
	 * 
	 * @return The CPT Description for this procedure
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the CPT attribute, used to determine if this is an immunization
	 * @return String
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * Sets the CPT attribute, used to determine if this is an immunization
	 * @param String
	 */
	public void setAttribute(String attrib) {
		attribute = attrib;
	}
	
	public long getOvProcedureID() {
		return ovProcedureID;
	}

	public void setOvProcedureID(long ovProcedureID) {
		this.ovProcedureID = ovProcedureID;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date d) {
		date = d;
	}

}
