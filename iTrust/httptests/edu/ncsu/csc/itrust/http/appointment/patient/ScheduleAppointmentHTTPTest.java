/**
 * 
 */
package edu.ncsu.csc.itrust.http.appointment.patient;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import edu.ncsu.csc.itrust.http.iTrustHTTPTest;

/**
 * @author Kathryn Lemanski
 *
 */
public class ScheduleAppointmentHTTPTest extends iTrustHTTPTest {

	/* 
	 * Authenticate Patient
	 * MID: 2
	 * Password: pw
	 * Choose option View My Record
	 */
	public void testScheduleAppointment1() throws Exception {
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
		WebTable wt = wr.getTableStartingWith("Requests Needing Response");
		wr = wt.getTableCell(4, 6).getLinkWith("Schedule").click();
		assertTrue(wr.getText().contains("Schedule this appointment:"));
		//TODO: add more test here???
	}



}
