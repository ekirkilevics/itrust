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
public class HW3_1Test  extends iTrustHTTPTest {

	/*
	 * Preconditions: LHCP 9000000000 and Patient 2 are in the database, and 9000000000 has authenticated successfully. 
	 * 1. Choose to Request Appointments. 
	 * 2. Input the following: Patient MID: 2; Reason for Visit: Need to check the status of your rash.; and Weeks from Today: 2. 
	 * 3. Submit. 
	 * 4. Logout. 
	 * 5. Login as patient 2. 
	 * 6. View Appointments Page.
	 */
	public void testLCHPRequestAppt() throws Exception {
		gen.clearAllTables();
		gen.hcp0();
		gen.patient2();
		// login as HCP and create appointment
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		wr = wr.getLinkWith("My Appointments").click();
		assertTrue(wr.getText().contains("Appointments"));
		wr = wr.getLinkWith("Appointment Request").click();
		WebForm form = wr.getForms()[0];
		form.setParameter("reason", "Need to check the status of your rash.");
		form.setParameter("patientMID", "2");
		form.setParameter("weeks", "2");
		form.setParameter("minutesString", "111");
		wr = form.submit();
		assertTrue(wr.getText().contains("Request Successfully Sent"));
		wr = wr.getLinkWith("Logout").click();
		
		// login as patient 2 to verify
		wc = login("2", "pw");
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		wr = wr.getLinkWith("My Appointments").click();
		assertTrue(wr.getText().contains("Appointments"));
		WebTable wt = wr.getTableStartingWith("Requests Needing Response");
		assertEquals(wt.getTableCell(2, 0).getText(), "Kelly Doctor");
		assertEquals(wt.getTableCell(2, 3).getText(), "111");
		assertEquals(wt.getTableCell(2, 4).getText(), "Need to check the status of your rash.");
		assertEquals(wt.getTableCell(2, 5).getText(), "2");

	}

}
