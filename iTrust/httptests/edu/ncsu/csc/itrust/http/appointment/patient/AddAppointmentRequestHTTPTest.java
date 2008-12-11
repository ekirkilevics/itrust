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
public class AddAppointmentRequestHTTPTest extends iTrustHTTPTest {

	private static WebConversation wc;
	private static WebResponse wr;
	private static WebForm form;
	
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();

		//login patient 2
		wc = login("2", "pw");
		wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		//click on View My Appointments
		//wr = wr.getLinkWith("View My Appointments").click();
		wr = wr.getLinkWith("Appointment Request").click();
		form = wr.getFormWithName("mainForm");
//		form = wr.getForms()[0];

	}
	
	/* 
	 * add an appointment request, return back to view appointment page.
	 * Authenticate Patient
	 * MID: 2
	 * Password: pw
	 * Choose option View My Record
	 */
	
	public void testAddAppointmentRequest() throws Exception {
		setName("AddAppointmentRequest");
		assertEquals("iTrust - Create Appointment Request", wr.getTitle());
	}

	public void testAddAppointmentRequest1() throws Exception {
		setName("AddAppointmentRequest1");

		form.setParameter("dateone", "11/22/2009");
		form.setParameter("timeone", "09:00");		
		form.setParameter("datetwo", "11/22/2009");
		form.setParameter("timetwo", "09:00");
		wr = form.submit();
		assertTrue(wr.getText().contains("Either a LHCP or Hospital ID must be entered as a 10-digit number"));
	}
	
	public void testAddAppointmentRequest2() throws Exception {
		setName("AddAppointmentRequest2");
		form.setParameter("dateone", "11/22/2005");
		form.setParameter("timeone", "09:00");
		form.setParameter("datetwo", "11/22/2009");
		form.setParameter("timetwo", "09:00");
		form.getScriptableObject().setParameterValue("UID_PERSONNELID", "9000000000");
		wr = form.submit();
		assertTrue(wr.getText().contains("Date 1 must be in the future"));
	}
	
	public void testAddAppointmentRequest3() throws Exception {
		setName("AddAppointmentRequest3");
		form.setParameter("dateone", "11/22/2009");
		form.setParameter("timeone", "09:00");
		form.setParameter("datetwo", "11/22/2005");
		form.setParameter("timetwo", "09:00");
		form.getScriptableObject().setParameterValue("UID_PERSONNELID", "9000000000");
		wr = form.submit();
		assertTrue(wr.getText().contains("Date 2 must be in the future"));
	}
	
	public void testAddAppointmentRequest4() throws Exception {
		setName("AddAppointmentRequest4");
		form.setParameter("dateone", "11/22/2009");
		form.setParameter("timeone", "09:00");
		form.setParameter("datetwo", "11/23/2009");
		form.setParameter("timetwo", "09:00");
		form.setParameter("reason", "I feel sick! :(");
		form.getScriptableObject().setParameterValue("UID_PERSONNELID", "9000000000");
		wr = form.submit();
		assertTrue(wr.getText().contains("Minutes must be 1-9999"));
	}
	
	public void testAddAppointmentRequest5() throws Exception {
		setName("AddAppointmentRequest5");
		//Test for out of range MID in personelID field
		form.setParameter("dateone", "11/22/2009");
		form.setParameter("timeone", "09:00");
		form.setParameter("datetwo", "11/23/2009");
		form.setParameter("timetwo", "09:00");
		form.setParameter("reason", "I feel sick! :(");
		form.getScriptableObject().setParameterValue("UID_PERSONNELID", "90");
		form.setParameter("minutesString", "60");
		wr = form.submit();
		assertTrue(wr.getText().contains("Either a LHCP or Hospital ID must be entered as a 10-digit number"));
	}
	
	public void testAddAppointmentRequest6() throws Exception {
		setName("AddAppointmentRequest6");
		//Test for out of range MID in personelID field		
		form.setParameter("dateone", "11/22/2009");
		form.setParameter("timeone", "09:00");
		form.setParameter("datetwo", "11/23/2009");
		form.setParameter("timetwo", "09:00");
		form.setParameter("reason", "I feel sick! :(");
		form.setParameter("minutesString", "60");
		wr = form.submit();
		assertTrue(wr.getText().contains("Either a LHCP or Hospital ID must be entered as a 10-digit number"));
	}
	
	public void testAddAppointmentRequest7() throws Exception {
		setName("AddAppointmentRequest7");
		form.setParameter("dateone", "11/22/2009");
		form.setParameter("timeone", "09:00");
		form.setParameter("datetwo", "11/23/2009");
		form.setParameter("timetwo", "09:00");
		form.setParameter("reason", "I feel sick! :(");
		form.getScriptableObject().setParameterValue("UID_PERSONNELID", "9000000000");
		form.setParameter("minutesString", "60");
		wr = form.submit();
		assertTrue(wr.getText().contains("Request Successfully Sent"));
		wr = wr.getLinkWith("My Appointments").click();
		assertTrue(wr.getText().contains("I feel sick! :("));
		WebTable wt = wr.getTableStartingWith("Request History");
		assertEquals("60", wt.getTableCell(4, 5).getText());
		assertEquals("I feel sick! :(", wt.getTableCell(4, 6).getText());
		assertEquals("Need LHCP Confirm", wt.getTableCell(4, 8).getText());
	}
	
	public void tearDown() throws Exception {

	}
}
