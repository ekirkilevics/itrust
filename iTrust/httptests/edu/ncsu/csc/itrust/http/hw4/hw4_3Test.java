package edu.ncsu.csc.itrust.http.hw4;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

/**
 * 
 * @author Ted Lowery (tblowery@ncsu.edu)
 *
 */
public class hw4_3Test extends iTrustHTTPTest {

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

}
