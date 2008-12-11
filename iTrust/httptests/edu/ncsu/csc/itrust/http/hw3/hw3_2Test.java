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
public class hw3_2Test extends iTrustHTTPTest {

	/*
	 * Preconditions: LHCP Request Appt has passed. 
	 * LHCP 9000000000 and Patient 2 are in the database, and 2 has authenticated successfully. 
	 * 1. View Appointments Page. 
	 * 2. View the office visit request created in LHCP Request Appt. 
	 * 3. Input the following: LHCP MID: 9000000000; Date & Time 1: July 25, 2008 @ 10 am; Date & Time 2: July 25, 2008 @ 4:30 pm. 
	 * 4. Submit.
	 */
	public void testPatientSuggestApptDates() throws Exception {
		// run prev testcase to setup request
		gen.clearAllTables();
		gen.standardData();
		
		// login as patient and reschedule request
		WebConversation wc = login("2", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		wr = wr.getLinkWith("My Appointments").click();
		assertEquals("iTrust - View My Appointments", wr.getTitle());
		WebTable wt = wr.getTableStartingWith("Requests Needing Response");
		assertEquals(wt.getTableCell(2, 0).getText(), "Kelly Doctor");
		assertEquals(wt.getTableCell(2, 4).getText(), "need to check the status of your rash.");
		assertEquals(wt.getTableCell(2, 5).getText(), "2");
		wr = wt.getTableCell(2, 7).getLinkWith("Reschedule").click();
		assertEquals("iTrust - Reschedule Appointment Request", wr.getTitle());
		WebForm form = wr.getForms()[0];
		form.setParameter("dateone", "07/25/2080");
		form.setParameter("timeone", "10:00");
		form.setParameter("datetwo", "07/25/2080");
		form.setParameter("timetwo", "16:30");
		form.setParameter("minutesString", "333");
		wr = form.submit();
		assertTrue(wr.getText().contains("Request Successfully Sent"));
	}

}
