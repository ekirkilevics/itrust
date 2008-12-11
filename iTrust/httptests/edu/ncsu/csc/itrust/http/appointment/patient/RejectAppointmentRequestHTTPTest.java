package edu.ncsu.csc.itrust.http.appointment.patient;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

/**
 * 
 * @author Ted Lowery (tblowery@ncsu.edu)
 *
 */
public class RejectAppointmentRequestHTTPTest extends iTrustHTTPTest {

	public void testRejectAppointment1() throws Exception {
		gen.clearAllTables();
		gen.appointments();
		gen.appointmentRequests();
		gen.patient2();
		gen.hcp0();
		gen.hcp3();

		//login patient 2
		WebConversation wc = login("2", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		//click on View My Appointments
		wr = wr.getLinkWith("My Appointments").click();
		assertEquals("iTrust - View My Appointments", wr.getTitle());
		assertTrue(wr.getText().contains("Reject"));
		WebTable wt = wr.getTableStartingWith("Requests Needing Response");
		wr = wt.getTableCell(2, 8).getLinkWith("Reject").click();
		assertTrue(wr.getText().contains("Appointment Request Rejected"));
		
		wt = wr.getTableStartingWith("Request History");
		assertEquals("Rejected", wt.getTableCell(2, 8).getText());
		
		//email should be sent (UC 27)
		wr = wr.getLinkWith("Show Fake Emails").click();
		wt = wr.getTableStartingWith("To List");
		assertTrue(wt.getTableCell(1, 3).getText().contains("Dear Kelly Doctor, The appointment with Andy Programmer has been rejected. Additional Comments: need to check the status of your rash."));
	}

}
