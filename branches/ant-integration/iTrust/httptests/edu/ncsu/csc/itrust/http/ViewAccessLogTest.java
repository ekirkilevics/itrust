package edu.ncsu.csc.itrust.http;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

public class ViewAccessLogTest extends iTrustHTTPTest {

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.uap1();
		gen.patient2();
		gen.patient1();
		gen.patient4();
		gen.hcp0();
	}

	/*
	 * HCP 9000000000 has viewed PHR of patient 2.
	 * Authenticate Patient
	 * MID: 2
	 * Password: pw
	 * Choose option View Access Log
	 */
	public void testViewAccessLog1() throws Exception {
		// clear operational profile
		gen.transactionLog();
		// hcp views phr of patient 2
		// login hcp
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		// click Edit PHR
		wr = wr.getLinkWith("PHR Information").click();
		assertEquals(ADDRESS + "auth/getPatientID.jsp?forward=hcp-uap/editPHR.jsp", wr.getURL().toString());
		// choose patient 2
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "2");
		patientForm.getButtons()[1].click();
		wr = wc.getCurrentPage();
		assertEquals(ADDRESS + "auth/hcp-uap/editPHR.jsp", wr.getURL().toString());
		// login patient 2
		wc = login("2", "pw");
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		// click on View Access Log
		wr = wr.getLinkWith("Access Log").click();
		// check the table that displays the access log
		
		WebTable table = wr.getTableStartingWithPrefix("Date");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        
		assertTrue(table.getCellAsText(1, 0).contains(dateFormat.format(date)));
		assertEquals("Kelly Doctor", table.getCellAsText(1, 1));
		//assertEquals("EditPHR - view patient record", table.getCellAsText(1, 2));		
	}

	/*
	 * HCP 9000000000 has viewed PHR of patient 2 on 11/11/2007.
	 * Authenticate Patient
	 * MID: 2
	 * Password: pw
	 * Choose option View Access Log
	 * Choose date range 6/22/2000 through 6/23/2000
	 */
	public void testViewAccessLog2() throws Exception {
		// clear operational profile
		gen.transactionLog();
		// login patient 2
		WebConversation wc = login("2", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		// click on View Access Log
		wr = wr.getLinkWith("Access Log").click();
		// select the date range and submit
		WebForm form = wr.getForms()[0];
		form.setParameter("startDate", "06/22/2000");
		form.setParameter("endDate", "06/23/2000");
		form.getSubmitButtons()[0].click();
		WebResponse add = wc.getCurrentPage();
		assertFalse(add.getText().contains("Exception"));
	}

	/*
	 * Authenticate Patient
	 * MID: 1
	 * Password: pw
	 * Choose option View Access Log
	 */
	public void testViewAccessLog3() throws Exception {
		// clear operational profile
		gen.transactionLog();
		// make sure that no exceptions are thrown even though patient 1 has nothing in the view
		// access log
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		wr = wr.getLinkWith("Access Log").click();
		assertFalse(wr.getText().contains("Exception"));
	}
}
