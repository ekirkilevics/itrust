package edu.ncsu.csc.itrust.http.appointment.lhcp;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

/**
 * 
 * @author tedlowery
 *
 */

public class AddAppointmentRequestHTTPTest extends iTrustHTTPTest {

	public void testAddAppointment1() throws Exception {
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
		assertTrue(wr.getText().contains("Add a new Appointment Request"));
		wr = wr.getLinkWith("Add a new Appointment Request").click();
		WebForm form = wr.getForms()[0];
		form.setParameter("weeks", "3");
		form.setParameter("reason", "Need to the see this patient");
		form.setParameter("patientMID", "2");
		form.setParameter("minutesString", "45");
		wr = form.submit();
		assertTrue(wr.getText().contains("Request Successfully Sent"));
		wr = wr.getLinkWith("My Appointments").click();
		
		WebTable wt = wr.getTableStartingWith("Request History");
		assertTrue(wt.getTableCell(5, 5).getText().equals("45"));
		assertTrue(wt.getTableCell(5, 6).getText().equals("Need to the see this patient"));
	}
}

