package edu.ncsu.csc.itrust.dao.appointmentrequest;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.AppointmentRequestBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AppointmentRequestDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.testutils.EvilDAOFactory;

public class AppointmentRequestExceptionTest extends TestCase {

	private DAOFactory factory = EvilDAOFactory.getEvilInstance();
	private AppointmentRequestDAO appointmentRequestDAO = factory.getAppointmentRequestDAO();

	@Override
	protected void setUp() throws Exception {
	}

	public void testGetAppointmentRequestException() throws Exception {
		try {
			appointmentRequestDAO.getAppointmentRequest(0L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testAddAppointmentRequestException() throws Exception {
		AppointmentRequestBean bean = new AppointmentRequestBean();
		bean.setRequestedMID(2L);
		bean.setRequesterMID(2L);
		try {
			appointmentRequestDAO.addAppointmentRequest(bean);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testUpdateAppointmentRequestException() throws Exception {
		AppointmentRequestBean bean = new AppointmentRequestBean();
		bean.setRequestedMID(2L);
		bean.setRequesterMID(2L);
		try {
			appointmentRequestDAO.updateAppointmentRequest(bean);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetAllAppointmentRequestsException() throws Exception {
		try {
			appointmentRequestDAO.getAllAppointmentRequests(1L);
			fail("DBException should have been thrown");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}

	public void testGetAllAppointmentRequestByStatusException() throws Exception {
		try {
			appointmentRequestDAO.getAllAppointmentRequestsByStatus(2L, "");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}

	}

	public void testGetAllAppointmentRequestsByHistoryException() throws Exception {
		try {
			appointmentRequestDAO.getAppointmentRequestHistory(2L, "");
		} catch (DBException e) {
			assertEquals(EvilDAOFactory.MESSAGE, e.getSQLException().getMessage());
		}
	}
}
