package edu.ncsu.csc.itrust.http.appointment.patient;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

public class ViewAppointmentRequestHTTPTest extends iTrustHTTPTest {
	
	private static WebConversation wc;
	private static WebResponse wr;
	
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();

		//login patient 2
		wc = login("2", "pw");
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		//click on View My Appointments
		wr = wr.getLinkWith("My Appointments").click();	
	}
	
	/* 
	 * View appointments, add a request, return back to view appointment page.
	 * Authenticate Patient
	 * MID: 2
	 * Password: pw
	 * Choose option View My Record
	 */
	public void testViewAppointmentRequest() throws Exception {
		setName("ViewAppointmentRequest");
		assertTrue(wr.getText().contains("Request History"));
		assertTrue(wr.getText().contains("Requests Needing Response"));
		assertTrue(wr.getText().contains("Appointment Request"));
		assertTrue(wr.getText().contains("need to check the status of your rash."));
	}
	
}
