package edu.ncsu.csc.itrust.dao.appointment;

import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.AppointmentBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AppointmentDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class GetAppointmentListTest extends TestCase {

	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private AppointmentDAO apptDAO = factory.getAppointmentDAO();
	private AppointmentBean apptBean = new AppointmentBean();

	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.appointments();
		apptBean.setAppointmentDate(new java.sql.Date(System.currentTimeMillis()));
		apptBean.setMinutes(33);
		apptBean.setPatientMID(1L);
		apptBean.setRequestID(1L);
		apptBean.setLHCPMID(90000000000L);

	}

	public void testGetAppointmentList() throws Exception {

		List<AppointmentBean> list1 = apptDAO.getAllAppointments(2);// only with patient mid that begins with
																	// 2
		int size1 = list1.size();
		assertEquals(1, size1);
		assertEquals(2, list1.get(0).getRequestID());
		assertEquals(1, list1.get(0).getID());
		// assertEquals('2007-07-25 16:30:00', list.get(0).getAppointmentDate());
		assertEquals(9000000000L, list1.get(0).getLHCPMID());

		List<AppointmentBean> list2 = apptDAO.getAllAppointments(1);
		int size2 = list2.size();
		assertEquals(1, size2);
		assertEquals(2, list2.get(0).getID());
		assertEquals(4, list2.get(0).getRequestID());

		List<AppointmentBean> list3 = apptDAO.getAllAppointments(9000000000L); // looking up by a doctor
		int size3 = list3.size();
		assertEquals(2, size3);
		assertEquals(2, list3.get(1).getID());

		List<AppointmentBean> list4 = apptDAO.getAllAppointmentsForLHCP(9000000000L); // looking up by a
																						// doctor
		int size4 = list4.size();
		assertEquals(2, size4);
		assertEquals(2, list4.get(1).getID());

		List<AppointmentBean> list5 = apptDAO.getAllAppointmentsForPatient(2);// only with patient mid that
																				// begins with 2
		int size5 = list5.size();
		assertEquals(1, size5);
		assertEquals(2, list5.get(0).getRequestID());

		long id = apptDAO.addAppointment(apptBean);
		List<AppointmentBean> list6 = apptDAO.getAllAppointmentsForNextWeek(1L);
		assertEquals(1, list6.size());
		assertEquals(id, list6.get(0).getID());
	}

	public void testGetAppointmentListFailure() throws Exception {

		try {
			apptDAO.getAllAppointmentsForPatient(0);
			fail("Should throw an exception");
		} catch (DBException ex) {
			assertEquals("RequesterMID cannot be null", ex.getSQLException().getMessage());
		}

		try {
			apptDAO.getAllAppointmentsForLHCP(0);
			fail("Should throw an exception");
		} catch (DBException ex) {
			assertEquals("RequesterMID cannot be null", ex.getSQLException().getMessage());
		}

		try {
			apptDAO.getAllAppointmentsForNextWeek(0);
			fail("Should throw an exception");
		} catch (DBException ex) {
			assertEquals("RequesterMID cannot be null", ex.getSQLException().getMessage());
		}
	}

}
