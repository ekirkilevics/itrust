package edu.ncsu.csc.itrust.http.hw3;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

/**
 * 
 * @author Ted Lowery (tblowery@ncsu.edu)
 *
 */
public class hw3_3Test extends iTrustHTTPTest {

	/*
	 * Preconditions: Patient Suggest Appt Dates has passed. LHCP 9000000000 and Patient 2 are in the database, and 9000000000 has authenticated successfully. 
	 * 1. View Appointments Page.
	 * The LHCP is notified that a patient has requested an appointment with him and can view that 
	 * patient 2 requested an appt on July 25, 2008 @ 10 pm and on July 25, 2008 @ 4:30 pm.	 
	 */
	public void testLHCPApptNotify() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		// run prev testcase to setup request
		
		// login as lhcp and test result
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		wr = wr.getLinkWith("My Appointments").click();
		assertTrue(wr.getText().contains("Appointments"));
		WebTable wt = wr.getTableStartingWith("Requests Needing Response");
		assertEquals("Andy Programmer", wt.getTableCell(2, 0).getText());
		assertEquals("07/25/2007 10:00", wt.getTableCell(2, 1).getText());
		assertEquals("07/25/2007 16:30", wt.getTableCell(2, 2).getText());
	}

}
