package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import edu.ncsu.csc.itrust.beans.SurveyResultBean;

public class ExtraFeaturesTest extends iTrustHTTPTest {
	
	public void testAddAppointmentRequestViaSurveyResultClickthru() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		// log in as patient
		WebConversation wc = login("2", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		//go to page with HCP Survey Results
		wr = wr.getLinkWith("Satisfaction Survey Results").click();
		assertTrue(wr.getText().contains("Search HCP Survey Results"));
		WebForm form = wr.getForms()[0];
		form.setParameter("hcpHospitalID", "8181818181");
		form.setParameter("hcpSpecialty", SurveyResultBean.ANY_SPECIALTY);
		//view current page to ensure data is correct
		wr = form.submit();
		WebTable wt = wr.getTableStartingWith("LHCP Search Results:");
		assertEquals(3, wt.getRowCount());
		
		// select doctor and set an appointment
		wr = wt.getTableCell(2, 0).getLinkWith("Kelly Doctor").click();
		assertEquals("iTrust - View Personnel Details", wr.getTitle());
		assertTrue(wr.getText().contains("Personnel Details"));
		wr = wr.getLinkWith("Schedule an appointment with this doctor").click();
		assertEquals("iTrust - Create Appointment Request", wr.getTitle());
		form = wr.getForms()[0];
		form.setParameter("reason", "testAddAppointmentRequestViaSurveyResultClickthru");
		form.setParameter("minutesString", "30");
		wr = form.submit();
		assertTrue(wr.getText().contains("Request Successfully Sent"));
		
		// check the log
		wr = wr.getLinkWith("Transaction Log").click();
		assertTrue(wr.getText().contains("Add appointment request"));
	}
}
