package edu.ncsu.csc.itrust.action;

import java.text.SimpleDateFormat;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.iTrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class ViewMyAppointmentsActionTest extends TestCase {
	private ViewMyAppointmentsAction action;
	private DAOFactory factory = TestDAOFactory.getTestInstance();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient2();
		gen.hcp0();
		gen.hcp3();
		gen.hospitals();
		gen.appointments();
		gen.appointmentRequests();
	}

	public void testViewMyAppointments() throws Exception {
		action = new ViewMyAppointmentsAction(factory, 2L);
		assertEquals(3, action.getAllHospitals().size());
		assertEquals("Test Hospital 1", action.getHospital("1").getHospitalName());
		assertEquals(2L, action.getPatient().getMID());
		assertNull(action.getPersonnel()); // we're a patient, so this method makes no sense
		assertEquals(1, action.getAllPatients().size());
		assertEquals(2, action.getAllPersonnel().size());
		assertEquals(1, action.getAppointments().size());
		assertEquals(0, action.getAppointmentsForNextWeek().size());
		assertEquals(5, action.getRequestsNeedingReponse("Need Patient Confirm").size());
		assertEquals(2, action.getRequestHistory("Need Patient Confirm").size());
		assertEquals(1L, action.getAppointmentRequest(1L).getID());
		assertFalse("No time conflict", action.checkAppointmentConflictForPatient(2L, new SimpleDateFormat(
				"MM/dd/yyyy").parse("05/19/2008"), 100));
		assertFalse("Passing null date", action.checkAppointmentConflictForPatient(2L, null, 100));
		assertFalse("No time conflict", action.checkAppointmentConflictForLHCP(9000000003L,
				new SimpleDateFormat("MM/dd/yyyy").parse("05/19/2008"), 100));
		assertFalse("Passing null date", action.checkAppointmentConflictForLHCP(9000000000L, null, 100));
		// Schedule one that needs confirm
		try {
			action.scheduleAppointmentRequestByPatient(5L, 1);
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("Unable to schedule this appointment request, status is Rejected", e.getMessage());
		}
		action.scheduleAppointmentRequestByPatient(7L, 1);
		action.scheduleAppointmentRequestByLHCP(9000000000L, 3L, 1);
		action.rejectAppointmentRequestByPatient(1L);
		action.rejectAppointmentRequestByLHCP(2L);
		try {
			action.scheduleAppointmentRequestByLHCP(9000000000L, 1L, 1);
			fail("exception should have been thrown");
		} catch (iTrustException e) {
			assertEquals("Unable to schedule this appointment request, status is Rejected", e.getMessage());
		}
	}
}
