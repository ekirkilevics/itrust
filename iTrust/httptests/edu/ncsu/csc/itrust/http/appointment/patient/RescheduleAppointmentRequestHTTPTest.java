/**
 * 
 */
package edu.ncsu.csc.itrust.http.appointment.patient;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

/**
 * @author Kathryn Lemanski
 *
 */
public class RescheduleAppointmentRequestHTTPTest extends iTrustHTTPTest {


	public void testRescheduleAppointment1() throws Exception {
		gen.clearAllTables();
		gen.appointmentRequests();
		gen.patient2();
		gen.hcp3();
		gen.hcp0();

		//login patient 2
		WebConversation wc = login("2", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		//click on View My Appointments
		wr = wr.getLinkWith("My Appointments").click();
		assertEquals("iTrust - View My Appointments", wr.getTitle());
		assertTrue(wr.getText().contains("Reject"));
		WebTable wt = wr.getTableStartingWith("Requests Needing Response");
		wr = wt.getTableCell(2, 7).getLinkWith("Reschedule").click();
		assertTrue(wr.getText().contains("Reschedule Appointment Request"));
		WebForm form = wr.getForms()[0];
		form.setParameter("dateone", "12/22/2009");
		form.setParameter("timeone", "18:30");
		form.setParameter("datetwo", "01/23/2010");
		form.setParameter("timetwo", "16:00");
		form.setParameter("reason", "Need to the see doc");
		form.setParameter("minutesString", "222");
		wr = form.submit();
		assertTrue(wr.getText().contains("Request Successfully Sent"));
		
		wt = wr.getTableStartingWith("Request History");
		assertEquals("222", wt.getTableCell(5, 5).getText());
		assertEquals("Need to the see doc", wt.getTableCell(5, 6).getText());
		assertEquals("Need LHCP Confirm", wt.getTableCell(5, 8).getText());
	}

}
