package edu.ncsu.csc.itrust.dao.appointmentrequest;

import java.util.List;
import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.AppointmentRequestBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.AppointmentRequestDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class GetAllAppointmentsByRequestHistoryTest extends TestCase {


	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private AppointmentRequestDAO appointmentRequestDAO = factory.getAppointmentRequestDAO();
	
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.appointmentRequests();
	}
	
	public void testGetByHistoryList1() throws Exception {
		List<AppointmentRequestBean> list = appointmentRequestDAO.getAppointmentRequestHistory(2L, "Scheduled");
		int size = list.size();
		assertEquals(7, size);
		
		assertEquals(9000000000L, list.get(2).getRequestedMID());
		assertEquals("routine checkup", list.get(3).getReason());
	}
	
	public void testGetByHistory2() throws Exception {
		List<AppointmentRequestBean> list = appointmentRequestDAO.getAppointmentRequestHistory(9000000003L, "Need Patient Confirm");
		int size = list.size();
		assertEquals(0, size);
	}
	
	public void testGetByHistory3() throws Exception {
		List<AppointmentRequestBean> list = appointmentRequestDAO.getAppointmentRequestHistory(1L, "Rejected");
		int size = list.size();
		assertEquals(1, size);
	}

}
