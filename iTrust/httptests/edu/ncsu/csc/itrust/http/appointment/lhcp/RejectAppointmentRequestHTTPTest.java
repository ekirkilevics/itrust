package edu.ncsu.csc.itrust.http.appointment.lhcp;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

/**
 * 
 * @author tedlowery
 *
 */
public class RejectAppointmentRequestHTTPTest extends iTrustHTTPTest {

	public void testAddAppointment1() throws Exception {
		gen.clearAllTables();
		gen.appointmentRequests();
		gen.hcp0();
		gen.patient2();
			
		//login HCP
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		//click on View My Appointments
		wr = wr.getLinkWith("My Appointments").click();
		assertTrue(wr.getText().contains("Request History"));
		assertTrue(wr.getText().contains("Requests Needing Response"));
		
		WebTable wt = wr.getTableStartingWith("Requests Needing Response");
		wr = wt.getTableCell(3, 8).getLinkWith("Reject").click();
		assertTrue(wr.getText().contains("Appointment Request Rejected"));
		
		wt = wr.getTableStartingWith("Request History");
		assertEquals("Rejected", wt.getTableCell(5, 8).getText());
		//email should be sent (UC 27)
		//wr = wr.getLinkWith("Back to iTrust").click();
		wr = wr.getLinkWith("Show Fake Emails").click();
		wt = wr.getTableStartingWith("To List");
		assertTrue(wt.getTableCell(1, 3).getText().contains("Dear Andy Programmer, The appointment with Kelly Doctor has been rejected. Additional Comments: I feel sick"));
	}

}
