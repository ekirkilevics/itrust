package edu.ncsu.csc.itrust.http.hw4;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

/**
 * 
 * @author Ted Lowery (tblowery@ncsu.edu)
 *
 */
public class hw4_2Test extends iTrustHTTPTest {
	
	/*
	 * An HCP 9000000000, Admin 9000000001, and Patient MID 2 have been entered into the system. 
	 * The HCP logs in and requests a comprehensive report for patient 2. 
	 * The Admin logs in and rejects the new request for patient 2 with justification "You cannot access this patient!" entered.
	 */

	public void testComprehensiveAcceptanceRejected() throws Exception {
		gen.clearAllTables();
		gen.standardData();

		// The HCP logs in and requests a comprehensive report for patient 2.
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		wr = wr.getLinkWith("My Report Requests").click();
		assertTrue(wr.getText().contains("Report Requests"));
		wr = wr.getLinkWith("Add a new Report Request").click();
		assertTrue(wr.getText().contains("Please Select a Patient"));
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Report Request Accepted"));
		
		// The Admin logs in and rejects the new request for patient 2 with justification "You cannot access this patient!" entered.
		wc = login("9000000001", "pw");
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Admin Home", wr.getTitle());
		wr = wr.getLinkWith("All Report Requests").click();
		assertTrue(wr.getText().contains("Report Requests"));
		wr = wr.getLinkWith("Reject").click();
		WebForm form = wr.getForms()[0];
		form.setParameter("Comment", "You cannot access this patient!");
		wr = form.submit();
		assertTrue(wr.getText().contains("Report Request Rejected"));
		
		// The HCP views rejection message.
		wc = login("9000000000", "pw");
		wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		wr = wr.getLinkWith("My Report Requests").click();
		assertTrue(wr.getText().contains("Report Requests"));
		assertTrue(wr.getText().contains("You cannot access this patient!"));
		
		
	}
}
