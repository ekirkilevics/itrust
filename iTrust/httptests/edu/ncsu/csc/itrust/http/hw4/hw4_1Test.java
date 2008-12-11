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
public class hw4_1Test extends iTrustHTTPTest {
	
	/*
	 * An HCP 9000000000, Admin 9000000001, and Patient MID 2 have been entered into the system. 
	 * The HCP logs in and requests a comprehensive report for patient 2. 
	 * The Admin logs in and approves the new request for patient 2. 
	 * The HCP views the approved comprehensive patient report.
	 */

	public void testComprehensiveAcceptanceSuccess() throws Exception {
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
		
		// The Admin logs in and approves the new request for patient 2.
		wc = login("9000000001", "pw");
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Admin Home", wr.getTitle());
		wr = wr.getLinkWith("All Report Requests").click();
		assertTrue(wr.getText().contains("Report Requests"));
		wr = wr.getLinkWith("Approve").click();
		assertTrue(wr.getText().contains("Report Request Approved"));
		
		// there should be an email sent, as per UC 27, check here.
		wr = wr.getLinkWith("Display Database").click();
		assertEquals("Display Database",wr.getTitle());
		assertTrue(wr.getText().contains("The iTrust Health Care Provider"));
		assertTrue(wr.getText().contains("(9000000000) submitted a request to view your full"));
		assertTrue(wr.getText().contains("The iTrust administrator (9000000001)"));
		assertTrue(wr.getText().contains("approved a one-time viewing of this report"));
		assertTrue(wr.getText().contains("notified when the HCP chooses to view it."));
		
		
		
		// The HCP views the approved comprehensive patient report.

// You can't just call getLinkWith("View")  half the friggin page says view.
//  change to get the link from a table.
		
//		wc = login("9000000000", "pw");
//		wr = wc.getCurrentPage();
//		assertEquals("iTrust - HCP Home", wr.getTitle());
//		wr = wr.getLinkWith("View My Report Requests").click();
//		assertTrue(wr.getText().contains("Report Requests"));
//		wr = wr.getLinkWith("View").click();
//		assertTrue(wr.getText().contains("Comprehensive Patient Report"));
//		
//		// there should be an email sent, as per UC 27, check here.
//		wr = wr.getLinkWith("Back").click();
//		wr = wr.getLinkWith("Display Database").click();
//		assertEquals("Display Database",wr.getTitle());
//		assertTrue(wr.getText().contains("The iTrust Health Care Provider (9000000000) has chosen to view your full"));
//		assertTrue(wr.getText().contains("medical report, which was approved by an iTrust administrator (9000000001)"));
//		assertTrue(wr.getText().contains("This report was only viewable one time"));
		
	}
}
