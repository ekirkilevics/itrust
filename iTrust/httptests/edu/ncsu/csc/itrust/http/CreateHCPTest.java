package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

public class CreateHCPTest extends iTrustHTTPTest {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.admin1();
		gen.cptCodes();
	}

	/*
	 * Authenticate admin 90000000001
	 * Choose Add HCP option
	 * Physican type not currently implemented
	 * [Role: Licensed Physician]
	 * [Enabled: true]
	 * Last name: Williams
	 * First name: Laurie
	 * Email: laurie@ncsu.edu
	 * Street address 1: 900 Main Campus Dr
	 * Street address 2: BOX 2509
	 * City: Raleigh
	 * State: NC
	 * Zip code: 27606-1234
	 * Phone: 919-100-1000
	 */
	public void testCreateValidHCP() throws Exception {
		// login admin
		WebConversation wc = login("9000000001", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Admin Home", wr.getTitle());
		// click on Add HCP
		wr = wr.getLinkWith("Add HCP").click();
		// add the hcp
		assertEquals("iTrust - Add HCP", wr.getTitle());
		WebForm form = wr.getForms()[0];
		form.setParameter("firstName", "Laurie");
		form.setParameter("lastName", "Williams");
		form.setParameter("email", "laurie@ncsu.edu");
		wr = form.submit();
		// edit the hcp
		wr = wr.getLinkWith("Continue").click();
		assertEquals("iTrust - Edit Personnel", wr.getTitle());
		form = wr.getForms()[0];
		form.setParameter("streetAddress1", "900 Main Campus Dr");
		form.setParameter("streetAddress2", "Box 2509");
		form.setParameter("city", "Raleigh");
		form.setParameter("state", "NC");
		form.setParameter("zip1", "27606");
		form.setParameter("zip2", "1234");
		form.setParameter("phone1", "919");
		form.setParameter("phone2", "100");
		form.setParameter("phone3", "1000");
		form.getSubmitButtons()[0].click();
		wr = wc.getCurrentPage();
		assertTrue(wr.getText().contains("Information Successfully Updated"));
	}
}