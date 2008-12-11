package edu.ncsu.csc.itrust.dao.appointment;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.AppointmentBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AppointmentDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;

public class AppointmentExceptionTest extends TestCase {

	private DAOFactory factory = EvilDAOFactory.getEvilInstance();
	private AppointmentDAO appointmentDAO = factory.getAppointmentDAO();

	@Override
	protected void setUp() throws Exception {
	}

	public void testGetAppointmentException() throws Exception {
		try {
			appointmentDAO.getAppointment(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetAllAppointmentsException() throws Exception {
		try {
			appointmentDAO.getAllAppointments(1L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testAddAppointmentException() throws Exception {
		AppointmentBean bean = new AppointmentBean();
		bean.setPatientMID(2L);
		try {
			appointmentDAO.addAppointment(bean);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

}
