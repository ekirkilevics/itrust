package edu.ncsu.csc.itrust.http.appointment.lhcp;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

public class ScheduleAppointmentHTTPTest extends iTrustHTTPTest {

	/* 
	 * Authenticate HCP
	 * MID: 9000000000
	 * Password: pw
	 * Choose option View My Record
	 */
	public void testScheduleAppointment1() throws Exception {
		gen.clearAllTables();
		gen.patient2();
		gen.hcp0();
		gen.appointmentRequests();
		//login HCP
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		//click on View My Appointments
		wr = wr.getLinkWith("My Appointments").click();
		assertEquals("iTrust - View My Appointments", wr.getTitle());
		WebTable wt = wr.getTableStartingWith("Requests Needing Response");
		wr = wt.getTableCell(2, 6).getLinkWith("Schedule").click();
		assertTrue(wr.getText().contains("Schedule this appointment:"));
	}



}
