package edu.ncsu.csc.itrust.http.hw3;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

/**
 * 
 * @author Ted Lowery (tblowery@ncsu.edu)
 * 
 */
public class hw3_5Test extends iTrustHTTPTest {

	/*
	 * Preconditions: LHCP 9000000000 and Patient 2 are in the database, and patient 2 has authenticated
	 * successfully. 1. Choose to Request Appointments. 2. Input the following: Hosptial ID: 8181818181;
	 * Reason for Visit: I feel sick; Date & Time 1: July 24, 2008 @ 3 pm. 3. Submit.
	 */
	public void testLHCPScheduleAppt() throws Exception {
		gen.clearAllTables();
		gen.standardData();

		// login as patient and add new appt
		WebConversation wc = login("2", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		wr = wr.getLinkWith("My Appointments").click();
		assertTrue(wr.getText().contains("Appointments"));
		wr = wr.getLinkWith("Appointment Request").click();
		WebForm form = wr.getForms()[0];
		form.setParameter("hospitalID", "8181818181");
		form.setParameter("reason", "I feel sick");
		form.setParameter("dateone", "7/24/2080");
		form.setParameter("timeone", "15:00");
		form.setParameter("minutesString", "30");
		wr = form.submit();
		assertTrue(wr.getText().contains("Request Successfully Sent"));
		wr = wr.getLinkWith("Logout").click();

		// login as lhcp to verify
		wc = login("9000000000", "pw");
		wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		wr = wr.getLinkWith("My Appointments").click();
		assertTrue(wr.getText().contains("Appointments"));
		WebTable wt = wr.getTableStartingWith("Requests Needing Response");
		assertEquals("Andy Programmer", wt.getTableCell(2, 0).getText());
		assertEquals("07/25/2007 10:00", wt.getTableCell(2, 1).getText());
		assertEquals("20", wt.getTableCell(2, 3).getText());
		assertEquals("", wt.getTableCell(2, 4).getText());
	}

}
