package edu.ncsu.csc.itrust.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.ReportRequestBean;

public class ReportRequestBeanTest extends TestCase {
	
	public void testReportRequestBean() throws Exception {
		ReportRequestBean b = new ReportRequestBean();
		assertEquals(0L, b.getID());
		// test setters
		b.setID(1);
		b.setRequesterMID(2);
		b.setPatientMID(3);
		b.setApproverMID(4);
		b.setRequestedDateString("01/01/2008 12:00");
		b.setApprovedDateString("02/02/2008 12:00");
		b.setViewedDateString("03/03/2008 12:00");
		b.setStatus(ReportRequestBean.Requested);
		b.setComment("abc123");
		
		// confirm with getters
		assertEquals(1, b.getID());
		assertEquals(2, b.getRequesterMID());
		assertEquals(3, b.getPatientMID());
		assertEquals(4, b.getApproverMID());
		assertEquals("01/01/2008 12:00", b.getRequestedDateString());
		assertEquals("02/02/2008 12:00", b.getApprovedDateString());
		assertEquals("03/03/2008 12:00", b.getViewedDateString());
		assertEquals(ReportRequestBean.Requested, b.getStatus());
		assertEquals("abc123", b.getComment());
	}
	
	public void testBadApprovedDate() throws Exception {
		ReportRequestBean b = new ReportRequestBean();
		b.setApprovedDate(null);
		assertNull(b.getApprovedDate());
		b.setApprovedDateString("bad format");
		assertNull(b.getApprovedDate());
	}
	public void testBadRequestedDate() throws Exception {
		ReportRequestBean b = new ReportRequestBean();
		b.setRequestedDate(null);
		assertNull(b.getRequestedDate());
		b.setRequestedDateString("bad format");
		assertNull(b.getRequestedDate());
	}

}
