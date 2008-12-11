package edu.ncsu.csc.itrust.dao.appointmentrequest;

import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.AppointmentRequestBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AppointmentRequestDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class GetAllAppointmentRequestByMIDListTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private AppointmentRequestDAO appointmentRequestDAO = factory.getAppointmentRequestDAO();

	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.appointmentRequests();
	}

	public void testGetAppointmentRequestList() throws Exception {
		List<AppointmentRequestBean> list = appointmentRequestDAO.getAllAppointmentRequests(2);
		int size = list.size();
		assertEquals(size, 7);
	}
}
