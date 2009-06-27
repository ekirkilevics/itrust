package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;


public class ComprehensiveReportingTest extends iTrustHTTPTest {
	
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
	}
	
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
	
	/*
	 * An HCP 9000000000 has been entered into the system. 
	 * The HCP logs in and selects request comprehensive report. 
	 * HCP enters patient 22. The system responds that patient 22 cannot be found.
	 */
	public void testHCPChoosesInvalidPatient() throws Exception {
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
        wr = wc.getResponse(ADDRESS + "/util/getUser.jsp");
        //assertEquals("iTrust - Find User", wr.getTitle());
        wr.getForms()[0].setParameter("mid", "22");
        wr = wr.getForms()[0].submit();
        assertTrue(wr.getText().contains("User does not exist"));
	}
	
	/*
	 * An HCP 9000000000 and Patient MID 2 have been entered into the system. 
	 * The HCP logs in and selects request comprehensive report. 
	 * HCP enters patient 2. 
	 * The system responds with the name of this patient "Andy Programmer" and requests confirmation. 
	 * The HCP realizes this is the incorrect patient and cancels the request.
	 */
	public void testHCPChoosesIncorrectPatient() throws Exception {
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
        wr = wc.getResponse(ADDRESS + "/util/getUser.jsp");
        assertEquals("iTrust - Find User", wr.getTitle());
        wr.getForms()[0].setParameter("mid", "2");
        wr = wr.getForms()[0].submit();
        assertTrue(wr.getText().contains("Andy Programmer"));
        wr = wr.getForms()[0].submit(); // Find another user button
        assertEquals("iTrust - Find User", wr.getTitle());
	}
}
