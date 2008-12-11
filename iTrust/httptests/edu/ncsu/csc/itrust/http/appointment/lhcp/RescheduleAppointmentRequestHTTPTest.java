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
public class RescheduleAppointmentRequestHTTPTest extends iTrustHTTPTest {

	public void testRescheduleAppointment1() throws Exception {
		gen.clearAllTables();
		gen.appointmentRequests();
		gen.patient2();
		gen.hcp0();

		//login HCP 9000000000
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		//click on View My Appointments
		wr = wr.getLinkWith("My Appointments").click();
		assertEquals("iTrust - View My Appointments", wr.getTitle());
		WebTable wt = wr.getTableStartingWith("Requests Needing Response");
		wr = wt.getTableCell(2, 7).getLinkWith("Reschedule").click();
		assertTrue(wr.getText().contains("Reschedule Appointment Request"));
		WebForm form = wr.getForms()[0];
		form.setParameter("dateone", "12/24/2009");
		form.setParameter("timeone", "18:30");
		form.setParameter("datetwo", "01/24/2010");
		form.setParameter("timetwo", "16:00");
		form.setParameter("reason", "Those times didn't work!");
		form.setParameter("minutesString", "22");
		wr = form.submit();
		assertTrue(wr.getText().contains("Request Successfully Sent"));
		
		wt = wr.getTableStartingWith("Request History");
		assertEquals("22", wt.getTableCell(6, 5).getText());
		assertEquals("Those times didn't work!", wt.getTableCell(6, 6).getText());
		assertEquals("Need Patient Confirm", wt.getTableCell(6, 8).getText());
	}

}
